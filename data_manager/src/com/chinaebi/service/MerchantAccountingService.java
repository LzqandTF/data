package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MerFundStance;
import com.chinaebi.utils.mybaits.Page;

public interface MerchantAccountingService {
	
	/**
	 * 根据交易时间查询商户号和上期账户余额
	 * @param page
	 * @param map
	 * @return1
	 */
	public Page<MerFundStance> queryPageMerchantAccounting(Page<MerFundStance> page,Map<String, Object> map);
	
	/**
	 * 根据条件查询商户账户信息
	 * @param map
	 * @return
	 */
	public List<MerFundStance> queryMerchantAccountingList( Map<String, Object> map);

}
