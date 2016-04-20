package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the police_type table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="police_type"
 */

public abstract class BasePoliceType  implements Serializable {

	public static String REF = "PoliceType";
	public static String PROP_ID = "Id";
	public static String PROP_DESC = "Desc";
	public static String PROP_POLICE_NAME = "PoliceName";


	// constructors
	public BasePoliceType () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePoliceType (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BasePoliceType (
		java.lang.Integer id,
		java.lang.String policeName) {

		this.setId(id);
		this.setPoliceName(policeName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String policeName;
	private java.lang.String desc;

	// collections
	private java.util.Set<cn.com.chinaebi.dz.object.EmailPolice> emailPolices;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="police_id"
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
	 * Return the value associated with the column: police_name
	 */
	public java.lang.String getPoliceName () {
		return policeName;
	}

	/**
	 * Set the value related to the column: police_name
	 * @param policeName the police_name value
	 */
	public void setPoliceName (java.lang.String policeName) {
		this.policeName = policeName;
	}



	/**
	 * Return the value associated with the column: desc
	 */
	public java.lang.String getDesc () {
		return desc;
	}

	/**
	 * Set the value related to the column: desc
	 * @param desc the desc value
	 */
	public void setDesc (java.lang.String desc) {
		this.desc = desc;
	}


 
	public java.util.Set<cn.com.chinaebi.dz.object.EmailPolice> getEmailPolices () {
			return emailPolices==null? new java.util.TreeSet<cn.com.chinaebi.dz.object.EmailPolice>():emailPolices;
	}

	/**
	 * Set the value related to the column: EmailPolices
	 * @param emailPolices the EmailPolices value
	 */
	public void setEmailPolices (java.util.Set<cn.com.chinaebi.dz.object.EmailPolice> emailPolices) {
		this.emailPolices = emailPolices;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.PoliceType)) return false;
		else {
			cn.com.chinaebi.dz.object.PoliceType policeType = (cn.com.chinaebi.dz.object.PoliceType) obj;
			if (null == this.getId() || null == policeType.getId()) return false;
			else return (this.getId().equals(policeType.getId()));
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