package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MerchantSettleFail;
import com.chinaebi.utils.mybaits.Page;

public interface MerchantSettleFailService {
	/**
	 * 分页查询结算发起失败记录
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MerchantSettleFail> queryPageMerchantSettleFail(Page<MerchantSettleFail> page,Map<String,Object> map);
	/**
	 * 查询结算发起失败查询记录
	 * @param map
	 * @return
	 */
	public List<MerchantSettleFail> queryMerchantSettleFailList(Map<String,Object> map);
	/**
	 * 添加商户结算失败记录信息
	 * @param merchantSettleFail
	 * @return
	 */
	public boolean addMerchantSettleFail(MerchantSettleFail merchantSettleFail);
}
