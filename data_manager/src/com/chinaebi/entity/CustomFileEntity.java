package com.chinaebi.entity;

import java.io.Serializable;

public class CustomFileEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int file_type;
	private String attribute_name;
	private String show_attribute_name;
	private int handler_type;
	private String new_value;
	private String ole_value;
	private String template_function;
	private String attribute_column;
	private int column_length;
	
	private int rule_id;
	private int attribute_type;
	private int dz_column_id;
	
	private String user_rule_column;
	private int object_id;
	
	public int getObject_id() {
		return object_id;
	}
	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}
	public String getUser_rule_column() {
		return user_rule_column;
	}
	public void setUser_rule_column(String user_rule_column) {
		this.user_rule_column = user_rule_column;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}
	public int getHandler_type() {
		return handler_type;
	}
	public void setHandler_type(int handler_type) {
		this.handler_type = handler_type;
	}
	public String getNew_value() {
		return new_value;
	}
	public void setNew_value(String new_value) {
		this.new_value = new_value;
	}
	public String getOle_value() {
		return ole_value;
	}
	public void setOle_value(String ole_value) {
		this.ole_value = ole_value;
	}
	public String getTemplate_function() {
		return template_function;
	}
	public void setTemplate_function(String template_function) {
		this.template_function = template_function;
	}
	public int getColumn_length() {
		return column_length;
	}
	public void setColumn_length(int column_length) {
		this.column_length = column_length;
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
	public String getShow_attribute_name() {
		return show_attribute_name;
	}
	public void setShow_attribute_name(String show_attribute_name) {
		this.show_attribute_name = show_attribute_name;
	}
	public int getRule_id() {
		return rule_id;
	}
	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}
	public int getAttribute_type() {
		return attribute_type;
	}
	public void setAttribute_type(int attribute_type) {
		this.attribute_type = attribute_type;
	}
	public int getDz_column_id() {
		return dz_column_id;
	}
	public void setDz_column_id(int dz_column_id) {
		this.dz_column_id = dz_column_id;
	}
	
	
}
