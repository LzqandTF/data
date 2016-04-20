package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseSettleMerchantConfig;



public class SettleMerchantConfig extends BaseSettleMerchantConfig {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SettleMerchantConfig () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SettleMerchantConfig (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SettleMerchantConfig (
		java.lang.String id,
		java.lang.String settleMerName,
		java.lang.String operateTime) {

		super (
			id,
			settleMerName,
			operateTime);
	}

/*[CONSTRUCTOR MARKER END]*/


}