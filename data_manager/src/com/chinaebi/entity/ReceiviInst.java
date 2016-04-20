package com.chinaebi.entity;

public class ReceiviInst implements java.io.Serializable{
	private static final long serialVersionUID = -449031761308440737L;
	
	private int receivi_id;
	private String receivi_name;
	private String receivi_remark;
	private int receivi_status;
	private String receivi_time;
	public int getReceivi_id() {
		return receivi_id;
	}
	public void setReceivi_id(int receivi_id) {
		this.receivi_id = receivi_id;
	}
	public String getReceivi_name() {
		return receivi_name;
	}
	public void setReceivi_name(String receivi_name) {
		this.receivi_name = receivi_name;
	}
	public String getReceivi_remark() {
		return receivi_remark;
	}
	public void setReceivi_remark(String receivi_remark) {
		this.receivi_remark = receivi_remark;
	}
	public int getReceivi_status() {
		return receivi_status;
	}
	public void setReceivi_status(int receivi_status) {
		this.receivi_status = receivi_status;
	}
	public String getReceivi_time() {
		return receivi_time;
	}
	public void setReceivi_time(String receivi_time) {
		this.receivi_time = receivi_time;
	}
	
}
