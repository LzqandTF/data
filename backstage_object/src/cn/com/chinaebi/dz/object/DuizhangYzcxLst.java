package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDuizhangYzcxLst;



public class DuizhangYzcxLst extends BaseDuizhangYzcxLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DuizhangYzcxLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DuizhangYzcxLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DuizhangYzcxLst (
		java.lang.String id,
		java.lang.String deductStlmDate,
		java.lang.String reqTime,
		java.lang.String merCode,
		java.lang.String termId,
		java.lang.String tradeAmount,
		java.lang.Integer bkChk,
		java.lang.Integer whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.String outAccount) {

		super (
			id,
			deductStlmDate,
			reqTime,
			merCode,
			termId,
			tradeAmount,
			bkChk,
			whetherErroeHandle,
			dzFileName,
			instName,
			outAccount);
	}

/*[CONSTRUCTOR MARKER END]*/


}