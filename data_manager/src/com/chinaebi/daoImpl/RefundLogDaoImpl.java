package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.RefundLogDao;
import com.chinaebi.entity.RytRefundLog;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "refundLogDao")
public class RefundLogDaoImpl extends MyBatisDao implements RefundLogDao {

	@Override
	public Page<RytRefundLog> queryPageOnlineTkDataLst(Page<RytRefundLog> page,Map<String, Object> map) {
		return selectPage(page, "RytRefundLog.queryPageOnlineTkDataLst", "RytRefundLog.queryOnlineTkDataCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RytRefundLog> queryOnlineTkDataLstForExcel(Map<String, Object> map) {
		return getSqlSession().selectList("RytRefundLog.queryOnlineTkDataLstForExcel", map);
	}

	@Override
	public Page<RytRefundLog> queryPageTkCheckDataLst(Page<RytRefundLog> page,Map<String, Object> map) {
		return selectPage(page, "RytRefundLog.queryPageTkCheckDataLst", "RytRefundLog.queryTkCheckDataCount", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RytRefundLog> queryTkCheckDataLstForExcel(Map<String, Object> map) {
		return getSqlSession().selectList("RytRefundLog.queryTkCheckDataLstForExcel", map);
	}

	@Override
	public RytRefundLog queryTkMoney(Map<String, Object> map) {
		return (RytRefundLog) getSqlSession().selectOne("RytRefundLog.queryTkMoney", map);
	}
	
	@Override
	public Page<RytRefundLog> queryPageRefundData(Page<RytRefundLog> page,Map<String, Object> map) {
		return selectPage(page, "RytRefundLog.queryPageRefundData", "RytRefundLog.queryPageCountRedundData", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RytRefundLog> queryRedundDataList(Map<String, Object> map) {
		return getSqlSession().selectList("RytRefundLog.queryRedundDataList", map);
	}

	@Override
	public RytRefundLog queryRedundDataDetail(String id) {
		return (RytRefundLog) getSqlSession().selectOne("RytRefundLog.queryRedundDataDetail", id);
	}

	@Override
	public Page<RytRefundLog> queryPageRedundHandleData(Page<RytRefundLog> page, Map<String, Object> map) {
		return selectPage(page, "RytRefundLog.queryPageRedundHandleData", "RytRefundLog.queryPageCountRedundHandleData", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RytRefundLog> queryRedundHandleDataList(Map<String, Object> map) {
		return getSqlSession().selectList("RytRefundLog.queryRedundHandleDataList", map);
	}

	@Override
	public RytRefundLog queryPageRefundAmount(Map<String, Object> map) {
		return (RytRefundLog) getSqlSession().selectOne("RytRefundLog.queryPageRefundAmount", map);
	}

	@Override
	public RytRefundLog queryPageRedundHandleAmount(Map<String, Object> map) {
		return (RytRefundLog) getSqlSession().selectOne("RytRefundLog.queryPageRedundHandleAmount", map);
	}

	@Override
	public int updateRefundDataTkFail(Map<String, Object> map) {
		return getSqlSession().update("RytRefundLog.updateRefundDataTkFail", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RytRefundLog> queryRefundLogDataInfoById(Map<String, Object> map) {
		return getSqlSession().selectList("RytRefundLog.queryRefundLogDataInfoById", map);
	}

	@Override
	public int updateRefundDataHandelSucess(Map<String, Object> map) {
		return getSqlSession().update("RytRefundLog.updateRefundDataHandelSucess", map);
	}
	
	@Override
	public int updateRefundLogDataToRGJB(Map<String, Object> map) {
		return getSqlSession().update("RytRefundLog.updateRefundLogDataToRGJB", map);
	}

	@Override
	public int updateRefundLogDataToCheckedById(Map<String, Object> map) {
		return getSqlSession().update("RytRefundLog.updateRefundLogDataToCheckedById", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RytRefundLog> queryDataById(Map<String, Object> map) {
		return getSqlSession().selectList("RytRefundLog.queryDataById", map);
	}

	@Override
	public int updateRefundLogDataToChexiaoById(Map<String, Object> map) {
		return getSqlSession().update("RytRefundLog.updateRefundLogDataToChexiaoById", map);
	}

	@Override
	public int updateRefundLogDataToOnlineTk(Map<String, Object> map) {
		return getSqlSession().update("RytRefundLog.updateRefundLogDataToOnlineTk", map);
	}

	@Override
	public RytRefundLog queryOnlineTkTotalMoney(Map<String, Object> map) {
		return (RytRefundLog) getSqlSession().selectOne("RytRefundLog.queryOnlineTkTotalMoney", map);
	}
}
