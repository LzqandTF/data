package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.MccBigType;
import com.chinaebi.utils.mybaits.Page;

public interface MccBigTypeDao {
	/**
	 * 分页查询MCC大类数据
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MccBigType> queryPageMccBigType(Page<MccBigType> page,Map<String,Object> map);
	
	/**
	 * 通过主键ID查MCC大类信息
	 * @param big_type_id
	 * @return
	 */
	public MccBigType queryMccBigTypeById(int big_type_id);
	
	/**
	 * 查询所有MCC大类信息
	 * @return
	 */
	public List<MccBigType> queryAllMccBigType();
	
	/**
	 * 修改MCC大类信息
	 * @param mccBigType
	 * @return
	 */
	public int updateMccBigType(MccBigType mccBigType);
	
	/**
	 * 新增MCC大类信息
	 * @param mccBigType
	 * @return
	 */
	public int addMccBigType(MccBigType mccBigType);
	
	/**
	 * 通过主键删除MCC大类信息
	 * @param big_type_id
	 * @return
	 */
	public int deleteMccBigType(int big_type_id);
}
