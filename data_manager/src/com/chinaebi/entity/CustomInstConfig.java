package com.chinaebi.entity;

import java.io.Serializable;

public class CustomInstConfig implements Serializable{

	private static final long serialVersionUID = -1206431546174880548L;
	
	private int id;
	private int object_id;
	private int inst_id;
	private int inst_type;
	private String inst_name;
	
	public String getInst_name() {
		return inst_name;
	}
	public void setInst_name(String inst_name) {
		this.inst_name = inst_name;
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
	public int getInst_id() {
		return inst_id;
	}
	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	
}
