package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.exception.PageException;

import com.chinaebi.dao.ErrorHandlingDao;
import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.service.ErrorHandlingService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "errorHandlingService")
public class ErrorHandlingServiceImpl implements ErrorHandlingService {

	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	@Qualifier(value = "errorHandlingDao")
	private ErrorHandlingDao errorHandlingDao;
	
	@Override
	public Page<ErrorHandling> queryPageErrorHandling(Page<ErrorHandling> page,
			Map<String, Object> map) {
		Page<ErrorHandling> pageList = null;
		try {
			pageList = errorHandlingDao.queryPageErrorHandling(page, map);
			if(pageList.getResult() == null){
				throw new PageException("errorHandlingDao.queryPageErrorHandling(page,map) 查询数据为NULL");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public boolean deleteErrorHandling(Integer id) {
		try {
			int flag = errorHandlingDao.deleteErrorHandling(id);
			if(flag > 0){
				return true;
			}else{
				logger.error(id + "errorHandlingDao.deleteErrorHandling 删除失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean updateErrorHandling(ErrorHandling errorHandling) {
		try {
			int flag = errorHandlingDao.updateErrorHandling(errorHandling);
			if(flag > 0){
				return true;
			}else{
				logger.error(errorHandling.getId() + "errorHandlingDao.updateErrorHandling 修改失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean addErrorHandling(ErrorHandling handling_name) {
		try {
			int flag = errorHandlingDao.addErrorHandling(handling_name);
			if(flag > 0){
				return true;
			}else{
				logger.error(handling_name.getHandling_name() + "errorHandlingDao.addErrorHandling 添加失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public List<ErrorHandling> getErrorHandleList() {
		List<ErrorHandling> list = null;
		try {
			list = errorHandlingDao.getErrorHandleList();
			if(list == null ){
				logger.error("errorHandlingDao.getErrorHandleList 获取差错处理方式列表数据为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	@Override
	public ErrorHandling getErrorHandle(Integer id) {
		ErrorHandling errorHandling = null;
		try {
			Object obj = errorHandlingDao.getErrorHandle(id);
			if(obj == null ){
				logger.error("errorHandlingDao.getErrorHandle 获取差错处理方式数据为空");
			}else{
				errorHandling = (ErrorHandling)obj;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return errorHandling;
	}

}
