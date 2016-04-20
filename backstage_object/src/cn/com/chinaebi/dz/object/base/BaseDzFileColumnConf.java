package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the dz_file_column_conf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="dz_file_column_conf"
 */

public abstract class BaseDzFileColumnConf  implements Serializable {

	public static String REF = "DzFileColumnConf";
	public static String PROP_COLUMN_LENGTH = "ColumnLength";
	public static String PROP_ATTRIBUTE_COLUMN = "AttributeColumn";
	public static String PROP_ATTRIBUTE_TYPE = "AttributeType";
	public static String PROP_ATTRIBUTE_NAME = "AttributeName";
	public static String PROP_ID = "Id";


	// constructors
	public BaseDzFileColumnConf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDzFileColumnConf (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDzFileColumnConf (
		java.lang.Integer id,
		java.lang.String attributeName,
		java.lang.String attributeColumn) {

		this.setId(id);
		this.setAttributeName(attributeName);
		this.setAttributeColumn(attributeColumn);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String attributeName;
	private java.lang.String attributeColumn;
	private java.lang.Integer attributeType;
	private java.lang.Integer columnLength;

	// collections
	private java.util.Set<cn.com.chinaebi.dz.object.ObjectRelevanceColumn> objectRelevanceColumns;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="dz_column_id"
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
	 * Return the value associated with the column: attribute_name
	 */
	public java.lang.String getAttributeName () {
		return attributeName;
	}

	/**
	 * Set the value related to the column: attribute_name
	 * @param attributeName the attribute_name value
	 */
	public void setAttributeName (java.lang.String attributeName) {
		this.attributeName = attributeName;
	}



	/**
	 * Return the value associated with the column: attribute_column
	 */
	public java.lang.String getAttributeColumn () {
		return attributeColumn;
	}

	/**
	 * Set the value related to the column: attribute_column
	 * @param attributeColumn the attribute_column value
	 */
	public void setAttributeColumn (java.lang.String attributeColumn) {
		this.attributeColumn = attributeColumn;
	}



	/**
	 * Return the value associated with the column: attribute_type
	 */
	public java.lang.Integer getAttributeType () {
		return attributeType;
	}

	/**
	 * Set the value related to the column: attribute_type
	 * @param attributeType the attribute_type value
	 */
	public void setAttributeType (java.lang.Integer attributeType) {
		this.attributeType = attributeType;
	}



	/**
	 * Return the value associated with the column: column_length
	 */
	public java.lang.Integer getColumnLength () {
		return columnLength;
	}

	/**
	 * Set the value related to the column: column_length
	 * @param columnLength the column_length value
	 */
	public void setColumnLength (java.lang.Integer columnLength) {
		this.columnLength = columnLength;
	}


 
	public java.util.Set<cn.com.chinaebi.dz.object.ObjectRelevanceColumn> getObjectRelevanceColumns () {
			return objectRelevanceColumns==null? new java.util.TreeSet<cn.com.chinaebi.dz.object.ObjectRelevanceColumn>():objectRelevanceColumns;
	}

	/**
	 * Set the value related to the column: ObjectRelevanceColumns
	 * @param objectRelevanceColumns the ObjectRelevanceColumns value
	 */
	public void setObjectRelevanceColumns (java.util.Set<cn.com.chinaebi.dz.object.ObjectRelevanceColumn> objectRelevanceColumns) {
		this.objectRelevanceColumns = objectRelevanceColumns;
	}

	public void addToObjectRelevanceColumns (cn.com.chinaebi.dz.object.ObjectRelevanceColumn objectRelevanceColumn) {
		if (null == getObjectRelevanceColumns()) setObjectRelevanceColumns(new java.util.TreeSet<cn.com.chinaebi.dz.object.ObjectRelevanceColumn>());
		getObjectRelevanceColumns().add(objectRelevanceColumn);
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DzFileColumnConf)) return false;
		else {
			cn.com.chinaebi.dz.object.DzFileColumnConf dzFileColumnConf = (cn.com.chinaebi.dz.object.DzFileColumnConf) obj;
			if (null == this.getId() || null == dzFileColumnConf.getId()) return false;
			else return (this.getId().equals(dzFileColumnConf.getId()));
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