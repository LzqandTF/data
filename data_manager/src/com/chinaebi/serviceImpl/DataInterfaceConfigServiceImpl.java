package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.DataInterfaceConfigDao;
import com.chinaebi.entity.DataInterfaceConfig;
import com.chinaebi.service.DataInterfaceConfigService;
import com.chinaebi.utils.mybaits.Page;



@Service(value="dataInterfaceConfigService")
public class DataInterfaceConfigServiceImpl implements DataInterfaceConfigService{

	private static final Logger log = LoggerFactory.getLogger(DataInterfaceConfigServiceImpl.class);
	
	@Autowired
	@Qualifier(value="dataInterfaceConfigDao")
	private DataInterfaceConfigDao dataInterfaceConfigDao;
	
	@Override
	public Page<DataInterfaceConfig> queryPageDataInterfaceConfig(
			Page<DataInterfaceConfig> page, Map<String, Object> map) {
		Page<DataInterfaceConfig> page_ = null;
		try{
			page_ = dataInterfaceConfigDao.queryPageDataInterfaceConfig(page, map);
		}catch(Exception e){
			log.error("接口数据配置分页查询操作抛出异常："+e.getMessage());
		}
		return page_;
	}

	@Override
	public int insertDataInterfaceConfig(DataInterfaceConfig dataInterfaceConfig) {
		int effectNum = 0;
		try{
			effectNum = dataInterfaceConfigDao.insertDataInterfaceConfig(dataInterfaceConfig);
		}catch(Exception e){
			log.error("接口数据配置添加操作抛出异常："+e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteDataInterfaceConfig(int id) {
		int effectNum = 0;
		try{
			effectNum = dataInterfaceConfigDao.deleteDataInterfaceConfig(id);
		}catch(Exception e){
			log.error("接口数据配置删除操作抛出异常："+e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteDataInterfaceConfigByObjectId(int object_id) {
		int effectNum = 0;
		try{
			effectNum = dataInterfaceConfigDao.deleteDataInterfaceConfigByObjectId(object_id);
		}catch(Exception e){
			log.error("接口数据配置根据系统ID进行删除操作抛出异常："+e.getMessage());
		}
		return effectNum;
	}

	@Override
	public List<DataInterfaceConfig> queryDataInterfaceConfigByObjectId(int object_id) {
		List<DataInterfaceConfig> dataInterfaceConfig = null;
		try{
			dataInterfaceConfig = dataInterfaceConfigDao.queryDataInterfaceConfigByObjectId(object_id);
		}catch(Exception e){
			log.error("通过系统ID查询接口数据配置抛出异常："+e.getMessage());
		}
		return dataInterfaceConfig;
	}

	@Override
	public List<DataInterfaceConfig> queryDataInterfaceConfigList() {
		List<DataInterfaceConfig> dataInterfaceConfig = null;
		try{
			dataInterfaceConfig = dataInterfaceConfigDao.queryDataInterfaceConfigList();
		}catch(Exception e){
			log.error("查询接口数据配置抛出异常："+e.getMessage());
		}
		return dataInterfaceConfig;
	}
	
	@Override
	public boolean queryDataInterfaceConfigNumberByValueOrName(int object_id,String value){
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("object_id", object_id);
			map.put("value", value);
			int number = dataInterfaceConfigDao.queryDataInterfaceConfigNumberByValueOrName(map);
			if(number > 0){
				return false;
			}
		}catch(Exception e){
			log.error("通过值或者名称查询接口数据配置个数操作抛出异常："+e.getMessage());
			return false;
		}
		return true;
	}

}
