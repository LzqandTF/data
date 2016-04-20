package com.chinaebi.entity;

import java.io.Serializable;

public class FeePoundage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fee_poundage;
	private String poundage_detail;
	public String getFee_poundage() {
		return fee_poundage;
	}
	public void setFee_poundage(String fee_poundage) {
		this.fee_poundage = fee_poundage;
	}
	public String getPoundage_detail() {
		return poundage_detail;
	}
	public void setPoundage_detail(String poundage_detail) {
		this.poundage_detail = poundage_detail;
	}
	
}
