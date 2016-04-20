package com.chinaebi.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.ErrorAuditRecords;
import com.chinaebi.service.ErrorAuditRecordsService;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 差错审计控制层
 * @author wufei
 *
 */
@Controller
public class ErrorAuditRecordsController {
	private static Logger logger = LoggerFactory.getLogger(ErrorAuditRecordsController.class);
	
	//差错审计数据查询
	private static final String QUERY_ALL = "queryErrorAuditRecords.do";
	private static final String JSP_PAGE = "log/log";
	
	//差错审计详情查询
	private static final String QUERY_DETAIL = "queryDetail.do";
	
	@Autowired
	@Qualifier(value = "errorAuditRecordsService")
	private ErrorAuditRecordsService errorAuditRecordsService;
	
	@Autowired
	@Qualifier(value = "commonClass")
	private CommonClass commonClass;
	
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryPageErrorAuditRecords(ServletRequest request, Model model) {
		logger.info("数据日志   进入差错审计数据查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//分页显示条数
			String pageSize = request.getParameter("pageSize");
			Page<ErrorAuditRecords> page = new Page<ErrorAuditRecords>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			//查询参数
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String businessType = request.getParameter("businessType");
			String operator = request.getParameter("operator");
			String operationType = request.getParameter("operationType");
			String reqSysStance = request.getParameter("reqSysStance");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(businessType)) {
				map.put("businessType", businessType);
			}
			if (StringUtils.isNotBlank(operator)) {
				map.put("operator", operator);
			}
			if (StringUtils.isNotBlank(operationType)) {
				map.put("operationType", operationType);
			}
			if (StringUtils.isNotBlank(reqSysStance)) {
				map.put("reqSysStance", reqSysStance);
			}
			
			Page<ErrorAuditRecords> pageLst = errorAuditRecordsService.queryPageErrorAuditRecords(page, map);
			for (ErrorAuditRecords errorAuditRecords : pageLst) {
				//交易处理码
				String reqProcess = errorAuditRecords.getReq_process();
				//交易消息类型
				int tradeMsgType = errorAuditRecords.getTrademsg_type();
				errorAuditRecords.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(reqProcess,tradeMsgType));
			}
			model.addAttribute("errorAuditRecords", pageLst);
			model.addAttribute("business_type", businessType);
			model.addAttribute("operation_type", operationType);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("获取差错审计数据出错：" + e.getMessage());
		}
		return JSP_PAGE;
	}
	
	@RequestMapping(value = QUERY_DETAIL, method = RequestMethod.POST)
	@ResponseBody
	public ErrorAuditRecords queryDetail(ServletRequest request) {
		logger.info("数据日志  进入差错审计日志详情查询...");
		ErrorAuditRecords errorAuditRecords = null;
		try {
			String tradeId = request.getParameter("tradeId");
			String recordTime = request.getParameter("recordTime");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(tradeId)) {
				map.put("tradeId", tradeId);
			}
			if (StringUtils.isNotBlank(recordTime)) {
				map.put("recordTime", recordTime);
			}
			errorAuditRecords = errorAuditRecordsService.queryDetail(map);
			String tradeTime = errorAuditRecords.getTrade_time();
			if (StringUtils.isNotBlank(tradeTime)) {
				errorAuditRecords.setTrade_time(tradeTime.substring(0, 19));
			}
			String tradeFee = errorAuditRecords.getTrade_fee();
			if (StringUtils.isNotBlank(tradeFee)) {
				if (errorAuditRecords.getError_resource() == 0) {
					//String tradeFee_ = tradeFee.substring(1, tradeFee.length());
					double trade_fee = Double.valueOf(tradeFee);
					double fee = trade_fee / 100;
					errorAuditRecords.setTrade_fee(String.format("%.2f", fee));
				} else {
					double trade_fee = Double.valueOf(tradeFee);
					double fee = trade_fee / 100;
					errorAuditRecords.setTrade_fee(String.format("%.2f", fee));
				}
			}
			//交易处理码
			String reqProcess = errorAuditRecords.getReq_process();
			//交易消息类型
			int tradeMsgType = errorAuditRecords.getTrademsg_type();
			errorAuditRecords.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(reqProcess,tradeMsgType));
			String deductRollBkSysTime = errorAuditRecords.getDeduct_rollbk_sys_time();
			if (StringUtils.isNotBlank(deductRollBkSysTime)) {
				errorAuditRecords.setDeduct_rollbk_sys_time(deductRollBkSysTime.substring(0, 19));
			}
			String deductSysTime = errorAuditRecords.getDeduct_sys_time();
			if (StringUtils.isNotBlank(deductSysTime)) {
				errorAuditRecords.setDeduct_sys_time(deductSysTime.substring(0, 19));
			}
			String deductStlmDate = errorAuditRecords.getDeduct_stlm_date();
			if (StringUtils.isNotBlank(deductStlmDate)) {
				errorAuditRecords.setDeduct_stlm_date(deductStlmDate.substring(0, 10));
			}
		} catch (Exception e) {
			logger.error("根据主键查询差错审计详情出错：" + e.getMessage());
		}
		return errorAuditRecords;
	}
}
