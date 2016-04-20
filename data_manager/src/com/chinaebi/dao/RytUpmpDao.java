package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.RytRefundLog;
import com.chinaebi.entity.RytUpmp;
import com.chinaebi.utils.mybaits.Page;

public interface RytUpmpDao {
	public Page<RytUpmp> queryPageRtyUpmp(Page<RytUpmp> page, Map<String, Object> map);
	public Page<RytRefundLog> queryPageRytRefundLog(Page<RytRefundLog> page, Map<String, Object> map);
	
	public RytUpmp queryDetail(Map<String, Object> map);
	public RytRefundLog queryDetailLog(Map<String, Object> map);
	
	public Page<RytUpmp> queryPageRtyUpmpForDz(Page<RytUpmp> page, Map<String, Object> map);
	
	public String queryTotalMoneyOfDz(Map<String,Object> map);
	
	
	/**
	 * 对账明细查询，如果银行机构为线上网关，则查询对应的线上数据下载到Excel表格
	 * @param map
	 * @return
	 */
	public List<RytUpmp> queryRytDzDetailDataList(Map<String, Object> map);
	
	public int updateDataByTesq(Map<String, Object> map);
	
	/**
	 * 线下成功交易未对账进入差错后修改对账状态为无需对账
	 * @param map
	 * @return
	 */
	public int updateBkchkByTradeId(Map<String, Object> map);
}
