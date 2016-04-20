package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the object_relevance_column table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="object_relevance_column"
 */

public abstract class BaseObjectRelevanceColumn  implements Serializable {

	public static String REF = "ObjectRelevanceColumn";
	public static String PROP_SHOW_ATTRIBUTE_NAME = "ShowAttributeName";
	public static String PROP_DZ_COLUMN = "DzColumn";
	public static String PROP_FILE_TYPE = "FileType";
	public static String PROP_RULE_ID = "RuleId";
	public static String PROP_ID = "Id";
	public static String PROP_OBJECT = "Object";


	// constructors
	public BaseObjectRelevanceColumn () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseObjectRelevanceColumn (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseObjectRelevanceColumn (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.DzFileColumnConf dzColumn,
		cn.com.chinaebi.dz.object.CustomObject object,
		java.lang.Integer ruleId,
		java.lang.Integer fileType,
		java.lang.String showAttributeName) {

		this.setId(id);
		this.setDzColumn(dzColumn);
		this.setObject(object);
		this.setRuleId(ruleId);
		this.setFileType(fileType);
		this.setShowAttributeName(showAttributeName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer ruleId;
	private java.lang.Integer fileType;
	private java.lang.String showAttributeName;

	// many to one
	private cn.com.chinaebi.dz.object.DzFileColumnConf dzColumn;
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
	 * Return the value associated with the column: rule_id
	 */
	public java.lang.Integer getRuleId () {
		return ruleId;
	}

	/**
	 * Set the value related to the column: rule_id
	 * @param ruleId the rule_id value
	 */
	public void setRuleId (java.lang.Integer ruleId) {
		this.ruleId = ruleId;
	}



	/**
	 * Return the value associated with the column: file_type
	 */
	public java.lang.Integer getFileType () {
		return fileType;
	}

	/**
	 * Set the value related to the column: file_type
	 * @param fileType the file_type value
	 */
	public void setFileType (java.lang.Integer fileType) {
		this.fileType = fileType;
	}



	/**
	 * Return the value associated with the column: show_attribute_name
	 */
	public java.lang.String getShowAttributeName () {
		return showAttributeName;
	}

	/**
	 * Set the value related to the column: show_attribute_name
	 * @param showAttributeName the show_attribute_name value
	 */
	public void setShowAttributeName (java.lang.String showAttributeName) {
		this.showAttributeName = showAttributeName;
	}



	/**
	 * Return the value associated with the column: dz_column_id
	 */
	public cn.com.chinaebi.dz.object.DzFileColumnConf getDzColumn () {
		return dzColumn;
	}

	/**
	 * Set the value related to the column: dz_column_id
	 * @param dzColumn the dz_column_id value
	 */
	public void setDzColumn (cn.com.chinaebi.dz.object.DzFileColumnConf dzColumn) {
		this.dzColumn = dzColumn;
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
		if (!(obj instanceof cn.com.chinaebi.dz.object.ObjectRelevanceColumn)) return false;
		else {
			cn.com.chinaebi.dz.object.ObjectRelevanceColumn objectRelevanceColumn = (cn.com.chinaebi.dz.object.ObjectRelevanceColumn) obj;
			if (null == this.getId() || null == objectRelevanceColumn.getId()) return false;
			else return (this.getId().equals(objectRelevanceColumn.getId()));
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