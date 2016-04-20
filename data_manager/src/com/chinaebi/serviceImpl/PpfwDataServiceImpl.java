package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.PpfwDataDao;
import com.chinaebi.entity.PpfwData;
import com.chinaebi.service.PpfwDataService;
import com.chinaebi.utils.mybaits.Page;


@Service(value="ppfwDataService")
public class PpfwDataServiceImpl implements PpfwDataService{
	
	private static final Logger log = LoggerFactory.getLogger(PpfwDataServiceImpl.class);
	
	@Autowired
	@Qualifier(value="ppfwDataDao")
	private PpfwDataDao ppfwDataDao;

	@Override
	public Page<PpfwData> queryPpfwData(Page<PpfwData> page,Map<String, Object> map) {
		Page<PpfwData> page_ = null;
		try{
			page_ = ppfwDataDao.queryPpfwData(page, map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public double getPpfwFeeTotalCount(Map<String, Object> map) {
		double result = 0.00d;
		try{
			result = ppfwDataDao.getPpfwFeeTotalCount(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public double queryPagePpfwDataTotalCount(Map<String, Object> map) {
		double result = 0.00d;
		try{
			result = ppfwDataDao.queryPagePpfwDataTotalCount(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

}
