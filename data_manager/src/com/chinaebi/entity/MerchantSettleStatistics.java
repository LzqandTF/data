package com.chinaebi.entity;

import java.io.Serializable;

/**
 * 商户结算信息实体Bean
 * @author wufei
 *
 */
public class MerchantSettleStatistics implements Serializable {
	private static final long serialVersionUID = 945325997550929317L;
	
	private int id;
	private int inst_id;
	private String mer_code;
	private int mer_type;
	private int settle_type;
	private int deduct_stlm_date;
	private String trade_amount;
	private int trade_count;
	private String refund_amount;
	private int refund_count;
	private String mer_fee;
	private String system_fee;
	private String mer_refund_fee;
	private String settle_amount;
	private int lastSettleDate;
	private String mer_name;
	private String system_refund_fee;
	private int whetherJs;
	private int data_status;
	private int inst_type;
	private String zf_fee;
	private String refund_zf_fee;
	private int bank_id;
	private int js_date;
	private int trade_gc_count;
	
	private String name_;
	private String mer_abbreviation;
	
	public int getJs_date() {
		return js_date;
	}
	public void setJs_date(int js_date) {
		this.js_date = js_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInst_id() {
		return inst_id;
	}
	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
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
	public int getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(int deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
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
	public String getMer_fee() {
		return mer_fee;
	}
	public void setMer_fee(String mer_fee) {
		this.mer_fee = mer_fee;
	}
	public String getSystem_fee() {
		return system_fee;
	}
	public void setSystem_fee(String system_fee) {
		this.system_fee = system_fee;
	}
	public String getMer_refund_fee() {
		return mer_refund_fee;
	}
	public void setMer_refund_fee(String mer_refund_fee) {
		this.mer_refund_fee = mer_refund_fee;
	}
	public String getSettle_amount() {
		return settle_amount;
	}
	public void setSettle_amount(String settle_amount) {
		this.settle_amount = settle_amount;
	}
	public int getLastSettleDate() {
		return lastSettleDate;
	}
	public void setLastSettleDate(int lastSettleDate) {
		this.lastSettleDate = lastSettleDate;
	}
	public String getMer_name() {
		return mer_name;
	}
	public void setMer_name(String mer_name) {
		this.mer_name = mer_name;
	}
	public String getSystem_refund_fee() {
		return system_refund_fee;
	}
	public void setSystem_refund_fee(String system_refund_fee) {
		this.system_refund_fee = system_refund_fee;
	}
	public int getWhetherJs() {
		return whetherJs;
	}
	public void setWhetherJs(int whetherJs) {
		this.whetherJs = whetherJs;
	}
	public int getData_status() {
		return data_status;
	}
	public void setData_status(int data_status) {
		this.data_status = data_status;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	public String getZf_fee() {
		return zf_fee;
	}
	public void setZf_fee(String zf_fee) {
		this.zf_fee = zf_fee;
	}
	public String getRefund_zf_fee() {
		return refund_zf_fee;
	}
	public void setRefund_zf_fee(String refund_zf_fee) {
		this.refund_zf_fee = refund_zf_fee;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getMer_abbreviation() {
		return mer_abbreviation;
	}
	public void setMer_abbreviation(String mer_abbreviation) {
		this.mer_abbreviation = mer_abbreviation;
	}
	public int getTrade_gc_count() {
		return trade_gc_count;
	}
	public void setTrade_gc_count(int trade_gc_count) {
		this.trade_gc_count = trade_gc_count;
	}
}
