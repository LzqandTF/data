package com.chinaebi.entity;

import java.io.Serializable;

public class DzFileInfo implements Serializable{

	private static final long serialVersionUID = -1568094910582794882L;
	
	private int id;
	private String file_name;
	private String file_type;
	private String deduct_sys_date;
	private String create_last_time;
	private String file_path;
	private int object_id;
	private String object_name;
	
	public int getObject_id() {
		return object_id;
	}
	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}
	public String getObject_name() {
		return object_name;
	}
	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getDeduct_sys_date() {
		return deduct_sys_date;
	}
	public void setDeduct_sys_date(String deduct_sys_date) {
		this.deduct_sys_date = deduct_sys_date;
	}
	public String getCreate_last_time() {
		return create_last_time;
	}
	public void setCreate_last_time(String create_last_time) {
		this.create_last_time = create_last_time;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
}
