package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_jiangSuyh_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_jiangSuyh_lst"
 */

public abstract class BaseDuizhangJiangsuyhLst  implements Serializable {

	public static String REF = "DuizhangJiangsuyhLst";
	public static String PROP_IN_ACCOUNT = "InAccount";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_ORDER_STATUS = "OrderStatus";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_JS_STATUS = "JsStatus";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_JS_DATE = "JsDate";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_QS_DATE = "QsDate";
	public static String PROP_CURRENCY = "Currency";
	public static String PROP_PAY_TYPE = "PayType";
	public static String PROP_OUT_ACCOUNT_NAME = "OutAccountName";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_QS_STATUS = "QsStatus";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_QS_RESULT = "QsResult";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_ORDER_ID = "OrderId";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_ID = "Id";
	public static String PROP_IN_ACCOUNT_NAME = "InAccountName";


	// constructors
	public BaseDuizhangJiangsuyhLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangJiangsuyhLst (java.lang.String id) {
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
	private java.lang.String payType;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.String deductSysReference;
	private java.lang.String termId;
	private java.lang.String merCode;
	private java.lang.String process;
	private java.lang.String qsStatus;
	private java.lang.String qsResult;
	private java.lang.String merName;
	private java.lang.String orderStatus;
	private java.lang.String jsStatus;
	private java.lang.String jsDate;
	private java.lang.String qsDate;
	private java.lang.String inAccount;
	private java.lang.String outAccountName;
	private java.lang.String inAccountName;
	private java.lang.String currency;



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
	 * Return the value associated with the column: payType
	 */
	public java.lang.String getPayType () {
		return payType;
	}

	/**
	 * Set the value related to the column: payType
	 * @param payType the payType value
	 */
	public void setPayType (java.lang.String payType) {
		this.payType = payType;
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
	 * Return the value associated with the column: qsStatus
	 */
	public java.lang.String getQsStatus () {
		return qsStatus;
	}

	/**
	 * Set the value related to the column: qsStatus
	 * @param qsStatus the qsStatus value
	 */
	public void setQsStatus (java.lang.String qsStatus) {
		this.qsStatus = qsStatus;
	}



	/**
	 * Return the value associated with the column: qsResult
	 */
	public java.lang.String getQsResult () {
		return qsResult;
	}

	/**
	 * Set the value related to the column: qsResult
	 * @param qsResult the qsResult value
	 */
	public void setQsResult (java.lang.String qsResult) {
		this.qsResult = qsResult;
	}



	/**
	 * Return the value associated with the column: merName
	 */
	public java.lang.String getMerName () {
		return merName;
	}

	/**
	 * Set the value related to the column: merName
	 * @param merName the merName value
	 */
	public void setMerName (java.lang.String merName) {
		this.merName = merName;
	}



	/**
	 * Return the value associated with the column: orderStatus
	 */
	public java.lang.String getOrderStatus () {
		return orderStatus;
	}

	/**
	 * Set the value related to the column: orderStatus
	 * @param orderStatus the orderStatus value
	 */
	public void setOrderStatus (java.lang.String orderStatus) {
		this.orderStatus = orderStatus;
	}



	/**
	 * Return the value associated with the column: jsStatus
	 */
	public java.lang.String getJsStatus () {
		return jsStatus;
	}

	/**
	 * Set the value related to the column: jsStatus
	 * @param jsStatus the jsStatus value
	 */
	public void setJsStatus (java.lang.String jsStatus) {
		this.jsStatus = jsStatus;
	}



	/**
	 * Return the value associated with the column: jsDate
	 */
	public java.lang.String getJsDate () {
		return jsDate;
	}

	/**
	 * Set the value related to the column: jsDate
	 * @param jsDate the jsDate value
	 */
	public void setJsDate (java.lang.String jsDate) {
		this.jsDate = jsDate;
	}



	/**
	 * Return the value associated with the column: qsDate
	 */
	public java.lang.String getQsDate () {
		return qsDate;
	}

	/**
	 * Set the value related to the column: qsDate
	 * @param qsDate the qsDate value
	 */
	public void setQsDate (java.lang.String qsDate) {
		this.qsDate = qsDate;
	}



	/**
	 * Return the value associated with the column: inAccount
	 */
	public java.lang.String getInAccount () {
		return inAccount;
	}

	/**
	 * Set the value related to the column: inAccount
	 * @param inAccount the inAccount value
	 */
	public void setInAccount (java.lang.String inAccount) {
		this.inAccount = inAccount;
	}



	/**
	 * Return the value associated with the column: outAccountName
	 */
	public java.lang.String getOutAccountName () {
		return outAccountName;
	}

	/**
	 * Set the value related to the column: outAccountName
	 * @param outAccountName the outAccountName value
	 */
	public void setOutAccountName (java.lang.String outAccountName) {
		this.outAccountName = outAccountName;
	}



	/**
	 * Return the value associated with the column: inAccountName
	 */
	public java.lang.String getInAccountName () {
		return inAccountName;
	}

	/**
	 * Set the value related to the column: inAccountName
	 * @param inAccountName the inAccountName value
	 */
	public void setInAccountName (java.lang.String inAccountName) {
		this.inAccountName = inAccountName;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangJiangsuyhLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangJiangsuyhLst duizhangJiangsuyhLst = (cn.com.chinaebi.dz.object.DuizhangJiangsuyhLst) obj;
			if (null == this.getId() || null == duizhangJiangsuyhLst.getId()) return false;
			else return (this.getId().equals(duizhangJiangsuyhLst.getId()));
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