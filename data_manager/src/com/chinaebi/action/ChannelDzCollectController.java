package com.chinaebi.action;


import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinaebi.entity.ChannelDzCollect;
import com.chinaebi.service.ChannelDzCollectService;
import com.chinaebi.utils.CreateExcelUtil;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.Ryt_trade_type;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class ChannelDzCollectController {
	// 记录查询时的日志
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/*
	 *分页查询对账明细下载数据功能
	 */
	private static final String QUERYPAGECHANNELDZDATA = "queryPageChannelDzData.do";
	
	/*
	 * 下载对账明细数据功能
	 */
	private static final String DUIZHANGDETAILDATADOWNLOAD = "duiZhangDetailDataDownLoad.do";
	
	/*
	 * 对账明细数据下载页面
	 */
	private static final String DUIZHANGDETAILSDOWNLOADJSP = "/duizhangResultData/duizhangDetailsDownload";
	
	@Autowired
	@Qualifier(value="channelDzCollectService")
	private ChannelDzCollectService channelDzCollectService;
	
	@RequestMapping(value=QUERYPAGECHANNELDZDATA,method=RequestMethod.POST)
	public String queryPageChannelDzData(Model model,ServletRequest request){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		String startTime = request.getParameter("startTime");//交易起始日期
		String endTime = request.getParameter("endTime");//交易截止日期
		String deduct_sys_stance = request.getParameter("deduct_sys_stance");//交易流水号
		String deduct_stlm_date = request.getParameter("deduct_stlm_date");//清算日期
		String additional_data = request.getParameter("additional_data");//订单号
		String bk_chk = request.getParameter("bk_chk");//对账结果
		String req_mer_code = request.getParameter("req_mer_code");//电银商户号
		String whetherRiqie = request.getParameter("whetherRiqie");//是否日期
		String trade_result = request.getParameter("trade_result");//交易结果
		String bank_id = request.getParameter("bank_id");//银行网关ID
		String deduct_sys_id = request.getParameter("channel");//扣款渠道ID
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Page<ChannelDzCollect> page = new Page<ChannelDzCollect>();
		if (StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if (StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else
			page.setPageSize(10);
		
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", DateUtil.getformatyyyyMMddHHmmssStart(startTime.replaceAll("-", "")));
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", DateUtil.getformatyyyyMMddHHmmssEnd(endTime.replaceAll("-", "")));
		}
		if (StringUtils.isNotBlank(deduct_sys_stance)) {
			map.put("deduct_sys_stance", deduct_sys_stance);
		}
		if (StringUtils.isNotBlank(deduct_stlm_date)) {
			map.put("deduct_stlm_date", deduct_stlm_date.replaceAll("-", ""));
		}
		if (StringUtils.isNotBlank(deduct_sys_id)) {
			map.put("deduct_sys_id", deduct_sys_id);
		}
		if (StringUtils.isNotBlank(additional_data)) {
			map.put("additional_data", additional_data);
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
		if (StringUtils.isNotBlank(trade_result)) {
			map.put("trade_result", trade_result);
		}
		if (StringUtils.isNotBlank(bank_id)) {
			map.put("bank_id", bank_id);
		}
		
		Page<ChannelDzCollect> originalDataPage = channelDzCollectService.queryPageChannelDzData(page,map);
		
		if(originalDataPage != null && originalDataPage.getResult() != null && originalDataPage.getResult().size() > 0){
			List<ChannelDzCollect> list = originalDataPage.getResult();
			if(list != null && list.size() > 0){
				for (ChannelDzCollect channelDzCollect : list) {
					channelDzCollect.setTrade_type(Ryt_trade_type.getRytTradeName(StringUtils.isNotBlank(channelDzCollect.getTrade_type())?Integer.valueOf(channelDzCollect.getTrade_type()):31));
				}
			}
		}
		
		String mfFlag = request.getParameter("mfFlag");//查询类型    1-查询条件查询   ; 2-分页查询
		
		String dzSuccessMoney = request.getParameter("dzSuccessMoney");//对账成功金额
		String merFee = request.getParameter("merFee");//对账成功数据对应商户手续费
		String dzFailMoney = request.getParameter("dzFailMoney");//对账失败金额
		String noDzMoney = request.getParameter("noDzMoney");//未对账金额
		
		if("1".equals(mfFlag)){
			
			Map<Integer,String> map_amount = channelDzCollectService.queryChannelDzDataTradeAmount(map);
			
			if (StringUtils.isBlank(bk_chk)) {//对账条件没有选择或者无需对账
				model.addAttribute("dzSuccessMoney", StringUtils.isNotBlank(map_amount.get(1)) ? ( ( map_amount.get(1).indexOf(",")> -1 ) ? map_amount.get(1).split(",")[0] : "0.00") : "0.00");
				model.addAttribute("merFee", StringUtils.isNotBlank(map_amount.get(1)) ? ( ( map_amount.get(1).indexOf(",")> -1 ) ? map_amount.get(1).split(",")[1] : "0.00") : "0.00");
				model.addAttribute("dzFailMoney", map_amount.get(2)==null?0.00:map_amount.get(2));
				model.addAttribute("noDzMoney", map_amount.get(0)==null?0.00:map_amount.get(0));
			}else{
				if("0".equals(bk_chk)){//未对账
					model.addAttribute("dzSuccessMoney", "");
					model.addAttribute("merFee", "");
					model.addAttribute("dzFailMoney", "");
					model.addAttribute("noDzMoney", map_amount.get(0)==null?0.00:map_amount.get(0));
				}else if("1".equals(bk_chk)){//对账成功
					model.addAttribute("dzSuccessMoney", StringUtils.isNotBlank(map_amount.get(1)) ? ( ( map_amount.get(1).indexOf(",")> -1 ) ? map_amount.get(1).split(",")[0] : "0.00") : "0.00");
					model.addAttribute("merFee", StringUtils.isNotBlank(map_amount.get(1)) ? ( ( map_amount.get(1).indexOf(",")> -1 ) ? map_amount.get(1).split(",")[1] : "0.00") : "0.00");
					model.addAttribute("dzFailMoney", "");
					model.addAttribute("noDzMoney", "");
				}else if("2".equals(bk_chk)){//对账失败
					model.addAttribute("dzSuccessMoney", "");
					model.addAttribute("merFee", "");
					model.addAttribute("dzFailMoney", map_amount.get(2)==null?0.00:map_amount.get(2));
					model.addAttribute("noDzMoney", "");
				}else if("3".equals(bk_chk)){
					model.addAttribute("dzSuccessMoney", "");
					model.addAttribute("merFee", "");
					model.addAttribute("dzFailMoney", "");
					model.addAttribute("noDzMoney", "");
				}
			}
		}else{
			model.addAttribute("dzSuccessMoney", dzSuccessMoney);
			model.addAttribute("merFee", merFee);
			model.addAttribute("dzFailMoney", dzFailMoney);
			model.addAttribute("noDzMoney", noDzMoney);
		}

		model.addAttribute("getDataResult", originalDataPage);
		
		model.addAttribute("bankId", bank_id);
		model.addAttribute("inst_id", deduct_sys_id);
		model.addAttribute("bk_chk", bk_chk);
		model.addAttribute("whetherRiqie", whetherRiqie);
		model.addAttribute("trade_result", trade_result);
		model.addAttribute("pageSize", pageSize);
		
		return DUIZHANGDETAILSDOWNLOADJSP;
	}
	
	
	@RequestMapping(value=DUIZHANGDETAILDATADOWNLOAD)
	public void duizhangDetailDataDownLoad(HttpServletRequest request, HttpServletResponse response){
		try{
			String startTime = request.getParameter("startTime");//交易起始日期
			String endTime = request.getParameter("endTime");//交易截止日期
			String deduct_sys_stance = request.getParameter("deduct_sys_stance");//交易流水号
			String deduct_stlm_date = request.getParameter("deduct_stlm_date");//清算日期
			String additional_data = request.getParameter("additional_data");//订单号
			String bk_chk = request.getParameter("bk_chk");//对账结果
			String req_mer_code = request.getParameter("req_mer_code");//电银商户号
			String whetherRiqie = request.getParameter("whetherRiqie");//是否日期
			String trade_result = request.getParameter("trade_result");//交易结果
			String bank_id = request.getParameter("bank_id");//银行网关ID
			String deduct_sys_id = request.getParameter("channel");//扣款渠道ID
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			if (StringUtils.isNotBlank(startTime)) {
				map.put("startTime", DateUtil.getformatyyyyMMddHHmmssStart(startTime.replaceAll("-", "")));
			}
			if (StringUtils.isNotBlank(endTime)) {
				map.put("endTime", DateUtil.getformatyyyyMMddHHmmssEnd(endTime.replaceAll("-", "")));
			}
			if (StringUtils.isNotBlank(deduct_sys_stance)) {
				map.put("deduct_sys_stance", deduct_sys_stance);
			}
			if (StringUtils.isNotBlank(deduct_stlm_date)) {
				map.put("deduct_stlm_date", deduct_stlm_date.replaceAll("-", ""));
			}
			if (StringUtils.isNotBlank(deduct_sys_id)) {
				map.put("deduct_sys_id", deduct_sys_id);
			}
			if (StringUtils.isNotBlank(additional_data)) {
				map.put("additional_data", additional_data);
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
			if (StringUtils.isNotBlank(trade_result)) {
				map.put("trade_result", trade_result);
			}
			if (StringUtils.isNotBlank(bank_id)) {
				map.put("bank_id", bank_id);
			}
			
			List<ChannelDzCollect> dataList = channelDzCollectService.queryChannelDzDataList(map);
			
			//Excel表头
			String[] header = {"序号", "交易流水号", "电银商户号", "商户简称", "商户订单号", "交易时间", "订单金额(元)", "实际交易金额(元)", "交易状态", "对账结果", "交易类别", "扣款渠道", "商户手续费(元)", "支付手续费(元)", "银行流水号"};
			Calendar calendar = Calendar.getInstance();//系统当前时间
			SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMdd");  
			String newdate=sdformat.format(calendar.getTime());
			
			// 创建新的Excel 工作簿
		    HSSFWorkbook workbook = new HSSFWorkbook();
		    // 设置单元格格式(文本)
		 	HSSFCellStyle cellStyle = workbook.createCellStyle();
		    // 在Excel 工作簿中建一工作表
		    HSSFSheet sheet = workbook.createSheet(newdate);
		    //创建抬头(标题)
		    CreateExcelUtil.createHeader(workbook,sheet,header);
		    String totalOrderAmount = "0.00";
		    String totalActualAmount = "0.00";
		    double totalMerFee = 0.00;
		    double totalZfFee = 0.00;
		    int row = 1;
			if(dataList != null && dataList.size() > 0){
				String[] data = null;
				String bkChk = "未知";
				String tradeResult = "未知";
			    for(ChannelDzCollect dzData:dataList){
			    	if(dzData.getBk_chk() == 0){
			    		bkChk = "未对账";
			    	}else if(dzData.getBk_chk() == 1){
			    		bkChk = "对账成功";
			    	}else if(dzData.getBk_chk() == 2){
			    		bkChk = "对账失败";
			    	}else if(dzData.getBk_chk() == 3){
			    		bkChk = "无需对账";
			    	}
			    	
			    	if(dzData.getTrade_result() == 0){
			    		tradeResult = "初始状态";
			    	}else if(dzData.getTrade_result() == 1){
			    		tradeResult = "待支付";
			    	}else if(dzData.getTrade_result() == 2){
			    		tradeResult = "成功";
			    	}else if(dzData.getTrade_result() == 3){
			    		tradeResult = "失败";
			    	}else if(dzData.getTrade_result() == 4){
			    		tradeResult = "请求银行失败";
			    	}else if(dzData.getTrade_result() == 5){
			    		tradeResult = "撤销";
			    	}else if(dzData.getTrade_result() == 6){
			    		tradeResult = "超时";
			    	}
			    	
					data = new String[] {
						row+"",
						dzData.getReq_sys_stance(),
						dzData.getReq_mer_code(),
						dzData.getDy_mer_name(),
						dzData.getOid(),
						dzData.getDeduct_sys_time(),
						String.format("%.2f", dzData.getTrade_amount()),
						String.format("%.2f", dzData.getTrade_amount()),
						tradeResult,
						bkChk,
						Ryt_trade_type.getRytTradeName(StringUtils.isNotBlank(dzData.getTrade_type())?Integer.valueOf(dzData.getTrade_type()):31),
						dzData.getInst_name(),
						String.format("%.2f", dzData.getMer_fee()),
						String.format("%.2f", dzData.getZf_fee()),
						dzData.getDeduct_sys_stance()
					};
					CreateExcelUtil.output(cellStyle,sheet,row,data);
					data = null;
					row++;
					totalOrderAmount = PoundageCalculate.add(dzData.getTrade_amount(),totalOrderAmount).toString();
					totalActualAmount = PoundageCalculate.add(dzData.getTrade_amount(),totalActualAmount).toString();
					if(dzData.getBk_chk() == 1){//仅对账成功的数据，才累加商户手续费
						totalMerFee = PoundageCalculate.add(dzData.getMer_fee(),totalMerFee).doubleValue();
					}
					totalZfFee = PoundageCalculate.add(dzData.getZf_fee(),totalZfFee).doubleValue();
				}
			}else{
				log.info("查询对账明细数据为空");
			}
			
			//写入数据统计行
			String[] data = new String[]{"总计:"+(row-1)+"条记录","","","","","", totalOrderAmount,totalActualAmount,"","","","",totalMerFee+"",totalZfFee+"",""};
			CreateExcelUtil.output(cellStyle,sheet,row,data);
			
			boolean flag = CreateExcelUtil.createExcel(response, workbook,("对账明细表_"+newdate+".xls"));
			if(flag){
				log.info("对账明细表"+newdate+".xls  文件创建成功");
			}else{
				log.info("对账明细表"+newdate+".xls  文件创建失败");
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
	}
}
