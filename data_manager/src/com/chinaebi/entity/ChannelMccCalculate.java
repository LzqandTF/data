package com.chinaebi.entity;

import java.io.Serializable;

public class ChannelMccCalculate implements Serializable{

	private static final long serialVersionUID = -6584307243820364247L;
	private int inst_id ;
	private float issuer;
	private float billToParty;
	private float unionpay;
	public int getInst_id() {
		return inst_id;
	}
	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
	}
	public float getIssuer() {
		return issuer;
	}
	public void setIssuer(float issuer) {
		this.issuer = issuer;
	}
	public float getBillToParty() {
		return billToParty;
	}
	public void setBillToParty(float billToParty) {
		this.billToParty = billToParty;
	}
	public float getUnionpay() {
		return unionpay;
	}
	public void setUnionpay(float unionpay) {
		this.unionpay = unionpay;
	}
	
}
