package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.RuleTemplate;
import com.chinaebi.utils.mybaits.Page;

public interface RuleTemplateService {
	public Page<RuleTemplate> queryPageRuleTemplate(Page<RuleTemplate> page,Map<String,Object> map);
	
	public int insertRuleTemplate(RuleTemplate ruleTemplate);
	
	public int updateRuleTemplate(RuleTemplate ruleTemplate);
	
	public int deleteRuleTemplate(int object_id);
	
	public RuleTemplate queryRuleTemplateById(int object_id);
	
	public List<RuleTemplate> queryRuleTemplateList();
}
