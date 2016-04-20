package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the merchant_settle_statistics table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="merchant_settle_statistics"
 */

public abstract class BaseMerchantSettleStatistics  implements Serializable {

	public static String REF = "MerchantSettleStatistics";
	public static String PROP_SYSTEM_REFUND_FEE = "SystemRefundFee";
	public static String PROP_REFUND_ZF_FEE = "RefundZfFee";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_SYSTEM_FEE = "SystemFee";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_INST_ID = "InstId";
	public static String PROP_MER_TYPE = "MerType";
	public static String PROP_REFUND_COUNT = "RefundCount";
	public static String PROP_DATA_STATUS = "DataStatus";
	public static String PROP_MER_FEE = "MerFee";
	public static String PROP_ZF_FEE = "ZfFee";
	public static String PROP_INST_TYPE = "InstType";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_JS_DATE = "JsDate";
	public static String PROP_BANK_ID = "BankId";
	public static String PROP_WHETHER_JS = "WhetherJs";
	public static String PROP_ID = "Id";
	public static String PROP_TRADE_GC_COUNT = "TradeGcCount";
	public static String PROP_MER_REFUND_FEE = "MerRefundFee";
	public static String PROP_TRADE_COUNT = "TradeCount";
	public static String PROP_REFUND_AMOUNT = "RefundAmount";
	public static String PROP_SETTLE_AMOUNT = "SettleAmount";


	// constructors
	public BaseMerchantSettleStatistics () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMerchantSettleStatistics (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMerchantSettleStatistics (
		java.lang.Integer id,
		java.lang.Integer instId,
		java.lang.String merCode,
		java.lang.Integer merType,
		java.lang.Integer deductStlmDate,
		java.lang.String tradeAmount,
		java.lang.Integer tradeCount,
		java.lang.String refundAmount,
		java.lang.Integer refundCount,
		java.lang.String merFee,
		java.lang.String systemFee,
		java.lang.String merRefundFee,
		java.lang.String settleAmount,
		java.lang.String systemRefundFee,
		java.lang.Integer bankId,
		java.lang.Integer tradeGcCount) {

		this.setId(id);
		this.setInstId(instId);
		this.setMerCode(merCode);
		this.setMerType(merType);
		this.setDeductStlmDate(deductStlmDate);
		this.setTradeAmount(tradeAmount);
		this.setTradeCount(tradeCount);
		this.setRefundAmount(refundAmount);
		this.setRefundCount(refundCount);
		this.setMerFee(merFee);
		this.setSystemFee(systemFee);
		this.setMerRefundFee(merRefundFee);
		this.setSettleAmount(settleAmount);
		this.setSystemRefundFee(systemRefundFee);
		this.setBankId(bankId);
		this.setTradeGcCount(tradeGcCount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer instId;
	private java.lang.String merCode;
	private java.lang.Integer merType;
	private java.lang.Integer deductStlmDate;
	private java.lang.String tradeAmount;
	private java.lang.Integer tradeCount;
	private java.lang.String refundAmount;
	private java.lang.Integer refundCount;
	private java.lang.String merFee;
	private java.lang.String systemFee;
	private java.lang.String merRefundFee;
	private java.lang.String settleAmount;
	private java.lang.String systemRefundFee;
	private boolean whetherJs;
	private java.lang.Integer dataStatus;
	private java.lang.Integer instType;
	private java.lang.String zfFee;
	private java.lang.String refundZfFee;
	private java.lang.Integer bankId;
	private java.lang.Integer jsDate;
	private java.lang.Integer tradeGcCount;



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
	 * Return the value associated with the column: inst_id
	 */
	public java.lang.Integer getInstId () {
		return instId;
	}

	/**
	 * Set the value related to the column: inst_id
	 * @param instId the inst_id value
	 */
	public void setInstId (java.lang.Integer instId) {
		this.instId = instId;
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
	 * Return the value associated with the column: deduct_stlm_date
	 */
	public java.lang.Integer getDeductStlmDate () {
		return deductStlmDate;
	}

	/**
	 * Set the value related to the column: deduct_stlm_date
	 * @param deductStlmDate the deduct_stlm_date value
	 */
	public void setDeductStlmDate (java.lang.Integer deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
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
	 * Return the value associated with the column: mer_refund_fee
	 */
	public java.lang.String getMerRefundFee () {
		return merRefundFee;
	}

	/**
	 * Set the value related to the column: mer_refund_fee
	 * @param merRefundFee the mer_refund_fee value
	 */
	public void setMerRefundFee (java.lang.String merRefundFee) {
		this.merRefundFee = merRefundFee;
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
	 * Return the value associated with the column: whetherJs
	 */
	public boolean isWhetherJs () {
		return whetherJs;
	}

	/**
	 * Set the value related to the column: whetherJs
	 * @param whetherJs the whetherJs value
	 */
	public void setWhetherJs (boolean whetherJs) {
		this.whetherJs = whetherJs;
	}



	/**
	 * Return the value associated with the column: data_status
	 */
	public java.lang.Integer getDataStatus () {
		return dataStatus;
	}

	/**
	 * Set the value related to the column: data_status
	 * @param dataStatus the data_status value
	 */
	public void setDataStatus (java.lang.Integer dataStatus) {
		this.dataStatus = dataStatus;
	}



	/**
	 * Return the value associated with the column: inst_type
	 */
	public java.lang.Integer getInstType () {
		return instType;
	}

	/**
	 * Set the value related to the column: inst_type
	 * @param instType the inst_type value
	 */
	public void setInstType (java.lang.Integer instType) {
		this.instType = instType;
	}



	/**
	 * Return the value associated with the column: zf_fee
	 */
	public java.lang.String getZfFee () {
		return zfFee;
	}

	/**
	 * Set the value related to the column: zf_fee
	 * @param zfFee the zf_fee value
	 */
	public void setZfFee (java.lang.String zfFee) {
		this.zfFee = zfFee;
	}



	/**
	 * Return the value associated with the column: refund_zf_fee
	 */
	public java.lang.String getRefundZfFee () {
		return refundZfFee;
	}

	/**
	 * Set the value related to the column: refund_zf_fee
	 * @param refundZfFee the refund_zf_fee value
	 */
	public void setRefundZfFee (java.lang.String refundZfFee) {
		this.refundZfFee = refundZfFee;
	}



	/**
	 * Return the value associated with the column: bank_id
	 */
	public java.lang.Integer getBankId () {
		return bankId;
	}

	/**
	 * Set the value related to the column: bank_id
	 * @param bankId the bank_id value
	 */
	public void setBankId (java.lang.Integer bankId) {
		this.bankId = bankId;
	}



	/**
	 * Return the value associated with the column: js_date
	 */
	public java.lang.Integer getJsDate () {
		return jsDate;
	}

	/**
	 * Set the value related to the column: js_date
	 * @param jsDate the js_date value
	 */
	public void setJsDate (java.lang.Integer jsDate) {
		this.jsDate = jsDate;
	}



	/**
	 * Return the value associated with the column: trade_gc_count
	 */
	public java.lang.Integer getTradeGcCount () {
		return tradeGcCount;
	}

	/**
	 * Set the value related to the column: trade_gc_count
	 * @param tradeGcCount the trade_gc_count value
	 */
	public void setTradeGcCount (java.lang.Integer tradeGcCount) {
		this.tradeGcCount = tradeGcCount;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MerchantSettleStatistics)) return false;
		else {
			cn.com.chinaebi.dz.object.MerchantSettleStatistics merchantSettleStatistics = (cn.com.chinaebi.dz.object.MerchantSettleStatistics) obj;
			if (null == this.getId() || null == merchantSettleStatistics.getId()) return false;
			else return (this.getId().equals(merchantSettleStatistics.getId()));
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