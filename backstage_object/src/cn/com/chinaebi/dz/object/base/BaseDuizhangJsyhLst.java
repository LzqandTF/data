package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_jsyh_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_jsyh_lst"
 */

public abstract class BaseDuizhangJsyhLst  implements Serializable {

	public static String REF = "DuizhangJsyhLst";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_ORDER_ID = "OrderId";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_TRADING_STATUS = "TradingStatus";
	public static String PROP_REMARK2 = "Remark2";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_REMARK1 = "Remark1";
	public static String PROP_TRADING_TYPE = "TradingType";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_COUNTER_NO = "CounterNo";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_FEE_POUNDAGE = "FeePoundage";
	public static String PROP_ID = "Id";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_REFUND_AMOUNT = "RefundAmount";


	// constructors
	public BaseDuizhangJsyhLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangJsyhLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String orderId;
	private java.lang.String outAccount;
	private java.lang.String tradeAmount;
	private java.lang.String refundAmount;
	private java.lang.String tradeFee;
	private java.lang.String feePoundage;
	private java.lang.String reqTime;
	private java.lang.String counterNo;
	private java.lang.String reqSysStance;
	private java.lang.String remark1;
	private java.lang.String remark2;
	private java.lang.String tradingStatus;
	private java.lang.String tradingType;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.Integer bkChk;
	private java.lang.String deductStlmDate;
	private java.lang.String deductSysReference;
	private java.lang.String termId;
	private java.lang.String merCode;
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
	 * Return the value associated with the column: refundAmount
	 */
	public java.lang.String getRefundAmount () {
		return refundAmount;
	}

	/**
	 * Set the value related to the column: refundAmount
	 * @param refundAmount the refundAmount value
	 */
	public void setRefundAmount (java.lang.String refundAmount) {
		this.refundAmount = refundAmount;
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
	 * Return the value associated with the column: fee_poundage
	 */
	public java.lang.String getFeePoundage () {
		return feePoundage;
	}

	/**
	 * Set the value related to the column: fee_poundage
	 * @param feePoundage the fee_poundage value
	 */
	public void setFeePoundage (java.lang.String feePoundage) {
		this.feePoundage = feePoundage;
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
	 * Return the value associated with the column: counter_no
	 */
	public java.lang.String getCounterNo () {
		return counterNo;
	}

	/**
	 * Set the value related to the column: counter_no
	 * @param counterNo the counter_no value
	 */
	public void setCounterNo (java.lang.String counterNo) {
		this.counterNo = counterNo;
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
	 * Return the value associated with the column: remark1
	 */
	public java.lang.String getRemark1 () {
		return remark1;
	}

	/**
	 * Set the value related to the column: remark1
	 * @param remark1 the remark1 value
	 */
	public void setRemark1 (java.lang.String remark1) {
		this.remark1 = remark1;
	}



	/**
	 * Return the value associated with the column: remark2
	 */
	public java.lang.String getRemark2 () {
		return remark2;
	}

	/**
	 * Set the value related to the column: remark2
	 * @param remark2 the remark2 value
	 */
	public void setRemark2 (java.lang.String remark2) {
		this.remark2 = remark2;
	}



	/**
	 * Return the value associated with the column: trading_status
	 */
	public java.lang.String getTradingStatus () {
		return tradingStatus;
	}

	/**
	 * Set the value related to the column: trading_status
	 * @param tradingStatus the trading_status value
	 */
	public void setTradingStatus (java.lang.String tradingStatus) {
		this.tradingStatus = tradingStatus;
	}



	/**
	 * Return the value associated with the column: trading_type
	 */
	public java.lang.String getTradingType () {
		return tradingType;
	}

	/**
	 * Set the value related to the column: trading_type
	 * @param tradingType the trading_type value
	 */
	public void setTradingType (java.lang.String tradingType) {
		this.tradingType = tradingType;
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
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangJsyhLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangJsyhLst duizhangJsyhLst = (cn.com.chinaebi.dz.object.DuizhangJsyhLst) obj;
			if (null == this.getId() || null == duizhangJsyhLst.getId()) return false;
			else return (this.getId().equals(duizhangJsyhLst.getId()));
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