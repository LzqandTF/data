package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseSettleMerchantMatchTable;



public class SettleMerchantMatchTable extends BaseSettleMerchantMatchTable {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SettleMerchantMatchTable () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SettleMerchantMatchTable (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SettleMerchantMatchTable (
		java.lang.String id,
		cn.com.chinaebi.dz.object.SettleMerchantConfig settleMerCode,
		java.lang.String dyMerCode) {

		super (
			id,
			settleMerCode,
			dyMerCode);
	}

/*[CONSTRUCTOR MARKER END]*/


}