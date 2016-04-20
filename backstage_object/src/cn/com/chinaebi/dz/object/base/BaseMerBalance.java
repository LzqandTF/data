package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mer_balance table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mer_balance"
 */

public abstract class BaseMerBalance  implements Serializable {

	public static String REF = "MerBalance";
	public static String PROP_MER_STATE = "MerState";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_MER_CATEGORY = "MerCategory";
	public static String PROP_ID = "Id";
	public static String PROP_MER_BALANCE = "MerBalance";


	// constructors
	public BaseMerBalance () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMerBalance (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMerBalance (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.MerBasic merCode,
		java.lang.Integer merCategory,
		java.lang.String merBalance,
		java.lang.Integer merState) {

		this.setId(id);
		this.setMerCode(merCode);
		this.setMerCategory(merCategory);
		this.setMerBalance(merBalance);
		this.setMerState(merState);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer merCategory;
	private java.lang.String merBalance;
	private java.lang.Integer merState;

	// many to one
	private cn.com.chinaebi.dz.object.MerBasic merCode;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: mer_category
	 */
	public java.lang.Integer getMerCategory () {
		return merCategory;
	}

	/**
	 * Set the value related to the column: mer_category
	 * @param merCategory the mer_category value
	 */
	public void setMerCategory (java.lang.Integer merCategory) {
		this.merCategory = merCategory;
	}



	/**
	 * Return the value associated with the column: mer_balance
	 */
	public java.lang.String getMerBalance () {
		return merBalance;
	}

	/**
	 * Set the value related to the column: mer_balance
	 * @param merBalance the mer_balance value
	 */
	public void setMerBalance (java.lang.String merBalance) {
		this.merBalance = merBalance;
	}



	/**
	 * Return the value associated with the column: mer_state
	 */
	public java.lang.Integer getMerState () {
		return merState;
	}

	/**
	 * Set the value related to the column: mer_state
	 * @param merState the mer_state value
	 */
	public void setMerState (java.lang.Integer merState) {
		this.merState = merState;
	}



	/**
	 * Return the value associated with the column: mer_code
	 */
	public cn.com.chinaebi.dz.object.MerBasic getMerCode () {
		return merCode;
	}

	/**
	 * Set the value related to the column: mer_code
	 * @param merCode the mer_code value
	 */
	public void setMerCode (cn.com.chinaebi.dz.object.MerBasic merCode) {
		this.merCode = merCode;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MerBalance)) return false;
		else {
			cn.com.chinaebi.dz.object.MerBalance merBalance = (cn.com.chinaebi.dz.object.MerBalance) obj;
			if (null == this.getId() || null == merBalance.getId()) return false;
			else return (this.getId().equals(merBalance.getId()));
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