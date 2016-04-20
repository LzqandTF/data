package com.chinaebi.entity;

import java.io.Serializable;

public class InnerErrorHandling implements Serializable{

	private static final long serialVersionUID = -8715859115908585341L;
	
	private int handling_id;
	private String handling_name;
	public int getHandling_id() {
		return handling_id;
	}
	public void setHandling_id(int handling_id) {
		this.handling_id = handling_id;
	}
	public String getHandling_name() {
		return handling_name;
	}
	public void setHandling_name(String handling_name) {
		this.handling_name = handling_name;
	}
}
