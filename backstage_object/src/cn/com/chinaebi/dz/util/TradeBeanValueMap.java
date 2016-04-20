package cn.com.chinaebi.dz.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.DuizhangBeijingbankLst;
import cn.com.chinaebi.dz.object.DuizhangCupsLst;
import cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst;
import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.OriginalBeijingbankLst;
import cn.com.chinaebi.dz.object.OriginalCupsLst;
import cn.com.chinaebi.dz.object.OriginalDljhLst;
import cn.com.chinaebi.dz.object.OriginalSzzhLst;
import cn.com.chinaebi.dz.object.OriginalZhongxingbankLst;
import cn.com.chinaebi.dz.object.RiqieBeijingbankLst;
import cn.com.chinaebi.dz.object.RiqieCupsLst;
import cn.com.chinaebi.dz.object.RiqieDljhLst;
import cn.com.chinaebi.dz.object.RiqieSzzhLst;
import cn.com.chinaebi.dz.object.RiqieZhongxingbankLst;
import cn.com.chinaebi.dz.object.RytUpmp;
import cn.com.chinaebi.dz.util.OnlineTradeType;

public class TradeBeanValueMap {
	
	public static Log log = LogFactory.getLog(TradeBeanValueMap.class);
	private static cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorData = cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.OriginalZhongxingbankLstDAO originalZhongxinbank = cn.com.chinaebi.dz.object.dao.OriginalZhongxingbankLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.DuizhangCupsLstDAO duizhangCupsDao = cn.com.chinaebi.dz.object.dao.DuizhangCupsLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.DuizhangBeijingbankLstDAO duizhangBeijingbankDao = cn.com.chinaebi.dz.object.dao.DuizhangBeijingbankLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.DuizhangZhongxingbankLstDAO duizhangZhongxinbankDao = cn.com.chinaebi.dz.object.dao.DuizhangZhongxingbankLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.TradeAmountConfDAO tradeAmountConfDao = cn.com.chinaebi.dz.object.dao.TradeAmountConfDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.InstInfoDAO instInfoDao = cn.com.chinaebi.dz.object.dao.InstInfoDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO = cn.com.chinaebi.dz.object.dao.TradeLstDAO.getInstance();
	
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
//	public static final String Original_Trade_Data = "originalTradeData";
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
	public static final String WHETHERERRORHANDLE = "whetherErrorHandle";//长短款标识
	public static final String HANDLERREMARK = "handlerRemark";//结算处理描述
	public static final String JSDATE = "jsDate";//审批时间
//	public static final String CHECKTIME = "checkTime";//审批时间
	public static final String HANDLINGNAME = "handlingName";//差错处理方式
	public static final String DEDUCTROLLBK = "deductRollBk";
	public static final String OPERATOR = "operator";
	
	public static final String MERNAME = "merName";//商户基本信息中的商户名称
	
	
	
	//线上收款属性名
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String IP = "ip";
	public static final String MDATE = "mdate";
	public static final String MID = "mid";
	public static final String BID = "bid";
	public static final String OID = "oid";
	public static final String AMOUNT = "amount";
	public static final String PAY_AMT = "pay_amt";
	public static final String TYPE = "type";
	public static final String GATE = "gate";
	public static final String SYS_DATE = "sys_date";
	public static final String INIT_SYS_DATE = "init_sys_date";
	public static final String SYS_TIME = "sys_time";
	public static final String BATCH = "batch";
	public static final String FEE_AMT = "fee_amt";
	public static final String BANK_FEE = "bank_fee";
	public static final String TSTAT = "tstat";
	public static final String BK_FLAG = "bk_flag";
	public static final String ORG_SEQ = "org_seq";
	public static final String REF_SEQ = "ref_seq";
	public static final String REFUND_AMT = "refund_amt";
	public static final String MER_PRIV = "mer_priv";
	public static final String BK_SEND = "bk_send";
	public static final String BK_RECV = "bk_recv";
	public static final String BK_URL = "bk_url";
	public static final String FG_UR = "fg_url";
	public static final String BK_CHK = "bk_chk";
	public static final String BK_DATE = "bk_date";
	public static final String BK_SEQ1 = "bk_seq1";
	public static final String BK_SEQ2 = "bk_seq2";
	public static final String BK_RESP = "bk_resp";
	public static final String MOBILE_NO = "mobile_no";
	public static final String TRANS_PERIOD = "trans_period";
	public static final String CARD_NO = "card_no";
	public static final String ERROR_CODE = "error_code";
	public static final String AUTHOR_TYPE = "author_type";
	public static final String PHONE_NO = "phone_no";
	public static final String OPER_ID = "oper_id";
	public static final String GID = "gid";
	public static final String PRE_AMT = "pre_amt";
	public static final String BK_FEE_MODEL = "bk_fee_model";
	public static final String PRE_AMT1 = "pre_amt1";
	public static final String ERROR_MSG = "error_msg";
	public static final String P1 = "p1";
	public static final String P2 = "p2";
	public static final String P3 = "p3";
	public static final String P4 = "p4";
	public static final String P5 = "p5";
	public static final String P6 = "p6";
	public static final String P7 = "p7";
	public static final String P8 = "p8";
	public static final String P9 = "p9";
	public static final String P10 = "p10";
	public static final String P11 = "p11";
	public static final String P12 = "p12";
	public static final String P13 = "p13";
	public static final String P14 = "p14";
	public static final String P15 = "p15";
	public static final String IS_LIQ = "is_liq";
	public static final String IS_NOTICE = "is_notice";
	public static final String DATA_SOURCE = "data_source";
	public static final String CURRENCY = "currency";
	public static final String EXCHANGE_RATE = "exchange_rate";
	public static final String AGAINPAY_STATUS = "againPay_status";
	public static final String WHETHERJS = "whetherJs";
	public static final String WHETHERVALID = "whetherValid";
	public static final String WHETHERERROR = "whetherErroeHandle";
	public static final String WHETHERRIQIE = "whetherRiqie";
	
	
	//线上退款属性名称
	public static String PROP_REF_AMT = "refAmt";
	public static String PROP_MID = "mid";
	public static String PROP_MER_PRIV = "merPriv";
	public static String PROP_OID = "oid";
	public static String PROP_USER_NAME = "userName";
	public static String PROP_WHETHER_RIQIE = "whetherRiqie";
	public static String PROP_ONLINE_REFUND_REASON = "onlineRefundReason";
	public static String PROP_MDATE = "mdate";
	public static String PROP_REF_DATE = "refDate";
	public static String PROP_PRE_AMT1 = "preAmt1";
	public static String PROP_CARD_NO = "cardNo";
	public static String PROP_BK_CHK = "bkChk";
	public static String PROP_BATCH = "batch";
	public static String PROP_ORG_MDATE = "orgMdate";
	public static String PROP_ORG_BK_SEQ = "orgBkSeq";
	public static String PROP_AUTHOR_TYPE = "authorType";
	public static String PROP_REFUND_TYPE = "refundType";
	public static String PROP_CURRENCY = "currency";
	public static String PROP_ORG_AMT = "orgAmt";
	public static String PROP_GATE = "gate";
	public static String PROP_REASON = "reason";
	public static String PROP_BK_FEE = "bkFee";
	public static String PROP_SYS_DATE = "sysDate";
	public static String PROP_ORG_OID = "orgOid";
	public static String PROP_REFUND_REASON = "refundReason";
	public static String PROP_BG_RET_URL = "bgRetUrl";
	public static String PROP_ONLINE_REFUND_ID = "onlineRefundId";
	public static String PROP_GID = "gid";
	public static String PROP_REQ_DATE = "reqDate";
	public static String PROP_ONLINE_REFUND_STATE = "onlineRefundState";
	public static String PROP_P1 = "p1";
	public static String PROP_PRE_AMT = "preAmt";
	public static String PROP_BK_FEE_REAL = "bkFeeReal";
	public static String PROP_MER_FEE = "merFee";
	public static String PROP_PRO_DATE = "proDate";
	public static String PROP_STAT = "stat";
	public static String PROP_VSTATE = "vstate";
	public static String PROP_EXCHANGE_RATE = "exchangeRate";
	public static String PROP_ORG_PAY_AMT = "orgPayAmt";
	public static String PROP_ETRO_REASON = "etroReason";
	public static String PROP_WHETHER_VALID = "whetherValid";
	public static String PROP_WHETHER_JS = "whetherJs";
	public static String PROP_ID = "id";
	public static String PROP_TSEQ = "tseq";
	public static String PROP_WHETHER_ERROE_HANDLE = "whetherErroeHandle";
	public static String PROP_SYS_TIME = "sysTime";
	public static String PROP_ORI_SYS_DATE_TIME = "oriSysDateTime";
	
	
	private static DuizhangBeijingbankLst duizhangBeijingbankLst;
	private static DuizhangCupsLst duizhangCupsLst;
	private static DuizhangZhongxingbankLst duizhangZhongxingbankLst;
	
	private static OriginalBeijingbankLst originalBeijingbankLst;
	private static OriginalCupsLst originalCupsLst;
	private static OriginalZhongxingbankLst originalZhongxingbankLst;
	private static OriginalDljhLst originalDljhLst;
	private static OriginalSzzhLst originalSzzhLst;
	//内部清算实体对象
	private static ErrorDataLst errorDataLst;
	
	private static RiqieBeijingbankLst riqieBeijingbankLst;
	private static RiqieCupsLst riqieCupsLst;
	private static RiqieZhongxingbankLst riqieZhongxingbankLst;
	private static RiqieDljhLst riqieDljhLst;
	private static RiqieSzzhLst riqieSzzhLst;
	
	private static RytUpmp rytUpmp;
	
	/**
	 * 对账实体对象操作--针对excel文件
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getDzFileValueOfExcel(String propertyName, Object obj, int instId)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		
		if(instId == 70001){
			if(obj instanceof DuizhangBeijingbankLst){
				duizhangBeijingbankLst = (DuizhangBeijingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getTradeFee();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getOutAccount();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getReqSysStance();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getMerCode();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getReqTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getReqInputType();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getDeductSysReference();
				}
			}
			else
				log.error("70001 对应的对象 "+obj+" 错误");
		}else if(instId == 11){
			if(obj instanceof DuizhangCupsLst){
				duizhangCupsLst = (DuizhangCupsLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getTradeFee();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getOutAccount();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getReqSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getDeductSysResponse();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getReqTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getReqInputType();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getProcess();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getMerCode();
				}
			}
			else
				log.error("11 对应的对象 "+obj+" 错误");
		}else if(instId == 10){
			if(obj instanceof DuizhangZhongxingbankLst){
				duizhangZhongxingbankLst = (DuizhangZhongxingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getTradeFee();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getOutAccount();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getReqResponse();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getDeductMerCode();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getDeductMerTermId();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqType();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getDeductStlmDate();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getDeductSysReference();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getMerName();
				}
			}
			else
				log.error("10对应的对象 "+obj+" 错误");
		}
		return value;
	}
	
	/**
	 * 对账实体对象操作--针对txt文件
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getDzFileValueOfTxt(String propertyName, Object obj, int instId)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		
		if(instId == 70001){
			if(obj instanceof DuizhangBeijingbankLst){
				duizhangBeijingbankLst = (DuizhangBeijingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getTradeFee();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getOutAccount();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getReqSysStance();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getMerCode();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getReqTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getReqInputType();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankLst.getDeductSysReference();
				}
			}
			else
				log.error("70001 对应的对象 "+obj+" 错误");
		}else if(instId == 11){
			if(obj instanceof DuizhangCupsLst){
				duizhangCupsLst = (DuizhangCupsLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getTradeFee();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getOutAccount();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getReqSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getDeductSysResponse();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getReqTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getReqInputType();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getProcess();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsLst.getMerCode();
				}
			}
			else
				log.error("11 对应的对象 "+obj+" 错误");
		}else if(instId == 10){
			if(obj instanceof DuizhangZhongxingbankLst){
				duizhangZhongxingbankLst = (DuizhangZhongxingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getTradeFee();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getOutAccount();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getReqResponse();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getDeductMerCode();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getDeductMerTermId();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqType();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getDeductStlmDate();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getDeductSysReference();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = duizhangZhongxingbankLst.getMerName();
				}
			}
			else
				log.error("10对应的对象 "+obj+" 错误");
		}
		return value;
	}
	
	
	/**
	 * 原笔日切实体对象操作--针对excel文件
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getRiqieValueOfExcel(String propertyName, Object obj, int instId)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		
		if(instId == 70001){
			if(obj instanceof RiqieBeijingbankLst){
				riqieBeijingbankLst = (RiqieBeijingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getMerName();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqSysStance();
				}
			}
			else
				log.error("70001 对应的对象 "+obj+" 错误");
		}else if(instId == 10){
			if(obj instanceof RiqieZhongxingbankLst){
				riqieZhongxingbankLst = (RiqieZhongxingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getMerName();
				}
			}
			else
				log.error("10 对应的对象 "+obj+" 错误");
		}else if(instId == 11){
			if(obj instanceof RiqieCupsLst){
				riqieCupsLst = (RiqieCupsLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getMerName();
				}
			}
			else
				log.error("11 对应的对象 "+obj+" 错误");
		}else if(instId == 14){
			if(obj instanceof RiqieDljhLst){
				riqieDljhLst = (RiqieDljhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = riqieDljhLst.getMerName();
				}
			}
			else
				log.error("14 对应的对象 "+obj+" 错误");
		}else if(instId == 3){
			if(obj instanceof RiqieSzzhLst){
				riqieSzzhLst = (RiqieSzzhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = riqieSzzhLst.getMerName();
				}
			}
			else
				log.error("3对应的对象 "+obj+" 错误");
		}else
			log.error("无对应的匹配对象 "+obj+" 错误");
		return value;
	}
	
	/**
	 * 原笔日切实体对象操作--针对txt文件
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getRiqieValueOfTxt(String propertyName, Object obj, int instId)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		
		if(instId == 70001){
			if(obj instanceof RiqieBeijingbankLst){
				riqieBeijingbankLst = (RiqieBeijingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = riqieBeijingbankLst.getMerName();
				}
			}
			else
				log.error("70001 对应的对象 "+obj+" 错误");
		}else if(instId == 10){
			if(obj instanceof RiqieZhongxingbankLst){
				riqieZhongxingbankLst = (RiqieZhongxingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = riqieZhongxingbankLst.getMerName();
				}
			}
			else
				log.error("10 对应的对象 "+obj+" 错误");
		}else if(instId == 11){
			if(obj instanceof RiqieCupsLst){
				riqieCupsLst = (RiqieCupsLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = riqieCupsLst.getMerName();
				}
			}
			else
				log.error("11 对应的对象 "+obj+" 错误");
		}
		return value;
	}
	
	
	
	/**
	 * 原笔实体对象操作--针对excel文件--针对非冲正交易数据
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getOriginalValueOfNotRollBkForExcel(String propertyName, Object obj, int instId)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		if(instId == 70001){
			if(obj instanceof OriginalBeijingbankLst){
				originalBeijingbankLst = (OriginalBeijingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalBeijingbankLst.getTradeAmount()==null?0d:Double.valueOf(originalBeijingbankLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalBeijingbankLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalBeijingbankLst.getTradeFee().substring(1, originalBeijingbankLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = tradeAmountConfDao.getTradeTypeName(originalBeijingbankLst.getTrademsgType(), originalBeijingbankLst.getReqProcess());
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqProcess();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getInAccount()==null?"":originalBeijingbankLst.getInAccount();
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalBeijingbankLst.getTerminalInfo())+"";
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalBeijingbankLst.getTerminalInfo(), errorData);
					value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductMerTermId();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getOriginalTransInfo()) || "null".equals(originalBeijingbankLst.getOriginalTransInfo()))?"":originalBeijingbankLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
//					value = StringUtils.isBlank(originalBeijingbank.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(),originalBeijingbankLst.getTrademsgType(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10)))?"":originalBeijingbank.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(),originalBeijingbankLst.getTrademsgType(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10));
					value = StringUtils.isBlank(tradeLstDAO.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(), originalBeijingbankLst.getTrademsgType(), originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10), ""))?"":tradeLstDAO.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(), originalBeijingbankLst.getTrademsgType(), originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10), "original_beijingbank_lst");
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductStlmDate()==null?"":(originalBeijingbankLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getAuthorizationCode()==null?"":originalBeijingbankLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalBeijingbankLst.getDeductSysReference())?"":originalBeijingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysTime()==null?"":(originalBeijingbankLst.getDeductSysTime()+"").substring(0, (originalBeijingbankLst.getDeductSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getAdditionalData()==null?"":originalBeijingbankLst.getAdditionalData();
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					String date = originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10);
					value = duizhangBeijingbankDao.getAcceptorPayFeeByTraceFromDzBjbankFile(originalBeijingbankLst.getReqSysStance(),date);
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(originalBeijingbankLst.getDeductSysId(),0);
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(originalBeijingbankLst.getDeductSysId(),0);
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqSysStance();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("70001 对应的对象 "+obj+" 错误");
		}else if(instId == 10){
			if(obj instanceof OriginalZhongxingbankLst){
				originalZhongxingbankLst = (OriginalZhongxingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getAuthorizationCode()==null?"":originalZhongxingbankLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getMerName();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("10 对应的对象 "+obj+" 错误");
		}else if(instId == 11){
			if(obj instanceof OriginalCupsLst){
				originalCupsLst = (OriginalCupsLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalCupsLst.getTradeAmount()==null?0d:Double.valueOf(originalCupsLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalCupsLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalCupsLst.getTradeFee().substring(1, originalCupsLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = tradeAmountConfDao.getTradeTypeName(originalCupsLst.getTrademsgType(), originalCupsLst.getReqProcess());
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqProcess();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getInAccount()==null?"":originalCupsLst.getInAccount();
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalCupsLst.getTerminalInfo())+"";
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalCupsLst.getTerminalInfo(), errorData);
					value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductMerTermId();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getOriginalTransInfo()) || "null".equals(originalCupsLst.getOriginalTransInfo()))?"":originalCupsLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
//					value = originalCups.getOriginalTradeTimeOfCancel(originalCupsLst.getOriginalTransInfo(),originalCupsLst.getTrademsgType(),originalCupsLst.getDeductStlmDate().toString().substring(0, 10));
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalCupsLst.getOriginalTransInfo(), originalCupsLst.getTrademsgType(), originalCupsLst.getDeductStlmDate().toString().substring(0, 10), "original_cups_lst");
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductStlmDate()==null?"":(originalCupsLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getAuthorizationCode()==null?"":originalCupsLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysTime()==null?"":(originalCupsLst.getDeductSysTime()+"").substring(0, (originalCupsLst.getDeductSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getAdditionalData()==null?"":originalCupsLst.getAdditionalData();
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					String date = originalCupsLst.getDeductStlmDate().toString().substring(0, 10);
					value = duizhangCupsDao.getAcceptorPayFeeByTraceFromDzCupsFile(originalCupsLst.getReqSysStance(),date);
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(originalCupsLst.getDeductSysId(),0);
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(originalCupsLst.getDeductSysId(),0);
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqSysStance();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.isDeductRollBk()?"1":"0";
				}
			}else
				log.error("11 对应的对象 "+obj+" 错误");
		}else if(instId == 14){
			if(obj instanceof OriginalDljhLst){
				originalDljhLst = (OriginalDljhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalDljhLst.getTradeAmount()==null?0d:Double.valueOf(originalDljhLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalDljhLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalDljhLst.getTradeFee().substring(1, originalDljhLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = tradeAmountConfDao.getTradeTypeName(originalDljhLst.getTrademsgType(), originalDljhLst.getReqProcess());
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqProcess();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getInAccount()==null?"":originalDljhLst.getInAccount();
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalDljhLst.getTerminalInfo())+"";
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalDljhLst.getTerminalInfo(), errorData);
					value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductMerTermId();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getOriginalTransInfo()) || "null".equals(originalDljhLst.getOriginalTransInfo()))?"":originalDljhLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					String date = originalDljhLst.getDeductStlmDate().toString().substring(0, 10);
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalDljhLst.getOriginalTransInfo(),originalDljhLst.getTrademsgType(),date,"original_dljh_lst");
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductStlmDate()==null?"":(originalDljhLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getAuthorizationCode()==null?"":originalDljhLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysTime()==null?"":(originalDljhLst.getDeductSysTime()+"").substring(0, (originalDljhLst.getDeductSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getAdditionalData()==null?"":originalDljhLst.getAdditionalData();
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getZfFileFee();
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(originalDljhLst.getDeductSysId(),0);
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(originalDljhLst.getDeductSysId(),0);
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqSysStance();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.isDeductRollBk()?"1":"0";
				}
			}else
				log.error("14对应的对象 "+obj+" 错误");
		}else if(instId == 3){
			if(obj instanceof OriginalSzzhLst){
				originalSzzhLst = (OriginalSzzhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalSzzhLst.getTradeAmount()==null?0d:Double.valueOf(originalSzzhLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalSzzhLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalSzzhLst.getTradeFee().substring(1, originalSzzhLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = tradeAmountConfDao.getTradeTypeName(originalSzzhLst.getTrademsgType(), originalSzzhLst.getReqProcess());
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqProcess();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getInAccount()==null?"":originalSzzhLst.getInAccount();
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalSzzhLst.getTerminalInfo())+"";
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalSzzhLst.getTerminalInfo(), errorData);
					value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductMerTermId();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getOriginalTransInfo()) || "null".equals(originalSzzhLst.getOriginalTransInfo()))?"":originalSzzhLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					String date = originalSzzhLst.getDeductStlmDate().toString().substring(0, 10);
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalSzzhLst.getOriginalTransInfo(),originalSzzhLst.getTrademsgType(),date,"original_szzh_lst");
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductStlmDate()==null?"":(originalSzzhLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getAuthorizationCode()==null?"":originalSzzhLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysTime()==null?"":(originalSzzhLst.getDeductSysTime()+"").substring(0, (originalSzzhLst.getDeductSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getAdditionalData()==null?"":originalSzzhLst.getAdditionalData();
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getZfFileFee();
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(originalSzzhLst.getDeductSysId(),0);
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(originalSzzhLst.getDeductSysId(),0);
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqSysStance();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.isDeductRollBk()?"1":"0";
				}
			}else
				log.error("3对应的对象 "+obj+" 错误");
		}
		return value;
	}
	/**
	 * 原笔实体对象操作--针对excel文件--针对冲正交易数据
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getOriginalValueOfRollBkForExcel(String propertyName, Object obj, int instId)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		if(instId == 70001){
			if(obj instanceof OriginalBeijingbankLst){
				originalBeijingbankLst = (OriginalBeijingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalCupsLst.getTradeAmount()==null?0d:Double.valueOf(originalBeijingbankLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalBeijingbankLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalBeijingbankLst.getTradeFee().substring(1, originalBeijingbankLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = tradeAmountConfDao.getTradeTypeName(originalBeijingbankLst.getTrademsgType(), originalBeijingbankLst.getReqProcess());
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqProcess();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getInAccount()==null?"":originalBeijingbankLst.getInAccount();
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalBeijingbankLst.getTerminalInfo())+"";
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalBeijingbankLst.getTerminalInfo(), errorData);
					value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductRollBkStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductMerTermId();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getOriginalTransInfo()) || "null".equals(originalBeijingbankLst.getOriginalTransInfo()))?"":originalBeijingbankLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
//					value = originalBeijingbank.getOriginalTradeTimeOfRollBk(originalBeijingbankLst.getReqSysStance(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10));
					value = tradeLstDAO.getOriginalTradeTimeOfRollBk(originalBeijingbankLst.getReqSysStance(), originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10), "original_beijingbank_lst");
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductStlmDate()==null?"":(originalBeijingbankLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getAuthorizationCode()==null?"":originalBeijingbankLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalBeijingbankLst.getDeductRollbkSysReference())?"":originalBeijingbankLst.getDeductRollbkSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductRollbkSysTime()==null?"":(originalBeijingbankLst.getDeductRollbkSysTime()+"").substring(0, (originalBeijingbankLst.getDeductRollbkSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getAdditionalData()==null?"":originalBeijingbankLst.getAdditionalData();
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					String date = originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10);
					value = duizhangBeijingbankDao.getAcceptorPayFeeByTraceFromDzBjbankFile(originalBeijingbankLst.getDeductRollBkStance(),date);
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(originalBeijingbankLst.getDeductSysId(),0);
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(originalBeijingbankLst.getDeductSysId(),0);
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("70001 对应的对象 "+obj+" 错误");
		}else if(instId == 10){
			if(obj instanceof OriginalZhongxingbankLst){
				originalZhongxingbankLst = (OriginalZhongxingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getAuthorizationCode()==null?"":originalZhongxingbankLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getMerName();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("10 对应的对象 "+obj+" 错误");
		}else if(instId == 11){
			if(obj instanceof OriginalCupsLst){
				originalCupsLst = (OriginalCupsLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalCupsLst.getTradeAmount()==null?0d:Double.valueOf(originalCupsLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalCupsLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalCupsLst.getTradeFee().substring(1, originalCupsLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = tradeAmountConfDao.getTradeTypeName(originalCupsLst.getTrademsgType(), originalCupsLst.getReqProcess());
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqProcess();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getInAccount()==null?"":originalCupsLst.getInAccount();
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalCupsLst.getTerminalInfo())+"";
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalCupsLst.getTerminalInfo(), errorData);
					value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductMerTermId();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getOriginalTransInfo()) || "null".equals(originalCupsLst.getOriginalTransInfo()))?"":originalCupsLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
//					value = originalCups.getOriginalTradeTimeOfRollBk(originalCupsLst.getReqSysStance(),originalCupsLst.getDeductStlmDate().toString().substring(0, 10));
					value = tradeLstDAO.getOriginalTradeTimeOfRollBk(originalCupsLst.getReqSysStance(), originalCupsLst.getDeductStlmDate().toString().substring(0, 10), "original_cups_lst");
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductStlmDate()==null?"":(originalCupsLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getAuthorizationCode()==null?"":originalCupsLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductRollbkSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductRollbkSysTime()==null?"":(originalCupsLst.getDeductRollbkSysTime()+"").substring(0, (originalCupsLst.getDeductRollbkSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getAdditionalData()==null?"":originalCupsLst.getAdditionalData();
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					String date = originalCupsLst.getDeductStlmDate().toString().substring(0, 10);
					value = duizhangCupsDao.getAcceptorPayFeeByTraceFromDzCupsFile(originalCupsLst.getDeductRollBkStance(),date);
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(originalCupsLst.getDeductSysId(),0);
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(originalCupsLst.getDeductSysId(),0);
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("11 对应的对象 "+obj+" 错误");
		}else if(instId == 14){
			if(obj instanceof OriginalDljhLst){
				originalDljhLst = (OriginalDljhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalDljhLst.getTradeAmount()==null?0d:Double.valueOf(originalDljhLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalDljhLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalDljhLst.getTradeFee().substring(1, originalDljhLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = tradeAmountConfDao.getTradeTypeName(originalDljhLst.getTrademsgType(), originalDljhLst.getReqProcess());
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqProcess();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getInAccount()==null?"":originalDljhLst.getInAccount();
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalDljhLst.getTerminalInfo())+"";
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalDljhLst.getTerminalInfo(), errorData);
					value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductRollBkStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductMerTermId();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getOriginalTransInfo()) || "null".equals(originalDljhLst.getOriginalTransInfo()))?"":originalDljhLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					String date = originalDljhLst.getDeductStlmDate().toString().substring(0, 10);
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalDljhLst.getOriginalTransInfo(),originalDljhLst.getTrademsgType(),date,"original_dljh_lst");
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductStlmDate()==null?"":(originalDljhLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getAuthorizationCode()==null?"":originalDljhLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductRollbkSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductRollbkSysTime()==null?"":(originalDljhLst.getDeductRollbkSysTime()+"").substring(0, (originalDljhLst.getDeductRollbkSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getAdditionalData()==null?"":originalDljhLst.getAdditionalData();
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getZfFileFee();
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(originalDljhLst.getDeductSysId(),0);
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(originalDljhLst.getDeductSysId(),0);
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqSysStance();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.isDeductRollBk()?"1":"0";
				}
			}else
				log.error("14对应的对象 "+obj+" 错误");
		}else if(instId == 3){
			if(obj instanceof OriginalSzzhLst){
				originalSzzhLst = (OriginalSzzhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalSzzhLst.getTradeAmount()==null?0d:Double.valueOf(originalSzzhLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalSzzhLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalSzzhLst.getTradeFee().substring(1, originalSzzhLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = tradeAmountConfDao.getTradeTypeName(originalSzzhLst.getTrademsgType(), originalSzzhLst.getReqProcess());
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqProcess();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getInAccount()==null?"":originalSzzhLst.getInAccount();
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalSzzhLst.getTerminalInfo())+"";
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalSzzhLst.getTerminalInfo(), errorData);
					value = (StringUtils.isBlank(trade_name)||"null".equals(trade_name))?"":trade_name;
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductRollBkStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductMerTermId();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getOriginalTransInfo()) || "null".equals(originalSzzhLst.getOriginalTransInfo()))?"":originalSzzhLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					String date = originalSzzhLst.getDeductStlmDate().toString().substring(0, 10);
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalSzzhLst.getOriginalTransInfo(),originalSzzhLst.getTrademsgType(),date,"original_szzh_lst");
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductStlmDate()==null?"":(originalSzzhLst.getDeductStlmDate()+"").substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getAuthorizationCode()==null?"":originalSzzhLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductRollbkSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductRollbkSysTime()==null?"":(originalSzzhLst.getDeductRollbkSysTime()+"").substring(0, (originalSzzhLst.getDeductRollbkSysTime()+"").length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getAdditionalData()==null?"":originalSzzhLst.getAdditionalData();
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getZfFileFee();
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(originalSzzhLst.getDeductSysId(),0);
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(originalSzzhLst.getDeductSysId(),0);
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqSysStance();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.isDeductRollBk()?"1":"0";
				}
			}
		}
		return value;
	}
	/**
	 * 原笔实体对象操作--针对txt文件--非冲正交易
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getOriginalValueOfNotRollBkForTxt(String propertyName, Object obj, int instId,String columnLength)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		if(instId == 70001){
			if(obj instanceof OriginalBeijingbankLst){
				originalBeijingbankLst = (OriginalBeijingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalBeijingbankLst.getTradeAmount()==null?0d:Double.valueOf(originalBeijingbankLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalBeijingbankLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalBeijingbankLst.getTradeFee().substring(1, originalBeijingbankLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalBeijingbankLst.getTrademsgType(), originalBeijingbankLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getOutAccount()==null?"":originalBeijingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getInAccount()) || "null".equals(originalBeijingbankLst.getInAccount()))?"":originalBeijingbankLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductMerTermId();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductStlmDate()==null?"":originalBeijingbankLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getAuthorizationCode()) || "null".equals(originalBeijingbankLst.getAuthorizationCode()))?"":originalBeijingbankLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalBeijingbankLst.getMerName())?"":new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalBeijingbankLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysTime()==null?"":originalBeijingbankLst.getDeductSysTime().toString().substring(0, originalBeijingbankLst.getDeductSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalBeijingbankLst.getTerminalInfo());
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalBeijingbankLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
	            	value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getAdditionalData()) || "null".equals(originalBeijingbankLst.getAdditionalData()))?"":originalBeijingbankLst.getAdditionalData().length()>=20?originalBeijingbankLst.getAdditionalData().substring(0, 20):originalBeijingbankLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getOriginalTransInfo()) || "null".equals(originalBeijingbankLst.getOriginalTransInfo()))?"":originalBeijingbankLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
//					value = "".equals(originalBeijingbank.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(),originalBeijingbankLst.getTrademsgType(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10)))?"":originalCups.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(),originalBeijingbankLst.getTrademsgType(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10));
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalBeijingbankLst.getOriginalTransInfo(), originalBeijingbankLst.getTrademsgType(), originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10), "original_beijingbank_lst");
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalBeijingbankLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalBeijingbankLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankDao.getAcceptorPayFeeByTraceFromDzBjbankFile(originalBeijingbankLst.getReqSysStance(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10));
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("70001 对应的对象 "+obj+" 错误");
		}else if(instId == 10){
			if(obj instanceof OriginalZhongxingbankLst){
				originalZhongxingbankLst = (OriginalZhongxingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getMerName();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("10 对应的对象 "+obj+" 错误");
		}else if(instId == 11){
			if(obj instanceof OriginalCupsLst){
				originalCupsLst = (OriginalCupsLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalCupsLst.getTradeAmount()==null?0d:Double.valueOf(originalCupsLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalCupsLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalCupsLst.getTradeFee().substring(1, originalCupsLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalCupsLst.getTrademsgType(), originalCupsLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getOutAccount()==null?"":originalCupsLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getInAccount()) || "null".equals(originalCupsLst.getInAccount()))?"":originalCupsLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductMerTermId();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductStlmDate()==null?"":originalCupsLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getAuthorizationCode()) || "null".equals(originalCupsLst.getAuthorizationCode()))?"":originalCupsLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalCupsLst.getMerName())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalCupsLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysTime()==null?"":originalCupsLst.getDeductSysTime().toString().substring(0, originalCupsLst.getDeductSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalCupsLst.getTerminalInfo());
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalCupsLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
	            	value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getAdditionalData()) || "null".equals(originalCupsLst.getAdditionalData()))?"":originalCupsLst.getAdditionalData().length()>=20?originalCupsLst.getAdditionalData().substring(0, 20):originalCupsLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getOriginalTransInfo()) || "null".equals(originalCupsLst.getOriginalTransInfo()))?"":originalCupsLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
//					value = "".equals(originalCups.getOriginalTradeTimeOfCancel(originalCupsLst.getOriginalTransInfo(),originalCupsLst.getTrademsgType(),originalCupsLst.getDeductStlmDate().toString().substring(0, 10)))?"":originalCups.getOriginalTradeTimeOfCancel(originalCupsLst.getOriginalTransInfo(),originalCupsLst.getTrademsgType(),originalCupsLst.getDeductStlmDate().toString().substring(0, 10));
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalCupsLst.getOriginalTransInfo(), originalCupsLst.getTrademsgType(), originalCupsLst.getDeductStlmDate().toString().substring(0, 10), "original_cups_lst");
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalCupsLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalCupsLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsDao.getAcceptorPayFeeByTraceFromDzCupsFile(originalCupsLst.getReqSysStance(),originalCupsLst.getDeductStlmDate().toString().substring(0, 10));
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("11 对应的对象 "+obj+" 错误");
		}else if(instId == 14){
			if(obj instanceof OriginalDljhLst){
				originalDljhLst = (OriginalDljhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalDljhLst.getTradeAmount()==null?0d:Double.valueOf(originalDljhLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalDljhLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalDljhLst.getTradeFee().substring(1, originalDljhLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalDljhLst.getTrademsgType(), originalDljhLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getOutAccount()==null?"":originalDljhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getInAccount()) || "null".equals(originalDljhLst.getInAccount()))?"":originalDljhLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductMerTermId();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductStlmDate()==null?"":originalDljhLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getAuthorizationCode()) || "null".equals(originalDljhLst.getAuthorizationCode()))?"":originalDljhLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalDljhLst.getMerName())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalDljhLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysTime()==null?"":originalDljhLst.getDeductSysTime().toString().substring(0, originalDljhLst.getDeductSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalDljhLst.getTerminalInfo());
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalDljhLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
	            	value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getAdditionalData()) || "null".equals(originalDljhLst.getAdditionalData()))?"":originalDljhLst.getAdditionalData().length()>=20?originalCupsLst.getAdditionalData().substring(0, 20):originalCupsLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getOriginalTransInfo()) || "null".equals(originalDljhLst.getOriginalTransInfo()))?"":originalDljhLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalDljhLst.getOriginalTransInfo(), originalDljhLst.getTrademsgType(), originalDljhLst.getDeductStlmDate().toString().substring(0, 10), "original_dljh_lst");
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalDljhLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalDljhLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getZfFileFee();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.isDeductRollBk()?"1":"0";
				}
			}else
				log.error("14对应的对象 "+obj+" 错误");
		}else if(instId == 3){
			if(obj instanceof OriginalSzzhLst){
				originalSzzhLst = (OriginalSzzhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalSzzhLst.getTradeAmount()==null?0d:Double.valueOf(originalSzzhLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalSzzhLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalSzzhLst.getTradeFee().substring(1, originalSzzhLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalSzzhLst.getTrademsgType(), originalSzzhLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getOutAccount()==null?"":originalSzzhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getInAccount()) || "null".equals(originalSzzhLst.getInAccount()))?"":originalSzzhLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductMerTermId();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductStlmDate()==null?"":originalSzzhLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getAuthorizationCode()) || "null".equals(originalSzzhLst.getAuthorizationCode()))?"":originalSzzhLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalSzzhLst.getMerName())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalSzzhLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysTime()==null?"":originalSzzhLst.getDeductSysTime().toString().substring(0, originalSzzhLst.getDeductSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalSzzhLst.getTerminalInfo());
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalSzzhLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
	            	value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getAdditionalData()) || "null".equals(originalSzzhLst.getAdditionalData()))?"":originalSzzhLst.getAdditionalData().length()>=20?originalSzzhLst.getAdditionalData().substring(0, 20):originalSzzhLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getOriginalTransInfo()) || "null".equals(originalSzzhLst.getOriginalTransInfo()))?"":originalSzzhLst.getOriginalTransInfo();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalSzzhLst.getOriginalTransInfo(), originalSzzhLst.getTrademsgType(), originalSzzhLst.getDeductStlmDate().toString().substring(0, 10), "original_szzh_lst");
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalSzzhLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalSzzhLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getZfFileFee();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.isDeductRollBk()?"1":"0";
				}
			}
		}
		return value;
	}
	/**
	 * 原笔实体对象操作--针对txt文件--冲正交易
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getOriginalValueOfRollBkForTxt(String propertyName, Object obj, int instId,String columnLength)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
		if(instId == 70001){
			if(obj instanceof OriginalBeijingbankLst){
				originalBeijingbankLst = (OriginalBeijingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalBeijingbankLst.getTradeAmount()==null?0d:Double.valueOf(originalBeijingbankLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalBeijingbankLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalBeijingbankLst.getTradeFee().substring(1, originalBeijingbankLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalBeijingbankLst.getTrademsgType(), originalBeijingbankLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getOutAccount()==null?"":originalBeijingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getInAccount()) || "null".equals(originalBeijingbankLst.getInAccount()))?"":originalBeijingbankLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductMerTermId();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductStlmDate()==null?"":originalBeijingbankLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getAuthorizationCode()) || "null".equals(originalBeijingbankLst.getAuthorizationCode()))?"":originalBeijingbankLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductRollbkSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalBeijingbankLst.getMerName())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalBeijingbankLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getDeductRollbkSysTime()==null?"":originalBeijingbankLst.getDeductRollbkSysTime().toString().substring(0, originalBeijingbankLst.getDeductRollbkSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalBeijingbankLst.getTerminalInfo());
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalBeijingbankLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalBeijingbankLst.getAdditionalData()) || "null".equals(originalBeijingbankLst.getAdditionalData()))?"":originalBeijingbankLst.getAdditionalData().length()>=20?originalBeijingbankLst.getAdditionalData().substring(0, 20):originalBeijingbankLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getReqSysStance();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
//					value = originalBeijingbank.getOriginalTradeTimeOfRollBk(originalBeijingbankLst.getReqSysStance(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10));
					value = tradeLstDAO.getOriginalTradeTimeOfRollBk(originalBeijingbankLst.getReqSysStance(), originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10), "original_beijingbank_lst");
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalBeijingbankLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalBeijingbankLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = duizhangBeijingbankDao.getAcceptorPayFeeByTraceFromDzBjbankFile(originalBeijingbankLst.getDeductRollBkStance(),originalBeijingbankLst.getDeductStlmDate().toString().substring(0, 10));
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalBeijingbankLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("70001 对应的对象 "+obj+" 错误");
		}else if(instId == 10){
			if(obj instanceof OriginalZhongxingbankLst){
				originalZhongxingbankLst = (OriginalZhongxingbankLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeAmount();
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeFee();
				}else if(TRADE_CURRENCY.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeCurrency();
				}else if(TRADE_RESULT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeResult();
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getInAccount();
				}else if(REQ_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqSysId();
				}else if(REQ_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqSysStance();
				}else if(REQ_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqResponse();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_SYS_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysResponse();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductMerTermId();
				}else if(REQ_TIME.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqTime();
				}else if(TRADE_DATETIME.equalsIgnoreCase(propertyName) || TRADE_DATE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeTime();
				}else if(REQ_TYPE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqType();
				}else if(REQ_INPUT_TYPE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqInputType();
				}else if(DEDUCT_ROLL_BK_REASON.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkReason();
				}else if(DEDUCT_ROLLBACK_RESPONSE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkResponse();
				}else if(BALANCE_INFO.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeOtherInfo();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductStlmDate();
				}else if(TRADE_DESC.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getTradeDesc();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysStance();
				}else if(DEDUCT_ROLLBACK_STANCE.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductRollBkStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.getMerName();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalZhongxingbankLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("10 对应的对象 "+obj+" 错误");
		}else if(instId == 11){
			if(obj instanceof OriginalCupsLst){
				originalCupsLst = (OriginalCupsLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalCupsLst.getTradeAmount()==null?0d:Double.valueOf(originalCupsLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalCupsLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalCupsLst.getTradeFee().substring(1, originalCupsLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalCupsLst.getTrademsgType(), originalCupsLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getOutAccount()==null?"":originalCupsLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getInAccount()) || "null".equals(originalCupsLst.getInAccount()))?"":originalCupsLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductMerTermId();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductStlmDate()==null?"":originalCupsLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getAuthorizationCode()) || "null".equals(originalCupsLst.getAuthorizationCode()))?"":originalCupsLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductRollbkSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalCupsLst.getMerName())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalCupsLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getDeductRollbkSysTime()==null?"":originalCupsLst.getDeductRollbkSysTime().toString().substring(0, originalCupsLst.getDeductRollbkSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalCupsLst.getTerminalInfo());
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalCupsLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getAdditionalData()) || "null".equals(originalCupsLst.getAdditionalData()))?"":originalCupsLst.getAdditionalData().length()>=20?originalCupsLst.getAdditionalData().substring(0, 20):originalCupsLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalCupsLst.getReqSysStance()) || "null".equals(originalCupsLst.getReqSysStance()))?"":originalCupsLst.getReqSysStance();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
//					value = originalCups.getOriginalTradeTimeOfRollBk(originalCupsLst.getReqSysStance(),originalCupsLst.getDeductStlmDate().toString().substring(0, 10));
					value = tradeLstDAO.getOriginalTradeTimeOfRollBk(originalCupsLst.getReqSysStance(), originalCupsLst.getDeductStlmDate().toString().substring(0, 10), "original_cups_lst");
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalCupsLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalCupsLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = duizhangCupsDao.getAcceptorPayFeeByTraceFromDzCupsFile(originalCupsLst.getDeductRollBkStance(),originalCupsLst.getDeductStlmDate().toString().substring(0, 10));
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalCupsLst.isDeductRollBk()?"1":"0";
				}
			}
			else
				log.error("11 对应的对象 "+obj+" 错误");
		}else if(instId == 14){
			if(obj instanceof OriginalDljhLst){
				originalDljhLst = (OriginalDljhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalDljhLst.getTradeAmount()==null?0d:Double.valueOf(originalDljhLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalDljhLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalDljhLst.getTradeFee().substring(1, originalDljhLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalDljhLst.getTrademsgType(), originalDljhLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getOutAccount()==null?"":originalDljhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getInAccount()) || "null".equals(originalDljhLst.getInAccount()))?"":originalDljhLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductRollBkStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductMerTermId();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductStlmDate()==null?"":originalDljhLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getAuthorizationCode()) || "null".equals(originalDljhLst.getAuthorizationCode()))?"":originalDljhLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductRollbkSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalDljhLst.getMerName())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalDljhLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getDeductRollbkSysTime()==null?"":originalDljhLst.getDeductRollbkSysTime().toString().substring(0, originalDljhLst.getDeductRollbkSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalDljhLst.getTerminalInfo());
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalDljhLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
	            	value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getAdditionalData()) || "null".equals(originalDljhLst.getAdditionalData()))?"":originalDljhLst.getAdditionalData().length()>=20?originalCupsLst.getAdditionalData().substring(0, 20):originalCupsLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalDljhLst.getReqSysStance()) || "null".equals(originalDljhLst.getReqSysStance()))?"":originalDljhLst.getReqSysStance();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalDljhLst.getReqSysStance(), originalDljhLst.getTrademsgType(), originalDljhLst.getDeductStlmDate().toString().substring(0, 10), "original_dljh_lst");
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalDljhLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalDljhLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.getZfFileFee();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalDljhLst.isDeductRollBk()?"1":"0";
				}
			}else
				log.error("14对应的对象 "+obj+" 错误");
		}else if(instId == 3){
			if(obj instanceof OriginalSzzhLst){
				originalSzzhLst = (OriginalSzzhLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",originalSzzhLst.getTradeAmount()==null?0d:Double.valueOf(originalSzzhLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalSzzhLst.getTradeFee())?"0.0":String.format("%.2f",Double.valueOf(originalSzzhLst.getTradeFee().substring(1, originalSzzhLst.getTradeFee().length()))/100);
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(originalSzzhLst.getTrademsgType(), originalSzzhLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getOutAccount()==null?"":originalSzzhLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getInAccount()) || "null".equals(originalSzzhLst.getInAccount()))?"":originalSzzhLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductRollBkStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductMerTermId();
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductStlmDate()==null?"":originalSzzhLst.getDeductStlmDate().toString().substring(0,10).replace("-", "");
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getAuthorizationCode()) || "null".equals(originalSzzhLst.getAuthorizationCode()))?"":originalSzzhLst.getAuthorizationCode();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductRollbkSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(originalSzzhLst.getMerName())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(originalSzzhLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getDeductRollbkSysTime()==null?"":originalSzzhLst.getDeductRollbkSysTime().toString().substring(0, originalSzzhLst.getDeductRollbkSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					value = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(originalSzzhLst.getTerminalInfo());
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String tradeName = new String(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(originalSzzhLst.getTerminalInfo(), errorData).getBytes("GBK"),"ISO-8859-1");
	            	value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(tradeName) || "null".equals(tradeName))?"":tradeName).getBytes("ISO-8859-1"),"GBK");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getAdditionalData()) || "null".equals(originalSzzhLst.getAdditionalData()))?"":originalSzzhLst.getAdditionalData().length()>=20?originalSzzhLst.getAdditionalData().substring(0, 20):originalSzzhLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(originalSzzhLst.getReqSysStance()) || "null".equals(originalSzzhLst.getReqSysStance()))?"":originalSzzhLst.getReqSysStance();
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					value = tradeLstDAO.getOriginalTradeTimeOfCancel(originalSzzhLst.getReqSysStance(), originalSzzhLst.getTrademsgType(), originalSzzhLst.getDeductStlmDate().toString().substring(0, 10), "original_szzh_lst");
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(originalSzzhLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(originalSzzhLst.getDeductSysId(),0).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getTerminalId();
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getAgentId();
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.getZfFileFee();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = originalSzzhLst.isDeductRollBk()?"1":"0";
				}
			}
		}
		return value;
	}
	
	/**
	 * 原笔差错实体对象操作--针对Txt文件
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getOriginalErrorValueOfTxt(String propertyName, Object obj, int instId,String columnLength)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = null;
			if(obj instanceof ErrorDataLst){
				errorDataLst = (ErrorDataLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",errorDataLst.getTradeAmount()==null?0d:Double.valueOf(errorDataLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					if(errorDataLst.getReqProcess() != null && StringUtils.equals(errorDataLst.getReqProcess(), "480000")){
						value = (StringUtils.isBlank(errorDataLst.getTradeFee()) || "0".equals(errorDataLst.getTradeFee()) || "0.0".equals(errorDataLst.getTradeFee()))?"0.0":String.format("%.2f",Double.valueOf(errorDataLst.getTradeFee().substring(1, errorDataLst.getTradeFee().length()))/100);
					}else{
						value = (StringUtils.isBlank(errorDataLst.getTradeFee()) || "0".equals(errorDataLst.getTradeFee()) || "0.0".equals(errorDataLst.getTradeFee()))?"0.0":String.format("%.2f",Double.valueOf(errorDataLst.getTradeFee().substring(0, errorDataLst.getTradeFee().length()))/100);
					}
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getOutAccount()==null?"":errorDataLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getInAccount())||"null".equals(errorDataLst.getInAccount()))?"":errorDataLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getReqMerCode()) || "null".equals(errorDataLst.getReqMerCode()))?"":errorDataLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getReqMerTermId()) || "null".equals(errorDataLst.getReqMerTermId()))?"":errorDataLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getDeductMerCode()) || "null".equals(errorDataLst.getDeductMerCode()))?"":errorDataLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getDeductMerTermId()) || "null".equals(errorDataLst.getDeductMerTermId()))?"":errorDataLst.getDeductMerTermId();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getAuthorizationCode()) || "null".equals(errorDataLst.getAuthorizationCode()))?"":errorDataLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = errorDataLst.isDeductRollBk()?errorDataLst.getDeductRollbkSysReference():errorDataLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getReqProcess()) || "null".equals(errorDataLst.getReqProcess()))?"":errorDataLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(errorDataLst.getMerName())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(errorDataLst.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysTime()==null?"":errorDataLst.getDeductSysTime().toString().substring(0, errorDataLst.getDeductSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					String tradeMsg_type = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(tradeAmountConfDao.getTradeTypeName(errorDataLst.getTrademsgType()==null?0:errorDataLst.getTrademsgType(), errorDataLst.getReqProcess()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
					value = StringUtils.isBlank(tradeMsg_type) || "null".equals(tradeMsg_type.trim())?String.format("%-"+Integer.valueOf(columnLength)+"s",""):tradeMsg_type;
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					if(StringUtils.isBlank(errorDataLst.getTerminalInfo())){
						value = errorDataLst.getTrademsgType();//线上的交易类别
					}else{
						value = (StringUtils.isBlank(FindTradeCodeUtil.getNewInstance().tradeCodeSubString(errorDataLst.getTerminalInfo())) || "null".equals(FindTradeCodeUtil.getNewInstance().tradeCodeSubString(errorDataLst.getTerminalInfo())))?"":FindTradeCodeUtil.getNewInstance().tradeCodeSubString(errorDataLst.getTerminalInfo());
					}
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					String trade_name = "";
					if(StringUtils.isNotBlank(errorDataLst.getTerminalInfo())){
						trade_name = new String((FindTradeCodeUtil.getNewInstance().returnTradeCodeName(errorDataLst.getTerminalInfo(), errorData)==null?"":FindTradeCodeUtil.getNewInstance().returnTradeCodeName(errorDataLst.getTerminalInfo(), errorData)).getBytes("GBK"),"ISO-8859-1");
						value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",(StringUtils.isBlank(trade_name) || "null".equals(trade_name))?"":trade_name).getBytes("ISO-8859-1"),"GBK");
					}else{
						if(errorDataLst.getTrademsgType() != null){
							trade_name = errorDataLst.getTrademsgType()==2?new String("收款".getBytes(),"utf-8"):(errorDataLst.getTrademsgType()==20?new String("退款".getBytes(),"utf-8"):"");
						}else{
							trade_name = new String("未知".getBytes(),"utf-8");
						}
						int length = (StringUtils.isBlank(trade_name) || "null".equals(trade_name))?0:trade_name.length();
						value = String.format("%-"+(Integer.valueOf(columnLength)-length)+"s",(StringUtils.isBlank(trade_name) || "null".equals(trade_name))?"":trade_name);
					}
					
				}else if(WHETHERERRORHANDLE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getWhetherErroeHandle()==null?"":errorDataLst.getWhetherErroeHandle();
				}else if(HANDLERREMARK.equalsIgnoreCase(propertyName)){            	    
					String remark = (StringUtils.isBlank(errorDataLst.getHandlerRemark()) || "null".equals(errorDataLst.getHandlerRemark()))?"":errorDataLst.getHandlerRemark();
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(remark.getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(JSDATE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getJsDate()==null?"":errorDataLst.getJsDate().replaceAll("-", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getAdditionalData()) || "null".equals(errorDataLst.getAdditionalData()))?"":errorDataLst.getAdditionalData().length()>=20?errorDataLst.getAdditionalData().substring(0, 20):errorDataLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					String originalSysStance = "";
					if(errorDataLst.isDeductRollBk()){
						originalSysStance = errorDataLst.getReqSysStance();;
					}else{
						originalSysStance = errorDataLst.getOriginalTransInfo();
					}
					value = (StringUtils.isBlank(originalSysStance) || "null".equals(originalSysStance))?"":originalSysStance;
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					String original_trade_time = "";
					if(instId == 11){
						if(errorDataLst.isDeductRollBk()){
//							original_trade_time = originalCups.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance(),errorDataLst.getDeductStlmDate().toString().substring(0, 10));
							original_trade_time = tradeLstDAO.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance(), errorDataLst.getDeductStlmDate().toString().substring(0, 10), "original_cups_lst");
						}else{
							if(errorDataLst.getTrademsgType() != null){
//								original_trade_time = originalCups.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(),errorDataLst.getTrademsgType(),errorDataLst.getDeductStlmDate().toString().substring(0, 10));
								original_trade_time = tradeLstDAO.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(), errorDataLst.getTrademsgType(), errorDataLst.getDeductStlmDate().toString().substring(0, 10), "original_cups_lst");
							}
						}
					}else if(instId == 70001){
						if(errorDataLst.isDeductRollBk()){
//							original_trade_time = originalBeijingbank.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance(),errorDataLst.getDeductStlmDate().toString().substring(0, 10));
							original_trade_time = tradeLstDAO.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance(), errorDataLst.getDeductStlmDate().toString().substring(0, 10), "original_beijingbank_lst");
						}else{
							if(errorDataLst.getTrademsgType() != null){
//								original_trade_time = originalBeijingbank.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(),errorDataLst.getTrademsgType(),errorDataLst.getDeductStlmDate().toString().substring(0, 10));
								original_trade_time = tradeLstDAO.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(), errorDataLst.getTrademsgType(), errorDataLst.getDeductStlmDate().toString().substring(0, 10), "original_beijingbank_lst");
							}
						}
					}else if(instId == 10){
						if(errorDataLst.isDeductRollBk()){
							original_trade_time = originalZhongxinbank.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance());
						}else{
							original_trade_time = originalZhongxinbank.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(),errorDataLst.getTrademsgType());
						}
					}else if(instId == 55001){
						original_trade_time = errorDataLst.getCustomDefineInfo();
					}
					value = (StringUtils.isBlank(original_trade_time) || "null".equals(original_trade_time))?"":original_trade_time;
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					String acceptor_pay_fee = "";
					if(instId == 11 || instId == 55001){
						if(errorDataLst.isDeductRollBk()){
							acceptor_pay_fee = duizhangCupsDao.getAcceptorReceiveFeeByTraceFromDzCupsFile(errorDataLst.getDeductRollBkStance());
						}else{
							acceptor_pay_fee =  duizhangCupsDao.getAcceptorPayFeeByTraceFromDzCupsFile(errorDataLst.getReqSysStance());
						}
					}else if(instId == 70001){
						if(errorDataLst.isDeductRollBk()){
							acceptor_pay_fee = duizhangBeijingbankDao.getAcceptorReceiveFeeByTraceFromDzBjFile(errorDataLst.getDeductRollBkStance());
						}else{
							acceptor_pay_fee = duizhangBeijingbankDao.getAcceptorPayFeeByTraceFromDzBjFile(errorDataLst.getReqSysStance());
						}
					}else if(instId == 10){
						if(errorDataLst.isDeductRollBk()){
							acceptor_pay_fee = duizhangZhongxinbankDao.getAcceptorPayFeeByTraceFromDzZxFile(errorDataLst.getDeductRollBkStance());
						}else{
							acceptor_pay_fee = duizhangZhongxinbankDao.getAcceptorPayFeeByTraceFromDzZxFile(errorDataLst.getReqSysStance());
						}
					}
					value = acceptor_pay_fee;
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getReceiviNameById(errorDataLst.getDeductSysId(),errorDataLst.getInstType()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(instInfoDao.getInstInfoNameById(errorDataLst.getDeductSysId(),errorDataLst.getInstType()).getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getTerminalId()) || "null".equals(errorDataLst.getTerminalId()))?"":errorDataLst.getTerminalId();
				}else if(HANDLINGNAME.equalsIgnoreCase(propertyName)){
					value = errorData.getInnerErrorHandlingName(errorDataLst.getHandlingId());
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductStlmDate()==null?"":errorDataLst.getDeductStlmDate().toString().substring(0, 10);
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getAgentId()==null?"":errorDataLst.getAgentId();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = errorDataLst.isDeductRollBk()?"1":"0";
				}
		}
		return value;
	}
	/**
	 * 原笔差错实体对象操作--针对excel文件
	 * @param instId : 11 : 银联CUPS、10 ：中信、70001  ：北京银行 
	 */
	public static Object getOriginalErrorValueOfExcel(String propertyName, Object obj, int instId)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
			Object value = null;
			if(obj instanceof ErrorDataLst){
				errorDataLst = (ErrorDataLst)obj;
				if(TRADE_AMOUNT.equalsIgnoreCase(propertyName)){
					value = String.format("%.2f",errorDataLst.getTradeAmount()==null?0d:Double.valueOf(errorDataLst.getTradeAmount())/100);
				}else if(TRADE_FEE.equalsIgnoreCase(propertyName)){
					if(errorDataLst.getReqProcess() != null && StringUtils.equals(errorDataLst.getReqProcess(), "480000")){
						value = (StringUtils.isBlank(errorDataLst.getTradeFee()) || "0".equals(errorDataLst.getTradeFee()) || "0.0".equals(errorDataLst.getTradeFee()))?"0.0":String.format("%.2f",Double.valueOf(errorDataLst.getTradeFee().substring(1, errorDataLst.getTradeFee().length()))/100);
					}else{
						value = (StringUtils.isBlank(errorDataLst.getTradeFee()) || "0".equals(errorDataLst.getTradeFee()) || "0.0".equals(errorDataLst.getTradeFee()))?"0.0":String.format("%.2f",Double.valueOf(errorDataLst.getTradeFee().substring(0, errorDataLst.getTradeFee().length()))/100);
					}
				}else if(OUT_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getOutAccount();
				}else if(IN_ACCOUNT.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getInAccount())||"null".equals(errorDataLst.getInAccount()))?"":errorDataLst.getInAccount();
				}else if(REQ_MER_CODE.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(errorDataLst.getReqMerCode())?"":errorDataLst.getReqMerCode();
				}else if(REQ_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(errorDataLst.getReqMerTermId())?"":errorDataLst.getReqMerTermId();
				}else if(DEDUCT_MER_CODE.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getDeductMerCode())||"null".equals(errorDataLst.getDeductMerCode()))?"":errorDataLst.getDeductMerCode();
				}else if(DEDUCT_SYS_ID.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysId();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysStance();
				}else if(DEDUCT_MER_TERM_ID.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getDeductMerTermId())||"null".equals(errorDataLst.getDeductMerTermId()))?"":errorDataLst.getDeductMerTermId();
				}else if(Authorization_Code.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getAuthorizationCode();
				}else if(DEDUCT_SYS_STANCE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysStance();
				}else if(Deduct_Sys_Reference.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysReference();
				}else if(PROCESS.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getReqProcess();
				}else if(Mer_Name_Or_Address.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getMerName())||"null".equals(errorDataLst.getMerName()))?"":errorDataLst.getMerName();
				}else if(TRADE_TIME.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductSysTime()==null?"":errorDataLst.getDeductSysTime().toString().substring(0, errorDataLst.getDeductSysTime().toString().length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
				}else if(TRADEMSG_TYPE.equalsIgnoreCase(propertyName)){
					String trade_msg_type = tradeAmountConfDao.getTradeTypeName(errorDataLst.getTrademsgType()==null?0:errorDataLst.getTrademsgType(), errorDataLst.getReqProcess());
					value = StringUtils.isBlank(trade_msg_type) || "null".equals(trade_msg_type)?"":trade_msg_type;
				}else if(TRADETYPE.equalsIgnoreCase(propertyName)){
					if(StringUtils.isBlank(errorDataLst.getTerminalInfo())){
						value = errorDataLst.getTrademsgType();
					}else{
						value = (StringUtils.isBlank(FindTradeCodeUtil.getNewInstance().tradeCodeSubString(errorDataLst.getTerminalInfo())) || "null".equals(FindTradeCodeUtil.getNewInstance().tradeCodeSubString(errorDataLst.getTerminalInfo())))?"":FindTradeCodeUtil.getNewInstance().tradeCodeSubString(errorDataLst.getTerminalInfo());
					}
				}else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					if(StringUtils.isBlank(errorDataLst.getTerminalInfo())){
						if(errorDataLst.getTrademsgType() != null){
							value = errorDataLst.getTrademsgType()==2?"收款":(errorDataLst.getTrademsgType()==20?"退款":"");
						}else{
							value = "未知";
						}
					}else{
						value = (StringUtils.isBlank(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(errorDataLst.getTerminalInfo(), errorData)) || "null".equals(FindTradeCodeUtil.getNewInstance().returnTradeCodeName(errorDataLst.getTerminalInfo(), errorData)))?"":FindTradeCodeUtil.getNewInstance().returnTradeCodeName(errorDataLst.getTerminalInfo(), errorData);
					}
				}else if(JSDATE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getJsDate()==null?"":errorDataLst.getJsDate().replaceAll("-", "");
				}else if(Additional_Data.equalsIgnoreCase(propertyName)){
					value = (StringUtils.isBlank(errorDataLst.getAdditionalData()) || "null".equals(errorDataLst.getAdditionalData()))?"":errorDataLst.getAdditionalData();
				}else if(Original_Trans_Info.equalsIgnoreCase(propertyName)){
					if(errorDataLst.isDeductRollBk()){
						value = errorDataLst.getReqSysStance()==null?"":errorDataLst.getReqSysStance();
					}else{
						value = errorDataLst.getOriginalTransInfo()==null?"":errorDataLst.getOriginalTransInfo();
					}
				}else if(ORIGINALTRANSTIME.equalsIgnoreCase(propertyName)){
					if(instId == 11){
						if(errorDataLst.isDeductRollBk()){
//							value = originalCups.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance(),errorDataLst.getDeductStlmDate().toString().substring(0, 10));
							value = tradeLstDAO.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance(), errorDataLst.getDeductStlmDate().toString().substring(0, 10), "original_cups_lst");
						}else{
							if(errorDataLst.getTrademsgType() != null){
//								value = originalCups.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(),errorDataLst.getTrademsgType(),errorDataLst.getDeductStlmDate().toString().substring(0, 10));
								value = tradeLstDAO.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(), errorDataLst.getTrademsgType(), errorDataLst.getDeductStlmDate().toString().substring(0, 10), "original_cups_lst");
							}
						}
					}else if(instId == 70001){
						if(errorDataLst.isDeductRollBk()){
//							value = originalBeijingbank.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance(),errorDataLst.getDeductStlmDate().toString().substring(0, 10));
							value = tradeLstDAO.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance(), errorDataLst.getDeductStlmDate().toString().substring(0, 10), "original_beijingbank_lst");
						}else{
							if(errorDataLst.getTrademsgType() != null){
//								value = originalBeijingbank.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(),errorDataLst.getTrademsgType(),errorDataLst.getDeductStlmDate().toString().substring(0, 10));
								value = tradeLstDAO.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(), errorDataLst.getTrademsgType(), errorDataLst.getDeductStlmDate().toString().substring(0, 10), "original_beijingbank_lst");
							}
						}
					}else if(instId == 10){
						if(errorDataLst.isDeductRollBk()){
							value = originalZhongxinbank.getOriginalTradeTimeOfRollBk(errorDataLst.getReqSysStance());
						}else{
							value = originalZhongxinbank.getOriginalTradeTimeOfCancel(errorDataLst.getOriginalTransInfo(),errorDataLst.getTrademsgType());
						}
					}else if(instId == 55001){
						value = errorDataLst.getCustomDefineInfo();
					}
					value = value==null?"":value;
				}else if(ACCEPTORPAYFEE.equalsIgnoreCase(propertyName)){
					if(instId == 11 || instId == 55001){
						if(errorDataLst.isDeductRollBk()){
							value = duizhangCupsDao.getAcceptorReceiveFeeByTraceFromDzCupsFile(errorDataLst.getDeductRollBkStance());
						}else{
							value =  duizhangCupsDao.getAcceptorPayFeeByTraceFromDzCupsFile(errorDataLst.getReqSysStance());
						}
					}else if(instId == 70001){
						if(errorDataLst.isDeductRollBk()){
							value = duizhangBeijingbankDao.getAcceptorReceiveFeeByTraceFromDzBjFile(errorDataLst.getDeductRollBkStance());
						}else{
							value = duizhangBeijingbankDao.getAcceptorPayFeeByTraceFromDzBjFile(errorDataLst.getReqSysStance());
						}
					}else if(instId == 10){
						if(errorDataLst.isDeductRollBk()){
							value = duizhangZhongxinbankDao.getAcceptorPayFeeByTraceFromDzZxFile(errorDataLst.getDeductRollBkStance());
						}else{
							value = duizhangZhongxinbankDao.getAcceptorPayFeeByTraceFromDzZxFile(errorDataLst.getReqSysStance());
						}
					}
				}else if(RECEIVI_NAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getReceiviNameById(errorDataLst.getDeductSysId(),errorDataLst.getInstType());
				}else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					value = instInfoDao.getInstInfoNameById(errorDataLst.getDeductSysId(),errorDataLst.getInstType());
				}else if(TERMINAL_ID.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(errorDataLst.getTerminalId())?"":errorDataLst.getTerminalId();
				}else if(HANDLINGNAME.equalsIgnoreCase(propertyName)){
					String handling_name = errorData.getInnerErrorHandlingName(errorDataLst.getHandlingId());
					value = StringUtils.isBlank(handling_name)?"":handling_name;
				}else if(DEDUCT_STLM_DATE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getDeductStlmDate()==null?"":errorDataLst.getDeductStlmDate().toString().substring(0, 10);
				}else if(AGENTID.equalsIgnoreCase(propertyName)){
					value = StringUtils.isBlank(errorDataLst.getAgentId())?"":errorDataLst.getAgentId();
				}else if(HANDLERREMARK.equalsIgnoreCase(propertyName)){            	    
					value = (StringUtils.isBlank(errorDataLst.getHandlerRemark()) || "null".equals(errorDataLst.getHandlerRemark()))?"":errorDataLst.getHandlerRemark();
				}else if(WHETHERERRORHANDLE.equalsIgnoreCase(propertyName)){
					value = errorDataLst.getWhetherErroeHandle()==null?"":errorDataLst.getWhetherErroeHandle();
				}else if(DEDUCTROLLBK.equalsIgnoreCase(propertyName)){
					value = errorDataLst.isDeductRollBk()?"1":"0";
				}
			}
		return value;
	}
	
	/**
	 * 获取融易付收款类交易实体的属性值
	 * 55001 ：银联UPMP
	 * @param propertyName : 属性名
	 * @param obj ：实体对账
	 * @param instId ：渠道号
	 * @return object
	 */
	public static Object getRyfHlog(String propertyName, Object obj, int instId){
		
		Object value = null;
		if(instId == 55001){
			if(obj instanceof RytUpmp){
				rytUpmp = (RytUpmp)obj;
				 if(ID.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getId();
				 }else if(VERSION.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getVersion();
				 }else if(MDATE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getMdate();
				 }else if(MID.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getMid();
				 }else if(BID.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBid();
				 }else if(OID.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getOid();
				 }else if(AMOUNT.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getAmount()==null?0.0d:Double.valueOf(rytUpmp.getAmount())/100;
				 }else if(PAY_AMT.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getPayAmt();
				 }else if(TYPE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getType();
				 }else if(GATE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getGate();
				 }else if(SYS_DATE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getSysDate();
				 }else if(INIT_SYS_DATE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getInitSysDate();
				 }else if(SYS_TIME.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getSysTime();
				 }else if(BATCH.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBatch();
				 }else if(FEE_AMT.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getFeeAmt();
				 }else if(BANK_FEE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBankFee();
				 }else if(TSTAT.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getTstat();
				 }else if(BK_FLAG.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkFlag();
				 }else if(ORG_SEQ.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getOrgSeq();
				 }else if(REF_SEQ.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getRefSeq();
				 }else if(REFUND_AMT.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getRefundAmt();
				 }else if(MER_PRIV.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getMerPriv();
				 }else if(BK_SEND.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkSend();
				 }else if(BK_RECV.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkRecv();
				 }else if(BK_CHK.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkChk();
				 }else if(BK_DATE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkDate();
				 }else if(BK_SEQ1.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkSeq1();
				 }else if(BK_SEQ2.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkSeq2();
				 }else if(BK_RESP.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkResp();
				 }else if(MOBILE_NO.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getMobileNo();
				 }else if(TRANS_PERIOD.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getTransPeriod();
				 }else if(CARD_NO.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getCardNo();
				 }else if(ERROR_CODE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getErrorCode();
				 }else if(AUTHOR_TYPE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getAuthorType();
				 }else if(PHONE_NO.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getPhoneNo();
				 }else if(OPER_ID.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getOperId();
				 }else if(GID.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getGid();
				 }else if(PRE_AMT.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getPreAmt();
				 }else if(BK_FEE_MODEL.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getBkFeeModel();
				 }else if(PRE_AMT1.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getPreAmt1();
				 }else if(ERROR_MSG.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getErrorMsg();
				 }else if(P1.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP1();
				 }else if(P2.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP2();
				 }else if(P3.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP3();
				 }else if(P4.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP4();
				 }else if(P5.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP5();
				 }else if(P6.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP6();
				 }else if(P7.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP7();
				 }else if(P8.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP8();
				 }else if(P9.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP9();
				 }else if(P10.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP10();
				 }else if(P11.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP11();
				 }else if(P12.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP12();
				 }else if(P13.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP13();
				 }else if(P14.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP14();
				 }else if(P15.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getP15();
				 }else if(IS_LIQ.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getIsLiq();
				 }else if(IS_NOTICE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getIsNotice();
				 }else if(DATA_SOURCE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getDataSource();
				 }else if(CURRENCY.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getCurrency();
				 }else if(EXCHANGE_RATE.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getExchangeRate();
				 }else if(AGAINPAY_STATUS.equalsIgnoreCase(propertyName)){
					 value = rytUpmp.getAgainpayStatus();
				 }else if(DEDUCTSYSNAME.equalsIgnoreCase(propertyName)){
					 value = instInfoDao.getInstInfoNameById(rytUpmp.getGid(),1);
				 }else if(TRADETYPEINCHINESE.equalsIgnoreCase(propertyName)){
					 value = OnlineTradeType.getTradeTypeInChinese(rytUpmp.getType());
				 }else{
					 log.error(propertyName+"属性值匹配错误 ："+instId);
				 }
			}else
				log.error(instId+ " 对应的对象 "+obj+" 错误");
		}
		return value;
	}
}
