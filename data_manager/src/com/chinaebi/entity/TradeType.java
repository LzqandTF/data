package com.chinaebi.entity;

import java.io.Serializable;

public class TradeType implements Serializable {
	private static final long serialVersionUID = -2741404815863030116L;
	
	private String trade_code;
	private String trade_name;
	private String trade_type_category_id;
	
	public String getTrade_code() {
		return trade_code;
	}
	public void setTrade_code(String trade_code) {
		this.trade_code = trade_code;
	}
	public String getTrade_name() {
		return trade_name;
	}
	public void setTrade_name(String trade_name) {
		this.trade_name = trade_name;
	}
	public String getTrade_type_category_id() {
		return trade_type_category_id;
	}
	public void setTrade_type_category_id(String trade_type_category_id) {
		this.trade_type_category_id = trade_type_category_id;
	}
}

