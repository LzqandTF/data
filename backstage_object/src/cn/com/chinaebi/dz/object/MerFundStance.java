package cn.com.chinaebi.dz.object;

import java.util.Date;

import cn.com.chinaebi.dz.object.base.BaseMerFundStance;



public class MerFundStance extends BaseMerFundStance {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MerFundStance () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MerFundStance (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MerFundStance (
		java.lang.String id,
		cn.com.chinaebi.dz.object.MerBasic merCode,
		java.util.Date tradeTime,
		java.lang.Double tradeAmount,
		java.lang.Double merFee,
		java.lang.Double changeAmount,
		java.lang.Double accountAmount,
		java.lang.String tradeStance,
		java.lang.Integer dercStatus,
		java.lang.Integer merState,
		java.lang.Integer merCategory,
		java.lang.String merName,
		java.lang.Integer instId,
		java.lang.String deductStlmDate,
		java.lang.Integer bankId) {

		super (
			id,
			merCode,
			tradeTime,
			tradeAmount,
			merFee,
			changeAmount,
			accountAmount,
			tradeStance,
			dercStatus,
			merState,
			merCategory,
			merName,
			instId,
			deductStlmDate,
			bankId);
	}

/*[CONSTRUCTOR MARKER END]*/


}