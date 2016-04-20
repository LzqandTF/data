package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMerBalance;



public class MerBalance extends BaseMerBalance {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MerBalance () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MerBalance (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MerBalance (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.MerBasic merCode,
		java.lang.Integer merCategory,
		java.lang.String merBalance,
		java.lang.Integer merState) {

		super (
			id,
			merCode,
			merCategory,
			merBalance,
			merState);
	}

/*[CONSTRUCTOR MARKER END]*/


}