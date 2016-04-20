package com.chinaebi.dao;

import java.util.Map;

import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.utils.mybaits.Page;

public interface MerchantsBalanceDao {
	
	public Page<MerchantsBalance> queryPageMerchantsBalanceList(Page<MerchantsBalance> page, Map<String, Object> map)throws Exception;
	/**
	 * 通过商户号查询商户余额
	 * @param mer_code 商户号
	 * @return 
	 */
	public MerchantsBalance queryMerBalanceByMerCode(String mer_code);
	
	/**
	 * 新增商户余额
	 * @param merchantsBalance
	 * @return
	 */
	public int addMerchantsBalance(MerchantsBalance merchantsBalance);
	
	/**
	 * 修改商户余额
	 * @param merchantsBalance
	 * @return
	 */
	public int updateMerchantsBalance(MerchantsBalance merchantsBalance);
}
