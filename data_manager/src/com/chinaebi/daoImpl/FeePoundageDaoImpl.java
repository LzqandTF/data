package com.chinaebi.daoImpl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.FeePoundageDao;
import com.chinaebi.entity.FeePoundage;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value="feePoundageDao")
public class FeePoundageDaoImpl extends MyBatisDao implements FeePoundageDao{

	@Override
	public List<FeePoundage> queryFeePoundage() {
		return getSqlSession().selectList("FeePoundage.queryFeePoundage");
	}

}
