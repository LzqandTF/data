package com.chinaebi.entity;

@SuppressWarnings("serial")
public class ExecuteNode implements java.io.Serializable{
	private String id;
	private int deduct_sys_id;
	private String deduct_stml_date;
	private int trade_collect;
	private int dz_file_gain;
	private int dz_handle;
	private int error_handle;
	private int dz_file_create;
	private String inst_name;
	private int inst_type;
	
	public String getInst_name() {
		return inst_name;
	}
	public void setInst_name(String inst_name) {
		this.inst_name = inst_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDeduct_sys_id() {
		return deduct_sys_id;
	}
	public void setDeduct_sys_id(int deduct_sys_id) {
		this.deduct_sys_id = deduct_sys_id;
	}
	public String getDeduct_stml_date() {
		return deduct_stml_date;
	}
	public void setDeduct_stml_date(String deduct_stml_date) {
		this.deduct_stml_date = deduct_stml_date;
	}
	public int getTrade_collect() {
		return trade_collect;
	}
	public void setTrade_collect(int trade_collect) {
		this.trade_collect = trade_collect;
	}
	public int getDz_file_gain() {
		return dz_file_gain;
	}
	public void setDz_file_gain(int dz_file_gain) {
		this.dz_file_gain = dz_file_gain;
	}
	public int getDz_handle() {
		return dz_handle;
	}
	public void setDz_handle(int dz_handle) {
		this.dz_handle = dz_handle;
	}
	public int getError_handle() {
		return error_handle;
	}
	public void setError_handle(int error_handle) {
		this.error_handle = error_handle;
	}
	public int getDz_file_create() {
		return dz_file_create;
	}
	public void setDz_file_create(int dz_file_create) {
		this.dz_file_create = dz_file_create;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	
}
