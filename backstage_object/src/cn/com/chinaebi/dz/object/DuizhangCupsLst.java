package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDuizhangCupsLst;



public class DuizhangCupsLst extends BaseDuizhangCupsLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DuizhangCupsLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DuizhangCupsLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DuizhangCupsLst (
		java.lang.String id,
		java.lang.String acqInstIdCode,
		java.lang.String fwdInstIdCode,
		java.lang.String reqSysStance,
		java.lang.String reqTime,
		java.lang.String outAccount,
		java.lang.String tradeAmount,
		java.lang.String msgType,
		java.lang.String termId,
		java.lang.String merCode,
		java.lang.String reqType,
		java.lang.String deductSysResponse,
		java.lang.String reqInputType,
		int whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk,
		java.lang.String deductStlmDate) {

		super (
			id,
			acqInstIdCode,
			fwdInstIdCode,
			reqSysStance,
			reqTime,
			outAccount,
			tradeAmount,
			msgType,
			termId,
			merCode,
			reqType,
			deductSysResponse,
			reqInputType,
			whetherErroeHandle,
			dzFileName,
			instName,
			bkChk,
			deductStlmDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}