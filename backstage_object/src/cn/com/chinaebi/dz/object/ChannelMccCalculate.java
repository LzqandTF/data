package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseChannelMccCalculate;



public class ChannelMccCalculate extends BaseChannelMccCalculate {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ChannelMccCalculate () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ChannelMccCalculate (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ChannelMccCalculate (
		java.lang.Integer id,
		java.lang.Float issuer,
		java.lang.Float billToParty,
		java.lang.Float unionpay) {

		super (
			id,
			issuer,
			billToParty,
			unionpay);
	}

/*[CONSTRUCTOR MARKER END]*/


}