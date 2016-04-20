package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MccTypeDao;
import com.chinaebi.entity.MccType;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "mccTypeDao")
public class MccTypeDaoImpl extends MyBatisDao implements MccTypeDao{

	@Override
	public Page<MccType> queryPageMccType(Page<MccType> page,
			Map<String, Object> map) {
		return selectPage(page, "MccType.queryPageMccType",
				"MccType.queryPageCount", map);
	}

	@Override
	public MccType queryMccTypeById(int id) {
		return (MccType)getSqlSession().selectOne("MccType.queryMccTypeById", id);
	}

	@Override
	public List<MccType> queryAllMccType() {
		return getSqlSession().selectList("MccType.queryAllMccType");
	}

	@Override
	public int deleteMccType(int id) {
		return getSqlSession().delete("MccType.deleteMccType", id);
	}

	@Override
	public int updateMccType(MccType mccType) {
		return getSqlSession().update("MccType.updateMccType", mccType);
	}

	@Override
	public int addMccType(MccType mccType) {
		return getSqlSession().insert("MccType.addMccType", mccType);
	}
	
	public int deleteMccTypeByParentId(int big_type_id){
		return getSqlSession().delete("MccType.deleteMccTypeByParentId", big_type_id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MccType> queryMccTypeByParentId(int big_type_id) {
		return getSqlSession().selectList("MccType.queryMccTypeByParentId", big_type_id);
	}

}
