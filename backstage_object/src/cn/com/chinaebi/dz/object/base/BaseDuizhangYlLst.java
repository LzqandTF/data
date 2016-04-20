package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_yl_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_yl_lst"
 */

public abstract class BaseDuizhangYlLst  implements Serializable {

	public static String REF = "DuizhangYlLst";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_INTERACT = "Interact";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_PAY_WAY = "PayWay";
	public static String PROP_TRADING_NO = "TradingNo";
	public static String PROP_MER_CODES = "MerCodes";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_MERCUSTOM = "Mercustom";
	public static String PROP_RAW_REQ_SYS_STANCES = "RawReqSysStances";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_SEND_CODE = "SendCode";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_MER_CATEGORY = "MerCategory";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_PREFERENTIAL_AMOUNT = "PreferentialAmount";
	public static String PROP_TRACKING_NO = "TrackingNo";
	public static String PROP_ACCOUNT_TYPE = "AccountType";
	public static String PROP_BILL_NO = "BillNo";
	public static String PROP_TERMINAL_NO = "TerminalNo";
	public static String PROP_RAW_REQ_SYS_STANCE = "RawReqSysStance";
	public static String PROP_RAW_REQ_TIME = "RawReqTime";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_BOOKED_WAY = "BookedWay";
	public static String PROP_BUSINESS_TYPE = "BusinessType";
	public static String PROP_NET_AMOUNT = "NetAmount";
	public static String PROP_ORDER_ID = "OrderId";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_TRADING_AS_TYPE = "TradingAsType";
	public static String PROP_TRADING_TYPE = "TradingType";
	public static String PROP_AGENT_CODE = "AgentCode";
	public static String PROP_SETTLEMENT_AMOUNT = "SettlementAmount";
	public static String PROP_INVOICE_AMOUNT = "InvoiceAmount";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_TWO_MER_ABBREVIATION = "TwoMerAbbreviation";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_TERMINAL_TYPE = "TerminalType";
	public static String PROP_TWO_MERCODE = "TwoMercode";
	public static String PROP_TWO_MER_AMOUNT = "TwoMerAmount";
	public static String PROP_PAT_TYPE = "PatType";
	public static String PROP_ID = "Id";
	public static String PROP_KEEP = "Keep";
	public static String PROP_RAW_PAY_WAY = "RawPayWay";
	public static String PROP_BILL_TYPE = "BillType";


	// constructors
	public BaseDuizhangYlLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangYlLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String tradingNo;
	private java.lang.String agentCode;
	private java.lang.String sendCode;
	private java.lang.String trackingNo;
	private java.lang.String reqTime;
	private java.lang.String outAccount;
	private java.lang.String tradeAmount;
	private java.lang.String merCategory;
	private java.lang.String terminalType;
	private java.lang.String reqSysStance;
	private java.lang.String rawPayWay;
	private java.lang.String orderId;
	private java.lang.String patType;
	private java.lang.String rawReqSysStance;
	private java.lang.String rawReqTime;
	private java.lang.String tradeFee;
	private java.lang.String settlementAmount;
	private java.lang.String payWay;
	private java.lang.String merCode;
	private java.lang.String tradingType;
	private java.lang.String tradingAsType;
	private java.lang.String businessType;
	private java.lang.String accountType;
	private java.lang.String billType;
	private java.lang.String billNo;
	private java.lang.String interact;
	private java.lang.String rawReqSysStances;
	private java.lang.String merCodes;
	private java.lang.String bookedWay;
	private java.lang.String twoMercode;
	private java.lang.String twoMerAbbreviation;
	private java.lang.String twoMerAmount;
	private java.lang.String netAmount;
	private java.lang.String terminalNo;
	private java.lang.String mercustom;
	private java.lang.String preferentialAmount;
	private java.lang.String invoiceAmount;
	private java.lang.String keep;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.Integer bkChk;
	private java.lang.String deductStlmDate;
	private boolean whetherTk;
	private java.lang.String process;
	private java.lang.String termId;
	private java.lang.String deductSysReference;



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
	 * Return the value associated with the column: tradingNo
	 */
	public java.lang.String getTradingNo () {
		return tradingNo;
	}

	/**
	 * Set the value related to the column: tradingNo
	 * @param tradingNo the tradingNo value
	 */
	public void setTradingNo (java.lang.String tradingNo) {
		this.tradingNo = tradingNo;
	}



	/**
	 * Return the value associated with the column: agentCode
	 */
	public java.lang.String getAgentCode () {
		return agentCode;
	}

	/**
	 * Set the value related to the column: agentCode
	 * @param agentCode the agentCode value
	 */
	public void setAgentCode (java.lang.String agentCode) {
		this.agentCode = agentCode;
	}



	/**
	 * Return the value associated with the column: sendCode
	 */
	public java.lang.String getSendCode () {
		return sendCode;
	}

	/**
	 * Set the value related to the column: sendCode
	 * @param sendCode the sendCode value
	 */
	public void setSendCode (java.lang.String sendCode) {
		this.sendCode = sendCode;
	}



	/**
	 * Return the value associated with the column: trackingNo
	 */
	public java.lang.String getTrackingNo () {
		return trackingNo;
	}

	/**
	 * Set the value related to the column: trackingNo
	 * @param trackingNo the trackingNo value
	 */
	public void setTrackingNo (java.lang.String trackingNo) {
		this.trackingNo = trackingNo;
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
	 * Return the value associated with the column: merCategory
	 */
	public java.lang.String getMerCategory () {
		return merCategory;
	}

	/**
	 * Set the value related to the column: merCategory
	 * @param merCategory the merCategory value
	 */
	public void setMerCategory (java.lang.String merCategory) {
		this.merCategory = merCategory;
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
	 * Return the value associated with the column: rawPayWay
	 */
	public java.lang.String getRawPayWay () {
		return rawPayWay;
	}

	/**
	 * Set the value related to the column: rawPayWay
	 * @param rawPayWay the rawPayWay value
	 */
	public void setRawPayWay (java.lang.String rawPayWay) {
		this.rawPayWay = rawPayWay;
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
	 * Return the value associated with the column: patType
	 */
	public java.lang.String getPatType () {
		return patType;
	}

	/**
	 * Set the value related to the column: patType
	 * @param patType the patType value
	 */
	public void setPatType (java.lang.String patType) {
		this.patType = patType;
	}



	/**
	 * Return the value associated with the column: rawReqSysStance
	 */
	public java.lang.String getRawReqSysStance () {
		return rawReqSysStance;
	}

	/**
	 * Set the value related to the column: rawReqSysStance
	 * @param rawReqSysStance the rawReqSysStance value
	 */
	public void setRawReqSysStance (java.lang.String rawReqSysStance) {
		this.rawReqSysStance = rawReqSysStance;
	}



	/**
	 * Return the value associated with the column: rawReqTime
	 */
	public java.lang.String getRawReqTime () {
		return rawReqTime;
	}

	/**
	 * Set the value related to the column: rawReqTime
	 * @param rawReqTime the rawReqTime value
	 */
	public void setRawReqTime (java.lang.String rawReqTime) {
		this.rawReqTime = rawReqTime;
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
	 * Return the value associated with the column: payWay
	 */
	public java.lang.String getPayWay () {
		return payWay;
	}

	/**
	 * Set the value related to the column: payWay
	 * @param payWay the payWay value
	 */
	public void setPayWay (java.lang.String payWay) {
		this.payWay = payWay;
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
	 * Return the value associated with the column: tradingType
	 */
	public java.lang.String getTradingType () {
		return tradingType;
	}

	/**
	 * Set the value related to the column: tradingType
	 * @param tradingType the tradingType value
	 */
	public void setTradingType (java.lang.String tradingType) {
		this.tradingType = tradingType;
	}



	/**
	 * Return the value associated with the column: tradingAsType
	 */
	public java.lang.String getTradingAsType () {
		return tradingAsType;
	}

	/**
	 * Set the value related to the column: tradingAsType
	 * @param tradingAsType the tradingAsType value
	 */
	public void setTradingAsType (java.lang.String tradingAsType) {
		this.tradingAsType = tradingAsType;
	}



	/**
	 * Return the value associated with the column: businessType
	 */
	public java.lang.String getBusinessType () {
		return businessType;
	}

	/**
	 * Set the value related to the column: businessType
	 * @param businessType the businessType value
	 */
	public void setBusinessType (java.lang.String businessType) {
		this.businessType = businessType;
	}



	/**
	 * Return the value associated with the column: accountType
	 */
	public java.lang.String getAccountType () {
		return accountType;
	}

	/**
	 * Set the value related to the column: accountType
	 * @param accountType the accountType value
	 */
	public void setAccountType (java.lang.String accountType) {
		this.accountType = accountType;
	}



	/**
	 * Return the value associated with the column: billType
	 */
	public java.lang.String getBillType () {
		return billType;
	}

	/**
	 * Set the value related to the column: billType
	 * @param billType the billType value
	 */
	public void setBillType (java.lang.String billType) {
		this.billType = billType;
	}



	/**
	 * Return the value associated with the column: billNo
	 */
	public java.lang.String getBillNo () {
		return billNo;
	}

	/**
	 * Set the value related to the column: billNo
	 * @param billNo the billNo value
	 */
	public void setBillNo (java.lang.String billNo) {
		this.billNo = billNo;
	}



	/**
	 * Return the value associated with the column: interact
	 */
	public java.lang.String getInteract () {
		return interact;
	}

	/**
	 * Set the value related to the column: interact
	 * @param interact the interact value
	 */
	public void setInteract (java.lang.String interact) {
		this.interact = interact;
	}



	/**
	 * Return the value associated with the column: rawReqSysStances
	 */
	public java.lang.String getRawReqSysStances () {
		return rawReqSysStances;
	}

	/**
	 * Set the value related to the column: rawReqSysStances
	 * @param rawReqSysStances the rawReqSysStances value
	 */
	public void setRawReqSysStances (java.lang.String rawReqSysStances) {
		this.rawReqSysStances = rawReqSysStances;
	}



	/**
	 * Return the value associated with the column: merCodes
	 */
	public java.lang.String getMerCodes () {
		return merCodes;
	}

	/**
	 * Set the value related to the column: merCodes
	 * @param merCodes the merCodes value
	 */
	public void setMerCodes (java.lang.String merCodes) {
		this.merCodes = merCodes;
	}



	/**
	 * Return the value associated with the column: bookedWay
	 */
	public java.lang.String getBookedWay () {
		return bookedWay;
	}

	/**
	 * Set the value related to the column: bookedWay
	 * @param bookedWay the bookedWay value
	 */
	public void setBookedWay (java.lang.String bookedWay) {
		this.bookedWay = bookedWay;
	}



	/**
	 * Return the value associated with the column: twoMercode
	 */
	public java.lang.String getTwoMercode () {
		return twoMercode;
	}

	/**
	 * Set the value related to the column: twoMercode
	 * @param twoMercode the twoMercode value
	 */
	public void setTwoMercode (java.lang.String twoMercode) {
		this.twoMercode = twoMercode;
	}



	/**
	 * Return the value associated with the column: twoMerAbbreviation
	 */
	public java.lang.String getTwoMerAbbreviation () {
		return twoMerAbbreviation;
	}

	/**
	 * Set the value related to the column: twoMerAbbreviation
	 * @param twoMerAbbreviation the twoMerAbbreviation value
	 */
	public void setTwoMerAbbreviation (java.lang.String twoMerAbbreviation) {
		this.twoMerAbbreviation = twoMerAbbreviation;
	}



	/**
	 * Return the value associated with the column: twoMerAmount
	 */
	public java.lang.String getTwoMerAmount () {
		return twoMerAmount;
	}

	/**
	 * Set the value related to the column: twoMerAmount
	 * @param twoMerAmount the twoMerAmount value
	 */
	public void setTwoMerAmount (java.lang.String twoMerAmount) {
		this.twoMerAmount = twoMerAmount;
	}



	/**
	 * Return the value associated with the column: netAmount
	 */
	public java.lang.String getNetAmount () {
		return netAmount;
	}

	/**
	 * Set the value related to the column: netAmount
	 * @param netAmount the netAmount value
	 */
	public void setNetAmount (java.lang.String netAmount) {
		this.netAmount = netAmount;
	}



	/**
	 * Return the value associated with the column: terminalNo
	 */
	public java.lang.String getTerminalNo () {
		return terminalNo;
	}

	/**
	 * Set the value related to the column: terminalNo
	 * @param terminalNo the terminalNo value
	 */
	public void setTerminalNo (java.lang.String terminalNo) {
		this.terminalNo = terminalNo;
	}



	/**
	 * Return the value associated with the column: mercustom
	 */
	public java.lang.String getMercustom () {
		return mercustom;
	}

	/**
	 * Set the value related to the column: mercustom
	 * @param mercustom the mercustom value
	 */
	public void setMercustom (java.lang.String mercustom) {
		this.mercustom = mercustom;
	}



	/**
	 * Return the value associated with the column: preferentialAmount
	 */
	public java.lang.String getPreferentialAmount () {
		return preferentialAmount;
	}

	/**
	 * Set the value related to the column: preferentialAmount
	 * @param preferentialAmount the preferentialAmount value
	 */
	public void setPreferentialAmount (java.lang.String preferentialAmount) {
		this.preferentialAmount = preferentialAmount;
	}



	/**
	 * Return the value associated with the column: invoiceAmount
	 */
	public java.lang.String getInvoiceAmount () {
		return invoiceAmount;
	}

	/**
	 * Set the value related to the column: invoiceAmount
	 * @param invoiceAmount the invoiceAmount value
	 */
	public void setInvoiceAmount (java.lang.String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}



	/**
	 * Return the value associated with the column: keep
	 */
	public java.lang.String getKeep () {
		return keep;
	}

	/**
	 * Set the value related to the column: keep
	 * @param keep the keep value
	 */
	public void setKeep (java.lang.String keep) {
		this.keep = keep;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangYlLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangYlLst duizhangYlLst = (cn.com.chinaebi.dz.object.DuizhangYlLst) obj;
			if (null == this.getId() || null == duizhangYlLst.getId()) return false;
			else return (this.getId().equals(duizhangYlLst.getId()));
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