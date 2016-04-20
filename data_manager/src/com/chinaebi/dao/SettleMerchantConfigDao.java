package com.chinaebi.dao;

import java.util.Map;

import com.chinaebi.entity.SettleMerchantConfig;
import com.chinaebi.utils.mybaits.Page;

public interface SettleMerchantConfigDao {
	/**
	 * 分页查询结算商户配置信息
	 * @param page
	 * @param map 查询参数
	 * @return
	 */
	public Page<SettleMerchantConfig> queryPageSettleMerchantConfig(Page<SettleMerchantConfig> page,Map<String,Object> map);
	
	/**
	 * 新增结算商户配置信息
	 * @param settleMerchantConfig
	 * @return
	 */
	public int insertSettleMerchantConfig(SettleMerchantConfig settleMerchantConfig);
	
	/**
	 * 通过结算商户号删除结算商户配置信息
	 * @param settle_mer_code 结算商户号
	 * @return
	 */
	public int deleteSettleMerchantConfig(String settle_mer_code);
	
	/**
	 * 通过结算商户号查询在结算商户配置表中是否已配置
	 * @param settle_mer_code 结算商户号
	 * @return
	 */
	public int queryConfigCountBySettleMerCode(String settle_mer_code);
}
