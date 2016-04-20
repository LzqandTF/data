package com.chinaebi.entity;

public class RuleTemplate implements java.io.Serializable{

	private static final long serialVersionUID = -9150347660475250164L;
	
	private int template_id;
	private String template_name;
	private String template_function;
	private String template_descripe;
	
	public String getTemplate_descripe() {
		return template_descripe;
	}
	public void setTemplate_descripe(String template_descripe) {
		this.template_descripe = template_descripe;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getTemplate_function() {
		return template_function;
	}
	public void setTemplate_function(String template_function) {
		this.template_function = template_function;
	}
}