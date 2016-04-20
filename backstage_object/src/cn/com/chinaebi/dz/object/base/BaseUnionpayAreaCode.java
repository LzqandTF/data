package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the unionpay_area_code table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="unionpay_area_code"
 */

public abstract class BaseUnionpayAreaCode  implements Serializable {

	public static String REF = "UnionpayAreaCode";
	public static String PROP_PARENT_AREA_CODE = "ParentAreaCode";
	public static String PROP_AREA_NAME = "AreaName";
	public static String PROP_ID = "Id";


	// constructors
	public BaseUnionpayAreaCode () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUnionpayAreaCode (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer parentAreaCode;
	private java.lang.String areaName;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="area_code"
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
	 * Return the value associated with the column: parent_area_code
	 */
	public java.lang.Integer getParentAreaCode () {
		return parentAreaCode;
	}

	/**
	 * Set the value related to the column: parent_area_code
	 * @param parentAreaCode the parent_area_code value
	 */
	public void setParentAreaCode (java.lang.Integer parentAreaCode) {
		this.parentAreaCode = parentAreaCode;
	}



	/**
	 * Return the value associated with the column: area_name
	 */
	public java.lang.String getAreaName () {
		return areaName;
	}

	/**
	 * Set the value related to the column: area_name
	 * @param areaName the area_name value
	 */
	public void setAreaName (java.lang.String areaName) {
		this.areaName = areaName;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.UnionpayAreaCode)) return false;
		else {
			cn.com.chinaebi.dz.object.UnionpayAreaCode unionpayAreaCode = (cn.com.chinaebi.dz.object.UnionpayAreaCode) obj;
			if (null == this.getId() || null == unionpayAreaCode.getId()) return false;
			else return (this.getId().equals(unionpayAreaCode.getId()));
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