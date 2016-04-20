package com.chinaebi.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
import com.chinaebi.entity.DuizhangErrorData;
import com.chinaebi.service.DuizhangErrorDataService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class DuizhangErrorDataController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//差错单查询
	private static final String QUERYPAGECUPSERRORDATA = "/queryPageCupsErrorData.do";
	private static final String DZERRORCUPSDATA = "/duizhangData/errorcupsdata";
	
	//差错单详情查询
	private static final String QUERYERRORCUPSDETAILLDATA = "/queryErrorCupsDetailData.do";
	
	//银联差错对账明细查询  分页查询
	private static final String QUERY_ALL = "queryErrorDzData.do";
	private static final String JSP_PAGE = "duizhangResultData/duizhangErrorCupsLst";
	
	//银联差错对账明细查询  详情查询
	private static final String QUERY_DETAIL = "queryErrorDzDataDetail.do";
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<DuizhangErrorData> errorDzDataDao;
	
	@Autowired
	@Qualifier(value = "commonClass")
	private CommonClass commonClass;
	
	@Autowired
	@Qualifier(value = "duizhangErrorDataService")
	private DuizhangErrorDataService duizhangErrorDataService;
	
	/**
	 * 差错单数据查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERYPAGECUPSERRORDATA ,method = RequestMethod.POST)
	public String queryPageCupsErrorData(ServletRequest request,Model model){
		logger.info("进入银联差错文件数据分页查询方法……");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String reqSysStance = request.getParameter("reqSysStance");
		String error_trade_flag = request.getParameter("error_trade_flag");
		String deductSysReference = request.getParameter("deductSysReference");
		String outAccount = request.getParameter("outAccount");
		String origDataStance = request.getParameter("origDataStance");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String inst_id = request.getParameter("inst_name");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(reqSysStance)){
			map.put("reqSysStance", reqSysStance.trim());
		}
		if(StringUtils.isNotBlank(error_trade_flag)){
			map.put("error_trade_flag", error_trade_flag.trim());
		}
		if(StringUtils.isNotBlank(deductSysReference)){
			map.put("deductSysReference", deductSysReference.trim());
		}
		if(StringUtils.isNotBlank(outAccount)){
			map.put("outAccount", outAccount.trim());
		}
		if(StringUtils.isNotBlank(origDataStance)){
			map.put("origDataStance", origDataStance.trim());
		}
		if(StringUtils.isNotBlank(startTime)){
			map.put("startTime", startTime.replace(" ", "").replace("-", "").replace(":", ""));
		}
		if(StringUtils.isNotBlank(endTime)){
			map.put("endTime", endTime.replace(" ", "").replace("-", "").replace(":", ""));
		}
		if (StringUtils.isNotBlank(inst_id)) {
			map.put("error_dz_data_tableName", inst_id);
		}
		Page<DuizhangErrorData> page = new Page<DuizhangErrorData>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		
		Page<DuizhangErrorData> duiErrorDataPage = duizhangErrorDataService.queryPageDzErrorCupsLst(page, map);
		List<DuizhangErrorData> list = duiErrorDataPage.getResult();
		for (DuizhangErrorData duizhangErrorCupsLst : list) {
			duizhangErrorCupsLst.setOnTradeAccount(new Double(duizhangErrorCupsLst.getOnTradeAccount())/100+"");
			duizhangErrorCupsLst.setErrorTradePayFee(0==Integer.valueOf(duizhangErrorCupsLst.getErrorTradePayFee())?"0":new Double(duizhangErrorCupsLst.getErrorTradePayFee())/100+"");
			duizhangErrorCupsLst.setErrorTradeReceiveFee(0==Integer.valueOf(duizhangErrorCupsLst.getErrorTradeReceiveFee())?"0":new Double(duizhangErrorCupsLst.getErrorTradeReceiveFee())/100+"");
			duizhangErrorCupsLst.setReqTime(patternSelf(duizhangErrorCupsLst.getReqTime()));
			duizhangErrorCupsLst.setOnTradeTime(patternSelf(duizhangErrorCupsLst.getOnTradeTime()));
			duizhangErrorCupsLst.setOnDeduct_stlm_date(patternSelf(duizhangErrorCupsLst.getOnDeduct_stlm_date()));
		}
		model.addAttribute("pageErrorCups", duiErrorDataPage);
		model.addAttribute("inst_id", inst_id);
		model.addAttribute("pageSize", pageSize);
		return DZERRORCUPSDATA;
	}
	
	/**
	 * 银联差错数据详细信息查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value=QUERYERRORCUPSDETAILLDATA,method=RequestMethod.POST)
	@ResponseBody
	public DuizhangErrorData queryErrorCupsDetailData(ServletRequest request,ServletResponse response){
		logger.info("进入银联差错数据详细信息查询方法");
		String id = request.getParameter("id");
		String inst_id = request.getParameter("inst_name");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(id)) {
			map.put("id", id);
		}
		if (StringUtils.isNotBlank(inst_id)) {
			map.put("error_dz_data_tableName", inst_id);
		}
		DuizhangErrorData duizhangErrorData = duizhangErrorDataService.queryDetailById(map);
		return duizhangErrorData;
	}
	
	/**
	 * 银联差错对账明细查询  分页查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryDuizhangErrorCupsLst(ServletRequest request, Model model) {
		logger.info("进入银联差错对账明细查询  分页查询...");
		String error_dz_data_tableName = null;
		try {
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<DuizhangErrorData> page = new Page<DuizhangErrorData>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(50);
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String reqSysStance = request.getParameter("reqSysStance");
			String handling_id = request.getParameter("handling_id");
			error_dz_data_tableName = request.getParameter("inst_name");
			String bk_chk = request.getParameter("bk_chk");
			String deductSysReference = request.getParameter("deductSysReference");
			String origDataStance = request.getParameter("origDataStance");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(reqSysStance)) {
				map.put("reqSysStance", reqSysStance);
			}
			if (StringUtils.isNotBlank(deductSysReference)) {
				map.put("deductSysReference", deductSysReference);
			}
			if (StringUtils.isNotBlank(bk_chk)) {
				map.put("bk_chk", bk_chk);
			} else {
				map.put("bk_chk", 0);
			}
			if (StringUtils.isNotBlank(error_dz_data_tableName)) {
				map.put("error_dz_data_tableName", error_dz_data_tableName);
			}
			if (StringUtils.isNotBlank(origDataStance)) {
				map.put("origDataStance", origDataStance);
			}
			
			Page<DuizhangErrorData> duiErrorDataPage = errorDzDataDao.queryForPage(page, "Duizhang_ErrorData.queryPageErrorDzData", "Duizhang_ErrorData.queryCount", map);
			if (duiErrorDataPage.getResult().size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (DuizhangErrorData duizhangErrorData : duiErrorDataPage) {
					//处理时间
					String tradeTime = duizhangErrorData.getReqTime();
					sb.setLength(0);
					sb.append(tradeTime.substring(0, 4)).append("-").append(tradeTime.substring(4, 6)).append("-").
					append(tradeTime.substring(6, 8)).append(" ").append(tradeTime.substring(8, 10)).append(":").
					append(tradeTime.substring(10, 12)).append(":").append(tradeTime.substring(tradeTime.length() - 2, tradeTime.length()));
					duizhangErrorData.setReqTime(sb.toString());
					//处理卡号
					String outAccount = duizhangErrorData.getOutAccount();
					sb.setLength(0);
					sb.append(outAccount.substring(0, 6)).append("***").append(outAccount.substring(outAccount.length() - 4, outAccount.length()));
					duizhangErrorData.setOutAccount(sb.toString());
					//差错原因
					String errorInfo = duizhangErrorData.getError_info();
					duizhangErrorData.setHandling_name(commonClass.getHandlingNameById(commonClass.getIdByReasonCode(errorInfo)));
				}
			} else {
				logger.info("查询银联差错对账文件" + error_dz_data_tableName + "为空..." );
			}
			model.addAttribute("pageDataLst", duiErrorDataPage);
			model.addAttribute("handling_name", handling_id);
			model.addAttribute("table_name", error_dz_data_tableName);
			model.addAttribute("bk_chk", bk_chk);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("查询银联差错对账文件" + error_dz_data_tableName + "出错：" + e.getMessage());
		}
		return JSP_PAGE;
	}
	
	/**
	 * 银联差错对账明细查询  详情查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = QUERY_DETAIL)
	@ResponseBody
	public DuizhangErrorData queryDetail(ServletRequest request) {
		logger.info("进入银联差错对账明细查询  详情查询...");
		String error_dz_data_tableName = null;
		String id = null;
		DuizhangErrorData duizhangErrorData = null;
		try {
			error_dz_data_tableName = request.getParameter("inst_name");
			id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(error_dz_data_tableName)) {
				map.put("error_dz_data_tableName", error_dz_data_tableName);
			}
			if (StringUtils.isNotBlank(id)) {
				map.put("id", id);
			}
			duizhangErrorData = duizhangErrorDataService.queryDetailById(map);
			if (duizhangErrorData != null) {
				StringBuilder sb = new StringBuilder();
				//处理交易时间
				String reqTime = duizhangErrorData.getReqTime();
				sb.setLength(0);
				sb.append(reqTime.substring(0, 4)).append("-").append(reqTime.substring(4, 6)).append("-").
				append(reqTime.substring(6, 8)).append(" ").append(reqTime.substring(8, 10)).append(":").
				append(reqTime.substring(10, 12)).append(":").append(reqTime.substring(reqTime.length() - 2, reqTime.length()));
				duizhangErrorData.setReqTime(sb.toString());
				
				//差错原因
				String errorInfo = duizhangErrorData.getError_info();
				duizhangErrorData.setReason_desc(commonClass.getReasonDescByReasonCode(errorInfo));
				
				//上一笔交易的时间
				String onTradeTime = duizhangErrorData.getOnTradeTime();
				sb.setLength(0);
				sb.append(reqTime.substring(0, 4)).append("-").append(onTradeTime.substring(0, 2)).append("-").
				append(onTradeTime.substring(2, 4)).append(" ").append(onTradeTime.substring(4, 6)).append(":").
				append(onTradeTime.substring(6, 8)).append(":").append(onTradeTime.substring(8, 10));
				duizhangErrorData.setOnTradeTime(sb.toString());
				
				//上一笔交易的清算日期
				String onDeduct_stlm_date = duizhangErrorData.getOnDeduct_stlm_date();
				sb.setLength(0);
				sb.append(reqTime.substring(0, 4)).append("-").append(onDeduct_stlm_date.substring(0, 2)).append("-").
				append(onDeduct_stlm_date.substring(2, 4));
				duizhangErrorData.setOnDeduct_stlm_date(sb.toString());
			}
		} catch (Exception e) {
			logger.error("根据机构主键" + id + "查询" + error_dz_data_tableName + "详情数据出错：" + e.getMessage());
		}
		return duizhangErrorData;
	}
	
	private String patternSelf(String time) {
		if(StringUtils.isNotBlank(time)){
			if(time.length()==14){
				time = time.substring(0, 8);
				time = time.substring(0, 4)+"-"+time.substring(4, 6)+"-"+time.substring(6, 8);
				return time;
			}else if(time.length()==10){
				time = time.substring(0, 2)+"-"+time.substring(2, 4)+" "+time.substring(4, 6)+":"+time.substring(6, 8)+":"+time.substring(8, 10);
				return time;
			}else if(time.length()==4){
				time = time.substring(0, 2)+"-"+time.substring(2, 4);
				return time;
			}
		}
		return null;
	}
}

