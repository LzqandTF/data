package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseOriginalCupsLst;



public class OriginalCupsLst extends BaseOriginalCupsLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public OriginalCupsLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public OriginalCupsLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public OriginalCupsLst (
		java.lang.String id,
		java.util.Date tradeTime,
		java.util.Date outAccValidTime,
		java.lang.Integer tradeType,
		java.util.Date reqTime,
		java.util.Date deductStlmDate,
		java.util.Date deductSysTime,
		java.lang.Integer bkChk,
		boolean whetherJs,
		boolean whetherValid,
		java.lang.Integer whetherErroeHandle,
		boolean whetherRiqie,
		java.util.Date deductRollbkSysTime,
		boolean whtherInnerJs,
		java.lang.Integer bankId) {

		super (
			id,
			tradeTime,
			outAccValidTime,
			tradeType,
			reqTime,
			deductStlmDate,
			deductSysTime,
			bkChk,
			whetherJs,
			whetherValid,
			whetherErroeHandle,
			whetherRiqie,
			deductRollbkSysTime,
			whtherInnerJs,
			bankId);
	}

/*[CONSTRUCTOR MARKER END]*/


}