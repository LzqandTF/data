package com.chinaebi.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.chinaebi.entity.ManualRec;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.entity.Merchants;
import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.service.ManualRecService;
import com.chinaebi.service.MerFundStanceService;
import com.chinaebi.service.MerchantsBalanceService;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.utils.CreateExcelUtil;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 手工调账请求、审核
 * @author wufei
 *
 */
@Controller
public class ManualRecController {
	private static Logger logger = LoggerFactory.getLogger(ManualRecController.class);
	
	//根据商户号获取商户信息
	private static final String QUERY_MERINFO_BY_MERCODE = "getMerInfoByMerCode.do";
	
	//手工调账请求
	private static final String ADD_MANUAL_REC = "addManualRec.do";
	
	//手工调账审核  分页查询
	private static final String QUERY_PAGE_DATA_LST = "queryNeedApprovalDataLst.do";
	private static final String JSP_PAGE = "merBillingManager/handleAccountApproval";
	
	//手工调账审核成功
	private static final String UPDATE_DATA_BY_ID = "updateDataById.do";
	
	//手工调账审核失败
	private static final String APPROVAL_DATA_BY_ID = "approvalDataToFail.do";
	
	//手工调账查询
	private static final String QUERY_ALL = "queryManualRecDataLst.do";
	private static final String QUERY_HANDLE_ACCOUNT = "merBillingManager/handleAccountQuery";
	
	//手工调账查询  下载Excel
	private static final String DOWN_EXCEL_MANUALREC_DATALST = "downExcelManualRecDataLst.do";
	
	@Autowired
	@Qualifier(value = "manualRecService")
	private ManualRecService manualRecService;
	
	@Autowired
	@Qualifier(value = "merchantsService")
	private MerchantsService merchantsService;
	
	@Autowired
	@Qualifier(value = "merchantsBalanceService")
	private MerchantsBalanceService merchantsBalanceService;
	
	@Autowired
	@Qualifier(value = "merFundStanceService")
	private MerFundStanceService merFundStanceService;
	
	/**
	 * 手工调账请求   根据商户号获取商户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = QUERY_MERINFO_BY_MERCODE)
	@ResponseBody
	public Merchants queryDataLstByMerCode(ServletRequest request) {
		logger.info("进入手工调账请求，根据商户号获取商户信息查询...");
		Merchants merchants = null;
		try {
			String merCode = request.getParameter("merCode");
			merchants = manualRecService.queryMerInfoByMerCode(merCode);
		} catch (Exception e) {
			logger.error("根据商户号查询商户信息出错：" + e.getMessage());
		}
		return merchants;
	}
	
	/**
	 * 手工调账申请，向数据表中添加数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = ADD_MANUAL_REC, method = RequestMethod.POST)
	@ResponseBody
	public int updateByMerCode(ServletRequest request) {
		logger.info("进入手工调账申请...");
		int effectNum = 0;
		try {
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			String mer_code = request.getParameter("mer_code");//商户号
			String mer_abbreviation = request.getParameter("mer_abbreviation");
			String mer_balance = request.getParameter("mer_balance");//商户余额
			String addorsub = request.getParameter("addorsub");//操作1：增加2：减少
			String handler_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//操作时间
			String rec_amount = request.getParameter("rec_amount");//调整金额
			String requestUser = request.getParameter("requestUser");//调账申请操作员
			String request_desc = request.getParameter("request_desc");//申请调账原因
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(id)) {
				map.put("id", id);
			}
			if (StringUtils.isNotBlank(mer_code)) {
				map.put("mer_code", mer_code);
			}
			if (StringUtils.isNotBlank(mer_abbreviation)) {
				map.put("mer_abbreviation", mer_abbreviation);
			}
			if (StringUtils.isNotBlank(mer_balance)) {
				map.put("mer_balance", mer_balance);
			}
			if (StringUtils.isNotBlank(addorsub)) {
				map.put("addorsub", Integer.valueOf(addorsub));
			}
			if (StringUtils.isNotBlank(handler_time)) {
				map.put("handler_time", handler_time);
			}
			if (StringUtils.isNotBlank(rec_amount)) {
				map.put("rec_amount", String.format("%.2f", Double.valueOf(rec_amount)));
			}
			if (StringUtils.isNotBlank(requestUser)) {
				map.put("send_user_name", requestUser);
			}
			if (StringUtils.isNotBlank(request_desc)) {
				map.put("request_desc", request_desc);
			}
			effectNum = manualRecService.addManualRec(map);
		} catch (Exception e) {
			logger.error("手工调账申请，向手工调账表中添加数据出错：" + e.getMessage());
		}
		return effectNum;
	}
	
	/**
	 * 手工调账审核  分页查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_PAGE_DATA_LST, method = RequestMethod.POST)
	public String queryPageDataLst(ServletRequest request, Model model) {
		logger.info("进入手工调账审核分页查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<ManualRec> page = new Page<ManualRec>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String mer_code = request.getParameter("mer_code");
			String addorsub = request.getParameter("addorsub");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String data_status = request.getParameter("data_status");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mer_code)) {
				map.put("mer_code", mer_code);
			}
			if (StringUtils.isNotBlank(addorsub)) {
				map.put("addorsub", Integer.valueOf(addorsub));
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(data_status)) {
				map.put("data_status", Integer.valueOf(data_status));
			}
			model.addAttribute("pageDataLst", manualRecService.queryPageDataLst(page, map));
			model.addAttribute("addorsub", addorsub);
			model.addAttribute("data_status", data_status);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return JSP_PAGE;
	}
	
	/**
	 * 手工调账审核  审核成功
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UPDATE_DATA_BY_ID, method = RequestMethod.POST)
	@ResponseBody
	public int updateDataById(ServletRequest request) {
		logger.info("开始将手工调账数据做审核成功操作...");
		int effectNum = 0;
		Merchants merchants = null;
		MerchantsBalance merBalance = null;
		int updateMerBalanceNum = 0;
		MerchantsBalance merchantsBalance = null;
		int merchantsBalanceResult = 0;
		MerFundStance merFundStance = null;
		int merFundStanceResult = 0;
		try {
			String manualRecInfo = request.getParameter("manualRecInfo");
			String[] str = manualRecInfo.split(",");
			String id = null;//主键ID
			String mer_code = null;//商户号
			String rec_amount = null;//调动余额
			String addorsub = null;
			String auditor_user_name = request.getParameter("auditor_user_name");//审核员
			String audit_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//审核时间
			
			merchants = new Merchants();
			merchantsBalance = new MerchantsBalance();
			merFundStance = new MerFundStance();
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(auditor_user_name)) {
				map.put("auditor_user_name", auditor_user_name);
			}
			if (StringUtils.isNotBlank(audit_time)) {
				map.put("audit_time", audit_time);
				map.put("data_status", 2);
			}
			for (int i = 0; i < str.length/4; i++) {
				id = str[i*4];
				mer_code = str[i*4+1];
				rec_amount = str[i*4+2];
				addorsub = str[i*4+3];
				map.put("id", id);
				//根据商户号获取商户的状态，若果商户的状态是关闭，则不作审核操作
				merchants = merchantsService.queryMerchantBasicInfoByMerCode(mer_code);
				if (merchants != null) {
					if (merchants.getMer_state() == 5) {
						effectNum = manualRecService.updateDataById(map);
						//手工调账审核成功影响商户余额变动、商户资金流水
						if (effectNum > 0) {
							logger.info("商户号为：" + mer_code + "的手工调账数据审核成功");
							merBalance = merchantsBalanceService.queryMerBalanceByMerCode(mer_code);
							if (merBalance != null) {
								merchantsBalance.setMer_code(mer_code);
								if ("1".equals(addorsub)) {//调增
									merchantsBalance.setMer_balance(PoundageCalculate.add(merBalance.getMer_balance(), rec_amount));
									merFundStance.setTrade_amount(Double.valueOf(rec_amount));//交易金额
									merFundStance.setChange_amount(Double.valueOf(rec_amount));//变动金额
									merFundStance.setAccount_amount(Double.valueOf(PoundageCalculate.add(merBalance.getMer_balance(), rec_amount)));//账户余额
								} else {//调减
									merchantsBalance.setMer_balance(PoundageCalculate.sub(merBalance.getMer_balance(), rec_amount));
									merFundStance.setTrade_amount(Double.valueOf(PoundageCalculate.sub("0", rec_amount)));//交易金额
									merFundStance.setChange_amount(Double.valueOf(PoundageCalculate.sub("0", rec_amount)));//变动金额
									merFundStance.setAccount_amount(Double.valueOf(PoundageCalculate.sub(merBalance.getMer_balance(), rec_amount)));//账户余额
								}
								updateMerBalanceNum = merchantsBalanceService.updateMerchantsBalance(merchantsBalance);
								if (updateMerBalanceNum > 0) {
									logger.info("修改商户余额成功");
								} else {
									logger.error("修改商户余额失败");
								}
							} else {
								logger.info("开始将该条审核成功的数据插入到商户余额表中...");
								//商户余额
								merchantsBalance.setMer_code(mer_code);
								merchantsBalance.setMer_category(merchants.getMer_category());
								if ("1".equals(addorsub)) {//调增
									merchantsBalance.setMer_balance(rec_amount);
									merFundStance.setTrade_amount(Double.valueOf(rec_amount));//交易金额
									merFundStance.setChange_amount(Double.valueOf(rec_amount));//变动金额
									merFundStance.setAccount_amount(Double.valueOf(PoundageCalculate.add("0", rec_amount)));//账户余额
								} else {
									merchantsBalance.setMer_balance(PoundageCalculate.sub("0", rec_amount));
									merFundStance.setTrade_amount(Double.valueOf(PoundageCalculate.sub("0", rec_amount)));//交易金额
									merFundStance.setChange_amount(Double.valueOf(PoundageCalculate.sub("0", rec_amount)));//变动金额
									merFundStance.setAccount_amount(Double.valueOf(PoundageCalculate.sub("0", rec_amount)));//账户余额
								}
								merchantsBalance.setMer_state(String.valueOf(merchants.getMer_state()));
								merchantsBalanceResult = merchantsBalanceService.addMerchantsBalance(merchantsBalance);
								if (merchantsBalanceResult > 0) {
									logger.info("插入商户余额表操作成功");
								} else {
									logger.error("插入商户余额表操作失败");
								}
							}
							
							//商户资金流水
							merFundStance.setId(UUID.randomUUID().toString().replaceAll("-", ""));//主键ID
							merFundStance.setMer_code(mer_code);//商户号
							merFundStance.setTrade_time(audit_time);//交易时间
							merFundStance.setMer_fee(Double.valueOf("0.00"));//商户手续费
							
							String date = audit_time.substring(0, 10).replaceAll("-", "");
							String time = audit_time.substring(11, audit_time.length()).replaceAll(":", "");
							merFundStance.setTrade_stance(date + time);//交易流水号
							merFundStance.setDerc_status(DataStatus.SHOU_GONG_TIAO_ZHANG);//简短说明
							merFundStance.setMer_state(merchants.getMer_state());//商户状态
							merFundStance.setMer_category(merchants.getMer_category());//商户类别
							merFundStance.setMer_name(merchants.getMer_name());//商户名称
							merFundStance.setInst_id(1);//扣款渠道
							merFundStance.setDeduct_stlm_date(audit_time.substring(0, 10));//清算日期
							merFundStance.setInst_type(0);//渠道类型inst_type
							merFundStance.setStance_time(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
							merFundStance.setBank_id(1);
							merFundStanceResult = merFundStanceService.insertMerFundStance(merFundStance);
							if (merFundStanceResult > 0) {
								logger.info("插入商户资金流水表操作成功");
							} else {
								logger.error("插入商户资金流水表操作失败");
							}
						} else {
							logger.error("手工调账做审核成功操作，修改数据状态失败");
						}
					} else {
						logger.info("该商户已关闭，不能进行审核操作");
					}
				}
			}
		} catch (Exception e) {
			logger.error("将手工调账数据审核成功出错：" + e.getMessage());
		}
		return effectNum;
	}
	
	
	/**
	 * 手工调账审核  审核失败
	 * @param request
	 * @return
	 */
	@RequestMapping(value = APPROVAL_DATA_BY_ID, method = RequestMethod.POST)
	@ResponseBody
	public int updateDataToFail(ServletRequest request) {
		logger.info("开始将手工调账数据做审核失败操作...");
		int effectNum = 0;
		Merchants merchants = null;
		try {
			String manualRecInfo = request.getParameter("manualRecInfo");
			String[] str = manualRecInfo.split(",");
			String id = null;//主键ID
			String mer_code = null;//商户号
			String auditor_user_name = request.getParameter("auditor_user_name");//审核员
			String audit_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//审核时间
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(auditor_user_name)) {
				map.put("auditor_user_name", auditor_user_name);
			}
			if (StringUtils.isNotBlank(audit_time)) {
				map.put("audit_time", audit_time);
				map.put("data_status", 3);
			}
			for (int i = 0; i < str.length/4; i++) {
				id = str[i*4];
				mer_code = str[i*4+1];
				map.put("id", id);
				//根据商户号获取商户的状态，若果商户的状态是关闭，则不作审核操作
				merchants = merchantsService.queryMerchantBasicInfoByMerCode(mer_code);
				if (merchants != null) {
					if (merchants.getMer_state() == 5) {
						effectNum = manualRecService.updateDataToFail(map);
						if (effectNum > 0) {
							logger.info("手工调账做审核失败操作成功");
						} else {
							logger.error("手工调账做审核失败操作失败");
						}
					} else {
						logger.info("该商户已关闭，不能做审核失败操作");
					}
				}
			}
		} catch (Exception e) {
			logger.error("将手工调账数据做审核失败操作出错：" + e.getMessage());
		}
		return effectNum;
	}
	
	/**
	 * 手工调账查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryManualRecDataLst(ServletRequest request, Model model) {
		logger.info("进入手工调账查询模块，开始分页查询手工调账数据...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<ManualRec> page = new Page<ManualRec>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String mer_code = request.getParameter("mer_code");
			String addorsub = request.getParameter("addorsub");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String data_status = request.getParameter("data_status");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mer_code)) {
				map.put("mer_code", mer_code);
			}
			if (StringUtils.isNotBlank(addorsub)) {
				map.put("addorsub", Integer.valueOf(addorsub));
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(data_status)) {
				map.put("data_status", Integer.valueOf(data_status));
			}
			model.addAttribute("pageDataLst", manualRecService.queryPageAllData(page, map));
			model.addAttribute("addorsub", addorsub);
			model.addAttribute("data_status", data_status);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			logger.error("手工调账查询模块，分页查询手工调账数据出现异常：" + e.getMessage());
		}
		return QUERY_HANDLE_ACCOUNT;
	}
	
	/**
	 * 手工调账查询  数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = DOWN_EXCEL_MANUALREC_DATALST)
	public void downExcelDataLst(HttpServletRequest request, HttpServletResponse response) {
		logger.info("手工调账查询模块，开始下载Excel表格...");
		try {
			String mer_code = request.getParameter("mer_code");
			String addorsub = request.getParameter("addorsub");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String data_status = request.getParameter("data_status");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mer_code)) {
				map.put("mer_code", mer_code);
			}
			if (StringUtils.isNotBlank(addorsub)) {
				map.put("addorsub", Integer.valueOf(addorsub));
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			if (StringUtils.isNotBlank(data_status)) {
				map.put("data_status", Integer.valueOf(data_status));
			}
			
			List<ManualRec> list = manualRecService.queryDataLst(map);
			String[] header = new String[] {
					"商户号", "商户简称", "调账请求操作员ID", "调账请求时间", "调账金额", "调账类型", "调账状态", 
					"调账审核操作员ID", "调账审核时间", "调账原因"
			};
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		    HSSFCellStyle cellStyle = workbook.createCellStyle();
		    String[] data = null;
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("手工调账数据列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    if (list != null && list.size() > 0) {
			    int row = 1;
			    for (ManualRec manualRec : list) {
					data = new String[] {
							manualRec.getMer_code(),
							manualRec.getMer_abbreviation(),
							manualRec.getSend_user_name(),
							manualRec.getHandler_time(),
							manualRec.getRec_amount(),
							manualRec.getAddorsub() == 1 ? "手工增加" : "手工减少",
							manualRec.getData_status() == 1 ? "调账提交" : manualRec.getData_status() == 2 ? "审核成功" : "审核失败",
							manualRec.getAuditor_user_name(),
							manualRec.getAudit_time(),
							manualRec.getRequest_desc()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
		    }
		    boolean flag = CreateExcelUtil.createExcel(response, workbook,"手工调账数据列表.xls");
			if(flag){
				logger.info("手工调账数据列表.xls 文件创建成功");
			}else{
				logger.info("手工调账数据列表.xls 文件创建失败");
			}
		} catch (Exception e) {
			logger.error("手工调账数据下载出现异常：" + e.getMessage());
		}
	}
}
