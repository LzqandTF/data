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
import cn.com.chinaebi.dz.object.DuizhangCupsLst;
import cn.com.chinaebi.dz.object.base.BaseDuizhangCupsLstDAO;
import cn.com.chinaebi.dz.util.ConnectionUtil;
import cn.com.chinaebi.dz.util.StringHandle;
import cn.com.chinaebi.dz.util.TradeBeanValueMap;

/**
 * 银联对账数据同步到数据库类
 * @author Admin
 *
 **/
public class DuizhangCupsLstDAO extends BaseDuizhangCupsLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangCupsLstDAO{
	
	public DuizhangCupsLstDAO(){};
	public DuizhangCupsLstDAO(Session session){
		super(session);
	}
	private Log log =LogFactory.getLog(getClass());
	
	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String sql,int batchNum,String deduct_stlm_date,String type) throws Exception{
		Session session = null;
		Connection conn = null;
		String[] sql_arr = sql.split(";");
		ArrayList<String> list = new ArrayList<String>();
		try{
			session = this.getSession();
			conn = ConnectionUtil.getConnecttion();
			conn.setAutoCommit(false);
			String sql_query = "";
			String sql_delete = "";
			String log_query = "";
			String log_delete = "";
			if(StringUtils.isBlank(type)){
				sql_query = "select count(*) from duizhang_cups_lst where deduct_stlm_date like '%"+deduct_stlm_date+"%'";
				sql_delete = "delete from duizhang_cups_lst where deduct_stlm_date like ?";
				log_query = "查询银联cups对账文件日期为"+deduct_stlm_date+"的数据";
				log_delete = "删除日期为"+deduct_stlm_date+"的银联cups对账文件数据成功，提交事务";
			}else{
				sql_query = "select count(*) from duizhang_error_cups_lst where reqTime like '%"+deduct_stlm_date+"%'";
				sql_delete = "delete from duizhang_error_cups_lst where reqTime like ?";
				log_query = "查询银联cups差错对账文件日期为"+deduct_stlm_date+"的数据";
				log_delete = "删除日期为"+deduct_stlm_date+"的银联cups差错对账文件数据成功，提交事务";
			}
			SQLQuery q = session.createSQLQuery(sql_query);
			Integer c = Integer.valueOf(q.uniqueResult().toString());
			log.info(log_query+(c==0?"为0":"不为0"));
			if(c > 0){
				log.info("日期为"+deduct_stlm_date+"的数据存在，执行删除操作");
				SQLQuery query = session.createSQLQuery(sql_delete);
				query.setString(0, "%" + deduct_stlm_date + "%");
				int count = query.executeUpdate();
				if(count > 0){
					log.info(log_delete);
					
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
				log.error(e1);
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
	 *保存银联对账数据到数据库中
	 * 
	 */
	private void saveBankData(List<String> sqlList,Connection conn) throws Exception{
		Statement stmt = null;
		int[] counts = null;
		try{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
	public List<DuizhangCupsLst> findDuizhangCupsLst(String reqTime) {
		List<DuizhangCupsLst> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from DuizhangCupsLst where substring(ReqTime,1,4) = ?");
			query.setString(0, reqTime);
			List listResult = query.list();
			if(listResult != null && listResult.size() > 0){
				list = listResult;
				session.flush();
				session.clear();
			}else{
				log.warn("from DuizhangCupsLst where Pepdate = "+reqTime +" is not data");
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
	
	@Override
	public boolean updateError(String reqSysStance,Integer whetherErroeHandle){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_cups_lst where reqSysStance = '"+reqSysStance+"'");
			Integer countQuery = Integer.valueOf(query.uniqueResult().toString());
			if(countQuery > 0){
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update duizhang_cups_lst set whetherErroeHandle = ? where reqSysStance = ?");
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
				log.error("select count(*) duizhang_cups_lst where reqSysStance = "+reqSysStance +" is not data");
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
	public String findDzFileData(Object entity,Object[] oriObject,Object[] dzFileObjct,Integer instId,boolean isRiqie,String duizhangCupsReqTime) throws Exception {
		Session session = null;
		String tradeFee = "0";
		
		if(oriObject.length != dzFileObjct.length){
			throw new Exception("DuizhangCupsLstDAO.findDzFileData 配置查询字段不匹配");
		}
		
		StringBuffer sqlBuffer = new StringBuffer();
		String sql = "";
		if(dzFileObjct != null &&dzFileObjct.length > 0){
			sqlBuffer.append("select acceptorReceiveFee,acceptorPayFee from duizhang_cups_lst where deduct_stlm_date = '"+duizhangCupsReqTime+"' and ");
			for (Object object : dzFileObjct) {
				sqlBuffer.append(object +"=? and ");
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
	public Map<String, String> findDzFileData(String duizhangCupsReqTime) throws Exception {
		Session session = null;
		Map<String, String> cupsDz_map = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select acceptorReceiveFee,acceptorPayFee,reqSysStance from duizhang_cups_lst where deduct_stlm_date = ?");
			query.setParameter(0, duizhangCupsReqTime);
			List list = query.list();
			if(list != null && list.size() > 0){
				cupsDz_map = new HashMap<String, String>();
				for (Object object : list) {
					Object[] obj = (Object[])object;
					String tradeFee = "0";
					String acceptorReceiveFee = obj[0].toString();
					String acceptorPayFee = obj[1].toString();
					String reqSysStance = obj[2].toString();
					tradeFee = 	StringUtils.equals(acceptorReceiveFee, "000000000000") ? acceptorPayFee:acceptorReceiveFee;
					tradeFee = String.valueOf(Double.valueOf(tradeFee)/100);
					cupsDz_map.put(reqSysStance, tradeFee);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return cupsDz_map;
	}
	
	@Override
	public boolean findDateData(String reqTime) {
		Session session = null;
		boolean flag = false;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_cups_lst where deduct_stlm_date = '"+reqTime+"'");
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
	public boolean updateClean(String reqSysStance, int duizhangFlag,boolean deductRollBk,String deductStmlDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				buffer.append("update duizhang_cups_lst set bk_chk = ? where reqSysStance = ? and deduct_stlm_date = ?");
				if(deductRollBk){
					buffer.append(" and msgType = '0420'");
				}
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setInteger(0, duizhangFlag);
				sqlQuery.setString(1, reqSysStance);
				sqlQuery.setString(2, deductStmlDate);
				int count =  sqlQuery.executeUpdate();
				if(count > 0 ){
					transaction.commit();
					flag = true;
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
	 * 根据时间，删除银联银行对账文件数据表中的数据,避免重复
	 * @param summaryFileDate
	 */
	@Override
	public void deleteDzDataBySummaryDate(String summaryFileDate) {
		Session session = null;
		Transaction transaction = null;
		if(StringUtils.isNotBlank(summaryFileDate)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery query = session.createSQLQuery("delete from duizhang_cups_lst where reqTime like ?");
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
	 * 手动对账之前，更改银联对账文件的是否差错处理字段状态
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
				SQLQuery query = session.createSQLQuery("update duizhang_cups_lst set whetherErroeHandle = 0 where reqTime like ?");
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
				//log.info("手机联系人信息："+linkMan);
				String[] linkMans = linkMan.split(",");
				for(int i=0;i<linkMans.length;i++){
					list_phones.add(linkMans[i]);
				}
				String remark = list.get(0)[1].toString();
				//log.info(remark);
				Map<String,Boolean> map = SMSSendUtil.sendRequest(list_phones,remark, msg_req_url);
				//log.info(map.get(list_phones.get(0))==true?"发送成功":"发送失败");
				//log.info(map.get(list_phones.get(1))==true?"发送成功":"发送失败");
			}else{
				log.warn("未找到短信联系人");
				//System.out.println("未找到短信联系人");
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
	
	public synchronized boolean insertDzFileData(String date,String file_name,String file_type,String create_last_time,String file_path,int object_id,String object_name){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		if(StringUtils.isNotBlank(date)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery q_count = session.createSQLQuery("select count(*) from dz_file_tab where deduct_sys_date=? and file_type=? and file_name=? and object_id=?");
				q_count.setString(0, date);
				q_count.setString(1, file_type);
				q_count.setString(2, file_name);
				q_count.setInteger(3, object_id);
				Integer num = Integer.valueOf(q_count.uniqueResult().toString());
				log.info("根据日期，名称，文件类型三个条件查询对账文件表数据条数为:"+num);
				if(num > 0){
					log.info("更新对账文件表操作:");
					SQLQuery q_update = session.createSQLQuery("update dz_file_tab set create_last_time=?,file_path=? where deduct_sys_date=? and file_type=? and file_name=? and object_id=?");
					q_update.setString(0, create_last_time);
					q_update.setString(1, file_path);
					q_update.setString(2, date);
					q_update.setString(3, file_type);
					q_update.setString(4, file_name);
					q_update.setInteger(5, object_id);
					int count = q_update.executeUpdate();
					if(count > 0){
						transaction.commit();
						flag = true;
					}
				}else{
					log.info("向对账文件表中插入数据:");
					SQLQuery query = session.createSQLQuery("INSERT INTO dz_file_tab(deduct_sys_date,file_type,file_name,create_last_time,file_path,object_id,object_name) VALUES(?,?,?,?,?,?,?)");
					query.setString(0, date);
					query.setString(1, file_type);
					query.setString(2, file_name);
					query.setString(3, create_last_time);
					query.setString(4, file_path);
					query.setInteger(5, object_id);
					query.setString(6, object_name);
					int count = query.executeUpdate();
					if(count > 0){
						transaction.commit();
						flag = true;
					}
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
	
	public boolean deleteErrorDzDataBySummaryDate(String summaryFileDate) {
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		if(StringUtils.isNotBlank(summaryFileDate)){
			try{
				session = this.getSession();
				transaction = session.beginTransaction();
				SQLQuery q = session.createSQLQuery("select count(*) from duizhang_error_cups_lst where reqTime like ?");
				q.setString(0, summaryFileDate + "%");
				Integer num = Integer.valueOf(q.uniqueResult().toString());
				if(num > 0){
					SQLQuery query = session.createSQLQuery("delete from duizhang_error_cups_lst where reqTime like ?");
					query.setString(0, summaryFileDate + "%");
					int count = query.executeUpdate();
					log.info("删除数据条数:"+count);
					if(count > 0){
						transaction.commit();
						flag = true;
					}
				}else{
					log.info("需要删除数据条数为0");
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
	
	@Override
	public List<DuizhangCupsLst> findDateData(String reqTime, int bkChk) {
		Session session = null;
		List<DuizhangCupsLst> list = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from DuizhangCupsLst where DeductStlmDate = '"+reqTime+"' and bk_chk = 0");
			Object obj = query.list();
			if(obj != null){
				list = (List<DuizhangCupsLst>)obj;
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
	public String getAcceptorPayFeeByTraceFromDzCupsFile(String sysStance,String date){
		Session session = null;
		List<DuizhangCupsLst> list = null;
		String acceptorPayFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				Query query = session.createQuery("from DuizhangCupsLst where ReqSysStance = ?  and DeductStlmDate = ? ");
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
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费（针对冲正交易）
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorReceiveFeeByTraceFromDzCupsFile(String sysStance,String date){
		Session session = null;
		String acceptorReceiveFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				SQLQuery query = session.createSQLQuery("select acceptorReceiveFee from duizhang_cups_lst where reqSysStance = ?  and deduct_stlm_date = ? ");
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
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费，针对差错总表的查询方法
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzCupsFile(String sysStance){
		Session session = null;
		List<DuizhangCupsLst> list = null;
		String acceptorPayFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				Query query = session.createQuery("from DuizhangCupsLst where ReqSysStance = ? ");
				query.setString(0, sysStance);
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
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费（针对冲正交易），针对差错总表的查询方法
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorReceiveFeeByTraceFromDzCupsFile(String sysStance){
		Session session = null;
		String acceptorReceiveFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				SQLQuery query = session.createSQLQuery("select acceptorReceiveFee from duizhang_cups_lst where reqSysStance = ? ");
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
