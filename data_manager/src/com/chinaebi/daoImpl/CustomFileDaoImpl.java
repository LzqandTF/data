package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.CustomFileDao;
import com.chinaebi.entity.CustomFileEntity;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "customFileDao")
public class CustomFileDaoImpl extends MyBatisDao implements CustomFileDao{

	@Override
	public List<CustomFileEntity> queryCustomFileEntityConfigInfo(Map<String,Object> map) {
		return getSqlSession().selectList("CustomFileEntity.queryCustomFileEntityConfigInfo", map);
	}

	@Override
	public List<CustomFileEntity> queryReplaceValue(Map<String, Object> map) {
		return getSqlSession().selectList("CustomFileEntity.queryReplaceValue", map);
	}

}
