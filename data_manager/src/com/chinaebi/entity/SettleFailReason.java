package com.chinaebi.entity;

import java.io.Serializable;

public class SettleFailReason implements Serializable {

	private static final long serialVersionUID = -4166700124892466881L;
	
	private int reason_id;
	private String reason;
	public int getReason_id() {
		return reason_id;
	}
	public void setReason_id(int reason_id) {
		this.reason_id = reason_id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}
