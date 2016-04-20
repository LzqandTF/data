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
@XmlType(propOrder = {"organizationname"})
public class OrgDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String organizationname; // 代理商名称
	public String getOrganizationname() {
		return organizationname;
	}
	@XmlElement(name = "organizationname")
	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}
	
	
}
