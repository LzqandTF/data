package cn.com.chinaebi.dz.object.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.object.base.BaseMerBalanceDAO;


public class MerBalanceDAO extends BaseMerBalanceDAO implements cn.com.chinaebi.dz.object.dao.iface.MerBalanceDAO {
	
	private Log log = LogFactory.getLog(getClass());
	public MerBalanceDAO () {}
	
	public MerBalanceDAO (Session session) {
		super(session);
	}

	@Override
	public MerBalance findMerBalance(String mer_code) {
		Session session = null;
		MerBalance merBalance = null;
		try {
			session = this.getSession();
			Query query = session.createQuery("from MerBalance where MerCode = ?");
			query.setString(0, mer_code);
			Object obj = query.uniqueResult();
			if(obj != null){
				merBalance = (MerBalance)obj;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return merBalance;
	}

	@Override
	public int updateMerBalanceByMerCode(String mer_code, String mer_balance) {
		int effectNum = 0;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery updateSql = session.createSQLQuery("update mer_balance set mer_balance = ? where mer_code = ?");
			updateSql.setParameter(0, mer_balance);
			updateSql.setParameter(1, mer_code);
			effectNum = updateSql.executeUpdate();
			if (effectNum > 0) {
				transaction.commit();
				log.info("根据商户号" + mer_code + "修改商户余额成功");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error("根据商户号" + mer_code + "修改商户余额出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return effectNum;
	}

	@Override
	public int addMerBalance(MerBalance merBalance) {
		int effectNum = 0;
		Session session = null;
		Transaction transaction = null;
		try {
			session = this.getSession();
			transaction = session.beginTransaction();
			SQLQuery insertSql = session.createSQLQuery("insert into mer_balance(mer_code, mer_category, mer_balance, mer_state) values(?,?,?,?)");
			insertSql.setParameter(0, merBalance.getMerCode());
			insertSql.setParameter(1, merBalance.getMerCategory());
			insertSql.setParameter(2, merBalance.getMerBalance());
			insertSql.setParameter(3, merBalance.getMerState());
			effectNum = insertSql.executeUpdate();
			if (effectNum > 0) {
				transaction.commit();
				log.info("向余额表中插入数据成功");
			}
		} catch (Exception e) {
			transaction.rollback();
			log.error("向余额表中插入数据出现异常：" + e.getMessage());
		} finally {
			closeSession(session);
		}
		return effectNum;
	}

}