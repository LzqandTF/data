package com.chinaebi.service;


public interface FunctionRightLoginService {


	/**
	 * 删除某一系统操作员的权限
	 * @param loginId
	 * @return
	 */
	public int deleteByLoginId(int loginId);
	
	/**
	 * 添加操作员权限
	 * @param funcId
	 * @param loginId
	 * @return
	 */
	public int addFunctionRightLogin(String funcId,String loginId);
}
