package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mer_tradecode table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mer_tradecode"
 */

public abstract class BaseMerTradecode  implements Serializable {

	public static String REF = "MerTradecode";
	public static String PROP_NAME = "Name";
	public static String PROP_STATUS = "Status";
	public static String PROP_VALUE = "Value";
	public static String PROP_OPERATION_TIME = "OperationTime";
	public static String PROP_ID = "Id";
	public static String PROP_OBJECT_NAME = "ObjectName";
	public static String PROP_OBJECT = "Object";


	// constructors
	public BaseMerTradecode () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMerTradecode (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMerTradecode (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.CustomObject object,
		java.lang.String value,
		java.lang.String objectName,
		java.util.Date operationTime,
		java.lang.String name,
		int status) {

		this.setId(id);
		this.setObject(object);
		this.setValue(value);
		this.setObjectName(objectName);
		this.setOperationTime(operationTime);
		this.setName(name);
		this.setStatus(status);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String value;
	private java.lang.String objectName;
	private java.util.Date operationTime;
	private java.lang.String name;
	private int status;

	// many to one
	private cn.com.chinaebi.dz.object.CustomObject object;



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
	 * Return the value associated with the column: value
	 */
	public java.lang.String getValue () {
		return value;
	}

	/**
	 * Set the value related to the column: value
	 * @param value the value value
	 */
	public void setValue (java.lang.String value) {
		this.value = value;
	}



	/**
	 * Return the value associated with the column: object_name
	 */
	public java.lang.String getObjectName () {
		return objectName;
	}

	/**
	 * Set the value related to the column: object_name
	 * @param objectName the object_name value
	 */
	public void setObjectName (java.lang.String objectName) {
		this.objectName = objectName;
	}



	/**
	 * Return the value associated with the column: operation_time
	 */
	public java.util.Date getOperationTime () {
		return operationTime;
	}

	/**
	 * Set the value related to the column: operation_time
	 * @param operationTime the operation_time value
	 */
	public void setOperationTime (java.util.Date operationTime) {
		this.operationTime = operationTime;
	}



	/**
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: status
	 */
	public int getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (int status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: object_id
	 */
	public cn.com.chinaebi.dz.object.CustomObject getObject () {
		return object;
	}

	/**
	 * Set the value related to the column: object_id
	 * @param object the object_id value
	 */
	public void setObject (cn.com.chinaebi.dz.object.CustomObject object) {
		this.object = object;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MerTradecode)) return false;
		else {
			cn.com.chinaebi.dz.object.MerTradecode merTradecode = (cn.com.chinaebi.dz.object.MerTradecode) obj;
			if (null == this.getId() || null == merTradecode.getId()) return false;
			else return (this.getId().equals(merTradecode.getId()));
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