package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseTradeLst;



public class TradeLst extends BaseTradeLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TradeLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TradeLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TradeLst (
		java.lang.String id,
		java.util.Date tradeTime,
		java.util.Date outAccValidTime,
		java.lang.Integer tradeType,
		java.util.Date reqTime,
		java.util.Date deductStlmDate,
		java.util.Date deductSysTime,
		java.util.Date deductRollbkSysTime) {

		super (
			id,
			tradeTime,
			outAccValidTime,
			tradeType,
			reqTime,
			deductStlmDate,
			deductSysTime,
			deductRollbkSysTime);
	}

/*[CONSTRUCTOR MARKER END]*/


}