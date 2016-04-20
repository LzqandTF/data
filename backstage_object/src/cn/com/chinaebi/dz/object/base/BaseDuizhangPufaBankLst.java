package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_pufa_bank_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_pufa_bank_lst"
 */

public abstract class BaseDuizhangPufaBankLst  implements Serializable {

	public static String REF = "DuizhangPufaBankLst";
	public static String PROP_ORDER_ABBREVIATIONS = "OrderAbbreviations";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_ORDER_ID = "OrderId";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_KEEP1 = "Keep1";
	public static String PROP_KEEP2 = "Keep2";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_DEDUCT_SYS_RESPONSE = "DeductSysResponse";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_SETTLEMENT_AMOUNT = "SettlementAmount";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_ID = "Id";
	public static String PROP_WHETHER_TK = "WhetherTk";


	// constructors
	public BaseDuizhangPufaBankLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangPufaBankLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDuizhangPufaBankLst (
		java.lang.String id,
		boolean whetherTk) {

		this.setId(id);
		this.setWhetherTk(whetherTk);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String orderAbbreviations;
	private java.lang.String deductStlmDate;
	private java.lang.String reqTime;
	private java.lang.String orderId;
	private java.lang.String reqSysStance;
	private java.lang.String merCode;
	private java.lang.String deductSysReference;
	private java.lang.String tradeAmount;
	private java.lang.String tradeFee;
	private java.lang.String settlementAmount;
	private java.lang.String deductSysResponse;
	private java.lang.String keep1;
	private java.lang.String keep2;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.Integer bkChk;
	private boolean whetherTk;
	private java.lang.String outAccount;
	private java.lang.String termId;
	private java.lang.String process;



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
	 * Return the value associated with the column: orderAbbreviations
	 */
	public java.lang.String getOrderAbbreviations () {
		return orderAbbreviations;
	}

	/**
	 * Set the value related to the column: orderAbbreviations
	 * @param orderAbbreviations the orderAbbreviations value
	 */
	public void setOrderAbbreviations (java.lang.String orderAbbreviations) {
		this.orderAbbreviations = orderAbbreviations;
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
	 * Return the value associated with the column: settlementAmount
	 */
	public java.lang.String getSettlementAmount () {
		return settlementAmount;
	}

	/**
	 * Set the value related to the column: settlementAmount
	 * @param settlementAmount the settlementAmount value
	 */
	public void setSettlementAmount (java.lang.String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}



	/**
	 * Return the value associated with the column: deductSysResponse
	 */
	public java.lang.String getDeductSysResponse () {
		return deductSysResponse;
	}

	/**
	 * Set the value related to the column: deductSysResponse
	 * @param deductSysResponse the deductSysResponse value
	 */
	public void setDeductSysResponse (java.lang.String deductSysResponse) {
		this.deductSysResponse = deductSysResponse;
	}



	/**
	 * Return the value associated with the column: keep1
	 */
	public java.lang.String getKeep1 () {
		return keep1;
	}

	/**
	 * Set the value related to the column: keep1
	 * @param keep1 the keep1 value
	 */
	public void setKeep1 (java.lang.String keep1) {
		this.keep1 = keep1;
	}



	/**
	 * Return the value associated with the column: keep2
	 */
	public java.lang.String getKeep2 () {
		return keep2;
	}

	/**
	 * Set the value related to the column: keep2
	 * @param keep2 the keep2 value
	 */
	public void setKeep2 (java.lang.String keep2) {
		this.keep2 = keep2;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangPufaBankLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangPufaBankLst duizhangPufaBankLst = (cn.com.chinaebi.dz.object.DuizhangPufaBankLst) obj;
			if (null == this.getId() || null == duizhangPufaBankLst.getId()) return false;
			else return (this.getId().equals(duizhangPufaBankLst.getId()));
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