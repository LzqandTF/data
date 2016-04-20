package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.RuleTemplateDao;
import com.chinaebi.entity.RuleTemplate;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.RuleTemplateService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "ruleTemplateService")
public class RuleTemplateServiceImpl implements RuleTemplateService{
	
	private static final Logger log =LoggerFactory.getLogger(RuleTemplateServiceImpl.class); 
	
	@Autowired
	@Qualifier(value = "ruleTemplateDao")
	private RuleTemplateDao ruleTemplateDao;

	@Override
	public Page<RuleTemplate> queryPageRuleTemplate(Page<RuleTemplate> page,
			Map<String, Object> map) {
		Page<RuleTemplate> page_ = null;
		try{
			page_ = ruleTemplateDao.queryPageRuleTemplate(page, map);
			if(page.getResult() == null){
				throw new PageException("ruleTemplateDao.queryPageRuleTemplate(page,map)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public int insertRuleTemplate(RuleTemplate ruleTemplate) {
		int effectNum = 0;
		try{
			effectNum = ruleTemplateDao.insertRuleTemplate(ruleTemplate);
			if(!(effectNum > 0)){
				throw new InsertException("ruleTemplateDao.insertRuleTemplate 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateRuleTemplate(RuleTemplate ruleTemplate) {
		int effectNum = 0;
		try{
			effectNum = ruleTemplateDao.updateRuleTemplate(ruleTemplate);
			if(!(effectNum > 0)){
				throw new UpdateException("ruleTemplateDao.updateRuleTemplate 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteRuleTemplate(int id) {
		int effectNum = 0;
		try{
			effectNum = ruleTemplateDao.deleteRuleTemplate(id);
			if(!(effectNum > 0)){
				throw new DeleteException("ruleTemplateDao.deleteRuleTemplate 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public RuleTemplate queryRuleTemplateById(int id) {
		RuleTemplate RuleTemplate = null;
		try{
			RuleTemplate = ruleTemplateDao.queryRuleTemplateById(id);
			if(RuleTemplate == null){
				throw new SelectException("ruleTemplateDao.queryRuleTemplateById  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return RuleTemplate;
	}

	@Override
	public List<RuleTemplate> queryRuleTemplateList() {
		List<RuleTemplate> list = null;
		try{
			list = ruleTemplateDao.queryRuleTemplateList();
			if(list == null){
				throw new SelectException("RuleTemplateDao.queryRuleTemplateList()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	
}
