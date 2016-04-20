package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inst_mer_rate_conf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inst_mer_rate_conf"
 */

public abstract class BaseInstMerRateConf  implements Serializable {

	public static String REF = "InstMerRateConf";
	public static String PROP_INST_RATE_P_K = "InstRatePK";
	public static String PROP_FEE_POUNDAGE = "FeePoundage";
	public static String PROP_ID = "Id";
	public static String PROP_USER_NAME = "UserName";


	// constructors
	public BaseInstMerRateConf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInstMerRateConf (cn.com.chinaebi.dz.object.InstMerRateConfPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInstMerRateConf (
		cn.com.chinaebi.dz.object.InstMerRateConfPK id,
		cn.com.chinaebi.dz.object.InstRate instRatePK,
		java.lang.String feePoundage,
		java.lang.String userName) {

		this.setId(id);
		this.setInstRatePK(instRatePK);
		this.setFeePoundage(feePoundage);
		this.setUserName(userName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private cn.com.chinaebi.dz.object.InstMerRateConfPK id;

	// fields
	private java.lang.String feePoundage;
	private java.lang.String userName;

	// many to one
	private cn.com.chinaebi.dz.object.InstRate instRatePK;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public cn.com.chinaebi.dz.object.InstMerRateConfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (cn.com.chinaebi.dz.object.InstMerRateConfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: fee_Poundage
	 */
	public java.lang.String getFeePoundage () {
		return feePoundage;
	}

	/**
	 * Set the value related to the column: fee_Poundage
	 * @param feePoundage the fee_Poundage value
	 */
	public void setFeePoundage (java.lang.String feePoundage) {
		this.feePoundage = feePoundage;
	}



	/**
	 * Return the value associated with the column: user_name
	 */
	public java.lang.String getUserName () {
		return userName;
	}

	/**
	 * Set the value related to the column: user_name
	 * @param userName the user_name value
	 */
	public void setUserName (java.lang.String userName) {
		this.userName = userName;
	}



	/**
	 * Return the value associated with the column: inst_id
	 */
	public cn.com.chinaebi.dz.object.InstRate getInstRatePK () {
		return instRatePK;
	}

	/**
	 * Set the value related to the column: inst_id
	 * @param instRatePK the inst_id value
	 */
	public void setInstRatePK (cn.com.chinaebi.dz.object.InstRate instRatePK) {
		this.instRatePK = instRatePK;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.InstMerRateConf)) return false;
		else {
			cn.com.chinaebi.dz.object.InstMerRateConf instMerRateConf = (cn.com.chinaebi.dz.object.InstMerRateConf) obj;
			if (null == this.getId() || null == instMerRateConf.getId()) return false;
			else return (this.getId().equals(instMerRateConf.getId()));
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