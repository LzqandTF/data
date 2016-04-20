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

import com.chinaebi.dao.MerFundStanceDao;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.service.MerFundStanceService;
import com.chinaebi.utils.mybaits.Page;


@Service(value="merFundStanceService")
public class MerFundStanceServiceImpl implements MerFundStanceService{
	
	@Autowired
	@Qualifier(value = "merFundStanceDao")
	private MerFundStanceDao merFundStanceDao;
	
	private static final Logger log =LoggerFactory.getLogger(MerFundStanceServiceImpl.class);

	@Override
	public Page<MerFundStance> queryPageMerFundStance(Page<MerFundStance> page,Map<String, Object> map) {
		Page<MerFundStance> page_result = null;
		try{
			page_result = merFundStanceDao.queryPageMerFundStance(page, map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_result;
	}

	@Override
	public int insertMerFundStance(MerFundStance merFundStance) {
		int result = 0;
		try{
			result = merFundStanceDao.insertMerFundStance(merFundStance);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int updateMerFundStance(MerFundStance merFundStance) {
		int result = 0;
		try{
			result = merFundStanceDao.updateMerFundStance(merFundStance);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public double queryLastAccountAmount(String mer_code) {
		double result = 0d;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("mer_code",mer_code);
			String account_amount = merFundStanceDao.queryLastAccountAmount(map);
			result = StringUtils.isNotBlank(account_amount)?Double.valueOf(account_amount):0d;
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public List<MerFundStance> queryMerFundStanceList(Map<String, Object> map) {
		List<MerFundStance> list = null;
		try{
			list = merFundStanceDao.queryMerFundStanceList(map);
			if(list == null){
				log.debug("查询商户资金流水信息集合为空");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

}
