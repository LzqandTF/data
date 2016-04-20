package cn.com.chinaebi.dz.object.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.InstMerRateConf;
import cn.com.chinaebi.dz.object.InstMerRateConfPK;
import cn.com.chinaebi.dz.object.base.BaseInstMerRateConfDAO;


public class InstMerRateConfDAO extends BaseInstMerRateConfDAO implements cn.com.chinaebi.dz.object.dao.iface.InstMerRateConfDAO {

	private Log logger = LogFactory.getLog(getClass());
	public InstMerRateConfDAO () {}
	
	public InstMerRateConfDAO (Session session) {
		super(session);
	}

	@Override
	public List<InstMerRateConf> getInstMerRateConf(Integer inst_id, Integer inst_type,String mer_code) {
		Session session = null;
		List<InstMerRateConf> instMerRateConfs = new ArrayList<InstMerRateConf>();
		try {
//			if(StringUtils.isNotBlank(card_type))
//				card_type = card_type.toUpperCase();
			
			session = this.getSession();
			//and (card_type = ? or card_type = 'All')
			SQLQuery sqlQuery = session.createSQLQuery("select mer_code,card_type,fee_Poundage,lineOrinter from inst_mer_rate_conf where mer_code = ? and inst_id = ? and inst_type = ?");
			sqlQuery.setParameter(0, mer_code);
			sqlQuery.setParameter(1, inst_id);
			sqlQuery.setParameter(2, inst_type);
//			sqlQuery.setParameter(3, card_type);
			List list = sqlQuery.list();
			if(list != null && list.size() > 0){
				for (Object object : list) {
					Object[] obj = (Object[])object;
					InstMerRateConf instMerRateConf = new InstMerRateConf();
					InstMerRateConfPK id = new InstMerRateConfPK();
					id.setMerCode(obj[0].toString()); //商户号
					id.setCardType(obj[1].toString());//卡类型
					id.setLineOrinter(Integer.valueOf(obj[3] == null ? "0" : obj[3].toString()));
					instMerRateConf.setId(id);
					instMerRateConf.setFeePoundage(obj[2].toString());
					instMerRateConfs.add(instMerRateConf);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			closeSession(session);
		}
		return instMerRateConfs;
	}

}