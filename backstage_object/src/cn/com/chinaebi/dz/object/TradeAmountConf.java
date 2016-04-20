package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseTradeAmountConf;



public class TradeAmountConf extends BaseTradeAmountConf {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TradeAmountConf () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TradeAmountConf (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TradeAmountConf (
		java.lang.String id,
		java.lang.String process,
		java.lang.Integer trademsgType,
		java.lang.String name,
		boolean tradeMoneyStatus) {

		super (
			id,
			process,
			trademsgType,
			name,
			tradeMoneyStatus);
	}

/*[CONSTRUCTOR MARKER END]*/


}