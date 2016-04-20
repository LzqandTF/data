/**
 * 权限管理
 * @author YangYang
 * @date 2013-7-3
 */
package com.chinaebi.dao;

import java.util.List;

import com.chinaebi.entity.FunctionRight;

public interface FunctionRightDao {

	/**
	 * 查询所有权限
	 * @return
	 */
	public List<FunctionRight> queryAll();
	
	/**
	 * 查询某一系统操作员的权限
	 * @param loginId
	 * @return
	 */	
	public List<FunctionRight> queryByLoginId(int loginId);
	
	/**
	 * 根据一级权限ID查询其子权限
	 * @param parentId
	 * @return
	 */	
	public List<FunctionRight> queryByParentId(int parentId);
	/**
	 * 查询机构所有权限
	 * @return
	 */
	public List<FunctionRight> queryAllXsdl();
}
