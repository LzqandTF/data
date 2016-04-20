package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.CustomFileEntity;

public interface CustomFileDao {
	/**
	 * 查询自定义文件配置信息
	 * @param object_id
	 * @return
	 */
	public List<CustomFileEntity> queryCustomFileEntityConfigInfo(Map<String,Object> map);
	
	/**
	 * 查询自定义文件新旧值替换配置信息
	 * @param map
	 * @return
	 */
	public List<CustomFileEntity> queryReplaceValue(Map<String,Object> map);
}
