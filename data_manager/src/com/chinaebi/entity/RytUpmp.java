package com.chinaebi.entity;

import java.io.Serializable;

public class RytUpmp implements Serializable {
	private static final long serialVersionUID = -5721880236659105207L;
	
	private String tseq;
	private int version;
	private String ip;
	private int mdate;
	private String mid;
	private String bid;
	private String oid;
	private String amount;
	private String pay_amt;
	private int type;
	private int gate;
	private String sys_date;
	private int init_sys_date;
	private String sys_time;
	private String batch;
	private String fee_amt;
	private String bank_fee;
	private int tstat;
	private int bk_flag;
	private int org_seq;
	private int ref_seq;
	private int refund_amt;
	private String mer_priv;
	private int bk_send;
	private int bk_recv;
	private String bk_url;
	private String fg_url;
	private String bk_chk;
	private int bk_date;
	private String bk_seq1;
	private String bk_seq2;
	private String bk_resp;
	private String mobile_no;
	private int trans_period;
	private String card_no;
	private String error_code;
	private int author_type;
	private String phone_no;
	private int oper_id;
	private int gid;
	private int pre_amt;
	private String bk_fee_model;
	private String pre_amt1;
	private String error_msg;
	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private String p5;
	private String p6;
	private String p7;
	private String p8;
	private String p9;
	private String p10;
	private String p11;
	private String p12;
	private String p13;
	private String p14;
	private String p15;
	private int is_liq;
	private int is_notice;
	private int data_source;
	private String currency;
	private String exchange_rate;//
	private int againPay_status;
	private int whetherJs;
	private int whetherValid;
	private int whetherErroeHandle;
	private int whetherRiqie;
	private int whetherQs;
	private double mer_fee;
	private double zf_fee;
	private double zf_file_fee;
	private int zf_fee_bj;
	private int deduct_stlm_date;
	private int whetherAccessStance;
	private int bank_id;
	private int whtherInnerJs;
	private String out_user_id;
	private String in_user_id;
	private String bind_mid;
	
	private String name;
	private int againPay_date;
	
	private String mer_abbreviation;//商户简称
	private String tradeType; // 交易类别
	
	public String getTseq() {
		return tseq;
	}
	public void setTseq(String tseq) {
		this.tseq = tseq;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getMdate() {
		return mdate;
	}
	public void setMdate(int mdate) {
		this.mdate = mdate;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public double getAmount() {
		return Double.valueOf(amount)/100;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public double getPay_amt() {
		return Double.valueOf(pay_amt)/100;
	}
	public void setPay_amt(String pay_amt) {
		this.pay_amt = pay_amt;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getGate() {
		return gate;
	}
	public void setGate(int gate) {
		this.gate = gate;
	}
	public String getSys_date() {
		return sys_date;
	}
	public void setSys_date(String sys_date) {
		this.sys_date = sys_date;
	}
	public int getInit_sys_date() {
		return init_sys_date;
	}
	public void setInit_sys_date(int init_sys_date) {
		this.init_sys_date = init_sys_date;
	}
	public String getSys_time() {
		return sys_time;
	}
	public void setSys_time(String sys_time) {
		this.sys_time = sys_time;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public double getFee_amt() {
		return Double.valueOf(fee_amt)/100;
	}
	public void setFee_amt(String fee_amt) {
		this.fee_amt = fee_amt;
	}
	public double getBank_fee() {
		return Double.valueOf(bank_fee)/100;
	}
	public void setBank_fee(String bank_fee) {
		this.bank_fee = bank_fee;
	}
	public int getTstat() {
		return tstat;
	}
	public void setTstat(int tstat) {
		this.tstat = tstat;
	}
	public int getBk_flag() {
		return bk_flag;
	}
	public void setBk_flag(int bk_flag) {
		this.bk_flag = bk_flag;
	}
	public int getOrg_seq() {
		return org_seq;
	}
	public void setOrg_seq(int org_seq) {
		this.org_seq = org_seq;
	}
	public int getRef_seq() {
		return ref_seq;
	}
	public void setRef_seq(int ref_seq) {
		this.ref_seq = ref_seq;
	}
	public int getRefund_amt() {
		return refund_amt;
	}
	public void setRefund_amt(int refund_amt) {
		this.refund_amt = refund_amt;
	}
	public String getMer_priv() {
		return mer_priv;
	}
	public void setMer_priv(String mer_priv) {
		this.mer_priv = mer_priv;
	}
	public int getBk_send() {
		return bk_send;
	}
	public void setBk_send(int bk_send) {
		this.bk_send = bk_send;
	}
	public int getBk_recv() {
		return bk_recv;
	}
	public void setBk_recv(int bk_recv) {
		this.bk_recv = bk_recv;
	}
	public String getBk_url() {
		return bk_url;
	}
	public void setBk_url(String bk_url) {
		this.bk_url = bk_url;
	}
	public String getFg_url() {
		return fg_url;
	}
	public void setFg_url(String fg_url) {
		this.fg_url = fg_url;
	}
	public String getBk_chk() {
		return bk_chk;
	}
	public void setBk_chk(String bk_chk) {
		this.bk_chk = bk_chk;
	}
	public int getBk_date() {
		return bk_date;
	}
	public void setBk_date(int bk_date) {
		this.bk_date = bk_date;
	}
	public String getBk_seq1() {
		return bk_seq1;
	}
	public void setBk_seq1(String bk_seq1) {
		this.bk_seq1 = bk_seq1;
	}
	public String getBk_seq2() {
		return bk_seq2;
	}
	public void setBk_seq2(String bk_seq2) {
		this.bk_seq2 = bk_seq2;
	}
	public String getBk_resp() {
		return bk_resp;
	}
	public void setBk_resp(String bk_resp) {
		this.bk_resp = bk_resp;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public int getTrans_period() {
		return trans_period;
	}
	public void setTrans_period(int trans_period) {
		this.trans_period = trans_period;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public int getAuthor_type() {
		return author_type;
	}
	public void setAuthor_type(int author_type) {
		this.author_type = author_type;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public int getOper_id() {
		return oper_id;
	}
	public void setOper_id(int oper_id) {
		this.oper_id = oper_id;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getPre_amt() {
		return pre_amt;
	}
	public void setPre_amt(int pre_amt) {
		this.pre_amt = pre_amt;
	}
	public String getBk_fee_model() {
		return bk_fee_model;
	}
	public void setBk_fee_model(String bk_fee_model) {
		this.bk_fee_model = bk_fee_model;
	}
	public double getPre_amt1() {
		return Double.valueOf(pre_amt1)/100;
	}
	public void setPre_amt1(String pre_amt1) {
		this.pre_amt1 = pre_amt1;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getP3() {
		return p3;
	}
	public void setP3(String p3) {
		this.p3 = p3;
	}
	public String getP4() {
		return p4;
	}
	public void setP4(String p4) {
		this.p4 = p4;
	}
	public String getP5() {
		return p5;
	}
	public void setP5(String p5) {
		this.p5 = p5;
	}
	public String getP6() {
		return p6;
	}
	public void setP6(String p6) {
		this.p6 = p6;
	}
	public String getP7() {
		return p7;
	}
	public void setP7(String p7) {
		this.p7 = p7;
	}
	public String getP8() {
		return p8;
	}
	public void setP8(String p8) {
		this.p8 = p8;
	}
	public String getP9() {
		return p9;
	}
	public void setP9(String p9) {
		this.p9 = p9;
	}
	public String getP10() {
		return p10;
	}
	public void setP10(String p10) {
		this.p10 = p10;
	}
	public String getP11() {
		return p11;
	}
	public void setP11(String p11) {
		this.p11 = p11;
	}
	public String getP12() {
		return p12;
	}
	public void setP12(String p12) {
		this.p12 = p12;
	}
	public String getP13() {
		return p13;
	}
	public void setP13(String p13) {
		this.p13 = p13;
	}
	public String getP14() {
		return p14;
	}
	public void setP14(String p14) {
		this.p14 = p14;
	}
	public String getP15() {
		return p15;
	}
	public void setP15(String p15) {
		this.p15 = p15;
	}
	public int getIs_liq() {
		return is_liq;
	}
	public void setIs_liq(int is_liq) {
		this.is_liq = is_liq;
	}
	public int getIs_notice() {
		return is_notice;
	}
	public void setIs_notice(int is_notice) {
		this.is_notice = is_notice;
	}
	public int getData_source() {
		return data_source;
	}
	public void setData_source(int data_source) {
		this.data_source = data_source;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getExchange_rate() {
		return exchange_rate;
	}
	public void setExchange_rate(String exchange_rate) {
		this.exchange_rate = exchange_rate;
	}
	public int getAgainPay_status() {
		return againPay_status;
	}
	public void setAgainPay_status(int againPay_status) {
		this.againPay_status = againPay_status;
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
	public double getZf_fee() {
		return zf_fee;
	}
	public void setZf_fee(double zf_fee) {
		this.zf_fee = zf_fee;
	}
	public double getZf_file_fee() {
		return zf_file_fee;
	}
	public void setZf_file_fee(double zf_file_fee) {
		this.zf_file_fee = zf_file_fee;
	}
	public int getZf_fee_bj() {
		return zf_fee_bj;
	}
	public void setZf_fee_bj(int zf_fee_bj) {
		this.zf_fee_bj = zf_fee_bj;
	}
	public void setWhetherRiqie(int whetherRiqie) {
		this.whetherRiqie = whetherRiqie;
	}
	public int getAgainPay_date() {
		return againPay_date;
	}
	public void setAgainPay_date(int againPay_date) {
		this.againPay_date = againPay_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMer_abbreviation() {
		return mer_abbreviation;
	}
	public void setMer_abbreviation(String mer_abbreviation) {
		this.mer_abbreviation = mer_abbreviation;
	}
	public int getDeduct_stlm_date() {
		return deduct_stlm_date;
	}
	public void setDeduct_stlm_date(int deduct_stlm_date) {
		this.deduct_stlm_date = deduct_stlm_date;
	}
	public int getWhetherAccessStance() {
		return whetherAccessStance;
	}
	public void setWhetherAccessStance(int whetherAccessStance) {
		this.whetherAccessStance = whetherAccessStance;
	}
	public int getBank_id() {
		return bank_id;
	}
	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}
	public int getWhtherInnerJs() {
		return whtherInnerJs;
	}
	public void setWhtherInnerJs(int whtherInnerJs) {
		this.whtherInnerJs = whtherInnerJs;
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
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
}
