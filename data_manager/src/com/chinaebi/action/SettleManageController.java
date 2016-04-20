package com.chinaebi.action;

import java.util.ArrayList;
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
import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.ManualRec;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.entity.MerchantFundSettle;
import com.chinaebi.entity.MerchantSettleFail;
import com.chinaebi.entity.MerchantSettleStatistics;
import com.chinaebi.entity.Merchants;
import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.entity.SettleFailReason;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.ErrorDataLstService;
import com.chinaebi.service.ManualRecService;
import com.chinaebi.service.MerFundStanceService;
import com.chinaebi.service.MerchantFundSettleService;
import com.chinaebi.service.MerchantSettleFailService;
import com.chinaebi.service.MerchantSettleStatisticsService;
import com.chinaebi.service.MerchantsBalanceService;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.service.SettleFailReasonService;
import com.chinaebi.utils.CreateExcelUtil;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.FlightUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 结算发起控制类
 * @author shi.peng
 */
@Controller
public class SettleManageController {
	
	private static final Logger log = LoggerFactory.getLogger(SettleManageController.class);
	
	/**
	 * 结算发起模块
	 */
	//页面
	public static final String SETTLEMERINFODETAIL = "/merBillingManager/settleMerInfoDetail";
	public static final String SETTLESTART = "/merBillingManager/settleStart";
	public static final String SETTLENEXTSTEP = "merBillingManager/settleNextStep";
	
	//方法
	public static final String QUERYSETTLEMERINFODETAIL = "/querySettleMerInfoDetail.do";
	public static final String QUERYSETTLEMERCHANTINFO = "/querySettleMerchantInfo.do";
	public static final String BATCHSETTLEMERINFO = "/batchSettleMerInfo.do";
	
	/**
	 * 结算发起失败原因配置模块
	 */
	//页面
	public static final String SETTLEFAILREASON = "/sysConfig/settleFailReason";
	
	//方法
	public static final String QUERYSETTLEFAILREASON = "/querySettleFailReason.do";
	public static final String ADDSETTLEFAILREASON = "/addSettleFailReason.do";
	public static final String UPDATESETTLEFAILREASON = "/updateSettleFailReason.do";
	public static final String DELETESETTLEFAILREASON = "/deleteSettleFailReason.do";
	public static final String GETSETTLEFAILREASONLIST	= "/getSettleFailReason.do";
	
	/**
	 * 结算发起失败查询模块
	 */
	//页面 
	public static final String SETTLEFAILINFO = "/merBillingManager/settleStartFailSelect";
	
	//方法
	public static final String 	QUERYSETTLEFAILINFO = "/querySettleFailInfo.do";
	public static final String SETTLEFAILDOWNEXCEL = "/settleFailDownExcel.do";
	
	
	/**
	 * 结算制表确认模块
	 */
	public static final String UPLOADSETTLEDATATOFTP = "/uploadSettleDataToFtp.do";
	
	
	/**
	 * 商户资金流水查询模块
	 */
	public static final String MERFUNDSTANCESELECT = "/merBillingManager/merFundStanceSelect";
	
	//商户资金流水查询
	public static final String QUERYMERFUNDSTANCEPAGE = "/queryMerFundStancePage.do";
	
	//商户资金流水下载
	public static final String MERFUNDSTANCEDOWNLOADEXCEL = "/merFundStanceDownLoadExcel.do";
	
	private int total_result = 0;//结算发起总条数
	private int sucess_result = 0;//发起成功条数
	private int repeat_result = 0;//重复结算条数
	private int noSettleOrder_result = 0;//无可结算订单条数
	
	private static String sys_batch_no = "";//系统批次号
	
	@Autowired
	@Qualifier(value = "merchantsService")
	private MerchantsService merchantsService;
	
	@Autowired
	@Qualifier(value = "merchantSettleStatisticsService")
	private MerchantSettleStatisticsService merchantSettleStatisticsService;
	
	@Autowired
	@Qualifier(value = "merchantFundSettleService")
	private MerchantFundSettleService merchantFundSettleService;
	
	@Autowired
	@Qualifier(value = "merchantSettleFailService")
	private MerchantSettleFailService merchantSettleFailService;
	
	@Autowired
	@Qualifier(value = "settleFailReasonService")
	private SettleFailReasonService settleFailReasonService;
	
	@Autowired
	@Qualifier(value="commonDao")
	private ICommonDao<Integer> commonDao; 
	
	@Autowired
	@Qualifier(value="errorDataLstService")
	private ErrorDataLstService errorDataLstService;
	
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value="merFundStanceService")
	private MerFundStanceService merFundStanceService;
	
	@Autowired
	@Qualifier(value="manualRecService")
	private ManualRecService manualRecService;
	
	@Autowired
	@Qualifier(value="merchantsBalanceService")
	private MerchantsBalanceService merchantsBalanceService;
	
//	ResourceBundle res = ResourceBundle.getBundle("application");
//	String settle_start_date = res.getString("SETTLE_START_DATE");
	
	/**
	 * 结算发起查询功能模块
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value=QUERYSETTLEMERINFODETAIL,method=RequestMethod.POST)
	public String querySettleMerInfoDetail(HttpSession session,ServletRequest request,Model model) throws Exception{
		log.info("进入查询满足结算发起的商户列表页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		String mer_type = request.getParameter("mer_type");
		String mer_abbreviation = request.getParameter("mer_abbreviation_");
		String mer_code = request.getParameter("mer_code_");
		String deduct_stlm_date = request.getParameter("endTime"); 
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(deduct_stlm_date)){
			map.put("deduct_stlm_date", Integer.valueOf(deduct_stlm_date.replaceAll("-", "")));
		}
		if(StringUtils.isNotBlank(mer_abbreviation)){
			map.put("mer_abbreviation", mer_abbreviation);
		}
		if(StringUtils.isNotBlank(mer_code)){
			map.put("mer_code", mer_code);
		}
		if(StringUtils.isNotBlank(mer_type)){
			map.put("mer_type", mer_type);
		}
		
//		if(StringUtils.isNotBlank(settle_start_date)){
//			map.put("settle_start_date", settle_start_date);
//		}else{
//			map.put("settle_start_date", 0);
//		}
		
		Page<Merchants> page = new Page<Merchants>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		
		model.addAttribute("pageSettleMerInfo", merchantsService.queryPageSettleMerchantInfo(page, map));
		model.addAttribute("pageSize", pageSize);
		return SETTLEMERINFODETAIL;
	}
	
	/**
	 * 结算发起查询功能模块
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value=QUERYSETTLEMERCHANTINFO,method=RequestMethod.POST)
	public String settleStart(HttpSession session,ServletRequest request,Model model) {
		List<Merchants> list = null;
		try{
			String deduct_stlm_date = request.getParameter("endTime");
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(deduct_stlm_date)){
				map.put("deduct_stlm_date", Integer.valueOf(deduct_stlm_date.replaceAll("-", "")));
			}
			
//			if(StringUtils.isNotBlank(settle_start_date)){
//				map.put("settle_start_date", settle_start_date);
//			}else{
//				map.put("settle_start_date", 0);
//			}
			
			list = merchantsService.querySettleMerchantInfoCount(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		model.addAttribute("merchantList", list);
		
		//查询线上商户可结算商户
		return SETTLENEXTSTEP;
	}
	
	/**
	 * 针对某种商户类别，进行单个商户或者批量商户的结算操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value=BATCHSETTLEMERINFO,method=RequestMethod.POST)
	@ResponseBody
	public synchronized String batchSettleMerInfo(ServletRequest request){
		String result = "0;0;0;0;0";
		try{
			
			String settleInfo = request.getParameter("settleInfo");
			
			String deduct_stlm_date = request.getParameter("endTime");
			
			String mer_type = request.getParameter("mer_type");
			
			if(StringUtils.isNotBlank(mer_type)){//判断页面参数中是否包含商户类型，如果包括，说明按照商户类型进行批量结算操作，如果不是则按照商户批量结算
				String[] merTypes = mer_type.split(",");
				List<Merchants> list = new ArrayList<Merchants>();
				total_result = 0;//结算发起总条数
				sucess_result = 0;//发起成功条数
				repeat_result = 0;//重复结算条数
				noSettleOrder_result = 0;//无可结算订单条数
				for(int i=0;i<merTypes.length;i++){
					Map<String,Object> map = new HashMap<String,Object>();
					if(StringUtils.isNotBlank(deduct_stlm_date)){
						map.put("deduct_stlm_date", Integer.valueOf(deduct_stlm_date.replaceAll("-", "")));
					}
					if(StringUtils.isNotBlank(mer_type)){
						map.put("mer_type", merTypes[i]);
					}
//					if(StringUtils.isNotBlank(settle_start_date)){
//						map.put("settle_start_date", settle_start_date);
//					}else{
//						map.put("settle_start_date", 0);
//					}
					list = merchantsService.querySettleMerchantInfoList(map);//根据商户类型 查询可结算商户信息
					if(list != null && list.size() > 0){
						total_result = total_result + list.size();
						for (Merchants merchants : list) {
							result = batchStartSettleMerInfo(merchants,deduct_stlm_date);//针对商户类型进行结算
						}
					}
				}
				
			}else{
				result = batchStartSettleMerInfo(settleInfo,deduct_stlm_date);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 分页查询结算发起失败原因配置信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERYSETTLEFAILREASON,method=RequestMethod.POST)
	public String querySettleFailReason(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		String reason_id = request.getParameter("reasonId");
		String reason = request.getParameter("reasonName");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(reason_id)){
			map.put("reason_id", Integer.valueOf(reason_id));
		}
		if(StringUtils.isNotBlank(reason)){
			map.put("reason", reason);
		}
		
		Page<SettleFailReason> page = new Page<SettleFailReason>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		model.addAttribute("pageSettleFailReason",settleFailReasonService.queryPageSettleFailReason(page, map));
		model.addAttribute("pageSize", pageSize);
		return SETTLEFAILREASON;
	}
	
	/**
	 * 新增结算发起失败原因配置信息
	 * @param settleFailReason
	 * @return
	 */
	@RequestMapping(value=ADDSETTLEFAILREASON,method=RequestMethod.POST)
	@ResponseBody
	public int addSettleFailReason(SettleFailReason settleFailReason){
		int result = settleFailReasonService.addSettleFailReason(settleFailReason);
		return result;
	}
	
	/**
	 * 修改结算发起失败原因配置信息
	 * @param settleFailReason
	 * @return
	 */
	@RequestMapping(value=UPDATESETTLEFAILREASON,method=RequestMethod.POST)
	@ResponseBody
	public int updateSettleFailReason(SettleFailReason settleFailReason){
		int result = settleFailReasonService.updateSettleFailReason(settleFailReason);
		return result;
	}
	
	/**
	 * 删除结算发起失败原因配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value=DELETESETTLEFAILREASON,method=RequestMethod.POST)
	@ResponseBody
	public int deleteSettleFailReason(ServletRequest request){
		String reason_id = request.getParameter("reason_id");
		int result = settleFailReasonService.deleteSettleFailReason(Integer.valueOf(reason_id));
		return result;
	}
	
	/**
	 * 获取所有结算发起失败原因配置信息
	 * @return
	 */
	@RequestMapping(value=GETSETTLEFAILREASONLIST,method=RequestMethod.POST)
	@ResponseBody
	public List<SettleFailReason> getSettleFailReason(){
		List<SettleFailReason> list = settleFailReasonService.getSettleFailReasonList();
		return list;
	}
	
	/**
	 * 查询结算发起失败记录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERYSETTLEFAILINFO,method=RequestMethod.POST)
	public String querySettleFailInfo(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		
		String reason_id = request.getParameter("reason");
		String mer_code = request.getParameter("mer_code");
		String mer_type = request.getParameter("mer_type");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(reason_id)){
			map.put("reason_id", Integer.valueOf(reason_id));
		}
		if(StringUtils.isNotBlank(startTime)){
			map.put("startTime", Integer.valueOf(startTime.replaceAll("-", "")));
		}
		if(StringUtils.isNotBlank(endTime)){
			map.put("endTime", Integer.valueOf(endTime.replaceAll("-", "")));
		}
		if(StringUtils.isNotBlank(start_time)){
			map.put("start_time", Integer.valueOf(start_time.replaceAll("-", "")));
		}
		if(StringUtils.isNotBlank(end_time)){
			map.put("end_time", Integer.valueOf(end_time.replaceAll("-", "")));
		}
		if(StringUtils.isNotBlank(mer_code)){
			map.put("mer_code", mer_code);
		}
		if(StringUtils.isNotBlank(mer_type)){
			map.put("mer_type", mer_type);
		}
		
		Page<MerchantSettleFail> page = new Page<MerchantSettleFail>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		model.addAttribute("pageMerchantSettleFailInfo",merchantSettleFailService.queryPageMerchantSettleFail(page, map));
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("reason_id", reason_id);
		model.addAttribute("mer_type", mer_type);
		return SETTLEFAILINFO;
	}
	
	/**
	 * 结算失败查询 数据下载
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = SETTLEFAILDOWNEXCEL)
	public void downExcelSettleFail(HttpServletRequest request,HttpServletResponse response) {
		log.info("开始下载结算失败查询数据...");
		try {
			//Excel表头
			String[] header = new String[] {
				"商户类型", "商户号", "商户简称", "结算截止日期", "结算发起日期", "失败原因"
			};

			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			
			String reason_id = request.getParameter("reason");
			String mer_code = request.getParameter("mer_code");
			String mer_type = request.getParameter("mer_type");

			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(reason_id)){
				map.put("reason_id", Integer.valueOf(reason_id));
			}
			if(StringUtils.isNotBlank(startTime)){
				map.put("startTime", Integer.valueOf(startTime.replaceAll("-", "")));
			}
			if(StringUtils.isNotBlank(endTime)){
				map.put("endTime", Integer.valueOf(endTime.replaceAll("-", "")));
			}
			if(StringUtils.isNotBlank(start_time)){
				map.put("start_time", Integer.valueOf(start_time.replaceAll("-", "")));
			}
			if(StringUtils.isNotBlank(end_time)){
				map.put("end_time", Integer.valueOf(end_time.replaceAll("-", "")));
			}
			if(StringUtils.isNotBlank(mer_code)){
				map.put("mer_code", mer_code);
			}
			if(StringUtils.isNotBlank(mer_type)){
				map.put("mer_type", mer_type);
			}
			
			List<MerchantSettleFail> list = merchantSettleFailService.queryMerchantSettleFailList(map);
			
			Calendar calendar = Calendar.getInstance();//系统当前时间
			String nowTime = DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd");
			StringBuffer nameBuffer = new StringBuffer("");
			nameBuffer.append("FailLOG_");
			nameBuffer.append(nowTime.replaceAll("-", ""));
			nameBuffer.append(".xls");
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("结算发起失败列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
			if (list != null && list.size() > 0) {
				String[] data = null;
			    int row = 1;
			    for (MerchantSettleFail merchantSettleFail : list) {
			    	data = new String[] {
						merchantSettleFail.getMer_type()==2?"线下商户":"线上商户",
						merchantSettleFail.getMer_code(),
						merchantSettleFail.getMer_name(),
						merchantSettleFail.getLast_settle_date()+"",
						merchantSettleFail.getSettle_start_date()+"",
						merchantSettleFail.getReason()
					};
			    	CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
				}
			}
			boolean flag = CreateExcelUtil.createExcel(response, workbook,nameBuffer.toString());
			if(flag){
				log.info(nameBuffer.toString()+" 文件创建成功");
			}else{
				log.info(nameBuffer.toString()+" 文件创建失败");
			}
		} catch (Exception e) {
			log.error("结算发起失败列表 下载xls报表出错：" + e.getMessage());
		}
	}
	
	/**
	 * 计算商户结算金额是否满足需求
	 * @param trade_amount 正交易金额
	 * @param refund_amount 反交易金额
	 * @param trade_fee 系统手续费    暂时不考虑系统手续费即交易手续费
	 * @param mer_fee 商户手续费
	 * @param mer_refund_fee 商户退款手续费
	 * @param compareNum 比较对象
	 * @param bil_way 商户结算类型 1：全额结算  2：净额结算
	 * @return
	 */
	public boolean checkTradeMoney(Double trade_amount,Double refund_amount,Double trade_fee,Double mer_fee,Double mer_refund_fee,Double compareNum,int bil_way){
		if(1 == bil_way){
			return PoundageCalculate.add(trade_amount, refund_amount).doubleValue() > compareNum;
//			return (trade_amount + refund_amount) > compareNum;
		}else if(2 == bil_way){
			return PoundageCalculate.sub(PoundageCalculate.add(trade_amount, refund_amount).doubleValue(), PoundageCalculate.sub(mer_fee, mer_refund_fee).doubleValue()).doubleValue() > compareNum;
//			return (trade_amount + refund_amount - mer_fee + mer_refund_fee) > compareNum;
		}else{
			return false;
		}
	}
	
	/**
	 * 针对某个商户进行结算操作
	 * @param merchant
	 * @param deduct_stlm_date
	 * @return
	 */
	public String batchStartSettleMerInfo(Merchants merchant,String deduct_stlm_date){
		int result = 7;//无可结算订单
		int this_settle_date = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(merchant != null && StringUtils.isNotBlank(deduct_stlm_date)){
				String mer_code = merchant.getMer_code();//商户号
				int lastSettleDate = merchant.getLastSettleDate();//上次结算日期
				int mer_category = merchant.getMer_category();//商户类别
				int bil_cycle = merchant.getBil_cycle();//结算周期
				String mer_abbreviation = merchant.getMer_abbreviation();//商户简称
				int bil_way = merchant.getBil_way();//结算方式
				String bil_smallamt = merchant.getBil_smallamt();//商户结算最低金额
				int endDate = merchant.getEndDate();//商户合同到期日期
				int bil_status = merchant.getBil_status();//商户结算状态
				String bil_bank = merchant.getBil_bank();//结算银行联行号
				String bil_bankname = merchant.getBank_branch();//结算银行名称
				String bil_accountname = merchant.getBil_accountname();//结算账户名称
				String bil_bankaccount = merchant.getBil_bankaccount();//结算银行账号
				int bil_object = merchant.getBil_object();//结算账户类型(结算对象)  1：银行账户/2：电银账户/3：账户系统-企业/4:账户系统-个人
				int bil_manual = merchant.getBil_manual();//手工结算状态 1:开通/2:关闭
				int whtherFz = merchant.getWhtherFz();//是否资金分账  0：否 /1:是
				String bill_account = merchant.getBil_account();//是否资金分账  0：否 /1:是
				if(StringUtils.isNotBlank(mer_code)){
					map.put("mer_code", mer_code);
				}
				if(lastSettleDate != 0 ){
					map.put("lastSettleDate", lastSettleDate);
				}
				map.put("mer_type", mer_category);
				if(bil_cycle  != 0 ){
					if(1 == bil_cycle){
						this_settle_date = Integer.valueOf(deduct_stlm_date.replaceAll("-", ""));
						map.put("deduct_stlm_date", this_settle_date);
					}else if(2 == bil_cycle){
						this_settle_date = Integer.valueOf(DateUtil.getThisWeekMonday(deduct_stlm_date).replaceAll("-", ""));
						map.put("deduct_stlm_date", this_settle_date);
					}else if(3 == bil_cycle){
						this_settle_date = Integer.valueOf(DateUtil.getMinMonthDate(deduct_stlm_date).replaceAll("-", ""));
						map.put("deduct_stlm_date", this_settle_date);
					}
					
					if(lastSettleDate >= this_settle_date){//判断此次结算截止日期自然日是否小于上次结算成功日期
						result = -1;//该商户已结算过
						repeat_result ++;
					}else{
						MerchantSettleStatistics merchantSettleStatistics = merchantSettleStatisticsService.querySettleMerchantInfoGroupByMerCode(map);
						
						if (merchantSettleStatistics != null) {
							
							map.put("merCode", map.get("mer_code"));
							map.put("startDate", map.get("lastSettleDate"));
							map.put("endDate", map.get("deduct_stlm_date"));
							int tradeNoDzCount = 0;
							//获取渠道ID列表
							List<MerchantSettleStatistics> instIdList = merchantSettleStatisticsService.queryInstIdList(map);
							StringBuffer sb_date = new StringBuffer("");
							for (MerchantSettleStatistics inst : instIdList) {
								InstInfo instInfo = dataManagerInit.getInstInfoById(inst.getInst_id(),inst.getInst_type());//从内存中读取扣款渠道信息记录
								if(instInfo != null){
									BankInst bankInst = dataManagerInit.getBankInstByBankId(instInfo.getBank_id());
									if(instInfo.getInst_type() == 1){
										if(bankInst.getOriginal_data_tableName().contains(",")){
											map.put("original_data_tableName", bankInst.getOriginal_data_tableName().split(",")[0]);
										}else{
											map.put("original_data_tableName", bankInst.getOriginal_data_tableName());
										}
										map.put("deduct_stlm_date", this_settle_date);
										map.put("lastSettleDate", lastSettleDate);
																						
									}else{
										map.put("original_data_tableName", bankInst.getOriginal_data_tableName());
										
										sb_date.append(String.valueOf(this_settle_date).substring(0, 4));
										sb_date.append("-");
										sb_date.append(String.valueOf(this_settle_date).substring(4, 6));
										sb_date.append("-");
										sb_date.append(String.valueOf(this_settle_date).substring(6, 8));
										map.put("deduct_stlm_date", DateUtil.getformatConversionStart(sb_date.toString()));
										
										sb_date.setLength(0);
										
										sb_date.append(String.valueOf(lastSettleDate).substring(0, 4));
										sb_date.append("-");
										sb_date.append(String.valueOf(lastSettleDate).substring(4, 6));
										sb_date.append("-");
										sb_date.append(String.valueOf(lastSettleDate).substring(6, 8));
										
										map.put("lastSettleDate", DateUtil.getformatConversionStart(sb_date.toString()));
										
										sb_date.setLength(0);
									}
									
									map.put("inst_type", instInfo.getInst_type());
									
									tradeNoDzCount += commonDao.queryTradeNoDzHandlerCount("Original_Data.queryTradeNoDzHandlerCount", map);
								}else{
									log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户未找到相关扣款渠道配置信息");
								}
							}
							if(tradeNoDzCount == 0){//判断商户是否存在交易未对账
								int errorNoHandleCount = errorDataLstService.queryErrorNoHandlerCount(map);
								if(errorNoHandleCount == 0){//判断商户是否有未处理差错数据
									if(bil_way != 0 ){
										if(StringUtils.isNotBlank(merchantSettleStatistics.getTrade_amount()) && 
											StringUtils.isNotBlank(merchantSettleStatistics.getRefund_amount()) &&
											StringUtils.isNotBlank(merchantSettleStatistics.getSystem_fee()) &&
											StringUtils.isNotBlank(merchantSettleStatistics.getMer_fee()) &&
											StringUtils.isNotBlank(merchantSettleStatistics.getMer_refund_fee())){
											
											if(checkTradeMoney(Double.valueOf(merchantSettleStatistics.getTrade_amount()), 
													Double.valueOf(merchantSettleStatistics.getRefund_amount()), 
													Double.valueOf(merchantSettleStatistics.getSystem_fee()),
													Double.valueOf(merchantSettleStatistics.getMer_fee()),
													Double.valueOf(merchantSettleStatistics.getMer_refund_fee()),
													0d,
													bil_way)){//判断商户结算金额是否小于等于0
												//TODO 判断商户结算金额是否大于商户余额
												MerchantsBalance merchantsBalance = merchantsBalanceService.queryMerBalanceByMerCode(merchantSettleStatistics.getMer_code());
												if(merchantsBalance != null){
													if(StringUtils.isNotBlank(merchantsBalance.getMer_balance())){
														if(!checkTradeMoney(Double.valueOf(merchantSettleStatistics.getTrade_amount()), 
																Double.valueOf(merchantSettleStatistics.getRefund_amount()), 
																Double.valueOf(merchantSettleStatistics.getSystem_fee()),
																Double.valueOf(merchantSettleStatistics.getMer_fee()),
																Double.valueOf(merchantSettleStatistics.getMer_refund_fee()),
																Double.valueOf(merchantsBalance.getMer_balance()),
																bil_way)){//判断商户结算金额是否大于商户余额
															if(checkTradeMoney(Double.valueOf(merchantSettleStatistics.getTrade_amount()), 
																	Double.valueOf(merchantSettleStatistics.getRefund_amount()), 
																	Double.valueOf(merchantSettleStatistics.getSystem_fee()),
																	Double.valueOf(merchantSettleStatistics.getMer_fee()),
																	Double.valueOf(merchantSettleStatistics.getMer_refund_fee()),
																	Double.valueOf(bil_smallamt),
																	bil_way)){//判断商户结算金额是否满足最低结算金额
																if(endDate >= this_settle_date && endDate >= lastSettleDate){//判断商户合同是否在有效期内
																	if(1 == bil_status){//判断商户结算状态是否开通
																		String trade_amount = merchantSettleStatistics.getTrade_amount();
																		String refund_amount = merchantSettleStatistics.getRefund_amount();
																		
																		MerchantFundSettle merchantFundSettle = new MerchantFundSettle();
																		merchantFundSettle.setMer_code(mer_code);//商户号
																		merchantFundSettle.setOpen_account_code(bil_bankaccount);//结算账户号
																		
																		if(Integer.valueOf(bil_object) == 1){//结算到银行账户
																			merchantFundSettle.setOpen_account_code(bil_bankaccount);//结算账户号
																			merchantFundSettle.setOpen_acount_name(bil_accountname);//结算账户名称
																		}else if(Integer.valueOf(bil_object) == 2){//结算到电银账户
																			merchantFundSettle.setOpen_account_code(bill_account);//结算账户号
																			merchantFundSettle.setOpen_acount_name("电银账户");//结算账户名称
																		}else{
																			log.debug("结算对象信息"+bil_object+"为空");
																			merchantFundSettle.setOpen_account_code("");//结算账户号
																			merchantFundSettle.setOpen_acount_name("");//结算账户名称
																		}
																		
																		merchantFundSettle.setOpen_bank_name(bil_bankname);//结算银行名称
																		merchantFundSettle.setBil_bank(bil_bank);//结算银行联行号
																		merchantFundSettle.setMer_type(mer_category);//商户类别
																		merchantFundSettle.setSettle_type(bil_way);//结算方式
																		merchantFundSettle.setMer_name(mer_abbreviation);//商户简称
																		merchantFundSettle.setSettle_way(bil_object);//结算对象
																		merchantFundSettle.setCreate_tab_date(0);//创建结算单表日期
																	//	if(merchantFundSettleService.checkMerchantFundSettleExsit(mer_code)){
																			merchantFundSettle.setStart_date(lastSettleDate);//结算起始日期自然日
																		//}else{
																		//	merchantFundSettle.setStart_date(0);//第一次发起结算,起始日期保存为0
																		//}
																		merchantFundSettle.setEnd_date(this_settle_date);//结算截止日期自然日
																		merchantFundSettle.setTrade_amount(trade_amount);//交易金额
																		merchantFundSettle.setTrade_count(merchantSettleStatistics.getTrade_count());//交易笔数
																		merchantFundSettle.setRefund_amount(refund_amount);//退款金额
																		merchantFundSettle.setRefund_count(merchantSettleStatistics.getRefund_count());//退款笔数
																		merchantFundSettle.setSystem_fee(merchantSettleStatistics.getSystem_fee());//系统手续费
																		
																		merchantFundSettle.setRefund_mer_fee(merchantSettleStatistics.getMer_refund_fee());//商户退回手续费
																		
																		merchantFundSettle.setMer_fee(merchantSettleStatistics.getMer_fee());//商户手续费
																		
																		merchantFundSettle.setSystem_refund_fee(merchantSettleStatistics.getSystem_refund_fee());//系统退回手续费
																		
																		Double settleAmount = 0d;
																		
																		if(bil_way == 1){
																			settleAmount =  PoundageCalculate.add(Double.valueOf(trade_amount),Double.valueOf(refund_amount)).doubleValue();
																		}else if(bil_way == 2){
																			settleAmount = PoundageCalculate.sub(PoundageCalculate.add(Double.valueOf(trade_amount),Double.valueOf(refund_amount)).doubleValue(),PoundageCalculate.add(Double.valueOf(merchantSettleStatistics.getMer_fee()),Double.valueOf(merchantSettleStatistics.getMer_refund_fee())).doubleValue()).doubleValue();
																		}
																		
																		//统计商户手工调账记录,并归结到结算金额中
																		List<ManualRec> manualRecList = manualRecService.queryMerManualRec(mer_code);
																		
																		if(manualRecList != null && manualRecList.size() > 0){
																			if(manualRecList.size() == 1){
																				if(manualRecList.get(0).getAddorsub() == 1){
																					merchantFundSettle.setRec_amount_sub("0.00");
																					merchantFundSettle.setRec_amount_sub_count(0);
																				}else if(manualRecList.get(0).getAddorsub() == 2){
																					merchantFundSettle.setRec_amount_add("0.00");
																					merchantFundSettle.setRec_amount_add_count(0);
																				}
																			}
																			for (ManualRec manualRec : manualRecList) {
																				if(manualRec.getAddorsub() == 1){
																					merchantFundSettle.setRec_amount_add(manualRec.getRec_amount());
																					merchantFundSettle.setRec_amount_add_count(manualRec.getManual_count());
																					settleAmount = PoundageCalculate.add(settleAmount,Double.valueOf(manualRec.getRec_amount())).doubleValue();
																				}else if(manualRec.getAddorsub() == 2){
																					merchantFundSettle.setRec_amount_sub(manualRec.getRec_amount());
																					merchantFundSettle.setRec_amount_sub_count(manualRec.getManual_count());
																					settleAmount = PoundageCalculate.sub(settleAmount,Double.valueOf(manualRec.getRec_amount())).doubleValue();
																				}
																			}
																		}else{
																			merchantFundSettle.setRec_amount_add("0.00");
																			merchantFundSettle.setRec_amount_add_count(0);
																			merchantFundSettle.setRec_amount_sub("0.00");
																			merchantFundSettle.setRec_amount_sub_count(0);
																		}
																		
																		merchantFundSettle.setSettle_amount(settleAmount.toString());//应结算金额
																		
																		merchantFundSettle.setMer_batch_no(mer_code+this_settle_date);//商户批次号
																		
																		if(sucess_result == 0){
																			sys_batch_no = merchantFundSettleService.getSysBatchNo()+"";
																		}
																		merchantFundSettle.setSys_batch_no(StringUtils.leftPad(sys_batch_no, 12, "0"));//系统批次号
																		
																		merchantFundSettle.setSettle_state(0);//结算状态
																		
																		Calendar calendar_ = Calendar.getInstance();//系统当前时间
																		String nowTime_ = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd");
																		merchantFundSettle.setSettle_date(Integer.valueOf(nowTime_.replaceAll("-", "")));//结算发起日期
																		
																		merchantFundSettle.setSettle_confirm_date(0);//结算确认日期
																		
																		merchantFundSettle.setBil_manual(bil_manual);//手工结算状态
																		
																		merchantFundSettle.setWhtherFz(whtherFz);//商户是否资金分账
																		
																		int saveResult = merchantFundSettleService.addMerchantFundSettle(merchantFundSettle);
																		
																		if(saveResult > 0){
																			result = 0;//结算发起成功
																			sucess_result ++;
																		}else{
																			result = 9;//保存商户结算信息失败
																			log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,商户结算信息保存失败");
																		}
																	}else{
																		result = 6;//该商户结算状态处于关闭状态
																		log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,商户结算状态处于关闭状态");
																	}
																}else{
																	result = 5;//商户的合同已不在有效期内
																	log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,商户合同不在有效期内");
																}
															}else{
																result = 4;//商户结算金额不满足最低结算金额
																log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,结算金额不满足最低结算金额");
															}
														}else{
															result = 8;//商户结算金额低于商户余额
															log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,结算金额小于商户余额");
														}
													}else{
														log.debug("商户号为"+mer_code+",商户名称为"+mer_abbreviation+"的商户余额为空");
													}
												}else{
													log.debug("通过商户号"+merchantSettleStatistics.getMer_code()+"查询商户余额对象不存在,不可发起结算操作");
												}
											}else{
												result = 3;//商户结算金额小于等于0
												log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,结算金额小于等于0");
											}
										}
									}else{
										log.info("商户号"+mer_code+",商户简称："+mer_abbreviation +"结算方式未配置，请核实字段信息");
									}
								}else{
									result = 2;//该商户有差错数据未处理
									log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,有差错数据未处理");
								}
							}else{
								result = 1;//该商户有交易未对账
								log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,商户有交易未对账");
							}
								
						}else{//该周期范围内，该商户不存在可结算订单
							log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,不存在可结算订单");
							noSettleOrder_result ++;
						}
						
						//添加结算发起失败记录
						if(result != 0 && result != -1){
							MerchantSettleFail merchantSettleFail = new MerchantSettleFail();
							merchantSettleFail.setLast_settle_date(lastSettleDate);
							merchantSettleFail.setMer_code(mer_code);
							merchantSettleFail.setMer_name(mer_abbreviation);
							merchantSettleFail.setMer_type(mer_category);
							Calendar calendar_ = Calendar.getInstance();//系统当前时间
							String nowTime_ = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd");
							merchantSettleFail.setSettle_start_date(Integer.valueOf(nowTime_.replaceAll("-", "")));//结算发起日期
							merchantSettleFail.setReason_id(result);
							boolean flag = merchantSettleFailService.addMerchantSettleFail(merchantSettleFail);
							if(flag){
								log.info("商户结算发起失败记录插入成功");
							}else{
								log.info("商户结算发起失败记录插入失败");
							}
						}
					}
				}else{
					log.info("商户号"+mer_code+",商户简称："+mer_abbreviation +"的结算周期不存在，请核实相关信息");
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		StringBuffer sb = new StringBuffer("");
		
		sb.append(total_result);
		sb.append(";");
		sb.append(sucess_result);
		sb.append(";");
		sb.append(total_result-sucess_result-repeat_result-noSettleOrder_result);
		sb.append(";");
		sb.append(repeat_result);
		sb.append(";");
		sb.append(noSettleOrder_result);
		
		return sb.toString();
	}
	
	/**
	 * 针对单一商户进行结算操作
	 * @param settleInfo
	 * @param deduct_stlm_date
	 * @return
	 */
	public String batchStartSettleMerInfo(String settleInfo,String deduct_stlm_date){
		total_result = 0;//结算发起总条数
		sucess_result = 0;//发起成功条数
		repeat_result = 0;//重复结算条数
		noSettleOrder_result = 0;//无可结算订单条数
		int result = 7;
		int this_settle_date = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(StringUtils.isNotBlank(settleInfo) && StringUtils.isNotBlank(deduct_stlm_date)){
				String[] settleInfoArr = settleInfo.split(",");
				if(settleInfoArr != null && settleInfoArr.length > 0){
					total_result = settleInfoArr.length;
					StringBuffer sb_date = new StringBuffer("");
					for(int i=0;i<settleInfoArr.length;i++){
						log.info("当前结算信息："+settleInfoArr[i]);
						if(settleInfoArr[i].contains(";")){
							String[] settle_info = settleInfoArr[i].split(";");
							if(settle_info != null && settle_info.length > 0){
								String mer_code = settle_info[0];//商户号
								String lastSettleDate = settle_info[1];//上次结算日期
								String mer_category = settle_info[2];//商户类别
								String bil_cycle = settle_info[3];//结算周期
								String mer_abbreviation = settle_info[4];//商户简称
								String bil_way = settle_info[5];//结算方式
								String bil_smallamt = settle_info[6];//商户结算最低金额
								String endDate = settle_info[7];//商户合同到期日期
								String bil_status = settle_info[8];//商户结算状态
								String bil_bank = settle_info[9];//结算银行联行号
								String bil_bankname = settle_info[10];//结算银行名称
								String bil_accountname = settle_info[11];//结算账户名称
								String bil_bankaccount = settle_info[12];//结算银行账号
								String bil_object = settle_info[13];//结算账户类型(结算对象)  1：银行账户/2：电银账户/3：账户系统-企业/4:账户系统-个人
								String bil_manual = settle_info[14];//手工结算状态 1:开通/2:关闭
								String whtherFz = settle_info[15];//是否资金分账标识
								String bil_account = settle_info[16];//是否资金分账标识
								if(StringUtils.isNotBlank(mer_code)){
									map.put("mer_code", mer_code);
								}
								if(StringUtils.isNotBlank(lastSettleDate)){
									map.put("lastSettleDate", Integer.valueOf(lastSettleDate));
								}
								if(StringUtils.isNotBlank(mer_category)){
									map.put("mer_type", Integer.valueOf(mer_category));
								}
								if(StringUtils.isNotBlank(bil_cycle)){
									if("1".equals(bil_cycle)){
										this_settle_date = Integer.valueOf(deduct_stlm_date.replaceAll("-", ""));
										map.put("deduct_stlm_date", this_settle_date);
									}else if("2".equals(bil_cycle)){
										this_settle_date = Integer.valueOf(DateUtil.getThisWeekMonday(deduct_stlm_date).replaceAll("-", ""));
										map.put("deduct_stlm_date", this_settle_date);
									}else if("3".equals(bil_cycle)){
										this_settle_date = Integer.valueOf(DateUtil.getMinMonthDate(deduct_stlm_date).replaceAll("-", ""));
										map.put("deduct_stlm_date", this_settle_date);
									}
								}else{
									log.info("商户号"+mer_code+",商户简称："+mer_abbreviation +"结算周期未配置，请核实字段信息");
								}
								
								if(Integer.valueOf(lastSettleDate) >= this_settle_date){//判断此次结算截止日期自然日是否小于上次结算成功日期
									result = -1;//该商户已结算过
									repeat_result ++;
									continue;
								}
								
								MerchantSettleStatistics merchantSettleStatistics = merchantSettleStatisticsService.querySettleMerchantInfoGroupByMerCode(map);
								
								if (merchantSettleStatistics != null) {
									map.put("merCode", map.get("mer_code"));
									map.put("startDate", map.get("lastSettleDate"));
									map.put("endDate", map.get("deduct_stlm_date"));
									int tradeNoDzCount = 0;
									//获取渠道ID列表
									List<MerchantSettleStatistics> instIdList = merchantSettleStatisticsService.queryInstIdList(map);
									for (MerchantSettleStatistics inst : instIdList) {
										InstInfo instInfo = dataManagerInit.getInstInfoById(inst.getInst_id(),inst.getInst_type());//从内存中读取扣款渠道信息记录
										if(instInfo != null){
											BankInst bankInst = dataManagerInit.getBankInstByBankId(instInfo.getBank_id());
											if(instInfo.getInst_type() == 1){
												if(bankInst.getOriginal_data_tableName().contains(",")){
													map.put("original_data_tableName", bankInst.getOriginal_data_tableName().split(",")[0]);
												}else{
													map.put("original_data_tableName", bankInst.getOriginal_data_tableName());
												}
												map.put("deduct_stlm_date", this_settle_date);
												map.put("lastSettleDate", lastSettleDate);
																								
											}else{
												map.put("original_data_tableName", bankInst.getOriginal_data_tableName());
												
												sb_date.append(String.valueOf(this_settle_date).substring(0, 4));
												sb_date.append("-");
												sb_date.append(String.valueOf(this_settle_date).substring(4, 6));
												sb_date.append("-");
												sb_date.append(String.valueOf(this_settle_date).substring(6, 8));
												map.put("deduct_stlm_date", DateUtil.getformatConversionStart(sb_date.toString()));
												
												sb_date.setLength(0);
												
												sb_date.append(lastSettleDate.substring(0, 4));
												sb_date.append("-");
												sb_date.append(lastSettleDate.substring(4, 6));
												sb_date.append("-");
												sb_date.append(lastSettleDate.substring(6, 8));
												
												map.put("lastSettleDate", DateUtil.getformatConversionStart(sb_date.toString()));
												
												sb_date.setLength(0);
											}
											
											map.put("inst_type", instInfo.getInst_type());
											
											tradeNoDzCount += commonDao.queryTradeNoDzHandlerCount("Original_Data.queryTradeNoDzHandlerCount", map);
										}else{
											log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户未找到相关扣款渠道配置信息");
										}
									}
									if(tradeNoDzCount == 0){//判断商户是否存在交易未对账
										int errorNoHandleCount = errorDataLstService.queryErrorNoHandlerCount(map);
										if(errorNoHandleCount == 0){//判断商户是否有未处理差错数据
											if(StringUtils.isNotBlank(bil_way)){
												if(StringUtils.isNotBlank(merchantSettleStatistics.getTrade_amount()) && 
													StringUtils.isNotBlank(merchantSettleStatistics.getRefund_amount()) &&
													StringUtils.isNotBlank(merchantSettleStatistics.getSystem_fee()) &&
													StringUtils.isNotBlank(merchantSettleStatistics.getMer_fee()) &&
													StringUtils.isNotBlank(merchantSettleStatistics.getMer_refund_fee())){
													
													if(checkTradeMoney(Double.valueOf(merchantSettleStatistics.getTrade_amount()), 
															Double.valueOf(merchantSettleStatistics.getRefund_amount()), 
															Double.valueOf(merchantSettleStatistics.getSystem_fee()),
															Double.valueOf(merchantSettleStatistics.getMer_fee()),
															Double.valueOf(merchantSettleStatistics.getMer_refund_fee()),
															0d,
															Integer.valueOf(bil_way))){//判断商户结算金额是否小于0
														//TODO 判断商户结算金额是否大于商户余额
														MerchantsBalance merchantsBalance = merchantsBalanceService.queryMerBalanceByMerCode(merchantSettleStatistics.getMer_code());
														if(merchantsBalance != null){
															if(StringUtils.isNotBlank(merchantsBalance.getMer_balance())){
																if(!checkTradeMoney(Double.valueOf(merchantSettleStatistics.getTrade_amount()), 
																		Double.valueOf(merchantSettleStatistics.getRefund_amount()), 
																		Double.valueOf(merchantSettleStatistics.getSystem_fee()),
																		Double.valueOf(merchantSettleStatistics.getMer_fee()),
																		Double.valueOf(merchantSettleStatistics.getMer_refund_fee()),
																		Double.valueOf(merchantsBalance.getMer_balance()),
																		Integer.valueOf(bil_way))){
																	if(checkTradeMoney(Double.valueOf(merchantSettleStatistics.getTrade_amount()), 
																			Double.valueOf(merchantSettleStatistics.getRefund_amount()), 
																			Double.valueOf(merchantSettleStatistics.getSystem_fee()),
																			Double.valueOf(merchantSettleStatistics.getMer_fee()),
																			Double.valueOf(merchantSettleStatistics.getMer_refund_fee()),
																			Double.valueOf(bil_smallamt),
																			Integer.valueOf(bil_way))){//判断商户结算金额是否满足最低结算金额
																		if(Integer.valueOf(endDate) >= this_settle_date && Integer.valueOf(endDate) >= Integer.valueOf(lastSettleDate)){//判断商户合同是否在有效期内
																			if("1".equals(bil_status)){//判断商户结算状态是否开通
																				String trade_amount = merchantSettleStatistics.getTrade_amount();
																				String refund_amount = merchantSettleStatistics.getRefund_amount();
																				
																				MerchantFundSettle merchantFundSettle = new MerchantFundSettle();
																				merchantFundSettle.setMer_code(mer_code);//商户号
																				
																				if(Integer.valueOf(bil_object) == 1){//结算到银行账户
																					merchantFundSettle.setOpen_account_code(bil_bankaccount);//结算账户号
																					merchantFundSettle.setOpen_acount_name(bil_accountname);//结算账户名称
																				}else if(Integer.valueOf(bil_object) == 2){//结算到电银账户
																					merchantFundSettle.setOpen_account_code(bil_account);//结算账户号
																					merchantFundSettle.setOpen_acount_name("电银账户");//结算账户名称
																				}else{
																					log.debug("结算对象信息"+bil_object+"为空");
																					merchantFundSettle.setOpen_account_code("");//结算账户号
																					merchantFundSettle.setOpen_acount_name("");//结算账户名称
																				}
																				
																				merchantFundSettle.setOpen_bank_name(bil_bankname);//结算银行名称
																				
																				merchantFundSettle.setBil_bank(bil_bank);//结算银行联行号
																				
																				merchantFundSettle.setMer_type(Integer.valueOf(mer_category));//商户类别
																				merchantFundSettle.setSettle_type(Integer.valueOf(bil_way));//结算方式
																				merchantFundSettle.setMer_name(mer_abbreviation);//商户简称
																				merchantFundSettle.setSettle_way(Integer.valueOf(bil_object));//结算对象
																				merchantFundSettle.setCreate_tab_date(0);//创建结算单表日期
																				//if(merchantFundSettleService.checkMerchantFundSettleExsit(mer_code)){
																					merchantFundSettle.setStart_date(Integer.valueOf(lastSettleDate));//结算起始日期自然日
																			//	}else{
																				//	merchantFundSettle.setStart_date(0);//第一次发起结算,起始日期保存为0
																				//}
																				merchantFundSettle.setEnd_date(this_settle_date);//结算截止日期自然日
																				merchantFundSettle.setTrade_amount(trade_amount);//交易金额
																				merchantFundSettle.setTrade_count(merchantSettleStatistics.getTrade_count());//交易笔数
																				merchantFundSettle.setRefund_amount(refund_amount);//退款金额
																				merchantFundSettle.setRefund_count(merchantSettleStatistics.getRefund_count());//退款笔数
																				merchantFundSettle.setSystem_fee(merchantSettleStatistics.getSystem_fee());//系统手续费
																				
																				merchantFundSettle.setRefund_mer_fee(merchantSettleStatistics.getMer_refund_fee());//商户退回手续费
																				
																				merchantFundSettle.setMer_fee(merchantSettleStatistics.getMer_fee());//商户手续费
																				
																				merchantFundSettle.setSystem_refund_fee(merchantSettleStatistics.getSystem_refund_fee());//系统退回手续费
																				
																				Double settleAmount = 0d;
																				if("1".equals(bil_way)){
																					settleAmount =  PoundageCalculate.add(Double.valueOf(trade_amount),Double.valueOf(refund_amount)).doubleValue();
																				}else if("2".equals(bil_way)){
																					settleAmount = PoundageCalculate.sub(PoundageCalculate.add(Double.valueOf(trade_amount),Double.valueOf(refund_amount)).doubleValue(),PoundageCalculate.add(Double.valueOf(merchantSettleStatistics.getMer_fee()),Double.valueOf(merchantSettleStatistics.getMer_refund_fee())).doubleValue()).doubleValue();
																				}
																				
																				List<ManualRec> manualRecList = manualRecService.queryMerManualRec(mer_code);
																				
																				if(manualRecList != null && manualRecList.size() > 0){
																					if(manualRecList.size() == 1){
																						if(manualRecList.get(0).getAddorsub() == 1){
																							merchantFundSettle.setRec_amount_sub("0.00");
																							merchantFundSettle.setRec_amount_sub_count(0);
																						}else if(manualRecList.get(0).getAddorsub() == 2){
																							merchantFundSettle.setRec_amount_add("0.00");
																							merchantFundSettle.setRec_amount_add_count(0);
																						}
																					}
																					for (ManualRec manualRec : manualRecList) {
																						if(manualRec.getAddorsub() == 1){
																							merchantFundSettle.setRec_amount_add(manualRec.getRec_amount());
																							merchantFundSettle.setRec_amount_add_count(manualRec.getManual_count());
																							settleAmount = PoundageCalculate.add(settleAmount,Double.valueOf(manualRec.getRec_amount())).doubleValue();
																						}else if(manualRec.getAddorsub() == 2){
																							merchantFundSettle.setRec_amount_sub(manualRec.getRec_amount());
																							merchantFundSettle.setRec_amount_sub_count(manualRec.getManual_count());
																							settleAmount = PoundageCalculate.sub(settleAmount,Double.valueOf(manualRec.getRec_amount())).doubleValue();
																						}
																					}
																				}else{
																					merchantFundSettle.setRec_amount_add("0.00");
																					merchantFundSettle.setRec_amount_add_count(0);
																					merchantFundSettle.setRec_amount_sub("0.00");
																					merchantFundSettle.setRec_amount_sub_count(0);
																				}
																				
																				merchantFundSettle.setSettle_amount(settleAmount.toString());//应结算金额
																				
																				merchantFundSettle.setMer_batch_no(mer_code+this_settle_date);//商户批次号
																				
																				if(sucess_result == 0){
																					sys_batch_no = merchantFundSettleService.getSysBatchNo()+"";
																				}
																				merchantFundSettle.setSys_batch_no(StringUtils.leftPad(sys_batch_no, 12, "0"));//系统批次号
																				
																				merchantFundSettle.setSettle_state(0);//结算状态
																				
																				Calendar calendar_ = Calendar.getInstance();//系统当前时间
																				String nowTime_ = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd");
																				merchantFundSettle.setSettle_date(Integer.valueOf(nowTime_.replaceAll("-", "")));//结算发起日期
																				
																				merchantFundSettle.setSettle_confirm_date(0);//结算确认日期
																				
																				merchantFundSettle.setBil_manual(StringUtils.isNotBlank(bil_manual)?Integer.valueOf(bil_manual):2);
																				
																				merchantFundSettle.setWhtherFz(StringUtils.isBlank(whtherFz)?0:Integer.valueOf(whtherFz));//商户是否资金分账
																				
																				int saveResult = merchantFundSettleService.addMerchantFundSettle(merchantFundSettle);
																				
																				if(saveResult > 0){
																					result = 0;//结算发起成功
																					sucess_result ++;
																				}else{
																					result = 9;//保存商户结算信息失败
																					log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,保存商户结算信息失败");
																				}
																			}else{
																				result = 6;//该商户结算状态处于关闭状态
																				log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,商户结算状态处于关闭状态");
																			}
																		}else{
																			result = 5;//商户的合同已不在有效期内
																			log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,商户的合同不在有效期内");
																		}
																	}else{
																		result = 4;//商户结算金额不满足最低结算金额
																		log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,商户结算金额不满足最低结算金额");
																	}
																}else{
																	result = 8;//商户结算金额大于商户余额
																	log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,结算金额大于商户余额");
																}
															}else{
																log.debug("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户余额为空");
															}
														}else{
															log.debug("通过商户号"+merchantSettleStatistics.getMer_code()+"查询到商户余额对象不存在,不可发起结算操作");
														}
													}else{
														result = 3;//商户结算金额小于等于0
														log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,结算金额小于等于0");
													}
												}
											}else{
												log.info("商户号"+mer_code+",商户简称："+mer_abbreviation +"结算方式未配置，请核实字段信息");
											}
										}else{
											result = 2;//该商户有差错数据未处理
											log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,存在差错未处理");
										}
									}else{
										result = 1;//该商户有交易未对账
										log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,存在交易未对账");
									}
								}else{//该周期范围内，该商户不存在可结算订单
									log.info("商户号为" + mer_code + ",商户简称为" + mer_abbreviation + "的商户,在日期" + lastSettleDate + "与" + this_settle_date + "之间,不存在可结算订单");
									noSettleOrder_result ++;
								}
								
								//添加结算发起失败记录
								if(result != 0 && result != -1){
									MerchantSettleFail merchantSettleFail = new MerchantSettleFail();
									merchantSettleFail.setLast_settle_date(Integer.valueOf(lastSettleDate));
									merchantSettleFail.setMer_code(mer_code);
									merchantSettleFail.setMer_name(mer_abbreviation);
									merchantSettleFail.setMer_type(Integer.valueOf(mer_category));
									Calendar calendar_ = Calendar.getInstance();//系统当前时间
									String nowTime_ = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd");
									merchantSettleFail.setSettle_start_date(Integer.valueOf(nowTime_.replaceAll("-", "")));//结算发起日期
									merchantSettleFail.setReason_id(result);
									boolean flag = merchantSettleFailService.addMerchantSettleFail(merchantSettleFail);
									if(flag){
										log.info("商户结算发起失败记录插入成功");
									}else{
										log.info("商户结算发起失败记录插入失败");
									}
								}
								
								continue;
								
							}
						}
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		StringBuffer sb = new StringBuffer("");
		
		sb.append(total_result);
		sb.append(";");
		sb.append(sucess_result);
		sb.append(";");
		sb.append(total_result-sucess_result-repeat_result-noSettleOrder_result);
		sb.append(";");
		sb.append(repeat_result);
		sb.append(";");
		sb.append(noSettleOrder_result);
		
		return sb.toString();
	}
	
	/**
	 * 根据商户类型，查询可结算商户信息，并将商户信息拼接成字符串
	 * @param list
	 * @return
	 */
	public String parseMerchantInfo(List<Merchants> list){
		StringBuffer buffer = new StringBuffer("");
		try{
			if(list != null && list.size() > 0){
				for (Merchants merchant : list) {
					String mer_code = merchant.getMer_code();
					int lastSettleDate = merchant.getLastSettleDate();
					int mer_type = merchant.getMer_category();
					int bil_cycle = merchant.getBil_cycle();
					String mer_abbreviation = merchant.getMer_abbreviation();
					int bil_way = merchant.getBil_way();
					String bil_smallamt = merchant.getBil_smallamt();
					int endDate = merchant.getEndDate();
					int bil_status = merchant.getBil_status();
					String bil_bank = merchant.getBil_bank();
					String bil_bankname = merchant.getBil_bankname();
					String bil_accountname = merchant.getBil_accountname();
					String bil_bankaccount = merchant.getBil_account();
					int bil_object = merchant.getBil_object();
					buffer.append(mer_code);
					buffer.append(";");
					buffer.append(lastSettleDate);
					buffer.append(";");
					buffer.append(mer_type);
					buffer.append(";");
					buffer.append(bil_cycle);
					buffer.append(";");
					buffer.append(mer_abbreviation);
					buffer.append(";");
					buffer.append(bil_way);
					buffer.append(";");
					buffer.append(bil_smallamt);
					buffer.append(";");
					buffer.append(endDate);
					buffer.append(";");
					buffer.append(bil_status);
					buffer.append(";");
					buffer.append(bil_bank);
					buffer.append(";");
					buffer.append(bil_bankname);
					buffer.append(";");
					buffer.append(bil_accountname);
					buffer.append(";");
					buffer.append(bil_bankaccount);
					buffer.append(";");
					buffer.append(bil_object);
					buffer.append(",");
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return StringUtils.isBlank(buffer.toString())?null:buffer.toString().substring(0, buffer.toString().length()-1);
	}
	
	/**
	 * 创建结算单，并上传结算单至ftp服务器
	 * @param request
	 * @return
	 */
	@RequestMapping(value=UPLOADSETTLEDATATOFTP,method=RequestMethod.POST)
	@ResponseBody
	public int queryNoComfirmSettleDataCount(ServletRequest request){
		String startTime = request.getParameter("startTime"); 
		String endTime = request.getParameter("endTime");
		String settlementAccountType = request.getParameter("settlementAccountType");
		Map<String,Integer> map = new HashMap<String,Integer>();
		if(StringUtils.isNotBlank(startTime)){
			map.put("startTime", Integer.valueOf(startTime.replaceAll("-", "")));
		}
		if(StringUtils.isNotBlank(endTime)){
			map.put("endTime", Integer.valueOf(endTime.replaceAll("-", "")));
		}
		if(StringUtils.isNotBlank(settlementAccountType)){
			map.put("settlementAccountType", Integer.valueOf(settlementAccountType));
		}
		int result = merchantFundSettleService.queryNoConfirmSettleDataCount(map);
		
		if(result == 0){
			//调用接口 生成结算文件，并上传至FTP中
			boolean flag = merchantFundSettleService.createSettleFile(map);
			if(flag){
				log.info("创建结算单成功");
			}else{
				result = -1;
				log.info("创建结算单失败");
			}
		}else{
			
		}
		
		return result;
	}
	
	/**
	 * 商户资金流水查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERYMERFUNDSTANCEPAGE,method=RequestMethod.POST)
	public String queryMerFundStancePage(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		String mer_code = request.getParameter("mer_code");//商户号
		String trade_stance = request.getParameter("trade_stance");//交易流水号
		String mer_category = request.getParameter("mer_category");//商户类别
		String startDate = request.getParameter("startDate");//交易日期
		String endDate = request.getParameter("endDate");
		String start_date = request.getParameter("start_date");//流水日期
		String end_date = request.getParameter("end_date");
		String mer_state = request.getParameter("mer_state");//商户状态
		String derc_status = request.getParameter("derc_status");//简短描述
		String deduct_stlm_date = request.getParameter("deduct_stlm_date");//清算日期
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(mer_category)){
			map.put("mer_category", Integer.valueOf(mer_category));
		}
		if(StringUtils.isNotBlank(mer_state)){
			map.put("mer_state", Integer.valueOf(mer_state));
		}
		if(StringUtils.isNotBlank(mer_code)){
			map.put("mer_code", mer_code);
		}
		if(StringUtils.isNotBlank(startDate)){
			map.put("startDate", DateUtil.getformatConversionStart(startDate));
		}
		if(StringUtils.isNotBlank(endDate)){
			map.put("endDate", DateUtil.getformatConversionEnd(endDate));
		}
		if(StringUtils.isNotBlank(start_date)){
			map.put("start_date", DateUtil.getFormatOfMsDateConversionStart(start_date));
		}
		if(StringUtils.isNotBlank(end_date)){
			map.put("end_date", DateUtil.getFormatOfMsDateConversionEnd(end_date));
		}
		if(StringUtils.isNotBlank(trade_stance)){
			map.put("trade_stance", trade_stance);
		}
		if(StringUtils.isNotBlank(derc_status)){
			map.put("derc_status", derc_status);
		}
		if (StringUtils.isNotBlank(deduct_stlm_date)) {
			map.put("deduct_stlm_date", deduct_stlm_date);
		}
		
		Page<MerFundStance> page = new Page<MerFundStance>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		model.addAttribute("pageMerFundStance",merFundStanceService.queryPageMerFundStance(page, map));
		model.addAttribute("pageSize", pageSize);
		
		model.addAttribute("mer_state", mer_state);
		model.addAttribute("mer_category", mer_category);
		model.addAttribute("derc_status", derc_status);
		
		return MERFUNDSTANCESELECT;
	}
	
	/**
	 * 下载商户资金流水
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = MERFUNDSTANCEDOWNLOADEXCEL)
	public void downLoadMerFundStance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//Excel表头
			String[] header = {
				"序号","商户号", "商户简称","流水时间", "交易时间", "交易金额","商户手续费", "变动金额", "账户余额", "交易流水号", "简短说明"
			};
			String startDate = request.getParameter("startDate");//交易日期
			String endDate = request.getParameter("endDate");
			String start_date = request.getParameter("start_date");//流水日期
			String end_date = request.getParameter("end_date");
			String mer_category = request.getParameter("mer_category");  //商户类型
			String mer_state = request.getParameter("mer_state");  //商户状态
			String mer_code = request.getParameter("mer_code");  //商户号
			String trade_stance = request.getParameter("trade_stance");  //交易流水号
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");//清算日期
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", DateUtil.getformatConversionStart(startDate));
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", DateUtil.getformatConversionEnd(endDate));
			} 
			if(StringUtils.isNotBlank(start_date)){
				map.put("start_date", DateUtil.getFormatOfMsDateConversionStart(start_date));
			}
			if(StringUtils.isNotBlank(end_date)){
				map.put("end_date", DateUtil.getFormatOfMsDateConversionEnd(end_date));
			}
			if(StringUtils.isNotBlank(mer_category)){
				map.put("mer_category", Integer.valueOf(mer_category));
			}
			if(StringUtils.isNotBlank(mer_state)){
				map.put("mer_state", Integer.valueOf(mer_state));
			}
			if (StringUtils.isNotBlank(mer_code)) {
				map.put("mer_code", mer_code);
			}
			if (StringUtils.isNotBlank(trade_stance)) {
				map.put("trade_stance", trade_stance);
			}
			if (StringUtils.isNotBlank(deduct_stlm_date)) {
				map.put("deduct_stlm_date", deduct_stlm_date);
			}
			List<MerFundStance> merFundStanceList = merFundStanceService.queryMerFundStanceList(map);
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
			HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet("商户资金流水列表");
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    int row = 1;
		    double tradeAmount = 0.0d;
		    double merFee = 0.0d;
		    double changeAmount = 0.0d;
			if (merFundStanceList != null && merFundStanceList.size() > 0) {
				String[] data = null;
				for (MerFundStance merFundStance : merFundStanceList) {
					String description_status = "";
					if(merFundStance.getDerc_status() == 1){
						description_status = "消费(支付)";
					}else if(merFundStance.getDerc_status() == 2){
						description_status = "退款(冲正)";
					}else if(merFundStance.getDerc_status() == 3){
						description_status = "差错调整(支付)";
					}else if(merFundStance.getDerc_status() == 4){
						description_status = "差错调整(冲正)";
					}else if(merFundStance.getDerc_status() == 5){
						description_status = "结算到电银账户";
					} else if (merFundStance.getDerc_status() == 6) {
						description_status = "结算到银行账户";
					} else if (merFundStance.getDerc_status() == 7) {
						description_status = "手工调账";
					}
					data = new String[] {
						row+"",
						merFundStance.getMer_code(),
						merFundStance.getMer_name(),
						merFundStance.getStance_time(),
						merFundStance.getTrade_time(),
						String.format("%.2f", merFundStance.getTrade_amount()),
						String.format("%.2f", merFundStance.getMer_fee()),
						String.format("%.2f", merFundStance.getChange_amount()),
						String.format("%.2f", merFundStance.getAccount_amount()),
						merFundStance.getTrade_stance(),
						description_status
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					tradeAmount += merFundStance.getTrade_amount();
					merFee += merFundStance.getMer_fee();
					changeAmount += merFundStance.getChange_amount();
					row++;
				}
			}
			
			//写入数据统计行
			String[] data = new String[]{"总计:"+(row-1)+"条记录","","","","",FlightUtil.dfFormat("0.00", tradeAmount)+"",FlightUtil.dfFormat("0.00", merFee)+"",FlightUtil.dfFormat("0.00", changeAmount)+"","","",""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,"MerHLOG_"+DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT_3)+".xls");
			if(flag){
				log.info("商户资金流水.xls 文件创建成功");
			}else{
				log.info("商户资金流水.xls 文件创建失败");
			}
		} catch (Exception e) {
			log.error("下载商户资金流水列表出错：" + e.getMessage());
		}
	}
}
