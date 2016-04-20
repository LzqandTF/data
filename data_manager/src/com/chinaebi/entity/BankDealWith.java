package com.chinaebi.entity;

import java.io.Serializable;

/**
 * @author wanghui
 *
 */
public class BankDealWith implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3992017041862279603L;
	
	private String id;
	/**
	 * 线上、线下名称
	 */
	private String name;
	/**
	 * 线上、线下对应库
	 */
	private String originalDataTableName;
	/**
	 * 线上、线下标示
	 *
	 */
	private String instType;
	/**
	 * 支付金额
	 * @return
	 */
	private String sunmPayAmt;
	/**
	 * 退款金额
	 * @return
	 */
	private String sumArefundAmt;
	/**
	 * 银行手续费
	 * @return
	 */
	private String sumPayFee;
	/**
	 * 银行退回手续费
	 * @return
	 */
	private String sumArefundFee;
	/**
	 * 银行实收手续费
	 * @return
	 */
	private String bankPaidInFee;
	/**
	 * 银行划款额
	 * @return
	 */
	private String bankAmt;
	
	/**
	 * 流水号
	 * @return
	 */
	private String tseq;
	
	/**
	 * 订单号
	 * @return
	 */
	private String oid;
	
	/**
	 * 商户号
	 * @return
	 */
	private String mid;
	
	/**
	 * 商户简称
	 * @return
	 */
	private String mabbreviation;
	
	/**
	 * 交易类型
	 * @return
	 */
	private String type;
	
	/**
	 * 交易时间
	 * @return
	 */
	private String mdate;
	
	/**
	 * 交易金额
	 * @return
	 */
	private String amount;
	
	/**
	 * 银行应收手续费
	 * @return
	 */
	private String zf_file_fee;
	
	/**
	 * 银行实收手续费
	 * @return
	 */
	private String zf_fee;
	
	/**
	 * 处理码
	 * @return
	 */
	private String process;
	
	/**
	 * 交易消息类型
	 * @return
	 */
	private int tradeMsgTypeprivate; 
	
	/**
	 * 交易日期
	 * @return
	 */
	private int sys_date;
	
	/**
	 * 交易时间
	 * @return
	 */
	private int sys_time;
	
	/**
	 * 品牌服务费
	 */
	private Double ppfw_fee;
	
	
	public Double getPpfw_fee() {
		return ppfw_fee;
	}
	public void setPpfw_fee(Double ppfw_fee) {
		this.ppfw_fee = ppfw_fee;
	}
	public int getSys_date() {
		return sys_date;
	}
	public void setSys_date(int sys_date) {
		this.sys_date = sys_date;
	}
	public int getSys_time() {
		return sys_time;
	}
	public void setSys_time(int sys_time) {
		this.sys_time = sys_time;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public int getTradeMsgTypeprivate() {
		return tradeMsgTypeprivate;
	}
	public void setTradeMsgTypeprivate(int tradeMsgTypeprivate) {
		this.tradeMsgTypeprivate = tradeMsgTypeprivate;
	}
	public String getTseq() {
		return tseq;
	}
	public void setTseq(String tseq) {
		this.tseq = tseq;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMabbreviation() {
		return mabbreviation;
	}
	public void setMabbreviation(String mabbreviation) {
		this.mabbreviation = mabbreviation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMdate() {
		return mdate;
	}
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getZf_file_fee() {
		return zf_file_fee;
	}
	public void setZf_file_fee(String zf_file_fee) {
		this.zf_file_fee = zf_file_fee;
	}
	public String getZf_fee() {
		return zf_fee;
	}
	public void setZf_fee(String zf_fee) {
		this.zf_fee = zf_fee;
	}
	public String getBankPaidInFee() {
		return bankPaidInFee;
	}
	public void setBankPaidInFee(String bankPaidInFee) {
		this.bankPaidInFee = bankPaidInFee;
	}
	public String getSunmPayAmt() {
		return sunmPayAmt;
	}
	public void setSunmPayAmt(String sunmPayAmt) {
		this.sunmPayAmt = sunmPayAmt;
	}
	public String getSumArefundAmt() {
		return sumArefundAmt;
	}
	public void setSumArefundAmt(String sumArefundAmt) {
		this.sumArefundAmt = sumArefundAmt;
	}
	public String getSumPayFee() {
		return sumPayFee;
	}
	public void setSumPayFee(String sumPayFee) {
		this.sumPayFee = sumPayFee;
	}
	public String getSumArefundFee() {
		return sumArefundFee;
	}
	public void setSumArefundFee(String sumArefundFee) {
		this.sumArefundFee = sumArefundFee;
	}
	public String getBankAmt() {
		return bankAmt;
	}
	public void setBankAmt(String bankAmt) {
		this.bankAmt = bankAmt;
	}
	public String getInstType() {
		return instType;
	}
	public void setInstType(String instType) {
		this.instType = instType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalDataTableName() {
		return originalDataTableName;
	}
	public void setOriginalDataTableName(String originalDataTableName) {
		this.originalDataTableName = originalDataTableName;
	}
}
