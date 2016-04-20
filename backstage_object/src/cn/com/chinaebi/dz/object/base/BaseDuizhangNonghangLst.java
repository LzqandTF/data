package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_nonghang_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_nonghang_lst"
 */

public abstract class BaseDuizhangNonghangLst  implements Serializable {

	public static String REF = "DuizhangNonghangLst";
	public static String PROP_ACCOUNT_CHANGE_AMOUNT = "AccountChangeAmount";
	public static String PROP_MER_ACCOUNT = "MerAccount";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_MER_BATCH_FEE = "MerBatchFee";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_ORDER_ID = "OrderId";
	public static String PROP_ACCOUNTING_DATE = "AccountingDate";
	public static String PROP_HOST_STANCE = "HostStance";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_TRADING_TYPE = "TradingType";
	public static String PROP_MER_FEE = "MerFee";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_ID = "Id";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_ORI_ORDER_ID = "OriOrderId";
	public static String PROP_ACCOUNT_TYPE = "AccountType";


	// constructors
	public BaseDuizhangNonghangLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangNonghangLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String merCode;
	private java.lang.String tradingType;
	private java.lang.String orderId;
	private java.lang.String reqTime;
	private java.lang.String tradeAmount;
	private java.lang.String merAccount;
	private java.lang.String accountChangeAmount;
	private java.lang.String outAccount;
	private java.lang.String accountType;
	private java.lang.String merFee;
	private java.lang.String merBatchFee;
	private java.lang.String accountingDate;
	private java.lang.String hostStance;
	private java.lang.String reqSysStance;
	private java.lang.String oriOrderId;
	private java.lang.String deductSysReference;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.Integer bkChk;
	private java.lang.String deductStlmDate;
	private java.lang.String termId;
	private java.lang.String process;
	private boolean whetherTk;



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
	 * Return the value associated with the column: tradingType
	 */
	public java.lang.String getTradingType () {
		return tradingType;
	}

	/**
	 * Set the value related to the column: tradingType
	 * @param tradingType the tradingType value
	 */
	public void setTradingType (java.lang.String tradingType) {
		this.tradingType = tradingType;
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
	 * Return the value associated with the column: merAccount
	 */
	public java.lang.String getMerAccount () {
		return merAccount;
	}

	/**
	 * Set the value related to the column: merAccount
	 * @param merAccount the merAccount value
	 */
	public void setMerAccount (java.lang.String merAccount) {
		this.merAccount = merAccount;
	}



	/**
	 * Return the value associated with the column: accountChangeAmount
	 */
	public java.lang.String getAccountChangeAmount () {
		return accountChangeAmount;
	}

	/**
	 * Set the value related to the column: accountChangeAmount
	 * @param accountChangeAmount the accountChangeAmount value
	 */
	public void setAccountChangeAmount (java.lang.String accountChangeAmount) {
		this.accountChangeAmount = accountChangeAmount;
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
	 * Return the value associated with the column: accountType
	 */
	public java.lang.String getAccountType () {
		return accountType;
	}

	/**
	 * Set the value related to the column: accountType
	 * @param accountType the accountType value
	 */
	public void setAccountType (java.lang.String accountType) {
		this.accountType = accountType;
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
	 * Return the value associated with the column: mer_batch_fee
	 */
	public java.lang.String getMerBatchFee () {
		return merBatchFee;
	}

	/**
	 * Set the value related to the column: mer_batch_fee
	 * @param merBatchFee the mer_batch_fee value
	 */
	public void setMerBatchFee (java.lang.String merBatchFee) {
		this.merBatchFee = merBatchFee;
	}



	/**
	 * Return the value associated with the column: accounting_date
	 */
	public java.lang.String getAccountingDate () {
		return accountingDate;
	}

	/**
	 * Set the value related to the column: accounting_date
	 * @param accountingDate the accounting_date value
	 */
	public void setAccountingDate (java.lang.String accountingDate) {
		this.accountingDate = accountingDate;
	}



	/**
	 * Return the value associated with the column: hostStance
	 */
	public java.lang.String getHostStance () {
		return hostStance;
	}

	/**
	 * Set the value related to the column: hostStance
	 * @param hostStance the hostStance value
	 */
	public void setHostStance (java.lang.String hostStance) {
		this.hostStance = hostStance;
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
	 * Return the value associated with the column: oriOrderId
	 */
	public java.lang.String getOriOrderId () {
		return oriOrderId;
	}

	/**
	 * Set the value related to the column: oriOrderId
	 * @param oriOrderId the oriOrderId value
	 */
	public void setOriOrderId (java.lang.String oriOrderId) {
		this.oriOrderId = oriOrderId;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangNonghangLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangNonghangLst duizhangNonghangLst = (cn.com.chinaebi.dz.object.DuizhangNonghangLst) obj;
			if (null == this.getId() || null == duizhangNonghangLst.getId()) return false;
			else return (this.getId().equals(duizhangNonghangLst.getId()));
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