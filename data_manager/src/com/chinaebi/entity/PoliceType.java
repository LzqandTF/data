package com.chinaebi.entity;

import java.io.Serializable;

public class PoliceType implements Serializable {
	private static final long serialVersionUID = 8082175630395191152L;
	
	private int police_id;
	private String police_name;
	private String desc;
	public int getPolice_id() {
		return police_id;
	}
	public void setPolice_id(int police_id) {
		this.police_id = police_id;
	}
	public String getPolice_name() {
		return police_name;
	}
	public void setPolice_name(String police_name) {
		this.police_name = police_name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
