package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the rule_template table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="rule_template"
 */

public abstract class BaseRuleTemplate  implements Serializable {

	public static String REF = "RuleTemplate";
	public static String PROP_TEMPLATE_NAME = "TemplateName";
	public static String PROP_ID = "Id";
	public static String PROP_TEMPLATE_FUNCTION = "TemplateFunction";


	// constructors
	public BaseRuleTemplate () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRuleTemplate (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseRuleTemplate (
		java.lang.Integer id,
		java.lang.String templateName,
		java.lang.String templateFunction) {

		this.setId(id);
		this.setTemplateName(templateName);
		this.setTemplateFunction(templateFunction);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String templateName;
	private java.lang.String templateFunction;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="template_id"
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
	 * Return the value associated with the column: template_name
	 */
	public java.lang.String getTemplateName () {
		return templateName;
	}

	/**
	 * Set the value related to the column: template_name
	 * @param templateName the template_name value
	 */
	public void setTemplateName (java.lang.String templateName) {
		this.templateName = templateName;
	}



	/**
	 * Return the value associated with the column: template_function
	 */
	public java.lang.String getTemplateFunction () {
		return templateFunction;
	}

	/**
	 * Set the value related to the column: template_function
	 * @param templateFunction the template_function value
	 */
	public void setTemplateFunction (java.lang.String templateFunction) {
		this.templateFunction = templateFunction;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.RuleTemplate)) return false;
		else {
			cn.com.chinaebi.dz.object.RuleTemplate ruleTemplate = (cn.com.chinaebi.dz.object.RuleTemplate) obj;
			if (null == this.getId() || null == ruleTemplate.getId()) return false;
			else return (this.getId().equals(ruleTemplate.getId()));
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