package com.chinaebi.service;

import java.util.Map;

import com.chinaebi.entity.PpfwData;
import com.chinaebi.utils.mybaits.Page;

public interface PpfwDataService {
	/**
	 * 分页查询品牌服务费信息
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<PpfwData> queryPpfwData(Page<PpfwData> page,Map<String,Object> map);
	/**
	 * 统计品牌服务费总数
	 * @param map
	 * @return
	 */
	public double getPpfwFeeTotalCount(Map<String,Object> map);
	
	/**
	 * 分页查询功能中，统计品牌服务费值
	 * @param map
	 * @return
	 */
	public double queryPagePpfwDataTotalCount(Map<String,Object> map);
}
