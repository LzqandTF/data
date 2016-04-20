package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.RuleHandlerDao;
import com.chinaebi.entity.RuleHandler;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.RuleHandlerService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "ruleHandlerService")
public class RuleHandlerServiceImpl implements RuleHandlerService{
	
	private static final Logger log =LoggerFactory.getLogger(RuleHandlerServiceImpl.class); 
	
	@Autowired
	@Qualifier(value = "ruleHandlerDao")
	private RuleHandlerDao ruleHandlerDao;

	@Override
	public Page<RuleHandler> queryPageRuleHandler(Page<RuleHandler> page,
			Map<String, Object> map) {
		Page<RuleHandler> page_ = null;
		try{
			page_ = ruleHandlerDao.queryPageRuleHandler(page, map);
			if(page.getResult() == null){
				throw new PageException("ruleHandlerDao.queryPageRuleHandler(page,map)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public int insertRuleHandler(RuleHandler ruleHandler) {
		int effectNum = 0;
		try{
			effectNum = ruleHandlerDao.insertRuleHandler(ruleHandler);
			if(!(effectNum > 0)){
				throw new InsertException("ruleHandlerDao.insertRuleHandler 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateRuleHandler(RuleHandler ruleHandler) {
		int effectNum = 0;
		try{
			effectNum = ruleHandlerDao.updateRuleHandler(ruleHandler);
			if(!(effectNum > 0)){
				throw new UpdateException("RuleHandlerDao.updateRuleHandler 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteRuleHandler(int id) {
		int effectNum = 0;
		try{
			effectNum = ruleHandlerDao.deleteRuleHandler(id);
			if(!(effectNum > 0)){
				throw new DeleteException("ruleHandlerDao.deleteRuleHandler 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public RuleHandler queryRuleHandlerById(int id) {
		RuleHandler RuleHandler = null;
		try{
			RuleHandler = ruleHandlerDao.queryRuleHandlerById(id);
			if(RuleHandler == null){
				throw new SelectException("ruleHandlerDao.queryRuleHandlerById  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return RuleHandler;
	}

	@Override
	public List<RuleHandler> queryRuleHandlerList() {
		List<RuleHandler> list = null;
		try{
			list = ruleHandlerDao.queryRuleHandlerList();
			if(list == null){
				throw new SelectException("ruleHandlerDao.queryRuleHandlerList()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	
	@Override
	public List<RuleHandler> queryRuleHandlerListByColumn(String attribute_column){
		List<RuleHandler> list = null;
		try{
			list = ruleHandlerDao.queryRuleHandlerListByColumn(attribute_column);
			if(list == null){
				throw new SelectException("ruleHandlerDao.queryRuleHandlerListByColumn()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	
}
