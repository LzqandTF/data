package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseFtpUploadRecord;



public class FtpUploadRecord extends BaseFtpUploadRecord {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public FtpUploadRecord () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public FtpUploadRecord (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public FtpUploadRecord (
		java.lang.Integer id,
		java.lang.String deductStlmDate,
		java.lang.Integer objectId,
		java.lang.String objectName,
		java.lang.String uploadContent,
		java.util.Date generateTime,
		java.lang.Integer uploadStatus) {

		super (
			id,
			deductStlmDate,
			objectId,
			objectName,
			uploadContent,
			generateTime,
			uploadStatus);
	}

/*[CONSTRUCTOR MARKER END]*/


}