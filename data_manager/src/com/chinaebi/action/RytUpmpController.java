package com.chinaebi.action;

import java.util.HashMap;
import java.util.List;
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

import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.RytRefundLog;
import com.chinaebi.entity.RytUpmp;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.RytUpmpService;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.Ryt_trade_type;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 线上交易明细查询控制类
 * @author wufei
 *
 */
@Controller
public class RytUpmpController {
	private static Logger logger = LoggerFactory.getLogger(RytUpmpController.class);
	
	//线上交易数据查询
	private static final String QUERY_RYT_UMPM_DATA = "queryRytDataList.do";
	private static final String JSP_PAGE = "originalData/ryt_data_select";
	
	// 线上交易数据查询(收款交易数据查询——详情查询)
	private static final String QUERY_SK_DETAIL = "queryRytSkDataDetail.do";
	
	// 线上交易数据查询(退款交易数据查询——详情查询)
	private static final String QUERY_TK_DETAIL = "queryRytTkDataDetail.do";
	
	@Autowired
	@Qualifier(value = "rytUpmpService")
	private RytUpmpService rytUpmpService;
	
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	/**
	 * 分页查询收款、退款交易数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_RYT_UMPM_DATA, method = RequestMethod.POST)
	public String queryPageRytUpmp(ServletRequest request, Model model) {
		logger.info("进入线上交易数据查询...");
		String skTableName = null;
		String tkTableName = null;
		String deduct_sys_id = null;
		String inst_type = null;
		try {
			//分页
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			//查询参数
			String tradeType = request.getParameter("tradeType");
			String tseq = request.getParameter("tseq");
			String oid = request.getParameter("oid");
			String mid = request.getParameter("mid");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String tstat = request.getParameter("tstat");
			
			// 获取原始交易表名
			String bankId = request.getParameter("bank_id");
			String bank_id = null;
			String[] bankInstList = bankId.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bank_id = bankInstList[0];
				skTableName = bankInstList[2];
				tkTableName = bankInstList[3];
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			String gate = request.getParameter("gate");
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(oid)) {
				map.put("oid", oid);
			}
			if (StringUtils.isNotBlank(mid)) {
				map.put("mid", mid);
			}
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", startDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(tstat)) {
				map.put("tstat", tstat);
			}
			//查询UPMP收款交易
			if (Integer.valueOf(tradeType) == 1) {
				Page<RytUpmp> page = new Page<RytUpmp>();
				if (StringUtils.isNotBlank(curPage))
					page.setPageNo(Integer.parseInt(curPage.trim()));
				if (StringUtils.isNotBlank(pageSize))
					page.setPageSize(Integer.parseInt(pageSize.trim()));
				else
					page.setPageSize(10);
				if (StringUtils.isNotBlank(skTableName)) {
					map.put("skTableName", skTableName);
				}
				
				if (StringUtils.isNotBlank(gate)) {
					String[] str = gate.split(",");
					map.put("deduct_sys_id", str[0]);
					map.put("inst_type", str[1]);
				}
				
				Page<RytUpmp> pageList = rytUpmpService.queryPageRytUpmp(page, map);
				if(pageList != null && pageList.getResult() != null && pageList.getResult().size() > 0){
					List<RytUpmp> list = pageList.getResult();
					for (RytUpmp rytUpmp : list) {
						rytUpmp.setSys_date(DateUtil.parseTimePattern(rytUpmp.getSys_date(), rytUpmp.getSys_time()));
						rytUpmp.setTradeType(Ryt_trade_type.getRytTradeName(rytUpmp.getType()));
					}
				}
				model.addAttribute("rytUpmpData", pageList);
			}
			//查询UPMP退款交易
			else {
				Page<RytRefundLog> page = new Page<RytRefundLog>();
				if (StringUtils.isNotBlank(curPage))
					page.setPageNo(Integer.parseInt(curPage.trim()));
				if (StringUtils.isNotBlank(pageSize))
					page.setPageSize(Integer.parseInt(pageSize.trim()));
				else
					page.setPageSize(10);
				if (StringUtils.isNotBlank(tkTableName)) {
					map.put("tkTableName", tkTableName);
				}
				
				if (StringUtils.isNotBlank(gate)) {
					String[] str = gate.split(",");
					deduct_sys_id = str[0];
					inst_type = str[1];
					map.put("deduct_sys_id", "in(" + deduct_sys_id + ")");
					map.put("inst_type", inst_type);
				} else {
					List<InstInfo> instInfoList = dataManagerInit.getInstInfoByBankId(Integer.valueOf(bank_id));
					if (instInfoList != null && instInfoList.size() > 0) {
						StringBuilder sb = new StringBuilder();
						sb.append("in(");
						for (InstInfo instInfo : instInfoList) {
							sb.append(instInfo.getInstId()).append(",");
							inst_type = String.valueOf(instInfo.getInst_type());
						}
						sb.append(")");
						deduct_sys_id = sb.toString().replace(",)", ")");
						map.put("deduct_sys_id", deduct_sys_id);
						map.put("inst_type", inst_type);
					}
				}
				
				Page<RytRefundLog> pageList = rytUpmpService.queryPageRytRefundLog(page, map);
				if(pageList != null && pageList.getResult() != null && pageList.getResult().size() > 0){
					List<RytRefundLog> list = pageList.getResult();
					for (RytRefundLog rytRefundLog : list) {
						rytRefundLog.setRef_date(DateUtil.parseDatePattern(rytRefundLog.getRef_date()));
					}
				}
				model.addAttribute("rytRefundLog", pageList);
			}
			model.addAttribute("tradeType", tradeType);
			model.addAttribute("bankId", bankId);
			model.addAttribute("gate", gate);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("tstat", tstat);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return JSP_PAGE;
	}
	
	/**
	 * 查询收款交易明细数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = QUERY_SK_DETAIL)
	@ResponseBody
	public RytUpmp queryRytUpmpDetail(ServletRequest request) {
		RytUpmp rytUpmp = null;
		String skTableName = null;
		try {
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				skTableName = bankInstList[2];
			}
			String tseq = request.getParameter("tesq");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(skTableName)) {
				map.put("skTableName", skTableName);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			rytUpmp = rytUpmpService.queryDetailRytUpmp(map);
			if (rytUpmp != null) {
				rytUpmp.setSys_date(DateUtil.parseTimePattern(rytUpmp.getSys_date(), rytUpmp.getSys_time()));
				rytUpmp.setTradeType(Ryt_trade_type.getRytTradeName(rytUpmp.getType()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rytUpmp;
	}
	
	/**
	 * 查询退款交易明细数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = QUERY_TK_DETAIL)
	@ResponseBody
	public RytRefundLog querRytRefundLogDetai(ServletRequest request) {
		RytRefundLog refundLog = null;
		String skTableName = null;
		String tkTableName = null;
		String deduct_sys_id = null;
		try {
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				skTableName = bankInstList[2];
				tkTableName = bankInstList[3];
			}
			
			String gate = request.getParameter("gate");
			String[] str = gate.split(",");
			if (str != null && str.length > 0) {
				deduct_sys_id = str[0];
			}
			String id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(skTableName)) {
				map.put("skTableName", skTableName);
			}
			if (StringUtils.isNotBlank(tkTableName)) {
				map.put("tkTableName", tkTableName);
			}
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
			}
			if (StringUtils.isNotBlank(id)) {
				map.put("id", id);
			}
			refundLog =  rytUpmpService.querRytRefundLog(map);
			if (refundLog != null) {
				refundLog.setRef_date(DateUtil.parseDatePattern(refundLog.getRef_date()));
				refundLog.setP1(DateUtil.parseDatePattern(refundLog.getP1()));
				refundLog.setOrg_mdate(DateUtil.parseDatePattern(refundLog.getOrg_mdate()));
				return refundLog;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return refundLog;
	}
}
