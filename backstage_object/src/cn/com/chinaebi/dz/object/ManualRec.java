package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseManualRec;



public class ManualRec extends BaseManualRec {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ManualRec () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ManualRec (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ManualRec (
		java.lang.String id,
		cn.com.chinaebi.dz.object.MerBasic merCode,
		java.lang.String recAmount,
		java.lang.String merBalance,
		java.lang.Integer addorsub,
		java.util.Date handlerTime,
		java.util.Date auditTime,
		java.lang.String sendUserName,
		java.lang.String requestDesc) {

		super (
			id,
			merCode,
			recAmount,
			merBalance,
			addorsub,
			handlerTime,
			auditTime,
			sendUserName,
			requestDesc);
	}

/*[CONSTRUCTOR MARKER END]*/


}