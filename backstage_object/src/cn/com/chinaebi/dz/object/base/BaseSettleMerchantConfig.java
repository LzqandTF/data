package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the settle_merchant_config table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="settle_merchant_config"
 */

public abstract class BaseSettleMerchantConfig  implements Serializable {

	public static String REF = "SettleMerchantConfig";
	public static String PROP_SETTLE_MER_NAME = "SettleMerName";
	public static String PROP_ID = "Id";
	public static String PROP_OPERATE_TIME = "OperateTime";


	// constructors
	public BaseSettleMerchantConfig () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSettleMerchantConfig (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSettleMerchantConfig (
		java.lang.String id,
		java.lang.String settleMerName,
		java.lang.String operateTime) {

		this.setId(id);
		this.setSettleMerName(settleMerName);
		this.setOperateTime(operateTime);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String settleMerName;
	private java.lang.String operateTime;

	// collections
	private java.util.Set<cn.com.chinaebi.dz.object.SettleMerchantMatchTable> settleMerchantMatchTables;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="settle_mer_code"
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
	 * Return the value associated with the column: settle_mer_name
	 */
	public java.lang.String getSettleMerName () {
		return settleMerName;
	}

	/**
	 * Set the value related to the column: settle_mer_name
	 * @param settleMerName the settle_mer_name value
	 */
	public void setSettleMerName (java.lang.String settleMerName) {
		this.settleMerName = settleMerName;
	}



	/**
	 * Return the value associated with the column: operate_time
	 */
	public java.lang.String getOperateTime () {
		return operateTime;
	}

	/**
	 * Set the value related to the column: operate_time
	 * @param operateTime the operate_time value
	 */
	public void setOperateTime (java.lang.String operateTime) {
		this.operateTime = operateTime;
	}


 
	public java.util.Set<cn.com.chinaebi.dz.object.SettleMerchantMatchTable> getSettleMerchantMatchTables () {
			return settleMerchantMatchTables==null? new java.util.TreeSet<cn.com.chinaebi.dz.object.SettleMerchantMatchTable>():settleMerchantMatchTables;
	}

	/**
	 * Set the value related to the column: SettleMerchantMatchTables
	 * @param settleMerchantMatchTables the SettleMerchantMatchTables value
	 */
	public void setSettleMerchantMatchTables (java.util.Set<cn.com.chinaebi.dz.object.SettleMerchantMatchTable> settleMerchantMatchTables) {
		this.settleMerchantMatchTables = settleMerchantMatchTables;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.SettleMerchantConfig)) return false;
		else {
			cn.com.chinaebi.dz.object.SettleMerchantConfig settleMerchantConfig = (cn.com.chinaebi.dz.object.SettleMerchantConfig) obj;
			if (null == this.getId() || null == settleMerchantConfig.getId()) return false;
			else return (this.getId().equals(settleMerchantConfig.getId()));
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