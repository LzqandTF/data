package com.chinaebi.entity;

import java.io.Serializable;

public class MerchantSettleFail implements Serializable{

	private static final long serialVersionUID = 3148193080198059972L;
	
	private int id;
	private String mer_code;
	private int mer_type;
	private String mer_name;
	private int last_settle_date;
	private int settle_start_date;
	private int reason_id;
	private String reason;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
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
	public int getMer_type() {
		return mer_type;
	}
	public void setMer_type(int mer_type) {
		this.mer_type = mer_type;
	}
	public String getMer_name() {
		return mer_name;
	}
	public void setMer_name(String mer_name) {
		this.mer_name = mer_name;
	}
	public int getLast_settle_date() {
		return last_settle_date;
	}
	public void setLast_settle_date(int last_settle_date) {
		this.last_settle_date = last_settle_date;
	}
	public int getSettle_start_date() {
		return settle_start_date;
	}
	public void setSettle_start_date(int settle_start_date) {
		this.settle_start_date = settle_start_date;
	}
	public int getReason_id() {
		return reason_id;
	}
	public void setReason_id(int reason_id) {
		this.reason_id = reason_id;
	}
}
