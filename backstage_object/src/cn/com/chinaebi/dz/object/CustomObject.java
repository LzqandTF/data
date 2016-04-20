package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseCustomObject;



public class CustomObject extends BaseCustomObject {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomObject () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomObject (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomObject (
		java.lang.Integer id,
		java.lang.String objectName,
		java.lang.String fileAddress,
		java.lang.String dzFileName,
		boolean whetherUpload,
		int generateNumber,
		boolean whetherCreateFileByRange,
		int fileType) {

		super (
			id,
			objectName,
			fileAddress,
			dzFileName,
			whetherUpload,
			generateNumber,
			whetherCreateFileByRange,
			fileType);
	}

/*[CONSTRUCTOR MARKER END]*/


}