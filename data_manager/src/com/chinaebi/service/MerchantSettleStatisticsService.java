package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MerchantSettleStatistics;
import com.chinaebi.utils.mybaits.Page;

/**
 * 商户结算信息Service接口
 * 
 * @author wufei
 * 
 */
public interface MerchantSettleStatisticsService {
	/**
	 * 分页获取商户结算信息列表
	 * @param page 分页参数
	 * @param map  查询参数
	 * @return
	 */
	public Page<MerchantSettleStatistics> queryPageMerchantSettleStatistics(Page<MerchantSettleStatistics> page, Map<String, Object> map);
	
	/**
	 * 以商户号分组查询可结算商户信息
	 * @param map 查询参数
	 * @return
	 */
	public MerchantSettleStatistics querySettleMerchantInfoGroupByMerCode(Map<String,Object> map);
	/**
	 * 查询可结算商户信息
	 * @param map 查询参数
	 * @return
	 */
	public List<MerchantSettleStatistics> querySettleMerchantInfo(Map<String,Object> map);
	
	/**
	 * 更新结算商户状态
	 * @param map
	 * @return
	 */
	public int updateMerchantSettleStatistics(Map<String,Object> map);
	
	/**
	 * 获取渠道列表
	 * @param map
	 * @return
	 */
	public List<MerchantSettleStatistics> queryInstIdList(Map<String, Object> map);
	
	/**
	 * 根据查询条件，统计T+1数据表中数据
	 * @param map
	 * @return
	 */
	public MerchantSettleStatistics queryMerchantSettleStatisticsInfo(Map<String,Object> map);
	
	/**
	 * 根据相关条件，修改相应T+1数据表中数据信息
	 * @param map
	 * @return
	 */
	public int updateMerchantSettleStatisticsInfo(MerchantSettleStatistics merchantSettleStatistics);
	
	/**
	 * 根据相关条件，新增T+1数据信息
	 * @param map
	 * @return
	 */
	public int addMerchantSettleStatisticsInfo(MerchantSettleStatistics merchantSettleStatistics);
	
	/**
	 * 网关对应商户交易表查询
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MerchantSettleStatistics> queryPageMSSByMerCodeAndInstId(Page<MerchantSettleStatistics> page, Map<String, Object> map);
	
	/**
	 * 网关对应商户交易表查询(金额统计)
	 * @param map
	 * @return
	 */
	public MerchantSettleStatistics queryTotalMoney(Map<String, Object> map);
	
	/**
	 * 网关对应商户交易表查询(xls报表下载)
	 * @param map
	 * @return
	 */
	public List<MerchantSettleStatistics> queryDataLstForExcel(Map<String, Object> map);
	
	public Map<String, List<MerchantSettleStatistics>> queryDataLstByInstIdForExcel(Map<String, Object> map);
	
	public Map<String, List<MerchantSettleStatistics>> queryDataLstByMerForExcel(Map<String, Object> map);
}
