package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.InstRateDao;
import com.chinaebi.entity.InstRate;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value="instRateDao")
public class InstRateDaoImpl extends MyBatisDao implements InstRateDao{

	@Override
	public Page<InstRate> queryPageInstRate(Page<InstRate> page,
			Map<String, Object> map) {
		return selectPage(page,"InstRate.queryPageInstRate","InstRate.queryPageCount",map);
	}

	@Override
	public int addInstRate(InstRate instRate) {
		return getSqlSession().insert("InstRate.addInstRate", instRate);
	}

	@Override
	public int deleteInstRate(Map<String, Object> map) {
		return getSqlSession().delete("InstRate.deleteInstRate", map);
	}

	@Override
	public int updateInstRate(InstRate instRate) {
		return getSqlSession().update("InstRate.updateInstRate", instRate);
	}

	@Override
	public List<InstRate> queryInstRateInstId() {
		return getSqlSession().selectList("InstRate.queryInstRateInstId");
	}

}
