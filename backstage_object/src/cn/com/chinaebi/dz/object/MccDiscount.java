package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMccDiscount;



public class MccDiscount extends BaseMccDiscount {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MccDiscount () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MccDiscount (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MccDiscount (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.MccType type,
		java.lang.String mccCode,
		java.lang.String issuers,
		java.lang.String billToParty,
		java.lang.String unionpay,
		java.lang.String rangeDesc,
		java.lang.String mccFee) {

		super (
			id,
			type,
			mccCode,
			issuers,
			billToParty,
			unionpay,
			rangeDesc,
			mccFee);
	}

/*[CONSTRUCTOR MARKER END]*/


}