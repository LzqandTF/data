package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.InstMerRateConfig;

public interface InstMerRateConfigDao {
	/**
	 * 查询渠道商户费率配置信息
	 * @param map
	 * @return
	 */
	public List<InstMerRateConfig> queryInstMerRateConfigInstByInstIdAndInstType(Map<String,Integer> map);
	
	/**
	 * 新增渠道商户费率配置信息
	 * @param instMerRateConfig
	 * @return
	 */
	public int addInstMerRateConfig(InstMerRateConfig instMerRateConfig);
	
	/**
	 * 删除渠道商户费率配置信息
	 * @param map
	 * @return
	 */
	public int deleteInstMerRateConfig(InstMerRateConfig instMerRateConfig);
	
	/**
	 * 检验是否存在相同配置的渠道商户费率信息
	 * @param instMerRateConfig
	 * @return
	 */
	public boolean checkInstMerRateConfigExistOrNot(InstMerRateConfig instMerRateConfig);
	
	/**
	 * 通过渠道ID和渠道类型删除渠道商户费率信息
	 * @param map
	 * @return
	 */
	public int deleteInstMerRateConfigByInstIdAndInstType(Map<String,Object> map);
	
}
