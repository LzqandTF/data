package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseHlog;



public class Hlog extends BaseHlog {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Hlog () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Hlog (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Hlog (
		java.lang.Long id,
		java.lang.Integer version,
		java.lang.Integer mdate,
		java.lang.Byte type,
		java.lang.Integer gate,
		java.lang.Integer sysDate,
		java.lang.Integer initSysDate,
		java.lang.Integer sysTime,
		java.lang.Byte tstat,
		java.lang.Integer bkFlag,
		java.lang.Integer transPeriod) {

		super (
			id,
			version,
			mdate,
			type,
			gate,
			sysDate,
			initSysDate,
			sysTime,
			tstat,
			bkFlag,
			transPeriod);
	}

/*[CONSTRUCTOR MARKER END]*/


}