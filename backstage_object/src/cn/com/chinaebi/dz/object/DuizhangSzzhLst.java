package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDuizhangSzzhLst;



public class DuizhangSzzhLst extends BaseDuizhangSzzhLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DuizhangSzzhLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DuizhangSzzhLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DuizhangSzzhLst (
		java.lang.String id,
		java.lang.String merCode,
		java.lang.String termId,
		java.lang.String outAccount,
		java.lang.String tradeDate,
		java.lang.String reqTime,
		java.lang.String tradeAmount,
		java.lang.String settleAmount,
		java.lang.Integer whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk,
		java.lang.String deductStlmDate) {

		super (
			id,
			merCode,
			termId,
			outAccount,
			tradeDate,
			reqTime,
			tradeAmount,
			settleAmount,
			whetherErroeHandle,
			dzFileName,
			instName,
			bkChk,
			deductStlmDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}