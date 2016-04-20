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
@XmlType(propOrder = {"value","msg"})
public class StatusDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String value; // 状态
	private String msg; // 返回消息
	public String getValue() {
		return value;
	}
	@XmlElement(name = "value")
	public void setValue(String value) {
		this.value = value;
	}
	public String getMsg() {
		return msg;
	}
	@XmlElement(name = "msg")
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
