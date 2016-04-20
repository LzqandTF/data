package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.chinaebi.dao.MerchantFundSettleDao;
import com.chinaebi.entity.MerchantFundSettle;
import com.chinaebi.entity.SettleMerchantMatch;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "merchantFundSettleDao")
public class MerchantFundSettleDaoImpl extends MyBatisDao implements MerchantFundSettleDao {

	@Override
	public Page<MerchantFundSettle> queryPageMerchantFundSettle(Page<MerchantFundSettle> page, Map<String, Object> map) {
		return selectPage(page, "MerchantFundSettle.queryPageMerchantFundSettle", "MerchantFundSettle.queryCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantFundSettle> queryMerchantFundSettleList(Map<String, Object> map) {
		return getSqlSession().selectList("MerchantFundSettle.queryMerchantFundSettleList", map);
	}

	@Override
	public int addMerchantFundSettle(MerchantFundSettle merchantFundSettle) {
		return getSqlSession().insert("MerchantFundSettle.addMerchantFundSettle",merchantFundSettle);
	}

	@Override
	public String queryMaxSysBatchNo(int end_date) {
		return (String) getSqlSession().selectOne("MerchantFundSettle.queryMaxSysBatchNo", end_date);
	}

	@Override
	public int queryNoConfirmSettleDataCount(Map<String, Integer> map) {
		return Integer.valueOf(getSqlSession().selectOne("MerchantFundSettle.queryNoConfirmSettleDataCount",map).toString());
	}

	@Override
	public Long getSysBatchNo() {
		return Long.valueOf(getSqlSession().selectOne("MerchantFundSettle.getSysBatchNo").toString());
	}

	@Override
	public int queryMerchantFundSettleCount(String mer_code) {
		return Integer.valueOf(getSqlSession().selectOne("MerchantFundSettle.queryMerchantFundSettleCount", mer_code).toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SettleMerchantMatch> queryDyMerCodeBySettleMerCode(String settle_mer_code) {
		return getSqlSession().selectList("SettleMerchantMatch.queryDyMerCodeBySettleMerCode", settle_mer_code);
	}

	@Override
	public Page<MerchantFundSettle> queryPageDfResult(Page<MerchantFundSettle> page, Map<String, Object> map) {
		return selectPage(page, "MerchantFundSettle.queryPageDfResult", "MerchantFundSettle.queryDfResultCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantFundSettle> queryDfResultDataLst(Map<String, Object> map) {
		return getSqlSession().selectList("MerchantFundSettle.downExcelDfResult", map);
	}

	@Override
	public Page<MerchantFundSettle> queryPageNeedZBDataLst(Page<MerchantFundSettle> page, Map<String, Object> map) {
		return selectPage(page, "MerchantFundSettle.queryPageNeedZBDataLst", "MerchantFundSettle.queryNeedZBDataCount", map);
	}

	@Override
	public Page<MerchantFundSettle> queryPageNeedQRDataLst(Page<MerchantFundSettle> page, Map<String, Object> map) {
		return selectPage(page, "MerchantFundSettle.queryPageNeedQRDataLst", "MerchantFundSettle.queryNeedQRDataCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantFundSettle> queryDataListByIds(Map<String, Object> map) {
		return getSqlSession().selectList("MerchantFundSettle.queryDataListByIds", map);
	}

	@Override
	public Page<MerchantFundSettle> queryPageDyDfResult(Page<MerchantFundSettle> page, Map<String, Object> map) {
		return selectPage(page, "MerchantFundSettle.queryPageDyDfResult", "MerchantFundSettle.queryDyDfResultCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantFundSettle> queryMerSettleDataOfNoQR(Map<String,Object> map) {
		return getSqlSession().selectList("MerchantFundSettle.queryMerSettleDataOfNoQR", map);
	}

	@Override
	public int updateMerchantFundSettleSynResultAndDate(Map<String, Object> map) {
		return getSqlSession().update("MerchantFundSettle.updateMerchantFundSettleSynResultAndDate", map);
	}

	@Override
	public int updateMerchantFundSettleDataQrStatus(int id) {
		return getSqlSession().update("MerchantFundSettle.updateMerchantFundSettleDataQrStatus", id);
	}
	
	@Override
	public int updateDataStatusToZB(Map<String, Object> map) {
		return getSqlSession().update("MerchantFundSettle.updateDataStatusToZB", map);
	}

	@Override
	public int queryMerSettleDataEndDate(String mer_code) {
		if(getSqlSession().selectOne("MerchantFundSettle.queryMerSettleDataEndDate", mer_code) == null || StringUtils.isBlank(getSqlSession().selectOne("MerchantFundSettle.queryMerSettleDataEndDate", mer_code).toString())){
			return 0;
		}
		return Integer.valueOf(getSqlSession().selectOne("MerchantFundSettle.queryMerSettleDataEndDate", mer_code).toString());
	}
}
