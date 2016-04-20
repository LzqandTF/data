package com.chinaebi.reload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.entity.InnerErrorHandling;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.Merchants;
import com.chinaebi.entity.ReasonCode;
import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.entity.TradeType;
import com.chinaebi.service.BankInstService;
import com.chinaebi.service.ErrorHandlingService;
import com.chinaebi.service.InnerErrorHandlingService;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.service.ReasonCodeDaoService;
import com.chinaebi.service.TradeAmountConfService;
import com.chinaebi.service.TradeTypeService;
import com.chinaebi.utils.StringUtils;
@Component(value = "dataManagerInit")
public class DataManagerInit {
	private static DataManagerInit instance;
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	// 银行机构配置信息
	@Autowired
	@Qualifier(value = "bankInstService")
	private BankInstService bankInstService;
	
	// 扣款渠道配置信息
	@Autowired
	@Qualifier(value = "instInfoService")
	private InstInfoService instInfoService;
	
	// 商户基本信息和商户结算信息
	@Autowired
	@Qualifier(value = "merchantsService")
	private MerchantsService merchantsService;
	
	//交易消息类型
	@Autowired
	@Qualifier(value="tradeAmountConfService")
	private TradeAmountConfService tradeAmountConfService;
	
	//差错处理方式
	@Autowired
	@Qualifier(value = "errorHandlingService")
	private ErrorHandlingService errorHandlingService;
	
	//差错处理方式原因码
	@Autowired
	@Qualifier(value = "reasonCodeDaoService")
	private ReasonCodeDaoService reasonCodeDaoService;
	
	// 交易类型
	@Autowired
	@Qualifier(value = "tradeTypeService")
	private TradeTypeService tradeTypeService;
	
	//内部差错处理方式
	@Autowired
	@Qualifier(value = "innerErrorHandlingService")
	private InnerErrorHandlingService innerErrorHandlingService;
	
	private List<BankInst> bankInstList;
	private Map<String, Object> bankInstMap;
	
	private List<InstInfo> instInfoList;
	private Map<String, Object> instInfoMap;
	
	private Map<String, Object> merBasicMap;
	
	private Map<String, Object> merBillingMap;
	
	private List<ErrorHandling> errorHandlingList;
	private Map<Integer, Object> errorHandingMap;
	
	private List<TradeAmountConf> tradeAmountConfsList;
	private Map<String, Object> tradeAmountConfsMap;
	
	private List<ReasonCode> reasonCodesList;
	private Map<String, Object> reasonCodesMap;
	
	private List<TradeType> tradeTypeList;
	private Map<String, Object> tradeTypeMap;
	
	private List<InnerErrorHandling> innerErrorHandlingList;
	private Map<Integer, Object> innerErrorHandlingMap;
	
	
	public static DataManagerInit getInstance() {
		if (instance == null) {
			synchronized (DataManagerInit.class) {
				if (instance == null) {
					instance = new DataManagerInit();
				}
			}
		}
		return instance;
	}
	
	public void startup() {
		try {
			if(log.isInfoEnabled())
				log.info("\r\n***************************************\r\n"
					+ "DataManagerInit started up !\r\n"
					+ "***************************************");
			init();
		} catch (Exception e) {
			log.error("Backstage start up failed!!!!", e);
		}
	} 
	
	public void init(){
		try {
			log.info("开始加载系统数据");
			initBankInstList();
			initInstInfoList();
			initMerBasicList();
			initMerBillingList();
			initTradeAmountConf();
			initErrorHandlingList();
			initReasonCodeDaoList();
			initTradeTypeMap();
			initInnerErrorHandlingList();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	public void initBankInstList() {
		log.info("开始加载银行机构配置数据");
		
		if (bankInstList == null) {
			bankInstList = new ArrayList<BankInst>();
		} else {
			bankInstList.clear();
		}
		
		if (bankInstMap == null) {
			bankInstMap = new HashMap<String, Object>();
		} else {
			bankInstMap.clear();
		}
		
		bankInstList = bankInstService.queryAllBankInst();
		for (BankInst list : bankInstList) {
			bankInstMap.put(list.getBank_id() + "-", list);
		}
	}
	
	public void initInstInfoList(){
		log.info("开始加载扣款渠道配置数据");
		
		if (instInfoList == null) {
			instInfoList = new ArrayList<InstInfo>();
		} else {
			instInfoList.clear();
		}
		
		if(instInfoMap == null){
			instInfoMap = new HashMap<String, Object>();
		}else{
			instInfoMap.clear();
		}

		instInfoList = instInfoService.queryAll();
		for (InstInfo list : instInfoList) {
			instInfoMap.put(list.getInstId()+"-"+list.getInst_type(), list);
		}
	}
	
	public void initMerBasicList(){
		log.info("开始加载商户基本信息数据");
		
		if(merBasicMap == null){
			merBasicMap = new HashMap<String, Object>();
		}else{
			merBasicMap.clear();
		}

		List<Merchants> merBasicList = merchantsService.queryAllMerBasicInfo();
		for (Merchants list : merBasicList) {
			merBasicMap.put(list.getMer_code(), list);
		}
	}
	
	public void initMerBillingList(){
		log.info("开始加载商户结算信息数据");
		
		if(merBillingMap == null){
			merBillingMap = new HashMap<String, Object>();
		}else{
			merBillingMap.clear();
		}

		List<Merchants> merBillingList = merchantsService.queryAllMerBillingInfo();
		for (Merchants list : merBillingList) {
			merBillingMap.put(list.getMer_code(), list);
		}
	}
	
	public void initTradeAmountConf(){
		log.info("开始加载交易金额配置数据");
		
		if (tradeAmountConfsList == null) {
			tradeAmountConfsList = new ArrayList<TradeAmountConf>();
		} else {
			tradeAmountConfsList.clear();
		}
		if (tradeAmountConfsMap == null) {
			tradeAmountConfsMap = new HashMap<String, Object>();
		} else {
			tradeAmountConfsMap.clear();
		}
		tradeAmountConfsList = tradeAmountConfService.queryTradeAmountConf();
		for (TradeAmountConf tradeAmountConf : tradeAmountConfsList) {
			tradeAmountConfsMap.put(tradeAmountConf.getProcess()+tradeAmountConf.getTrademsgType(), tradeAmountConf);
		}
	}
	
	public void initErrorHandlingList(){
		log.info("开始加载差错处理方式配置数据");
		
		if (errorHandlingList == null) {
			errorHandlingList = new ArrayList<ErrorHandling>();
		} else {
			errorHandlingList.clear();
		}
		if (errorHandingMap == null) {
			errorHandingMap = new HashMap<Integer, Object>();
		} else {
			errorHandingMap.clear();
		}
		errorHandlingList = errorHandlingService.getErrorHandleList();
		for (ErrorHandling errorHandling : errorHandlingList) {
			errorHandingMap.put(errorHandling.getId(), errorHandling);
		}
	}
	
	public void initReasonCodeDaoList(){
		log.info("开始加载差错处理方式原因码配置数据");
		
		if (reasonCodesList == null) {
			reasonCodesList = new ArrayList<ReasonCode>();
		} else {
			reasonCodesList.clear();
		}
		if (reasonCodesMap == null) {
			reasonCodesMap = new HashMap<String, Object>();
		} else {
			reasonCodesMap.clear();
		}
		reasonCodesList = reasonCodeDaoService.getReasonCodeLst();
		for (ReasonCode reasonCode : reasonCodesList) {
			reasonCodesMap.put(reasonCode.getReason_id(), reasonCode);
		}
	}
	
	public void initTradeTypeMap(){
		log.info("开始加载交易类别配置数据");
		
		if (tradeTypeList == null) {
			tradeTypeList = new ArrayList<TradeType>();
		} else {
			tradeTypeList.clear();
		}
		if (tradeTypeMap == null) {
			tradeTypeMap = new HashMap<String, Object>();
		} else {
			tradeTypeMap.clear();
		}
		tradeTypeList = tradeTypeService.queryAll();
		for (TradeType tradeType : tradeTypeList) {
			tradeTypeMap.put(tradeType.getTrade_code(), tradeType);
		}
	}
	
	/**
	 * 获取内部差错处理方式
	 */
	public void initInnerErrorHandlingList(){
		log.info("开始加载内部差错处理方式配置数据");
		
		if (innerErrorHandlingList == null) {
			innerErrorHandlingList = new ArrayList<InnerErrorHandling>();
		} else {
			innerErrorHandlingList.clear();
		}
		if (innerErrorHandlingMap == null) {
			innerErrorHandlingMap = new HashMap<Integer, Object>();
		} else {
			innerErrorHandlingMap.clear();
		}
		innerErrorHandlingList = innerErrorHandlingService.queryInnerErrorHandlingAll();
		for (InnerErrorHandling innerErrorHandling : innerErrorHandlingList) {
			innerErrorHandlingMap.put(innerErrorHandling.getHandling_id(), innerErrorHandling);
		}
	}
	
	public List<TradeAmountConf> getTradeAmountConfList(){
		if(tradeAmountConfsList != null){
			return tradeAmountConfsList;
		}else{
			log.warn("初始化交易金额配置数据获取失败为空");
			return null;
		}
	}
	
	public TradeAmountConf getTradeAmountConfMap(String key){
		if(tradeAmountConfsMap != null){
			return (TradeAmountConf)tradeAmountConfsMap.get(key);
		}else{
			log.warn("初始化交易金额配置数据获取失败为空");
			return null;
		}
	}
	
	public List<ErrorHandling> getErrorHandlingList(){
		if(errorHandlingList != null){
			return errorHandlingList;
		}else{
			log.warn("初始化差错处理方式配置数据获取失败为空");
			return null;
		}
	}
	
	public ErrorHandling getErrorHanding(int id) {
		ErrorHandling errorHandling = null;
		if (errorHandingMap != null) {
			Object obj = errorHandingMap.get(id);
			if (obj != null) {
				errorHandling = (ErrorHandling)obj;
			} else {
				log.warn(id + " : 获取失败、数据库中不存在");
			}
		}
		return errorHandling;
	}
	
	public List<ReasonCode> getReasonCodeList(Integer id){
		List<ReasonCode> list = null;
		if(reasonCodesList != null){
			list = new ArrayList<ReasonCode>();
			for (ReasonCode reasonCode : reasonCodesList) {
				if(reasonCode.getId() == id){
					list.add(reasonCode);
				}
			}
		}else{
			log.warn("初始化差错处理方式原因码配置数据获取失败为空");
		}
		return list;
	}
	
	public List<InstInfo> getInstInfoList(){
		if(instInfoList != null){
			return instInfoList;
		}else{
			log.warn("初始化扣款渠道配置数据获取失败为空");
		}
		return null;
	}
	
	public List<InstInfo> getOuterDzInfo(int whether_outer_dz) {
		List<InstInfo> list = null;
		if (instInfoList != null) {
			list = new ArrayList<InstInfo>();
			for (InstInfo instInfo : instInfoList) {
				if (instInfo.getWhetherOuterDz() == whether_outer_dz) {
					list.add(instInfo);
				}
			}
		} else {
			log.warn("初始化扣款渠道配置数据获取失败为空");
		}
		return list;
	}
	
	public List<InstInfo> getInstInfo(int whether_outer_error_dz) {
		List<InstInfo> list = null;
		if (instInfoList != null) {
			list = new ArrayList<InstInfo>();
			for (InstInfo instInfo : instInfoList) {
				if (instInfo.getWhether_outer_error_dz() == whether_outer_error_dz) {
					list.add(instInfo);
				}
			}
		} else {
			log.warn("初始化扣款渠道配置数据获取失败为空");
		}
		return list;
	}
	
	public List<InstInfo> getInnerDuizhangInstInfoList(int whether_inner_js) {
		List<InstInfo> list = null;
		if (instInfoList != null) {
			list = new ArrayList<InstInfo>();
			for (InstInfo instInfo : instInfoList) {
				if (instInfo.getWhether_inner_js() == whether_inner_js) {
					list.add(instInfo);
				}
			}
		} else {
			log.warn("初始化扣款渠道配置数据获取失败为空");
		}
		return list;
	}
	
	public InstInfo getInstInfoById(int deduct_sys_id,int inst_type) {
		InstInfo instInfo = null;
		if (instInfoMap != null) {
			instInfo = (InstInfo)instInfoMap.get(deduct_sys_id+"-"+inst_type);
		} else {
			log.warn("初始化扣款渠道配置数据获取失败为空");
		}
		return instInfo;
	}
	
	public TradeType getTradeType(String trace){
		TradeType tradeType = null;
		if(tradeTypeMap != null){
			Object obj = tradeTypeMap.get(trace);
			if(obj != null){
				tradeType = (TradeType)obj;
			}else{
				log.warn(trace + " : 获取失败、数据库中不存在");
			}
		}
		return tradeType;
	}
	
	public ReasonCode getReasonCode(String reasonCode) {
		ReasonCode reaCode = null;
		if (reasonCodesMap != null) {
			Object obj = reasonCodesMap.get(reasonCode);
			if (obj != null) {
				reaCode = (ReasonCode) obj;
			} else {
				log.warn(reaCode + " : 获取失败、数据库中不存在");
			}
		}
		return reaCode;
	}
	
	/**
	 * 根据线上线下渠道标识获取支付渠道信息
	 * @param instType
	 * @return
	 */
	public List<InstInfo> getLineInstInfo(int instType) {
		List<InstInfo> list = null;
		if (instInfoList != null) {
			list = new ArrayList<InstInfo>();
			for (InstInfo instInfo : instInfoList) {
				if (instInfo.getInst_type() == instType) {
					list.add(instInfo);
				}
			}
		} else {
			log.info("根据线上线下渠道标识获取支付渠道为空");
		}
		return list;
	}
	
	/**
	 * 获取线下的内部对账渠道
	 * @param instType
	 * @param whether_outer_dz
	 * @return
	 */
	public List<InstInfo> getUnderLineInnerDzInfoList(int instType, int whether_outer_dz) {
		List<InstInfo> list = null;
		if (instInfoList != null) {
			list = new ArrayList<InstInfo>();
			for (InstInfo instInfo : instInfoList) {
				if (instInfo.getInst_type() == instType && instInfo.getWhetherOuterDz() == whether_outer_dz) {
					list.add(instInfo);
				}
			}
		} else {
			log.info("根据线上线下渠道标识获取支付渠道为空");
		}
		return list;
	}
	
	/**
	 * 根据处理方式ID获取内部差错处理方式
	 * @param id
	 * @return
	 */
	public InnerErrorHandling getInnerErrorHanding(int handlingId) {
		InnerErrorHandling innerErrorHandling = null;
		if (innerErrorHandlingMap != null) {
			Object obj = innerErrorHandlingMap.get(handlingId);
			if (obj != null) {
				innerErrorHandling = (InnerErrorHandling)obj;
			} else {
				log.warn(handlingId + " : 获取失败、数据库中不存在");
			}
		}
		return innerErrorHandling;
	}
	
	
	/**
	 * 管理平台修改渠道信息成功时更新内存
	 * @param instId
	 * @param instInfo
	 */
	public void setInstInfoMap(int instId ,int instType, InstInfo instInfo){
		if(instInfoMap == null){
			instInfoMap = new Hashtable<String,Object>();
		}
		log.info("修改渠道信息时更新内存成功，修改内存渠道信息"+instInfo.getInstId()+"渠道名称"+instInfo.getName());
		instInfoMap.put(instId+"-"+instType, instInfo);
		initInstInfoList();
	}
	
	/**
	 * 管理平台删除渠道成功时更新内存
	 * @param instId
	 */
	public void removeInstInfo(int instId){
		if(instId != 0){
			log.info("删除操作时更新内存成功，内存中删除的渠道ID是：" + instId);
			instInfoMap.remove(instId);
		}
	}
	
	/**
	 * 根据是否解析品牌服务文件标识获取支付渠道信息
	 * @param instType
	 * @return
	 */
	public List<InstInfo> getParsePpfwInstInfo(int whether_parse_brank) {
		List<InstInfo> list = null;
		if (instInfoList != null) {
			list = new ArrayList<InstInfo>();
			for (InstInfo instInfo : instInfoList) {
				if (instInfo.getWhether_parse_brank() == whether_parse_brank) {
					list.add(instInfo);
				}
			}
		} else {
			log.info("根据线上线下渠道标识获取支付渠道为空");
		}
		return list;
	}
	
	/**
	 * 根据银行机构ID获取银行机构信息
	 * @param bankId
	 * @return
	 */
	public BankInst getBankInstByBankId(int bankId) {
		BankInst bankInst = null;
		if (bankInstMap != null) {
			bankInst = (BankInst) bankInstMap.get(bankId + "-");
		}
		return bankInst;
	}
	
	/**
	 * 管理平台修改银行机构时更新内存信息
	 * @param bankId
	 * @param map
	 */
	public void setBankInstMap(int bankId, Map<String, Object> map) {
		if (bankInstMap == null) {
			bankInstMap = new HashMap<String, Object>();
		}
		log.info("修改银行机构信息时更新管理平台内存成功，修改内存银行机构名称" + map.get("bank_name"));
		bankInstMap.put(bankId+"-", map);
		initBankInstList();
	} 
	
	public void setBankInstMap(int bankId, BankInst bankInst) {
		if (bankInstMap == null) {
			bankInstMap = new HashMap<String, Object>();
		}
		log.info("修改银行机构信息时更新管理平台内存成功，修改内存银行机构ID:" + bankInst.getBank_id() + "机构名称：" + bankInst.getBank_name());
		bankInstMap.put(bankId+"-", bankInst);
		initBankInstList();
	} 
	
	/**
	 * 管理平台删除银行机构时更新内存
	 * @param bankId
	 */
	public void removeBankInst(int bankId) {
		if (bankId != 0) {
			log.info("删除银行机构时更新管理平台内存成功，内存中删除的银行机构ID是：" + bankId);
			bankInstMap.remove(bankId);
		}
	}
	
	public void setMerBasicMap(String merCode, Merchants merchants){
		if(merBasicMap == null){
			merBasicMap = new Hashtable<String,Object>();
		}
		merBasicMap.put(merCode, merchants);
	}
	
	public void setMerBillingMap(String merCode, Merchants merchants){
		if(merBillingMap == null){
			merBillingMap = new Hashtable<String,Object>();
		}
		merBillingMap.put(merCode, merchants);
	}
	
	/**
	 * 根据银行机构ID删除渠道信息时更新管理平台渠道内存信息
	 * @param bankId
	 */
	public void removeInstInfoByBankId(int bankId){
		if(bankId != 0){
			log.info("根据银行机构ID删除渠道信息时更新管理平台渠道内存信息，删除渠道的银行机构ID是：" + bankId);
			instInfoMap.remove(bankId);
		}
	}
	/**
	 * 根据渠道名称模糊查询内部清算渠道信息
	 * @param inst_name 渠道名称
	 * @param whether_out_dz 是否外部对账
	 * @return
	 */
	public List<InstInfo> getInstInfoByInstName(String inst_name,int whether_out_dz,int inst_type){
		List<InstInfo> list = null;
		List<InstInfo> list_all = getOuterDzInfo(whether_out_dz);
		if (list_all != null) {
			list = new ArrayList<InstInfo>();
			if(inst_type != 2){
				for (InstInfo instInfo : list_all) {
					if(instInfo.getInst_type() == inst_type){
						if(StringUtils.isNotBlank(inst_name)){
							if (instInfo.getName().indexOf(inst_name) > -1) {
								list.add(instInfo);
							}
						}else{
							list.add(instInfo);
						}
					}
				}
			}else{
				if(StringUtils.isNotBlank(inst_name)){
					for (InstInfo instInfo : list_all) {
						if (instInfo.getName().indexOf(inst_name) > -1) {
							list.add(instInfo);
						}
					}
				}else{
					list = list_all;
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据银行机构ID获取渠道信息
	 * @param bankId
	 * @return
	 */
	public List<InstInfo> getInstInfoByBankId(int bankId) {
		List<InstInfo> list = null;
		if (instInfoList != null) {
			list = new ArrayList<InstInfo>();
			for (InstInfo instInfo : instInfoList) {
				if (instInfo.getBank_id() == bankId) {
					list.add(instInfo);
				}
			}
		} else {
			log.info("根据银行机构ID获取渠道信息为空");
		}
		return list;
	}
	
	public List<BankInst> getBankInstList() {
		if (bankInstList != null) {
			return bankInstList;
		} else {
			log.warn("初始化银行机构置数据获取失败为空");
		}
		return null;
	}
	
	/**
	 * 根据银行网关类型获取银行机构信息
	 * @param bankType
	 * @return
	 */
	public List<BankInst> getBankInstListByBankType(int bankType) {
		List<BankInst> list = null;
		if (bankInstList != null) {
			list = new ArrayList<BankInst>();
			for (BankInst bankInst : bankInstList) {
				if (bankInst.getBank_type() == bankType) {
					list.add(bankInst);
				}
			}
		} else {
			log.info("根据银行网关类型获取银行机构信息为空！");
		}
		return list;
	}
	
	/**
	 * 根据外部对账类型获取银行机构信息
	 * @param whetherOuterDz
	 * @return
	 */
	public List<BankInst> getBankInstListByWhetherOuterDz(int whetherOuterDz) {
		List<BankInst> list = null;
		if (bankInstList != null) {
			list = new ArrayList<BankInst>();
			for (BankInst bankInst : bankInstList) {
				if (bankInst.getWhether_outer_dz() == whetherOuterDz) {
					list.add(bankInst);
				}
			}
		} else {
			log.info("根据外部对账类型获取银行机构信息为空！");
		}
		return list;
	}
	/**
	 * 根据银行机构ID获取外部对账渠道信息
	 * @param bankId
	 * @return
	 */
	public List<InstInfo> getOutDzInstInfoByBankId(int bankId) {
		List<InstInfo> list = null;
		if (instInfoList != null) {
			list = new ArrayList<InstInfo>();
			for (InstInfo instInfo : instInfoList) {
				if (instInfo.getBank_id() == bankId) {
					list.add(instInfo);
				}
			}
		} else {
			log.info("根据银行机构ID获取渠道信息为空");
		}
		return list;
	}
}
