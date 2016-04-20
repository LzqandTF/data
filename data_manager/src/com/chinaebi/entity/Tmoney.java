package com.chinaebi.entity;

import java.io.Serializable;

public class Tmoney implements Serializable {
	private static final long serialVersionUID = 6358240413711518250L;
	
	private String id;
	private String _name;
	private String total_money;
	private int settle_way;
	private int deduct_stlm_date;
	private String mer_code;
	private String bil_account;
	private String bil_accountname;
	private int inst_id;
	private int inst_type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String getTotal_money() {
		return total_money;
	}
	public void setTotal_money(String total_money) {
		this.total_money = total_money;
	}
	public int getSettle_way() {
		return settle_way;
	}
	public void setSettle_way(int settle_way) {
		this.settle_way = settle_way;
	}
	public int getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(int deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
	}
	public String getMer_code() {
		return mer_code;
	}
	public void setMer_code(String mer_code) {
		this.mer_code = mer_code;
	}
	public String getBil_account() {
		return bil_account;
	}
	public void setBil_account(String bil_account) {
		this.bil_account = bil_account;
	}
	public String getBil_accountname() {
		return bil_accountname;
	}
	public void setBil_accountname(String bil_accountname) {
		this.bil_accountname = bil_accountname;
	}
	public int getInst_id() {
		return inst_id;
	}
	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	
}
