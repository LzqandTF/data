package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.CustomInstConfig;

public interface CustomInstConfigService {
	/**
	 * 新增系统接口指定渠道配置信息
	 * @param customInstConfig
	 * @return
	 */
	public int insertCustomInstConfig(CustomInstConfig customInstConfig) throws Exception ;
	
	/**
	 * 删除系统接口指定渠道配置信息
	 * @param object_id
	 * @return
	 */
	public int deleteCustomInstConfig(int object_id) throws Exception ;
	
	/**
	 * 修改系统接口指定渠道配置信息
	 * @param customInstConfig
	 * @return
	 */
	public int updateCustomInstConfig(CustomInstConfig customInstConfig) throws Exception ;
	
	/**
	 * 查询系统接口指定渠道配置信息
	 * @param object_id
	 * @return
	 */
	public List<CustomInstConfig> queryCustomInstConfigByObjectId(int object_id) throws Exception ;
	
	/**
	 * 查询系统接口指定渠道配置信息
	 * @param map
	 * @return
	 */
	public List<CustomInstConfig> queryCustomInstConfig(Map<String,Object> map) throws Exception ;
}
