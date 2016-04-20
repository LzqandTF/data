package com.chinaebi.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import com.chinaebi.dao.ICommonDao;
import com.chinaebi.entity.ErrorAuditRecords;
import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.entity.YlCupsErrorEntry;
import com.chinaebi.exception.SelectException;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.ErrorAuditRecordsService;
import com.chinaebi.service.YlCupsErrorEntryService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 银联差错处理模块
 * 
 */
@Controller
public class YlCupsErrorEntryController {
	// 记录查询时的日志
	private static Logger logger = LoggerFactory.getLogger(YlCupsErrorEntryController.class);
	
	//银联差错录入 只查询未提交的数据
	private static final String QUERY_NO_COMMIT_DATA = "getCupsErrorInputLst.do";
	private static final String CUPSERRORINPUT = "errorOption/cupsErrorInput";

	private static final String QUERY_DETAIL = "queryCupsErrorInputDetail.do";
	
	//银联差错录入 提交动作
	private static final String UPDATE_TRADE_STATUS = "updateTradeStatus.do";

	// 无原交易录入
	private static final String INPUT_DATA = "addCupsErrorInput.do";
	private static final String INPUT_DATA_CONTENT = "errorOption/cupsErrorInputDialogbox";

	private static final String ADD_DIALOG_BOX = "againCupsErrorInput.do";
	private static final String QUERY_HANDLER_METHOD = "errorOption/againCupsErrorInputDialogbox";
	
	//银联差错录入
	private static final String ADD_CUPS_ERROR_DATA = "addCupsErrorData.do";
	
	//银联差错查询
	private static final String QUERY_CUPS_ERROR_LST = "queryCupsErrorLst.do";
	private static final String QUERY_CUPS_ERROR_CONTENT = "errorOption/cupsErrorSelect";
	
	private static final String QUERY_CUPS_ERROR_DETAIL = "queryCupsErrorDetailData.do";

	//银联差错审批
	private static final String QUERY_CUPS_ERROR_APPROVAL = "getCupsErrorApprovalData.do";
	private static final String QUERY_CUPS_ERROR_APPROVAL_CONTENT = "errorOption/cupsErrorApproval";

	private static final String QUERY_CUPS_APPROVAL_DETAIL = "queryCupsErrorApprovalDetail.do";
	
	//通过操作
	private static final String UPDATE_PASS = "updatePass.do";
	
	//驳回操作
	private static final String UPDATE_REJECT = "updateReject.do";

	//银联差错查询  重提交动作
	private static final String AGAIN_COMMIT = "againCommit.do";

	//银联差错查询 重录入动作
	private static final String UPDATE_DATA = "updateCupsErrorInputData.do";
	
	//电银差错对账明细查询
	private static final String QUERY_ALL = "queryErrorOriginalData.do";
	private static final String JSP_PAGE = "duizhangResultData/errorDuizhangDetailsSelect";
	
	//电银差错对账明细查询，详情查询
	private static final String QUERY_DETAIL_ = "queryDyErrorDetail.do";
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<YlCupsErrorEntry> errorOriginalDataDao;
	
	// 实现依赖注入
	@Autowired
	@Qualifier(value = "ylCupsErrorEntryService")
	private YlCupsErrorEntryService ylCupsErrorEntryService;

	@Autowired
	@Qualifier(value = "dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "commonClass")
	private CommonClass commonClass;
	
	@Autowired
	@Qualifier(value = "errorAuditRecordsService")
	private ErrorAuditRecordsService errorAuditRecordsService;
	
	/**
	 * 银联差错录入  只查询数据状态为未提交的数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_NO_COMMIT_DATA, method = RequestMethod.POST)
	public String queryPageCupsErrorInput(ServletRequest request, Model model) {
		logger.info("银联差错录入  进入未提交数据查询...");
		try {
			// 分页参数
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			// 查询参数
			String deductStlmDate = request.getParameter("deductStlmDate");
			String out_account = request.getParameter("outAccount");
			String reqSysStance = request.getParameter("reqSysStance");
			String tradeType = request.getParameter("tradeType");
			
			Page<YlCupsErrorEntry> page = new Page<YlCupsErrorEntry>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);

			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(deductStlmDate)) {
				map.put("deductStlmDate", deductStlmDate);
			}
			if (StringUtils.isNotBlank(out_account)) {
				map.put("out_account", out_account);
			}
			if (StringUtils.isNotBlank(reqSysStance)) {
				map.put("reqSysStance", reqSysStance);
			}
			if (StringUtils.isNotBlank(tradeType)) {
				map.put("process", tradeType.substring(0, 6));
			}
			if (StringUtils.isNotBlank(tradeType)) {
				map.put("tradeMsgType", tradeType.substring(6, tradeType.length()));
			}
			
			Page<YlCupsErrorEntry> list = ylCupsErrorEntryService.queryPageNoCommitData(page, map);
			StringBuffer sb = new StringBuffer();
			for (YlCupsErrorEntry ylCupsErrorEntry : list) {
				sb.setLength(0);
				//处理卡号
				String outAccount = ylCupsErrorEntry.getOut_account();
				if (StringUtils.isNotBlank(outAccount)) {
					sb.append(outAccount.substring(0, 6)).append("***").append(outAccount.substring(outAccount.length() - 4, outAccount.length()));
					ylCupsErrorEntry.setOut_account(sb.toString());
				}
				//处理码
				String process = ylCupsErrorEntry.getProcess();
				//交易消息类型
				int trade_msg_type = ylCupsErrorEntry.getTradeMsgType();
				// 交易类型
				ylCupsErrorEntry.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
				//终端信息
				String id = ylCupsErrorEntry.getId();
				// 交易类别
				ylCupsErrorEntry.setTradeName(commonClass.getTradeNameByTerminalInfo(id));
				//金额处理
				ylCupsErrorEntry.setTradeAmount(new Double(ylCupsErrorEntry.getTradeAmount())/100 + "");
				//处理方式
				int handling_id = ylCupsErrorEntry.getHandling_id();
				ylCupsErrorEntry.setHandling_name(commonClass.getHandlingNameById(handling_id));
			}
			model.addAttribute("pageDataLst", list);
			model.addAttribute("tradeType", tradeType);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("银联差错录入模块  查询数据出错：" + e.getMessage());
		}
		return CUPSERRORINPUT;
	}

	@RequestMapping(value = QUERY_DETAIL)
	@ResponseBody
	public YlCupsErrorEntry queryCupsErrorInputDetail(ServletRequest request) {
		YlCupsErrorEntry ylCupsErrorEntry = null;
		try {
			String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				ylCupsErrorEntry = ylCupsErrorEntryService.queryCupsErrorInputDetail(id);
				//处理码
				String process = ylCupsErrorEntry.getProcess();
				//交易消息类型
				int trade_msg_type = ylCupsErrorEntry.getTradeMsgType();
				int handling_id = ylCupsErrorEntry.getHandling_id();
				//金额处理
				ylCupsErrorEntry.setTradeAmount(new DecimalFormat("0.00").format(Double.parseDouble(ylCupsErrorEntry.getTradeAmount())/100));
				//原因码
				String reason_code = ylCupsErrorEntry.getReason_code();
				// 交易类型
				ylCupsErrorEntry.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
				//处理方式
				ylCupsErrorEntry.setHandling_name(commonClass.getHandlingNameById(handling_id));
				//原因描述
				ylCupsErrorEntry.setReason_des(commonClass.getReasonDescByReasonCode(reason_code));
			}
		} catch (Exception e) {
			logger.error("根据id查询银联差错录入明细出错：" + e.getMessage());
		}
		return ylCupsErrorEntry;
	}

	@RequestMapping(value = UPDATE_TRADE_STATUS, method = RequestMethod.POST)
	@ResponseBody
	public int updateTradeStatus(HttpServletRequest request) {
		String id = request.getParameter("id");
		String operator = request.getParameter("operator");

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(id)) {
			map.put("id", id);
			map.put("commit_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date()));
			map.put("trade_source", "内部差错");
			map.put("operator", operator);
			try {
				int effectNum = ylCupsErrorEntryService.updateTradeStatus(map);
				if (effectNum > 0) {
					//将提交后的数据保存到差错审计表中
					YlCupsErrorEntry ylCupsErrorEntry = queryDetailById(id);
					if (ylCupsErrorEntry != null) {
						ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, ylCupsErrorEntry);
						errorAuditRecords.setOperator(ylCupsErrorEntry.getOperator());
						String tradeSource = ylCupsErrorEntry.getTrade_source();
						if (tradeSource.equals("内部差错")) {
							errorAuditRecords.setError_resource(2);
						} else {
							errorAuditRecords.setError_resource(3);
						}
						errorAuditRecords.setBusiness_type(4);
						errorAuditRecords.setOperation_type(4);
						errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
					}
				}
				return effectNum;
			} catch (Exception e) {
				logger.error("提交操作失败" + e.getMessage());
				return -1;
			}
		} else {
			logger.error("提交操作缺少主键");
			return -1;
		}
	}

	@RequestMapping(value = INPUT_DATA, method = RequestMethod.POST)
	public String inputData(ServletRequest request, Model model) {
		try {
			model.addAttribute("errorHandleList",dataManagerInit.getErrorHandlingList());
			model.addAttribute("tradeType",dataManagerInit.getTradeAmountConfList());
			model.addAttribute("instList", dataManagerInit.getInstInfo(1));
		} catch (Exception e) {
			logger.error("获取差错处理方式失败..." + e.getMessage());
		}
		return INPUT_DATA_CONTENT;
	}
	
	@RequestMapping(value = ADD_DIALOG_BOX, method = RequestMethod.POST)
	public String getHandlerMethod(ServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			String instId = request.getParameter("instId");
			if (StringUtils.isNotBlank(id)) {
				YlCupsErrorEntry ylCupsErrorEntry = ylCupsErrorEntryService.queryCupsErrorInputDetail(id);
				//处理码
				String process = ylCupsErrorEntry.getProcess();
				//交易消息类型
				int trade_msg_type = ylCupsErrorEntry.getTradeMsgType();
				int handling_id = ylCupsErrorEntry.getHandling_id();
				//原因码
				String reason_code = ylCupsErrorEntry.getReason_code();
				ylCupsErrorEntry.setTradeAmount(new DecimalFormat("0.00").format(Double.parseDouble(ylCupsErrorEntry.getTradeAmount())/100));
				// 交易类型
				ylCupsErrorEntry.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
				//处理方式
				ylCupsErrorEntry.setHandling_name(commonClass.getHandlingNameById(handling_id));
				//原因描述
				ylCupsErrorEntry.setReason_des(commonClass.getReasonDescByReasonCode(reason_code));
				model.addAttribute("reasonCodeList", dataManagerInit.getReasonCodeList(ylCupsErrorEntry.getHandling_id()));
				model.addAttribute("ylCupsErrorEntry", ylCupsErrorEntry);
			}
			List<ErrorHandling> errorHandlingList = dataManagerInit.getErrorHandlingList();
			model.addAttribute("errorHandleList", errorHandlingList);
			model.addAttribute("tradeType", dataManagerInit.getTradeAmountConfList());
			model.addAttribute("instList", dataManagerInit.getInstInfo(1));
			model.addAttribute("instId",instId);
		} catch (Exception e) {
			logger.error("获取差错处理方式失败..." + e.getMessage());
		}
		return QUERY_HANDLER_METHOD;
	}

	@RequestMapping(value = ADD_CUPS_ERROR_DATA, method = RequestMethod.POST)
	@ResponseBody
	public int addCupsErrorData(HttpServletRequest request, ServletResponse response) throws Exception {
		int num = 0;
		String reqSysStance = request.getParameter("reqSysStance2");
		String tradeTime = request.getParameter("tradeTime2");
		String deductStlmDate = request.getParameter("deductStlmDate2");
		String tradeAmount = request.getParameter("tradeAmount2");
		String out_account = request.getParameter("out_account2");
		String acqInstIdCode = request.getParameter("acqInstIdCode2");
		String trade_type2 = request.getParameter("trade_type2");
		String handling_id = request.getParameter("handling_id");
		String reasonCode = request.getParameter("reasonCode");
		String operator = request.getParameter("operator");
		String instId = request.getParameter("instId");
		
		int count = ylCupsErrorEntryService.queryDataByReqSysStance(reqSysStance);
		if (count == 0) {
			YlCupsErrorEntry ylCupsErrorEntry = new YlCupsErrorEntry();
			ylCupsErrorEntry.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			ylCupsErrorEntry.setReqSysStance(reqSysStance);
			ylCupsErrorEntry.setOut_account(out_account);
			ylCupsErrorEntry.setTradeAmount(new BigDecimal(tradeAmount).multiply(new BigDecimal(100))+"");
			ylCupsErrorEntry.setHandling_id(Integer.valueOf(handling_id));
			ylCupsErrorEntry.setAcqInstIdCode(acqInstIdCode);
			ylCupsErrorEntry.setReason_code(reasonCode);
			ylCupsErrorEntry.setTrade_time(tradeTime);
			ylCupsErrorEntry.setDeductStlmDate(deductStlmDate);
			ylCupsErrorEntry.setTrade_category(commonClass.getTradeNameByTerminalInfo(ylCupsErrorEntry.getId()));
			ylCupsErrorEntry.setDeduct_sys_id(Integer.valueOf(instId));
			ylCupsErrorEntry.setOperator(operator);
			ylCupsErrorEntry.setProcess(trade_type2.substring(0, 6));
			ylCupsErrorEntry.setTradeMsgType(Integer.valueOf(trade_type2.substring(6, trade_type2.length())));

			int effectNum = ylCupsErrorEntryService.addOriginalData(ylCupsErrorEntry);
			if (effectNum > 0) {
				num = 1;
				//将直接录入的数据保存到差错审计表中
				ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, ylCupsErrorEntry);
				errorAuditRecords.setOperator(ylCupsErrorEntry.getOperator());
				errorAuditRecords.setError_resource(3);
				errorAuditRecords.setBusiness_type(3);
				errorAuditRecords.setOperation_type(3);
				errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
			}
		}
		return num;
	}
	
	/**
	 * 银联差错查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_CUPS_ERROR_LST, method = RequestMethod.POST)
	public String queryCupsErrorData(ServletRequest request, Model model) {
		logger.info("银联差错查询  进入银差错表查询所有数据...");
		// 分页参数
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		// 查询参数
		String deductStlmDate = request.getParameter("deductStlmDate");
		String out_account = request.getParameter("out_account");
		String entering_time = request.getParameter("entering_time");
		String trade_type = request.getParameter("trade_type");
		String reqSysStance = request.getParameter("reqSysStance");
		String handling_id = request.getParameter("handling_id");
		String deduct_sys_id = request.getParameter("channel");
		String trade_status = request.getParameter("trade_status");
		
		Page<YlCupsErrorEntry> page = new Page<YlCupsErrorEntry>();
		if (StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if (StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else
			page.setPageSize(10);

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(deductStlmDate)) {
			map.put("deductStlmDate", deductStlmDate);
		}
		if (StringUtils.isNotBlank(out_account)) {
			map.put("out_account", out_account);
		}
		if (StringUtils.isNotBlank(entering_time)) {
			map.put("entering_time", entering_time);
		}
		if (StringUtils.isNotBlank(trade_type)) {
			map.put("process", trade_type.substring(0, 6));
		}
		if (StringUtils.isNotBlank(trade_type)) {
			map.put("tradeMsgType", trade_type.substring(6, trade_type.length()));
		}
		if (StringUtils.isNotBlank(reqSysStance)) {
			map.put("reqSysStance", reqSysStance);
		}
		if (StringUtils.isNotBlank(handling_id)) {
			map.put("handling_id", handling_id);
		}
		if (StringUtils.isNotBlank(trade_status)) {
			map.put("trade_status", trade_status);
		}

		Page<YlCupsErrorEntry> list = ylCupsErrorEntryService.queryPageCupsErrorInput(page, map);
		StringBuffer sb = new StringBuffer();
		for (YlCupsErrorEntry ylCupsErrorEntry : list) {
			//账号
			String outAccount = ylCupsErrorEntry.getOut_account();
			//处理码
			String process = ylCupsErrorEntry.getProcess();
			//消息类型
			int trade_msg_type = ylCupsErrorEntry.getTradeMsgType();
			int handlingId = ylCupsErrorEntry.getHandling_id();
			//金额处理
			ylCupsErrorEntry.setTradeAmount(new Double(ylCupsErrorEntry.getTradeAmount())/100 + "");
			sb.setLength(0);
			sb.append(outAccount.substring(0, 6)).append("***").append(outAccount.substring(outAccount.length() - 4, outAccount.length()));
			ylCupsErrorEntry.setOut_account(sb.toString());
			// 交易类型
			ylCupsErrorEntry.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
			//处理方式
			ylCupsErrorEntry.setHandling_name(commonClass.getHandlingNameById(handlingId));
			//驳回原因
			String trun_remark = ylCupsErrorEntry.getTurnDown_remark();
			if (StringUtils.isNotBlank(trun_remark)) {
				if (trun_remark.length() > 3) {
					sb.setLength(0);
					sb.append(trun_remark.substring(0, 3)).append("...");
					ylCupsErrorEntry.setTurnDown_remark(sb.toString());
				}
			}
		}
		model.addAttribute("pageDataLst", list);
		model.addAttribute("inst_id",deduct_sys_id);
		model.addAttribute("trade_types", trade_type);
		model.addAttribute("handling_name", handling_id);
		model.addAttribute("trade_status", trade_status);
		model.addAttribute("pageSize", pageSize);
		return QUERY_CUPS_ERROR_CONTENT;
	}

	@RequestMapping(value = QUERY_CUPS_ERROR_DETAIL)
	@ResponseBody
	public YlCupsErrorEntry queryCupsErrorDetailData(ServletRequest request) {
		YlCupsErrorEntry ylCupsErrorEntry = null;
		try {
			String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				ylCupsErrorEntry = ylCupsErrorEntryService.queryCupsErrorInputDetail(id);
				//处理方式
				String process = ylCupsErrorEntry.getProcess();
				//交易消息类型
				int trade_msg_type = ylCupsErrorEntry.getTradeMsgType();
				int handling_id = ylCupsErrorEntry.getHandling_id();
				//原因码
				String reason_code = ylCupsErrorEntry.getReason_code();
				//金额处理
				ylCupsErrorEntry.setTradeAmount(new DecimalFormat("0.00").format(Double.parseDouble(ylCupsErrorEntry.getTradeAmount())/100));
				// 交易类型
				ylCupsErrorEntry.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
				//处理方式
				ylCupsErrorEntry.setHandling_name(commonClass.getHandlingNameById(handling_id));
				//原因描述
				ylCupsErrorEntry.setReason_des(commonClass.getReasonDescByReasonCode(reason_code));
			}
		} catch (Exception e) {
			logger.error("根据id查询银联差错数据明细出错：" + e.getMessage());
		}
		return ylCupsErrorEntry;
	}

	/**
	 * 银联差错审批 查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_CUPS_ERROR_APPROVAL, method = RequestMethod.POST)
	public String queryCupsErrorApprovalData(ServletRequest request, Model model) throws Exception {
		logger.info("银联差错审批——数据查询");
		// 分页参数
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		Page<YlCupsErrorEntry> page = new Page<YlCupsErrorEntry>();
		if (StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if (StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else
			page.setPageSize(10);

		// 查询参数
		String deductStlmDate = request.getParameter("deductStlmDate");
		String reqSysStance = request.getParameter("reqSysStance");
		String deduct_sys_id = request.getParameter("channel");
		String trade_type = request.getParameter("trade_type");
		String out_account = request.getParameter("out_account");
		String handling_id = request.getParameter("handling_id");

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(deductStlmDate)) {
			map.put("deductStlmDate", deductStlmDate);
		}
		if (StringUtils.isNotBlank(reqSysStance)) {
			map.put("reqSysStance", reqSysStance);
		}
		if (StringUtils.isNotBlank(trade_type)) {
			map.put("process", trade_type.substring(0, 6));
		}
		if (StringUtils.isNotBlank(trade_type)) {
			map.put("tradeMsgType", trade_type.substring(6, trade_type.length()));
		}
		if (StringUtils.isNotBlank(deduct_sys_id)) {
			map.put("deduct_sys_id",deduct_sys_id);
		}
		if (StringUtils.isNotBlank(out_account)) {
			map.put("out_account", out_account);
		}
		if (StringUtils.isNotBlank(handling_id)) {
			map.put("handling_id", handling_id);
		}
		Page<YlCupsErrorEntry> list = ylCupsErrorEntryService.queryNeedApprovalData(page, map);
		StringBuffer sb = new StringBuffer();
		for (YlCupsErrorEntry ylCupsErrorEntry : list) {
			//账号
			String outAccount = ylCupsErrorEntry.getOut_account();
			//处理码
			String process = ylCupsErrorEntry.getProcess();
			//交易消息类型
			int trade_msg_type = ylCupsErrorEntry.getTradeMsgType();
			int handlingId = ylCupsErrorEntry.getHandling_id();
			ylCupsErrorEntry.setTradeAmount(new Double(ylCupsErrorEntry.getTradeAmount())/100 + "");
			sb.setLength(0);
			sb.append(outAccount.substring(0, 6)).append("***").append(outAccount.substring(outAccount.length() - 4, outAccount.length()));
			ylCupsErrorEntry.setOut_account(sb.toString());
			//交易类型
			ylCupsErrorEntry.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
			//处理方式
			ylCupsErrorEntry.setHandling_name(commonClass.getHandlingNameById(handlingId));
		}
		model.addAttribute("pageDataLst", list);
		model.addAttribute("inst_id",deduct_sys_id);
		model.addAttribute("trade_types", trade_type);
		model.addAttribute("handling_name", handling_id);
		model.addAttribute("pageSize", pageSize);
		return QUERY_CUPS_ERROR_APPROVAL_CONTENT;
	}

	@RequestMapping(value = QUERY_CUPS_APPROVAL_DETAIL)
	@ResponseBody
	public YlCupsErrorEntry queryCupsErrorApprovalDetail(ServletRequest request) {
		YlCupsErrorEntry ylCupsErrorEntry = null;
		try {
			String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				ylCupsErrorEntry = ylCupsErrorEntryService.queryCupsErrorInputDetail(id);
				//处理码
				String process = ylCupsErrorEntry.getProcess();
				//交易消息类型
				int trade_msg_type = ylCupsErrorEntry.getTradeMsgType();
				int handlingId = ylCupsErrorEntry.getHandling_id();
				//原因码
				String reasonCode = ylCupsErrorEntry.getReason_code();
				//金额处理
				ylCupsErrorEntry.setTradeAmount(new DecimalFormat("0.00").format(Double.parseDouble(ylCupsErrorEntry.getTradeAmount())/100));
				// 交易类型
				ylCupsErrorEntry.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
				//处理方式
				ylCupsErrorEntry.setHandling_name(commonClass.getHandlingNameById(handlingId));
				
				ylCupsErrorEntry.setReason_des(commonClass.getReasonDescByReasonCode(reasonCode));
			}
		} catch (Exception e) {
			logger.error("根据id查询银联差错数据明细出错：" + e.getMessage());
		}
		return ylCupsErrorEntry;
	}

	@RequestMapping(value = UPDATE_PASS, method = RequestMethod.POST)
	@ResponseBody
	public Integer approvalPass(HttpServletRequest request, ServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String audit_operator = request.getParameter("audit_operator");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(id)) {
			map.put("id", id);
			map.put("entering_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map.put("audit_operator", audit_operator);
			try {
				int effectNum = ylCupsErrorEntryService.updatePass(map);
				if (effectNum > 0) {
					//将审批后的数据保存到差错审计表中
					YlCupsErrorEntry ylCupsErrorEntry = queryDetailById(id);
					if (ylCupsErrorEntry != null) {
						ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, ylCupsErrorEntry);
						errorAuditRecords.setOperator(ylCupsErrorEntry.getAudit_operator());
						String tradeSource = ylCupsErrorEntry.getTrade_source();
						if (tradeSource.equals("内部差错")) {
							errorAuditRecords.setError_resource(2);
						} else {
							errorAuditRecords.setError_resource(3);
						}
						errorAuditRecords.setBusiness_type(5);
						errorAuditRecords.setOperation_type(1);
						errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
					}
				}
				return effectNum;
			} catch (Exception e) {
				logger.error("审批操作失败" + e.getMessage());
				return -1;
			}
		} else {
			logger.error("通过操作(缺少主键编号)");
			return -1;
		}
	}

	@RequestMapping(value = UPDATE_REJECT, method = RequestMethod.POST)
	public void updateApproval(HttpServletRequest request, ServletResponse response) throws Exception {
		boolean flag = false;
		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("trade_id");
		String reason = request.getParameter("reason");
		String audit_operator = request.getParameter("audit_operator");
		logger.info(id + "--" + reason);
		if (StringUtils.isNotBlank(id) || StringUtils.isNotBlank(reason)) {
			YlCupsErrorEntry ylCupsErrorEntry = new YlCupsErrorEntry();
			ylCupsErrorEntry.setId(id);
			ylCupsErrorEntry.setTurnDown_remark(reason);
			ylCupsErrorEntry.setAudit_operator(audit_operator);
			int effectNum = ylCupsErrorEntryService.updateReject(ylCupsErrorEntry);
			if (effectNum > 0) {
				flag = true;
				PrintWriter out = response.getWriter();
				out.print(flag);
				out.flush();
				out.close();
				
				//将驳回后的数据保存到差错审计表中
				YlCupsErrorEntry yCupsErrorEntry = queryDetailById(id);
				if (yCupsErrorEntry != null) {
					ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, yCupsErrorEntry);
					errorAuditRecords.setOperator(yCupsErrorEntry.getAudit_operator());
					String tradeSource = ylCupsErrorEntry.getTrade_source();
					if (tradeSource == "内部差错") {
						errorAuditRecords.setError_resource(2);
					} else {
						errorAuditRecords.setError_resource(3);
					}
					errorAuditRecords.setBusiness_type(6);
					errorAuditRecords.setOperation_type(2);
					errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
				}
			}
		} else {
			logger.error("驳回操作(缺少主键编号) or 驳回原因不能为空");
		}
	}

	@RequestMapping(value = AGAIN_COMMIT, method = RequestMethod.POST)
	@ResponseBody
	public int againCommit(HttpServletRequest request) {
		String id = request.getParameter("id");
		String operator = request.getParameter("operator");

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(id)) {
			map.put("id", id);
			map.put("commit_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			map.put("operator", operator);
			try {
				int effectNum = ylCupsErrorEntryService.updateTradeStatus(map);
				if (effectNum > 0) {
					//将重提交后的数据保存到差错审计表中
					YlCupsErrorEntry ylCupsErrorEntry = queryDetailById(id);
					if (ylCupsErrorEntry != null) {
						ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, ylCupsErrorEntry);
						errorAuditRecords.setOperator(ylCupsErrorEntry.getOperator());
						String tradeSource = ylCupsErrorEntry.getTrade_source();
						if (tradeSource.equals("内部差错")) {
							errorAuditRecords.setError_resource(2);
						} else {
							errorAuditRecords.setError_resource(3);
						}
						errorAuditRecords.setBusiness_type(4);
						errorAuditRecords.setOperation_type(6);
						errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
					}
				}
				return effectNum;
			} catch (Exception e) {
				logger.error("提交操作失败" + e.getMessage());
				return -1;
			}
		} else {
			logger.error("提交操作缺少主键");
			return -1;
		}
	}

	@RequestMapping(value = UPDATE_DATA, method = RequestMethod.POST)
	public void updateData(HttpServletRequest request, ServletResponse response) throws Exception {
		boolean flag = false;
		String id = request.getParameter("id");
		String reqSysStance = request.getParameter("reqSysStance2");
		String tradeTime = request.getParameter("tradeTime2");
		String deductStlmDate = request.getParameter("deductStlmDate2");
		String tradeAmount = request.getParameter("tradeAmount2");
		String out_account = request.getParameter("out_account2");
		String acqInstIdCode = request.getParameter("acqInstIdCode2");
		String deduct_sys_id = request.getParameter("deduct_sys_id");
		String trade_type = request.getParameter("trade_type2");
		String handling_id = request.getParameter("handling_id");
		String reasonCode = request.getParameter("reasonCode");
		String operator = request.getParameter("operator");
		
		YlCupsErrorEntry ylCupsErrorEntry = new YlCupsErrorEntry();
		ylCupsErrorEntry.setId(id);
		ylCupsErrorEntry.setReqSysStance(reqSysStance);
		ylCupsErrorEntry.setTrade_time(tradeTime);
		ylCupsErrorEntry.setDeductStlmDate(deductStlmDate);
		ylCupsErrorEntry.setOut_account(out_account);
		ylCupsErrorEntry.setTradeAmount(String.valueOf(((Integer.valueOf(tradeAmount.replace(".", ""))))));
		ylCupsErrorEntry.setAcqInstIdCode(acqInstIdCode);
		ylCupsErrorEntry.setDeduct_sys_id(Integer.valueOf(deduct_sys_id));
		ylCupsErrorEntry.setProcess(trade_type.substring(0, 6));
		ylCupsErrorEntry.setTradeMsgType(Integer.valueOf(trade_type.substring(6, trade_type.length())));
		ylCupsErrorEntry.setHandling_id(Integer.valueOf(handling_id));
		ylCupsErrorEntry.setReason_code(reasonCode);
		ylCupsErrorEntry.setEntering_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		ylCupsErrorEntry.setOperator(operator);

		int effectNum = ylCupsErrorEntryService.updateData(ylCupsErrorEntry);
		if (effectNum > 0) {
			flag = true;
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
			
			//将重提交后的数据保存到差错审计表中
			YlCupsErrorEntry yCupsErrorEntry = queryDetailById(id);
			if (yCupsErrorEntry != null) {
				ErrorAuditRecords errorAuditRecords = new ErrorAuditRecords(request, yCupsErrorEntry);
				errorAuditRecords.setOperator(yCupsErrorEntry.getOperator());
				errorAuditRecords.setError_resource(3);
				errorAuditRecords.setBusiness_type(3);
				errorAuditRecords.setOperation_type(5);
				errorAuditRecordsService.addErrorAuditRecords(errorAuditRecords);
			}
		}
	}
	
	/**
	 * 根据主键id查询银联差错详情
	 * @param id
	 * @return
	 */
	public YlCupsErrorEntry queryDetailById(String id) {
		YlCupsErrorEntry ylCupsErrorEntry = null;
		try {
			ylCupsErrorEntry = ylCupsErrorEntryService.queryCupsErrorInputDetail(id);
			if (ylCupsErrorEntry == null) {
				throw new SelectException("cupsErrorInputService.queryCupsErrorInputDetail(id)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("根据" + id + "查询银联差错详情出错：" + e.getMessage());
		}
		return ylCupsErrorEntry;
	}
	
	
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryErrorDuizhangDetail(ServletRequest request, Model model) {
		logger.info("电银差错对账明细查询  进入差错原始数据查询...");
		String error_original_data_tableName = null;
		try {
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			
			Page<YlCupsErrorEntry> page = new Page<YlCupsErrorEntry>();
			if(StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if(StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else 
				page.setPageSize(10);
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String reqSysStance = request.getParameter("reqSysStance");
			String bk_chk = request.getParameter("bk_chk");
			error_original_data_tableName = request.getParameter("inst_name");
		
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			String handling_id = request.getParameter("handling_id");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime);
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime);
			}
			if (StringUtils.isNotBlank(reqSysStance)) {
				map.put("reqSysStance", reqSysStance);
			}
			if (StringUtils.isNotBlank(bk_chk)) {
				map.put("bk_chk", bk_chk);
			}
			if (StringUtils.isNotBlank(deduct_sys_reference)) {
				map.put("deduct_sys_reference", deduct_sys_reference);
			}
			if (StringUtils.isNotBlank(handling_id)) {
				map.put("handling_id", handling_id);
			}
			if (StringUtils.isNotBlank(error_original_data_tableName)) {
				String[] strings = error_original_data_tableName.split(",");
				map.put("deduct_sys_id", strings[0]);
				map.put("error_original_data_tableName", strings[1]);
			}
			
			Page<YlCupsErrorEntry> errorOriginalDataPage = errorOriginalDataDao.queryForPage(page, "YlCupsErrorEntry.queryPageErrorOriginalData", "YlCupsErrorEntry.queryCount", map);
			StringBuilder sb = new StringBuilder();
			for (YlCupsErrorEntry errorOriginalData : errorOriginalDataPage) {
				//处账号
				String outAccount = errorOriginalData.getOut_account();
				sb.setLength(0);
				sb.append(outAccount.substring(0, 6)).append("***").append(outAccount.substring(outAccount.length() - 4, outAccount.length()));
				errorOriginalData.setOut_account(sb.toString());
				int handlingId = errorOriginalData.getHandling_id();
				errorOriginalData.setHandling_name(commonClass.getHandlingNameById(handlingId));
			}
			model.addAttribute("pageDataLst", errorOriginalDataPage);
			model.addAttribute("bk_chk", bk_chk);
			model.addAttribute("table_name",error_original_data_tableName);
			model.addAttribute("handling_name", handling_id);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.info("电银差错对账明细查询  查询" + error_original_data_tableName + "出错：" + e.getMessage());
		}
		return JSP_PAGE;
	}
	
	@RequestMapping(value = QUERY_DETAIL_)
	@ResponseBody
	public YlCupsErrorEntry queryDetail(ServletRequest request) {
		logger.info("进入差错原始交易数据详情查询...");
		String error_original_data_tableName = null;
		String id = null;
		YlCupsErrorEntry errorOriginalData = null;
		try {
			error_original_data_tableName = request.getParameter("inst_name");
			String[] str = error_original_data_tableName.split(",");
			if (str != null && str.length > 1) {
				error_original_data_tableName =str[1];
			}
			id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(error_original_data_tableName)) {
				map.put("error_original_data_tableName", error_original_data_tableName);
			}
			if (StringUtils.isNotBlank(id)) {
				map.put("id", id);
			}
			errorOriginalData = errorOriginalDataDao.queryForObject("YlCupsErrorEntry.queryDetail", map);
			//处理清算日期
			String deductStlmDate = errorOriginalData.getDeductStlmDate();
			if (StringUtils.isNotBlank(deductStlmDate)) {
				errorOriginalData.setDeductStlmDate(deductStlmDate.substring(0, 10));
			}
			//处理码
			String process = errorOriginalData.getProcess();
			//交易消息类型
			int trade_msg_type = errorOriginalData.getTradeMsgType();
			int handling_id = errorOriginalData.getHandling_id();
			//原因码
			String reason_code = errorOriginalData.getReason_code();
			// 交易类型
			errorOriginalData.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
			//处理方式
			errorOriginalData.setHandling_name(commonClass.getHandlingNameById(handling_id));
			//原因描述
			errorOriginalData.setReason_des(commonClass.getReasonDescByReasonCode(reason_code));
		} catch (Exception e) {
			logger.error("根据机构主键" + id + "查询" + error_original_data_tableName + "详情数据出错：" + e.getMessage());
		}
		return errorOriginalData;
	}
}
