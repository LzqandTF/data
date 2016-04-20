package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.SettleMerchantMatchDao;
import com.chinaebi.entity.SettleMerchantMatch;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value="settleMerchantMatchDao")
public class SettleMerchantMatchDaoImpl extends MyBatisDao implements SettleMerchantMatchDao{

	@Override
	public int querySettleMerchantMatchCount(Map<String, Object> map) {
		return Integer.valueOf(getSqlSession().selectOne("SettleMerchantMatch.querySettleMerchantMatchCount",map).toString());
	}

	@Override
	public int insertSettleMerchantMatch(SettleMerchantMatch settleMerchantMatch) {
		return getSqlSession().insert("SettleMerchantMatch.insertSettleMerchantMatch",settleMerchantMatch);
	}

	@Override
	public List<SettleMerchantMatch> querySettleMerchantMatch(Map<String, Object> map) {
		return getSqlSession().selectList("SettleMerchantMatch.querySettleMerchantMatch", map);
	}

	@Override
	public int deleteSettleMerchantMatchBySettleMerCode(String settle_mer_code) {
		return getSqlSession().delete("SettleMerchantMatch.deleteSettleMerchantMatchBySettleMerCode", settle_mer_code);
	}

	@Override
	public int deleteSettleMerchantMatchBySettleMerCodeAndDyCode(Map<String, Object> map) {
		return getSqlSession().delete("SettleMerchantMatch.deleteSettleMerchantMatchBySettleMerCodeAndDyCode", map);
	}

	@Override
	public String querySettleMerCodeByDyMerCode(String dy_mer_code) {
		return getSqlSession().selectOne("SettleMerchantMatch.querySettleMerCodeByDyMerCode", dy_mer_code)==null?"":getSqlSession().selectOne("SettleMerchantMatch.querySettleMerCodeByDyMerCode", dy_mer_code).toString();
	}

	@Override
	public int updateDyMerCode(Map<String, Object> map) {
		return getSqlSession().update("SettleMerchantMatch.updateDyMerCode", map);
	}	

}
