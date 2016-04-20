package com.chinaebi.entity;

import java.io.Serializable;

public class DuizhangResult implements Serializable {

	private static final long serialVersionUID = 6548313411318080557L;
	
	private String deductStlmDate;
	private int deductSysId;
	private int selectCount;
	private int deductCount;
	private int selectCountByCheck;
	private int selectCountByWhetherErroeHandle;
	private int selectCountByNoCheck;
	
	public String getDeductStlmDate() {
		return deductStlmDate;
	}
	public void setDeductStlmDate(String deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
	}
	public int getDeductSysId() {
		return deductSysId;
	}
	public void setDeductSysId(int deductSysId) {
		this.deductSysId = deductSysId;
	}
	public int getSelectCount() {
		return selectCount;
	}
	public void setSelectCount(int selectCount) {
		this.selectCount = selectCount;
	}
	public int getDeductCount() {
		return deductCount;
	}
	public void setDeductCount(int deductCount) {
		this.deductCount = deductCount;
	}
	public int getSelectCountByCheck() {
		return selectCountByCheck;
	}
	public void setSelectCountByCheck(int selectCountByCheck) {
		this.selectCountByCheck = selectCountByCheck;
	}
	public int getSelectCountByWhetherErroeHandle() {
		return selectCountByWhetherErroeHandle;
	}
	public void setSelectCountByWhetherErroeHandle(
			int selectCountByWhetherErroeHandle) {
		this.selectCountByWhetherErroeHandle = selectCountByWhetherErroeHandle;
	}
	public int getSelectCountByNoCheck() {
		return selectCountByNoCheck;
	}
	public void setSelectCountByNoCheck(int selectCountByNoCheck) {
		this.selectCountByNoCheck = selectCountByNoCheck;
	}
}

