package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ExecuteNode;
import com.chinaebi.utils.mybaits.Page;

public interface ExecuteNodeDao {
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
	 * 查询渠道工作流节点
	 * @param executeNode
	 * @return
	 */
	public List<ExecuteNode> queryAll(Map<String,Object> map);
	/**
	 * 通过id查询渠道工作流
	 * @param id
	 * @return
	 */
	public ExecuteNode queryExecuteNodeByDeductIdAndDeductDate(Map<String,Object> map);
	/**
	 * 分页查询个渠道工作流节点
	 * @param map
	 * @return
	 */
	public Page<ExecuteNode> queryPage(Page<ExecuteNode> page ,Map<String,Object> map);
	
}
