package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MccDiscount;
import com.chinaebi.utils.mybaits.Page;

public interface MccDiscountDao {
	/**
	 * 分页查询Mcc配置
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MccDiscount> queryPageMccDiscount(Page<MccDiscount> page,Map<String,Object> map);
	
	/**
	 * 通过id查询Mcc配置
	 * @param id
	 * @return
	 */
	public MccDiscount queryMccDiscountById(int id);
	
	/**
	 * 返回所有Mcc配置集合
	 * @return
	 */
	public List<MccDiscount> queryAllMccDiscount();
	
	/**
	 * 通过id删除Mcc配置
	 * @param id
	 * @return
	 */
	public int deleteMccDiscount(int id);
	
	/**
	 * 修改Mcc配置
	 * @param MccDiscount
	 * @return
	 */
	public int updateMccDiscount(MccDiscount MccDiscount);
	
	/**
	 * 添加Mcc配置
	 * @param MccDiscount
	 * @return
	 */
	public int addMccDiscount(MccDiscount MccDiscount);
	
	/**
	 *通过MCC类型删除MCC扣率配置信息 
	 * @param id
	 * @return
	 */
	public int deleteMccDiscountByMccTypeId(int id);
	
	
}
