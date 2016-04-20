package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMerTradecode;



public class MerTradecode extends BaseMerTradecode {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MerTradecode () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MerTradecode (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MerTradecode (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.CustomObject object,
		java.lang.String value,
		java.lang.String objectName,
		java.util.Date operationTime,
		java.lang.String name,
		int status) {

		super (
			id,
			object,
			value,
			objectName,
			operationTime,
			name,
			status);
	}

/*[CONSTRUCTOR MARKER END]*/


}