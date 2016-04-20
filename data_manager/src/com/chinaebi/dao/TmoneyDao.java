package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.Tmoney;
import com.chinaebi.utils.mybaits.Page;

public interface TmoneyDao {
	/**
	 * 分页查询银行账户数据
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<Tmoney> queryPageBankAccountData(Page<Tmoney> page, Map<String, Object> map);
	
	/**
	 * 获取银行账户数据列表
	 * @param map
	 * @return
	 */
	public List<Tmoney> queryBankAccountDataList(Map<String, Object> map);
	
	/**
	 * 分页查询电银账户数据
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<Tmoney> queryPageDyAccountData(Page<Tmoney> page, Map<String, Object> map);
	
	/**
	 * 获取电银账户数据列表
	 * @param map
	 * @return
	 */
	public List<Tmoney> queryDyAccountDataList(Map<String, Object> map);
}
