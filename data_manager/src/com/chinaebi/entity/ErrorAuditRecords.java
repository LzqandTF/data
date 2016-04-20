package com.chinaebi.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author wufei
 *
 */
public class ErrorAuditRecords implements Serializable {
	private static final long serialVersionUID = 2424382089691164040L;
	
	public ErrorAuditRecords() {
	}
	
	private String trade_id;
	private String record_time;
	private String terminal_id;
	private String terminal_info;
	private int terminal_type;
	private String trade_time;
	private String out_account;
	private String out_account_type;
	private String out_acc_valid_time;
	private String out_account_info;
	private String out_account_info2;
	private String out_account_desc;
	private String in_account;
	private String in_card_name;
	private String in_bank_id;
	private String trade_amount;
	private String trade_fee;
	private int trade_currency;
	private int trade_result;
	private int req_sys_id;
	private String req_sys_stance;
	private String req_mer_code;
	private String req_mer_term_id;
	private String req_response;
	private int deduct_sys_id;
	private String deduct_sys_stance;
	private String deduct_mer_code;
	private String deduct_mer_term_id;
	private int deduct_result;
	private String deduct_sys_response;
	private int deduct_roll_bk;
	private int deduct_roll_bk_result;
	private String deduct_roll_bk_reason;
	private String deduct_roll_bk_response;
	private String deduct_roll_bk_stance;
	private int trade_type;
	private int msg_num;
	private String pass_wd_mode;
	private String req_type;
	private String req_input_type;
	private String req_time;
	private String trade_req_method;
	private String trade_desc;
	private int in_account_type;
	private String trade_other_info;
	private String deduct_stlm_date;
	private String deduct_sys_time;
	private int gain_sys_id;
	private String gain_sys_stance;
	private String gain_mer_code;
	private String gain_mer_term_id;
	private String gain_sys_response;
	private int gain_result;
	private int gain_trade_amount;
	private String gain_sys_reference;
	private String nii;
	private String authorization_code;
	private String additional_response_data;
	private String additional_data;
	private String boc;
	private String custom_define_info;
	private String original_trans_info;
	private String req_process;
	private String deduct_sys_reference;
	private int trademsg_type;
	private String deduct_rollbk_sys_reference;
	private String mer_name;
	private int bk_chk;
	private int whetherJs;
	private int whetherValid;
	private int whetherErroeHandle;
	private int whetherRiqe;
	private String acqInstIdCode;
	private String fwdInstIdCode;
	private String deduct_rollbk_sys_time;
	private String agentId;
	private String whetherzero;
	private int whetherInnerJs;
	private String handling_time;
	private String check_time;
	private int handling_id;
	private String reason_id;
	private String handler_remark;
	private String operator;
	private String turnDown_remark;
	private int handling_status;
	private int error_resource;
	private int business_type;
	private int operation_type;
	private String operator_ip;
	private String ic_card_ser_no;
	private String ic_read_and_condition;
	private int inst_type;
	
	private String name_;//扣款渠道
	private String tradeType;//交易类型
	
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	public String getRecord_time() {
		return record_time;
	}
	public void setRecord_time(String record_time) {
		this.record_time = record_time;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	public String getTerminal_info() {
		return terminal_info;
	}
	public void setTerminal_info(String terminal_info) {
		this.terminal_info = terminal_info;
	}
	public int getTerminal_type() {
		return terminal_type;
	}
	public void setTerminal_type(int terminal_type) {
		this.terminal_type = terminal_type;
	}
	public String getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(String trade_time) {
		this.trade_time = trade_time;
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
	public String getOut_acc_valid_time() {
		return out_acc_valid_time;
	}
	public void setOut_acc_valid_time(String out_acc_valid_time) {
		this.out_acc_valid_time = out_acc_valid_time;
	}
	public String getOut_account_info() {
		return out_account_info;
	}
	public void setOut_account_info(String out_account_info) {
		this.out_account_info = out_account_info;
	}
	public String getOut_account_info2() {
		return out_account_info2;
	}
	public void setOut_account_info2(String out_account_info2) {
		this.out_account_info2 = out_account_info2;
	}
	public String getOut_account_desc() {
		return out_account_desc;
	}
	public void setOut_account_desc(String out_account_desc) {
		this.out_account_desc = out_account_desc;
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
	public String getIn_bank_id() {
		return in_bank_id;
	}
	public void setIn_bank_id(String in_bank_id) {
		this.in_bank_id = in_bank_id;
	}
	public String getTrade_amount() {
		return trade_amount;
	}
	public void setTrade_amount(String trade_amount) {
		this.trade_amount = trade_amount;
	}
	public String getTrade_fee() {
		return trade_fee;
	}
	public void setTrade_fee(String trade_fee) {
		this.trade_fee = trade_fee;
	}
	public int getTrade_currency() {
		return trade_currency;
	}
	public void setTrade_currency(int trade_currency) {
		this.trade_currency = trade_currency;
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
	public String getReq_response() {
		return req_response;
	}
	public void setReq_response(String req_response) {
		this.req_response = req_response;
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
	public int getDeduct_result() {
		return deduct_result;
	}
	public void setDeduct_result(int deduct_result) {
		this.deduct_result = deduct_result;
	}
	public String getDeduct_sys_response() {
		return deduct_sys_response;
	}
	public void setDeduct_sys_response(String deduct_sys_response) {
		this.deduct_sys_response = deduct_sys_response;
	}
	public int getDeduct_roll_bk() {
		return deduct_roll_bk;
	}
	public void setDeduct_roll_bk(int deduct_roll_bk) {
		this.deduct_roll_bk = deduct_roll_bk;
	}
	public int getDeduct_roll_bk_result() {
		return deduct_roll_bk_result;
	}
	public void setDeduct_roll_bk_result(int deduct_roll_bk_result) {
		this.deduct_roll_bk_result = deduct_roll_bk_result;
	}
	public String getDeduct_roll_bk_reason() {
		return deduct_roll_bk_reason;
	}
	public void setDeduct_roll_bk_reason(String deduct_roll_bk_reason) {
		this.deduct_roll_bk_reason = deduct_roll_bk_reason;
	}
	public String getDeduct_roll_bk_response() {
		return deduct_roll_bk_response;
	}
	public void setDeduct_roll_bk_response(String deduct_roll_bk_response) {
		this.deduct_roll_bk_response = deduct_roll_bk_response;
	}
	public String getDeduct_roll_bk_stance() {
		return deduct_roll_bk_stance;
	}
	public void setDeduct_roll_bk_stance(String deduct_roll_bk_stance) {
		this.deduct_roll_bk_stance = deduct_roll_bk_stance;
	}
	public int getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(int trade_type) {
		this.trade_type = trade_type;
	}
	public int getMsg_num() {
		return msg_num;
	}
	public void setMsg_num(int msg_num) {
		this.msg_num = msg_num;
	}
	public String getPass_wd_mode() {
		return pass_wd_mode;
	}
	public void setPass_wd_mode(String pass_wd_mode) {
		this.pass_wd_mode = pass_wd_mode;
	}
	public String getReq_type() {
		return req_type;
	}
	public void setReq_type(String req_type) {
		this.req_type = req_type;
	}
	public String getReq_input_type() {
		return req_input_type;
	}
	public void setReq_input_type(String req_input_type) {
		this.req_input_type = req_input_type;
	}
	public String getReq_time() {
		return req_time;
	}
	public void setReq_time(String req_time) {
		this.req_time = req_time;
	}
	public String getTrade_req_method() {
		return trade_req_method;
	}
	public void setTrade_req_method(String trade_req_method) {
		this.trade_req_method = trade_req_method;
	}
	public String getTrade_desc() {
		return trade_desc;
	}
	public void setTrade_desc(String trade_desc) {
		this.trade_desc = trade_desc;
	}
	public int getIn_account_type() {
		return in_account_type;
	}
	public void setIn_account_type(int in_account_type) {
		this.in_account_type = in_account_type;
	}
	public String getTrade_other_info() {
		return trade_other_info;
	}
	public void setTrade_other_info(String trade_other_info) {
		this.trade_other_info = trade_other_info;
	}
	public String getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(String deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
	}
	public String getDeduct_sys_time() {
		return deduct_sys_time;
	}
	public void setDeduct_sys_time(String deduct_sys_time) {
		this.deduct_sys_time = deduct_sys_time;
	}
	public int getGain_sys_id() {
		return gain_sys_id;
	}
	public void setGain_sys_id(int gain_sys_id) {
		this.gain_sys_id = gain_sys_id;
	}
	public String getGain_sys_stance() {
		return gain_sys_stance;
	}
	public void setGain_sys_stance(String gain_sys_stance) {
		this.gain_sys_stance = gain_sys_stance;
	}
	public String getGain_mer_code() {
		return gain_mer_code;
	}
	public void setGain_mer_code(String gain_mer_code) {
		this.gain_mer_code = gain_mer_code;
	}
	public String getGain_mer_term_id() {
		return gain_mer_term_id;
	}
	public void setGain_mer_term_id(String gain_mer_term_id) {
		this.gain_mer_term_id = gain_mer_term_id;
	}
	public String getGain_sys_response() {
		return gain_sys_response;
	}
	public void setGain_sys_response(String gain_sys_response) {
		this.gain_sys_response = gain_sys_response;
	}
	public int getGain_result() {
		return gain_result;
	}
	public void setGain_result(int gain_result) {
		this.gain_result = gain_result;
	}
	public int getGain_trade_amount() {
		return gain_trade_amount;
	}
	public void setGain_trade_amount(int gain_trade_amount) {
		this.gain_trade_amount = gain_trade_amount;
	}
	public String getGain_sys_reference() {
		return gain_sys_reference;
	}
	public void setGain_sys_reference(String gain_sys_reference) {
		this.gain_sys_reference = gain_sys_reference;
	}
	public String getNii() {
		return nii;
	}
	public void setNii(String nii) {
		this.nii = nii;
	}
	public String getAuthorization_code() {
		return authorization_code;
	}
	public void setAuthorization_code(String authorization_code) {
		this.authorization_code = authorization_code;
	}
	public String getAdditional_response_data() {
		return additional_response_data;
	}
	public void setAdditional_response_data(String additional_response_data) {
		this.additional_response_data = additional_response_data;
	}
	public String getAdditional_data() {
		return additional_data;
	}
	public void setAdditional_data(String additional_data) {
		this.additional_data = additional_data;
	}
	public String getBoc() {
		return boc;
	}
	public void setBoc(String boc) {
		this.boc = boc;
	}
	public String getCustom_define_info() {
		return custom_define_info;
	}
	public void setCustom_define_info(String custom_define_info) {
		this.custom_define_info = custom_define_info;
	}
	public String getOriginal_trans_info() {
		return original_trans_info;
	}
	public void setOriginal_trans_info(String original_trans_info) {
		this.original_trans_info = original_trans_info;
	}
	public String getReq_process() {
		return req_process;
	}
	public void setReq_process(String req_process) {
		this.req_process = req_process;
	}
	public String getDeduct_sys_reference() {
		return deduct_sys_reference;
	}
	public void setDeduct_sys_reference(String deduct_sys_reference) {
		this.deduct_sys_reference = deduct_sys_reference;
	}
	public int getTrademsg_type() {
		return trademsg_type;
	}
	public void setTrademsg_type(int trademsg_type) {
		this.trademsg_type = trademsg_type;
	}
	public String getDeduct_rollbk_sys_reference() {
		return deduct_rollbk_sys_reference;
	}
	public void setDeduct_rollbk_sys_reference(String deduct_rollbk_sys_reference) {
		this.deduct_rollbk_sys_reference = deduct_rollbk_sys_reference;
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
	public int getWhetherJs() {
		return whetherJs;
	}
	public void setWhetherJs(int whetherJs) {
		this.whetherJs = whetherJs;
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
	public int getWhetherRiqe() {
		return whetherRiqe;
	}
	public void setWhetherRiqe(int whetherRiqe) {
		this.whetherRiqe = whetherRiqe;
	}
	public String getAcqInstIdCode() {
		return acqInstIdCode;
	}
	public void setAcqInstIdCode(String acqInstIdCode) {
		this.acqInstIdCode = acqInstIdCode;
	}
	public String getFwdInstIdCode() {
		return fwdInstIdCode;
	}
	public void setFwdInstIdCode(String fwdInstIdCode) {
		this.fwdInstIdCode = fwdInstIdCode;
	}
	public String getDeduct_rollbk_sys_time() {
		return deduct_rollbk_sys_time;
	}
	public void setDeduct_rollbk_sys_time(String deduct_rollbk_sys_time) {
		this.deduct_rollbk_sys_time = deduct_rollbk_sys_time;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getWhetherzero() {
		return whetherzero;
	}
	public void setWhetherzero(String whetherzero) {
		this.whetherzero = whetherzero;
	}
	public int getWhetherInnerJs() {
		return whetherInnerJs;
	}
	public void setWhetherInnerJs(int whetherInnerJs) {
		this.whetherInnerJs = whetherInnerJs;
	}
	public String getHandling_time() {
		return handling_time;
	}
	public void setHandling_time(String handling_time) {
		this.handling_time = handling_time;
	}
	public String getCheck_time() {
		return check_time;
	}
	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	public int getHandling_id() {
		return handling_id;
	}
	public void setHandling_id(int handling_id) {
		this.handling_id = handling_id;
	}
	public String getReason_id() {
		return reason_id;
	}
	public void setReason_id(String reason_id) {
		this.reason_id = reason_id;
	}
	public String getHandler_remark() {
		return handler_remark;
	}
	public void setHandler_remark(String handler_remark) {
		this.handler_remark = handler_remark;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTurnDown_remark() {
		return turnDown_remark;
	}
	public void setTurnDown_remark(String turnDown_remark) {
		this.turnDown_remark = turnDown_remark;
	}
	public int getHandling_status() {
		return handling_status;
	}
	public void setHandling_status(int handling_status) {
		this.handling_status = handling_status;
	}
	public int getError_resource() {
		return error_resource;
	}
	public void setError_resource(int error_resource) {
		this.error_resource = error_resource;
	}
	public int getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(int business_type) {
		this.business_type = business_type;
	}
	public int getOperation_type() {
		return operation_type;
	}
	public void setOperation_type(int operation_type) {
		this.operation_type = operation_type;
	}
	public String getOperator_ip() {
		return operator_ip;
	}
	public void setOperator_ip(String operator_ip) {
		this.operator_ip = operator_ip;
	}
	public String getName_() {
		return name_;
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	public String getIc_card_ser_no() {
		return ic_card_ser_no;
	}
	public void setIc_card_ser_no(String ic_card_ser_no) {
		this.ic_card_ser_no = ic_card_ser_no;
	}
	public String getIc_read_and_condition() {
		return ic_read_and_condition;
	}
	public void setIc_read_and_condition(String ic_read_and_condition) {
		this.ic_read_and_condition = ic_read_and_condition;
	}
	
	//内部差错数据
	public ErrorAuditRecords(HttpServletRequest request, ErrorDataLst errorDataLst){
		this.setTrade_id(errorDataLst.getTrade_id());
		this.setRecord_time(getCurrentTime());
		this.setTerminal_id(errorDataLst.getTerminal_id());
		this.setTerminal_info(errorDataLst.getTerminal_info());
		this.setTerminal_type(errorDataLst.getTerminal_type());
		this.setTrade_time(errorDataLst.getTrade_time());
		this.setOut_account(errorDataLst.getOut_account());
		this.setOut_account_type(errorDataLst.getOut_account_type());
		this.setOut_acc_valid_time(errorDataLst.getOut_acc_valid_time());
		this.setOut_account_info(errorDataLst.getOut_account_info());
		this.setOut_account_info2(errorDataLst.getOut_account_info2());
		this.setOut_account_desc(errorDataLst.getOut_account_desc());
		this.setIn_account(errorDataLst.getIn_account());
		this.setIn_card_name(errorDataLst.getIn_card_name());
		this.setIn_bank_id(errorDataLst.getIn_bank_id());
		this.setTrade_amount(String.valueOf(errorDataLst.getTrade_amount()));
		this.setTrade_fee(errorDataLst.getTrade_fee());
		this.setTrade_currency(errorDataLst.getTrade_currency());
		this.setTrade_result(errorDataLst.getTrade_result());
		this.setReq_sys_id(errorDataLst.getReq_sys_id());
		this.setReq_sys_stance(errorDataLst.getReq_sys_stance());
		this.setReq_mer_code(errorDataLst.getReq_mer_code());
		this.setReq_mer_term_id(errorDataLst.getReq_mer_term_id());
		this.setReq_response(errorDataLst.getReq_response());
		this.setDeduct_sys_id(errorDataLst.getDeduct_sys_id());
		this.setDeduct_sys_stance(errorDataLst.getDeduct_sys_stance());
		this.setDeduct_mer_code(errorDataLst.getDeduct_mer_code());
		this.setDeduct_mer_term_id(errorDataLst.getDeduct_mer_term_id());
		this.setDeduct_result(errorDataLst.getDeduct_result());
		this.setDeduct_sys_response(errorDataLst.getDeduct_sys_response());
		this.setDeduct_roll_bk(errorDataLst.getDeduct_roll_bk());
		this.setDeduct_roll_bk_result(errorDataLst.getDeduct_roll_bk_result());
		this.setDeduct_roll_bk_reason(errorDataLst.getDeduct_roll_bk_reason());
		this.setDeduct_roll_bk_response(errorDataLst.getDeduct_roll_bk_response());
		this.setDeduct_roll_bk_stance(errorDataLst.getDeduct_roll_bk_stance());
		this.setTrade_type(errorDataLst.getTrade_type());
		this.setMsg_num(errorDataLst.getMsg_num());
		this.setPass_wd_mode(errorDataLst.getPass_wd_mode());
		this.setReq_type(errorDataLst.getReq_type());
		this.setReq_input_type(errorDataLst.getReq_input_type());
		this.setReq_time(errorDataLst.getReq_time());
		this.setTrade_req_method(errorDataLst.getTrade_req_method());
		this.setTrade_desc(errorDataLst.getTrade_desc());
		this.setIn_account_type(errorDataLst.getIn_account_type());
		this.setTrade_other_info(errorDataLst.getTrade_other_info());
		this.setDeduct_stlm_date(errorDataLst.getDeduct_stlm_date());
		this.setDeduct_sys_time(errorDataLst.getDeduct_sys_time());
		this.setGain_sys_id(errorDataLst.getGain_sys_id());
		this.setGain_sys_stance(errorDataLst.getGain_sys_stance());
		this.setGain_mer_code(errorDataLst.getGain_mer_code());
		this.setGain_mer_term_id(errorDataLst.getGain_mer_term_id());
		this.setGain_sys_response(errorDataLst.getGain_sys_response());
		this.setGain_result(errorDataLst.getGain_result());
		this.setGain_trade_amount(errorDataLst.getGain_trade_amount());
		this.setGain_sys_reference(errorDataLst.getGain_sys_reference());
		this.setNii(errorDataLst.getNii());
		this.setAuthorization_code(errorDataLst.getAuthorization_code());
		this.setAdditional_response_data(errorDataLst.getAdditional_response_data());
		this.setAdditional_data(errorDataLst.getAdditional_data());
		this.setBoc(errorDataLst.getBoc());
		this.setCustom_define_info(errorDataLst.getCustom_define_info());
		this.setOriginal_trans_info(errorDataLst.getOriginal_trans_info());
		this.setReq_process(errorDataLst.getReq_process());
		this.setDeduct_sys_reference(errorDataLst.getDeduct_sys_reference());
		this.setTrademsg_type(errorDataLst.getTrademsg_type());
		this.setDeduct_rollbk_sys_reference(errorDataLst.getDeduct_rollbk_sys_reference());
		this.setMer_name(errorDataLst.getMer_name());
		this.setBk_chk(errorDataLst.getBk_chk());
		this.setWhetherJs(errorDataLst.getWhetherJs());
		this.setWhetherValid(errorDataLst.getWhetherValid());
		this.setWhetherErroeHandle(errorDataLst.getWhetherErroeHandle());
		this.setWhetherRiqe(errorDataLst.getWhetherRiqie());
		this.setAcqInstIdCode(errorDataLst.getAcqInstIdCode());
		this.setFwdInstIdCode(errorDataLst.getFwdInstIdCode());
		this.setDeduct_rollbk_sys_time(errorDataLst.getDeduct_rollbk_sys_time());
		this.setAgentId(errorDataLst.getAgentId());
		this.setWhetherzero(errorDataLst.getWhetherzero());
		this.setWhetherInnerJs(errorDataLst.getWhtherInnerJs());
		this.setHandling_time(errorDataLst.getHandling_time());
		this.setCheck_time(errorDataLst.getCheck_time());
		this.setHandling_id(errorDataLst.getHandling_id());
		this.setReason_id(errorDataLst.getReason_id());
		this.setHandler_remark(errorDataLst.getHandler_remark());
		this.setTurnDown_remark(errorDataLst.getTurnDown_remark());
		this.setHandling_status(errorDataLst.getHandling_status());
		this.setError_resource(errorDataLst.getError_resource());
		this.setOperator_ip(getIpAddr(request));
		this.setInst_type(errorDataLst.getInst_type());
	}
	
	//银联差错数据
	public ErrorAuditRecords(HttpServletRequest request, YlCupsErrorEntry ylCupsErrorEntry){
		this.setTrade_id(ylCupsErrorEntry.getId());
		this.setRecord_time(getCurrentTime());
		this.setTerminal_id(null);
		this.setTerminal_info(null);
//		this.setTerminal_type(null);
		this.setTrade_time(ylCupsErrorEntry.getTrade_time());
		this.setOut_account(ylCupsErrorEntry.getOut_account());
		this.setOut_account_type(null);
		this.setOut_acc_valid_time(null);
		this.setOut_account_info(null);
		this.setOut_account_info2(null);
		this.setOut_account_desc(null);
		this.setIn_account(null);
		this.setIn_card_name(null);
		this.setIn_bank_id(null);
		
		String tradeAmount = ylCupsErrorEntry.getTradeAmount();
		if (tradeAmount.contains(".")) {
			String amount = tradeAmount.replace(".", "");
			//判断小数点后面有几位数字
			int position = tradeAmount.indexOf(".");
			int one = amount.length() - position;
			if (one == 1) {
				double money = Double.valueOf(amount) / 1000;
				this.setTrade_amount(String.valueOf(money));
			} else {
				double money = Double.valueOf(amount) / 10000;
				this.setTrade_amount(String.valueOf(money));
			}
		} else {
			double money = Double.valueOf(tradeAmount) / 100;
			this.setTrade_amount(String.valueOf(money));
		}
		this.setTrade_fee(null);
//		this.setTrade_currency(null);
		this.setTrade_result(ylCupsErrorEntry.getTrade_result());
//		this.setReq_sys_id(null);
		this.setReq_sys_stance(ylCupsErrorEntry.getReqSysStance());
		this.setReq_mer_code(null);
		this.setReq_mer_term_id(null);
		this.setReq_response(null);
		this.setDeduct_sys_id(ylCupsErrorEntry.getDeduct_sys_id());
		this.setDeduct_sys_stance(ylCupsErrorEntry.getReqSysStance());
		this.setDeduct_mer_code(null);
		this.setDeduct_mer_term_id(null);
//		this.setDeduct_result(null);
//		this.setDeduct_sys_response(null);
		if (ylCupsErrorEntry.getTrade_result() == 0) {
			this.setDeduct_sys_response("00");
		} else if (ylCupsErrorEntry.getTrade_result() == 1) {
			this.setDeduct_sys_response("N1");
		} else {
			this.setDeduct_sys_response("N2");
		}
//		this.setDeduct_roll_bk(null);
//		this.setDeduct_roll_bk_result(null);
		this.setDeduct_roll_bk_reason(null);
		
		
		this.setDeduct_roll_bk_response(null);
		this.setDeduct_roll_bk_stance(ylCupsErrorEntry.getReqSysStance());
//		this.setTrade_type(null);
//		this.setMsg_num(null);
		this.setPass_wd_mode(null);
		this.setReq_type(null);
		this.setReq_input_type(null);
		this.setReq_time(null);
		this.setTrade_req_method(null);
		this.setTrade_desc(null);
//		this.setIn_account_type(null);
		this.setTrade_other_info(null);
		this.setDeduct_stlm_date(ylCupsErrorEntry.getDeductStlmDate());
		this.setDeduct_sys_time(ylCupsErrorEntry.getTrade_time());
//		this.setGain_sys_id(null);
		this.setGain_sys_stance(null);
		this.setGain_mer_code(null);
		this.setGain_mer_term_id(null);
		this.setGain_sys_response(null);
//		this.setGain_result(null);
//		this.setGain_trade_amount(null);
		this.setGain_sys_reference(null);
		this.setNii(null);
		this.setAuthorization_code(null);
		this.setAdditional_response_data(null);
		this.setAdditional_data(null);
		this.setBoc(null);
		this.setCustom_define_info(null);
		this.setOriginal_trans_info(null);
		this.setReq_process(ylCupsErrorEntry.getProcess());
		this.setDeduct_sys_reference(ylCupsErrorEntry.getDeduct_sys_reference());
		this.setTrademsg_type(ylCupsErrorEntry.getTradeMsgType());
		this.setDeduct_rollbk_sys_reference(null);
		this.setMer_name(ylCupsErrorEntry.getMer_name());
		this.setBk_chk(ylCupsErrorEntry.getBk_chk());
//		this.setWhetherJs(null);
//		this.setWhetherValid(null);
//		this.setWhetherErroeHandle(null);
//		this.setWhetherRiqe(null);
		this.setAcqInstIdCode(ylCupsErrorEntry.getAcqInstIdCode());
		this.setFwdInstIdCode(null);
		this.setDeduct_rollbk_sys_time(ylCupsErrorEntry.getTrade_time());
		this.setAgentId(null);
		this.setWhetherzero(null);
//		this.setWhetherInnerJs(null);
		this.setHandling_time(null);
		this.setCheck_time(null);
		this.setHandling_id(ylCupsErrorEntry.getHandling_id());
		this.setReason_id(ylCupsErrorEntry.getReason_code());
		this.setHandler_remark(null);
		this.setTurnDown_remark(ylCupsErrorEntry.getTurnDown_remark());
		this.setHandling_status(ylCupsErrorEntry.getTrade_status());
		this.setOperator_ip(getIpAddr(request));
	}
	
	/**
	 * 获取当前系统时间
	 * @return
	 */
	private String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return sdf.format(date);
	}
	
	public static String getIpAddr(HttpServletRequest request)
	  {
	    String ip = null;
	    Enumeration enu = request.getHeaderNames();
	    while (enu.hasMoreElements()) {
	      String name = (String)enu.nextElement();
	      if (name.equalsIgnoreCase("X-Forwarded-For")) {
	        ip = request.getHeader(name);
	      }
	      else if (name.equalsIgnoreCase("Proxy-Client-IP")) {
	        ip = request.getHeader(name);
	      }
	      else if (name.equalsIgnoreCase("WL-Proxy-Client-IP")) {
	        ip = request.getHeader(name);
	      }
	      if ((ip != null) && (ip.length() != 0))
	        break;
	    }
	    if ((ip == null) || (ip.length() == 0))
	      ip = request.getRemoteAddr();
	    return ip;
	  }
}
