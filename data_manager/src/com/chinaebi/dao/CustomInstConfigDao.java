package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.CustomInstConfig;

public interface CustomInstConfigDao {
	/**
	 * 新增系统接口指定渠道配置信息
	 * @param customInstConfig
	 * @return
	 */
	public int insertCustomInstConfig(CustomInstConfig customInstConfig);
	
	/**
	 * 删除系统接口指定渠道配置信息
	 * @param object_id
	 * @return
	 */
	public int deleteCustomInstConfig(int object_id);
	
	/**
	 * 修改系统接口指定渠道配置信息
	 * @param customInstConfig
	 * @return
	 */
	public int updateCustomInstConfig(CustomInstConfig customInstConfig);
	
	/**
	 * 查询系统接口指定渠道配置信息
	 * @param object_id
	 * @return
	 */
	public List<CustomInstConfig> queryCustomInstConfigByObjectId(int object_id);
	
	/**
	 * 查询系统接口指定渠道配置信息
	 * @param map
	 * @return
	 */
	public List<CustomInstConfig> queryCustomInstConfig(Map<String,Object> map);
}
