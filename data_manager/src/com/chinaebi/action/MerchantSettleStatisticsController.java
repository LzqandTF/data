package com.chinaebi.action;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
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

import com.chinaebi.entity.MerchantSettleStatistics;
import com.chinaebi.entity.Merchants;
import com.chinaebi.service.MerchantSettleStatisticsService;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.utils.CreateExcelUtil;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 商户T+1统计表控制层
 * @author wufei
 *
 */
@SuppressWarnings("deprecation")
@Controller
public class MerchantSettleStatisticsController {
	private static final Logger logger = LoggerFactory.getLogger(MerchantSettleStatisticsController.class);
	
	// 网关对应商户交易表查询
	private static final String QUERY_MERCHATN_SETTLE_STATISTICS_LIST = "queryMerchantSettleStatisticsList.do";
	private static final String JSP_PAGE = "merBillingManager/queryMsettleStatistics";
	
	// 网关对应商户交易表查询(金额统计)
	private static final String QUERY_TOTAL_MONEY = "queryTotalMoney.do";
	
	private static final String QUERY_MERNAME_LIST_BY_MERNAME = "queryMerNameListByMerName.do";
	
	// 下载xsl报表
	private static final String QUERY_DATALST_FOR_EXCEL = "queryDataLstForExcel.do";
	
	// 汇总下载
	private static final String GET_DATA_LST_FOR_EXCEL = "getDataLstForExcel.do";
	
	private static final String GET_MSS_DATA_LST_BY_MER_FOR_ExCEL = "getMssDataLstByMerForExcel.do";
	
	@Autowired
	@Qualifier(value = "merchantSettleStatisticsService")
	private MerchantSettleStatisticsService merchantSettleStatisticsService;
	
	@Autowired
	@Qualifier(value = "merchantsService")
	private MerchantsService merchantsService;
	
	@RequestMapping(value = QUERY_MERCHATN_SETTLE_STATISTICS_LIST, method = RequestMethod.POST)
	public String queryPageMerchantSettleStatistics(ServletRequest request, Model model) {
		logger.info("进入网关对应商户交易表查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//分页显示条数
			String pageSize = request.getParameter("pageSize");
			Page<MerchantSettleStatistics> page = new Page<MerchantSettleStatistics>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String bank_id = request.getParameter("bank_id");
			String inst_id = request.getParameter("inst_id");
			
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String merCode = request.getParameter("merCode");
			String mer_name = request.getParameter("mer_name");
			
			String bankId = null;
			String inst_type = null;
			if (StringUtils.isNotBlank(bank_id)) {
				// 获取原始交易表名
				String[] bankInstList = bank_id.split(",");
				if (bankInstList != null && bankInstList.length > 0) {
					bankId = bankInstList[0];
					inst_type = bankInstList[1];
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
				model.addAttribute("merInfo", 0);
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bank_id", bankId);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(inst_id)) {
				map.put("inst_id", inst_id);
			}
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", startDate.replace("-", ""));
				model.addAttribute("merInfo", 1);
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("mer_code", merCode);
			}
			if (StringUtils.isNotBlank(mer_name)) {
				map.put("mer_name", mer_name);
			}
			
			model.addAttribute("pageDataLst", merchantSettleStatisticsService.queryPageMSSByMerCodeAndInstId(page, map));
			model.addAttribute("bankId", bank_id);
			model.addAttribute("instId", inst_id);
			model.addAttribute("mer_name", mer_name);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return JSP_PAGE;
	}
	
	@RequestMapping(value = QUERY_TOTAL_MONEY)
	@ResponseBody
	public MerchantSettleStatistics queryTotalMoney(ServletRequest request) {
		MerchantSettleStatistics merchantSettleStatistics = null;
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String bank_id = request.getParameter("bank_id");
			String inst_id = request.getParameter("inst_id");
			
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String merCode = request.getParameter("merCode");
			String mer_name = request.getParameter("mer_name");
			
			String bankId = null;
			String inst_type = null;
			if (StringUtils.isNotBlank(bank_id)) {
				// 获取原始交易表名
				String[] bankInstList = bank_id.split(",");
				if (bankInstList != null && bankInstList.length > 0) {
					bankId = bankInstList[0];
					inst_type = bankInstList[1];
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bank_id", bankId);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(inst_id)) {
				map.put("inst_id", inst_id);
			}
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", startDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("mer_code", merCode);
			}
			if (StringUtils.isNotBlank(mer_name)) {
				map.put("mer_name", mer_name);
			}
			
			merchantSettleStatistics = merchantSettleStatisticsService.queryTotalMoney(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return merchantSettleStatistics;
	}
	
	@RequestMapping(value = QUERY_MERNAME_LIST_BY_MERNAME)
	@ResponseBody
	public List<Merchants> queryMerNameListByMerName(ServletRequest request) {
		List<Merchants> list = null;
		try {
			String mer_name = request.getParameter("mer_name");
			if (StringUtils.isNotBlank(mer_name)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mer_name", mer_name);
				list = merchantsService.queryMerNameListByMerName(map);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	@RequestMapping(value = QUERY_DATALST_FOR_EXCEL)
	public void downExcel(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载网关对应商户交易表查询数据...");
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String bank_id = request.getParameter("bank_id");
			String inst_id = request.getParameter("inst_id");
			
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String merCode = request.getParameter("merCode");
			String mer_name = request.getParameter("mer_name");
			if (mer_name != null && !"null".equals(mer_name)) {
				mer_name = new String(mer_name.getBytes("iso-8859-1"),"UTF-8");
			}
			
			String bankId = null;
			String inst_type = null;
			if (StringUtils.isNotBlank(bank_id)) {
				// 获取原始交易表名
				String[] bankInstList = bank_id.split(",");
				if (bankInstList != null && bankInstList.length > 0) {
					bankId = bankInstList[0];
					inst_type = bankInstList[1];
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bank_id", bankId);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(inst_id)) {
				map.put("inst_id", inst_id);
			}
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", startDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("mer_code", merCode);
			}
			if (StringUtils.isNotBlank(mer_name)) {
				map.put("mer_name", mer_name);
			}
			
			//Excel表头
			String[] header = { "扣款渠道", "商户号", "商户简称", "支付金额",
				"退款金额", "银行手续费", "银行退回手续费", "银行划款额"
			};
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		 	
		 	Calendar calendar = Calendar.getInstance();
			String date = DateUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("网关表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    int row = 1;
		    String tradeAmount = "0";
		    String refundAmount = "0";
		    String zfFee = "0";
		    String refundZfFee = "0";
		    String settleAmount = "0";
			List<MerchantSettleStatistics> list = merchantSettleStatisticsService.queryDataLstForExcel(map);
			if (list != null && list.size() > 0) {
				String[] data = null;
			    for (MerchantSettleStatistics merchantSettleStatistics : list) {
			    	tradeAmount = PoundageCalculate.add(merchantSettleStatistics.getTrade_amount(),tradeAmount).toString() ;
			    	refundAmount = PoundageCalculate.add(merchantSettleStatistics.getRefund_amount(),refundAmount).toString();
			    	zfFee = PoundageCalculate.add(merchantSettleStatistics.getZf_fee(),zfFee).toString();
			    	refundZfFee = PoundageCalculate.add(merchantSettleStatistics.getRefund_zf_fee(),refundZfFee).toString();
			    	settleAmount = PoundageCalculate.sub(PoundageCalculate.add(tradeAmount, refundAmount), PoundageCalculate.add(zfFee, refundZfFee)).toString();
			    	data = new String[] {
			    			merchantSettleStatistics.getName_(),
			    			merchantSettleStatistics.getMer_code(),
			    			merchantSettleStatistics.getMer_abbreviation(),
			    			merchantSettleStatistics.getTrade_amount(),
			    			merchantSettleStatistics.getRefund_amount(),
			    			merchantSettleStatistics.getZf_fee(),
			    			merchantSettleStatistics.getRefund_zf_fee(),
			    			PoundageCalculate.sub(PoundageCalculate.add(merchantSettleStatistics.getTrade_amount(), merchantSettleStatistics.getRefund_amount()), PoundageCalculate.add(merchantSettleStatistics.getZf_fee(), merchantSettleStatistics.getRefund_zf_fee())).toString()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
			    }
			}
			//写入数据统计行
			String[] data = new String[]{"总计:"+(row-1)+"条记录","","",tradeAmount+"",refundAmount+"",zfFee+"",refundZfFee+"",settleAmount+""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"TRANHLOG_"+ date +".xls");
			if(flag){
				logger.info("网关表.xls  文件创建成功");
			}else{
				logger.info("网关表.xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = GET_DATA_LST_FOR_EXCEL)
	public void downDataLstExcel(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始按渠道下载汇总表...");
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String bank_id = request.getParameter("bank_id");
			String inst_id = request.getParameter("inst_id");
			
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String merCode = request.getParameter("merCode");
			String mer_name = request.getParameter("mer_name");
			if (mer_name != null && !"null".equals(mer_name)) {
				mer_name = new String(mer_name.getBytes("iso-8859-1"),"UTF-8");
			}
			
			String bankId = null;
			String inst_type = null;
			if (StringUtils.isNotBlank(bank_id)) {
				// 获取原始交易表名
				String[] bankInstList = bank_id.split(",");
				if (bankInstList != null && bankInstList.length > 0) {
					bankId = bankInstList[0];
					inst_type = bankInstList[1];
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bank_id", bankId);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(inst_id)) {
				map.put("inst_id", inst_id);
			}
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", startDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("mer_code", merCode);
			}
			if (StringUtils.isNotBlank(mer_name)) {
				map.put("mer_name", mer_name);
			}
			
			//Excel表头
			String[] header = {"网关表-按扣款渠道"};
			String[] headerTop = {"扣款渠道", "商户号", "商户简称", "支付金额", "支付汇总金额", "支付笔数"};
			
			Calendar calendar = Calendar.getInstance();
			String date = DateUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("网关表(按渠道)");
		    
		    // 单元格合并      
            // 四个参数分别是：起始行，起始列，结束行，结束列      
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));
            
            sheet.setColumnWidth(0, 6500);
            sheet.setColumnWidth(1, 6500);
            sheet.setColumnWidth(2, 8000);
            sheet.setColumnWidth(3, 5500);
            sheet.setColumnWidth(4, 5500);
		    
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
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    CreateExcelUtil.createTop(workbook,sheet,headerTop, cellStyle);
		   
		    Map<String, List<MerchantSettleStatistics>> mapList = merchantSettleStatisticsService.queryDataLstByInstIdForExcel(map);
			if (mapList != null && mapList.keySet().size() > 0) {
				String[] data = null;
				int row = 2; // 已经定义了两行抬头内容
			    int row_size = 0; // 记录每个list大小
				String totalAmount = "0";
				int totalCount = 0;
				for (String key : mapList.keySet()) {
					List<MerchantSettleStatistics> list_data = mapList.get(key);
					row_size = list_data.size();
					String tradeAmount = "0";
					int tradeCount = 0;
					for (MerchantSettleStatistics merchantSettleStatistics : list_data) {
						tradeAmount =  PoundageCalculate.add(merchantSettleStatistics.getTrade_amount(),tradeAmount);
//						tradeCount +=  merchantSettleStatistics.getTrade_count() - merchantSettleStatistics.getRefund_count();
						tradeCount += merchantSettleStatistics.getTrade_gc_count();
						data = new String[] {
								"",
								merchantSettleStatistics.getMer_code(),
								merchantSettleStatistics.getMer_abbreviation(),
								String.format("%.2f", Double.valueOf(merchantSettleStatistics.getTrade_amount())),
								"",
								""
						};
						CreateExcelUtil.output(cellStyle,sheet,row,data);
						data = null;
						row++;
					}
					
					totalAmount = PoundageCalculate.add(totalAmount,tradeAmount);
					totalCount += tradeCount;
					
					HSSFRow hssfRow = sheet.getRow(row-row_size);
					//赋值渠道名称
					hssfRow.getCell(0).setCellValue(key);
					sheet.addMergedRegion(new Region(row-row_size, (short) 0, row - 1, (short) 0));
					//赋值支付汇总金额
					hssfRow.getCell(4).setCellValue(String.format("%.2f", Double.valueOf(tradeAmount)));
					sheet.addMergedRegion(new Region(row-row_size, (short) 4, row - 1, (short) 4));
//					//赋值支付支付笔数
					hssfRow.getCell(5).setCellValue(tradeCount);
					sheet.addMergedRegion(new Region(row-row_size, (short) 5, row - 1, (short) 5));
				}
				
				//写入数据统计行
				data = new String[]{"总计:" + (row-2) + "条记录", "", "", "", String.format("%.2f", Double.valueOf(totalAmount))+"", totalCount+""};
				CreateExcelUtil.output(cellStyle,sheet,row,data);
				
				boolean flag = CreateExcelUtil.createExcel(response, workbook,"TRANHLOG_bank_"+ date +".xls");
				if(flag){
					logger.info("TRANHLOG_bank_" + date +".xls  文件创建成功");
				}else{
					logger.info("TRANHLOG_bank_" + date +".xls   文件创建失败");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = GET_MSS_DATA_LST_BY_MER_FOR_ExCEL)
	public void queryMssDataLstByMerForExcel(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载按商户下载汇总表...");
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String bank_id = request.getParameter("bank_id");
			String inst_id = request.getParameter("inst_id");
			
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String merCode = request.getParameter("merCode");
			String mer_name = request.getParameter("mer_name");
			if (mer_name != null && !"null".equals(mer_name)) {
				mer_name = new String(mer_name.getBytes("iso-8859-1"),"UTF-8");
			}
			
			String bankId = null;
			String inst_type = null;
			if (StringUtils.isNotBlank(bank_id)) {
				// 获取原始交易表名
				String[] bankInstList = bank_id.split(",");
				if (bankInstList != null && bankInstList.length > 0) {
					bankId = bankInstList[0];
					inst_type = bankInstList[1];
				}
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(bankId)) {
				map.put("bank_id", bankId);
			}
			if (StringUtils.isNotBlank(inst_type)) {
				map.put("inst_type", inst_type);
			}
			if (StringUtils.isNotBlank(inst_id)) {
				map.put("inst_id", inst_id);
			}
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", startDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate.replace("-", ""));
			}
			if (StringUtils.isNotBlank(merCode)) {
				map.put("mer_code", merCode);
			}
			if (StringUtils.isNotBlank(mer_name)) {
				map.put("mer_name", mer_name);
			}
			
			//Excel表头
			String[] header = {"网关表-按商户"};
			
			//Excel表头
			String[] headerTop = { "商户号", "商户简称", "扣款渠道", "支付金额","支付汇总金额", "商户手续费", "银行手续费", "支付笔数"
			};
			
			Calendar calendar = Calendar.getInstance();
			String date = DateUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("网关表(按商户)");
		    
		    // 单元格合并      
            // 四个参数分别是：起始行，起始列，结束行，结束列      
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 7));
            
            sheet.setColumnWidth(0, 6500);
            sheet.setColumnWidth(1, 6500);
            sheet.setColumnWidth(2, 8000);
            sheet.setColumnWidth(3, 5500);
            sheet.setColumnWidth(4, 5500);
            sheet.setColumnWidth(5, 5500);
            sheet.setColumnWidth(6, 5500);
            sheet.setColumnWidth(7, 5500);
		    
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
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    CreateExcelUtil.createTop(workbook,sheet,headerTop, cellStyle);
		    
		    int row = 2;
		  
		    Map<String, List<MerchantSettleStatistics>>  list = merchantSettleStatisticsService.queryDataLstByMerForExcel(map);
		    if (list != null && list.keySet().size() > 0) {
				int row_size = 0;//记录每个list大小
				
				String totalAmount = "0";
				String totalMerFee = "0";
				String totalZfFee = "0";
				int totalCount = 0;
				
				for (String key : list.keySet()) {
					List<MerchantSettleStatistics> list_data = list.get(key);
					row_size = list_data.size();
					String[] data = null;
					String merAbbreviation = null;
					
					String tradeAmount = "0";
					String merFee = "0";
					String zfFee = "0";
					int tradeCount = 0;
					for (MerchantSettleStatistics merchantSettleStatistics : list_data) {
						merAbbreviation = merchantSettleStatistics.getMer_abbreviation();

						tradeAmount =  PoundageCalculate.add(merchantSettleStatistics.getTrade_amount(),tradeAmount);
						merFee = PoundageCalculate.add(merchantSettleStatistics.getMer_fee(),merFee);
						zfFee = PoundageCalculate.add(merchantSettleStatistics.getZf_fee(),zfFee);
//						tradeCount +=  merchantSettleStatistics.getTrade_count() - merchantSettleStatistics.getRefund_count();
						tradeCount +=  merchantSettleStatistics.getTrade_gc_count();
						data = new String[] {
								"",
								"",
				    			merchantSettleStatistics.getName_(),
				    			String.format("%.2f", Double.valueOf(merchantSettleStatistics.getTrade_amount())),
				    			"",
				    			"",
				    			"",
				    			""
						};
						CreateExcelUtil.output(cellStyle,sheet,row,data);
						data = null;
						row++;
					}
					
					totalAmount =  PoundageCalculate.add(tradeAmount,totalAmount);
					totalMerFee = PoundageCalculate.add(merFee,totalMerFee);
					totalZfFee = PoundageCalculate.add(zfFee,totalZfFee);
					totalCount += tradeCount;
					
					HSSFRow hssfRow = sheet.getRow(row-row_size);
					//赋值渠道名称
					hssfRow.getCell(0).setCellValue(key);
					sheet.addMergedRegion(new Region(row-row_size, (short) 0, row - 1, (short) 0));
					
					//赋值合并商户简称
					hssfRow.getCell(1).setCellValue(merAbbreviation);
					sheet.addMergedRegion(new Region(row-row_size, (short) 1, row - 1, (short) 1));
					
					
//					//赋值合并支付汇总金额
					hssfRow.getCell(4).setCellValue(String.format("%.2f", Double.valueOf(tradeAmount)));
					sheet.addMergedRegion(new Region(row-row_size, (short) 4, row - 1, (short) 4));
					
//					//赋值合并商户手续费
					hssfRow.getCell(5).setCellValue(String.format("%.2f", Double.valueOf(merFee)));
					sheet.addMergedRegion(new Region(row-row_size, (short) 5, row - 1, (short) 5));
					
//					//赋值合并银行手续费
					hssfRow.getCell(6).setCellValue(String.format("%.2f", Double.valueOf(zfFee)));
					sheet.addMergedRegion(new Region(row-row_size, (short) 6, row - 1, (short) 6));
//					//赋值合并支付笔数
					hssfRow.getCell(7).setCellValue(tradeCount);
					sheet.addMergedRegion(new Region(row-row_size, (short) 7, row - 1, (short) 7));
				}
				//写入数据统计行
				String[] data = new String[]{"总计:"+ (row -2) +"条记录", "", "", "", String.format("%.2f", Double.valueOf(totalAmount))+"", String.format("%.2f", Double.valueOf(totalMerFee))+"", String.format("%.2f", Double.valueOf(totalZfFee))+"", totalCount+""};
				CreateExcelUtil.output(cellStyle,sheet,row,data);
				
		    }
		    
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"TRANHLOG_mer_"+ date +".xls");
			if(flag){
				logger.info("TRANHLOG_bank_" + date +".xls  文件创建成功");
			}else{
				logger.info("TRANHLOG_bank_" + date +".xls   文件创建失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
