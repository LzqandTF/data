package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.Merchants;
import com.chinaebi.utils.mybaits.Page;

public interface MerchantsService {
	
	/**
	 * 分页查询商户信息列表
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<Merchants> queryPageMerchantsList(Page<Merchants> page, Map<String, Object> map);
	
	/**
	 * 分页查询可结算商户列表
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<Merchants> queryPageSettleMerchantInfo(Page<Merchants> page,Map<String,Object> map);
	
	/**
	 * 统计可结算商户数量
	 * @param map
	 * @return
	 */
	public List<Merchants> querySettleMerchantInfoCount(Map<String,Object> map);
	
	/**
	 * 查询可结算商户信息
	 * @param map
	 * @return
	 */
	public List<Merchants> querySettleMerchantInfoList(Map<String,Object> map);
	
	/**
	 * 通过商户号查询商户状态、商户简称、商户类别等信息
	 * @param mer_code 商户号
	 * @return
	 */
	public Merchants queryMerchantBasicInfoByMerCode(String mer_code);
	
	/**
	 * 通过商户号模糊查询商户信息
	 * @param mer_code
	 * @return
	 */
	public List<Merchants> vagueQueryMerchantInfoByMerCode(String mer_code);
	
	/**
	 * 查询商户结算银行卡号
	 * @return
	 */
	public List<Merchants> queryMerchantsBilBankAccountInfo();
	
	/**
	 * 修改商户结算银行卡号
	 * @param map
	 * @return
	 */
	public int updateMerBillingBankAccount(Map<String,Object> map);
	
	/**
	 * 商户信息配置，根据商户查询商户基本信息和结算信息
	 * @param merCode
	 * @return
	 */
	public Merchants queryAllMerInfoByMerCode(String merCode);
	
	/**
	 * 修改商户基本信息
	 * @param map
	 * @return
	 */
	public int updateMerBasicByMerCode(Map<String, Object> map);
	
	/**
	 * 修改商户结算信息
	 * @param map
	 * @return
	 */
	public int updateMerBillingByMerCode(Map<String, Object> map);
	
	/**
	 * 查询所有商户基本信息
	 * @return
	 */
	public List<Merchants> queryAllMerBasicInfo();
	
	/**
	 * 查询所有商户结算信息
	 * @return
	 */
	public List<Merchants> queryAllMerBillingInfo();
	
	/**
	 * 发送接口更新后台内存中商户基本信息
	 * @param merCode 商户号
	 * @return
	 */
	public boolean updateRamMerBasicInfo(String merCode);
	
	/**
	 * 发送接口更新后台内存中商户结算信息
	 * @param merCode 商户号
	 * @return
	 */
	public boolean updateRamMerBillingInfo(String merCode);
	
	/**
	 * 根据商户名称模糊查询获得商户名称列表
	 * @param merName
	 * @return
	 */
	public List<Merchants> queryMerNameListByMerName(Map<String, Object> map);
	
	/**
	 * 修改商户余额、新增商户资金流水公共方法
	 * @param mer_code	商户号
	 * @param bank_id	网关ID
	 * @param inst_id	渠道ID
	 * @param bank_type	网关类型	
	 * @param trade_amount	交易金额
	 * @param mer_fee	商户手续费
	 * @param trade_date	交易日期
	 * @param trade_stance	交易流水号
	 * @param desc_info	简短描述
	 * @param whetherTk	是否为退款交易(负向交易)
	 * @return  0-操作成功;1-商户不存在;2-商户余额不足;3商户余额调整失败;4-商户资金流水操作失败;5-系统异常
	 */
	public int updateMerchantFundStanceAndBalance(String mer_code,int bank_id,int inst_id,int bank_type,double trade_amount,double mer_fee,int trade_date,String trade_stance,int desc_info,int whetherTk) throws Exception;
	
	/**
	 * 修改商户余额、新增商户资金流水、新增T+1表数据 公共方法
	 * @param mer_code	商户号
	 * @param bank_id	网关ID
	 * @param inst_id	渠道ID
	 * @param bank_type	网关类型
	 * @param trade_amount	交易金额
	 * @param mer_fee	商户手续费
	 * @param zf_fee	支付手续费
	 * @param system_fee	系统手续费
	 * @param trade_date	交易日期
	 * @param trade_stance	交易流水号
	 * @param desc_info	简短描述
	 * @param whetherTk	是否退款
	 * @param data_status	数据状态（0-正常；1-退款经办；2-退款审核；3-差错处理）  退款管理调用统一传1
	 * @return	0-操作成功;1-商户不存在;2-商户余额不足;3商户余额调整失败;4-商户资金流水操作失败;5-新增T+1表数据操作失败;6-系统异常
	 * @throws Exception
	 */
	public int updateMerchantFundStanceAndBalanceAndStatistics(String mer_code,int bank_id,int inst_id,int bank_type,double trade_amount,double mer_fee,double zf_fee,double system_fee,int trade_date,String trade_stance,int desc_info,int whetherTk,int data_status) throws Exception;
}