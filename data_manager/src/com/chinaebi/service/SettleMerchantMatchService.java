package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.SettleMerchantMatch;

public interface SettleMerchantMatchService {
	/**
	 * 查询结算商户与内部商户号配置是否存在配置关系
	 * @param map
	 * @return
	 */
	public int querySettleMerchantMatchCount(Map<String,Object> map);
	
	/**
	 * 新增结算商户与内部商户号配置关系
	 * @param settleMerchantMatch
	 * @return
	 */
	public int insertSettleMerchantMatch(SettleMerchantMatch settleMerchantMatch);
	
	/**
	 * 查询结算商户号与电银商户号配置信息
	 * @param map
	 * @return
	 */
	public List<SettleMerchantMatch> querySettleMerchantMatch(Map<String,Object> map);
	
	/**
	 * 通过结算商户号删除结算商户号与电银商户号的配置信息
	 * @param settle_mer_code
	 * @return
	 */
	public int deleteSettleMerchantMatchBySettleMerCode(String settle_mer_code);
	
	/**
	 * 根据结算商户号与电银商户号删除匹配数据
	 * @param map
	 * @return
	 */
	public int deleteSettleMerchantMatchBySettleMerCodeAndDyCode(String settle_mer_code,String dy_mer_code);
	
	/**
	 * 根绝电银商户号查询对应配置的结算商户号
	 * @param dy_mer_code
	 * @return
	 */
	public String querySettleMerCodeByDyMerCode(String dy_mer_code);
	/**
	 * 修改结算商户配置表中的电银商户号
	 * @param map
	 * @return
	 */
	public int updateDyMerCode(Map<String,Object> map);
}
