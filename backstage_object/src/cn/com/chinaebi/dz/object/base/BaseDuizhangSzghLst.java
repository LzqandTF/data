package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_szgh_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_szgh_lst"
 */

public abstract class BaseDuizhangSzghLst  implements Serializable {

	public static String REF = "DuizhangSzghLst";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_RESERVE_CHARACTER1 = "ReserveCharacter1";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_TIME_STMP = "TimeStmp";
	public static String PROP_INC_AMT = "IncAmt";
	public static String PROP_MSG_TYPE = "MsgType";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_ALTERNATE_COUNT1 = "AlternateCount1";
	public static String PROP_ALTERNATE_COUNT2 = "AlternateCount2";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_ACCEPTOR_PAY_FEE = "AcceptorPayFee";
	public static String PROP_ZONE_NO = "ZoneNo";
	public static String PROP_DEDUCT_SYS_RESPONSE = "DeductSysResponse";
	public static String PROP_REQ_TYPE = "ReqType";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_FEE_RATE = "FeeRate";
	public static String PROP_ACC_NAME = "AccName";
	public static String PROP_ID = "Id";
	public static String PROP_BR_NO = "BrNo";
	public static String PROP_STREAM_NO = "StreamNo";
	public static String PROP_SACC_TYPE = "SaccType";
	public static String PROP_RESERVE_AMOUNT_FIELD7 = "ReserveAmountField7";
	public static String PROP_FW_FEE_RATE = "FwFeeRate";
	public static String PROP_RESERVE_AMOUNT_FIELD6 = "ReserveAmountField6";
	public static String PROP_RESERVE_AMOUNT_FIELD9 = "ReserveAmountField9";
	public static String PROP_RESERVE_AMOUNT_FIELD8 = "ReserveAmountField8";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_RESERVE_AMOUNT_FIELD2 = "ReserveAmountField2";
	public static String PROP_RESERVE_AMOUNT_FIELD3 = "ReserveAmountField3";
	public static String PROP_RESERVE_AMOUNT_FIELD4 = "ReserveAmountField4";
	public static String PROP_RESERVE_AMOUNT_FIELD5 = "ReserveAmountField5";
	public static String PROP_FWD_INST_ID_CODE = "FwdInstIdCode";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_RESERVE_AMOUNT_FIELD1 = "ReserveAmountField1";
	public static String PROP_SZ_FEE_AMT = "SzFeeAmt";
	public static String PROP_TRADE_ADRESS = "TradeAdress";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_COOP_NAME = "CoopName";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_ACCEPTOR_RECEIVE_FEE = "AcceptorReceiveFee";
	public static String PROP_ACQ_INST_ID_CODE = "AcqInstIdCode";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_FW_FEE_AMT = "FwFeeAmt";
	public static String PROP_GC_FEE_AMT = "GcFeeAmt";
	public static String PROP_SET_ACC_NO = "SetAccNo";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_AUTHORIZATION_CODE = "AuthorizationCode";
	public static String PROP_TERMINAL_TYPE = "TerminalType";
	public static String PROP_DRC_RF = "DrcRf";
	public static String PROP_WHETHER_ERROE_HANDLE = "WhetherErroeHandle";
	public static String PROP_COOP_NO = "CoopNo";


	// constructors
	public BaseDuizhangSzghLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangSzghLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDuizhangSzghLst (
		java.lang.String id,
		java.lang.String reqSysStance,
		java.lang.String reqTime,
		java.lang.String tradeAmount,
		java.lang.String merCode,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk) {

		this.setId(id);
		this.setReqSysStance(reqSysStance);
		this.setReqTime(reqTime);
		this.setTradeAmount(tradeAmount);
		this.setMerCode(merCode);
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
	private java.lang.String acqInstIdCode;
	private java.lang.String fwdInstIdCode;
	private java.lang.String reqSysStance;
	private java.lang.String reqTime;
	private java.lang.String outAccount;
	private java.lang.String tradeAmount;
	private java.lang.String tradeFee;
	private java.lang.String msgType;
	private java.lang.String process;
	private java.lang.String termId;
	private java.lang.String merCode;
	private java.lang.String deductSysReference;
	private java.lang.String reqType;
	private java.lang.String authorizationCode;
	private java.lang.String deductSysResponse;
	private java.lang.String acceptorReceiveFee;
	private java.lang.String acceptorPayFee;
	private java.lang.String tradeAdress;
	private java.lang.String terminalType;
	private java.lang.String coopNo;
	private java.lang.String zoneNo;
	private java.lang.String brNo;
	private java.lang.String saccType;
	private java.lang.String setAccNo;
	private java.lang.String accName;
	private java.lang.String coopName;
	private java.lang.String incAmt;
	private java.lang.String gcFeeAmt;
	private java.lang.String szFeeAmt;
	private java.lang.String fwFeeAmt;
	private java.lang.String feeRate;
	private java.lang.String fwFeeRate;
	private java.lang.String drcRf;
	private java.lang.String timeStmp;
	private java.lang.String streamNo;
	private java.lang.String alternateCount1;
	private java.lang.String alternateCount2;
	private java.lang.String reserveAmountField1;
	private java.lang.String reserveAmountField2;
	private java.lang.String reserveAmountField3;
	private java.lang.String reserveAmountField4;
	private java.lang.String reserveAmountField5;
	private java.lang.String reserveAmountField6;
	private java.lang.String reserveAmountField7;
	private java.lang.String reserveAmountField8;
	private java.lang.String reserveAmountField9;
	private java.lang.String reserveCharacter1;
	private boolean whetherErroeHandle;
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
	 * Return the value associated with the column: coopNo
	 */
	public java.lang.String getCoopNo () {
		return coopNo;
	}

	/**
	 * Set the value related to the column: coopNo
	 * @param coopNo the coopNo value
	 */
	public void setCoopNo (java.lang.String coopNo) {
		this.coopNo = coopNo;
	}



	/**
	 * Return the value associated with the column: zoneNo
	 */
	public java.lang.String getZoneNo () {
		return zoneNo;
	}

	/**
	 * Set the value related to the column: zoneNo
	 * @param zoneNo the zoneNo value
	 */
	public void setZoneNo (java.lang.String zoneNo) {
		this.zoneNo = zoneNo;
	}



	/**
	 * Return the value associated with the column: brNo
	 */
	public java.lang.String getBrNo () {
		return brNo;
	}

	/**
	 * Set the value related to the column: brNo
	 * @param brNo the brNo value
	 */
	public void setBrNo (java.lang.String brNo) {
		this.brNo = brNo;
	}



	/**
	 * Return the value associated with the column: saccType
	 */
	public java.lang.String getSaccType () {
		return saccType;
	}

	/**
	 * Set the value related to the column: saccType
	 * @param saccType the saccType value
	 */
	public void setSaccType (java.lang.String saccType) {
		this.saccType = saccType;
	}



	/**
	 * Return the value associated with the column: setAccNo
	 */
	public java.lang.String getSetAccNo () {
		return setAccNo;
	}

	/**
	 * Set the value related to the column: setAccNo
	 * @param setAccNo the setAccNo value
	 */
	public void setSetAccNo (java.lang.String setAccNo) {
		this.setAccNo = setAccNo;
	}



	/**
	 * Return the value associated with the column: accName
	 */
	public java.lang.String getAccName () {
		return accName;
	}

	/**
	 * Set the value related to the column: accName
	 * @param accName the accName value
	 */
	public void setAccName (java.lang.String accName) {
		this.accName = accName;
	}



	/**
	 * Return the value associated with the column: coopName
	 */
	public java.lang.String getCoopName () {
		return coopName;
	}

	/**
	 * Set the value related to the column: coopName
	 * @param coopName the coopName value
	 */
	public void setCoopName (java.lang.String coopName) {
		this.coopName = coopName;
	}



	/**
	 * Return the value associated with the column: incAmt
	 */
	public java.lang.String getIncAmt () {
		return incAmt;
	}

	/**
	 * Set the value related to the column: incAmt
	 * @param incAmt the incAmt value
	 */
	public void setIncAmt (java.lang.String incAmt) {
		this.incAmt = incAmt;
	}



	/**
	 * Return the value associated with the column: gcFeeAmt
	 */
	public java.lang.String getGcFeeAmt () {
		return gcFeeAmt;
	}

	/**
	 * Set the value related to the column: gcFeeAmt
	 * @param gcFeeAmt the gcFeeAmt value
	 */
	public void setGcFeeAmt (java.lang.String gcFeeAmt) {
		this.gcFeeAmt = gcFeeAmt;
	}



	/**
	 * Return the value associated with the column: szFeeAmt
	 */
	public java.lang.String getSzFeeAmt () {
		return szFeeAmt;
	}

	/**
	 * Set the value related to the column: szFeeAmt
	 * @param szFeeAmt the szFeeAmt value
	 */
	public void setSzFeeAmt (java.lang.String szFeeAmt) {
		this.szFeeAmt = szFeeAmt;
	}



	/**
	 * Return the value associated with the column: fwFeeAmt
	 */
	public java.lang.String getFwFeeAmt () {
		return fwFeeAmt;
	}

	/**
	 * Set the value related to the column: fwFeeAmt
	 * @param fwFeeAmt the fwFeeAmt value
	 */
	public void setFwFeeAmt (java.lang.String fwFeeAmt) {
		this.fwFeeAmt = fwFeeAmt;
	}



	/**
	 * Return the value associated with the column: feeRate
	 */
	public java.lang.String getFeeRate () {
		return feeRate;
	}

	/**
	 * Set the value related to the column: feeRate
	 * @param feeRate the feeRate value
	 */
	public void setFeeRate (java.lang.String feeRate) {
		this.feeRate = feeRate;
	}



	/**
	 * Return the value associated with the column: fwFeeRate
	 */
	public java.lang.String getFwFeeRate () {
		return fwFeeRate;
	}

	/**
	 * Set the value related to the column: fwFeeRate
	 * @param fwFeeRate the fwFeeRate value
	 */
	public void setFwFeeRate (java.lang.String fwFeeRate) {
		this.fwFeeRate = fwFeeRate;
	}



	/**
	 * Return the value associated with the column: drcRf
	 */
	public java.lang.String getDrcRf () {
		return drcRf;
	}

	/**
	 * Set the value related to the column: drcRf
	 * @param drcRf the drcRf value
	 */
	public void setDrcRf (java.lang.String drcRf) {
		this.drcRf = drcRf;
	}



	/**
	 * Return the value associated with the column: timeStmp
	 */
	public java.lang.String getTimeStmp () {
		return timeStmp;
	}

	/**
	 * Set the value related to the column: timeStmp
	 * @param timeStmp the timeStmp value
	 */
	public void setTimeStmp (java.lang.String timeStmp) {
		this.timeStmp = timeStmp;
	}



	/**
	 * Return the value associated with the column: streamNo
	 */
	public java.lang.String getStreamNo () {
		return streamNo;
	}

	/**
	 * Set the value related to the column: streamNo
	 * @param streamNo the streamNo value
	 */
	public void setStreamNo (java.lang.String streamNo) {
		this.streamNo = streamNo;
	}



	/**
	 * Return the value associated with the column: alternateCount1
	 */
	public java.lang.String getAlternateCount1 () {
		return alternateCount1;
	}

	/**
	 * Set the value related to the column: alternateCount1
	 * @param alternateCount1 the alternateCount1 value
	 */
	public void setAlternateCount1 (java.lang.String alternateCount1) {
		this.alternateCount1 = alternateCount1;
	}



	/**
	 * Return the value associated with the column: alternateCount2
	 */
	public java.lang.String getAlternateCount2 () {
		return alternateCount2;
	}

	/**
	 * Set the value related to the column: alternateCount2
	 * @param alternateCount2 the alternateCount2 value
	 */
	public void setAlternateCount2 (java.lang.String alternateCount2) {
		this.alternateCount2 = alternateCount2;
	}



	/**
	 * Return the value associated with the column: reserveAmountField1
	 */
	public java.lang.String getReserveAmountField1 () {
		return reserveAmountField1;
	}

	/**
	 * Set the value related to the column: reserveAmountField1
	 * @param reserveAmountField1 the reserveAmountField1 value
	 */
	public void setReserveAmountField1 (java.lang.String reserveAmountField1) {
		this.reserveAmountField1 = reserveAmountField1;
	}



	/**
	 * Return the value associated with the column: reserveAmountField2
	 */
	public java.lang.String getReserveAmountField2 () {
		return reserveAmountField2;
	}

	/**
	 * Set the value related to the column: reserveAmountField2
	 * @param reserveAmountField2 the reserveAmountField2 value
	 */
	public void setReserveAmountField2 (java.lang.String reserveAmountField2) {
		this.reserveAmountField2 = reserveAmountField2;
	}



	/**
	 * Return the value associated with the column: reserveAmountField3
	 */
	public java.lang.String getReserveAmountField3 () {
		return reserveAmountField3;
	}

	/**
	 * Set the value related to the column: reserveAmountField3
	 * @param reserveAmountField3 the reserveAmountField3 value
	 */
	public void setReserveAmountField3 (java.lang.String reserveAmountField3) {
		this.reserveAmountField3 = reserveAmountField3;
	}



	/**
	 * Return the value associated with the column: reserveAmountField4
	 */
	public java.lang.String getReserveAmountField4 () {
		return reserveAmountField4;
	}

	/**
	 * Set the value related to the column: reserveAmountField4
	 * @param reserveAmountField4 the reserveAmountField4 value
	 */
	public void setReserveAmountField4 (java.lang.String reserveAmountField4) {
		this.reserveAmountField4 = reserveAmountField4;
	}



	/**
	 * Return the value associated with the column: reserveAmountField5
	 */
	public java.lang.String getReserveAmountField5 () {
		return reserveAmountField5;
	}

	/**
	 * Set the value related to the column: reserveAmountField5
	 * @param reserveAmountField5 the reserveAmountField5 value
	 */
	public void setReserveAmountField5 (java.lang.String reserveAmountField5) {
		this.reserveAmountField5 = reserveAmountField5;
	}



	/**
	 * Return the value associated with the column: reserveAmountField6
	 */
	public java.lang.String getReserveAmountField6 () {
		return reserveAmountField6;
	}

	/**
	 * Set the value related to the column: reserveAmountField6
	 * @param reserveAmountField6 the reserveAmountField6 value
	 */
	public void setReserveAmountField6 (java.lang.String reserveAmountField6) {
		this.reserveAmountField6 = reserveAmountField6;
	}



	/**
	 * Return the value associated with the column: reserveAmountField7
	 */
	public java.lang.String getReserveAmountField7 () {
		return reserveAmountField7;
	}

	/**
	 * Set the value related to the column: reserveAmountField7
	 * @param reserveAmountField7 the reserveAmountField7 value
	 */
	public void setReserveAmountField7 (java.lang.String reserveAmountField7) {
		this.reserveAmountField7 = reserveAmountField7;
	}



	/**
	 * Return the value associated with the column: reserveAmountField8
	 */
	public java.lang.String getReserveAmountField8 () {
		return reserveAmountField8;
	}

	/**
	 * Set the value related to the column: reserveAmountField8
	 * @param reserveAmountField8 the reserveAmountField8 value
	 */
	public void setReserveAmountField8 (java.lang.String reserveAmountField8) {
		this.reserveAmountField8 = reserveAmountField8;
	}



	/**
	 * Return the value associated with the column: reserveAmountField9
	 */
	public java.lang.String getReserveAmountField9 () {
		return reserveAmountField9;
	}

	/**
	 * Set the value related to the column: reserveAmountField9
	 * @param reserveAmountField9 the reserveAmountField9 value
	 */
	public void setReserveAmountField9 (java.lang.String reserveAmountField9) {
		this.reserveAmountField9 = reserveAmountField9;
	}



	/**
	 * Return the value associated with the column: reserveCharacter1
	 */
	public java.lang.String getReserveCharacter1 () {
		return reserveCharacter1;
	}

	/**
	 * Set the value related to the column: reserveCharacter1
	 * @param reserveCharacter1 the reserveCharacter1 value
	 */
	public void setReserveCharacter1 (java.lang.String reserveCharacter1) {
		this.reserveCharacter1 = reserveCharacter1;
	}



	/**
	 * Return the value associated with the column: whetherErroeHandle
	 */
	public boolean isWhetherErroeHandle () {
		return whetherErroeHandle;
	}

	/**
	 * Set the value related to the column: whetherErroeHandle
	 * @param whetherErroeHandle the whetherErroeHandle value
	 */
	public void setWhetherErroeHandle (boolean whetherErroeHandle) {
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
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangSzghLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangSzghLst duizhangSzghLst = (cn.com.chinaebi.dz.object.DuizhangSzghLst) obj;
			if (null == this.getId() || null == duizhangSzghLst.getId()) return false;
			else return (this.getId().equals(duizhangSzghLst.getId()));
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