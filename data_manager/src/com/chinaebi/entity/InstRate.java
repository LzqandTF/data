package com.chinaebi.entity;

import java.io.Serializable;

public class InstRate implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int inst_id;
	private int inst_type;
	private int whetherReturnFee;
	private int inst_rate_type;
	private int inst_rate_mcc;
	private String bank_inst_code;
	private String user_name;
	private String inst_name;
	
	private String mccDaLeiArr;
	private String mccXiLeiArr;
	private String deductMerCodeArr;
	private String cardTypeArr;
	private String bankTypeArr;
	private String operTypeArr;
	public String getOperTypeArr() {
		return operTypeArr;
	}
	public void setOperTypeArr(String operTypeArr) {
		this.operTypeArr = operTypeArr;
	}
	public String getMccDaLeiArr() {
		return mccDaLeiArr;
	}
	public void setMccDaLeiArr(String mccDaLeiArr) {
		this.mccDaLeiArr = mccDaLeiArr;
	}
	public String getMccXiLeiArr() {
		return mccXiLeiArr;
	}
	public void setMccXiLeiArr(String mccXiLeiArr) {
		this.mccXiLeiArr = mccXiLeiArr;
	}
	public String getDeductMerCodeArr() {
		return deductMerCodeArr;
	}
	public void setDeductMerCodeArr(String deductMerCodeArr) {
		this.deductMerCodeArr = deductMerCodeArr;
	}
	public String getCardTypeArr() {
		return cardTypeArr;
	}
	public void setCardTypeArr(String cardTypeArr) {
		this.cardTypeArr = cardTypeArr;
	}
	public String getBankTypeArr() {
		return bankTypeArr;
	}
	public void setBankTypeArr(String bankTypeArr) {
		this.bankTypeArr = bankTypeArr;
	}
	public String getChannelFeeArr() {
		return channelFeeArr;
	}
	public void setChannelFeeArr(String channelFeeArr) {
		this.channelFeeArr = channelFeeArr;
	}
	private String channelFeeArr;
	
	
	public int getInst_id() {
		return inst_id;
	}
	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	public int getWhetherReturnFee() {
		return whetherReturnFee;
	}
	public void setWhetherReturnFee(int whetherReturnFee) {
		this.whetherReturnFee = whetherReturnFee;
	}
	public int getInst_rate_type() {
		return inst_rate_type;
	}
	public void setInst_rate_type(int inst_rate_type) {
		this.inst_rate_type = inst_rate_type;
	}
	public int getInst_rate_mcc() {
		return inst_rate_mcc;
	}
	public void setInst_rate_mcc(int inst_rate_mcc) {
		this.inst_rate_mcc = inst_rate_mcc;
	}
	public String getBank_inst_code() {
		return bank_inst_code;
	}
	public void setBank_inst_code(String bank_inst_code) {
		this.bank_inst_code = bank_inst_code;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getInst_name() {
		return inst_name;
	}
	public void setInst_name(String inst_name) {
		this.inst_name = inst_name;
	}
	
}
