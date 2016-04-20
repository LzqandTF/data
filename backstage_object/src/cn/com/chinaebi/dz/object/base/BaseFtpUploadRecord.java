package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the ftp_upload_record table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="ftp_upload_record"
 */

public abstract class BaseFtpUploadRecord  implements Serializable {

	public static String REF = "FtpUploadRecord";
	public static String PROP_DEDUCT_STLM_DATE = "DeductStlmDate";
	public static String PROP_UPLOAD_STATUS = "UploadStatus";
	public static String PROP_ID = "Id";
	public static String PROP_GENERATE_TIME = "GenerateTime";
	public static String PROP_OBJECT_NAME = "ObjectName";
	public static String PROP_UPLOAD_CONTENT = "UploadContent";
	public static String PROP_OBJECT_ID = "ObjectId";


	// constructors
	public BaseFtpUploadRecord () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseFtpUploadRecord (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseFtpUploadRecord (
		java.lang.Integer id,
		java.lang.String deductStlmDate,
		java.lang.Integer objectId,
		java.lang.String objectName,
		java.lang.String uploadContent,
		java.util.Date generateTime,
		java.lang.Integer uploadStatus) {

		this.setId(id);
		this.setDeductStlmDate(deductStlmDate);
		this.setObjectId(objectId);
		this.setObjectName(objectName);
		this.setUploadContent(uploadContent);
		this.setGenerateTime(generateTime);
		this.setUploadStatus(uploadStatus);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String deductStlmDate;
	private java.lang.Integer objectId;
	private java.lang.String objectName;
	private java.lang.String uploadContent;
	private java.util.Date generateTime;
	private java.lang.Integer uploadStatus;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="id"
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
	 * Return the value associated with the column: object_id
	 */
	public java.lang.Integer getObjectId () {
		return objectId;
	}

	/**
	 * Set the value related to the column: object_id
	 * @param objectId the object_id value
	 */
	public void setObjectId (java.lang.Integer objectId) {
		this.objectId = objectId;
	}



	/**
	 * Return the value associated with the column: object_name
	 */
	public java.lang.String getObjectName () {
		return objectName;
	}

	/**
	 * Set the value related to the column: object_name
	 * @param objectName the object_name value
	 */
	public void setObjectName (java.lang.String objectName) {
		this.objectName = objectName;
	}



	/**
	 * Return the value associated with the column: upload_content
	 */
	public java.lang.String getUploadContent () {
		return uploadContent;
	}

	/**
	 * Set the value related to the column: upload_content
	 * @param uploadContent the upload_content value
	 */
	public void setUploadContent (java.lang.String uploadContent) {
		this.uploadContent = uploadContent;
	}



	/**
	 * Return the value associated with the column: generate_time
	 */
	public java.util.Date getGenerateTime () {
		return generateTime;
	}

	/**
	 * Set the value related to the column: generate_time
	 * @param generateTime the generate_time value
	 */
	public void setGenerateTime (java.util.Date generateTime) {
		this.generateTime = generateTime;
	}



	/**
	 * Return the value associated with the column: upload_status
	 */
	public java.lang.Integer getUploadStatus () {
		return uploadStatus;
	}

	/**
	 * Set the value related to the column: upload_status
	 * @param uploadStatus the upload_status value
	 */
	public void setUploadStatus (java.lang.Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.FtpUploadRecord)) return false;
		else {
			cn.com.chinaebi.dz.object.FtpUploadRecord ftpUploadRecord = (cn.com.chinaebi.dz.object.FtpUploadRecord) obj;
			if (null == this.getId() || null == ftpUploadRecord.getId()) return false;
			else return (this.getId().equals(ftpUploadRecord.getId()));
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