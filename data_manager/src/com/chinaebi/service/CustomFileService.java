package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.CustomFileEntity;

public interface CustomFileService {
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
	
	/**
	 * 对文件内容进行规则化处理
	 * @param o 系统配置内容数组
	 * @param content 将要应用规则的字符串
	 * @return
	 */
	public String ruleHandleContent(CustomFileEntity customFileEntity, String content) ;
}
