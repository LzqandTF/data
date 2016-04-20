package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.InnerErrorHandling;
import com.chinaebi.utils.mybaits.Page;

public interface InnerErrorHandlingDao {
	/**
	 * 分页查询内部差错处理方式信息
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<InnerErrorHandling> queryPageInnerErrorHandling(Page<InnerErrorHandling> page,Map<String,Object> map);
	/**
	 * 根据ID删除内部差错处理方式数据
	 * @param id
	 * @return
	 */
	public int deleteErrorHandling(int id);
	/**
	 * 修改内部差错处理方式数据
	 * @param innerErrorHandling
	 * @return
	 */
	public int updateErrorHandling(InnerErrorHandling innerErrorHandling);
	/**
	 * 添加内部差错处理方式数据
	 * @param innerErrorHandling
	 * @return
	 */
	public int addErrorHandling(InnerErrorHandling innerErrorHandling);
	
	/**
	 * 查询所有内部差错处理方式数据
	 * @return List<InnerErrorHandling>
	 */
	public List<InnerErrorHandling> queryInnerErrorHandlingAll();
	
	public InnerErrorHandling queryInnerErrorHandling(int id);
}
