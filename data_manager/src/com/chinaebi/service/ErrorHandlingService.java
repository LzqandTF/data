package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.utils.mybaits.Page;

public interface ErrorHandlingService {

	/**
	 * 分页查询数据
	 * @param page
	 * @param map
	 * @return
	 */
	Page<ErrorHandling> queryPageErrorHandling(
			Page<ErrorHandling> page, Map<String, Object> map);
	
	
	boolean deleteErrorHandling(Integer id);
	
	boolean updateErrorHandling(ErrorHandling errorHandling);
	
	boolean addErrorHandling(ErrorHandling handling_name);
	
	List<ErrorHandling> getErrorHandleList();
	
	ErrorHandling getErrorHandle(Integer id);
}
