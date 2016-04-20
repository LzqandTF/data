package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the custom_inst_config table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="custom_inst_config"
 */

public abstract class BaseCustomInstConfig  implements Serializable {

	public static String REF = "CustomInstConfig";
	public static String PROP_INST_TYPE = "InstType";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_INST_ID = "InstId";
	public static String PROP_ID = "Id";
	public static String PROP_OBJECT_ID = "ObjectId";


	// constructors
	public BaseCustomInstConfig () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomInstConfig (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomInstConfig (
		java.lang.Integer id,
		java.lang.Integer objectId,
		java.lang.Integer instId,
		java.lang.Integer instType,
		java.lang.String instName) {

		this.setId(id);
		this.setObjectId(objectId);
		this.setInstId(instId);
		this.setInstType(instType);
		this.setInstName(instName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer objectId;
	private java.lang.Integer instId;
	private java.lang.Integer instType;
	private java.lang.String instName;



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
	 * Return the value associated with the column: object_id
	 */
	public java.lang.Integer getObjectId () {
		return objectId;
	}

	/**
	 * Set the value related to the column: object_id
	 * @param objectId the object_id value
	 */
	public void setObjectId (java.lang.Integer objectId) {
		this.objectId = objectId;
	}



	/**
	 * Return the value associated with the column: inst_id
	 */
	public java.lang.Integer getInstId () {
		return instId;
	}

	/**
	 * Set the value related to the column: inst_id
	 * @param instId the inst_id value
	 */
	public void setInstId (java.lang.Integer instId) {
		this.instId = instId;
	}



	/**
	 * Return the value associated with the column: inst_type
	 */
	public java.lang.Integer getInstType () {
		return instType;
	}

	/**
	 * Set the value related to the column: inst_type
	 * @param instType the inst_type value
	 */
	public void setInstType (java.lang.Integer instType) {
		this.instType = instType;
	}



	/**
	 * Return the value associated with the column: inst_name
	 */
	public java.lang.String getInstName () {
		return instName;
	}

	/**
	 * Set the value related to the column: inst_name
	 * @param instName the inst_name value
	 */
	public void setInstName (java.lang.String instName) {
		this.instName = instName;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.CustomInstConfig)) return false;
		else {
			cn.com.chinaebi.dz.object.CustomInstConfig customInstConfig = (cn.com.chinaebi.dz.object.CustomInstConfig) obj;
			if (null == this.getId() || null == customInstConfig.getId()) return false;
			else return (this.getId().equals(customInstConfig.getId()));
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