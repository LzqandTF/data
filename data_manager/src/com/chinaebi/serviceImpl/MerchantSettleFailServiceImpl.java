package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.MerchantSettleFailDao;
import com.chinaebi.entity.MerchantSettleFail;
import com.chinaebi.exception.SelectException;
import com.chinaebi.service.MerchantSettleFailService;
import com.chinaebi.utils.mybaits.Page;

@Service(value="merchantSettleFailService")
public class MerchantSettleFailServiceImpl implements MerchantSettleFailService{

	private static final Logger log = LoggerFactory.getLogger(MerchantSettleFailServiceImpl.class);
	
	@Autowired
	@Qualifier(value="merchantSettleFailDao")
	private MerchantSettleFailDao merchantSettleFailDao;
	
	@Override
	public Page<MerchantSettleFail> queryPageMerchantSettleFail(
			Page<MerchantSettleFail> page, Map<String, Object> map) {
		Page<MerchantSettleFail> page_ = null;
		try{
			page_ = merchantSettleFailDao.queryPageMerchantSettleFail(page, map);
			if(page_ == null){
				throw new SelectException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}
	
	@Override
	public List<MerchantSettleFail> queryMerchantSettleFailList(Map<String,Object> map){
		List<MerchantSettleFail> list = null;
		try{
			list = merchantSettleFailDao.queryMerchantSettleFailList(map);
			if(list == null){
				throw new SelectException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public boolean addMerchantSettleFail(MerchantSettleFail merchantSettleFail) {
		try{
			int result = merchantSettleFailDao.addMerchantSettleFail(merchantSettleFail);
			if(result > 0){
				return true;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return false;
	}

}
