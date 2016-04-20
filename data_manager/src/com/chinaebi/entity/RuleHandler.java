package com.chinaebi.entity;

public class RuleHandler implements java.io.Serializable{

	private static final long serialVersionUID = -5754590564905226075L;
	
	private int rule_id;
	private String dz_column_name;
	private String attribute_column;
	private String old_value;
	private String new_value;
	private int handler_type = -1;
	private int template_id;
	private String rule_description;
	private String template_name;
	private int id;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public int getRule_id() {
		return rule_id;
	}
	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}
	public String getDz_column_name() {
		return dz_column_name;
	}
	public void setDz_column_name(String dz_column_name) {
		this.dz_column_name = dz_column_name;
	}
	public String getAttribute_column() {
		return attribute_column;
	}
	public void setAttribute_column(String attribute_column) {
		this.attribute_column = attribute_column;
	}
	public String getOld_value() {
		return old_value;
	}
	public void setOld_value(String old_value) {
		this.old_value = old_value;
	}
	public String getNew_value() {
		return new_value;
	}
	public void setNew_value(String new_value) {
		this.new_value = new_value;
	}
	public int getHandler_type() {
		return handler_type;
	}
	public void setHandler_type(int handler_type) {
		this.handler_type = handler_type;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public String getRule_description() {
		return rule_description;
	}
	public void setRule_description(String rule_description) {
		this.rule_description = rule_description;
	}
}
