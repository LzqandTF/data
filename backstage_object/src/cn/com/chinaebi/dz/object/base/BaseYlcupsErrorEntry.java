package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the ylcups_error_entry table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="ylcups_error_entry"
 */

public abstract class BaseYlcupsErrorEntry  implements Serializable {

	public static String REF = "YlcupsErrorEntry";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_AUDIT_OPERATOR = "AuditOperator";
	public static String PROP_DEDUCT_SYS_ID = "DeductSysId";
	public static String PROP_OPERATOR = "Operator";
	public static String PROP_REASON_CODE = "ReasonCode";
	public static String PROP_HANDLING_ID = "HandlingId";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_TRADE_SOURCE = "TradeSource";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_TRADE_STATUS = "TradeStatus";
	public static String PROP_TRADE_TIME = "TradeTime";
	public static String PROP_COMMIT_TIME = "CommitTime";
	public static String PROP_TRADE_MSG_TYPE = "TradeMsgType";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TURNDOWN_REMARK = "TurndownRemark";
	public static String PROP_TRADE_RESULT = "TradeResult";
	public static String PROP_ACQ_INST_ID_CODE = "AcqInstIdCode";
	public static String PROP_TRADE_CATEGORY = "TradeCategory";
	public static String PROP_ENTERING_TIME = "EnteringTime";
	public static String PROP_INST_TYPE = "InstType";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_ID = "Id";


	// constructors
	public BaseYlcupsErrorEntry () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseYlcupsErrorEntry (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseYlcupsErrorEntry (
		java.lang.String id,
		java.lang.String outAccount,
		java.util.Date tradeTime,
		java.lang.String reqSysStance,
		java.lang.String tradeAmount,
		java.lang.Integer handlingId,
		java.lang.String reasonCode,
		java.util.Date deductStlmDate,
		java.lang.String operator,
		java.lang.Integer bkChk) {

		this.setId(id);
		this.setOutAccount(outAccount);
		this.setTradeTime(tradeTime);
		this.setReqSysStance(reqSysStance);
		this.setTradeAmount(tradeAmount);
		this.setHandlingId(handlingId);
		this.setReasonCode(reasonCode);
		this.setDeductStlmDate(deductStlmDate);
		this.setOperator(operator);
		this.setBkChk(bkChk);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String outAccount;
	private java.lang.String merName;
	private java.util.Date tradeTime;
	private java.lang.Integer tradeResult;
	private java.lang.String deductSysReference;
	private java.lang.String reqSysStance;
	private java.lang.String tradeAmount;
	private java.lang.Integer handlingId;
	private java.lang.String acqInstIdCode;
	private java.lang.String reasonCode;
	private java.util.Date deductStlmDate;
	private java.lang.String tradeCategory;
	private java.lang.Integer deductSysId;
	private java.lang.String tradeSource;
	private java.lang.Integer tradeStatus;
	private java.lang.String turndownRemark;
	private java.lang.String enteringTime;
	private java.lang.String operator;
	private java.lang.String auditOperator;
	private java.lang.String commitTime;
	private java.lang.String process;
	private java.lang.Integer tradeMsgType;
	private java.lang.Integer bkChk;
	private java.lang.Integer instType;



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
	 * Return the value associated with the column: out_account
	 */
	public java.lang.String getOutAccount () {
		return outAccount;
	}

	/**
	 * Set the value related to the column: out_account
	 * @param outAccount the out_account value
	 */
	public void setOutAccount (java.lang.String outAccount) {
		this.outAccount = outAccount;
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
	 * Return the value associated with the column: trade_time
	 */
	public java.util.Date getTradeTime () {
		return tradeTime;
	}

	/**
	 * Set the value related to the column: trade_time
	 * @param tradeTime the trade_time value
	 */
	public void setTradeTime (java.util.Date tradeTime) {
		this.tradeTime = tradeTime;
	}



	/**
	 * Return the value associated with the column: trade_result
	 */
	public java.lang.Integer getTradeResult () {
		return tradeResult;
	}

	/**
	 * Set the value related to the column: trade_result
	 * @param tradeResult the trade_result value
	 */
	public void setTradeResult (java.lang.Integer tradeResult) {
		this.tradeResult = tradeResult;
	}



	/**
	 * Return the value associated with the column: deduct_sys_reference
	 */
	public java.lang.String getDeductSysReference () {
		return deductSysReference;
	}

	/**
	 * Set the value related to the column: deduct_sys_reference
	 * @param deductSysReference the deduct_sys_reference value
	 */
	public void setDeductSysReference (java.lang.String deductSysReference) {
		this.deductSysReference = deductSysReference;
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
	 * Return the value associated with the column: handling_id
	 */
	public java.lang.Integer getHandlingId () {
		return handlingId;
	}

	/**
	 * Set the value related to the column: handling_id
	 * @param handlingId the handling_id value
	 */
	public void setHandlingId (java.lang.Integer handlingId) {
		this.handlingId = handlingId;
	}



	/**
	 * Return the value associated with the column: acqInstIdCode
	 */
	public java.lang.String getAcqInstIdCode () {
		return acqInstIdCode;
	}

	/**
	 * Set the value related to the column: acqInstIdCode
	 * @param acqInstIdCode the acqInstIdCode value
	 */
	public void setAcqInstIdCode (java.lang.String acqInstIdCode) {
		this.acqInstIdCode = acqInstIdCode;
	}



	/**
	 * Return the value associated with the column: reason_code
	 */
	public java.lang.String getReasonCode () {
		return reasonCode;
	}

	/**
	 * Set the value related to the column: reason_code
	 * @param reasonCode the reason_code value
	 */
	public void setReasonCode (java.lang.String reasonCode) {
		this.reasonCode = reasonCode;
	}



	/**
	 * Return the value associated with the column: deductStlmDate
	 */
	public java.util.Date getDeductStlmDate () {
		return deductStlmDate;
	}

	/**
	 * Set the value related to the column: deductStlmDate
	 * @param deductStlmDate the deductStlmDate value
	 */
	public void setDeductStlmDate (java.util.Date deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
	}



	/**
	 * Return the value associated with the column: trade_category
	 */
	public java.lang.String getTradeCategory () {
		return tradeCategory;
	}

	/**
	 * Set the value related to the column: trade_category
	 * @param tradeCategory the trade_category value
	 */
	public void setTradeCategory (java.lang.String tradeCategory) {
		this.tradeCategory = tradeCategory;
	}



	/**
	 * Return the value associated with the column: deduct_sys_id
	 */
	public java.lang.Integer getDeductSysId () {
		return deductSysId;
	}

	/**
	 * Set the value related to the column: deduct_sys_id
	 * @param deductSysId the deduct_sys_id value
	 */
	public void setDeductSysId (java.lang.Integer deductSysId) {
		this.deductSysId = deductSysId;
	}



	/**
	 * Return the value associated with the column: trade_source
	 */
	public java.lang.String getTradeSource () {
		return tradeSource;
	}

	/**
	 * Set the value related to the column: trade_source
	 * @param tradeSource the trade_source value
	 */
	public void setTradeSource (java.lang.String tradeSource) {
		this.tradeSource = tradeSource;
	}



	/**
	 * Return the value associated with the column: trade_status
	 */
	public java.lang.Integer getTradeStatus () {
		return tradeStatus;
	}

	/**
	 * Set the value related to the column: trade_status
	 * @param tradeStatus the trade_status value
	 */
	public void setTradeStatus (java.lang.Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}



	/**
	 * Return the value associated with the column: turnDown_remark
	 */
	public java.lang.String getTurndownRemark () {
		return turndownRemark;
	}

	/**
	 * Set the value related to the column: turnDown_remark
	 * @param turndownRemark the turnDown_remark value
	 */
	public void setTurndownRemark (java.lang.String turndownRemark) {
		this.turndownRemark = turndownRemark;
	}



	/**
	 * Return the value associated with the column: entering_time
	 */
	public java.lang.String getEnteringTime () {
		return enteringTime;
	}

	/**
	 * Set the value related to the column: entering_time
	 * @param enteringTime the entering_time value
	 */
	public void setEnteringTime (java.lang.String enteringTime) {
		this.enteringTime = enteringTime;
	}



	/**
	 * Return the value associated with the column: operator
	 */
	public java.lang.String getOperator () {
		return operator;
	}

	/**
	 * Set the value related to the column: operator
	 * @param operator the operator value
	 */
	public void setOperator (java.lang.String operator) {
		this.operator = operator;
	}



	/**
	 * Return the value associated with the column: audit_operator
	 */
	public java.lang.String getAuditOperator () {
		return auditOperator;
	}

	/**
	 * Set the value related to the column: audit_operator
	 * @param auditOperator the audit_operator value
	 */
	public void setAuditOperator (java.lang.String auditOperator) {
		this.auditOperator = auditOperator;
	}



	/**
	 * Return the value associated with the column: commit_time
	 */
	public java.lang.String getCommitTime () {
		return commitTime;
	}

	/**
	 * Set the value related to the column: commit_time
	 * @param commitTime the commit_time value
	 */
	public void setCommitTime (java.lang.String commitTime) {
		this.commitTime = commitTime;
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
	 * Return the value associated with the column: tradeMsgType
	 */
	public java.lang.Integer getTradeMsgType () {
		return tradeMsgType;
	}

	/**
	 * Set the value related to the column: tradeMsgType
	 * @param tradeMsgType the tradeMsgType value
	 */
	public void setTradeMsgType (java.lang.Integer tradeMsgType) {
		this.tradeMsgType = tradeMsgType;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.YlcupsErrorEntry)) return false;
		else {
			cn.com.chinaebi.dz.object.YlcupsErrorEntry ylcupsErrorEntry = (cn.com.chinaebi.dz.object.YlcupsErrorEntry) obj;
			if (null == this.getId() || null == ylcupsErrorEntry.getId()) return false;
			else return (this.getId().equals(ylcupsErrorEntry.getId()));
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