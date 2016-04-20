package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.CustomInstConfigDao;
import com.chinaebi.entity.CustomInstConfig;
import com.chinaebi.service.CustomInstConfigService;

@Service(value="customInstConfigService")
public class CustomInstConfigServiceImpl implements CustomInstConfigService{

	private static final Logger log = LoggerFactory.getLogger(CustomInstConfigServiceImpl.class); 
	
	@Autowired
	@Qualifier(value="customInstConfigDao")
	private CustomInstConfigDao customInstConfigDao;
	
	@Override
	public int insertCustomInstConfig(CustomInstConfig customInstConfig) throws Exception  {
		int result = 0;
		try{
			result = customInstConfigDao.insertCustomInstConfig(customInstConfig);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return result;
	}

	@Override
	public int deleteCustomInstConfig(int object_id) throws Exception {
		int result = 0;
		try{
			result = customInstConfigDao.deleteCustomInstConfig(object_id);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return result;
	}

	@Override
	public int updateCustomInstConfig(CustomInstConfig customInstConfig) throws Exception {
		int result = 0;
		try{
			result = customInstConfigDao.updateCustomInstConfig(customInstConfig);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return result;
	}

	@Override
	public List<CustomInstConfig> queryCustomInstConfigByObjectId(int object_id) throws Exception {
		List<CustomInstConfig> result = null;
		try{
			result = customInstConfigDao.queryCustomInstConfigByObjectId(object_id);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return result;
	}

	@Override
	public List<CustomInstConfig> queryCustomInstConfig(Map<String, Object> map)
			throws Exception {
		List<CustomInstConfig> result = null;
		try{
			result = customInstConfigDao.queryCustomInstConfig(map);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return result;
	}

}
