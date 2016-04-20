package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseErrorTkLst;



public class ErrorTkLst extends BaseErrorTkLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ErrorTkLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ErrorTkLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ErrorTkLst (
		java.lang.String id,
		java.util.Date tradeTime,
		java.lang.Integer instType,
		java.lang.Integer handlingId,
		java.lang.Integer bankId) {

		super (
			id,
			tradeTime,
			instType,
			handlingId,
			bankId);
	}

/*[CONSTRUCTOR MARKER END]*/


}