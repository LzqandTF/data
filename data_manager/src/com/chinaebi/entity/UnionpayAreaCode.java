package com.chinaebi.entity;

import java.io.Serializable;

public class UnionpayAreaCode implements Serializable {
	private static final long serialVersionUID = 8422345654387098293L;
	
	private int area_code;
	private int parent_area_code;
	private String area_name;
	
	public int getArea_code() {
		return area_code;
	}
	public void setArea_code(int area_code) {
		this.area_code = area_code;
	}
	public int getParent_area_code() {
		return parent_area_code;
	}
	public void setParent_area_code(int parent_area_code) {
		this.parent_area_code = parent_area_code;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
}
