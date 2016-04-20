package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ExecuteNode;
import com.chinaebi.utils.mybaits.Page;

public interface ExecuteNodeService {
	/**
	 * 分页查询渠道工作流节点
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<ExecuteNode> queryPage(Page<ExecuteNode> page,Map<String,Object> map);
	/**
	 * 添加渠道工作流节点
	 * @param executeNode
	 * @return
	 */
	public int addExecuteNode(ExecuteNode executeNode);
	/**
	 * 修改渠道工作流节点
	 * @param executeNode
	 * @return
	 */
	public int updateExecuteNode(ExecuteNode executeNode);
	/**
	 * 查询所有渠道工作流节点
	 * @param executeNode
	 * @return
	 */
	public List<ExecuteNode> queryAll(int deduct_sys_id,String deduct_stml_date);
	/**
	 * 通过id查询渠道工作流
	 * @param id
	 * @return
	 */
	public ExecuteNode queryExecuteNodeByIdAndDeductDate(String id,String deduct_stml_date);
	
	/**
	 * 更新渠道工作流节点
	 * @param map
	 * @return
	 */
	public boolean updateExecuteStatus(Map<String,Object> map);
}
