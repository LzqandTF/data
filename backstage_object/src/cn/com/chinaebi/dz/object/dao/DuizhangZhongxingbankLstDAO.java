package cn.com.chinaebi.dz.object.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst;
import cn.com.chinaebi.dz.object.base.BaseDuizhangZhongxingbankLstDAO;
import cn.com.chinaebi.dz.util.ConnectionUtil;
import cn.com.chinaebi.dz.util.TradeBeanValueMap;

/**
 * 中信银行对账数据同步到数据库类
 * @author Admin
 *
 */
public class DuizhangZhongxingbankLstDAO extends BaseDuizhangZhongxingbankLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangZhongxingbankLstDAO {

	private Log log = LogFactory.getLog(getClass());
	public DuizhangZhongxingbankLstDAO () {}
	
	public DuizhangZhongxingbankLstDAO (Session session) {
		super(session);
	}
	
	/**
	 *保存中信银行对账数据到数据库中
	 * (non-Javadoc)
	 * @see cn.com.chinaebi.dz.object.dao.iface.DuizhangZhongxingbankLstDAO#saveBankData(java.util.List)
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
			log.info("批量处理条数："+counts.length);
		}catch(Exception e){
			log.error(e);
			throw e;
		}
	}
	
	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String sql,int batchNum,String tradeTime) throws Exception{
		Session session = null;
		Connection conn = null;
		String[] sql_arr = sql.split(";");
		ArrayList<String> list = new ArrayList<String>();
		try{
			session = this.getSession("data_manager_hibernate.xml");
			conn = ConnectionUtil.getConnecttion();
			conn.setAutoCommit(false);
			SQLQuery q = session.createSQLQuery("select count(*) from duizhang_zhongxingbank_lst where deduct_stlm_date like '"+tradeTime+"%'");
			Integer c = Integer.valueOf(q.uniqueResult().toString());
			log.info("查询中信银行对账文件日期为"+tradeTime+"的数据"+(c==0?"为0":"不为0"));
			if(c > 0){
				log.info("日期为"+tradeTime+"的中信银行对账文件数据存在，执行删除操作");
				SQLQuery query = session.createSQLQuery("delete from duizhang_zhongxingbank_lst where deduct_stlm_date like ?");
				query.setString(0, tradeTime + "%");
				int count = query.executeUpdate();
				if(count > 0){
					log.info("删除日期为"+tradeTime+"的中信银行对账文件数据成功，提交事务");
					
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
			if(list.size() <batchNum){
				saveBankData(list,conn);
			}
			conn.commit();
			return true;
		}catch(Exception e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("事务回滚异常："+e1.getMessage());
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
	 * 由于中信银行可能存在多个对账文件，所以在这里调用存储过程，进行多个文件之间的数据差异处理
	 */
	public void excutePro() {
		Session session = null;
		Connection conn = null;
		try {
			session = this.getSession("data_manager_hibernate.xml");
			conn = session.connection();
			log.info("执行存储过程……");
			CallableStatement proc = conn.prepareCall("{call pro_duizhang_zhongxinbank()}");
			proc.execute();
			conn.commit();
			conn.close();
		} catch (HibernateException e) {
			log.info(e);
		} catch (SQLException e) {
			try {
				log.info("捕获异常，事务回滚……");
				conn.rollback();
			} catch (SQLException e1) {
				log.info("事务回滚异常:"+e1);
			}
			log.info(e);
		}finally{
			if(session!=null){
				log.info("关闭session");
				session.close();
			}
		}
	}
	
	
	@Override
	public boolean updateError(String reqSysStance,Integer whetherErroeHandle){
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_zhongxingbank_lst where reqSysStance = '"+reqSysStance+"'");
			Integer countQuery = Integer.valueOf(query.uniqueResult().toString());
			if(countQuery > 0){
				transaction = session.beginTransaction();
				SQLQuery sqlQuery = session.createSQLQuery("update duizhang_zhongxingbank_lst set whetherErroeHandle = ? where reqSysStance = ?");
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
			log.error(e);
			transaction.rollback();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return flag;
	}
	
	@Override
	public boolean findDzFileData(Object entity,Object[] oriObject,Object[] dzFileObjct,Integer instId,boolean isRiqie,String deductStmlDate)throws Exception {
		Session session = null;
		boolean flag = false;
		
		if(oriObject.length != dzFileObjct.length){
			throw new Exception("DuizhangZhongxingbankLstDAO.findDzFileData 配置查询字段不匹配");
		}
		
		StringBuffer sqlBuffer = new StringBuffer();
		String sql = "";
		if(dzFileObjct != null &&dzFileObjct.length > 0){
			sqlBuffer.append("select count(*) from duizhang_zhongxingbank_lst where deduct_stlm_date = '"+deductStmlDate+"' and ");
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
	public boolean findDateData(String reqTime) {
		Session session = null;
		boolean flag = false;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_zhongxingbank_lst where deduct_stlm_date = '"+reqTime+"'");
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
	
	/**
	 * 根据时间，删除中信银行对账文件数据表中的数据,避免重复
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
				SQLQuery query = session.createSQLQuery("delete from duizhang_zhongxingbank_lst where deductStlmDate = ?");
				query.setString(0, summaryFileDate);
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
	 * 手动对账之前，更改中信银行对账数据的是否差错处理字段状态
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
				SQLQuery query = session.createSQLQuery("update duizhang_zhongxingbank_lst set whetherErroeHandle = 0 where tradeTime like ?");
				query.setString(0, summaryDate + "%");
				int count = query.executeUpdate();
				if(count > 0){
					transaction.commit();
					flag = true;
				}
			}catch(Exception e){
				log.error(e);
				transaction.rollback();
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return flag;
	}
	

	@Override
	public boolean updateClean(String reqSysStance, int duizhangFlag,String deductStlmDate) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				buffer.append("update duizhang_zhongxingbank_lst set bk_chk = ? where reqSysStance = ? and deduct_stlm_date = ?");
				SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
				sqlQuery.setInteger(0, duizhangFlag);
				sqlQuery.setString(1, reqSysStance);
				sqlQuery.setString(2, deductStlmDate);
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

	@Override
	public List<DuizhangZhongxingbankLst> findDateData(String reqTime, int bkChk) {
		Session session = null;
		List<DuizhangZhongxingbankLst> list = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from DuizhangZhongxingbankLst where DeductStlmDate_ = '"+reqTime+"' and bk_chk = 0");
			Object obj = query.list();
			if(obj != null){
				list = (List<DuizhangZhongxingbankLst>)obj;
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
	public String getAcceptorPayFeeByTraceFromDzZxFile(String sysStance){
		Session session = null;
		String acceptorPayFee = "";
		if(StringUtils.isNotBlank(sysStance)){
			try{
				session = this.getSession();
				SQLQuery query = session.createSQLQuery("select tradeFee from duizhang_zhongxingbank_lst where reqSysStance = ?");
				query.setString(0, sysStance);
				acceptorPayFee = query.uniqueResult()+"";
				acceptorPayFee = StringUtils.isBlank(acceptorPayFee)?"0.0":String.format("%.2f",Double.valueOf(acceptorPayFee)/100);
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
}