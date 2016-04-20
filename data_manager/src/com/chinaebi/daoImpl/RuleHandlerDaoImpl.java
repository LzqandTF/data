package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.RuleHandlerDao;
import com.chinaebi.entity.RuleHandler;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "ruleHandlerDao")
public class RuleHandlerDaoImpl extends MyBatisDao implements RuleHandlerDao{

	@Override
	public Page<RuleHandler> queryPageRuleHandler(Page<RuleHandler> page,Map<String, Object> map) {
		return selectPage(page,"RuleHandler.queryPageRuleHandler", "RuleHandler.queryPageCount",map);
	}

	@Override
	public int insertRuleHandler(RuleHandler ruleHandler) {
		return  getSqlSession().insert("RuleHandler.insertRuleHandler",ruleHandler);
	}

	@Override
	public int updateRuleHandler(RuleHandler ruleHandler) {
		return getSqlSession().update("RuleHandler.updateRuleHandler", ruleHandler);
	}

	@Override
	public int deleteRuleHandler(int id) {
		return getSqlSession().delete("RuleHandler.deleteRuleHandler", id);
	}

	@Override
	public RuleHandler queryRuleHandlerById(int id) {
		return (RuleHandler)getSqlSession().selectOne("RuleHandler.queryRuleHandlerById", id);
	}

	@Override
	public List<RuleHandler> queryRuleHandlerList() {
		return getSqlSession().selectList("RuleHandler.queryRuleHandlerList");
	}
	
	@Override
	public List<RuleHandler> queryRuleHandlerListByColumn(String attribute_column){
		return getSqlSession().selectList("RuleHandler.queryRuleHandlerListByColumn",attribute_column);
	}

}
