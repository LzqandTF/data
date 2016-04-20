package com.chinaebi.serviceImpl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.RefundLogDao;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.RytRefundLog;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.service.RefundLogService;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "refundLogService")
public class RefundLogServiceImpl implements RefundLogService {
	private static final Logger log = LoggerFactory.getLogger(RefundLogServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "refundLogDao")
	private RefundLogDao refundLogDao;
	
	@Autowired
	@Qualifier(value = "merchantsService")
	private MerchantsService merchantsService;
	
	//从内存中加载各种配置列表
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;

	@Override
	public Page<RytRefundLog> queryPageOnlineTkDataLst(Page<RytRefundLog> page,Map<String, Object> map) {
		Page<RytRefundLog> pageList = null;
		try {
			pageList = refundLogDao.queryPageOnlineTkDataLst(page, map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<RytRefundLog> queryOnlineTkDataLstForExcel(Map<String, Object> map) {
		List<RytRefundLog> list = null;
		try {
			list = refundLogDao.queryOnlineTkDataLstForExcel(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public Page<RytRefundLog> queryPageTkCheckDataLst(Page<RytRefundLog> page,Map<String, Object> map) {
		Page<RytRefundLog> pageList = null;
		try {
			pageList = refundLogDao.queryPageTkCheckDataLst(page, map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<RytRefundLog> queryTkCheckDataLstForExcel(Map<String, Object> map) {
		List<RytRefundLog> list = null;
		try {
			list = refundLogDao.queryTkCheckDataLstForExcel(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public RytRefundLog queryTkMoney(Map<String, Object> map) {
		RytRefundLog rytRefundLog = null;
		try {
			rytRefundLog = refundLogDao.queryTkMoney(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return rytRefundLog;
	}
	
	@Override
	public Page<RytRefundLog> queryPageRefundData(Page<RytRefundLog> page,Map<String, Object> map) {
		Page<RytRefundLog> page_ = null;
		try{
			page_ = refundLogDao.queryPageRefundData(page, map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public List<RytRefundLog> queryRedundDataList(Map<String, Object> map) {
		List<RytRefundLog> list = null;
		try{
			list = refundLogDao.queryRedundDataList(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public RytRefundLog queryRedundDataDetail(String id) {
		RytRefundLog refundLog = null;
		try{
			refundLog = refundLogDao.queryRedundDataDetail(id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return refundLog;
	}

	@Override
	public Page<RytRefundLog> queryPageRedundHandleData(Page<RytRefundLog> page, Map<String, Object> map) {
		Page<RytRefundLog> page_ = null;
		try{
			page_ = refundLogDao.queryPageRedundHandleData(page, map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public List<RytRefundLog> queryRedundHandleDataList(Map<String, Object> map) {
		List<RytRefundLog> list = null;
		try{
			list = refundLogDao.queryRedundHandleDataList(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public RytRefundLog queryPageRefundAmount(Map<String, Object> map) {
		RytRefundLog refundLog = null;
		try{
			refundLog = refundLogDao.queryPageRefundAmount(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return refundLog;
	}

	@Override
	public RytRefundLog queryPageRedundHandleAmount(Map<String, Object> map) {
		RytRefundLog refundLog = null;
		try{
			refundLog = refundLogDao.queryPageRedundHandleAmount(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return refundLog;
	}


	@Override
	public List<RytRefundLog> queryRefundLogDataInfoById(String ids) {
		List<RytRefundLog> list = null;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", ids);
			list = refundLogDao.queryRefundLogDataInfoById(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int updateRefundLogData(RytRefundLog refund) {
		try{
			Calendar calendar = Calendar.getInstance();
			int pro_date = Integer.valueOf(DateUtil.formatDate(calendar.getTime(), DataStatus.date_format_2));
			calendar.add(Calendar.YEAR, -1);
			int minDate = Integer.valueOf(DateUtil.formatDate(calendar.getTime(), DataStatus.date_format_2));
			
			if(refund.getSys_date() >= minDate){
				InstInfo instInfo = dataManagerInit.getInstInfoById(refund.getGid(), DataStatus.instType_1);
				int result = merchantsService.updateMerchantFundStanceAndBalanceAndStatistics(refund.getMid(), instInfo.getBank_id(), instInfo.getInstId(), instInfo.getInst_type(), 
						refund.getRef_amt(), refund.getMer_fee(),0.00d,0.00d, pro_date, refund.getId()+"", DataStatus.REFUND, DataStatus.TK_YES,DataStatus.TK_PRO);
				
				if(result == 0){
					Map<String,Object> map_refund = new HashMap<String,Object>();
					
					map_refund.put("id", refund.getId());
					map_refund.put("pro_date",pro_date);
					
					refundLogDao.updateRefundDataHandelSucess(map_refund);
					
					return 1;
				}else if(result == 2){
					return 3;
				}else{
					return 0;
				}
			}else{
				return 2;//退款不在有效时间范围内
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new RuntimeException();
		}
	}
	
	@Override
	public synchronized String updateRefundLogData(int operType,Map<String,Object> map){
		StringBuffer result = new StringBuffer("");
		
		int totalCount = 0;
		int sucessCount = 0;
		int failCount = 0;
		int alreadyHandleCount = 0;
		StringBuffer balanceNotEnough = new StringBuffer("");
		StringBuffer interfaceFail = new StringBuffer("");
		StringBuffer outOfDate = new StringBuffer("");
		StringBuffer otherInfo = new StringBuffer("");
		
		try{
			List<RytRefundLog> list = refundLogDao.queryRefundLogDataInfoById(map);
			
			if(list != null && list.size() > 0){
				if(operType == 1){//退款经办
					totalCount = list.size();
					for (RytRefundLog rytRefundLog : list) {
						if(rytRefundLog.getStat() == 2){
							alreadyHandleCount ++;
						}else{
							int effectNum = updateRefundLogData(rytRefundLog);
							if(effectNum == 1){
								sucessCount ++;
							}else if(effectNum ==2){
								failCount++;
								outOfDate.append(rytRefundLog.getId());
								outOfDate.append(",");
							}else if(effectNum == 3){
								failCount++;
								balanceNotEnough.append(rytRefundLog.getId());
								balanceNotEnough.append(",");
							}else{
								failCount++;
								otherInfo.append(rytRefundLog.getId());
								otherInfo.append(",");
							}
						}
					}
					result.append(totalCount+"");
					result.append(";");
					result.append(sucessCount+"");
					result.append(";");
					result.append(alreadyHandleCount+"");
					result.append(";");
					result.append(failCount+"");
					result.append(";");
					result.append(StringUtils.isBlank(outOfDate.toString())?"":outOfDate.toString().substring(0, outOfDate.toString().length()-1));
					result.append(";");
					result.append(StringUtils.isBlank(interfaceFail.toString())?"":interfaceFail.toString().substring(0, interfaceFail.toString().length()-1));
					result.append(";");
					result.append(StringUtils.isBlank(balanceNotEnough.toString())?"":balanceNotEnough.toString().substring(0, balanceNotEnough.toString().length()-1));
					result.append(";");
					result.append(StringUtils.isBlank(otherInfo.toString())?"":otherInfo.toString().substring(0, otherInfo.toString().length()-1));
				}else if(operType == 2){//退款撤销
					for (RytRefundLog rytRefundLog : list) {
						map.put("id", rytRefundLog.getId());
						int num = refundLogDao.updateRefundDataTkFail(map);
						result.append(num);
					}
				}else{
					log.debug("操作类型不正确,请核实!");
				}
				
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new RuntimeException();
		}
		return result.toString();
	}
	
	@Override
	public int updateRefundLogDataToRGJB(RytRefundLog refund) {
		try{
			Calendar calendar = Calendar.getInstance();
			int pro_date = Integer.valueOf(DateUtil.formatDate(calendar.getTime(), "yyyyMMdd"));
			calendar.add(Calendar.YEAR, -1);
			int minDate = Integer.valueOf(DateUtil.formatDate(calendar.getTime(), "yyyyMMdd"));
			if(refund.getSys_date() >= minDate){
				InstInfo instInfo = dataManagerInit.getInstInfoById(refund.getGid(), 1);
				int result = merchantsService.updateMerchantFundStanceAndBalanceAndStatistics(refund.getMid(), instInfo.getBank_id(), instInfo.getInstId(), instInfo.getInst_type(), 
						refund.getRef_amt(), refund.getMer_fee(),0.00d,0.00d, pro_date, refund.getId()+"", DataStatus.REFUND, 1,DataStatus.TK_PRO);
				if(result == 0){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("stat", 2);
					map.put("pro_date", pro_date);
					map.put("id", refund.getId());
					refundLogDao.updateRefundLogDataToRGJB(map);
					return 1;
				}else if(result == 2){
					return 3;
				}else{
					return 0;
				}
			}else{
				return 2;//退款不在有效时间范围内
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw new RuntimeException();
		}
	}
	
	@Override
	public int updateRefundLogDataToRGJB(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = refundLogDao.updateRefundLogDataToRGJB(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateRefundLogDataToCheckedById(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = refundLogDao.updateRefundLogDataToCheckedById(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public List<RytRefundLog> queryDataById(Map<String, Object> map) {
		List<RytRefundLog> list = null;
		try {
			list = refundLogDao.queryDataById(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int updateRefundLogDataToChexiaoById(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = refundLogDao.updateRefundLogDataToChexiaoById(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateRefundLogDataToOnlineTk(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = refundLogDao.updateRefundLogDataToOnlineTk(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}


	@Override
	public RytRefundLog queryOnlineTkTotalMoney(Map<String, Object> map) {
		RytRefundLog rytRefundLog = null;
		try {
			rytRefundLog = refundLogDao.queryOnlineTkTotalMoney(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return rytRefundLog;
	}

}
