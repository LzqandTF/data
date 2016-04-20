package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.CustomInstConfigDao;
import com.chinaebi.entity.CustomInstConfig;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value="customInstConfigDao")
public class CustomInstConfigDaoImpl extends MyBatisDao implements CustomInstConfigDao{

	@Override
	public int insertCustomInstConfig(CustomInstConfig customInstConfig) {
		return getSqlSession().insert("CustomInstConfig.insertCustomInstConfig", customInstConfig);
	}

	@Override
	public int deleteCustomInstConfig(int object_id) {
		return getSqlSession().delete("CustomInstConfig.deleteCustomInstConfig", object_id);
	}

	@Override
	public int updateCustomInstConfig(CustomInstConfig customInstConfig) {
		return getSqlSession().update("CustomInstConfig.updateCustomInstConfig", customInstConfig);
	}

	@Override
	public List<CustomInstConfig> queryCustomInstConfigByObjectId(int object_id) {
		return getSqlSession().selectList("CustomInstConfig.queryCustomInstConfigByObjectId", object_id);
	}

	@Override
	public List<CustomInstConfig> queryCustomInstConfig(Map<String, Object> map) {
		return getSqlSession().selectList("CustomInstConfig.queryCustomInstConfig", map);
	}
	
}
