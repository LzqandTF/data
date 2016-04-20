package com.chinaebi.entity;

public class ObjectRelevanceColumn implements java.io.Serializable{

	private static final long serialVersionUID = -1563502737416709410L;
	
	private int id;
	private int object_id;
	private int dz_column_id;
	private int rule_id;
	private int file_type;
	private String attribute_column;
	private String attribute_name;
	private String show_attribute_name;
	private String attribute_type;
	
	public String getAttribute_column() {
		return attribute_column;
	}
	public void setAttribute_column(String attribute_column) {
		this.attribute_column = attribute_column;
	}
	public String getAttribute_name() {
		return attribute_name;
	}
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}
	public String getShow_attribute_name() {
		return show_attribute_name;
	}
	public void setShow_attribute_name(String show_attribute_name) {
		this.show_attribute_name = show_attribute_name;
	}
	public String getAttribute_type() {
		return attribute_type;
	}
	public void setAttribute_type(String attribute_type) {
		this.attribute_type = attribute_type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getObject_id() {
		return object_id;
	}
	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}
	public int getDz_column_id() {
		return dz_column_id;
	}
	public void setDz_column_id(int dz_column_id) {
		this.dz_column_id = dz_column_id;
	}
	public int getRule_id() {
		return rule_id;
	}
	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}
}
