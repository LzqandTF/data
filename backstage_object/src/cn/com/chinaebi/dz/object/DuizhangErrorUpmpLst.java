package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDuizhangErrorUpmpLst;



public class DuizhangErrorUpmpLst extends BaseDuizhangErrorUpmpLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DuizhangErrorUpmpLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DuizhangErrorUpmpLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DuizhangErrorUpmpLst (
		java.lang.String id,
		java.lang.String errorTradeFlag,
		java.lang.String acqInstIdCode,
		java.lang.String fwdInstIdCode,
		java.lang.String reqSysStance,
		java.lang.String reqTime,
		java.lang.String outAccount,
		java.lang.String tradeAccount,
		java.lang.String msgType,
		java.lang.String process,
		java.lang.String merType,
		java.lang.String termId,
		java.lang.String reqType,
		java.lang.String origDataStance,
		java.lang.String deductSysResponse,
		java.lang.String reqInputType,
		java.lang.Integer whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk) {

		super (
			id,
			errorTradeFlag,
			acqInstIdCode,
			fwdInstIdCode,
			reqSysStance,
			reqTime,
			outAccount,
			tradeAccount,
			msgType,
			process,
			merType,
			termId,
			reqType,
			origDataStance,
			deductSysResponse,
			reqInputType,
			whetherErroeHandle,
			dzFileName,
			instName,
			bkChk);
	}

/*[CONSTRUCTOR MARKER END]*/


}