package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.SettleMerchantConfigDao;
import com.chinaebi.entity.SettleMerchantConfig;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value="settleMerchantConfigDao")
public class SettleMerchantConfigDaoImpl extends MyBatisDao implements SettleMerchantConfigDao{

	@Override
	public Page<SettleMerchantConfig> queryPageSettleMerchantConfig(
			Page<SettleMerchantConfig> page, Map<String, Object> map) {
		return selectPage(page, "SettleMerchantConfig.queryPageSettleMerchantConfig", "SettleMerchantConfig.queryPageCount",map);
	}

	@Override
	public int insertSettleMerchantConfig(SettleMerchantConfig settleMerchantConfig) {
		return getSqlSession().insert("SettleMerchantConfig.insertSettleMerchantConfig", settleMerchantConfig);
	}

	@Override
	public int deleteSettleMerchantConfig(String settle_mer_code) {
		return getSqlSession().delete("SettleMerchantConfig.deleteSettleMerchantConfig", settle_mer_code);
	}

	@Override
	public int queryConfigCountBySettleMerCode(String settle_mer_code) {
		return Integer.valueOf(getSqlSession().selectOne("SettleMerchantConfig.queryConfigCountBySettleMerCode", settle_mer_code).toString());
	}
	
}

