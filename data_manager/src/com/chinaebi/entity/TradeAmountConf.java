package com.chinaebi.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TradeAmountConf implements Serializable {
	
	private String id;
	private String process;
	private int trademsgType;
	private String name;
	private int tradeMoneyStatus;
	public TradeAmountConf() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public int getTrademsgType() {
		return trademsgType;
	}
	public void setTrademsgType(int trademsgType) {
		this.trademsgType = trademsgType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTradeMoneyStatus() {
		return tradeMoneyStatus;
	}
	public void setTradeMoneyStatus(int tradeMoneyStatus) {
		this.tradeMoneyStatus = tradeMoneyStatus;
	}
	
	
	
	

}
