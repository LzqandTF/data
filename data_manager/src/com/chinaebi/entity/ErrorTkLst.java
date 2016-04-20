package com.chinaebi.entity;

import java.io.Serializable;

public class ErrorTkLst implements Serializable {
	private static final long serialVersionUID = 3101050730248978013L;
	
	private String trade_id;
	private String trade_time;
	private int trade_amount;
	private int deduct_sys_id;
	private int inst_type;
	private int handling_id;
	private int bank_id;
	private int syn_flag;
	
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	public String getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
	}
	public int getTrade_amount() {
		return trade_amount;
	}
	public void setTrade_amount(int trade_amount) {
		this.trade_amount = trade_amount;
	}
	public int getDeduct_sys_id() {
		return deduct_sys_id;
	}
	public void setDeduct_sys_id(int deduct_sys_id) {
		this.deduct_sys_id = deduct_sys_id;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	public int getHandling_id() {
		return handling_id;
	}
	public void setHandling_id(int handling_id) {
		this.handling_id = handling_id;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public int getSyn_flag() {
		return syn_flag;
	}
	public void setSyn_flag(int syn_flag) {
		this.syn_flag = syn_flag;
	}
}
