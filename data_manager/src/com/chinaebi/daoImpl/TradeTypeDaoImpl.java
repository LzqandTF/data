package com.chinaebi.daoImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chinaebi.dao.TradeTypeDao;
import com.chinaebi.entity.TradeType;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "tradeTypeDao")
public class TradeTypeDaoImpl extends MyBatisDao implements TradeTypeDao {
	
	// 记录查询时的日志
		private Logger logger = LoggerFactory.getLogger(getClass());
	
	/* (non-Javadoc)
	 * @see com.chinaebi.daoImpl.TradeTypeDao#queryTradeType(java.lang.String)
	 */
	@Override
	public String queryTradeType (String tradeCode) {
		Object object = getSqlSession().selectOne("Trade_Type.queryTradeType", tradeCode);
		if(object != null)
			return (String) object;
		else {
			logger.error(tradeCode +": 获取交易码信息数据查询为空");
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TradeType> queryAll() {
		List list = getSqlSession().selectList("Trade_Type.queryAll");
		if(list != null)
			return (List<TradeType>)list;
		else {
			logger.error("获取交易码信息数据查询为空");
			return null;
		}
	}
}

