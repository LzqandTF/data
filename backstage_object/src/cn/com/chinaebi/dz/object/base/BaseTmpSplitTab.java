package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tmp_split_tab table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tmp_split_tab"
 */

public abstract class BaseTmpSplitTab  implements Serializable {

	public static String REF = "TmpSplitTab";
	public static String PROP_DEDUCT_SYS_ID = "DeductSysId";
	public static String PROP_ID = "Id";
	public static String PROP_TRADE_TIME = "TradeTime";


	// constructors
	public BaseTmpSplitTab () {
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTmpSplitTab (
		java.lang.String id,
		java.lang.String tradeTime,
		java.lang.String deductSysId) {

		this.setId(id);
		this.setTradeTime(tradeTime);
		this.setDeductSysId(deductSysId);
		initialize();
	}

	protected void initialize () {}



	// fields
	private java.lang.String id;
	private java.lang.String tradeTime;
	private java.lang.String deductSysId;






	/**
	 * Return the value associated with the column: id
	 */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the value related to the column: id
	 * @param id the id value
	 */
	public void setId (java.lang.String id) {
		this.id = id;
	}



	/**
	 * Return the value associated with the column: trade_time
	 */
	public java.lang.String getTradeTime () {
		return tradeTime;
	}

	/**
	 * Set the value related to the column: trade_time
	 * @param tradeTime the trade_time value
	 */
	public void setTradeTime (java.lang.String tradeTime) {
		this.tradeTime = tradeTime;
	}



	/**
	 * Return the value associated with the column: deduct_sys_id
	 */
	public java.lang.String getDeductSysId () {
		return deductSysId;
	}

	/**
	 * Set the value related to the column: deduct_sys_id
	 * @param deductSysId the deduct_sys_id value
	 */
	public void setDeductSysId (java.lang.String deductSysId) {
		this.deductSysId = deductSysId;
	}







	public String toString () {
		return super.toString();
	}


}