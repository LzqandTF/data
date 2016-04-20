package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.SettleMerchantConfigDao;
import com.chinaebi.entity.SettleMerchantConfig;
import com.chinaebi.service.SettleMerchantConfigService;
import com.chinaebi.utils.mybaits.Page;

@Service(value="settleMerchantConfigService")
public class SettleMerchantConfigServiceImpl implements SettleMerchantConfigService{
	
	private static final Logger log = LoggerFactory.getLogger(SettleMerchantConfigServiceImpl.class);
	
	@Autowired
	@Qualifier(value="settleMerchantConfigDao")
	private SettleMerchantConfigDao settleMerchantConfigDao;
	
	@Override
	public Page<SettleMerchantConfig> queryPageSettleMerchantConfig(
			Page<SettleMerchantConfig> page, Map<String, Object> map) {
		Page<SettleMerchantConfig> page_ = null;
		try{
			page_ = settleMerchantConfigDao.queryPageSettleMerchantConfig(page, map);
			if(page_ == null || page_.getResult() == null){
				log.debug("分页查询结算商户对应配置结果为空！");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public int insertSettleMerchantConfig(SettleMerchantConfig settleMerchantConfig) {
		int result = 0;
		try{
			result = settleMerchantConfigDao.insertSettleMerchantConfig(settleMerchantConfig);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteSettleMerchantConfig(String settle_mer_code) {
		int result = 0;
		try{
			result = settleMerchantConfigDao.deleteSettleMerchantConfig(settle_mer_code);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int queryConfigCountBySettleMerCode(String settle_mer_code) {
		int result = 0;
		try{
			result = settleMerchantConfigDao.queryConfigCountBySettleMerCode(settle_mer_code);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

}
