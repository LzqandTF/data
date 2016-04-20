package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;

import com.chinaebi.dao.ExecuteNodeDao;
import com.chinaebi.entity.ExecuteNode;
import com.chinaebi.service.ExecuteNodeService;
import com.chinaebi.utils.mybaits.Page;

@Service(value ="executeNodeService")
public class ExecuteNodeServiceImpl implements ExecuteNodeService{
	private static final Logger log = LoggerFactory.getLogger(ExecuteNodeServiceImpl.class);
	@Autowired
	@Qualifier(value="executeNodeDao")
	private ExecuteNodeDao executeNodeDao;
	
	/**
	 * 分页查询渠道工作流节点
	 * @param page
	 * @param map
	 * @return
	 */
	@Override
	public Page<ExecuteNode> queryPage(Page<ExecuteNode> page,Map<String,Object> map){
		Page<ExecuteNode> pageList = null;
		try{
			pageList = executeNodeDao.queryPage(page, map);
			if(pageList.getResult() == null){
				throw new PageException("executeNodeDao.queryPage(page, map) 查询数据为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return pageList;
	}
	
	@Override
	public int addExecuteNode(ExecuteNode executeNode) {
		int effectNum = 0;
		try{
			effectNum = executeNodeDao.addExecuteNode(executeNode);
			if(!(effectNum > 0)){
				throw new InsertException("executeNodeDao.addExecuteNode(executeNode)  插入渠道工作流节点失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateExecuteNode(ExecuteNode executeNode) {
		int effectNum = -1;
		try{
			effectNum = executeNodeDao.updateExecuteNode(executeNode);
			if(!(effectNum > -1)){
				throw new UpdateException("executeNodeDao.updateExecuteNode(executeNode) 修改渠道工作流节点失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public List<ExecuteNode> queryAll(int deduct_sys_id,String deduct_stml_date) {
		List<ExecuteNode> list = null;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("deduct_sys_id",deduct_sys_id);
			map.put("deduct_stml_date",deduct_stml_date);
			list = executeNodeDao.queryAll(map);
			if(list==null || list.size()==0){
				throw new SelectException("executeNodeDao.queryAll()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	/**
	 * 通过id查询渠道工作流
	 * @param id
	 * @return
	 */
	public ExecuteNode queryExecuteNodeByIdAndDeductDate(String id,String deduct_stml_date){
		ExecuteNode executeNode = null;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", id);
			map.put("deduct_stml_date", deduct_stml_date);
			executeNode = executeNodeDao.queryExecuteNodeByDeductIdAndDeductDate(map);
			if(executeNode == null){
				throw new SelectException("executeNodeDao.queryExecuteNodeById(id)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return executeNode;
	}
	
	/**
	 * 更新渠道工作流节点
	 * @param map
	 * @return
	 */
	public boolean updateExecuteStatus(Map<String,Object> map){
		boolean flag = false;
		try{
			if(map.get("deduct_sys_id") == null ){
				log.info("渠道ID 为空，不能修改工作节点状态");
				return false;
			}
			if(map.get("deduct_stml_date") == null){
				log.info("清算日期 为空，不能修改工作节点状态");
				return false;
			}
			log.info("渠道ID"+map.get("deduct_sys_id")+"");
			log.info("清算日期"+map.get("deduct_stml_date")+"");
			log.info("渠道类型"+map.get("inst_type")+"");
			ExecuteNode executeNode = executeNodeDao.queryExecuteNodeByDeductIdAndDeductDate(map);
			log.info(executeNode==null?"为空":"不为空");
			if(executeNode == null){//判断对象是否为空
				log.info("对象为空，进入添加方法");
				ExecuteNode e = new ExecuteNode();
				e.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				e.setDeduct_stml_date(map.get("deduct_stml_date")+"");
				e.setDeduct_sys_id(Integer.valueOf(map.get("deduct_sys_id")+""));
				e.setInst_type(Integer.valueOf(map.get("inst_type").toString()));
				if(map.get("dz_file_create") != null){
					log.info("设置对账文件生成节点为"+Integer.valueOf(map.get("dz_file_create")+""));
					e.setDz_file_create(Integer.valueOf(map.get("dz_file_create")+""));
				}
				if(map.get("dz_file_gain") != null){
					log.info("设置对账文件获取节点为"+Integer.valueOf(map.get("dz_file_gain")+""));
					e.setDz_file_gain(Integer.valueOf(map.get("dz_file_gain")+""));
				}
				if(map.get("dz_handle") != null){
					log.info("设置对账处理节点为"+Integer.valueOf(map.get("dz_handle")+""));
					e.setDz_handle(Integer.valueOf(map.get("dz_handle")+""));
				}
				if(map.get("error_handle") != null){
					log.info("设置差错处理节点为"+Integer.valueOf(map.get("error_handle")+""));
					e.setError_handle(Integer.valueOf(map.get("error_handle")+""));
				}
				if(map.get("trade_collect") != null){
					log.info("设置交易数据汇总节点为"+Integer.valueOf(map.get("trade_collect")+""));
					e.setTrade_collect(Integer.valueOf(map.get("trade_collect")+""));
				}
				if(map.get("inst_name") != null){
					e.setInst_name(map.get("inst_name")+"");
				}
				int effectNum = addExecuteNode(e);
				if(effectNum > 0){
					flag = true;
				}
			}else{
				log.info("对象不为空，进入修改方法");
				if(map.get("dz_file_create") != null){
					log.info("设置对账文件生成节点为"+Integer.valueOf(map.get("dz_file_create")+""));
					executeNode.setDz_file_create(Integer.valueOf(map.get("dz_file_create")+""));
				}
				if(map.get("dz_file_gain") != null){
					log.info("设置对账文件获取节点为"+Integer.valueOf(map.get("dz_file_gain")+""));
					executeNode.setDz_file_gain(Integer.valueOf(map.get("dz_file_gain")+""));
				}
				if(map.get("dz_handle") != null){
					log.info("设置对账处理节点为"+Integer.valueOf(map.get("dz_handle")+""));
					executeNode.setDz_handle(Integer.valueOf(map.get("dz_handle")+""));
				}
				if(map.get("error_handle") != null){
					log.info("设置差错处理节点为"+Integer.valueOf(map.get("error_handle")+""));
					executeNode.setError_handle(Integer.valueOf(map.get("error_handle")+""));
				}
				if(map.get("trade_collect") != null){
					log.info("设置交易数据汇总节点为"+Integer.valueOf(map.get("trade_collect")+""));
					executeNode.setTrade_collect(Integer.valueOf(map.get("trade_collect")+""));
				}
				executeNode.setDeduct_stml_date(map.get("deduct_stml_date")+"");
				executeNode.setDeduct_sys_id(Integer.valueOf(map.get("deduct_sys_id")+""));
				executeNode.setInst_type(Integer.valueOf(map.get("inst_type").toString()));
				int effectNum = updateExecuteNode(executeNode);
				if(effectNum > -1){
					flag = true;
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		log.info("更新工作流节点状态"+(flag?"成功":"失败"));
		return flag;
	}
}
