package com.chinaebi.daoImpl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ReplaceValueDao;
import com.chinaebi.entity.ReplaceValue;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "replaceValueDao")
public class ReplaceValueDaoImpl extends MyBatisDao implements ReplaceValueDao{

	@Override
	public int insertRepalceValue(ReplaceValue repalceValue) {
		return  getSqlSession().insert("ReplaceValue.insertReplaceValue",repalceValue);
	}

	@Override
	public int deleteRepalceValue(int id) {
		return getSqlSession().delete("ReplaceValue.deleteReplaceValue", id);
	}

	@Override
	public int updateRepalceValue(ReplaceValue repalceValue) {
		return getSqlSession().update("ReplaceValue.updateReplaceValue", repalceValue);
	}

	@Override
	public ReplaceValue queryReplaceValueById(int id) {
		return (ReplaceValue)getSqlSession().selectOne("ReplaceValue.queryReplaceValueById", id);
	}

	@Override
	public List<ReplaceValue> queryReplaceValueByRuleId(int rule_id) {
		return getSqlSession().selectList("ReplaceValue.queryReplaceValueByRuleId", rule_id);
	}

	@Override
	public List<ReplaceValue> queryReplaceValueList() {
		return getSqlSession().selectList("ReplaceValue.queryReplaceValueList");
	}
	
	public int queryReplaceValueCountByRuleIdAndOldValue(ReplaceValue replaceValue){
		return (Integer) getSqlSession().selectOne("ReplaceValue.queryReplaceValueCountByRuleIdAndOldValue",replaceValue);
	}
	
	public int deleteReplaceValueByRuleId(int rule_id){
		return getSqlSession().delete("ReplaceValue.deleteReplaceValueByRuleId", rule_id);
	}
}

