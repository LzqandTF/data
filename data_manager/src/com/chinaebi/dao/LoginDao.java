/**
 * 系统操作员类
 * @author YangYang
 * @date 2013-7-3
 */
package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.Login;
import com.chinaebi.utils.mybaits.Page;

public interface LoginDao {

	/**
	 * 验证登录
	 * @param map
	 * @return
	 */
	public Login checkLogin(Map<String, Object> map);
	
	/**
	 * 查询所有系统操作员
	 * @return
	 */
	public List<Login> queryAllLogins(); 
	
	/**
	 * 验证用户名是否存在
	 * @param loginName
	 * @return
	 */
	public int checkAddLogin(String loginName);
	
	/**
	 * 添加系统操作员
	 * @param login
	 * @return
	 */
	public int addLogin(Login login);
	
	/**
	 * 修改系统操作员
	 * @param login
	 * @return
	 */
	public int updateLogin(Login login);
	
	/**
	 * 分页查询
	 * @param map
	 * @param page
	 * @return
	 */
	public Page<Login> queryPageLogin(Map<String, Object> map,Page<Login> page);
	
	/**
	 * 修改操作员中文名称
	 * @param map
	 * @return
	 */
	public int updateChineseName(Map<String,Object> map);
		
}
