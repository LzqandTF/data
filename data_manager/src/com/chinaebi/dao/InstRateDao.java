package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.InstRate;
import com.chinaebi.utils.mybaits.Page;

public interface InstRateDao {
	/**
	 * 分页查询渠道费率配置信息
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<InstRate> queryPageInstRate(Page<InstRate> page,Map<String,Object> map);
	
	/**
	 * 新增渠道费率配置信息
	 * @param instRate
	 * @return
	 */
	public int addInstRate(InstRate instRate);
	
	/**
	 * 删除渠道费率配置信息
	 * @param map
	 * @return
	 */
	public int deleteInstRate(Map<String,Object> map);
	
	/**
	 * 修改渠道费率配置信息
	 * @param instRate
	 * @return
	 */
	public int updateInstRate(InstRate instRate);
	
	/**
	 * 查询已配置渠道费率的渠道ID和渠道类型
	 * @return
	 */
	public List<InstRate> queryInstRateInstId();
}
