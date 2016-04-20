package cn.com.chinaebi.dz.object.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.object.base.BaseMerBillingDAO;

public class MerBillingDAO extends BaseMerBillingDAO implements cn.com.chinaebi.dz.object.dao.iface.MerBillingDAO {

	private Log log =LogFactory.getLog(getClass());
	
	public MerBillingDAO () {}
	
	public MerBillingDAO (Session session) {
		super(session);
	}

	@Override
	public MerBilling queryMerBilling(String merCode) {
		MerBilling merBilling = null;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select bil_way,refund_fee from mer_billing where mer_code = ?");
			query.setParameter(0, merCode);
			Object object = query.uniqueResult();
			if (object != null) {
				Object[] objArr = (Object[])object;
				merBilling = new MerBilling();
				merBilling.setBilWay(Integer.valueOf(objArr[0].toString()));
				merBilling.setRefundFee(Integer.valueOf(objArr[1].toString()));
				return merBilling;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return merBilling;
	}

	@Override
	public MerBilling getMerBillingByInSql(String merCode) throws Exception {
		Session session = null;
		MerBilling merBilling = null; 
		Transaction transaction = null;
		List<?> listResult = null;
		try {
			session = this.getSession();
			session.flush();
			session.clear();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("select * from mer_billing where mer_code = ? ").addEntity(MerBilling.class);
			query.setString(0, merCode);
			transaction.commit();
			listResult = query.list();
			for (Object object : listResult) {
				if(object instanceof MerBilling){
					merBilling = (MerBilling) object;
				}
			}
		}catch (Exception e) {
			log.error(e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		log.info(merBilling == null?("select * from mer_billing where mer_code = "+ merCode +" is no data"):"查到商户结算信息为"+merCode+"的数据");
		return merBilling;
	}
}