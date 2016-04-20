package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.utils.mybaits.Page;

public interface ErrorHandlingDao {

	/**
	 * 分页查询数据
	 * @param page 分页参数
	 * @param map 查询参数
	 * @return
	 */
	public abstract Page<ErrorHandling> queryPageErrorHandling(
			Page<ErrorHandling> page, Map<String, Object> map);
	
	
	int deleteErrorHandling(Integer id);
	
	int updateErrorHandling(ErrorHandling errorHandling);
	
	int addErrorHandling(ErrorHandling handling_name);
	
	List<ErrorHandling> getErrorHandleList();
	
	Object getErrorHandle(Integer id);
}
