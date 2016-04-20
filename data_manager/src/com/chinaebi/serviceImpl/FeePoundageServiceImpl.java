package com.chinaebi.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.FeePoundageDao;
import com.chinaebi.entity.FeePoundage;
import com.chinaebi.service.FeePoundageService;

@Service(value="feePoundageService")
public class FeePoundageServiceImpl implements FeePoundageService{
	
	private static final Logger log = LoggerFactory.getLogger(FeePoundageServiceImpl.class);

	@Autowired
	@Qualifier(value="feePoundageDao")
	private FeePoundageDao feePoundageDao;
	
	@Override
	public List<FeePoundage> queryFeePoundage() {
		List<FeePoundage> list = null;
		try{
			list = feePoundageDao.queryFeePoundage();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

}
