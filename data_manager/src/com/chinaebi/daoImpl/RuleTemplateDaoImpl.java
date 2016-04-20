package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.RuleTemplateDao;
import com.chinaebi.entity.RuleTemplate;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "ruleTemplateDao")
public class RuleTemplateDaoImpl extends MyBatisDao implements RuleTemplateDao{

	@Override
	public Page<RuleTemplate> queryPageRuleTemplate(Page<RuleTemplate> page,Map<String, Object> map) {
		return selectPage(page,"RuleTemplate.queryPageRuleTemplate", "RuleTemplate.queryPageCount",map);
	}

	@Override
	public int insertRuleTemplate(RuleTemplate ruleTemplate) {
		return  getSqlSession().insert("RuleTemplate.insertRuleTemplate",ruleTemplate);
	}

	@Override
	public int updateRuleTemplate(RuleTemplate ruleTemplate) {
		return getSqlSession().update("RuleTemplate.updateRuleTemplate", ruleTemplate);
	}

	@Override
	public int deleteRuleTemplate(int id) {
		return getSqlSession().delete("RuleTemplate.deleteRuleTemplate", id);
	}

	@Override
	public RuleTemplate queryRuleTemplateById(int id) {
		return (RuleTemplate)getSqlSession().selectOne("RuleTemplate.queryRuleTemplateById", id);
	}

	@Override
	public List<RuleTemplate> queryRuleTemplateList() {
		return getSqlSession().selectList("RuleTemplate.queryRuleTemplateList");
	}

}
