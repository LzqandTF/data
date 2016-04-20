package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ObjectRelevanceColumn;
import com.chinaebi.utils.mybaits.Page;

public interface ObjectRelevanceColumnDao {
	public Page<ObjectRelevanceColumn> queryPageObjectRelevanceColumn(Page<ObjectRelevanceColumn> page,Map<String,Object> map);
	
	public int insertObjectRelevanceColumn(ObjectRelevanceColumn objectRelevanceColumn);
	
	public int updateObjectRelevanceColumn(ObjectRelevanceColumn objectRelevanceColumn);
	
	public int deleteObjectRelevanceColumn(int id);
	
	public ObjectRelevanceColumn queryObjectRelevanceColumnById(int id);
	
	public List<ObjectRelevanceColumn> queryObjectRelevanceColumnList();
	
	public int deleteObjectRelevanceColumnCountByObjectId(Map<String,Object> map);
	
	public List<ObjectRelevanceColumn> queryObjectColumnList(ObjectRelevanceColumn objectRelevanceColumn);
	
	public int deleteObjectRelevanceColumnCountByDzColumnId(int dz_column_id);
	
	public int updateObjectRelevanceColumnByRuleId(int rule_id);
	
	public int deleteObjectOrDzColumnId(Map<String,Object> map);
}
