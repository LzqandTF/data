package com.chinaebi.entity;

public class FunctionRightLogin {

	private int id; // 主键
	private int funcId; // 功能ID
	private int loginId; // 用户ID

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFuncId() {
		return funcId;
	}

	public void setFuncId(int funcId) {
		this.funcId = funcId;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public FunctionRightLogin() {
		super();
	}

}
