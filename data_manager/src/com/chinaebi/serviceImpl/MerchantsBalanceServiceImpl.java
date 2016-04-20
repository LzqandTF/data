package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.MerchantsBalanceDao;
import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.exception.PageException;
import com.chinaebi.service.MerchantsBalanceService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "merchantsBalanceService")
public class MerchantsBalanceServiceImpl implements MerchantsBalanceService{
	
	private static Logger log = LoggerFactory.getLogger(MerchantsBalanceServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "merchantsBalanceDao")
	private MerchantsBalanceDao merchantsBalanceDao;
	
	@Override
	public Page<MerchantsBalance> queryPageMerchantsBalanceList(Page<MerchantsBalance> page, Map<String, Object> map) {
		Page<MerchantsBalance> pageList=null;
		try {
			pageList=merchantsBalanceDao.queryPageMerchantsBalanceList(page, map);
			if(pageList.getResult()==null){
				throw new PageException("merchantsBalanceDao.queryPageMerchantsBalanceList(page, map)  查询结果为NULL");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public MerchantsBalance queryMerBalanceByMerCode(String mer_code) {
		MerchantsBalance merchantsBalance = null;
		try{
			merchantsBalance = merchantsBalanceDao.queryMerBalanceByMerCode(mer_code);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return merchantsBalance;
	}

	@Override
	public int addMerchantsBalance(MerchantsBalance merchantsBalance) {
		int result = 0;
		try{
			result = merchantsBalanceDao.addMerchantsBalance(merchantsBalance);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int updateMerchantsBalance(MerchantsBalance merchantsBalance) {
		int result = 0;
		try{
			result = merchantsBalanceDao.updateMerchantsBalance(merchantsBalance);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

}
