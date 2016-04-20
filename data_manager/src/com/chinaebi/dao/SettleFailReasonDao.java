package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.SettleFailReason;
import com.chinaebi.utils.mybaits.Page;

public interface SettleFailReasonDao {
	/**
	 * 添加结算发起失败原因配置信息
	 * @param settleFailReason
	 * @return
	 */
	public int addSettleFailReason(SettleFailReason settleFailReason);
	
	/**
	 * 修改结算发起失败原因配置信息
	 * @param settleFailReason
	 * @return
	 */
	public int updateSettleFailReason(SettleFailReason settleFailReason);
	
	/**
	 * 通过主键ID删除结算发起失败原因配置信息
	 * @param reason_id
	 * @return
	 */
	public int deleteSettleFailReason(int reason_id);
	
	/**
	 * 分页查询结算发起失败原因配置信息
	 * @param page  分页查询
	 * @param map  查询条件参数
	 * @return
	 */
	public Page<SettleFailReason> queryPageSettleFailReason(Page<SettleFailReason> page,Map<String,Object> map);
	
	/**
	 * 通过主键ID查询结算发起失败原因配置信息
	 * @param reason_id
	 * @return
	 */
	public SettleFailReason querySettleFailReasonById(int reason_id);
	
	/**
	 * 获取结算发起失败原因集合
	 * @return
	 */
	public List<SettleFailReason> getSettleFailReasonList();
}
