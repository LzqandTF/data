package com.chinaebi.entity;

import java.io.Serializable;

public class MerchantsBalance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7466156190108904102L;
	
	private int  id; 
	
	/**
	 * 商户号
	 */
	private String mer_code;
	
	/**
	 * 商户类别
	 */
	private Integer mer_category;
	
	/**
	 * 商户余额
	 */
	private String mer_balance;
	
	/**
	 * 商户状态
	 */
	private String mer_state;
	
	/**
	 * 商户简称
	 */
	private String merName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMer_code() {
		return mer_code;
	}
	public void setMer_code(String mer_code) {
		this.mer_code = mer_code;
	}
	public String getMer_state() {
		return mer_state;
	}
	public void setMer_state(String mer_state) {
		this.mer_state = mer_state;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getMer_balance() {
		return mer_balance;
	}
	public void setMer_balance(String mer_balance) {
		this.mer_balance = mer_balance;
	}
	public Integer getMer_category() {
		return mer_category;
	}
	public void setMer_category(Integer mer_category) {
		this.mer_category = mer_category;
	}
	
}
