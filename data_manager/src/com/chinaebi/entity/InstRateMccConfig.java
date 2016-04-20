package com.chinaebi.entity;

import java.io.Serializable;
import java.util.List;

public class InstRateMccConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int inst_id;
	private int inst_type;
	private int whether_return_fee;
	private int mcc_b_type;
	private int mcc_s_type;
	private List<MccType> mcc_type_list;
	public List<MccType> getMcc_type_list() {
		return mcc_type_list;
	}
	public void setMcc_type_list(List<MccType> mcc_type_list) {
		this.mcc_type_list = mcc_type_list;
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
	public int getWhether_return_fee() {
		return whether_return_fee;
	}
	public void setWhether_return_fee(int whether_return_fee) {
		this.whether_return_fee = whether_return_fee;
	}
	public int getMcc_b_type() {
		return mcc_b_type;
	}
	public void setMcc_b_type(int mcc_b_type) {
		this.mcc_b_type = mcc_b_type;
	}
	public int getMcc_s_type() {
		return mcc_s_type;
	}
	public void setMcc_s_type(int mcc_s_type) {
		this.mcc_s_type = mcc_s_type;
	}
	
	
}
