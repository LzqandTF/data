package com.chinaebi.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.dao.ErrorDataLstDao;
import com.chinaebi.dao.ICommonDao;
import com.chinaebi.dao.YlCupsErrorEntryDao;
import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.ChannelDzCollect;
import com.chinaebi.entity.DuizhangData;
import com.chinaebi.entity.ErrorAuditRecords;
import com.chinaebi.entity.ErrorDataLst;
import com.chinaebi.entity.InnerErrorHandling;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.entity.Merchants;
import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.entity.OriginalData;
import com.chinaebi.entity.RytUpmp;
import com.chinaebi.entity.YlCupsErrorEntry;
import com.chinaebi.exception.SelectException;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.ChannelDzCollectService;
import com.chinaebi.service.DuizhangDataService;
import com.chinaebi.service.ErrorAuditRecordsService;
import com.chinaebi.service.ErrorDataLstService;
import com.chinaebi.service.ErrorTkLstService;
import com.chinaebi.service.InnerErrorHandlingService;
import com.chinaebi.service.MerFundStanceService;
import com.chinaebi.service.MerchantsBalanceService;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.service.RytUpmpService;
import com.chinaebi.service.SettleMerchantMatchService;
import com.chinaebi.utils.Common;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.ReflectUtils;
import com.chinaebi.utils.Ryt_trade_type;
import com.chinaebi.utils.StringPingJie;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 差错数据处理控制类
 * @author wufei
 *
 */
@Controller
public class ErrorDataLstController {
	//记录日志
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//内部差错调整查询
	private static final String QUERY_ERROR_ADJUST = "getNeedAdjustData.do";
	private static final String INNER_ERROR_ADJUST_PAGE = "errorOption/innerErrorAdjust";
	
	//差错总金额统计
	private static final String QUERY_ERROR_TOTAL_MONEY = "queryErrorTotalMoney.do";
	
	//内部差错审批查询
	private static final String QUERY_ERROR_APPROVAL = "getNeedApprovalData.do";
	private static final String ERROR_APPROVAL = "errorOption/innerErrorAdjustApproval";
	
	// 差错调整
	private static final String ERROR_DATA_LST = "updateErrorDataLst.do";
	
	// 差错详情查询
	private static final String QUERY_DETAIL_TRADE_ID = "queryErrorDataLstDetailByTradeId.do";
	
	// 根据流水号查询对账文件数据信息
	private static final String QUERY_DZ_DATA_BY_REQSYSSTANCE = "queryDzDataByReqSysStance.do";
	
	//通过操作
	private static final String APPROVAL_PASS = "updateStatusToPass.do";
	
	//驳回操作
	private static final String APPROVAL_REJECT = "updateStatusToReject.do";
	
	//已处理差错查询
	private static final String OPTION_ERROR_QUERY = "getOptionErrorData.do";
	private static final String OPTION_ERROR_CONTENT = "errorOption/optionErrorSelect";
	
	//已处理差错查询 明细查询
	private static final String OPTION_ERROR_DETAIL_QUERY = "queryOptionErrorDetail.do";
	
	//已处理差错查询 修改备注
	private static final String UPDATE_HANDLER_REMARK = "updateHandlerRemark.do";
	
	// 线上成功交易未对账进入差错
	private static final String ADD_DATA_TO_ERROR_DATA_LST = "addDataToErrorDataLst.do";
	
	// 线下成功交易未对账进入差错
	private static final String ADD_UNDER_ONLINE_DATA_TO_ERROR_DATA_LST = "addUnderLineDataToErrorDataLst.do";
	
	@Autowired
	@Qualifier(value = "errorDataLstService")
	private ErrorDataLstService errorDataLstService;
	
	@Autowired
	@Qualifier(value = "commonClass")
	private CommonClass commonClass;
	
	@Autowired
	@Qualifier(value = "innerErrorHandlingService")
	private InnerErrorHandlingService innerErrorHandlingService;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<OriginalData> errorDataDao;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<RytUpmp> rytCommonDao;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<DuizhangData> duizhangDataDao;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<String> errorMoney;
	
	@Autowired
	@Qualifier(value="commonDao")
	private ICommonDao<Integer> originalDataDao;
	
	//从内存中加载各种配置列表
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "errorAuditRecordsService")
	private ErrorAuditRecordsService errorAuditRecordsService;
	
	@Autowired
	@Qualifier(value = "errorDataLstDao")
	private ErrorDataLstDao errorDataLstDao;
	
	@Autowired
	@Qualifier(value = "ylCupsErrorEntryDao")
	private YlCupsErrorEntryDao ylCupsErrorEntryDao;
	
	@Autowired
	@Qualifier(value = "merchantsService")
	private MerchantsService merchantsService;
	
	@Autowired
	@Qualifier(value = "merFundStanceService")
	private MerFundStanceService merFundStanceService;
	
	@Autowired
	@Qualifier(value = "merchantsBalanceService")
	private MerchantsBalanceService merchantsBalanceService;
	
	@Autowired
	@Qualifier(value = "duizhangDataService")
	private DuizhangDataService duizhangDataService;
	
	@Autowired
	@Qualifier(value = "channelDzCollectService")
	private ChannelDzCollectService channelDzCollectService;
	
	@Autowired
	@Qualifier(value = "settleMerchantMatchService")
	private SettleMerchantMatchService settleMerchantMatchService;
	
	@Autowired
	@Qualifier(value = "errorTkLstService")
	private ErrorTkLstService errorTkLstService;
	
	@Autowired
	@Qualifier(value = "rytUpmpService")
	private RytUpmpService rytUpmpService;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<OriginalData> orgDataDao;
	
	/**
	 * 内部差错调整  分页查询差错数据列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_ERROR_ADJUST, method = RequestMethod.POST)
	public String innerErrorAdjustSelect(ServletRequest request, Model model) {
		logger.info("内部差错调整  进入差错总表数据查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			Page<ErrorDataLst> page = new Page<ErrorDataLst>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			//查询条件
			//交易日期
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			//参考号
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			//订单号
			String additional_data = request.getParameter("additional_data");
			String whtherInnerJs = request.getParameter("whtherInnerJs");
			String js_date = request.getParameter("js_date");
			String handling_id = request.getParameter("handling_id");
			String whetherErroeHandle = request.getParameter("whetherErroeHandle");
			String handling_status = request.getParameter("handling_status");
			String req_sys_stance = request.getParameter("req_sys_stance");
			String error_resource = request.getParameter("error_resource");
			
			String deduct_sys_id = null;
			String dz_data_tableName = null;
			String inst_type = null;
			
			String bank_id = request.getParameter("bank_id");
			String bankId = null;
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bankId = bankInstList[0];
				inst_type = bankInstList[1];
				dz_data_tableName = bankInstList[2];
			}
			String tableName = request.getParameter("channel");
			if (StringUtils.isNotBlank(tableName)) {
				String[] str = tableName.split(",");
				if (str != null && str.length > 0) {
					deduct_sys_id = str[0];
				} 
			}
			
			//将查询参数放入Map中
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(deduct_sys_reference)) {
				map.put("deduct_sys_reference", deduct_sys_reference);
			}
			if (StringUtils.isNotBlank(additional_data)) {
				map.put("additional_data", additional_data);
			}
			if (StringUtils.isNotBlank(whtherInnerJs)) {
				map.put("whtherInnerJs", whtherInnerJs);
			}
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(dz_data_tableName)) {
				map.put("dz_data_tableName", dz_data_tableName);
			}
			if (StringUtils.isNotBlank(js_date)) {
				map.put("js_date", js_date);
			}
			if (StringUtils.isNotBlank(handling_id)) {
				map.put("handling_id", Integer.valueOf(handling_id));
			}
			if (StringUtils.isNotBlank(whetherErroeHandle)) {
				map.put("whetherErroeHandle", whetherErroeHandle);
			}
			if (StringUtils.isNotBlank(handling_status)) {
				map.put("handling_status", handling_status);
			}
			if (StringUtils.isNotBlank(req_sys_stance)) {
				map.put("req_sys_stance", req_sys_stance);
			}
			if (StringUtils.isNotBlank(error_resource)) {
				map.put("error_resource", error_resource);
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bankId", bankId);
			}
			model.addAttribute("needAdjustData", errorDataLstService.queryPageErrorDataLst(page, map));
			model.addAttribute("whtherInnerJs", whtherInnerJs);
			model.addAttribute("bankId", bank_id);
			model.addAttribute("inst_id", tableName);
			model.addAttribute("handling_name", map.get("handling_id"));
			model.addAttribute("error_type", map.get("whetherErroeHandle"));
			model.addAttribute("handling_status", map.get("handling_status"));
			model.addAttribute("error_resource", error_resource);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return INNER_ERROR_ADJUST_PAGE;
	}
	
	/**
	 * 内部差错调整   差错总金额统计
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = QUERY_ERROR_TOTAL_MONEY, method = RequestMethod.POST)
	public void queryErrorTotalMoney(ServletRequest request, ServletResponse response) {
		logger.info("内部差错调整   差错总金额统计如下：");
		PrintWriter out = null;
		double totalErrorMoney = 0;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			//金额条件
			//交易日期
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			//参考号
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			//订单号
			String additional_data = request.getParameter("additional_data");
			String whtherInnerJs = request.getParameter("whtherInnerJs");
			
			String bankId = null;
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bankId = bankInstList[0];
			}
			
			String deduct_sys_id = null;
			String channel = request.getParameter("channel");
			if (StringUtils.isNotBlank(channel)) {
				String[] str = channel.split(",");
				if (str != null && str.length > 2) {
					deduct_sys_id = str[0];
				}
			}
			String js_date = request.getParameter("js_date");
			String handling_id = request.getParameter("handling_id");
			String whetherErroeHandle = request.getParameter("whetherErroeHandle");
			String handling_status = request.getParameter("handling_status");
			String req_sys_stance = request.getParameter("req_sys_stance");
			String error_resource = request.getParameter("error_resource");
			//将查询参数放入Map中
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(deduct_sys_reference)) {
				map.put("deduct_sys_reference", deduct_sys_reference);
			}
			if (StringUtils.isNotBlank(additional_data)) {
				map.put("additional_data", additional_data);
			}
			if (StringUtils.isNotBlank(whtherInnerJs)) {
				map.put("whtherInnerJs", whtherInnerJs);
			}
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
			}
			if (StringUtils.isNotBlank(js_date)) {
				map.put("js_date", js_date);
			}
			if (StringUtils.isNotBlank(handling_id)) {
				map.put("handling_id", Integer.valueOf(handling_id));
			}
			if (StringUtils.isNotBlank(whetherErroeHandle)) {
				map.put("whetherErroeHandle", whetherErroeHandle);
			}
			if (StringUtils.isNotBlank(handling_status)) {
				map.put("handling_status", handling_status);
			}
			if (StringUtils.isNotBlank(req_sys_stance)) {
				map.put("req_sys_stance", req_sys_stance);
			}
			if (StringUtils.isNotBlank(error_resource)) {
				map.put("error_resource", error_resource);
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bankId", bankId);
			}
			//差错的冲正总金额
			String rollBkMoney = errorMoney.queryMoney("ErrorData_Lst.queryRollBkMoney", map);
			String noRollBkMoney = errorMoney.queryMoney("ErrorData_Lst.queryNoRollBkMoney", map);
			double totalMoney = Double.valueOf(rollBkMoney) + Double.valueOf(noRollBkMoney);
			totalErrorMoney = totalMoney / 100;
			logger.info("差错总金额是：" + String.format("%.2f",totalErrorMoney));
			out.print(String.format("%.2f",totalErrorMoney));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 查询内部差错调整审批数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_ERROR_APPROVAL, method = RequestMethod.POST)
	public String innerErrorAdjustApproval(ServletRequest request, Model model) {
		logger.info("内部差错调整审批  进入差错总表数据查询...");
		try {
			//从请求传过来的分页参数
			String curPage = request.getParameter("pageNum");//当前页
			String pageSize = request.getParameter("pageSize");//每页显示的数据量
			
			Page<ErrorDataLst> page = new Page<ErrorDataLst>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String req_sys_stance = request.getParameter("req_sys_stance");
			String additional_data = request.getParameter("additional_data");
			String js_date = request.getParameter("js_date");
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			String handling_id = request.getParameter("handling_id");
			String whetherErroeHandle = request.getParameter("whetherErroeHandle");
			String error_resource = request.getParameter("error_resource");
			
			String deduct_sys_id = null;
			String dz_data_tableName = null;
			String inst_type = null;
			
			String bank_id = request.getParameter("bank_id");
			String bankId = null;
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bankId = bankInstList[0];
				inst_type = bankInstList[1];
				dz_data_tableName = bankInstList[2];
			}
			
			String tableName = request.getParameter("channel");
			if (StringUtils.isNotBlank(tableName)) {
				String[] str = tableName.split(",");
				if (str != null && str.length > 0) {
					deduct_sys_id = str[0];
				}
			}
			
			Map<String, Object>  map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(req_sys_stance)) {
				map.put("req_sys_stance", req_sys_stance);
			}
			if (StringUtils.isNotBlank(additional_data)) {
				map.put("additional_data", additional_data);
			}
			if (StringUtils.isNotBlank(js_date)) {
				map.put("js_date", js_date);
			}
			if (StringUtils.isNotBlank(deduct_sys_reference)) {
				map.put("deduct_sys_reference", deduct_sys_reference);
			}
			if (StringUtils.isNotBlank(handling_id)) {
				map.put("handling_id", Integer.valueOf(handling_id));
			}
			if (StringUtils.isNotBlank(whetherErroeHandle)) {
				map.put("whetherErroeHandle", whetherErroeHandle);
			}
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(dz_data_tableName)) {
				map.put("dz_data_tableName", dz_data_tableName);
			}
			if (StringUtils.isNotBlank(error_resource)) {
				map.put("error_resource", error_resource);
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bankId", bankId);
			}
			Page<ErrorDataLst> errorDataLst = errorDataLstService.queryPageApprovalData(page, map);
			List<InnerErrorHandling> list = innerErrorHandlingService.queryInnerErrorHandlingAll();
			model.addAttribute("innerErrorHandlingList", list);
			model.addAttribute("needApprovalData", errorDataLst);
			model.addAttribute("bankId", bank_id);
			model.addAttribute("inst_id", tableName);
			model.addAttribute("handling_name", handling_id);
			model.addAttribute("trade_status", whetherErroeHandle);
			model.addAttribute("error_resource", error_resource);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("内部差错调整审批  数据查询出错：" + e.getMessage());
		}
		return ERROR_APPROVAL;
	}
	
	@RequestMapping(value = ERROR_DATA_LST, method = RequestMethod.POST)
	public void updateErrorDataLst(HttpServletRequest request, ServletResponse response) throws Exception {
		boolean flag = false;
		String trade_id = request.getParameter("trade_id");
		String operator = request.getParameter("operator");
		String handling_id = request.getParameter("handling_id");
		String handler_remark = request.getParameter("handler_remark");
		String js_date = request.getParameter("js_date");
		String deduct_sys_id = request.getParameter("deduct_sys_id");
		
		ErrorDataLst errorDataLst = new ErrorDataLst();
		errorDataLst.setTrade_id(trade_id);
		errorDataLst.setHandling_id((Integer.valueOf(handling_id)));
		errorDataLst.setHandler_remark(handler_remark);
		errorDataLst.setHandling_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		errorDataLst.setJs_date(js_date);
		errorDataLst.setOperator(operator);
		if (StringUtils.isNotBlank(deduct_sys_id) && !"null".equals(deduct_sys_id)) {
			errorDataLst.setDeduct_sys_id(Integer.valueOf(deduct_sys_id));
		}
		int effectNum = errorDataLstService.updateErrorHandleMethod(errorDataLst);
		if (effectNum > 0) {
			flag = true;
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
			
			//将调整后的数据保存到差错审计表中
			ErrorDataLst errDataLst = queryDetailByTradeId(trade_id);
			if (errDataLst != null) {
				ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, errDataLst);
				errorAuditRecords.setOperator(errDataLst.getOperator());
				errorAuditRecords.setBusiness_type(0);
				errorAuditRecords.setOperation_type(0);
				errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
			}
		}
	}
	
	/**
	 * 差错调整   详情查询
	 * @param request 获取页面参数
	 * @return
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = QUERY_DETAIL_TRADE_ID, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDetailByTradeId(ServletRequest request) {
		logger.info("进入差错调整详情信息查询...");
		Map<String, Object> map = null;
		try {
			String tradeId = request.getParameter("tradeId");
			ErrorDataLst errorDataLst = errorDataLstService.queryDetailByTradeId(tradeId);
			if (errorDataLst != null) {
				map = new HashMap<String, Object>();
				map.put("errorDataLst", errorDataLst);
				
				//交易时间
				String tradeTime = errorDataLst.getTrade_time();
				
				//扣款渠道ID
				int deductSysId = errorDataLst.getDeduct_sys_id();
				
				//获取交易流水号，用于获取原始交易数据中的相关交易
				String reqSysStance = errorDataLst.getReq_sys_stance();
				//获取扣款流水号，用于获取对账文件中的相关交易
				int rollBk = errorDataLst.getDeduct_roll_bk();
				String deductSysReference = null;
				if(rollBk == 0){
					deductSysReference = errorDataLst.getDeduct_sys_reference();
				}else{
					deductSysReference = errorDataLst.getDeduct_rollbk_sys_reference();
				}
				int inst_type = errorDataLst.getInst_type();
				map.put("instType", inst_type);
				
				//获取原始交易表名及对账文件表名
				BankInst bankInst = dataManagerInit.getBankInstByBankId(errorDataLst.getBank_id());
				if (bankInst != null) {
					map.put("original_data_tableName", bankInst.getOriginal_data_tableName());
					map.put("dz_data_tableName", bankInst.getDz_data_tableName());
				}
				InstInfo instInfo = dataManagerInit.getInstInfoById(deductSysId,inst_type);
				
				int errorResource = errorDataLst.getError_resource();
				if (errorResource == 0) {
					if (inst_type == 0) {
						List<OriginalData> originalDataList = null;
						map.put("reqSysStance", reqSysStance);
						map.put("deductSysReference", deductSysReference.substring(deductSysReference.length()-6, deductSysReference.length()));
						map.put("tradeTime", StringPingJie.getInstance().getStringPingJie(tradeTime.substring(0, 10), " 00:00:00"));
						map.put("endTime", StringPingJie.getInstance().getStringPingJie(tradeTime.substring(0, 10), " 23:59:59"));
						map.put("originalTransInfo", errorDataLst.getOriginal_trans_info());
						// 线下原始相关交易
						originalDataList = errorDataDao.queryForList("Original_Data.queryByReqSysStanceAndTradeTime", map);
						for(OriginalData originalData : originalDataList) {
							int deductRollBk = originalData.getDeduct_roll_bk();
							String deductSysTime = originalData.getDeduct_sys_time();
							String deductRollbkSysTime = originalData.getDeduct_rollbk_sys_time();
							String reqProcess = originalData.getReq_process();
							int tradeMsgType = originalData.getTrademsg_type();
							String terminalInfo = originalData.getTerminal_info();
							//如果冲正，则显示冲正时间
							if (deductRollBk == 0) {
								if (StringUtils.isNotBlank(deductSysTime)) {
									originalData.setDeduct_sys_time(deductSysTime.substring(0, 19));
								}
							} else {
								//没有冲正，则显示扣款时间
								if (StringUtils.isNotBlank(deductRollbkSysTime)) {
									originalData.setDeduct_rollbk_sys_time(deductRollbkSysTime.substring(0, 19));
								}
							}
							originalData.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(reqProcess,tradeMsgType));
							originalData.setTradeName(commonClass.getTradeNameByTerminalInfo(terminalInfo));
						}
						map.put("originalDataList", originalDataList);
					} else {
						List<RytUpmp> rytList = null;
						map.put("tradeTime", tradeTime.substring(0, 10).replace("-", ""));
						map.put("tseq",  errorDataLst.getTrade_id());
						rytList = rytCommonDao.queryForList("RytUpmp.queryByTseqAndSysDate", map);
						if (rytList != null && rytList.size() > 0) {
							for (RytUpmp rytUpmp : rytList) {
								rytUpmp.setSys_date(DateUtil.parseTimePattern(rytUpmp.getSys_date(), rytUpmp.getSys_time()));
								rytUpmp.setTradeType(Ryt_trade_type.getRytTradeName(rytUpmp.getType()));
							}
						} 
						map.put("rytList", rytList);
					}
				}

				// 获取对账文件中的相关交易
				List<DuizhangData> duizhangDataList = null;
				map.put("tradeTime", tradeTime.substring(0, 10).replace("-", ""));
				Object dzDataColumn = ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column());
				if (dzDataColumn != null && !"null".equals(dzDataColumn) && !"".equals(dzDataColumn)) {
					if (inst_type == 0) {
						map.put("reqSysStance", dzDataColumn);
						map.put("origDataStance", errorDataLst.getOriginal_trans_info());
						map.put("deductSysReference", deductSysReference);
						duizhangDataList = duizhangDataDao.queryForList("Duizhang_Data.queryDuizhangDataByReqSysStance", map);
					} else {
						if (deductSysId != 0) {
							map.put("id", dzDataColumn);
							duizhangDataList = duizhangDataService.queryOnlineDzFileData(map);
						}
					}
				}
				map.put("duizhangDataList", duizhangDataList);
			} else {
				map.put("errorDataLst", null);
				map.put("originalDataList", null);
				map.put("rytList", null);
				map.put("rytRefundLogsList", null);
				map.put("duizhangDataList", null);
			}
		} catch (Exception e) {
			logger.error("差错调整详情信息查询出现异常：" + e.getMessage());
		}
		return map;
	}
	
	/**
	 * 根据交易流水查询对账文件数据信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = QUERY_DZ_DATA_BY_REQSYSSTANCE, method = RequestMethod.POST)
	public void queryDzFileDataByReqSysStance(ServletRequest request, ServletResponse response, ErrorDataLst errorDataLst){
		PrintWriter out = null;
		String msgHead = null;
		try {
			out = response.getWriter();
			int deduct_sys_id = errorDataLst.getDeduct_sys_id();
			int inst_type = errorDataLst.getInst_type();
			String tradeTime = errorDataLst.getTrade_time();
			
			Map<String, Object> map = new HashMap<String, Object>();
			InstInfo instInfo = dataManagerInit.getInstInfoById(deduct_sys_id, inst_type);
			BankInst bankInst = dataManagerInit.getBankInstByBankId(instInfo.getBank_id());
			if(instInfo != null){
				map.put("dz_data_tableName", bankInst.getDz_data_tableName());
				map.put("instType", inst_type);
				map.put("reqSysStance", ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column()));
			}
			if (StringUtils.isNotBlank(tradeTime)) {
				map.put("tradeTime", tradeTime.substring(0, 10).replace("-", ""));
			}
			
			List<DuizhangData> list = duizhangDataDao.queryForList("Duizhang_Data.queryDzFileDataByReqSysStance", map);
			if(list.size() > 0 && list != null){
				StringBuffer buffer = new StringBuffer("[");
				for (DuizhangData duizhangData : list) {
					String trade_amount = duizhangData.getTradeAmount();
					double tradeAmount = 0d;
					if (StringUtils.isNotBlank(trade_amount)) {
						tradeAmount = Double.valueOf(trade_amount);
					}
					buffer.append("{");
						buffer.append("reqSysStance : '" + duizhangData.getReqSysStance() + "',");
						buffer.append("reqTime : '" + duizhangData.getReqTime() + "',");
						buffer.append("outAccount : '" + duizhangData.getOutAccount() + "',");
						buffer.append("tradeAmount : '" + String.format("%.2f", tradeAmount) + "',");
						buffer.append("tradeFee : '" + duizhangData.getTradeFee() + "',");
						buffer.append("process : '" + duizhangData.getProcess() + "',");
						buffer.append("merCode : '" + duizhangData.getMerCode() + "',");
						buffer.append("termId : '" + duizhangData.getTermId() + "',");
						buffer.append("deductSysReference: '" + duizhangData.getDeductSysReference() + "',");
						buffer.append("deduct_stlm_date : '" + duizhangData.getDeduct_stlm_date() + "'");
					buffer.append("}");
				}
				buffer.append("]");
				out.print(buffer.toString());
				return;
			}else{
				logger.error("查询对账文件数据不存在");
				msgHead = "data_error";
			}
			out.print(msgHead);
		} catch (Exception e) {
			logger.error("根据流水号获取对账文件数据明细错误 :"+e.getMessage());
		}
	}
	
	/**
	 * 审批通过操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = APPROVAL_PASS, method = RequestMethod.POST)
	@ResponseBody
	public Integer approvalPass(HttpServletRequest request, ServletResponse response) throws Exception {
		String trade_id = request.getParameter("trade_id");
		String deduct_sys_id = request.getParameter("deduct_sys_id");
		String audit_operator = request.getParameter("audit_operator");
		if (StringUtils.isNotBlank(trade_id)) {
			ErrorDataLst errorData = new ErrorDataLst();
			errorData.setTrade_id(trade_id);
			errorData.setDeduct_sys_id(Integer.valueOf(deduct_sys_id));
			errorData.setCheck_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			errorData.setAudit_operator(audit_operator);
			try {
				int effectNum = errorDataLstService.updateHandlingStatus(request, errorData);
				if (effectNum > 0) {
					//将审批后的数据保存到差错审计表中
					ErrorDataLst errDataLst = queryDetailByTradeId(trade_id);
					if (errDataLst != null) {
						ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, errDataLst);
						errorAuditRecords.setOperator(errDataLst.getAudit_operator());
						errorAuditRecords.setBusiness_type(1);
						errorAuditRecords.setOperation_type(1);
						errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
					}
					//审批时如果是银联的并且是退款的则插入银联差错数据中
					InstInfo instInfo = dataManagerInit.getInstInfoById(errDataLst.getDeduct_sys_id(),errDataLst.getInst_type());
					if(instInfo != null){
						if(instInfo.getWhether_outer_error_dz() == 1) {
							ErrorDataLst errorDataLst = errorDataLstDao.queryErrorDataLstDetails(errorData.getTrade_id());
							//如果是内部差错处理方式是2-->退款 进入银联差错处理
							if(errorDataLst.getHandling_id() == 2){
								YlCupsErrorEntry ylCupsErrorEntry = new YlCupsErrorEntry();
								ylCupsErrorEntry.setId(errorDataLst.getTrade_id());
								ylCupsErrorEntry.setOut_account(errorDataLst.getOut_account());
								ylCupsErrorEntry.setMer_name(errorDataLst.getMer_name());
								ylCupsErrorEntry.setTrade_time(errorDataLst.getTrade_time());
								ylCupsErrorEntry.setTrade_result(errorDataLst.getTrade_result());
								ylCupsErrorEntry.setDeduct_sys_reference(errorDataLst.getDeduct_sys_reference());
								if(errorDataLst.getError_resource() == 1){
									ylCupsErrorEntry.setReqSysStance(errorDataLst.getReq_sys_stance());
								}else{
									if(errorDataLst.getDeduct_roll_bk() == 1){
										ylCupsErrorEntry.setReqSysStance(errorDataLst.getDeduct_roll_bk_stance());
									}else
										ylCupsErrorEntry.setReqSysStance(errorDataLst.getDeduct_sys_stance());
								}
								double tradeAmount = errorDataLst.getTrade_amount() * 100;
								String amount = String.format("%.2f",tradeAmount);
								ylCupsErrorEntry.setTradeAmount(amount);
								ylCupsErrorEntry.setHandling_id(errorDataLst.getHandling_id());
								ylCupsErrorEntry.setAcqInstIdCode(errorDataLst.getAcqInstIdCode());
//								ylCupsErrorEntry.setReason_code(errorDataLst.getReason_id());
								ylCupsErrorEntry.setDeductStlmDate(errorDataLst.getDeduct_stlm_date());
								//交易类别
								ylCupsErrorEntry.setTrade_category(commonClass.getTradeNameByTerminalInfo(errorDataLst.getTerminal_info()));
								ylCupsErrorEntry.setDeduct_sys_id(errorDataLst.getDeduct_sys_id());
								ylCupsErrorEntry.setTrade_source("内部差错");
								ylCupsErrorEntry.setTrade_status(0);
//								ylCupsErrorEntry.setTurnDown_remark(errorDataLst.getTurnDown_remark());
								ylCupsErrorEntry.setEntering_time((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
								ylCupsErrorEntry.setOperator(errorDataLst.getOperator());
								ylCupsErrorEntry.setProcess(errorDataLst.getReq_process());
								ylCupsErrorEntry.setTradeMsgType(errorDataLst.getTrademsg_type());
								ylCupsErrorEntry.setInst_type(errorDataLst.getInst_type());
								int insertNum = ylCupsErrorEntryDao.addYlcupsErrorEntry(ylCupsErrorEntry);
								if (insertNum == 0) {
									logger.error("审批通过操作失败...");
								} else {
									//将直接录入的数据保存到差错审计表中
									ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, ylCupsErrorEntry);
									errorAuditRecords.setOperator(ylCupsErrorEntry.getOperator());
									errorAuditRecords.setError_resource(2);
									errorAuditRecords.setBusiness_type(3);
									errorAuditRecords.setOperation_type(3);
									errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
								}
							}
						}
					} else {
						logger.error("获取机构信息为空");
					}
					
					if(errDataLst.getHandling_id() == 1){//正常结算
						//TODO
						//根绝差错数据中的电银商户号 查询是否有配置相应的结算商户号，若配置结算商户号，则以结算商户号为维度进行保存T+1，保存商户资金流水，调整商户余额操作
						String settle_mer_code = settleMerchantMatchService.querySettleMerCodeByDyMerCode(errDataLst.getReq_mer_code());
						
						if(StringUtils.isBlank(settle_mer_code)){
							settle_mer_code = errDataLst.getReq_mer_code();
						}
						
						//TODO
						try{
							merchantsService.updateMerchantFundStanceAndBalanceAndStatistics(settle_mer_code, errDataLst.getBank_id(), errDataLst.getDeduct_sys_id(), 
									errDataLst.getInst_type(),errDataLst.getTrade_amount(), errDataLst.getMer_fee(), errDataLst.getZf_fee(), 
									StringUtils.isBlank(errDataLst.getZf_file_fee())?0.00d:Double.valueOf(errDataLst.getZf_file_fee()), Integer.valueOf(errDataLst.getJs_date().replaceAll("-", "")), 
									errDataLst.getDeduct_sys_stance(), DataStatus.ERROR_ADJUST_CONSUME, errDataLst.getWhetherTk(), 3);
						}catch(Exception e){
							logger.error("差错正常结算,修改商户余额、资金流水、T+1数据表操作抛出异常:"+e);
						}
						
						try {
							if(instInfo != null){
								BankInst bankInst = dataManagerInit.getBankInstByBankId(instInfo.getBank_id());
								Map<String,Object> map = new HashMap<String,Object>();
								map.put("inst_type", instInfo.getInst_type());
								map.put("deduct_sys_stance", errDataLst.getDeduct_sys_stance());
								map.put("deduct_roll_bk", errDataLst.getDeduct_roll_bk());
								map.put("deduct_stlm_date", errDataLst.getDeduct_stlm_date().length()>19?errDataLst.getDeduct_stlm_date().substring(0, 19):errDataLst.getDeduct_stlm_date());
								map.put("originalDataTableName", bankInst.getOriginal_data_tableName());
								map.put("whetherQs", DataStatus.data_qs);
								//修改原笔交易数据 是否清算状态为 可清算
								originalDataDao.updateOriginalDataQsStatus("Original_Data.updateOriginalDataQsStatus", map);
							}else{
								logger.debug("差错正常结算时,无法获取渠道相关信息,无法获取原始交易数据表,不能进行修改原交易清算状态操作");
							}
						} catch (Exception e) {
							logger.error("差错正常结算,修改原笔交易数据清算状态为可结算操作抛出异常:"+e);
						}
						
						try {
							if(StringUtils.isNotBlank(trade_id)){
								//修改渠道对账统计表数据 是否清算状态为 可清算
								Map<String,Object> parameterMap = new HashMap<String,Object>();
								parameterMap.put("js_date", errDataLst.getJs_date().replaceAll("-", ""));
								parameterMap.put("trade_id", trade_id);
								channelDzCollectService.updateChannelDzCollectDataQsStatus(parameterMap);
							}else{
								logger.debug("差错正常结算时,获取差错数据主键ID为空,不能进行修改对账总表交易数据清算状态操作");
							}
						} catch (Exception e) {
							logger.error("差错正常结算,修改对账总表交易数据清算状态为可结算操作抛出异常:"+e);
						}
					}
					
					// 如果该条差错处理方式是退款，则将该条审批之后的数据保存至差错退款表中
					logger.info("开始将退款审批数据保存至差错退款表中...");
					String whetherTk = Common.getProperties("whetherTk");
					if (StringUtils.isNotBlank(whetherTk)) {
						logger.info("application 配置保存到差错退款表中的数据标识 ：" + whetherTk);
						Map<String, Object> map = new HashMap<String, Object>();
						int num = 0;
						if ("0".equals(whetherTk)) {
							if (errDataLst.getHandling_id() == 2) {
								map.put("trade_id", errDataLst.getTrade_id());
								map.put("trade_time", errDataLst.getTrade_time());
								map.put("trade_amount", errDataLst.getTrade_amount());
								if (errDataLst.getInst_type() == 0) {
									map.put("deduct_sys_id", instInfo.getGate());
								} else {
									map.put("deduct_sys_id", errDataLst.getDeduct_sys_id());
								}
								map.put("inst_type", errDataLst.getInst_type());
								map.put("handling_id", errDataLst.getHandling_id());
								map.put("bank_id", errDataLst.getBank_id());
								num = errorTkLstService.addErrorTkLst(map);
							}
						} else {
							map.put("trade_id", errDataLst.getTrade_id());
							map.put("trade_time", errDataLst.getTrade_time());
							map.put("trade_amount", errDataLst.getTrade_amount());
							if (errDataLst.getInst_type() == 0) {
								map.put("deduct_sys_id", instInfo.getGate());
							} else {
								map.put("deduct_sys_id", errDataLst.getDeduct_sys_id());
							}
							map.put("inst_type", errDataLst.getInst_type());
							map.put("handling_id", errDataLst.getHandling_id());
							map.put("bank_id", errDataLst.getBank_id());
							num = errorTkLstService.addErrorTkLst(map);
						}
						if (num > 0) {
							logger.info("已成功将退款审批数据保存至差错退款表中");
						}
					}
				}
				return effectNum;
			} catch (Exception e) {
				logger.error("审批操作失败" + e.getMessage());
				return -1;
			}
		}else {
			logger.error("通过操作(缺少主键编号)");
			return -1;
		}
	}
	
	/**
	 * 已处理差错查询
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = APPROVAL_REJECT, method = RequestMethod.POST)
	public void updateApproval(HttpServletRequest request, ServletResponse response) throws Exception {
		boolean flag = false;
		response.setCharacterEncoding("utf-8");
		String trade_id = request.getParameter("trade_id");
		String reason = request.getParameter("reason");
		String audit_operator = request.getParameter("audit_operator");
		
		logger.info(trade_id +"--"+reason);
		if(StringUtils.isNotBlank(trade_id) || StringUtils.isNotBlank(reason)){
			ErrorDataLst errorDataLst=new ErrorDataLst();
			errorDataLst.setTrade_id(trade_id);
			errorDataLst.setTurnDown_remark(reason);
			errorDataLst.setAudit_operator(audit_operator);
			int effectNum = errorDataLstService.updateApproval(errorDataLst);
			if (effectNum > 0) {
				flag = true;
				PrintWriter out = response.getWriter();
				out.print(flag);
				out.flush();
				out.close();
				
				//将调整后的数据保存到差错审计表中
				ErrorDataLst errDataLst = queryDetailByTradeId(trade_id);
				if (errDataLst != null) {
					ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, errDataLst);
					errorAuditRecords.setOperator(errDataLst.getOperator());
					errorAuditRecords.setBusiness_type(2);
					errorAuditRecords.setOperation_type(2);
					errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
				}
			}
		}else {
			logger.error("驳回操作(缺少主键编号) or 驳回原因不能为空");
		}
	}
	
	/**
	 * 已处理差错查询  分页获取已处理的差错数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = OPTION_ERROR_QUERY, method = RequestMethod.POST)
	public String queryOptionErrorData(ServletRequest request, Model model) {
		logger.info("已处理差错查询  进入差错总表数据查询...");
		try {
			//分页
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数
			String pageSize = request.getParameter("pageSize");
			
			Page<ErrorDataLst> page = new Page<ErrorDataLst>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			//查询条件
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			String js_date = request.getParameter("js_date");
			String additional_data = request.getParameter("additional_data");
			String req_sys_stance = request.getParameter("req_sys_stance");
			String handling_id = request.getParameter("handling_id");
			String check_date = request.getParameter("check_date");
			
			String bankId = null;
			String bank_id = request.getParameter("bank_id");
			String inst_type = null;
			
			if (StringUtils.isNotBlank(bank_id)) {
				String[] bankInstList = bank_id.split(",");
				if (bankInstList != null && bankInstList.length > 0) {
					bankId = bankInstList[0];
					inst_type = bankInstList[1];
				}
			}
			String inst_info = request.getParameter("channel");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(deduct_sys_reference)) {
				map.put("deduct_sys_reference", deduct_sys_reference);
			}
			if (StringUtils.isNotBlank(js_date)) {
				map.put("js_date", js_date);
			}
			if (StringUtils.isNotBlank(inst_info)) {
				String[] instInfo = inst_info.split(",");
				map.put("deduct_sys_id", instInfo[0]);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(additional_data)) {
				map.put("additional_data", additional_data);
			}
			if (StringUtils.isNotBlank(req_sys_stance)) {
				map.put("req_sys_stance", req_sys_stance);
			}
			if (StringUtils.isNotBlank(handling_id)) {
				map.put("handling_id", handling_id);
			}
			if (StringUtils.isNotBlank(check_date)) {
				map.put("check_date", check_date);
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bankId", bankId);
			}
			
			Page<ErrorDataLst> pageErrorDataLst = errorDataLstService.queryOptionErrorLst(page, map);
			StringBuffer sb = new StringBuffer();
			for (ErrorDataLst errorDataLst : pageErrorDataLst) {
				//账号
				String outAccount = errorDataLst.getOut_account();
				//处理码
				String process = errorDataLst.getReq_process();
				//交易消息类型
				int trade_msg_type = errorDataLst.getTrademsg_type();
				int handlingId = errorDataLst.getHandling_id();
				if (StringUtils.isNotBlank(outAccount) && outAccount.length() > 10) {
					sb.setLength(0);
					sb.append(outAccount.substring(0, 6)).append("***").append(outAccount.substring(outAccount.length() - 4, outAccount.length()));
					errorDataLst.setOut_account(sb.toString());
				}
				//交易类型
				errorDataLst.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
				//处理方式
				errorDataLst.setHandling_name(commonClass.getInnerHandlingNameByHandlingId(handlingId));
				//备注
				String remark = errorDataLst.getHandler_remark();
				if (StringUtils.isNotBlank(remark)) {
					if (remark.length() > 3)
						errorDataLst.setHandler_remark(remark.substring(0, 3) + "...");
					else 
						errorDataLst.setHandler_remark(remark);
				}
			}
			model.addAttribute("optionErrorData", pageErrorDataLst);
			model.addAttribute("bankId", bank_id);
			model.addAttribute("inst_id",inst_info);
			model.addAttribute("handling_name", map.get("handling_id"));
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.info("已处理差错查询出错：" + e.getMessage());
		}
		return OPTION_ERROR_CONTENT;
	}
	
	/**
	 * 已处理差错查询 明细查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = OPTION_ERROR_DETAIL_QUERY, method = RequestMethod.POST)
	@ResponseBody
	public ErrorDataLst queryOptionErrorDetail(ServletRequest request) {
		ErrorDataLst errorDataLst = null;
		try {
			String trade_id = request.getParameter("trade_id");
			if (StringUtils.isNotBlank(trade_id)) {
				errorDataLst = errorDataLstService.queryErrorDataLstDetails(trade_id);
				String tradeFee = errorDataLst.getTrade_fee();
				if (StringUtils.isNotBlank(tradeFee)) {
					double trade_fee = Double.valueOf(tradeFee);
					double fee = trade_fee / 100;
					errorDataLst.setTrade_fee(String.format("%.2f", fee));
				}
			}
		} catch (Exception e) {
			logger.error("根据trade_id查询差错调整明细出错：" + e.getMessage());
		}
		return errorDataLst;
	}
	
	@RequestMapping(value = UPDATE_HANDLER_REMARK, method = RequestMethod.POST)
	@ResponseBody
	public Integer updateHandlerRemark(ServletRequest request) {
		String trade_id = request.getParameter("trade_id");
		String handlerRemark = request.getParameter("remark");
		if (StringUtils.isNotBlank(trade_id)) {
			ErrorDataLst errorDataLst=new ErrorDataLst();
			errorDataLst.setTrade_id(trade_id);
			errorDataLst.setHandler_remark(handlerRemark);
			try {
				int effectNum = errorDataLstService.updateHandlerRemark(errorDataLst);
				return effectNum;
			} catch (Exception e) {
				logger.error("修改备注失败" + e.getMessage());
				return -1;
			}
		}else {
			logger.error("修改备注(缺少主键编号)");
			return -1;
		}
	}
	
	/**
	 * 获取差错详情信息
	 * @param tradeId
	 * @return
	 */
	public ErrorDataLst queryDetailByTradeId(String tradeId) {
		ErrorDataLst errDataLst = null;
		try {
			errDataLst = errorDataLstService.queryErrorDataLstDetails(tradeId);
			if (errDataLst == null) {
				throw new SelectException("errorDataLstService.queryErrorDataLstDetails(tradeId) 查询差错详情信息为NULL");
			}
		} catch (Exception e) {
			logger.error("查询差错详情数据出错：" + e.getMessage());
		}
		return errDataLst;
	}
	
	@RequestMapping(value = ADD_DATA_TO_ERROR_DATA_LST, method = RequestMethod.POST)
	@ResponseBody
	public int addDataToErrorDataLst(ServletRequest request) {
		logger.info("开始将线上成功未对账交易数据进入差错操作...");
		int effectNum = 0;
		try {
			ErrorDataLst errorDataLst = new ErrorDataLst();
			String tseq = request.getParameter("tseq");
			String sys_date = request.getParameter("sys_date");
			String card_no = request.getParameter("card_no");
			String amt = request.getParameter("amount");
			String merFee = request.getParameter("mer_fee");
			String gid = request.getParameter("gid");
			String zf_fee = request.getParameter("zf_fee");
			String zf_file_fee = request.getParameter("zf_file_fee");
			String oid = request.getParameter("oid");
			String zf_fee_bj = request.getParameter("zf_fee_bj");
			String whetherErroeHandle = request.getParameter("whetherErroeHandle");
			String mid = request.getParameter("mid");
			String bankId = request.getParameter("bank_id");
			String type = request.getParameter("type");
			String gate = request.getParameter("gate");
			String out_user_id = request.getParameter("out_user_id");
			String in_user_id = request.getParameter("in_user_id");
			String p14 = request.getParameter("p14");
			String whetherValid = request.getParameter("whetherValid");
			
			int bank_id = 0;
			String skTableName = null;
			String[] bankInstList = bankId.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bank_id = Integer.valueOf(bankInstList[0]);
				skTableName = bankInstList[2];
			}
			
			errorDataLst.setTrade_id(tseq);
			errorDataLst.setTrade_time(sys_date);
			errorDataLst.setOut_account(card_no);
			errorDataLst.setOut_acc_valid_time(sys_date);
			
			if (StringUtils.isNotBlank(amt)) {
				Double amount = Double.valueOf(amt) * 10000;
				errorDataLst.setTrade_amount(amount.intValue());
			}
			
			double mer_fee = 0d;
			if (StringUtils.isNotBlank(merFee)) {
				mer_fee = Double.valueOf(merFee);
			}
			errorDataLst.setMer_fee(mer_fee);
			errorDataLst.setReq_sys_stance(tseq);
			errorDataLst.setTrade_result(0);
			errorDataLst.setReq_response("00");
			errorDataLst.setDeduct_result(0);
			errorDataLst.setDeduct_sys_response("00");
			errorDataLst.setDeduct_sys_id(Integer.valueOf(gid));
			errorDataLst.setDeduct_sys_stance(tseq);
			errorDataLst.setDeduct_roll_bk(DataStatus.NO_ROLL_BK);
			errorDataLst.setReq_time(sys_date);
			errorDataLst.setDeduct_stlm_date(sys_date);
			errorDataLst.setDeduct_sys_time(sys_date);
			errorDataLst.setTrademsg_type(2);
			errorDataLst.setReq_process("910000");
			errorDataLst.setWhetherQs(DataStatus.no_clean);
			errorDataLst.setBk_chk(3);
			errorDataLst.setError_resource(DataStatus.ORG_ERROR_RESOURCE);
			errorDataLst.setInst_type(DataStatus.instType_1);
			errorDataLst.setZf_fee(Double.valueOf(zf_fee));
			errorDataLst.setZf_file_fee(zf_file_fee);
			errorDataLst.setAdditional_data(oid);
			errorDataLst.setTrade_type(2);
			errorDataLst.setZf_fee_bj(Integer.valueOf(zf_fee_bj));
			errorDataLst.setWhetherErroeHandle(Integer.valueOf(whetherErroeHandle));
			errorDataLst.setReq_mer_code(mid);
			errorDataLst.setBank_id(bank_id);
			errorDataLst.setWhetherValid(Integer.valueOf(whetherValid));
			
			effectNum = errorDataLstService.addDataToErrorDataLst(errorDataLst);
			if (effectNum > 0) {
				logger.info("已成功将线上成功交易未对账数据移至差错");
				
				Map<String, Object> map = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(skTableName)) {
					map.put("skTableName", skTableName);
				}
				if (StringUtils.isNotBlank(tseq)) {
					map.put("tseq", tseq);
				}
				effectNum = rytUpmpService.updateDataByTesq(map);
				if (effectNum > 0) {
					logger.info("根据主键tesq修改线上成功交易未对账数据状态成功");
					
					ChannelDzCollect channelDzCollect = new ChannelDzCollect();
					channelDzCollect.setTrade_id(tseq);
					channelDzCollect.setOut_account(card_no);
					
					channelDzCollect.setTrade_amount(Double.valueOf(amt));
					channelDzCollect.setTrade_result(2);
					channelDzCollect.setReq_sys_stance(tseq);
					channelDzCollect.setReq_mer_code(mid);
					channelDzCollect.setDeduct_sys_id(Integer.valueOf(gid));
					channelDzCollect.setDeduct_sys_stance(tseq);
					channelDzCollect.setDeduct_mer_code(mid);
					
					String sysDate = sys_date.substring(0, 10).replace("-", "").trim();
					String sysTime = sys_date.substring(10, sys_date.length()).replace(":", "").trim();
					
					channelDzCollect.setTrade_time(StringPingJie.getInstance().getStringPingJie(sysDate, sysTime));
					channelDzCollect.setDeduct_sys_time(StringPingJie.getInstance().getStringPingJie(sysDate, sysTime));
					channelDzCollect.setDeduct_stlm_date(DateUtil.formatDate(new Date(), "yyyyMMdd"));
					channelDzCollect.setDeduct_roll_bk(0);
					channelDzCollect.setTrade_type(type);
					channelDzCollect.setBk_chk(3);
					channelDzCollect.setWhetherErroeHandle(Integer.valueOf(whetherErroeHandle));
					channelDzCollect.setWhetherQs(DataStatus.no_clean);
					channelDzCollect.setMer_fee(mer_fee);
					channelDzCollect.setZf_fee(Double.valueOf(zf_fee));
					channelDzCollect.setZf_file_fee(zf_file_fee);
					channelDzCollect.setZf_fee_bj(Integer.valueOf(zf_fee_bj));
					channelDzCollect.setInstType(DataStatus.instType_1);
					channelDzCollect.setGate(Integer.valueOf(gate));
					channelDzCollect.setSettle_code(mid);
					
					InstInfo instInfo = dataManagerInit.getInstInfoById(Integer.valueOf(gid), DataStatus.instType_1);
					String instName = null;
					if (instInfo != null) {
						instName = instInfo.getName();
					}
					channelDzCollect.setInst_name(instName);
					channelDzCollect.setOid(oid);
					channelDzCollect.setSys_date(Integer.valueOf(DateUtil.formatDate(new Date(), "yyyyMMdd")));
					channelDzCollect.setBank_id(bank_id);
					channelDzCollect.setJs_date(Integer.valueOf(DateUtil.formatDate(new Date(), "yyyyMMdd")));
					channelDzCollect.setOut_user_id(out_user_id);
					channelDzCollect.setIn_user_id(in_user_id);
					channelDzCollect.setRemark(p14);
					channelDzCollect.setWhetherValid(Integer.valueOf(whetherValid));
					
					int num = channelDzCollectService.addNoDzDataToChannelDzCollect(channelDzCollect);
					if (num > 0) {
						logger.info("已成功将线上成功交易未对账数据保存至对账总表");
					}
					
					//根绝交易中的商户号 查询是否有配置相应的结算商户号，若配置结算商户号，则以结算商户号为维度进行保存T+1，保存商户资金流水，调整商户余额操作
					String settle_mer_code = settleMerchantMatchService.querySettleMerCodeByDyMerCode(mid);
					if(StringUtils.isBlank(settle_mer_code)){
						settle_mer_code = mid;
					}
					
					Merchants merchants = merchantsService.queryMerchantBasicInfoByMerCode(settle_mer_code);
					MerchantsBalance merchantsBalance = null;
					if(merchants != null){
						double change_amount = 0d;
						double amount_ = 0d;
						if (StringUtils.isNotBlank(amt)) {
							amount_ = Double.valueOf(amt);
						}
						try {
							//向商户资金流水表中插入数据
							MerFundStance merFundStance = new MerFundStance();
							merFundStance.setId(UUID.randomUUID().toString().replaceAll("-", ""));//主键id
							merFundStance.setInst_id(Integer.valueOf(gid));//扣款渠道id
							merFundStance.setInst_type(DataStatus.instType_1);//渠道类型
							merFundStance.setMer_code(settle_mer_code);//商户号
							merFundStance.setMer_name(merchants.getMer_abbreviation());//商户简称
							merFundStance.setTrade_amount(amount_);//交易金额
							merFundStance.setTrade_stance(tseq);//交易流水
							merFundStance.setTrade_time(sys_date);//交易时间
							merFundStance.setDeduct_stlm_date(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));//清算日期
							merFundStance.setMer_state(merchants.getMer_state());//商户状态
							merFundStance.setMer_category(merchants.getMer_category());//商户类别
							merFundStance.setDerc_status(DataStatus.CONSUME);//简短描述
							merFundStance.setMer_fee(mer_fee);//商户手续费
							merFundStance.setStance_time(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
							merFundStance.setBank_id(bank_id);
							
							if(merchants.getBil_way() == 1){
								change_amount = amount_;
							}else{
								change_amount = PoundageCalculate.sub(amount_, mer_fee).doubleValue();
							}
							
							merFundStance.setChange_amount(change_amount);//变动金额
							
							
							merchantsBalance = merchantsBalanceService.queryMerBalanceByMerCode(settle_mer_code);
							if (merchantsBalance != null) {
								merFundStance.setAccount_amount(PoundageCalculate.sub(merchantsBalance.getMer_balance(), change_amount).doubleValue());//本期账户余额
							}
							
							int result = merFundStanceService.insertMerFundStance(merFundStance);
							if(result > 0){
								logger.info("将线上成功未对账交易数据保存至商户资金流水操作成功");
							}
						} catch (Exception e) {
							logger.error("进入差错,保存商户资金流水操作抛出异常:"+e);
						}
						
						try {
							//调整商户余额
							logger.info("对商户"+merchants.getMer_abbreviation()+"进行商户余额调整，调整幅度为"+change_amount);
							if(merchantsBalance != null){
								merchantsBalance.setMer_balance(PoundageCalculate.sub(merchantsBalance.getMer_balance(), change_amount).toString());
								int result = merchantsBalanceService.updateMerchantsBalance(merchantsBalance);
								if (result > 0) {
									logger.info("将线上成功未对账交易数据进入差错修改商户余额成功");
								}
							}
						} catch (Exception e) {
							logger.error("进入差错,调整商户余额操作抛出异常:"+e);
						}
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("线上成功交易未对账进入差错出现异常：" + e.getMessage());
		}
		return effectNum;
	}
	
	@RequestMapping(value = ADD_UNDER_ONLINE_DATA_TO_ERROR_DATA_LST,  method = RequestMethod.POST)
	@ResponseBody
	public int addUnderLineDataToErrorDataLst(ServletRequest request) {
		int effectNum = 0;
		try {
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			String original_data_tableName = null;
			if (bankInstList != null && bankInstList.length > 0) {
				original_data_tableName = bankInstList[2];
			}
			String tradeId = request.getParameter("tradeId");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(original_data_tableName)) {
				map.put("original_data_tableName", original_data_tableName);
			}
			if (StringUtils.isNotBlank(tradeId)) {
				map.put("trade_id", tradeId);
			}
			OriginalData originalData = orgDataDao.queryForObject("Original_Data.queryDetail", map);
			if (originalData != null) {
				ErrorDataLst errorDataLst = new ErrorDataLst();
				errorDataLst.setTrade_id(originalData.getTrade_id());
				errorDataLst.setTerminal_id(originalData.getTerminal_id());
				errorDataLst.setTerminal_info(originalData.getTerminal_info());
				errorDataLst.setTerminal_type(Integer.valueOf(originalData.getTerminal_type()));
				errorDataLst.setTrade_time(originalData.getTrade_time());
				errorDataLst.setOut_account(originalData.getOut_account());
				errorDataLst.setOut_account_type(originalData.getOut_account_type());
				errorDataLst.setOut_acc_valid_time(originalData.getOut_acc_valid_time());
				errorDataLst.setOut_account_info(originalData.getOut_account_info());
				errorDataLst.setOut_account_info2(originalData.getOut_account_info2());
				errorDataLst.setOut_account_desc(originalData.getOut_account_desc());
				errorDataLst.setIn_account(originalData.getIn_account());
				errorDataLst.setIn_card_name(originalData.getIn_card_name());
				double tradeAmount = originalData.getTrade_amount();
				
				Double amount =  Double.valueOf(tradeAmount) * 10000;
				errorDataLst.setTrade_amount(amount.intValue());
				errorDataLst.setTrade_fee(originalData.getTrade_fee());
				errorDataLst.setTrade_currency(originalData.getTrade_currency());
				errorDataLst.setTrade_result(originalData.getTrade_result());
				errorDataLst.setReq_sys_id(originalData.getReq_sys_id());
				errorDataLst.setReq_sys_stance(originalData.getReq_sys_stance());
				errorDataLst.setReq_mer_code(originalData.getReq_mer_code());
				errorDataLst.setReq_mer_term_id(originalData.getReq_mer_term_id());
				errorDataLst.setReq_response(originalData.getReq_response());
				errorDataLst.setDeduct_sys_id(originalData.getDeduct_sys_id());
				errorDataLst.setDeduct_sys_stance(originalData.getDeduct_sys_stance());
				errorDataLst.setDeduct_mer_code(originalData.getDeduct_mer_code());
				errorDataLst.setDeduct_mer_term_id(originalData.getDeduct_mer_term_id());
				errorDataLst.setDeduct_result(originalData.getDeduct_result());
				errorDataLst.setDeduct_sys_response(originalData.getDeduct_sys_response());
				errorDataLst.setDeduct_roll_bk(originalData.getDeduct_roll_bk());
				errorDataLst.setDeduct_roll_bk_result(originalData.getDeduct_roll_bk_result());
				errorDataLst.setDeduct_roll_bk_reason(originalData.getDeduct_roll_bk_reason());
				errorDataLst.setDeduct_roll_bk_response(originalData.getDeduct_roll_bk_response());
				errorDataLst.setDeduct_roll_bk_stance(originalData.getDeduct_roll_bk_stance());
				errorDataLst.setTrade_type(originalData.getTrade_type());
				errorDataLst.setMsg_num(originalData.getMsg_num());
				errorDataLst.setPass_wd_mode(originalData.getPass_wd_mode());
				errorDataLst.setReq_type(originalData.getReq_type());
				errorDataLst.setReq_input_type(originalData.getReq_input_type());
				errorDataLst.setReq_time(originalData.getReq_time());
				errorDataLst.setTrade_req_method(originalData.getTrade_req_method());
				errorDataLst.setTrade_desc(originalData.getTrade_desc());
				errorDataLst.setIn_account_type(originalData.getIn_account_type());
				errorDataLst.setTrade_other_info(originalData.getTrade_other_info());
				errorDataLst.setDeduct_stlm_date(originalData.getDeduct_stlm_date());
				errorDataLst.setDeduct_sys_time(originalData.getDeduct_sys_time());
				errorDataLst.setGain_sys_id(originalData.getGain_sys_id());
				errorDataLst.setGain_sys_stance(originalData.getGain_sys_stance());
				errorDataLst.setGain_mer_code(originalData.getGain_mer_code());
				errorDataLst.setGain_mer_term_id(originalData.getGain_mer_term_id());
				errorDataLst.setGain_sys_response(originalData.getGain_sys_response());
				errorDataLst.setGain_result(originalData.getGain_result());
				errorDataLst.setGain_trade_amount(originalData.getGain_trade_amount());
				errorDataLst.setGain_sys_reference(originalData.getGain_sys_reference());
				errorDataLst.setNii(originalData.getNii());
				errorDataLst.setAuthorization_code(originalData.getAuthorization_code());
				errorDataLst.setAdditional_response_data(originalData.getAdditional_response_data());
				errorDataLst.setAdditional_data(originalData.getAdditional_data());
				errorDataLst.setBoc(originalData.getBoc());
				errorDataLst.setCustom_define_info(originalData.getCustom_define_info());
				errorDataLst.setOriginal_trans_info(originalData.getOriginal_trans_info());
				errorDataLst.setReq_process(originalData.getReq_process());
				errorDataLst.setDeduct_sys_reference(originalData.getDeduct_sys_reference());
				errorDataLst.setTrademsg_type(originalData.getTrademsg_type());
				errorDataLst.setDeduct_rollbk_sys_reference(originalData.getDeduct_rollbk_sys_reference());
				errorDataLst.setMer_name(originalData.getMer_name());
				errorDataLst.setBk_chk(3);
				errorDataLst.setWhetherJs(originalData.getWhetherJs());
				errorDataLst.setWhetherValid(originalData.getWhetherValid());
				errorDataLst.setWhetherErroeHandle(originalData.getWhetherErroeHandle());
				errorDataLst.setWhetherRiqie(originalData.getWhetherRiqie());
				errorDataLst.setAcqInstIdCode(originalData.getAcqInstIdCode());
				errorDataLst.setFwdInstIdCode(originalData.getFwdInstIdCode());
				errorDataLst.setDeduct_rollbk_sys_time(originalData.getDeduct_rollbk_sys_time());
				errorDataLst.setAgentId(originalData.getAgentId());
				errorDataLst.setWhetherzero(originalData.getWhetherzero());
				errorDataLst.setWhtherInnerJs(originalData.getWhtherInnerJs());
				errorDataLst.setIc_card_ser_no(originalData.getIc_card_ser_no());
				errorDataLst.setIc_read_and_condition(originalData.getIc_read_and_condition());
				errorDataLst.setWhetherQs(originalData.getWhetherQs());
				errorDataLst.setMer_fee(Double.valueOf(originalData.getMer_fee()));
				errorDataLst.setWhetherTk(originalData.getWhetherTk());
				if (originalData.getTrade_amount() < 0) {
					errorDataLst.setZf_fee(0 - Double.valueOf(originalData.getZf_fee()));
					errorDataLst.setZf_file_fee("-"+originalData.getZf_file_fee());
				} else {
					errorDataLst.setZf_fee(Double.valueOf(originalData.getZf_fee()));
					errorDataLst.setZf_file_fee(originalData.getZf_file_fee());
				}
				errorDataLst.setZf_fee_bj(originalData.getZf_fee_bj());
				errorDataLst.setFee_formula(originalData.getFee_formula());
				errorDataLst.setWhetherAccessStance(originalData.getWhetherAccessStance());
				errorDataLst.setWhtherInnerDz(originalData.getWhtherInnerDz());
				errorDataLst.setBank_id(originalData.getBank_id());
				errorDataLst.setHandling_status(0);
				errorDataLst.setError_resource(DataStatus.ORG_ERROR_RESOURCE);
				errorDataLst.setInst_type(DataStatus.instType_0);
				
				effectNum = errorDataLstService.addUnderLineDataToErrorDataLst(errorDataLst);
				if (effectNum > 0) {
					logger.info("已成功将线下成功交易未对账数据保存至差错表中...");
					effectNum = rytUpmpService.updateBkchkByTradeId(map);
					if (effectNum > 0) {
						logger.info("已成功将线下成功交易未对账数据对账状态修改为无需对账");
						
						ChannelDzCollect channelDzCollect = new ChannelDzCollect();
						channelDzCollect.setTrade_id(originalData.getTrade_id());
						channelDzCollect.setOut_account(originalData.getOut_account());
						
						channelDzCollect.setTrade_amount(tradeAmount);
						
						channelDzCollect.setTrade_result(2);
						channelDzCollect.setReq_sys_stance(originalData.getReq_sys_stance());
						channelDzCollect.setReq_mer_code(originalData.getReq_mer_code());
						channelDzCollect.setDeduct_sys_id(originalData.getDeduct_sys_id());
						if (originalData.getDeduct_roll_bk() == 0) {
							channelDzCollect.setDeduct_sys_stance(originalData.getDeduct_sys_stance());
						} else {
							channelDzCollect.setDeduct_sys_stance(originalData.getDeduct_roll_bk_stance());
						}
						
						channelDzCollect.setDeduct_mer_code(originalData.getDeduct_mer_code());
						
						String sysDate = originalData.getTrade_time().substring(0, 10).replace("-", "").trim();
						String sysTime = originalData.getTrade_time().substring(10,  19).replace(":", "").trim();
						
						channelDzCollect.setTrade_time(StringPingJie.getInstance().getStringPingJie(sysDate, sysTime));
						channelDzCollect.setDeduct_sys_time(StringPingJie.getInstance().getStringPingJie(sysDate, sysTime));
						channelDzCollect.setDeduct_stlm_date(DateUtil.formatDate(new Date(), "yyyyMMdd"));
						channelDzCollect.setDeduct_roll_bk(DataStatus.NO_ROLL_BK);
						
						int tradeMsgType = originalData.getTrademsg_type();
						switch(tradeMsgType){
							case 2://消费
								if(StringUtils.isNotBlank(originalData.getReq_process())){
									if(StringUtils.equals(originalData.getReq_process(), "480000")){
										tradeMsgType = 29;
									}else
										tradeMsgType = 19;
								}else
									tradeMsgType = 19;
								break;
							case 26://消费冲正
								tradeMsgType = 20;
								break;
							case 18://消费撤销
								tradeMsgType = 21;
								break;
							case 28://消费撤销冲正
								tradeMsgType = 22;
								break;
							case 20://退货
								tradeMsgType = 23;
								break;
							case 56://预授权完成
								tradeMsgType = 24;
								break;
							case 58://预授权完成撤销
								tradeMsgType = 25;
								break;
							case 80://预授权完成冲正
								tradeMsgType = 26;
								break;
							case 82://预授权完成撤销冲正
								tradeMsgType = 27;
							case 110://脱机消费
								tradeMsgType = 28;
								break;
							default://未知
								tradeMsgType = 30;
								break;
						}
						
						channelDzCollect.setTrade_type(String.valueOf(tradeMsgType));
						channelDzCollect.setBk_chk(3);
						channelDzCollect.setWhetherErroeHandle(originalData.getWhetherErroeHandle());
						channelDzCollect.setWhetherQs(DataStatus.no_clean);
						channelDzCollect.setMer_fee(Double.valueOf(originalData.getMer_fee()));
						channelDzCollect.setZf_fee(Double.valueOf(originalData.getZf_fee()));
						channelDzCollect.setZf_file_fee(originalData.getZf_file_fee());
						channelDzCollect.setZf_fee_bj(originalData.getZf_fee_bj());
						channelDzCollect.setInstType(DataStatus.instType_0);
						channelDzCollect.setGate(originalData.getDeduct_sys_id());
						channelDzCollect.setSettle_code(originalData.getReq_mer_code());
						
						InstInfo instInfo = dataManagerInit.getInstInfoById(originalData.getDeduct_sys_id(), DataStatus.instType_0);
						String instName = null;
						if (instInfo != null) {
							instName = instInfo.getName();
						}
						channelDzCollect.setInst_name(instName);
						channelDzCollect.setOid(originalData.getAdditional_response_data());
						channelDzCollect.setSys_date(Integer.valueOf(DateUtil.formatDate(new Date(), "yyyyMMdd")));
						channelDzCollect.setBank_id(originalData.getBank_id());
						channelDzCollect.setJs_date(Integer.valueOf(DateUtil.formatDate(new Date(), "yyyyMMdd")));
						channelDzCollect.setOut_user_id(null);
						channelDzCollect.setIn_user_id(null);
						channelDzCollect.setRemark("进入差错");
						channelDzCollect.setWhetherValid(originalData.getWhetherValid());
						
						int num = channelDzCollectService.addNoDzDataToChannelDzCollect(channelDzCollect);
						if (num > 0) {
							logger.info("已成功将线上成功交易未对账数据保存至对账总表");
						}
						
						//根绝交易中的商户号 查询是否有配置相应的结算商户号，若配置结算商户号，则以结算商户号为维度进行保存T+1，保存商户资金流水，调整商户余额操作
						String settle_mer_code = settleMerchantMatchService.querySettleMerCodeByDyMerCode(originalData.getReq_mer_code());
						if(StringUtils.isBlank(settle_mer_code)){
							settle_mer_code = originalData.getReq_mer_code();
						}
						
						Merchants merchants = merchantsService.queryMerchantBasicInfoByMerCode(settle_mer_code);
						MerchantsBalance merchantsBalance = null;
						if(merchants != null){
							double change_amount = 0d;
							double amount_ = 0d;
							amount_ = tradeAmount;
							try {
								//向商户资金流水表中插入数据
								MerFundStance merFundStance = new MerFundStance();
								merFundStance.setId(UUID.randomUUID().toString().replaceAll("-", ""));//主键id
								merFundStance.setInst_id(originalData.getDeduct_sys_id());//扣款渠道id
								merFundStance.setInst_type(DataStatus.instType_0);//渠道类型
								merFundStance.setMer_code(settle_mer_code);//商户号
								merFundStance.setMer_name(merchants.getMer_abbreviation());//商户简称
								merFundStance.setTrade_amount(amount_);//交易金额
								merFundStance.setTrade_stance(originalData.getReq_sys_stance());//交易流水
								merFundStance.setTrade_time(originalData.getTrade_time());//交易时间
								merFundStance.setDeduct_stlm_date(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));//清算日期
								merFundStance.setMer_state(merchants.getMer_state());//商户状态
								merFundStance.setMer_category(merchants.getMer_category());//商户类别
								merFundStance.setDerc_status(DataStatus.CONSUME);//简短描述
								merFundStance.setMer_fee(Double.valueOf(originalData.getMer_fee()));//商户手续费
								merFundStance.setStance_time(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
								merFundStance.setBank_id(originalData.getBank_id());
								
								if(merchants.getBil_way() == 1){
									change_amount = amount_;
								}else{
									change_amount = PoundageCalculate.sub(amount_, originalData.getMer_fee()).doubleValue();
								}
								
								merFundStance.setChange_amount(change_amount);//变动金额
								merchantsBalance = merchantsBalanceService.queryMerBalanceByMerCode(settle_mer_code);
								if (merchantsBalance != null) {
									merFundStance.setAccount_amount(PoundageCalculate.sub(merchantsBalance.getMer_balance(), change_amount).doubleValue());//本期账户余额
								}
								
								int result = merFundStanceService.insertMerFundStance(merFundStance);
								if(result > 0){
									logger.info("将线上成功未对账交易数据保存至商户资金流水操作成功");
								}
							} catch (Exception e) {
								logger.error("进入差错,保存商户资金流水操作抛出异常:"+e);
							}
							
							try {
								//调整商户余额
								logger.info("对商户"+merchants.getMer_abbreviation()+"进行商户余额调整，调整幅度为"+amount_);
								if(merchantsBalance != null){
									merchantsBalance.setMer_balance(PoundageCalculate.sub(merchantsBalance.getMer_balance(), amount_).toString());
									int result = merchantsBalanceService.updateMerchantsBalance(merchantsBalance);
									if (result > 0) {
										logger.info("将线上成功未对账交易数据进入差错修改商户余额成功");
									}
								}
							} catch (Exception e) {
								logger.error("进入差错,调整商户余额操作抛出异常:"+e);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("线下成功交易未对账进入差错出现异常：" + e.getMessage());
		}
		return effectNum;
	}
	
	public static void main(String[] args) {
		System.out.println(Double.valueOf("1.01").intValue());
	}
}

