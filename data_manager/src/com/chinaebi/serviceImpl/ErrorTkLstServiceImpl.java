package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.ErrorTkLstDao;
import com.chinaebi.service.ErrorTkLstService;

@Service(value = "errorTkLstService")
public class ErrorTkLstServiceImpl implements ErrorTkLstService {
	private static final Logger logger = LoggerFactory.getLogger(ErrorTkLstServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "errorTkLstDao")
	private ErrorTkLstDao errorTkLstDao;
	
	@Override
	public int addErrorTkLst(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = errorTkLstDao.addErrorTkLst(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

}
