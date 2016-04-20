package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.MccDiscountDao;
import com.chinaebi.entity.MccDiscount;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "mccDiscountDao")
public class MccDiscountDaoImpl extends MyBatisDao implements MccDiscountDao{

	@Override
	public Page<MccDiscount> queryPageMccDiscount(Page<MccDiscount> page,
			Map<String, Object> map) {
		return selectPage(page, "MccDiscount.queryPageMccDiscount",
				"MccDiscount.queryPageCount", map);
	}

	@Override
	public MccDiscount queryMccDiscountById(int id) {
		return (MccDiscount)getSqlSession().selectOne("MccDiscount.queryMccDiscountById", id);
	}

	@Override
	public List<MccDiscount> queryAllMccDiscount() {
		return getSqlSession().selectList("MccDiscount.queryAllMccDiscount");
	}

	@Override
	public int deleteMccDiscount(int id) {
		return getSqlSession().delete("MccDiscount.deleteMccDiscount", id);
	}

	@Override
	public int updateMccDiscount(MccDiscount MccDiscount) {
		return getSqlSession().update("MccDiscount.updateMccDiscount", MccDiscount);
	}

	@Override
	public int addMccDiscount(MccDiscount MccDiscount) {
		return getSqlSession().insert("MccDiscount.addMccDiscount", MccDiscount);
	}

	@Override
	public int deleteMccDiscountByMccTypeId(int id) {
		return getSqlSession().delete("MccDiscount.deleteMccDiscountByMccTypeId", id);
	}

}
