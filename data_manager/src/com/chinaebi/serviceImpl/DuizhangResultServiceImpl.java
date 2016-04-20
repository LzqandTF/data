package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.DuizhangResultDao;
import com.chinaebi.entity.OriginalData;
import com.chinaebi.service.DuizhangResultService;

@Service(value = "duizhangResultService")
public class DuizhangResultServiceImpl implements DuizhangResultService {
	
	private static final Logger log = LoggerFactory.getLogger(DuizhangResultServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "duizhangResultDao")
	private DuizhangResultDao duizhangResultDao;
	
	@Override
	public Map<String, Object> proceDuizhangResult(Map<String, Object> paramMap) {
		Map<String, Object> map = duizhangResultDao.proceDuizhangResult(paramMap);
		return map;
	}

	@Override
	public Map<String, Object> proceErrorDuizhangResult(Map<String, Object> paramMap) {
		Map<String, Object> map = duizhangResultDao.proceErrorDuizhangResult(paramMap);
		return map;
	}

	@Override
	public Map<String, Object> proceRytUpmpDzResult(Map<String, Object> map) {
		Map<String, Object> resultMap = duizhangResultDao.proceRytUpmpDzResult(map);
		return resultMap;
	}

	@Override
	public Map<String, Object> proceRytUpmpErrorResult(Map<String, Object> map) {
		Map<String, Object> resultMap = duizhangResultDao.proceRytUpmpErrorResult(map);
		return resultMap;
	}

	@Override
	public List<OriginalData> queryChannelDzResultDataLst(Map<String, Object> map) {
		List<OriginalData> list = null;
		try{
			list = duizhangResultDao.queryChannelDzResultDataLst(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
}

