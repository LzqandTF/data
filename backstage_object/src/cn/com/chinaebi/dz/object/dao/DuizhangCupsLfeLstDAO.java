package cn.com.chinaebi.dz.object.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseDuizhangCupsLfeLstDAO;
import cn.com.chinaebi.dz.util.ConnectionUtil;


public class DuizhangCupsLfeLstDAO extends BaseDuizhangCupsLfeLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangCupsLfeLstDAO {

	public DuizhangCupsLfeLstDAO () {}
	
	public DuizhangCupsLfeLstDAO (Session session) {
		super(session);
	}
	
	private Log log =LogFactory.getLog(getClass());

	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String sql,int batchNum,String deduct_stlm_date) throws Exception{
		Session session = null;
		Connection conn = null;
		String[] sql_arr = sql.split(";");
		ArrayList<String> list = new ArrayList<String>();
		try{
			session = this.getSession();
			conn = ConnectionUtil.getConnecttion();
			conn.setAutoCommit(false);
			SQLQuery q = session.createSQLQuery("select count(*) from duizhang_cups_lfe_lst where deduct_stlm_date like '%"+deduct_stlm_date+"%'");
			Integer c = Integer.valueOf(q.uniqueResult().toString());
			log.info("查询日期为"+deduct_stlm_date+"的数据"+(c==0?"为0":"不为0"));
			if(c > 0){
				log.info("日期为"+deduct_stlm_date+"的银联cups品牌服务费对账文件数据存在，执行删除操作");
				SQLQuery query = session.createSQLQuery("delete from duizhang_cups_lfe_lst where deduct_stlm_date like ?");
				query.setString(0, "%" + deduct_stlm_date + "%");
				int count = query.executeUpdate();
				if(count > 0){
					log.info("删除日期为"+deduct_stlm_date+"的银联cups品牌服务费对账文件数据成功，提交事务");
					
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
	 *保存银联cups品牌服务费对账数据到数据库中
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


}