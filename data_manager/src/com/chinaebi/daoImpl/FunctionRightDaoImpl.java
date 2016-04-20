package com.chinaebi.daoImpl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.FunctionRightDao;
import com.chinaebi.entity.FunctionRight;
import com.chinaebi.utils.mybaits.MyBatisDao;

@Component(value = "functionRightDao")
public class FunctionRightDaoImpl extends MyBatisDao 
		implements FunctionRightDao {

	@SuppressWarnings("unchecked")
	public List<FunctionRight> queryAll() {
		return getSqlSession().selectList("FunctionRight.queryAll");
	}

	@SuppressWarnings("unchecked")
	public List<FunctionRight> queryByLoginId(int loginId) {
		return getSqlSession().selectList("FunctionRight.queryByLoginId",loginId);
	}

	@SuppressWarnings("unchecked")
	public List<FunctionRight> queryByParentId(int parentId) {		
		return getSqlSession().selectList("FunctionRight.queryByParentId",parentId);
	}
	/**
	 * 查询机构所有权限
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FunctionRight> queryAllXsdl() {
		return getSqlSession().selectList("FunctionRight.queryAllXsdl");
	}
}
