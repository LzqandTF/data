package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseCustomInstConfig;



public class CustomInstConfig extends BaseCustomInstConfig {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomInstConfig () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomInstConfig (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomInstConfig (
		java.lang.Integer id,
		java.lang.Integer objectId,
		java.lang.Integer instId,
		java.lang.Integer instType,
		java.lang.String instName) {

		super (
			id,
			objectId,
			instId,
			instType,
			instName);
	}

/*[CONSTRUCTOR MARKER END]*/


}