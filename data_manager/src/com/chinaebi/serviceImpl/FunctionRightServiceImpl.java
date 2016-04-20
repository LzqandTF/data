package com.chinaebi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.FunctionRightDao;
import com.chinaebi.entity.FunctionRight;
import com.chinaebi.service.FunctionRightService;

@Service(value ="functionRightService")
public class FunctionRightServiceImpl implements FunctionRightService {

	@Autowired
	@Qualifier(value = "functionRightDao")
	private FunctionRightDao functionRightDao;
	
	public List<FunctionRight> queryAll() {
 		return functionRightDao.queryAll();
	}

	public List<FunctionRight> queryByLoginId(int loginId) {
		return functionRightDao.queryByLoginId(loginId);
	}

	public List<FunctionRight> queryByParentId(int parentId) {
		return functionRightDao.queryByParentId(parentId);
	}
	/**
	 * @return
	 */
	public List<FunctionRight> queryAllXsdl(){
		return functionRightDao.queryAllXsdl();
	}
}
