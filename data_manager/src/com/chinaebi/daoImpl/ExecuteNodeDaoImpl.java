package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ExecuteNodeDao;
import com.chinaebi.entity.ExecuteNode;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "executeNodeDao")
public class ExecuteNodeDaoImpl extends MyBatisDao implements ExecuteNodeDao{
	/**
	 * 添加渠道工作流节点
	 * @param executeNode
	 * @return
	 */
	@Override
	public int addExecuteNode(ExecuteNode executeNode) {
		return getSqlSession().insert("ExecuteNode.addExecuteNode",executeNode);
	}
	/**
	 * 修改渠道工作流节点
	 * @param executeNode
	 * @return
	 */
	@Override
	public int updateExecuteNode(ExecuteNode executeNode) {
		return getSqlSession().update("ExecuteNode.updateExecuteNode",executeNode);
	}
	/**
	 * 查询所有渠道工作流节点
	 * @param executeNode
	 * @return
	 */
	@Override
	public List<ExecuteNode> queryAll(Map<String,Object> map) {
		return getSqlSession().selectList("ExecuteNode.queryAll",map);
	}
	/**
	 * 通过id查询渠道工作流
	 * @param id
	 * @return
	 */
	@Override
	public ExecuteNode queryExecuteNodeByDeductIdAndDeductDate(Map<String,Object> map){
		return (ExecuteNode) getSqlSession().selectOne("ExecuteNode.queryExecuteNodeByDeductIdAndDeductDate", map);
	}
	
	/**
	 * 分页查询个渠道工作流节点
	 * @param map
	 * @return
	 */
	@Override
	public Page<ExecuteNode> queryPage(Page<ExecuteNode> page ,Map<String, Object> map) {
		return selectPage(page, "ExecuteNode.queryPage","ExecuteNode.queryPageCount",map);
	}

}
