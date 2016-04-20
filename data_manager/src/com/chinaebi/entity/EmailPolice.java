package com.chinaebi.entity;

import java.io.Serializable;

public class EmailPolice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int email_id;
	private String email;
	private String phone;
	private String email_theme;
	private String email_content;
	private String phone_content;
	private int data_type;
	private String email_remark;
	private String phone_remark;
	private int police_id;
	private String police_name;
	public int getEmail_id() {
		return email_id;
	}
	public void setEmail_id(int email_id) {
		this.email_id = email_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail_theme() {
		return email_theme;
	}
	public void setEmail_theme(String email_theme) {
		this.email_theme = email_theme;
	}
	public String getEmail_content() {
		return email_content;
	}
	public void setEmail_content(String email_content) {
		this.email_content = email_content;
	}
	public String getPhone_content() {
		return phone_content;
	}
	public void setPhone_content(String phone_content) {
		this.phone_content = phone_content;
	}
	public int getData_type() {
		return data_type;
	}
	public void setData_type(int data_type) {
		this.data_type = data_type;
	}
	public String getEmail_remark() {
		return email_remark;
	}
	public void setEmail_remark(String email_remark) {
		this.email_remark = email_remark;
	}
	public String getPhone_remark() {
		return phone_remark;
	}
	public void setPhone_remark(String phone_remark) {
		this.phone_remark = phone_remark;
	}
	public int getPolice_id() {
		return police_id;
	}
	public void setPolice_id(int police_id) {
		this.police_id = police_id;
	}
	public String getPolice_name() {
		return police_name;
	}
	public void setPolice_name(String police_name) {
		this.police_name = police_name;
	}
}
