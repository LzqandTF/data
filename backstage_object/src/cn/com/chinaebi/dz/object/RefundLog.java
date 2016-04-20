package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRefundLog;



public class RefundLog extends BaseRefundLog {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RefundLog () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RefundLog (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RefundLog (
		java.lang.Long id,
		java.lang.Integer authorType) {

		super (
			id,
			authorType);
	}

/*[CONSTRUCTOR MARKER END]*/


}