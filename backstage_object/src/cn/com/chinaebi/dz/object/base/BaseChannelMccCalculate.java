package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the channel_mcc_calculate table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="channel_mcc_calculate"
 */

public abstract class BaseChannelMccCalculate  implements Serializable {

	public static String REF = "ChannelMccCalculate";
	public static String PROP_ISSUER = "Issuer";
	public static String PROP_BILL_TO_PARTY = "BillToParty";
	public static String PROP_ID = "Id";
	public static String PROP_UNIONPAY = "Unionpay";


	// constructors
	public BaseChannelMccCalculate () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseChannelMccCalculate (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseChannelMccCalculate (
		java.lang.Integer id,
		java.lang.Float issuer,
		java.lang.Float billToParty,
		java.lang.Float unionpay) {

		this.setId(id);
		this.setIssuer(issuer);
		this.setBillToParty(billToParty);
		this.setUnionpay(unionpay);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Float issuer;
	private java.lang.Float billToParty;
	private java.lang.Float unionpay;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="inst_id"
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
	 * Return the value associated with the column: issuer
	 */
	public java.lang.Float getIssuer () {
		return issuer;
	}

	/**
	 * Set the value related to the column: issuer
	 * @param issuer the issuer value
	 */
	public void setIssuer (java.lang.Float issuer) {
		this.issuer = issuer;
	}



	/**
	 * Return the value associated with the column: billToParty
	 */
	public java.lang.Float getBillToParty () {
		return billToParty;
	}

	/**
	 * Set the value related to the column: billToParty
	 * @param billToParty the billToParty value
	 */
	public void setBillToParty (java.lang.Float billToParty) {
		this.billToParty = billToParty;
	}



	/**
	 * Return the value associated with the column: unionpay
	 */
	public java.lang.Float getUnionpay () {
		return unionpay;
	}

	/**
	 * Set the value related to the column: unionpay
	 * @param unionpay the unionpay value
	 */
	public void setUnionpay (java.lang.Float unionpay) {
		this.unionpay = unionpay;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.ChannelMccCalculate)) return false;
		else {
			cn.com.chinaebi.dz.object.ChannelMccCalculate channelMccCalculate = (cn.com.chinaebi.dz.object.ChannelMccCalculate) obj;
			if (null == this.getId() || null == channelMccCalculate.getId()) return false;
			else return (this.getId().equals(channelMccCalculate.getId()));
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