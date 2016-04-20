package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_cups_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_cups_lst"
 */

public abstract class BaseDuizhangCupsLst  implements Serializable {

	public static String REF = "DuizhangCupsLst";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_ISSUING_BANK_CODE = "IssuingBankCode";
	public static String PROP_ORIG_DATA_TIME = "OrigDataTime";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_CONVERT_SHOW = "ConvertShow";
	public static String PROP_THROUGH_SERVICE_FEE = "ThroughServiceFee";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_MSG_TYPE = "MsgType";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_ECI = "Eci";
	public static String PROP_ID_CONDITION_CODE = "IdConditionCode";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_FWD_INST_ID_CODE = "FwdInstIdCode";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_TRADE_ADRESS = "TradeAdress";
	public static String PROP_CARD_NUMBER = "CardNumber";
	public static String PROP_REQ_INPUT_TYPE = "ReqInputType";
	public static String PROP_PORTION_AMOUNT = "PortionAmount";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_ACCEPTOR_RECEIVE_FEE = "AcceptorReceiveFee";
	public static String PROP_OTHER_INFO = "OtherInfo";
	public static String PROP_TERM_READ_ABILITY = "TermReadAbility";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_ACQ_INST_ID_CODE = "AcqInstIdCode";
	public static String PROP_ACCEPTOR_PAY_FEE = "AcceptorPayFee";
	public static String PROP_MER_TYPE = "MerType";
	public static String PROP_DEDUCT_SYS_RESPONSE = "DeductSysResponse";
	public static String PROP_REQ_TYPE = "ReqType";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_AUTHORIZATION_CODE = "AuthorizationCode";
	public static String PROP_TERMINAL_TYPE = "TerminalType";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_BY_STAGES_FEE = "ByStagesFee";
	public static String PROP_RCVG_INST_ID_CODE = "RcvgInstIdCode";
	public static String PROP_ID = "Id";
	public static String PROP_WHETHER_ERROE_HANDLE = "WhetherErroeHandle";
	public static String PROP_ORIG_DATA_STANCE = "OrigDataStance";


	// constructors
	public BaseDuizhangCupsLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangCupsLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDuizhangCupsLst (
		java.lang.String id,
		java.lang.String acqInstIdCode,
		java.lang.String fwdInstIdCode,
		java.lang.String reqSysStance,
		java.lang.String reqTime,
		java.lang.String outAccount,
		java.lang.String tradeAmount,
		java.lang.String msgType,
		java.lang.String termId,
		java.lang.String merCode,
		java.lang.String reqType,
		java.lang.String deductSysResponse,
		java.lang.String reqInputType,
		int whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk,
		java.lang.String deductStlmDate) {

		this.setId(id);
		this.setAcqInstIdCode(acqInstIdCode);
		this.setFwdInstIdCode(fwdInstIdCode);
		this.setReqSysStance(reqSysStance);
		this.setReqTime(reqTime);
		this.setOutAccount(outAccount);
		this.setTradeAmount(tradeAmount);
		this.setMsgType(msgType);
		this.setTermId(termId);
		this.setMerCode(merCode);
		this.setReqType(reqType);
		this.setDeductSysResponse(deductSysResponse);
		this.setReqInputType(reqInputType);
		this.setWhetherErroeHandle(whetherErroeHandle);
		this.setDzFileName(dzFileName);
		this.setInstName(instName);
		this.setBkChk(bkChk);
		this.setDeductStlmDate(deductStlmDate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String acqInstIdCode;
	private java.lang.String fwdInstIdCode;
	private java.lang.String reqSysStance;
	private java.lang.String reqTime;
	private java.lang.String outAccount;
	private java.lang.String tradeAmount;
	private java.lang.String portionAmount;
	private java.lang.String tradeFee;
	private java.lang.String msgType;
	private java.lang.String process;
	private java.lang.String merType;
	private java.lang.String termId;
	private java.lang.String merCode;
	private java.lang.String deductSysReference;
	private java.lang.String reqType;
	private java.lang.String authorizationCode;
	private java.lang.String rcvgInstIdCode;
	private java.lang.String origDataStance;
	private java.lang.String deductSysResponse;
	private java.lang.String reqInputType;
	private java.lang.String acceptorReceiveFee;
	private java.lang.String acceptorPayFee;
	private java.lang.String throughServiceFee;
	private java.lang.String convertShow;
	private java.lang.String cardNumber;
	private java.lang.String termReadAbility;
	private java.lang.String idConditionCode;
	private java.lang.String origDataTime;
	private java.lang.String issuingBankCode;
	private java.lang.String tradeAdress;
	private java.lang.String terminalType;
	private java.lang.String eci;
	private java.lang.String byStagesFee;
	private java.lang.String otherInfo;
	private int whetherErroeHandle;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.Integer bkChk;
	private java.lang.String deductStlmDate;



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
	 * Return the value associated with the column: portionAmount
	 */
	public java.lang.String getPortionAmount () {
		return portionAmount;
	}

	/**
	 * Set the value related to the column: portionAmount
	 * @param portionAmount the portionAmount value
	 */
	public void setPortionAmount (java.lang.String portionAmount) {
		this.portionAmount = portionAmount;
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
	 * Return the value associated with the column: throughServiceFee
	 */
	public java.lang.String getThroughServiceFee () {
		return throughServiceFee;
	}

	/**
	 * Set the value related to the column: throughServiceFee
	 * @param throughServiceFee the throughServiceFee value
	 */
	public void setThroughServiceFee (java.lang.String throughServiceFee) {
		this.throughServiceFee = throughServiceFee;
	}



	/**
	 * Return the value associated with the column: convertShow
	 */
	public java.lang.String getConvertShow () {
		return convertShow;
	}

	/**
	 * Set the value related to the column: convertShow
	 * @param convertShow the convertShow value
	 */
	public void setConvertShow (java.lang.String convertShow) {
		this.convertShow = convertShow;
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
	 * Return the value associated with the column: origDataTime
	 */
	public java.lang.String getOrigDataTime () {
		return origDataTime;
	}

	/**
	 * Set the value related to the column: origDataTime
	 * @param origDataTime the origDataTime value
	 */
	public void setOrigDataTime (java.lang.String origDataTime) {
		this.origDataTime = origDataTime;
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
	 * Return the value associated with the column: terminalType
	 */
	public java.lang.String getTerminalType () {
		return terminalType;
	}

	/**
	 * Set the value related to the column: terminalType
	 * @param terminalType the terminalType value
	 */
	public void setTerminalType (java.lang.String terminalType) {
		this.terminalType = terminalType;
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
	 * Return the value associated with the column: otherInfo
	 */
	public java.lang.String getOtherInfo () {
		return otherInfo;
	}

	/**
	 * Set the value related to the column: otherInfo
	 * @param otherInfo the otherInfo value
	 */
	public void setOtherInfo (java.lang.String otherInfo) {
		this.otherInfo = otherInfo;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangCupsLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangCupsLst duizhangCupsLst = (cn.com.chinaebi.dz.object.DuizhangCupsLst) obj;
			if (null == this.getId() || null == duizhangCupsLst.getId()) return false;
			else return (this.getId().equals(duizhangCupsLst.getId()));
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