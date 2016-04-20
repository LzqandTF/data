package cn.com.chinaebi.dz.object.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseInstRateDAO;


public class InstRateDAO extends BaseInstRateDAO implements cn.com.chinaebi.dz.object.dao.iface.InstRateDAO {

	private Log logger = LogFactory.getLog(getClass());
	public InstRateDAO () {}
	
	public InstRateDAO (Session session) {
		super(session);
	}

	@Override
	public Object[] getInstRateType(Integer inst_id, Integer inst_type) {
		Session session = null;
		Object[] type = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select inst_rate_type,inst_rate_mcc,bank_inst_code,whetherReturnFee from inst_rate where inst_id = ? and inst_type = ?");
			sqlQuery.setParameter(0, inst_id);
			sqlQuery.setParameter(1, inst_type);
			Object obj = sqlQuery.uniqueResult();
			if(obj != null){
				type = (Object[])obj;
				type[3] = Boolean.valueOf(type[3].toString()) == false ? 0 : 1;
			}else{
				logger.warn("getInstRateType inst_id = "+inst_id+"、inst_type = "+inst_type+" 查询没有获取到该渠道的费率类型");
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return type;
	}

	@Override
	public Map<String, Boolean> findChanelMccRateConf(Integer instId,
			Integer instType) {
		Session session = null;
		Map<String, Boolean> map = null;
		try {
			session = this.getSession();
			StringBuffer buffer = new StringBuffer();
			buffer.append("select dis.mcc_code from inst_rate_mcc_conf rate INNER JOIN mcc_big_type bitType INNER JOIN mcc_type mccType INNER JOIN mcc_discount dis ");
			buffer.append(" on rate.inst_id = ? and rate.inst_type = ? ");
			buffer.append("and rate.mcc_b_type = bitType.big_type_id and bitType.big_type_id = mccType.big_type_id and mccType.type_id = dis.type_id");
			SQLQuery sqlQuery = session.createSQLQuery(buffer.toString());
			sqlQuery.setParameter(0, instId);
			sqlQuery.setParameter(1, instType);
			List list = sqlQuery.list();
			if(list != null && list.size() > 0){
				map = new HashMap<String, Boolean>();
				for (Object object : list) {
					String mcc_code = object.toString();
					map.put(mcc_code, true);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return map;
	}
}