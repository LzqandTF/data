package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the merchant_fund_settle table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="merchant_fund_settle"
 */

public abstract class BaseMerchantFundSettle  implements Serializable {

	public static String REF = "MerchantFundSettle";
	public static String PROP_CREATE_TAB_DATE = "CreateTabDate";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_SETTLE_STATE = "SettleState";
	public static String PROP_OPEN_ACOUNT_NAME = "OpenAcountName";
	public static String PROP_BIL_BANK = "BilBank";
	public static String PROP_REFUND_COUNT = "RefundCount";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_SETTLE_TYPE = "SettleType";
	public static String PROP_OPEN_ACCOUNT_CODE = "OpenAccountCode";
	public static String PROP_REC_AMOUNT_ADD = "RecAmountAdd";
	public static String PROP_MER_BATCH_NO = "MerBatchNo";
	public static String PROP_SYS_BATCH_NO = "SysBatchNo";
	public static String PROP_START_DATE = "StartDate";
	public static String PROP_WHTHER_FZ = "WhtherFz";
	public static String PROP_BIL_MANUAL = "BilManual";
	public static String PROP_REFUND_AMOUNT = "RefundAmount";
	public static String PROP_TRADE_COUNT = "TradeCount";
	public static String PROP_SETTLE_AMOUNT = "SettleAmount";
	public static String PROP_SYSTEM_REFUND_FEE = "SystemRefundFee";
	public static String PROP_SYSTEM_FEE = "SystemFee";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_MER_TYPE = "MerType";
	public static String PROP_SYN_DATE = "SynDate";
	public static String PROP_REC_AMOUNT_SUB = "RecAmountSub";
	public static String PROP_REC_AMOUNT_ADD_COUNT = "RecAmountAddCount";
	public static String PROP_SETTLE_WAY = "SettleWay";
	public static String PROP_MER_FEE = "MerFee";
	public static String PROP_END_DATE = "EndDate";
	public static String PROP_REFUND_MER_FEE = "RefundMerFee";
	public static String PROP_OPEN_BANK_NAME = "OpenBankName";
	public static String PROP_SETTLE_DATE = "SettleDate";
	public static String PROP_SETTLE_CONFIRM_DATE = "SettleConfirmDate";
	public static String PROP_ID = "Id";
	public static String PROP_SYN_RESULT = "SynResult";
	public static String PROP_ERROR_MSG = "ErrorMsg";
	public static String PROP_REC_AMOUNT_SUB_COUNT = "RecAmountSubCount";


	// constructors
	public BaseMerchantFundSettle () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMerchantFundSettle (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMerchantFundSettle (
		java.lang.Integer id,
		java.lang.String merCode,
		java.lang.Integer merType,
		java.lang.Integer settleType,
		java.lang.String sysBatchNo,
		java.lang.String merBatchNo,
		java.lang.Integer startDate,
		java.lang.Integer endDate,
		java.lang.Integer createTabDate,
		java.lang.String merName,
		java.lang.String tradeAmount,
		java.lang.Integer tradeCount,
		java.lang.String refundAmount,
		java.lang.Integer refundCount,
		java.lang.String systemFee,
		java.lang.String refundMerFee,
		java.lang.String openBankName,
		java.lang.String openAcountName,
		java.lang.String openAccountCode,
		java.lang.String settleAmount,
		java.lang.Integer settleWay,
		java.lang.Integer settleState,
		java.lang.Integer settleDate,
		java.lang.Integer settleConfirmDate,
		java.lang.Integer bilManual,
		java.lang.String merFee,
		java.lang.String systemRefundFee) {

		this.setId(id);
		this.setMerCode(merCode);
		this.setMerType(merType);
		this.setSettleType(settleType);
		this.setSysBatchNo(sysBatchNo);
		this.setMerBatchNo(merBatchNo);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.setCreateTabDate(createTabDate);
		this.setMerName(merName);
		this.setTradeAmount(tradeAmount);
		this.setTradeCount(tradeCount);
		this.setRefundAmount(refundAmount);
		this.setRefundCount(refundCount);
		this.setSystemFee(systemFee);
		this.setRefundMerFee(refundMerFee);
		this.setOpenBankName(openBankName);
		this.setOpenAcountName(openAcountName);
		this.setOpenAccountCode(openAccountCode);
		this.setSettleAmount(settleAmount);
		this.setSettleWay(settleWay);
		this.setSettleState(settleState);
		this.setSettleDate(settleDate);
		this.setSettleConfirmDate(settleConfirmDate);
		this.setBilManual(bilManual);
		this.setMerFee(merFee);
		this.setSystemRefundFee(systemRefundFee);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String merCode;
	private java.lang.Integer merType;
	private java.lang.Integer settleType;
	private java.lang.String sysBatchNo;
	private java.lang.String merBatchNo;
	private java.lang.Integer startDate;
	private java.lang.Integer endDate;
	private java.lang.Integer createTabDate;
	private java.lang.String merName;
	private java.lang.String tradeAmount;
	private java.lang.Integer tradeCount;
	private java.lang.String refundAmount;
	private java.lang.Integer refundCount;
	private java.lang.String systemFee;
	private java.lang.String refundMerFee;
	private java.lang.String openBankName;
	private java.lang.String openAcountName;
	private java.lang.String openAccountCode;
	private java.lang.String settleAmount;
	private java.lang.Integer settleWay;
	private java.lang.Integer settleState;
	private java.lang.Integer settleDate;
	private java.lang.Integer settleConfirmDate;
	private java.lang.Integer bilManual;
	private java.lang.String merFee;
	private java.lang.String systemRefundFee;
	private java.lang.String bilBank;
	private java.lang.String errorMsg;
	private java.lang.String recAmountAdd;
	private java.lang.Integer recAmountAddCount;
	private java.lang.String recAmountSub;
	private java.lang.Integer recAmountSubCount;
	private boolean whtherFz;
	private java.lang.Integer synResult;
	private java.lang.Integer synDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: mer_code
	 */
	public java.lang.String getMerCode () {
		return merCode;
	}

	/**
	 * Set the value related to the column: mer_code
	 * @param merCode the mer_code value
	 */
	public void setMerCode (java.lang.String merCode) {
		this.merCode = merCode;
	}



	/**
	 * Return the value associated with the column: mer_type
	 */
	public java.lang.Integer getMerType () {
		return merType;
	}

	/**
	 * Set the value related to the column: mer_type
	 * @param merType the mer_type value
	 */
	public void setMerType (java.lang.Integer merType) {
		this.merType = merType;
	}



	/**
	 * Return the value associated with the column: settle_type
	 */
	public java.lang.Integer getSettleType () {
		return settleType;
	}

	/**
	 * Set the value related to the column: settle_type
	 * @param settleType the settle_type value
	 */
	public void setSettleType (java.lang.Integer settleType) {
		this.settleType = settleType;
	}



	/**
	 * Return the value associated with the column: sys_batch_no
	 */
	public java.lang.String getSysBatchNo () {
		return sysBatchNo;
	}

	/**
	 * Set the value related to the column: sys_batch_no
	 * @param sysBatchNo the sys_batch_no value
	 */
	public void setSysBatchNo (java.lang.String sysBatchNo) {
		this.sysBatchNo = sysBatchNo;
	}



	/**
	 * Return the value associated with the column: mer_batch_no
	 */
	public java.lang.String getMerBatchNo () {
		return merBatchNo;
	}

	/**
	 * Set the value related to the column: mer_batch_no
	 * @param merBatchNo the mer_batch_no value
	 */
	public void setMerBatchNo (java.lang.String merBatchNo) {
		this.merBatchNo = merBatchNo;
	}



	/**
	 * Return the value associated with the column: start_date
	 */
	public java.lang.Integer getStartDate () {
		return startDate;
	}

	/**
	 * Set the value related to the column: start_date
	 * @param startDate the start_date value
	 */
	public void setStartDate (java.lang.Integer startDate) {
		this.startDate = startDate;
	}



	/**
	 * Return the value associated with the column: end_date
	 */
	public java.lang.Integer getEndDate () {
		return endDate;
	}

	/**
	 * Set the value related to the column: end_date
	 * @param endDate the end_date value
	 */
	public void setEndDate (java.lang.Integer endDate) {
		this.endDate = endDate;
	}



	/**
	 * Return the value associated with the column: create_tab_date
	 */
	public java.lang.Integer getCreateTabDate () {
		return createTabDate;
	}

	/**
	 * Set the value related to the column: create_tab_date
	 * @param createTabDate the create_tab_date value
	 */
	public void setCreateTabDate (java.lang.Integer createTabDate) {
		this.createTabDate = createTabDate;
	}



	/**
	 * Return the value associated with the column: mer_name
	 */
	public java.lang.String getMerName () {
		return merName;
	}

	/**
	 * Set the value related to the column: mer_name
	 * @param merName the mer_name value
	 */
	public void setMerName (java.lang.String merName) {
		this.merName = merName;
	}



	/**
	 * Return the value associated with the column: trade_amount
	 */
	public java.lang.String getTradeAmount () {
		return tradeAmount;
	}

	/**
	 * Set the value related to the column: trade_amount
	 * @param tradeAmount the trade_amount value
	 */
	public void setTradeAmount (java.lang.String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}



	/**
	 * Return the value associated with the column: trade_count
	 */
	public java.lang.Integer getTradeCount () {
		return tradeCount;
	}

	/**
	 * Set the value related to the column: trade_count
	 * @param tradeCount the trade_count value
	 */
	public void setTradeCount (java.lang.Integer tradeCount) {
		this.tradeCount = tradeCount;
	}



	/**
	 * Return the value associated with the column: refund_amount
	 */
	public java.lang.String getRefundAmount () {
		return refundAmount;
	}

	/**
	 * Set the value related to the column: refund_amount
	 * @param refundAmount the refund_amount value
	 */
	public void setRefundAmount (java.lang.String refundAmount) {
		this.refundAmount = refundAmount;
	}



	/**
	 * Return the value associated with the column: refund_count
	 */
	public java.lang.Integer getRefundCount () {
		return refundCount;
	}

	/**
	 * Set the value related to the column: refund_count
	 * @param refundCount the refund_count value
	 */
	public void setRefundCount (java.lang.Integer refundCount) {
		this.refundCount = refundCount;
	}



	/**
	 * Return the value associated with the column: system_fee
	 */
	public java.lang.String getSystemFee () {
		return systemFee;
	}

	/**
	 * Set the value related to the column: system_fee
	 * @param systemFee the system_fee value
	 */
	public void setSystemFee (java.lang.String systemFee) {
		this.systemFee = systemFee;
	}



	/**
	 * Return the value associated with the column: refund_mer_fee
	 */
	public java.lang.String getRefundMerFee () {
		return refundMerFee;
	}

	/**
	 * Set the value related to the column: refund_mer_fee
	 * @param refundMerFee the refund_mer_fee value
	 */
	public void setRefundMerFee (java.lang.String refundMerFee) {
		this.refundMerFee = refundMerFee;
	}



	/**
	 * Return the value associated with the column: open_bank_name
	 */
	public java.lang.String getOpenBankName () {
		return openBankName;
	}

	/**
	 * Set the value related to the column: open_bank_name
	 * @param openBankName the open_bank_name value
	 */
	public void setOpenBankName (java.lang.String openBankName) {
		this.openBankName = openBankName;
	}



	/**
	 * Return the value associated with the column: open_acount_name
	 */
	public java.lang.String getOpenAcountName () {
		return openAcountName;
	}

	/**
	 * Set the value related to the column: open_acount_name
	 * @param openAcountName the open_acount_name value
	 */
	public void setOpenAcountName (java.lang.String openAcountName) {
		this.openAcountName = openAcountName;
	}



	/**
	 * Return the value associated with the column: open_account_code
	 */
	public java.lang.String getOpenAccountCode () {
		return openAccountCode;
	}

	/**
	 * Set the value related to the column: open_account_code
	 * @param openAccountCode the open_account_code value
	 */
	public void setOpenAccountCode (java.lang.String openAccountCode) {
		this.openAccountCode = openAccountCode;
	}



	/**
	 * Return the value associated with the column: settle_amount
	 */
	public java.lang.String getSettleAmount () {
		return settleAmount;
	}

	/**
	 * Set the value related to the column: settle_amount
	 * @param settleAmount the settle_amount value
	 */
	public void setSettleAmount (java.lang.String settleAmount) {
		this.settleAmount = settleAmount;
	}



	/**
	 * Return the value associated with the column: settle_way
	 */
	public java.lang.Integer getSettleWay () {
		return settleWay;
	}

	/**
	 * Set the value related to the column: settle_way
	 * @param settleWay the settle_way value
	 */
	public void setSettleWay (java.lang.Integer settleWay) {
		this.settleWay = settleWay;
	}



	/**
	 * Return the value associated with the column: settle_state
	 */
	public java.lang.Integer getSettleState () {
		return settleState;
	}

	/**
	 * Set the value related to the column: settle_state
	 * @param settleState the settle_state value
	 */
	public void setSettleState (java.lang.Integer settleState) {
		this.settleState = settleState;
	}



	/**
	 * Return the value associated with the column: settle_date
	 */
	public java.lang.Integer getSettleDate () {
		return settleDate;
	}

	/**
	 * Set the value related to the column: settle_date
	 * @param settleDate the settle_date value
	 */
	public void setSettleDate (java.lang.Integer settleDate) {
		this.settleDate = settleDate;
	}



	/**
	 * Return the value associated with the column: settle_confirm_date
	 */
	public java.lang.Integer getSettleConfirmDate () {
		return settleConfirmDate;
	}

	/**
	 * Set the value related to the column: settle_confirm_date
	 * @param settleConfirmDate the settle_confirm_date value
	 */
	public void setSettleConfirmDate (java.lang.Integer settleConfirmDate) {
		this.settleConfirmDate = settleConfirmDate;
	}



	/**
	 * Return the value associated with the column: bil_manual
	 */
	public java.lang.Integer getBilManual () {
		return bilManual;
	}

	/**
	 * Set the value related to the column: bil_manual
	 * @param bilManual the bil_manual value
	 */
	public void setBilManual (java.lang.Integer bilManual) {
		this.bilManual = bilManual;
	}



	/**
	 * Return the value associated with the column: mer_fee
	 */
	public java.lang.String getMerFee () {
		return merFee;
	}

	/**
	 * Set the value related to the column: mer_fee
	 * @param merFee the mer_fee value
	 */
	public void setMerFee (java.lang.String merFee) {
		this.merFee = merFee;
	}



	/**
	 * Return the value associated with the column: system_refund_fee
	 */
	public java.lang.String getSystemRefundFee () {
		return systemRefundFee;
	}

	/**
	 * Set the value related to the column: system_refund_fee
	 * @param systemRefundFee the system_refund_fee value
	 */
	public void setSystemRefundFee (java.lang.String systemRefundFee) {
		this.systemRefundFee = systemRefundFee;
	}



	/**
	 * Return the value associated with the column: bil_bank
	 */
	public java.lang.String getBilBank () {
		return bilBank;
	}

	/**
	 * Set the value related to the column: bil_bank
	 * @param bilBank the bil_bank value
	 */
	public void setBilBank (java.lang.String bilBank) {
		this.bilBank = bilBank;
	}



	/**
	 * Return the value associated with the column: error_msg
	 */
	public java.lang.String getErrorMsg () {
		return errorMsg;
	}

	/**
	 * Set the value related to the column: error_msg
	 * @param errorMsg the error_msg value
	 */
	public void setErrorMsg (java.lang.String errorMsg) {
		this.errorMsg = errorMsg;
	}



	/**
	 * Return the value associated with the column: rec_amount_add
	 */
	public java.lang.String getRecAmountAdd () {
		return recAmountAdd;
	}

	/**
	 * Set the value related to the column: rec_amount_add
	 * @param recAmountAdd the rec_amount_add value
	 */
	public void setRecAmountAdd (java.lang.String recAmountAdd) {
		this.recAmountAdd = recAmountAdd;
	}



	/**
	 * Return the value associated with the column: rec_amount_add_count
	 */
	public java.lang.Integer getRecAmountAddCount () {
		return recAmountAddCount;
	}

	/**
	 * Set the value related to the column: rec_amount_add_count
	 * @param recAmountAddCount the rec_amount_add_count value
	 */
	public void setRecAmountAddCount (java.lang.Integer recAmountAddCount) {
		this.recAmountAddCount = recAmountAddCount;
	}



	/**
	 * Return the value associated with the column: rec_amount_sub
	 */
	public java.lang.String getRecAmountSub () {
		return recAmountSub;
	}

	/**
	 * Set the value related to the column: rec_amount_sub
	 * @param recAmountSub the rec_amount_sub value
	 */
	public void setRecAmountSub (java.lang.String recAmountSub) {
		this.recAmountSub = recAmountSub;
	}



	/**
	 * Return the value associated with the column: rec_amount_sub_count
	 */
	public java.lang.Integer getRecAmountSubCount () {
		return recAmountSubCount;
	}

	/**
	 * Set the value related to the column: rec_amount_sub_count
	 * @param recAmountSubCount the rec_amount_sub_count value
	 */
	public void setRecAmountSubCount (java.lang.Integer recAmountSubCount) {
		this.recAmountSubCount = recAmountSubCount;
	}



	/**
	 * Return the value associated with the column: whtherFz
	 */
	public boolean isWhtherFz () {
		return whtherFz;
	}

	/**
	 * Set the value related to the column: whtherFz
	 * @param whtherFz the whtherFz value
	 */
	public void setWhtherFz (boolean whtherFz) {
		this.whtherFz = whtherFz;
	}



	/**
	 * Return the value associated with the column: syn_result
	 */
	public java.lang.Integer getSynResult () {
		return synResult;
	}

	/**
	 * Set the value related to the column: syn_result
	 * @param synResult the syn_result value
	 */
	public void setSynResult (java.lang.Integer synResult) {
		this.synResult = synResult;
	}



	/**
	 * Return the value associated with the column: syn_date
	 */
	public java.lang.Integer getSynDate () {
		return synDate;
	}

	/**
	 * Set the value related to the column: syn_date
	 * @param synDate the syn_date value
	 */
	public void setSynDate (java.lang.Integer synDate) {
		this.synDate = synDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MerchantFundSettle)) return false;
		else {
			cn.com.chinaebi.dz.object.MerchantFundSettle merchantFundSettle = (cn.com.chinaebi.dz.object.MerchantFundSettle) obj;
			if (null == this.getId() || null == merchantFundSettle.getId()) return false;
			else return (this.getId().equals(merchantFundSettle.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}