package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseChannelDzCollect;



public class ChannelDzCollect extends BaseChannelDzCollect {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ChannelDzCollect () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ChannelDzCollect (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ChannelDzCollect (
		java.lang.String id,
		java.lang.Double tradeAmount,
		java.lang.Integer tradeResult,
		java.lang.Integer deductSysId,
		java.lang.String deductSysStance,
		java.lang.String deductMerCode,
		java.lang.Long tradeTime,
		java.lang.Long deductSysTime,
		java.lang.Integer deductStlmDate,
		java.lang.Integer instType,
		java.lang.String settleCode,
		java.lang.String instName,
		java.lang.Integer bankId) {

		super (
			id,
			tradeAmount,
			tradeResult,
			deductSysId,
			deductSysStance,
			deductMerCode,
			tradeTime,
			deductSysTime,
			deductStlmDate,
			instType,
			settleCode,
			instName,
			bankId);
	}

/*[CONSTRUCTOR MARKER END]*/


}