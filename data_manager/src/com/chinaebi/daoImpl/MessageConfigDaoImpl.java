package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MessageConfigDao;
import com.chinaebi.entity.MessageConfig;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "messageConfigDao")
public class MessageConfigDaoImpl extends MyBatisDao implements MessageConfigDao{
	/**
	 * 通过事件名称查找短信配置对象
	 * @param name
	 * @return
	 */
	public MessageConfig queryMessageConfigByName(Map<String,Object> map){
		return (MessageConfig)getSqlSession().selectOne("MessageConfig.queryMessageConfigByName",map);
	}
	/**
	 * 添加短信配置对象
	 * @param messageConfig
	 * @return
	 */
	public int addMessageConfig(MessageConfig messageConfig){
		return getSqlSession().insert("MessageConfig.addMessageConfig",messageConfig);
	}
	/**
	 * 修改短信配置对象
	 * @param messageConfig
	 * @return
	 */
	public int updateMessageConfig(MessageConfig messageConfig){
		return getSqlSession().update("MessageConfig.updateMessageConfig",messageConfig);
	}
}
