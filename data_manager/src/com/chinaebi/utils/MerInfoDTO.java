package com.chinaebi.utils;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 状态信息
 * 
 * @author gjy
 * 
 */
@XmlType(propOrder = {"innerMercode","merName"})
public class MerInfoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String innerMercode; // 商户号
	private String merName; // 商户名称
	
	public String getInnerMercode() {
		return innerMercode;
	}
	@XmlElement(name = "innerMercode")
	public void setInnerMercode(String innerMercode) {
		this.innerMercode = innerMercode;
	}
	public String getMerName() {
		return merName;
	}
	@XmlElement(name = "merName")
	public void setMerName(String merName) {
		this.merName = merName;
	}
	

}
