package com.chinaebi.entity;

import java.io.Serializable;

/**
 * 差错处理方式实体
 */
public class ErrorHandling implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1824543901060980605L;

	
	private int id;
	private String handling_name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHandling_name() {
		return handling_name;
	}
	public void setHandling_name(String handling_name) {
		this.handling_name = handling_name;
	}
	
	
}
