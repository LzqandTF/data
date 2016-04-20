package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.MccDiscountDao;
import com.chinaebi.entity.MccDiscount;
import com.chinaebi.service.MccDiscountService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "mccDiscountService")
public class MccDiscountServiceImpl implements MccDiscountService{

	@Autowired
	@Qualifier(value = "mccDiscountDao")
	private MccDiscountDao mccDiscountDao ;
	
	@Override
	public Page<MccDiscount> queryPageMccDiscount(Page<MccDiscount> page,
			Map<String, Object> map) {
		return mccDiscountDao.queryPageMccDiscount(page, map);
	}

	@Override
	public MccDiscount queryMccDiscountById(int id) {
		return mccDiscountDao.queryMccDiscountById(id);
	}

	@Override
	public List<MccDiscount> queryAllMccDiscount() {
		return mccDiscountDao.queryAllMccDiscount();
	}

	@Override
	public int deleteMccDiscount(int id) {
		return mccDiscountDao.deleteMccDiscount(id);
	}

	@Override
	public int updateMccDiscount(MccDiscount mccDiscount) {
		return mccDiscountDao.updateMccDiscount(mccDiscount);
	}

	@Override
	public int addMccDiscount(MccDiscount mccDiscount) {
		return mccDiscountDao.addMccDiscount(mccDiscount);
	}

	@Override
	public int deleteMccDiscountByMccTypeId(int id) {
		return mccDiscountDao.deleteMccDiscountByMccTypeId(id);
	}
	
}
