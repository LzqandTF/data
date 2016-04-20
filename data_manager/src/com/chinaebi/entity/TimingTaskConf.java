package com.chinaebi.entity;

@SuppressWarnings("serial")
public class TimingTaskConf implements java.io.Serializable{
	public TimingTaskConf(){
		super();
	}
	private int instId;
	private String acquisitionTime;
	private String gatherDataTime;
	private String dzHandlerTime;
	private String acquisitionTimeDesc;
	private String gatherDataTimeDesc;
	private String dzHandlerTimeDesc;
	private String acquisitionTimeName;
	private String gatherDataTimeName;
	private String dzHandlerTimeName;
	private String gatherDataIntervalTime;
	private String acquisitionIntervalTime;
	private String dzIntervalTime;
	private int gatherDataMostTimes;
	private int acquisitionMostTimes;
	private int dzMostTimes;
	private String dzHandlerEndTime;
	private String dzFileType;
	private String dzFileCreateTime;
	private String dzFileCreateTimeName;
	private int channel_id;
	private int inst_type;
	
	
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getDzFileCreateTime() {
		return dzFileCreateTime;
	}
	public void setDzFileCreateTime(String dzFileCreateTime) {
		this.dzFileCreateTime = dzFileCreateTime;
	}
	public String getDzFileCreateTimeName() {
		return dzFileCreateTimeName;
	}
	public void setDzFileCreateTimeName(String dzFileCreateTimeName) {
		this.dzFileCreateTimeName = dzFileCreateTimeName;
	}
	public String getDzFileType() {
		return dzFileType;
	}
	public void setDzFileType(String dzFileType) {
		this.dzFileType = dzFileType;
	}
	public String getDzHandlerEndTime() {
		return dzHandlerEndTime;
	}
	public void setDzHandlerEndTime(String dzHandlerEndTime) {
		this.dzHandlerEndTime = dzHandlerEndTime;
	}
	public String getGatherDataIntervalTime() {
		return gatherDataIntervalTime;
	}
	public void setGatherDataIntervalTime(String gatherDataIntervalTime) {
		this.gatherDataIntervalTime = gatherDataIntervalTime;
	}
	public String getAcquisitionIntervalTime() {
		return acquisitionIntervalTime;
	}
	public void setAcquisitionIntervalTime(String acquisitionIntervalTime) {
		this.acquisitionIntervalTime = acquisitionIntervalTime;
	}
	public String getDzIntervalTime() {
		return dzIntervalTime;
	}
	public void setDzIntervalTime(String dzIntervalTime) {
		this.dzIntervalTime = dzIntervalTime;
	}
	public int getGatherDataMostTimes() {
		return gatherDataMostTimes;
	}
	public void setGatherDataMostTimes(int gatherDataMostTimes) {
		this.gatherDataMostTimes = gatherDataMostTimes;
	}
	public int getAcquisitionMostTimes() {
		return acquisitionMostTimes;
	}
	public void setAcquisitionMostTimes(int acquisitionMostTimes) {
		this.acquisitionMostTimes = acquisitionMostTimes;
	}
	public int getDzMostTimes() {
		return dzMostTimes;
	}
	public void setDzMostTimes(int dzMostTimes) {
		this.dzMostTimes = dzMostTimes;
	}
	public String getAcquisitionTimeName() {
		return acquisitionTimeName;
	}
	public void setAcquisitionTimeName(String acquisitionTimeName) {
		this.acquisitionTimeName = acquisitionTimeName;
	}
	public String getGatherDataTimeName() {
		return gatherDataTimeName;
	}
	public void setGatherDataTimeName(String gatherDataTimeName) {
		this.gatherDataTimeName = gatherDataTimeName;
	}
	public String getDzHandlerTimeName() {
		return dzHandlerTimeName;
	}
	public void setDzHandlerTimeName(String dzHandlerTimeName) {
		this.dzHandlerTimeName = dzHandlerTimeName;
	}
	public int getInstId() {
		return instId;
	}
	public void setInstId(int instId) {
		this.instId = instId;
	}
	public String getAcquisitionTime() {
		return acquisitionTime;
	}
	public void setAcquisitionTime(String acquisitionTime) {
		this.acquisitionTime = acquisitionTime;
	}
	public String getGatherDataTime() {
		return gatherDataTime;
	}
	public void setGatherDataTime(String gatherDataTime) {
		this.gatherDataTime = gatherDataTime;
	}
	public String getDzHandlerTime() {
		return dzHandlerTime;
	}
	public void setDzHandlerTime(String dzHandlerTime) {
		this.dzHandlerTime = dzHandlerTime;
	}
	public String getAcquisitionTimeDesc() {
		return acquisitionTimeDesc;
	}
	public void setAcquisitionTimeDesc(String acquisitionTimeDesc) {
		this.acquisitionTimeDesc = acquisitionTimeDesc;
	}
	public String getGatherDataTimeDesc() {
		return gatherDataTimeDesc;
	}
	public void setGatherDataTimeDesc(String gatherDataTimeDesc) {
		this.gatherDataTimeDesc = gatherDataTimeDesc;
	}
	public String getDzHandlerTimeDesc() {
		return dzHandlerTimeDesc;
	}
	public void setDzHandlerTimeDesc(String dzHandlerTimeDesc) {
		this.dzHandlerTimeDesc = dzHandlerTimeDesc;
	}
	public int getInst_type() {
		return inst_type;
	}
	public void setInst_type(int inst_type) {
		this.inst_type = inst_type;
	}
}
