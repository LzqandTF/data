package com.chinaebi.entity;

import java.io.Serializable;

public class SettleMerchantConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String settle_mer_code;
	private String settle_mer_name;
	private String operate_time;
	private int rownum;
	private String user_id;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getSettle_mer_code() {
		return settle_mer_code;
	}
	public void setSettle_mer_code(String settle_mer_code) {
		this.settle_mer_code = settle_mer_code;
	}
	public String getSettle_mer_name() {
		return settle_mer_name;
	}
	public void setSettle_mer_name(String settle_mer_name) {
		this.settle_mer_name = settle_mer_name;
	}
	public String getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}
	
}
