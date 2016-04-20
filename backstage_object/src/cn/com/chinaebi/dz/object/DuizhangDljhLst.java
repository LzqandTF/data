package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDuizhangDljhLst;



public class DuizhangDljhLst extends BaseDuizhangDljhLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DuizhangDljhLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DuizhangDljhLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DuizhangDljhLst (
		java.lang.String id,
		java.lang.String reqTime,
		java.lang.String outAccount,
		java.lang.String tradeAmount,
		java.lang.String termId,
		java.lang.String merCode,
		java.lang.Integer whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk,
		java.lang.String deductStlmDate,
		java.lang.String settleAmount) {

		super (
			id,
			reqTime,
			outAccount,
			tradeAmount,
			termId,
			merCode,
			whetherErroeHandle,
			dzFileName,
			instName,
			bkChk,
			deductStlmDate,
			settleAmount);
	}

/*[CONSTRUCTOR MARKER END]*/


}