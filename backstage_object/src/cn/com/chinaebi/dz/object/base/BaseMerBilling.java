package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mer_billing table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mer_billing"
 */

public abstract class BaseMerBilling  implements Serializable {

	public static String REF = "MerBilling";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_BIL_BANK = "BilBank";
	public static String PROP_BIL_ACCOUNTNAME = "BilAccountname";
	public static String PROP_BIL_STATUS = "BilStatus";
	public static String PROP_BIL_SMALLAMT = "BilSmallamt";
	public static String PROP_REFUND_FEE = "RefundFee";
	public static String PROP_BIL_BANKACCOUNT = "BilBankaccount";
	public static String PROP_BIL_TYPE = "BilType";
	public static String PROP_BIL_OBJECT = "BilObject";
	public static String PROP_WHTHER_FZ = "WhtherFz";
	public static String PROP_MER_POUNDAGE = "MerPoundage";
	public static String PROP_LAST_LIQ_DATE = "LastLiqDate";
	public static String PROP_BIL_CYCLE = "BilCycle";
	public static String PROP_BIL_ACCOUNT = "BilAccount";
	public static String PROP_ID = "Id";
	public static String PROP_BIL_BANKNAME = "BilBankname";
	public static String PROP_BIL_WAY = "BilWay";
	public static String PROP_BIL_MANUAL = "BilManual";
	public static String PROP_BANK_BRANCH = "BankBranch";


	// constructors
	public BaseMerBilling () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMerBilling (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMerBilling (
		java.lang.String id,
		cn.com.chinaebi.dz.object.MerBasic merCode,
		java.lang.String bilBankaccount,
		java.lang.Integer bilWay,
		java.lang.Integer bilCycle,
		java.lang.String bilAccount,
		java.lang.Integer bilType,
		java.lang.Integer bilStatus) {

		this.setId(id);
		this.setMerCode(merCode);
		this.setBilBankaccount(bilBankaccount);
		this.setBilWay(bilWay);
		this.setBilCycle(bilCycle);
		this.setBilAccount(bilAccount);
		this.setBilType(bilType);
		this.setBilStatus(bilStatus);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.Integer bilObject;
	private java.lang.String bilBank;
	private java.lang.String bilBankname;
	private java.lang.String bilAccountname;
	private java.lang.String bilBankaccount;
	private java.lang.Integer bilWay;
	private java.lang.String bilSmallamt;
	private java.lang.Integer bilCycle;
	private java.lang.Integer bilManual;
	private java.lang.String bilAccount;
	private java.lang.Integer bilType;
	private java.lang.String merPoundage;
	private java.lang.Integer bilStatus;
	private java.lang.Integer lastLiqDate;
	private java.lang.String bankBranch;
	private java.lang.Integer refundFee;
	private boolean whtherFz;

	// many to one
	private cn.com.chinaebi.dz.object.MerBasic merCode;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: bil_object
	 */
	public java.lang.Integer getBilObject () {
		return bilObject;
	}

	/**
	 * Set the value related to the column: bil_object
	 * @param bilObject the bil_object value
	 */
	public void setBilObject (java.lang.Integer bilObject) {
		this.bilObject = bilObject;
	}



	/**
	 * Return the value associated with the column: bil_bank
	 */
	public java.lang.String getBilBank () {
		return bilBank;
	}

	/**
	 * Set the value related to the column: bil_bank
	 * @param bilBank the bil_bank value
	 */
	public void setBilBank (java.lang.String bilBank) {
		this.bilBank = bilBank;
	}



	/**
	 * Return the value associated with the column: bil_bankname
	 */
	public java.lang.String getBilBankname () {
		return bilBankname;
	}

	/**
	 * Set the value related to the column: bil_bankname
	 * @param bilBankname the bil_bankname value
	 */
	public void setBilBankname (java.lang.String bilBankname) {
		this.bilBankname = bilBankname;
	}



	/**
	 * Return the value associated with the column: bil_accountname
	 */
	public java.lang.String getBilAccountname () {
		return bilAccountname;
	}

	/**
	 * Set the value related to the column: bil_accountname
	 * @param bilAccountname the bil_accountname value
	 */
	public void setBilAccountname (java.lang.String bilAccountname) {
		this.bilAccountname = bilAccountname;
	}



	/**
	 * Return the value associated with the column: bil_bankaccount
	 */
	public java.lang.String getBilBankaccount () {
		return bilBankaccount;
	}

	/**
	 * Set the value related to the column: bil_bankaccount
	 * @param bilBankaccount the bil_bankaccount value
	 */
	public void setBilBankaccount (java.lang.String bilBankaccount) {
		this.bilBankaccount = bilBankaccount;
	}



	/**
	 * Return the value associated with the column: bil_way
	 */
	public java.lang.Integer getBilWay () {
		return bilWay;
	}

	/**
	 * Set the value related to the column: bil_way
	 * @param bilWay the bil_way value
	 */
	public void setBilWay (java.lang.Integer bilWay) {
		this.bilWay = bilWay;
	}



	/**
	 * Return the value associated with the column: bil_smallamt
	 */
	public java.lang.String getBilSmallamt () {
		return bilSmallamt;
	}

	/**
	 * Set the value related to the column: bil_smallamt
	 * @param bilSmallamt the bil_smallamt value
	 */
	public void setBilSmallamt (java.lang.String bilSmallamt) {
		this.bilSmallamt = bilSmallamt;
	}



	/**
	 * Return the value associated with the column: bil_cycle
	 */
	public java.lang.Integer getBilCycle () {
		return bilCycle;
	}

	/**
	 * Set the value related to the column: bil_cycle
	 * @param bilCycle the bil_cycle value
	 */
	public void setBilCycle (java.lang.Integer bilCycle) {
		this.bilCycle = bilCycle;
	}



	/**
	 * Return the value associated with the column: bil_manual
	 */
	public java.lang.Integer getBilManual () {
		return bilManual;
	}

	/**
	 * Set the value related to the column: bil_manual
	 * @param bilManual the bil_manual value
	 */
	public void setBilManual (java.lang.Integer bilManual) {
		this.bilManual = bilManual;
	}



	/**
	 * Return the value associated with the column: bil_account
	 */
	public java.lang.String getBilAccount () {
		return bilAccount;
	}

	/**
	 * Set the value related to the column: bil_account
	 * @param bilAccount the bil_account value
	 */
	public void setBilAccount (java.lang.String bilAccount) {
		this.bilAccount = bilAccount;
	}



	/**
	 * Return the value associated with the column: bil_type
	 */
	public java.lang.Integer getBilType () {
		return bilType;
	}

	/**
	 * Set the value related to the column: bil_type
	 * @param bilType the bil_type value
	 */
	public void setBilType (java.lang.Integer bilType) {
		this.bilType = bilType;
	}



	/**
	 * Return the value associated with the column: mer_poundage
	 */
	public java.lang.String getMerPoundage () {
		return merPoundage;
	}

	/**
	 * Set the value related to the column: mer_poundage
	 * @param merPoundage the mer_poundage value
	 */
	public void setMerPoundage (java.lang.String merPoundage) {
		this.merPoundage = merPoundage;
	}



	/**
	 * Return the value associated with the column: bil_status
	 */
	public java.lang.Integer getBilStatus () {
		return bilStatus;
	}

	/**
	 * Set the value related to the column: bil_status
	 * @param bilStatus the bil_status value
	 */
	public void setBilStatus (java.lang.Integer bilStatus) {
		this.bilStatus = bilStatus;
	}



	/**
	 * Return the value associated with the column: last_liq_date
	 */
	public java.lang.Integer getLastLiqDate () {
		return lastLiqDate;
	}

	/**
	 * Set the value related to the column: last_liq_date
	 * @param lastLiqDate the last_liq_date value
	 */
	public void setLastLiqDate (java.lang.Integer lastLiqDate) {
		this.lastLiqDate = lastLiqDate;
	}



	/**
	 * Return the value associated with the column: bank_branch
	 */
	public java.lang.String getBankBranch () {
		return bankBranch;
	}

	/**
	 * Set the value related to the column: bank_branch
	 * @param bankBranch the bank_branch value
	 */
	public void setBankBranch (java.lang.String bankBranch) {
		this.bankBranch = bankBranch;
	}



	/**
	 * Return the value associated with the column: refund_fee
	 */
	public java.lang.Integer getRefundFee () {
		return refundFee;
	}

	/**
	 * Set the value related to the column: refund_fee
	 * @param refundFee the refund_fee value
	 */
	public void setRefundFee (java.lang.Integer refundFee) {
		this.refundFee = refundFee;
	}



	/**
	 * Return the value associated with the column: whtherFz
	 */
	public boolean isWhtherFz () {
		return whtherFz;
	}

	/**
	 * Set the value related to the column: whtherFz
	 * @param whtherFz the whtherFz value
	 */
	public void setWhtherFz (boolean whtherFz) {
		this.whtherFz = whtherFz;
	}



	/**
	 * Return the value associated with the column: mer_code
	 */
	public cn.com.chinaebi.dz.object.MerBasic getMerCode () {
		return merCode;
	}

	/**
	 * Set the value related to the column: mer_code
	 * @param merCode the mer_code value
	 */
	public void setMerCode (cn.com.chinaebi.dz.object.MerBasic merCode) {
		this.merCode = merCode;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MerBilling)) return false;
		else {
			cn.com.chinaebi.dz.object.MerBilling merBilling = (cn.com.chinaebi.dz.object.MerBilling) obj;
			if (null == this.getId() || null == merBilling.getId()) return false;
			else return (this.getId().equals(merBilling.getId()));
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