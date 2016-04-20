package com.chinaebi.entity;

import java.io.Serializable;

public class DuizhangErrorData implements Serializable {
	private static final long serialVersionUID = 5781306218703599004L;

	private String id;
	private String error_trade_flag;
	private String acqInstIdCode;
	private String fwdInstIdCode;
	private String reqSysStance;
	private String reqTime;
	private String outAccount;
	private String tradeAccount;
	private String msgType;
	private String process;
	private String merType;
	private String termId;
	private String deductSysReference;
	private String reqType;
	private String authorizationCode;
	private String rcvgInstIdCode;
	private String issuingBankCode;
	private String origDataStance;
	private String deductSysResponse;
	private String reqInputType;
	private String acceptorReceiveFee;
	private String acceptorPayFee;
	private String byStagesFee;
	private String tradeFee;
	private String errorTradePayFee;
	private String errorTradeReceiveFee;
	private String error_info;
	private String ReceiveRollOutCode;
	private String accountIdentification;
	private String intoInstCode;
	private String onTradeTime;
	private String cardNumber;
	private String termReadAbility;
	private String idConditionCode;
	private String onDeduct_stlm_date;
	private String onTradeAccount;
	private String tradeAdress;
	private String eci;
	private String whetherErroeHandle;
	private String dz_file_name;
	private String inst_name;
	private String merchant_code;
	private String sender_clearing_organizations;
	private String recipient_clearing_organization;
	private String transferee_clearing_organizations;
	private String before_transation_ter_type;
	private String merchant_name_address;
	private String special_billing_type;
	private String special_charges_grade;
	private String tac_logo;
	private String card_product_info;
	private String tran_code_caused_error;
	private String tran_initiated_method;
	private String account_settle_type;
	private String reserved_for_use;
	private int bk_chk;
	//以上是duizhang_error_cups_lst、duizhang_error_upmp_lst公共字段
	private String deduct_stlm_date;//duizhang_error_upmp_lst中的字段
	
	private String tradeType;
	private String handling_name;
	private String reason_desc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getError_trade_flag() {
		return error_trade_flag;
	}
	public void setError_trade_flag(String error_trade_flag) {
		this.error_trade_flag = error_trade_flag;
	}
	public String getAcqInstIdCode() {
		return acqInstIdCode;
	}
	public void setAcqInstIdCode(String acqInstIdCode) {
		this.acqInstIdCode = acqInstIdCode;
	}
	public String getFwdInstIdCode() {
		return fwdInstIdCode;
	}
	public void setFwdInstIdCode(String fwdInstIdCode) {
		this.fwdInstIdCode = fwdInstIdCode;
	}
	public String getReqSysStance() {
		return reqSysStance;
	}
	public void setReqSysStance(String reqSysStance) {
		this.reqSysStance = reqSysStance;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getOutAccount() {
		return outAccount;
	}
	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}
	public String getTradeAccount() {
		return String.valueOf(Double.valueOf(tradeAccount)/100);
	}
	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getMerType() {
		return merType;
	}
	public void setMerType(String merType) {
		this.merType = merType;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getDeductSysReference() {
		return deductSysReference;
	}
	public void setDeductSysReference(String deductSysReference) {
		this.deductSysReference = deductSysReference;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getRcvgInstIdCode() {
		return rcvgInstIdCode;
	}
	public void setRcvgInstIdCode(String rcvgInstIdCode) {
		this.rcvgInstIdCode = rcvgInstIdCode;
	}
	public String getIssuingBankCode() {
		return issuingBankCode;
	}
	public void setIssuingBankCode(String issuingBankCode) {
		this.issuingBankCode = issuingBankCode;
	}
	public String getOrigDataStance() {
		return origDataStance;
	}
	public void setOrigDataStance(String origDataStance) {
		this.origDataStance = origDataStance;
	}
	public String getDeductSysResponse() {
		return deductSysResponse;
	}
	public void setDeductSysResponse(String deductSysResponse) {
		this.deductSysResponse = deductSysResponse;
	}
	public String getReqInputType() {
		return reqInputType;
	}
	public void setReqInputType(String reqInputType) {
		this.reqInputType = reqInputType;
	}
	public String getAcceptorReceiveFee() {
		return acceptorReceiveFee;
	}
	public void setAcceptorReceiveFee(String acceptorReceiveFee) {
		this.acceptorReceiveFee = acceptorReceiveFee;
	}
	public String getAcceptorPayFee() {
		return acceptorPayFee;
	}
	public void setAcceptorPayFee(String acceptorPayFee) {
		this.acceptorPayFee = acceptorPayFee;
	}
	public String getByStagesFee() {
		return byStagesFee;
	}
	public void setByStagesFee(String byStagesFee) {
		this.byStagesFee = byStagesFee;
	}
	public String getTradeFee() {
		return tradeFee;
	}
	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee;
	}
	public String getErrorTradePayFee() {
		return errorTradePayFee;
	}
	public void setErrorTradePayFee(String errorTradePayFee) {
		this.errorTradePayFee = errorTradePayFee;
	}
	public String getErrorTradeReceiveFee() {
		return errorTradeReceiveFee;
	}
	public void setErrorTradeReceiveFee(String errorTradeReceiveFee) {
		this.errorTradeReceiveFee = errorTradeReceiveFee;
	}
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public String getReceiveRollOutCode() {
		return ReceiveRollOutCode;
	}
	public void setReceiveRollOutCode(String receiveRollOutCode) {
		ReceiveRollOutCode = receiveRollOutCode;
	}
	public String getAccountIdentification() {
		return accountIdentification;
	}
	public void setAccountIdentification(String accountIdentification) {
		this.accountIdentification = accountIdentification;
	}
	public String getIntoInstCode() {
		return intoInstCode;
	}
	public void setIntoInstCode(String intoInstCode) {
		this.intoInstCode = intoInstCode;
	}
	public String getOnTradeTime() {
		return onTradeTime;
	}
	public void setOnTradeTime(String onTradeTime) {
		this.onTradeTime = onTradeTime;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getTermReadAbility() {
		return termReadAbility;
	}
	public void setTermReadAbility(String termReadAbility) {
		this.termReadAbility = termReadAbility;
	}
	public String getIdConditionCode() {
		return idConditionCode;
	}
	public void setIdConditionCode(String idConditionCode) {
		this.idConditionCode = idConditionCode;
	}
	public String getOnDeduct_stlm_date() {
		return onDeduct_stlm_date;
	}
	public void setOnDeduct_stlm_date(String onDeduct_stlm_date) {
		this.onDeduct_stlm_date = onDeduct_stlm_date;
	}
	public String getOnTradeAccount() {
		return onTradeAccount;
	}
	public void setOnTradeAccount(String onTradeAccount) {
		this.onTradeAccount = onTradeAccount;
	}
	public String getTradeAdress() {
		return tradeAdress;
	}
	public void setTradeAdress(String tradeAdress) {
		this.tradeAdress = tradeAdress;
	}
	public String getEci() {
		return eci;
	}
	public void setEci(String eci) {
		this.eci = eci;
	}
	public String getWhetherErroeHandle() {
		return whetherErroeHandle;
	}
	public void setWhetherErroeHandle(String whetherErroeHandle) {
		this.whetherErroeHandle = whetherErroeHandle;
	}
	public int getBk_chk() {
		return bk_chk;
	}
	public void setBk_chk(int bk_chk) {
		this.bk_chk = bk_chk;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getReason_desc() {
		return reason_desc;
	}
	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}
	public String getInst_name() {
		return inst_name;
	}
	public void setInst_name(String inst_name) {
		this.inst_name = inst_name;
	}
	public String getDz_file_name() {
		return dz_file_name;
	}
	public void setDz_file_name(String dz_file_name) {
		this.dz_file_name = dz_file_name;
	}
	public String getMerchant_code() {
		return merchant_code;
	}
	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}
	public String getSender_clearing_organizations() {
		return sender_clearing_organizations;
	}
	public void setSender_clearing_organizations(
			String sender_clearing_organizations) {
		this.sender_clearing_organizations = sender_clearing_organizations;
	}
	public String getRecipient_clearing_organization() {
		return recipient_clearing_organization;
	}
	public void setRecipient_clearing_organization(
			String recipient_clearing_organization) {
		this.recipient_clearing_organization = recipient_clearing_organization;
	}
	public String getTransferee_clearing_organizations() {
		return transferee_clearing_organizations;
	}
	public void setTransferee_clearing_organizations(
			String transferee_clearing_organizations) {
		this.transferee_clearing_organizations = transferee_clearing_organizations;
	}
	public String getBefore_transation_ter_type() {
		return before_transation_ter_type;
	}
	public void setBefore_transation_ter_type(String before_transation_ter_type) {
		this.before_transation_ter_type = before_transation_ter_type;
	}
	public String getMerchant_name_address() {
		return merchant_name_address;
	}
	public void setMerchant_name_address(String merchant_name_address) {
		this.merchant_name_address = merchant_name_address;
	}
	public String getSpecial_billing_type() {
		return special_billing_type;
	}
	public void setSpecial_billing_type(String special_billing_type) {
		this.special_billing_type = special_billing_type;
	}
	public String getSpecial_charges_grade() {
		return special_charges_grade;
	}
	public void setSpecial_charges_grade(String special_charges_grade) {
		this.special_charges_grade = special_charges_grade;
	}
	public String getTac_logo() {
		return tac_logo;
	}
	public void setTac_logo(String tac_logo) {
		this.tac_logo = tac_logo;
	}
	public String getCard_product_info() {
		return card_product_info;
	}
	public void setCard_product_info(String card_product_info) {
		this.card_product_info = card_product_info;
	}
	public String getTran_initiated_method() {
		return tran_initiated_method;
	}
	public void setTran_initiated_method(String tran_initiated_method) {
		this.tran_initiated_method = tran_initiated_method;
	}
	public String getAccount_settle_type() {
		return account_settle_type;
	}
	public void setAccount_settle_type(String account_settle_type) {
		this.account_settle_type = account_settle_type;
	}
	public String getReserved_for_use() {
		return reserved_for_use;
	}
	public void setReserved_for_use(String reserved_for_use) {
		this.reserved_for_use = reserved_for_use;
	}
	public String getHandling_name() {
		return handling_name;
	}
	public void setHandling_name(String handling_name) {
		this.handling_name = handling_name;
	}
	public String getTran_code_caused_error() {
		return tran_code_caused_error;
	}
	public void setTran_code_caused_error(String tran_code_caused_error) {
		this.tran_code_caused_error = tran_code_caused_error;
	}
	public String getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(String deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
	}
}
