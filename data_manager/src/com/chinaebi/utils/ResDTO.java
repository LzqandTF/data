package com.chinaebi.utils;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 状态信息
 * 
 * @author gjy
 * 
 */
@XmlRootElement(name ="res")
@XmlType(propOrder = {"status","list","organization"})
public class ResDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StatusDTO status;
	private ListDTO list;
	private OrgDTO organization;
	
	@XmlElement(name = "status")
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	public StatusDTO getStatus() {
		return status;
	}

	public OrgDTO getOrganization() {
		return organization;
	}
	@XmlElement(name = "organization")
	public void setOrganization(OrgDTO organization) {
		this.organization = organization;
	}
	public ListDTO getList() {
		return list;
	}
	@XmlElement(name = "list")
	public void setList(ListDTO list) {
		this.list = list;
	}
	

}
