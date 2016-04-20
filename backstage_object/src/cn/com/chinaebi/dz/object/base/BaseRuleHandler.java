package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the rule_handler table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="rule_handler"
 */

public abstract class BaseRuleHandler  implements Serializable {

	public static String REF = "RuleHandler";
	public static String PROP_DZ_COLUMN_NAME = "DzColumnName";
	public static String PROP_ATTRIBUTE_COLUMN = "AttributeColumn";
	public static String PROP_NEW_VALUE = "NewValue";
	public static String PROP_ID = "Id";
	public static String PROP_OLD_VALUE = "OldValue";
	public static String PROP_HANDLER_TYPE = "HandlerType";
	public static String PROP_TEMPLATE_ID = "TemplateId";


	// constructors
	public BaseRuleHandler () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRuleHandler (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseRuleHandler (
		java.lang.Integer id,
		java.lang.String dzColumnName,
		java.lang.String attributeColumn,
		java.lang.String oldValue,
		java.lang.String newValue,
		java.lang.Integer handlerType,
		java.lang.Integer templateId) {

		this.setId(id);
		this.setDzColumnName(dzColumnName);
		this.setAttributeColumn(attributeColumn);
		this.setOldValue(oldValue);
		this.setNewValue(newValue);
		this.setHandlerType(handlerType);
		this.setTemplateId(templateId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String dzColumnName;
	private java.lang.String attributeColumn;
	private java.lang.String oldValue;
	private java.lang.String newValue;
	private java.lang.Integer handlerType;
	private java.lang.Integer templateId;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="rule_id"
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
	 * Return the value associated with the column: dz_column_name
	 */
	public java.lang.String getDzColumnName () {
		return dzColumnName;
	}

	/**
	 * Set the value related to the column: dz_column_name
	 * @param dzColumnName the dz_column_name value
	 */
	public void setDzColumnName (java.lang.String dzColumnName) {
		this.dzColumnName = dzColumnName;
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
	 * Return the value associated with the column: old_value
	 */
	public java.lang.String getOldValue () {
		return oldValue;
	}

	/**
	 * Set the value related to the column: old_value
	 * @param oldValue the old_value value
	 */
	public void setOldValue (java.lang.String oldValue) {
		this.oldValue = oldValue;
	}



	/**
	 * Return the value associated with the column: new_value
	 */
	public java.lang.String getNewValue () {
		return newValue;
	}

	/**
	 * Set the value related to the column: new_value
	 * @param newValue the new_value value
	 */
	public void setNewValue (java.lang.String newValue) {
		this.newValue = newValue;
	}



	/**
	 * Return the value associated with the column: handler_type
	 */
	public java.lang.Integer getHandlerType () {
		return handlerType;
	}

	/**
	 * Set the value related to the column: handler_type
	 * @param handlerType the handler_type value
	 */
	public void setHandlerType (java.lang.Integer handlerType) {
		this.handlerType = handlerType;
	}



	/**
	 * Return the value associated with the column: template_id
	 */
	public java.lang.Integer getTemplateId () {
		return templateId;
	}

	/**
	 * Set the value related to the column: template_id
	 * @param templateId the template_id value
	 */
	public void setTemplateId (java.lang.Integer templateId) {
		this.templateId = templateId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.RuleHandler)) return false;
		else {
			cn.com.chinaebi.dz.object.RuleHandler ruleHandler = (cn.com.chinaebi.dz.object.RuleHandler) obj;
			if (null == this.getId() || null == ruleHandler.getId()) return false;
			else return (this.getId().equals(ruleHandler.getId()));
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