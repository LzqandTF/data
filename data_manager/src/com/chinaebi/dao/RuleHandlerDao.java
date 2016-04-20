package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.RuleHandler;
import com.chinaebi.utils.mybaits.Page;

public interface RuleHandlerDao {
	public Page<RuleHandler> queryPageRuleHandler(Page<RuleHandler> page,Map<String,Object> map);
	
	public int insertRuleHandler(RuleHandler ruleHandler);
	
	public int updateRuleHandler(RuleHandler ruleHandler);
	
	public int deleteRuleHandler(int id);
	
	public RuleHandler queryRuleHandlerById(int id);
	
	public List<RuleHandler> queryRuleHandlerList();
	
	public List<RuleHandler> queryRuleHandlerListByColumn(String attribute_column);
}
