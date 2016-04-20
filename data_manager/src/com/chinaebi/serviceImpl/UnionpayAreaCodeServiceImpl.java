package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.UnionpayAreaCodeDao;
import com.chinaebi.entity.UnionpayAreaCode;
import com.chinaebi.service.UnionpayAreaCodeService;

@Service(value = "unionpayAreaCodeService")
public class UnionpayAreaCodeServiceImpl  implements UnionpayAreaCodeService{
	private static final Logger logger = LoggerFactory.getLogger(UnionpayAreaCodeServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "unionpayAreaCodeDao")
	private UnionpayAreaCodeDao unionpayAreaCodeDao;
	
	@Override
	public UnionpayAreaCode queryAreaNameByProvince(String provice) {
		UnionpayAreaCode unionpayAreaCode = null;
		try {
			unionpayAreaCode = unionpayAreaCodeDao.queryAreaNameByProvince(provice);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return unionpayAreaCode;
	}

	@Override
	public UnionpayAreaCode queryAreaNameByProvinceAndCity(Map<String, Object> map) {
		UnionpayAreaCode unionpayAreaCode = null;
		try {
			unionpayAreaCode = unionpayAreaCodeDao.queryAreaNameByProvinceAndCity(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return unionpayAreaCode;
	}

}
