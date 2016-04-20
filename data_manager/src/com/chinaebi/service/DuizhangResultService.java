package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.OriginalData;

public interface DuizhangResultService {
	public abstract Map<String, Object> proceDuizhangResult(Map<String, Object> map);
	public abstract Map<String, Object> proceErrorDuizhangResult(Map<String, Object> map);
	
	/**
	 * 融易通UPMP线上交易对账笔数统计
	 * @param map
	 * @return
	 */
	Map<String, Object> proceRytUpmpDzResult(Map<String, Object> map);
	
	/**
	 * 融易通UPMP线上对账交易差错笔数统计
	 * @param map
	 * @return
	 */
	Map<String, Object> proceRytUpmpErrorResult(Map<String, Object> map);
	
	/**
	 * 查询渠道对账明细数据
	 * @param map
	 * @return
	 */
	public abstract List<OriginalData> queryChannelDzResultDataLst(Map<String,Object> map);
}

