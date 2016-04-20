package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseYlcupsErrorEntry;



public class YlcupsErrorEntry extends BaseYlcupsErrorEntry {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public YlcupsErrorEntry () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public YlcupsErrorEntry (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public YlcupsErrorEntry (
		java.lang.String id,
		java.lang.String outAccount,
		java.util.Date tradeTime,
		java.lang.String reqSysStance,
		java.lang.String tradeAmount,
		java.lang.Integer handlingId,
		java.lang.String reasonCode,
		java.util.Date deductStlmDate,
		java.lang.String operator,
		java.lang.Integer bkChk) {

		super (
			id,
			outAccount,
			tradeTime,
			reqSysStance,
			tradeAmount,
			handlingId,
			reasonCode,
			deductStlmDate,
			operator,
			bkChk);
	}

/*[CONSTRUCTOR MARKER END]*/


}