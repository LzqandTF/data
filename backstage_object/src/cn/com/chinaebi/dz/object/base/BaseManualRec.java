package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the manual_rec table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="manual_rec"
 */

public abstract class BaseManualRec  implements Serializable {

	public static String REF = "ManualRec";
	public static String PROP_ADDORSUB = "Addorsub";
	public static String PROP_MER_CODE = "MerCode";
	public static String PROP_SETTLE_TIME = "SettleTime";
	public static String PROP_MER_BALANCE = "MerBalance";
	public static String PROP_DATA_STATUS = "DataStatus";
	public static String PROP_REQUEST_DESC = "RequestDesc";
	public static String PROP_MER_ABBREVIATION = "MerAbbreviation";
	public static String PROP_AUDITOR_USER_NAME = "AuditorUserName";
	public static String PROP_AUDIT_TIME = "AuditTime";
	public static String PROP_AUDIT_DESC = "AuditDesc";
	public static String PROP_REC_AMOUNT = "RecAmount";
	public static String PROP_HANDLER_TIME = "HandlerTime";
	public static String PROP_WHETHER_JS = "WhetherJs";
	public static String PROP_ID = "Id";
	public static String PROP_SEND_USER_NAME = "SendUserName";


	// constructors
	public BaseManualRec () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseManualRec (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseManualRec (
		java.lang.String id,
		cn.com.chinaebi.dz.object.MerBasic merCode,
		java.lang.String recAmount,
		java.lang.String merBalance,
		java.lang.Integer addorsub,
		java.util.Date handlerTime,
		java.util.Date auditTime,
		java.lang.String sendUserName,
		java.lang.String requestDesc) {

		this.setId(id);
		this.setMerCode(merCode);
		this.setRecAmount(recAmount);
		this.setMerBalance(merBalance);
		this.setAddorsub(addorsub);
		this.setHandlerTime(handlerTime);
		this.setAuditTime(auditTime);
		this.setSendUserName(sendUserName);
		this.setRequestDesc(requestDesc);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String merAbbreviation;
	private java.lang.String recAmount;
	private java.lang.String merBalance;
	private java.lang.Integer addorsub;
	private java.util.Date handlerTime;
	private java.lang.Integer settleTime;
	private java.util.Date auditTime;
	private java.lang.String sendUserName;
	private java.lang.String auditorUserName;
	private java.lang.Integer dataStatus;
	private java.lang.String requestDesc;
	private java.lang.String auditDesc;
	private boolean whetherJs;

	// many to one
	private cn.com.chinaebi.dz.object.MerBasic merCode;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
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
	 * Return the value associated with the column: mer_abbreviation
	 */
	public java.lang.String getMerAbbreviation () {
		return merAbbreviation;
	}

	/**
	 * Set the value related to the column: mer_abbreviation
	 * @param merAbbreviation the mer_abbreviation value
	 */
	public void setMerAbbreviation (java.lang.String merAbbreviation) {
		this.merAbbreviation = merAbbreviation;
	}



	/**
	 * Return the value associated with the column: rec_amount
	 */
	public java.lang.String getRecAmount () {
		return recAmount;
	}

	/**
	 * Set the value related to the column: rec_amount
	 * @param recAmount the rec_amount value
	 */
	public void setRecAmount (java.lang.String recAmount) {
		this.recAmount = recAmount;
	}



	/**
	 * Return the value associated with the column: mer_balance
	 */
	public java.lang.String getMerBalance () {
		return merBalance;
	}

	/**
	 * Set the value related to the column: mer_balance
	 * @param merBalance the mer_balance value
	 */
	public void setMerBalance (java.lang.String merBalance) {
		this.merBalance = merBalance;
	}



	/**
	 * Return the value associated with the column: addorsub
	 */
	public java.lang.Integer getAddorsub () {
		return addorsub;
	}

	/**
	 * Set the value related to the column: addorsub
	 * @param addorsub the addorsub value
	 */
	public void setAddorsub (java.lang.Integer addorsub) {
		this.addorsub = addorsub;
	}



	/**
	 * Return the value associated with the column: handler_time
	 */
	public java.util.Date getHandlerTime () {
		return handlerTime;
	}

	/**
	 * Set the value related to the column: handler_time
	 * @param handlerTime the handler_time value
	 */
	public void setHandlerTime (java.util.Date handlerTime) {
		this.handlerTime = handlerTime;
	}



	/**
	 * Return the value associated with the column: settle_time
	 */
	public java.lang.Integer getSettleTime () {
		return settleTime;
	}

	/**
	 * Set the value related to the column: settle_time
	 * @param settleTime the settle_time value
	 */
	public void setSettleTime (java.lang.Integer settleTime) {
		this.settleTime = settleTime;
	}



	/**
	 * Return the value associated with the column: audit_time
	 */
	public java.util.Date getAuditTime () {
		return auditTime;
	}

	/**
	 * Set the value related to the column: audit_time
	 * @param auditTime the audit_time value
	 */
	public void setAuditTime (java.util.Date auditTime) {
		this.auditTime = auditTime;
	}



	/**
	 * Return the value associated with the column: send_user_name
	 */
	public java.lang.String getSendUserName () {
		return sendUserName;
	}

	/**
	 * Set the value related to the column: send_user_name
	 * @param sendUserName the send_user_name value
	 */
	public void setSendUserName (java.lang.String sendUserName) {
		this.sendUserName = sendUserName;
	}



	/**
	 * Return the value associated with the column: auditor_user_name
	 */
	public java.lang.String getAuditorUserName () {
		return auditorUserName;
	}

	/**
	 * Set the value related to the column: auditor_user_name
	 * @param auditorUserName the auditor_user_name value
	 */
	public void setAuditorUserName (java.lang.String auditorUserName) {
		this.auditorUserName = auditorUserName;
	}



	/**
	 * Return the value associated with the column: data_status
	 */
	public java.lang.Integer getDataStatus () {
		return dataStatus;
	}

	/**
	 * Set the value related to the column: data_status
	 * @param dataStatus the data_status value
	 */
	public void setDataStatus (java.lang.Integer dataStatus) {
		this.dataStatus = dataStatus;
	}



	/**
	 * Return the value associated with the column: request_desc
	 */
	public java.lang.String getRequestDesc () {
		return requestDesc;
	}

	/**
	 * Set the value related to the column: request_desc
	 * @param requestDesc the request_desc value
	 */
	public void setRequestDesc (java.lang.String requestDesc) {
		this.requestDesc = requestDesc;
	}



	/**
	 * Return the value associated with the column: audit_desc
	 */
	public java.lang.String getAuditDesc () {
		return auditDesc;
	}

	/**
	 * Set the value related to the column: audit_desc
	 * @param auditDesc the audit_desc value
	 */
	public void setAuditDesc (java.lang.String auditDesc) {
		this.auditDesc = auditDesc;
	}



	/**
	 * Return the value associated with the column: whetherJs
	 */
	public boolean isWhetherJs () {
		return whetherJs;
	}

	/**
	 * Set the value related to the column: whetherJs
	 * @param whetherJs the whetherJs value
	 */
	public void setWhetherJs (boolean whetherJs) {
		this.whetherJs = whetherJs;
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
		if (!(obj instanceof cn.com.chinaebi.dz.object.ManualRec)) return false;
		else {
			cn.com.chinaebi.dz.object.ManualRec manualRec = (cn.com.chinaebi.dz.object.ManualRec) obj;
			if (null == this.getId() || null == manualRec.getId()) return false;
			else return (this.getId().equals(manualRec.getId()));
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