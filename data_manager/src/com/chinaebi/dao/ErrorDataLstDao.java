package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ErrorDataLst;
import com.chinaebi.utils.mybaits.Page;

public interface ErrorDataLstDao {
	public abstract Page<ErrorDataLst> queryPageErrorDataLst(Page<ErrorDataLst> page, Map<String, Object> map);
	
	public abstract ErrorDataLst queryErrorDataLstDetails(String trade_id);
	
	/**
	 * 根据tradeId查询详情信息
	 * @param tradeId  查询参数主键ID
	 * @return  返回ErrorDataLst实体
	 */
	public ErrorDataLst queryDetailByTradeId(String tradeId);
	
	public abstract List<ErrorDataLst> selectList(Map<String, Object> map);
	
	public abstract int updateErrorHandleMethod(ErrorDataLst errorDataLst);
	
	public abstract int updateHandlingStatus(ErrorDataLst errorDataLst);
	
	public abstract int updateApproval(ErrorDataLst errorDataLst);
	
	public abstract int updateHandlerRemark(ErrorDataLst errorDataLst);
	
	/**
	 * 查询内部差错调整审批数据
	 * @param page
	 * @param map
	 * @return
	 */
	public abstract Page<ErrorDataLst> queryPageApprovalData(Page<ErrorDataLst> page, Map<String, Object> map);
	
	/**
	 * 已处理差错查询
	 * @param page
	 * @param map
	 * @return
	 */
	public abstract Page<ErrorDataLst> queryOptionErrorLst(Page<ErrorDataLst> page, Map<String, Object> map);
	
	/**
	 * 内部差错调整数据下载
	 * @param map
	 * @return
	 */
	public abstract List<ErrorDataLst> queryAllForDownExcel(Map<String, Object> map);
	
	/**
	 * 统计商户未处理差错数据条数
	 * @param map 查询参数
	 * @return
	 */
	public abstract int queryErrorNoHandlerCount(Map<String,Object> map);
	
	/**
	 * 分页查询对账可疑数据
	 * @param page
	 * @param map
	 * @return
	 */
	public abstract Page<ErrorDataLst> queryPageDzTotalErrorData(Page<ErrorDataLst> page,Map<String,Object> map);
	
	/**
	 * 统计差错数据总数
	 * @param map
	 * @return
	 */
	public abstract int queryCountDzTotalErrorData(Map<String,Object> map);
	
	/**
	 * 查询差错数据集合
	 * @param map
	 * @return
	 */
	public abstract List<ErrorDataLst> queryDzTotalErrorDataList(Map<String,Object> map);
	
	/**
	 * 线上成功交易未对账，手动移至差错
	 * @param errorDataLst
	 * @return
	 */
	public int addDataToErrorDataLst(ErrorDataLst errorDataLst);
	
	/**
	 * 线上成功交易未对账，手动移至差错
	 * @param errorDataLst
	 * @return
	 */
	public int addUnderLineDataToErrorDataLst(ErrorDataLst errorDataLst);
}
