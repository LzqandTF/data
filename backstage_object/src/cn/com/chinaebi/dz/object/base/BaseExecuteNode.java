package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the execute_node table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="execute_node"
 */

public abstract class BaseExecuteNode  implements Serializable {

	public static String REF = "ExecuteNode";
	public static String PROP_INST_TYPE = "InstType";
	public static String PROP_INST_NAME = "instName";
	public static String PROP_DZ_FILE_CREATE = "DzFileCreate";
	public static String PROP_DEDUCT_SYS_ID = "DeductSysId";
	public static String PROP_ID = "Id";
	public static String PROP_DZ_FILE_GAIN = "DzFileGain";
	public static String PROP_TRADE_COLLECT = "TradeCollect";
	public static String PROP_DZ_HANDLE = "DzHandle";
	public static String PROP_DEDUCT_STML_DATE = "DeductStmlDate";
	public static String PROP_ERROR_HANDLE = "ErrorHandle";


	// constructors
	public BaseExecuteNode () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseExecuteNode (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseExecuteNode (
		java.lang.String id,
		java.lang.Integer deductSysId,
		java.util.Date deductStmlDate) {

		this.setId(id);
		this.setDeductSysId(deductSysId);
		this.setDeductStmlDate(deductStmlDate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.Integer deductSysId;
	private java.util.Date deductStmlDate;
	private java.lang.Integer tradeCollect;
	private java.lang.Integer dzFileGain;
	private java.lang.Integer dzHandle;
	private java.lang.Integer errorHandle;
	private java.lang.Integer dzFileCreate;
	private java.lang.String instName;
	private java.lang.Integer instType;



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
	 * Return the value associated with the column: deduct_stml_date
	 */
	public java.util.Date getDeductStmlDate () {
		return deductStmlDate;
	}

	/**
	 * Set the value related to the column: deduct_stml_date
	 * @param deductStmlDate the deduct_stml_date value
	 */
	public void setDeductStmlDate (java.util.Date deductStmlDate) {
		this.deductStmlDate = deductStmlDate;
	}



	/**
	 * Return the value associated with the column: trade_collect
	 */
	public java.lang.Integer getTradeCollect () {
		return tradeCollect;
	}

	/**
	 * Set the value related to the column: trade_collect
	 * @param tradeCollect the trade_collect value
	 */
	public void setTradeCollect (java.lang.Integer tradeCollect) {
		this.tradeCollect = tradeCollect;
	}



	/**
	 * Return the value associated with the column: dz_file_gain
	 */
	public java.lang.Integer getDzFileGain () {
		return dzFileGain;
	}

	/**
	 * Set the value related to the column: dz_file_gain
	 * @param dzFileGain the dz_file_gain value
	 */
	public void setDzFileGain (java.lang.Integer dzFileGain) {
		this.dzFileGain = dzFileGain;
	}



	/**
	 * Return the value associated with the column: dz_handle
	 */
	public java.lang.Integer getDzHandle () {
		return dzHandle;
	}

	/**
	 * Set the value related to the column: dz_handle
	 * @param dzHandle the dz_handle value
	 */
	public void setDzHandle (java.lang.Integer dzHandle) {
		this.dzHandle = dzHandle;
	}



	/**
	 * Return the value associated with the column: error_handle
	 */
	public java.lang.Integer getErrorHandle () {
		return errorHandle;
	}

	/**
	 * Set the value related to the column: error_handle
	 * @param errorHandle the error_handle value
	 */
	public void setErrorHandle (java.lang.Integer errorHandle) {
		this.errorHandle = errorHandle;
	}



	/**
	 * Return the value associated with the column: dz_file_create
	 */
	public java.lang.Integer getDzFileCreate () {
		return dzFileCreate;
	}

	/**
	 * Set the value related to the column: dz_file_create
	 * @param dzFileCreate the dz_file_create value
	 */
	public void setDzFileCreate (java.lang.Integer dzFileCreate) {
		this.dzFileCreate = dzFileCreate;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.ExecuteNode)) return false;
		else {
			cn.com.chinaebi.dz.object.ExecuteNode executeNode = (cn.com.chinaebi.dz.object.ExecuteNode) obj;
			if (null == this.getId() || null == executeNode.getId()) return false;
			else return (this.getId().equals(executeNode.getId()));
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