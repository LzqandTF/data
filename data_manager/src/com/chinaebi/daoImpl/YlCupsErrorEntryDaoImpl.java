package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.YlCupsErrorEntryDao;
import com.chinaebi.entity.YlCupsErrorEntry;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

/**
 * 银联差错录入
 */
@Component(value = "ylCupsErrorEntryDao")
public class YlCupsErrorEntryDaoImpl extends MyBatisDao implements YlCupsErrorEntryDao {

	@Override
	public Page<YlCupsErrorEntry> queryPageCupsErrorInput(
			Page<YlCupsErrorEntry> page, Map<String, Object> map) {
		
		return selectPage(page, "YlCupsErrorEntry.queryPageYlCupsErrorEntry","YlCupsErrorEntry.queryPageCount",map);
	}

	@Override
	public int addYlcupsErrorEntry(YlCupsErrorEntry ylcupsErrorEntry) {
		return getSqlSession().insert("YlCupsErrorEntry.addYlcupsErrorEntry", ylcupsErrorEntry);
	}

	@Override
	public YlCupsErrorEntry queryCupsErrorInputDetail(String id) {
		return (YlCupsErrorEntry) getSqlSession().selectOne("YlCupsErrorEntry.queryCupsErrorInputDetail", id);
	}

	@Override
	public int updateTradeStatus(Map<String, Object> map) {
		return getSqlSession().update("YlCupsErrorEntry.updateTradeStatus", map);
	}

	@Override
	public int updatePass(Map<String, Object> map) {
		return getSqlSession().update("YlCupsErrorEntry.updatePass", map);
	}

	@Override
	public int updateReject(YlCupsErrorEntry ylCupsErrorEntry) {
		return getSqlSession().update("YlCupsErrorEntry.updateReject", ylCupsErrorEntry);
	}

	@Override
	public int updateData(YlCupsErrorEntry ylCupsErrorEntry) {
		return getSqlSession().update("YlCupsErrorEntry.updateData", ylCupsErrorEntry);
	}

	@Override
	public Page<YlCupsErrorEntry> queryPageNoCommitData(Page<YlCupsErrorEntry> page, Map<String, Object> map) {
		return selectPage(page, "YlCupsErrorEntry.queryPageNoCommitData", "YlCupsErrorEntry.selectNoCommitCount", map);
	}

	@Override
	public Page<YlCupsErrorEntry> queryPageNeedApprovalData(Page<YlCupsErrorEntry> page, Map<String, Object> map) {
		return selectPage(page, "YlCupsErrorEntry.queryNeedApprovalData", "YlCupsErrorEntry.queryNeedApprovalCount", map);
	}

	@Override
	public int queryDataByReqSysStance(String reqSysStance) {
		return (Integer) getSqlSession().selectOne("YlCupsErrorEntry.queryDataByReqSysStance", reqSysStance);
	}

	@Override
	public int addOriginalData(YlCupsErrorEntry ylCupsErrorEntry) {
		return getSqlSession().insert("YlCupsErrorEntry.addOriginalData", ylCupsErrorEntry);
	}
}
