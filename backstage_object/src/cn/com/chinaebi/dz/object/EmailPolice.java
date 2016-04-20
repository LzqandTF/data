package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseEmailPolice;



public class EmailPolice extends BaseEmailPolice {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public EmailPolice () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public EmailPolice (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public EmailPolice (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.PoliceType police,
		java.lang.Integer dataType) {

		super (
			id,
			police,
			dataType);
	}

/*[CONSTRUCTOR MARKER END]*/


}