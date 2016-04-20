package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.CustomObjectDao;
import com.chinaebi.entity.CustomObject;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "customObjectDao")
public class CustomObjectDaoImpl extends MyBatisDao implements CustomObjectDao{

	@Override
	public Page<CustomObject> queryPageCustomObject(Page<CustomObject> page,Map<String, Object> map) {
		return selectPage(page,"CustomObject.queryPageCustomObject", "CustomObject.queryPageCount",map);
	}

	@Override
	public int insertCustomObject(CustomObject customObject) {
		return  getSqlSession().insert("CustomObject.insertCustomObject",customObject);
	}

	@Override
	public int updateCustomObject(CustomObject customObject) {
		return getSqlSession().update("CustomObject.updateCustomObject", customObject);
	}

	@Override
	public int deleteCustomObject(int object_id) {
		return getSqlSession().delete("CustomObject.deleteCustomObject", object_id);
	}

	@Override
	public CustomObject queryCustomObjectById(int object_id) {
		return (CustomObject)getSqlSession().selectOne("CustomObject.queryCustomObjectById", object_id);
	}

	@Override
	public List<CustomObject> queryCustomObjectList() {
		return getSqlSession().selectList("CustomObject.queryCustomObjectList");
	}
	
	@Override
	public List<CustomObject> queryCustomObjectListOfInterfaceTypeNotAll() {
		return getSqlSession().selectList("CustomObject.queryCustomObjectListOfInterfaceTypeNotAll");
	}

	@Override
	public int unionCheckobjectName(CustomObject customObject) {
		return (Integer)getSqlSession().selectOne("CustomObject.queryUnionCheckobjectName", customObject);
	}

	@Override
	public int unionCheckfileAddress(CustomObject customObject) {
		return (Integer)getSqlSession().selectOne("CustomObject.queryUnionCheckfileAddress", customObject);
	}

	@Override
	public List<CustomObject> queryCustomObjectByFileType(
			int file_type) {
		return getSqlSession().selectList("CustomObject.queryCustomObjectByFileType",file_type);
	}

	@Override
	public CustomObject queryCustomObjectByName(String object_name) {
		return (CustomObject)getSqlSession().selectOne("CustomObject.queryCustomObjectByName", object_name);
	}

}
