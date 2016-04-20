package com.chinaebi.entity;


public class MerInfo {
	private String innerMercode;//商户号
	private String merName;//商户名称
	private String organizationName;//代理上名称
	
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getInnerMercode() {
		return innerMercode;
	}
	public void setInnerMercode(String innerMercode) {
		this.innerMercode = innerMercode;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	
}
