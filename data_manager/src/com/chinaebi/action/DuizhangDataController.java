package com.chinaebi.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinaebi.dao.ICommonDao;
import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.DuizhangData;
import com.chinaebi.entity.ErrorDataLst;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.OriginalData;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.DuizhangDataService;
import com.chinaebi.service.ErrorDataLstService;
import com.chinaebi.service.ExecuteNodeService;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.FileUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class DuizhangDataController {
	protected Logger log = LoggerFactory.getLogger(DuizhangDataController.class);
	
	//对账单查询
	private static final String QUERYCHANNELDUIZHANGDATA = "/queryChannelDuiZhangData.do";
	private static final String DUIZHANGBANKDATA = "/duizhangData/dui_zhang_data_select";
	
	private static final String MANUALUPLOADDZDATAINFO = "/duizhangData/manual_upload_dz_data";
	
	private static final String QUERYCHANNELDUIZHANGDETAILDATA	 = "/queryChannelDuiZhangDetailData.do";
	
	private static final String MANUALUPLOADDZDATA = "/manualUploadDzData.do";
	
	//差错处理
	private static final String MANUALERRORDZ = "/manualErrorDz.do";
	private static final String MANUALPARSINGERRORDZ = "/manualParsingErrorDz.do";
	private static final String MANUALUPLOADERRORDZDATA = "/manualUploadErrorDzData.do";
	
	//银行对账明细查询
	private static final String QUERY_ALL = "queryDuizhangData.do";
	private static final String JSP_PAGE = "duizhangResultData/bankDuizhangDetail";
	
	// 银行对账明细查询(详情查询)
	private static final String QUERY_DETAIL = "queryDuizhangDataDetail.do";
	
	/*
	 * 分页查询银行对账失败数据
	 */
	private static final String QUERYBANKERRORDATA  = "queryBankErrorData.do";
	
	private static final String DOWNLOADBANKERRORDATAOFTXT = "downLoadBankErrorDataOfTxt.do";
	
	@Autowired
	@Qualifier(value = "instInfoService")
	private InstInfoService instInfoService;
	
	@Autowired
	@Qualifier(value = "executeNodeService")
	private ExecuteNodeService executeNodeService;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<DuizhangData> duizhangDataDao;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<OriginalData> originalDataDao;
	
	@Autowired
	@Qualifier(value = "duizhangDataService")
	private DuizhangDataService duizhangDataService;
	
	@Autowired
	@Qualifier(value = "dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "errorDataLstService")
	private ErrorDataLstService errorDataLstService;
	
	/**
	 * 对账单查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERYCHANNELDUIZHANGDATA,method = RequestMethod.POST)
	public String queryChannelDuiZhangData(ServletRequest request,Model model){
		try{
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			String reqSysStance = request.getParameter("reqSysStance");
			String merCode = request.getParameter("merCode");
			String termId = request.getParameter("termId");
			String outAccount = request.getParameter("outAccount");
			String merType = request.getParameter("merType");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String deductStartTime = request.getParameter("deductStartTime");
			//参考号
			String deductSysReference = request.getParameter("deductSysReference");
			
			String bank_id = request.getParameter("bank_id");
			Page<DuizhangData> page = new Page<DuizhangData>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			Map<String, Object> map = new HashMap<String, Object>();
			String dz_data_tableName = null;
			
			if (StringUtils.isNotBlank(bank_id)) {
				String[] bankInstList = bank_id.split(",");
				map.put("dz_data_tableName", bankInstList[1]);
			}
			if(StringUtils.isNotBlank(reqSysStance)){
				map.put("reqSysStance", reqSysStance.trim());
			}
			if(StringUtils.isNotBlank(merCode)){
				map.put("merCode", merCode.trim());
			}
			if(StringUtils.isNotBlank(termId)){
				map.put("termId", termId.trim());
			}
			if(StringUtils.isNotBlank(outAccount)){
				map.put("outAccount", outAccount.trim());
			}
			if(StringUtils.isNotBlank(merType)){
				map.put("merType", merType.trim());
			}
			if(StringUtils.isNotBlank(startTime)){
				map.put("startTime", startTime.replace("-", ""));
			}
			if(StringUtils.isNotBlank(endTime)){
				map.put("endTime", endTime.replace("-", ""));
			}
			if(StringUtils.isNotBlank(deductStartTime)){
				map.put("deduct_stlm_date", deductStartTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(deductSysReference)) {
				map.put("deductSysReference", deductSysReference);
			}
			Page<DuizhangData> page_ = duizhangDataDao.queryForPage(page,"Duizhang_Data.queryPageDuizData", "Duizhang_Data.queryDuizDataCount", map);
			List<DuizhangData> list = page_.getResult();
			for (DuizhangData duizhangData : list) {
				if(StringUtils.isNotBlank(duizhangData.getOutAccount())){
					if(duizhangData.getOutAccount().length()<7){
						duizhangData.setOutAccount(duizhangData.getOutAccount());
					}else{
						duizhangData.setOutAccount(duizhangData.getOutAccount().substring(0,6)+"********"+duizhangData.getOutAccount().substring(duizhangData.getOutAccount().length()-4));
					}
				}else{
					duizhangData.setOutAccount("");
				}
				if(StringUtils.isNotBlank(duizhangData.getReqTime())){
					String reqTime = duizhangData.getReqTime().replaceAll("-", "");
					duizhangData.setReqTime(reqTime.substring(0, 4)+"-"+reqTime.substring(4, 6)+"-"+reqTime.substring(6, 8));
				}
			}
			model.addAttribute("pageData",page_);
			model.addAttribute("bankId", bank_id);
			model.addAttribute("dz_data_tableName", dz_data_tableName);
			model.addAttribute("totalItems", page_.getTotalItems());
			model.addAttribute("totalPage", page_.getTotalPage());
			model.addAttribute("size", page_.getResult().size());
			model.addAttribute("pageSize", pageSize);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return DUIZHANGBANKDATA;
	}
	
	@RequestMapping(value = QUERYCHANNELDUIZHANGDETAILDATA,method=RequestMethod.POST)
	@ResponseBody
	public DuizhangData queryChannelDuiZhangDetailData(ServletRequest request){
		DuizhangData duizhangData = null;
		try{
			String bank_id = request.getParameter("bank_id");
			String inst_name = request.getParameter("inst_name");
			String id = request.getParameter("id");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(bank_id)) {
				String[] bankInstList = bank_id.split(",");
				map.put("dz_data_tableName", bankInstList[1]);
			}
			if (StringUtils.isNotBlank(inst_name)) {
				String[] inst_ = inst_name.split(",");
				map.put("inst_type", inst_[1]);
			}
			if(StringUtils.isNotBlank(id)){
				map.put("id", id);
			}
			duizhangData = duizhangDataDao.queryForObject("Duizhang_Data.queryDetail", map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return duizhangData;
	}
	
	/**
	 * 上传文件，根据不同扣款渠道，将文件上传到不同的路径下
	 * @param inst_id
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = MANUALUPLOADDZDATA,method = RequestMethod.POST)
	public String uploadFile(@RequestParam MultipartFile file,HttpServletRequest request,Model model) {
		int result = -1;
		try{
			String instIdAndType = request.getParameter("inst_name");
			String summaryDate = request.getParameter("summaryDate");
			String bankId = request.getParameter("bankId");
			
			log.info("针对银行网关"+bankId+",进行上传文件并对账操作,参数:扣款渠道--"+(StringUtils.isNotBlank(instIdAndType)?instIdAndType:"")+",日期--"+summaryDate);
			
			if(StringUtils.isNotBlank(bankId)){
				
				InstInfo instInfo = null;
				if(StringUtils.isNotBlank(instIdAndType)){
					String[] info = instIdAndType.split(",");
					instInfo = instInfoService.queryInstInfoByInstId(Integer.valueOf(info[0]),Integer.valueOf(info[1]));
				}
				
				BankInst bankInst = dataManagerInit.getBankInstByBankId(Integer.valueOf(bankId));
				
				if(bankInst != null){
					boolean parseFlag = false;//解析文件成功与否标识
					boolean doParseFile = false;//是否执行了解析文件操作标识
					if(file != null && StringUtils.isNotBlank(file.getOriginalFilename())){
						if(file.getSize() > 0){//判断文件内容是否为空
							result = duizhangDataService.uploadDzFile(bankInst, instInfo, file, summaryDate);
							if(result == 0){
								log.info("手动上传对账文件成功,进行解析文件操作");
								doParseFile = true;
								parseFlag = duizhangDataService.parseDzFile(bankInst, instInfo, summaryDate,file.getOriginalFilename());
							}else{
								log.debug("上传文件操作失败");
							}
						}else{
							result = 6;//对账单内容为空
							log.info("对账单-------"+file.getOriginalFilename()+"内容为空");
						}
					}else{//若没有上传对账文件,则直接调用对账接口进行对账操作
						parseFlag = true;
					}
					
				    if(parseFlag){
						boolean dzFlag = duizhangDataService.dzHandle(bankInst, instInfo, summaryDate);
						if(dzFlag){
							result = 5;//对账操作成功
							//TODO  查询对账可疑数据，并显示在页面上
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("bankTable", bankInst.getDz_data_tableName());
							map.put("tradeDataTable", bankInst.getOriginal_data_tableName());
							map.put("deduct_stlm_date", summaryDate.replaceAll("-", ""));
							map.put("deduct_stlm_date_error", DateUtil.getformatConversionStart(summaryDate));
							map.put("bankId", bankId);
							map.put("deduct_sys_id", instInfo==null?0:instInfo.getInstId());
							map.put("bankType", bankInst.getBank_type());
							log.info("统计"+bankInst.getBank_name()+"对账结果：参数： -- 对账数据表名-----"+bankInst.getDz_data_tableName() + ",----清算日期-----"+ summaryDate.replaceAll("-", "")+"-------渠道类型-------"+bankInst.getBank_type());
							Page<ErrorDataLst> page = new Page<ErrorDataLst>();
							String curPage = request.getParameter("pageNum");
							String pageSize = request.getParameter("pageSize");
							if (StringUtils.isNotBlank(curPage))
								page.setPageNo(Integer.parseInt(curPage.trim()));
							if (StringUtils.isNotBlank(pageSize))
								page.setPageSize(Integer.parseInt(pageSize.trim()));
							else
								page.setPageSize(10);
							
							Page<ErrorDataLst> pageErrorDataLst = errorDataLstService.queryPageDzTotalErrorData(page, map);
							
							int totalBankData = duizhangDataService.queryBankDataCountOfAll(map);
							int sucessBankData = originalDataDao.queryDzSucessDataTotalCount("Original_Data.queryDzSucessDataTotalCount", map);
							int errorBankData = errorDataLstService.queryCountDzTotalErrorData(map);
							model.addAttribute("pageData", pageErrorDataLst);
							model.addAttribute("totalBankData", totalBankData);
							model.addAttribute("sucessBankData", sucessBankData);
							model.addAttribute("errorBankData", errorBankData);
						}else{
							result = 4;//对账操作失败
						}
					}else{
						result = 3;//解析文件失败
					}
				    if(doParseFile){
				    	try {
							//组建节点工作流状态更新数据map
							Map<String,Object> map = new HashMap<String,Object>();
							if(instInfo != null){
								map.put("deduct_sys_id",instInfo.getInstId());
								map.put("inst_type",instInfo.getInst_type());
								map.put("deduct_stml_date",summaryDate);
								map.put("inst_name", instInfo.getName());
								map.put("dz_file_gain", parseFlag?1:2);
								executeNodeService.updateExecuteStatus(map);//节点工作流更新状态操作
							}else{
								List<InstInfo> list = dataManagerInit.getInstInfoByBankId(bankInst.getBank_id());
								for (InstInfo inst_info : list) {
									map.put("deduct_sys_id",inst_info.getInstId());
									map.put("inst_type",inst_info.getInst_type());
									map.put("deduct_stml_date",summaryDate);
									map.put("inst_name", inst_info.getName());
									map.put("dz_file_gain", parseFlag?1:2);
									executeNodeService.updateExecuteStatus(map);//节点工作流更新状态操作
								}
							}
						} catch (Exception e) {
							log.error("更新银行网关"+bankInst.getBank_name()+"下各渠道对账文件上传工作流节点失败"+e);
						}
				    }
				}
			}
			model.addAttribute("inst_id", instIdAndType);
			model.addAttribute("bank_id", bankId);
			model.addAttribute("summaryDate", summaryDate);
		}catch(Exception e){
			log.error("手动上传并对账抛出异常"+e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("result", result);
		if(result == 0){
			log.info("手动上传并对账结果为:----上传成功，解析失败");
		}else if(result == 1){
			log.info("手动上传并对账结果为:----上传文件失败,系统错误");
		}else if(result == 2){
			log.info("手动上传并对账结果为:----上传文件失败,文件格式不匹配");
		}else if(result == 3){
			log.info("手动上传并对账结果为:----解析文件失败");
		}else if(result == 4){
			log.info("手动上传并对账结果为:----对账操作失败");
		}else if(result == 5){
			log.info("手动上传并对账结果为:----对账操作成功");
		}else if(result == 6){
			log.info("手动上传并对账结果为:----对账单内容为空,请重新上传");
		}else{
			log.info("手动上传并对账结果为:----参数原因,上传并对账操作失败");
		}
		log.info("手动上传并对账操作结束，返回页面");
		return MANUALUPLOADDZDATAINFO;
	}
	
	
	@RequestMapping(value = MANUALUPLOADERRORDZDATA,method=RequestMethod.POST)
	public String manuakUploadErrorDzData(@RequestParam MultipartFile file_error,ServletRequest request,Model model){
		log.info("进入手动上传差错对账文件方法……");
		String instIdAndType = request.getParameter("inst_name_error");
		String summaryDate = request.getParameter("summaryErrorDate");
		
		try {
			log.info("根据不同扣款渠道，将不同渠道的差错对账文件上传到指定路径");
			if(StringUtils.isNotBlank(instIdAndType)){
				String[] info = instIdAndType.split(",");
				InstInfo instInfo = instInfoService.queryInstInfoByInstId(Integer.valueOf(info[0]),Integer.valueOf(info[1]));
				BankInst bankInst = dataManagerInit.getBankInstByBankId(instInfo.getBank_id());
				if(instInfo != null){
					String dz_file_path = bankInst.getDz_file_path();
					//TODO
					
					String dz_file_name_pattern = bankInst.getDz_file_name_pattern();
					
					String file_name = dz_file_name_pattern;
					
					String upload_file_name = file_error.getOriginalFilename();
					
					if(dz_file_name_pattern.contains(DataStatus.date_format_1)){
						file_name = file_name.replaceAll(DataStatus.date_format_1,"\\\\d{4}-\\\\d{2}-\\\\d{2}");
					}else if(dz_file_name_pattern.contains(DataStatus.date_format_2) && !dz_file_name_pattern.contains(DataStatus.date_format_4)){
						file_name = file_name.replaceAll(DataStatus.date_format_2,"\\\\d{8}");
					}else if(dz_file_name_pattern.contains(DataStatus.date_format_3) && !dz_file_name_pattern.contains(DataStatus.date_format_2) && !dz_file_name_pattern.contains(DataStatus.date_format_5) && !dz_file_name_pattern.contains(DataStatus.date_format_4)){
						file_name = file_name.replaceAll(DataStatus.date_format_3,"\\\\d{2}");
					}else if(dz_file_name_pattern.contains(DataStatus.date_format_4)){
						file_name = file_name.replaceAll(DataStatus.date_format_4,"\\\\d{14}");
					}else if(dz_file_name_pattern.contains(DataStatus.date_format_5) && !dz_file_name_pattern.contains(DataStatus.date_format_2) && !dz_file_name_pattern.contains(DataStatus.date_format_4)){
						file_name = file_name.replaceAll(DataStatus.date_format_5,"\\\\d{6}");
					}else if(dz_file_name_pattern.contains(DataStatus.date_format_6)){
						file_name = file_name.replaceAll(DataStatus.date_format_6,"\\\\d{1,2}.\\\\d{1,2}");
        			}
					
					if(dz_file_name_pattern.contains("*")){
        				if(file_name.split("\\*").length > 0){
							file_name = file_name.split("\\*")[0];
						}else{
							file_name = "";
						}
        				if(upload_file_name.length() > file_name.indexOf("*")){
        					upload_file_name = upload_file_name.substring(0, dz_file_name_pattern.indexOf("*"));
        				}
        			}
					
					if(upload_file_name.matches(file_name)){
						FileUtil.inputstreamToFile(file_error.getInputStream(), new File(dz_file_path, file_error.getOriginalFilename()));
						model.addAttribute("return_msg_e", "true");
						log.info("手动上传差错对账文件成功");
					}else{
						model.addAttribute("return_msg_e", "false");
						model.addAttribute("file_msg_e", "false");
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			model.addAttribute("return_msg_e", "false");
			log.info("手动上传差错对账文件失败");
		}
		List<InstInfo> list = instInfoService.queryAll();
		model.addAttribute("instInfoList", list);
		model.addAttribute("inst_id_e", instIdAndType);
		model.addAttribute("summaryErrorDate", summaryDate);
		return MANUALUPLOADDZDATAINFO;
	}
	
	@RequestMapping(value=MANUALPARSINGERRORDZ,method=RequestMethod.POST)
	@ResponseBody
	public int manualParsingErrorDz(ServletRequest request) throws Exception{
		log.info("进入手动解析差错对账文件方法……");
		String instIdAndType = request.getParameter("inst_id");
		String summaryDate = request.getParameter("summaryDate");
		boolean flag = false;
		if(StringUtils.isNotBlank(instIdAndType) && StringUtils.isNotBlank(summaryDate)){
			String[] info = instIdAndType.split(",");
			summaryDate = summaryDate.replaceAll("-", "");
			InstInfo instInfo = instInfoService.queryInstInfoByInstId(Integer.valueOf(info[0]),Integer.valueOf(info[1]));
			BankInst bankInst = dataManagerInit.getBankInstByBankId(instInfo.getBank_id());
			String dz_file_path = bankInst.getDz_file_path();
			flag = duizhangDataService.manualParsingDzData(bankInst.getBank_id(),instInfo.getInstId(),summaryDate,dz_file_path,DataStatus.ERROR_DZ_FILE,Integer.valueOf(info[1]));
		}
		if(flag){
			return 1;
		}else{
			return 0;
		}
	}
	@RequestMapping(value=MANUALERRORDZ,method=RequestMethod.POST)
	@ResponseBody
	public int manuakErrorDz(ServletRequest request,ServletResponse response) throws Exception{
		log.info("进入手动差错对账方法……");
		boolean flag = false;
		String instIdAndType = request.getParameter("inst_id");
		String summaryDate = request.getParameter("summaryDate");
		if(StringUtils.isNotBlank(instIdAndType) && StringUtils.isNotBlank(summaryDate)){
			String[] info = instIdAndType.split(",");
			InstInfo instInfo = instInfoService.queryInstInfoByInstId(Integer.valueOf(info[0]),Integer.valueOf(info[1]));
			if(instInfo != null){
				boolean reductionDataStatusTypeFlag = duizhangDataService.reductionErrorDataStatusType(instInfo.getInstId(),summaryDate,instInfo.getInst_type());
				if(reductionDataStatusTypeFlag){
					flag = duizhangDataService.manualErrorDzData(instInfo.getInstId(),summaryDate,instInfo.getTradeDzImplClass(),instInfo.getInst_type());	
				}else{
					
				}
			}
		}
		if(flag){
			return 1;
		}else{
			return 0;
		}
	}
	
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryDuizhangData(ServletRequest request, Model model) {
		log.info("银行对账明细查询  进入对账文件数据查询...");
		String dz_data_tableName = null;
		try {
			//分页
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<DuizhangData> page = new Page<DuizhangData>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			//查询参数
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");
			String reqSysStance = request.getParameter("reqSysStance");
			String outAccount = request.getParameter("outAccount");
			String bk_chk = request.getParameter("bk_chk");
			
			String bank_id = request.getParameter("bank_id");
			String instType = null;
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				instType = bankInstList[1];
				dz_data_tableName = bankInstList[2];
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(instType)) {
				map.put("instType", instType);
			}
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", startTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", endTime.replace("-", ""));
			}
			if (StringUtils.isNotBlank(deduct_stlm_date)) {
				map.put("deduct_stlm_date", deduct_stlm_date.replace("-", ""));
			}
			if (StringUtils.isNotBlank(reqSysStance)) {
				if ("1".equals(instType)) {
					map.put("deductSysReference", StringUtils.leftPad(reqSysStance.trim(), 12, "0"));
				} else {
					map.put("reqSysStance", reqSysStance.trim());
				}
			}
			if (StringUtils.isNotBlank(outAccount)) {
				map.put("outAccount", outAccount);
			}
			if (StringUtils.isNotBlank(bk_chk)) {
				map.put("bk_chk", bk_chk);
			}
			if (StringUtils.isNotBlank(dz_data_tableName)) {
				map.put("dz_data_tableName", dz_data_tableName);
			}
			Page<DuizhangData> duizhangDataPage = duizhangDataDao.queryForPage(page, "Duizhang_Data.queryPageDuizhangData", "Duizhang_Data.queryCount", map);
			model.addAttribute("pageDataLst", duizhangDataPage);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("bankId", bank_id);
			return JSP_PAGE;
		} catch (Exception e) {
			log.error("查询对账文件" + dz_data_tableName + "数据出错：" + e.getMessage());
			return JSP_PAGE;
		}
	}
	
	@RequestMapping(value = QUERY_DETAIL)
	@ResponseBody
	public DuizhangData queryDetail(ServletRequest request){
		log.info("银行对账明细查询  进入对账文件数据明细查询...");
		String dz_data_tableName = null;
		String id = null;
		DuizhangData duizhangData = null;
		try {
			String bank_id = request.getParameter("bank_id");
			String[] bankInstList = bank_id.split(",");
			if (bankInstList != null && bankInstList.length > 0) {
				dz_data_tableName = bankInstList[2];
			}
			id = request.getParameter("id");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(dz_data_tableName)) {
				map.put("dz_data_tableName", dz_data_tableName);
			}
			if (StringUtils.isNotBlank(id)) {
				map.put("id", id);
			}
			 duizhangData = duizhangDataDao.queryForObject("Duizhang_Data.queryDetail", map);
			 if (duizhangData != null) {
				 return duizhangData;
			 }
			
		} catch (Exception e) {
			log.error("根据机构主键" + id + "查询" + dz_data_tableName + "详情数据出错：" + e.getMessage());
		}
		return duizhangData;
	}
	
	@RequestMapping(value=QUERYBANKERRORDATA,method=RequestMethod.POST)
	public String queryBankErrorData(HttpServletRequest request,Model model){
		try{
			String summaryDate = request.getParameter("summaryDate");
			String bankId = request.getParameter("bankId");
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			String instIdAndType = request.getParameter("inst_name");
			if(StringUtils.isNotBlank(bankId)){
				BankInst bankInst = dataManagerInit.getBankInstByBankId(Integer.valueOf(bankId));
				if(bankInst != null){
					
					InstInfo instInfo = null;
					if(StringUtils.isNotBlank(instIdAndType)){
						String[] info = instIdAndType.split(",");
						instInfo = instInfoService.queryInstInfoByInstId(Integer.valueOf(info[0]),Integer.valueOf(info[1]));
					}
					
					//TODO  查询对账可疑数据，并显示在页面上
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("bankTable", bankInst.getDz_data_tableName());
					map.put("deduct_stlm_date", summaryDate.replaceAll("-", ""));
					map.put("tradeDataTable", bankInst.getOriginal_data_tableName());
					map.put("deduct_stlm_date", summaryDate.replaceAll("-", ""));
					map.put("deduct_stlm_date_error", DateUtil.getformatConversionStart(summaryDate));
					map.put("bankId", bankId);
					map.put("deduct_sys_id", instInfo==null?0:instInfo.getInstId());
					map.put("bankType", bankInst.getBank_type());
					log.info("统计"+bankInst.getBank_name()+"对账结果：参数： -- 对账数据表名-----"+bankInst.getDz_data_tableName() + ",----清算日期-----"+ summaryDate.replaceAll("-", "")+"-------渠道类型-------"+bankInst.getBank_type());
					Page<ErrorDataLst> page = new Page<ErrorDataLst>();
					if (StringUtils.isNotBlank(curPage))
						page.setPageNo(Integer.parseInt(curPage.trim()));
					if (StringUtils.isNotBlank(pageSize))
						page.setPageSize(Integer.parseInt(pageSize.trim()));
					else
						page.setPageSize(10);
					Page<ErrorDataLst> pageErrorDataLst = errorDataLstService.queryPageDzTotalErrorData(page, map);
					
					int totalBankData = duizhangDataService.queryBankDataCountOfAll(map);
					int sucessBankData = originalDataDao.queryDzSucessDataTotalCount("Original_Data.queryDzSucessDataTotalCount", map);
					int errorBankData = errorDataLstService.queryCountDzTotalErrorData(map);
					
					model.addAttribute("pageData", pageErrorDataLst);
					model.addAttribute("totalBankData", totalBankData);
					model.addAttribute("sucessBankData", sucessBankData);
					model.addAttribute("errorBankData", errorBankData);
					model.addAttribute("result", -2);
					model.addAttribute("inst_id", instIdAndType);
					model.addAttribute("bank_id", bankId);
					model.addAttribute("pageSize", pageSize);
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return MANUALUPLOADDZDATAINFO;
	}
	
	/**
	 * 商户结算单下载--针对银行账户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = DOWNLOADBANKERRORDATAOFTXT)
	public void downMerDataOfBankAccountTxt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BufferedOutputStream buff = null; 
		ServletOutputStream outSTr = null;
		
		try {
			
			String bank_id = request.getParameter("bank_id");//银行网关号
			String deduct_stlm_date = request.getParameter("summaryDate");//清算日期
			
			if(StringUtils.isNotBlank(bank_id) && StringUtils.isNotBlank(deduct_stlm_date)){
				
				log.info("下载银行对账可疑数据...参数:---网关号-----"+bank_id+"------交易日期-------"+deduct_stlm_date);
				
				BankInst bankInst = dataManagerInit.getBankInstByBankId(Integer.valueOf(bank_id));
				
				if(bankInst != null){
					
					Calendar calendar = Calendar.getInstance();//系统当前时间
					SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMdd");  
					String newdate=sdformat.format(calendar.getTime());
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/plain");
					response.addHeader("Content-Disposition", "attachment;filename=BankErrorData_"+newdate+".txt");
					 
			        StringBuffer write = new StringBuffer();
			        
			        String tab = "|";
			        String enter = "\r\n";
			        
			        outSTr = response.getOutputStream();
		            buff = new BufferedOutputStream(outSTr);
					
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
					if(dataList != null && dataList.size() > 0){
						for (ErrorDataLst dzData:dataList) {
			                write.append((StringUtils.isBlank(dzData.getAdditional_data())|| "null".equals(dzData.getAdditional_data()))?"":dzData.getAdditional_data()+"/"+dzData.getReq_sys_stance());
			                write.append(tab);
			                write.append(dzData.getTrade_amount());
			                write.append(tab);
			                write.append(dzData.getNii());
			                write.append(enter);
			            }  
			            buff.write(write.toString().getBytes("UTF-8"));
			            buff.flush(); 
			            buff.close();
					}else{
						log.info("参数:---网关号-----"+bank_id+"------交易日期-------"+deduct_stlm_date+",查询银行对账可疑数据为空");
					}
					
				}else{
					log.info("下载银行对账可疑数据，参数：银行网关号------"+bank_id+"------获取网关为空");
				}
				
			}else{
				log.info("下载方法无法执行，参数：网关号---"+bank_id+"---清算日期---"+deduct_stlm_date+",两个参数中存在空值，无法查询并下载");
			}
			
		} catch (Exception e) {
			log.error("下载银行对账可疑数据txt报表出错：" + e.getMessage());
		}finally {  
            try {
            	if(buff != null){
            		buff.close(); 
            	}
                if(outSTr != null){
                	outSTr.close(); 
                }
            } catch (Exception e) {  
            	log.error(e.getMessage()); 
            }  
        }  
	}
}
