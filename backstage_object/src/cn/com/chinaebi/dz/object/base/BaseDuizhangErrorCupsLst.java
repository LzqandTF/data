package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_error_cups_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_error_cups_lst"
 */

public abstract class BaseDuizhangErrorCupsLst  implements Serializable {

	public static String REF = "DuizhangErrorCupsLst";
	public static String PROP_ISSUING_BANK_CODE = "IssuingBankCode";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_SPECIAL_CHARGES_GRADE = "SpecialChargesGrade";
	public static String PROP_MSG_TYPE = "MsgType";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_ERROR_INFO = "ErrorInfo";
	public static String PROP_MERCHANT_CODE = "MerchantCode";
	public static String PROP_ECI = "Eci";
	public static String PROP_ID_CONDITION_CODE = "IdConditionCode";
	public static String PROP_TAC_LOGO = "TacLogo";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_TRADE_ACCOUNT = "TradeAccount";
	public static String PROP_REQ_INPUT_TYPE = "ReqInputType";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_ACCEPTOR_PAY_FEE = "AcceptorPayFee";
	public static String PROP_DEDUCT_SYS_RESPONSE = "DeductSysResponse";
	public static String PROP_REQ_TYPE = "ReqType";
	public static String PROP_ON_TRADE_TIME = "OnTradeTime";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_RCVG_INST_ID_CODE = "RcvgInstIdCode";
	public static String PROP_ID = "Id";
	public static String PROP_ON_TRADE_ACCOUNT = "OnTradeAccount";
	public static String PROP_MERCHANT_NAME_ADDRESS = "MerchantNameAddress";
	public static String PROP_SENDER_CLEARING_ORGANIZATIONS = "SenderClearingOrganizations";
	public static String PROP_TRAN_INITIATED_METHOD = "TranInitiatedMethod";
	public static String PROP_BEFORE_TRANSATION_TER_TYPE = "BeforeTransationTerType";
	public static String PROP_CARD_PRODUCT_INFO = "CardProductInfo";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_ERROR_TRADE_RECEIVE_FEE = "ErrorTradeReceiveFee";
	public static String PROP_FWD_INST_ID_CODE = "FwdInstIdCode";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_INTO_INST_CODE = "IntoInstCode";
	public static String PROP_RECEIVE_ROLL_OUT_CODE = "ReceiveRollOutCode";
	public static String PROP_TRADE_ADRESS = "TradeAdress";
	public static String PROP_SPECIAL_BILLING_TYPE = "SpecialBillingType";
	public static String PROP_CARD_NUMBER = "CardNumber";
	public static String PROP_RECIPIENT_CLEARING_ORGANIZATION = "RecipientClearingOrganization";
	public static String PROP_ACCOUNT_SETTLE_TYPE = "AccountSettleType";
	public static String PROP_TRAN_CODE_CAUSED_ERROR = "TranCodeCausedError";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_ACCEPTOR_RECEIVE_FEE = "AcceptorReceiveFee";
	public static String PROP_ACQ_INST_ID_CODE = "AcqInstIdCode";
	public static String PROP_TERM_READ_ABILITY = "TermReadAbility";
	public static String PROP_ERROR_TRADE_PAY_FEE = "ErrorTradePayFee";
	public static String PROP_MER_TYPE = "MerType";
	public static String PROP_ACCOUNT_IDENTIFICATION = "AccountIdentification";
	public static String PROP_TRANSFEREE_CLEARING_ORGANIZATIONS = "TransfereeClearingOrganizations";
	public static String PROP_ONDEDUCT_STLM_DATE = "OndeductStlmDate";
	public static String PROP_RESERVED_FOR_USE = "ReservedForUse";
	public static String PROP_AUTHORIZATION_CODE = "AuthorizationCode";
	public static String PROP_BY_STAGES_FEE = "ByStagesFee";
	public static String PROP_WHETHER_ERROE_HANDLE = "WhetherErroeHandle";
	public static String PROP_ORIG_DATA_STANCE = "OrigDataStance";
	public static String PROP_ERROR_TRADE_FLAG = "ErrorTradeFlag";


	// constructors
	public BaseDuizhangErrorCupsLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangErrorCupsLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDuizhangErrorCupsLst (
		java.lang.String id,
		java.lang.String errorTradeFlag,
		java.lang.String acqInstIdCode,
		java.lang.String fwdInstIdCode,
		java.lang.String reqSysStance,
		java.lang.String reqTime,
		java.lang.String outAccount,
		java.lang.String tradeAccount,
		java.lang.String msgType,
		java.lang.String process,
		java.lang.String merType,
		java.lang.String termId,
		java.lang.String reqType,
		java.lang.String origDataStance,
		java.lang.String deductSysResponse,
		java.lang.String reqInputType,
		java.lang.Integer whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk) {

		this.setId(id);
		this.setErrorTradeFlag(errorTradeFlag);
		this.setAcqInstIdCode(acqInstIdCode);
		this.setFwdInstIdCode(fwdInstIdCode);
		this.setReqSysStance(reqSysStance);
		this.setReqTime(reqTime);
		this.setOutAccount(outAccount);
		this.setTradeAccount(tradeAccount);
		this.setMsgType(msgType);
		this.setProcess(process);
		this.setMerType(merType);
		this.setTermId(termId);
		this.setReqType(reqType);
		this.setOrigDataStance(origDataStance);
		this.setDeductSysResponse(deductSysResponse);
		this.setReqInputType(reqInputType);
		this.setWhetherErroeHandle(whetherErroeHandle);
		this.setDzFileName(dzFileName);
		this.setInstName(instName);
		this.setBkChk(bkChk);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String errorTradeFlag;
	private java.lang.String acqInstIdCode;
	private java.lang.String fwdInstIdCode;
	private java.lang.String reqSysStance;
	private java.lang.String reqTime;
	private java.lang.String outAccount;
	private java.lang.String tradeAccount;
	private java.lang.String msgType;
	private java.lang.String process;
	private java.lang.String merType;
	private java.lang.String termId;
	private java.lang.String deductSysReference;
	private java.lang.String reqType;
	private java.lang.String authorizationCode;
	private java.lang.String rcvgInstIdCode;
	private java.lang.String issuingBankCode;
	private java.lang.String origDataStance;
	private java.lang.String deductSysResponse;
	private java.lang.String reqInputType;
	private java.lang.String acceptorReceiveFee;
	private java.lang.String acceptorPayFee;
	private java.lang.String byStagesFee;
	private java.lang.String tradeFee;
	private java.lang.String errorTradePayFee;
	private java.lang.String errorTradeReceiveFee;
	private java.lang.String errorInfo;
	private java.lang.String receiveRollOutCode;
	private java.lang.String accountIdentification;
	private java.lang.String intoInstCode;
	private java.lang.String onTradeTime;
	private java.lang.String cardNumber;
	private java.lang.String termReadAbility;
	private java.lang.String idConditionCode;
	private java.lang.String ondeductStlmDate;
	private java.lang.String onTradeAccount;
	private java.lang.String tradeAdress;
	private java.lang.String eci;
	private java.lang.Integer whetherErroeHandle;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.String merchantCode;
	private java.lang.String senderClearingOrganizations;
	private java.lang.String recipientClearingOrganization;
	private java.lang.String transfereeClearingOrganizations;
	private java.lang.String beforeTransationTerType;
	private java.lang.String merchantNameAddress;
	private java.lang.String specialBillingType;
	private java.lang.String specialChargesGrade;
	private java.lang.String tacLogo;
	private java.lang.String cardProductInfo;
	private java.lang.String tranCodeCausedError;
	private java.lang.String tranInitiatedMethod;
	private java.lang.String accountSettleType;
	private java.lang.String reservedForUse;
	private java.lang.Integer bkChk;



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
	 * Return the value associated with the column: error_trade_flag
	 */
	public java.lang.String getErrorTradeFlag () {
		return errorTradeFlag;
	}

	/**
	 * Set the value related to the column: error_trade_flag
	 * @param errorTradeFlag the error_trade_flag value
	 */
	public void setErrorTradeFlag (java.lang.String errorTradeFlag) {
		this.errorTradeFlag = errorTradeFlag;
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
	 * Return the value associated with the column: tradeAccount
	 */
	public java.lang.String getTradeAccount () {
		return tradeAccount;
	}

	/**
	 * Set the value related to the column: tradeAccount
	 * @param tradeAccount the tradeAccount value
	 */
	public void setTradeAccount (java.lang.String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}



	/**
	 * Return the value associated with the column: msgType
	 */
	public java.lang.String getMsgType () {
		return msgType;
	}

	/**
	 * Set the value related to the column: msgType
	 * @param msgType the msgType value
	 */
	public void setMsgType (java.lang.String msgType) {
		this.msgType = msgType;
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
	 * Return the value associated with the column: merType
	 */
	public java.lang.String getMerType () {
		return merType;
	}

	/**
	 * Set the value related to the column: merType
	 * @param merType the merType value
	 */
	public void setMerType (java.lang.String merType) {
		this.merType = merType;
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
	 * Return the value associated with the column: reqType
	 */
	public java.lang.String getReqType () {
		return reqType;
	}

	/**
	 * Set the value related to the column: reqType
	 * @param reqType the reqType value
	 */
	public void setReqType (java.lang.String reqType) {
		this.reqType = reqType;
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
	 * Return the value associated with the column: rcvgInstIdCode
	 */
	public java.lang.String getRcvgInstIdCode () {
		return rcvgInstIdCode;
	}

	/**
	 * Set the value related to the column: rcvgInstIdCode
	 * @param rcvgInstIdCode the rcvgInstIdCode value
	 */
	public void setRcvgInstIdCode (java.lang.String rcvgInstIdCode) {
		this.rcvgInstIdCode = rcvgInstIdCode;
	}



	/**
	 * Return the value associated with the column: issuingBankCode
	 */
	public java.lang.String getIssuingBankCode () {
		return issuingBankCode;
	}

	/**
	 * Set the value related to the column: issuingBankCode
	 * @param issuingBankCode the issuingBankCode value
	 */
	public void setIssuingBankCode (java.lang.String issuingBankCode) {
		this.issuingBankCode = issuingBankCode;
	}



	/**
	 * Return the value associated with the column: origDataStance
	 */
	public java.lang.String getOrigDataStance () {
		return origDataStance;
	}

	/**
	 * Set the value related to the column: origDataStance
	 * @param origDataStance the origDataStance value
	 */
	public void setOrigDataStance (java.lang.String origDataStance) {
		this.origDataStance = origDataStance;
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
	 * Return the value associated with the column: reqInputType
	 */
	public java.lang.String getReqInputType () {
		return reqInputType;
	}

	/**
	 * Set the value related to the column: reqInputType
	 * @param reqInputType the reqInputType value
	 */
	public void setReqInputType (java.lang.String reqInputType) {
		this.reqInputType = reqInputType;
	}



	/**
	 * Return the value associated with the column: acceptorReceiveFee
	 */
	public java.lang.String getAcceptorReceiveFee () {
		return acceptorReceiveFee;
	}

	/**
	 * Set the value related to the column: acceptorReceiveFee
	 * @param acceptorReceiveFee the acceptorReceiveFee value
	 */
	public void setAcceptorReceiveFee (java.lang.String acceptorReceiveFee) {
		this.acceptorReceiveFee = acceptorReceiveFee;
	}



	/**
	 * Return the value associated with the column: acceptorPayFee
	 */
	public java.lang.String getAcceptorPayFee () {
		return acceptorPayFee;
	}

	/**
	 * Set the value related to the column: acceptorPayFee
	 * @param acceptorPayFee the acceptorPayFee value
	 */
	public void setAcceptorPayFee (java.lang.String acceptorPayFee) {
		this.acceptorPayFee = acceptorPayFee;
	}



	/**
	 * Return the value associated with the column: byStagesFee
	 */
	public java.lang.String getByStagesFee () {
		return byStagesFee;
	}

	/**
	 * Set the value related to the column: byStagesFee
	 * @param byStagesFee the byStagesFee value
	 */
	public void setByStagesFee (java.lang.String byStagesFee) {
		this.byStagesFee = byStagesFee;
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
	 * Return the value associated with the column: errorTradePayFee
	 */
	public java.lang.String getErrorTradePayFee () {
		return errorTradePayFee;
	}

	/**
	 * Set the value related to the column: errorTradePayFee
	 * @param errorTradePayFee the errorTradePayFee value
	 */
	public void setErrorTradePayFee (java.lang.String errorTradePayFee) {
		this.errorTradePayFee = errorTradePayFee;
	}



	/**
	 * Return the value associated with the column: errorTradeReceiveFee
	 */
	public java.lang.String getErrorTradeReceiveFee () {
		return errorTradeReceiveFee;
	}

	/**
	 * Set the value related to the column: errorTradeReceiveFee
	 * @param errorTradeReceiveFee the errorTradeReceiveFee value
	 */
	public void setErrorTradeReceiveFee (java.lang.String errorTradeReceiveFee) {
		this.errorTradeReceiveFee = errorTradeReceiveFee;
	}



	/**
	 * Return the value associated with the column: error_info
	 */
	public java.lang.String getErrorInfo () {
		return errorInfo;
	}

	/**
	 * Set the value related to the column: error_info
	 * @param errorInfo the error_info value
	 */
	public void setErrorInfo (java.lang.String errorInfo) {
		this.errorInfo = errorInfo;
	}



	/**
	 * Return the value associated with the column: ReceiveRollOutCode
	 */
	public java.lang.String getReceiveRollOutCode () {
		return receiveRollOutCode;
	}

	/**
	 * Set the value related to the column: ReceiveRollOutCode
	 * @param receiveRollOutCode the ReceiveRollOutCode value
	 */
	public void setReceiveRollOutCode (java.lang.String receiveRollOutCode) {
		this.receiveRollOutCode = receiveRollOutCode;
	}



	/**
	 * Return the value associated with the column: accountIdentification
	 */
	public java.lang.String getAccountIdentification () {
		return accountIdentification;
	}

	/**
	 * Set the value related to the column: accountIdentification
	 * @param accountIdentification the accountIdentification value
	 */
	public void setAccountIdentification (java.lang.String accountIdentification) {
		this.accountIdentification = accountIdentification;
	}



	/**
	 * Return the value associated with the column: intoInstCode
	 */
	public java.lang.String getIntoInstCode () {
		return intoInstCode;
	}

	/**
	 * Set the value related to the column: intoInstCode
	 * @param intoInstCode the intoInstCode value
	 */
	public void setIntoInstCode (java.lang.String intoInstCode) {
		this.intoInstCode = intoInstCode;
	}



	/**
	 * Return the value associated with the column: onTradeTime
	 */
	public java.lang.String getOnTradeTime () {
		return onTradeTime;
	}

	/**
	 * Set the value related to the column: onTradeTime
	 * @param onTradeTime the onTradeTime value
	 */
	public void setOnTradeTime (java.lang.String onTradeTime) {
		this.onTradeTime = onTradeTime;
	}



	/**
	 * Return the value associated with the column: cardNumber
	 */
	public java.lang.String getCardNumber () {
		return cardNumber;
	}

	/**
	 * Set the value related to the column: cardNumber
	 * @param cardNumber the cardNumber value
	 */
	public void setCardNumber (java.lang.String cardNumber) {
		this.cardNumber = cardNumber;
	}



	/**
	 * Return the value associated with the column: termReadAbility
	 */
	public java.lang.String getTermReadAbility () {
		return termReadAbility;
	}

	/**
	 * Set the value related to the column: termReadAbility
	 * @param termReadAbility the termReadAbility value
	 */
	public void setTermReadAbility (java.lang.String termReadAbility) {
		this.termReadAbility = termReadAbility;
	}



	/**
	 * Return the value associated with the column: idConditionCode
	 */
	public java.lang.String getIdConditionCode () {
		return idConditionCode;
	}

	/**
	 * Set the value related to the column: idConditionCode
	 * @param idConditionCode the idConditionCode value
	 */
	public void setIdConditionCode (java.lang.String idConditionCode) {
		this.idConditionCode = idConditionCode;
	}



	/**
	 * Return the value associated with the column: onDeduct_stlm_date
	 */
	public java.lang.String getOndeductStlmDate () {
		return ondeductStlmDate;
	}

	/**
	 * Set the value related to the column: onDeduct_stlm_date
	 * @param ondeductStlmDate the onDeduct_stlm_date value
	 */
	public void setOndeductStlmDate (java.lang.String ondeductStlmDate) {
		this.ondeductStlmDate = ondeductStlmDate;
	}



	/**
	 * Return the value associated with the column: onTradeAccount
	 */
	public java.lang.String getOnTradeAccount () {
		return onTradeAccount;
	}

	/**
	 * Set the value related to the column: onTradeAccount
	 * @param onTradeAccount the onTradeAccount value
	 */
	public void setOnTradeAccount (java.lang.String onTradeAccount) {
		this.onTradeAccount = onTradeAccount;
	}



	/**
	 * Return the value associated with the column: tradeAdress
	 */
	public java.lang.String getTradeAdress () {
		return tradeAdress;
	}

	/**
	 * Set the value related to the column: tradeAdress
	 * @param tradeAdress the tradeAdress value
	 */
	public void setTradeAdress (java.lang.String tradeAdress) {
		this.tradeAdress = tradeAdress;
	}



	/**
	 * Return the value associated with the column: eci
	 */
	public java.lang.String getEci () {
		return eci;
	}

	/**
	 * Set the value related to the column: eci
	 * @param eci the eci value
	 */
	public void setEci (java.lang.String eci) {
		this.eci = eci;
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
	 * Return the value associated with the column: merchant_code
	 */
	public java.lang.String getMerchantCode () {
		return merchantCode;
	}

	/**
	 * Set the value related to the column: merchant_code
	 * @param merchantCode the merchant_code value
	 */
	public void setMerchantCode (java.lang.String merchantCode) {
		this.merchantCode = merchantCode;
	}



	/**
	 * Return the value associated with the column: sender_clearing_organizations
	 */
	public java.lang.String getSenderClearingOrganizations () {
		return senderClearingOrganizations;
	}

	/**
	 * Set the value related to the column: sender_clearing_organizations
	 * @param senderClearingOrganizations the sender_clearing_organizations value
	 */
	public void setSenderClearingOrganizations (java.lang.String senderClearingOrganizations) {
		this.senderClearingOrganizations = senderClearingOrganizations;
	}



	/**
	 * Return the value associated with the column: recipient_clearing_organization
	 */
	public java.lang.String getRecipientClearingOrganization () {
		return recipientClearingOrganization;
	}

	/**
	 * Set the value related to the column: recipient_clearing_organization
	 * @param recipientClearingOrganization the recipient_clearing_organization value
	 */
	public void setRecipientClearingOrganization (java.lang.String recipientClearingOrganization) {
		this.recipientClearingOrganization = recipientClearingOrganization;
	}



	/**
	 * Return the value associated with the column: transferee_clearing_organizations
	 */
	public java.lang.String getTransfereeClearingOrganizations () {
		return transfereeClearingOrganizations;
	}

	/**
	 * Set the value related to the column: transferee_clearing_organizations
	 * @param transfereeClearingOrganizations the transferee_clearing_organizations value
	 */
	public void setTransfereeClearingOrganizations (java.lang.String transfereeClearingOrganizations) {
		this.transfereeClearingOrganizations = transfereeClearingOrganizations;
	}



	/**
	 * Return the value associated with the column: before_transation_ter_type
	 */
	public java.lang.String getBeforeTransationTerType () {
		return beforeTransationTerType;
	}

	/**
	 * Set the value related to the column: before_transation_ter_type
	 * @param beforeTransationTerType the before_transation_ter_type value
	 */
	public void setBeforeTransationTerType (java.lang.String beforeTransationTerType) {
		this.beforeTransationTerType = beforeTransationTerType;
	}



	/**
	 * Return the value associated with the column: merchant_name_address
	 */
	public java.lang.String getMerchantNameAddress () {
		return merchantNameAddress;
	}

	/**
	 * Set the value related to the column: merchant_name_address
	 * @param merchantNameAddress the merchant_name_address value
	 */
	public void setMerchantNameAddress (java.lang.String merchantNameAddress) {
		this.merchantNameAddress = merchantNameAddress;
	}



	/**
	 * Return the value associated with the column: special_billing_type
	 */
	public java.lang.String getSpecialBillingType () {
		return specialBillingType;
	}

	/**
	 * Set the value related to the column: special_billing_type
	 * @param specialBillingType the special_billing_type value
	 */
	public void setSpecialBillingType (java.lang.String specialBillingType) {
		this.specialBillingType = specialBillingType;
	}



	/**
	 * Return the value associated with the column: special_charges_grade
	 */
	public java.lang.String getSpecialChargesGrade () {
		return specialChargesGrade;
	}

	/**
	 * Set the value related to the column: special_charges_grade
	 * @param specialChargesGrade the special_charges_grade value
	 */
	public void setSpecialChargesGrade (java.lang.String specialChargesGrade) {
		this.specialChargesGrade = specialChargesGrade;
	}



	/**
	 * Return the value associated with the column: tac_logo
	 */
	public java.lang.String getTacLogo () {
		return tacLogo;
	}

	/**
	 * Set the value related to the column: tac_logo
	 * @param tacLogo the tac_logo value
	 */
	public void setTacLogo (java.lang.String tacLogo) {
		this.tacLogo = tacLogo;
	}



	/**
	 * Return the value associated with the column: card_product_info
	 */
	public java.lang.String getCardProductInfo () {
		return cardProductInfo;
	}

	/**
	 * Set the value related to the column: card_product_info
	 * @param cardProductInfo the card_product_info value
	 */
	public void setCardProductInfo (java.lang.String cardProductInfo) {
		this.cardProductInfo = cardProductInfo;
	}



	/**
	 * Return the value associated with the column: tran_code_caused_error
	 */
	public java.lang.String getTranCodeCausedError () {
		return tranCodeCausedError;
	}

	/**
	 * Set the value related to the column: tran_code_caused_error
	 * @param tranCodeCausedError the tran_code_caused_error value
	 */
	public void setTranCodeCausedError (java.lang.String tranCodeCausedError) {
		this.tranCodeCausedError = tranCodeCausedError;
	}



	/**
	 * Return the value associated with the column: tran_initiated_method
	 */
	public java.lang.String getTranInitiatedMethod () {
		return tranInitiatedMethod;
	}

	/**
	 * Set the value related to the column: tran_initiated_method
	 * @param tranInitiatedMethod the tran_initiated_method value
	 */
	public void setTranInitiatedMethod (java.lang.String tranInitiatedMethod) {
		this.tranInitiatedMethod = tranInitiatedMethod;
	}



	/**
	 * Return the value associated with the column: account_settle_type
	 */
	public java.lang.String getAccountSettleType () {
		return accountSettleType;
	}

	/**
	 * Set the value related to the column: account_settle_type
	 * @param accountSettleType the account_settle_type value
	 */
	public void setAccountSettleType (java.lang.String accountSettleType) {
		this.accountSettleType = accountSettleType;
	}



	/**
	 * Return the value associated with the column: reserved_for_use
	 */
	public java.lang.String getReservedForUse () {
		return reservedForUse;
	}

	/**
	 * Set the value related to the column: reserved_for_use
	 * @param reservedForUse the reserved_for_use value
	 */
	public void setReservedForUse (java.lang.String reservedForUse) {
		this.reservedForUse = reservedForUse;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangErrorCupsLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangErrorCupsLst duizhangErrorCupsLst = (cn.com.chinaebi.dz.object.DuizhangErrorCupsLst) obj;
			if (null == this.getId() || null == duizhangErrorCupsLst.getId()) return false;
			else return (this.getId().equals(duizhangErrorCupsLst.getId()));
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