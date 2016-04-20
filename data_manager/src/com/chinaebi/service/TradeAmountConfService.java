package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.utils.mybaits.Page;

public interface TradeAmountConfService {
	/**
	 * 分页查询交易金额配置信息
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<TradeAmountConf> queryPageTradeAmountConf(Page<TradeAmountConf> page,Map<String, Object> map);

	/**
	 * 
	 * 修改交易金额配置信息
	 * @param tradeAmountConf
	 * @return
	 */
	public int updateTradeAmountConf(TradeAmountConf tradeAmountConf);

	/**
	 * 删除交易金额配置信息
	 * @param id
	 * @return
	 */
	public boolean deleteInstInfo(int id);
	/**
	 * 添加交易金额配置信息
	 * @param tradeAmountConf
	 * @return
	 */
	public int addTradeAmountConf(TradeAmountConf tradeAmountConf);
	/**
	 * 查询交易金额配置list
	 * @return
	 */
	public List<TradeAmountConf> queryTradeAmountConf();
	public String queryTradeName(Map<String, Object> map);
}
