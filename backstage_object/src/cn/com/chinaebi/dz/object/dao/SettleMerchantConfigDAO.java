package cn.com.chinaebi.dz.object.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseSettleMerchantConfigDAO;


public class SettleMerchantConfigDAO extends BaseSettleMerchantConfigDAO implements cn.com.chinaebi.dz.object.dao.iface.SettleMerchantConfigDAO {

	private Log log = LogFactory.getLog(getClass());
	public SettleMerchantConfigDAO () {}
	
	public SettleMerchantConfigDAO (Session session) {
		super(session);
	}

	@Override
	public List getSettleMerchantConfig() {
		Session session = null;
		List list = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select settle_mer_code from settle_merchant_config");
			list = sqlQuery.list();
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}


}