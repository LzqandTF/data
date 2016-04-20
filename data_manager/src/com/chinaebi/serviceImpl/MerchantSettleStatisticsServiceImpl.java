package com.chinaebi.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.MerchantSettleStatisticsDao;
import com.chinaebi.entity.MerchantSettleStatistics;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.MerchantSettleStatisticsService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "merchantSettleStatisticsService")
public class MerchantSettleStatisticsServiceImpl implements MerchantSettleStatisticsService {
	private static Logger log = LoggerFactory.getLogger(MerchantSettleStatisticsServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "merchantSettleStatisticsDao")
	private MerchantSettleStatisticsDao merchantSettleStatisticsDao;
	
	@Override
	public Page<MerchantSettleStatistics> queryPageMerchantSettleStatistics(
			Page<MerchantSettleStatistics> page, Map<String, Object> map) {
		Page<MerchantSettleStatistics> pageList = null;
		try{
			pageList = merchantSettleStatisticsDao.queryPageMerchantSettleStatistics(pageList, map);
			if(pageList.getResult() == null){
				throw new PageException("merchantSettleStatisticsDao.queryPageMerchantSettleStatistics(pageList, map) 查询数据为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return pageList;
	}
	
	/**
	 * 以商户号分组查询可结算商户信息
	 * @param map 查询参数
	 * @return
	 */
	public MerchantSettleStatistics querySettleMerchantInfoGroupByMerCode(Map<String,Object> map){
		MerchantSettleStatistics merchantSettleStatistics = null;
		try{
			merchantSettleStatistics = merchantSettleStatisticsDao.querySettleMerchantInfoGroupByMerCode(map);
			if(merchantSettleStatistics == null){
				throw new SelectException("未查询到符合条件的可结算商户信息");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return merchantSettleStatistics;
	}
	/**
	 * 查询可结算商户信息
	 * @param map 查询参数
	 * @return
	 */
	public List<MerchantSettleStatistics> querySettleMerchantInfo(Map<String,Object> map){
		List<MerchantSettleStatistics> merchantSettleStatistics = null;
		try{
			merchantSettleStatistics = merchantSettleStatisticsDao.querySettleMerchantInfo(map);
			if(merchantSettleStatistics == null){
				throw new SelectException("未查询到符合条件的可结算商户信息");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return merchantSettleStatistics;
	}

	@Override
	public int updateMerchantSettleStatistics(Map<String, Object> map) {
		int result = 0;
		try{
			result = merchantSettleStatisticsDao.updateMerchantSettleStatistics(map);
			if(result < 0){
				throw new UpdateException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
	
	@Override
	public List<MerchantSettleStatistics> queryInstIdList(Map<String, Object> map) {
		List<MerchantSettleStatistics> list = null;
		try {
			list = merchantSettleStatisticsDao.queryInstIdList(map);
			if (list == null) {
				throw new SelectException("merchantSettleStatisticsDao.queryInstIdList(map)  查询结果为NULL");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public MerchantSettleStatistics queryMerchantSettleStatisticsInfo(Map<String, Object> map) {
		MerchantSettleStatistics result = null;
		try{
			result = merchantSettleStatisticsDao.queryMerchantSettleStatisticsInfo(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int updateMerchantSettleStatisticsInfo(MerchantSettleStatistics merchantSettleStatistics) {
		int result = 0;
		try{
			result = merchantSettleStatisticsDao.updateMerchantSettleStatisticsInfo(merchantSettleStatistics);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int addMerchantSettleStatisticsInfo(MerchantSettleStatistics merchantSettleStatistics) {
		int result = 0;
		try{
			result = merchantSettleStatisticsDao.addMerchantSettleStatisticsInfo(merchantSettleStatistics);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public Page<MerchantSettleStatistics> queryPageMSSByMerCodeAndInstId(Page<MerchantSettleStatistics> page, Map<String, Object> map) {
		Page<MerchantSettleStatistics> pageList = null;
		try {
			pageList = merchantSettleStatisticsDao.queryPageMSSByMerCodeAndInstId(page, map);
			if(pageList.getResult() == null) {
				throw new PageException("merchantSettleStatisticsDao.queryPageMSSByMerCodeAndInstId(page, map) 查询结果为NULL");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public MerchantSettleStatistics queryTotalMoney(Map<String, Object> map) {
		MerchantSettleStatistics merchantSettleStatistics = null;
		try {
			merchantSettleStatistics = merchantSettleStatisticsDao.queryTotalMoney(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return merchantSettleStatistics;
	}

	@Override
	public List<MerchantSettleStatistics> queryDataLstForExcel(Map<String, Object> map) {
		List<MerchantSettleStatistics> list = null;
		try {
			list = merchantSettleStatisticsDao.queryDataLstForExcel(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}
	
	
	@Override
	public Map<String, List<MerchantSettleStatistics>> queryDataLstByInstIdForExcel(Map<String, Object> map) {
		Map<String, List<MerchantSettleStatistics>> map_data = null;
		try {
			List<MerchantSettleStatistics> list = merchantSettleStatisticsDao.queryDataLstForExcel(map);
			if(list != null && list.size() > 0){
				map_data = new HashMap<String, List<MerchantSettleStatistics>>();
				for (MerchantSettleStatistics merchantSettleStatistics : list) {
					String name_ = merchantSettleStatistics.getName_();
					List<MerchantSettleStatistics> value_list = map_data.get(name_);
					List<MerchantSettleStatistics> list_data = null;
					if(value_list == null){
						list_data = new ArrayList<MerchantSettleStatistics>();
						list_data.add(merchantSettleStatistics);
						map_data.put(name_,list_data);
					}else{
						list_data = value_list;
						list_data.add(merchantSettleStatistics);
						map_data.put(name_, list_data);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return map_data;
	}
	
	@Override
	public Map<String, List<MerchantSettleStatistics>> queryDataLstByMerForExcel(Map<String, Object> map) {
		Map<String, List<MerchantSettleStatistics>> map_data = null;
		try {
			List<MerchantSettleStatistics> list = merchantSettleStatisticsDao.queryDataLstForExcel(map);
			if(list != null && list.size() > 0){
				map_data = new HashMap<String, List<MerchantSettleStatistics>>();
				for (MerchantSettleStatistics merchantSettleStatistics : list) {
					String mer_code = merchantSettleStatistics.getMer_code();
					List<MerchantSettleStatistics> value_list = map_data.get(mer_code);
					List<MerchantSettleStatistics> list_data = null;
					if(value_list == null){
						list_data = new ArrayList<MerchantSettleStatistics>();
						list_data.add(merchantSettleStatistics);
						map_data.put(mer_code,list_data);
					}else{
						list_data = value_list;
						list_data.add(merchantSettleStatistics);
						map_data.put(mer_code, list_data);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return map_data;
	}
}
