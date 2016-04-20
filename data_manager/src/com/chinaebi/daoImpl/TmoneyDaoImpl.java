package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.TmoneyDao;
import com.chinaebi.entity.Tmoney;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "tmoneyDao")
public class TmoneyDaoImpl extends MyBatisDao implements TmoneyDao {
	@Override
	public Page<Tmoney> queryPageBankAccountData(Page<Tmoney> page,
			Map<String, Object> map) {
		return selectPage(page, "Tmoney.queryPageBankAccountData", "Tmoney.queryBankAccountCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tmoney> queryBankAccountDataList(Map<String, Object> map) {
		return getSqlSession().selectList("Tmoney.queryBankAccountDataList", map);
	}

	@Override
	public Page<Tmoney> queryPageDyAccountData(Page<Tmoney> page,
			Map<String, Object> map) {
		return selectPage(page, "Tmoney.queryPageDyAccountData", "Tmoney.queryDyAccountCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tmoney> queryDyAccountDataList(Map<String, Object> map) {
		return getSqlSession().selectList("Tmoney.queryDyAccountDataList", map);
	}
}
