package com.chinaebi.entity;

import java.io.Serializable;

/**
 * 银联差错实体Bean
 * @author wufei
 *
 */
public class YlCupsErrorEntry implements Serializable {

	private static final long serialVersionUID = 2262102023348518295L;
	
	private String id;
	private String out_account;
	private String mer_name;
	private String trade_time;
	private int trade_result;
	private String deduct_sys_reference;
	private String reqSysStance;
	private String tradeAmount;
	private int handling_id;
	private String acqInstIdCode;
	private String reason_code;
	private String deductStlmDate;
	private String trade_category;
	private int deduct_sys_id;
	private String trade_source;
	private int trade_status;
	private String turnDown_remark;
	private String entering_time;
	private String operator;
	private String audit_operator;
	private String commit_time;
	private String process;
	private int tradeMsgType;
	private int bk_chk;
	private int inst_type;
	
	private String tradeType;//交易类型
	private String tradeName;//交易类别
	private String handling_name;//处理方式中文名称
	private String reason_des;//原因码对应的原因描述
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOut_account() {
		return out_account;
	}
	public void setOut_account(String out_account) {
		this.out_account = out_account;
	}
	public String getMer_name() {
		return mer_name;
	}
	public void setMer_name(String mer_name) {
		this.mer_name = mer_name;
	}
	public String getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
	}
	public int getTrade_result() {
		return trade_result;
	}
	public void setTrade_result(int trade_result) {
		this.trade_result = trade_result;
	}
	public String getDeduct_sys_reference() {
		return deduct_sys_reference;
	}
	public void setDeduct_sys_reference(String deduct_sys_reference) {
		this.deduct_sys_reference = deduct_sys_reference;
	}
	public String getReqSysStance() {
		return reqSysStance;
	}
	public void setReqSysStance(String reqSysStance) {
		this.reqSysStance = reqSysStance;
	}
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public int getHandling_id() {
		return handling_id;
	}
	public void setHandling_id(int handling_id) {
		this.handling_id = handling_id;
	}
	public String getAcqInstIdCode() {
		return acqInstIdCode;
	}
	public void setAcqInstIdCode(String acqInstIdCode) {
		this.acqInstIdCode = acqInstIdCode;
	}
	public String getReason_code() {
		return reason_code;
	}
	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}
	public String getDeductStlmDate() {
		return deductStlmDate;
	}
	public void setDeductStlmDate(String deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
	}
	public String getTrade_category() {
		return trade_category;
	}
	public void setTrade_category(String trade_category) {
		this.trade_category = trade_category;
	}
	public int getDeduct_sys_id() {
		return deduct_sys_id;
	}
	public void setDeduct_sys_id(int deduct_sys_id) {
		this.deduct_sys_id = deduct_sys_id;
	}
	public String getTrade_source() {
		return trade_source;
	}
	public void setTrade_source(String trade_source) {
		this.trade_source = trade_source;
	}
	public int getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(int trade_status) {
		this.trade_status = trade_status;
	}
	public String getTurnDown_remark() {
		return turnDown_remark;
	}
	public void setTurnDown_remark(String turnDown_remark) {
		this.turnDown_remark = turnDown_remark;
	}
	public String getEntering_time() {
		return entering_time;
	}
	public void setEntering_time(String entering_time) {
		this.entering_time = entering_time;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getAudit_operator() {
		return audit_operator;
	}
	public void setAudit_operator(String audit_operator) {
		this.audit_operator = audit_operator;
	}
	public String getCommit_time() {
		return commit_time;
	}
	public void setCommit_time(String commit_time) {
		this.commit_time = commit_time;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public int getTradeMsgType() {
		return tradeMsgType;
	}
	public void setTradeMsgType(int tradeMsgType) {
		this.tradeMsgType = tradeMsgType;
	}
	public int getBk_chk() {
		return bk_chk;
	}
	public void setBk_chk(int bk_chk) {
		this.bk_chk = bk_chk;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getHandling_name() {
		return handling_name;
	}
	public void setHandling_name(String handling_name) {
		this.handling_name = handling_name;
	}
	public String getReason_des() {
		return reason_des;
	}
	public void setReason_des(String reason_des) {
		this.reason_des = reason_des;
	}
}

