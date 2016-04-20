package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MerchantFundSettle;
import com.chinaebi.entity.SettleMerchantMatch;
import com.chinaebi.utils.mybaits.Page;

/**
 * 商户资金结算信息Dao接口
 * @author wufei
 *
 */
public interface MerchantFundSettleDao {
	/**
	 * 分页获取商户资金结算信息列表
	 * @param page 分页参数
	 * @param map  查询参数
	 * @return
	 */
	public Page<MerchantFundSettle> queryPageMerchantFundSettle(Page<MerchantFundSettle> page, Map<String, Object> map);
	
	public List<MerchantFundSettle> queryMerchantFundSettleList(Map<String, Object> map);
	
	/**
	 * 添加商户资金结算信息
	 * @param merchantFundSettle
	 * @return
	 */
	public int addMerchantFundSettle(MerchantFundSettle merchantFundSettle);
	
	/**
	 * 通过结算截止日期查询最大系统批次号
	 * @param end_date
	 * @return
	 */
	public String queryMaxSysBatchNo(int end_date);
	
	/**
	 * 统计未结算确认的数据条数
	 * @param map
	 * @return
	 */
	public int queryNoConfirmSettleDataCount(Map<String,Integer> map);
	
	/**
	 * 获得当前系统批次号
	 * @return
	 */
	public Long getSysBatchNo();
	
	/**
	 * 通过商户号查询结算单该商户结算信息条数
	 * @param mer_code
	 * @return
	 */
	public int queryMerchantFundSettleCount(String mer_code);
	
	/**
	 * 根据结算商户号获取电银商户号
	 * @param settle_mer_code
	 * @return
	 */
	public List<SettleMerchantMatch> queryDyMerCodeBySettleMerCode(String settle_mer_code);
	
	/**
	 * 分页查询代付结果
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MerchantFundSettle> queryPageDfResult(Page<MerchantFundSettle> page, Map<String, Object> map);
	
	/**
	 * 下载代付结果
	 * @param map
	 * @return
	 */
	public List<MerchantFundSettle> queryDfResultDataLst(Map<String, Object> map);
	
	/**
	 * 分页查询电银账户划款结果
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MerchantFundSettle> queryPageDyDfResult(Page<MerchantFundSettle> page, Map<String, Object> map);
	
	/**
	 * 结算制表分页查询
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MerchantFundSettle> queryPageNeedZBDataLst(Page<MerchantFundSettle> page, Map<String, Object> map);
	
	/**
	 * 分页查询需要结算制表确认的数据
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MerchantFundSettle> queryPageNeedQRDataLst(Page<MerchantFundSettle> page, Map<String, Object> map);
	
	/**
	 * 从结算单中获取商户信息
	 * @param map
	 * @return
	 */
	public List<MerchantFundSettle> queryDataListByIds(Map<String, Object> map);
	
	/**
	 * 查询需要结算确认的已结算制表数据结算单数据
	 * @param ids  结算单ID
	 * @return
	 */
	public List<MerchantFundSettle> queryMerSettleDataOfNoQR(Map<String,Object> map);
	
	/**
	 * 修改结算到电银账户的结算单数据的同步状态与同步数据时间
	 * @param map
	 * @return
	 */
	public int updateMerchantFundSettleSynResultAndDate(Map<String,Object> map);
	
	/**
	 * 修改结算单结算状态为已结算确认
	 * @param id
	 * @return
	 */
	public int updateMerchantFundSettleDataQrStatus(int id);
	
	/**
	 * 结算制表操作
	 * @param map
	 * @return
	 */
	public int updateDataStatusToZB(Map<String, Object> map);
	
	/**
	 * 查询商户最后一次结算截止日期 
	 * @param mer_code
	 * @return
	 */
	public int queryMerSettleDataEndDate(String mer_code);
}
