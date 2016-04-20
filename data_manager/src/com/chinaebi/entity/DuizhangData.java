package com.chinaebi.entity;

import java.io.Serializable;

import com.chinaebi.utils.StringUtils;

public class DuizhangData implements Serializable{
	private static final long serialVersionUID = -1337466628836712563L;
	
	private String id;
	private String acqInstIdCode;
	private String fwdInstIdCode;
	private String reqSysStance;
	private String reqTime;
	private String tradeTime;//中信银行
	private String outAccount;
	private String tradeAmount;
	private String portionAmount;
	private String tradeFee;
	private String msgType;
	private String process;
	private String merType;
	private String termId;
	private String merCode;
	private String deductSysReference;
	private String reqType;
	private String authorizationCode;
	private String rcvgInstIdCode;
	private String origDataStance;
	private String deductSysResponse;
	private String reqInputType;
	private String acceptorReceiveFee;
	private String acceptorPayFee;
	private String throughServiceFee;
	private String convertShow;
	private String cardNumber;
	private String termReadAbility;
	private String idConditionCode;
	private String origDataTime;
	private String issuingBankCode;
	private String tradeAdress;
	private String terminalType;
	private String eci;
	private String byStagesFee;
	private String otherInfo;
	private int whetherErroeHandle;
	private String dz_file_name;
	private String inst_name;
	private int bk_chk;
	private String deduct_stlm_date;
	
	// 线上网关对账文件中特有的字段
	private String orderId;
	
	private String trade_code;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getPortionAmount() {
		return portionAmount;
	}
	public void setPortionAmount(String portionAmount) {
		this.portionAmount = portionAmount;
	}
	public String getTradeFee() {
		return tradeFee;
	}
	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getProcess() {
		if (StringUtils.isNotBlank(process)) {
			return process;
		} else {
			return "";
		}
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
		if (StringUtils.isNotBlank(termId)) {
			return termId;
		} else {
			return "";
		}
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getMerCode() {
		if (StringUtils.isNotBlank(merCode)) {
			return merCode;
		} else {
			return "";
		}
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getDeductSysReference() {
		if (StringUtils.isNotBlank(deductSysReference)) {
			return deductSysReference;
		} else {
			return "";
		}
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
	public String getThroughServiceFee() {
		return throughServiceFee;
	}
	public void setThroughServiceFee(String throughServiceFee) {
		this.throughServiceFee = throughServiceFee;
	}
	public String getConvertShow() {
		return convertShow;
	}
	public void setConvertShow(String convertShow) {
		this.convertShow = convertShow;
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
	public String getOrigDataTime() {
		return origDataTime;
	}
	public void setOrigDataTime(String origDataTime) {
		this.origDataTime = origDataTime;
	}
	public String getIssuingBankCode() {
		return issuingBankCode;
	}
	public void setIssuingBankCode(String issuingBankCode) {
		this.issuingBankCode = issuingBankCode;
	}
	public String getTradeAdress() {
		return tradeAdress;
	}
	public void setTradeAdress(String tradeAdress) {
		this.tradeAdress = tradeAdress;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getEci() {
		return eci;
	}
	public void setEci(String eci) {
		this.eci = eci;
	}
	public String getByStagesFee() {
		return byStagesFee;
	}
	public void setByStagesFee(String byStagesFee) {
		this.byStagesFee = byStagesFee;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public int getWhetherErroeHandle() {
		return whetherErroeHandle;
	}
	public void setWhetherErroeHandle(int whetherErroeHandle) {
		this.whetherErroeHandle = whetherErroeHandle;
	}
	public String getDz_file_name() {
		return dz_file_name;
	}
	public void setDz_file_name(String dz_file_name) {
		this.dz_file_name = dz_file_name;
	}
	public String getInst_name() {
		return inst_name;
	}
	public void setInst_name(String inst_name) {
		this.inst_name = inst_name;
	}
	public int getBk_chk() {
		return bk_chk;
	}
	public void setBk_chk(int bk_chk) {
		this.bk_chk = bk_chk;
	}
	public String getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(String deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTrade_code() {
		return trade_code;
	}
	public void setTrade_code(String trade_code) {
		this.trade_code = trade_code;
	}
}
