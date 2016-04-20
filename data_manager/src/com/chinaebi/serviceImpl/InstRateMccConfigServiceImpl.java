package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.InstRateMccConfigDao;
import com.chinaebi.entity.InstRateMccConfig;
import com.chinaebi.service.InstRateMccConfigService;

@Service(value="instRateMccConfigService")
public class InstRateMccConfigServiceImpl implements InstRateMccConfigService{
	
	private static final Logger log = LoggerFactory.getLogger(InstRateMccConfigServiceImpl.class);

	@Autowired
	@Qualifier(value="instRateMccConfigDao")
	private InstRateMccConfigDao instRateMccConfigDao;
	
	@Override
	public int addInstRateMccConfig(InstRateMccConfig instRateMccConfig) {
		int result = 0;
		try{
			result = instRateMccConfigDao.addInstRateMccConfig(instRateMccConfig);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteInstRateMccConfig(InstRateMccConfig instRateMccConfig) {
		int result = 0;
		try{
			result = instRateMccConfigDao.deleteInstRateMccConfig(instRateMccConfig);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result ;
	}

	@Override
	public List<InstRateMccConfig> queryMccLiWaiConfig(Map<String, Integer> map) {
		List<InstRateMccConfig> list = null;
		try{
			list = instRateMccConfigDao.queryMccLiWaiConfig(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int checkInstRateMccConfigExistOrNot(
			InstRateMccConfig instRateMccConfig) {
		int result = 0;
		try{
			result = instRateMccConfigDao.checkInstRateMccConfigExistOrNot(instRateMccConfig);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

}
