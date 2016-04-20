package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.MccTypeDao;
import com.chinaebi.entity.MccType;
import com.chinaebi.service.MccTypeService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "mccTypeService")
public class MccTypeServiceImpl implements MccTypeService{

	@Autowired
	@Qualifier(value = "mccTypeDao")
	private MccTypeDao mccTypeDao ;
	
	@Override
	public Page<MccType> queryPageMccType(Page<MccType> page,
			Map<String, Object> map) {
		return mccTypeDao.queryPageMccType(page, map);
	}

	@Override
	public MccType queryMccTypeById(int id) {
		return mccTypeDao.queryMccTypeById(id);
	}

	@Override
	public List<MccType> queryAllMccType() {
		return mccTypeDao.queryAllMccType();
	}

	@Override
	public int deleteMccType(int id) {
		return mccTypeDao.deleteMccType(id);
	}

	@Override
	public int updateMccType(MccType mccType) {
		return mccTypeDao.updateMccType(mccType);
	}

	@Override
	public int addMccType(MccType mccType) {
		return mccTypeDao.addMccType(mccType);
	}
	
	@Override
	public int deleteMccTypeByParentId(int big_type_id){
		return mccTypeDao.deleteMccTypeByParentId(big_type_id);
	}

	@Override
	public List<MccType> queryMccTypeByParentId(int big_type_id) {
		return mccTypeDao.queryMccTypeByParentId(big_type_id);
	}
}
