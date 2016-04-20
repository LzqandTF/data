package com.chinaebi.entity;

import java.io.Serializable;

public class MccBigType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int big_type_id;
	private String type_name;
	public int getBig_type_id() {
		return big_type_id;
	}
	public void setBig_type_id(int big_type_id) {
		this.big_type_id = big_type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
}
