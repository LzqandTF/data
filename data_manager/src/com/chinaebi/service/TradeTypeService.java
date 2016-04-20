package com.chinaebi.service;

import java.util.List;

import com.chinaebi.entity.TradeType;

public interface TradeTypeService {

	public String queryTradeType(String tradeCode);
	
	public  List<TradeType> queryAll();

}
