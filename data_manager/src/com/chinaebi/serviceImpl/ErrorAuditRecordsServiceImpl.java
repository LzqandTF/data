package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.ErrorAuditRecordsDao;
import com.chinaebi.entity.ErrorAuditRecords;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.service.ErrorAuditRecordsService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "errorAuditRecordsService")
public class ErrorAuditRecordsServiceImpl implements ErrorAuditRecordsService {
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	@Qualifier(value = "errorAuditRecordsDao")
	private ErrorAuditRecordsDao errorAuditRecordsDao;
	
	@Override
	public Page<ErrorAuditRecords> queryPageErrorAuditRecords(
			Page<ErrorAuditRecords> page, Map<String, Object> map) {
		Page<ErrorAuditRecords> pageList = null;
		try {
			pageList = errorAuditRecordsDao.queryPageErrorAuditRecords(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("errorAuditRecordsDao.queryPageErrorAuditRecords(page, map)  查询数据为NULL");
			}
		} catch (Exception e) {
			logger.error("查询差错审计数据出错：" + e.getMessage());
		}
		return pageList;
	}

	@Override
	public ErrorAuditRecords queryDetail(Map<String, Object> map) {
		ErrorAuditRecords errorAuditRecords = null;
		try {
			errorAuditRecords = errorAuditRecordsDao.queryDetail(map);
			if (errorAuditRecords == null) {
				throw new SelectException("errorAuditRecordsDao.queryDetail(map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("查询差错审计详情数据出错：" + e.getMessage());
		}
		return errorAuditRecords;
	}

	@Override
	public int addErrorAuditRecords(ErrorAuditRecords errorAuditRecords) {
		int effectNum = 0;
		try {
			effectNum = errorAuditRecordsDao.addErrorAuditRecords(errorAuditRecords);
			if (effectNum == 0) {
				logger.error("errorAuditRecordsDao.addErrorAuditRecords(errorAuditRecords)  差错审计数据保存失败");
			}
		} catch (Exception e) {
			logger.error("添加差错审计数据出错：" + e.getMessage());
		}
		return effectNum;
	}

}
