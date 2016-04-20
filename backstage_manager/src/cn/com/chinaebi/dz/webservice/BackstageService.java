package cn.com.chinaebi.dz.webservice;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.object.util.ExchangeCodeEncodingUtil;
import cn.com.chinaebi.dz.service.BankFileHandleService;
import cn.com.chinaebi.dz.service.DuizhangHandleService;
import cn.com.chinaebi.dz.service.FileCreateHandleService;
import cn.com.chinaebi.dz.service.InstInfoService;
import cn.com.chinaebi.dz.service.MerchantsBalanceService;
import cn.com.chinaebi.dz.service.TimingTaskService;
import cn.com.chinaebi.dz.service.TradeLstService;
import cn.com.chinaebi.dz.thread.run.ReflectTask;
import cn.com.chinaebi.dz.util.StringPingJie;

public class BackstageService extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(BackstageService.class);
	
	private StringPingJie stringPingJie = StringPingJie.getInstance();
	private Map<String, Boolean> concurrentHashMap = new ConcurrentHashMap<String, Boolean>();
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	private String returnXmlString(String trace, boolean status){
		DzTradeContextDocument document_return = DzTradeContextDocument.Factory.newInstance();
		DzTradeContextType type_return = document_return.addNewDzTradeContext();
		type_return.setTrace(trace);
		if(status){
			type_return.setOpCode("00");
		}else{
			type_return.setOpCode("01");
		}
		log.info(stringPingJie.getStringPingJie("\n回执xml信息:\n",document_return.toString()));
		return document_return.toString();
	}
	
	private String returnXmlRootElecment(String xmlString){
		String returnElecment = null;
		if(StringUtils.isNotBlank(xmlString)){
			int lastLen = xmlString.lastIndexOf("</");
			String str = xmlString.substring(lastLen, xmlString.length());
			returnElecment = str.substring(2, str.length()-1);
		}else
			log.warn("returnXmlRootElecment方法xmlStr参数为空");
		log.info(stringPingJie.getStringPingJie("returnXmlRootElecment return = ",returnElecment));
		return returnElecment;
	}
	
	private String returnTraceValue(String xmlString){
		int tradeFirstLen = xmlString.indexOf("<trace>");
		int tradeLastLen = xmlString.indexOf("</trace>");
		return xmlString.substring(tradeFirstLen, tradeLastLen).replace("<trace>", "");
	}
	/**
	 * 根据商户号查询商户余额信息回执
	 * @param trace
	 * @param mer_code
	 * @param mer_balance
	 * @return
	 */
	private String returnXmlStringBalance(String trace,String mer_code,String mer_balance){
		RreturnRyfMerBalanceSelectReqDocument document=RreturnRyfMerBalanceSelectReqDocument.Factory.newInstance();
		RreturnRyfMerBalanceSelectReqType type=document.getRreturnRyfMerBalanceSelectReq();
		type.setTrace(trace);
		type.setMerCode(mer_code);
		type.setMerBalance(mer_balance);
		log.info(stringPingJie.getStringPingJie("\n回执xml信息:\n",document.toString()));
		return document.toString();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String xmlString = request.getParameter("xmlString");
		log.info(stringPingJie.getStringPingJie("收到管理平台发送报文：",xmlString));
		ReflectTask reflectTask = null;
		try {
			log.info("检查该请求是否正在执行");
			String rootElecment = returnXmlRootElecment(xmlString);
			if(StringUtils.isNotBlank(rootElecment) && concurrentHashMap.get(rootElecment) != null){
				boolean flag = concurrentHashMap.get(rootElecment);
				if(flag){
					log.info("检查到该请求正在执行,不能重复执行该请求,需等该请求执行完毕");
					response.getWriter().print(returnXmlString(returnTraceValue(xmlString), false));
					return;
				}else{
					log.info("未检查到系统正在处理该请求,允许受理执行");
				}
			}
			
			log.info("受理请求,开始处理");
			if(StringUtils.isNotBlank(xmlString)){
				if(xmlString.startsWith("<manualSummaryOriginal")){//手动汇总渠道原始交易数据
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					ManualSummaryOriginalDocument document = ManualSummaryOriginalDocument.Factory.parse(xmlString);
					ManualSummaryOriginalType type = document.getManualSummaryOriginal();
					boolean flag = TradeLstService.channelDataCollectHandle(type);
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				}
				else if(xmlString.startsWith("<manualParseDzFile")){//手动解析银行对账文件
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					ManualParseDzFileDocument document = ManualParseDzFileDocument.Factory.parse(xmlString);
					ManualParseDzFileType type = document.getManualParseDzFile();
					
					boolean flag = BankFileHandleService.dzFileParseHandle(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				}else if(xmlString.startsWith("<manualHandlerAgainDz")){//手动对账
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					ManualHandlerAgainDzDocument document = ManualHandlerAgainDzDocument.Factory.parse(xmlString);
					ManualHandlerAgainDzType type = document.getManualHandlerAgainDz();
					
					boolean flag = DuizhangHandleService.duizhangHandle(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				}else if(xmlString.startsWith("<manualHandlerErrorDz")){//手动差错对账
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					ManualHandlerErrorDzDocument document = ManualHandlerErrorDzDocument.Factory.parse(xmlString);
					ManualHandlerErrorDzType type = document.getManualHandlerErrorDz();
					
					boolean flag = DuizhangHandleService.errorDuizhangHandle(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				}
//				else if(xmlString.startsWith("<reductionDataStatus")){//还原原始交易数据
//					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
//					
//					ReductionDataStatusDocument document = ReductionDataStatusDocument.Factory.parse(xmlString);
//					ReductionDataStatusType type = document.getReductionDataStatus();
//					
//					boolean flag = DuizhangHandleService.reductionDataStatus(type);
//					
//					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
//					
//					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
//					response.getWriter().print(returnXmlString(type.getTrace(), flag));
//					
//				}
				else if(xmlString.startsWith("<reductionErrorDataStatus")){//还原差错数据
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					ReductionErrorDataStatusDocument document = ReductionErrorDataStatusDocument.Factory.parse(xmlString);
					ReductionErrorDataStatusType type = document.getReductionErrorDataStatus();
					
					boolean flag = DuizhangHandleService.reductionErrorDataStatus(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				}else if(xmlString.startsWith("<timingTaskSummaryDataConf")){//更改内存中原始交易数据定时任务
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					boolean flag = false;
					String trace = "";
					try{
						
						TimingTaskSummaryDataConfDocument document = TimingTaskSummaryDataConfDocument.Factory.parse(xmlString);
						TimingTaskSummaryDataConfType type = document.getTimingTaskSummaryDataConf();
						
						trace = type.getTrace();
						
						flag = TimingTaskService.summaryDataTimingTaskHandle(type);
						
					}catch(Exception e){
						log.error(e);
					}
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(trace, flag));
					
				}else if(xmlString.startsWith("<timingTaskDzHandlerConf")){//修改内存中对账定时任务配置信息
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					boolean flag = false;
					String trace = "";
					try{
						
						TimingTaskDzHandlerConfDocument document = TimingTaskDzHandlerConfDocument.Factory.parse(xmlString);
						TimingTaskDzHandlerConfType type = document.getTimingTaskDzHandlerConf();
						
						trace = type.getTrace();
						
						flag = TimingTaskService.duiZhangTimingTaskHandle(type);
						
					}catch(Exception e){
						log.error(e);
					}
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(trace, flag));
					
				}else if(xmlString.startsWith("<timingTaskDzFileConf")){//更改内存中解析对账文件定时任务
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					boolean flag = false;
					String trace = "";
					try{
						
						TimingTaskDzFileConfDocument document = TimingTaskDzFileConfDocument.Factory.parse(xmlString);
						TimingTaskDzFileConfType type = document.getTimingTaskDzFileConf();
						
						trace = type.getTrace();
						
						flag = TimingTaskService.dzFileParseTimingTaskHandle(type);
						
						
					}catch(Exception e){
						log.error(e);
					}
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(trace, flag));
					
				}else if(xmlString.startsWith("<timingTaskDzFileGenerate")){//更改内存中对账文件生成定时任务
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					boolean flag = false;
					String trace = "";
					try{
						
						TimingTaskDzFileGenerateDocument document = TimingTaskDzFileGenerateDocument.Factory.parse(xmlString);
						TimingTaskDzFileGenerateType type = document.getTimingTaskDzFileGenerate();
						
						trace = type.getTrace();
						
						flag = TimingTaskService.dzFileGenerateTimingTaskHandle(type);
						
					}catch(Exception e){
						log.error(e);
					}
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(trace, flag));
					
				}else if(xmlString.startsWith("<reductionGenerateDzFile")){//手动生成文件
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					ReductionGenerateDzFileDocument document = ReductionGenerateDzFileDocument.Factory.parse(xmlString);
					ReductionGenerateDzFileType type = document.getReductionGenerateDzFile();
					
					boolean flag = FileCreateHandleService.createFileHandle(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				} else if (xmlString.startsWith("<bankInstUpdateRam")) { // 更新内存中银行机构信息
					log.info("开始更新后台内存中银行机构信息...");
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					BankInstUpdateRamDocument document = BankInstUpdateRamDocument.Factory.parse(xmlString);
					BankInstUpdateRamType type = document.getBankInstUpdateRam();
					
					boolean flag = InstInfoService.bankInstUpdateRam(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				} else if(xmlString.startsWith("<deductChannelUpdateRam")){//更新内存中渠道信息
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					DeductChannelUpdateRamDocument document = DeductChannelUpdateRamDocument.Factory.parse(xmlString);
					DeductChannelUpdateRamType type = document.getDeductChannelUpdateRam();
					
					boolean flag = InstInfoService.deductChannelUpdateRam(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				}else if(xmlString.startsWith("<merBasicUpdateRam")){//更新内存中商户基本信息
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					MerBasicUpdateRamDocument document = MerBasicUpdateRamDocument.Factory.parse(xmlString);
					MerBasicUpdateRamType type = document.getMerBasicUpdateRam();
					
					boolean flag = InstInfoService.merBasicUpdateRam(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
				} else if(xmlString.startsWith("<merBillingUpdateRam")){//更新内存中商户结算信息
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					MerBillingUpdateRamDocument document = MerBillingUpdateRamDocument.Factory.parse(xmlString);
					MerBillingUpdateRamType type = document.getMerBillingUpdateRam();
					
					boolean flag = InstInfoService.merBillingUpdateRam(type);
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
				} else if(xmlString.startsWith("<merInfoHandlerReq")){//商户管理信息同步处理
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					MerInfoHandlerReqDocument document = MerInfoHandlerReqDocument.Factory.parse(xmlString);
					MerInfoHandlerReqType type = document.getMerInfoHandlerReq();
					
					String tradeCode=type.getTrace();//回执编号
					log.info(stringPingJie.getStringPingJie("接收到管理平台请求，收到参数如下：数据处理类型-",type.getDataHandler(),";商户名称-",ExchangeCodeEncodingUtil.parseISO88591ToUTF8(type.getMerName()),";电银商户号-",type.getInnerMerCode()));
					
					log.info(stringPingJie.getStringPingJie("管理平台发送报文：",xmlString));
					
					boolean flag = false;
					
					try{
//						flag = MerchantService.handleMerchantInfo(type);
						log.warn("商户同步信息不受理,只受理融易付平台发送请求接口");
					}catch(Exception e){
						log.error(e);
					}
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("返回管理平台信息为:",(flag?"商户信息同步成功":"商户信息同步失败")));
					response.getWriter().print(returnXmlString(tradeCode, flag));
					
				}else if(xmlString.startsWith("<ryfMerStanceSelectReq")){//根据商户号查询商户余额信息
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					RyfMerStanceSelectReqDocument document = RyfMerStanceSelectReqDocument.Factory.parse(xmlString);
					RyfMerStanceSelectReqType type=document.getRyfMerStanceSelectReq();
					String tradeCode=type.getTrace();//回执编号
					String mer_code=type.getMerCode();//商户号
					String mer_balance="0";//商户余额
					try {
						MerBalance balance=MerchantsBalanceService.handleMerchantInfo(type);
						if(balance!=null){
							mer_balance=balance.getMerBalance();
						}
					} catch (Exception e) {
						log.error(e);
					}
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					response.getWriter().print(returnXmlStringBalance(tradeCode,mer_code,mer_balance));
					
				}else if(xmlString.startsWith("<deductChannelOpenOrClose")){//扣款渠道状态修改操作,对相应定时任务做处理
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					boolean flag = false;
					DeductChannelOpenOrCloseDocument document = DeductChannelOpenOrCloseDocument.Factory.parse(xmlString);
					DeductChannelOpenOrCloseType type = document.getDeductChannelOpenOrClose();
					String tradeCode = type.getTrace();
					try{
						flag = InstInfoService.instInfoStatusHandle(type);
					}catch(Exception e){
						log.error(e);
					}
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("返回管理平台信息为:",(flag?"渠道定时任务信息配置同步成功":"渠道定时任务信息配置同步失败")));
					response.getWriter().print(returnXmlString(tradeCode, flag));
					
				}else if(xmlString.startsWith("<createSettleFile")){//创建结算单
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					boolean flag = false;
					CreateSettleFileDocument document = CreateSettleFileDocument.Factory.parse(xmlString);
					CreateSettleFileType type = document.getCreateSettleFile();
					
					try{
						flag = FileCreateHandleService.createSettleFile(type);
					}catch(Exception e){
						log.error("创建结算单过程抛出异常："+e);
					}
					
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("接口回执信息：接口处理",(flag?"成功":"失败")));
					response.getWriter().print(returnXmlString(type.getTrace(), flag));
					
				} else if (xmlString.startsWith("<merMoneyDeductedReq")) { //代付
					concurrentHashMap.put(returnXmlRootElecment(xmlString), true);
					
					MerMoneyDeductedReqDocument document = MerMoneyDeductedReqDocument.Factory.parse(xmlString);
					MerMoneyDeductedReqType type = document.getMerMoneyDeductedReq();
					String tradeCode=type.getTrace();//回执编号
					int timeout = 180;
					boolean dfResult = false;
					try{
						reflectTask = ReflectTask.getInstance(type);
						int dfResultFlag = reflectTask.run("cn.com.chinaebi.dz.service.MerchantFundSettleService", "sendMerBilingAmtDf", timeout, reflectTask, MerMoneyDeductedReqType.class);
//						dfResult = MerchantFundSettleService.sendMerBilingAmtDf(type);
						if(dfResultFlag == 0){
							dfResult = true;
						}
					}catch(Exception e){
						log.error("代付出现异常：" + e.getMessage());
					}
					concurrentHashMap.remove(returnXmlRootElecment(xmlString));
					
					log.info(stringPingJie.getStringPingJie("返回管理平台信息为:",(dfResult?"代付成功":"代付失败")));
					response.getWriter().print(returnXmlString(tradeCode, dfResult));
				}
			} 
		}catch (Exception e) {
			concurrentHashMap.remove(returnXmlRootElecment(xmlString));
			log.error("手动接口处理异常：异常信息……"+e);
		}
	}
	
	
	public static void main(String[] args) {
		String xml = "<manualSummaryOriginal xmlns='http://www.dz.chinaebi.com.cn/webservice'>"
 +"<trace>0099</trace>"
  +"<instId>11</instId>"
  +"<instType>0</instType>"
  +"<summaryDate>2015-03-01</summaryDate>"
+"</manualSummaryOriginal>";
		
		System.out.println(xml.lastIndexOf("</"));
		String str = xml.substring(xml.lastIndexOf("</"), xml.length());
		str = str.substring(2, str.length()-1);
		System.out.println(xml.substring(xml.lastIndexOf("</"), xml.length()));
		System.out.println(str);
		System.out.println("----------------------------");
		int tradeFirstLen = xml.indexOf("<trace>");
		System.out.println("tradeFirstLen = "+tradeFirstLen);
		int tradeLastLen = xml.indexOf("</trace>");
		System.out.println("tradeLastLen = "+tradeLastLen);
		System.out.println(xml.substring(tradeFirstLen, tradeLastLen).replace("<trace>", ""));
		
	}
}
