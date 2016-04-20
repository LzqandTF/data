package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMccType;



public class MccType extends BaseMccType {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MccType () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MccType (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MccType (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.MccBigType bigType,
		java.lang.String typeName) {

		super (
			id,
			bigType,
			typeName);
	}

/*[CONSTRUCTOR MARKER END]*/


}