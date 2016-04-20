package com.chinaebi.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.FunctionRightLoginDao;
import com.chinaebi.entity.FunctionRightLogin;
import com.chinaebi.service.FunctionRightLoginService;
import com.chinaebi.utils.StringUtils;

@Service(value = "functionRightLoginService")
public class FunctionRightLoginServiceImpl implements FunctionRightLoginService {

	@Autowired
	@Qualifier(value = "functionRightLoginDao")
	private FunctionRightLoginDao functionRightLoginDao;
	
	public int deleteByLoginId(int loginId) {
		return functionRightLoginDao.deleteByLoginId(loginId);
	}

	public int addFunctionRightLogin(String funcId, String loginId) {
		FunctionRightLogin f = new FunctionRightLogin();
		if(StringUtils.isNotBlank(funcId) && StringUtils.isNotBlank(loginId)){
			f.setFuncId(Integer.parseInt(funcId.trim()));
			f.setLoginId(Integer.parseInt(loginId.trim()));
		}
		return functionRightLoginDao.addFunctionRightLogin(f);
	}

}
