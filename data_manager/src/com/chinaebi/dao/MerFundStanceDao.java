package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MerFundStance;
import com.chinaebi.utils.mybaits.Page;

public interface MerFundStanceDao {
	/**
	 * 分页查询商户资金流水
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MerFundStance> queryPageMerFundStance(Page<MerFundStance> page,Map<String,Object> map);
	
	/**
	 * 新增商户资金流水
	 * @param merFundStance
	 * @return
	 */
	public int insertMerFundStance(MerFundStance merFundStance);
	
	/**
	 * 修改商户资金流水
	 * @param merFundStance
	 * @return
	 */
	public int updateMerFundStance(MerFundStance merFundStance);
	
	/**
	 * 通过商户号查询 前期账户余额
	 * @param mer_code
	 * @return
	 */
	public String queryLastAccountAmount(Map<String,Object> map);
	
	/**
	 * 查询商户资金流水信息集合
	 * @param map
	 * @return
	 */
	public List<MerFundStance> queryMerFundStanceList(Map<String,Object> map);
}
