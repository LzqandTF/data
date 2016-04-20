package com.chinaebi.dao;

import com.chinaebi.entity.FunctionRightLogin;

public interface FunctionRightLoginDao {

	/**
	 * 删除某一系统操作员的权限
	 * @param loginId
	 * @return
	 */
	public int deleteByLoginId(int loginId);
	
	/**
	 * 添加操作员权限
	 * @param f
	 * @return
	 */
	public int addFunctionRightLogin(FunctionRightLogin f);
}
