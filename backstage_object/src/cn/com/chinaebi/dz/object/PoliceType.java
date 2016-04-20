package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BasePoliceType;



public class PoliceType extends BasePoliceType {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PoliceType () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PoliceType (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PoliceType (
		java.lang.Integer id,
		java.lang.String policeName) {

		super (
			id,
			policeName);
	}

/*[CONSTRUCTOR MARKER END]*/


}