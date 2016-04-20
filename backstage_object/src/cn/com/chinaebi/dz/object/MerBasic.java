package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMerBasic;



public class MerBasic extends BaseMerBasic {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MerBasic () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MerBasic (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MerBasic (
		java.lang.String id,
		java.lang.String merName,
		java.lang.Integer merCategory,
		java.lang.Integer merState,
		java.lang.Integer startDate,
		java.lang.Integer endDate) {

		super (
			id,
			merName,
			merCategory,
			merState,
			startDate,
			endDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}