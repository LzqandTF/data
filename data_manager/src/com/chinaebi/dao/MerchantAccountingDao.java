package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MerFundStance;
import com.chinaebi.utils.mybaits.Page;

public interface MerchantAccountingDao {
	
	public Page<MerFundStance> queryPageMerchantAccounting(Page<MerFundStance> page,Map<String, Object> map)throws Exception;
	
	public List<MerFundStance> queryMerchantAccountingList(Map<String, Object> map)throws Exception;
	
}
