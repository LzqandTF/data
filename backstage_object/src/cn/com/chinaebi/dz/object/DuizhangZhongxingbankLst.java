package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDuizhangZhongxingbankLst;



public class DuizhangZhongxingbankLst extends BaseDuizhangZhongxingbankLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DuizhangZhongxingbankLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DuizhangZhongxingbankLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DuizhangZhongxingbankLst (
		java.lang.String id,
		java.lang.String deductStlmDate,
		java.lang.String tradeTime,
		java.lang.String reqResponse,
		java.lang.String reqSysStance,
		java.lang.String outAccount,
		java.lang.String tradeAmount,
		java.lang.String deductMerTermId,
		java.lang.String deductMerCode,
		java.lang.String merName,
		int whetherErroeHandle,
		java.lang.String dzFileName,
		java.lang.String instName,
		java.lang.Integer bkChk,
		java.lang.String deductStlmDate_) {

		super (
			id,
			deductStlmDate,
			tradeTime,
			reqResponse,
			reqSysStance,
			outAccount,
			tradeAmount,
			deductMerTermId,
			deductMerCode,
			merName,
			whetherErroeHandle,
			dzFileName,
			instName,
			bkChk,
			deductStlmDate_);
	}

/*[CONSTRUCTOR MARKER END]*/


}