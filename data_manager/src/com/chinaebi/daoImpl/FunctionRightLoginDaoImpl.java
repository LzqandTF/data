package com.chinaebi.daoImpl;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.FunctionRightLoginDao;
import com.chinaebi.entity.FunctionRightLogin;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "functionRightLoginDao")
public class FunctionRightLoginDaoImpl extends MyBatisDao
		implements FunctionRightLoginDao {

	public int deleteByLoginId(int loginId) {
		return getSqlSession().delete("FunctionRightLogin.deleteByLoginId",loginId);
	}

	public int addFunctionRightLogin(FunctionRightLogin f) {
		return getSqlSession().insert("FunctionRightLogin.addFunctionRightLogin",f);
	}

}
