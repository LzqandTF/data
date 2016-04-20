/**
 * 机构信息配置类
 * @author Li.Jikui
 * @date 2014-2-25
 */
package com.chinaebi.dao;
import java.util.List;
import java.util.Map;

import com.chinaebi.entity.InstInfo;
import com.chinaebi.utils.mybaits.Page;

public interface InstInfoDao {
	
	
	
	/**
	 * 添加机构信息配置
	 * @param instInfo
	 * @return
	 */
	public int addInstInfo(InstInfo instInfo);
	
	/**
	 * 修改机构信息配置
	 * @param instInfo
	 * @return
	 */
	public int updateInstInfo(InstInfo instInfo);
	
	/**
	 * 分页查询
	 * @param map
	 * @param page
	 * @return
	 */
	public Page<InstInfo> queryPageInstInfo(Page<InstInfo> page,Map<String, Object> map);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<InstInfo> queryAll();
		
	
	/**
	 * 删除机构信息配置
	 * @param instInfo
	 * @return
	 */
	public int deleteInstInfo(int instId);
	/**
	 * 根据参数的值，禁用或者激活渠道
	 * @param valueOf
	 * @return
	 */
	public int updateInstInfoStatus(Map<String,Object> map);
	
	/**
	 * 根据渠道id查询渠道个数
	 * @param instId
	 * @return
	 */
	public int queryInstInfoById(int instId);
	
	/**
	 * 通过渠道ID查询渠道信息
	 * @param instId
	 * @return
	 */
	public InstInfo queryInstInfoByInstId(Map<String,Integer> map);
	
	/**
	 * 根据bank_id获取渠道总数
	 * @param bank_id
	 * @return
	 */
	public int queryInstInfoCountByBankId(String bank_id);
	
	/**
	 * 根据银行机构ID删除扣款渠道
	 * @param bank_id
	 * @return
	 */
	public int deleteInstInfoByBankId(String bank_id);
}
