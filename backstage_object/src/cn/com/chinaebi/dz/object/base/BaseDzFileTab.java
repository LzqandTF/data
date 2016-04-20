package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the dz_file_tab table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="dz_file_tab"
 */

public abstract class BaseDzFileTab  implements Serializable {

	public static String REF = "DzFileTab";
	public static String PROP_DEDUCT_SYS_DATE = "DeductSysDate";
	public static String PROP_FILE_NAME = "FileName";
	public static String PROP_FILE_TYPE = "FileType";
	public static String PROP_ID = "Id";
	public static String PROP_OBJECT_NAME = "ObjectName";
	public static String PROP_FILE_PATH = "FilePath";
	public static String PROP_CREATE_LAST_TIME = "CreateLastTime";
	public static String PROP_OBJECT_ID = "ObjectId";


	// constructors
	public BaseDzFileTab () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDzFileTab (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDzFileTab (
		java.lang.Integer id,
		java.lang.String deductSysDate,
		java.lang.String fileType,
		java.lang.String fileName,
		java.lang.String createLastTime,
		java.lang.String filePath,
		java.lang.Integer objectId,
		java.lang.String objectName) {

		this.setId(id);
		this.setDeductSysDate(deductSysDate);
		this.setFileType(fileType);
		this.setFileName(fileName);
		this.setCreateLastTime(createLastTime);
		this.setFilePath(filePath);
		this.setObjectId(objectId);
		this.setObjectName(objectName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String deductSysDate;
	private java.lang.String fileType;
	private java.lang.String fileName;
	private java.lang.String createLastTime;
	private java.lang.String filePath;
	private java.lang.Integer objectId;
	private java.lang.String objectName;



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
	 * Return the value associated with the column: deduct_sys_date
	 */
	public java.lang.String getDeductSysDate () {
		return deductSysDate;
	}

	/**
	 * Set the value related to the column: deduct_sys_date
	 * @param deductSysDate the deduct_sys_date value
	 */
	public void setDeductSysDate (java.lang.String deductSysDate) {
		this.deductSysDate = deductSysDate;
	}



	/**
	 * Return the value associated with the column: file_type
	 */
	public java.lang.String getFileType () {
		return fileType;
	}

	/**
	 * Set the value related to the column: file_type
	 * @param fileType the file_type value
	 */
	public void setFileType (java.lang.String fileType) {
		this.fileType = fileType;
	}



	/**
	 * Return the value associated with the column: file_name
	 */
	public java.lang.String getFileName () {
		return fileName;
	}

	/**
	 * Set the value related to the column: file_name
	 * @param fileName the file_name value
	 */
	public void setFileName (java.lang.String fileName) {
		this.fileName = fileName;
	}



	/**
	 * Return the value associated with the column: create_last_time
	 */
	public java.lang.String getCreateLastTime () {
		return createLastTime;
	}

	/**
	 * Set the value related to the column: create_last_time
	 * @param createLastTime the create_last_time value
	 */
	public void setCreateLastTime (java.lang.String createLastTime) {
		this.createLastTime = createLastTime;
	}



	/**
	 * Return the value associated with the column: file_path
	 */
	public java.lang.String getFilePath () {
		return filePath;
	}

	/**
	 * Set the value related to the column: file_path
	 * @param filePath the file_path value
	 */
	public void setFilePath (java.lang.String filePath) {
		this.filePath = filePath;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.DzFileTab)) return false;
		else {
			cn.com.chinaebi.dz.object.DzFileTab dzFileTab = (cn.com.chinaebi.dz.object.DzFileTab) obj;
			if (null == this.getId() || null == dzFileTab.getId()) return false;
			else return (this.getId().equals(dzFileTab.getId()));
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