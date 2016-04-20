package com.chinaebi.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.Login;
import com.chinaebi.entity.MerchantSettleStatistics;
import com.chinaebi.entity.Merchants;
import com.chinaebi.entity.RytRefundLog;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.MerchantSettleStatisticsService;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.service.RefundLogService;
import com.chinaebi.utils.CreateExcelUtil;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@SuppressWarnings("deprecation")
@Controller
public class RefundLogController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String REFUNDSELECT = "/refundManager/refundSelect";
	private static final String REFUNDHANDLE = "/refundManager/refundHandle";
	private static final String QUERYPAGEREFUNDDATA = "/queryPageRedundData.do";
	private static final String QUERYPAGEREFUNDHANDLEDATA = "/queryPageRefundHandleData.do";
	private static final String REFUNDSELECTDATADOWNLOAD = "/refundSelectDataDownLoad.do";
	private static final String REFUNDHANDLEDATADOWNLOAD = "/refundHandleDataDownLoad.do";
	private static final String QUERYREFUNDDATADETAIL = "/queryRefundDataDetail.do";
	private static final String CANNCELTK = "/canncelTk.do";
	private static final String HANDLETK = "/handleTk.do";
	
	// 联机退款(查询)
	private static final String QUERY_ONLINE_TK_DATA_LST = "queryOnlineTkDataLst.do";
	private static final String JSP_PAGE = "refundManager/onlineRefund";
	
	// 联机退款页面金额的统计
	private static final String QUERY_ONLINE_TK_TOTALMONEY = "queryOnlineTkTotalMoney.do";
	
	// 联机退款(下载)
	private static final String DOWN_EXCEL_ONLINE_TK_DATA = "downExcelOnlineTkData.do";
	
	// 退款审核(查询)
	private static final String QUERY_TK_CHECK_DATA_LST = "queryTkCheckDataLst.do";
	private static final String TK_CHECK_JSP = "refundManager/refundCheck";
	
	// 退款审核页面金额统计
	private static final String QUERY_TK_MONEY = "queryTkMoney.do";
	
	// 退款审核(下载)
	private static final String DOWN_EXCEL_TK_CHECK_DATA = "downExcelTkCheckData.do";
	
	// 联机退款(联机退款操作)
	public static final String UPDATE_REFUNDLOG_DATA_ONLINETK = "updateRefundLogDataToOnlineTk.do";
	
	// 联机退款(撤销退款操作)
	public static final String UPDATE_REFUNDLOG_DATA_CHEXIAO_BY_ID = "updateRefundLogDataToChexiaoById.do";
	
	// 联机退款(人工经办操作)
	public static final String UPDATE_REFUNDLOG_DATA_TO_RGJB = "updateRefundLogDataToRGJB.do";
	
	// 退款审核(退款审核操作)
	public static final String UPDATE_REFUNDLOG_DATA_TO_CHECKED_BY_ID = "updateRefundLogDataToCheckedById.do";
	
	@Autowired
	@Qualifier(value = "refundLogService")
	private RefundLogService refundLogService;
	
	@Autowired
	@Qualifier(value = "merchantSettleStatisticsService")
	private MerchantSettleStatisticsService merchantSettleStatisticsService;
	
	@Autowired
	@Qualifier(value = "merchantsService")
	private MerchantsService merchantsService;
	
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	/**
	 * 联机退款(查询)
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_ONLINE_TK_DATA_LST, method = RequestMethod.POST)
	public String queryOnlineTkDataLst(ServletRequest request, Model model) {
		logger.info("进入联机退款查询...");
		try {
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<RytRefundLog> page = new Page<RytRefundLog>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String dateType = request.getParameter("dateType");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String onlineRefundState = request.getParameter("onlineRefundState"); // 联机退款状态
			String merState = request.getParameter("merState");
			String merCode = request.getParameter("merCode");
			String onlineRefundId = request.getParameter("onlineRefundId");
			String tseq = request.getParameter("tseq");
			String gate = request.getParameter("gate");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(onlineRefundState)) {
				map.put("onlineRefundState", onlineRefundState);
			}
			if (StringUtils.isNotBlank(merState)) {
				map.put("merState", merState);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(onlineRefundId)) {
				map.put("onlineRefundId", onlineRefundId);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(gate)) {
				map.put("gate", gate);
			}
			model.addAttribute("pageLst", refundLogService.queryPageOnlineTkDataLst(page, map));
			model.addAttribute("dateType", dateType);
			model.addAttribute("onlineRefundState", onlineRefundState);
			model.addAttribute("merState", merState);
			model.addAttribute("gate", gate);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return JSP_PAGE;
	}
	
	/**
	 * 联机退款(下载)
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = DOWN_EXCEL_ONLINE_TK_DATA)
	public void downExcelOnlineTkData(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载联机退款数据...");
		try {
			String dateType = request.getParameter("dateType");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String onlineRefundState = request.getParameter("onlineRefundState"); // 联机退款状态
			String merState = request.getParameter("merState");
			String merCode = request.getParameter("merCode");
			String onlineRefundId = request.getParameter("onlineRefundId");
			String tseq = request.getParameter("tseq");
			String gate = request.getParameter("gate");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(onlineRefundState)) {
				map.put("onlineRefundState", onlineRefundState);
			}
			if (StringUtils.isNotBlank(merState)) {
				map.put("merState", merState);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(onlineRefundId)) {
				map.put("onlineRefundId", onlineRefundId);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(gate)) {
				map.put("gate", gate);
			}
			
			String[] header= new String[]{"联机退款表"};
			String[] headerTop = new String[] {
					"序号", "退款流水号", "原电银流水号", "商户号", "商户简称", "原商户订单号", "原扣款渠道", "原交易银行", 
					"申请退款金额", "退回商户手续费", "原交易日期", "退款确认日期", "联机退款状态", "退款失败原因", "申请退款原因"
			};
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    //在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("联机退款");
		    
		    sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 14));
		    
		    sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 5500);
            sheet.setColumnWidth(4, 6000);
            sheet.setColumnWidth(5, 5500);
            sheet.setColumnWidth(6, 6000);
            sheet.setColumnWidth(7, 6000);
            sheet.setColumnWidth(8, 6000);
            sheet.setColumnWidth(9, 4000);
            sheet.setColumnWidth(10, 4000);
            sheet.setColumnWidth(11, 4000);
            sheet.setColumnWidth(12, 4000);
            sheet.setColumnWidth(13, 4000);
            sheet.setColumnWidth(14, 5000);
            
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		 	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		 	cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 	
		 	// 设置表格底部边框
		    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		   // 设置表格左边边框
		    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		   // 设置表格右边边框
		    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		   // 设置表格顶部边框
		    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		   // 设置表格中间横线
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		   // 设置表格中间竖线
		    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		  
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook, sheet, header);
		    CreateExcelUtil.createTop(workbook,sheet,headerTop, cellStyle);

			//获取对账总表中的交易数据
			List<RytRefundLog> list = refundLogService.queryOnlineTkDataLstForExcel(map);
			if(list != null && list.size() > 0){
				String[] data = null;
			    int row = 2;
			    
			    String totalRefAmt = "0";
				String totalMerFee = "0";
			    for (RytRefundLog rytRefundLog : list) {
			    	totalRefAmt = PoundageCalculate.add(String.valueOf(rytRefundLog.getRef_amt()), totalRefAmt);
			    	totalMerFee = PoundageCalculate.add(String.valueOf(rytRefundLog.getMer_fee()), totalMerFee);
					data = new String[] {
							String.valueOf(row),
							String.valueOf(rytRefundLog.getId()),
							rytRefundLog.getTseq(),
							rytRefundLog.getMid(),
							rytRefundLog.getMer_abbreviation(),
							rytRefundLog.getOrg_oid(),
							rytRefundLog.getName(),
							rytRefundLog.getBankName(),
							String.format("%.2f", rytRefundLog.getRef_amt()),
							String.format("%.2f", rytRefundLog.getMer_fee()),
							String.valueOf(rytRefundLog.getSys_date()),
							String.valueOf(rytRefundLog.getReq_date()),
							rytRefundLog.getOnline_refund_state() == 0 ? "待处理" :  rytRefundLog.getOnline_refund_state() == 1 ? "退款中" : rytRefundLog.getOnline_refund_state() == 2 ? "联机退款成功" : "联机退款失败",
							rytRefundLog.getReason(),
							rytRefundLog.getRefund_reason()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			    //写入数据统计行
				data = new String[]{"总计:"+ (row - 2) +"条记录", "", "", "","", "", "", "", String.format("%.2f", Double.valueOf(totalRefAmt)) + "", String.format("%.2f", Double.valueOf(totalMerFee)) + "", "", "", "", "", ""};
				CreateExcelUtil.output(cellStyle,sheet,row,data);
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(date);
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"Refund_" + currentDate + ".xls");
			if(flag){
				logger.info("联机退款  Refund_" + currentDate + ".xls  文件创建成功");
			}else{
				logger.info("联机退款 Refund_" + currentDate + ".xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("下联机退款下载出现异常：" + e.getMessage());
		}
	}
	
	/**
	 * 退款审核(查询)
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_TK_CHECK_DATA_LST, method = RequestMethod.POST)
	public String queryTkCheckDataLst(ServletRequest request, Model model) {
		logger.info("进入退款审核查询...");
		try {
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<RytRefundLog> page = new Page<RytRefundLog>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String refundType = request.getParameter("refundType");
			String merCode = request.getParameter("merCode");
			String tseq = request.getParameter("tseq");
			String merState = request.getParameter("merState");
			String vState = request.getParameter("vState");
			String gate = request.getParameter("gate");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(refundType)) {
				map.put("refundType", refundType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(merState)) {
				map.put("merState", merState);
			}
			if (StringUtils.isNotBlank(vState)) {
				map.put("vState", vState);
			}
			if (StringUtils.isNotBlank(gate)) {
				map.put("gate", gate);
			}
			model.addAttribute("pageLst", refundLogService.queryPageTkCheckDataLst(page, map));
			model.addAttribute("refundType", refundType);
			model.addAttribute("merState", merState);
			model.addAttribute("vState", vState);
			model.addAttribute("gate", gate);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return TK_CHECK_JSP;
	}
	
	/**
	 * 退款审核(下载)
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = DOWN_EXCEL_TK_CHECK_DATA)
	public void downExcelTkCheckData(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载退款审核数据...");
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String refundType = request.getParameter("refundType");
			String merCode = request.getParameter("merCode");
			String tseq = request.getParameter("tseq");
			String merState = request.getParameter("merState");
			String vState = request.getParameter("vState");
			String gate = request.getParameter("gate");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(refundType)) {
				map.put("refundType", refundType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(merState)) {
				map.put("merState", merState);
			}
			if (StringUtils.isNotBlank(vState)) {
				map.put("vState", vState);
			}
			if (StringUtils.isNotBlank(gate)) {
				map.put("gate", gate);
			}
			
			String[] header= new String[]{"联机退款表"};
			String[] headerTop = new String[] {
					"序号", "退款流水号", "原电银流水号", "商户号", "原商户订单号", "原银行流水号", "原扣款渠道", "原交易银行", "原交易日期", "原订单金额(元)", 
					"原实际交易金额(元)", "原交易银行", "退款金额(元)", "退还商户手续费(元)", "优惠金额(元)", "审核日期"
			};
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		 	
			//在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("退款审核");
		    
		    sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 15));
		    
		    sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 4000);
            sheet.setColumnWidth(3, 5500);
            sheet.setColumnWidth(4, 4000);
            sheet.setColumnWidth(5, 5500);
            sheet.setColumnWidth(6, 5500);
            sheet.setColumnWidth(7, 4000);
            sheet.setColumnWidth(8, 4000);
            sheet.setColumnWidth(9, 4000);
            sheet.setColumnWidth(10, 4000);
            sheet.setColumnWidth(11, 4000);
            sheet.setColumnWidth(12, 4000);
            sheet.setColumnWidth(13, 4000);
            sheet.setColumnWidth(14, 4000);
            sheet.setColumnWidth(15, 4000);
		    
		    
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		 	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		 	cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		 	
		 	// 设置表格底部边框
		    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		   // 设置表格左边边框
		    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		   // 设置表格右边边框
		    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		   // 设置表格顶部边框
		    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		   // 设置表格中间横线
		    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		   // 设置表格中间竖线
		    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		  
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook, sheet, header);
		    CreateExcelUtil.createTop(workbook,sheet,headerTop, cellStyle);

			//获取对账总表中的交易数据
			List<RytRefundLog> list = refundLogService.queryTkCheckDataLstForExcel(map);
			if(list != null && list.size() > 0){
				String[] data = null;
			    int row = 2;
			    String totalRefAmt = "0";
			    String totalMerFee = "0";
			    for (RytRefundLog rytRefundLog : list) {
			    	totalRefAmt = PoundageCalculate.add(String.valueOf(rytRefundLog.getRef_amt()), totalRefAmt);
			    	totalMerFee = PoundageCalculate.add(String.valueOf(rytRefundLog.getMer_fee()), totalMerFee);
					data = new String[] {
							String.valueOf(row),
							String.valueOf(rytRefundLog.getId()),
							rytRefundLog.getTseq(),
							rytRefundLog.getMid(),
							rytRefundLog.getOrg_oid(),
							rytRefundLog.getTseq(),
							rytRefundLog.getBankName(),
							rytRefundLog.getName(),
							String.valueOf(rytRefundLog.getSys_date()),
							String.format("%.2f", rytRefundLog.getOrg_amt()),
							String.format("%.2f", rytRefundLog.getOrg_pay_amt()),
							rytRefundLog.getName(),
							String.format("%.2f", rytRefundLog.getRef_amt()),
							String.format("%.2f", rytRefundLog.getMer_fee()),
							String.format("%.2f", rytRefundLog.getPre_amt1()),
							rytRefundLog.getRef_date()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			    //写入数据统计行
				data = new String[]{"总计:"+ (row - 2) +"条记录", "", "", "","", "", "", "", "", "", "", "", String.format("%.2f", Double.valueOf(totalRefAmt)) + "", String.format("%.2f", Double.valueOf(totalMerFee)) + "", "", ""};
				CreateExcelUtil.output(cellStyle,sheet,row,data);
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(date);
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"RefundVerify_" + currentDate + ".xls");
			if(flag){
				logger.info("退款审核 RefundVerify_" + currentDate + ".xls  文件创建成功");
			}else{
				logger.info("退款审核 RefundVerify_" + currentDate + ".xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("退款审核下载Excel出现异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = QUERY_TK_MONEY)
	@ResponseBody
	public RytRefundLog queryTkMoney(ServletRequest request, Model model) {
		RytRefundLog rytRefundLog = null;
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String refundType = request.getParameter("refundType");
			String merCode = request.getParameter("merCode");
			String tseq = request.getParameter("tseq");
			String merState = request.getParameter("merState");
			String vState = request.getParameter("vState");
			String gate = request.getParameter("gate");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(refundType)) {
				map.put("refundType", refundType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(merState)) {
				map.put("merState", merState);
			}
			if (StringUtils.isNotBlank(vState)) {
				map.put("vState", vState);
			}
			if (StringUtils.isNotBlank(gate)) {
				map.put("gate", gate);
			}
			
			rytRefundLog = refundLogService.queryTkMoney(map);
			if (rytRefundLog != null) {
				rytRefundLog.setBatch(String.format("%.2f", rytRefundLog.getRef_amt()));
				rytRefundLog.setBgRetUrl(String.format("%.2f", rytRefundLog.getMer_fee()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rytRefundLog;
	}
	
	@RequestMapping(value=QUERYPAGEREFUNDDATA,method=RequestMethod.POST)
	public String queryPageRefundData(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		String startTime = request.getParameter("startTime");//交易起始日期
		String endTime = request.getParameter("endTime");//交易截止日期
		String tk_date = request.getParameter("tk_date");//日期查询条件
		String gate = request.getParameter("gate");//原交易渠道
		String stat = request.getParameter("stat");//退款状态
		String mid = request.getParameter("mid");//商户号
		String tseq = request.getParameter("tseq");//原交易流水号
		String mer_status = request.getParameter("mer_status");//商户状态
		String refund_type = request.getParameter("refund_type");//退款类型
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Page<RytRefundLog> page = new Page<RytRefundLog>();
		if (StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if (StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else
			page.setPageSize(10);
		
		if (StringUtils.isNotBlank(tk_date)) {
			map.put("tk_date", tk_date);
		}
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(gate)) {
			map.put("gate", gate);
		}
		if (StringUtils.isNotBlank(stat)) {
			map.put("stat", stat);
		}
		if (StringUtils.isNotBlank(mid)) {
			map.put("mid", mid);
		}
		if (StringUtils.isNotBlank(tseq)) {
			map.put("tseq", tseq);
		}
		if (StringUtils.isNotBlank(mer_status)) {
			map.put("mer_status", mer_status);
		}
		if (StringUtils.isNotBlank(refund_type)) {
			map.put("refund_type", refund_type);
		}
		
		Page<RytRefundLog> pageList = refundLogService.queryPageRefundData(page, map);
		
		model.addAttribute("getDataResult", pageList);
		
		String flag = request.getParameter("flag");
		
		if(StringUtils.isNotBlank(flag) && "2".equals(flag)){//分页查询
			String applyTkTotalAmount = request.getParameter("applyTkTotalAmount");
			String refundMerFeeTotalAmount = request.getParameter("refundMerFeeTotalAmount");
			model.addAttribute("applyTkTotalAmount", StringUtils.isBlank(applyTkTotalAmount)?0.00:applyTkTotalAmount);
	        model.addAttribute("refundMerFeeTotalAmount", StringUtils.isBlank(refundMerFeeTotalAmount)?0.00:refundMerFeeTotalAmount);
		}else{
			RytRefundLog refundLog = refundLogService.queryPageRefundAmount(map);
	        model.addAttribute("applyTkTotalAmount", refundLog==null?0.00:refundLog.getRef_amt());
	        model.addAttribute("refundMerFeeTotalAmount", refundLog==null?0.00:refundLog.getMer_fee());
		}
		
		model.addAttribute("tk_date", tk_date);
		model.addAttribute("gate", gate);
		model.addAttribute("stat", stat);
		model.addAttribute("mer_status", mer_status);
		model.addAttribute("refund_type", refund_type);
		model.addAttribute("pageSize", pageSize);
		
		return REFUNDSELECT;
	}
	
	@RequestMapping(value=QUERYREFUNDDATADETAIL,method=RequestMethod.POST)
	@ResponseBody
	public RytRefundLog queryRefundDataDetail(ServletRequest request){
		String id = request.getParameter("id");
		return refundLogService.queryRedundDataDetail(id);
	}
	
	@RequestMapping(value=QUERYPAGEREFUNDHANDLEDATA,method=RequestMethod.POST)
	public String queryPageRefundHandleData(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		String startTime = request.getParameter("startTime");//交易起始日期
		String endTime = request.getParameter("endTime");//交易截止日期
		String tk_date = request.getParameter("tk_date");//日期查询条件
		String stat = request.getParameter("stat");//退款状态
		String mid = request.getParameter("mid");//商户号
		String tseq = request.getParameter("tseq");//原交易流水号
		String mer_status = request.getParameter("mer_status");//商户状态
		String id = request.getParameter("tk_stance");//退款类型
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Page<RytRefundLog> page = new Page<RytRefundLog>();
		if (StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if (StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else
			page.setPageSize(10);
		
		if (StringUtils.isNotBlank(tk_date)) {
			map.put("tk_date", tk_date);
		}
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime",endTime.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(stat)) {
			map.put("stat", stat);
		}
		if (StringUtils.isNotBlank(mid)) {
			map.put("mid", mid);
		}
		if (StringUtils.isNotBlank(tseq)) {
			map.put("tseq", tseq);
		}
		if (StringUtils.isNotBlank(mer_status)) {
			map.put("mer_status", mer_status);
		}
		if (StringUtils.isNotBlank(id)) {
			map.put("id", id);
		}
		
		Page<RytRefundLog> pageList = refundLogService.queryPageRedundHandleData(page, map);
		
		model.addAttribute("getDataResult", pageList);
		
		String flag = request.getParameter("flag");
		
		if(StringUtils.isNotBlank(flag) && "2".equals(flag)){//分页查询
			String applyTkTotalAmount = request.getParameter("applyTkTotalAmount");
			String refundMerFeeTotalAmount = request.getParameter("refundMerFeeTotalAmount");
			model.addAttribute("applyTkTotalAmount", StringUtils.isBlank(applyTkTotalAmount)?0.00:applyTkTotalAmount);
	        model.addAttribute("refundMerFeeTotalAmount", StringUtils.isBlank(refundMerFeeTotalAmount)?0.00:refundMerFeeTotalAmount);
		}else{
			RytRefundLog refundLog = refundLogService.queryPageRedundHandleAmount(map);
	        model.addAttribute("applyTkTotalAmount", refundLog==null?0.00:refundLog.getRef_amt());
	        model.addAttribute("refundMerFeeTotalAmount", refundLog==null?0.00:refundLog.getMer_fee());
		}
		
		model.addAttribute("tk_date", tk_date);
		model.addAttribute("stat", stat);
		model.addAttribute("mer_status", mer_status);
		model.addAttribute("pageSize", pageSize);
		return REFUNDHANDLE;
	}
	
	@RequestMapping(value=REFUNDSELECTDATADOWNLOAD)
	public void refundSelectDataDownLoad(ServletRequest request,HttpServletResponse response){
		try{
			String startTime = request.getParameter("startTime");//交易起始日期
			String endTime = request.getParameter("endTime");//交易截止日期
			String tk_date = request.getParameter("tk_date");//日期查询条件
			String gate = request.getParameter("gate");//原交易渠道
			String stat = request.getParameter("stat");//退款状态
			String mid = request.getParameter("mid");//商户号
			String tseq = request.getParameter("tseq");//原交易流水号
			String mer_status = request.getParameter("mer_status");//商户状态
			String refund_type = request.getParameter("refund_type");//退款类型
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			if (StringUtils.isNotBlank(tk_date)) {
				map.put("tk_date", tk_date);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime",endTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(gate)) {
				map.put("gate", gate);
			}
			if (StringUtils.isNotBlank(stat)) {
				map.put("stat", stat);
			}
			if (StringUtils.isNotBlank(mid)) {
				map.put("mid", mid);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(mer_status)) {
				map.put("mer_status", mer_status);
			}
			if (StringUtils.isNotBlank(refund_type)) {
				map.put("refund_type", refund_type);
			}
			
			List<RytRefundLog> dataList = refundLogService.queryRedundDataList(map);
			
			//Excel表头
			String[] header = {"序号", "退款流水号", "原电银流水号", "商户号", "原商户订单号", "原银行流水号", "原交易日期", "原订单金额(元)", "原实际交易金额(元)", "原扣款渠道", "原交易银行", "退款金额(元)", "退还商户手续费(元)", "经办日期","退款确认日期","退款处理类型","退款状态","订单优惠金额(元)"};
			Calendar calendar = Calendar.getInstance();//系统当前时间
			SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMddHHmmss");  
			String newdate=sdformat.format(calendar.getTime());
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet(newdate);
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    String totalRefundAmount = "0.00";
		    double totalMerFee = 0.00;
		    int row = 1;
			if(dataList != null && dataList.size() > 0){
				String[] data = null;
				String refundType = "";
				String stat_ch = "";
			    for(RytRefundLog refundData:dataList){
			    	
			    	if(refundData.getRefund_type() == 0){
			    		refundType = "人工经办";
			    	}else if(refundData.getRefund_type() == 1){
			    		refundType = "联机退款";
			    	}else if(refundData.getRefund_type() == 2){
			    		refundType = "联机退款-人工经办";
			    	}else{
			    		refundType = "未知";
			    	}
			    	
			    	if(refundData.getStat() == 7){
			    		stat_ch = "商户撤销退款";
			    	}else if(refundData.getStat() == 1){
			    		stat_ch = "商户确认退款";
			    	}else if(refundData.getStat() == 2){
			    		stat_ch = "退款成功";
			    	}else if(refundData.getStat() == 3){
			    		stat_ch = "操作成功";
			    	}else if(refundData.getStat() == 4){
			    		stat_ch = "操作失败";
			    	}else if(refundData.getStat() == 5){
			    		stat_ch = "商户申请退款";
			    	}else if(refundData.getStat() == 6){
			    		stat_ch = "退款失败";
			    	}else{
			    		stat_ch = "未知";
			    	}
			    	
			    	
					data = new String[] {
						row+"",
						refundData.getId()+"",
						refundData.getTseq(),
						refundData.getMid(),
						refundData.getOrg_oid(),
						refundData.getOrg_bk_seq(),
						refundData.getSys_date()+"",
						String.format("%.2f", refundData.getOrg_amt()),
						String.format("%.2f", refundData.getOrg_pay_amt()),
						refundData.getName(),
						refundData.getBankName(),
						String.format("%.2f", refundData.getRef_amt()),
						String.format("%.2f", refundData.getMer_fee()),
						refundData.getPro_date()+"",
						refundData.getReq_date()+"",
						refundType,
						stat_ch,
						refundData.getPre_amt1()+""
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
					totalRefundAmount = PoundageCalculate.add(refundData.getRef_amt(),totalRefundAmount).toString();
					totalMerFee = PoundageCalculate.add(refundData.getMer_fee(),totalMerFee).doubleValue();
				}
			}else{
				logger.info("查询退款数据为空");
			}
			
			//写入数据统计行
			String[] data = new String[]{"总计:"+(row-1)+"条记录","","","","", "","","","","","",totalRefundAmount,totalMerFee+"","","","","",""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,("MERREFUNDQUERY_"+newdate+".xls"));
			if(flag){
				logger.info("MERREFUNDQUERY_"+newdate+".xls  文件创建成功");
			}else{
				logger.info("MERREFUNDQUERY_"+newdate+".xls  文件创建失败");
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping(value=REFUNDHANDLEDATADOWNLOAD)
	public void refundHandleDataDownLoad(ServletRequest request, HttpServletResponse response,HttpSession session){
		try{
			String startTime = request.getParameter("startTime");//交易起始日期
			String endTime = request.getParameter("endTime");//交易截止日期
			String tk_date = request.getParameter("tk_date");//日期查询条件
			String stat = request.getParameter("stat");//退款状态
			String mid = request.getParameter("mid");//商户号
			String tseq = request.getParameter("tseq");//原交易流水号
			String mer_status = request.getParameter("mer_status");//商户状态
			String id = request.getParameter("tk_stance");//退款类型
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			if (StringUtils.isNotBlank(tk_date)) {
				map.put("tk_date", tk_date);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime",endTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(stat)) {
				map.put("stat", stat);
			}
			if (StringUtils.isNotBlank(mid)) {
				map.put("mid", mid);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(mer_status)) {
				map.put("mer_status", mer_status);
			}
			if (StringUtils.isNotBlank(id)) {
				map.put("id", id);
			}
			
			List<RytRefundLog> dataList = refundLogService.queryRedundHandleDataList(map);
			
			//Excel表头
			String[] header = {"序号", "退款流水号", "原电银流水号", "商户号", "原商户订单号", "原银行流水号","原扣款渠道", "原交易银行", "原交易日期", "原订单金额(元)", "原实际交易金额(元)", "退款金额(元)", "退还商户手续费(元)","订单优惠金额(元)", "经办日期","退款人签字栏"};
			Calendar calendar = Calendar.getInstance();//系统当前时间
			SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMddHHmmss");  
			String newdate=sdformat.format(calendar.getTime());
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet(newdate);
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    String totalRefundAmount = "0.00";
		    String totalOrderAmount = "0.00";
		    int row = 1;
			if(dataList != null && dataList.size() > 0){
				String[] data = null;
			    for(RytRefundLog refundData:dataList){
					data = new String[] {
						row+"",
						refundData.getId()+"",
						refundData.getTseq(),
						refundData.getMid(),
						refundData.getOid(),
						refundData.getOrg_bk_seq(),
						refundData.getName(),
						refundData.getBankName(),
						refundData.getSys_date()+"",
						String.format("%.2f", refundData.getOrg_amt()),
						String.format("%.2f", refundData.getOrg_pay_amt()),
						String.format("%.2f", refundData.getRef_amt()),
						String.format("%.2f", refundData.getMer_fee()),
						refundData.getPre_amt1()+"",
						refundData.getPro_date()+"",
						""
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
					totalRefundAmount = PoundageCalculate.add(refundData.getRef_amt(),totalRefundAmount).toString();
					totalOrderAmount = PoundageCalculate.add(refundData.getOrg_pay_amt(),totalOrderAmount).toString();
				}
			}else{
				logger.info("查询退款数据为空");
			}
			
			Login login = (Login) session.getAttribute("login");
			
			//写入数据统计行
			String[] data = new String[]{"总计:",(row-1)+"条记录","","","", "","","","","",totalOrderAmount,totalRefundAmount,"","","",""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			//写入其他信息
			String[] data1 = new String[]{"","制表人:",login==null?"":login.getChineseName(),"制表日期:",newdate.substring(0, 8), "复核人:","","日期:","","","","","","","",""};
			CreateExcelUtil.output(cellStyle,sheet,row+1,data1);
			String[] data2 = new String[]{"","退款录入:","","日期:","", "退款复核:","","日期:","","","","","","","",""};
			CreateExcelUtil.output(cellStyle,sheet,row+2,data2);
			String[] data3 = new String[]{"","","","","", "结算主管:","","日期:","","","","","","","",""};
			CreateExcelUtil.output(cellStyle,sheet,row+3,data3);
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,("REFUNDLOG_"+newdate+".xls"));
			if(flag){
				logger.info("REFUNDLOG_"+newdate+".xls  文件创建成功");
			}else{
				logger.info("REFUNDLOG_"+newdate+".xls  文件创建失败");
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping(value=CANNCELTK,method=RequestMethod.POST)
	@ResponseBody
	public String canncelTk(ServletRequest request){
		String idList = request.getParameter("idList");//主键集合
		String etro_reason = request.getParameter("etro_reason");//撤销退款原因
		if(StringUtils.isBlank(idList) && StringUtils.isBlank(etro_reason)){
			return "0";
		}else{
			String[] str = idList.split(",");
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			for (int i = 0; i < str.length; i++) {
				sb.append(str[i]+",");
			}
			
			String ids = sb.substring(0, sb.length()-1).toString() + ")";
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("etro_reason", etro_reason);
			
			return refundLogService.updateRefundLogData(2,map);
		}
	}
	
	@RequestMapping(value=HANDLETK,method=RequestMethod.POST)
	@ResponseBody
	public synchronized String handleTk(ServletRequest request){
		String result = "";
		String idList = request.getParameter("idList");//主键集合
		
		if(StringUtils.isNotBlank(idList)){
			String[] str = idList.split(",");
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			for (int i = 0; i < str.length; i++) {
				sb.append(str[i]+",");
			}
			
			String ids = sb.substring(0, sb.length()-1).toString() + ")";
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("ids", ids);
			
			result = refundLogService.updateRefundLogData(1, map);
			
		}
		
		
		return result;
	}
	
	/**
	 * 联机退款(联机退款操作)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UPDATE_REFUNDLOG_DATA_ONLINETK, method = RequestMethod.POST)
	@ResponseBody
	public int updateRefundLogDataToOnlineTk(ServletRequest request) {
		logger.info("正在进行联机退款操作...");
		int effectNum = 0;
		try {
			String idList = request.getParameter("idList");//主键集合
			if (StringUtils.isNotBlank(idList)) {
				String[] str = idList.split(",");
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				for (int i = 0; i < str.length; i++) {
					sb.append(str[i]+",");
				}
				String ids = sb.substring(0, sb.length()-1).toString() + ")";
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", ids);
				
				List<RytRefundLog> list = refundLogService.queryRefundLogDataInfoById(ids);
				if (list != null && list.size() > 0) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					Calendar calendar = Calendar.getInstance();
					int pro_date = Integer.valueOf(DateUtil.formatDate(calendar.getTime(), "yyyyMMdd"));
					for (RytRefundLog rytRefundLog : list) {
						if (rytRefundLog.getOnline_refund_state() == 0) {
							paramMap.put("id", rytRefundLog.getId());
							paramMap.put("pro_date", pro_date);
							paramMap.put("ref_date", pro_date);
							effectNum = refundLogService.updateRefundLogDataToOnlineTk(paramMap);
							if (effectNum > 0) {
								logger.info("联机退款操作成功...");
							}
						} else{
							effectNum = -1;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}
	
	/**
	 * 联机退款(撤销退款操作)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UPDATE_REFUNDLOG_DATA_CHEXIAO_BY_ID, method = RequestMethod.POST)
	@ResponseBody
	public int updateRefundLogDataToChexiaoById(ServletRequest request) {
		logger.info("正在进行撤销退款操作...");
		int effectNum = 0;
		try {
			String idList = request.getParameter("idList");//主键集合
			if (StringUtils.isNotBlank(idList)) {
				String[] str = idList.split(",");
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				for (int i = 0; i < str.length; i++) {
					sb.append(str[i]+",");
				}
				String ids = sb.substring(0, sb.length()-1).toString() + ")";
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", ids);
				
				List<RytRefundLog> list = refundLogService.queryRefundLogDataInfoById(ids);
				if (list != null && list.size() > 0) {
					for (RytRefundLog rytRefundLog : list) {
						if (rytRefundLog.getOnline_refund_state() == 1) {
							effectNum = refundLogService.updateRefundLogDataToChexiaoById(map);
							if (effectNum > 0) {
								logger.info("撤销退款操作成功...");
							}
						} else if (rytRefundLog.getOnline_refund_state() == 3 || rytRefundLog.getOnline_refund_state() == 4) {
							effectNum = -1;
						}
					}
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}
	
	/**
	 * (联机退款)人工经办操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UPDATE_REFUNDLOG_DATA_TO_RGJB,method=RequestMethod.POST)
	@ResponseBody
	public synchronized String updateRefundLogDataToRGJB(ServletRequest request){
		StringBuffer result = new StringBuffer("");
		String idList = request.getParameter("idList");//主键集合
		
		int totalCount = 0;
		int sucessCount = 0;
		int failCount = 0;
		StringBuffer balanceNotEnough = new StringBuffer("");
		StringBuffer interfaceFail = new StringBuffer("");
		StringBuffer outOfDate = new StringBuffer("");
		StringBuffer otherInfo = new StringBuffer("");
		
		if(StringUtils.isNotBlank(idList)){
			String[] str = idList.split(",");
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			for (int i = 0; i < str.length; i++) {
				sb.append(str[i]+",");
			}
			
			String ids = sb.substring(0, sb.length()-1).toString() + ")";
			List<RytRefundLog> list = refundLogService.queryRefundLogDataInfoById(ids);
			
			if(list != null && list.size() > 0){
				totalCount = list.size();
				for (RytRefundLog rytRefundLog : list) {
					try {
						int effectNum = refundLogService.updateRefundLogDataToRGJB(rytRefundLog);
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
					}catch(Exception e){
						failCount++;
						logger.error(e.getMessage());
					}
				}
			}
		}
		
		result.append(totalCount+"");
		result.append(";");
		result.append(sucessCount+"");
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
		
		return result.toString();
	}
	
	/**
	 * 退款审核(退款审核操作)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UPDATE_REFUNDLOG_DATA_TO_CHECKED_BY_ID, method = RequestMethod.POST)
	@ResponseBody
	public int updateRefundLogDataToCheckedById(ServletRequest request) {
		logger.info("正在进行退款审核完成操作...");
		int effectNum = 0;
		try {
			String idList = request.getParameter("idList");//主键集合
			if (StringUtils.isNotBlank(idList)) {
				String[] str = idList.split(",");
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				for (int i = 0; i < str.length; i++) {
					sb.append(str[i]+",");
				}
				String ids = sb.substring(0, sb.length()-1).toString() + ")";
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", ids);
				String refDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
				map.put("ref_date", refDate);
				effectNum = refundLogService.updateRefundLogDataToCheckedById(map);
				if (effectNum > 0) {
					logger.info("审核完成操作成功，开始将审核后的退款数据保存至T+1表中...");
					List<RytRefundLog> list = refundLogService.queryDataById(map);
					if (list != null && list.size() > 0) {
						for (RytRefundLog rytRefundLog : list) {
							Merchants merchants = merchantsService.queryMerchantBasicInfoByMerCode(rytRefundLog.getMid());
							if(merchants != null){
								map.put("inst_id", rytRefundLog.getGid());
								map.put("inst_type", 1);
								map.put("mer_code", rytRefundLog.getMid());
								map.put("data_status", DataStatus.REFUND);
								map.put("deduct_stlm_date", rytRefundLog.getRef_date());
								MerchantSettleStatistics merchantSettleStatistics = merchantSettleStatisticsService.queryMerchantSettleStatisticsInfo(map);
								
								if (merchantSettleStatistics != null) {
										merchantSettleStatistics.setRefund_amount(PoundageCalculate.sub(merchantSettleStatistics.getRefund_amount(), rytRefundLog.getRef_amt()).toString());
										merchantSettleStatistics.setRefund_count(merchantSettleStatistics.getRefund_count() + 1);
										merchantSettleStatistics.setMer_refund_fee(PoundageCalculate.sub(merchantSettleStatistics.getMer_refund_fee(),rytRefundLog.getMer_fee()).toString());
										merchantSettleStatistics.setMer_refund_fee("0.00");
										merchantSettleStatistics.setSystem_refund_fee("0.00");
										merchantSettleStatistics.setRefund_zf_fee("0.00");
									merchantSettleStatisticsService.updateMerchantSettleStatisticsInfo(merchantSettleStatistics);
								} else {
									merchantSettleStatistics = new MerchantSettleStatistics();
									merchantSettleStatistics.setDeduct_stlm_date(Integer.valueOf(refDate));
									merchantSettleStatistics.setJs_date(Integer.valueOf(refDate));
									merchantSettleStatistics.setMer_code(rytRefundLog.getMid());
									merchantSettleStatistics.setInst_id(rytRefundLog.getGid());
									merchantSettleStatistics.setInst_type(1);
									merchantSettleStatistics.setMer_type(merchants.getMer_category());
									merchantSettleStatistics.setSettle_amount("0.00");
									merchantSettleStatistics.setData_status(DataStatus.REFUND);
									merchantSettleStatistics.setRefund_amount(PoundageCalculate.sub(0, rytRefundLog.getRef_amt()).toString());
									merchantSettleStatistics.setRefund_count(1);
									merchantSettleStatistics.setMer_refund_fee(PoundageCalculate.sub(0, rytRefundLog.getMer_fee()).toString());
									merchantSettleStatistics.setSystem_refund_fee("0.00");
									merchantSettleStatistics.setRefund_zf_fee("0.00");
									merchantSettleStatistics.setTrade_amount("0.00");
									merchantSettleStatistics.setTrade_count(0);
									merchantSettleStatistics.setMer_fee("0.00");
									merchantSettleStatistics.setSystem_fee("0.00");
									merchantSettleStatistics.setZf_fee("0.00");
									merchantSettleStatistics.setBank_id(dataManagerInit.getInstInfoById(rytRefundLog.getGid(), 1).getBank_id());
									merchantSettleStatisticsService.addMerchantSettleStatisticsInfo(merchantSettleStatistics);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}
	
	@RequestMapping(value = QUERY_ONLINE_TK_TOTALMONEY)
	@ResponseBody
	public RytRefundLog queryOnlineTkTotalMoney(ServletRequest request, Model model) {
		RytRefundLog rytRefundLog = null;
		try {
			String dateType = request.getParameter("dateType");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String onlineRefundState = request.getParameter("onlineRefundState"); // 联机退款状态
			String merState = request.getParameter("merState");
			String merCode = request.getParameter("merCode");
			String onlineRefundId = request.getParameter("onlineRefundId");
			String tseq = request.getParameter("tseq");
			String gate = request.getParameter("gate");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(onlineRefundState)) {
				map.put("onlineRefundState", onlineRefundState);
			}
			if (StringUtils.isNotBlank(merState)) {
				map.put("merState", merState);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(onlineRefundId)) {
				map.put("onlineRefundId", onlineRefundId);
			}
			if (StringUtils.isNotBlank(tseq)) {
				map.put("tseq", tseq);
			}
			if (StringUtils.isNotBlank(gate)) {
				map.put("gate", gate);
			}
			
			rytRefundLog = refundLogService.queryOnlineTkTotalMoney(map);
			if (rytRefundLog != null) {
				rytRefundLog.setBatch(String.format("%.2f", rytRefundLog.getRef_amt()));
				rytRefundLog.setBgRetUrl(String.format("%.2f", rytRefundLog.getMer_fee()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return rytRefundLog;
	}
}
