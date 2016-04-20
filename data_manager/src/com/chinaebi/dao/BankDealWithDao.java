package com.chinaebi.dao;

import java.util.List;
import java.util.Map;
import com.chinaebi.entity.BankDealWith;
import com.chinaebi.utils.mybaits.Page;

public interface BankDealWithDao {
	
	public List<BankDealWith> queryInstInfo()throws Exception;
	
	public List<BankDealWith> queryListCountInfo(Map<String, Object>map)throws Exception;
	
	public List<BankDealWith> queryListCountInfoExcel(Map<String, Object> map)throws Exception;
	
	public Page<BankDealWith> queryTradingList(Page<BankDealWith> page,Map<String, Object> map)throws Exception;
	
	public String queryMerName(String mercode)throws Exception;
	
	public String queryMernameOne(String mercode)throws Exception;
	
	public BankDealWith querySumTradingList(Map<String, Object>map)throws Exception;
	
}
