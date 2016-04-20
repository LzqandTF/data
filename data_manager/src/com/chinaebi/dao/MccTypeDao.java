package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MccType;
import com.chinaebi.utils.mybaits.Page;

public interface MccTypeDao {
	/**
	 * 分页查询Mcc类型
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MccType> queryPageMccType(Page<MccType> page,Map<String,Object> map);
	
	/**
	 * 通过id查询Mcc类型
	 * @param id
	 * @return
	 */
	public MccType queryMccTypeById(int id);
	
	/**
	 * 返回所有Mcc类型集合
	 * @return
	 */
	public List<MccType> queryAllMccType();
	
	/**
	 * 通过id删除Mcc类型
	 * @param id
	 * @return
	 */
	public int deleteMccType(int id);
	
	/**
	 * 修改Mcc类型
	 * @param mccType
	 * @return
	 */
	public int updateMccType(MccType mccType);
	
	/**
	 * 添加Mcc类型
	 * @param mccType
	 * @return
	 */
	public int addMccType(MccType mccType);
	
	/**
	 * 通过大类ID删除MCC	细类数据信息
	 * @param big_type_id
	 * @return
	 */
	public int deleteMccTypeByParentId(int big_type_id);
	
	/**
	 * 通过大类ID查询所有MCC细类信息
	 * @param big_type_id
	 * @return
	 */
	public List<MccType> queryMccTypeByParentId(int big_type_id);
	
	
}
