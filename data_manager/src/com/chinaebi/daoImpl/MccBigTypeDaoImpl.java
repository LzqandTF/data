package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MccBigTypeDao;
import com.chinaebi.entity.MccBigType;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value="mccBigTypeDao")
public class MccBigTypeDaoImpl extends MyBatisDao implements MccBigTypeDao{

	@Override
	public Page<MccBigType> queryPageMccBigType(Page<MccBigType> page,
			Map<String, Object> map) {
		return selectPage(page,"MccBigType.queryPageMccBigType","MccBigType.queryPageCount", map);
	}

	@Override
	public MccBigType queryMccBigTypeById(int big_type_id) {
		return (MccBigType)getSqlSession().selectOne("MccBigType.queryMccBigTypeById", big_type_id);
	}

	@Override
	public List<MccBigType> queryAllMccBigType() {
		return getSqlSession().selectList("MccBigType.queryAllMccBigType");
	}

	@Override
	public int updateMccBigType(MccBigType mccBigType) {
		return getSqlSession().update("MccBigType.updateMccBigType", mccBigType);
	}

	@Override
	public int addMccBigType(MccBigType mccBigType) {
		return getSqlSession().insert("MccBigType.addMccBigType", mccBigType);
	}

	@Override
	public int deleteMccBigType(int big_type_id) {
		return getSqlSession().delete("MccBigType.deleteMccBigType", big_type_id);
	}

}
