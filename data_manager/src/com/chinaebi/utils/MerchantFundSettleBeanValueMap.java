package com.chinaebi.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chinaebi.entity.MerchantFundSettle;


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
				value = merchantFundSettle.getCreate_tab_date();
			}else if(propertyName.equalsIgnoreCase(DF_RESULT)){
				value = merchantFundSettle.getSyn_result();
			}else if(propertyName.equalsIgnoreCase(END_DATE)){
				value = merchantFundSettle.getEnd_date();
			}else if(propertyName.equalsIgnoreCase(ID)){
				value = merchantFundSettle.getId();
			}else if(propertyName.equalsIgnoreCase(MER_BATCH_NO)){
				value = merchantFundSettle.getMer_batch_no();
			}else if(propertyName.equalsIgnoreCase(MER_CODE)){
				value = merchantFundSettle.getMer_code();
			}else if(propertyName.equalsIgnoreCase(MER_NAME)){
				value = merchantFundSettle.getMer_name();
			}else if(propertyName.equalsIgnoreCase(MER_TYPE)){
				value = merchantFundSettle.getMer_type();
			}else if(propertyName.equalsIgnoreCase(OPEN_ACCOUNT_CODE)){
				value = merchantFundSettle.getOpen_account_code();
			}else if(propertyName.equalsIgnoreCase(OPEN_ACOUNT_NAME)){
				value = merchantFundSettle.getOpen_acount_name();
			}else if(propertyName.equalsIgnoreCase(OPEN_BANK_NAME)){
				value = merchantFundSettle.getOpen_bank_name();
			}else if(propertyName.equalsIgnoreCase(REFUND_AMOUNT)){
				value = merchantFundSettle.getRefund_amount();
			}else if(propertyName.equalsIgnoreCase(REFUND_COUNT)){
				value = merchantFundSettle.getRefund_count();
			}else if(propertyName.equalsIgnoreCase(REFUND_MER_FEE)){
				value = merchantFundSettle.getRefund_mer_fee();
			}else if(propertyName.equalsIgnoreCase(SETTLE_AMOUNT)){
				value = merchantFundSettle.getSettle_amount();
			}else if(propertyName.equalsIgnoreCase(SETTLE_STATE)){
				value = merchantFundSettle.getSettle_state();
			}else if(propertyName.equalsIgnoreCase(SETTLE_TYPE)){
				value = merchantFundSettle.getSettle_type();
			}else if(propertyName.equalsIgnoreCase(SETTLE_WAY)){
				value = merchantFundSettle.getSettle_way();
			}else if(propertyName.equalsIgnoreCase(START_DATE)){
				value = merchantFundSettle.getStart_date();
			}else if(propertyName.equalsIgnoreCase(SYSTEM_FEE)){
				value = merchantFundSettle.getSystem_fee();
			}else if(propertyName.equalsIgnoreCase(SYS_BATCH_NO)){
				value = merchantFundSettle.getSys_batch_no();
			}else if(propertyName.equalsIgnoreCase(TRADE_AMOUNT)){
				value = merchantFundSettle.getTrade_amount();
			}else if(propertyName.equalsIgnoreCase(TRADE_COUNT)){
				value = merchantFundSettle.getTrade_count();
			}else if(propertyName.equalsIgnoreCase(MER_FEE)){
				value = merchantFundSettle.getMer_fee();
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
				value = merchantFundSettle.getCreate_tab_date();
			}else if(propertyName.equalsIgnoreCase(DF_RESULT)){
				value = merchantFundSettle.getSyn_result();
			}else if(propertyName.equalsIgnoreCase(END_DATE)){
				value = merchantFundSettle.getEnd_date();
			}else if(propertyName.equalsIgnoreCase(ID)){
				value = merchantFundSettle.getId();
			}else if(propertyName.equalsIgnoreCase(MER_BATCH_NO)){
				value = merchantFundSettle.getMer_batch_no();
			}else if(propertyName.equalsIgnoreCase(MER_CODE)){
				value = merchantFundSettle.getMer_code();
			}else if(propertyName.equalsIgnoreCase(MER_NAME)){
				value = new String(String.format("%-"+columnLength+"s",new String(merchantFundSettle.getMer_name().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(propertyName.equalsIgnoreCase(MER_TYPE)){
				value = merchantFundSettle.getMer_type();
			}else if(propertyName.equalsIgnoreCase(OPEN_ACCOUNT_CODE)){
				value = merchantFundSettle.getOpen_account_code();
			}else if(propertyName.equalsIgnoreCase(OPEN_ACOUNT_NAME)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(merchantFundSettle.getOpen_acount_name().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(propertyName.equalsIgnoreCase(OPEN_BANK_NAME)){
				value = new String(String.format("%-"+Integer.valueOf(columnLength)+"s",new String(merchantFundSettle.getOpen_bank_name().getBytes("GBK"),"ISO-8859-1")).getBytes("ISO-8859-1"),"GBK");
			}else if(propertyName.equalsIgnoreCase(REFUND_AMOUNT)){
				value = merchantFundSettle.getRefund_amount();
			}else if(propertyName.equalsIgnoreCase(REFUND_COUNT)){
				value = merchantFundSettle.getRefund_count();
			}else if(propertyName.equalsIgnoreCase(REFUND_MER_FEE)){
				value = merchantFundSettle.getRefund_mer_fee();
			}else if(propertyName.equalsIgnoreCase(SETTLE_AMOUNT)){
				value = merchantFundSettle.getSettle_amount();
			}else if(propertyName.equalsIgnoreCase(SETTLE_STATE)){
				value = merchantFundSettle.getSettle_state();
			}else if(propertyName.equalsIgnoreCase(SETTLE_TYPE)){
				value = merchantFundSettle.getSettle_type();
			}else if(propertyName.equalsIgnoreCase(SETTLE_WAY)){
				value = merchantFundSettle.getSettle_way();
			}else if(propertyName.equalsIgnoreCase(START_DATE)){
				value = merchantFundSettle.getStart_date();
			}else if(propertyName.equalsIgnoreCase(SYSTEM_FEE)){
				value = merchantFundSettle.getSystem_fee();
			}else if(propertyName.equalsIgnoreCase(SYS_BATCH_NO)){
				value = merchantFundSettle.getSys_batch_no();
			}else if(propertyName.equalsIgnoreCase(TRADE_AMOUNT)){
				value = merchantFundSettle.getTrade_amount();
			}else if(propertyName.equalsIgnoreCase(TRADE_COUNT)){
				value = merchantFundSettle.getTrade_count();
			}else if(propertyName.equalsIgnoreCase(MER_FEE)){
				value = merchantFundSettle.getMer_fee();
			}else{
				log.error("未找到与"+propertyName+"相匹配属性");
			}
		}
		return value;
	}
}
