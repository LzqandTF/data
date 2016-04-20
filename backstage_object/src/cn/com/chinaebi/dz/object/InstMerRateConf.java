package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseInstMerRateConf;



public class InstMerRateConf extends BaseInstMerRateConf {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InstMerRateConf () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InstMerRateConf (cn.com.chinaebi.dz.object.InstMerRateConfPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InstMerRateConf (
		cn.com.chinaebi.dz.object.InstMerRateConfPK id,
		cn.com.chinaebi.dz.object.InstRate instRatePK,
		java.lang.String feePoundage,
		java.lang.String userName) {

		super (
			id,
			instRatePK,
			feePoundage,
			userName);
	}

/*[CONSTRUCTOR MARKER END]*/


}