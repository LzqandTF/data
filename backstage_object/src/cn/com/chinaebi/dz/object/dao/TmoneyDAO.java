package cn.com.chinaebi.dz.object.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.base.BaseTmoneyDAO;

import java.sql.CallableStatement;


public class TmoneyDAO extends BaseTmoneyDAO implements cn.com.chinaebi.dz.object.dao.iface.TmoneyDAO {

	public TmoneyDAO () {}
	
	public TmoneyDAO (Session session) {
		super(session);
	}
	
	private Log log = LogFactory.getLog(getClass());

	/**
	 * 调用头寸调拨存储过程方法
	 * @param trade_time 	交易日期
	 * @param tableName		原始数据表名
	 * @param deduct_stlm_date	清算日期
	 * @return
	 */
	@Override
	public boolean proce_pos_head_handle(String trade_time,String tableName,int deduct_stlm_date, int inst_id, int inst_type){
		log.info("进入线下调用存储过程,添加头寸调拨数据方法,参数为：交易日期-"+trade_time+";表名-"+tableName+";清算日期-"+deduct_stlm_date);
		boolean result_flag = false;
		Session session = null;
		Transaction ts = null;
		try {
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_pos_head_handle(?,?,?,?,?)");
				cs.setObject(1, trade_time);
				cs.setObject(2, tableName);
				cs.setObject(3, deduct_stlm_date);
				cs.setObject(4, inst_id);
				cs.setObject(5, inst_type);
				cs.execute();
				ts.commit();
				result_flag = true;
				log.info("调用存储过程,添加头寸调拨数据成功");
			} else {
				log.info("TmoneyDAO.t_bank_transfer() 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			log.error("调用头寸调拨存储过程抛出异常：" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result_flag;
	}

	@Override
	public boolean deleteTmoneyData(int deduct_stlm_date, int inst_id, int inst_type) {
		log.info("进入删除头寸调拨数据方法,参数为：清算日期-"+deduct_stlm_date + "渠道ID-" + inst_id + "渠道类型-" + inst_type);
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = this.getSession();
			transaction = session.beginTransaction();
			String sql = "delete from tmoney where deduct_stlm_date = ? and inst_id = ? and inst_type = ?";
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger(0, deduct_stlm_date);
			query.setInteger(1, inst_id);
			query.setInteger(2, inst_type);
			int count = query.executeUpdate();
			if(count >= 0){
				result = true;
				transaction.commit();
				log.info("删除日期"+deduct_stlm_date+"的头寸调拨数据成功");
			}
		}catch(Exception e){
			try {
				transaction.rollback();
			} catch (HibernateException e1) {
				log.error("根据清算日期删除头寸调拨数据时，事务回滚操作抛出异常"+e1);
			}
			log.error(e);
		}finally{
			closeSession(session);
		}
		return result;
	}

	@Override
	public boolean proce_ryf_head_handle(String trade_time, String tableName,
			int deduct_stlm_date, String tableColumn, String amountColumn, int inst_id, int inst_type) {
		log.info("进入线上调用存储过程,添加头寸调拨数据方法,参数为：交易日期-"+trade_time+";表名-"+tableName+";清算日期-"+deduct_stlm_date);
		boolean result_flag = false;
		Session session = null;
		Transaction ts = null;
		try {
			session = this.getSession();
			if (session != null) {
				ts = session.beginTransaction();
				CallableStatement cs = (CallableStatement) session.connection().prepareCall("call proce_ryf_head_handle(?,?,?,?,?,?,?)");
				cs.setObject(1, Integer.valueOf(trade_time.replaceAll("-", "")));
				cs.setObject(2, tableName);
				cs.setObject(3, tableColumn);
				cs.setObject(4, amountColumn);
				cs.setObject(5, deduct_stlm_date);
				cs.setObject(6, inst_id);
				cs.setObject(7, inst_type);
				cs.execute();
				ts.commit();
				result_flag = true;
				log.info("调用存储过程,添加头寸调拨数据成功");
			} else {
				log.info("TmoneyDAO.t_bank_transfer() 获取session为null");
			}
		} catch (Exception e) {
			ts.rollback();
			log.error("调用头寸调拨存储过程抛出异常：" + e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result_flag;
	}

}