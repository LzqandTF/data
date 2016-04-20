package com.chinaebi.entity;

import java.io.Serializable;

/**
 * 差错处理方式原因码
 */
public class ReasonCode implements Serializable{

	private static final long serialVersionUID = -3705664054348862442L;

	private String reason_id;
	private String reason_desc;
	private Integer id;
	
	public String getReason_id() {
		return reason_id;
	}
	public void setReason_id(String reason_id) {
		this.reason_id = reason_id;
	}
	public String getReason_desc() {
		return reason_desc;
	}
	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
