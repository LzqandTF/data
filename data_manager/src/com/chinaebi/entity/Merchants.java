package com.chinaebi.entity;

import java.io.Serializable;

/**
 * 商户基本信息、商户结算信息
 * @author wufei
 *
 */
public class Merchants implements Serializable {
	private static final long serialVersionUID = 5778492559884114579L;
	
	// 商户基本信息
	private String mer_code;
	private String mer_name;
	private int mer_category;
	private String mer_abbreviation;
	private int mer_state;
	private String city;
	private int mer_type;
	private int startDate;
	private int endDate;
	private String channel;
	private String expand;
	private String expandno;
	private String provinces;
	
	// 商户结算信息
	private int bil_object;
	private String bil_bank;	
	private String bil_bankname;
	private String bil_accountname;
	private String bil_bankaccount;
	private int bil_way;
	private String bil_smallamt;
	private int bil_cycle;
	private int bil_manual;
	private String bil_account;
	private int bil_type;
	private String mer_poundage;
	private int bil_status;
	private int last_liq_date;
	private String bank_branch;
	private int refund_fee;
	private int whtherFz;
	
	private String mer_merchants;
	private String provincesandcity;
	
	private int lastSettleDate;
	private int merchantTotalCount;
	
	private String mer_balance;

	public String getMer_code() {
		return mer_code;
	}

	public void setMer_code(String mer_code) {
		this.mer_code = mer_code;
	}

	public String getMer_name() {
		return mer_name;
	}

	public void setMer_name(String mer_name) {
		this.mer_name = mer_name;
	}

	public int getMer_category() {
		return mer_category;
	}

	public void setMer_category(int mer_category) {
		this.mer_category = mer_category;
	}

	public String getMer_abbreviation() {
		return mer_abbreviation;
	}

	public void setMer_abbreviation(String mer_abbreviation) {
		this.mer_abbreviation = mer_abbreviation;
	}

	public int getMer_state() {
		return mer_state;
	}

	public void setMer_state(int mer_state) {
		this.mer_state = mer_state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getMer_type() {
		return mer_type;
	}

	public void setMer_type(int mer_type) {
		this.mer_type = mer_type;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}

	public String getExpandno() {
		return expandno;
	}

	public void setExpandno(String expandno) {
		this.expandno = expandno;
	}

	public String getProvinces() {
		return provinces;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public int getBil_object() {
		return bil_object;
	}

	public void setBil_object(int bil_object) {
		this.bil_object = bil_object;
	}

	public String getBil_bank() {
		return bil_bank;
	}

	public void setBil_bank(String bil_bank) {
		this.bil_bank = bil_bank;
	}

	public String getBil_bankname() {
		return bil_bankname;
	}

	public void setBil_bankname(String bil_bankname) {
		this.bil_bankname = bil_bankname;
	}

	public String getBil_accountname() {
		return bil_accountname;
	}

	public void setBil_accountname(String bil_accountname) {
		this.bil_accountname = bil_accountname;
	}

	public String getBil_bankaccount() {
		return bil_bankaccount;
	}

	public void setBil_bankaccount(String bil_bankaccount) {
		this.bil_bankaccount = bil_bankaccount;
	}

	public int getBil_way() {
		return bil_way;
	}

	public void setBil_way(int bil_way) {
		this.bil_way = bil_way;
	}

	public String getBil_smallamt() {
		return bil_smallamt;
	}

	public void setBil_smallamt(String bil_smallamt) {
		this.bil_smallamt = bil_smallamt;
	}

	public int getBil_cycle() {
		return bil_cycle;
	}

	public void setBil_cycle(int bil_cycle) {
		this.bil_cycle = bil_cycle;
	}

	public int getBil_manual() {
		return bil_manual;
	}

	public void setBil_manual(int bil_manual) {
		this.bil_manual = bil_manual;
	}

	public String getBil_account() {
		return bil_account;
	}

	public void setBil_account(String bil_account) {
		this.bil_account = bil_account;
	}

	public int getBil_type() {
		return bil_type;
	}

	public void setBil_type(int bil_type) {
		this.bil_type = bil_type;
	}

	public String getMer_poundage() {
		return mer_poundage;
	}

	public void setMer_poundage(String mer_poundage) {
		this.mer_poundage = mer_poundage;
	}

	public int getBil_status() {
		return bil_status;
	}

	public void setBil_status(int bil_status) {
		this.bil_status = bil_status;
	}

	public int getLast_liq_date() {
		return last_liq_date;
	}

	public void setLast_liq_date(int last_liq_date) {
		this.last_liq_date = last_liq_date;
	}

	public String getBank_branch() {
		return bank_branch;
	}

	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}

	public int getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

	public int getWhtherFz() {
		return whtherFz;
	}

	public void setWhtherFz(int whtherFz) {
		this.whtherFz = whtherFz;
	}

	public String getMer_merchants() {
		return mer_merchants;
	}

	public void setMer_merchants(String mer_merchants) {
		this.mer_merchants = mer_merchants;
	}

	public String getProvincesandcity() {
		return provincesandcity;
	}

	public void setProvincesandcity(String provincesandcity) {
		this.provincesandcity = provincesandcity;
	}

	public int getLastSettleDate() {
		return lastSettleDate;
	}

	public void setLastSettleDate(int lastSettleDate) {
		this.lastSettleDate = lastSettleDate;
	}

	public int getMerchantTotalCount() {
		return merchantTotalCount;
	}

	public void setMerchantTotalCount(int merchantTotalCount) {
		this.merchantTotalCount = merchantTotalCount;
	}

	public String getMer_balance() {
		return mer_balance;
	}

	public void setMer_balance(String mer_balance) {
		this.mer_balance = mer_balance;
	}
}
