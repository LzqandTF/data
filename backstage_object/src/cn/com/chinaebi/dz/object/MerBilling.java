package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMerBilling;



public class MerBilling extends BaseMerBilling {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MerBilling () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MerBilling (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MerBilling (
		java.lang.String id,
		cn.com.chinaebi.dz.object.MerBasic merCode,
		java.lang.String bilBankaccount,
		java.lang.Integer bilWay,
		java.lang.Integer bilCycle,
		java.lang.String bilAccount,
		java.lang.Integer bilType,
		java.lang.Integer bilStatus) {

		super (
			id,
			merCode,
			bilBankaccount,
			bilWay,
			bilCycle,
			bilAccount,
			bilType,
			bilStatus);
	}

/*[CONSTRUCTOR MARKER END]*/


}