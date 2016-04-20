package com.chinaebi.dao;

import java.util.List;

import com.chinaebi.entity.ReplaceValue;

public interface ReplaceValueDao {
	public int insertRepalceValue(ReplaceValue repalceValue);
	public int deleteRepalceValue(int id);
	public int deleteReplaceValueByRuleId(int rule_id);
	public int updateRepalceValue(ReplaceValue repalceValue);
	public ReplaceValue queryReplaceValueById(int id);
	public List<ReplaceValue> queryReplaceValueByRuleId(int rule_id);
	public List<ReplaceValue> queryReplaceValueList();
	public int queryReplaceValueCountByRuleIdAndOldValue(ReplaceValue replaceValue);
}
