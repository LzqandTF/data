package com.chinaebi.entity;

import java.io.Serializable;

/**
 * 商户资金结算信息实体Bean
 * @author wufei
 *
 */
public class MerchantFundSettle implements Serializable {
	private static final long serialVersionUID = -8616270230872027045L;
	
	private int id;
	private String mer_code;
	private int mer_type;
	private int settle_type;
	private String sys_batch_no;
	private String mer_batch_no;
	private int start_date;
	private int end_date;
	private int create_tab_date;
	private String mer_name;
	private String trade_amount;
	private int trade_count;
	private String refund_amount;
	private int refund_count;
	private String system_fee;
	private String refund_mer_fee;
	private String open_bank_name;
	private String open_acount_name;
	private String open_account_code;
	private String settle_amount;
	private int settle_way;
	private int settle_state;
	private String totalMoney;
	private int settle_date;
	private int settle_confirm_date;
	private int bil_manual;
	private String mer_fee;
	private String system_refund_fee;
	private String bil_bank;
	private String error_msg;
	private String rec_amount_add;
	private int rec_amount_add_count;
	private String rec_amount_sub;
	private int rec_amount_sub_count;
	private int whtherFz;
	private int syn_result;
	private int syn_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMer_code() {
		return mer_code;
	}
	public void setMer_code(String mer_code) {
		this.mer_code = mer_code;
	}
	public int getMer_type() {
		return mer_type;
	}
	public void setMer_type(int mer_type) {
		this.mer_type = mer_type;
	}
	public int getSettle_type() {
		return settle_type;
	}
	public void setSettle_type(int settle_type) {
		this.settle_type = settle_type;
	}
	public String getSys_batch_no() {
		return sys_batch_no;
	}
	public void setSys_batch_no(String sys_batch_no) {
		this.sys_batch_no = sys_batch_no;
	}
	public String getMer_batch_no() {
		return mer_batch_no;
	}
	public void setMer_batch_no(String mer_batch_no) {
		this.mer_batch_no = mer_batch_no;
	}
	public int getStart_date() {
		return start_date;
	}
	public void setStart_date(int start_date) {
		this.start_date = start_date;
	}
	public int getEnd_date() {
		return end_date;
	}
	public void setEnd_date(int end_date) {
		this.end_date = end_date;
	}
	public int getCreate_tab_date() {
		return create_tab_date;
	}
	public void setCreate_tab_date(int create_tab_date) {
		this.create_tab_date = create_tab_date;
	}
	public String getMer_name() {
		return mer_name;
	}
	public void setMer_name(String mer_name) {
		this.mer_name = mer_name;
	}
	public String getTrade_amount() {
		return trade_amount;
	}
	public void setTrade_amount(String trade_amount) {
		this.trade_amount = trade_amount;
	}
	public int getTrade_count() {
		return trade_count;
	}
	public void setTrade_count(int trade_count) {
		this.trade_count = trade_count;
	}
	public String getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
	}
	public int getRefund_count() {
		return refund_count;
	}
	public void setRefund_count(int refund_count) {
		this.refund_count = refund_count;
	}
	public String getSystem_fee() {
		return system_fee;
	}
	public void setSystem_fee(String system_fee) {
		this.system_fee = system_fee;
	}
	public String getRefund_mer_fee() {
		return refund_mer_fee;
	}
	public void setRefund_mer_fee(String refund_mer_fee) {
		this.refund_mer_fee = refund_mer_fee;
	}
	public String getOpen_bank_name() {
		return open_bank_name;
	}
	public void setOpen_bank_name(String open_bank_name) {
		this.open_bank_name = open_bank_name;
	}
	public String getOpen_acount_name() {
		return open_acount_name;
	}
	public void setOpen_acount_name(String open_acount_name) {
		this.open_acount_name = open_acount_name;
	}
	public String getOpen_account_code() {
		return open_account_code;
	}
	public void setOpen_account_code(String open_account_code) {
		this.open_account_code = open_account_code;
	}
	public String getSettle_amount() {
		return settle_amount;
	}
	public void setSettle_amount(String settle_amount) {
		this.settle_amount = settle_amount;
	}
	public int getSettle_way() {
		return settle_way;
	}
	public void setSettle_way(int settle_way) {
		this.settle_way = settle_way;
	}
	public int getSettle_state() {
		return settle_state;
	}
	public void setSettle_state(int settle_state) {
		this.settle_state = settle_state;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public int getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(int settle_date) {
		this.settle_date = settle_date;
	}
	public int getSettle_confirm_date() {
		return settle_confirm_date;
	}
	public void setSettle_confirm_date(int settle_confirm_date) {
		this.settle_confirm_date = settle_confirm_date;
	}
	public int getBil_manual() {
		return bil_manual;
	}
	public void setBil_manual(int bil_manual) {
		this.bil_manual = bil_manual;
	}
	public String getMer_fee() {
		return mer_fee;
	}
	public void setMer_fee(String mer_fee) {
		this.mer_fee = mer_fee;
	}
	public String getSystem_refund_fee() {
		return system_refund_fee;
	}
	public void setSystem_refund_fee(String system_refund_fee) {
		this.system_refund_fee = system_refund_fee;
	}
	public String getBil_bank() {
		return bil_bank;
	}
	public void setBil_bank(String bil_bank) {
		this.bil_bank = bil_bank;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getRec_amount_add() {
		return rec_amount_add;
	}
	public void setRec_amount_add(String rec_amount_add) {
		this.rec_amount_add = rec_amount_add;
	}
	public int getRec_amount_add_count() {
		return rec_amount_add_count;
	}
	public void setRec_amount_add_count(int rec_amount_add_count) {
		this.rec_amount_add_count = rec_amount_add_count;
	}
	public String getRec_amount_sub() {
		return rec_amount_sub;
	}
	public void setRec_amount_sub(String rec_amount_sub) {
		this.rec_amount_sub = rec_amount_sub;
	}
	public int getRec_amount_sub_count() {
		return rec_amount_sub_count;
	}
	public void setRec_amount_sub_count(int rec_amount_sub_count) {
		this.rec_amount_sub_count = rec_amount_sub_count;
	}
	public int getWhtherFz() {
		return whtherFz;
	}
	public void setWhtherFz(int whtherFz) {
		this.whtherFz = whtherFz;
	}
	public int getSyn_result() {
		return syn_result;
	}
	public void setSyn_result(int syn_result) {
		this.syn_result = syn_result;
	}
	public int getSyn_date() {
		return syn_date;
	}
	public void setSyn_date(int syn_date) {
		this.syn_date = syn_date;
	}
}
