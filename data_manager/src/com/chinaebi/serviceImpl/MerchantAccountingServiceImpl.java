package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.MerchantAccountingDao;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.service.MerchantAccountingService;
import com.chinaebi.utils.mybaits.Page;


@Service(value = "merchantAccountingService")
public class MerchantAccountingServiceImpl implements MerchantAccountingService {
	
	
	@Autowired
	private MerchantAccountingDao merchantAccountingDao;
	
	private static Logger log = LoggerFactory.getLogger(MerchantAccountingServiceImpl.class);

	@Override
	public Page<MerFundStance> queryPageMerchantAccounting(Page<MerFundStance> page,Map<String, Object> map) {
		Page<MerFundStance> merfun=null;
		try {
			merfun=merchantAccountingDao.queryPageMerchantAccounting(page,map);
			if(merfun==null){
				log.warn("根据条件查询商户账户信息数据为空!!!");
			}
		} catch (Exception e) {
			log.error(""+e.getMessage());
		}
		return merfun;
	}
	
	@Override
	public List<MerFundStance> queryMerchantAccountingList(Map<String, Object> map) {
		List<MerFundStance> list=null;
		try {
			list=merchantAccountingDao.queryMerchantAccountingList(map);
			if(list.size()==0){
				log.warn("根据条件查询商户账户信息数据为空!!!");
			}
		} catch (Exception e) {
			log.error(""+e.getMessage());
		}
		return list;
	}
}
