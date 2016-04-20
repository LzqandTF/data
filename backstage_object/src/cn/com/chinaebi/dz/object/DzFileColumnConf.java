package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDzFileColumnConf;



public class DzFileColumnConf extends BaseDzFileColumnConf {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DzFileColumnConf () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DzFileColumnConf (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DzFileColumnConf (
		java.lang.Integer id,
		java.lang.String attributeName,
		java.lang.String attributeColumn) {

		super (
			id,
			attributeName,
			attributeColumn);
	}

/*[CONSTRUCTOR MARKER END]*/


}