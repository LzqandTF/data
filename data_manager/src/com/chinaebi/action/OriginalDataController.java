package com.chinaebi.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.OriginalData;
import com.chinaebi.entity.RytUpmp;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.DuizhangResultService;
import com.chinaebi.service.RytUpmpService;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.Ryt_trade_type;
import com.chinaebi.utils.StringPingJie;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 交易数据查询
 */
@Controller
public class OriginalDataController {
	// 记录查询时的日志
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//原始交易数据查询
	private static final String QUERY_ORIGINAL_DATA = "queryOriginalData.do";
	private static final String ORIGINAL_DEDUCT_CHANNEL_SELECT = "originalData/originalDeductChannelSelect";
	
	//原始交易详情查询
	private static final String QUERY_DETAIL_BY_TRADE_ID = "queryDetailByTradeId.do";
	
	// 对账明细查询
	private static final String QUERY_DUIZHANG_ORIGINAL_DATA = "queryDuizhangOriginalData.do";
	// 对账明细查询(金额统计：对账成功金额、商户手续费、对账失败金额、未对账金额)
	private static final String QUERY_DY_MONEY = "queryDYMoney.do";
	private static final String DUIZHANG_DETAILS_SELECT = "duizhangResultData/duizhangDetailsSelect";
	
	// 对账明细查询(详情查询)
	private static final String QUERY_DETAIL = "queryOriginalDataDetail.do";
	
	//对账结果查询(对账笔数统计)
	private static final String QUERY_DUIZHANG_RESULT = "queryDuizhangResult.do";
	//对账结果查询(金额统计)
	private static final String QUERY_DZ_MONEY = "queryDuizhangMoney.do";
	
	//差错对账结果查询
	private static final String QUERY_ERROR_DZ_RESULT = "queryErrorDuizhangResult.do";
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<OriginalData> originalDataDao;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<String> originalMoney;
	
	@Autowired
	@Qualifier(value = "duizhangResultService")
	private DuizhangResultService duizhangResultService;
	
	@Autowired
	@Qualifier(value = "commonClass")
	private CommonClass commonClass;
	
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "rytUpmpService")
	private RytUpmpService rytUpmpService;
	
	private StringPingJie stringPingJie = StringPingJie.getInstance();
	
	@RequestMapping(value = QUERY_ORIGINAL_DATA, method = RequestMethod.POST)
	public String queryOriginalData(ServletRequest request, Model model) {
		logger.info("进入线下交易数据查询...");
		String original_data_tableName = null;
		String deduct_sys_id = null;
		String inst_type = null;
		try {
			//分页
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<OriginalData> page = new Page<OriginalData>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String req_sys_stance = request.getParameter("req_sys_stance");
			String out_account = request.getParameter("out_account");
			String req_mer_code = request.getParameter("req_mer_code");
			String deduct_mer_code = request.getParameter("deduct_mer_code");
			String tableName = request.getParameter("channel");
			String[] str = tableName.split(",");
			if (str != null && str.length > 0) {
				deduct_sys_id = str[0];
				inst_type = str[1];
			}
			String additional_data = request.getParameter("additional_data");
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");
			String whtherInnerJs = request.getParameter("whtherInnerJs");
			String req_mer_term_id = request.getParameter("req_mer_term_id");
			String deduct_roll_bk_stance = request.getParameter("deduct_roll_bk_stance");
			
			//银行机构信息
			String bankId = request.getParameter("bank_id");
			String[] bankInstList = bankId.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				original_data_tableName = bankInstList[1];
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(req_sys_stance)) {
				map.put("req_sys_stance", req_sys_stance);
			}
			if (StringUtils.isNotBlank(out_account)) {
				map.put("out_account", out_account);
			}
			if (StringUtils.isNotBlank(req_mer_code)) {
				map.put("req_mer_code", req_mer_code);
			}
			if (StringUtils.isNotBlank(deduct_mer_code)) {
				map.put("deduct_mer_code", deduct_mer_code);
			}
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
			}
			if (StringUtils.isNotBlank(original_data_tableName)) {
				map.put("original_data_tableName", original_data_tableName);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(additional_data)) {
				map.put("additional_data", additional_data);
			}
			if (StringUtils.isNotBlank(deduct_stlm_date)) {
				map.put("deduct_stlm_date", deduct_stlm_date);
			}
			if (StringUtils.isNotBlank(whtherInnerJs)) {
				map.put("whtherInnerJs", whtherInnerJs);
			}
			if (StringUtils.isNotBlank(req_mer_term_id)) {
				map.put("req_mer_term_id", req_mer_term_id);
			}
			if (StringUtils.isNotBlank(deduct_roll_bk_stance)) {
				map.put("deduct_roll_bk_stance", deduct_roll_bk_stance);
			}
			
			Page<OriginalData> originalDataPage = originalDataDao.queryForPage(page, "Original_Data.queryPageOriginalData", "Original_Data.queryCount", map);
			StringBuilder sb = new StringBuilder();
			for (OriginalData originalData : originalDataPage) {
				//账号
				String outAccount = originalData.getOut_account();
				//处理码
				String process = originalData.getReq_process();
				//交易消息类型
				int trade_msg_type = originalData.getTrademsg_type();
				//终端信息
				String terminalInfo = originalData.getTerminal_info();
				
				//账号显示方式
				sb.setLength(0);
				sb.append(outAccount.substring(0, 6)).append("***").append(outAccount.substring(outAccount.length() - 4, outAccount.length()));
				originalData.setOut_account(sb.toString());
				//交易类型
				originalData.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
				//交易类别
				originalData.setTradeName(commonClass.getTradeNameByTerminalInfo(terminalInfo));
			}
			model.addAttribute("getDataResult", originalDataPage);
			model.addAttribute("inst_id", tableName);
			model.addAttribute("whtherInnerJs", whtherInnerJs);
			model.addAttribute("bankId", bankId);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("查询原始交易" + original_data_tableName + "数据出错：" + e.getMessage());
		}
		return ORIGINAL_DEDUCT_CHANNEL_SELECT;
	}
	
	@RequestMapping(value = QUERY_DETAIL_BY_TRADE_ID)
	@ResponseBody
	public OriginalData queryDetailByTradeId(ServletRequest request) {
		logger.info("进入线下交易详情查询...");
		String original_data_tableName = null;
		String trade_id = null;
		OriginalData originalData = null;
		try {
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				original_data_tableName = bankInstList[1];
			}
			trade_id = request.getParameter("trade_id");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(original_data_tableName)) {
				map.put("original_data_tableName", original_data_tableName);
			}
			if (StringUtils.isNotBlank(trade_id)) {
				map.put("trade_id", trade_id);
			}
			originalData = originalDataDao.queryForObject("Original_Data.queryDetail", map);
			//电银手续费
			String trade_fee = originalData.getTrade_fee();
			if (StringUtils.isNotBlank(trade_fee)) {
				String tradeFee = trade_fee.substring(1, trade_fee.length());
				double freeMoney = Double.valueOf(tradeFee);
				if (freeMoney > 0) {
					double fee = freeMoney / 100;
					originalData.setTrade_fee(String.format("%.2f", fee));
				}
			}
			//处理码
			String process = originalData.getReq_process();
			//交易消息类型
			int trade_msg_type = originalData.getTrademsg_type();
			originalData.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
			//处理清算日期
			String deductStlmDate = originalData.getDeduct_stlm_date();
			if (StringUtils.isNotBlank(deductStlmDate)) {
				originalData.setDeduct_stlm_date(deductStlmDate.substring(0, 10));
			}
		} catch (Exception e) {
			logger.error("原始交易详情查询——根据机构主键" + trade_id + "查询" + original_data_tableName + "详情数据出错：" + e.getMessage());
		}
		return originalData;
	}
	
	/**
	 * 线下对账明细查询  分页获取原始交易数据列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_DUIZHANG_ORIGINAL_DATA, method = RequestMethod.POST)
	public String queryDuizhangOriginalData(ServletRequest request, Model model) {
		logger.info("进入对账明细查询...");
		String original_data_tableName = null;
		String deduct_sys_id = null;
		String inst_type = null;
		try {
			//分页
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			
			String mflag = request.getParameter("mflag");
			String dzSuccessMoney = request.getParameter("dzSuccessMoney");
			String merFee = request.getParameter("merFee");
			String dzFailMoney = request.getParameter("dzFailMoney");
			String noDzMoney = request.getParameter("noDzMoney");
			String noDzMoney1 = request.getParameter("noDzMoney1");
			String dzSuccessMoney1 = request.getParameter("dzSuccessMoney1");
			String dzFailMoney1 = request.getParameter("dzFailMoney1");
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String deduct_sys_stance = request.getParameter("deduct_sys_stance");
			String out_account = request.getParameter("out_account");
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");
			String deduct_mer_code = request.getParameter("deduct_mer_code");
			String additional_data = request.getParameter("additional_data");
			String req_mer_term_id = request.getParameter("req_mer_term_id");
			String whtherInnerJs = request.getParameter("whtherInnerJs");
			String bk_chk = request.getParameter("bk_chk");
			String req_mer_code = request.getParameter("req_mer_code");
			String whetherRiqie = request.getParameter("whetherRiqie");
			//参考号
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			String deduct_result = request.getParameter("deduct_result");
			
			String bank_type = null;
			// 获取原始交易表名
			String bankId = request.getParameter("bank_id");
			String[] bankInstList = bankId.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bank_type = bankInstList[1];
				original_data_tableName = bankInstList[2];
			}
			
			// 获取渠道ID和渠道类型
			String tableName = request.getParameter("channel");
			if (StringUtils.isNotBlank(tableName)) {
				String[] str = tableName.split(",");
				if (str != null && str.length > 0) {
					deduct_sys_id = str[0];
					inst_type = str[1];
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if ("0".equals(bank_type)) {
				Page<OriginalData> page = new Page<OriginalData>();
				if (StringUtils.isNotBlank(curPage))
					page.setPageNo(Integer.parseInt(curPage.trim()));
				if (StringUtils.isNotBlank(pageSize))
					page.setPageSize(Integer.parseInt(pageSize.trim()));
				else
					page.setPageSize(10);
				
				if (StringUtils.isNotBlank(startTime)) {
					map.put("startTime", DateUtil.getformatConversionStart(startTime));
				}
				if (StringUtils.isNotBlank(endTime)) {
					map.put("endTime", DateUtil.getformatConversionEnd(endTime));
				}
				if (StringUtils.isNotBlank(deduct_sys_stance)) {
					map.put("deduct_sys_stance", deduct_sys_stance);
				}
				if (StringUtils.isNotBlank(out_account)) {
					map.put("out_account", out_account);
				}
				if (StringUtils.isNotBlank(deduct_stlm_date)) {
					map.put("deduct_stlm_date", deduct_stlm_date);
				}
				if (StringUtils.isNotBlank(deduct_mer_code)) {
					map.put("deduct_mer_code", deduct_mer_code);
				}
				if (StringUtils.isNotBlank(deduct_sys_id)) {
					map.put("deduct_sys_id", deduct_sys_id);
				}
				if (StringUtils.isNotBlank(original_data_tableName)) {
					map.put("original_data_tableName", original_data_tableName);
				}
				if (StringUtils.isNotBlank(inst_type)) {
					map.put("inst_type", inst_type);
				}
				if (StringUtils.isNotBlank(additional_data)) {
					map.put("additional_data", additional_data);
				}
				if (StringUtils.isNotBlank(req_mer_term_id)) {
					map.put("req_mer_term_id", req_mer_term_id);
				}
				if (StringUtils.isNotBlank(whtherInnerJs)) {
					map.put("whtherInnerJs", whtherInnerJs);
				}
				if (StringUtils.isNotBlank(req_mer_code)) {
					map.put("req_mer_code", req_mer_code);
				}
				if (StringUtils.isNotBlank(whetherRiqie)) {
					map.put("whetherRiqie", whetherRiqie);
				}
				if (StringUtils.isNotBlank(bk_chk)) {
					map.put("bk_chk", bk_chk);
				}
				if (StringUtils.isNotBlank(deduct_sys_reference)) {
					map.put("deduct_sys_reference", deduct_sys_reference);
				}
				if (StringUtils.isNotBlank(deduct_result)) {
					if ("2".equals(deduct_result)) {
						map.put("deduct_result", 0);
					}
					if ("3".equals(deduct_result)) {
						map.put("deduct_result", 2);
					}
					if ("6".equals(deduct_result)) {
						map.put("deduct_result", 1);
					}
				}
				
				Page<OriginalData> originalDataPage = originalDataDao.queryForPage(page, "Original_Data.queryPageDuizhangOriginalData", "Original_Data.queryDuizhangCount", map);
				for (OriginalData originalData : originalDataPage) {
					//终端信息
					String terminalInfo = originalData.getTerminal_info();
					//交易类别
					originalData.setTradeName(commonClass.getTradeNameByTerminalInfo(terminalInfo));
				}
				model.addAttribute("getDataResult", originalDataPage);
			} else {
				Page<RytUpmp> page = new Page<RytUpmp>();
				if (StringUtils.isNotBlank(curPage))
					page.setPageNo(Integer.parseInt(curPage.trim()));
				if (StringUtils.isNotBlank(pageSize))
					page.setPageSize(Integer.parseInt(pageSize.trim()));
				else
					page.setPageSize(10);
				
				if (StringUtils.isNotBlank(original_data_tableName)) {
					map.put("skTableName", original_data_tableName);
				}
				if (StringUtils.isNotBlank(deduct_sys_stance)) {
					map.put("tseq", deduct_sys_stance);
				}
				if (StringUtils.isNotBlank(additional_data)) {
					map.put("oid", additional_data);
				}
				if (StringUtils.isNotBlank(deduct_sys_id)) {
					map.put("deduct_sys_id", deduct_sys_id);
				}
				if (StringUtils.isNotBlank(inst_type)) {
					map.put("inst_type", inst_type);
				}
				if (StringUtils.isNotBlank(out_account)) {
					map.put("card_no", out_account);
				}
				if (StringUtils.isNotBlank(req_mer_code)) {
					map.put("mid", req_mer_code);
				}
				if (StringUtils.isNotBlank(whetherRiqie)) {
					map.put("whetherRiqie", whetherRiqie);
					model.addAttribute("whetherRiqie", whetherRiqie);
				}
				if (StringUtils.isNotBlank(bk_chk)) {
					map.put("bk_chk", bk_chk);
					model.addAttribute("bk_chk", bk_chk);
				}
				if (StringUtils.isNotBlank(startTime)) {
					map.put("startDate", startTime.replace("-", ""));
				}
				if (StringUtils.isNotBlank(endTime)) {
					map.put("endDate", endTime.replace("-", ""));
				}
				if (StringUtils.isNotBlank(deduct_stlm_date)) {
					map.put("deductStlmDate", deduct_stlm_date.replace("-", ""));
				}
				if (StringUtils.isNotBlank(deduct_result)) {
					map.put("tstat", deduct_result);
				}
				if (StringUtils.isNotBlank(whtherInnerJs)) {
					map.put("whtherInnerJs", whtherInnerJs);
				}
				
				Page<RytUpmp> page_ = rytUpmpService.queryPageRytUpmpForDz(page, map);
				if(page_ != null && page_.getResult() != null && page_.getResult().size() > 0){
					List<RytUpmp> list_ = page_.getResult();
					for (RytUpmp rytUpmp : list_) {
						rytUpmp.setSys_date(DateUtil.parseTimePattern(rytUpmp.getSys_date(), rytUpmp.getSys_time()));
						rytUpmp.setTradeType(Ryt_trade_type.getRytTradeName(rytUpmp.getType()));
					}
				}
				model.addAttribute("rytUpmpData", page_);
			}
			model.addAttribute("bankId", bankId);
			model.addAttribute("inst_id", tableName);
			model.addAttribute("whtherInnerJs", whtherInnerJs);
			model.addAttribute("bk_chk", bk_chk);
			model.addAttribute("whetherRiqie", whetherRiqie);
			model.addAttribute("deduct_result", deduct_result);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("bank_type", bank_type);
			model.addAttribute("mflag", mflag);
			model.addAttribute("dzSuccessMoney", dzSuccessMoney);
			model.addAttribute("merFee", merFee);
			model.addAttribute("dzFailMoney", dzFailMoney);
			model.addAttribute("noDzMoney", noDzMoney);
			model.addAttribute("noDzMoney1", noDzMoney1);
			model.addAttribute("dzSuccessMoney1", dzSuccessMoney1);
			model.addAttribute("dzFailMoney1", dzFailMoney1);
		} catch (Exception e) {
			logger.error("查询原始交易" + original_data_tableName + "数据出错：" + e.getMessage());
		}
		return DUIZHANG_DETAILS_SELECT;
	}
	
	@RequestMapping(value = QUERY_DETAIL)
	@ResponseBody
	public OriginalData queryDetail(ServletRequest request) {
		logger.info("进入原始交易数据详情查询...");
		String original_data_tableName = null;
		String trade_id = null;
		OriginalData originalData = null;
		try {
			String bank_id = request.getParameter("bank_id");
			String bank_type = null;
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bank_type = bankInstList[1];
				original_data_tableName = bankInstList[2];
			}
			trade_id = request.getParameter("trade_id");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(original_data_tableName)) {
				map.put("original_data_tableName", original_data_tableName);
			}
			if (StringUtils.isNotBlank(trade_id)) {
				map.put("trade_id", trade_id);
			}
			if ("0".equals(bank_type)) {
				
			}
			originalData = originalDataDao.queryForObject("Original_Data.queryDetail", map);
			//电银手续费
			String trade_fee = originalData.getTrade_fee();
			if (StringUtils.isNotBlank(trade_fee)) {
				String tradeFee = trade_fee.substring(1, trade_fee.length());
				double freeMoney = Double.valueOf(tradeFee);
				if (freeMoney > 0) {
					double fee = freeMoney / 100;
					originalData.setTrade_fee(String.format("%.2f", fee));
				}
			}
			//处理码
			String process = originalData.getReq_process();
			//交易消息类型
			int trade_msg_type = originalData.getTrademsg_type();
			originalData.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
			//处理清算日期
			String deductStlmDate = originalData.getDeduct_stlm_date();
			if (StringUtils.isNotBlank(deductStlmDate)) {
				originalData.setDeduct_stlm_date(deductStlmDate.substring(0, 10));
			}
		} catch (Exception e) {
			logger.error("根据机构主键" + trade_id + "查询" + original_data_tableName + "详情数据出错：" + e.getMessage());
		}
		return originalData;
	}
	
	/**
	 * 对账结果查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_DUIZHANG_RESULT)
	public void queryDuizhangResult(ServletRequest request,ServletResponse response) {
		logger.info("进入对账结果查询...");
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			String trade_date = request.getParameter("trade_date");
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");
			
			String original_data_tableName = null;
			String dz_data_tableName = null;
			
			String bankId = null;
			String bankType = null;
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bankId = bankInstList[0];
				bankType = bankInstList[1];
				dz_data_tableName = bankInstList[2];
				original_data_tableName = bankInstList[3];
			}
			
			String channel = request.getParameter("channel");
			
			Map<String, Object> map = new HashMap<String, Object>();
			//交易日期
			if (StringUtils.isNotBlank(trade_date)) {
				if ("0".equals(bankType)) {
					map.put("original_tradeTime", trade_date);
				} else {
					map.put("original_tradeTime", trade_date.replace("-", ""));
				}
				map.put("dz_tradeTime", trade_date.replace("-", ""));
				map.put("flag", 1);
			}
			//清算日期
			if (StringUtils.isNotBlank(deduct_stlm_date)) {
				if ("0".equals(bankType)) {
					map.put("original_tradeTime", deduct_stlm_date);
				} else {
					map.put("original_tradeTime", deduct_stlm_date.replace("-", ""));
				}
				map.put("dz_tradeTime", deduct_stlm_date.replace("-", ""));
				map.put("flag", 2);
			}
			if (StringUtils.isNotBlank(original_data_tableName)) {
				map.put("original_table_name", original_data_tableName);
				map.put("receipttablename", original_data_tableName);
			}
			if (StringUtils.isNotBlank(dz_data_tableName)) {
				map.put("duizhang_table_name", dz_data_tableName);
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			if (StringUtils.isNotBlank(channel)) {
				String[] instInfoList = channel.split(",");
				map.put("deduct_sys_id", instInfoList[0]);
				
				InstInfo instInfo = dataManagerInit.getInstInfoById(Integer.valueOf(instInfoList[0]), Integer.valueOf(instInfoList[1]));
				if ("0".equals(bankType)) {
					resultMap = duizhangResultService.proceDuizhangResult(map);
				} else {
					resultMap = duizhangResultService.proceRytUpmpDzResult(map);
				}
				sb.append("{");
					sb.append("original_tradeTime : '"+resultMap.get("original_tradeTime")+"',");
					sb.append("infoName : '"+instInfo.getName()+"',");
					sb.append("original_trade_count : "+resultMap.get("original_trade_count")+",");
					sb.append("duizhang_trade_count : "+resultMap.get("duizhang_trade_count")+",");
					sb.append("original_dz_success : "+resultMap.get("original_dz_success")+",");
					sb.append("original_dz_error : "+resultMap.get("original_dz_error")+",");
					sb.append("original_dz_no : "+resultMap.get("original_dz_no")+",");
					sb.append("original_dz_noNeed : "+resultMap.get("original_dz_noNeed"));
				sb.append("},");
			} else {
				List<InstInfo> list = dataManagerInit.getInstInfoByBankId(Integer.valueOf(bankId));
				for (InstInfo info : list) {
					map.put("infoName", info.getName());
					map.put("deduct_sys_id", info.getInstId());
					
					if ("0".equals(bankType)) {
						resultMap = duizhangResultService.proceDuizhangResult(map);
					} else {
						resultMap = duizhangResultService.proceRytUpmpDzResult(map);
					}
					sb.append("{");
						sb.append("original_tradeTime : '"+resultMap.get("original_tradeTime")+"',");
						sb.append("infoName : '"+info.getName()+"',");
						sb.append("original_trade_count : "+resultMap.get("original_trade_count")+",");
						sb.append("duizhang_trade_count : "+resultMap.get("duizhang_trade_count")+",");
						sb.append("original_dz_success : "+resultMap.get("original_dz_success")+",");
						sb.append("original_dz_error : "+resultMap.get("original_dz_error")+",");
						sb.append("original_dz_no : "+resultMap.get("original_dz_no")+",");
						sb.append("original_dz_noNeed : "+resultMap.get("original_dz_noNeed"));
					sb.append("},");
				}
			}
			sb.append("]");
			String json = sb.toString().replaceAll("},]", "}]");
			out.print(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("对账结果查询出现异常：" + e.getMessage());
		}
	}
	
	/**
	 * 对账结果查询(金额统计)
	 * @param map
	 * @param model
	 */
	@RequestMapping(value = QUERY_DZ_MONEY, method = RequestMethod.POST)
	@ResponseBody
	public String queryDuizhangMoney(ServletRequest request){
		logger.info("对账金额统计如下：");
		int flag = 1;
		double dzSuccessMoney = 0;
		double dzFailMoney = 0;
		try {
			String trade_date = request.getParameter("trade_date");
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");
			
			String bankType = null;
			String original_data_tableName = null;
			
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bankType = bankInstList[1];
				original_data_tableName = bankInstList[3];
			}
			
			String deduct_sys_id = null;
			String channel = request.getParameter("channel");
			if (StringUtils.isNotBlank(channel)) {
				String[] instInfoList = channel.split(",");
				deduct_sys_id = instInfoList[0];
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			//交易日期
			if (StringUtils.isNotBlank(trade_date)) {
				if ("0".equals(bankType)) {
					map.put("trade_date", trade_date);
				} else {
					map.put("trade_date", trade_date.replace("-", ""));
				}
			}
			//清算日期
			if (StringUtils.isNotBlank(deduct_stlm_date)) {
				if ("0".equals(bankType)) {
					map.put("deduct_stlm_date", deduct_stlm_date);
				} else {
					map.put("trade_date", deduct_stlm_date.replace("-", ""));
				}
			}
			if (StringUtils.isNotBlank(original_data_tableName)) {
				map.put("original_data_tableName", original_data_tableName);
				map.put("skTableName", original_data_tableName);
			}
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
			}
			
			//线下对账金额统计
			if ("0".equals(bankType)) {
				//对账成功金额
				if (flag == 1) {
					map.put("bk_chk", 1);
					String  money = originalMoney.queryMoney("Original_Data.queryMoney", map);
					dzSuccessMoney = Double.valueOf(money) / 100;
					logger.info(stringPingJie.getStringPingJie("对账成功金额：" , String.format("%.2f",dzSuccessMoney)));
					map.put("dzSuccessMoney",String.format("%.2f",dzSuccessMoney));
					list.add(map);
					flag = 2;
				}
				if (flag == 2) { //对账失败金额
					map.put("bk_chk", 2);
					String money = originalMoney.queryMoney("Original_Data.queryMoney", map);
					dzFailMoney = Double.valueOf(money) / 100;
					logger.info(stringPingJie.getStringPingJie("对账失败金额：" , String.format("%.2f",dzFailMoney)));
					map.put("dzFailMoney",String.format("%.2f",dzFailMoney));
					list.add(map);
				}
			} else {  //线上对账金额统计
				//对账成功金额
				if (flag == 1) {
					map.put("bk_chk", 1);
					String skTotalMoney = rytUpmpService.queryTotalMoneyOfDz(map);
					double totalSkMoney = 0d;
					if(StringUtils.isNotBlank(skTotalMoney) && !"null".equals(skTotalMoney)){
						totalSkMoney = Double.valueOf(skTotalMoney);
					}
					dzSuccessMoney = totalSkMoney / 100;
					logger.info("对账成功金额：" + String.format("%.2f",dzSuccessMoney));
					map.put("dzSuccessMoney",String.format("%.2f",dzSuccessMoney));
					list.add(map);
					flag = 2;
				}
				if (flag == 2) { //对账失败金额
					map.put("bk_chk", 2);
					String skTotalMoney = rytUpmpService.queryTotalMoneyOfDz(map);
					double totalSkMoney = 0d;
					if(StringUtils.isNotBlank(skTotalMoney) && !"null".equals(skTotalMoney)){
						totalSkMoney = Double.valueOf(skTotalMoney);
					}
					dzFailMoney = totalSkMoney / 100;
					logger.info("对账失败金额：" + String.format("%.2f",dzFailMoney));
					map.put("dzFailMoney",String.format("%.2f",dzFailMoney));
					list.add(map);
				}
			}
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(list);
			return jsonArray.toString();
		} catch (Exception e) {
			logger.error("对账结果查询金额统计异常：" + e.getMessage());
			return "error";
		}
	}
	
	/**
	 * 对账结果查询(金额统计)
	 * @param map
	 * @param model
	 */
	@RequestMapping(value = QUERY_DY_MONEY, method = RequestMethod.POST)
	@ResponseBody
	public String queryDYMoney(ServletRequest request, Model model){
		logger.info("对账明细查询  金额统计如下：");
		String original_data_tableName = null;
		String deduct_sys_id = null;
		int flag = 0;
		double noDzMoney = 0d;
		double dzSuccessMoney = 0d;
		double merFee = 0d;
		double dzFailMoney = 0d;
		double allMoney = 0d;
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String deduct_sys_stance = request.getParameter("deduct_sys_stance");
			String out_account = request.getParameter("out_account");
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");
			String deduct_mer_code = request.getParameter("deduct_mer_code");
			String additional_data = request.getParameter("additional_data");
			String req_mer_term_id = request.getParameter("req_mer_term_id");
			String whtherInnerJs = request.getParameter("whtherInnerJs");
			String bk_chk = request.getParameter("bk_chk");
			String req_mer_code = request.getParameter("req_mer_code");
			String whetherRiqie = request.getParameter("whetherRiqie");
			//参考号
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			String deduct_result = request.getParameter("deduct_result");
			
			String bank_type = null;
			// 获取原始交易表名
			String bankId = request.getParameter("bank_id");
			String[] bankInstList = bankId.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bank_type = bankInstList[1];
				original_data_tableName = bankInstList[2];
			} 
			
			// 获取渠道ID和渠道类型
			String tableName = request.getParameter("channel");
			if (StringUtils.isNotBlank(tableName)) {
				String[] str = tableName.split(",");
				if (str != null && str.length > 0) {
					deduct_sys_id = str[0];
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			if ("0".equals(bank_type)) {
				if (StringUtils.isNotBlank(startTime)) {
					map.put("startTime", DateUtil.getformatConversionStart(startTime));
				}
				if (StringUtils.isNotBlank(endTime)) {
					map.put("endTime", DateUtil.getformatConversionEnd(endTime));
				}
				if (StringUtils.isNotBlank(deduct_sys_stance)) {
					map.put("deduct_sys_stance", deduct_sys_stance);
				}
				if (StringUtils.isNotBlank(out_account)) {
					map.put("out_account", out_account);
				}
				if (StringUtils.isNotBlank(deduct_stlm_date)) {
					map.put("deduct_stlm_date", deduct_stlm_date);
				}
				if (StringUtils.isNotBlank(deduct_mer_code)) {
					map.put("deduct_mer_code", deduct_mer_code);
				}
				if (StringUtils.isNotBlank(deduct_sys_id)) {
					map.put("deduct_sys_id", deduct_sys_id);
				}
				if (StringUtils.isNotBlank(original_data_tableName)) {
					map.put("original_data_tableName", original_data_tableName);
				}
				if (StringUtils.isNotBlank(additional_data)) {
					map.put("additional_data", additional_data);
				}
				if (StringUtils.isNotBlank(req_mer_term_id)) {
					map.put("req_mer_term_id", req_mer_term_id);
				}
				if (StringUtils.isNotBlank(whtherInnerJs)) {
					map.put("whtherInnerJs", whtherInnerJs);
				}
				if (StringUtils.isNotBlank(req_mer_code)) {
					map.put("req_mer_code", req_mer_code);
				}
				if (StringUtils.isNotBlank(whetherRiqie)) {
					map.put("whetherRiqie", whetherRiqie);
				}
				if (StringUtils.isNotBlank(deduct_sys_reference)) {
					map.put("deduct_sys_reference", deduct_sys_reference);
				}
				if (StringUtils.isNotBlank(deduct_result)) {
					if ("2".equals(deduct_result)) {
						map.put("deduct_result", 0);
					}
					if ("3".equals(deduct_result)) {
						map.put("deduct_result", 2);
					}
					if ("6".equals(deduct_result)) {
						map.put("deduct_result", 1);
					}
				}
				if (StringUtils.isNotBlank(bk_chk)) {
					map.put("bk_chk", bk_chk);
					if ("1".equals(bk_chk)) {
						String  money = originalMoney.queryMoney("Original_Data.queryMoney", map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(money) && !"null".equals(money)){
							totalMoney = Double.valueOf(money);
						}
						dzSuccessMoney = totalMoney / 100;
						logger.info("对账成功金额：" + String.format("%.2f",dzSuccessMoney));
						map.put("allMoney",String.format("%.2f",dzSuccessMoney));
						
						String mer_fee = originalMoney.queryMoney("Original_Data.queryMerFee", map);
						if(StringUtils.isNotBlank(mer_fee) && !"null".equals(mer_fee)){
							merFee = Double.valueOf(mer_fee);
						}
						logger.info("商户手续费：" + String.format("%.2f", merFee));
						map.put("merFee", String.format("%.2f", merFee));
						list.add(map);
					} else {
						String chooseMoney = originalMoney.queryMoney("Original_Data.queryMoney", map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(chooseMoney) && !"null".equals(chooseMoney)){
							totalMoney = Double.valueOf(chooseMoney);
						}
						allMoney = totalMoney / 100;
						logger.info("您所选择对账状态下的对应金额是：" + String.format("%.2f",allMoney));
						map.put("allMoney",String.format("%.2f",allMoney));
						list.add(map);
					}
				} else {
					if (flag == 0) {
						map.put("bk_chk", 0);
						String money = originalMoney.queryMoney("Original_Data.queryMoney", map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(money) && !"null".equals(money)){
							totalMoney = Double.valueOf(money);
						}
						noDzMoney = totalMoney / 100;
						logger.info("未对账金额：" + String.format("%.2f",noDzMoney));
						map.put("noDzMoney",String.format("%.2f",noDzMoney));
						list.add(map);
						flag = 1;
					} 
					if (flag == 1) {
						map.put("bk_chk", 1);
						String  money = originalMoney.queryMoney("Original_Data.queryMoney", map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(money) && !"null".equals(money)){
							totalMoney = Double.valueOf(money);
						}
						dzSuccessMoney = totalMoney / 100;
						logger.info("对账成功金额：" + String.format("%.2f",dzSuccessMoney));
						map.put("dzSuccessMoney",String.format("%.2f",dzSuccessMoney));
						
						String mer_fee = originalMoney.queryMoney("Original_Data.queryMerFee", map);
						if(StringUtils.isNotBlank(mer_fee) && !"null".equals(mer_fee)){
							merFee = Double.valueOf(mer_fee);
						}
						logger.info("商户手续费：" + String.format("%.2f", merFee));
						map.put("merFee", String.format("%.2f", merFee));
						list.add(map);
						flag = 2;
					}
					if (flag == 2) {
						map.put("bk_chk", 2);
						String money = originalMoney.queryMoney("Original_Data.queryMoney", map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(money) && !"null".equals(money)){
							totalMoney = Double.valueOf(money);
						}
						dzFailMoney = totalMoney / 100;
						logger.info("对账失败金额：" + String.format("%.2f",dzFailMoney));
						map.put("dzFailMoney",String.format("%.2f",dzFailMoney));
						list.add(map);
					}
				}
				model.addAttribute("bk_chk", bk_chk);
			} else {
				if (StringUtils.isNotBlank(original_data_tableName)) {
					map.put("skTableName", original_data_tableName);
				}
				if (StringUtils.isNotBlank(deduct_sys_stance)) {
					map.put("tseq", deduct_sys_stance);
				}
				if (StringUtils.isNotBlank(additional_data)) {
					map.put("oid", additional_data);
				}
				if (StringUtils.isNotBlank(deduct_sys_id)) {
					map.put("deduct_sys_id", deduct_sys_id);
				}
				if (StringUtils.isNotBlank(out_account)) {
					map.put("card_no", out_account);
				}
				if (StringUtils.isNotBlank(req_mer_code)) {
					map.put("mid", req_mer_code);
				}
				if (StringUtils.isNotBlank(whetherRiqie)) {
					map.put("whetherRiqie", whetherRiqie);
				}
				if (StringUtils.isNotBlank(bk_chk)) {
					map.put("bk_chk", bk_chk);
				}
				if (StringUtils.isNotBlank(startTime)) {
					map.put("startDate", startTime.replace("-", ""));
				}
				if (StringUtils.isNotBlank(endTime)) {
					map.put("endDate", endTime.replace("-", ""));
				}
				if (StringUtils.isNotBlank(deduct_stlm_date)) {
					map.put("deductStlmDate", deduct_stlm_date.replace("-", ""));
				}
				if (StringUtils.isNotBlank(deduct_result)) {
					map.put("tstat", deduct_result);
				}
				if (StringUtils.isNotBlank(whtherInnerJs)) {
					map.put("whtherInnerJs", whtherInnerJs);
				}
				
				if (StringUtils.isNotBlank(bk_chk)) {
					map.put("bk_chk", bk_chk);
					if ("1".equals(bk_chk)) {
						String successMoney = rytUpmpService.queryTotalMoneyOfDz(map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(successMoney) && !"null".equals(successMoney)){
							totalMoney = Double.valueOf(successMoney);
						}
						dzSuccessMoney = totalMoney / 100;
						logger.info("对账成功金额：" + String.format("%.2f",dzSuccessMoney));
						map.put("allMoney",String.format("%.2f",dzSuccessMoney));
						
						String totalMerFee = originalMoney.queryMoney("RytUpmp.queryRtyTotalMerFee", map);
						if(StringUtils.isNotBlank(totalMerFee) && !"null".equals(totalMerFee)){
							merFee = Double.valueOf(totalMerFee);
						}
						logger.info("商户手续费：" + String.format("%.2f", merFee));
						map.put("merFee", String.format("%.2f", merFee));
						list.add(map);
					} else {
						String chooseMoney = rytUpmpService.queryTotalMoneyOfDz(map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(chooseMoney) && !"null".equals(chooseMoney)){
							totalMoney = Double.valueOf(chooseMoney);
						}
						allMoney = totalMoney / 100;
						logger.info("您所选择对账状态下的对应金额是：" + String.format("%.2f",allMoney));
						map.put("allMoney",String.format("%.2f",allMoney));
						list.add(map);
					}
					
				} else {
					if (flag == 0) {
						map.put("bk_chk", 0);
						String noMoney= rytUpmpService.queryTotalMoneyOfDz(map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(noMoney) && !"null".equals(noMoney)){
							totalMoney = Double.valueOf(noMoney);
						}
						noDzMoney = totalMoney / 100;
						logger.info("未对账金额：" + String.format("%.2f",noDzMoney));
						map.put("noDzMoney",String.format("%.2f",noDzMoney));
						list.add(map);
						flag = 1;
					} 
					if (flag == 1) {
						map.put("bk_chk", 1);
						String successMoney = rytUpmpService.queryTotalMoneyOfDz(map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(successMoney) && !"null".equals(successMoney)){
							totalMoney = Double.valueOf(successMoney);
						}
						dzSuccessMoney = totalMoney / 100;
						logger.info("对账成功金额：" + String.format("%.2f",dzSuccessMoney));
						map.put("dzSuccessMoney",String.format("%.2f",dzSuccessMoney));
						
						String totalMerFee = originalMoney.queryMoney("RytUpmp.queryRtyTotalMerFee", map);
						
						if (StringUtils.isNotBlank(totalMerFee) && !"null".equals(totalMerFee)) {
							merFee =  Double.valueOf(totalMerFee);
						}
						logger.info("商户手续费：" + String.format("%.2f", merFee));
						map.put("merFee", String.format("%.2f", merFee));
						
						list.add(map);
						flag = 2;
					}
					if (flag == 2) {
						map.put("bk_chk", 2);
						String failMoney = rytUpmpService.queryTotalMoneyOfDz(map);
						double totalMoney = 0d;
						if(StringUtils.isNotBlank(failMoney) && !"null".equals(failMoney)){
							totalMoney = Double.valueOf(failMoney);
						}
						dzFailMoney = totalMoney / 100;
						logger.info("对账失败金额：" + String.format("%.2f",dzFailMoney));
						map.put("dzFailMoney",String.format("%.2f",dzFailMoney));
						list.add(map);
					}
				}
				model.addAttribute("bk_chk", bk_chk);
			}
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(list);
			return jsonArray.toString();
		} catch (Exception e) {
			logger.error("对账明细查询金额统计异常：" + e.getMessage());
			return "error";
		}
	}
	
	/**
	 * 差错对账结果查询   差错对账笔数统计
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = QUERY_ERROR_DZ_RESULT)
	public void queryErrorDuizhangResult(ServletRequest request, ServletResponse response) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			String trade_date = request.getParameter("trade_date");
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");
			String tableName = request.getParameter("inst_name");
			String deduct_sys_id = null;
			int instType = -1;
			String error_original_data_tableName = null;
			String error_dz_data_tableName = null;
			Map<String, Object> map = new HashMap<String, Object>();
			String[] str = tableName.split(",");
			if (str != null && str.length > 0) {
				deduct_sys_id = str[0];
				instType = Integer.valueOf(str[3]);
				//根据渠道ID获取线上、线下标识
				if (StringUtils.isNotBlank(deduct_sys_id)) {
					InstInfo instInfo = dataManagerInit.getInstInfoById(Integer.valueOf(deduct_sys_id), instType);
					if (instInfo != null) {
						map.put("infoName", instInfo.getName());//获取渠道名称
					}
				}
				error_original_data_tableName =str[1]; //差错原始表名
				error_dz_data_tableName = str[2]; //差错对账文件表名
			}
			if (StringUtils.isNotBlank(trade_date)) {
				map.put("original_tradeTime", trade_date);
				map.put("dz_tradeTime", trade_date.replace("-", ""));
				map.put("flag", 1);  //标识为1：按交易日期统计结果
			}
			if (StringUtils.isNotBlank(deduct_stlm_date)) {
				map.put("original_tradeTime", deduct_stlm_date);
				map.put("dz_tradeTime", deduct_stlm_date.replace("-", ""));
				map.put("flag", 2);  //标识为2：按清算日期统计结果
			}
			if (StringUtils.isNotBlank(error_original_data_tableName)) {
				map.put("original_table_name", error_original_data_tableName);
			}
			if (StringUtils.isNotBlank(error_dz_data_tableName)) {
				map.put("duizhang_table_name", error_dz_data_tableName);
			}
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
			}
			if (instType == 0) {
				map = duizhangResultService.proceErrorDuizhangResult(map);
			} else {
				map = duizhangResultService.proceRytUpmpErrorResult(map);
			}
			StringBuffer sb = new StringBuffer();
			sb.append("[");
				sb.append("{");
					sb.append("original_tradeTime:'"+map.get("original_tradeTime")+"',");
					sb.append("infoName:'"+map.get("infoName")+"',");
					sb.append("original_trade_count:"+map.get("original_trade_count")+",");
					sb.append("qingkuan_count:"+map.get("qingkuan_count")+",");
					sb.append("daiji_count:"+map.get("daiji_count")+",");
					sb.append("tuihuo_count:"+map.get("tuihuo_count")+",");
					sb.append("again_qingkuan_count:"+map.get("again_qingkuan_count")+",");
					sb.append("twice_duidan_count:"+map.get("twice_duidan_count")+",");
					sb.append("exception_changkuan_count:"+map.get("exception_changkuan_count")+",");
					sb.append("error_channel_count:"+map.get("error_channel_count")+"");
				sb.append("}");
			sb.append("]");
			out.print(sb.toString());
		} catch (Exception e) {
			logger.error("差错对账结果统计出错：" + e.getMessage());
		}
	}
}
