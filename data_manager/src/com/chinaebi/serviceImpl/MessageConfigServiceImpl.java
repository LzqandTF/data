package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.UpdateException;

import com.chinaebi.dao.MessageConfigDao;
import com.chinaebi.entity.MessageConfig;
import com.chinaebi.service.MessageConfigService;


@Service(value = "messageConfigService")
public class MessageConfigServiceImpl implements MessageConfigService{
	protected Logger log = LoggerFactory.getLogger(MessageConfigServiceImpl.class);
	@Autowired
	@Qualifier(value="messageConfigDao")
	private MessageConfigDao messageConfigDao;
	/**
	 * 通过事件名称查找短信配置对象
	 * @param name
	 * @return
	 */
	public MessageConfig queryMessageConfigByName(Map<String,Object> map){
		MessageConfig messageConfig = null;
		try{
			messageConfig = messageConfigDao.queryMessageConfigByName(map);
			if(messageConfig == null){
				log.info("未找到对应时间的短信配置对象");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return messageConfig;
	}
	/**
	 * 添加短信配置对象
	 * @param messageConfig
	 * @return
	 */
	public int addMessageConfig(MessageConfig messageConfig){
		int effectNum = 0;
		try{
			effectNum = messageConfigDao.addMessageConfig(messageConfig);
			if(effectNum == 0){
				throw new InsertException("messageConfigDao.addMessageConfig(messageConfig)  添加失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
	/**
	 * 修改短信配置对象
	 * @param messageConfig
	 * @return
	 */
	public int updateMessageConfig(MessageConfig messageConfig){
		int effectNum = 0;
		try{
			effectNum = messageConfigDao.updateMessageConfig(messageConfig);
			if(effectNum == 0){
				throw new UpdateException("messageConfigDao.updateMessageConfig(messageConfig)  更新失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
}
