package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_hbyh_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_hbyh_lst"
 */

public abstract class BaseDuizhangHbyhLst  implements Serializable {

	public static String REF = "DuizhangHbyhLst";
	public static String PROP_TRADE_DATE = "TradeDate";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_BANK_HANDLE_STATUS = "BankHandleStatus";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_ORDER_ID = "OrderId";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_PAYER_NAME = "PayerName";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_PAY_STATUS = "PayStatus";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_ID = "Id";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_TRADE_TIME = "TradeTime";
	public static String PROP_CURRENCY = "Currency";
	public static String PROP_T_K_STATUS = "TKStatus";


	// constructors
	public BaseDuizhangHbyhLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangHbyhLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String orderId;
	private java.lang.String reqSysStance;
	private java.lang.String reqTime;
	private java.lang.String tradeAmount;
	private java.lang.String tradeFee;
	private java.lang.Integer bkChk;
	private java.lang.String deductStlmDate;
	private boolean whetherTk;
	private java.lang.String outAccount;
	private java.lang.String currency;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.String deductSysReference;
	private java.lang.String termId;
	private java.lang.String merCode;
	private java.lang.String process;
	private java.lang.String payerName;
	private java.lang.String tradeDate;
	private java.lang.String tradeTime;
	private java.lang.String payStatus;
	private java.lang.String tKStatus;
	private java.lang.String bankHandleStatus;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: orderId
	 */
	public java.lang.String getOrderId () {
		return orderId;
	}

	/**
	 * Set the value related to the column: orderId
	 * @param orderId the orderId value
	 */
	public void setOrderId (java.lang.String orderId) {
		this.orderId = orderId;
	}



	/**
	 * Return the value associated with the column: reqSysStance
	 */
	public java.lang.String getReqSysStance () {
		return reqSysStance;
	}

	/**
	 * Set the value related to the column: reqSysStance
	 * @param reqSysStance the reqSysStance value
	 */
	public void setReqSysStance (java.lang.String reqSysStance) {
		this.reqSysStance = reqSysStance;
	}



	/**
	 * Return the value associated with the column: reqTime
	 */
	public java.lang.String getReqTime () {
		return reqTime;
	}

	/**
	 * Set the value related to the column: reqTime
	 * @param reqTime the reqTime value
	 */
	public void setReqTime (java.lang.String reqTime) {
		this.reqTime = reqTime;
	}



	/**
	 * Return the value associated with the column: tradeAmount
	 */
	public java.lang.String getTradeAmount () {
		return tradeAmount;
	}

	/**
	 * Set the value related to the column: tradeAmount
	 * @param tradeAmount the tradeAmount value
	 */
	public void setTradeAmount (java.lang.String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}



	/**
	 * Return the value associated with the column: tradeFee
	 */
	public java.lang.String getTradeFee () {
		return tradeFee;
	}

	/**
	 * Set the value related to the column: tradeFee
	 * @param tradeFee the tradeFee value
	 */
	public void setTradeFee (java.lang.String tradeFee) {
		this.tradeFee = tradeFee;
	}



	/**
	 * Return the value associated with the column: bk_chk
	 */
	public java.lang.Integer getBkChk () {
		return bkChk;
	}

	/**
	 * Set the value related to the column: bk_chk
	 * @param bkChk the bk_chk value
	 */
	public void setBkChk (java.lang.Integer bkChk) {
		this.bkChk = bkChk;
	}



	/**
	 * Return the value associated with the column: deduct_stlm_date
	 */
	public java.lang.String getDeductStlmDate () {
		return deductStlmDate;
	}

	/**
	 * Set the value related to the column: deduct_stlm_date
	 * @param deductStlmDate the deduct_stlm_date value
	 */
	public void setDeductStlmDate (java.lang.String deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
	}



	/**
	 * Return the value associated with the column: whetherTk
	 */
	public boolean isWhetherTk () {
		return whetherTk;
	}

	/**
	 * Set the value related to the column: whetherTk
	 * @param whetherTk the whetherTk value
	 */
	public void setWhetherTk (boolean whetherTk) {
		this.whetherTk = whetherTk;
	}



	/**
	 * Return the value associated with the column: outAccount
	 */
	public java.lang.String getOutAccount () {
		return outAccount;
	}

	/**
	 * Set the value related to the column: outAccount
	 * @param outAccount the outAccount value
	 */
	public void setOutAccount (java.lang.String outAccount) {
		this.outAccount = outAccount;
	}



	/**
	 * Return the value associated with the column: currency
	 */
	public java.lang.String getCurrency () {
		return currency;
	}

	/**
	 * Set the value related to the column: currency
	 * @param currency the currency value
	 */
	public void setCurrency (java.lang.String currency) {
		this.currency = currency;
	}



	/**
	 * Return the value associated with the column: dz_file_name
	 */
	public java.lang.String getDzFileName () {
		return dzFileName;
	}

	/**
	 * Set the value related to the column: dz_file_name
	 * @param dzFileName the dz_file_name value
	 */
	public void setDzFileName (java.lang.String dzFileName) {
		this.dzFileName = dzFileName;
	}



	/**
	 * Return the value associated with the column: inst_name
	 */
	public java.lang.String getInstName () {
		return instName;
	}

	/**
	 * Set the value related to the column: inst_name
	 * @param instName the inst_name value
	 */
	public void setInstName (java.lang.String instName) {
		this.instName = instName;
	}



	/**
	 * Return the value associated with the column: deductSysReference
	 */
	public java.lang.String getDeductSysReference () {
		return deductSysReference;
	}

	/**
	 * Set the value related to the column: deductSysReference
	 * @param deductSysReference the deductSysReference value
	 */
	public void setDeductSysReference (java.lang.String deductSysReference) {
		this.deductSysReference = deductSysReference;
	}



	/**
	 * Return the value associated with the column: termId
	 */
	public java.lang.String getTermId () {
		return termId;
	}

	/**
	 * Set the value related to the column: termId
	 * @param termId the termId value
	 */
	public void setTermId (java.lang.String termId) {
		this.termId = termId;
	}



	/**
	 * Return the value associated with the column: merCode
	 */
	public java.lang.String getMerCode () {
		return merCode;
	}

	/**
	 * Set the value related to the column: merCode
	 * @param merCode the merCode value
	 */
	public void setMerCode (java.lang.String merCode) {
		this.merCode = merCode;
	}



	/**
	 * Return the value associated with the column: process
	 */
	public java.lang.String getProcess () {
		return process;
	}

	/**
	 * Set the value related to the column: process
	 * @param process the process value
	 */
	public void setProcess (java.lang.String process) {
		this.process = process;
	}



	/**
	 * Return the value associated with the column: payerName
	 */
	public java.lang.String getPayerName () {
		return payerName;
	}

	/**
	 * Set the value related to the column: payerName
	 * @param payerName the payerName value
	 */
	public void setPayerName (java.lang.String payerName) {
		this.payerName = payerName;
	}



	/**
	 * Return the value associated with the column: tradeDate
	 */
	public java.lang.String getTradeDate () {
		return tradeDate;
	}

	/**
	 * Set the value related to the column: tradeDate
	 * @param tradeDate the tradeDate value
	 */
	public void setTradeDate (java.lang.String tradeDate) {
		this.tradeDate = tradeDate;
	}



	/**
	 * Return the value associated with the column: tradeTime
	 */
	public java.lang.String getTradeTime () {
		return tradeTime;
	}

	/**
	 * Set the value related to the column: tradeTime
	 * @param tradeTime the tradeTime value
	 */
	public void setTradeTime (java.lang.String tradeTime) {
		this.tradeTime = tradeTime;
	}



	/**
	 * Return the value associated with the column: payStatus
	 */
	public java.lang.String getPayStatus () {
		return payStatus;
	}

	/**
	 * Set the value related to the column: payStatus
	 * @param payStatus the payStatus value
	 */
	public void setPayStatus (java.lang.String payStatus) {
		this.payStatus = payStatus;
	}



	/**
	 * Return the value associated with the column: tKStatus
	 */
	public java.lang.String getTKStatus () {
		return tKStatus;
	}

	/**
	 * Set the value related to the column: tKStatus
	 * @param tKStatus the tKStatus value
	 */
	public void setTKStatus (java.lang.String tKStatus) {
		this.tKStatus = tKStatus;
	}



	/**
	 * Return the value associated with the column: bankHandleStatus
	 */
	public java.lang.String getBankHandleStatus () {
		return bankHandleStatus;
	}

	/**
	 * Set the value related to the column: bankHandleStatus
	 * @param bankHandleStatus the bankHandleStatus value
	 */
	public void setBankHandleStatus (java.lang.String bankHandleStatus) {
		this.bankHandleStatus = bankHandleStatus;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangHbyhLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangHbyhLst duizhangHbyhLst = (cn.com.chinaebi.dz.object.DuizhangHbyhLst) obj;
			if (null == this.getId() || null == duizhangHbyhLst.getId()) return false;
			else return (this.getId().equals(duizhangHbyhLst.getId()));
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