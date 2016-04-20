package com.chinaebi.entity;

import java.util.Date;

@SuppressWarnings("serial")
public class Login implements java.io.Serializable {

	private int id; // 主键		
	private String loginName; // 登陆名称
	private String password; // 登录密码
	private Date loginDate; // 上次登录时间
	private int status;	//状态
	private String chineseName;//中文名称

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Login() {
		super();
	}

}
