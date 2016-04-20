package com.chinaebi.entity;

import java.io.Serializable;

/**
 * 对账总表实体Bean
 * @author wufei
 *
 */
public class ChannelDzCollect implements Serializable {
	private static final long serialVersionUID = 8733633976534577020L;
	
	private String trade_id;
	private String out_account;
	private String out_account_type;
	private String in_account;
	private String in_card_name;
	private double trade_amount;
	private String trade_fee;
	private String trade_currency;
	private String trade_currency_name;
	private int trade_result;
	private int req_sys_id;
	private String req_sys_stance;
	private String req_mer_code;
	private String req_mer_term_id;
	private int deduct_sys_id;
	private String deduct_sys_stance;
	private String deduct_mer_code;
	private String deduct_mer_term_id;
	private String deduct_sys_time;
	private String deduct_stlm_date;
	private int deduct_roll_bk;
	private String trade_type;
	private String trade_type_name;
	private String authorization_code;
	private String deduct_sys_reference;
	private String mer_name;
	private int bk_chk;
	private int whetherValid;
	private int whetherErroeHandle;
	private int whetherRiqie;
	private int whetherQs;
	private double mer_fee;
	private int whetherTk;
	private double zf_fee;
	private String zf_file_fee;
	private int zf_fee_bj;
	private String fee_formula;
	private String original_trans_info;
	private int instType;
	private int gate;
	private String settle_code;
	private String inst_name;
	private String dy_mer_name;
	private String oid;
	private String additional_response_data;
	private int sys_date;
	private int bank_id;
	private int js_date;
	private String out_user_id;
	private String in_user_id;
	private String bind_mid;
	private String totalAmount;
	private String remark;
	private String trade_time;
	
	public String getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	public String getOut_account() {
		return out_account;
	}
	public void setOut_account(String out_account) {
		this.out_account = out_account;
	}
	public String getOut_account_type() {
		return out_account_type;
	}
	public void setOut_account_type(String out_account_type) {
		this.out_account_type = out_account_type;
	}
	public String getIn_account() {
		return in_account;
	}
	public void setIn_account(String in_account) {
		this.in_account = in_account;
	}
	public String getIn_card_name() {
		return in_card_name;
	}
	public void setIn_card_name(String in_card_name) {
		this.in_card_name = in_card_name;
	}
	public double getTrade_amount() {
		return trade_amount;
	}
	public void setTrade_amount(double trade_amount) {
		this.trade_amount = trade_amount;
	}
	public String getTrade_fee() {
		return trade_fee;
	}
	public void setTrade_fee(String trade_fee) {
		this.trade_fee = trade_fee;
	}
	public String getTrade_currency() {
		return trade_currency;
	}
	public void setTrade_currency(String trade_currency) {
		this.trade_currency = trade_currency;
	}
	public String getTrade_currency_name() {
		return trade_currency_name;
	}
	public void setTrade_currency_name(String trade_currency_name) {
		this.trade_currency_name = trade_currency_name;
	}
	public int getTrade_result() {
		return trade_result;
	}
	public void setTrade_result(int trade_result) {
		this.trade_result = trade_result;
	}
	public int getReq_sys_id() {
		return req_sys_id;
	}
	public void setReq_sys_id(int req_sys_id) {
		this.req_sys_id = req_sys_id;
	}
	public String getReq_sys_stance() {
		return req_sys_stance;
	}
	public void setReq_sys_stance(String req_sys_stance) {
		this.req_sys_stance = req_sys_stance;
	}
	public String getReq_mer_code() {
		return req_mer_code;
	}
	public void setReq_mer_code(String req_mer_code) {
		this.req_mer_code = req_mer_code;
	}
	public String getReq_mer_term_id() {
		return req_mer_term_id;
	}
	public void setReq_mer_term_id(String req_mer_term_id) {
		this.req_mer_term_id = req_mer_term_id;
	}
	public int getDeduct_sys_id() {
		return deduct_sys_id;
	}
	public void setDeduct_sys_id(int deduct_sys_id) {
		this.deduct_sys_id = deduct_sys_id;
	}
	public String getDeduct_sys_stance() {
		return deduct_sys_stance;
	}
	public void setDeduct_sys_stance(String deduct_sys_stance) {
		this.deduct_sys_stance = deduct_sys_stance;
	}
	public String getDeduct_mer_code() {
		return deduct_mer_code;
	}
	public void setDeduct_mer_code(String deduct_mer_code) {
		this.deduct_mer_code = deduct_mer_code;
	}
	public String getDeduct_mer_term_id() {
		return deduct_mer_term_id;
	}
	public void setDeduct_mer_term_id(String deduct_mer_term_id) {
		this.deduct_mer_term_id = deduct_mer_term_id;
	}
	public String getDeduct_sys_time() {
		return deduct_sys_time;
	}
	public void setDeduct_sys_time(String deduct_sys_time) {
		this.deduct_sys_time = deduct_sys_time;
	}
	public String getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(String deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
	}
	public int getDeduct_roll_bk() {
		return deduct_roll_bk;
	}
	public void setDeduct_roll_bk(int deduct_roll_bk) {
		this.deduct_roll_bk = deduct_roll_bk;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTrade_type_name() {
		return trade_type_name;
	}
	public void setTrade_type_name(String trade_type_name) {
		this.trade_type_name = trade_type_name;
	}
	public String getAuthorization_code() {
		return authorization_code;
	}
	public void setAuthorization_code(String authorization_code) {
		this.authorization_code = authorization_code;
	}
	public String getDeduct_sys_reference() {
		return deduct_sys_reference;
	}
	public void setDeduct_sys_reference(String deduct_sys_reference) {
		this.deduct_sys_reference = deduct_sys_reference;
	}
	public String getMer_name() {
		return mer_name;
	}
	public void setMer_name(String mer_name) {
		this.mer_name = mer_name;
	}
	public int getBk_chk() {
		return bk_chk;
	}
	public void setBk_chk(int bk_chk) {
		this.bk_chk = bk_chk;
	}
	public int getWhetherValid() {
		return whetherValid;
	}
	public void setWhetherValid(int whetherValid) {
		this.whetherValid = whetherValid;
	}
	public int getWhetherErroeHandle() {
		return whetherErroeHandle;
	}
	public void setWhetherErroeHandle(int whetherErroeHandle) {
		this.whetherErroeHandle = whetherErroeHandle;
	}
	public int getWhetherRiqie() {
		return whetherRiqie;
	}
	public void setWhetherRiqie(int whetherRiqie) {
		this.whetherRiqie = whetherRiqie;
	}
	public int getWhetherQs() {
		return whetherQs;
	}
	public void setWhetherQs(int whetherQs) {
		this.whetherQs = whetherQs;
	}
	public double getMer_fee() {
		return mer_fee;
	}
	public void setMer_fee(double mer_fee) {
		this.mer_fee = mer_fee;
	}
	public int getWhetherTk() {
		return whetherTk;
	}
	public void setWhetherTk(int whetherTk) {
		this.whetherTk = whetherTk;
	}
	public double getZf_fee() {
		return zf_fee;
	}
	public void setZf_fee(double zf_fee) {
		this.zf_fee = zf_fee;
	}
	public String getZf_file_fee() {
		return zf_file_fee;
	}
	public void setZf_file_fee(String zf_file_fee) {
		this.zf_file_fee = zf_file_fee;
	}
	public int getZf_fee_bj() {
		return zf_fee_bj;
	}
	public void setZf_fee_bj(int zf_fee_bj) {
		this.zf_fee_bj = zf_fee_bj;
	}
	public String getFee_formula() {
		return fee_formula;
	}
	public void setFee_formula(String fee_formula) {
		this.fee_formula = fee_formula;
	}
	public String getOriginal_trans_info() {
		return original_trans_info;
	}
	public void setOriginal_trans_info(String original_trans_info) {
		this.original_trans_info = original_trans_info;
	}
	public int getInstType() {
		return instType;
	}
	public void setInstType(int instType) {
		this.instType = instType;
	}
	public int getGate() {
		return gate;
	}
	public void setGate(int gate) {
		this.gate = gate;
	}
	public String getSettle_code() {
		return settle_code;
	}
	public void setSettle_code(String settle_code) {
		this.settle_code = settle_code;
	}
	public String getInst_name() {
		return inst_name;
	}
	public void setInst_name(String inst_name) {
		this.inst_name = inst_name;
	}
	public String getDy_mer_name() {
		return dy_mer_name;
	}
	public void setDy_mer_name(String dy_mer_name) {
		this.dy_mer_name = dy_mer_name;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getAdditional_response_data() {
		return additional_response_data;
	}
	public void setAdditional_response_data(String additional_response_data) {
		this.additional_response_data = additional_response_data;
	}
	public int getSys_date() {
		return sys_date;
	}
	public void setSys_date(int sys_date) {
		this.sys_date = sys_date;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public int getJs_date() {
		return js_date;
	}
	public void setJs_date(int js_date) {
		this.js_date = js_date;
	}
	public String getOut_user_id() {
		return out_user_id;
	}
	public void setOut_user_id(String out_user_id) {
		this.out_user_id = out_user_id;
	}
	public String getIn_user_id() {
		return in_user_id;
	}
	public void setIn_user_id(String in_user_id) {
		this.in_user_id = in_user_id;
	}
	public String getBind_mid() {
		return bind_mid;
	}
	public void setBind_mid(String bind_mid) {
		this.bind_mid = bind_mid;
	}
}
