package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inst_rate table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inst_rate"
 */

public abstract class BaseInstRate  implements Serializable {

	public static String REF = "InstRate";
	public static String PROP_INST_NAME = "InstName";
	public static String PROP_INST_RATE_MCC = "InstRateMcc";
	public static String PROP_INST_RATE_TYPE = "InstRateType";
	public static String PROP_ID = "Id";
	public static String PROP_USER_NAME = "UserName";
	public static String PROP_BANK_INST_CODE = "BankInstCode";
	public static String PROP_WHETHER_RETURN_FEE = "WhetherReturnFee";


	// constructors
	public BaseInstRate () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInstRate (cn.com.chinaebi.dz.object.InstRatePK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInstRate (
		cn.com.chinaebi.dz.object.InstRatePK id,
		boolean whetherReturnFee,
		java.lang.Integer instRateType,
		java.lang.String bankInstCode,
		java.lang.String userName,
		java.lang.String instName) {

		this.setId(id);
		this.setWhetherReturnFee(whetherReturnFee);
		this.setInstRateType(instRateType);
		this.setBankInstCode(bankInstCode);
		this.setUserName(userName);
		this.setInstName(instName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private cn.com.chinaebi.dz.object.InstRatePK id;

	// fields
	private boolean whetherReturnFee;
	private java.lang.Integer instRateType;
	private java.lang.Integer instRateMcc;
	private java.lang.String bankInstCode;
	private java.lang.String userName;
	private java.lang.String instName;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public cn.com.chinaebi.dz.object.InstRatePK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (cn.com.chinaebi.dz.object.InstRatePK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: whetherReturnFee
	 */
	public boolean isWhetherReturnFee () {
		return whetherReturnFee;
	}

	/**
	 * Set the value related to the column: whetherReturnFee
	 * @param whetherReturnFee the whetherReturnFee value
	 */
	public void setWhetherReturnFee (boolean whetherReturnFee) {
		this.whetherReturnFee = whetherReturnFee;
	}



	/**
	 * Return the value associated with the column: inst_rate_type
	 */
	public java.lang.Integer getInstRateType () {
		return instRateType;
	}

	/**
	 * Set the value related to the column: inst_rate_type
	 * @param instRateType the inst_rate_type value
	 */
	public void setInstRateType (java.lang.Integer instRateType) {
		this.instRateType = instRateType;
	}



	/**
	 * Return the value associated with the column: inst_rate_mcc
	 */
	public java.lang.Integer getInstRateMcc () {
		return instRateMcc;
	}

	/**
	 * Set the value related to the column: inst_rate_mcc
	 * @param instRateMcc the inst_rate_mcc value
	 */
	public void setInstRateMcc (java.lang.Integer instRateMcc) {
		this.instRateMcc = instRateMcc;
	}



	/**
	 * Return the value associated with the column: bank_inst_code
	 */
	public java.lang.String getBankInstCode () {
		return bankInstCode;
	}

	/**
	 * Set the value related to the column: bank_inst_code
	 * @param bankInstCode the bank_inst_code value
	 */
	public void setBankInstCode (java.lang.String bankInstCode) {
		this.bankInstCode = bankInstCode;
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
		if (!(obj instanceof cn.com.chinaebi.dz.object.InstRate)) return false;
		else {
			cn.com.chinaebi.dz.object.InstRate instRate = (cn.com.chinaebi.dz.object.InstRate) obj;
			if (null == this.getId() || null == instRate.getId()) return false;
			else return (this.getId().equals(instRate.getId()));
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