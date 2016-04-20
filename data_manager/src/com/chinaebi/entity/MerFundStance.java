package com.chinaebi.entity;

import java.io.Serializable;

public class MerFundStance implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String mer_code;
	private String trade_time;
	private double trade_amount;
	private double mer_fee;
	private double change_amount;
	private double account_amount;
	private String trade_stance;
	private int derc_status;
	private int mer_state;
	private int mer_category;
	private String mer_name;
	private int inst_id;
	private String deduct_stlm_date;
	private int inst_type;
	private String stance_time;
	private int bank_id;
	
	/**
	 * 上期账户余额
	 */
	private String onAccountAmt;
	/**
	 * 交易开始时间
	 */
	private String startDate;
	/**
	 * 支付金额
	 */
	private String theAmount ;
	/**
	 * 退款金额
	 */
	private String refundAmount;
	/**
	 * 商户手续费
	 */
	private String merFees; 
	/**
	 * 商户退回手续费
	 */
	private String merBackFees;
	/**
	 * 结算金额
	 */
	private String settlementAmt;
	/**
	 * 交易结束时间
	 */
	private String endDate;
	/**
	 * 本期账户余额
	 */
	private String thisAccountAmt;
	
	/*
	 * 手工调增金额
	 */
	private String addAmount;
	
	/*
	 * 手工调减金额
	 */
	private String subAmount;
	
	
	public String getAddAmount() {
		return addAmount;
	}
	public void setAddAmount(String addAmount) {
		this.addAmount = addAmount;
	}
	public String getSubAmount() {
		return subAmount;
	}
	public void setSubAmount(String subAmount) {
		this.subAmount = subAmount;
	}
	public String getStance_time() {
		return stance_time;
	}
	public void setStance_time(String stance_time) {
		this.stance_time = stance_time;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getOnAccountAmt() {
		return onAccountAmt;
	}
	public void setOnAccountAmt(String onAccountAmt) {
		this.onAccountAmt = onAccountAmt;
	}
	public String getTheAmount() {
		return theAmount;
	}
	public void setTheAmount(String theAmount) {
		this.theAmount = theAmount;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getMerFees() {
		return merFees;
	}
	public void setMerFees(String merFees) {
		this.merFees = merFees;
	}
	public String getMerBackFees() {
		return merBackFees;
	}
	public void setMerBackFees(String merBackFees) {
		this.merBackFees = merBackFees;
	}
	public String getSettlementAmt() {
		return settlementAmt;
	}
	public void setSettlementAmt(String settlementAmt) {
		this.settlementAmt = settlementAmt;
	}
	public String getThisAccountAmt() {
		return thisAccountAmt;
	}
	public void setThisAccountAmt(String thisAccountAmt) {
		this.thisAccountAmt = thisAccountAmt;
	}
	public String getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(String deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
	}
	public int getInst_id() {
		return inst_id;
	}
	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
	}
	public String getMer_name() {
		return mer_name;
	}
	public void setMer_name(String mer_name) {
		this.mer_name = mer_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getMer_code() {
		return mer_code;
	}
	public void setMer_code(String mer_code) {
		this.mer_code = mer_code;
	}
	public String getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
	}
	public double getTrade_amount() {
		return trade_amount;
	}
	public void setTrade_amount(double trade_amount) {
		this.trade_amount = trade_amount;
	}
	public double getMer_fee() {
		return mer_fee;
	}
	public void setMer_fee(double mer_fee) {
		this.mer_fee = mer_fee;
	}
	public double getChange_amount() {
		return change_amount;
	}
	public void setChange_amount(double change_amount) {
		this.change_amount = change_amount;
	}
	public double getAccount_amount() {
		return account_amount;
	}
	public void setAccount_amount(double account_amount) {
		this.account_amount = account_amount;
	}
	public String getTrade_stance() {
		return trade_stance;
	}
	public void setTrade_stance(String trade_stance) {
		this.trade_stance = trade_stance;
	}
	public int getDerc_status() {
		return derc_status;
	}
	public void setDerc_status(int derc_status) {
		this.derc_status = derc_status;
	}
	public int getMer_state() {
		return mer_state;
	}
	public void setMer_state(int mer_state) {
		this.mer_state = mer_state;
	}
	public int getMer_category() {
		return mer_category;
	}
	public void setMer_category(int mer_category) {
		this.mer_category = mer_category;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
}
