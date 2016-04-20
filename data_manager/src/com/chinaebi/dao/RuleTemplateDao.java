package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.RuleTemplate;
import com.chinaebi.utils.mybaits.Page;

public interface RuleTemplateDao {
	public Page<RuleTemplate> queryPageRuleTemplate(Page<RuleTemplate> page,Map<String,Object> map);
	
	public int insertRuleTemplate(RuleTemplate ruleTemplate);
	
	public int updateRuleTemplate(RuleTemplate ruleTemplate);
	
	public int deleteRuleTemplate(int id);
	
	public RuleTemplate queryRuleTemplateById(int id);
	
	public List<RuleTemplate> queryRuleTemplateList();
}
