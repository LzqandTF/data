package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the channel_trade_collect table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="channel_trade_collect"
 */

public abstract class BaseChannelTradeCollect  implements Serializable {

	public static String REF = "ChannelTradeCollect";
	public static String PROP_IN_ACCOUNT = "InAccount";
	public static String PROP_ORIGINAL_TRANS_INFO = "OriginalTransInfo";
	public static String PROP_ZF_FEE_BJ = "ZfFeeBj";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_TRADE_CURRENCY_NAME = "TradeCurrencyName";
	public static String PROP_DEDUCT_SYS_ID = "DeductSysId";
	public static String PROP_OID = "Oid";
	public static String PROP_REQ_MER_TERM_ID = "ReqMerTermId";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_REQ_SYS_ID = "ReqSysId";
	public static String PROP_DEDUCT_SYS_STANCE = "DeductSysStance";
	public static String PROP_REQ_MER_CODE = "ReqMerCode";
	public static String PROP_ZF_FEE = "ZfFee";
	public static String PROP_BIND_MID = "BindMid";
	public static String PROP_SETTLE_CODE = "SettleCode";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_TRADE_CURRENCY = "TradeCurrency";
	public static String PROP_ZF_FILE_FEE = "ZfFileFee";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_TRADE_TIME = "TradeTime";
	public static String PROP_ADDITIONAL_RESPONSE_DATA = "AdditionalResponseData";
	public static String PROP_GATE = "Gate";
	public static String PROP_SYS_DATE = "SysDate";
	public static String PROP_DEDUCT_MER_TERM_ID = "DeductMerTermId";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_IN_USER_ID = "InUserId";
	public static String PROP_TRADE_RESULT = "TradeResult";
	public static String PROP_OUT_ACCOUNT_TYPE = "OutAccountType";
	public static String PROP_OUT_USER_ID = "OutUserId";
	public static String PROP_MER_FEE = "MerFee";
	public static String PROP_IN_CARD_NAME = "InCardName";
	public static String PROP_INST_TYPE = "InstType";
	public static String PROP_DEDUCT_SYS_TIME = "DeductSysTime";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_TRADE_TYPE = "TradeType";
	public static String PROP_AUTHORIZATION_CODE = "AuthorizationCode";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_BANK_ID = "BankId";
	public static String PROP_DEDUCT_ROLL_BK = "DeductRollBk";
	public static String PROP_ID = "Id";
	public static String PROP_DEDUCT_MER_CODE = "DeductMerCode";
	public static String PROP_REMARK = "Remark";
	public static String PROP_DY_MER_NAME = "DyMerName";
	public static String PROP_FEE_FORMULA = "FeeFormula";


	// constructors
	public BaseChannelTradeCollect () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseChannelTradeCollect (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseChannelTradeCollect (
		java.lang.String id,
		java.lang.String outAccount,
		java.lang.Double tradeAmount,
		java.lang.Integer tradeResult,
		java.lang.Integer deductSysId,
		java.lang.String deductSysStance,
		java.lang.String deductMerCode,
		java.lang.Long tradeTime,
		java.lang.Long deductSysTime,
		java.lang.Integer deductStlmDate,
		java.lang.Integer instType,
		java.lang.String settleCode,
		java.lang.String instName,
		java.lang.Integer sysDate,
		java.lang.Integer bankId) {

		this.setId(id);
		this.setOutAccount(outAccount);
		this.setTradeAmount(tradeAmount);
		this.setTradeResult(tradeResult);
		this.setDeductSysId(deductSysId);
		this.setDeductSysStance(deductSysStance);
		this.setDeductMerCode(deductMerCode);
		this.setTradeTime(tradeTime);
		this.setDeductSysTime(deductSysTime);
		this.setDeductStlmDate(deductStlmDate);
		this.setInstType(instType);
		this.setSettleCode(settleCode);
		this.setInstName(instName);
		this.setSysDate(sysDate);
		this.setBankId(bankId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String outAccount;
	private java.lang.String outAccountType;
	private java.lang.String inAccount;
	private java.lang.String inCardName;
	private java.lang.Double tradeAmount;
	private java.lang.String tradeFee;
	private java.lang.String tradeCurrency;
	private java.lang.String tradeCurrencyName;
	private java.lang.Integer tradeResult;
	private java.lang.Integer reqSysId;
	private java.lang.String reqSysStance;
	private java.lang.String reqMerCode;
	private java.lang.String reqMerTermId;
	private java.lang.Integer deductSysId;
	private java.lang.String deductSysStance;
	private java.lang.String deductMerCode;
	private java.lang.String deductMerTermId;
	private java.lang.Long tradeTime;
	private java.lang.Long deductSysTime;
	private java.lang.Integer deductStlmDate;
	private java.lang.Byte deductRollBk;
	private java.lang.Integer tradeType;
	private java.lang.String authorizationCode;
	private java.lang.String deductSysReference;
	private java.lang.String merName;
	private java.lang.Double merFee;
	private java.lang.Byte whetherTk;
	private java.lang.Double zfFee;
	private java.lang.String zfFileFee;
	private java.lang.Integer zfFeeBj;
	private java.lang.String feeFormula;
	private java.lang.String originalTransInfo;
	private java.lang.Integer instType;
	private java.lang.Integer gate;
	private java.lang.String settleCode;
	private java.lang.String instName;
	private java.lang.String dyMerName;
	private java.lang.String oid;
	private java.lang.String additionalResponseData;
	private java.lang.Integer sysDate;
	private java.lang.Integer bankId;
	private java.lang.String outUserId;
	private java.lang.String inUserId;
	private java.lang.String bindMid;
	private java.lang.String remark;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="trade_id"
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
	 * Return the value associated with the column: out_account_type
	 */
	public java.lang.String getOutAccountType () {
		return outAccountType;
	}

	/**
	 * Set the value related to the column: out_account_type
	 * @param outAccountType the out_account_type value
	 */
	public void setOutAccountType (java.lang.String outAccountType) {
		this.outAccountType = outAccountType;
	}



	/**
	 * Return the value associated with the column: in_account
	 */
	public java.lang.String getInAccount () {
		return inAccount;
	}

	/**
	 * Set the value related to the column: in_account
	 * @param inAccount the in_account value
	 */
	public void setInAccount (java.lang.String inAccount) {
		this.inAccount = inAccount;
	}



	/**
	 * Return the value associated with the column: in_card_name
	 */
	public java.lang.String getInCardName () {
		return inCardName;
	}

	/**
	 * Set the value related to the column: in_card_name
	 * @param inCardName the in_card_name value
	 */
	public void setInCardName (java.lang.String inCardName) {
		this.inCardName = inCardName;
	}



	/**
	 * Return the value associated with the column: trade_amount
	 */
	public java.lang.Double getTradeAmount () {
		return tradeAmount;
	}

	/**
	 * Set the value related to the column: trade_amount
	 * @param tradeAmount the trade_amount value
	 */
	public void setTradeAmount (java.lang.Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}



	/**
	 * Return the value associated with the column: trade_fee
	 */
	public java.lang.String getTradeFee () {
		return tradeFee;
	}

	/**
	 * Set the value related to the column: trade_fee
	 * @param tradeFee the trade_fee value
	 */
	public void setTradeFee (java.lang.String tradeFee) {
		this.tradeFee = tradeFee;
	}



	/**
	 * Return the value associated with the column: trade_currency
	 */
	public java.lang.String getTradeCurrency () {
		return tradeCurrency;
	}

	/**
	 * Set the value related to the column: trade_currency
	 * @param tradeCurrency the trade_currency value
	 */
	public void setTradeCurrency (java.lang.String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}



	/**
	 * Return the value associated with the column: trade_currency_name
	 */
	public java.lang.String getTradeCurrencyName () {
		return tradeCurrencyName;
	}

	/**
	 * Set the value related to the column: trade_currency_name
	 * @param tradeCurrencyName the trade_currency_name value
	 */
	public void setTradeCurrencyName (java.lang.String tradeCurrencyName) {
		this.tradeCurrencyName = tradeCurrencyName;
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
	 * Return the value associated with the column: req_sys_id
	 */
	public java.lang.Integer getReqSysId () {
		return reqSysId;
	}

	/**
	 * Set the value related to the column: req_sys_id
	 * @param reqSysId the req_sys_id value
	 */
	public void setReqSysId (java.lang.Integer reqSysId) {
		this.reqSysId = reqSysId;
	}



	/**
	 * Return the value associated with the column: req_sys_stance
	 */
	public java.lang.String getReqSysStance () {
		return reqSysStance;
	}

	/**
	 * Set the value related to the column: req_sys_stance
	 * @param reqSysStance the req_sys_stance value
	 */
	public void setReqSysStance (java.lang.String reqSysStance) {
		this.reqSysStance = reqSysStance;
	}



	/**
	 * Return the value associated with the column: req_mer_code
	 */
	public java.lang.String getReqMerCode () {
		return reqMerCode;
	}

	/**
	 * Set the value related to the column: req_mer_code
	 * @param reqMerCode the req_mer_code value
	 */
	public void setReqMerCode (java.lang.String reqMerCode) {
		this.reqMerCode = reqMerCode;
	}



	/**
	 * Return the value associated with the column: req_mer_term_id
	 */
	public java.lang.String getReqMerTermId () {
		return reqMerTermId;
	}

	/**
	 * Set the value related to the column: req_mer_term_id
	 * @param reqMerTermId the req_mer_term_id value
	 */
	public void setReqMerTermId (java.lang.String reqMerTermId) {
		this.reqMerTermId = reqMerTermId;
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
	 * Return the value associated with the column: deduct_sys_stance
	 */
	public java.lang.String getDeductSysStance () {
		return deductSysStance;
	}

	/**
	 * Set the value related to the column: deduct_sys_stance
	 * @param deductSysStance the deduct_sys_stance value
	 */
	public void setDeductSysStance (java.lang.String deductSysStance) {
		this.deductSysStance = deductSysStance;
	}



	/**
	 * Return the value associated with the column: deduct_mer_code
	 */
	public java.lang.String getDeductMerCode () {
		return deductMerCode;
	}

	/**
	 * Set the value related to the column: deduct_mer_code
	 * @param deductMerCode the deduct_mer_code value
	 */
	public void setDeductMerCode (java.lang.String deductMerCode) {
		this.deductMerCode = deductMerCode;
	}



	/**
	 * Return the value associated with the column: deduct_mer_term_id
	 */
	public java.lang.String getDeductMerTermId () {
		return deductMerTermId;
	}

	/**
	 * Set the value related to the column: deduct_mer_term_id
	 * @param deductMerTermId the deduct_mer_term_id value
	 */
	public void setDeductMerTermId (java.lang.String deductMerTermId) {
		this.deductMerTermId = deductMerTermId;
	}



	/**
	 * Return the value associated with the column: trade_time
	 */
	public java.lang.Long getTradeTime () {
		return tradeTime;
	}

	/**
	 * Set the value related to the column: trade_time
	 * @param tradeTime the trade_time value
	 */
	public void setTradeTime (java.lang.Long tradeTime) {
		this.tradeTime = tradeTime;
	}



	/**
	 * Return the value associated with the column: deduct_sys_time
	 */
	public java.lang.Long getDeductSysTime () {
		return deductSysTime;
	}

	/**
	 * Set the value related to the column: deduct_sys_time
	 * @param deductSysTime the deduct_sys_time value
	 */
	public void setDeductSysTime (java.lang.Long deductSysTime) {
		this.deductSysTime = deductSysTime;
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
	 * Return the value associated with the column: deduct_roll_bk
	 */
	public java.lang.Byte getDeductRollBk () {
		return deductRollBk;
	}

	/**
	 * Set the value related to the column: deduct_roll_bk
	 * @param deductRollBk the deduct_roll_bk value
	 */
	public void setDeductRollBk (java.lang.Byte deductRollBk) {
		this.deductRollBk = deductRollBk;
	}



	/**
	 * Return the value associated with the column: trade_type
	 */
	public java.lang.Integer getTradeType () {
		return tradeType;
	}

	/**
	 * Set the value related to the column: trade_type
	 * @param tradeType the trade_type value
	 */
	public void setTradeType (java.lang.Integer tradeType) {
		this.tradeType = tradeType;
	}



	/**
	 * Return the value associated with the column: authorization_code
	 */
	public java.lang.String getAuthorizationCode () {
		return authorizationCode;
	}

	/**
	 * Set the value related to the column: authorization_code
	 * @param authorizationCode the authorization_code value
	 */
	public void setAuthorizationCode (java.lang.String authorizationCode) {
		this.authorizationCode = authorizationCode;
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
	 * Return the value associated with the column: mer_fee
	 */
	public java.lang.Double getMerFee () {
		return merFee;
	}

	/**
	 * Set the value related to the column: mer_fee
	 * @param merFee the mer_fee value
	 */
	public void setMerFee (java.lang.Double merFee) {
		this.merFee = merFee;
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
	 * Return the value associated with the column: zf_fee
	 */
	public java.lang.Double getZfFee () {
		return zfFee;
	}

	/**
	 * Set the value related to the column: zf_fee
	 * @param zfFee the zf_fee value
	 */
	public void setZfFee (java.lang.Double zfFee) {
		this.zfFee = zfFee;
	}



	/**
	 * Return the value associated with the column: zf_file_fee
	 */
	public java.lang.String getZfFileFee () {
		return zfFileFee;
	}

	/**
	 * Set the value related to the column: zf_file_fee
	 * @param zfFileFee the zf_file_fee value
	 */
	public void setZfFileFee (java.lang.String zfFileFee) {
		this.zfFileFee = zfFileFee;
	}



	/**
	 * Return the value associated with the column: zf_fee_bj
	 */
	public java.lang.Integer getZfFeeBj () {
		return zfFeeBj;
	}

	/**
	 * Set the value related to the column: zf_fee_bj
	 * @param zfFeeBj the zf_fee_bj value
	 */
	public void setZfFeeBj (java.lang.Integer zfFeeBj) {
		this.zfFeeBj = zfFeeBj;
	}



	/**
	 * Return the value associated with the column: fee_formula
	 */
	public java.lang.String getFeeFormula () {
		return feeFormula;
	}

	/**
	 * Set the value related to the column: fee_formula
	 * @param feeFormula the fee_formula value
	 */
	public void setFeeFormula (java.lang.String feeFormula) {
		this.feeFormula = feeFormula;
	}



	/**
	 * Return the value associated with the column: original_trans_info
	 */
	public java.lang.String getOriginalTransInfo () {
		return originalTransInfo;
	}

	/**
	 * Set the value related to the column: original_trans_info
	 * @param originalTransInfo the original_trans_info value
	 */
	public void setOriginalTransInfo (java.lang.String originalTransInfo) {
		this.originalTransInfo = originalTransInfo;
	}



	/**
	 * Return the value associated with the column: instType
	 */
	public java.lang.Integer getInstType () {
		return instType;
	}

	/**
	 * Set the value related to the column: instType
	 * @param instType the instType value
	 */
	public void setInstType (java.lang.Integer instType) {
		this.instType = instType;
	}



	/**
	 * Return the value associated with the column: gate
	 */
	public java.lang.Integer getGate () {
		return gate;
	}

	/**
	 * Set the value related to the column: gate
	 * @param gate the gate value
	 */
	public void setGate (java.lang.Integer gate) {
		this.gate = gate;
	}



	/**
	 * Return the value associated with the column: settle_code
	 */
	public java.lang.String getSettleCode () {
		return settleCode;
	}

	/**
	 * Set the value related to the column: settle_code
	 * @param settleCode the settle_code value
	 */
	public void setSettleCode (java.lang.String settleCode) {
		this.settleCode = settleCode;
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
	 * Return the value associated with the column: dy_mer_name
	 */
	public java.lang.String getDyMerName () {
		return dyMerName;
	}

	/**
	 * Set the value related to the column: dy_mer_name
	 * @param dyMerName the dy_mer_name value
	 */
	public void setDyMerName (java.lang.String dyMerName) {
		this.dyMerName = dyMerName;
	}



	/**
	 * Return the value associated with the column: oid
	 */
	public java.lang.String getOid () {
		return oid;
	}

	/**
	 * Set the value related to the column: oid
	 * @param oid the oid value
	 */
	public void setOid (java.lang.String oid) {
		this.oid = oid;
	}



	/**
	 * Return the value associated with the column: additional_response_data
	 */
	public java.lang.String getAdditionalResponseData () {
		return additionalResponseData;
	}

	/**
	 * Set the value related to the column: additional_response_data
	 * @param additionalResponseData the additional_response_data value
	 */
	public void setAdditionalResponseData (java.lang.String additionalResponseData) {
		this.additionalResponseData = additionalResponseData;
	}



	/**
	 * Return the value associated with the column: sys_date
	 */
	public java.lang.Integer getSysDate () {
		return sysDate;
	}

	/**
	 * Set the value related to the column: sys_date
	 * @param sysDate the sys_date value
	 */
	public void setSysDate (java.lang.Integer sysDate) {
		this.sysDate = sysDate;
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
	 * Return the value associated with the column: out_user_id
	 */
	public java.lang.String getOutUserId () {
		return outUserId;
	}

	/**
	 * Set the value related to the column: out_user_id
	 * @param outUserId the out_user_id value
	 */
	public void setOutUserId (java.lang.String outUserId) {
		this.outUserId = outUserId;
	}



	/**
	 * Return the value associated with the column: in_user_id
	 */
	public java.lang.String getInUserId () {
		return inUserId;
	}

	/**
	 * Set the value related to the column: in_user_id
	 * @param inUserId the in_user_id value
	 */
	public void setInUserId (java.lang.String inUserId) {
		this.inUserId = inUserId;
	}



	/**
	 * Return the value associated with the column: bind_mid
	 */
	public java.lang.String getBindMid () {
		return bindMid;
	}

	/**
	 * Set the value related to the column: bind_mid
	 * @param bindMid the bind_mid value
	 */
	public void setBindMid (java.lang.String bindMid) {
		this.bindMid = bindMid;
	}



	/**
	 * Return the value associated with the column: remark
	 */
	public java.lang.String getRemark () {
		return remark;
	}

	/**
	 * Set the value related to the column: remark
	 * @param remark the remark value
	 */
	public void setRemark (java.lang.String remark) {
		this.remark = remark;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.ChannelTradeCollect)) return false;
		else {
			cn.com.chinaebi.dz.object.ChannelTradeCollect channelTradeCollect = (cn.com.chinaebi.dz.object.ChannelTradeCollect) obj;
			if (null == this.getId() || null == channelTradeCollect.getId()) return false;
			else return (this.getId().equals(channelTradeCollect.getId()));
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