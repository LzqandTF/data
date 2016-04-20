package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mcc_type table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mcc_type"
 */

public abstract class BaseMccType  implements Serializable {

	public static String REF = "MccType";
	public static String PROP_BIG_TYPE = "BigType";
	public static String PROP_ID = "Id";
	public static String PROP_TYPE_NAME = "TypeName";


	// constructors
	public BaseMccType () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMccType (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMccType (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.MccBigType bigType,
		java.lang.String typeName) {

		this.setId(id);
		this.setBigType(bigType);
		this.setTypeName(typeName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String typeName;

	// many to one
	private cn.com.chinaebi.dz.object.MccBigType bigType;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="type_id"
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



	/**
	 * Return the value associated with the column: big_type_id
	 */
	public cn.com.chinaebi.dz.object.MccBigType getBigType () {
		return bigType;
	}

	/**
	 * Set the value related to the column: big_type_id
	 * @param bigType the big_type_id value
	 */
	public void setBigType (cn.com.chinaebi.dz.object.MccBigType bigType) {
		this.bigType = bigType;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MccType)) return false;
		else {
			cn.com.chinaebi.dz.object.MccType mccType = (cn.com.chinaebi.dz.object.MccType) obj;
			if (null == this.getId() || null == mccType.getId()) return false;
			else return (this.getId().equals(mccType.getId()));
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