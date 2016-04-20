package com.chinaebi.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinaebi.dao.ICommonDao;
import com.chinaebi.entity.BankDealWith;
import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.DuizhangErrorData;
import com.chinaebi.entity.ErrorDataLst;
import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.entity.OriginalData;
import com.chinaebi.entity.RytUpmp;
import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.entity.YlCupsErrorEntry;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.BankDealWithService;
import com.chinaebi.service.DuizhangResultService;
import com.chinaebi.service.ErrorDataLstService;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.service.MerchantAccountingService;
import com.chinaebi.service.PpfwDataService;
import com.chinaebi.service.RytUpmpService;
import com.chinaebi.utils.CreateExcelUtil;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.FlightUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.ReflectUtils;
import com.chinaebi.utils.Ryt_trade_type;
import com.chinaebi.utils.StringPingJie;
import com.chinaebi.utils.StringUtils;

/**
 * 数据下载控制层
 */
@Controller
public class DownExcelController {
	//记录日志
	private Logger logger = LoggerFactory.getLogger(getClass());

	//电银差错对账明细查询 数据下载
	private static final String ERROR_DUIZHANG_DETAIL_EXCEL = "errorDuizhangDetailDownExcel.do";
	
	//银联差错对账明细查询  数据下载
	private static final String DUIZHANG_ERROR_CUPS_LST_EXCEL = "duizhangErrorCupsLstDownExcel.do";
	
	//内部差错调整 数据下载
	private static final String INNER_ERROR_ADJUST_EXCEL = "innerErrorAdjustDownExcel.do";
	
	//银联查询查询 数据下载
	private static final String CUPS_ERROR_DOWN_EXCEL = "cupsErrorDownExcel.do";
	
	//已处理差错查询  下载Excel表格
	private static final String OPTION_ERROR_DOWN_EXCEL = "optionErrorDownExcel.do";
	
	//应收银行交易款
	public static final String BANKDEALWITH_EXCEL="bankdealwithExcel.do";
	
	//商户账务查询
	public static final String MERCHANTACCOUNTING_EXCEL="merchantAccountingExcel.do";
	
	//对账明细查询(下载Excel)
	private static final String DZ_DETAIL_DOWN_EXCEL = "dzDetailDownExcel.do";
	
	/*
	 * 下载银行对账可疑数据
	 */
	private static final String DOWNLOADBANKERRORDATAOFEXCEL = "downLoadBankErrorDataOfExcel.do";
	
	@Autowired
	@Qualifier(value = "merchantAccountingService")
	private MerchantAccountingService merchantAccountingService;
	
	@Autowired
	@Qualifier(value = "bankDealWithService")
	private BankDealWithService bankDealWithService;

	@Autowired
	@Qualifier(value = "errorDataLstService")
	private ErrorDataLstService errorDataLstService;
	
	@Autowired
	@Qualifier(value = "dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "commonClass")
	private CommonClass commonClass;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<YlCupsErrorEntry> errorOriginalDataDao;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<DuizhangErrorData> errorDzDataDao;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<YlCupsErrorEntry> ylCupsErrorEntryDao;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<ErrorDataLst> errorDataLstDao;
	
	@Autowired
	@Qualifier(value = "duizhangResultService")
	private DuizhangResultService duizhangResultService;
	
	@Autowired
	@Qualifier(value="ppfwDataService")
	private PpfwDataService ppfwDataService;
	
	@Autowired
	@Qualifier(value = "rytUpmpService")
	private RytUpmpService rytUpmpService;
	
	@Autowired
	@Qualifier(value = "instInfoService")
	private InstInfoService instInfoService;
	
	/**
	 * 差错对账交易明细查询 银联差错数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = ERROR_DUIZHANG_DETAIL_EXCEL)
	public void downLoadFileXls(HttpServletRequest request, HttpServletResponse response) {
		try {
			//Excel表格头部
			String[] header = new String[] {"流水号", "交易时间", "交易金额 ", "转出卡号",
				"参考号", "交易类型", "来源", "对账状态"
			};
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String reqSysStance = request.getParameter("reqSysStance");
			String bk_chk = request.getParameter("bk_chk");
			String error_original_data_tableName = request.getParameter("inst_name");
		
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
			List<YlCupsErrorEntry> list = errorOriginalDataDao.queryForList("YlCupsErrorEntry.queryDataDownExcel", map);
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("差错对账交易明细列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
			
			if (list != null && list.size() > 0) {
				String[] data = null;
			    int row = 1;
			    for (YlCupsErrorEntry errorOriginalData : list) {
					int handlingId = errorOriginalData.getHandling_id();
					errorOriginalData.setHandling_name(commonClass.getHandlingNameById(handlingId));
					data = new String[] {
						errorOriginalData.getReqSysStance(),
						errorOriginalData.getTrade_time(),
						String.format("%.2f", Double.valueOf(errorOriginalData.getTradeAmount())),
						errorOriginalData.getOut_account(),
						errorOriginalData.getDeduct_sys_reference(),
						errorOriginalData.getHandling_name(),
						errorOriginalData.getTrade_source(),
						errorOriginalData.getBk_chk() == 0 ? "未对账" : errorOriginalData.getBk_chk() == 1 ?
								"对账成功" : "对账失败"
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"差错对账交易明细列表.xls");
			if(flag){
				logger.info("差错对账交易明细列表.xls  文件创建成功");
			}else{
				logger.info("差错对账交易明细列表.xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 银联差错对账明细查询 数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = DUIZHANG_ERROR_CUPS_LST_EXCEL)
	public void downExcelDuizhangErrorCupsLst(HttpServletRequest request, HttpServletResponse response) {
		try {
			//Excel表格头部
			String[] header = new String[] {"流水号", "交易时间", "交易金额 ", "转出卡号",
				"参考号", "交易类型", "对账状态"
			};
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String reqSysStance = request.getParameter("reqSysStance");
			String error_dz_data_tableName = request.getParameter("inst_name");
			String bk_chk = request.getParameter("bk_chk");
			String deductSysReference = request.getParameter("deductSysReference");
			
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
			List<DuizhangErrorData> pageDuizhangErrorCupsLst = errorDzDataDao.queryForList("Duizhang_ErrorData.queryDataForDownExcel", map);
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("银联差错对账文件数据列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
			
			if (pageDuizhangErrorCupsLst != null && pageDuizhangErrorCupsLst.size() > 0) {
				StringBuffer sb = new StringBuffer();
				String[] data = null;
			    int row = 1;
				for (DuizhangErrorData duizhangErrorCupsLst : pageDuizhangErrorCupsLst) {
					//处理时间
					String tradeTime = duizhangErrorCupsLst.getReqTime();
					sb.setLength(0);
					sb.append(tradeTime.substring(0, 4)).append("-").append(tradeTime.substring(4, 6)).append("-").
					append(tradeTime.substring(6, 8)).append(" ").append(tradeTime.substring(8, 10)).append(":").
					append(tradeTime.substring(10, 12)).append(":").append(tradeTime.substring(tradeTime.length() - 2, tradeTime.length()));
					duizhangErrorCupsLst.setReqTime(sb.toString());
					//差错原因
					String errorInfo = duizhangErrorCupsLst.getError_info();
					duizhangErrorCupsLst.setHandling_name(commonClass.getHandlingNameById(commonClass.getIdByReasonCode(errorInfo)));
					data = new String[] {
						duizhangErrorCupsLst.getReqSysStance(),
						duizhangErrorCupsLst.getReqTime(),
						String.format("%.2f", Double.valueOf(duizhangErrorCupsLst.getTradeAccount())),
						duizhangErrorCupsLst.getOutAccount(),
						duizhangErrorCupsLst.getDeductSysReference(),
						duizhangErrorCupsLst.getHandling_name(),
						duizhangErrorCupsLst.getBk_chk() == 0 ? "对账可疑" : duizhangErrorCupsLst.getBk_chk() == 1 ?
								"对账成功" : "对账失败"
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"银联差错对账文件数据列表.xls");
			if(flag){
				logger.info("银联差错对账文件数据列表.xls  文件创建成功");
			}else{
				logger.info("银联差错对账文件数据列表.xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 内部差错调整 数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = INNER_ERROR_ADJUST_EXCEL)
	public void downExcelForInnerErrorAdjust(HttpServletRequest request, HttpServletResponse response) {
		logger.info("进入差错调整xls下载操作...");
		try {
			//查询条件
			//交易日期
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			//参考号
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			//订单号
			String additional_data = request.getParameter("additional_data");
			String whtherInnerJs = request.getParameter("whtherInnerJs");
			
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
			
			List<ErrorDataLst> list = errorDataLstService.queryAllForDownExcel(map);
			
			String[] header = new String[]{
					"序号","流水号", "交易时间D", "交易金额D", "交易类型D", "交易结果D", "交易时间Q", "交易金额Q", "交易类型Q",
					"交易结果Q", "扣款渠道", "差错类型", "状态", "差错来源", "备注", "处理方式", "处理时间", "差错清算日期","重对账备注", "文件名称"
			};
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		 	
		 	Calendar calendar = Calendar.getInstance();
		 	String date = DateUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		 	
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("差错处理表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    int row = 1;
			if(list != null && list.size() > 0){
				String[] data = null;
			    
				for (ErrorDataLst errorDataLst : list) {
					data = new String[] {
						row+"",
						errorDataLst.getDeduct_sys_stance(), 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getDeduct_roll_bk() == 0) ? (errorDataLst.getDeduct_sys_time().contains(".") ? errorDataLst.getDeduct_sys_time().replace(".0", "") : errorDataLst.getDeduct_sys_time()) : 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getDeduct_roll_bk() == 1) ? (errorDataLst.getDeduct_rollbk_sys_time().contains(".") ? errorDataLst.getDeduct_rollbk_sys_time().replace(".0", "") : errorDataLst.getDeduct_rollbk_sys_time()) : "",
						errorDataLst.getError_resource() == 0 ? String.valueOf(errorDataLst.getTrade_amount()) : "",
						(errorDataLst.getError_resource() == 0 && errorDataLst.getInst_type() == 0)	? errorDataLst.getTradeType() : 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getInst_type() == 1 && errorDataLst.getTrademsg_type() == 2)	? "收款交易" : 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getInst_type() == 1 && errorDataLst.getTrademsg_type() == 20)	? "退款交易" : "",
						(errorDataLst.getError_resource() == 0 && errorDataLst.getDeduct_roll_bk() == 0 && "00".equals(errorDataLst.getDeduct_sys_response()) ) ? "成功" : 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getDeduct_roll_bk() == 0 && "N1".equals(errorDataLst.getDeduct_sys_response()) ) ? "超时" : 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getDeduct_roll_bk() == 0 && !"N1".equals(errorDataLst.getDeduct_sys_response()) ) ? "失败" : 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getDeduct_roll_bk() == 1 && "00".equals(errorDataLst.getDeduct_roll_bk_response()) ) ? "成功" : 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getDeduct_roll_bk() == 1 && "N1".equals(errorDataLst.getDeduct_roll_bk_response()) ) ? "超时" : 
						(errorDataLst.getError_resource() == 0 && errorDataLst.getDeduct_roll_bk() == 1 && !"N1".equals(errorDataLst.getDeduct_roll_bk_response()) ) ? "失败" : "",
						errorDataLst.getError_resource() == 0 ? errorDataLst.getDzTime() : (errorDataLst.getTrade_time().contains(".") ? errorDataLst.getTrade_time().replace(".0", "") :errorDataLst.getTrade_time()) ,
						errorDataLst.getError_resource() == 0 ?	errorDataLst.getDzTradeAmount() : String.valueOf(errorDataLst.getTrade_amount()),
						errorDataLst.getError_resource() == 0 ? errorDataLst.getDzTradeType() : 
						(errorDataLst.getError_resource() == 1 && errorDataLst.getInst_type() == 0) ? errorDataLst.getTradeType() : 
						(errorDataLst.getError_resource() == 1 && errorDataLst.getInst_type() == 1 && errorDataLst.getTrademsg_type() == 2) ? "收款交易" : 
						(errorDataLst.getError_resource() == 1 && errorDataLst.getInst_type() == 1 && errorDataLst.getTrademsg_type() == 20) ? "退款交易" : "",
						errorDataLst.getError_resource() == 0 ? errorDataLst.getDzTradeResult() : "成功",
						errorDataLst.getName_(),
						errorDataLst.getWhetherErroeHandle() == 0 ? "正常" : errorDataLst.getWhetherErroeHandle() == 1 ? "长款" : errorDataLst.getWhetherErroeHandle() == 2 ? "短款" : errorDataLst.getWhetherErroeHandle() == 3 ? "金额不符" : "短款(清算未对账)",
						errorDataLst.getHandling_status() == 0 ? "未处理" : errorDataLst.getHandling_status() == 1 ? "待审核" : errorDataLst.getHandling_status() == 2 ? "已审核" : "已驳回",
						errorDataLst.getError_resource() == 0 ? "原始差错" : "对账文件差错",
						errorDataLst.getHandler_remark(),
						errorDataLst.getHandling_name(),
						errorDataLst.getHandling_time(),
						errorDataLst.getJs_date(),
						errorDataLst.getCdz_remark(),
						errorDataLst.getNii()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			String[] data = new String[]{"总计:"+(row-1)+"条记录","","","","","","","","","","","","","","","","",""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,StringPingJie.getInstance().getStringPingJie("差错处理表_", date, ".xls"));
			if(flag){
				logger.info(StringPingJie.getInstance().getStringPingJie("差错处理表_", date, ".xls 文件创建成功"));
			}else{
				logger.info(StringPingJie.getInstance().getStringPingJie("差错处理表_", date, ".xls 文件创建失败"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 银联查差错查询 数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = CUPS_ERROR_DOWN_EXCEL)
	public void downExcelCupsError(HttpServletRequest request, HttpServletResponse response) {
		try {
			//Excel表头
			String[] header = { "清算日期", "主账号", "交易时间", "交易金额",
				"交易结果", "系统跟踪号", "交易类型", "处理方式", 
				"来源", "状态", "驳回原因"
			};
			
			//查询参数
			String deductStlmDate = request.getParameter("deductStlmDate");
			String out_account = request.getParameter("out_account");
			String entering_time = request.getParameter("entering_time");
			String trade_type = request.getParameter("tradeMsgType");
			String reqSysStance = request.getParameter("reqSysStance");
			String handling_id = request.getParameter("handling_id");
			String trade_status = request.getParameter("trade_status");
			
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
			List<YlCupsErrorEntry> list = ylCupsErrorEntryDao.queryForList("YlCupsErrorEntry.queryDataForDownExcel", map);
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("银联差错列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
			if (list != null && list.size() > 0) {
				String[] data = null;
			    int row = 1;
			    for (YlCupsErrorEntry ylCupsErrorEntry : list) {
					//金额处理
					ylCupsErrorEntry.setTradeAmount(new Double(ylCupsErrorEntry.getTradeAmount())/100 + "");
					//处理交易类型
					String reqProcess = ylCupsErrorEntry.getProcess();
					int tradeMsgType = ylCupsErrorEntry.getTradeMsgType();
					if (StringUtils.isNotBlank(reqProcess) && tradeMsgType != 0) {
						TradeAmountConf tradeAmountConf = dataManagerInit.getTradeAmountConfMap(reqProcess + tradeMsgType);
						if (tradeAmountConf != null) {
							ylCupsErrorEntry.setTradeType(tradeAmountConf.getName());
						} else {
							ylCupsErrorEntry.setTradeType("未知");
						}
					} else {
						ylCupsErrorEntry.setTradeType("未知");
					}
					
					//处理方式
					int handlingId = ylCupsErrorEntry.getHandling_id();
					ErrorHandling errorHandling = dataManagerInit
							.getErrorHanding(handlingId);
					if (errorHandling != null) {
						ylCupsErrorEntry.setHandling_name(errorHandling
								.getHandling_name());
					} else {
						ylCupsErrorEntry.setHandling_name("未知");
					}
					
					data = new String[] {
						ylCupsErrorEntry.getDeductStlmDate().substring(0, 19), 
						ylCupsErrorEntry.getOut_account(),
						ylCupsErrorEntry.getTrade_time(),
						String.format("%.2f", Double.valueOf(ylCupsErrorEntry.getTradeAmount())),
						ylCupsErrorEntry.getTrade_result() == 0  ? "成功" : ylCupsErrorEntry.getTrade_result() == 1 ? 
								"超时" : ylCupsErrorEntry.getTrade_result() == 2 ? "失败" : 
								(ylCupsErrorEntry.getTrade_result() == 3 && "480000".equals(ylCupsErrorEntry.getProcess())) ? "受理成功" :
								(ylCupsErrorEntry.getTrade_result() == 3 && !"480000".equals(ylCupsErrorEntry.getProcess())) ? "冲正成功" : "",
						ylCupsErrorEntry.getReqSysStance(),
						ylCupsErrorEntry.getTradeType(),
						ylCupsErrorEntry.getHandling_name(),
						ylCupsErrorEntry.getTrade_source(),
						ylCupsErrorEntry.getTrade_status() == 0 ? "未处理" : ylCupsErrorEntry.getTrade_status() == 1 ?
								"待审核" : ylCupsErrorEntry.getTrade_status() == 2 ? "已审核" : "已驳回",
						ylCupsErrorEntry.getTurnDown_remark()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"银联差错列表.xls");
			if(flag){
				logger.info("银联差错列表.xls  文件创建成功");
			}else{
				logger.info("银联差错列表.xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 已处理差错查询 数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = OPTION_ERROR_DOWN_EXCEL)
	public void downExcelOptionError(HttpServletRequest request,HttpServletResponse response) {
		logger.info("开始下载已处理差错数据...");
		try {
			//Excel表头
			String[] header = {
				"内部商户号", "流水号", "主账号", "交易金额", "交易类型", "扣款渠道",
				"审批时间", "差错清算日期", "交易时间", "处理方式", "备注"
			};
			
			//查询条件
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			String js_date = request.getParameter("js_date");
			String additional_data = request.getParameter("additional_data");
			String req_sys_stance = request.getParameter("req_sys_stance");
			String handling_id = request.getParameter("handling_id");
			String check_date = request.getParameter("check_date");
			
			String deduct_sys_id = null;
			String inst_type = null;
			String bankId = null;
			String bank_id = request.getParameter("bank_id");
			
			if (StringUtils.isNotBlank(bank_id)) {
				String[] bankInstList = bank_id.split(",");
				if (bankInstList != null && bankInstList.length > 0) {
					bankId = bankInstList[0];
				}
			}
			String channel = request.getParameter("channel");
			if (StringUtils.isNotBlank(inst_type)) {
				String[] instInfoList = channel.split(",");
				if (instInfoList != null && instInfoList.length > 0) {
					inst_type = instInfoList[0];
					deduct_sys_id = instInfoList[1];
				}
			}
			
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
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
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
			List<ErrorDataLst> list = errorDataLstDao.queryForList("ErrorData_Lst.queryOptionDataForDownExcel", map);
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("已处理差错列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
			if (list != null && list.size() > 0) {
				String[] data = null;
			    int row = 1;
			    for (ErrorDataLst errorDataLst : list) {
					//处理流水号
					int deductRollBk = errorDataLst.getDeduct_roll_bk();
					String sysStance = "";
					if (deductRollBk == 0) {
						sysStance = errorDataLst.getDeduct_sys_stance();
					} else {
						sysStance = errorDataLst.getDeduct_roll_bk_stance();
					}
					
					//处理交易类型
					String reqProcess = errorDataLst.getReq_process();
					int tradeMsgType = errorDataLst.getTrademsg_type();
					int deductSysId = errorDataLst.getDeduct_sys_id();
					int instType = errorDataLst.getInst_type();
					InstInfo instInfo = dataManagerInit.getInstInfoById(deductSysId, instType);
					//扣款渠道
					String infoName = "";
					if (instInfo != null) {
						infoName = instInfo.getName();
						if (instType == 0) {
							errorDataLst.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(reqProcess,tradeMsgType));
						} else {
							if (tradeMsgType == 2) {
								errorDataLst.setTradeType("收款交易");
							}
							if (tradeMsgType == 20) {
								errorDataLst.setTradeType("退款交易");
							}
						}
					}
					
					//处理方式
					int handlingId = errorDataLst.getHandling_id();
					errorDataLst.setHandling_name(commonClass.getInnerHandlingNameByHandlingId(handlingId));
					
					data = new String[] {
						errorDataLst.getReq_mer_code(),
						sysStance,
						errorDataLst.getOut_account(),
						String.format("%.2f", Double.valueOf(errorDataLst.getTrade_amount())),
						errorDataLst.getTradeType(),
						infoName,
						errorDataLst.getCheck_time(),
						errorDataLst.getJs_date(),
						errorDataLst.getTrade_time().substring(0, 19),
						errorDataLst.getHandling_name(),
						errorDataLst.getHandler_remark()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"已处理差错列表.xls");
			if(flag){
				logger.info("已处理差错列表.xls  文件创建成功");
			}else{
				logger.info("已处理差错列表.xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("已处理差错查询 下载xls报表出错：" + e.getMessage());
		}
	}
	
	/**
	 * 应收银行交易款查询数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = BANKDEALWITH_EXCEL)
	public void bankExcel(HttpServletRequest request,HttpServletResponse response) {
		logger.info("开始下载应收银行交易款查询数据...");
		try {
			//Excel表头
			String[] header = {"序号","渠道类型", "渠道名", "支付金额", "退款金额", "银行手续费", "银行退回手续费", "银行实收手续费", "品牌服务费", "银行划款额"};
			String startDate=null;
			String endDate=null;
			String startTime=request.getParameter("startTime");//交易起始时间
			if(StringUtils.isNotBlank(startTime)){
				startDate=startTime.replace("-","");
			}
			String endTime=request.getParameter("endTime");//交易截止时间
			if(StringUtils.isNotBlank(endTime)){
				endDate=endTime.replace("-","");
			}
			StringBuffer inst_idBuffer=new StringBuffer();
			String shangxia=request.getParameter("arr");//线上、下渠道
			if(StringUtils.isNotBlank(shangxia)){
				String[] offline=shangxia.split(",");
				for (int i = 0; i < offline.length; i++) {
					
					inst_idBuffer.append(" (inst_id = ");
					String inst_id=offline[i].split("-")[0];
					inst_idBuffer.append(inst_id);
					inst_idBuffer.append(" and ");
					
					inst_idBuffer.append("inst_type = ");
					String inst_type=offline[i].split("-")[1];
					inst_idBuffer.append(inst_type);
					inst_idBuffer.append(") or ");
				}
			}
			String sqlStr = inst_idBuffer.toString();
			String instId=sqlStr.substring(0, sqlStr.length()-4);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("instId", instId);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			List<BankDealWith> pageList=bankDealWithService.queryListCountInfo(map);
			List<BankDealWith> list=new ArrayList<BankDealWith>();
			Map<String,Object> map_ = new HashMap<String,Object>();
			if(pageList !=null && pageList.size() > 0){
				for(BankDealWith bkw:pageList){
					map_.put(bkw.getName()+"-"+bkw.getInstType(), bkw.getName()+"-"+bkw.getInstType());
						BankDealWith bank=new BankDealWith();
						if(StringUtils.isNotBlank(bkw.getSunmPayAmt())){//支付金额
							double payamt=Double.parseDouble(bkw.getSunmPayAmt());
							bank.setSunmPayAmt(String.format("%.2f", payamt));//支付金额
						}
						if(StringUtils.isNotBlank(bkw.getSumArefundAmt())){//退款金额
							double arefundamt=Double.parseDouble(bkw.getSumArefundAmt());
							bank.setSumArefundAmt(String.format("%.2f",arefundamt));//退款金额
						}
						if(StringUtils.isNotBlank(bkw.getSumPayFee())){//支付手续费
							double payfee=Double.parseDouble(bkw.getSumPayFee());
							bank.setSumPayFee(String.format("%.2f",payfee));//支付手续费
						}
						if(StringUtils.isNotBlank(bkw.getSumArefundFee())){//退款手续费
							double arefunfee=Double.parseDouble(bkw.getSumArefundFee());
							bank.setSumArefundFee(String.format("%.2f",arefunfee));//退款手续费
						}
						bank.setOriginalDataTableName(bkw.getOriginalDataTableName());
						
						double bankPaidInFees=Double.parseDouble(bkw.getSumPayFee())+Double.parseDouble(bkw.getSumArefundFee());
						bank.setBankPaidInFee(String.format("%.2f",bankPaidInFees));//银行实收手续费
						
						Double ppfw_fee = 0.00d;//品牌服务费
						if(StringUtils.isNotBlank(bkw.getName())){
							InstInfo instinfo=dataManagerInit.getInstInfoById(Integer.parseInt(bkw.getName()),Integer.parseInt(bkw.getInstType()));
							bank.setName(instinfo.getName());//获取渠道名
							bank.setInstType(bkw.getInstType());//获取渠道类型
							String original_data_tableName = dataManagerInit.getBankInstByBankId(instinfo.getBank_id()).getOriginal_data_tableName();
							bank.setOriginalDataTableName(original_data_tableName);//线上线下渠道对应交易表
							
							if(instinfo.getWhether_parse_brank() == 1){//针对银联cups渠道，到数据库表中查询数据，其他渠道则默认为0.00
								ppfw_fee = ppfwDataService.getPpfwFeeTotalCount(map);
							}
							bank.setPpfw_fee(ppfw_fee);
						}
						
						double bankamts=(Double.parseDouble(bkw.getSunmPayAmt()))+(Double.parseDouble(bkw.getSumArefundAmt()))-((Double.parseDouble(bkw.getSumPayFee()))+(Double.parseDouble(bkw.getSumArefundFee())) + ppfw_fee);
						bank.setBankAmt(String.format("%.2f", bankamts));//银行划款额
						
						list.add(bank);
				  }
				String[] id_arr = shangxia.split(",");
				for(int i=0;i<id_arr.length;i++){
					String instid=id_arr[i].split("-")[0];
					String instType=id_arr[i].split("-")[1];//渠道类型
					if(!map_.containsKey(instid+"-"+instType)){
						BankDealWith with=new BankDealWith();
						InstInfo instinfo=dataManagerInit.getInstInfoById(Integer.parseInt(instid),Integer.parseInt(instType));
						String original_data_tableName = dataManagerInit.getBankInstByBankId(instinfo.getBank_id()).getOriginal_data_tableName();
						with.setOriginalDataTableName(original_data_tableName);//线上线下渠道对应交易表
						with.setName(instinfo.getName());//获取渠道名
						with.setInstType(instType);//获取渠道类型
						with.setSunmPayAmt("0.00");//支付金额
						with.setSumArefundAmt("0.00");//退款金额
						with.setSumPayFee("0.00");//支付手续费
						with.setSumArefundFee("0.00");//退款手续费
						with.setBankPaidInFee("0.00");//实收手续费
						with.setPpfw_fee(0.00d);//品牌服务费
						with.setBankAmt("0.00");//银行划款额
						list.add(with);
					}
				}
			}else{
				if(StringUtils.isNotBlank(shangxia)){
					String[] offline=shangxia.split(",");
					for (int i = 0; i < offline.length; i++) {
						String instid=offline[i].split("-")[0];
						String instType=offline[i].split("-")[1];//渠道类型
							BankDealWith with=new BankDealWith();
							InstInfo instinfo=dataManagerInit.getInstInfoById(Integer.parseInt(instid),Integer.parseInt(instType));
							String original_data_tableName = dataManagerInit.getBankInstByBankId(instinfo.getBank_id()).getOriginal_data_tableName();
							with.setOriginalDataTableName(original_data_tableName);//线上线下渠道对应交易表
							with.setName(instinfo.getName());//获取渠道名
							with.setInstType(instType);//获取渠道类型
							with.setSunmPayAmt("0.00");//支付金额
							with.setSumArefundAmt("0.00");//退款金额
							with.setSumPayFee("0.00");//支付手续费
							with.setSumArefundFee("0.00");//退款手续费
							with.setBankPaidInFee("0.00");//实收手续费
							with.setPpfw_fee(0.00d);//品牌服务费
							with.setBankAmt("0.00");//银行划款额
							list.add(with);
					}
				}
			}
			
			Calendar calendar = Calendar.getInstance();//系统当前时间
			SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMdd");  
			String newdate=sdformat.format(calendar.getTime());
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("BankHLOG_"+newdate);
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    int row = 1;
		    double tradeAmount = 0.0d;//支付金额
		    double refundAmount = 0.0d;//退款金额
		    double zfFee = 0.0d;//银行手续费
		    double zfRefundFee = 0.0d;//银行退回手续费
		    double zfShFee = 0.0d;//银行实收手续费
		    double ppFwFee = 0.0d;//品牌服务费
		    double zfAmount = 0.0d;//银行划款额
			if (list != null && list.size() > 0) {
				String[] data = null;
			    for (BankDealWith bankDealWith : list) {
					if(bankDealWith!=null){
						data = new String[] {
							row+"",
							bankDealWith.getInstType().equals("1")?"线上渠道":"线下渠道",
							bankDealWith.getName(),
							bankDealWith.getSunmPayAmt(),
							bankDealWith.getSumArefundAmt(),
							bankDealWith.getSumPayFee(),
							bankDealWith.getSumArefundFee(),
							bankDealWith.getBankPaidInFee(),
							String.format("%.2f", bankDealWith.getPpfw_fee()),
							bankDealWith.getBankAmt()
						};
						CreateExcelUtil.output(cellStyle,sheet,row,data);
						data = null;
						if(StringUtils.isNotBlank(bankDealWith.getSunmPayAmt())){
							tradeAmount +=  Double.valueOf(bankDealWith.getSunmPayAmt());
						}
						if(StringUtils.isNotBlank(bankDealWith.getSumArefundAmt())){
							refundAmount +=  Double.valueOf(bankDealWith.getSumArefundAmt());
						}
						if(StringUtils.isNotBlank(bankDealWith.getSumPayFee())){
							zfFee +=  Double.valueOf(bankDealWith.getSumPayFee());
						}
						if(StringUtils.isNotBlank(bankDealWith.getSumArefundFee())){
							zfRefundFee +=  Double.valueOf(bankDealWith.getSumArefundFee());
						}
						if(StringUtils.isNotBlank(bankDealWith.getBankPaidInFee())){
							zfShFee +=  Double.valueOf(bankDealWith.getBankPaidInFee());
						}
						
						ppFwFee +=  Double.valueOf(bankDealWith.getPpfw_fee());
						
						if(StringUtils.isNotBlank(bankDealWith.getBankAmt())){
							zfAmount +=  Double.valueOf(bankDealWith.getBankAmt());
						}
						
						row++;
					}
				}
			}
			
			//写入数据统计行
			String[] data = new String[]{"总计:"+(row-1)+"条记录","","",FlightUtil.dfFormat("0.00", tradeAmount)+"",FlightUtil.dfFormat("0.00", refundAmount)+"",FlightUtil.dfFormat("0.00", zfFee)+"",FlightUtil.dfFormat("0.00", zfRefundFee)+"",FlightUtil.dfFormat("0.00", zfShFee)+"",FlightUtil.dfFormat("0.00", ppFwFee)+"",FlightUtil.dfFormat("0.00", zfAmount)+""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"BankHLOG_"+newdate+".xls");
			if(flag){
				logger.info("BankHLOG_"+newdate+".xls  文件创建成功");
			}else{
				logger.info("BankHLOG_"+newdate+".xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("应收银行交易款数据列表下载xls报表出错：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = MERCHANTACCOUNTING_EXCEL )
	public void AccountingExcel(HttpServletRequest request,HttpServletResponse response) {
		logger.info("开始下载商户账务查询列表查询数据...");
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String merCode = request.getParameter("merCode");
			String mer_category = request.getParameter("mer_category");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(mer_category)){
				map.put("mer_category", Integer.valueOf(mer_category));
			}
			if(StringUtils.isNotBlank(merCode)){
				map.put("mer_code", merCode);
			}
			if(StringUtils.isNotBlank(startTime)){
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if(StringUtils.isNotBlank(endTime)){
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			List<MerFundStance> list = merchantAccountingService.queryMerchantAccountingList(map);//根据交易时间获取商户号
			
			//Excel表头
			String[] header = {"商户号", "商户简称", "商户类型", "上期账户余额", "起始交易日期", "支付金额", "退款金额","商户手续费","商户退回手续费","结算金额","结束交易日期","本期账户余额"};
			
			Calendar calendar = Calendar.getInstance();//系统当前时间
			SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMdd");  
			String newdate=sdformat.format(calendar.getTime());
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("MerAccount_"+newdate);
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
			if (list != null && list.size() > 0) {
				String[] data = null;
			    int row = 1;
			    for(MerFundStance merFundStance:list){
			    	if(StringUtils.isBlank(merFundStance.getOnAccountAmt()) && StringUtils.isBlank(merFundStance.getTheAmount()) && StringUtils.isBlank(merFundStance.getRefundAmount())){
						continue;
						
					}else if("0".equals(merFundStance.getOnAccountAmt()) && "0".equals(merFundStance.getTheAmount()) && "0".equals(merFundStance.getRefundAmount())){
						continue;
					}
					data = new String[] {
						merFundStance.getMer_code(),
						merFundStance.getMer_name(),
						merFundStance.getMer_category() == 0 ? "RYF商户" : merFundStance.getMer_category() == 1 ? "VAS商户" : "POS商户",		
						merFundStance.getOnAccountAmt(),
						merFundStance.getStartDate(),
						merFundStance.getTheAmount(),
						merFundStance.getRefundAmount(),
						merFundStance.getMerFees(),
						merFundStance.getMerBackFees(),
						merFundStance.getSettlementAmt(),
						merFundStance.getEndDate(),
						merFundStance.getThisAccountAmt(),
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"MerAccount_"+newdate+".xls");
			if(flag){
				logger.info("MerAccount_"+newdate+".xls  文件创建成功");
			}else{
				logger.info("MerAccount_"+newdate+".xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("商户账务查询列表下载xls报表出错：" + e.getMessage());
		}
	}
	
	/**
	 * 对账明细下载xls报表
	 * @param request
	 */
	@RequestMapping(value = DZ_DETAIL_DOWN_EXCEL)
	public void dzDetailDownExcel(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载对账明细数据...");
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");
			String deduct_sys_stance = request.getParameter("deduct_sys_stance");
			String deduct_mer_code = request.getParameter("deduct_mer_code");
			String out_account = request.getParameter("out_account");
			String req_mer_term_id = request.getParameter("req_mer_term_id");
			String additional_data = request.getParameter("additional_data");
			String whtherInnerJs = request.getParameter("whtherInnerJs");
			String req_mer_code = request.getParameter("req_mer_code");
			String bk_chk = request.getParameter("bk_chk");
			String whetherRiqie = request.getParameter("whetherRiqie");
			String deduct_sys_reference = request.getParameter("deduct_sys_reference");
			String deduct_result = request.getParameter("deduct_result");
			
			String original_data_tableName = null;
			String bank_type = null;
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				bank_type = bankInstList[1];
				original_data_tableName = bankInstList[2];
			}
			
			String inst_id = null;
			String inst_type = null;
			InstInfo info = null;
			String channel = request.getParameter("channel");
			if (StringUtils.isNotBlank(channel)) {
				String[] instInfoList = channel.split(",");
				if (instInfoList != null && instInfoList.length > 0) {
					inst_id = instInfoList[0];
					inst_type = instInfoList[1];
					info = dataManagerInit.getInstInfoById(Integer.valueOf(inst_id), Integer.valueOf(inst_type));
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		 	
		 	Calendar calendar = Calendar.getInstance();
			String date = DateUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		 	
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("对账明细列表");
		    int row = 1;//行数
		    String tradeAmount = "0";//交易金额
		    double merFee = 0d;//商户手续费
		    double zfFee = 0d;//支付手续费
		    
		    String[] header = new String[]{
					"序号", "交易流水号", "电银商户号","商户简称","商户订单号", "交易时间", "订单金额(元)","交易金额(元)", "交易状态", 
					"对账结果","交易类别", "扣款渠道", "商户手续费", "支付手续费", "银行流水号"
			};
			
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
			if ("0".equals(bank_type)) {
				if (StringUtils.isNotBlank(startTime)) {
					map.put("startTime", DateUtil.getformatConversionStart(startTime));
				}
				if (StringUtils.isNotBlank(endTime)) {
					map.put("endTime", DateUtil.getformatConversionEnd(endTime));
				}
				if (StringUtils.isNotBlank(deduct_stlm_date)) {
					map.put("deduct_stlm_date", deduct_stlm_date);
				}
				if (StringUtils.isNotBlank(deduct_sys_stance)) {
					map.put("deduct_sys_stance", deduct_sys_stance);
				}
				if (StringUtils.isNotBlank(deduct_mer_code)) {
					map.put("deduct_mer_code", deduct_mer_code);
				}
				if (StringUtils.isNotBlank(original_data_tableName)) {
					map.put("original_data_tableName", original_data_tableName);//渠道原始交易数据表名
				}
				if (StringUtils.isNotBlank(inst_id)) {
					map.put("deduct_sys_id", inst_id);//渠道ID
				}
				if (StringUtils.isNotBlank(inst_type)) {
					map.put("inst_type", inst_type);//渠道类型  0-线下;1-线上
				}
				if (StringUtils.isNotBlank(out_account)) {
					map.put("out_account", out_account);
				}
				if (StringUtils.isNotBlank(req_mer_term_id)) {
					map.put("req_mer_term_id", req_mer_term_id);
				}
				if (StringUtils.isNotBlank(additional_data)) {
					map.put("additional_data", additional_data);
				}
				if (StringUtils.isNotBlank(whtherInnerJs)) {
					map.put("whtherInnerJs", whtherInnerJs);
				}
				if (StringUtils.isNotBlank(req_mer_code)) {
					map.put("req_mer_code", req_mer_code);
				}
				if (StringUtils.isNotBlank(bk_chk)) {
					map.put("bk_chk", bk_chk);
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
				
				List<OriginalData> list = duizhangResultService.queryChannelDzResultDataLst(map);
				
				if(list != null && list.size() > 0){
					String[] data = null;
					for (OriginalData originalData : list) {
						tradeAmount = PoundageCalculate.add(String.valueOf(originalData.getTrade_amount()),tradeAmount).toString() ;
						merFee = PoundageCalculate.add(originalData.getMer_fee(),merFee).doubleValue() ;
						zfFee = PoundageCalculate.add(originalData.getZf_fee(),zfFee).doubleValue() ;
						
						Object obj = ReflectUtils.getFieldValue(originalData, info.getDz_data_column());
						
						int deductRollBk = originalData.getDeduct_roll_bk();
						String reqSysStance = "";
						String tradeStatus = "未知";
						if (deductRollBk == 0) {
							reqSysStance = originalData.getDeduct_sys_stance();
							int tradeResult = originalData.getTrade_result();
							switch(tradeResult){
							  case 0:
								  tradeStatus = "成功";
								  break;
							  case 1:
								  tradeStatus = "超时";
								  break;
							  case 2:
								  tradeStatus = "失败";
								  break;
							}
						} else {
							reqSysStance = originalData.getDeduct_roll_bk_stance();
							int tradeResult = originalData.getDeduct_roll_bk_result();
							switch(tradeResult){
							  case 0:
								  tradeStatus = "成功";
								  break;
							  case 1:
								  tradeStatus = "超时";
								  break;
							  case 2:
								  tradeStatus = "失败";
								  break;
							}
						}
						
						data = new String[] {
								row+"",
								reqSysStance,
								originalData.getReq_mer_code(),//电银商户号
								originalData.getMer_name(),//商户简称
								originalData.getAdditional_data(),//商户订单号
								StringUtils.isNotBlank(originalData.getTrade_time())?originalData.getTrade_time().substring(0, originalData.getTrade_time().length()-2):"",//交易时间
								originalData.getTrade_amount()==0d?"0.00":String.valueOf(originalData.getTrade_amount()),//交易金额
								originalData.getTrade_amount()==0d?"0.00":String.valueOf(originalData.getTrade_amount()),//交易金额
								tradeStatus,
								originalData.getBk_chk()==0?"未对账":originalData.getBk_chk()==1?"对账成功":originalData.getBk_chk()==2?"对账失败":"无需对账",
								commonClass.getTradeNameByTerminalInfo(originalData.getTerminal_info()),//交易类别
								originalData.getName_(),//扣款渠道名称
								StringUtils.isBlank(originalData.getMer_fee())?"0.00":originalData.getMer_fee(),//商户手续费
								StringUtils.isBlank(originalData.getZf_fee())?"0.00":originalData.getZf_fee(),//支付手续费
								obj == null ? "" : obj.toString()
						};
						CreateExcelUtil.output(cellStyle,sheet,row,data);
						data = null;
						row++;
					}
				}
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
				if (StringUtils.isNotBlank(inst_id)) {
					map.put("deduct_sys_id", inst_id);
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
				
				List<RytUpmp> rytUpmpList = rytUpmpService.queryRytDzDetailDataList(map);
				
				if(rytUpmpList != null && rytUpmpList.size() > 0){
					String[] data = null;
					for (RytUpmp rytUpmp : rytUpmpList) {
						tradeAmount = PoundageCalculate.add(String.valueOf(rytUpmp.getAmount()),tradeAmount).toString() ;
						merFee = PoundageCalculate.add(rytUpmp.getMer_fee(),merFee).doubleValue() ;
						zfFee = PoundageCalculate.add(rytUpmp.getZf_fee(),zfFee).doubleValue() ;
						data = new String[] {
								row+"",
								rytUpmp.getTseq(),
								rytUpmp.getMid(),
								rytUpmp.getMer_abbreviation(),
								rytUpmp.getOid(),
								DateUtil.parseTimePattern(rytUpmp.getSys_date(), rytUpmp.getSys_time()),
								String.valueOf(rytUpmp.getAmount()),
								String.valueOf(rytUpmp.getAmount()),
								rytUpmp.getTstat() == 0 ? "初始状态" : rytUpmp.getTstat() == 1 ? "待支付" :  rytUpmp.getTstat() == 2 ? "成功" : rytUpmp.getTstat() == 3 ? "失败" : rytUpmp.getTstat() == 4 ? "请求银行失败" : "撤销",
								rytUpmp.getBk_chk().equals("0") ? "未对账" : rytUpmp.getBk_chk().equals("1") ? "对账成功" : rytUpmp.getBk_chk().equals("2") ? "对账失败" : "无需对账",
								Ryt_trade_type.getRytTradeName(rytUpmp.getType()),
								rytUpmp.getName(),
								String.valueOf(rytUpmp.getMer_fee()),
								String.valueOf(rytUpmp.getZf_fee()),
								rytUpmp.getTseq()
								
						};
						CreateExcelUtil.output(cellStyle,sheet,row,data);
						data = null;
						row++;
					}
				}
			}
			//写入数据统计行
			String[] data = new String[]{"总计:"+(row-1)+"条记录","","","","","", tradeAmount,tradeAmount+"","","","","",merFee+"",zfFee+"",""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"对账明细列表_"+date+".xls");
			if(flag){
				logger.info("对账明细列表.xls  文件创建成功");
			}else{
				logger.info("对账明细列表.xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("下载对账明细Excel出现异常：" + e.getMessage());
		}
	}
	/**
	 * 下载银行对账可疑数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value=DOWNLOADBANKERRORDATAOFEXCEL)
	public void downLoadBankErrorDataOfExcel(HttpServletRequest request, HttpServletResponse response){
		try {
			String bank_id = request.getParameter("bank_id");//银行网关号
			String deduct_stlm_date = request.getParameter("summaryDate");//清算日期
			
			if(StringUtils.isNotBlank(bank_id) && StringUtils.isNotBlank(deduct_stlm_date)){
				
				logger.info("下载银行对账可疑数据...参数:---网关号-----"+bank_id+"------交易日期-------"+deduct_stlm_date);
				
				BankInst bankInst = dataManagerInit.getBankInstByBankId(Integer.valueOf(bank_id));
				
				if(bankInst != null){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("bankTable", bankInst.getDz_data_tableName());
					map.put("deduct_stlm_date_error", DateUtil.getformatConversionStart(deduct_stlm_date));
					map.put("bankId", bank_id);
					
					String instIdAndType = request.getParameter("inst_name");
					InstInfo instInfo = null;
					if(StringUtils.isNotBlank(instIdAndType)){
						String[] info = instIdAndType.split(",");
						instInfo = instInfoService.queryInstInfoByInstId(Integer.valueOf(info[0]),Integer.valueOf(info[1]));
					}
					map.put("deduct_sys_id", instInfo==null?0:instInfo.getInstId());
					
					List<ErrorDataLst> dataList = errorDataLstService.queryDzTotalErrorDataList(map);
					//Excel表头
					String[] header = {"银行订单号/银行流水号", "银行交易金额", "对账数据来源"};
					Calendar calendar = Calendar.getInstance();//系统当前时间
					SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMdd");  
					String newdate=sdformat.format(calendar.getTime());
					
					// 创建新的Excel 工作簿
				    HSSFWorkbook workbook = new HSSFWorkbook();
				    // 设置单元格格式(文本)
				 	HSSFCellStyle cellStyle = workbook.createCellStyle();
				    // 在Excel 工作簿中建一工作表
				    HSSFSheet sheet = workbook.createSheet("BankErrorData_"+newdate);
				    //创建抬头(标题)
				    CreateExcelUtil.createHeader(workbook,sheet,header);
					if(dataList != null && dataList.size() > 0){
						String[] data = null;
					    int row = 1;
					    for(ErrorDataLst dzData:dataList){
							data = new String[] {
								dzData.getAdditional_data()+"/"+dzData.getReq_sys_stance(),
								dzData.getTrade_amount()+"",
								dzData.getNii(),		
							};
							CreateExcelUtil.output(cellStyle,sheet,row,data);
							data = null;
							row++;
						}
					}else{
						logger.info("参数:---网关号-----"+bank_id+"------交易日期-------"+deduct_stlm_date+",查询银行对账可疑数据为空");
					}
					
					boolean flag = CreateExcelUtil.createExcel(response, workbook,"BankErrorData_"+newdate+".xls");
					if(flag){
						logger.info("BankErrorData_"+newdate+".xls  文件创建成功");
					}else{
						logger.info("BankErrorData_"+newdate+".xls  文件创建失败");
					}
					
				}else{
					logger.info("下载银行对账可疑数据，参数：银行网关号------"+bank_id+"------获取网关为空");
				}
				
			}else{
				logger.info("下载方法无法执行，参数：网关号---"+bank_id+"---清算日期---"+deduct_stlm_date+",两个参数中存在空值，无法查询并下载");
			}
			
		} catch (Exception e) {
			logger.error("下载银行对账可疑数据xls报表出错：" + e.getMessage());
		}
	
	}
}
