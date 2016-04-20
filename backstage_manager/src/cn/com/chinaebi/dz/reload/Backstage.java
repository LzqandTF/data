package cn.com.chinaebi.dz.reload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.object.TimingTaskConf;
import cn.com.chinaebi.dz.object.TradeAmountConf;
import cn.com.chinaebi.dz.object.dao.BankInstDAO;
import cn.com.chinaebi.dz.object.dao.InstInfoDAO;
import cn.com.chinaebi.dz.object.dao.MerBasicDAO;
import cn.com.chinaebi.dz.object.dao.MerBillingDAO;
import cn.com.chinaebi.dz.object.dao.TimingTaskConfDAO;
import cn.com.chinaebi.dz.object.dao.TradeAmountConfDAO;
import cn.com.chinaebi.dz.object.util.PropertiesUtils;
import cn.com.chinaebi.dz.service.TradeDzfList;
import cn.com.chinaebi.dz.service.TradeOkList;
import cn.com.chinaebi.task.quartz.iface.SchedulerService;

/**
 * TODO ...
 * 
 **/
public class Backstage {
	
	private static Backstage instance;
	
	protected final Log log = LogFactory.getLog(getClass());
	private SchedulerService schedulerService;

	private volatile Map<InstInfoPK, Object> instInfoMap;
	private volatile Map<Integer, Object> bankInstMap;
	private volatile Map<Integer, Object> instReconciliationColumnsMap;
	private volatile Map<Integer, Object> timingTaskConfMap;
	private volatile Map<String, Object> tradeAmountConfMap;
	private volatile Map<String, Object> merBasicMap;
	private volatile Map<String, Object> merBillingMap;
	
	private List<InstInfo> instInfoList;


	public static Backstage getInstance() {
		if (instance == null) {
			synchronized (Backstage.class) {
				if (instance == null) {
					instance = new Backstage();
				}
			}
		}
		return instance;
	}
	
	
	public void startup() {
		try {
			if(log.isInfoEnabled())
				log.info("\r\n***************************************\r\n"
					+ "Backstage started up !\r\n"
					+ "***************************************");
			init();
		} catch (Exception e) {
			log.error("Backstage start up failed!!!!", e);
		}
	}
	
	
	public void init(){
		try {
			log.info("开始加载系统数据");
			initInstInfo();
			initBankInst();
			initTimingTaskConf();
			initTradeAmountConf();
			initMerBasicConf();
			initMerBillingConf();
			executeTask();
			startBfjDataSyn();
			/*Runnable dzf_run = new Runnable() {
				@Override
				public void run() {
					TradeDzfList.getInstance().run();
				}
			};
			new Thread(dzf_run).start();
			Runnable ok_run = new Runnable() {
				@Override
				public void run() {
					TradeOkList.getInstance().run();
				}
			};
			new Thread(ok_run).start();*/
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	/**
	 * 初始化定时任务
	 */
	public void executeTask(){
		log.info("开始初始化定时执行任务");
		Map<Integer,Object> map = getTimingTaskConfMap();
		ApplicationContext springContext =  new  ClassPathXmlApplicationContext("classpath:spring-quartz.xml");       
		log.info(springContext == null ? "加载spring配置文件 : 失败" : "加载spring配置文件 : 成功");
		schedulerService =(SchedulerService)springContext.getBean( "schedulerService" );  
		if(schedulerService == null){
			log.info("初始化定时执行任务失败 schedulerService 对象为空");
		}else{
			if(map.entrySet() != null){
				for(Map.Entry<Integer, Object> entry:map.entrySet()) {
					TimingTaskConf timingTaskConf = (TimingTaskConf)entry.getValue();
					
					//判断该定时任务对应的渠道是否处于开通状态
					if(InstInfoDAO.getInstance().getInstStatusByInstId(timingTaskConf.getChannelId(),timingTaskConf.getInstType())){//处于开通状态，加载此定时任务
						//对账文件
						if(StringUtils.isNotBlank(timingTaskConf.getAcquisitionTimeName())){
							schedulerService.schedule(timingTaskConf.getAcquisitionTimeName(),timingTaskConf.getAcquisitionTime());
						}
						//对账处理
						if(StringUtils.isNotBlank(timingTaskConf.getDzHandlerTimeName())){
							schedulerService.schedule(timingTaskConf.getDzHandlerTimeName(),timingTaskConf.getDzHandlerTime());
						}
						//汇总核心交易数据
						if(StringUtils.isNotBlank(timingTaskConf.getGatherDataTimeName())){
							schedulerService.schedule(timingTaskConf.getGatherDataTimeName(),timingTaskConf.getGatherDataTime());
						}
						//对账文件生成
						if(StringUtils.isNotBlank(timingTaskConf.getDzFileCreateName())){
							schedulerService.schedule(timingTaskConf.getDzFileCreateName(),timingTaskConf.getDzFileCreateTime());
						}
					}
					
				}
			}else{
				log.error("getTimingTaskConfMap 获取数据为空");
			}
		}
	} 
	
	
	/**
	 * 备付金数据定时任务同步
	 * bfj_flag : 0 - 删除定时任务、1 - 修改定时任务
	 * @return
	 */
	public boolean startBfjDataSyn(){
		boolean flag = false;
		if(schedulerService != null){
			String bfj_key = PropertiesUtils.rtReadProperties("bfj_key", "conf");
			String bfj_cron = PropertiesUtils.rtReadProperties("bfj_cron", "conf");
			if(StringUtils.isNotBlank(bfj_key) && StringUtils.isNotBlank(bfj_cron)){
				schedulerService.schedule(bfj_key, bfj_cron);
			}
		}else{
			log.error("备付金定时任务启动失败,schedulerService 不能为空");
		}
		return flag;
	}
	
	/**
	 * 定时任务修改
	 * @param id : 主键
	 * @param taskName ： 定时任务名称
	 * @param cronExpression ： 定时任务表达式 
	 * @return boolean
	 */
	public boolean updateTimingTaskConf(Integer id,String taskName,String cronExpression){
		boolean flag = false;
		Map<Integer, Object> map  = getTimingTaskConfMap();
		if(map != null){
			Object obj = map.get(id);
			if(obj != null){
				TimingTaskConf taskConf = (TimingTaskConf)obj;
				if(StringUtils.equals(taskName, taskConf.getGatherDataTimeName())){
					try {
						if(schedulerService == null){
							log.info("初始化定时执行任务失败 schedulerService 对象为空");
							return flag;
						}
						flag = schedulerService.modifyJob(taskName, cronExpression);
					} catch (Exception e) {
						log.error(e);
					}
				}else if(StringUtils.equals(taskName, taskConf.getAcquisitionTimeName())){
					try {
						if(schedulerService == null){
							log.info("初始化定时执行任务失败 schedulerService 对象为空");
							return flag;
						}
						flag = schedulerService.modifyJob(taskName, cronExpression);
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
				}else if(StringUtils.equals(taskName, taskConf.getDzHandlerTimeName())){
					try {
						if(schedulerService == null){
							log.info("初始化定时执行任务失败 schedulerService 对象为空");
							return flag;
						}
						flag = schedulerService.modifyJob(taskName, cronExpression);
					} catch (Exception e) {
						log.error(e);
					}
				}else if(StringUtils.equals(taskName, taskConf.getDzFileCreateName())){
					log.info("进行修改对账文件生成定时任务");
					try {
						if(schedulerService == null){
							log.info("初始化定时执行任务失败 schedulerService 对象为空");
							return flag;
						}
						flag = schedulerService.modifyJob(taskName, cronExpression);
						log.info(flag?"定时任务修改成功":"定时任务修改失败");
					} catch (Exception e) {
						log.error(e);
					}
				}
				else{
					log.error("定时任务名为 : "+taskName + " 的定时任务不存在");
				}
			}else{
				log.error("编号为 : "+id + " 定时任务不存在");
			}
		}else{ 
			log.error("getTimingTaskConfMap() 获取map对象问空");
		}
		return flag;
	}
	
	/**
	 * 添加定时任务
	 * @param jobName ：定时任务名称
	 * @param cronExpression ：定时任务表达式 
	 * @return boolean
	 */
	public boolean addTimingTaskConf(String jobName,String cronExpression){
		boolean flag = false;
		try{
			schedulerService.schedule(jobName, cronExpression);
			flag = true;
		}catch(Exception e){
			log.error(e);
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 删除定时任务
	 * @param id ：主键ID
	 * @param taskName ：任务名称
	 * @return boolean
	 */
	public boolean deleteTimingTaskConf(Integer id,String taskName){
		boolean flag = false;
		Map<Integer, Object> map  = getTimingTaskConfMap();
		if(map != null){
			Object obj = map.get(id);
			if(obj != null){
				TimingTaskConf taskConf = (TimingTaskConf)obj;
				if(StringUtils.equals(taskName, taskConf.getAcquisitionTimeName()) || StringUtils.equals(taskName, taskConf.getDzHandlerTimeName())){
					try {
						flag = schedulerService.removeJob(taskName);
					} catch (Exception e) {
						log.error(e);
					}
				}else{
					log.error("定时任务名为 : "+taskName + " 的定时任务不存在");
				}
			}else{
				log.error("编号为 : "+id + " 定时任务不存在");
			}
		}else{
			log.error("getTimingTaskConfMap() 获取map对象问空");
		}
		return flag;
	}
	
	public boolean checkJob(String jobName){
		boolean flag = false;
		try{
			flag = schedulerService.checkJob(jobName);
		}catch(Exception e){
			log.error("根据定时任务名称"+jobName+"查询定时任务抛出异常："+e);
		}
		return flag;
	}
	
	public void initInstInfo(){
		log.info("开始加载渠道信息配置数据");
		
		if (instInfoList == null) {
			instInfoList = new ArrayList<InstInfo>();
		} else {
			instInfoList.clear();
		}
		
		if (instInfoMap == null) {
			instInfoMap = new ConcurrentHashMap<InstInfoPK, Object>();
		} else {
			instInfoMap.clear();
		}
		
		instInfoList = InstInfoDAO.getInstance().findAll();
		if(instInfoList != null){
			for (InstInfo instInfo : instInfoList) {
				instInfoMap.put(instInfo.getId(), instInfo);
			}
		}
	}
	
	public void initBankInst(){
		
		log.info("开始加载银行网关配置数据");
		if (bankInstMap == null) {
			bankInstMap = new ConcurrentHashMap<Integer, Object>();
		} else {
			bankInstMap.clear();
		}
		List<BankInst> list = BankInstDAO.getInstance().findAll();
		if(list != null){
			for (BankInst bankInst : list) {
				bankInstMap.put(bankInst.getId(), bankInst);
			}
		}
	}
	
	public void realodInstInfo(){
		
		log.info("重新开始加载机构信息配置数据");
		if (instInfoMap == null) {
			instInfoMap = new ConcurrentHashMap<InstInfoPK, Object>();
		} else {
			instInfoMap.clear();
		}
		
		List<InstInfo> list = InstInfoDAO.getInstance().findAll();
		if(list != null){
			for (InstInfo instInfo : list) {
				instInfoMap.put(instInfo.getId(), instInfo);
			}
		}
	}
	
	public void initTimingTaskConf(){
		log.info("开始加载定时任务配置数据");
		if (timingTaskConfMap == null) {
			timingTaskConfMap = new ConcurrentHashMap<Integer, Object>();
		} else {
			timingTaskConfMap.clear();
		}
		
		List<TimingTaskConf> list = TimingTaskConfDAO.getInstance().findAll();
		if(list != null){
			for (TimingTaskConf taskConf : list) {
				timingTaskConfMap.put(taskConf.getId(), taskConf);
			}
		}
	}
	
	public void initTradeAmountConf(){
		
		log.info("开始加载交易金额配置数据");
		if (tradeAmountConfMap == null) {
			tradeAmountConfMap = new ConcurrentHashMap<String, Object>();
		} else {
			tradeAmountConfMap.clear();
		}
		
		List<TradeAmountConf> list = TradeAmountConfDAO.getInstance().findAll();
		if(list != null){
			for (TradeAmountConf amountConf : list) {
				tradeAmountConfMap.put(amountConf.getProcess()+amountConf.getTrademsgType(), amountConf);
			}
		}
	}
	
	public void initMerBasicConf(){
		
		log.info("开始加载商户基本信息配置数据");
		if(merBasicMap == null){
			merBasicMap = new ConcurrentHashMap<String, Object>();
		}else{
			merBasicMap.clear();
		}
		
		List<MerBasic> list = MerBasicDAO.getInstance().findAll();
		if(list != null){
			for (MerBasic merBasic : list) {
				merBasicMap.put(merBasic.getId(), merBasic);
			}
		}
	}
	
	public void initMerBillingConf(){
		
		log.info("开始加载商户结算信息配置数据");
		if(merBillingMap == null){
			merBillingMap = new ConcurrentHashMap<String, Object>();
		}else{
			merBillingMap.clear();
		}
		
		List<MerBilling> list = MerBillingDAO.getInstance().findAll();
		if(list != null){
			for (MerBilling merBilling : list) {
				merBillingMap.put(merBilling.getMerCode().getId(), merBilling);
			}
		}
	}
	
	/**
	 * 获取商户手续费公式
	 */
	public String getMerPoundage(String merCode){
		Object merPoundage = merBillingMap.get(merCode);
		MerBilling merBilling = null;
		if(merPoundage != null){
			merBilling = (MerBilling)merPoundage;
			return merBilling.getMerPoundage();
		}else
			log.info(merCode+" 不存在merBillingMap内存中");
		return null;
	}
	
	
	public Object getTradeAmountConf(String unique){
		Object obj = tradeAmountConfMap.get(unique);
		if(obj != null)
			return (TimingTaskConf)obj;
		else
			return null;
	}
	
	public TimingTaskConf getTimingTaskConf(Integer instId){
		Object obj = timingTaskConfMap.get(instId);
		if(obj != null)
			return (TimingTaskConf)obj;
		else
			return null;
	}
	
	public Map<Integer, Object> getTimingTaskConfMap(){
		return timingTaskConfMap!=null ? timingTaskConfMap : null;
	}
	
	public Map<Integer, Object> getInstReconciliationColumnsMap(){
		return instReconciliationColumnsMap!=null ? instReconciliationColumnsMap : null;
	}
	
	public Map<InstInfoPK, Object> getInstInfoMap(){
		return instInfoMap!=null ? instInfoMap : null;
	}
	
	public Map<Integer, Object> getBankInstMap(){
		return bankInstMap!=null ? bankInstMap : null;
	}
	
	public InstInfo getInstInfo(InstInfoPK instInfoPK){
		Object obj = instInfoMap.get(instInfoPK);
		if(obj != null)
			return (InstInfo)obj;
		else
			return null;
	}
	
	public BankInst getBankInst(Integer bankGate){
		Object obj = bankInstMap.get(bankGate);
		if(obj != null)
			return (BankInst)obj;
		else
			return null;
	}
	
	public void setBankInstMap(BankInst bankInst) {
		if (bankInstMap == null) {
			bankInstMap = new Hashtable<Integer, Object>();
		}
		log.info("修改内存信息银行机构ID："+bankInst.getId()+"机构名称："+bankInst.getBankName());
		bankInstMap.put(bankInst.getId(), bankInst);
	}
	
	public void removeBankInst(int bankId){
		if(bankId != 0){
			bankInstMap.remove(bankId);
			log.info("成功删除内存中银行机构信息，机构ID：" + bankId);
		}
	}
	
	public List<Integer> getInstInfoIdList(){
		List<InstInfo> list = InstInfoDAO.getInstance().findAll();
		List<Integer> ids = new ArrayList<Integer>();
		for (InstInfo instInfo : list) {
			if(instInfo.isWhetherOuterDz()){
//				if(!instInfo.isWhetherOuterErrorDz()){
					ids.add(instInfo.getId().getInstId());
//				}
			}
		}
		return ids;
	}
	public List<InstInfo> getInstInfoListOfWhetherOuterDz(){
		List<InstInfo> list = InstInfoDAO.getInstance().findAll();
		List<InstInfo> returnList = new ArrayList<InstInfo>();
		for (InstInfo instInfo : list) {
			if(instInfo.isWhetherOuterDz()){
				returnList.add(instInfo);
			}
		}
		return returnList;
	}
	public List<InstInfo> getInstInfoListOfNotOuterDz(){
		List<InstInfo> list = InstInfoDAO.getInstance().findAll();
		List<InstInfo> returnList = new ArrayList<InstInfo>();
		for (InstInfo instInfo : list) {
			if(!instInfo.isWhetherOuterDz()){
				returnList.add(instInfo);
			}
		}
		return returnList;
	}
	public void setInstInfoMap(InstInfoPK instInfoPK, InstInfo instInfo){
		if(instInfoMap == null){
			instInfoMap = new Hashtable<InstInfoPK,Object>();
		}
		log.info("修改内存信息"+instInfo.getId()+"渠道名称"+instInfo.getName());
		instInfoMap.put(instInfoPK, instInfo);
	}
	
	public void removeInstInfo(int instId){
		if(instId != 0){
			instInfoMap.remove(instId);
		}
	}
	
	public void setMerBasicMap(String merCode,MerBasic merBasic){
		if(merBasicMap == null){
			merBasicMap = new Hashtable<String,Object>();
		}
		log.info("修改内存中商户基本信息,商户号:"+merCode+",商户名称为:"+merBasic.getMerName());
		merBasicMap.put(merCode, merBasic);
	}
	
	public MerBasic getMerBasic(String merCode){
		if(StringUtils.isBlank(merCode)){
			return null;
		}
		Object obj = merBasicMap.get(merCode);
		if(obj != null)
			return (MerBasic)obj;
		else
			return null;
	}

	public Map<String, Object> getMerBasicList(){
		if(merBasicMap != null){
			return merBasicMap;
		}
		return null;
	}
	
	public MerBilling getMerBilling(String merCode){
		if(StringUtils.isBlank(merCode)){
			return null;
		}
		Object obj = merBillingMap.get(merCode);
		if(obj != null)
			return (MerBilling)obj;
		else
			return null;
	}
	
	public void setMerBillingMap(String merCode,MerBilling merBilling){
		if(merBillingMap == null){
			merBillingMap = new Hashtable<String,Object>();
		}
		log.info("修改内存中商户结算信息,商户号:"+merCode);
		merBillingMap.put(merCode, merBilling);
	}
	/**
	 * 获取内部结算渠道Map集合
	 * @return
	 */
	public Map<InstInfoPK,Object> getInnerJsInstMap(){
		Map<InstInfoPK,Object> map = new HashMap<InstInfoPK,Object>();
		if(instInfoMap != null){
			for(InstInfoPK instInfopk : instInfoMap.keySet()){
				if(getInstInfo(instInfopk) != null){
					if(getInstInfo(instInfopk).isWhetherInnerJs()){
						map.put(instInfopk, getInstInfo(instInfopk));
					}
				}
			}
		}
		return map;
	}
	/**
	 * 获取外部对账渠道Map集合
	 * @return
	 */
	public Map<InstInfoPK,Object> getOutDzInstMap(){
		Map<InstInfoPK,Object> map = new HashMap<InstInfoPK,Object>();
		if(instInfoMap != null){
			for(InstInfoPK instInfopk : instInfoMap.keySet()){
				if(getInstInfo(instInfopk) != null){
					if(getInstInfo(instInfopk).isWhetherOuterDz()){
						map.put(instInfopk, getInstInfo(instInfopk));
					}
				}
			}
		}
		return map;
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
				if (instInfo.getBank().getId() == bankId) {
					list.add(instInfo);
				}
			}
		} else {
			log.warn("初始化扣款渠道配置数据获取失败为空");
		}
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtils.rtReadProperties("bfj_key", null));
	}
}
