package com.chinaebi.utils;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder = {"merInfo"})
public class ListDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1153369014529272039L;
	private List<MerInfoDTO> merInfo;

	public List<MerInfoDTO> getMerInfo() {
		return merInfo;
	}
	@XmlElement(name = "merInfo")
	public void setMerInfo(List<MerInfoDTO> merInfo) {
		this.merInfo = merInfo;
	}
}
