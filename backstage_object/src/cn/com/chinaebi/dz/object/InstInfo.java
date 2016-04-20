package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseInstInfo;



public class InstInfo extends BaseInstInfo {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InstInfo () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InstInfo (cn.com.chinaebi.dz.object.InstInfoPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InstInfo (
		cn.com.chinaebi.dz.object.InstInfoPK id,
		cn.com.chinaebi.dz.object.BankInst bank,
		boolean whetherInnerDz,
		java.lang.String name,
		boolean whetherInnerJs,
		boolean active,
		boolean whetherOuterErrorDz,
		boolean whetherOuterDz,
		java.lang.Integer gate) {

		super (
			id,
			bank,
			whetherInnerDz,
			name,
			whetherInnerJs,
			active,
			whetherOuterErrorDz,
			whetherOuterDz,
			gate);
	}

/*[CONSTRUCTOR MARKER END]*/


}