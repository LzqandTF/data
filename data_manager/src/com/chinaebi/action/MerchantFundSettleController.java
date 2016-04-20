package com.chinaebi.action;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.dao.ICommonDao;
import com.chinaebi.entity.ChannelDzCollect;
import com.chinaebi.entity.CustomFileEntity;
import com.chinaebi.entity.CustomObject;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.MerchantFundSettle;
import com.chinaebi.entity.MerchantSettleStatistics;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.ChannelDzCollectService;
import com.chinaebi.service.CustomFileService;
import com.chinaebi.service.CustomObjectService;
import com.chinaebi.service.ManualRecService;
import com.chinaebi.service.MerchantFundSettleService;
import com.chinaebi.service.MerchantSettleStatisticsService;
import com.chinaebi.utils.Common;
import com.chinaebi.utils.CreateExcelUtil;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.FlightUtil;
import com.chinaebi.utils.MerchantFundSettleBeanValueMap;
import com.chinaebi.utils.RegularExpressionUtil;
import com.chinaebi.utils.Ryt_trade_type;
import com.chinaebi.utils.StringPingJie;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 商户结算单操作类
 * @author wufei
 *
 */
@Controller
public class MerchantFundSettleController {
	private static final Logger logger = LoggerFactory.getLogger(MerchantFundSettleController.class);

	//商户结算单查询
	private static final String QUERY_MER_ADDTIONAL_DATA = "queryMerAdditionalData.do";
	private static final String JSP_PAGE = "merBillingManager/query_merBillingData";
	
	//商户结算打印
	private static final String GET_MER_PRINTER_PAGE = "getMerPinterPage.do";
	private static final String MER_PRINTER_PAGE = "merBillingManager/mer_printer";
	
	//下载结算单--电银账户
	private static final String DOWN_Mer_DATA_OF_DY_ACCOUNT_EXCEL = "downloadMerSettleDataListOfDyAccount.do";
	//下载结算单 --银行账户
	private static final String DOWN_Mer_DATA_OF_BANK_ACCOUNT_EXCEL = "downloadMerSettleDataListOfBankAccount.do";
	
	//商户结算单明细下载
	private static final String DOWN_MER_SETTLE_DETAIL_TO_EXCEL = "downMerSettleDetailToExcel.do";
	
	//商户结算单查询  明细查询
	private static final String QUERY_MER_SETTLE_DETAIL_LIST = "queryMerSettleDataDetailList.do";
	private static final String DETAIL_JSP_PAGE = "merBillingManager/merSettltDetailList";
	
	//商户结算单下载  格式.txt 电银账户
	private static final String DOWN_MER_DATA_OF_DY_ACCOUNT_TXT = "downMerDataListOfDyAcountTxt.do";
	
	//商户结算单下载  格式.txt 银行账户
	private static final String DOWN_MER_DATA_OF_BANK_ACCOUNT_TXT = "downMerDataListOfBankAccountTxt.do";
	
	// 银行账户划款结果查询
	private static final String QUERY_DF_RESULT_DATALST = "queryDfResultDataLst.do";
	private static final String DF_RESULT_JSP = "merBillingManager/queryDfResult";
	
	// 划款结果下载Excel表格
	private static final String DOWN_EXCEL_DF_RESULT = "downExcelDfResult.do";
	
	// 电银账户划款结果查询
	private static final String QUERY_DY_RESULT_DATALST = "queryDyDfResultDataLst.do";
	private static final String DY_DF_RESULT_JSP = "merBillingManager/queryDyDfResult";
	
	// 电银账户划款结果查询下载txt文件
	private static final String DOWN_TXT_DY_DF_RESULT = "downTxtDyDfResult.do";
	
	// 电银账户划款结果查询(结算单同步)
	private static final String SYNC_MERCHANT_FUND_SETTLE = "syncMerchantFundSettle.do";
	
	// 结算制表查询
	private static final String QUERY_NEED_ZB_DATA_LST = "queryNeedZBDataLst.do";
	private static final String JSZB_JSP = "/merBillingManager/selectSettlement";
	
	// 结算制表操作
	private static final String UPDATE_DATA_STATUS_TO_ZB = "updateDataStatusToZB.do";
	
	//结算制表确认(查询)
	private static final String QUERYMERFUNDSETINFOCONFIRM="/queryMerFundSetInfoConfirm.do";
	private static final String SELECTSETTLEMENTCONFIRM = "/merBillingManager/selectSettlementConfirm";
	
	// 结算确认操作
	private static final String UPDATEMERFUNDSETINFOTOJSQR = "/updateMerFundSetInfoToJsQr.do";
		
	@Autowired
	@Qualifier(value = "merchantFundSettleService")
	private MerchantFundSettleService merchantFundSettleService;
	
	@Autowired
	@Qualifier(value = "customFileService")
	private CustomFileService customFileService;
	
	@Autowired
	@Qualifier(value = "customObjectService")
	private CustomObjectService customObjectService;
	
	@Autowired
	@Qualifier(value = "channelDzCollectService")
	private ChannelDzCollectService channelDzCollectService;
	
	@Autowired
	@Qualifier(value="merchantSettleStatisticsService")
	private MerchantSettleStatisticsService settleStatisticsService;
	
	@Autowired
	@Qualifier(value = "dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value="commonDao")
	private ICommonDao<Integer> commonDao;
	
	@Autowired
	@Qualifier(value="manualRecService")
	private ManualRecService manualRecService;
	
	/**
	 * 商户结算单查询  分页获取商户结算单信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_MER_ADDTIONAL_DATA, method = RequestMethod.POST)
	public String queryMerAdditionalData(ServletRequest request, Model model) {
		logger.info("进入商户结算单查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			Page<MerchantFundSettle> page = new Page<MerchantFundSettle>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(30);
			String dateType = request.getParameter("dateType");//1：制表日期 2：确认日期 3：结算截止日期
			String startTime = request.getParameter("startTime");//起始日期
			String endTime = request.getParameter("endTime");//结束日期
			String settleObject = request.getParameter("settleObject");//结算对象
			String merType = request.getParameter("merType");//商户类型
			String merCode = request.getParameter("merCode");//商户号
			String merName = request.getParameter("merName");//商户简称
			String openAcountName = request.getParameter("openAcountName");//结算账户名称
			String merBatchNo = request.getParameter("merBatchNo");//商户批次号
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(settleObject)) {
				if ("1".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 1);
				} else if ("2".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 2);
				} else if ("3".equals(settleObject)) {
					map.put("bilManual", 2);
					map.put("settleWay", 1);
				} else {
					map.put("bilManual", 2);
					map.put("settleWay", 2);
				}
			}
			if (StringUtils.isNotBlank(merType)) {
				map.put("merType", merType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merName)) {
				map.put("merName", merName);
			}
			if (StringUtils.isNotBlank(openAcountName)) {
				map.put("openAcountName", openAcountName);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			model.addAttribute("merchantFundSettle", merchantFundSettleService.queryPageMerchantFundSettle(page, map));
			model.addAttribute("settle_object", settleObject);
			model.addAttribute("mer_type", merType);
			model.addAttribute("dateType", dateType);
			model.addAttribute("pageSize", pageSize);
			return JSP_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return JSP_PAGE;
		}
	}
	
	/**
	 * 商户结算单打印
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = GET_MER_PRINTER_PAGE)
	public String getMerPrinterPage(ServletRequest request, Model model) {
		logger.info("进入商户结算单打印页面...");
		try {
			String dateType = request.getParameter("dateType");//1：制表日期 2：确认日期 3:结算截止日期
			String startTime = request.getParameter("startTime");//起始日期
			String endTime = request.getParameter("endTime");//结束日期
			String settleObject = request.getParameter("settleObject");//结算对象
			String merType = request.getParameter("merType");//商户类型
			String merCode = request.getParameter("merCode");//商户号
			String merName = request.getParameter("merName");//商户简称
			String openAcountName = new String(request.getParameter("openAcountName").getBytes("iso-8859-1"), "utf-8");//结算账户名称
			String merBatchNo = request.getParameter("merBatchNo");//商户批次号
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(settleObject)) {
				if ("1".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 1);
				} else if ("2".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 2);
				} else if ("3".equals(settleObject)) {
					map.put("bilManual", 2);
					map.put("settleWay", 1);
				} else {
					map.put("bilManual", 2);
					map.put("settleWay", 2);
				}
			}
			if (StringUtils.isNotBlank(merType)) {
				map.put("merType", merType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merName)) {
				map.put("merName", merName);
			}
			if (StringUtils.isNotBlank(openAcountName)) {
				map.put("openAcountName", openAcountName);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			List<MerchantFundSettle> merchantFundSettleList = merchantFundSettleService.queryMerchantFundSettleList(map);
			double totalMoney = 0d;
			if (merchantFundSettleList != null && merchantFundSettleList.size() > 0) {
				int id = 1;
				for (MerchantFundSettle merchantFundSettle : merchantFundSettleList) {
					//统计总的应结算金额
					String settleMoney = merchantFundSettle.getSettle_amount();
					if(StringUtils.isNotBlank(settleMoney) && !"null".equals(settleMoney)){
						totalMoney = totalMoney + Double.valueOf(settleMoney);
					}
					
					merchantFundSettle.setId(id);
					id++;
				}
			}
			logger.info("总的结算金额是：" + String.format("%.2f", totalMoney));
			model.addAttribute("merchantFundSettleList", merchantFundSettleList);
			model.addAttribute("settle_object", settleObject);
			model.addAttribute("totalMoney", String.format("%.2f",totalMoney));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return MER_PRINTER_PAGE;
	}
	
	/**
	 * 商户结算单下载 -- 银行账户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = DOWN_Mer_DATA_OF_BANK_ACCOUNT_EXCEL)
	public void downMerDataOfBankAccountExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("开始下载Excel银行账户商户结算单列表...");
		try {
			String dateType = request.getParameter("dateType");//1：制表日期 2：确认日期 3：结算截止日期
			String startTime = request.getParameter("startTime");//起始日期
			String endTime = request.getParameter("endTime");//结束日期
			String settleObject = request.getParameter("settleObject");//结算对象
			String merType = request.getParameter("merType");//商户类型
			String merCode = request.getParameter("merCode");//商户号
			String merName = request.getParameter("merName");//商户简称
		    String openAcountName = new String(request.getParameter("openAcountName").getBytes("iso-8859-1"), "utf-8");//结算账户名称  
			String merBatchNo = request.getParameter("merBatchNo");//商户批次号
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(settleObject)) {
				if ("1".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 1);
				} else if ("2".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 2);
				} else if ("3".equals(settleObject)) {
					map.put("bilManual", 2);
					map.put("settleWay", 1);
				} else {
					map.put("bilManual", 2);
					map.put("settleWay", 2);
				}
			}
			if (StringUtils.isNotBlank(merType)) {
				map.put("merType", merType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merName)) {
				map.put("merName", merName);
			}
			if (StringUtils.isNotBlank(openAcountName)) {
				map.put("openAcountName", openAcountName);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			//根据条件获得了数据列表
			List<MerchantFundSettle> merchantFundSettleList = merchantFundSettleService.queryMerchantFundSettleList(map);
			//Excel表格的头部
			String[] header = new String[] {
				"商户号", "商户简称", "商户类别","是否资金分账", "结算类型", "结算初始日期", "结算截止日期", "制表日期", "确认日期", "支付金额", 
				"支付笔数", "退款金额", "退款笔数", "商户手续费", "退回商户手续费", "手工调增金额", "手工调增笔数", "手工调减金额", 
				"手工调减笔数", "结算银行名称", "结算账户名称", "结算账号", "应结算金额"
			};
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		    HSSFCellStyle cellStyle = workbook.createCellStyle();
		    
		    Calendar calendar = Calendar.getInstance();
		 	String date = DateUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		    
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("结算单(银行账户)");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
			if (merchantFundSettleList != null && merchantFundSettleList.size() > 0) {
				String[] data = null;
			    int row = 1;
				for (MerchantFundSettle merchantFundSettle : merchantFundSettleList) {
					data = new String[]{
						merchantFundSettle.getMer_code(),
						merchantFundSettle.getMer_name(),
						merchantFundSettle.getMer_type() == 0 ? "RYF商户" : merchantFundSettle.getMer_type() == 1 ? "VAS商户" : "POS商户",
						merchantFundSettle.getWhtherFz() == 0 ? "否" : "是",
						merchantFundSettle.getSettle_type() == 1 ? "全额结算" : "净额结算",
						String.valueOf(merchantFundSettle.getStart_date()),
						String.valueOf(merchantFundSettle.getEnd_date()),
						String.valueOf(merchantFundSettle.getCreate_tab_date()),
						String.valueOf(merchantFundSettle.getSettle_confirm_date()),
						merchantFundSettle.getTrade_amount(),
						String.valueOf(merchantFundSettle.getTrade_count()),
						merchantFundSettle.getRefund_amount(),
						String.valueOf(merchantFundSettle.getRefund_count()),
						merchantFundSettle.getMer_fee(),
						merchantFundSettle.getRefund_mer_fee(),
						merchantFundSettle.getRec_amount_add(),
						String.valueOf(merchantFundSettle.getRec_amount_add_count()),
						merchantFundSettle.getRec_amount_sub(),
						String.valueOf(merchantFundSettle.getRec_amount_sub_count()),
						merchantFundSettle.getOpen_bank_name(),
						merchantFundSettle.getOpen_acount_name(),
						merchantFundSettle.getOpen_account_code(),
						merchantFundSettle.getSettle_amount()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook, StringPingJie.getInstance().getStringPingJie(date, "_ryfsettle.xls"));
			if(flag){
				logger.info(StringPingJie.getInstance().getStringPingJie(date, "_ryfsettle.xls 文件创建成功"));
			}else{
				logger.info(StringPingJie.getInstance().getStringPingJie(date, "_ryfsettle.xls 文件创建失败"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 商户结算单下载 -- 电银账户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = DOWN_Mer_DATA_OF_DY_ACCOUNT_EXCEL)
	public void downMerDataOfDyAccountExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("开始下载Excel电银账户商户结算单列表...");
		try {
			String dateType = request.getParameter("dateType");//1：制表日期 2：确认日期 3：结算截止日期
			String startTime = request.getParameter("startTime");//起始日期
			String endTime = request.getParameter("endTime");//结束日期
			String settleObject = request.getParameter("settleObject");//结算对象
			String merType = request.getParameter("merType");//商户类型
			String merCode = request.getParameter("merCode");//商户号
			String merName = request.getParameter("merName");//商户简称
			String openAcountName = new String(request.getParameter("openAcountName").getBytes("iso-8859-1"), "utf-8");//结算账户名称
			String merBatchNo = request.getParameter("merBatchNo");//商户批次号
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(settleObject)) {
				if ("1".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 1);
				} else if ("2".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 2);
				} else if ("3".equals(settleObject)) {
					map.put("bilManual", 2);
					map.put("settleWay", 1);
				} else {
					map.put("bilManual", 2);
					map.put("settleWay", 2);
				}
			}
			if (StringUtils.isNotBlank(merType)) {
				map.put("merType", merType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merName)) {
				map.put("merName", merName);
			}
			if (StringUtils.isNotBlank(openAcountName)) {
				map.put("openAcountName", openAcountName);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			List<MerchantFundSettle> merchantFundSettleList = merchantFundSettleService.queryMerchantFundSettleList(map);
			
			List<CustomObject> customObjectList = customObjectService.queryCustomObjectByFileType(2);//查询需要生成结算单系统接口信息
			
			if(customObjectList == null || customObjectList.size() == 0){
				response.setCharacterEncoding("utf-8");
				response.getWriter().write("<script language='javascript'>alert('未配置下载结算单的系统接口,下载失败');window.history.go(-1);</script>");
			}else{
				// 创建新的Excel 工作簿
			    HSSFWorkbook workbook = new HSSFWorkbook();
			    // 设置单元格格式(文本)
			    HSSFCellStyle cellStyle = workbook.createCellStyle();
			    // 在Excel 工作簿中建一工作表
			    HSSFSheet sheet = workbook.createSheet("结算单(电银账户)");
			    Calendar calendar = Calendar.getInstance();//系统当前时间
				String date = DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd");
				
				Map<String,Object> map_parameter = new HashMap<String,Object>();
				for (CustomObject customObject : customObjectList) {
					map_parameter.put("object_id", customObject.getObject_id());
					List<CustomFileEntity> customFileConfigList = customFileService.queryCustomFileEntityConfigInfo(map_parameter);
					
					if(customFileConfigList !=null && customFileConfigList.size() > 0){
						String[] header = new String[customFileConfigList.size()];
						CustomFileEntity customEntity = null;
						for (int i=0;i< customFileConfigList.size();i++) {
							customEntity = customFileConfigList.get(i);
							header[i] = customEntity.getShow_attribute_name();
						}
						//创建抬头(标题)
					    CreateExcelUtil.createHeader(workbook,sheet,header);
					    
						if(merchantFundSettleList != null && merchantFundSettleList.size() > 0){
							int row = 1;
							String[] dataArr = null;
							for (MerchantFundSettle merchantFundSettle : merchantFundSettleList) {
								dataArr = new String[customFileConfigList.size()];
								CustomFileEntity customFileEntity = null;
								for (int i=0;i<customFileConfigList.size();i++) {
									customFileEntity = customFileConfigList.get(i);
									Object value = MerchantFundSettleBeanValueMap.getSettleFileValueForExcel(customFileEntity.getAttribute_column(), merchantFundSettle);
									String excelContent = customFileService.ruleHandleContent(customFileEntity, value==null?"":value.toString());
									dataArr[i] = excelContent;
								}
								CreateExcelUtil.output(cellStyle,sheet,row,dataArr);
								dataArr = null;
								row ++;
							}
						}
					}
					boolean flag = CreateExcelUtil.createExcel(response, workbook, StringPingJie.getInstance().getStringPingJie(date, "_zhsettle.xls"));
					if(flag){
						logger.info(StringPingJie.getInstance().getStringPingJie(date, "_zhsettle.xls  文件创建成功"));
					}else{
						logger.info(StringPingJie.getInstance().getStringPingJie(date, "_zhsettle.xls  文件创建失败"));
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	
	/**
	 * 商户结算单明细下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = DOWN_MER_SETTLE_DETAIL_TO_EXCEL)
	public void downExcelMerSettleDetailToExcel(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载商户结算单明细...");
		try {
			//下载条件
			String merInfo = request.getParameter("merInfo");
			String[] str = merInfo.split(",");
			String merCode = null;  //商户号
			String startDate = null;  //结算发起日期
			String endDate = null;  //结算截止日期
			String mer_name = null;
			String[] sheetArr = new String[str.length/5];
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    String[] data = null;
		    int len = str.length/5;
			for (int i = 0; i < len; i++) {
				merCode = str[i*5];  //商户号
				startDate = str[i*5+1];  //结算发起日期
				endDate = str[i*5+2];  //结算截止日期
				mer_name = str[i*5+3];//商户批次号
				mer_name = new String(mer_name.getBytes("iso-8859-1"), "utf-8");
				sheetArr[i] = mer_name;
				
				Map<String, Object> map = new HashMap<String, Object>();
				if (StringUtils.isNotBlank(merCode)) {
					map.put("merCode", merCode);
				}
				if (StringUtils.isNotBlank(startDate)) {
					map.put("startDate", startDate);
				}
				if (StringUtils.isNotBlank(endDate)) {
					map.put("endDate", endDate);
				}
				
				String[] header = new String[] {
					"商户号", "商户简称", "扣款渠道", "交易日期", "清算日期", "交易流水号", "商户订单号", "交易金额", "商户手续费", "交易类型", "入账用户id"
				};
				
				//在Excel 工作簿中建一工作表
			    HSSFSheet sheet = workbook.createSheet(mer_name + endDate); //"TradeHLOG_" + endDate
			    //创建抬头(标题)
			    CreateExcelUtil.createHeader(workbook,sheet,header);

				//获取对账总表中的交易数据
				List<ChannelDzCollect> list = channelDzCollectService.queryChannelDzCollectDataLst(map);
				if(list != null && list.size() > 0){
				    int row = 1;
				    for (ChannelDzCollect channelDzCollect : list) {
						data = new String[] {
							channelDzCollect.getReq_mer_code(),
							channelDzCollect.getDy_mer_name(),
							channelDzCollect.getInst_name(),
							channelDzCollect.getDeduct_sys_time(),
							channelDzCollect.getDeduct_stlm_date(),
							channelDzCollect.getDeduct_sys_stance(),
							channelDzCollect.getOid(),
							String.valueOf(channelDzCollect.getTrade_amount()),
							String.valueOf(channelDzCollect.getMer_fee()),
							Ryt_trade_type.getRytTradeName(Integer.valueOf(channelDzCollect.getTrade_type())),
							channelDzCollect.getIn_user_id()
						};
						CreateExcelUtil.output(cellStyle,sheet,row,data);
						data = null;
						row++;
					}
				}
			
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String currentDate = sdf.format(date);
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"TradeHLOG_" + currentDate + ".xls");
			if(flag){
				logger.info("商户结算单明细 TradeHLOG_" + currentDate + ".xls  文件创建成功");
			}else{
				logger.info("商户结算单明细 TradeHLOG_" + currentDate + ".xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("下载商户结算单明细列表出错：" + e.getMessage());
		}
	
	}
	
	/**
	 * 商户结算单明细查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERY_MER_SETTLE_DETAIL_LIST)
	public String queryMerSettleDataDetailList(ServletRequest request,Model model){
		logger.info("进入结算单明细查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			Page<ChannelDzCollect> page = new Page<ChannelDzCollect>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(8);
			
			String merCode = request.getParameter("merCode");//商户号
			String startDate = request.getParameter("startDate");//结算发起时间
			String endDate = request.getParameter("endDate");//结算结束时间
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate);
			}
			
			Page<ChannelDzCollect> pageLst = channelDzCollectService.queryPageChannelDzCollect(page, map);
			model.addAttribute("getDataResult", pageLst);
		} catch (Exception e) {
			logger.error("结算单明细查询出错：" + e.getMessage());
		}
		return DETAIL_JSP_PAGE;
	}
	
	
	/**
	 * 商户结算单下载
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = DOWN_MER_DATA_OF_DY_ACCOUNT_TXT)
	public void downMerDataOfDyAccountTxt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("开始下载Txt电银账户商户结算单列表...");
		
		BufferedWriter buff = null;  
        PrintWriter outSTr = null;
		try {
			
            String dateType = request.getParameter("dateType");//1：制表日期 2：确认日期 3：结算截止日期
			String startTime = request.getParameter("startTime");//起始日期
			String endTime = request.getParameter("endTime");//结束日期
			String settleObject = request.getParameter("settleObject");//结算对象
			String merType = request.getParameter("merType");//商户类型
			String merCode = request.getParameter("merCode");//商户号
			String merName = request.getParameter("merName");//商户简称
			String openAcountName = new String(request.getParameter("openAcountName").getBytes("iso-8859-1"), "utf-8");//结算账户名称
			String merBatchNo = request.getParameter("merBatchNo");//商户批次号
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(settleObject)) {
				if ("1".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 1);
				} else if ("2".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 2);
				} else if ("3".equals(settleObject)) {
					map.put("bilManual", 2);
					map.put("settleWay", 1);
				} else {
					map.put("bilManual", 2);
					map.put("settleWay", 2);
				}
			}
			if (StringUtils.isNotBlank(merType)) {
				map.put("merType", merType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merName)) {
				map.put("merName", merName);
			}
			if (StringUtils.isNotBlank(openAcountName)) {
				map.put("openAcountName", openAcountName);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			List<MerchantFundSettle> merchantFundSettleList = merchantFundSettleService.queryMerchantFundSettleList(map);
			
			List<CustomObject> customObjectList = customObjectService.queryCustomObjectByFileType(2);//查询需要生成结算单系统接口信息
			Map<String,Object> map_parameter = new HashMap<String,Object>();
			
			if(customObjectList == null || customObjectList.size() == 0){
				response.setCharacterEncoding("utf-8");
				response.getWriter().write("<script language='javascript'>alert('未配置下载结算单的系统接口,下载失败');window.history.go(-1);</script>");
			}else{
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain");
				
				Calendar calendar = Calendar.getInstance();//系统当前时间
	    		String date = DateUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				
				outSTr = response.getWriter();
	            buff = new BufferedWriter(outSTr);
				for (CustomObject customObject : customObjectList) {
					
					response.addHeader("Content-Disposition", "attachment;filename="+date+customObject.getDz_file_name()+".txt");
					
					map_parameter.put("object_id", customObject.getObject_id());
					List<CustomFileEntity> customFileConfigList = customFileService.queryCustomFileEntityConfigInfo(map_parameter);
					if(customFileConfigList !=null && customFileConfigList.size() > 0){
						for (CustomFileEntity customFileEntity : customFileConfigList) {
							int hz = RegularExpressionUtil.statisticalChineseNumber(customFileEntity.getShow_attribute_name());
							int column_length = customFileEntity.getColumn_length();
							int x = column_length - hz;
							buff.write(String.format("%-"+x+"s", customFileEntity.getShow_attribute_name()) + "|");
						}
						buff.newLine();
						
						if(merchantFundSettleList != null && merchantFundSettleList.size() > 0){
							for (MerchantFundSettle merchantFundSettle : merchantFundSettleList) {
								int currentColumn = 0;
									for (CustomFileEntity customFileEntity : customFileConfigList) {
										Object value = MerchantFundSettleBeanValueMap.getSettleFileValueForTxt(customFileEntity.getAttribute_column(), merchantFundSettle, customFileEntity.getColumn_length()+"");
										String txtContent = customFileService.ruleHandleContent(customFileEntity, value==null?"":value.toString());
										currentColumn++;
										if(currentColumn == customFileConfigList.size()){
											if("openAcountName".equals(customFileEntity.getAttribute_column()) || "settle_mer_name".equals(customFileEntity.getAttribute_column()) || "openBankName".equals(customFileEntity.getAttribute_column())){
												buff.write(txtContent);
											}else{
												buff.write(String.format("%-"+customFileEntity.getColumn_length()+"s", txtContent));
											}
										}else{
											if("openAcountName".equals(customFileEntity.getAttribute_column()) || "settle_mer_name".equals(customFileEntity.getAttribute_column()) || "openBankName".equals(customFileEntity.getAttribute_column())){
												buff.write(txtContent + "|");
											}else{
												buff.write(String.format("%-"+customFileEntity.getColumn_length()+"s", txtContent)+ "|");
											}
										}	
									}
									buff.newLine();
							}
						}
					}
				}
	            buff.flush(); 
	            buff.close();
			}
			
		} catch (Exception e) {
			logger.error("下载商户结算单列表出错：" + e.getMessage());
		} finally {  
            try {  
            	if(buff != null){
            		 buff.close(); 
            	}
                if(outSTr != null){
                	outSTr.close(); 
                }
            } catch (Exception e) {  
                logger.error(e.getMessage()); 
            }  
        }  
	}
	
	/**
	 * 商户结算单下载--针对银行账户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = DOWN_MER_DATA_OF_BANK_ACCOUNT_TXT)
	public void downMerDataOfBankAccountTxt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("开始下载Txt银行账户商户结算单列表...");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		response.addHeader("Content-Disposition", "attachment;filename=MerDataList.txt");
		BufferedOutputStream buff = null;  
        StringBuffer write = new StringBuffer();
        ServletOutputStream outSTr = null;
        String tab = "	";
        String enter = "\r\n";
        MerchantFundSettle merchantFundSettle;
		try {
			outSTr = response.getOutputStream();
            buff = new BufferedOutputStream(outSTr);
            
            String dateType = request.getParameter("dateType");//1：制表日期 2：确认日期 3：结算截止日期
			String startTime = request.getParameter("startTime");//起始日期
			String endTime = request.getParameter("endTime");//结束日期
			String settleObject = request.getParameter("settleObject");//结算对象
			String merType = request.getParameter("merType");//商户类型
			String merCode = request.getParameter("merCode");//商户号
			String merName = request.getParameter("merName");//商户简称
			String openAcountName = new String(request.getParameter("openAcountName").getBytes("iso-8859-1"), "utf-8");//结算账户名称
			String merBatchNo = request.getParameter("merBatchNo");//商户批次号
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(settleObject)) {
				if ("1".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 1);
				} else if ("2".equals(settleObject)) {
					map.put("bilManual", 1);
					map.put("settleWay", 2);
				} else if ("3".equals(settleObject)) {
					map.put("bilManual", 2);
					map.put("settleWay", 1);
				} else {
					map.put("bilManual", 2);
					map.put("settleWay", 2);
				}
			}
			if (StringUtils.isNotBlank(merType)) {
				map.put("merType", merType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merName)) {
				map.put("merName", merName);
			}
			if (StringUtils.isNotBlank(openAcountName)) {
				map.put("openAcountName", openAcountName);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			List<MerchantFundSettle> merchantFundSettleList = merchantFundSettleService.queryMerchantFundSettleList(map);
			
			for (int i = 0; i < merchantFundSettleList.size(); i++) {
				merchantFundSettle = (MerchantFundSettle) merchantFundSettleList.get(i);
                write.append(merchantFundSettle.getMer_code());
                write.append(tab);
                write.append(merchantFundSettle.getMer_name());
                write.append(tab);
                write.append(merchantFundSettle.getMer_type() == 0 ? "RYF商户" : merchantFundSettle.getMer_type() == 1 ? "VAS商户" : "POS商户");
                write.append(tab);
                write.append(merchantFundSettle.getStart_date());
                write.append(tab);
                write.append(merchantFundSettle.getEnd_date());
                write.append(tab);
                write.append(merchantFundSettle.getCreate_tab_date());
                write.append(tab);
                write.append(merchantFundSettle.getOpen_acount_name());
                write.append(tab);
                write.append(merchantFundSettle.getOpen_account_code());
                write.append(tab);
                write.append(merchantFundSettle.getTrade_amount());
                write.append(tab);
                write.append(merchantFundSettle.getTrade_count());
                write.append(tab);
                write.append(merchantFundSettle.getRefund_amount());
                write.append(tab);
                write.append(merchantFundSettle.getRefund_count());
                write.append(tab);
                write.append(merchantFundSettle.getMer_fee());
                write.append(tab);
                write.append(merchantFundSettle.getRefund_mer_fee());
                write.append(tab);
                write.append(merchantFundSettle.getRec_amount_add());
                write.append(tab);
                write.append(merchantFundSettle.getRec_amount_add_count());
                write.append(tab);
                write.append(merchantFundSettle.getRec_amount_sub());
                write.append(tab);
                write.append(merchantFundSettle.getRec_amount_sub_count());
                write.append(tab);
                write.append(merchantFundSettle.getSettle_amount());
                write.append(tab);
                write.append(merchantFundSettle.getSettle_confirm_date());
                write.append(tab);
                write.append(merchantFundSettle.getSyn_result() == 1 ? "划款成功" : merchantFundSettle.getSyn_result() == 2 ? "划款失败" : "划款中");
                write.append(tab);
                write.append(merchantFundSettle.getError_msg());
                write.append(enter);
            }  
            buff.write(write.toString().getBytes("UTF-8"));
            buff.flush(); 
            buff.close();
		} catch (Exception e) {
			logger.error("下载商户结算单列表出错：" + e.getMessage());
		} finally {  
            try {  
                buff.close();  
                outSTr.close();  
            } catch (Exception e) {  
                e.printStackTrace(); 
            }  
        }  
	}
	
	public static String delNull(String str) {  
        String returnStr="";  
        if (StringUtils.isNotBlank(str)) {  
            returnStr=str;        
        }   
        return returnStr;  
    } 
	
	
	@RequestMapping(value = QUERY_DF_RESULT_DATALST)
	public String queryPageDfResult(ServletRequest request, Model model) {
		logger.info("进入银行账户划款结果查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			Page<MerchantFundSettle> page = new Page<MerchantFundSettle>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String dateType = request.getParameter("dateType");// 1：结算截止日期 2:结算确认日期
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String merCode = request.getParameter("merCode");
			String merBatchNo = request.getParameter("merBatchNo");
			String dfResult = request.getParameter("dfResult");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			if (StringUtils.isNotBlank(dfResult)) {
				map.put("dfResult", dfResult);
			}
			
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				map.put("startTime", startTime.replace("-", ""));
				map.put("endTime", endTime.replace("-", ""));
				model.addAttribute("merchantFundSettle", merchantFundSettleService.queryPageDfResult(page, map));
			}
			model.addAttribute("dateType", dateType);
			model.addAttribute("df_result", dfResult);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) { 
			logger.error(e.getMessage());
		}
		return DF_RESULT_JSP;
	}
	
	@RequestMapping(value = DOWN_EXCEL_DF_RESULT)
	public void downExcelDfResult(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载银行账户划款结果...");
		try {
			String dateType = request.getParameter("dateType");// 1：结算截止日期 2:结算确认日期
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String merCode = request.getParameter("merCode");
			String merBatchNo = request.getParameter("merBatchNo");
			String dfResult = request.getParameter("dfResult");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			if (StringUtils.isNotBlank(dfResult)) {
				map.put("dfResult", dfResult);
			}
			
			List<MerchantFundSettle> list = merchantFundSettleService.queryDfResultDataLst(map);
			
			String[] header = new String[]{
					"商户批次号", "系统批次号", "商户号", "商户简称", "结算截止日期", "结算确认日期", "结算银行支行名称", 
					"结算账号名称", "结算账号", "应结算金额", "划款结果", "失败原因"
			};
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		 	
		 	Calendar calendar = Calendar.getInstance();
		 	String date = DateUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
		 	
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("划款结果-银行账户");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    int row = 1;
		    double settleAmount = 0.0d;
			if(list != null && list.size() > 0){
				String[] data = null;
				for (MerchantFundSettle merchantFundSettle : list) {
					data = new String[] {
							merchantFundSettle.getMer_batch_no(),
							merchantFundSettle.getSys_batch_no(),
							merchantFundSettle.getMer_code(),
							merchantFundSettle.getMer_name(),
							String.valueOf(merchantFundSettle.getEnd_date()),
							String.valueOf(merchantFundSettle.getSettle_confirm_date()),
							merchantFundSettle.getOpen_bank_name(),
							merchantFundSettle.getOpen_acount_name(),
							merchantFundSettle.getOpen_account_code(),
							merchantFundSettle.getSettle_amount(),
							merchantFundSettle.getSyn_result() == 1 ? "划款成功" : merchantFundSettle.getSyn_result() == 2 || 
							merchantFundSettle.getSyn_result() == 4 || merchantFundSettle.getSyn_result() == 5 || merchantFundSettle.getSyn_result() == 6 ? "划款失败" : 
								merchantFundSettle.getSyn_result() == 3 ? "划款中" : "",
							merchantFundSettle.getError_msg()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					if(StringUtils.isNotBlank(merchantFundSettle.getSettle_amount())){
						settleAmount += Double.valueOf(merchantFundSettle.getSettle_amount());
					}
					row++;
				}
			}
			
			//写入数据统计行
			String[] data = new String[]{"总计:"+(row-1)+"条记录","","","","","","","","",FlightUtil.dfFormat("0.00", settleAmount)+"","","",""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,StringPingJie.getInstance().getStringPingJie("划款结果_", date, ".xls"));
			if(flag){
				logger.info(StringPingJie.getInstance().getStringPingJie("划款结果_", date, ".xls 文件创建成功"));
			}else{
				logger.info(StringPingJie.getInstance().getStringPingJie("划款结果_", date, ".xls 文件创建失败"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = QUERY_DY_RESULT_DATALST)
	public String queryPageDyDfResult(ServletRequest request, Model model) {
		logger.info("进入电银账户划款结果查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			Page<MerchantFundSettle> page = new Page<MerchantFundSettle>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String dateType = request.getParameter("dateType");// 1：结算截止日期 2:结算确认日期
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String merCode = request.getParameter("merCode");
			String merBatchNo = request.getParameter("merBatchNo");
			String syn_result = request.getParameter("syn_result");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dateType)) {
				map.put("dateType", dateType);
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			if (StringUtils.isNotBlank(syn_result)) {
				map.put("syn_result", syn_result);
			}
			
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				map.put("startTime", startTime.replace("-", ""));
				map.put("endTime", endTime.replace("-", ""));
				model.addAttribute("merchantFundSettle", merchantFundSettleService.queryPageDyDfResult(page, map));
			}
			model.addAttribute("dateType", dateType);
			model.addAttribute("syn_result", syn_result);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) { 
			logger.error(e.getMessage());
		}
		return DY_DF_RESULT_JSP;
	}
	
	/**
	 * 划款结果查询-电银账户(下载.txt文件)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = DOWN_TXT_DY_DF_RESULT)
	public void downTxtDyDfResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("开始下载Txt电银账户划款结果...");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		String filename = new String("划款结果-电银账户报表.txt".replaceAll(" ", "").getBytes("UTF-8"), "ISO8859-1");
		response.addHeader("Content-Disposition", "attachment;filename="+ filename);

		BufferedOutputStream buff = null;
		ServletOutputStream outSTr = null;
		try {
			outSTr = response.getOutputStream();
			buff = new BufferedOutputStream(outSTr);

			String mer_code = request.getParameter("mer_code");
			String mer_batch_no = request.getParameter("mer_batch_no");
			String start_date = request.getParameter("start_date");
			String end_date = request.getParameter("end_date");
			String settle_amount = request.getParameter("settle_amount");
			String whtherFz = request.getParameter("whtherFz");
			String settle_type = request.getParameter("settle_type").trim();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("startDate", start_date);
			map.put("endDate", end_date);
			map.put("merCode", mer_code);

			List<ChannelDzCollect> list = channelDzCollectService.queryChannelDzCollectDataLst(map);
			int size = list.size();
			StringBuffer detailSb = new StringBuffer();
			detailSb.append(mer_code);
			detailSb.append("\n");
			detailSb.append(mer_batch_no);
			detailSb.append("\n");
			detailSb.append(start_date);
			detailSb.append("\n");
			detailSb.append(end_date);
			detailSb.append("\n");
			if (list != null && size > 0) {
				detailSb.append(size);
			} else {
				detailSb.append(0);
			}
			detailSb.append("\n");
			
			double settleAmount = 0d;
			if(StringUtils.isNotBlank(settle_amount) && !"null".equals(settle_amount)){
				settleAmount = Double.valueOf(settle_amount);
			}
			detailSb.append(String.format("%.2f", settleAmount));
			detailSb.append("\n");
			if ("0".equals(whtherFz)) {
				detailSb.append("N");
			} else {
				detailSb.append("Y");
			}
			detailSb.append("\n");
			detailSb.append("入账用户ID|");
			detailSb.append("交易流水号|");
			detailSb.append("商户订单号|");
			detailSb.append("交易日期|");
			detailSb.append("交易金额|");
			detailSb.append("交易类型|");
			detailSb.append("商户手续费");
//			detailSb.append("商户手续费|");
//			detailSb.append("应结算金额");
			detailSb.append("\n");
			if (list != null && size > 0) {
				int i = 0;
				for (ChannelDzCollect channelDzCollect : list) {
					String inUserId = channelDzCollect.getIn_user_id();
					if (inUserId != null && !"null".equals(inUserId)) {
						detailSb.append(inUserId);
						detailSb.append("|");
					} else {
						detailSb.append("|");
					}
					detailSb.append(channelDzCollect.getDeduct_sys_stance());
					detailSb.append("|");
					String oid = channelDzCollect.getOid();
					if (oid != null && !"null".equals(oid)) {
						detailSb.append(oid);
						detailSb.append("|");
					} else {
						detailSb.append("|");
					}
					detailSb.append(channelDzCollect.getDeduct_sys_time().substring(0, 8));
					detailSb.append("|");
					detailSb.append(String.format("%.2f", channelDzCollect.getTrade_amount()));
					detailSb.append("|");
					detailSb.append(Ryt_trade_type.getRytTradeName(Integer.valueOf(channelDzCollect.getTrade_type())));
					detailSb.append("|");
					if(StringUtils.equals(settle_type, "1")){//全额
						detailSb.append("0.00");
//						detailSb.append("|");
//						detailSb.append(String.format("%.2f", channelDzCollect.getTrade_amount()));
					}else{
						detailSb.append(String.format("%.2f", channelDzCollect.getMer_fee()));
//						detailSb.append("|");
//						detailSb.append(String.format("%.2f", channelDzCollect.getTrade_amount() - channelDzCollect.getMer_fee()));
					}
					
					i++;
					if (i != size)
						detailSb.append("\n");
				}
			}
			
			String zhxtTxtKey = Common.getProperties("zhxtTxtKey");
			if (StringUtils.isNotBlank(zhxtTxtKey)) {
				buff.write(Common.toMd5String(detailSb.toString() + zhxtTxtKey).toUpperCase().getBytes());
				buff.write("\n".getBytes());
			}else{
				throw new Exception("application.properties 配置属性(zhxtTxtKey)不能为空");
			}
			buff.write(detailSb.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error("下载电银账户划款结果出现异常：" + e.getMessage());
		} finally {
			try {
				if(buff != null){
					buff.flush();
					buff.close();
				}
				if(outSTr != null)
					outSTr.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 结算制表(查询)
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_NEED_ZB_DATA_LST, method = RequestMethod.POST)
	public String queryMerchantFundSettleInfo(ServletRequest request,Model model){
		logger.info("进入结算制表查询页面...");
		try {
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<MerchantFundSettle> page = new Page<MerchantFundSettle>();
			if(StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if(StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else 
				page.setPageSize(30);
			
			// 结算截止日期
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String merType = request.getParameter("merType");//商户类型
			String merBatchNo = request.getParameter("merBatchNo");//商户批次号
			String sysBatchNo = request.getParameter("sysBatchNo");//系统批次号
			String merCode = request.getParameter("merCode");//商户号
			
			Map<String,Object> map = new HashMap<String,Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(merType)) {
				map.put("merType", merType);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			if (StringUtils.isNotBlank(sysBatchNo)) {
				map.put("sysBatchNo", StringUtils.leftPad(sysBatchNo,12,"0"));
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			
			Page<MerchantFundSettle> pageList = merchantFundSettleService.queryPageNeedZBDataLst(page, map);
			model.addAttribute("pageList",pageList);
			model.addAttribute("merType", merType);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("结算制表查询数据出现异常：" + e.getMessage());
		}
		return JSZB_JSP;
	}
	
	/**
	 * 结算制表操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UPDATE_DATA_STATUS_TO_ZB, method = RequestMethod.POST)
	@ResponseBody
	public int updateDataStatusToZB(ServletRequest request) {
		logger.info("开始进行结算制表操作...");
		int effectNum = 0;
		try {
			String idList = request.getParameter("idList");
			String[] str = idList.split(",");
			StringBuilder sb = new StringBuilder();
			sb.append("id in (");
			for (int i = 0; i < str.length; i++) {
				sb.append(str[i]+",");
			}
			
			String ids = sb.substring(0, sb.length()-1).toString() + ")";
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("id", ids);
			map.put("create_tab_date", DateUtil.getSimpleDateFormat("yyyyMMdd").format(DateUtil.getNowDate()));
			
			effectNum = merchantFundSettleService.updateDataStatusToZB(map);
			if (effectNum > 0) {
				//TODO 结算制表后，修改商户原始交易数据结算状态、T+1数据表数据结算状态、商户手工调账表数据结算状态为已结算
				List<MerchantFundSettle> list = merchantFundSettleService.queryDataListByIds(map);
				if (list != null && list.size() > 0) {
					InstInfo instInfo = null;
					int result = 0;
					for (MerchantFundSettle merchantFundSettle : list) {
						map.put("mer_code", merchantFundSettle.getMer_code());
						map.put("lastSettleDate", merchantFundSettle.getStart_date());//上次结算日期
						map.put("deduct_stlm_date", merchantFundSettle.getEnd_date());//本次结算截止日期
						
						logger.info("当前结算商户数据：商户号--"+merchantFundSettle.getMer_code()+",上次结算日期--"+merchantFundSettle.getStart_date()+",本次结算截止日期--"+merchantFundSettle.getEnd_date());
						
						List<MerchantSettleStatistics> settleStatistics = settleStatisticsService.querySettleMerchantInfo(map);
						for (MerchantSettleStatistics merchantSettleStatistics : settleStatistics) {
							logger.info("根据相关数据查询出对应交易渠道ID为"+merchantSettleStatistics.getInst_id());
							instInfo = dataManagerInit.getInstInfoById(merchantSettleStatistics.getInst_id(),merchantSettleStatistics.getInst_type());//从内存中读取扣款渠道信息记录
							logger.info("从内存中获取相应渠道名称为："+instInfo.getName());
							String original_data_tableName = dataManagerInit.getBankInstByBankId(instInfo.getBank_id()).getOriginal_data_tableName();
							if(instInfo.getInst_type() == 1){
								if(original_data_tableName.contains(",")){
									map.put("originalDataTableName",original_data_tableName.split(",")[0]);
								}else{
									map.put("originalDataTableName",original_data_tableName);
								}
							}else{
								map.put("originalDataTableName",original_data_tableName);
							}
							map.put("inst_type", instInfo.getInst_type());
							map.put("whether_js", 1);
							
							try{
								//修改原始交易数据结算状态为已结算
								result = commonDao.updateOriginalDataJsStatus("Original_Data.updateOriginalDataJsStatus", map);
								logger.info("以商户号"+merchantFundSettle.getMer_code()+",清算日期大于"+merchantFundSettle.getStart_date()+"且小于"+merchantFundSettle.getEnd_date()+"为条件,修改原始交易数据结算状态为已结算成功,修改条数为"+result);
							}catch(Exception e){
								logger.error("修改原始交易数据结算状态为已结算操作失败，抛出异常:"+e.getMessage());
							}
							
							try{
								//修改T+1表数据结算状态为已结算
								result = settleStatisticsService.updateMerchantSettleStatistics(map);
								logger.info("以商户号"+merchantFundSettle.getMer_code()+",清算日期大于"+merchantFundSettle.getStart_date()+"且小于"+merchantFundSettle.getEnd_date()+"为条件,修改T+1表数据结算状态为已结算成功,修改条数为"+result);
							}catch(Exception e){
								logger.error("修改T+1表数据结算状态为已结算操作失败，抛出异常:"+e.getMessage());
							}
							
							try{
								//修改手工调账表数据为已结算
								result = manualRecService.updateMerManualRecJsStatus(map);
								logger.info("以商户号"+merchantFundSettle.getMer_code()+"为条件,修改手工调账表数据为已结算成功,修改条数为"+result);
							}catch(Exception e){
								logger.error("修改手工调账表数据为已结算操作失败，抛出异常:"+e.getMessage());
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
	
	
	/**
	 * 结算制表确认(查询)
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERYMERFUNDSETINFOCONFIRM)
	public String queryMerchantFundSettleInfoConfirm(ServletRequest request, Model model){
		logger.info("进入结算制表确认查询页面...");
		try {
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<MerchantFundSettle> page = new Page<MerchantFundSettle>();
			if(StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if(StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else 
				page.setPageSize(30);
			
			// 结算截止日期
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String merType = request.getParameter("merType");//商户类型
			String settleWay = request.getParameter("settlementAccountType");//结算账户类型
			String merBatchNo = request.getParameter("merBatchNo");//商户批次号
			String sysBatchNo = request.getParameter("sysBatchNo");//系统批次号
			String merCode = request.getParameter("merCode");//商户号
			String settleState = request.getParameter("settleState");//结算状态
			
			Map<String,Object> map = new HashMap<String,Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(merType)) {
				map.put("merType", merType);
			}
			if (StringUtils.isNotBlank(settleWay)) {
				map.put("settleWay", settleWay);
			}
			if (StringUtils.isNotBlank(merBatchNo)) {
				map.put("merBatchNo", merBatchNo);
			}
			if (StringUtils.isNotBlank(sysBatchNo)) {
				map.put("sysBatchNo", StringUtils.leftPad(sysBatchNo,12,"0"));
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("merCode", merCode);
			}
			if (StringUtils.isNotBlank(settleState)) {
				map.put("settleState", settleState);
			}
			
			Page<MerchantFundSettle> pageList = merchantFundSettleService.queryPageNeedQRDataLst(page, map);
			model.addAttribute("merfunsetconfirm",pageList);
			model.addAttribute("merType", merType);
			model.addAttribute("settlementAccountType", settleWay);
			model.addAttribute("settleState", settleState);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("结算制表确认查询数据出现异常：" + e.getMessage());
		}
		return SELECTSETTLEMENTCONFIRM;
	}
	
	/**
	 * 电银账户划款结果查询(结算单同步操作)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = SYNC_MERCHANT_FUND_SETTLE, method = RequestMethod.POST)
	@ResponseBody
	public synchronized String syncMerchantFundSettle(ServletRequest request, Model model) {
		logger.info("进入电银账户划款结果查询  结算单同步操作...");
		try {
			String idList = request.getParameter("idList");
			if(StringUtils.isNotBlank(idList)){
				String[] str = idList.split(",");
				StringBuffer sb = new StringBuffer();
				sb.append("id in (");
				for (int i = 0; i < str.length; i++) {
					sb.append(str[i]+",");
				}
				String ids = sb.substring(0, sb.length()-1).toString() + ")";
				
				List<MerchantFundSettle> list = null;
				int totalQrDataNum = str.length; // 同步总数据条数
				int qrSucessDataNum = 0; // 同步成功数据条数
				
				list = merchantFundSettleService.queryMerSettleDataOfNoQR(ids,2, 2);
				if(list != null && list.size() > 0){
					
					final String zh_merCode = Common.getProperties("zh_merCode");
					
					final String iface_flag = Common.getProperties("iface_flag");
					
					final String zhxt_settle_data_synchronized_url = Common.getProperties("zhxt_settle_data_synchronized_url");
					
					final String zhxtMd5Key = Common.getProperties("zhxtMd5Key");
					
					final int connectTimeOut = StringUtils.isBlank(Common.getProperties("connectTimeOut"))?6000:Integer.valueOf(Common.getProperties("connectTimeOut"));//默认链接超时时间
					
					final int readTimeOut = StringUtils.isBlank(Common.getProperties("readTimeOut"))?6000:Integer.valueOf(Common.getProperties("readTimeOut"));//默认读数据超时时间
					
					merchantFundSettleService.setDataSynSucessNum(0);
					
					ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 200,
							TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10),
							new ThreadPoolExecutor.CallerRunsPolicy());
					//同步计数器,构造时传入int参数,该参数就是计数器的初始值，每调用一次countDown()方法，
					//计数器减1,计数器大于0 时，await()方法会阻塞程序继续执行
					final CountDownLatch end = new CountDownLatch(list.size());
					
					for (final MerchantFundSettle merchantFundSettle : list) {
						Runnable run = new Runnable() {
							public void run() {
								try {
									if(merchantFundSettle.getBil_manual() == 2 && merchantFundSettle.getMer_type() == 2){//自动结算的线下商户
										if(StringUtils.isNotBlank(iface_flag)){
											if("1".equals(iface_flag)){//指定商户
												if(StringUtils.isNotBlank(zh_merCode)){
													if(merchantFundSettle.getMer_code().equals(zh_merCode)){
														//调用账户系统接口，同步数据
														merchantFundSettleService.updateMerchantFundSettleDataSynchronized(merchantFundSettle,zhxt_settle_data_synchronized_url,connectTimeOut,readTimeOut,zhxtMd5Key);
													} else {
														logger.info("该商户" + merchantFundSettle.getMer_code() + "与指定商户不匹配，不调用账户系统接口");
													}
												}else{
													logger.debug("结算确认指定商户号未配置，请核实！");
												}
											}else if("2".equals(iface_flag)){//全部商户
												//调用账户系统接口，同步数据
												merchantFundSettleService.updateMerchantFundSettleDataSynchronized(merchantFundSettle,zhxt_settle_data_synchronized_url,connectTimeOut,readTimeOut,zhxtMd5Key);
											}else{
												logger.debug("结算确认接口调用开关配置为"+iface_flag+"，请核实！");
											}
										}else{
											logger.debug("结算确认接口调用开关未配置，请核实！");
										}
									}
								}catch(Exception e){
									logger.error(e.getMessage());
								}finally {
									end.countDown();
								}
							}
						};
						executor.submit(run);
					}
					try {
						end.await(); // 等待end状态变为0，结束
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
					executor.shutdown();
					qrSucessDataNum = merchantFundSettleService.getDataSynSucessNum();
				}
				model.addAttribute("totalQrDataNum", totalQrDataNum);
				model.addAttribute("qrSucessDataNum", qrSucessDataNum);
				return (totalQrDataNum+";"+qrSucessDataNum );
			}
			return "0;0";
		} catch (Exception e) {
			logger.error("电银划款结果查询同步结算单数据出现异常：" + e.getMessage());
		}
		return "0;0";
	} 
	
	/**
	 * 结算确认操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UPDATEMERFUNDSETINFOTOJSQR, method = RequestMethod.POST)
	@ResponseBody
	public synchronized String updateMerchantFundSettleDataToJsQr(ServletRequest request,Model model){
		logger.info("开始进行制表确认操作...");
		try {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				String[] str = id.split(",");
				StringBuffer sb = new StringBuffer();
				sb.append("id in (");
				for (int i = 0; i < str.length; i++) {
					sb.append(str[i]+",");
				}
				String ids = sb.substring(0, sb.length()-1).toString() + ")";
				String settle_way = request.getParameter("settle_way");
				
				List<MerchantFundSettle> list = null;
				int totalQrDataNum = str.length;//数据总数
				int qrSucessDataNum = 0;//结算确认成功条数
				
				if("2".equals(settle_way)){//电银账户结算确认操作
					list = merchantFundSettleService.queryMerSettleDataOfNoQR(ids,1,2);//查询电银账户需结算确认数据
					if(list != null && list.size() > 0){
						
						final String zh_merCode = Common.getProperties("zh_merCode");
						
						final String iface_flag = Common.getProperties("iface_flag");
						
						final String zhxt_settle_data_synchronized_url = Common.getProperties("zhxt_settle_data_synchronized_url");
						
						final String zhxtMd5Key = Common.getProperties("zhxtMd5Key");
						
						final int connectTimeOut = StringUtils.isBlank(Common.getProperties("connectTimeOut"))?6000:Integer.valueOf(Common.getProperties("connectTimeOut"));//默认链接超时时间
						
						final int readTimeOut = StringUtils.isBlank(Common.getProperties("readTimeOut"))?6000:Integer.valueOf(Common.getProperties("readTimeOut"));//默认读数据超时时间
						
						merchantFundSettleService.setDataSynSucessNum(0);
						
						ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 200,
								TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10),
								new ThreadPoolExecutor.CallerRunsPolicy());
						//同步计数器,构造时传入int参数,该参数就是计数器的初始值，每调用一次countDown()方法，
						//计数器减1,计数器大于0 时，await()方法会阻塞程序继续执行
						final CountDownLatch end = new CountDownLatch(list.size());
						
						for (final MerchantFundSettle merchantFundSettle : list) {
							Runnable run = new Runnable() {
								public void run() {
									try {
										//修改结算单状态与相关商户余额及资金流水
										merchantFundSettleService.updateSettleDataStatus(merchantFundSettle);
										if(merchantFundSettle.getBil_manual() == 2 && merchantFundSettle.getMer_type() == 2){//自动结算的线下商户
											if(StringUtils.isNotBlank(iface_flag)){
												if("1".equals(iface_flag)){//指定商户
													if(StringUtils.isNotBlank(zh_merCode)){
														if(merchantFundSettle.getMer_code().equals(zh_merCode)){
															//调用账户系统接口，同步数据
															merchantFundSettleService.updateMerchantFundSettleDataSynchronized(merchantFundSettle,zhxt_settle_data_synchronized_url,connectTimeOut,readTimeOut,zhxtMd5Key);
														}else {
															logger.info("该商户" + merchantFundSettle.getMer_code() + "与指定商户不匹配，不调用账户系统接口");
														}
													}else{
														logger.debug("结算确认指定商户号未配置，请核实！");
													}
												}else if("2".equals(iface_flag)){//全部商户
													//调用账户系统接口，同步数据
													merchantFundSettleService.updateMerchantFundSettleDataSynchronized(merchantFundSettle,zhxt_settle_data_synchronized_url,connectTimeOut,readTimeOut,zhxtMd5Key);
												}else{
													logger.debug("结算确认接口调用开关配置为"+iface_flag+"，请核实！");
												}
											}else{
												logger.debug("结算确认接口调用开关未配置，请核实！");
											}
										}
									}catch(Exception e){
										logger.error(e.getMessage());
									}finally {
										end.countDown();
									}
								}
							};
							executor.submit(run);
						}
						try {
							end.await(); // 等待end状态变为0，结束
						} catch (InterruptedException e) {
							logger.error(e.getMessage());
						}
						executor.shutdown();
						qrSucessDataNum = merchantFundSettleService.getDataSynSucessNum();
					}
				} else if("1".equals(settle_way)){//银行账户结算确认操作
					list = merchantFundSettleService.queryMerSettleDataOfNoQR(ids,1,1);//银行账户数据结算确认
					if(list != null && list.size() > 0){
						String iface_flag = Common.getProperties("iface_flag");
						String merCode = Common.getProperties("ryf_merCode");
						for (MerchantFundSettle merchantFundSettle : list) {
							// 修改结算单状态为已确认
							merchantFundSettleService.updateSettleDataStatus(merchantFundSettle);

							if (merchantFundSettle.getBil_manual() == 2 && merchantFundSettle.getMer_type() == 2) {
								if (StringUtils.isNotBlank(iface_flag)) {
									if ("1".equals(iface_flag)) {
										if (StringUtils.isNotBlank(merCode) && merCode.equals(merchantFundSettle.getMer_code())) {
											logger.info("开始进行代付操作...");
											logger.info("application 配置指定代付商户号是："+ merCode);
											boolean flag = merchantFundSettleService.updateMerBilingAmtDf(merchantFundSettle);
											logger.info(merCode + "代付结果 :"+ flag);
											if (flag) {
												qrSucessDataNum++;
											}
										}
									} else if("2".equals(iface_flag)) {
										logger.info("开始进行代付操作，代付所有自动结算银行账户商户号...");
										logger.info("代付的商户号是："+ merchantFundSettle.getMer_code());
										boolean flag = merchantFundSettleService.updateMerBilingAmtDf(merchantFundSettle);
										logger.info(merCode + "实时代付结果 :" + flag);
										if (flag) {
											qrSucessDataNum++;
										}
									}else{
										logger.debug("结算确认接口调用开关配置为"+iface_flag+"，请核实！");
									}
								}else{
									logger.debug("结算确认接口调用开关未配置，请核实！");
								}
							} else {
								qrSucessDataNum++;
							}
						}
					}
				}else{
					logger.debug("结算账户类型参数错误，未找到相应账户类型，页面传参为"+settle_way);
				}
				model.addAttribute("totalQrDataNum", totalQrDataNum);
				model.addAttribute("qrSucessDataNum", qrSucessDataNum);
				return (totalQrDataNum+";"+qrSucessDataNum);
			}
			return "0;0";
		} catch (Exception e) {
			logger.error("制表确认操作出现异常：" + e.getMessage());
		}
		return "0;0";
	}
	
}
