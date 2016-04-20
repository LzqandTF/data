package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.ErrorHandlingDao;
import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "errorHandlingDao")
public class ErrorHandlingDaoImpl extends MyBatisDao implements ErrorHandlingDao {

	@Override
	public Page<ErrorHandling> queryPageErrorHandling(Page<ErrorHandling> page,
			Map<String, Object> map) {
		
		return selectPage(page, "ErrorHandling.queryPageErrorHandling",
				"ErrorHandling.queryPageCount", map);
	}

	@Override
	public int deleteErrorHandling(Integer id) {
		return getSqlSession().delete("ErrorHandling.deleteErrorHandling", id);
	}

	@Override
	public int updateErrorHandling(ErrorHandling errorHandling) {
		return getSqlSession().update("ErrorHandling.updateErrorHandling", errorHandling);
	}

	@Override
	public int addErrorHandling(ErrorHandling handling_name) {
		return getSqlSession().insert("ErrorHandling.addErrorHandling", handling_name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ErrorHandling> getErrorHandleList() {
		return getSqlSession().selectList("ErrorHandling.getErrorHandleList");
	}

	@Override
	public Object getErrorHandle(Integer id) {
		return getSqlSession().selectOne("ErrorHandling.getErrorHandle",id);
	}

}
