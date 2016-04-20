package com.chinaebi.entity;

public class ReplaceValue implements java.io.Serializable{
	private static final long serialVersionUID = -4679294237764890714L;
	
	private int id;
	private String old_value;
	private String new_value;
	private int rule_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getRule_id() {
		return rule_id;
	}
	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}
}
