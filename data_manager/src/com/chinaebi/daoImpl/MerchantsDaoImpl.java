package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MerchantsDao;
import com.chinaebi.entity.Merchants;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "merchantsDao")
public class MerchantsDaoImpl extends MyBatisDao implements MerchantsDao {
	@Override
	public Page<Merchants> queryPageMerchantsList(Page<Merchants> page,Map<String, Object> map) {
		return selectPage(page, "Merchants.queryPageMerchantsList", "Merchants.queryCount", map);
	}
	
	public String queryMerAbbreviationByMerCode(String mer_code){
		return String.valueOf(getSqlSession().selectOne("Merchants.queryMerAbbreviationByMerCode", mer_code));
	}

	@Override
	public Page<Merchants> queryPageSettleMerchantInfo(Page<Merchants> page,
			Map<String, Object> map) {
		return selectPage(page, "Merchants.queryPageSettleMerchantInfo", "Merchants.queryPageSettleMerchantInfoCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchants> querySettleMerchantInfoCount(Map<String, Object> map) {
		return getSqlSession().selectList("Merchants.querySettleMerchantInfoCount",map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchants> querySettleMerchantInfoList(Map<String, Object> map) {
		return getSqlSession().selectList("Merchants.querySettleMerchantInfoList", map);
	}

	@Override
	public Merchants queryMerchantBasicInfoByMerCode(String mer_code) {
		return (Merchants) getSqlSession().selectOne("Merchants.queryMerchantBasicInfoByMerCode",mer_code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchants> vagueQueryMerchantInfoByMerCode(String mer_code) {
		return getSqlSession().selectList("Merchants.vagueQueryMerchantInfoByMerCode", mer_code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchants> queryMerchantsBilBankAccountInfo() {
		return getSqlSession().selectList("Merchants.queryMerchantsBilBankAccountInfo");
	}

	@Override
	public int updateMerBillingBankAccount(Map<String, Object> map) {
		return getSqlSession().update("Merchants.updateMerBillingBankAccount", map);
	}

	@Override
	public Merchants queryAllMerInfoByMerCode(String merCode) {
		return (Merchants) getSqlSession().selectOne("Merchants.queryAllMerInfoByMerCode", merCode);
	}
	
	@Override
	public int updateMerBasicByMerCode(Map<String, Object> map) {
		return getSqlSession().update("Merchants.updateMerBasicByMerCode", map);
	}
	
	@Override
	public int updateMerBillingByMerCode(Map<String, Object> map) {
		return getSqlSession().update("Merchants.updateMerBillingByMerCode", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchants> queryAllMerBasicInfo() {
		return getSqlSession().selectList("Merchants.queryAllMerBasicInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchants> queryAllMerBillingInfo() {
		return getSqlSession().selectList("Merchants.queryAllMerBillingInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchants> queryMerNameListByMerName(Map<String, Object> map) {
		return getSqlSession().selectList("Merchants.queryMerNameListByMerName", map);
	}
	
}
