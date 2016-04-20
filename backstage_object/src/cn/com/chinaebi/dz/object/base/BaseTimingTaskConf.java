package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the timing_task_conf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="timing_task_conf"
 */

public abstract class BaseTimingTaskConf  implements Serializable {

	public static String REF = "TimingTaskConf";
	public static String PROP_ACQUISITION_TIME_DESC = "AcquisitionTimeDesc";
	public static String PROP_DZ_HANDLER_TIME_NAME = "DzHandlerTimeName";
	public static String PROP_DZ_HANDLER_TIME_DESC = "DzHandlerTimeDesc";
	public static String PROP_CHANNEL_ID = "ChannelId";
	public static String PROP_DZ_FILE_CREATE_TIME = "DzFileCreateTime";
	public static String PROP_INST_TYPE = "InstType";
	public static String PROP_DZ_FILE_CREATE_NAME = "DzFileCreateName";
	public static String PROP_GATHER_DATA_TIME = "GatherDataTime";
	public static String PROP_GATHER_DATA_TIME_NAME = "GatherDataTimeName";
	public static String PROP_GATHER_DATA_TIME_DESC = "GatherDataTimeDesc";
	public static String PROP_ACQUISITION_TIME_NAME = "AcquisitionTimeName";
	public static String PROP_ID = "Id";
	public static String PROP_ACQUISITION_TIME = "AcquisitionTime";
	public static String PROP_DZ_HANDLER_TIME = "DzHandlerTime";


	// constructors
	public BaseTimingTaskConf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTimingTaskConf (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTimingTaskConf (
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

		this.setId(id);
		this.setAcquisitionTime(acquisitionTime);
		this.setAcquisitionTimeDesc(acquisitionTimeDesc);
		this.setAcquisitionTimeName(acquisitionTimeName);
		this.setGatherDataTimeDesc(gatherDataTimeDesc);
		this.setGatherDataTimeName(gatherDataTimeName);
		this.setGatherDataTime(gatherDataTime);
		this.setDzHandlerTime(dzHandlerTime);
		this.setDzFileCreateTime(dzFileCreateTime);
		this.setDzFileCreateName(dzFileCreateName);
		this.setDzHandlerTimeDesc(dzHandlerTimeDesc);
		this.setDzHandlerTimeName(dzHandlerTimeName);
		this.setChannelId(channelId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String acquisitionTime;
	private java.lang.String acquisitionTimeDesc;
	private java.lang.String acquisitionTimeName;
	private java.lang.String gatherDataTimeDesc;
	private java.lang.String gatherDataTimeName;
	private java.lang.String gatherDataTime;
	private java.lang.String dzHandlerTime;
	private java.lang.String dzFileCreateTime;
	private java.lang.String dzFileCreateName;
	private java.lang.String dzHandlerTimeDesc;
	private java.lang.String dzHandlerTimeName;
	private java.lang.Integer channelId;
	private java.lang.Integer instType;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="inst_id"
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
	 * Return the value associated with the column: acquisition_time
	 */
	public java.lang.String getAcquisitionTime () {
		return acquisitionTime;
	}

	/**
	 * Set the value related to the column: acquisition_time
	 * @param acquisitionTime the acquisition_time value
	 */
	public void setAcquisitionTime (java.lang.String acquisitionTime) {
		this.acquisitionTime = acquisitionTime;
	}



	/**
	 * Return the value associated with the column: acquisition_time_desc
	 */
	public java.lang.String getAcquisitionTimeDesc () {
		return acquisitionTimeDesc;
	}

	/**
	 * Set the value related to the column: acquisition_time_desc
	 * @param acquisitionTimeDesc the acquisition_time_desc value
	 */
	public void setAcquisitionTimeDesc (java.lang.String acquisitionTimeDesc) {
		this.acquisitionTimeDesc = acquisitionTimeDesc;
	}



	/**
	 * Return the value associated with the column: acquisition_time_name
	 */
	public java.lang.String getAcquisitionTimeName () {
		return acquisitionTimeName;
	}

	/**
	 * Set the value related to the column: acquisition_time_name
	 * @param acquisitionTimeName the acquisition_time_name value
	 */
	public void setAcquisitionTimeName (java.lang.String acquisitionTimeName) {
		this.acquisitionTimeName = acquisitionTimeName;
	}



	/**
	 * Return the value associated with the column: gather_data_time_desc
	 */
	public java.lang.String getGatherDataTimeDesc () {
		return gatherDataTimeDesc;
	}

	/**
	 * Set the value related to the column: gather_data_time_desc
	 * @param gatherDataTimeDesc the gather_data_time_desc value
	 */
	public void setGatherDataTimeDesc (java.lang.String gatherDataTimeDesc) {
		this.gatherDataTimeDesc = gatherDataTimeDesc;
	}



	/**
	 * Return the value associated with the column: gather_data_time_name
	 */
	public java.lang.String getGatherDataTimeName () {
		return gatherDataTimeName;
	}

	/**
	 * Set the value related to the column: gather_data_time_name
	 * @param gatherDataTimeName the gather_data_time_name value
	 */
	public void setGatherDataTimeName (java.lang.String gatherDataTimeName) {
		this.gatherDataTimeName = gatherDataTimeName;
	}



	/**
	 * Return the value associated with the column: gather_data_time
	 */
	public java.lang.String getGatherDataTime () {
		return gatherDataTime;
	}

	/**
	 * Set the value related to the column: gather_data_time
	 * @param gatherDataTime the gather_data_time value
	 */
	public void setGatherDataTime (java.lang.String gatherDataTime) {
		this.gatherDataTime = gatherDataTime;
	}



	/**
	 * Return the value associated with the column: dz_handler_time
	 */
	public java.lang.String getDzHandlerTime () {
		return dzHandlerTime;
	}

	/**
	 * Set the value related to the column: dz_handler_time
	 * @param dzHandlerTime the dz_handler_time value
	 */
	public void setDzHandlerTime (java.lang.String dzHandlerTime) {
		this.dzHandlerTime = dzHandlerTime;
	}



	/**
	 * Return the value associated with the column: dz_file_create_time
	 */
	public java.lang.String getDzFileCreateTime () {
		return dzFileCreateTime;
	}

	/**
	 * Set the value related to the column: dz_file_create_time
	 * @param dzFileCreateTime the dz_file_create_time value
	 */
	public void setDzFileCreateTime (java.lang.String dzFileCreateTime) {
		this.dzFileCreateTime = dzFileCreateTime;
	}



	/**
	 * Return the value associated with the column: dz_file_create_time_name
	 */
	public java.lang.String getDzFileCreateName () {
		return dzFileCreateName;
	}

	/**
	 * Set the value related to the column: dz_file_create_time_name
	 * @param dzFileCreateName the dz_file_create_time_name value
	 */
	public void setDzFileCreateName (java.lang.String dzFileCreateName) {
		this.dzFileCreateName = dzFileCreateName;
	}



	/**
	 * Return the value associated with the column: dz_handler_time_desc
	 */
	public java.lang.String getDzHandlerTimeDesc () {
		return dzHandlerTimeDesc;
	}

	/**
	 * Set the value related to the column: dz_handler_time_desc
	 * @param dzHandlerTimeDesc the dz_handler_time_desc value
	 */
	public void setDzHandlerTimeDesc (java.lang.String dzHandlerTimeDesc) {
		this.dzHandlerTimeDesc = dzHandlerTimeDesc;
	}



	/**
	 * Return the value associated with the column: dz_handler_time_name
	 */
	public java.lang.String getDzHandlerTimeName () {
		return dzHandlerTimeName;
	}

	/**
	 * Set the value related to the column: dz_handler_time_name
	 * @param dzHandlerTimeName the dz_handler_time_name value
	 */
	public void setDzHandlerTimeName (java.lang.String dzHandlerTimeName) {
		this.dzHandlerTimeName = dzHandlerTimeName;
	}



	/**
	 * Return the value associated with the column: channel_id
	 */
	public java.lang.Integer getChannelId () {
		return channelId;
	}

	/**
	 * Set the value related to the column: channel_id
	 * @param channelId the channel_id value
	 */
	public void setChannelId (java.lang.Integer channelId) {
		this.channelId = channelId;
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
		if (!(obj instanceof cn.com.chinaebi.dz.object.TimingTaskConf)) return false;
		else {
			cn.com.chinaebi.dz.object.TimingTaskConf timingTaskConf = (cn.com.chinaebi.dz.object.TimingTaskConf) obj;
			if (null == this.getId() || null == timingTaskConf.getId()) return false;
			else return (this.getId().equals(timingTaskConf.getId()));
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