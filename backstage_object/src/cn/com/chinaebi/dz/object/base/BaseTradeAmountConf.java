package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the trade_amount_conf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="trade_amount_conf"
 */

public abstract class BaseTradeAmountConf  implements Serializable {

	public static String REF = "TradeAmountConf";
	public static String PROP_NAME = "Name";
	public static String PROP_TRADE_MONEY_STATUS = "TradeMoneyStatus";
	public static String PROP_TRADEMSG_TYPE = "TrademsgType";
	public static String PROP_PROCESS = "Process";
	public static String PROP_ID = "Id";


	// constructors
	public BaseTradeAmountConf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTradeAmountConf (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTradeAmountConf (
		java.lang.String id,
		java.lang.String process,
		java.lang.Integer trademsgType,
		java.lang.String name,
		boolean tradeMoneyStatus) {

		this.setId(id);
		this.setProcess(process);
		this.setTrademsgType(trademsgType);
		this.setName(name);
		this.setTradeMoneyStatus(tradeMoneyStatus);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String process;
	private java.lang.Integer trademsgType;
	private java.lang.String name;
	private boolean tradeMoneyStatus;



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
	 * Return the value associated with the column: process
	 */
	public java.lang.String getProcess () {
		return process;
	}

	/**
	 * Set the value related to the column: process
	 * @param process the process value
	 */
	public void setProcess (java.lang.String process) {
		this.process = process;
	}



	/**
	 * Return the value associated with the column: trademsg_type
	 */
	public java.lang.Integer getTrademsgType () {
		return trademsgType;
	}

	/**
	 * Set the value related to the column: trademsg_type
	 * @param trademsgType the trademsg_type value
	 */
	public void setTrademsgType (java.lang.Integer trademsgType) {
		this.trademsgType = trademsgType;
	}



	/**
	 * Return the value associated with the column: name_
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name_
	 * @param name the name_ value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: trade_money_status
	 */
	public boolean isTradeMoneyStatus () {
		return tradeMoneyStatus;
	}

	/**
	 * Set the value related to the column: trade_money_status
	 * @param tradeMoneyStatus the trade_money_status value
	 */
	public void setTradeMoneyStatus (boolean tradeMoneyStatus) {
		this.tradeMoneyStatus = tradeMoneyStatus;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.TradeAmountConf)) return false;
		else {
			cn.com.chinaebi.dz.object.TradeAmountConf tradeAmountConf = (cn.com.chinaebi.dz.object.TradeAmountConf) obj;
			if (null == this.getId() || null == tradeAmountConf.getId()) return false;
			else return (this.getId().equals(tradeAmountConf.getId()));
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