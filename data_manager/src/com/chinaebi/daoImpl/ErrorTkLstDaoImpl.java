package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ErrorTkLstDao;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "errorTkLstDao")
public class ErrorTkLstDaoImpl extends MyBatisDao implements ErrorTkLstDao {

	@Override
	public int addErrorTkLst(Map<String, Object> map) {
		return getSqlSession().insert("ErrorTkLst.addErrorTkLst", map);
	}

}
