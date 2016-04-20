package cn.com.chinaebi.dz.object.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.com.chinaebi.dz.object.base.BaseTradeAmountConfDAO;


public class TradeAmountConfDAO extends BaseTradeAmountConfDAO implements cn.com.chinaebi.dz.object.dao.iface.TradeAmountConfDAO {

	private Log log = LogFactory.getLog(getClass());
	
	public TradeAmountConfDAO () {}
	
	public TradeAmountConfDAO (Session session) {
		super(session);
	}
	/**
	 * 根据交易类型和处理码获取交易类型中文名称
	 * @param tradeType
	 * @param process
	 * @return
	 */
	public String getTradeTypeName(int tradeMsgType,String process){
		Session session = null;
		String tradeTypeName = "";
		if(tradeMsgType != 0 && StringUtils.isNotBlank(process)){
			try{
				session = this.getSession();
				SQLQuery query = session.createSQLQuery("select name_ from trade_amount_conf where trademsg_type = ? and process = ?");
				query.setInteger(0, tradeMsgType);
				query.setString(1, process);
				tradeTypeName = query.uniqueResult()+"";
			}catch(Exception e){
				log.error(e);
			}finally {
				if (session != null) {
					session.close();
				}
			}
		}
		return tradeTypeName;
	}

}