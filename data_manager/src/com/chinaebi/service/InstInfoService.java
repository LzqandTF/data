package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.InstInfo;
import com.chinaebi.utils.mybaits.Page;

public interface InstInfoService {
	
	
	
	
	
	/**
	 * 添加机构信息配置
	 * @param instInfo
	 * @return
	 */
	public int addInstInfo(InstInfo instInfo);
	
	/**
	 * 修改机构配置
	 * @param instInfo
	 * @return
	 */
	public int updateInstInfo(InstInfo instInfo);
	
	/**
	 * 删除机构配置信息
	 * @param instInfo
	 * @return
	 */
	public boolean deleteInstInfo(int instId);
	
	/**
	 * 分页查询
	 * @param instId
	 * @return
	 */
	public Page<InstInfo> queryPageInstInfo(Page<InstInfo> page,Map<String, Object> map);
	
	public List<InstInfo> queryAll();

	/**
	 * 根据参数的值，禁用或者激活渠道
	 * @param valueOf
	 * @return
	 */
	public boolean updateInstInfoStatus(Map<String,Object> map);
	/**
	 * 根据渠道id查询渠道个数
	 * @param instId
	 * @return
	 */
	public int queryInstInfoById(int instId);
	
	/**
	 * 通过渠道ID查询渠道信息
	 * @param instId
	 * @return
	 */
	public InstInfo queryInstInfoByInstId(int instId,int instType);
	
	/**
	 * 调用接口，更新后台渠道内存信息
	 * @param instId
	 * @return
	 */
	public boolean updateRamDeductChannelInfo(int instId,int instType);
	
	/**
	 * 调用接口，更新渠道对应定时任务状态
	 * @param map
	 * @return
	 */
	public boolean handleInstInfoTimingTask(Map<String,Object> map);
	
	/**
	 * 根据bank_id获取渠道总数
	 * @param bank_id
	 * @return
	 */
	public int queryInstInfoCountByBankId(String bank_id);
	
	/**
	 * 根据银行机构ID删除扣款渠道
	 * @param bank_id
	 * @return
	 */
	public int deleteInstInfoByBankId(String bank_id);
}
