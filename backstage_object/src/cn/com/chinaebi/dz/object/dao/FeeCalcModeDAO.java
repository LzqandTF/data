package cn.com.chinaebi.dz.object.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseFeeCalcModeDAO;


public class FeeCalcModeDAO extends BaseFeeCalcModeDAO implements cn.com.chinaebi.dz.object.dao.iface.FeeCalcModeDAO {
	
	private Log log = LogFactory.getLog(getClass());
	public FeeCalcModeDAO () {}
	
	public FeeCalcModeDAO (Session session) {
		super(session);
	}

	@Override
	public String getMerFee(Integer gate, String mid,Integer gid) {
		String mer_fee = null;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select calc_mode from fee_calc_mode where gate = ? and mid = ? and gid = ? and state = 1");
			sqlQuery.setInteger(0, gate);
			sqlQuery.setString(1, mid);
			sqlQuery.setInteger(2, gid);
			Object object = sqlQuery.uniqueResult();
			if(object != null){
				mer_fee = object.toString();
			}else{
				log.warn("网关号："+gate +",渠道号："+gid+",商户号：" + mid +" 获取商户手续费为空");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return mer_fee;
	}

	@Override
	public String getMerFee(String mid, Integer gid) {
		String mer_fee = null;
		Session session = null;
		try {
			session = this.getSession();
			SQLQuery sqlQuery = session.createSQLQuery("select calc_mode from fee_calc_mode where mid = ? and gid = ? and state = 1");
			sqlQuery.setString(0, mid);
			sqlQuery.setInteger(1, gid);
			Object object = sqlQuery.uniqueResult();
			if(object != null){
				mer_fee = object.toString();
			}else{
				log.warn("渠道号："+gid +",商户号：" + mid +" 获取商户手续费为空");
			}
		} catch (Exception e) {
			log.error(e);
		}finally{
			closeSession(session);
		}
		return mer_fee;
	}


}