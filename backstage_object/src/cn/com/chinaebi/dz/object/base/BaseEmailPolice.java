package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the email_police table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="email_police"
 */

public abstract class BaseEmailPolice  implements Serializable {

	public static String REF = "EmailPolice";
	public static String PROP_PHONE_REMARK = "PhoneRemark";
	public static String PROP_PHONE = "Phone";
	public static String PROP_DATA_TYPE = "DataType";
	public static String PROP_PHONE_CONTENT = "PhoneContent";
	public static String PROP_EMAIL_THEME = "EmailTheme";
	public static String PROP_EMAIL = "Email";
	public static String PROP_EMAIL_CONTENT = "EmailContent";
	public static String PROP_ID = "Id";
	public static String PROP_POLICE = "Police";
	public static String PROP_EMAIL_REMARK = "EmailRemark";


	// constructors
	public BaseEmailPolice () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseEmailPolice (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseEmailPolice (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.PoliceType police,
		java.lang.Integer dataType) {

		this.setId(id);
		this.setPolice(police);
		this.setDataType(dataType);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String email;
	private java.lang.String phone;
	private java.lang.String emailTheme;
	private java.lang.String emailContent;
	private java.lang.String phoneContent;
	private java.lang.Integer dataType;
	private java.lang.String emailRemark;
	private java.lang.String phoneRemark;

	// many to one
	private cn.com.chinaebi.dz.object.PoliceType police;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="email_id"
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
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}



	/**
	 * Return the value associated with the column: phone
	 */
	public java.lang.String getPhone () {
		return phone;
	}

	/**
	 * Set the value related to the column: phone
	 * @param phone the phone value
	 */
	public void setPhone (java.lang.String phone) {
		this.phone = phone;
	}



	/**
	 * Return the value associated with the column: email_theme
	 */
	public java.lang.String getEmailTheme () {
		return emailTheme;
	}

	/**
	 * Set the value related to the column: email_theme
	 * @param emailTheme the email_theme value
	 */
	public void setEmailTheme (java.lang.String emailTheme) {
		this.emailTheme = emailTheme;
	}



	/**
	 * Return the value associated with the column: email_content
	 */
	public java.lang.String getEmailContent () {
		return emailContent;
	}

	/**
	 * Set the value related to the column: email_content
	 * @param emailContent the email_content value
	 */
	public void setEmailContent (java.lang.String emailContent) {
		this.emailContent = emailContent;
	}



	/**
	 * Return the value associated with the column: phone_content
	 */
	public java.lang.String getPhoneContent () {
		return phoneContent;
	}

	/**
	 * Set the value related to the column: phone_content
	 * @param phoneContent the phone_content value
	 */
	public void setPhoneContent (java.lang.String phoneContent) {
		this.phoneContent = phoneContent;
	}



	/**
	 * Return the value associated with the column: data_type
	 */
	public java.lang.Integer getDataType () {
		return dataType;
	}

	/**
	 * Set the value related to the column: data_type
	 * @param dataType the data_type value
	 */
	public void setDataType (java.lang.Integer dataType) {
		this.dataType = dataType;
	}



	/**
	 * Return the value associated with the column: email_remark
	 */
	public java.lang.String getEmailRemark () {
		return emailRemark;
	}

	/**
	 * Set the value related to the column: email_remark
	 * @param emailRemark the email_remark value
	 */
	public void setEmailRemark (java.lang.String emailRemark) {
		this.emailRemark = emailRemark;
	}



	/**
	 * Return the value associated with the column: phone_remark
	 */
	public java.lang.String getPhoneRemark () {
		return phoneRemark;
	}

	/**
	 * Set the value related to the column: phone_remark
	 * @param phoneRemark the phone_remark value
	 */
	public void setPhoneRemark (java.lang.String phoneRemark) {
		this.phoneRemark = phoneRemark;
	}



	/**
	 * Return the value associated with the column: police_id
	 */
	public cn.com.chinaebi.dz.object.PoliceType getPolice () {
		return police;
	}

	/**
	 * Set the value related to the column: police_id
	 * @param police the police_id value
	 */
	public void setPolice (cn.com.chinaebi.dz.object.PoliceType police) {
		this.police = police;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.EmailPolice)) return false;
		else {
			cn.com.chinaebi.dz.object.EmailPolice emailPolice = (cn.com.chinaebi.dz.object.EmailPolice) obj;
			if (null == this.getId() || null == emailPolice.getId()) return false;
			else return (this.getId().equals(emailPolice.getId()));
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