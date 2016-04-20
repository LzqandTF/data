package com.chinaebi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinaebi.entity.Login;
import com.chinaebi.utils.mybaits.Page;

public interface LoginService {
	
	/**
	 * 登录验证
	 * @param userName
	 * @param password
	 * @return
	 */
	public Login checkLogin(String userName,String password);
	
	/**
	 * 查询所有系统操作员
	 * @return
	 */
	public List<Login> queryAllLogins(); 

	/**
	 * 验证用户名是否存在
	 * @return
	 */
	public int checkAddLogin(String loginName);
	
	/**
	 * 添加系统操作员
	 * @param loginName
	 * @param password
	 * @return
	 */
	public int addLogin(String loginName,String password,String chineseName);
	
	/**
	 * 修改系统操作员
	 * @param loginName
	 * @param password
	 * @param status
	 * @return
	 */
	public int updateLogin(String id,String loginName,String password,String status,Date loginDate,String chineseName);
	
	/**
	 * 分页查询
	 * @param id
	 * @param loginName
	 * @param page
	 * @return
	 */
	public Page<Login> queryPageLogin(String id,String loginName,Page<Login> page);
	
	/**
	 * 修改操作员中文名称
	 * @param map
	 * @return
	 */
	public int updateChineseName(Map<String,Object> map);
}
