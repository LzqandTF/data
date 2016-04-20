package com.chinaebi.dao;

import java.util.List;

import com.chinaebi.entity.TradeType;

public interface TradeTypeDao {

	public  String queryTradeType(String tradeCode);
	
	public  List<TradeType> queryAll();

}
