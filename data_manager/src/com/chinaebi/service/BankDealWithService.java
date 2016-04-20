package com.chinaebi.service;

import java.util.List;
import java.util.Map;
import com.chinaebi.entity.BankDealWith;
import com.chinaebi.utils.mybaits.Page;

public interface BankDealWithService {
	
	/**
	 * 查询线上、线下数据
	 * @return
	 * @throws Exception
	 */
	public List<BankDealWith> queryInstInfo();
	
	/**
	 * 查询线上、线下数据总和
	 * @return
	 */
	public List<BankDealWith> queryListCountInfo(Map<String, Object> map);
	
	/**
	 * 下载应收银行交易款excel表数据
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BankDealWith> queryListCountInfoExcel(Map<String, Object> map);
	
	/**
	 * 统计所有交易数据的和
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BankDealWith> queryListCountInfoSum(Map<String, Object> map);
	
	/**
	 * 根据交易时间查询交易数据
	 * @param page
	 * @param sqlList
	 * @return
	 */
	public Page<BankDealWith> queryTradingList(Page<BankDealWith> page, Map<String, Object> map);
	
	/**
	 * 查询交易时间范围内所有金额总和
	 * @param map
	 * @return
	 */
	public BankDealWith querySumTradingList(Map<String, Object> map);
	
	/**
	 * 根据商户号查询商户简称
	 * @param mercode
	 * @return
	 */
	public String queryMerName(String mercode);
	
	/**
	 * 根据商户号查询商户简称
	 * @param mercode
	 * @return
	 */
	public String queryMernameOne(String mercode);

}
