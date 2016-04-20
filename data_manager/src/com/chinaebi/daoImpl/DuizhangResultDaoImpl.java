package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.DuizhangResultDao;
import com.chinaebi.entity.OriginalData;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "duizhangResultDao")
public class DuizhangResultDaoImpl extends MyBatisDao implements DuizhangResultDao {

	@Override
	public Map<String, Object> proceDuizhangResult(Map<String, Object> map) {
		getSqlSession().selectOne("Original_Data.queryResult", map);
		return map;
	}

	@Override
	public Map<String, Object> proceErrorDuizhangResult(Map<String, Object> map) {
		getSqlSession().selectOne("Duizhang_Data.queryErrorResult", map);
		return map;
	}

	@Override
	public Map<String, Object> proceRytUpmpDzResult(Map<String, Object> map) {
		getSqlSession().selectOne("Original_Data.queryRytUpmpDzResult", map);
		return map;
	}

	@Override
	public Map<String, Object> proceRytUpmpErrorResult(Map<String, Object> map) {
		getSqlSession().selectOne("Duizhang_Data.queryUpmpErrorResult", map);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OriginalData> queryChannelDzResultDataLst(Map<String, Object> map) {
		return getSqlSession().selectList("Original_Data.queryChannelDzResultDataLst", map);
	}
}

