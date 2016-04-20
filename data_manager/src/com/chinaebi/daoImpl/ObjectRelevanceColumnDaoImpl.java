package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ObjectRelevanceColumnDao;
import com.chinaebi.entity.ObjectRelevanceColumn;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "objectRelevanceColumnDao")
public class ObjectRelevanceColumnDaoImpl extends MyBatisDao implements ObjectRelevanceColumnDao{

	@Override
	public Page<ObjectRelevanceColumn> queryPageObjectRelevanceColumn(Page<ObjectRelevanceColumn> page,Map<String, Object> map) {
		return selectPage(page,"ObjectRelevanceColumn.queryPageObjectRelevanceColumn", "ObjectRelevanceColumn.queryPageCount",map);
	}

	@Override
	public int insertObjectRelevanceColumn(ObjectRelevanceColumn objectRelevanceColumn) {
		return  getSqlSession().insert("ObjectRelevanceColumn.insertObjectRelevanceColumn",objectRelevanceColumn);
	}

	@Override
	public int updateObjectRelevanceColumn(ObjectRelevanceColumn objectRelevanceColumn) {
		return getSqlSession().update("ObjectRelevanceColumn.updateObjectRelevanceColumn", objectRelevanceColumn);
	}

	@Override
	public int deleteObjectRelevanceColumn(int id) {
		return getSqlSession().delete("ObjectRelevanceColumn.deleteObjectRelevanceColumn", id);
	}

	@Override
	public ObjectRelevanceColumn queryObjectRelevanceColumnById(int id) {
		return (ObjectRelevanceColumn)getSqlSession().selectOne("ObjectRelevanceColumn.queryObjectRelevanceColumnById", id);
	}

	@Override
	public List<ObjectRelevanceColumn> queryObjectRelevanceColumnList() {
		return getSqlSession().selectList("ObjectRelevanceColumn.queryObjectRelevanceColumnList");
	}
	
	public int deleteObjectRelevanceColumnCountByObjectId(Map<String,Object> map){
		return getSqlSession().delete("ObjectRelevanceColumn.deleteObjectRelevanceColumnCountByObjectId",map);
	}

	@Override
	public List<ObjectRelevanceColumn> queryObjectColumnList(
			ObjectRelevanceColumn objectRelevanceColumn) {
		return getSqlSession().selectList("ObjectRelevanceColumn.queryObjectRelevanceColumn", objectRelevanceColumn);
	}

	public int deleteObjectRelevanceColumnCountByDzColumnId(int dz_column_id){
		return getSqlSession().delete("ObjectRelevanceColumn.deleteObjectRelevanceColumnCountByDzColumnId",dz_column_id);
	}
	
	public int updateObjectRelevanceColumnByRuleId(int rule_id){
		return getSqlSession().update("ObjectRelevanceColumn.updateObjectRelevanceColumnByRuleId", rule_id);
	}
	
	public int deleteObjectOrDzColumnId(Map<String,Object> map){
		return getSqlSession().delete("ObjectRelevanceColumn.deleteObjectOrDzColumnId", map);
	}
}
