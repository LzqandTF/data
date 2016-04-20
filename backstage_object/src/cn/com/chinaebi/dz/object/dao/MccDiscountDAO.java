package cn.com.chinaebi.dz.object.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.object.base.BaseMccDiscountDAO;


public class MccDiscountDAO extends BaseMccDiscountDAO implements cn.com.chinaebi.dz.object.dao.iface.MccDiscountDAO {

	private Log log = LogFactory.getLog(getClass());
	public MccDiscountDAO () {}
	
	public MccDiscountDAO (Session session) {
		super(session);
	}

	@Override
	public String getMccDiscountTotal(String mccCode) {
		Session session = null;
		String mccStr = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select mcc_fee from mcc_discount where mcc_code = ?");
			query.setString(0, mccCode);
			Object obj = query.uniqueResult();
			if(obj != null){
				mccStr = obj.toString();
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return mccStr;
	}

	@Override
	public Object[] getMccDiscount(String mccCode) {
		Session session = null;
		Object[] mccStr = null;
		try {
			session = this.getSession();
			SQLQuery query = session.createSQLQuery("select issuers,unionpay from mcc_discount where mcc_code = ?");
			query.setString(0, mccCode);
			Object obj = query.uniqueResult();
			if(obj != null){
				mccStr = (Object[])obj;
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return mccStr;
	}


}