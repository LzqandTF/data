package cn.com.chinaebi.dz.object.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseSettleMerchantMatchTableDAO;


public class SettleMerchantMatchTableDAO extends BaseSettleMerchantMatchTableDAO implements cn.com.chinaebi.dz.object.dao.iface.SettleMerchantMatchTableDAO {

	private Log log = LogFactory.getLog(getClass());
	public SettleMerchantMatchTableDAO () {}
	
	public SettleMerchantMatchTableDAO (Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getSettleMerchantMatchTable(String settleMerCode) {
		List<Object> list = null;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select dy_mer_code from settle_merchant_match_table where settle_mer_code = ?");
			sqlQuery.setParameter(0, settleMerCode);
			list = sqlQuery.list();
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return list;
	}

	@Override
	public String getSettleMerCode(String dyMerCode) {
		Session session = null;
		String settleMerCode = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select settle_mer_code from settle_merchant_match_table where dy_mer_code = ?");
			sqlQuery.setParameter(0, dyMerCode);
			Object obj = sqlQuery.uniqueResult();
			if(obj != null){
				settleMerCode = obj.toString();
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return settleMerCode;
	}
	
	
	@Override
	public Map<String, String> getSettleMerCode() {
		Session session = null;
		Map<String, String> map = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select dy_mer_code,settle_mer_code from settle_merchant_match_table");
			List list = sqlQuery.list();
			if(list != null){
				map = new HashMap<String, String>();
				for (Object object : list) {
					Object[] obj = (Object[])object;
					map.put(obj[0].toString(), obj[1].toString());
				}
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return map;
	}


}