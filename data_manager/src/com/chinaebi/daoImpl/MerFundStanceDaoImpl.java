package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MerFundStanceDao;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value="merFundStanceDao")
public class MerFundStanceDaoImpl extends MyBatisDao implements MerFundStanceDao{

	@Override
	public Page<MerFundStance> queryPageMerFundStance(Page<MerFundStance> page,
			Map<String, Object> map) {
		return selectPage(page, "MerFundStance.queryPageMerFundStance", "MerFundStance.queryPageCount", map);
	}

	@Override
	public int insertMerFundStance(MerFundStance merFundStance) {
		return getSqlSession().insert("MerFundStance.insertMerFundStance",merFundStance);
	}

	@Override
	public int updateMerFundStance(MerFundStance merFundStance) {
		return getSqlSession().update("MerFundStance.updateMerFundStance", merFundStance);
	}

	@Override
	public String queryLastAccountAmount(Map<String,Object> map) {
		return (String) getSqlSession().selectOne("MerFundStance.queryLastAccountAmount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MerFundStance> queryMerFundStanceList(Map<String, Object> map) {
		return getSqlSession().selectList("MerFundStance.queryMerFundStanceList", map);
	}

}
