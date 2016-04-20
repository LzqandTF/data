package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the error_tk_lst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="error_tk_lst"
 */

public abstract class BaseErrorTkLst  implements Serializable {

	public static String REF = "ErrorTkLst";
	public static String PROP_INST_TYPE = "InstType";
	public static String PROP_TRADE_AMOUNT = "TradeAmount";
	public static String PROP_BANK_ID = "BankId";
	public static String PROP_DEDUCT_SYS_ID = "DeductSysId";
	public static String PROP_HANDLING_ID = "HandlingId";
	public static String PROP_ID = "Id";
	public static String PROP_TRADE_TIME = "TradeTime";
	public static String PROP_SYN_FLAG = "SynFlag";


	// constructors
	public BaseErrorTkLst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseErrorTkLst (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseErrorTkLst (
		java.lang.String id,
		java.util.Date tradeTime,
		java.lang.Integer instType,
		java.lang.Integer handlingId,
		java.lang.Integer bankId) {

		this.setId(id);
		this.setTradeTime(tradeTime);
		this.setInstType(instType);
		this.setHandlingId(handlingId);
		this.setBankId(bankId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.util.Date tradeTime;
	private java.lang.Long tradeAmount;
	private java.lang.Integer deductSysId;
	private java.lang.Integer instType;
	private java.lang.Integer handlingId;
	private java.lang.Integer bankId;
	private boolean synFlag;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="trade_id"
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
	public java.lang.Long getTradeAmount () {
		return tradeAmount;
	}

	/**
	 * Set the value related to the column: trade_amount
	 * @param tradeAmount the trade_amount value
	 */
	public void setTradeAmount (java.lang.Long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}



	/**
	 * Return the value associated with the column: deduct_sys_id
	 */
	public java.lang.Integer getDeductSysId () {
		return deductSysId;
	}

	/**
	 * Set the value related to the column: deduct_sys_id
	 * @param deductSysId the deduct_sys_id value
	 */
	public void setDeductSysId (java.lang.Integer deductSysId) {
		this.deductSysId = deductSysId;
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
	 * Return the value associated with the column: handling_id
	 */
	public java.lang.Integer getHandlingId () {
		return handlingId;
	}

	/**
	 * Set the value related to the column: handling_id
	 * @param handlingId the handling_id value
	 */
	public void setHandlingId (java.lang.Integer handlingId) {
		this.handlingId = handlingId;
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
	 * Return the value associated with the column: syn_flag
	 */
	public boolean isSynFlag () {
		return synFlag;
	}

	/**
	 * Set the value related to the column: syn_flag
	 * @param synFlag the syn_flag value
	 */
	public void setSynFlag (boolean synFlag) {
		this.synFlag = synFlag;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.ErrorTkLst)) return false;
		else {
			cn.com.chinaebi.dz.object.ErrorTkLst errorTkLst = (cn.com.chinaebi.dz.object.ErrorTkLst) obj;
			if (null == this.getId() || null == errorTkLst.getId()) return false;
			else return (this.getId().equals(errorTkLst.getId()));
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