package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.SettleMerchantMatchDao;
import com.chinaebi.entity.SettleMerchantMatch;
import com.chinaebi.service.SettleMerchantMatchService;

@Service(value="settleMerchantMatchService")
public class SettleMerchantMatchServiceImpl implements SettleMerchantMatchService{
	
	private static final Logger log = LoggerFactory.getLogger(SettleMerchantMatchServiceImpl.class);

	@Autowired
	@Qualifier(value="settleMerchantMatchDao")
	private SettleMerchantMatchDao settleMerchantMatchDao;
	
	@Override
	public int querySettleMerchantMatchCount(Map<String, Object> map) {
		int result = 0;
		try{
			result = settleMerchantMatchDao.querySettleMerchantMatchCount(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int insertSettleMerchantMatch(SettleMerchantMatch settleMerchantMatch) {
		int result = 0;
		try{
			result = settleMerchantMatchDao.insertSettleMerchantMatch(settleMerchantMatch);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public List<SettleMerchantMatch> querySettleMerchantMatch(Map<String, Object> map) {
		List<SettleMerchantMatch> list = null;
		try{
			list = settleMerchantMatchDao.querySettleMerchantMatch(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int deleteSettleMerchantMatchBySettleMerCode(String settle_mer_code) {
		int result = 0;
		try{
			result = settleMerchantMatchDao.deleteSettleMerchantMatchBySettleMerCode(settle_mer_code);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteSettleMerchantMatchBySettleMerCodeAndDyCode(String settle_mer_code,String dy_mer_code) {
		int result = 0;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(settle_mer_code)){
				map.put("settle_mer_code", settle_mer_code);
			}
			if(StringUtils.isNotBlank(dy_mer_code)){
				map.put("dy_mer_code", dy_mer_code);
			}
			
			result = settleMerchantMatchDao.deleteSettleMerchantMatchBySettleMerCodeAndDyCode(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public String querySettleMerCodeByDyMerCode(String dy_mer_code) {
		String settle_mer_code = "";
		try{
			settle_mer_code = settleMerchantMatchDao.querySettleMerCodeByDyMerCode(dy_mer_code);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return settle_mer_code;
	}

	@Override
	public int updateDyMerCode(Map<String, Object> map) {
		int result = 0;
		try{
			result = settleMerchantMatchDao.updateDyMerCode(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

}
