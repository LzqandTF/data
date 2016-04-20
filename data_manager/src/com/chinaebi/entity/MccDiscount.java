package com.chinaebi.entity;

import java.io.Serializable;

public class MccDiscount implements Serializable{

	private static final long serialVersionUID = -8284071186516028983L;
	
	private int id;
	private String mcc_type;
	private String issuers;
	private String billToParty;
	private String unionpay;
	private String range_desc;
	private int parent_id;
	private String mcc_fee;
	
	public String getMcc_fee() {
		return mcc_fee;
	}
	public void setMcc_fee(String mcc_fee) {
		this.mcc_fee = mcc_fee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMcc_type() {
		return mcc_type;
	}
	public void setMcc_type(String mcc_type) {
		this.mcc_type = mcc_type;
	}
	public String getIssuers() {
		return issuers;
	}
	public void setIssuers(String issuers) {
		this.issuers = issuers;
	}
	public String getBillToParty() {
		return billToParty;
	}
	public void setBillToParty(String billToParty) {
		this.billToParty = billToParty;
	}
	public String getUnionpay() {
		return unionpay;
	}
	public void setUnionpay(String unionpay) {
		this.unionpay = unionpay;
	}
	public String getRange_desc() {
		return range_desc;
	}
	public void setRange_desc(String range_desc) {
		this.range_desc = range_desc;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
}
