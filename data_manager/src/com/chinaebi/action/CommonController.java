package com.chinaebi.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.reload.DataManagerInit;

/**
 * 公用控制层，用于加载渠道列表、交易类型列表、交易类别列表、处理方式及原因码列表
 * @author wufei
 *
 */
@Controller
public class CommonController {
	//记录日志
	private static Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	//查询渠道列表
	private static final String QUERY_INSTINFO_LIST = "getInstInfoList.do";
	
	//获取外部对账渠道列表
	private static final String QUERY_OUTER_DZ_LIST = "getOuterDzInfoList.do";
	
	//获取内部对账渠道列表
	private static final String QUERY_INNER_INSTINFO_LIST = "getInnerDuizhangInfoList.do";
	
	//查询差错处理方式列表
	private static final String QUERY_ERROR_HANDLING_LIST = "getErrorHandlingList.do";
	
	// 根据handling_id获取差错处理方法名称
	private static final String QUERY_ERROR_HANDING_LIST_BY_HANDINGID = "getErrorHandingListByHandingId.do";
	
	//查询交易类型列表
	private static final String QUERY_TRADE_AMOUNT_CONF_LIST = "getTradeAmountConfList.do";
	
	private static final String QUERY_OUTER_ERROR_DZ_INSTINFO = "getOutErrorDzInstInfo.do";
	
	private static final String QUERY_ON_LINE_INSTINFO = "getOnLineInstInfoList.do";
	
	private static final String QUERY_UNDER_INNER_DZ_INFO = "getUnderLineInnerDzInfoList.do";
	
	//查询渠道列表
	private static final String QUERY_ALL_INSTINFO_LIST = "getAllInstInfoList.do";
	
	/*
	 * 查询需要解析品牌服务费的渠道
	 */
	private static final String QUERY_PARSING_PPFW_INST_LIST = "queryParsingPpfwInstList.do";
	
	/*
	 * 根据渠道类型查询渠道信息
	 */
	private static final String lOAD_INST_INFO_OF_CUSTOM_OBJECT = "loadInstInfoOfCustomObject.do";
	
	// 根据银行机构ID获取渠道信息
	private static final String QUERY_INSTINFO_BY_BANKID = "getInstInfoByBankId.do";
	
	// 获取所有银行机构信息
	private static final String QUERY_ALL_BANK_INST = "queryAllBankInst.do";
	
	// 获取银行网关为线下类型的银行机构信息
	private static final String QUERY_UNDERLINE_BANKINST_LIST = "getUnderLineBankInstList.do";
	
	// 获取银行网关为线上类型的银行机构信息
	private static final String QUERY_ONLINE_BANKINST_LIST = "getOnLineBankInstList.do";
	
	// 获取对账类型是外部对账的银行机构信息
	private static final String QUERY_ISWhetherOuterDz_BANKINST = "getIsWhetherOuterDzBankInstList.do";
	
	private static final String QUERYOUTDZINSTINFOBYBANKID = "queryOutDzInstInfoByBankId.do";
	
	private static final String GETINSTINFOBYINSTNAME = "getInstInfoByInstName.do";
	
	
	//从内存中加载各种配置列表
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	/**
	 * 获取渠道列表
	 * @return
	 */
	@RequestMapping(value = QUERY_ALL_INSTINFO_LIST)
	@ResponseBody
	public List<InstInfo> getAllInstInfoList() {
		try {
			List<InstInfo> instInfoList = dataManagerInit.getInstInfoList();
			if(instInfoList != null && instInfoList.size() > 0){
				return instInfoList;
			}else{
				logger.error("获取渠道列表为空");
			}
		} catch (Exception e) {
			logger.error("获取渠道列表dataManagerInit.getInstInfoList()出错：" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取外部对账渠道列表
	 * @return
	 */
	@RequestMapping(value = QUERY_OUTER_DZ_LIST)
	@ResponseBody
	public List<InstInfo> getOuterDzInfoList() {
		try {
			int whether_outer_dz = 1;
			List<InstInfo> innerDuizhangInfoList = dataManagerInit.getOuterDzInfo(whether_outer_dz);
			if (innerDuizhangInfoList.size() > 0) {
				return innerDuizhangInfoList;
			} else {
				logger.info("getOuterDzInfoList()获取需要外部对账的渠道为空...");
			}
		} catch (Exception e) {
			logger.error("getOuterDzInfoList()获取需要外部对账的渠道为空" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取差错处理方式列表
	 * @return
	 */
	@RequestMapping(value = QUERY_ERROR_HANDLING_LIST)
	@ResponseBody
	public List<ErrorHandling> getErrorHandlingList() {
		try {
			List<ErrorHandling> errorHandlingList = dataManagerInit.getErrorHandlingList();
			return errorHandlingList;
		} catch (Exception e) {
			logger.error("获取差错处理方式dataManagerInit.getErrorHandlingList()出错：" + e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = QUERY_ERROR_HANDING_LIST_BY_HANDINGID)
	public String getErrorHandingNameByHandingId(ServletRequest request) {
		String handingName = null;
		try {
			String handingId = request.getParameter("handing_id");
			CommonClass  cc = new CommonClass();
			handingName = cc.getHandlingNameById(Integer.valueOf(handingId));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return handingName;
	}
	
	/**
	 * 获取交易类型列表
	 * @return
	 */
	@RequestMapping(value = QUERY_TRADE_AMOUNT_CONF_LIST)
	@ResponseBody
	public List<TradeAmountConf> getTradeAmountConfList() {
		try {
			List<TradeAmountConf> tradeAmountConfList = dataManagerInit.getTradeAmountConfList();
			return tradeAmountConfList;
		} catch (Exception e) {
			logger.error("获取交易类型dataManagerInit.getTradeAmountConfList()出错：" + e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = QUERY_OUTER_ERROR_DZ_INSTINFO)
	@ResponseBody
	public List<InstInfo> getInstInfoLists() {
		try {
			int whether_outer_error_dz = 1;
			List<InstInfo> instInfoList = dataManagerInit.getInstInfo(whether_outer_error_dz);
			if(instInfoList != null && instInfoList.size() > 0){
				return instInfoList;
			}else{
				logger.error("获取是否外部对账渠道信息为空");
			}
		} catch (Exception e) {
			logger.error("获取渠道列表dataManagerInit.getInstInfo(whether_outer_error_dz)出错：" + e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value = QUERY_INNER_INSTINFO_LIST)
	@ResponseBody
	public List<InstInfo> getInnerDuizhangInfoList() {
		try {
			int whether_inner_js = 1;
			List<InstInfo> innerDuizhangInfoList = dataManagerInit.getInnerDuizhangInstInfoList(whether_inner_js);
			if(innerDuizhangInfoList != null && innerDuizhangInfoList.size() > 0){
				return innerDuizhangInfoList;
			}else{
				logger.error("获取是否内部结算渠道信息为空");
			}
		} catch (Exception e) {
			logger.error("获取渠道列表dataManagerInit.getInnerDuizhangInstInfoList(whether_inner_js)出错：" +e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取线上支付渠道列表
	 * @return
	 */
	@RequestMapping(value = QUERY_ON_LINE_INSTINFO)
	@ResponseBody
	public List<InstInfo> getOnLineInstInfo() {
		int instType = 1;
		List<InstInfo> listInfo = null;
		try {
			listInfo = dataManagerInit.getLineInstInfo(instType);
			if (listInfo != null) {
				return listInfo;
			}
		} catch (Exception e) {
			logger.error("根据线上线下渠道标识获取支付渠道dataManagerInit.getLineInstInfo(instType)出错：" + e.getMessage());
		}
		return listInfo;
	}
	
	/**
	 * 获取线下扣款渠道列表
	 * @return
	 */
	@RequestMapping(value = QUERY_INSTINFO_LIST)
	@ResponseBody
	public List<InstInfo> getUnderOnLineInstInfo() {
		int instType = 0;
		List<InstInfo> listInfo = null;
		try {
			listInfo = dataManagerInit.getLineInstInfo(instType);
			if (listInfo != null) {
				return listInfo;
			}
		} catch (Exception e) {
			logger.error("根据线上线下渠道标识获取支付渠道dataManagerInit.getLineInstInfo(instType)出错：" + e.getMessage());
		}
		return listInfo;
	}
	
	@RequestMapping(value = QUERY_UNDER_INNER_DZ_INFO)
	@ResponseBody
	public List<InstInfo> getUnderLineOutDzInstInfo() {
		int instType = 0;
		int whether_outer_dz = 1;
		List<InstInfo> listInfo = null;
		try {
			listInfo = dataManagerInit.getUnderLineInnerDzInfoList(instType, whether_outer_dz);
			if (listInfo != null) {
				return listInfo;
			}
		} catch (Exception e) {
			logger.error("根据线上线下渠道标识获取支付渠道dataManagerInit.getLineInstInfo(instType)出错：" + e.getMessage());
		}
		return listInfo;
	}
	
	@RequestMapping(value = QUERY_PARSING_PPFW_INST_LIST)
	@ResponseBody
	public List<InstInfo> queryParsingPpfwInstList(){
		List<InstInfo> list = null;
		try{
			list = dataManagerInit.getParsePpfwInstInfo(1);
		}catch(Exception e){
			logger.error("查询需要解析品牌服务费渠道信息失败"+e);
		}
		return list;
	}
	
	@RequestMapping(value=lOAD_INST_INFO_OF_CUSTOM_OBJECT)
	@ResponseBody
	public List<InstInfo> queryInstInfoByInstType(ServletRequest request){
		String file_type = request.getParameter("file_type");
		String inst_name = request.getParameter("inst_name");
		String dataSource = request.getParameter("dataSource");
		int inst_type = 2;
		List<InstInfo> listInfo = null;
		try {
			if(StringUtils.isNotBlank(file_type)){
				if(StringUtils.isNotBlank(dataSource)){
					if("1".equals(dataSource)){
						inst_type = 0;
					}else if("3".equals(dataSource)){
						inst_type = 1;
					}
				}
				if("1".equals(file_type)){
					listInfo = dataManagerInit.getInstInfoByInstName(inst_name,1,inst_type);
				}else{
					listInfo = dataManagerInit.getInstInfoByInstName(inst_name, 0,inst_type);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return listInfo;
	}
	
	/**
	 * 根据银行机构ID获取渠道信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = QUERY_INSTINFO_BY_BANKID, method=RequestMethod.POST)
	public void getInstInfoByBankId(ServletRequest request, ServletResponse response) {
		logger.info("开始根据银行机构ID获取渠道信息...");
		String bankInst = request.getParameter("bankInst");
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			String[] str = bankInst.split(",");
			if (str != null && str.length > 0) {
				List<InstInfo> list = dataManagerInit.getInstInfoByBankId(Integer.valueOf(str[0]));
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				for (InstInfo instInfo : list) {
					sb.append("{");
						sb.append("instId : " + instInfo.getInstId() + ",");
						sb.append("inst_type : " + instInfo.getInst_type() + ",");
						sb.append("name : '" + instInfo.getName() + "'");
					sb.append("},");
				}
				sb.append("]");
				String json = sb.toString().replaceAll("},]", "}]");
				out.print(json);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error("根据银行机构ID获取渠道信息出现异常：" + e.getMessage());
		}
	}
	
	@RequestMapping(value = QUERY_ALL_BANK_INST)
	@ResponseBody
	public List<BankInst> queryAllBankInst() {
		List<BankInst> list = null;
		try {
			list = dataManagerInit.getBankInstList();
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	@RequestMapping(value = QUERY_UNDERLINE_BANKINST_LIST)
	@ResponseBody
	public List<BankInst> getUnderLineBankInstList() {
		List<BankInst> list = null;
		try {
			list = dataManagerInit.getBankInstListByBankType(0);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			logger.error("根据银行网关类型获取银行机构信息出现异常：" + e.getMessage());
		}
		return list;
	}
	
	@RequestMapping(value = QUERY_ONLINE_BANKINST_LIST)
	@ResponseBody
	public List<BankInst> getOnLineBankInstList() {
		List<BankInst> list = null;
		try {
			list = dataManagerInit.getBankInstListByBankType(1);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			logger.error("根据银行网关类型获取银行机构信息出现异常：" + e.getMessage());
		}
		return list;
	}
	
	@RequestMapping(value = QUERY_ISWhetherOuterDz_BANKINST)
	@ResponseBody
	public List<BankInst> getISWhetherOuterDzBankInstList() {
		List<BankInst> list = null;
		try {
			list = dataManagerInit.getBankInstListByWhetherOuterDz(1);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	@RequestMapping(value=QUERYOUTDZINSTINFOBYBANKID)
	 @ResponseBody
	 public List<InstInfo> getInstInfoByBankId(ServletRequest request){
	  List<InstInfo> list = null;
	  try {
	   String bank_id = request.getParameter("bank_id");
	   list = dataManagerInit.getOutDzInstInfoByBankId(Integer.valueOf(bank_id));
	  } catch (Exception e) {
	   logger.error("根据银行机构ID获取渠道信息出现异常：" + e.getMessage());
	  }
	  return list;
	 }
	
	@RequestMapping(value=GETINSTINFOBYINSTNAME)
	@ResponseBody
	public List<InstInfo> getInstInfoByInstName(ServletRequest request){
		List<InstInfo> list = null;
		try{
			String inst_name = request.getParameter("inst_name");
			list = dataManagerInit.getInstInfoByInstName(inst_name, 1, 1);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return list;
	}
}

