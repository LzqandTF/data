package com.chinaebi.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.chinaebi.entity.Tmoney;
import com.chinaebi.service.TmoneyService;
import com.chinaebi.utils.CreateExcelUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 账户头寸调拨控制层
 * @author wufei
 */
@Controller
public class TmoneyController {
	private static Logger logger = LoggerFactory.getLogger(TmoneyController.class);
	
	//账户头寸调拨
	//银行账户查询
	private static final String QUERY_BANK_ACCOUNT = "queryBankAccount.do";
	private static final String BANK_ACCOUNT_PAGE = "merBillingManager/query_bankAccount";
	
	//电银账号查询
	private static final String QUERY_DY_ACCOUNT = "queryDyAccount.do";
	private static final String DY_ACCOUNT_PAGE = "merBillingManager/query_dyAccount";
	
	//银行账户数据下载
	private static final String DOWN_EXCEL_BANK_ACCOUNT_LST = "getBankAccountLst.do";
		
	//电银账户数据下载
	private static final String DOWN_EXCEL_DY_ACCOUNT_LST = "getDyAccountLst.do";
	
	@Autowired
	@Qualifier(value = "tmoneyService")
	private TmoneyService tmoneyService;
	
	/**
	 * 银行账户查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_BANK_ACCOUNT)
	public String queryBankAccount(ServletRequest request, Model model) {
		logger.info("账户头寸调拨  进入银行账户查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			Page<Tmoney> page = new Page<Tmoney>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				map.put("startTime", startTime.replace("-", ""));
				map.put("endTime", endTime.replace("-", ""));
				model.addAttribute("bankAccount", tmoneyService.queryPageBankAccountData(page, map));
			}
			model.addAttribute("pageSize", pageSize);
			return BANK_ACCOUNT_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return BANK_ACCOUNT_PAGE;
		}
	}
	
	
	/**
	 * 电银账户查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_DY_ACCOUNT)
	public String queryDyAccount(ServletRequest request, Model model) {
		logger.info("账户头寸调拨  进入电银账户查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			Page<Tmoney> page = new Page<Tmoney>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				map.put("startTime", startTime.replace("-", ""));
				map.put("endTime", endTime.replace("-", ""));
				model.addAttribute("dyAccount", tmoneyService.queryPageDyAccountData(page, map));
			}
			model.addAttribute("pageSize", pageSize);
			return DY_ACCOUNT_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return DY_ACCOUNT_PAGE;
		}
	}
	
	/**
	 * 银行账户数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = DOWN_EXCEL_BANK_ACCOUNT_LST)
	public void downExcelBankAccountLst(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载银行账户数据列表...");
		try {
			//Excel表头
			String[] header = {
				"结算银行名称", "总交易金额"
			};
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			} 
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			List<Tmoney> list = tmoneyService.queryBankAccountDataList(map);
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		    HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("银行账户数据列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    
			if (list != null && list.size() > 0) {
				String[] data = null;
			    int row = 1;
				for (Tmoney tmoney : list) {
					data = new String[] {
						tmoney.get_name(),
						tmoney.getTotal_money()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"银行账户数据列表.xls");
			if(flag){
				logger.info("头寸调拨银行账户数据列表.xls  文件创建成功");
			}else{
				logger.info("头寸调拨银行账户数据列表.xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("下载银行账户数据列表出错：" + e.getMessage());
		}
	}
	
	/**
	 * 电银账户下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = DOWN_EXCEL_DY_ACCOUNT_LST)
	public void downExcelDyAccountLst(HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始下载电银账户数据列表...");
		try {
			//Excel表头
			String[] header = new String[] {
				"账户名称","电银账户号","总交易金额"
			};
			
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			Map<String, Object> map = new HashMap<String, Object>();
			
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			List<Tmoney> list = tmoneyService.queryDyAccountDataList(map);
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		    HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("电银账户数据列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    
			if (list != null && list.size() > 0) {
				String[] data = null;
			    int row = 1;
				for (Tmoney tmoney : list) {
					data = new String[] {
						tmoney.getBil_accountname(),
						tmoney.getBil_account(),
						tmoney.getTotal_money()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"电银账户数据列表.xls");
			if(flag){
				logger.info("头寸调拨电银账户数据列表.xls  文件创建成功");
			}else{
				logger.info("头寸调拨电银账户数据列表.xls  文件创建失败");
			}
		} catch (Exception e) {
			logger.error("下载电银账户数据列表出错：" + e.getMessage());
		}
	}
}
