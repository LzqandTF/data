package com.chinaebi.entity;

import java.io.Serializable;

public class ManualRec implements Serializable {
	private static final long serialVersionUID = 3486959104505770363L;
	
	private String id;
	private String mer_code;
	private String mer_abbreviation;
	private String rec_amount;
	private String mer_balance;
	private int addorsub;
	private String handler_time;
	private int settle_time;
	private String audit_time;
	private String send_user_name;
	private String auditor_user_name;
	private int data_status;
	private String request_desc;
	private String audit_desc;
	private int manual_count;
	
	public int getManual_count() {
		return manual_count;
	}
	public void setManual_count(int manual_count) {
		this.manual_count = manual_count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMer_code() {
		return mer_code;
	}
	public void setMer_code(String mer_code) {
		this.mer_code = mer_code;
	}
	public String getMer_abbreviation() {
		return mer_abbreviation;
	}
	public void setMer_abbreviation(String mer_abbreviation) {
		this.mer_abbreviation = mer_abbreviation;
	}
	public String getRec_amount() {
		return rec_amount;
	}
	public void setRec_amount(String rec_amount) {
		this.rec_amount = rec_amount;
	}
	public String getMer_balance() {
		return mer_balance;
	}
	public void setMer_balance(String mer_balance) {
		this.mer_balance = mer_balance;
	}
	public int getAddorsub() {
		return addorsub;
	}
	public void setAddorsub(int addorsub) {
		this.addorsub = addorsub;
	}
	public String getHandler_time() {
		return handler_time;
	}
	public void setHandler_time(String handler_time) {
		this.handler_time = handler_time;
	}
	public int getSettle_time() {
		return settle_time;
	}
	public void setSettle_time(int settle_time) {
		this.settle_time = settle_time;
	}
	public String getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}
	public String getSend_user_name() {
		return send_user_name;
	}
	public void setSend_user_name(String send_user_name) {
		this.send_user_name = send_user_name;
	}
	public String getAuditor_user_name() {
		return auditor_user_name;
	}
	public void setAuditor_user_name(String auditor_user_name) {
		this.auditor_user_name = auditor_user_name;
	}
	public int getData_status() {
		return data_status;
	}
	public void setData_status(int data_status) {
		this.data_status = data_status;
	}
	public String getRequest_desc() {
		return request_desc;
	}
	public void setRequest_desc(String request_desc) {
		this.request_desc = request_desc;
	}
	public String getAudit_desc() {
		return audit_desc;
	}
	public void setAudit_desc(String audit_desc) {
		this.audit_desc = audit_desc;
	}
}
