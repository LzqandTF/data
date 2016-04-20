package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDuizhangZgyhLst;



public class DuizhangZgyhLst extends BaseDuizhangZgyhLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DuizhangZgyhLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DuizhangZgyhLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DuizhangZgyhLst (
		java.lang.String id,
		java.lang.String merCode,
		java.lang.String termId,
		java.lang.String reqTime,
		java.lang.Integer whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk,
		java.lang.String deductStlmDate) {

		super (
			id,
			merCode,
			termId,
			reqTime,
			whetherErroeHandle,
			dzFileName,
			instName,
			bkChk,
			deductStlmDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}