package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MerchantSettleFailDao;
import com.chinaebi.entity.MerchantSettleFail;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value="merchantSettleFailDao")
public class MerchantSettleFailDaoImpl extends MyBatisDao implements MerchantSettleFailDao{

	@Override
	public Page<MerchantSettleFail> queryPageMerchantSettleFail(
			Page<MerchantSettleFail> page, Map<String, Object> map) {
		return selectPage(page, "MerchantSettleFail.queryPageMerchantSettleFail", "MerchantSettleFail.queryPageMerchantSettleFailCount",map);
	}
	
	@Override
	public List<MerchantSettleFail> queryMerchantSettleFailList(Map<String,Object> map){
		return getSqlSession().selectList("MerchantSettleFail.queryMerchantSettleFailList",map);
	}

	@Override
	public int addMerchantSettleFail(MerchantSettleFail merchantSettleFail) {
		return getSqlSession().insert("MerchantSettleFail.addMerchantSettleFail",merchantSettleFail);
	}

}
