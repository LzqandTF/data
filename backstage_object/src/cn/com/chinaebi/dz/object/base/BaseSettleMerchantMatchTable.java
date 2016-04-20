package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the settle_merchant_match_table table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="settle_merchant_match_table"
 */

public abstract class BaseSettleMerchantMatchTable  implements Serializable {

	public static String REF = "SettleMerchantMatchTable";
	public static String PROP_DY_MER_CODE = "DyMerCode";
	public static String PROP_SETTLE_MER_CODE = "SettleMerCode";
	public static String PROP_ID = "Id";


	// constructors
	public BaseSettleMerchantMatchTable () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSettleMerchantMatchTable (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSettleMerchantMatchTable (
		java.lang.String id,
		cn.com.chinaebi.dz.object.SettleMerchantConfig settleMerCode,
		java.lang.String dyMerCode) {

		this.setId(id);
		this.setSettleMerCode(settleMerCode);
		this.setDyMerCode(dyMerCode);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String dyMerCode;

	// many to one
	private cn.com.chinaebi.dz.object.SettleMerchantConfig settleMerCode;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: dy_mer_code
	 */
	public java.lang.String getDyMerCode () {
		return dyMerCode;
	}

	/**
	 * Set the value related to the column: dy_mer_code
	 * @param dyMerCode the dy_mer_code value
	 */
	public void setDyMerCode (java.lang.String dyMerCode) {
		this.dyMerCode = dyMerCode;
	}



	/**
	 * Return the value associated with the column: settle_mer_code
	 */
	public cn.com.chinaebi.dz.object.SettleMerchantConfig getSettleMerCode () {
		return settleMerCode;
	}

	/**
	 * Set the value related to the column: settle_mer_code
	 * @param settleMerCode the settle_mer_code value
	 */
	public void setSettleMerCode (cn.com.chinaebi.dz.object.SettleMerchantConfig settleMerCode) {
		this.settleMerCode = settleMerCode;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.SettleMerchantMatchTable)) return false;
		else {
			cn.com.chinaebi.dz.object.SettleMerchantMatchTable settleMerchantMatchTable = (cn.com.chinaebi.dz.object.SettleMerchantMatchTable) obj;
			if (null == this.getId() || null == settleMerchantMatchTable.getId()) return false;
			else return (this.getId().equals(settleMerchantMatchTable.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}