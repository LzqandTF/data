package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mcc_discount table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mcc_discount"
 */

public abstract class BaseMccDiscount  implements Serializable {

	public static String REF = "MccDiscount";
	public static String PROP_BILL_TO_PARTY = "BillToParty";
	public static String PROP_TYPE = "Type";
	public static String PROP_ISSUERS = "Issuers";
	public static String PROP_ID = "Id";
	public static String PROP_MCC_FEE = "MccFee";
	public static String PROP_RANGE_DESC = "RangeDesc";
	public static String PROP_MCC_CODE = "MccCode";
	public static String PROP_UNIONPAY = "Unionpay";


	// constructors
	public BaseMccDiscount () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMccDiscount (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMccDiscount (
		java.lang.Integer id,
		cn.com.chinaebi.dz.object.MccType type,
		java.lang.String mccCode,
		java.lang.String issuers,
		java.lang.String billToParty,
		java.lang.String unionpay,
		java.lang.String rangeDesc,
		java.lang.String mccFee) {

		this.setId(id);
		this.setType(type);
		this.setMccCode(mccCode);
		this.setIssuers(issuers);
		this.setBillToParty(billToParty);
		this.setUnionpay(unionpay);
		this.setRangeDesc(rangeDesc);
		this.setMccFee(mccFee);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String mccCode;
	private java.lang.String issuers;
	private java.lang.String billToParty;
	private java.lang.String unionpay;
	private java.lang.String rangeDesc;
	private java.lang.String mccFee;

	// many to one
	private cn.com.chinaebi.dz.object.MccType type;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="mcc_id"
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
	 * Return the value associated with the column: mcc_code
	 */
	public java.lang.String getMccCode () {
		return mccCode;
	}

	/**
	 * Set the value related to the column: mcc_code
	 * @param mccCode the mcc_code value
	 */
	public void setMccCode (java.lang.String mccCode) {
		this.mccCode = mccCode;
	}



	/**
	 * Return the value associated with the column: issuers
	 */
	public java.lang.String getIssuers () {
		return issuers;
	}

	/**
	 * Set the value related to the column: issuers
	 * @param issuers the issuers value
	 */
	public void setIssuers (java.lang.String issuers) {
		this.issuers = issuers;
	}



	/**
	 * Return the value associated with the column: billToParty
	 */
	public java.lang.String getBillToParty () {
		return billToParty;
	}

	/**
	 * Set the value related to the column: billToParty
	 * @param billToParty the billToParty value
	 */
	public void setBillToParty (java.lang.String billToParty) {
		this.billToParty = billToParty;
	}



	/**
	 * Return the value associated with the column: unionpay
	 */
	public java.lang.String getUnionpay () {
		return unionpay;
	}

	/**
	 * Set the value related to the column: unionpay
	 * @param unionpay the unionpay value
	 */
	public void setUnionpay (java.lang.String unionpay) {
		this.unionpay = unionpay;
	}



	/**
	 * Return the value associated with the column: range_desc
	 */
	public java.lang.String getRangeDesc () {
		return rangeDesc;
	}

	/**
	 * Set the value related to the column: range_desc
	 * @param rangeDesc the range_desc value
	 */
	public void setRangeDesc (java.lang.String rangeDesc) {
		this.rangeDesc = rangeDesc;
	}



	/**
	 * Return the value associated with the column: mcc_fee
	 */
	public java.lang.String getMccFee () {
		return mccFee;
	}

	/**
	 * Set the value related to the column: mcc_fee
	 * @param mccFee the mcc_fee value
	 */
	public void setMccFee (java.lang.String mccFee) {
		this.mccFee = mccFee;
	}



	/**
	 * Return the value associated with the column: type_id
	 */
	public cn.com.chinaebi.dz.object.MccType getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type_id
	 * @param type the type_id value
	 */
	public void setType (cn.com.chinaebi.dz.object.MccType type) {
		this.type = type;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MccDiscount)) return false;
		else {
			cn.com.chinaebi.dz.object.MccDiscount mccDiscount = (cn.com.chinaebi.dz.object.MccDiscount) obj;
			if (null == this.getId() || null == mccDiscount.getId()) return false;
			else return (this.getId().equals(mccDiscount.getId()));
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