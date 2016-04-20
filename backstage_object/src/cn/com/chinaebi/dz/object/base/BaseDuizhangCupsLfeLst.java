package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the duizhang_cups_lfe_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="duizhang_cups_lfe_lst"
 */

public abstract class BaseDuizhangCupsLfeLst  implements Serializable {

	public static String REF = "DuizhangCupsLfeLst";
	public static String PROP_IN_ACCOUNT = "InAccount";
	public static String PROP_FORWARDING_CODE = "ForwardingCode";
	public static String PROP_TERM_CODE = "TermCode";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_WHETHER_YL_CARD = "WhetherYlCard";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_CARD_NO = "CardNo";
	public static String PROP_CARD_IDENTIFICATION_CODE = "CardIdentificationCode";
	public static String PROP_CARD_ISSUER = "CardIssuer";
	public static String PROP_DEDUCT_SYS_REFENCE = "DeductSysRefence";
	public static String PROP_REQ_TIME = "ReqTime";
	public static String PROP_TRADE_START_TYPE = "TradeStartType";
	public static String PROP_BANK_CODE_ONE = "BankCodeOne";
	public static String PROP_PROCESS = "Process";
	public static String PROP_SERVER_POINT_CODE = "ServerPointCode";
	public static String PROP_CARD_TYPE = "CardType";
	public static String PROP_TRADE_AREA = "TradeArea";
	public static String PROP_RECEIVING_SINGLE_CODE = "ReceivingSingleCode";
	public static String PROP_BANK_CODE_TWO = "BankCodeTwo";
	public static String PROP_TERM_TYPE = "TermType";
	public static String PROP_OUT_ACCOUNT = "OutAccount";
	public static String PROP_NET_AMOUNT = "NetAmount";
	public static String PROP_ORI_TRADE_INFO = "OriTradeInfo";
	public static String PROP_ACCEPTANCE_CODE = "AcceptanceCode";
	public static String PROP_MER_TYPE = "MerType";
	public static String PROP_SPARE_TWO = "SpareTwo";
	public static String PROP_SPARE_ONE = "SpareOne";
	public static String PROP_CARD_ISSUER_CODE = "CardIssuerCode";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_BRAND_SERVICE_FEE = "BrandServiceFee";
	public static String PROP_REQ_SYS_STANCE = "ReqSysStance";
	public static String PROP_CARD_MEDIA = "CardMedia";
	public static String PROP_ID = "Id";
	public static String PROP_MESSAGE_TYPE = "MessageType";
	public static String PROP_RECEIVING_CODE = "ReceivingCode";


	// constructors
	public BaseDuizhangCupsLfeLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDuizhangCupsLfeLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String deductStlmDate;
	private java.lang.String bankCodeOne;
	private java.lang.Byte whetherYlCard;
	private java.lang.String cardType;
	private java.lang.String termType;
	private java.lang.Integer cardMedia;
	private java.lang.Integer tradeArea;
	private java.lang.String merType;
	private java.lang.String bankCodeTwo;
	private java.lang.String acceptanceCode;
	private java.lang.String forwardingCode;
	private java.lang.String cardIssuerCode;
	private java.lang.String receivingCode;
	private java.lang.String receivingSingleCode;
	private java.lang.String messageType;
	private java.lang.String process;
	private java.lang.String serverPointCode;
	private java.lang.String reqSysStance;
	private java.lang.String reqTime;
	private java.lang.String cardNo;
	private java.lang.String outAccount;
	private java.lang.String inAccount;
	private java.lang.String oriTradeInfo;
	private java.lang.String termCode;
	private java.lang.String cardIdentificationCode;
	private java.lang.String deductSysRefence;
	private java.lang.String tradeAmount;
	private java.lang.String cardIssuer;
	private java.lang.String brandServiceFee;
	private java.lang.String spareOne;
	private java.lang.String netAmount;
	private java.lang.String tradeStartType;
	private java.lang.String spareTwo;
	private java.lang.String dzFileName;



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
	 * Return the value associated with the column: bank_code_one
	 */
	public java.lang.String getBankCodeOne () {
		return bankCodeOne;
	}

	/**
	 * Set the value related to the column: bank_code_one
	 * @param bankCodeOne the bank_code_one value
	 */
	public void setBankCodeOne (java.lang.String bankCodeOne) {
		this.bankCodeOne = bankCodeOne;
	}



	/**
	 * Return the value associated with the column: whether_yl_card
	 */
	public java.lang.Byte getWhetherYlCard () {
		return whetherYlCard;
	}

	/**
	 * Set the value related to the column: whether_yl_card
	 * @param whetherYlCard the whether_yl_card value
	 */
	public void setWhetherYlCard (java.lang.Byte whetherYlCard) {
		this.whetherYlCard = whetherYlCard;
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
	 * Return the value associated with the column: term_type
	 */
	public java.lang.String getTermType () {
		return termType;
	}

	/**
	 * Set the value related to the column: term_type
	 * @param termType the term_type value
	 */
	public void setTermType (java.lang.String termType) {
		this.termType = termType;
	}



	/**
	 * Return the value associated with the column: card_media
	 */
	public java.lang.Integer getCardMedia () {
		return cardMedia;
	}

	/**
	 * Set the value related to the column: card_media
	 * @param cardMedia the card_media value
	 */
	public void setCardMedia (java.lang.Integer cardMedia) {
		this.cardMedia = cardMedia;
	}



	/**
	 * Return the value associated with the column: trade_area
	 */
	public java.lang.Integer getTradeArea () {
		return tradeArea;
	}

	/**
	 * Set the value related to the column: trade_area
	 * @param tradeArea the trade_area value
	 */
	public void setTradeArea (java.lang.Integer tradeArea) {
		this.tradeArea = tradeArea;
	}



	/**
	 * Return the value associated with the column: mer_type
	 */
	public java.lang.String getMerType () {
		return merType;
	}

	/**
	 * Set the value related to the column: mer_type
	 * @param merType the mer_type value
	 */
	public void setMerType (java.lang.String merType) {
		this.merType = merType;
	}



	/**
	 * Return the value associated with the column: bank_code_two
	 */
	public java.lang.String getBankCodeTwo () {
		return bankCodeTwo;
	}

	/**
	 * Set the value related to the column: bank_code_two
	 * @param bankCodeTwo the bank_code_two value
	 */
	public void setBankCodeTwo (java.lang.String bankCodeTwo) {
		this.bankCodeTwo = bankCodeTwo;
	}



	/**
	 * Return the value associated with the column: acceptance_code
	 */
	public java.lang.String getAcceptanceCode () {
		return acceptanceCode;
	}

	/**
	 * Set the value related to the column: acceptance_code
	 * @param acceptanceCode the acceptance_code value
	 */
	public void setAcceptanceCode (java.lang.String acceptanceCode) {
		this.acceptanceCode = acceptanceCode;
	}



	/**
	 * Return the value associated with the column: forwarding_code
	 */
	public java.lang.String getForwardingCode () {
		return forwardingCode;
	}

	/**
	 * Set the value related to the column: forwarding_code
	 * @param forwardingCode the forwarding_code value
	 */
	public void setForwardingCode (java.lang.String forwardingCode) {
		this.forwardingCode = forwardingCode;
	}



	/**
	 * Return the value associated with the column: card_issuer_code
	 */
	public java.lang.String getCardIssuerCode () {
		return cardIssuerCode;
	}

	/**
	 * Set the value related to the column: card_issuer_code
	 * @param cardIssuerCode the card_issuer_code value
	 */
	public void setCardIssuerCode (java.lang.String cardIssuerCode) {
		this.cardIssuerCode = cardIssuerCode;
	}



	/**
	 * Return the value associated with the column: receiving_code
	 */
	public java.lang.String getReceivingCode () {
		return receivingCode;
	}

	/**
	 * Set the value related to the column: receiving_code
	 * @param receivingCode the receiving_code value
	 */
	public void setReceivingCode (java.lang.String receivingCode) {
		this.receivingCode = receivingCode;
	}



	/**
	 * Return the value associated with the column: receiving_single_code
	 */
	public java.lang.String getReceivingSingleCode () {
		return receivingSingleCode;
	}

	/**
	 * Set the value related to the column: receiving_single_code
	 * @param receivingSingleCode the receiving_single_code value
	 */
	public void setReceivingSingleCode (java.lang.String receivingSingleCode) {
		this.receivingSingleCode = receivingSingleCode;
	}



	/**
	 * Return the value associated with the column: message_type
	 */
	public java.lang.String getMessageType () {
		return messageType;
	}

	/**
	 * Set the value related to the column: message_type
	 * @param messageType the message_type value
	 */
	public void setMessageType (java.lang.String messageType) {
		this.messageType = messageType;
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
	 * Return the value associated with the column: server_point_code
	 */
	public java.lang.String getServerPointCode () {
		return serverPointCode;
	}

	/**
	 * Set the value related to the column: server_point_code
	 * @param serverPointCode the server_point_code value
	 */
	public void setServerPointCode (java.lang.String serverPointCode) {
		this.serverPointCode = serverPointCode;
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
	 * Return the value associated with the column: cardNo
	 */
	public java.lang.String getCardNo () {
		return cardNo;
	}

	/**
	 * Set the value related to the column: cardNo
	 * @param cardNo the cardNo value
	 */
	public void setCardNo (java.lang.String cardNo) {
		this.cardNo = cardNo;
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
	 * Return the value associated with the column: inAccount
	 */
	public java.lang.String getInAccount () {
		return inAccount;
	}

	/**
	 * Set the value related to the column: inAccount
	 * @param inAccount the inAccount value
	 */
	public void setInAccount (java.lang.String inAccount) {
		this.inAccount = inAccount;
	}



	/**
	 * Return the value associated with the column: ori_trade_info
	 */
	public java.lang.String getOriTradeInfo () {
		return oriTradeInfo;
	}

	/**
	 * Set the value related to the column: ori_trade_info
	 * @param oriTradeInfo the ori_trade_info value
	 */
	public void setOriTradeInfo (java.lang.String oriTradeInfo) {
		this.oriTradeInfo = oriTradeInfo;
	}



	/**
	 * Return the value associated with the column: term_code
	 */
	public java.lang.String getTermCode () {
		return termCode;
	}

	/**
	 * Set the value related to the column: term_code
	 * @param termCode the term_code value
	 */
	public void setTermCode (java.lang.String termCode) {
		this.termCode = termCode;
	}



	/**
	 * Return the value associated with the column: card_identification_code
	 */
	public java.lang.String getCardIdentificationCode () {
		return cardIdentificationCode;
	}

	/**
	 * Set the value related to the column: card_identification_code
	 * @param cardIdentificationCode the card_identification_code value
	 */
	public void setCardIdentificationCode (java.lang.String cardIdentificationCode) {
		this.cardIdentificationCode = cardIdentificationCode;
	}



	/**
	 * Return the value associated with the column: deduct_sys_refence
	 */
	public java.lang.String getDeductSysRefence () {
		return deductSysRefence;
	}

	/**
	 * Set the value related to the column: deduct_sys_refence
	 * @param deductSysRefence the deduct_sys_refence value
	 */
	public void setDeductSysRefence (java.lang.String deductSysRefence) {
		this.deductSysRefence = deductSysRefence;
	}



	/**
	 * Return the value associated with the column: trade_amount
	 */
	public java.lang.String getTradeAmount () {
		return tradeAmount;
	}

	/**
	 * Set the value related to the column: trade_amount
	 * @param tradeAmount the trade_amount value
	 */
	public void setTradeAmount (java.lang.String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}



	/**
	 * Return the value associated with the column: card_issuer
	 */
	public java.lang.String getCardIssuer () {
		return cardIssuer;
	}

	/**
	 * Set the value related to the column: card_issuer
	 * @param cardIssuer the card_issuer value
	 */
	public void setCardIssuer (java.lang.String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}



	/**
	 * Return the value associated with the column: brand_service_fee
	 */
	public java.lang.String getBrandServiceFee () {
		return brandServiceFee;
	}

	/**
	 * Set the value related to the column: brand_service_fee
	 * @param brandServiceFee the brand_service_fee value
	 */
	public void setBrandServiceFee (java.lang.String brandServiceFee) {
		this.brandServiceFee = brandServiceFee;
	}



	/**
	 * Return the value associated with the column: spare_one
	 */
	public java.lang.String getSpareOne () {
		return spareOne;
	}

	/**
	 * Set the value related to the column: spare_one
	 * @param spareOne the spare_one value
	 */
	public void setSpareOne (java.lang.String spareOne) {
		this.spareOne = spareOne;
	}



	/**
	 * Return the value associated with the column: net_amount
	 */
	public java.lang.String getNetAmount () {
		return netAmount;
	}

	/**
	 * Set the value related to the column: net_amount
	 * @param netAmount the net_amount value
	 */
	public void setNetAmount (java.lang.String netAmount) {
		this.netAmount = netAmount;
	}



	/**
	 * Return the value associated with the column: trade_start_type
	 */
	public java.lang.String getTradeStartType () {
		return tradeStartType;
	}

	/**
	 * Set the value related to the column: trade_start_type
	 * @param tradeStartType the trade_start_type value
	 */
	public void setTradeStartType (java.lang.String tradeStartType) {
		this.tradeStartType = tradeStartType;
	}



	/**
	 * Return the value associated with the column: spare_two
	 */
	public java.lang.String getSpareTwo () {
		return spareTwo;
	}

	/**
	 * Set the value related to the column: spare_two
	 * @param spareTwo the spare_two value
	 */
	public void setSpareTwo (java.lang.String spareTwo) {
		this.spareTwo = spareTwo;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DuizhangCupsLfeLst)) return false;
		else {
			cn.com.chinaebi.dz.object.DuizhangCupsLfeLst duizhangCupsLfeLst = (cn.com.chinaebi.dz.object.DuizhangCupsLfeLst) obj;
			if (null == this.getId() || null == duizhangCupsLfeLst.getId()) return false;
			else return (this.getId().equals(duizhangCupsLfeLst.getId()));
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