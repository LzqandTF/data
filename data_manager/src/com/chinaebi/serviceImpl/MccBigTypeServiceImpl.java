package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.MccBigTypeDao;
import com.chinaebi.entity.MccBigType;
import com.chinaebi.service.MccBigTypeService;
import com.chinaebi.utils.mybaits.Page;


@Service(value="mccBigTypeService")
public class MccBigTypeServiceImpl implements MccBigTypeService{

	private static final Logger log = LoggerFactory.getLogger(MccBigTypeServiceImpl.class);
	
	@Autowired
	@Qualifier(value="mccBigTypeDao")
	private MccBigTypeDao mccBigTypeDao;
	
	@Override
	public Page<MccBigType> queryPageMccBigType(Page<MccBigType> page,
			Map<String, Object> map) {
		Page<MccBigType> page_ = null;
		try{
			page_ = mccBigTypeDao.queryPageMccBigType(page, map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public MccBigType queryMccBigTypeById(int big_type_id) {
		MccBigType mccBigType = null;
		try{
			mccBigType = mccBigTypeDao.queryMccBigTypeById(big_type_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return mccBigType;
	}

	@Override
	public List<MccBigType> queryAllMccBigType() {
		List<MccBigType> mccBigTypes = null;
		try{
			mccBigTypes = mccBigTypeDao.queryAllMccBigType();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return mccBigTypes;
	}

	@Override
	public int updateMccBigType(MccBigType mccBigType) {
		int result = 0;
		try{
			result = mccBigTypeDao.updateMccBigType(mccBigType);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int addMccBigType(MccBigType mccBigType) {
		int result = 0;
		try{
			result = mccBigTypeDao.addMccBigType(mccBigType);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteMccBigType(int big_type_id) {
		int result = 0;
		try{
			result = mccBigTypeDao.deleteMccBigType(big_type_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

}
