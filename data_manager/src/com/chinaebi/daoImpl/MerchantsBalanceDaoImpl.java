package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MerchantsBalanceDao;
import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;


@Component(value = "merchantsBalanceDao")
public class MerchantsBalanceDaoImpl extends MyBatisDao implements MerchantsBalanceDao{

	@Override
	public Page<MerchantsBalance> queryPageMerchantsBalanceList(Page<MerchantsBalance> page, Map<String, Object> map)throws Exception {
		return selectPage(page, "MerchantsBalance.queryPageMerchantsBalanceList", "MerchantsBalance.queryCount", map);
	}

	@Override
	public MerchantsBalance queryMerBalanceByMerCode(String mer_code) {
		return (MerchantsBalance) getSqlSession().selectOne("MerchantsBalance.queryMerBalanceByMerCode", mer_code);
	}

	@Override
	public int addMerchantsBalance(MerchantsBalance merchantsBalance) {
		return getSqlSession().insert("MerchantsBalance.addMerchantsBalance", merchantsBalance);
	}

	@Override
	public int updateMerchantsBalance(MerchantsBalance merchantsBalance) {
		return getSqlSession().insert("MerchantsBalance.updateMerchantsBalance", merchantsBalance);
	}

}
