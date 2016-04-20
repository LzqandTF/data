package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.InnerErrorHandlingDao;
import com.chinaebi.entity.InnerErrorHandling;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "innerErrorHandlingDao")
public class InnerErrorHandlingDaoImpl extends MyBatisDao implements InnerErrorHandlingDao{

	@Override
	public Page<InnerErrorHandling> queryPageInnerErrorHandling(
			Page<InnerErrorHandling> page, Map<String, Object> map) {
		return selectPage(page, "InnerErrorHandling.queryPageInnerErrorHandling",
				"InnerErrorHandling.queryPageCount", map);
	}

	@Override
	public int deleteErrorHandling(int id) {
		return getSqlSession().delete("InnerErrorHandling.deleteInnerErrorHandling", id);
	}

	@Override
	public int updateErrorHandling(InnerErrorHandling innerErrorHandling) {
		return getSqlSession().update("InnerErrorHandling.updateInnerErrorHandling", innerErrorHandling);
	}

	@Override
	public int addErrorHandling(InnerErrorHandling innerErrorHandling) {
		return getSqlSession().insert("InnerErrorHandling.addInnerErrorHandling", innerErrorHandling);
	}

	@Override
	public List<InnerErrorHandling> queryInnerErrorHandlingAll() {
		return getSqlSession().selectList("InnerErrorHandling.queryInnerErrorHandlingAll");
	}

	@Override
	public InnerErrorHandling queryInnerErrorHandling(int id) {
		return (InnerErrorHandling)getSqlSession().selectOne("InnerErrorHandling.queryInnerErrorHandling", id);
	}

}
