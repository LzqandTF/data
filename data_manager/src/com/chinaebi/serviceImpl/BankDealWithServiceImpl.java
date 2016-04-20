package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chinaebi.dao.BankDealWithDao;
import com.chinaebi.entity.BankDealWith;
import com.chinaebi.service.BankDealWithService;
import com.chinaebi.utils.mybaits.Page;


@Service(value = "bankDealWithService")
public class BankDealWithServiceImpl implements BankDealWithService{
	
	@Autowired
	private BankDealWithDao bankDealWithDao;

	private static Logger log = LoggerFactory.getLogger(BankDealWithServiceImpl.class);
	
	@Override
	public List<BankDealWith> queryInstInfo(){
		List<BankDealWith> bankDealWiths=null;
		try {
			bankDealWiths=bankDealWithDao.queryInstInfo();
			if(bankDealWiths==null){
				log.warn("查询线上、线下数据为空!!!");
			}
		} catch (Exception e) {
			log.error("查询线上、线下数据"+e.getMessage());
		}
		return bankDealWiths;
	}

	@Override
	public List<BankDealWith> queryListCountInfo(Map<String, Object> map){
		List<BankDealWith> with=null;
		try {
			with=bankDealWithDao.queryListCountInfo(map);
			if(with==null){
				log.warn("查询线上、线下数据总和为空!!!");
			}
		} catch (Exception e) {
			log.error("查询线上、线下数据总和"+e.getMessage());
		}
		return with;
	}

	@Override
	public List<BankDealWith> queryListCountInfoExcel(Map<String, Object> map){
		List<BankDealWith> list=null;
		try {
			list=bankDealWithDao.queryListCountInfoExcel(map);
			if(list==null){
				log.warn("下载应收银行交易款excel表数据为空!!!");
			}
		} catch (Exception e) {
			log.error("下载应收银行交易款excel表数据"+e.getMessage());
		}
		return list;
	}

	@Override
	public List<BankDealWith> queryListCountInfoSum(Map<String, Object> map){
		List<BankDealWith> listwith=null;
		try {
			listwith=bankDealWithDao.queryListCountInfoExcel(map);
			if(listwith==null){
				log.warn("统计所有交易数据的和为空!!!");
			}
		} catch (Exception e) {
			log.error("统计所有交易数据的和"+e.getMessage());
		}
		return listwith;
	}

	@Override
	public Page<BankDealWith> queryTradingList(Page<BankDealWith> page,Map<String, Object> map) {
		Page<BankDealWith> listwith=null;
		try {
			listwith=bankDealWithDao.queryTradingList(page,map);
			if(listwith==null){
				log.warn("查询交易数据为空!!!");
			}
		} catch (Exception e) {
			log.error("查询交易数据"+e.getMessage());
		}
		return listwith;
	}

	@Override
	public String queryMerName(String mercode) {
		String mabbreviation=null;
		try {
			mabbreviation=bankDealWithDao.queryMerName(mercode);
			if(mabbreviation.equals("")){
				log.warn("查询商户简称为空!!!");
			}
		} catch (Exception e) {
			log.error("查询商户简称"+e.getMessage());
		}
		return mabbreviation;
	}

	@Override
	public String queryMernameOne(String mercode) {
		String mabbreviationOne=null;
		try {
			mabbreviationOne=bankDealWithDao.queryMernameOne(mercode);
			if(mabbreviationOne.equals("")){
				log.warn("查询商户简称为空!!!");
			}
		} catch (Exception e) {
			log.error("查询商户简称"+e.getMessage());
		}
		return mabbreviationOne;
	}

	@Override
	public BankDealWith querySumTradingList(Map<String, Object> map) {
		BankDealWith bWith=null;
		try {
			bWith=bankDealWithDao.querySumTradingList(map);
			if(bWith==null){
				log.warn("查询表数据为空!!!");
			}
		} catch (Exception e) {
			log.error("查询数据和:"+e.getMessage());
		}
		return bWith;
	}

}
