package com.chinaebi.action;

import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.InstInfo;
import com.chinaebi.service.DataLstService;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.utils.StringUtils;

/**
 * 汇总数据控制器
 */
@Controller
public class DataLstController {
	protected Log log = LogFactory.getLog(getClass());
	
	public static final String MANUALSUMMARYDATA_INFO = "originalData/manual_summary_data";
	public static final String MANUALSUMMARYDATA = "/manualSummaryData.do";
	public static final String MANUALSUMMARYDATAINFO = "manualSummaryDataInfo.do";
	
	@Autowired
	@Qualifier(value="dataLstService")
	private DataLstService dataLstService;
	
	@Autowired
	@Qualifier(value = "instInfoService")
	private InstInfoService instInfoService;
	
	/**
	 * 跳转到手动汇总数据页面，传递机构信息list
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = MANUALSUMMARYDATAINFO)
	public String manualSummaryDataInfo(ServletRequest request,Model model) throws Exception{
		List<InstInfo> list = instInfoService.queryAll();
		model.addAttribute("instInfoList", list);
		return MANUALSUMMARYDATA_INFO;
	}
	
	/**
	 * 根据不同渠道手动汇总数据，提供接口，到backstage_manager实现
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = MANUALSUMMARYDATA,method = RequestMethod.POST)
	@ResponseBody
	public int manualSummaryData(ServletRequest request) throws Exception{
		String instIdAndType = request.getParameter("inst_name");
		String summaryDate = request.getParameter("summaryDate");
		String bankInst = request.getParameter("bank_id");
		boolean flag = false;
		try{
			String bank_id = null;
			String bankType = null;
			if (StringUtils.isNotBlank(bankInst)) {
				String[] bankInstList = bankInst.split(",");
				bank_id = bankInstList[0];
				bankType = bankInstList[1];
			}
			if(StringUtils.isNotBlank(bank_id) && StringUtils.isNotBlank(summaryDate)){
				if(StringUtils.isBlank(instIdAndType)){
					//以银行网关为维度进行手动数据汇总操作
					flag = dataLstService.manualSummaryData(Integer.valueOf(bank_id),0,summaryDate, Integer.valueOf(bankType));
				}else{
					//以渠道为维度进行手动数据汇总操作
					String[] info = instIdAndType.split(",");
					flag = dataLstService.manualSummaryData(Integer.valueOf(bank_id),Integer.valueOf(info[0]),summaryDate,Integer.valueOf(info[1]));
				}
			}
		}catch(Exception e){
			log.error("管理平台手动汇总原始交易数据抛出异常:"+e.getMessage());
		}
		if(flag){
			log.info("手动汇总数据成功");
			return 1;
		}else{
			log.info("手动汇总数据失败");
			return 0;
		}
	}
}

