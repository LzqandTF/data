package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MerchantSettleStatisticsDao;
import com.chinaebi.entity.MerchantSettleStatistics;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "merchantSettleStatisticsDao")
public class MerchantSettleStatisticsDaoImpl extends MyBatisDao implements MerchantSettleStatisticsDao {
	@Override
	public Page<MerchantSettleStatistics> queryPageMerchantSettleStatistics(Page<MerchantSettleStatistics> page, Map<String, Object> map) {
		return selectPage(page, "MerchantSettleStatistics.queryPageMerchantSettleStatistics", "MerchantSettleStatistics.queryCount", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<MerchantSettleStatistics> querySettleMerchantInfo(Map<String,Object> map){
		return getSqlSession().selectList("MerchantSettleStatistics.querySettleMerchantInfo", map);
	}
	
	public MerchantSettleStatistics querySettleMerchantInfoGroupByMerCode(Map<String,Object> map){
		return (MerchantSettleStatistics)getSqlSession().selectOne("MerchantSettleStatistics.querySettleMerchantInfoGroupByMerCode", map);
	}

	@Override
	public int updateMerchantSettleStatistics(Map<String, Object> map) {
		return getSqlSession().update("MerchantSettleStatistics.updateMerchantSettleStatistics", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantSettleStatistics> queryInstIdList(Map<String, Object> map) {
		return getSqlSession().selectList("MerchantSettleStatistics.queryInstIdList", map);
	}

	@Override
	public MerchantSettleStatistics queryMerchantSettleStatisticsInfo(Map<String, Object> map) {
		return (MerchantSettleStatistics)getSqlSession().selectOne("MerchantSettleStatistics.queryMerchantSettleStatisticsInfo", map);
	}

	@Override
	public int updateMerchantSettleStatisticsInfo(MerchantSettleStatistics merchantSettleStatistics) {
		return getSqlSession().update("MerchantSettleStatistics.updateMerchantSettleStatisticsInfo", merchantSettleStatistics);
	}

	@Override
	public int addMerchantSettleStatisticsInfo(MerchantSettleStatistics merchantSettleStatistics) {
		return getSqlSession().insert("MerchantSettleStatistics.addMerchantSettleStatisticsInfo", merchantSettleStatistics);
	}

	@Override
	public Page<MerchantSettleStatistics> queryPageMSSByMerCodeAndInstId(Page<MerchantSettleStatistics> page, Map<String, Object> map) {
		return selectPage(page, "MerchantSettleStatistics.queryPageMSSByMerCodeAndInstId", "MerchantSettleStatistics.queryCountByMerCodeAndInstId", map);
	}

	@Override
	public MerchantSettleStatistics queryTotalMoney(Map<String, Object> map) {
		return (MerchantSettleStatistics) getSqlSession().selectOne("MerchantSettleStatistics.queryTotalMoney", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantSettleStatistics> queryDataLstForExcel(Map<String, Object> map) {
		return getSqlSession().selectList("MerchantSettleStatistics.queryDataLstForExcel", map);
	}
}
