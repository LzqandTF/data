package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.InstMerRateConfigDao;
import com.chinaebi.entity.InstMerRateConfig;
import com.chinaebi.service.InstMerRateConfigService;

@Service(value="instMerRateConfigService")
public class InstMerRateConfigServiceImpl implements InstMerRateConfigService{

	private static final Logger log = LoggerFactory.getLogger(InstMerRateConfigServiceImpl.class);
	
	@Autowired
	@Qualifier(value="instMerRateConfigDao")
	private InstMerRateConfigDao instMerRateConfigDao;
	
	@Override
	public List<InstMerRateConfig> queryInstMerRateConfigInstByInstIdAndInstType(
			Map<String, Integer> map) {
		List<InstMerRateConfig> list = null;
		try{
			list = instMerRateConfigDao.queryInstMerRateConfigInstByInstIdAndInstType(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int addInstMerRateConfig(InstMerRateConfig instMerRateConfig) {
		int result = 0;
		try{
			result = instMerRateConfigDao.addInstMerRateConfig(instMerRateConfig);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteInstMerRateConfig(InstMerRateConfig instMerRateConfig) {
		int result = 0;
		try{
			result = instMerRateConfigDao.deleteInstMerRateConfig(instMerRateConfig);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public boolean checkInstMerRateConfigExistOrNot(
			InstMerRateConfig instMerRateConfig) {
		boolean result = false;
		try{
			result = instMerRateConfigDao.checkInstMerRateConfigExistOrNot(instMerRateConfig);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteInstMerRateConfigByInstIdAndInstType(
			Map<String, Object> map) {
		int result = 0;
		try{
			result = instMerRateConfigDao.deleteInstMerRateConfigByInstIdAndInstType(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

}
