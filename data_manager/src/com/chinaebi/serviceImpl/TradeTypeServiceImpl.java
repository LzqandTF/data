package com.chinaebi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.TradeTypeDao;
import com.chinaebi.entity.TradeType;
import com.chinaebi.service.TradeTypeService;

@Service(value = "tradeTypeService")
public class TradeTypeServiceImpl implements TradeTypeService {
	
	@Autowired
	@Qualifier(value = "tradeTypeDao")
	private TradeTypeDao tradeTypeDao;
	
	/* (non-Javadoc)
	 * @see com.chinaebi.serviceImpl.TradeTypeService#queryTradeType(java.lang.String)
	 */
	@Override
	public String queryTradeType(String tradeCode) {
		return tradeTypeDao.queryTradeType(tradeCode);
	}

	@Override
	public List<TradeType> queryAll() {
		return tradeTypeDao.queryAll();
	}
}

