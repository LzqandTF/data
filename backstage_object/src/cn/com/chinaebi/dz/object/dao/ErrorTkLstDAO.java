package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.base.BaseErrorTkLstDAO;

public class ErrorTkLstDAO extends BaseErrorTkLstDAO implements cn.com.chinaebi.dz.object.dao.iface.ErrorTkLstDAO {

	private static final Log log = LogFactory.getLog(ErrorTkLstDAO.class);
	public ErrorTkLstDAO () {}
	
	public ErrorTkLstDAO (Session session) {
		super(session);
	}

	@Override
	public List findErrorHandlingStatus() {
		Session session = null;
		List list = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select trade_id,trade_time,trade_amount,deduct_sys_id,handling_id from error_tk_lst where syn_flag = 0");
			list = sqlQuery.list();
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public boolean updateErrorTkLst(String trade_id) {
		boolean flag = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("update error_tk_lst set syn_flag = 1 where trade_id = ?");
			query.setParameter(0, trade_id);
			int count = query.executeUpdate();
			if(count > 0){
				transaction.commit();
				flag = true;
			}
		} catch(Exception e) {
			log.error("根据trade_id修改差错退款表数据状态syn_flag出现异常:"+e.getMessage());
			transaction.rollback();
		} finally {
			closeSession(session);
		}
		return flag;
	}

}