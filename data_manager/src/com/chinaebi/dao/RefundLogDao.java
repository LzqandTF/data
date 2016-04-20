package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.RytRefundLog;
import com.chinaebi.utils.mybaits.Page;

public interface RefundLogDao {
	/**
	 * 联机退款查询
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<RytRefundLog> queryPageOnlineTkDataLst(Page<RytRefundLog> page, Map<String, Object> map);
	
	/**
	 * 联机退款(下载)
	 * @param map
	 * @return
	 */
	public List<RytRefundLog> queryOnlineTkDataLstForExcel(Map<String, Object> map);
	
	/**
	 * 退款审核查询
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<RytRefundLog> queryPageTkCheckDataLst(Page<RytRefundLog> page, Map<String, Object> map);
	
	/**
	 * 退款审核下载
	 * @param map
	 * @return
	 */
	public List<RytRefundLog> queryTkCheckDataLstForExcel(Map<String, Object> map);
	
	/**
	 * 退款金额统计
	 * @param map
	 * @return
	 */
	public RytRefundLog queryTkMoney(Map<String, Object> map);
	/**
	 * 退款查询功能
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<RytRefundLog> queryPageRefundData(Page<RytRefundLog> page,Map<String,Object> map);
	
	/**
	 * 退款查询下载功能
	 * @param map
	 * @return
	 */
	public List<RytRefundLog> queryRedundDataList(Map<String,Object> map);
	
	/**
	 * 退款数据详细信息查询
	 * @param id
	 * @return
	 */
	public RytRefundLog queryRedundDataDetail(String id);
	
	/**
	 * 退款经办数据查询
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<RytRefundLog> queryPageRedundHandleData(Page<RytRefundLog> page,Map<String,Object> map);
	
	/**
	 * 退款经办数据下载功能
	 * @param map
	 * @return
	 */
	public List<RytRefundLog> queryRedundHandleDataList(Map<String,Object> map);
	
	/**
	 * 分页统计退款金额
	 * @param map
	 * @return
	 */
	public RytRefundLog queryPageRefundAmount(Map<String,Object> map);
	
	/**
	 * 分页统计退款经办金额
	 * @param map
	 * @return
	 */
	public RytRefundLog queryPageRedundHandleAmount(Map<String,Object> map);
	
	/**
	 * 修改退款数据退款失败，并标记撤销退款原因
	 * @param map
	 * @return
	 */
	public int updateRefundDataTkFail(Map<String,Object> map);
	
	/**
	 * 通过ID查询相关退款数据信息
	 * @param id
	 * @return
	 */
	public List<RytRefundLog> queryRefundLogDataInfoById(Map<String, Object> map);
	
	/**
	 * 修改退款数据为经办成功,并修改退款类型为人工经办
	 * @param id
	 * @return
	 */
	public int updateRefundDataHandelSucess(Map<String, Object> map);
	
	/**
	 * 联机退款(人工经办操作)
	 * @param map
	 * @return
	 */
	public int updateRefundLogDataToRGJB(Map<String, Object> map);
	
	/**
	 * 审核完成
	 * @param id
	 * @return
	 */
	public int updateRefundLogDataToCheckedById(Map<String, Object> map);
	
	public List<RytRefundLog> queryDataById(Map<String, Object> map);
	
	public int updateRefundLogDataToChexiaoById(Map<String, Object> map);
	
	public int updateRefundLogDataToOnlineTk(Map<String, Object> map);
	
	public RytRefundLog queryOnlineTkTotalMoney(Map<String, Object> map);
}
