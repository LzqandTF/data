package com.chinaebi.entity;

import java.util.Date;

public class FtpUploadRecord implements java.io.Serializable{

	private static final long serialVersionUID = 1458717670669900020L;
	
	private int id;
	private String deduct_stlm_date;
	private int object_id;
	private String object_name;
	private String upload_content;
	private Date generate_time;
	private int upload_status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(String deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
	}
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
	public String getUpload_content() {
		return upload_content;
	}
	public void setUpload_content(String upload_content) {
		this.upload_content = upload_content;
	}
	public Date getGenerate_time() {
		return generate_time;
	}
	public void setGenerate_time(Date generate_time) {
		this.generate_time = generate_time;
	}
	public int getUpload_status() {
		return upload_status;
	}
	public void setUpload_status(int upload_status) {
		this.upload_status = upload_status;
	}
}
