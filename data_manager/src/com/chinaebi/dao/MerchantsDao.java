package com.chinaebi.dao;


import java.util.List;
import java.util.Map;

import com.chinaebi.entity.Merchants;
import com.chinaebi.utils.mybaits.Page;

public interface MerchantsDao {
	
	public Page<Merchants> queryPageMerchantsList(Page<Merchants> page, Map<String, Object> map);
	
	/**
	 * 根据商户号查询商户简称
	 * @param mer_code
	 * @return
	 */
	public String queryMerAbbreviationByMerCode(String mer_code);
	
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
	 * 根据商户名称模糊查询获得商户名称列表
	 * @param merName
	 * @return
	 */
	public List<Merchants> queryMerNameListByMerName(Map<String, Object> map);
}
