package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_yzcx_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_yzcx_lst"
 */

public abstract class BaseDuizhangYzcxLst  implements Serializable {

	public static String REF = "DuizhangYzcxLst";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_ORDER_ID = "OrderId";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_AUTHORIZATION_CODE = "AuthorizationCode";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_ID = "Id";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_WHETHER_ERROE_HANDLE = "WhetherErroeHandle";
	public static String PROP_SETTLE_AMOUNT = "SettleAmount";
	public static String PROP_BK1 = "Bk1";
	public static String PROP_TRADE_CODE = "TradeCode";
	public static String PROP_BK2 = "Bk2";


	// constructors
	public BaseDuizhangYzcxLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangYzcxLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDuizhangYzcxLst (
		java.lang.String id,
		java.lang.String deductStlmDate,
		java.lang.String reqTime,
		java.lang.String merCode,
		java.lang.String termId,
		java.lang.String tradeAmount,
		java.lang.Integer bkChk,
		java.lang.Integer whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.String outAccount) {

		this.setId(id);
		this.setDeductStlmDate(deductStlmDate);
		this.setReqTime(reqTime);
		this.setMerCode(merCode);
		this.setTermId(termId);
		this.setTradeAmount(tradeAmount);
		this.setBkChk(bkChk);
		this.setWhetherErroeHandle(whetherErroeHandle);
		this.setDzFileName(dzFileName);
		this.setInstName(instName);
		this.setOutAccount(outAccount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String tradeCode;
	private java.lang.String deductStlmDate;
	private java.lang.String reqTime;
	private java.lang.String orderId;
	private java.lang.String reqSysStance;
	private java.lang.String merCode;
	private java.lang.String termId;
	private java.lang.String tradeAmount;
	private java.lang.String tradeFee;
	private java.lang.String settleAmount;
	private java.lang.String authorizationCode;
	private java.lang.String bk1;
	private java.lang.String bk2;
	private java.lang.Integer bkChk;
	private java.lang.Byte whetherTk;
	private java.lang.Integer whetherErroeHandle;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.String deductSysReference;
	private java.lang.String outAccount;



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
	 * Return the value associated with the column: trade_code
	 */
	public java.lang.String getTradeCode () {
		return tradeCode;
	}

	/**
	 * Set the value related to the column: trade_code
	 * @param tradeCode the trade_code value
	 */
	public void setTradeCode (java.lang.String tradeCode) {
		this.tradeCode = tradeCode;
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
	 * Return the value associated with the column: settleAmount
	 */
	public java.lang.String getSettleAmount () {
		return settleAmount;
	}

	/**
	 * Set the value related to the column: settleAmount
	 * @param settleAmount the settleAmount value
	 */
	public void setSettleAmount (java.lang.String settleAmount) {
		this.settleAmount = settleAmount;
	}



	/**
	 * Return the value associated with the column: authorizationCode
	 */
	public java.lang.String getAuthorizationCode () {
		return authorizationCode;
	}

	/**
	 * Set the value related to the column: authorizationCode
	 * @param authorizationCode the authorizationCode value
	 */
	public void setAuthorizationCode (java.lang.String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}



	/**
	 * Return the value associated with the column: bk_1
	 */
	public java.lang.String getBk1 () {
		return bk1;
	}

	/**
	 * Set the value related to the column: bk_1
	 * @param bk1 the bk_1 value
	 */
	public void setBk1 (java.lang.String bk1) {
		this.bk1 = bk1;
	}



	/**
	 * Return the value associated with the column: bk_2
	 */
	public java.lang.String getBk2 () {
		return bk2;
	}

	/**
	 * Set the value related to the column: bk_2
	 * @param bk2 the bk_2 value
	 */
	public void setBk2 (java.lang.String bk2) {
		this.bk2 = bk2;
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
	public java.lang.Byte getWhetherTk () {
		return whetherTk;
	}

	/**
	 * Set the value related to the column: whetherTk
	 * @param whetherTk the whetherTk value
	 */
	public void setWhetherTk (java.lang.Byte whetherTk) {
		this.whetherTk = whetherTk;
	}



	/**
	 * Return the value associated with the column: whetherErroeHandle
	 */
	public java.lang.Integer getWhetherErroeHandle () {
		return whetherErroeHandle;
	}

	/**
	 * Set the value related to the column: whetherErroeHandle
	 * @param whetherErroeHandle the whetherErroeHandle value
	 */
	public void setWhetherErroeHandle (java.lang.Integer whetherErroeHandle) {
		this.whetherErroeHandle = whetherErroeHandle;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangYzcxLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangYzcxLst duizhangYzcxLst = (cn.com.chinaebi.dz.object.DuizhangYzcxLst) obj;
			if (null == this.getId() || null == duizhangYzcxLst.getId()) return false;
			else return (this.getId().equals(duizhangYzcxLst.getId()));
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