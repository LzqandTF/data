package com.chinaebi.service;

import java.util.Map;

import com.chinaebi.entity.YlCupsErrorEntry;
import com.chinaebi.utils.mybaits.Page;

public interface YlCupsErrorEntryService {
	
	/**
	 * 银联差错录入 只查询未提交的数据
	 * @param page
	 * @param map
	 * @return
	 */
	public abstract Page<YlCupsErrorEntry> queryPageNoCommitData(Page<YlCupsErrorEntry> page, Map<String, Object> map);
	
	/**
	 * 银联差错审批  只查询待审批的数据
	 * @param page
	 * @param map
	 * @return
	 */
	public abstract Page<YlCupsErrorEntry> queryNeedApprovalData(Page<YlCupsErrorEntry> page, Map<String, Object> map);
	
	Page<YlCupsErrorEntry> queryPageCupsErrorInput(Page<YlCupsErrorEntry> page, Map<String,Object> map);
	
	/**
	 * 银联差错录入
	 * @param ylcupsErrorEntry
	 * @return
	 */
	int addYlcupsErrorEntry(YlCupsErrorEntry ylcupsErrorEntry);
	
	public abstract YlCupsErrorEntry queryCupsErrorInputDetail(String id);
	
	public abstract int updateTradeStatus(Map<String, Object> map);
	
	public abstract int updatePass(Map<String, Object> map);
	
	public abstract int updateReject(YlCupsErrorEntry ylCupsErrorEntry);
	
	public abstract int updateData(YlCupsErrorEntry ylCupsErrorEntry);
	
	/**
	 * 无原交易录入时先根据流水号查询该条数据是否存在
	 * @param reqSysStance
	 * @return
	 */
	public abstract int queryDataByReqSysStance(String reqSysStance);
	
	/**
	 * 无原交易录入
	 * @param ylCupsErrorEntry
	 * @return
	 */
	public abstract int addOriginalData(YlCupsErrorEntry ylCupsErrorEntry);
}
