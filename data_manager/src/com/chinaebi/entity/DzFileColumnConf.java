package com.chinaebi.entity;

public class DzFileColumnConf implements java.io.Serializable{

	private static final long serialVersionUID = 9190975005262348752L;
	
	private int dz_column_id;
	private String attribute_name;
	private String attribute_column;
	private int attribute_type;
	private int column_length;
	private String attribute_column_online;
	private String attribute_column_online_refund;
	
	
	public String getAttribute_column_online_refund() {
		return attribute_column_online_refund;
	}
	public void setAttribute_column_online_refund(
			String attribute_column_online_refund) {
		this.attribute_column_online_refund = attribute_column_online_refund;
	}
	public String getAttribute_column_online() {
		return attribute_column_online;
	}
	public void setAttribute_column_online(String attribute_column_online) {
		this.attribute_column_online = attribute_column_online;
	}
	public int getColumn_length() {
		return column_length;
	}
	public void setColumn_length(int column_length) {
		this.column_length = column_length;
	}
	public int getDz_column_id() {
		return dz_column_id;
	}
	public void setDz_column_id(int dz_column_id) {
		this.dz_column_id = dz_column_id;
	}
	public String getAttribute_name() {
		return attribute_name;
	}
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}
	public String getAttribute_column() {
		return attribute_column;
	}
	public void setAttribute_column(String attribute_column) {
		this.attribute_column = attribute_column;
	}
	public int getAttribute_type() {
		return attribute_type;
	}
	public void setAttribute_type(int attribute_type) {
		this.attribute_type = attribute_type;
	}
}
