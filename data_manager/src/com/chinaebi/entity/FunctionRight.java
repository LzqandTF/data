package com.chinaebi.entity;

@SuppressWarnings("serial")
public class FunctionRight implements java.io.Serializable {

	private int id; // 主键
	private String funcName; // 功能名称
	private String url; // 路径
	private int level; // 菜单等级
	private int parentId; // 父菜单ID

	private int flag;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public FunctionRight() {
		super();
	}

}
