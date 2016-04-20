package cn.com.chinaebi.dz.object.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerchantFundSettle;

public class MerchantFundSettleBeanValueMap {
	
	public static MerchantFundSettle merchantFundSettle;
	
	
	public static Log log = LogFactory.getLog(MerchantFundSettleBeanValueMap.class);
	public static String CREATE_TAB_DATE = "createTabDate";
	public static String SETTLE_STATE = "settleState";
	public static String SYSTEM_FEE = "systemFee";
	public static String TRADE_AMOUNT = "tradeAmount";
	public static String OPEN_ACOUNT_NAME = "openAcountName";
	public static String MER_CODE = "merCode";
	public static String MER_TYPE = "merType";
	public static String REFUND_COUNT = "refundCount";
	public static String SETTLE_TYPE = "settleType";
	public static String MER_NAME = "settle_mer_name";
	public static String SETTLE_WAY = "settleWay";
	public static String DF_RESULT = "dfResult";
	public static String OPEN_ACCOUNT_CODE = "openAccountCode";
	public static String MER_BATCH_NO = "merBatchNo";
	public static String SYS_BATCH_NO = "sysBatchNo";
	public static String END_DATE = "endDate";
	public static String REFUND_MER_FEE = "refundMerFee";
	public static String START_DATE = "startDate";
	public static String OPEN_BANK_NAME = "openBankName";
	public static String ID = "id";
	public static String SETTLE_AMOUNT = "settleAmount";
	public static String TRADE_COUNT = "tradeCount";
	public static String REFUND_AMOUNT = "refundAmount";
	public static String MER_FEE = "settle_mer_fee";
	
	public static Object getSettleFileValueForExcel(String propertyName, Object obj)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = "";
		if(obj instanceof MerchantFundSettle){
			merchantFundSettle = (MerchantFundSettle)obj;
			if(propertyName.equalsIgnoreCase(CREATE_TAB_DATE)){
				value = merchantFundSettle.getCreateTabDate();
			}else if(propertyName.equalsIgnoreCase(DF_RESULT)){
				value = merchantFundSettle.getSynResult();
			}else if(propertyName.equalsIgnoreCase(END_DATE)){
				value = merchantFundSettle.getEndDate();
			}else if(propertyName.equalsIgnoreCase(ID)){
				value = merchantFundSettle.getId();
			}else if(propertyName.equalsIgnoreCase(MER_BATCH_NO)){
				value = merchantFundSettle.getMerBatchNo();
			}else if(propertyName.equalsIgnoreCase(MER_CODE)){
				value = merchantFundSettle.getMerCode();
			}else if(propertyName.equalsIgnoreCase(MER_NAME)){
				value = merchantFundSettle.getMerName();
			}else if(propertyName.equalsIgnoreCase(MER_TYPE)){
				value = merchantFundSettle.getMerType();
			}else if(propertyName.equalsIgnoreCase(OPEN_ACCOUNT_CODE)){
				value = merchantFundSettle.getOpenAccountCode();
			}else if(propertyName.equalsIgnoreCase(OPEN_ACOUNT_NAME)){
				value = merchantFundSettle.getOpenAcountName();
			}else if(propertyName.equalsIgnoreCase(OPEN_BANK_NAME)){
				value = merchantFundSettle.getOpenBankName();
			}else if(propertyName.equalsIgnoreCase(REFUND_AMOUNT)){
				value = merchantFundSettle.getRefundAmount();
			}else if(propertyName.equalsIgnoreCase(REFUND_COUNT)){
				value = merchantFundSettle.getRefundCount();
			}else if(propertyName.equalsIgnoreCase(REFUND_MER_FEE)){
				value = merchantFundSettle.getRefundMerFee();
			}else if(propertyName.equalsIgnoreCase(SETTLE_AMOUNT)){
				value = merchantFundSettle.getSettleAmount();
			}else if(propertyName.equalsIgnoreCase(SETTLE_STATE)){
				value = merchantFundSettle.getSettleState();
			}else if(propertyName.equalsIgnoreCase(SETTLE_TYPE)){
				value = merchantFundSettle.getSettleType();
			}else if(propertyName.equalsIgnoreCase(SETTLE_WAY)){
				value = merchantFundSettle.getSettleWay();
			}else if(propertyName.equalsIgnoreCase(START_DATE)){
				value = merchantFundSettle.getStartDate();
			}else if(propertyName.equalsIgnoreCase(SYSTEM_FEE)){
				value = merchantFundSettle.getSystemFee();
			}else if(propertyName.equalsIgnoreCase(SYS_BATCH_NO)){
				value = merchantFundSettle.getSysBatchNo();
			}else if(propertyName.equalsIgnoreCase(TRADE_AMOUNT)){
				value = merchantFundSettle.getTradeAmount();
			}else if(propertyName.equalsIgnoreCase(TRADE_COUNT)){
				value = merchantFundSettle.getTradeCount();
			}else if(propertyName.equalsIgnoreCase(MER_FEE)){
				value = merchantFundSettle.getMerFee();
			}else{
				log.error("未找到与"+propertyName+"相匹配属性");
			}
		}
		return value;
	}
	public static Object getSettleFileValueForTxt(String propertyName, Object obj,String columnLength)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,Exception {
		Object value = "";
		if(obj instanceof MerchantFundSettle){
			merchantFundSettle = (MerchantFundSettle)obj;
			if(propertyName.equalsIgnoreCase(CREATE_TAB_DATE)){
				value = merchantFundSettle.getCreateTabDate();
			}else if(propertyName.equalsIgnoreCase(DF_RESULT)){
				value = merchantFundSettle.getSynResult();
			}else if(propertyName.equalsIgnoreCase(END_DATE)){
				value = merchantFundSettle.getEndDate();
			}else if(propertyName.equalsIgnoreCase(ID)){
				value = merchantFundSettle.getId();
			}else if(propertyName.equalsIgnoreCase(MER_BATCH_NO)){
				value = merchantFundSettle.getMerBatchNo();
			}else if(propertyName.equalsIgnoreCase(MER_CODE)){
				value = merchantFundSettle.getMerCode();
			}else if(propertyName.equalsIgnoreCase(MER_NAME)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(merchantFundSettle.getMerName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(propertyName.equalsIgnoreCase(MER_TYPE)){
				value = merchantFundSettle.getMerType();
			}else if(propertyName.equalsIgnoreCase(OPEN_ACCOUNT_CODE)){
				value = merchantFundSettle.getOpenAccountCode();
			}else if(propertyName.equalsIgnoreCase(OPEN_ACOUNT_NAME)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(merchantFundSettle.getOpenAcountName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(propertyName.equalsIgnoreCase(OPEN_BANK_NAME)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(merchantFundSettle.getOpenBankName().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(propertyName.equalsIgnoreCase(REFUND_AMOUNT)){
				value = merchantFundSettle.getRefundAmount();
			}else if(propertyName.equalsIgnoreCase(REFUND_COUNT)){
				value = merchantFundSettle.getRefundCount();
			}else if(propertyName.equalsIgnoreCase(REFUND_MER_FEE)){
				value = merchantFundSettle.getRefundMerFee();
			}else if(propertyName.equalsIgnoreCase(SETTLE_AMOUNT)){
				value = merchantFundSettle.getSettleAmount();
			}else if(propertyName.equalsIgnoreCase(SETTLE_STATE)){
				value = merchantFundSettle.getSettleState();
			}else if(propertyName.equalsIgnoreCase(SETTLE_TYPE)){
				value = merchantFundSettle.getSettleType();
			}else if(propertyName.equalsIgnoreCase(SETTLE_WAY)){
				value = merchantFundSettle.getSettleWay();
			}else if(propertyName.equalsIgnoreCase(START_DATE)){
				value = merchantFundSettle.getStartDate();
			}else if(propertyName.equalsIgnoreCase(SYSTEM_FEE)){
				value = merchantFundSettle.getSystemFee();
			}else if(propertyName.equalsIgnoreCase(SYS_BATCH_NO)){
				value = merchantFundSettle.getSysBatchNo();
			}else if(propertyName.equalsIgnoreCase(TRADE_AMOUNT)){
				value = merchantFundSettle.getTradeAmount();
			}else if(propertyName.equalsIgnoreCase(TRADE_COUNT)){
				value = merchantFundSettle.getTradeCount();
			}else if(propertyName.equalsIgnoreCase(MER_FEE)){
				value = merchantFundSettle.getMerFee();
			}else{
				log.error("未找到与"+propertyName+"相匹配属性");
			}
		}
		return value;
	}
}
