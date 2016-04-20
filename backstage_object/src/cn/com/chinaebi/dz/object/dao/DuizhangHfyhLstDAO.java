package cn.com.chinaebi.dz.object.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseDuizhangHfyhLstDAO;
import cn.com.chinaebi.dz.util.ConnectionUtil;


public class DuizhangHfyhLstDAO extends BaseDuizhangHfyhLstDAO implements cn.com.chinaebi.dz.object.dao.iface.DuizhangHfyhLstDAO {

	public DuizhangHfyhLstDAO () {}
	
	public DuizhangHfyhLstDAO (Session session) {
		super(session);
	}
	private Log log =LogFactory.getLog(getClass());
	
	
	@Override
	public boolean insertBankData(String sql,String deduct_stlm_date) throws Exception{
		Session session = null;
		Connection conn = null;
		String[] sql_arr = sql.split(";");
		try{
			session = this.getSession();
			conn = ConnectionUtil.getConnecttion();
			conn.setAutoCommit(false);
			saveBankData(sql_arr,conn);
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
	private void saveBankData(String[] sqlArr,Connection conn) throws Exception{
		Statement stmt = null;
		try{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(int index = 0;index<sqlArr.length;index++){
				if(sqlArr[index] != null && !"".equals(sqlArr[index])){
					stmt.addBatch(sqlArr[index]);
				}
			}
			stmt.executeBatch();
		}catch(Exception e){
			log.error(e);
			throw e;
		}
	}


}