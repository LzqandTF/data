package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseCardTransfer;



public class CardTransfer extends BaseCardTransfer {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CardTransfer () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CardTransfer (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CardTransfer (
		java.lang.Integer id,
		java.lang.String cardInsDesc,
		java.lang.String cardInsCode,
		java.lang.Integer cardNoLength,
		java.lang.Integer cardLength,
		java.lang.String cardHead) {

		super (
			id,
			cardInsDesc,
			cardInsCode,
			cardNoLength,
			cardLength,
			cardHead);
	}

/*[CONSTRUCTOR MARKER END]*/


}