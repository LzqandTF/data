package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseInstRate;



public class InstRate extends BaseInstRate {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InstRate () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InstRate (cn.com.chinaebi.dz.object.InstRatePK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InstRate (
		cn.com.chinaebi.dz.object.InstRatePK id,
		boolean whetherReturnFee,
		java.lang.Integer instRateType,
		java.lang.String bankInstCode,
		java.lang.String userName,
		java.lang.String instName) {

		super (
			id,
			whetherReturnFee,
			instRateType,
			bankInstCode,
			userName,
			instName);
	}

/*[CONSTRUCTOR MARKER END]*/


}