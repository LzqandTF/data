package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.InstRateMccConfigDao;
import com.chinaebi.entity.InstRateMccConfig;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value="instRateMccConfigDao")
public class InstRateMccConfigDaoImpl extends MyBatisDao implements InstRateMccConfigDao{

	@Override
	public int addInstRateMccConfig(InstRateMccConfig instRateMccConfig) {
		return getSqlSession().insert("InstRateMccConfig.addInstRateMccConfig", instRateMccConfig);
	}

	@Override
	public int deleteInstRateMccConfig(InstRateMccConfig instRateMccConfig) {
		return getSqlSession().delete("InstRateMccConfig.deleteInstRateMccConfig", instRateMccConfig);
	}

	@Override
	public List<InstRateMccConfig> queryMccLiWaiConfig(Map<String, Integer> map) {
		return getSqlSession().selectList("InstRateMccConfig.queryMccLiWaiConfig", map);
	}

	@Override
	public int checkInstRateMccConfigExistOrNot(InstRateMccConfig instRateMccConfig) {
		return Integer.valueOf(getSqlSession().selectOne("InstRateMccConfig.checkInstRateMccConfigExistOrNot", instRateMccConfig).toString());
	}
	
}
