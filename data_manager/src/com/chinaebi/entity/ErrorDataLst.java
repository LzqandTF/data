package com.chinaebi.entity;

import java.io.Serializable;

/**
 * 差错总表实体Bean
 * 
 * @author wufei
 * 
 */
public class ErrorDataLst implements Serializable {
	private static final long serialVersionUID = 5549705119616583072L;

	private String trade_id;
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
	private int trade_amount;
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
	private int whetherRiqie;
	private String acqInstIdCode;
	private String fwdInstIdCode;
	private String deduct_rollbk_sys_time;
	private String agentId;
	private String whetherzero;
	private int whtherInnerJs;
	private String handling_time;
	private String check_time;
	private int handling_id;
	private String reason_id;
	private String handler_remark;
	private String operator;
	private String audit_operator;
	private String turnDown_remark;
	private int handling_status;
	private int error_resource;
	private String ic_card_ser_no;
	private String ic_read_and_condition;
	private String js_date;
	private int whetherQs;
	private double mer_fee;
	private int whetherTk;
	private double zf_fee;
	private String zf_file_fee;
	private int inst_type;
	private int zf_fee_bj;
	
	private int whetherAccessStance;
	private int whtherInnerDz;
	
	public int getWhtherInnerDz() {
		return whtherInnerDz;
	}
	public void setWhtherInnerDz(int whtherInnerDz) {
		this.whtherInnerDz = whtherInnerDz;
	}
	public int getWhetherAccessStance() {
		return whetherAccessStance;
	}
	public void setWhetherAccessStance(int whetherAccessStance) {
		this.whetherAccessStance = whetherAccessStance;
	}
	public int getZf_fee_bj() {
		return zf_fee_bj;
	}
	public void setZf_fee_bj(int zf_fee_bj) {
		this.zf_fee_bj = zf_fee_bj;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	private String fee_formula;
	private int bank_id;
	
	//对账文件需要的字段
	private String dzTime;
	private String dzTradeAmount;
	private String msgType;
	private String process;
	private String dzTradeType;
	private String dzTradeResult;

	// 交易类型
	private String tradeType;
	private String handling_name;
	private String reason_desc;
	private String name_;
	
	private String cdz_remark;//重对账结果
	
	public String getCdz_remark() {
		return cdz_remark;
	}
	public void setCdz_remark(String cdz_remark) {
		this.cdz_remark = cdz_remark;
	}
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
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
	public double getTrade_amount() {
		return (Double.valueOf(trade_amount)/100);
	}
	public void setTrade_amount(int trade_amount) {
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
	public int getWhetherRiqie() {
		return whetherRiqie;
	}
	public void setWhetherRiqie(int whetherRiqie) {
		this.whetherRiqie = whetherRiqie;
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
	public int getWhtherInnerJs() {
		return whtherInnerJs;
	}
	public void setWhtherInnerJs(int whtherInnerJs) {
		this.whtherInnerJs = whtherInnerJs;
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
	public String getAudit_operator() {
		return audit_operator;
	}
	public void setAudit_operator(String audit_operator) {
		this.audit_operator = audit_operator;
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
	public String getJs_date() {
		return js_date;
	}
	public void setJs_date(String js_date) {
		this.js_date = js_date;
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
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
	public String getDzTime() {
		return dzTime;
	}
	public void setDzTime(String dzTime) {
		this.dzTime = dzTime;
	}
	public String getDzTradeAmount() {
		return dzTradeAmount;
	}
	public void setDzTradeAmount(String dzTradeAmount) {
		this.dzTradeAmount = dzTradeAmount;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getDzTradeType() {
		return dzTradeType;
	}
	public void setDzTradeType(String dzTradeType) {
		this.dzTradeType = dzTradeType;
	}
	public String getDzTradeResult() {
		return dzTradeResult;
	}
	public void setDzTradeResult(String dzTradeResult) {
		this.dzTradeResult = dzTradeResult;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getHandling_name() {
		return handling_name;
	}
	public void setHandling_name(String handling_name) {
		this.handling_name = handling_name;
	}
	public String getReason_desc() {
		return reason_desc;
	}
	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}
	public String getName_() {
		if (deduct_sys_id == 0) {
			return "未知";
		} else {
			return name_;
		}
		
	}
	public void setName_(String name_) {
		this.name_ = name_;
	}
	public String getFee_formula() {
		return fee_formula;
	}
	public void setFee_formula(String fee_formula) {
		this.fee_formula = fee_formula;
	}
}
