package com.chinaebi.entity;

import java.io.Serializable;

public class InstMerRateConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	private int inst_id;
	private int inst_type;
	private String mer_code;
	private String card_type;
	private String fee_Poundage;
	private int lineOrinter;
	private String user_name;
	private int gid;
	private int g_type;
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getG_type() {
		return g_type;
	}
	public void setG_type(int g_type) {
		this.g_type = g_type;
	}
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
	public String getMer_code() {
		return mer_code;
	}
	public void setMer_code(String mer_code) {
		this.mer_code = mer_code;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getFee_Poundage() {
		return fee_Poundage;
	}
	public void setFee_Poundage(String fee_Poundage) {
		this.fee_Poundage = fee_Poundage;
	}
	public int getLineOrinter() {
		return lineOrinter;
	}
	public void setLineOrinter(int lineOrinter) {
		this.lineOrinter = lineOrinter;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
