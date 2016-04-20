package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mer_fund_stance table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mer_fund_stance"
 */

public abstract class BaseMerFundStance  implements Serializable {

	public static String REF = "MerFundStance";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_MER_STATE = "MerState";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_ACCOUNT_AMOUNT = "AccountAmount";
	public static String PROP_INST_ID = "InstId";
	public static String PROP_DERC_STATUS = "DercStatus";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_MER_FEE = "MerFee";
	public static String PROP_CHANGE_AMOUNT = "ChangeAmount";
	public static String PROP_TRADE_STANCE = "TradeStance";
	public static String PROP_INST_TYPE = "InstType";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_BANK_ID = "BankId";
	public static String PROP_STANCE_TIME = "StanceTime";
	public static String PROP_MER_CATEGORY = "MerCategory";
	public static String PROP_ID = "Id";
	public static String PROP_TRADE_TIME = "TradeTime";


	// constructors
	public BaseMerFundStance () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMerFundStance (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMerFundStance (
		java.lang.String id,
		cn.com.chinaebi.dz.object.MerBasic merCode,
		java.util.Date tradeTime,
		java.lang.Double tradeAmount,
		java.lang.Double merFee,
		java.lang.Double changeAmount,
		java.lang.Double accountAmount,
		java.lang.String tradeStance,
		java.lang.Integer dercStatus,
		java.lang.Integer merState,
		java.lang.Integer merCategory,
		java.lang.String merName,
		java.lang.Integer instId,
		java.lang.String deductStlmDate,
		java.lang.Integer bankId) {

		this.setId(id);
		this.setMerCode(merCode);
		this.setTradeTime(tradeTime);
		this.setTradeAmount(tradeAmount);
		this.setMerFee(merFee);
		this.setChangeAmount(changeAmount);
		this.setAccountAmount(accountAmount);
		this.setTradeStance(tradeStance);
		this.setDercStatus(dercStatus);
		this.setMerState(merState);
		this.setMerCategory(merCategory);
		this.setMerName(merName);
		this.setInstId(instId);
		this.setDeductStlmDate(deductStlmDate);
		this.setBankId(bankId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.util.Date tradeTime;
	private java.lang.Double tradeAmount;
	private java.lang.Double merFee;
	private java.lang.Double changeAmount;
	private java.lang.Double accountAmount;
	private java.lang.String tradeStance;
	private java.lang.Integer dercStatus;
	private java.lang.Integer merState;
	private java.lang.Integer merCategory;
	private java.lang.String merName;
	private java.lang.Integer instId;
	private java.lang.String deductStlmDate;
	private java.lang.Integer instType;
	private java.lang.String stanceTime;
	private java.lang.Integer bankId;

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
	 * Return the value associated with the column: trade_time
	 */
	public java.util.Date getTradeTime () {
		return tradeTime;
	}

	/**
	 * Set the value related to the column: trade_time
	 * @param tradeTime the trade_time value
	 */
	public void setTradeTime (java.util.Date tradeTime) {
		this.tradeTime = tradeTime;
	}



	/**
	 * Return the value associated with the column: trade_amount
	 */
	public java.lang.Double getTradeAmount () {
		return tradeAmount;
	}

	/**
	 * Set the value related to the column: trade_amount
	 * @param tradeAmount the trade_amount value
	 */
	public void setTradeAmount (java.lang.Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}



	/**
	 * Return the value associated with the column: mer_fee
	 */
	public java.lang.Double getMerFee () {
		return merFee;
	}

	/**
	 * Set the value related to the column: mer_fee
	 * @param merFee the mer_fee value
	 */
	public void setMerFee (java.lang.Double merFee) {
		this.merFee = merFee;
	}



	/**
	 * Return the value associated with the column: change_amount
	 */
	public java.lang.Double getChangeAmount () {
		return changeAmount;
	}

	/**
	 * Set the value related to the column: change_amount
	 * @param changeAmount the change_amount value
	 */
	public void setChangeAmount (java.lang.Double changeAmount) {
		this.changeAmount = changeAmount;
	}



	/**
	 * Return the value associated with the column: account_amount
	 */
	public java.lang.Double getAccountAmount () {
		return accountAmount;
	}

	/**
	 * Set the value related to the column: account_amount
	 * @param accountAmount the account_amount value
	 */
	public void setAccountAmount (java.lang.Double accountAmount) {
		this.accountAmount = accountAmount;
	}



	/**
	 * Return the value associated with the column: trade_stance
	 */
	public java.lang.String getTradeStance () {
		return tradeStance;
	}

	/**
	 * Set the value related to the column: trade_stance
	 * @param tradeStance the trade_stance value
	 */
	public void setTradeStance (java.lang.String tradeStance) {
		this.tradeStance = tradeStance;
	}



	/**
	 * Return the value associated with the column: derc_status
	 */
	public java.lang.Integer getDercStatus () {
		return dercStatus;
	}

	/**
	 * Set the value related to the column: derc_status
	 * @param dercStatus the derc_status value
	 */
	public void setDercStatus (java.lang.Integer dercStatus) {
		this.dercStatus = dercStatus;
	}



	/**
	 * Return the value associated with the column: mer_state
	 */
	public java.lang.Integer getMerState () {
		return merState;
	}

	/**
	 * Set the value related to the column: mer_state
	 * @param merState the mer_state value
	 */
	public void setMerState (java.lang.Integer merState) {
		this.merState = merState;
	}



	/**
	 * Return the value associated with the column: mer_category
	 */
	public java.lang.Integer getMerCategory () {
		return merCategory;
	}

	/**
	 * Set the value related to the column: mer_category
	 * @param merCategory the mer_category value
	 */
	public void setMerCategory (java.lang.Integer merCategory) {
		this.merCategory = merCategory;
	}



	/**
	 * Return the value associated with the column: mer_name
	 */
	public java.lang.String getMerName () {
		return merName;
	}

	/**
	 * Set the value related to the column: mer_name
	 * @param merName the mer_name value
	 */
	public void setMerName (java.lang.String merName) {
		this.merName = merName;
	}



	/**
	 * Return the value associated with the column: inst_id
	 */
	public java.lang.Integer getInstId () {
		return instId;
	}

	/**
	 * Set the value related to the column: inst_id
	 * @param instId the inst_id value
	 */
	public void setInstId (java.lang.Integer instId) {
		this.instId = instId;
	}



	/**
	 * Return the value associated with the column: deduct_stlm_date
	 */
	public java.lang.String getDeductStlmDate () {
		return deductStlmDate;
	}

	/**
	 * Set the value related to the column: deduct_stlm_date
	 * @param deductStlmDate the deduct_stlm_date value
	 */
	public void setDeductStlmDate (java.lang.String deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
	}



	/**
	 * Return the value associated with the column: inst_type
	 */
	public java.lang.Integer getInstType () {
		return instType;
	}

	/**
	 * Set the value related to the column: inst_type
	 * @param instType the inst_type value
	 */
	public void setInstType (java.lang.Integer instType) {
		this.instType = instType;
	}



	/**
	 * Return the value associated with the column: stance_time
	 */
	public java.lang.String getStanceTime () {
		return stanceTime;
	}

	/**
	 * Set the value related to the column: stance_time
	 * @param stanceTime the stance_time value
	 */
	public void setStanceTime (java.lang.String stanceTime) {
		this.stanceTime = stanceTime;
	}



	/**
	 * Return the value associated with the column: bank_id
	 */
	public java.lang.Integer getBankId () {
		return bankId;
	}

	/**
	 * Set the value related to the column: bank_id
	 * @param bankId the bank_id value
	 */
	public void setBankId (java.lang.Integer bankId) {
		this.bankId = bankId;
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
		if (!(obj instanceof cn.com.chinaebi.dz.object.MerFundStance)) return false;
		else {
			cn.com.chinaebi.dz.object.MerFundStance merFundStance = (cn.com.chinaebi.dz.object.MerFundStance) obj;
			if (null == this.getId() || null == merFundStance.getId()) return false;
			else return (this.getId().equals(merFundStance.getId()));
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