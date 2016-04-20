package cn.com.chinaebi.dz.object.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.cust.risk.common.sms.SMSSendUtil;
import cn.com.chinaebi.dz.object.DuizhangBeijingbankLst;
import cn.com.chinaebi.dz.object.DuizhangCupsLst;
import cn.com.chinaebi.dz.object.base.BaseDuizhangBeijingbankLstDAO;
import cn.com.chinaebi.dz.util.ConnectionUtil;
import cn.com.chinaebi.dz.util.TradeBeanValueMap;

/**
 * 北京银行对账数据同步到数据库类
 * @author Admin
 *
 */
public class DuizhangBeijingbankLstDAO extends BaseDuizhangBeijingbankLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangBeijingbankLstDAO{
	
	private Log log =LogFactory.getLog(getClass());

	//private RiqieBeijingbankLst riqieBeijingbankLst = null;
	//private OriginalBeijingbankLst originalBeijingbankLst = null;

	public DuizhangBeijingbankLstDAO(){}
	public DuizhangBeijingbankLstDAO (Session session) {
		super(session);
	}
	
	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String sql,int batchNum,String reqTime) throws Exception{
		Session session = null;
		Connection conn = null;
		String[] sql_arr = sql.split(";");
		ArrayList<String> list = new ArrayList<String>();
		try{
			session = this.getSession();
			conn = ConnectionUtil.getConnecttion();
			conn.setAutoCommit(false);
			SQLQuery q = session.createSQLQuery("select count(*) from duizhang_beijingbank_lst where deduct_stlm_date like '%"+reqTime+"%'");
			Integer c = Integer.valueOf(q.uniqueResult().toString());
			log.info("查询日期为"+reqTime+"的数据"+(c==0?"为0":"不为0"));
			if(c > 0){
				log.info("日期为"+reqTime+"的北京银行对账文件数据存在，执行删除操作");
				SQLQuery query = session.createSQLQuery("delete from duizhang_beijingbank_lst where deduct_stlm_date like ?");
				query.setString(0, "%" + reqTime + "%");
				int count = query.executeUpdate();
				if(count > 0){
					log.info("删除日期为"+reqTime+"的北京银行对账文件数据成功，提交事务");
					
				}
			}
			for(int index = 0;index<sql_arr.length;index++){
				if(sql_arr[index] != null && !"".equals(sql_arr[index])){
					list.add(sql_arr[index]);
					if(index % batchNum == 0){
						saveBankData(list,conn);
						list.removeAll(list);
					}
				}
			}
			if(list.size() < batchNum){
				saveBankData(list,conn);
			}
			conn.commit();
			return true;
		}catch(Exception e){
			try {
				if(conn != null){
					conn.rollback();
				}
			} catch (SQLException e1) {
				log.info("事务回滚异常:"+e1);
			}
			log.error(e);
			throw e;
		}finally{
			if(conn != null){
				try {  
					ConnectionUtil.closeConnection(conn);  
	             } catch (SQLException e) {  
	            	 log.error(e);  
	             } 
			}
			if(session != null){
				session.close();
			}
		}
	}
	/**
	 *保存北京银行对账数据到数据库中
	 * 
	 */
	private void saveBankData(List<String> sqlList,Connection conn) throws Exception{
		Statement stmt = null;
		int[] counts = null;
		try{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			log.info("insert语句数组长度为："+sqlList.size());
			for(int index = 0;index<sqlList.size();index++){
				if(sqlList.get(index) != null && !"".equals(sqlList.get(index))){
					stmt.addBatch(sqlList.get(index));
				}
			}
			counts = stmt.executeBatch();
		}catch(Exception e){
			log.error(e);
			throw e;
		}
	}
	
	@Override
	public List<DuizhangBeijingbankLst> findDuizhangBeijingbankLst(String reqTime) {
		List<DuizhangBeijingbankLst> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from DuizhangBeijingbankLst where substring(ReqTime,1,4) = ?");
			query.setString(0, reqTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
				session.flush();
				session.clear();
			}else{
				log.warn("from DuizhangBeijingbankLst where Pepdate = "+reqTime +" is not data");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}
	
	
/*	public static void main(String[] args) {
		cn.com.chinaebi.dz.object.dao.iface.DuizhangBeijingbankLstDAO beijingbankLstDAO = DuizhangBeijingbankLstDAO.getInstance();
//		List<DuizhangBeijingbankLst> list = beijingbankLstDAO.findDuizhangBeijingbankLst("0206");
//		for (DuizhangBeijingbankLst duizhangBeijingbankLst : list) {
//			System.out.println(duizhangBeijingbankLst.getReqTime());
//		}
		System.out.println(beijingbankLstDAO.updateError("551243", 1));
		
	}*/
	
	@Override
	public boolean updateError(String reqSysStance,Integer whetherErroeHandle){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_beijingbank_lst where reqSysStance = '"+reqSysStance+"'");
			Integer countQuery = Integer.valueOf(query.uniqueResult().toString());
			if(countQuery > 0){
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update duizhang_beijingbank_lst set whetherErroeHandle = ? where reqSysStance = ?");
				sqlQuery.setInteger(0,whetherErroeHandle);
				sqlQuery.setString(1,reqSysStance);
				int count = sqlQuery.executeUpdate();
				if(count > 0){
					transaction.commit();
					session.flush();
					session.clear();
					flag = true;
				}
			}else{
				log.error("select count(*) duizhang_beijingbank_lst where reqSysStance = "+reqSysStance +" is not data");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return flag;
	}
	
	@Override
	public String findDzFileData(Object entity,Object[] oriObject,Object[] dzFileObjct,Integer instId,boolean isRiqie
			,String deductStmlDate) throws Exception{
		Session session = null;
		String tradeFee = "0";
		if(oriObject.length != dzFileObjct.length){
			throw new Exception("DuizhangBeijingbankLstDAO.findDzFileData 配置查询字段不匹配");
		}
		
		StringBuffer sqlBuffer = new StringBuffer();
		String sql = "";
		if(dzFileObjct != null &&dzFileObjct.length > 0){
			sqlBuffer.append("select acceptorReceiveFee,acceptorPayFee from duizhang_beijingbank_lst where deduct_stlm_date = '"+deductStmlDate+"' and ");
			for (Object object : dzFileObjct) {
				if("deductSysReference".equals(object)){
					sqlBuffer.append("substring("+object +",7,12)=? and ");
				}else{
					sqlBuffer.append(object +"=? and ");
				}
			}
			sql = sqlBuffer.toString().substring(0, sqlBuffer.toString().length()-5);
		}
		
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			for (int i=0;i<oriObject.length;i++) {
				if(isRiqie)
					query.setParameter(i,TradeBeanValueMap.getRiqieValueOfExcel(String.valueOf(oriObject[i]), entity, instId));
				else
					query.setParameter(i,TradeBeanValueMap.getOriginalValueOfNotRollBkForExcel(String.valueOf(oriObject[i]), entity, instId));
			}
			Object obj = query.uniqueResult();
			if(obj != null){
				Object[] objArr = (Object[])obj;
				String acceptorReceiveFee = objArr[0].toString();
				String acceptorPayFee = objArr[1].toString();
				tradeFee = 	StringUtils.equals(acceptorReceiveFee, "000000000000") ? acceptorPayFee:acceptorReceiveFee;
				tradeFee = String.valueOf(Double.valueOf(tradeFee)/100);
			}else{
				tradeFee = null;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return tradeFee;
	}
	
	@Override
	public Map<String, String> findDzFileData(String deductStmlDate) throws Exception{
		Session session = null;
		Map<String, String> map = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select reqSysStance,acceptorReceiveFee,acceptorPayFee from duizhang_beijingbank_lst where deduct_stlm_date = ?");
			query.setParameter(0, deductStmlDate);
			List list = query.list();
			if(list != null && list.size() > 0){
				map = new HashMap<String, String>();
				for (Object object : list) {
					Object[] obj = (Object[])object;
					String tradeFee = "0";
					String reqSysStance = obj[0].toString();
					String acceptorReceiveFee = obj[1].toString();
					String acceptorPayFee = obj[2].toString();
					tradeFee = 	StringUtils.equals(acceptorReceiveFee, "000000000000") ? acceptorPayFee:acceptorReceiveFee;
					tradeFee = String.valueOf(Double.valueOf(tradeFee)/100);
//					reqSysStance = reqSysStance.substring(6);
					if(StringUtils.isNotBlank(reqSysStance)){
						map.put(reqSysStance, tradeFee);
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return map;
	}
	
	@Override
	public boolean findDateData(String reqTime) {
		Session session = null;
		boolean flag = false;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_beijingbank_lst where deduct_stlm_date = '"+reqTime+"'");
			Integer count = Integer.valueOf(query.uniqueResult().toString());
			if(count > 0){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return flag;
	}
	
	@Override
	public boolean updateClean(String reqSysStance, int duizhangFlag,boolean deductRollBk,String deductStlmDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				buffer.append("update duizhang_beijingbank_lst set bk_chk = ? where deduct_stlm_date = ? and substring(deductSysReference,7,12) = ?");
				if(deductRollBk){
					buffer.append(" and msgType = '0420'");
				}
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setInteger(0, duizhangFlag);
				sqlQuery.setString(1, deductStlmDate);
				sqlQuery.setString(2, reqSysStance);
				int count =  sqlQuery.executeUpdate();
				if(count > -1 ){
					transaction.commit();
					flag = true;
				}else{
					transaction.rollback();
				}
			} catch (Exception e) {
				transaction.rollback();
				log.error(e);
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.error(reqSysStance +" 流水号不存在");
		}
		return flag;
	}
	/**
	 * 根据时间，删除北京银行对账文件数据表中的数据,避免重复
	 * @param summaryFileDate
	 */
	public void deleteDzDataBySummaryDate(String summaryFileDate) {
		Session session = null;
		Transaction transaction = null;
		if(StringUtils.isNotBlank(summaryFileDate)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("delete from duizhang_beijingbank_lst where reqTime like ?");
				query.setString(0, summaryFileDate + "%");
				int count = query.executeUpdate();
				if(count > 0){
					transaction.commit();
				}
			}catch(Exception e){
				transaction.rollback();
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
	}
	
	/**
	 * 手动对账之前，更改北京银行对账数据的是否差错处理字段状态
	 * @param summaryDate
	 * @return
	 */
	public boolean updatewhetherErroeHandleByTradeTime(String summaryDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		if(StringUtils.isNotBlank(summaryDate)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("update duizhang_beijingbank_lst set whetherErroeHandle = 0 where reqTime like ?");
				query.setString(0, summaryDate + "%");
				int count = query.executeUpdate();
				if(count > 0){
					transaction.commit();
					flag = true;
				}
			}catch(Exception e){
				transaction.rollback();
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return flag;
	}
	
	public boolean sendMessage(String eventId,String msg_req_url){
		Session session = null;
		boolean flag = false; 
		try{
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select linkMan,remark from message_config where eventName = ?");
			query.setString(0, eventId);
			List<Object[]> list = query.list();
			List<String> list_phones = new ArrayList<String>();
			if(list!=null && list.size()>0){
				String linkMan = list.get(0)[0].toString();
				log.info("手机联系人信息："+linkMan);
				String[] linkMans = linkMan.split(",");
				for(int i=0;i<linkMans.length;i++){
					list_phones.add(linkMans[i]);
				}
				String remark = list.get(0)[1].toString();
				Map<String,Boolean> map = SMSSendUtil.sendRequest(list_phones,remark, msg_req_url);
				log.info(map.get(list_phones.get(0))==true?"发送成功":"发送失败");
				log.info(map.get(list_phones.get(1))==true?"发送成功":"发送失败");
			}else{
				log.info("未找到短信联系人");
			}
		}catch(Exception e){
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return flag;
	}
	
	@Override
	public List<DuizhangBeijingbankLst> findNotDuiZhangFileData(String reqTime) {
		Session session = null;
		List<DuizhangBeijingbankLst> list = null;
		if(StringUtils.isNotBlank(reqTime)){
			try{
				session = this.getSession();
				Query query = session.createQuery("from duizhang_beijingbank_lst where deduct_stlm_date = ?");
				query.setString(0, reqTime);
				List list_result = query.list();
				if(list_result != null){
					list = list_result;
				}else{
					log.info("北京银行对账 "+reqTime+" 日期没有未对账数据");
				}
			}catch(Exception e){
				log.error(e.getMessage());
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}else{
			log.info("拉取北京银行对账文件未对账数据日期不能为空");
		}
		return list;
	}
	@Override
	public List<DuizhangBeijingbankLst> findDateData(String reqTime, int bkChk) {
		Session session = null;
		List<DuizhangBeijingbankLst> list = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from DuizhangBeijingbankLst where DeductStlmDate = '"+reqTime+"' and bk_chk = 0");
			Object obj = query.list();
			if(obj != null){
				list = (List<DuizhangBeijingbankLst>)obj;
			}else{
				log.info("没有获取到银联CUPS对账文件未对账可疑交易数据");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzBjFile(String sysStance,String date){
		Session session = null;
		String acceptorPayFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				SQLQuery query = session.createSQLQuery("select acceptorPayFee from duizhang_beijingbank_lst where substring(deductSysReference,7) = ? and deduct_stlm_date = ? ");
				query.setString(0, sysStance);
				query.setString(1, date.replaceAll("-", ""));
				acceptorPayFee = query.uniqueResult()+"";
				acceptorPayFee = (StringUtils.isBlank(acceptorPayFee) || "null".equals(acceptorPayFee) || 0==Double.valueOf(acceptorPayFee))?"0.0":String.format("%.2f",Double.valueOf(acceptorPayFee)/100);
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return acceptorPayFee;
	}
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费(针对冲正交易)
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorReceiveFeeByTraceFromDzBjFile(String sysStance,String date){
		Session session = null;
		String acceptorReceiveFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				SQLQuery query = session.createSQLQuery("select acceptorReceiveFee from duizhang_beijingbank_lst where substring(deductSysReference,7) = ? and deduct_stlm_date = ? ");
				query.setString(0, sysStance);
				query.setString(1, date.replaceAll("-", ""));
				acceptorReceiveFee = query.uniqueResult()+"";
				acceptorReceiveFee = (StringUtils.isBlank(acceptorReceiveFee) || "null".equals(acceptorReceiveFee) || 0==Double.valueOf(acceptorReceiveFee))?"0.0":String.format("%.2f",0-(Double.valueOf(acceptorReceiveFee)/100));
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return acceptorReceiveFee;
	}
	
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzBjbankFile(String sysStance,String date){
		Session session = null;
		List<DuizhangCupsLst> list = null;
		String acceptorPayFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				Query query = session.createQuery("from DuizhangBeijingbankLst where ReqSysStance = ?  and DeductStlmDate = ? ");
				query.setString(0, sysStance);
				query.setString(1, date.replaceAll("-", ""));
				List listResult = query.list();
				if(listResult != null && listResult.size() > 0){
					list = listResult;
					acceptorPayFee = list.get(0).getAcceptorPayFee();
					if(StringUtils.isBlank(acceptorPayFee)){
						acceptorPayFee = list.get(0).getAcceptorReceiveFee();
					}else{
						if(Integer.valueOf(acceptorPayFee)==0){
							if(StringUtils.isNotBlank(list.get(0).getAcceptorReceiveFee())){
								acceptorPayFee = (0-Double.valueOf(list.get(0).getAcceptorReceiveFee()))+"";
							}
						}
					}
				}
				acceptorPayFee = (StringUtils.isBlank(acceptorPayFee) || "null".equals(acceptorPayFee) || 0==Double.valueOf(acceptorPayFee))?"0.0":String.format("%.2f",Double.valueOf(acceptorPayFee)/100);
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return acceptorPayFee;
	}
	
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费,针对差错总表的方法
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzBjFile(String sysStance){
		Session session = null;
		String acceptorPayFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				SQLQuery query = session.createSQLQuery("select acceptorPayFee from duizhang_beijingbank_lst where substring(deductSysReference,7) = ? ");
				query.setString(0, sysStance);
				acceptorPayFee = query.uniqueResult()+"";
				acceptorPayFee = (StringUtils.isBlank(acceptorPayFee) || "null".equals(acceptorPayFee) || 0==Double.valueOf(acceptorPayFee))?"0.0":String.format("%.2f",Double.valueOf(acceptorPayFee)/100);
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return acceptorPayFee;
	}
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费(针对冲正交易),针对差错总表的方法
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorReceiveFeeByTraceFromDzBjFile(String sysStance){
		Session session = null;
		String acceptorReceiveFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				SQLQuery query = session.createSQLQuery("select acceptorReceiveFee from duizhang_beijingbank_lst where substring(deductSysReference,7) = ? ");
				query.setString(0, sysStance);
				acceptorReceiveFee = query.uniqueResult()+"";
				acceptorReceiveFee = (StringUtils.isBlank(acceptorReceiveFee) || "null".equals(acceptorReceiveFee) || 0==Double.valueOf(acceptorReceiveFee))?"0.0":String.format("%.2f",0-(Double.valueOf(acceptorReceiveFee)/100));
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return acceptorReceiveFee;
	}
}
