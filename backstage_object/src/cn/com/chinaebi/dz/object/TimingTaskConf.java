package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseTimingTaskConf;



public class TimingTaskConf extends BaseTimingTaskConf {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TimingTaskConf () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TimingTaskConf (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TimingTaskConf (
		java.lang.Integer id,
		java.lang.String acquisitionTime,
		java.lang.String acquisitionTimeDesc,
		java.lang.String acquisitionTimeName,
		java.lang.String gatherDataTimeDesc,
		java.lang.String gatherDataTimeName,
		java.lang.String gatherDataTime,
		java.lang.String dzHandlerTime,
		java.lang.String dzFileCreateTime,
		java.lang.String dzFileCreateName,
		java.lang.String dzHandlerTimeDesc,
		java.lang.String dzHandlerTimeName,
		java.lang.Integer channelId) {

		super (
			id,
			acquisitionTime,
			acquisitionTimeDesc,
			acquisitionTimeName,
			gatherDataTimeDesc,
			gatherDataTimeName,
			gatherDataTime,
			dzHandlerTime,
			dzFileCreateTime,
			dzFileCreateName,
			dzHandlerTimeDesc,
			dzHandlerTimeName,
			channelId);
	}

/*[CONSTRUCTOR MARKER END]*/


}