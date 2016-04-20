package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the ryt_gonghang table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="ryt_gonghang"
 */

public abstract class BaseRytGonghang  implements Serializable {

	public static String REF = "RytGonghang";
	public static String PROP_ZF_FEE_BJ = "ZfFeeBj";
	public static String PROP_MID = "Mid";
	public static String PROP_BANK_FEE = "BankFee";
	public static String PROP_BID = "Bid";
	public static String PROP_OID = "Oid";
	public static String PROP_WHETHER_RIQIE = "WhetherRiqie";
	public static String PROP_MDATE = "Mdate";
	public static String PROP_BK_FLAG = "BkFlag";
	public static String PROP_MOBILE_NO = "MobileNo";
	public static String PROP_CARD_NO = "CardNo";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_REF_SEQ = "RefSeq";
	public static String PROP_INIT_SYS_DATE = "InitSysDate";
	public static String PROP_ZF_FILE_FEE = "ZfFileFee";
	public static String PROP_AGAINPAY_STATUS = "AgainpayStatus";
	public static String PROP_VERSION = "Version";
	public static String PROP_CURRENCY = "Currency";
	public static String PROP_P12 = "P12";
	public static String PROP_SYS_DATE = "SysDate";
	public static String PROP_P11 = "P11";
	public static String PROP_P14 = "P14";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_P13 = "P13";
	public static String PROP_P15 = "P15";
	public static String PROP_IN_USER_ID = "InUserId";
	public static String PROP_ORG_SEQ = "OrgSeq";
	public static String PROP_FEE_AMT = "FeeAmt";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_IS_LIQ = "IsLiq";
	public static String PROP_BANK_ID = "bankId";
	public static String PROP_WHETHER_VALID = "WhetherValid";
	public static String PROP_WHETHER_JS = "WhetherJs";
	public static String PROP_WHETHER_QS = "WhetherQs";
	public static String PROP_ID = "Id";
	public static String PROP_ERROR_MSG = "ErrorMsg";
	public static String PROP_ERROR_CODE = "ErrorCode";
	public static String PROP_SYS_TIME = "SysTime";
	public static String PROP_P10 = "P10";
	public static String PROP_REFUND_AMT = "RefundAmt";
	public static String PROP_TSTAT = "Tstat";
	public static String PROP_TYPE = "Type";
	public static String PROP_BK_FEE_MODEL = "BkFeeModel";
	public static String PROP_BK_RECV = "BkRecv";
	public static String PROP_MER_PRIV = "MerPriv";
	public static String PROP_AGAINPAY_DATE = "AgainpayDate";
	public static String PROP_PRE_AMT1 = "PreAmt1";
	public static String PROP_PHONE_NO = "PhoneNo";
	public static String PROP_DATA_SOURCE = "DataSource";
	public static String PROP_ZF_FEE = "ZfFee";
	public static String PROP_BIND_MID = "BindMid";
	public static String PROP_BATCH = "Batch";
	public static String PROP_BK_RESP = "BkResp";
	public static String PROP_AUTHOR_TYPE = "AuthorType";
	public static String PROP_BK_SEQ2 = "BkSeq2";
	public static String PROP_BK_SEQ1 = "BkSeq1";
	public static String PROP_PAY_AMT = "PayAmt";
	public static String PROP_TRANS_PERIOD = "TransPeriod";
	public static String PROP_GATE = "Gate";
	public static String PROP_WHETHER_ACCESS_STANCE = "WhetherAccessStance";
	public static String PROP_OPER_ID = "OperId";
	public static String PROP_P9 = "P9";
	public static String PROP_IS_NOTICE = "IsNotice";
	public static String PROP_P8 = "P8";
	public static String PROP_GID = "Gid";
	public static String PROP_P5 = "P5";
	public static String PROP_P4 = "P4";
	public static String PROP_OUT_USER_ID = "OutUserId";
	public static String PROP_P7 = "P7";
	public static String PROP_P6 = "P6";
	public static String PROP_P1 = "P1";
	public static String PROP_PRE_AMT = "PreAmt";
	public static String PROP_P3 = "P3";
	public static String PROP_MER_FEE = "MerFee";
	public static String PROP_P2 = "P2";
	public static String PROP_BK_DATE = "BkDate";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_BK_SEND = "BkSend";
	public static String PROP_WHETHER_ERROE_HANDLE = "WhetherErroeHandle";
	public static String PROP_WHTHER_INNER_JS = "WhtherInnerJs";


	// constructors
	public BaseRytGonghang () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRytGonghang (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseRytGonghang (
		java.lang.Long id,
		java.lang.Integer version,
		java.lang.Integer mdate,
		java.lang.Byte type,
		java.lang.Integer gate,
		java.lang.Integer sysDate,
		java.lang.Integer initSysDate,
		java.lang.Integer sysTime,
		java.lang.Byte tstat,
		java.lang.Integer bkFlag,
		java.lang.Integer transPeriod,
		boolean whetherJs,
		boolean whetherValid,
		java.lang.Integer whetherErroeHandle,
		boolean whetherRiqie,
		java.lang.Integer bankId) {

		this.setId(id);
		this.setVersion(version);
		this.setMdate(mdate);
		this.setType(type);
		this.setGate(gate);
		this.setSysDate(sysDate);
		this.setInitSysDate(initSysDate);
		this.setSysTime(sysTime);
		this.setTstat(tstat);
		this.setBkFlag(bkFlag);
		this.setTransPeriod(transPeriod);
		this.setWhetherJs(whetherJs);
		this.setWhetherValid(whetherValid);
		this.setWhetherErroeHandle(whetherErroeHandle);
		this.setWhetherRiqie(whetherRiqie);
		this.setBankId(bankId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long id;

	// fields
	private java.lang.Integer version;
	private java.lang.Integer mdate;
	private java.lang.String mid;
	private java.lang.String bid;
	private java.lang.String oid;
	private java.lang.Long amount;
	private java.lang.Long payAmt;
	private java.lang.Byte type;
	private java.lang.Integer gate;
	private java.lang.Integer sysDate;
	private java.lang.Integer initSysDate;
	private java.lang.Integer sysTime;
	private java.lang.String batch;
	private java.lang.Integer feeAmt;
	private java.lang.Integer bankFee;
	private java.lang.Byte tstat;
	private java.lang.Integer bkFlag;
	private java.lang.Long orgSeq;
	private java.lang.Long refSeq;
	private java.lang.Long refundAmt;
	private java.lang.String merPriv;
	private java.lang.Integer bkSend;
	private java.lang.Integer bkRecv;
	private java.lang.Byte bkChk;
	private java.lang.Integer bkDate;
	private java.lang.String bkSeq1;
	private java.lang.String bkSeq2;
	private java.lang.String bkResp;
	private java.lang.String mobileNo;
	private java.lang.Integer transPeriod;
	private java.lang.String cardNo;
	private java.lang.String errorCode;
	private java.lang.Integer authorType;
	private java.lang.String phoneNo;
	private java.lang.Integer operId;
	private java.lang.Integer gid;
	private java.lang.Integer preAmt;
	private java.lang.String bkFeeModel;
	private java.lang.Integer preAmt1;
	private java.lang.String errorMsg;
	private java.lang.String p1;
	private java.lang.String p2;
	private java.lang.String p3;
	private java.lang.String p4;
	private java.lang.String p5;
	private java.lang.String p6;
	private java.lang.String p7;
	private java.lang.String p8;
	private java.lang.String p9;
	private java.lang.String p10;
	private java.lang.String p11;
	private java.lang.String p12;
	private java.lang.String p13;
	private java.lang.String p14;
	private java.lang.String p15;
	private java.lang.Byte isLiq;
	private java.lang.Byte isNotice;
	private java.lang.Byte dataSource;
	private java.lang.String currency;
	private java.math.BigDecimal exchangeRate;
	private java.lang.Integer againpayDate;
	private java.lang.Integer againpayStatus;
	private boolean whetherJs;
	private boolean whetherValid;
	private java.lang.Integer whetherErroeHandle;
	private boolean whetherRiqie;
	private boolean whetherQs;
	private java.lang.Double merFee;
	private java.lang.Double zfFee;
	private java.lang.String zfFileFee;
	private java.lang.Integer zfFeeBj;
	private java.lang.Integer deductStlmDate;
	private boolean whetherAccessStance;
	private java.lang.Integer bankId;
	private boolean whtherInnerJs;
	private java.lang.String outUserId;
	private java.lang.String inUserId;
	private java.lang.String bindMid;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="tseq"
     */
	public java.lang.Long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: version
	 */
	public java.lang.Integer getVersion () {
		return version;
	}

	/**
	 * Set the value related to the column: version
	 * @param version the version value
	 */
	public void setVersion (java.lang.Integer version) {
		this.version = version;
	}



	/**
	 * Return the value associated with the column: mdate
	 */
	public java.lang.Integer getMdate () {
		return mdate;
	}

	/**
	 * Set the value related to the column: mdate
	 * @param mdate the mdate value
	 */
	public void setMdate (java.lang.Integer mdate) {
		this.mdate = mdate;
	}



	/**
	 * Return the value associated with the column: mid
	 */
	public java.lang.String getMid () {
		return mid;
	}

	/**
	 * Set the value related to the column: mid
	 * @param mid the mid value
	 */
	public void setMid (java.lang.String mid) {
		this.mid = mid;
	}



	/**
	 * Return the value associated with the column: bid
	 */
	public java.lang.String getBid () {
		return bid;
	}

	/**
	 * Set the value related to the column: bid
	 * @param bid the bid value
	 */
	public void setBid (java.lang.String bid) {
		this.bid = bid;
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
	 * Return the value associated with the column: amount
	 */
	public java.lang.Long getAmount () {
		return amount;
	}

	/**
	 * Set the value related to the column: amount
	 * @param amount the amount value
	 */
	public void setAmount (java.lang.Long amount) {
		this.amount = amount;
	}



	/**
	 * Return the value associated with the column: pay_amt
	 */
	public java.lang.Long getPayAmt () {
		return payAmt;
	}

	/**
	 * Set the value related to the column: pay_amt
	 * @param payAmt the pay_amt value
	 */
	public void setPayAmt (java.lang.Long payAmt) {
		this.payAmt = payAmt;
	}



	/**
	 * Return the value associated with the column: type
	 */
	public java.lang.Byte getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.Byte type) {
		this.type = type;
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
	 * Return the value associated with the column: init_sys_date
	 */
	public java.lang.Integer getInitSysDate () {
		return initSysDate;
	}

	/**
	 * Set the value related to the column: init_sys_date
	 * @param initSysDate the init_sys_date value
	 */
	public void setInitSysDate (java.lang.Integer initSysDate) {
		this.initSysDate = initSysDate;
	}



	/**
	 * Return the value associated with the column: sys_time
	 */
	public java.lang.Integer getSysTime () {
		return sysTime;
	}

	/**
	 * Set the value related to the column: sys_time
	 * @param sysTime the sys_time value
	 */
	public void setSysTime (java.lang.Integer sysTime) {
		this.sysTime = sysTime;
	}



	/**
	 * Return the value associated with the column: batch
	 */
	public java.lang.String getBatch () {
		return batch;
	}

	/**
	 * Set the value related to the column: batch
	 * @param batch the batch value
	 */
	public void setBatch (java.lang.String batch) {
		this.batch = batch;
	}



	/**
	 * Return the value associated with the column: fee_amt
	 */
	public java.lang.Integer getFeeAmt () {
		return feeAmt;
	}

	/**
	 * Set the value related to the column: fee_amt
	 * @param feeAmt the fee_amt value
	 */
	public void setFeeAmt (java.lang.Integer feeAmt) {
		this.feeAmt = feeAmt;
	}



	/**
	 * Return the value associated with the column: bank_fee
	 */
	public java.lang.Integer getBankFee () {
		return bankFee;
	}

	/**
	 * Set the value related to the column: bank_fee
	 * @param bankFee the bank_fee value
	 */
	public void setBankFee (java.lang.Integer bankFee) {
		this.bankFee = bankFee;
	}



	/**
	 * Return the value associated with the column: tstat
	 */
	public java.lang.Byte getTstat () {
		return tstat;
	}

	/**
	 * Set the value related to the column: tstat
	 * @param tstat the tstat value
	 */
	public void setTstat (java.lang.Byte tstat) {
		this.tstat = tstat;
	}



	/**
	 * Return the value associated with the column: bk_flag
	 */
	public java.lang.Integer getBkFlag () {
		return bkFlag;
	}

	/**
	 * Set the value related to the column: bk_flag
	 * @param bkFlag the bk_flag value
	 */
	public void setBkFlag (java.lang.Integer bkFlag) {
		this.bkFlag = bkFlag;
	}



	/**
	 * Return the value associated with the column: org_seq
	 */
	public java.lang.Long getOrgSeq () {
		return orgSeq;
	}

	/**
	 * Set the value related to the column: org_seq
	 * @param orgSeq the org_seq value
	 */
	public void setOrgSeq (java.lang.Long orgSeq) {
		this.orgSeq = orgSeq;
	}



	/**
	 * Return the value associated with the column: ref_seq
	 */
	public java.lang.Long getRefSeq () {
		return refSeq;
	}

	/**
	 * Set the value related to the column: ref_seq
	 * @param refSeq the ref_seq value
	 */
	public void setRefSeq (java.lang.Long refSeq) {
		this.refSeq = refSeq;
	}



	/**
	 * Return the value associated with the column: refund_amt
	 */
	public java.lang.Long getRefundAmt () {
		return refundAmt;
	}

	/**
	 * Set the value related to the column: refund_amt
	 * @param refundAmt the refund_amt value
	 */
	public void setRefundAmt (java.lang.Long refundAmt) {
		this.refundAmt = refundAmt;
	}



	/**
	 * Return the value associated with the column: mer_priv
	 */
	public java.lang.String getMerPriv () {
		return merPriv;
	}

	/**
	 * Set the value related to the column: mer_priv
	 * @param merPriv the mer_priv value
	 */
	public void setMerPriv (java.lang.String merPriv) {
		this.merPriv = merPriv;
	}



	/**
	 * Return the value associated with the column: bk_send
	 */
	public java.lang.Integer getBkSend () {
		return bkSend;
	}

	/**
	 * Set the value related to the column: bk_send
	 * @param bkSend the bk_send value
	 */
	public void setBkSend (java.lang.Integer bkSend) {
		this.bkSend = bkSend;
	}



	/**
	 * Return the value associated with the column: bk_recv
	 */
	public java.lang.Integer getBkRecv () {
		return bkRecv;
	}

	/**
	 * Set the value related to the column: bk_recv
	 * @param bkRecv the bk_recv value
	 */
	public void setBkRecv (java.lang.Integer bkRecv) {
		this.bkRecv = bkRecv;
	}



	/**
	 * Return the value associated with the column: bk_chk
	 */
	public java.lang.Byte getBkChk () {
		return bkChk;
	}

	/**
	 * Set the value related to the column: bk_chk
	 * @param bkChk the bk_chk value
	 */
	public void setBkChk (java.lang.Byte bkChk) {
		this.bkChk = bkChk;
	}



	/**
	 * Return the value associated with the column: bk_date
	 */
	public java.lang.Integer getBkDate () {
		return bkDate;
	}

	/**
	 * Set the value related to the column: bk_date
	 * @param bkDate the bk_date value
	 */
	public void setBkDate (java.lang.Integer bkDate) {
		this.bkDate = bkDate;
	}



	/**
	 * Return the value associated with the column: bk_seq1
	 */
	public java.lang.String getBkSeq1 () {
		return bkSeq1;
	}

	/**
	 * Set the value related to the column: bk_seq1
	 * @param bkSeq1 the bk_seq1 value
	 */
	public void setBkSeq1 (java.lang.String bkSeq1) {
		this.bkSeq1 = bkSeq1;
	}



	/**
	 * Return the value associated with the column: bk_seq2
	 */
	public java.lang.String getBkSeq2 () {
		return bkSeq2;
	}

	/**
	 * Set the value related to the column: bk_seq2
	 * @param bkSeq2 the bk_seq2 value
	 */
	public void setBkSeq2 (java.lang.String bkSeq2) {
		this.bkSeq2 = bkSeq2;
	}



	/**
	 * Return the value associated with the column: bk_resp
	 */
	public java.lang.String getBkResp () {
		return bkResp;
	}

	/**
	 * Set the value related to the column: bk_resp
	 * @param bkResp the bk_resp value
	 */
	public void setBkResp (java.lang.String bkResp) {
		this.bkResp = bkResp;
	}



	/**
	 * Return the value associated with the column: mobile_no
	 */
	public java.lang.String getMobileNo () {
		return mobileNo;
	}

	/**
	 * Set the value related to the column: mobile_no
	 * @param mobileNo the mobile_no value
	 */
	public void setMobileNo (java.lang.String mobileNo) {
		this.mobileNo = mobileNo;
	}



	/**
	 * Return the value associated with the column: trans_period
	 */
	public java.lang.Integer getTransPeriod () {
		return transPeriod;
	}

	/**
	 * Set the value related to the column: trans_period
	 * @param transPeriod the trans_period value
	 */
	public void setTransPeriod (java.lang.Integer transPeriod) {
		this.transPeriod = transPeriod;
	}



	/**
	 * Return the value associated with the column: card_no
	 */
	public java.lang.String getCardNo () {
		return cardNo;
	}

	/**
	 * Set the value related to the column: card_no
	 * @param cardNo the card_no value
	 */
	public void setCardNo (java.lang.String cardNo) {
		this.cardNo = cardNo;
	}



	/**
	 * Return the value associated with the column: error_code
	 */
	public java.lang.String getErrorCode () {
		return errorCode;
	}

	/**
	 * Set the value related to the column: error_code
	 * @param errorCode the error_code value
	 */
	public void setErrorCode (java.lang.String errorCode) {
		this.errorCode = errorCode;
	}



	/**
	 * Return the value associated with the column: author_type
	 */
	public java.lang.Integer getAuthorType () {
		return authorType;
	}

	/**
	 * Set the value related to the column: author_type
	 * @param authorType the author_type value
	 */
	public void setAuthorType (java.lang.Integer authorType) {
		this.authorType = authorType;
	}



	/**
	 * Return the value associated with the column: phone_no
	 */
	public java.lang.String getPhoneNo () {
		return phoneNo;
	}

	/**
	 * Set the value related to the column: phone_no
	 * @param phoneNo the phone_no value
	 */
	public void setPhoneNo (java.lang.String phoneNo) {
		this.phoneNo = phoneNo;
	}



	/**
	 * Return the value associated with the column: oper_id
	 */
	public java.lang.Integer getOperId () {
		return operId;
	}

	/**
	 * Set the value related to the column: oper_id
	 * @param operId the oper_id value
	 */
	public void setOperId (java.lang.Integer operId) {
		this.operId = operId;
	}



	/**
	 * Return the value associated with the column: gid
	 */
	public java.lang.Integer getGid () {
		return gid;
	}

	/**
	 * Set the value related to the column: gid
	 * @param gid the gid value
	 */
	public void setGid (java.lang.Integer gid) {
		this.gid = gid;
	}



	/**
	 * Return the value associated with the column: pre_amt
	 */
	public java.lang.Integer getPreAmt () {
		return preAmt;
	}

	/**
	 * Set the value related to the column: pre_amt
	 * @param preAmt the pre_amt value
	 */
	public void setPreAmt (java.lang.Integer preAmt) {
		this.preAmt = preAmt;
	}



	/**
	 * Return the value associated with the column: bk_fee_model
	 */
	public java.lang.String getBkFeeModel () {
		return bkFeeModel;
	}

	/**
	 * Set the value related to the column: bk_fee_model
	 * @param bkFeeModel the bk_fee_model value
	 */
	public void setBkFeeModel (java.lang.String bkFeeModel) {
		this.bkFeeModel = bkFeeModel;
	}



	/**
	 * Return the value associated with the column: pre_amt1
	 */
	public java.lang.Integer getPreAmt1 () {
		return preAmt1;
	}

	/**
	 * Set the value related to the column: pre_amt1
	 * @param preAmt1 the pre_amt1 value
	 */
	public void setPreAmt1 (java.lang.Integer preAmt1) {
		this.preAmt1 = preAmt1;
	}



	/**
	 * Return the value associated with the column: error_msg
	 */
	public java.lang.String getErrorMsg () {
		return errorMsg;
	}

	/**
	 * Set the value related to the column: error_msg
	 * @param errorMsg the error_msg value
	 */
	public void setErrorMsg (java.lang.String errorMsg) {
		this.errorMsg = errorMsg;
	}



	/**
	 * Return the value associated with the column: p1
	 */
	public java.lang.String getP1 () {
		return p1;
	}

	/**
	 * Set the value related to the column: p1
	 * @param p1 the p1 value
	 */
	public void setP1 (java.lang.String p1) {
		this.p1 = p1;
	}



	/**
	 * Return the value associated with the column: p2
	 */
	public java.lang.String getP2 () {
		return p2;
	}

	/**
	 * Set the value related to the column: p2
	 * @param p2 the p2 value
	 */
	public void setP2 (java.lang.String p2) {
		this.p2 = p2;
	}



	/**
	 * Return the value associated with the column: p3
	 */
	public java.lang.String getP3 () {
		return p3;
	}

	/**
	 * Set the value related to the column: p3
	 * @param p3 the p3 value
	 */
	public void setP3 (java.lang.String p3) {
		this.p3 = p3;
	}



	/**
	 * Return the value associated with the column: p4
	 */
	public java.lang.String getP4 () {
		return p4;
	}

	/**
	 * Set the value related to the column: p4
	 * @param p4 the p4 value
	 */
	public void setP4 (java.lang.String p4) {
		this.p4 = p4;
	}



	/**
	 * Return the value associated with the column: p5
	 */
	public java.lang.String getP5 () {
		return p5;
	}

	/**
	 * Set the value related to the column: p5
	 * @param p5 the p5 value
	 */
	public void setP5 (java.lang.String p5) {
		this.p5 = p5;
	}



	/**
	 * Return the value associated with the column: p6
	 */
	public java.lang.String getP6 () {
		return p6;
	}

	/**
	 * Set the value related to the column: p6
	 * @param p6 the p6 value
	 */
	public void setP6 (java.lang.String p6) {
		this.p6 = p6;
	}



	/**
	 * Return the value associated with the column: p7
	 */
	public java.lang.String getP7 () {
		return p7;
	}

	/**
	 * Set the value related to the column: p7
	 * @param p7 the p7 value
	 */
	public void setP7 (java.lang.String p7) {
		this.p7 = p7;
	}



	/**
	 * Return the value associated with the column: p8
	 */
	public java.lang.String getP8 () {
		return p8;
	}

	/**
	 * Set the value related to the column: p8
	 * @param p8 the p8 value
	 */
	public void setP8 (java.lang.String p8) {
		this.p8 = p8;
	}



	/**
	 * Return the value associated with the column: p9
	 */
	public java.lang.String getP9 () {
		return p9;
	}

	/**
	 * Set the value related to the column: p9
	 * @param p9 the p9 value
	 */
	public void setP9 (java.lang.String p9) {
		this.p9 = p9;
	}



	/**
	 * Return the value associated with the column: p10
	 */
	public java.lang.String getP10 () {
		return p10;
	}

	/**
	 * Set the value related to the column: p10
	 * @param p10 the p10 value
	 */
	public void setP10 (java.lang.String p10) {
		this.p10 = p10;
	}



	/**
	 * Return the value associated with the column: p11
	 */
	public java.lang.String getP11 () {
		return p11;
	}

	/**
	 * Set the value related to the column: p11
	 * @param p11 the p11 value
	 */
	public void setP11 (java.lang.String p11) {
		this.p11 = p11;
	}



	/**
	 * Return the value associated with the column: p12
	 */
	public java.lang.String getP12 () {
		return p12;
	}

	/**
	 * Set the value related to the column: p12
	 * @param p12 the p12 value
	 */
	public void setP12 (java.lang.String p12) {
		this.p12 = p12;
	}



	/**
	 * Return the value associated with the column: p13
	 */
	public java.lang.String getP13 () {
		return p13;
	}

	/**
	 * Set the value related to the column: p13
	 * @param p13 the p13 value
	 */
	public void setP13 (java.lang.String p13) {
		this.p13 = p13;
	}



	/**
	 * Return the value associated with the column: p14
	 */
	public java.lang.String getP14 () {
		return p14;
	}

	/**
	 * Set the value related to the column: p14
	 * @param p14 the p14 value
	 */
	public void setP14 (java.lang.String p14) {
		this.p14 = p14;
	}



	/**
	 * Return the value associated with the column: p15
	 */
	public java.lang.String getP15 () {
		return p15;
	}

	/**
	 * Set the value related to the column: p15
	 * @param p15 the p15 value
	 */
	public void setP15 (java.lang.String p15) {
		this.p15 = p15;
	}



	/**
	 * Return the value associated with the column: is_liq
	 */
	public java.lang.Byte getIsLiq () {
		return isLiq;
	}

	/**
	 * Set the value related to the column: is_liq
	 * @param isLiq the is_liq value
	 */
	public void setIsLiq (java.lang.Byte isLiq) {
		this.isLiq = isLiq;
	}



	/**
	 * Return the value associated with the column: is_notice
	 */
	public java.lang.Byte getIsNotice () {
		return isNotice;
	}

	/**
	 * Set the value related to the column: is_notice
	 * @param isNotice the is_notice value
	 */
	public void setIsNotice (java.lang.Byte isNotice) {
		this.isNotice = isNotice;
	}



	/**
	 * Return the value associated with the column: data_source
	 */
	public java.lang.Byte getDataSource () {
		return dataSource;
	}

	/**
	 * Set the value related to the column: data_source
	 * @param dataSource the data_source value
	 */
	public void setDataSource (java.lang.Byte dataSource) {
		this.dataSource = dataSource;
	}



	/**
	 * Return the value associated with the column: currency
	 */
	public java.lang.String getCurrency () {
		return currency;
	}

	/**
	 * Set the value related to the column: currency
	 * @param currency the currency value
	 */
	public void setCurrency (java.lang.String currency) {
		this.currency = currency;
	}



	/**
	 * Return the value associated with the column: exchange_rate
	 */
	public java.math.BigDecimal getExchangeRate () {
		return exchangeRate;
	}

	/**
	 * Set the value related to the column: exchange_rate
	 * @param exchangeRate the exchange_rate value
	 */
	public void setExchangeRate (java.math.BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}



	/**
	 * Return the value associated with the column: againPay_date
	 */
	public java.lang.Integer getAgainpayDate () {
		return againpayDate;
	}

	/**
	 * Set the value related to the column: againPay_date
	 * @param againpayDate the againPay_date value
	 */
	public void setAgainpayDate (java.lang.Integer againpayDate) {
		this.againpayDate = againpayDate;
	}



	/**
	 * Return the value associated with the column: againPay_status
	 */
	public java.lang.Integer getAgainpayStatus () {
		return againpayStatus;
	}

	/**
	 * Set the value related to the column: againPay_status
	 * @param againpayStatus the againPay_status value
	 */
	public void setAgainpayStatus (java.lang.Integer againpayStatus) {
		this.againpayStatus = againpayStatus;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.RytGonghang)) return false;
		else {
			cn.com.chinaebi.dz.object.RytGonghang rytGonghang = (cn.com.chinaebi.dz.object.RytGonghang) obj;
			if (null == this.getId() || null == rytGonghang.getId()) return false;
			else return (this.getId().equals(rytGonghang.getId()));
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