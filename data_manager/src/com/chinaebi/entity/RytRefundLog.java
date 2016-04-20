package com.chinaebi.entity;

import java.io.Serializable;

import com.chinaebi.utils.StringUtils;

public class RytRefundLog implements Serializable {
	private static final long serialVersionUID = 6367752659860170367L;
	
	private int id;
	private String tseq;
	private int mdate;
	private String mid;
	private String oid;
	private String org_mdate;
	private String org_oid;
	private String ref_amt;
	private int sys_date;
	private int gate;
	private String card_no;
	private String user_name;
	private int req_date;
	private int pro_date;
	private String ref_date;
	private int stat;
	private String reason;
	private String etro_reason;
	private String refund_reason;
	private String batch;
	private String bgRetUrl;
	private int vstate;
	private int author_type;
	private String mer_fee;
	private String bk_fee;
	private int bk_fee_real;
	private String org_amt;
	private String org_bk_seq;
	private int gid;
	private int pre_amt;
	private String org_pay_amt;
	private String pre_amt1;
	private String mer_priv;
	private String p1;
	private String online_refund_id;
	private int online_refund_state;
	private String online_refund_reason;
	private int refund_type;
	private int sys_time;
	private int bk_chk;
	private int whetherJs;
	private int whetherValid;
	private int whetherErroeHandle;
	private int whetherRiqie;
	private int whetherQs;
	private double tk_mer_fee;
	
	private String name;
	
	private String mer_abbreviation;
	
	private String bankName;//银行网关名称
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTseq() {
		return tseq;
	}
	public void setTseq(String tseq) {
		this.tseq = tseq;
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
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrg_mdate() {
		return org_mdate;
	}
	public void setOrg_mdate(String org_mdate) {
		this.org_mdate = org_mdate;
	}
	public String getOrg_oid() {
		return org_oid;
	}
	public void setOrg_oid(String org_oid) {
		this.org_oid = org_oid;
	}
	public double getRef_amt() {
		return StringUtils.isBlank(ref_amt) ? 0.00 : Double.valueOf(ref_amt)/100;
	}
	public void setRef_amt(String ref_amt) {
		this.ref_amt = ref_amt;
	}
	public int getSys_date() {
		return sys_date;
	}
	public void setSys_date(int sys_date) {
		this.sys_date = sys_date;
	}
	public int getGate() {
		return gate;
	}
	public void setGate(int gate) {
		this.gate = gate;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getReq_date() {
		return req_date;
	}
	public void setReq_date(int req_date) {
		this.req_date = req_date;
	}
	public int getPro_date() {
		return pro_date;
	}
	public void setPro_date(int pro_date) {
		this.pro_date = pro_date;
	}
	public String getRef_date() {
		return ref_date;
	}
	public void setRef_date(String ref_date) {
		this.ref_date = ref_date;
	}
	public int getStat() {
		return stat;
	}
	public void setStat(int stat) {
		this.stat = stat;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEtro_reason() {
		return etro_reason;
	}
	public void setEtro_reason(String etro_reason) {
		this.etro_reason = etro_reason;
	}
	public String getRefund_reason() {
		return refund_reason;
	}
	public void setRefund_reason(String refund_reason) {
		this.refund_reason = refund_reason;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getBgRetUrl() {
		return bgRetUrl;
	}
	public void setBgRetUrl(String bgRetUrl) {
		this.bgRetUrl = bgRetUrl;
	}
	public int getVstate() {
		return vstate;
	}
	public void setVstate(int vstate) {
		this.vstate = vstate;
	}
	public int getAuthor_type() {
		return author_type;
	}
	public void setAuthor_type(int author_type) {
		this.author_type = author_type;
	}
	public double getMer_fee() {
		return StringUtils.isBlank(mer_fee) ? 0.00 : Double.valueOf(mer_fee)/100;
	}
	public void setMer_fee(String mer_fee) {
		this.mer_fee = mer_fee;
	}
	public double getBk_fee() {
		return StringUtils.isBlank(bk_fee) ? 0.00 : Double.valueOf(bk_fee)/100;
	}
	public void setBk_fee(String bk_fee) {
		this.bk_fee = bk_fee;
	}
	public int getBk_fee_real() {
		return bk_fee_real;
	}
	public void setBk_fee_real(int bk_fee_real) {
		this.bk_fee_real = bk_fee_real;
	}
	public double getOrg_amt() {
		return StringUtils.isBlank(org_amt) ? 0.00 : Double.valueOf(org_amt)/100;
	}
	public void setOrg_amt(String org_amt) {
		this.org_amt = org_amt;
	}
	public String getOrg_bk_seq() {
		return org_bk_seq;
	}
	public void setOrg_bk_seq(String org_bk_seq) {
		this.org_bk_seq = org_bk_seq;
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
	public double getOrg_pay_amt() {
		return StringUtils.isBlank(org_pay_amt) ? 0.00 : Double.valueOf(org_pay_amt)/100;
	}
	public void setOrg_pay_amt(String org_pay_amt) {
		this.org_pay_amt = org_pay_amt;
	}
	public double getPre_amt1() {
		return StringUtils.isBlank(pre_amt1) ? 0.00 : Double.valueOf(pre_amt1)/100;
	}
	public void setPre_amt1(String pre_amt1) {
		this.pre_amt1 = pre_amt1;
	}
	public String getMer_priv() {
		return mer_priv;
	}
	public void setMer_priv(String mer_priv) {
		this.mer_priv = mer_priv;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getOnline_refund_id() {
		return online_refund_id;
	}
	public void setOnline_refund_id(String online_refund_id) {
		this.online_refund_id = online_refund_id;
	}
	public int getOnline_refund_state() {
		return online_refund_state;
	}
	public void setOnline_refund_state(int online_refund_state) {
		this.online_refund_state = online_refund_state;
	}
	public String getOnline_refund_reason() {
		return online_refund_reason;
	}
	public void setOnline_refund_reason(String online_refund_reason) {
		this.online_refund_reason = online_refund_reason;
	}
	public int getRefund_type() {
		return refund_type;
	}
	public void setRefund_type(int refund_type) {
		this.refund_type = refund_type;
	}
	public int getSys_time() {
		return sys_time;
	}
	public void setSys_time(int sys_time) {
		this.sys_time = sys_time;
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
	public int getWhetherQs() {
		return whetherQs;
	}
	public void setWhetherQs(int whetherQs) {
		this.whetherQs = whetherQs;
	}
	public double getTk_mer_fee() {
		return tk_mer_fee;
	}
	public void setTk_mer_fee(double tk_mer_fee) {
		this.tk_mer_fee = tk_mer_fee;
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
}
