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

import cn.com.chinaebi.dz.object.DuizhangSzzhLst;
import cn.com.chinaebi.dz.object.base.BaseDuizhangSzzhLstDAO;
import cn.com.chinaebi.dz.util.ConnectionUtil;
import cn.com.chinaebi.dz.util.TradeBeanValueMap;


public class DuizhangSzzhLstDAO extends BaseDuizhangSzzhLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangSzzhLstDAO {

	
	private Log log =LogFactory.getLog(getClass());
	
	public DuizhangSzzhLstDAO () {}
	
	public DuizhangSzzhLstDAO (Session session) {
		super(session);
	}
	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String insertSql,String deduct_stlm_date) throws Exception{
		Session session = null;
		Connection conn = null;
		String[] sql_arr = insertSql.split(";");
		ArrayList<String> list = new ArrayList<String>();
		try{
			session = this.getSession();
			conn = ConnectionUtil.getConnecttion();
			conn.setAutoCommit(false);
			SQLQuery  query = session.createSQLQuery("select count(*) from duizhang_szzh_lst where deduct_stlm_date = ?");//根据清算日期查询数据信息
			query.setString(0, deduct_stlm_date);
			Integer c = Integer.valueOf(query.uniqueResult().toString());
			if(c > 0){
				SQLQuery delete = session.createSQLQuery("delete from duizhang_szzh_lst where deduct_stlm_date = ?");//根据清算日期删除数据信息
				delete.setString(0, deduct_stlm_date);
				int count = delete.executeUpdate();
				if(count > 0){
					for(int index = 0;index<sql_arr.length;index++){
						if(sql_arr[index] != null && !"".equals(sql_arr[index])){
							list.add(sql_arr[index]);
								saveBankData(list,conn);
								list.removeAll(list);
						}
					}
				}
			}else{
				for(int index = 0;index<sql_arr.length;index++){
					if(sql_arr[index] != null && !"".equals(sql_arr[index])){
						list.add(sql_arr[index]);
							saveBankData(list,conn);
							list.removeAll(list);
					}
				}
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
	 * 批量插入商户基本信息和商户结算信息数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String insertSql){
		Session session = null;
		Connection conn = null;
		String[] sql_arr = insertSql.split(";");
		ArrayList<String> list = new ArrayList<String>();
		try{
			session = this.getSession();
			conn = session.connection();
			conn.setAutoCommit(false);
				for(int index = 0;index<sql_arr.length;index++){
					if(sql_arr[index] != null && !"".equals(sql_arr[index])){
						list.add(sql_arr[index]);
							saveBankData(list,conn);
							list.removeAll(list);
					}
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
		}finally{
			if(conn != null){
				try {  
	                 conn.close();  
	             } catch (SQLException e) {  
	            	 log.error(e);  
	             } 
			}
			if(session != null){
				session.close();
			}
		}
		return false;
	}
	/**
	 *保存深圳中行对账数据到数据库中
	 * 
	 */
	private void saveBankData(List<String> sqlList,Connection conn) throws Exception{
		Statement stmt = null;
		try{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(int index = 0;index<sqlList.size();index++){
				if(sqlList.get(index) != null && !"".equals(sqlList.get(index))){
					stmt.addBatch(sqlList.get(index));
				}
			}
			stmt.executeBatch();
		}catch(Exception e){
			log.error(e);
			throw e;
		}
	}

	@Override
	public boolean findDateData(String reqTime) {
		Session session = null;
		boolean flag = false;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select count(*) from duizhang_szzh_lst where deduct_stlm_date = '"+reqTime+"'");
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
	public String findDzFileData(Object entity, Object[] oriObject,
			Object[] dzFileObjct, Integer instId, boolean isRiqie,
			String duizhangReqTime,int tradeMsgType) throws Exception {
		Session session = null;
		String tradeFee = "0";
		
		if(oriObject.length != dzFileObjct.length){
			throw new Exception("DuizhangSzzhLstDAO.findDzFileData 配置查询字段不匹配");
		}
		
		StringBuffer sqlBuffer = new StringBuffer();
		String sql = "";
		if(dzFileObjct != null &&dzFileObjct.length > 0){
			sqlBuffer.append("select tradeFee from duizhang_szzh_lst where deduct_stlm_date = '"+duizhangReqTime+"' and ");
			for (Object object : dzFileObjct) {
				sqlBuffer.append(object +"=? and ");
			}
			if(tradeMsgType == 20){ //退货交易
				sqlBuffer.append(" trade_code = 'REFI'");
				sql = sqlBuffer.toString();
			}else if(tradeMsgType == 2){ //消费交易
				sqlBuffer.append(" trade_code = 'PCEI'");
				sql = sqlBuffer.toString();
			}else
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
				tradeFee = Double.valueOf(obj.toString()).toString();
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
	public Map<String, String> findDzFileData(String duizhangReqTime) throws Exception{
		Session session = null;
		Map<String, String> map = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select tradeFee,deductSysReference,trade_code from duizhang_szzh_lst where deduct_stlm_date = ?");
			query.setParameter(0, duizhangReqTime);
			List list = query.list();
			if(list != null && list.size() > 0){
				map = new HashMap<String, String>();
				for (Object object : list) {
					Object[] obj = (Object[])object;
					String tradeFee = "0";
					tradeFee = Double.valueOf(obj[0].toString()).toString();
					String deductSysReference = obj[1].toString();
					String trade_code = obj[2].toString();
					map.put(deductSysReference+trade_code, tradeFee);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return map;
	}

	@Override
	public boolean updateClean(String reqSysStance, int duizhangFlag,
			boolean deductRollBk, String deductStmlDate,int tradeMsgType) {
		Session session = null;
		Transaction transaction = null;
		boolean flag = false; 
		if(StringUtils.isNotBlank(reqSysStance)){
			try {
				session = this.getSession();
				transaction = session.beginTransaction();
				StringBuffer buffer = new StringBuffer();
				buffer.append("update duizhang_szzh_lst set bk_chk = ? where deductSysReference = ? and deduct_stlm_date = ?");
				if(tradeMsgType == 20){ //退货交易
					buffer.append(" and (trade_code = 'REFI' or trade_code = 'REFP')");
				}else if(tradeMsgType == 2){ //消费交易
					buffer.append(" and (trade_code = 'PCEI' or trade_code = 'PCEP')");
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

	@Override
	public List<DuizhangSzzhLst> findDateData(String reqTime, int bkchk) {
		Session session = null;
		List<DuizhangSzzhLst> duizhangSzzhLsts = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from DuizhangSzzhLst where DeductStlmDate = ? and BkChk = ?");
			query.setString(0, reqTime);
			query.setInteger(1, bkchk);
			List list = query.list();
			if(list != null){
				duizhangSzzhLsts = (List<DuizhangSzzhLst>)list;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return duizhangSzzhLsts;
	}

}