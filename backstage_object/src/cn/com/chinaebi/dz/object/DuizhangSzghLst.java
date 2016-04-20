package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDuizhangSzghLst;



public class DuizhangSzghLst extends BaseDuizhangSzghLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DuizhangSzghLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DuizhangSzghLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DuizhangSzghLst (
		java.lang.String id,
		java.lang.String reqSysStance,
		java.lang.String reqTime,
		java.lang.String tradeAmount,
		java.lang.String merCode,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk) {

		super (
			id,
			reqSysStance,
			reqTime,
			tradeAmount,
			merCode,
			dzFileName,
			instName,
			bkChk);
	}

/*[CONSTRUCTOR MARKER END]*/


}