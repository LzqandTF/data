package com.chinaebi.entity;

import java.io.Serializable;

public class SettleMerchantMatch implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String dy_mer_code;
	private String settle_mer_code;
	private String user_id;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDy_mer_code() {
		return dy_mer_code;
	}
	public void setDy_mer_code(String dy_mer_code) {
		this.dy_mer_code = dy_mer_code;
	}
	public String getSettle_mer_code() {
		return settle_mer_code;
	}
	public void setSettle_mer_code(String settle_mer_code) {
		this.settle_mer_code = settle_mer_code;
	}
	

}
