package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the riqie_cups_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="riqie_cups_lst"
 */

public abstract class BaseRiqieCupsLst  implements Serializable {

	public static String REF = "RiqieCupsLst";
	public static String PROP_ORIGINAL_TRANS_INFO = "OriginalTransInfo";
	public static String PROP_NII = "Nii";
	public static String PROP_ZF_FEE_BJ = "ZfFeeBj";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_GAIN_SYS_REFERENCE = "GainSysReference";
	public static String PROP_REQ_MER_TERM_ID = "ReqMerTermId";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_WHETHER_RIQIE = "WhetherRiqie";
	public static String PROP_DEDUCT_ROLLBK_SYS_REFERENCE = "DeductRollbkSysReference";
	public static String PROP_TRADE_DESC = "TradeDesc";
	public static String PROP_DEDUCT_SYS_STANCE = "DeductSysStance";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_IC_READ_AND_CONDITION = "IcReadAndCondition";
	public static String PROP_IN_BANK_ID = "InBankId";
	public static String PROP_MSG_NUM = "MsgNum";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_ZF_FILE_FEE = "zfFileFee";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_IN_ACCOUNT_TYPE = "InAccountType";
	public static String PROP_IC_CARD_SER_NO = "IcCardSerNo";
	public static String PROP_ADDITIONAL_RESPONSE_DATA = "AdditionalResponseData";
	public static String PROP_GAIN_SYS_STANCE = "GainSysStance";
	public static String PROP_DEDUCT_MER_TERM_ID = "DeductMerTermId";
	public static String PROP_DEDUCT_ROLL_BK_STANCE = "DeductRollBkStance";
	public static String PROP_REQ_INPUT_TYPE = "ReqInputType";
	public static String PROP_CUSTOM_DEFINE_INFO = "CustomDefineInfo";
	public static String PROP_OUT_ACCOUNT_DESC = "OutAccountDesc";
	public static String PROP_TRADE_RESULT = "TradeResult";
	public static String PROP_OUT_ACCOUNT_TYPE = "OutAccountType";
	public static String PROP_REQ_PROCESS = "ReqProcess";
	public static String PROP_IN_CARD_NAME = "InCardName";
	public static String PROP_DEDUCT_SYS_RESPONSE = "DeductSysResponse";
	public static String PROP_TRADE_REQ_METHOD = "TradeReqMethod";
	public static String PROP_REQ_TYPE = "ReqType";
	public static String PROP_TRADE_TYPE = "TradeType";
	public static String PROP_TRADE_OTHER_INFO = "TradeOtherInfo";
	public static String PROP_DEDUCT_SYS_TIME = "DeductSysTime";
	public static String PROP_WHTHER_INNER_DZ = "WhtherInnerDz";
	public static String PROP_PASS_WD_MODE = "PassWdMode";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_DEDUCT_ROLL_BK_REASON = "DeductRollBkReason";
	public static String PROP_BANK_ID = "bankId";
	public static String PROP_WHETHER_VALID = "WhetherValid";
	public static String PROP_WHETHER_JS = "WhetherJs";
	public static String PROP_WHETHER_QS = "WhetherQs";
	public static String PROP_ID = "Id";
	public static String PROP_GAIN_SYS_RESPONSE = "GainSysResponse";
	public static String PROP_GAIN_RESULT = "GainResult";
	public static String PROP_FEE_FORMULA = "FeeFormula";
	public static String PROP_IN_ACCOUNT = "InAccount";
	public static String PROP_DEDUCT_ROLL_BK_RESPONSE = "DeductRollBkResponse";
	public static String PROP_TERMINAL_INFO = "TerminalInfo";
	public static String PROP_DEDUCT_SYS_ID = "DeductSysId";
	public static String PROP_AGENT_ID = "AgentId";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_REQ_SYS_ID = "ReqSysId";
	public static String PROP_REQ_MER_CODE = "ReqMerCode";
	public static String PROP_ZF_FEE = "ZfFee";
	public static String PROP_TERMINAL_ID = "TerminalId";
	public static String PROP_FWD_INST_ID_CODE = "FwdInstIdCode";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_BOC = "Boc";
	public static String PROP_OUT_ACCOUNT_INFO = "OutAccountInfo";
	public static String PROP_WHETHERZERO = "Whetherzero";
	public static String PROP_TRADEMSG_TYPE = "TrademsgType";
	public static String PROP_TRADE_CURRENCY = "TradeCurrency";
	public static String PROP_GAIN_TRADE_AMOUNT = "GainTradeAmount";
	public static String PROP_TRADE_TIME = "TradeTime";
	public static String PROP_GAIN_SYS_ID = "GainSysId";
	public static String PROP_WHETHER_ACCESS_STANCE = "WhetherAccessStance";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_OUT_ACC_VALID_TIME = "OutAccValidTime";
	public static String PROP_ACQ_INST_ID_CODE = "AcqInstIdCode";
	public static String PROP_DEDUCT_ROLLBK_SYS_TIME = "DeductRollbkSysTime";
	public static String PROP_REQ_RESPONSE = "ReqResponse";
	public static String PROP_GAIN_MER_CODE = "GainMerCode";
	public static String PROP_DEDUCT_RESULT = "DeductResult";
	public static String PROP_MER_FEE = "MerFee";
	public static String PROP_GAIN_MER_TERM_ID = "GainMerTermId";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_AUTHORIZATION_CODE = "AuthorizationCode";
	public static String PROP_ADDITIONAL_DATA = "AdditionalData";
	public static String PROP_TERMINAL_TYPE = "TerminalType";
	public static String PROP_DEDUCT_ROLL_BK_RESULT = "DeductRollBkResult";
	public static String PROP_DEDUCT_ROLL_BK = "DeductRollBk";
	public static String PROP_DEDUCT_MER_CODE = "DeductMerCode";
	public static String PROP_WHETHER_ERROE_HANDLE = "WhetherErroeHandle";
	public static String PROP_OUT_ACCOUNT_INFO2 = "OutAccountInfo2";
	public static String PROP_WHTHER_INNER_JS = "WhtherInnerJs";


	// constructors
	public BaseRiqieCupsLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRiqieCupsLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseRiqieCupsLst (
		java.lang.String id,
		java.util.Date tradeTime,
		java.util.Date outAccValidTime,
		java.lang.Integer tradeType,
		java.util.Date reqTime,
		java.util.Date deductStlmDate,
		java.util.Date deductSysTime,
		java.lang.Integer bkChk,
		boolean whetherJs,
		boolean whetherValid,
		java.lang.Integer whetherErroeHandle,
		boolean whetherRiqie,
		java.util.Date deductRollbkSysTime,
		boolean whtherInnerJs,
		java.lang.Integer bankId) {

		this.setId(id);
		this.setTradeTime(tradeTime);
		this.setOutAccValidTime(outAccValidTime);
		this.setTradeType(tradeType);
		this.setReqTime(reqTime);
		this.setDeductStlmDate(deductStlmDate);
		this.setDeductSysTime(deductSysTime);
		this.setBkChk(bkChk);
		this.setWhetherJs(whetherJs);
		this.setWhetherValid(whetherValid);
		this.setWhetherErroeHandle(whetherErroeHandle);
		this.setWhetherRiqie(whetherRiqie);
		this.setDeductRollbkSysTime(deductRollbkSysTime);
		this.setWhtherInnerJs(whtherInnerJs);
		this.setBankId(bankId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String terminalId;
	private java.lang.String terminalInfo;
	private java.lang.Short terminalType;
	private java.util.Date tradeTime;
	private java.lang.String outAccount;
	private java.lang.String outAccountType;
	private java.util.Date outAccValidTime;
	private java.lang.String outAccountInfo;
	private java.lang.String outAccountInfo2;
	private java.lang.String outAccountDesc;
	private java.lang.String inAccount;
	private java.lang.String inCardName;
	private java.lang.String inBankId;
	private java.lang.Long tradeAmount;
	private java.lang.String tradeFee;
	private java.lang.Short tradeCurrency;
	private java.lang.Integer tradeResult;
	private java.lang.Integer reqSysId;
	private java.lang.String reqSysStance;
	private java.lang.String reqMerCode;
	private java.lang.String reqMerTermId;
	private java.lang.String reqResponse;
	private java.lang.Integer deductSysId;
	private java.lang.String deductSysStance;
	private java.lang.String deductMerCode;
	private java.lang.String deductMerTermId;
	private java.lang.Integer deductResult;
	private java.lang.String deductSysResponse;
	private boolean deductRollBk;
	private java.lang.Short deductRollBkResult;
	private java.lang.String deductRollBkReason;
	private java.lang.String deductRollBkResponse;
	private java.lang.String deductRollBkStance;
	private java.lang.Integer tradeType;
	private java.lang.Integer msgNum;
	private java.lang.String passWdMode;
	private java.lang.String reqType;
	private java.lang.String reqInputType;
	private java.util.Date reqTime;
	private java.lang.String tradeReqMethod;
	private java.lang.String tradeDesc;
	private java.lang.Integer inAccountType;
	private java.lang.String tradeOtherInfo;
	private java.util.Date deductStlmDate;
	private java.util.Date deductSysTime;
	private java.lang.Integer gainSysId;
	private java.lang.String gainSysStance;
	private java.lang.String gainMerCode;
	private java.lang.String gainMerTermId;
	private java.lang.String gainSysResponse;
	private java.lang.Integer gainResult;
	private java.lang.Long gainTradeAmount;
	private java.lang.String gainSysReference;
	private java.lang.String nii;
	private java.lang.String authorizationCode;
	private java.lang.String additionalResponseData;
	private java.lang.String additionalData;
	private java.lang.String boc;
	private java.lang.String customDefineInfo;
	private java.lang.String originalTransInfo;
	private java.lang.String reqProcess;
	private java.lang.String deductSysReference;
	private java.lang.Integer trademsgType;
	private java.lang.String deductRollbkSysReference;
	private java.lang.String merName;
	private java.lang.Integer bkChk;
	private boolean whetherJs;
	private boolean whetherValid;
	private java.lang.Integer whetherErroeHandle;
	private boolean whetherRiqie;
	private java.lang.String acqInstIdCode;
	private java.lang.String fwdInstIdCode;
	private java.util.Date deductRollbkSysTime;
	private java.lang.String agentId;
	private java.lang.String whetherzero;
	private boolean whtherInnerJs;
	private java.lang.String icCardSerNo;
	private java.lang.String icReadAndCondition;
	private boolean whetherQs;
	private double merFee;
	private boolean whetherTk;
	private double zfFee;
	private java.lang.String zfFileFee;
	private java.lang.Integer zfFeeBj;
	private java.lang.String feeFormula;
	private boolean whetherAccessStance;
	private boolean whtherInnerDz;
	private java.lang.Integer bankId;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
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
	 * Return the value associated with the column: terminal_id
	 */
	public java.lang.String getTerminalId () {
		return terminalId;
	}

	/**
	 * Set the value related to the column: terminal_id
	 * @param terminalId the terminal_id value
	 */
	public void setTerminalId (java.lang.String terminalId) {
		this.terminalId = terminalId;
	}



	/**
	 * Return the value associated with the column: terminal_info
	 */
	public java.lang.String getTerminalInfo () {
		return terminalInfo;
	}

	/**
	 * Set the value related to the column: terminal_info
	 * @param terminalInfo the terminal_info value
	 */
	public void setTerminalInfo (java.lang.String terminalInfo) {
		this.terminalInfo = terminalInfo;
	}



	/**
	 * Return the value associated with the column: terminal_type
	 */
	public java.lang.Short getTerminalType () {
		return terminalType;
	}

	/**
	 * Set the value related to the column: terminal_type
	 * @param terminalType the terminal_type value
	 */
	public void setTerminalType (java.lang.Short terminalType) {
		this.terminalType = terminalType;
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
	 * Return the value associated with the column: out_acc_valid_time
	 */
	public java.util.Date getOutAccValidTime () {
		return outAccValidTime;
	}

	/**
	 * Set the value related to the column: out_acc_valid_time
	 * @param outAccValidTime the out_acc_valid_time value
	 */
	public void setOutAccValidTime (java.util.Date outAccValidTime) {
		this.outAccValidTime = outAccValidTime;
	}



	/**
	 * Return the value associated with the column: out_account_info
	 */
	public java.lang.String getOutAccountInfo () {
		return outAccountInfo;
	}

	/**
	 * Set the value related to the column: out_account_info
	 * @param outAccountInfo the out_account_info value
	 */
	public void setOutAccountInfo (java.lang.String outAccountInfo) {
		this.outAccountInfo = outAccountInfo;
	}



	/**
	 * Return the value associated with the column: out_account_info2
	 */
	public java.lang.String getOutAccountInfo2 () {
		return outAccountInfo2;
	}

	/**
	 * Set the value related to the column: out_account_info2
	 * @param outAccountInfo2 the out_account_info2 value
	 */
	public void setOutAccountInfo2 (java.lang.String outAccountInfo2) {
		this.outAccountInfo2 = outAccountInfo2;
	}



	/**
	 * Return the value associated with the column: out_account_desc
	 */
	public java.lang.String getOutAccountDesc () {
		return outAccountDesc;
	}

	/**
	 * Set the value related to the column: out_account_desc
	 * @param outAccountDesc the out_account_desc value
	 */
	public void setOutAccountDesc (java.lang.String outAccountDesc) {
		this.outAccountDesc = outAccountDesc;
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
	 * Return the value associated with the column: in_bank_id
	 */
	public java.lang.String getInBankId () {
		return inBankId;
	}

	/**
	 * Set the value related to the column: in_bank_id
	 * @param inBankId the in_bank_id value
	 */
	public void setInBankId (java.lang.String inBankId) {
		this.inBankId = inBankId;
	}



	/**
	 * Return the value associated with the column: trade_amount
	 */
	public java.lang.Long getTradeAmount () {
		return tradeAmount;
	}

	/**
	 * Set the value related to the column: trade_amount
	 * @param tradeAmount the trade_amount value
	 */
	public void setTradeAmount (java.lang.Long tradeAmount) {
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
	public java.lang.Short getTradeCurrency () {
		return tradeCurrency;
	}

	/**
	 * Set the value related to the column: trade_currency
	 * @param tradeCurrency the trade_currency value
	 */
	public void setTradeCurrency (java.lang.Short tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
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
	 * Return the value associated with the column: req_response
	 */
	public java.lang.String getReqResponse () {
		return reqResponse;
	}

	/**
	 * Set the value related to the column: req_response
	 * @param reqResponse the req_response value
	 */
	public void setReqResponse (java.lang.String reqResponse) {
		this.reqResponse = reqResponse;
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
	 * Return the value associated with the column: deduct_result
	 */
	public java.lang.Integer getDeductResult () {
		return deductResult;
	}

	/**
	 * Set the value related to the column: deduct_result
	 * @param deductResult the deduct_result value
	 */
	public void setDeductResult (java.lang.Integer deductResult) {
		this.deductResult = deductResult;
	}



	/**
	 * Return the value associated with the column: deduct_sys_response
	 */
	public java.lang.String getDeductSysResponse () {
		return deductSysResponse;
	}

	/**
	 * Set the value related to the column: deduct_sys_response
	 * @param deductSysResponse the deduct_sys_response value
	 */
	public void setDeductSysResponse (java.lang.String deductSysResponse) {
		this.deductSysResponse = deductSysResponse;
	}



	/**
	 * Return the value associated with the column: deduct_roll_bk
	 */
	public boolean isDeductRollBk () {
		return deductRollBk;
	}

	/**
	 * Set the value related to the column: deduct_roll_bk
	 * @param deductRollBk the deduct_roll_bk value
	 */
	public void setDeductRollBk (boolean deductRollBk) {
		this.deductRollBk = deductRollBk;
	}



	/**
	 * Return the value associated with the column: deduct_roll_bk_result
	 */
	public java.lang.Short getDeductRollBkResult () {
		return deductRollBkResult;
	}

	/**
	 * Set the value related to the column: deduct_roll_bk_result
	 * @param deductRollBkResult the deduct_roll_bk_result value
	 */
	public void setDeductRollBkResult (java.lang.Short deductRollBkResult) {
		this.deductRollBkResult = deductRollBkResult;
	}



	/**
	 * Return the value associated with the column: deduct_roll_bk_reason
	 */
	public java.lang.String getDeductRollBkReason () {
		return deductRollBkReason;
	}

	/**
	 * Set the value related to the column: deduct_roll_bk_reason
	 * @param deductRollBkReason the deduct_roll_bk_reason value
	 */
	public void setDeductRollBkReason (java.lang.String deductRollBkReason) {
		this.deductRollBkReason = deductRollBkReason;
	}



	/**
	 * Return the value associated with the column: deduct_roll_bk_response
	 */
	public java.lang.String getDeductRollBkResponse () {
		return deductRollBkResponse;
	}

	/**
	 * Set the value related to the column: deduct_roll_bk_response
	 * @param deductRollBkResponse the deduct_roll_bk_response value
	 */
	public void setDeductRollBkResponse (java.lang.String deductRollBkResponse) {
		this.deductRollBkResponse = deductRollBkResponse;
	}



	/**
	 * Return the value associated with the column: deduct_roll_bk_stance
	 */
	public java.lang.String getDeductRollBkStance () {
		return deductRollBkStance;
	}

	/**
	 * Set the value related to the column: deduct_roll_bk_stance
	 * @param deductRollBkStance the deduct_roll_bk_stance value
	 */
	public void setDeductRollBkStance (java.lang.String deductRollBkStance) {
		this.deductRollBkStance = deductRollBkStance;
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
	 * Return the value associated with the column: msg_num
	 */
	public java.lang.Integer getMsgNum () {
		return msgNum;
	}

	/**
	 * Set the value related to the column: msg_num
	 * @param msgNum the msg_num value
	 */
	public void setMsgNum (java.lang.Integer msgNum) {
		this.msgNum = msgNum;
	}



	/**
	 * Return the value associated with the column: pass_wd_mode
	 */
	public java.lang.String getPassWdMode () {
		return passWdMode;
	}

	/**
	 * Set the value related to the column: pass_wd_mode
	 * @param passWdMode the pass_wd_mode value
	 */
	public void setPassWdMode (java.lang.String passWdMode) {
		this.passWdMode = passWdMode;
	}



	/**
	 * Return the value associated with the column: req_type
	 */
	public java.lang.String getReqType () {
		return reqType;
	}

	/**
	 * Set the value related to the column: req_type
	 * @param reqType the req_type value
	 */
	public void setReqType (java.lang.String reqType) {
		this.reqType = reqType;
	}



	/**
	 * Return the value associated with the column: req_input_type
	 */
	public java.lang.String getReqInputType () {
		return reqInputType;
	}

	/**
	 * Set the value related to the column: req_input_type
	 * @param reqInputType the req_input_type value
	 */
	public void setReqInputType (java.lang.String reqInputType) {
		this.reqInputType = reqInputType;
	}



	/**
	 * Return the value associated with the column: req_time
	 */
	public java.util.Date getReqTime () {
		return reqTime;
	}

	/**
	 * Set the value related to the column: req_time
	 * @param reqTime the req_time value
	 */
	public void setReqTime (java.util.Date reqTime) {
		this.reqTime = reqTime;
	}



	/**
	 * Return the value associated with the column: trade_req_method
	 */
	public java.lang.String getTradeReqMethod () {
		return tradeReqMethod;
	}

	/**
	 * Set the value related to the column: trade_req_method
	 * @param tradeReqMethod the trade_req_method value
	 */
	public void setTradeReqMethod (java.lang.String tradeReqMethod) {
		this.tradeReqMethod = tradeReqMethod;
	}



	/**
	 * Return the value associated with the column: trade_desc
	 */
	public java.lang.String getTradeDesc () {
		return tradeDesc;
	}

	/**
	 * Set the value related to the column: trade_desc
	 * @param tradeDesc the trade_desc value
	 */
	public void setTradeDesc (java.lang.String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}



	/**
	 * Return the value associated with the column: in_account_type
	 */
	public java.lang.Integer getInAccountType () {
		return inAccountType;
	}

	/**
	 * Set the value related to the column: in_account_type
	 * @param inAccountType the in_account_type value
	 */
	public void setInAccountType (java.lang.Integer inAccountType) {
		this.inAccountType = inAccountType;
	}



	/**
	 * Return the value associated with the column: trade_other_info
	 */
	public java.lang.String getTradeOtherInfo () {
		return tradeOtherInfo;
	}

	/**
	 * Set the value related to the column: trade_other_info
	 * @param tradeOtherInfo the trade_other_info value
	 */
	public void setTradeOtherInfo (java.lang.String tradeOtherInfo) {
		this.tradeOtherInfo = tradeOtherInfo;
	}



	/**
	 * Return the value associated with the column: deduct_stlm_date
	 */
	public java.util.Date getDeductStlmDate () {
		return deductStlmDate;
	}

	/**
	 * Set the value related to the column: deduct_stlm_date
	 * @param deductStlmDate the deduct_stlm_date value
	 */
	public void setDeductStlmDate (java.util.Date deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
	}



	/**
	 * Return the value associated with the column: deduct_sys_time
	 */
	public java.util.Date getDeductSysTime () {
		return deductSysTime;
	}

	/**
	 * Set the value related to the column: deduct_sys_time
	 * @param deductSysTime the deduct_sys_time value
	 */
	public void setDeductSysTime (java.util.Date deductSysTime) {
		this.deductSysTime = deductSysTime;
	}



	/**
	 * Return the value associated with the column: gain_sys_id
	 */
	public java.lang.Integer getGainSysId () {
		return gainSysId;
	}

	/**
	 * Set the value related to the column: gain_sys_id
	 * @param gainSysId the gain_sys_id value
	 */
	public void setGainSysId (java.lang.Integer gainSysId) {
		this.gainSysId = gainSysId;
	}



	/**
	 * Return the value associated with the column: gain_sys_stance
	 */
	public java.lang.String getGainSysStance () {
		return gainSysStance;
	}

	/**
	 * Set the value related to the column: gain_sys_stance
	 * @param gainSysStance the gain_sys_stance value
	 */
	public void setGainSysStance (java.lang.String gainSysStance) {
		this.gainSysStance = gainSysStance;
	}



	/**
	 * Return the value associated with the column: gain_mer_code
	 */
	public java.lang.String getGainMerCode () {
		return gainMerCode;
	}

	/**
	 * Set the value related to the column: gain_mer_code
	 * @param gainMerCode the gain_mer_code value
	 */
	public void setGainMerCode (java.lang.String gainMerCode) {
		this.gainMerCode = gainMerCode;
	}



	/**
	 * Return the value associated with the column: gain_mer_term_id
	 */
	public java.lang.String getGainMerTermId () {
		return gainMerTermId;
	}

	/**
	 * Set the value related to the column: gain_mer_term_id
	 * @param gainMerTermId the gain_mer_term_id value
	 */
	public void setGainMerTermId (java.lang.String gainMerTermId) {
		this.gainMerTermId = gainMerTermId;
	}



	/**
	 * Return the value associated with the column: gain_sys_response
	 */
	public java.lang.String getGainSysResponse () {
		return gainSysResponse;
	}

	/**
	 * Set the value related to the column: gain_sys_response
	 * @param gainSysResponse the gain_sys_response value
	 */
	public void setGainSysResponse (java.lang.String gainSysResponse) {
		this.gainSysResponse = gainSysResponse;
	}



	/**
	 * Return the value associated with the column: gain_result
	 */
	public java.lang.Integer getGainResult () {
		return gainResult;
	}

	/**
	 * Set the value related to the column: gain_result
	 * @param gainResult the gain_result value
	 */
	public void setGainResult (java.lang.Integer gainResult) {
		this.gainResult = gainResult;
	}



	/**
	 * Return the value associated with the column: gain_trade_amount
	 */
	public java.lang.Long getGainTradeAmount () {
		return gainTradeAmount;
	}

	/**
	 * Set the value related to the column: gain_trade_amount
	 * @param gainTradeAmount the gain_trade_amount value
	 */
	public void setGainTradeAmount (java.lang.Long gainTradeAmount) {
		this.gainTradeAmount = gainTradeAmount;
	}



	/**
	 * Return the value associated with the column: gain_sys_reference
	 */
	public java.lang.String getGainSysReference () {
		return gainSysReference;
	}

	/**
	 * Set the value related to the column: gain_sys_reference
	 * @param gainSysReference the gain_sys_reference value
	 */
	public void setGainSysReference (java.lang.String gainSysReference) {
		this.gainSysReference = gainSysReference;
	}



	/**
	 * Return the value associated with the column: nii
	 */
	public java.lang.String getNii () {
		return nii;
	}

	/**
	 * Set the value related to the column: nii
	 * @param nii the nii value
	 */
	public void setNii (java.lang.String nii) {
		this.nii = nii;
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
	 * Return the value associated with the column: additional_data
	 */
	public java.lang.String getAdditionalData () {
		return additionalData;
	}

	/**
	 * Set the value related to the column: additional_data
	 * @param additionalData the additional_data value
	 */
	public void setAdditionalData (java.lang.String additionalData) {
		this.additionalData = additionalData;
	}



	/**
	 * Return the value associated with the column: boc
	 */
	public java.lang.String getBoc () {
		return boc;
	}

	/**
	 * Set the value related to the column: boc
	 * @param boc the boc value
	 */
	public void setBoc (java.lang.String boc) {
		this.boc = boc;
	}



	/**
	 * Return the value associated with the column: custom_define_info
	 */
	public java.lang.String getCustomDefineInfo () {
		return customDefineInfo;
	}

	/**
	 * Set the value related to the column: custom_define_info
	 * @param customDefineInfo the custom_define_info value
	 */
	public void setCustomDefineInfo (java.lang.String customDefineInfo) {
		this.customDefineInfo = customDefineInfo;
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
	 * Return the value associated with the column: req_process
	 */
	public java.lang.String getReqProcess () {
		return reqProcess;
	}

	/**
	 * Set the value related to the column: req_process
	 * @param reqProcess the req_process value
	 */
	public void setReqProcess (java.lang.String reqProcess) {
		this.reqProcess = reqProcess;
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
	 * Return the value associated with the column: trademsg_type
	 */
	public java.lang.Integer getTrademsgType () {
		return trademsgType;
	}

	/**
	 * Set the value related to the column: trademsg_type
	 * @param trademsgType the trademsg_type value
	 */
	public void setTrademsgType (java.lang.Integer trademsgType) {
		this.trademsgType = trademsgType;
	}



	/**
	 * Return the value associated with the column: deduct_rollbk_sys_reference
	 */
	public java.lang.String getDeductRollbkSysReference () {
		return deductRollbkSysReference;
	}

	/**
	 * Set the value related to the column: deduct_rollbk_sys_reference
	 * @param deductRollbkSysReference the deduct_rollbk_sys_reference value
	 */
	public void setDeductRollbkSysReference (java.lang.String deductRollbkSysReference) {
		this.deductRollbkSysReference = deductRollbkSysReference;
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
	 * Return the value associated with the column: whetherValid
	 */
	public boolean isWhetherValid () {
		return whetherValid;
	}

	/**
	 * Set the value related to the column: whetherValid
	 * @param whetherValid the whetherValid value
	 */
	public void setWhetherValid (boolean whetherValid) {
		this.whetherValid = whetherValid;
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
	 * Return the value associated with the column: whetherRiqie
	 */
	public boolean isWhetherRiqie () {
		return whetherRiqie;
	}

	/**
	 * Set the value related to the column: whetherRiqie
	 * @param whetherRiqie the whetherRiqie value
	 */
	public void setWhetherRiqie (boolean whetherRiqie) {
		this.whetherRiqie = whetherRiqie;
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
	 * Return the value associated with the column: fwdInstIdCode
	 */
	public java.lang.String getFwdInstIdCode () {
		return fwdInstIdCode;
	}

	/**
	 * Set the value related to the column: fwdInstIdCode
	 * @param fwdInstIdCode the fwdInstIdCode value
	 */
	public void setFwdInstIdCode (java.lang.String fwdInstIdCode) {
		this.fwdInstIdCode = fwdInstIdCode;
	}



	/**
	 * Return the value associated with the column: deduct_rollbk_sys_time
	 */
	public java.util.Date getDeductRollbkSysTime () {
		return deductRollbkSysTime;
	}

	/**
	 * Set the value related to the column: deduct_rollbk_sys_time
	 * @param deductRollbkSysTime the deduct_rollbk_sys_time value
	 */
	public void setDeductRollbkSysTime (java.util.Date deductRollbkSysTime) {
		this.deductRollbkSysTime = deductRollbkSysTime;
	}



	/**
	 * Return the value associated with the column: agentId
	 */
	public java.lang.String getAgentId () {
		return agentId;
	}

	/**
	 * Set the value related to the column: agentId
	 * @param agentId the agentId value
	 */
	public void setAgentId (java.lang.String agentId) {
		this.agentId = agentId;
	}



	/**
	 * Return the value associated with the column: whetherzero
	 */
	public java.lang.String getWhetherzero () {
		return whetherzero;
	}

	/**
	 * Set the value related to the column: whetherzero
	 * @param whetherzero the whetherzero value
	 */
	public void setWhetherzero (java.lang.String whetherzero) {
		this.whetherzero = whetherzero;
	}



	/**
	 * Return the value associated with the column: whtherInnerJs
	 */
	public boolean isWhtherInnerJs () {
		return whtherInnerJs;
	}

	/**
	 * Set the value related to the column: whtherInnerJs
	 * @param whtherInnerJs the whtherInnerJs value
	 */
	public void setWhtherInnerJs (boolean whtherInnerJs) {
		this.whtherInnerJs = whtherInnerJs;
	}



	/**
	 * Return the value associated with the column: ic_card_ser_no
	 */
	public java.lang.String getIcCardSerNo () {
		return icCardSerNo;
	}

	/**
	 * Set the value related to the column: ic_card_ser_no
	 * @param icCardSerNo the ic_card_ser_no value
	 */
	public void setIcCardSerNo (java.lang.String icCardSerNo) {
		this.icCardSerNo = icCardSerNo;
	}



	/**
	 * Return the value associated with the column: ic_read_and_condition
	 */
	public java.lang.String getIcReadAndCondition () {
		return icReadAndCondition;
	}

	/**
	 * Set the value related to the column: ic_read_and_condition
	 * @param icReadAndCondition the ic_read_and_condition value
	 */
	public void setIcReadAndCondition (java.lang.String icReadAndCondition) {
		this.icReadAndCondition = icReadAndCondition;
	}



	/**
	 * Return the value associated with the column: whetherQs
	 */
	public boolean isWhetherQs () {
		return whetherQs;
	}

	/**
	 * Set the value related to the column: whetherQs
	 * @param whetherQs the whetherQs value
	 */
	public void setWhetherQs (boolean whetherQs) {
		this.whetherQs = whetherQs;
	}



	/**
	 * Return the value associated with the column: mer_fee
	 */
	public double getMerFee () {
		return merFee;
	}

	/**
	 * Set the value related to the column: mer_fee
	 * @param merFee the mer_fee value
	 */
	public void setMerFee (double merFee) {
		this.merFee = merFee;
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
	 * Return the value associated with the column: zf_fee
	 */
	public double getZfFee () {
		return zfFee;
	}

	/**
	 * Set the value related to the column: zf_fee
	 * @param zfFee the zf_fee value
	 */
	public void setZfFee (double zfFee) {
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
	 * Return the value associated with the column: whetherAccessStance
	 */
	public boolean isWhetherAccessStance () {
		return whetherAccessStance;
	}

	/**
	 * Set the value related to the column: whetherAccessStance
	 * @param whetherAccessStance the whetherAccessStance value
	 */
	public void setWhetherAccessStance (boolean whetherAccessStance) {
		this.whetherAccessStance = whetherAccessStance;
	}



	/**
	 * Return the value associated with the column: whtherInnerDz
	 */
	public boolean isWhtherInnerDz () {
		return whtherInnerDz;
	}

	/**
	 * Set the value related to the column: whtherInnerDz
	 * @param whtherInnerDz the whtherInnerDz value
	 */
	public void setWhtherInnerDz (boolean whtherInnerDz) {
		this.whtherInnerDz = whtherInnerDz;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.RiqieCupsLst)) return false;
		else {
			cn.com.chinaebi.dz.object.RiqieCupsLst riqieCupsLst = (cn.com.chinaebi.dz.object.RiqieCupsLst) obj;
			if (null == this.getId() || null == riqieCupsLst.getId()) return false;
			else return (this.getId().equals(riqieCupsLst.getId()));
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