package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.EmailPolice;
import com.chinaebi.entity.PoliceType;
import com.chinaebi.utils.mybaits.Page;

public interface EmailPoliceDao {
	
	/**
	 * 分页查询邮件报警配置数据
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<EmailPolice> queryPageEmailPolice(Page<EmailPolice> page, Map<String, Object> map);
	
	/**
	 * 添加邮件报警配置数据
	 * @param emailPolice
	 * @return
	 */
	public int addEmailPolice(EmailPolice emailPolice);
	
	/**
	 * 修改邮件报警配置数据
	 * @param emailPolice
	 * @return
	 */
	public int updateEmailPolice(EmailPolice emailPolice);
	
	/**
	 * 删除邮件报警配置数据
	 * @param id
	 * @return
	 */
	public int deleteEmailPolice(int emailId);
	
	/**
	 * 通过ID查询详情
	 * @param id
	 * @return
	 */
	public List<EmailPolice> queryAllByPoliceId(int policeId);
	
	/**
	 * 返回所有报警类型集合
	 * @return
	 */
	public List<PoliceType> queryAll();
	
	/**
	 * 根据邮箱地址、手机号码查询数据
	 * @param map
	 * @return
	 */
	public int queryDataByEmailOrPhone(Map<String, Object> map);
}
