package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.InstMerRateConfigDao;
import com.chinaebi.entity.InstMerRateConfig;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value="instMerRateConfigDao")
public class InstMerRateConfigDaoImpl extends MyBatisDao implements InstMerRateConfigDao{

	@Override
	public List<InstMerRateConfig> queryInstMerRateConfigInstByInstIdAndInstType(
			Map<String, Integer> map) {
		return getSqlSession().selectList("InstMerRateConfig.queryInstMerRateConfigInstByInstIdAndInstType", map);
	}

	@Override
	public int addInstMerRateConfig(InstMerRateConfig instMerRateConfig) {
		return getSqlSession().insert("InstMerRateConfig.addInstMerRateConfig", instMerRateConfig);
	}

	@Override
	public int deleteInstMerRateConfig(InstMerRateConfig instMerRateConfig) {
		return getSqlSession().delete("InstMerRateConfig.deleteInstMerRateConfig", instMerRateConfig);
	}

	@Override
	public boolean checkInstMerRateConfigExistOrNot(
			InstMerRateConfig instMerRateConfig) {
		return Integer.valueOf(getSqlSession().selectOne("InstMerRateConfig.checkInstMerRateConfigExistOrNot", instMerRateConfig).toString()) > 0?true:false;
	}

	@Override
	public int deleteInstMerRateConfigByInstIdAndInstType(
			Map<String, Object> map) {
		return getSqlSession().delete("InstMerRateConfig.deleteInstMerRateConfigByInstIdAndInstType",map);
	}

}
