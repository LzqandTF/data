package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mcc_big_type table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mcc_big_type"
 */

public abstract class BaseMccBigType  implements Serializable {

	public static String REF = "MccBigType";
	public static String PROP_ID = "Id";
	public static String PROP_TYPE_NAME = "TypeName";


	// constructors
	public BaseMccBigType () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMccBigType (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMccBigType (
		java.lang.Integer id,
		java.lang.String typeName) {

		this.setId(id);
		this.setTypeName(typeName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String typeName;

	// collections
	private java.util.Set<cn.com.chinaebi.dz.object.MccType> mccTypes;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="big_type_id"
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
	 * Return the value associated with the column: type_name
	 */
	public java.lang.String getTypeName () {
		return typeName;
	}

	/**
	 * Set the value related to the column: type_name
	 * @param typeName the type_name value
	 */
	public void setTypeName (java.lang.String typeName) {
		this.typeName = typeName;
	}


 
	public java.util.Set<cn.com.chinaebi.dz.object.MccType> getMccTypes () {
			return mccTypes==null? new java.util.TreeSet<cn.com.chinaebi.dz.object.MccType>():mccTypes;
	}

	/**
	 * Set the value related to the column: MccTypes
	 * @param mccTypes the MccTypes value
	 */
	public void setMccTypes (java.util.Set<cn.com.chinaebi.dz.object.MccType> mccTypes) {
		this.mccTypes = mccTypes;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MccBigType)) return false;
		else {
			cn.com.chinaebi.dz.object.MccBigType mccBigType = (cn.com.chinaebi.dz.object.MccBigType) obj;
			if (null == this.getId() || null == mccBigType.getId()) return false;
			else return (this.getId().equals(mccBigType.getId()));
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