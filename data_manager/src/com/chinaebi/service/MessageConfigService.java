package com.chinaebi.service;

import java.util.Map;

import com.chinaebi.entity.MessageConfig;

public interface MessageConfigService {
	/**
	 * 通过事件名称查找短信配置对象
	 * @param name
	 * @return
	 */
	public MessageConfig queryMessageConfigByName(Map<String,Object> map);
	/**
	 * 添加短信配置对象
	 * @param messageConfig
	 * @return
	 */
	public int addMessageConfig(MessageConfig messageConfig);
	/**
	 * 修改短信配置对象
	 * @param messageConfig
	 * @return
	 */
	public int updateMessageConfig(MessageConfig messageConfig);
}
