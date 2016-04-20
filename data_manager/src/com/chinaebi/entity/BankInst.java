package com.chinaebi.entity;

import java.io.Serializable;

public class BankInst implements Serializable {
	private static final long serialVersionUID = 397314020477184743L;
	
	private int bank_id;
	private String bank_name;
	private String parse_dz_file_class;
	private String trade_dz_impl_class;
	private String dz_data_tableName;
	private String ftp_dz_file_path;
	private String dz_file_name_pattern;
	private String dz_file_path;
	private String original_data_tableName;
	private String riqie_original_tableName;
	private String tk_tableName;
	private String inst_entity_name;
	private String bank_entity_name;
	private int start_row;
	private int isTk;
	private int tk_type;
	private String tk_context;
	private int tk_column;
	private int bank_type;
	private int whether_outer_dz;
	
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getParse_dz_file_class() {
		return parse_dz_file_class;
	}
	public void setParse_dz_file_class(String parse_dz_file_class) {
		this.parse_dz_file_class = parse_dz_file_class;
	}
	public String getTrade_dz_impl_class() {
		return trade_dz_impl_class;
	}
	public void setTrade_dz_impl_class(String trade_dz_impl_class) {
		this.trade_dz_impl_class = trade_dz_impl_class;
	}
	public String getDz_data_tableName() {
		return dz_data_tableName;
	}
	public void setDz_data_tableName(String dz_data_tableName) {
		this.dz_data_tableName = dz_data_tableName;
	}
	public String getFtp_dz_file_path() {
		return ftp_dz_file_path;
	}
	public void setFtp_dz_file_path(String ftp_dz_file_path) {
		this.ftp_dz_file_path = ftp_dz_file_path;
	}
	public String getDz_file_name_pattern() {
		return dz_file_name_pattern;
	}
	public void setDz_file_name_pattern(String dz_file_name_pattern) {
		this.dz_file_name_pattern = dz_file_name_pattern;
	}
	public String getDz_file_path() {
		return dz_file_path;
	}
	public void setDz_file_path(String dz_file_path) {
		this.dz_file_path = dz_file_path;
	}
	public String getOriginal_data_tableName() {
		return original_data_tableName;
	}
	public void setOriginal_data_tableName(String original_data_tableName) {
		this.original_data_tableName = original_data_tableName;
	}
	public String getRiqie_original_tableName() {
		return riqie_original_tableName;
	}
	public void setRiqie_original_tableName(String riqie_original_tableName) {
		this.riqie_original_tableName = riqie_original_tableName;
	}
	public String getTk_tableName() {
		return tk_tableName;
	}
	public void setTk_tableName(String tk_tableName) {
		this.tk_tableName = tk_tableName;
	}
	public String getInst_entity_name() {
		return inst_entity_name;
	}
	public void setInst_entity_name(String inst_entity_name) {
		this.inst_entity_name = inst_entity_name;
	}
	public String getBank_entity_name() {
		return bank_entity_name;
	}
	public void setBank_entity_name(String bank_entity_name) {
		this.bank_entity_name = bank_entity_name;
	}
	public int getStart_row() {
		return start_row;
	}
	public void setStart_row(int start_row) {
		this.start_row = start_row;
	}
	public int getIsTk() {
		return isTk;
	}
	public void setIsTk(int isTk) {
		this.isTk = isTk;
	}
	public int getTk_type() {
		return tk_type;
	}
	public void setTk_type(int tk_type) {
		this.tk_type = tk_type;
	}
	public String getTk_context() {
		return tk_context;
	}
	public void setTk_context(String tk_context) {
		this.tk_context = tk_context;
	}
	public int getTk_column() {
		return tk_column;
	}
	public void setTk_column(int tk_column) {
		this.tk_column = tk_column;
	}
	public int getBank_type() {
		return bank_type;
	}
	public void setBank_type(int bank_type) {
		this.bank_type = bank_type;
	}
	public int getWhether_outer_dz() {
		return whether_outer_dz;
	}
	public void setWhether_outer_dz(int whether_outer_dz) {
		this.whether_outer_dz = whether_outer_dz;
	}
	
}
