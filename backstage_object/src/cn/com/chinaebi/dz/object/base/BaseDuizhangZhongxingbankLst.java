package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_zhongxingbank_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_zhongxingbank_lst"
 */

public abstract class BaseDuizhangZhongxingbankLst  implements Serializable {

	public static String REF = "DuizhangZhongxingbankLst";
	public static String PROP_ORIGINAL_REFERENCE = "OriginalReference";
	public static String PROP_DEDUCT_MER_TERM_ID = "DeductMerTermId";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_REQ_RESPONSE = "ReqResponse";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_SETTLEMENT_AMOUNT = "SettlementAmount";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_AUTHORIZATION_CODE = "AuthorizationCode";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_DEDUCT_STLM_DATE_ = "DeductStlmDate_";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_ID = "Id";
	public static String PROP_DEDUCT_MER_CODE = "DeductMerCode";
	public static String PROP_TRADE_TIME = "TradeTime";
	public static String PROP_WHETHER_ERROE_HANDLE = "WhetherErroeHandle";
	public static String PROP_REFERENCE_KOULV = "ReferenceKoulv";


	// constructors
	public BaseDuizhangZhongxingbankLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangZhongxingbankLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDuizhangZhongxingbankLst (
		java.lang.String id,
		java.lang.String deductStlmDate,
		java.lang.String tradeTime,
		java.lang.String reqResponse,
		java.lang.String reqSysStance,
		java.lang.String outAccount,
		java.lang.String tradeAmount,
		java.lang.String deductMerTermId,
		java.lang.String deductMerCode,
		java.lang.String merName,
		int whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk,
		java.lang.String deductStlmDate_) {

		this.setId(id);
		this.setDeductStlmDate(deductStlmDate);
		this.setTradeTime(tradeTime);
		this.setReqResponse(reqResponse);
		this.setReqSysStance(reqSysStance);
		this.setOutAccount(outAccount);
		this.setTradeAmount(tradeAmount);
		this.setDeductMerTermId(deductMerTermId);
		this.setDeductMerCode(deductMerCode);
		this.setMerName(merName);
		this.setWhetherErroeHandle(whetherErroeHandle);
		this.setDzFileName(dzFileName);
		this.setInstName(instName);
		this.setBkChk(bkChk);
		this.setDeductStlmDate_(deductStlmDate_);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String deductStlmDate;
	private java.lang.String tradeTime;
	private java.lang.String reqResponse;
	private java.lang.String reqSysStance;
	private java.lang.String outAccount;
	private java.lang.String tradeAmount;
	private java.lang.String tradeFee;
	private java.lang.String settlementAmount;
	private java.lang.String referenceKoulv;
	private java.lang.String authorizationCode;
	private java.lang.String deductSysReference;
	private java.lang.String originalReference;
	private java.lang.String deductMerTermId;
	private java.lang.String deductMerCode;
	private java.lang.String merName;
	private int whetherErroeHandle;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.Integer bkChk;
	private java.lang.String deductStlmDate_;



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
	 * Return the value associated with the column: deductStlmDate
	 */
	public java.lang.String getDeductStlmDate () {
		return deductStlmDate;
	}

	/**
	 * Set the value related to the column: deductStlmDate
	 * @param deductStlmDate the deductStlmDate value
	 */
	public void setDeductStlmDate (java.lang.String deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
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
	 * Return the value associated with the column: reqResponse
	 */
	public java.lang.String getReqResponse () {
		return reqResponse;
	}

	/**
	 * Set the value related to the column: reqResponse
	 * @param reqResponse the reqResponse value
	 */
	public void setReqResponse (java.lang.String reqResponse) {
		this.reqResponse = reqResponse;
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
	 * Return the value associated with the column: referenceKoulv
	 */
	public java.lang.String getReferenceKoulv () {
		return referenceKoulv;
	}

	/**
	 * Set the value related to the column: referenceKoulv
	 * @param referenceKoulv the referenceKoulv value
	 */
	public void setReferenceKoulv (java.lang.String referenceKoulv) {
		this.referenceKoulv = referenceKoulv;
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
	 * Return the value associated with the column: originalReference
	 */
	public java.lang.String getOriginalReference () {
		return originalReference;
	}

	/**
	 * Set the value related to the column: originalReference
	 * @param originalReference the originalReference value
	 */
	public void setOriginalReference (java.lang.String originalReference) {
		this.originalReference = originalReference;
	}



	/**
	 * Return the value associated with the column: deductMerTermId
	 */
	public java.lang.String getDeductMerTermId () {
		return deductMerTermId;
	}

	/**
	 * Set the value related to the column: deductMerTermId
	 * @param deductMerTermId the deductMerTermId value
	 */
	public void setDeductMerTermId (java.lang.String deductMerTermId) {
		this.deductMerTermId = deductMerTermId;
	}



	/**
	 * Return the value associated with the column: deductMerCode
	 */
	public java.lang.String getDeductMerCode () {
		return deductMerCode;
	}

	/**
	 * Set the value related to the column: deductMerCode
	 * @param deductMerCode the deductMerCode value
	 */
	public void setDeductMerCode (java.lang.String deductMerCode) {
		this.deductMerCode = deductMerCode;
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
	 * Return the value associated with the column: whetherErroeHandle
	 */
	public int getWhetherErroeHandle () {
		return whetherErroeHandle;
	}

	/**
	 * Set the value related to the column: whetherErroeHandle
	 * @param whetherErroeHandle the whetherErroeHandle value
	 */
	public void setWhetherErroeHandle (int whetherErroeHandle) {
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
	public java.lang.String getDeductStlmDate_ () {
		return deductStlmDate_;
	}

	/**
	 * Set the value related to the column: deduct_stlm_date
	 * @param deductStlmDate_ the deduct_stlm_date value
	 */
	public void setDeductStlmDate_ (java.lang.String deductStlmDate_) {
		this.deductStlmDate_ = deductStlmDate_;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst duizhangZhongxingbankLst = (cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst) obj;
			if (null == this.getId() || null == duizhangZhongxingbankLst.getId()) return false;
			else return (this.getId().equals(duizhangZhongxingbankLst.getId()));
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