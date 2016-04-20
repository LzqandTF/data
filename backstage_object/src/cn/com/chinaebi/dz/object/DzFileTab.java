package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseDzFileTab;



public class DzFileTab extends BaseDzFileTab {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DzFileTab () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DzFileTab (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DzFileTab (
		java.lang.Integer id,
		java.lang.String deductSysDate,
		java.lang.String fileType,
		java.lang.String fileName,
		java.lang.String createLastTime,
		java.lang.String filePath,
		java.lang.Integer objectId,
		java.lang.String objectName) {

		super (
			id,
			deductSysDate,
			fileType,
			fileName,
			createLastTime,
			filePath,
			objectId,
			objectName);
	}

/*[CONSTRUCTOR MARKER END]*/


}