package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the custom_object table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="custom_object"
 */

public abstract class BaseCustomObject  implements Serializable {

	public static String REF = "CustomObject";
	public static String PROP_FILE_SUFFIX = "FileSuffix";
	public static String PROP_DATA_TYPE = "DataType";
	public static String PROP_ERROR_FILE_NAME = "ErrorFileName";
	public static String PROP_WHETHER_CREATE_FILE_BY_INST = "WhetherCreateFileByInst";
	public static String PROP_WHETHER_UPLOAD = "WhetherUpload";
	public static String PROP_FTP_PASSWORD = "FtpPassword";
	public static String PROP_WHETHER_CREATE_ERROR_FILE = "WhetherCreateErrorFile";
	public static String PROP_DZ_FILE_NAME = "DzFileName";
	public static String PROP_FTP_PORT = "FtpPort";
	public static String PROP_FTP_USERNAME = "FtpUsername";
	public static String PROP_GENERATE_NUMBER = "GenerateNumber";
	public static String PROP_FILE_NEED_ONLINE_DATA = "FileNeedOnlineData";
	public static String PROP_WHETHER_CREATE_FILE_BY_RANGE = "WhetherCreateFileByRange";
	public static String PROP_FILE_TYPE = "FileType";
	public static String PROP_ID = "Id";
	public static String PROP_OBJECT_NAME = "ObjectName";
	public static String PROP_FTP_ADDRESS = "FtpAddress";
	public static String PROP_FILE_ADDRESS = "FileAddress";
	public static String PROP_FTP_IP = "FtpIp";


	// constructors
	public BaseCustomObject () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomObject (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomObject (
		java.lang.Integer id,
		java.lang.String objectName,
		java.lang.String fileAddress,
		java.lang.String dzFileName,
		boolean whetherUpload,
		int generateNumber,
		boolean whetherCreateFileByRange,
		int fileType) {

		this.setId(id);
		this.setObjectName(objectName);
		this.setFileAddress(fileAddress);
		this.setDzFileName(dzFileName);
		this.setWhetherUpload(whetherUpload);
		this.setGenerateNumber(generateNumber);
		this.setWhetherCreateFileByRange(whetherCreateFileByRange);
		this.setFileType(fileType);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String objectName;
	private java.lang.String fileAddress;
	private java.lang.String dzFileName;
	private java.lang.String errorFileName;
	private boolean whetherUpload;
	private java.lang.String ftpIp;
	private java.lang.String ftpAddress;
	private java.lang.String ftpPort;
	private java.lang.String ftpUsername;
	private java.lang.String ftpPassword;
	private java.lang.String fileSuffix;
	private int generateNumber;
	private int fileNeedOnlineData;
	private int dataType;
	private int whetherCreateErrorFile;
	private boolean whetherCreateFileByRange;
	private int fileType;
	private boolean whetherCreateFileByInst;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="object_id"
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
	 * Return the value associated with the column: file_address
	 */
	public java.lang.String getFileAddress () {
		return fileAddress;
	}

	/**
	 * Set the value related to the column: file_address
	 * @param fileAddress the file_address value
	 */
	public void setFileAddress (java.lang.String fileAddress) {
		this.fileAddress = fileAddress;
	}



	/**
	 * Return the value associated with the column: dz_file_name
	 */
	public java.lang.String getDzFileName () {
		return dzFileName;
	}

	/**
	 * Set the value related to the column: dz_file_name
	 * @param dzFileName the dz_file_name value
	 */
	public void setDzFileName (java.lang.String dzFileName) {
		this.dzFileName = dzFileName;
	}



	/**
	 * Return the value associated with the column: error_file_name
	 */
	public java.lang.String getErrorFileName () {
		return errorFileName;
	}

	/**
	 * Set the value related to the column: error_file_name
	 * @param errorFileName the error_file_name value
	 */
	public void setErrorFileName (java.lang.String errorFileName) {
		this.errorFileName = errorFileName;
	}



	/**
	 * Return the value associated with the column: whether_upload
	 */
	public boolean isWhetherUpload () {
		return whetherUpload;
	}

	/**
	 * Set the value related to the column: whether_upload
	 * @param whetherUpload the whether_upload value
	 */
	public void setWhetherUpload (boolean whetherUpload) {
		this.whetherUpload = whetherUpload;
	}



	/**
	 * Return the value associated with the column: ftp_ip
	 */
	public java.lang.String getFtpIp () {
		return ftpIp;
	}

	/**
	 * Set the value related to the column: ftp_ip
	 * @param ftpIp the ftp_ip value
	 */
	public void setFtpIp (java.lang.String ftpIp) {
		this.ftpIp = ftpIp;
	}



	/**
	 * Return the value associated with the column: ftp_address
	 */
	public java.lang.String getFtpAddress () {
		return ftpAddress;
	}

	/**
	 * Set the value related to the column: ftp_address
	 * @param ftpAddress the ftp_address value
	 */
	public void setFtpAddress (java.lang.String ftpAddress) {
		this.ftpAddress = ftpAddress;
	}



	/**
	 * Return the value associated with the column: ftp_port
	 */
	public java.lang.String getFtpPort () {
		return ftpPort;
	}

	/**
	 * Set the value related to the column: ftp_port
	 * @param ftpPort the ftp_port value
	 */
	public void setFtpPort (java.lang.String ftpPort) {
		this.ftpPort = ftpPort;
	}



	/**
	 * Return the value associated with the column: ftp_username
	 */
	public java.lang.String getFtpUsername () {
		return ftpUsername;
	}

	/**
	 * Set the value related to the column: ftp_username
	 * @param ftpUsername the ftp_username value
	 */
	public void setFtpUsername (java.lang.String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}



	/**
	 * Return the value associated with the column: ftp_password
	 */
	public java.lang.String getFtpPassword () {
		return ftpPassword;
	}

	/**
	 * Set the value related to the column: ftp_password
	 * @param ftpPassword the ftp_password value
	 */
	public void setFtpPassword (java.lang.String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}



	/**
	 * Return the value associated with the column: file_suffix
	 */
	public java.lang.String getFileSuffix () {
		return fileSuffix;
	}

	/**
	 * Set the value related to the column: file_suffix
	 * @param fileSuffix the file_suffix value
	 */
	public void setFileSuffix (java.lang.String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}



	/**
	 * Return the value associated with the column: generate_number
	 */
	public int getGenerateNumber () {
		return generateNumber;
	}

	/**
	 * Set the value related to the column: generate_number
	 * @param generateNumber the generate_number value
	 */
	public void setGenerateNumber (int generateNumber) {
		this.generateNumber = generateNumber;
	}



	/**
	 * Return the value associated with the column: file_need_online_data
	 */
	public int getFileNeedOnlineData () {
		return fileNeedOnlineData;
	}

	/**
	 * Set the value related to the column: file_need_online_data
	 * @param fileNeedOnlineData the file_need_online_data value
	 */
	public void setFileNeedOnlineData (int fileNeedOnlineData) {
		this.fileNeedOnlineData = fileNeedOnlineData;
	}



	/**
	 * Return the value associated with the column: data_type
	 */
	public int getDataType () {
		return dataType;
	}

	/**
	 * Set the value related to the column: data_type
	 * @param dataType the data_type value
	 */
	public void setDataType (int dataType) {
		this.dataType = dataType;
	}



	/**
	 * Return the value associated with the column: whether_create_error_file
	 */
	public int getWhetherCreateErrorFile () {
		return whetherCreateErrorFile;
	}

	/**
	 * Set the value related to the column: whether_create_error_file
	 * @param whetherCreateErrorFile the whether_create_error_file value
	 */
	public void setWhetherCreateErrorFile (int whetherCreateErrorFile) {
		this.whetherCreateErrorFile = whetherCreateErrorFile;
	}



	/**
	 * Return the value associated with the column: whether_create_file_by_range
	 */
	public boolean isWhetherCreateFileByRange () {
		return whetherCreateFileByRange;
	}

	/**
	 * Set the value related to the column: whether_create_file_by_range
	 * @param whetherCreateFileByRange the whether_create_file_by_range value
	 */
	public void setWhetherCreateFileByRange (boolean whetherCreateFileByRange) {
		this.whetherCreateFileByRange = whetherCreateFileByRange;
	}



	/**
	 * Return the value associated with the column: file_type
	 */
	public int getFileType () {
		return fileType;
	}

	/**
	 * Set the value related to the column: file_type
	 * @param fileType the file_type value
	 */
	public void setFileType (int fileType) {
		this.fileType = fileType;
	}



	/**
	 * Return the value associated with the column: whether_create_file_by_inst
	 */
	public boolean isWhetherCreateFileByInst () {
		return whetherCreateFileByInst;
	}

	/**
	 * Set the value related to the column: whether_create_file_by_inst
	 * @param whetherCreateFileByInst the whether_create_file_by_inst value
	 */
	public void setWhetherCreateFileByInst (boolean whetherCreateFileByInst) {
		this.whetherCreateFileByInst = whetherCreateFileByInst;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.CustomObject)) return false;
		else {
			cn.com.chinaebi.dz.object.CustomObject customObject = (cn.com.chinaebi.dz.object.CustomObject) obj;
			if (null == this.getId() || null == customObject.getId()) return false;
			else return (this.getId().equals(customObject.getId()));
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