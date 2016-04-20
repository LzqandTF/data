package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseTmoney;



public class Tmoney extends BaseTmoney {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Tmoney () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Tmoney (cn.com.chinaebi.dz.object.TmoneyPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Tmoney (
		cn.com.chinaebi.dz.object.TmoneyPK id,
		java.lang.String name,
		java.lang.Long totalMoney,
		java.lang.Integer settleWay) {

		super (
			id,
			name,
			totalMoney,
			settleWay);
	}

/*[CONSTRUCTOR MARKER END]*/


}