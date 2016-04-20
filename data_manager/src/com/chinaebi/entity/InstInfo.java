package com.chinaebi.entity;

@SuppressWarnings("serial")
public class InstInfo implements java.io.Serializable {

	private int instId;
	private int whetherInnerDz;
	private String tradeDzImplClass;
	private String name;
	private int whether_inner_js;
	private int whether_apply_online_tk;
	private int active;
	private int whether_outer_error_dz;
	private int whetherOuterDz;
	private String remark;
	private String error_dz_data_tableName;
	private String error_original_data_tableName;
	private String receivi_name;
	private int inst_type;
	private String inner_clear_file_path;
	private String dz_data_column;
	private int gate;
	private int bankftp_download;
	private String bankftp_path;
	private String bankftp_ip;
	private String bankftp_port;
	private String bankftp_username;
	private String bankftp_password;
	private int whether_t_1;
	private int whether_parse_brank;
	private int bank_id; // 银行机构ID
	
	public int getInstId() {
		return instId;
	}
	public void setInstId(int instId) {
		this.instId = instId;
	}
	public int getWhetherInnerDz() {
		return whetherInnerDz;
	}
	public void setWhetherInnerDz(int whetherInnerDz) {
		this.whetherInnerDz = whetherInnerDz;
	}
	public String getTradeDzImplClass() {
		return tradeDzImplClass;
	}
	public void setTradeDzImplClass(String tradeDzImplClass) {
		this.tradeDzImplClass = tradeDzImplClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWhether_inner_js() {
		return whether_inner_js;
	}
	public void setWhether_inner_js(int whether_inner_js) {
		this.whether_inner_js = whether_inner_js;
	}
	
	public int getWhether_apply_online_tk() {
		return whether_apply_online_tk;
	}
	public void setWhether_apply_online_tk(int whether_apply_online_tk) {
		this.whether_apply_online_tk = whether_apply_online_tk;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getWhether_outer_error_dz() {
		return whether_outer_error_dz;
	}
	public void setWhether_outer_error_dz(int whether_outer_error_dz) {
		this.whether_outer_error_dz = whether_outer_error_dz;
	}
	public int getWhetherOuterDz() {
		return whetherOuterDz;
	}
	public void setWhetherOuterDz(int whetherOuterDz) {
		this.whetherOuterDz = whetherOuterDz;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getError_dz_data_tableName() {
		return error_dz_data_tableName;
	}
	public void setError_dz_data_tableName(String error_dz_data_tableName) {
		this.error_dz_data_tableName = error_dz_data_tableName;
	}
	public String getError_original_data_tableName() {
		return error_original_data_tableName;
	}
	public void setError_original_data_tableName(
			String error_original_data_tableName) {
		this.error_original_data_tableName = error_original_data_tableName;
	}
	public String getReceivi_name() {
		return receivi_name;
	}
	public void setReceivi_name(String receivi_name) {
		this.receivi_name = receivi_name;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	public String getInner_clear_file_path() {
		return inner_clear_file_path;
	}
	public void setInner_clear_file_path(String inner_clear_file_path) {
		this.inner_clear_file_path = inner_clear_file_path;
	}
	public String getDz_data_column() {
		return dz_data_column;
	}
	public void setDz_data_column(String dz_data_column) {
		this.dz_data_column = dz_data_column;
	}
	public int getGate() {
		return gate;
	}
	public void setGate(int gate) {
		this.gate = gate;
	}
	public int getBankftp_download() {
		return bankftp_download;
	}
	public void setBankftp_download(int bankftp_download) {
		this.bankftp_download = bankftp_download;
	}
	public String getBankftp_path() {
		return bankftp_path;
	}
	public void setBankftp_path(String bankftp_path) {
		this.bankftp_path = bankftp_path;
	}
	public String getBankftp_ip() {
		return bankftp_ip;
	}
	public void setBankftp_ip(String bankftp_ip) {
		this.bankftp_ip = bankftp_ip;
	}
	public String getBankftp_port() {
		return bankftp_port;
	}
	public void setBankftp_port(String bankftp_port) {
		this.bankftp_port = bankftp_port;
	}
	public String getBankftp_username() {
		return bankftp_username;
	}
	public void setBankftp_username(String bankftp_username) {
		this.bankftp_username = bankftp_username;
	}
	public String getBankftp_password() {
		return bankftp_password;
	}
	public void setBankftp_password(String bankftp_password) {
		this.bankftp_password = bankftp_password;
	}
	public int getWhether_t_1() {
		return whether_t_1;
	}
	public void setWhether_t_1(int whether_t_1) {
		this.whether_t_1 = whether_t_1;
	}
	public int getWhether_parse_brank() {
		return whether_parse_brank;
	}
	public void setWhether_parse_brank(int whether_parse_brank) {
		this.whether_parse_brank = whether_parse_brank;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
}
