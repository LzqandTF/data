package com.chinaebi.entity;

import java.io.Serializable;

public class MccType implements Serializable{
	
	private static final long serialVersionUID = 5901212009816717504L;
	
	private int id;
	private String type_name;
	private int big_type_id;
	public int getBig_type_id() {
		return big_type_id;
	}
	public void setBig_type_id(int big_type_id) {
		this.big_type_id = big_type_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

}
