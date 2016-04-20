package com.chinaebi.entity;

public class CustomObject implements java.io.Serializable{
	
	private static final long serialVersionUID = -2113295354067473349L;
	
	private int object_id;
	private String object_name;
	private String file_address;
	private String dz_file_name;
	private String error_file_name;
	private int whether_upload;
	private String ftp_ip;
	private String ftp_address;
	private String ftp_port;
	private String ftp_username;
	private String ftp_password;
	private String file_suffix;
	private int generate_number;
	private int file_need_online_data;
	private int data_type;
	private int whether_create_error_file;
	private int whether_create_file_by_range;
	private int file_type;
	private int whether_create_file_by_inst;
	private String selectedColumn;
	
	public String getSelectedColumn() {
		return selectedColumn;
	}
	public void setSelectedColumn(String selectedColumn) {
		this.selectedColumn = selectedColumn;
	}
	public int getWhether_create_file_by_inst() {
		return whether_create_file_by_inst;
	}
	public void setWhether_create_file_by_inst(int whether_create_file_by_inst) {
		this.whether_create_file_by_inst = whether_create_file_by_inst;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}
	public int getWhether_create_file_by_range() {
		return whether_create_file_by_range;
	}
	public void setWhether_create_file_by_range(int whether_create_file_by_range) {
		this.whether_create_file_by_range = whether_create_file_by_range;
	}
	public int getWhether_create_error_file() {
		return whether_create_error_file;
	}
	public void setWhether_create_error_file(int whether_create_error_file) {
		this.whether_create_error_file = whether_create_error_file;
	}
	public int getData_type() {
		return data_type;
	}
	public void setData_type(int data_type) {
		this.data_type = data_type;
	}
	public int getFile_need_online_data() {
		return file_need_online_data;
	}
	public void setFile_need_online_data(int file_need_online_data) {
		this.file_need_online_data = file_need_online_data;
	}
	public int getGenerate_number() {
		return generate_number;
	}
	public void setGenerate_number(int generate_number) {
		this.generate_number = generate_number;
	}
	public String getFile_suffix() {
		return file_suffix;
	}
	public void setFile_suffix(String file_suffix) {
		this.file_suffix = file_suffix;
	}
	public int getWhether_upload() {
		return whether_upload;
	}
	public void setWhether_upload(int whether_upload) {
		this.whether_upload = whether_upload;
	}
	public String getFtp_ip() {
		return ftp_ip;
	}
	public void setFtp_ip(String ftp_ip) {
		this.ftp_ip = ftp_ip;
	}
	public String getFtp_address() {
		return ftp_address;
	}
	public void setFtp_address(String ftp_address) {
		this.ftp_address = ftp_address;
	}
	public String getFtp_port() {
		return ftp_port;
	}
	public void setFtp_port(String ftp_port) {
		this.ftp_port = ftp_port;
	}
	public String getFtp_username() {
		return ftp_username;
	}
	public void setFtp_username(String ftp_username) {
		this.ftp_username = ftp_username;
	}
	public String getFtp_password() {
		return ftp_password;
	}
	public void setFtp_password(String ftp_password) {
		this.ftp_password = ftp_password;
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
	public String getFile_address() {
		return file_address;
	}
	public void setFile_address(String file_address) {
		this.file_address = file_address;
	}
	public String getDz_file_name() {
		return dz_file_name;
	}
	public void setDz_file_name(String dz_file_name) {
		this.dz_file_name = dz_file_name;
	}
	public String getError_file_name() {
		return error_file_name;
	}
	public void setError_file_name(String error_file_name) {
		this.error_file_name = error_file_name;
	}
}
