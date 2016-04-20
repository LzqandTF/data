package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ObjectRelevanceColumn;
import com.chinaebi.utils.mybaits.Page;

public interface ObjectRelevanceColumnService {
	public Page<ObjectRelevanceColumn> queryPageObjectRelevanceColumn(Page<ObjectRelevanceColumn> page,Map<String,Object> map);
	
	public int insertObjectRelevanceColumn(ObjectRelevanceColumn objectRelevanceColumn);
	
	public int updateObjectRelevanceColumn(ObjectRelevanceColumn objectRelevanceColumn);
	
	public int deleteObjectRelevanceColumn(int id);
	
	public ObjectRelevanceColumn queryObjectRelevanceColumnById(int id);
	
	public List<ObjectRelevanceColumn> queryObjectRelevanceColumnList();
	
	public boolean deleteObjectRelevanceColumnCountByObjectId(int object_id,int file_type);
	
	public List<ObjectRelevanceColumn> queryObjectColumnList(ObjectRelevanceColumn objectRelevanceColumn);

	public boolean deleteObjectRelevanceColumnCountByDzColumnId(int dz_column_id);
	
	public boolean updateObjectRelevanceColumnByRuleId(int rule_id);
	
	public int deleteObjectOrDzColumnId(Map<String,Object> map);
}
