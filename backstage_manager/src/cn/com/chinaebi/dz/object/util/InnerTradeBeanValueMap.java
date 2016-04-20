package cn.com.chinaebi.dz.object.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.CreditcardpayTradeLst;
import cn.com.chinaebi.dz.object.DuizhangBeijingbankLst;
import cn.com.chinaebi.dz.object.DuizhangCupsLst;
import cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst;
import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.OriginalBeijingbankLst;
import cn.com.chinaebi.dz.object.OriginalCupsLst;
import cn.com.chinaebi.dz.object.OriginalShengjingbankLst;
import cn.com.chinaebi.dz.object.OriginalZhongxingbankLst;
import cn.com.chinaebi.dz.object.RiqieBeijingbankLst;
import cn.com.chinaebi.dz.object.RiqieCupsLst;
import cn.com.chinaebi.dz.object.RiqieZhongxingbankLst;
import cn.com.chinaebi.dz.object.RytUpmp;

public class InnerTradeBeanValueMap {
	
	public static Log log = LogFactory.getLog(InnerTradeBeanValueMap.class);
	private static cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorData = cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.TradeAmountConfDAO tradeAmountConfDao = cn.com.chinaebi.dz.object.dao.TradeAmountConfDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.InstInfoDAO instInfoDao = cn.com.chinaebi.dz.object.dao.InstInfoDAO.getInstance();

	
	public static final int DZ_FILE_TYPE = 1;
	public static final int ERROR_FILE_TYPE = 2;
	public static final int INNER_CLEAR_FILE_TYPE = 3;
	//线下属性值
	public static final String TRADE_AMOUNT = "tradeAmount";
	public static final String TRADE_FEE = "tradeFee";
	public static final String TRADE_CURRENCY = "tradeCurrency";
	public static final String TRADE_RESULT = "tradeResult";
	public static final String TRADE_TIME = "tradeTime";
	public static final String OUT_ACCOUNT = "outAccount";
	public static final String OUT_ACCOUNUT_VALID_TIME = "outAccountValidTime";
	public static final String OUT_ACCOUNT_INFO1 = "secondTrack";
	public static final String OUT_ACCOUNT_INFO2 = "thirdTrack";
	public static final String IN_ACCOUNT = "inAccount";
	public static final String REQ_SYS_ID = "reqSysId";
	public static final String REQ_SYS_STANCE = "reqSysStance";
	public static final String REQ_RESPONSE = "reqResponse";
	public static final String REQ_MER_CODE = "reqMerCode";
	public static final String REQ_MER_TERM_ID = "reqMerTermId";
	public static final String DEDUCT_MER_CODE = "deductMerCode";
	public static final String DEDUCT_SYS_ID = "deductSysId";
	public static final String DEDUCT_SYS_STANCE = "deductSysStance";
	public static final String DEDUCT_SYS_RESPONSE = "deductSysResponse";
	public static final String DEDUCT_MER_TERM_ID = "deductMerTermId";
	public static final String ROLL_BK_RESULT = "rollBKResult";
	public static final String PASS_WD="passWd";
	public static final String TERMINAL_ID = "terminalId";
	public static final String TERMIANL_TEL_NO = "terminalTelNo";
	public static final String MSG_TYPE = "msgType";
	public static final String PROCESS = "process";
	public static final String BITMAP = "bitMapString";
	public static final String TRADE_DATETIME = "tradeDatetime";
	public static final String TRADE_DATE = "tradeDate";
	public static final String TERMINAL_INFO = "terminalInfo";
	public static final String PW_MODE = "passWdMode";
	public static final String REQ_TIME = "reqTime";
	public static final String REQ_TYPE = "reqType";
	public static final String REQ_INPUT_TYPE = "reqInputType";
	public static final String DEDUCT_ROLLBACK_RESPONSE = "rollbackResponse";
	public static final String DEDUCT_ROLLBACK_STANCE = "rollbackStance";
	public static final String TERMINAL_TYPE = "terminalType";
	public static final String DEDUCT_ROLL_BK_REASON = "deductRollBkReason";
	public static final String BALANCE_INFO = "balanceInfo";
	public static final String DEDUCT_STLM_DATE = "deductStlmDate";
	public static final String TRADE_DESC = "tradeDesc";
	public static final String NII = "nii";
	public static final String Authorization_Code = "authorizationCode";
	public static final String Additional_Response_Data = "addRespData";
	public static final String Additional_Data = "additionalData";
	public static final String BOC = "boc";
	public static final String Custom_Define_Info = "customDefInfo";
	public static final String Original_Trans_Info = "originalTransInfo";
	public static final String Deduct_Sys_Reference = "deductSysReference";
	public static final String Deduct_ROLLBACK_Sys_Reference = "deductRollBkSysReference";
	public static final String Mer_Name_Or_Address = "merNameOrAddress";
	public static final String Mer_Type = "merType";
	public static final String Acq_Inst_Id_Code = "acqInstIdCode";
	public static final String Fwd_Inst_Id_Code = "fwdInstIdCode";
	public static final String Service_Pin_Code = "servicePinCode";
	public static final String TRADEMSG_TYPE = "tradeMsgType";//交易消息类型
	public static final String TRADETYPE = "tradeType";//交易类别
	public static final String TRADETYPEINCHINESE = "tradeTypeInChinese";//交易类别中文描述
	public static final String ORIGINALTRANSTIME = "originalTransTime";//原始交易时间
	public static final String RECEIVI_NAME = "receiviName";//收单机构名称
	public static final String AGENTID	= "agentId";//代理商ID
	public static final String DEDUCTSYSNAME = "deductSysName";//扣款渠道名称
	public static final String ACCEPTORPAYFEE = "acceptorPayFee";//银行手续费
	public static final String DEDUCTROLLBK = "deductRollBk";
	public static final String OPERATOR = "operator";
	
	
	//内部清算实体对象
	private static OriginalShengjingbankLst originalShengjingbankLst;
	private static CreditcardpayTradeLst creditcardpayTradeLst;
	
	
	public static Object getInnerObjectValue(String propertyName, Object obj)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		if(obj instanceof OriginalShengjingbankLst){
			originalShengjingbankLst = (OriginalShengjingbankLst)obj;
			if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
				value = String.format("%.2f",originalShengjingbankLst.getTradeAmount()==null?0d:Double.valueOf(originalShengjingbankLst.getTradeAmount())/100);
			}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
				value = StringUtils.isBlank(originalShengjingbankLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalShengjingbankLst.getTradeFee().substring(1, originalShengjingbankLst.getTradeFee().length()))/100);
			}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
				value = tradeAmountConfDao.getTradeTypeName(originalShengjingbankLst.getTrademsgType()==null?0:originalShengjingbankLst.getTrademsgType(), originalShengjingbankLst.getReqProcess());
			}else if(PROCESS.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqProcess();
			}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getOutAccount();
			}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getInAccount()==null?"":originalShengjingbankLst.getInAccount();
			}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
				value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalShengjingbankLst.getTerminalInfo())+"";
			}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
				String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalShengjingbankLst.getTerminalInfo(), errorData);
				value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
			}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqMerCode();
			}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqMerTermId();
			}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductMerCode();
			}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getGainSysId();
			}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqSysStance();
			}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductMerTermId();
			}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(originalShengjingbankLst.getOriginalTransInfo()) || "null".equals(originalShengjingbankLst.getOriginalTransInfo()))?"":originalShengjingbankLst.getOriginalTransInfo();
			}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
				value = "";
				//value = StringUtils.isBlank(originalBeijingbank.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(),originalBeijingbankLst.getTrademsgType(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10)))?"":originalBeijingbank.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(),originalBeijingbankLst.getTrademsgType(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10));
			}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductStlmDate()==null?"":(originalShengjingbankLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
			}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getAuthorizationCode()==null?"":originalShengjingbankLst.getAuthorizationCode();
			}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
				value = StringUtils.isBlank(originalShengjingbankLst.getDeductSysReference())?"":originalShengjingbankLst.getDeductSysReference();
			}else if(PROCESS.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqProcess();
			}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getMerName();
			}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductSysTime()==null?"":(originalShengjingbankLst.getDeductSysTime()+"").substring(0, (originalShengjingbankLst.getDeductSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
			}else if(Additional_Data.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getAdditionalData()==null?"":originalShengjingbankLst.getAdditionalData().split("\\|")[0];
			}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getTerminalId();
			}else if(AGENTID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getAgentId();
			}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
				value = "";
//					String date = originalShengjingbankLst.getDeductStlmDate().toString().substring(0, 10);
//					value = duizhangBeijingbankDao.getAcceptorPayFeeByTraceFromDzBjbankFile(originalShengjingbankLst.getReqSysStance(),date);
			}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
				value = instInfoDao.getReceiviNameById(originalShengjingbankLst.getGainSysId(),0);
			}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
				value = instInfoDao.getInstInfoNameById(originalShengjingbankLst.getGainSysId(),0);
			}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqSysStance();
			}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.isDeductRollBk()?"1":"0";
			}else if(OPERATOR.equalsIgnoreCase(propertyName)){
				value = StringUtils.isNotBlank(originalShengjingbankLst.getAdditionalData())?(originalShengjingbankLst.getAdditionalData().split("\\|").length>3?originalShengjingbankLst.getAdditionalData().split("\\|")[3]:""):"";
			}
			
		}else if(obj instanceof CreditcardpayTradeLst){
			creditcardpayTradeLst = (CreditcardpayTradeLst)obj;
			if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
				value = String.format("%.2f",creditcardpayTradeLst.getTradeAmount()==null?0d:Double.valueOf(creditcardpayTradeLst.getTradeAmount())/100);
			}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
				value = StringUtils.isBlank(creditcardpayTradeLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(creditcardpayTradeLst.getTradeFee().substring(1, creditcardpayTradeLst.getTradeFee().length()))/100);
			}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
				value = tradeAmountConfDao.getTradeTypeName(creditcardpayTradeLst.getTrademsgType(), creditcardpayTradeLst.getReqProcess());
			}else if(PROCESS.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getReqProcess();
			}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getOutAccount();
			}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getInAccount()==null?"":creditcardpayTradeLst.getInAccount();
			}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
				value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(creditcardpayTradeLst.getTerminalInfo())+"";
			}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
				String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(creditcardpayTradeLst.getTerminalInfo(), errorData);
				value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
			}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getReqMerCode();
			}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getReqMerTermId();
			}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductMerCode();
			}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductSysId();
			}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductSysStance();
			}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductMerTermId();
			}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(creditcardpayTradeLst.getOriginalTransInfo()) || "null".equals(creditcardpayTradeLst.getOriginalTransInfo()))?"":creditcardpayTradeLst.getOriginalTransInfo();
			}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
				value = "";
				//value = StringUtils.isBlank(originalBeijingbank.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(),originalBeijingbankLst.getTrademsgType(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10)))?"":originalBeijingbank.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(),originalBeijingbankLst.getTrademsgType(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10));
			}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductStlmDate()==null?"":(creditcardpayTradeLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
			}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getAuthorizationCode()==null?"":creditcardpayTradeLst.getAuthorizationCode();
			}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
				value = StringUtils.isBlank(creditcardpayTradeLst.getDeductSysReference())?"":creditcardpayTradeLst.getDeductSysReference();
			}else if(PROCESS.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getReqProcess();
			}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getMerName();
			}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductSysTime()==null?"":(creditcardpayTradeLst.getDeductSysTime()+"").substring(0, (creditcardpayTradeLst.getDeductSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
			}else if(Additional_Data.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getAdditionalData()==null?"":creditcardpayTradeLst.getAdditionalData();
			}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getTerminalId();
			}else if(AGENTID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getAgentId();
			}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
				value = "";
//					String date = originalShengjingbankLst.getDeductStlmDate().toString().substring(0, 10);
//					value = duizhangBeijingbankDao.getAcceptorPayFeeByTraceFromDzBjbankFile(originalShengjingbankLst.getReqSysStance(),date);
			}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
				value = instInfoDao.getReceiviNameById(creditcardpayTradeLst.getDeductSysId(),0);
			}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
				value = instInfoDao.getInstInfoNameById(creditcardpayTradeLst.getDeductSysId(),0);
			}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getReqSysStance();
			}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.isDeductRollBk()?"1":"0";
			}else if(OPERATOR.equalsIgnoreCase(propertyName)){
				value = StringUtils.isNotBlank(creditcardpayTradeLst.getAdditionalData())?(creditcardpayTradeLst.getAdditionalData().split("\\|").length>3?creditcardpayTradeLst.getAdditionalData().split("\\|")[3]:""):"";
			}
		}
		return value;
	}
	public static Object getInnerObjectValueForTxt(String propertyName, Object obj,String columnLength)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		if(obj instanceof OriginalShengjingbankLst){
			originalShengjingbankLst = (OriginalShengjingbankLst)obj;
			if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
				value = String.format("%.2f",originalShengjingbankLst.getTradeAmount()==null?0d:Double.valueOf(originalShengjingbankLst.getTradeAmount())/100);
			}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
				value = StringUtils.isBlank(originalShengjingbankLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalShengjingbankLst.getTradeFee().substring(1, originalShengjingbankLst.getTradeFee().length()))/100);
			}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalShengjingbankLst.getTrademsgType()==null?0:originalShengjingbankLst.getTrademsgType(), originalShengjingbankLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getOutAccount()==null?"":originalShengjingbankLst.getOutAccount();
			}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(originalShengjingbankLst.getInAccount()) || "null".equals(originalShengjingbankLst.getInAccount()))?"":originalShengjingbankLst.getInAccount();
			}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqMerCode();
			}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqMerTermId();
			}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductMerCode();
			}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getGainSysId();
			}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqSysStance();
			}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductMerTermId();
			}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductStlmDate()==null?"":originalShengjingbankLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
			}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(originalShengjingbankLst.getAuthorizationCode()) || "null".equals(originalShengjingbankLst.getAuthorizationCode()))?"":originalShengjingbankLst.getAuthorizationCode();
			}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductSysReference()==null?"":originalShengjingbankLst.getDeductSysReference();
			}else if(PROCESS.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getReqProcess();
			}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
				value = StringUtils.isBlank(originalShengjingbankLst.getMerName())?new String(String.format("%-"+Integer.valueOf(columnLength)+"s","")):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalShengjingbankLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getDeductSysTime()==null?"":originalShengjingbankLst.getDeductSysTime().toString().substring(0, originalShengjingbankLst.getDeductSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
			}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
				value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalShengjingbankLst.getTerminalInfo());
			}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
				String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalShengjingbankLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
            	value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
			}else if(Additional_Data.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(originalShengjingbankLst.getAdditionalData()) || "null".equals(originalShengjingbankLst.getAdditionalData()))?"":originalShengjingbankLst.getAdditionalData().split("\\|")[0];
			}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(originalShengjingbankLst.getOriginalTransInfo()) || "null".equals(originalShengjingbankLst.getOriginalTransInfo()))?"":originalShengjingbankLst.getOriginalTransInfo();
			}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalShengjingbankLst.getGainSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalShengjingbankLst.getGainSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getTerminalId();
			}else if(AGENTID.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.getAgentId();
			}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
				value = "";
			}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
				value = originalShengjingbankLst.isDeductRollBk()?"1":"0";
			}else if(OPERATOR.equalsIgnoreCase(propertyName)){
				value = StringUtils.isNotBlank(originalShengjingbankLst.getAdditionalData())?(originalShengjingbankLst.getAdditionalData().split("\\|").length>3?originalShengjingbankLst.getAdditionalData().split("\\|")[3]:""):"";
			}
		}else if(obj instanceof CreditcardpayTradeLst){
			creditcardpayTradeLst = (CreditcardpayTradeLst)obj;
			if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
				value = String.format("%.2f",creditcardpayTradeLst.getTradeAmount()==null?0d:Double.valueOf(creditcardpayTradeLst.getTradeAmount())/100);
			}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
				value = StringUtils.isBlank(creditcardpayTradeLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(creditcardpayTradeLst.getTradeFee().substring(1, creditcardpayTradeLst.getTradeFee().length()))/100);
			}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(creditcardpayTradeLst.getTrademsgType(), creditcardpayTradeLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getOutAccount()==null?"":creditcardpayTradeLst.getOutAccount();
			}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(creditcardpayTradeLst.getInAccount()) || "null".equals(creditcardpayTradeLst.getInAccount()))?"":creditcardpayTradeLst.getInAccount();
			}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getReqMerCode();
			}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getReqMerTermId();
			}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductMerCode();
			}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductSysId();
			}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductSysStance();
			}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductMerTermId();
			}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductStlmDate()==null?"":creditcardpayTradeLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
			}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(creditcardpayTradeLst.getAuthorizationCode()) || "null".equals(creditcardpayTradeLst.getAuthorizationCode()))?"":creditcardpayTradeLst.getAuthorizationCode();
			}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductSysReference();
			}else if(PROCESS.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getReqProcess();
			}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
				value = StringUtils.isBlank(creditcardpayTradeLst.getMerName())?"":new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(creditcardpayTradeLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getDeductSysTime()==null?"":creditcardpayTradeLst.getDeductSysTime().toString().substring(0, creditcardpayTradeLst.getDeductSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
			}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
				value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(creditcardpayTradeLst.getTerminalInfo());
			}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
				String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(creditcardpayTradeLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
            	value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
			}else if(Additional_Data.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(creditcardpayTradeLst.getAdditionalData()) || "null".equals(creditcardpayTradeLst.getAdditionalData()))?"":creditcardpayTradeLst.getAdditionalData().length()>=20?creditcardpayTradeLst.getAdditionalData().substring(0, 20):creditcardpayTradeLst.getAdditionalData();
			}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
				value = (StringUtils.isBlank(creditcardpayTradeLst.getOriginalTransInfo()) || "null".equals(creditcardpayTradeLst.getOriginalTransInfo()))?"":creditcardpayTradeLst.getOriginalTransInfo();
			}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(creditcardpayTradeLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(creditcardpayTradeLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getTerminalId();
			}else if(AGENTID.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.getAgentId();
			}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
				value = creditcardpayTradeLst.isDeductRollBk()?"1":"0";
			}else if(OPERATOR.equalsIgnoreCase(propertyName)){
				value = StringUtils.isNotBlank(creditcardpayTradeLst.getAdditionalData())?(creditcardpayTradeLst.getAdditionalData().split("\\|").length>3?creditcardpayTradeLst.getAdditionalData().split("\\|")[3]:""):"";
			}
		}
		
		return value;
		
	}
}
