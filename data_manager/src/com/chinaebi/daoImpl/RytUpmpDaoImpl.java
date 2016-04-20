package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.RytUpmpDao;
import com.chinaebi.entity.RytRefundLog;
import com.chinaebi.entity.RytUpmp;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "rytUpmpDao")
public class RytUpmpDaoImpl extends MyBatisDao implements RytUpmpDao {
	@Override
	public Page<RytUpmp> queryPageRtyUpmp(Page<RytUpmp> page, Map<String, Object> map) {
		return selectPage(page, "RytUpmp.queryPageRtyUpmp", "RytUpmp.queryCount", map);
	}

	@Override
	public Page<RytRefundLog> queryPageRytRefundLog(Page<RytRefundLog> page,
			Map<String, Object> map) {
		return selectPage(page, "RytRefundLog.queryPageRytRefundLog", "RytRefundLog.queryCount", map);
	}

	@Override
	public RytUpmp queryDetail(Map<String, Object> map) {
		return (RytUpmp) getSqlSession().selectOne("RytUpmp.queryDetail", map);
	}

	@Override
	public RytRefundLog queryDetailLog(Map<String, Object> map) {
		return (RytRefundLog) getSqlSession().selectOne("RytRefundLog.queryDetail", map);
	}
	
	public Page<RytUpmp> queryPageRtyUpmpForDz(Page<RytUpmp> page, Map<String, Object> map){
		return selectPage(page, "RytUpmp.queryPageRtyUpmpForDz", "RytUpmp.queryCountForDz", map);
	}
	
	public String queryTotalMoneyOfDz(Map<String,Object> map){
		return getSqlSession().selectOne("RytUpmp.queryTotalMoneyOfDz", map)+"";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RytUpmp> queryRytDzDetailDataList(Map<String, Object> map) {
		return getSqlSession().selectList("RytUpmp.queryRytDzDetailDataList", map);
	}

	@Override
	public int updateDataByTesq(Map<String, Object> map) {
		return getSqlSession().update("RytUpmp.updateDataByTesq", map);
	}

	@Override
	public int updateBkchkByTradeId(Map<String, Object> map) {
		return getSqlSession().update("Original_Data.updateBkchkByTradeId", map);
	}
}
