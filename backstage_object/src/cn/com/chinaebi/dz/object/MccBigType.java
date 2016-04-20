package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMccBigType;



public class MccBigType extends BaseMccBigType {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MccBigType () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MccBigType (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MccBigType (
		java.lang.Integer id,
		java.lang.String typeName) {

		super (
			id,
			typeName);
	}

/*[CONSTRUCTOR MARKER END]*/


}