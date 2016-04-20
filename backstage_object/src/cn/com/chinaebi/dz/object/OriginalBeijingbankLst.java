package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseOriginalBeijingbankLst;



public class OriginalBeijingbankLst extends BaseOriginalBeijingbankLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public OriginalBeijingbankLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public OriginalBeijingbankLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public OriginalBeijingbankLst (
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
		boolean whtherInnerJs) {

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
			whtherInnerJs);
	}

/*[CONSTRUCTOR MARKER END]*/


}