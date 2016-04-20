package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_kuaiqian_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_kuaiqian_lst"
 */

public abstract class BaseDuizhangKuaiqianLst  implements Serializable {

	public static String REF = "DuizhangKuaiqianLst";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_TRADE_FEE = "TradeFee";
	public static String PROP_TERM_NAME = "TermName";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_BK_CHK = "BkChk";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_DEDUCT_SYS_REFERENCE = "DeductSysReference";
	public static String PROP_PROCESS = "Process";
	public static String PROP_INSTITUTIONS_NO = "InstitutionsNo";
	public static String PROP_WHETHER_TK = "WhetherTk";
	public static String PROP_CARD_TYPE = "CardType";
	public static String PROP_TERM_REQ_TIME = "TermReqTime";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_TERM_ID = "TermId";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_TERM_PAPER_NO = "TermPaperNo";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_ORDER_ID = "OrderId";
	public static String PROP_TRADING_STATUS = "TradingStatus";
	public static String PROP_TRADING_TYPE = "TradingType";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_ID = "Id";
	public static String PROP_TRADE_NO = "TradeNo";
	public static String PROP_THE_MODEL = "TheModel";


	// constructors
	public BaseDuizhangKuaiqianLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangKuaiqianLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String tradeNo;
	private java.lang.String merName;
	private java.lang.String termPaperNo;
	private java.lang.String termName;
	private java.lang.String outAccount;
	private java.lang.String institutionsNo;
	private java.lang.String tradeAmount;
	private java.lang.String tradeFee;
	private java.lang.String reqTime;
	private java.lang.String termReqTime;
	private java.lang.String reqSysStance;
	private java.lang.String cardType;
	private java.lang.String tradingStatus;
	private java.lang.String tradingType;
	private java.lang.String theModel;
	private java.lang.String dzFileName;
	private java.lang.String instName;
	private java.lang.Integer bkChk;
	private java.lang.String deductStlmDate;
	private java.lang.String deductSysReference;
	private java.lang.String termId;
	private java.lang.String merCode;
	private java.lang.String process;
	private boolean whetherTk;
	private java.lang.String orderId;



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
	 * Return the value associated with the column: tradeNo
	 */
	public java.lang.String getTradeNo () {
		return tradeNo;
	}

	/**
	 * Set the value related to the column: tradeNo
	 * @param tradeNo the tradeNo value
	 */
	public void setTradeNo (java.lang.String tradeNo) {
		this.tradeNo = tradeNo;
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
	 * Return the value associated with the column: term_paper_no
	 */
	public java.lang.String getTermPaperNo () {
		return termPaperNo;
	}

	/**
	 * Set the value related to the column: term_paper_no
	 * @param termPaperNo the term_paper_no value
	 */
	public void setTermPaperNo (java.lang.String termPaperNo) {
		this.termPaperNo = termPaperNo;
	}



	/**
	 * Return the value associated with the column: term_name
	 */
	public java.lang.String getTermName () {
		return termName;
	}

	/**
	 * Set the value related to the column: term_name
	 * @param termName the term_name value
	 */
	public void setTermName (java.lang.String termName) {
		this.termName = termName;
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
	 * Return the value associated with the column: institutions_no
	 */
	public java.lang.String getInstitutionsNo () {
		return institutionsNo;
	}

	/**
	 * Set the value related to the column: institutions_no
	 * @param institutionsNo the institutions_no value
	 */
	public void setInstitutionsNo (java.lang.String institutionsNo) {
		this.institutionsNo = institutionsNo;
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
	 * Return the value associated with the column: term_req_time
	 */
	public java.lang.String getTermReqTime () {
		return termReqTime;
	}

	/**
	 * Set the value related to the column: term_req_time
	 * @param termReqTime the term_req_time value
	 */
	public void setTermReqTime (java.lang.String termReqTime) {
		this.termReqTime = termReqTime;
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
	 * Return the value associated with the column: card_type
	 */
	public java.lang.String getCardType () {
		return cardType;
	}

	/**
	 * Set the value related to the column: card_type
	 * @param cardType the card_type value
	 */
	public void setCardType (java.lang.String cardType) {
		this.cardType = cardType;
	}



	/**
	 * Return the value associated with the column: trading_status
	 */
	public java.lang.String getTradingStatus () {
		return tradingStatus;
	}

	/**
	 * Set the value related to the column: trading_status
	 * @param tradingStatus the trading_status value
	 */
	public void setTradingStatus (java.lang.String tradingStatus) {
		this.tradingStatus = tradingStatus;
	}



	/**
	 * Return the value associated with the column: trading_type
	 */
	public java.lang.String getTradingType () {
		return tradingType;
	}

	/**
	 * Set the value related to the column: trading_type
	 * @param tradingType the trading_type value
	 */
	public void setTradingType (java.lang.String tradingType) {
		this.tradingType = tradingType;
	}



	/**
	 * Return the value associated with the column: the_model
	 */
	public java.lang.String getTheModel () {
		return theModel;
	}

	/**
	 * Set the value related to the column: the_model
	 * @param theModel the the_model value
	 */
	public void setTheModel (java.lang.String theModel) {
		this.theModel = theModel;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangKuaiqianLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangKuaiqianLst duizhangKuaiqianLst = (cn.com.chinaebi.dz.object.DuizhangKuaiqianLst) obj;
			if (null == this.getId() || null == duizhangKuaiqianLst.getId()) return false;
			else return (this.getId().equals(duizhangKuaiqianLst.getId()));
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