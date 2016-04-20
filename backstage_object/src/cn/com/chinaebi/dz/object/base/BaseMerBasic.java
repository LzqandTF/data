package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mer_basic table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mer_basic"
 */

public abstract class BaseMerBasic  implements Serializable {

	public static String REF = "MerBasic";
	public static String PROP_CHANNEL = "Channel";
	public static String PROP_MER_STATE = "MerState";
	public static String PROP_MER_TYPE = "MerType";
	public static String PROP_PROVINCES = "Provinces";
	public static String PROP_MER_NAME = "MerName";
	public static String PROP_MER_ABBREVIATION = "MerAbbreviation";
	public static String PROP_CITY = "City";
	public static String PROP_EXPANDNO = "Expandno";
	public static String PROP_END_DATE = "EndDate";
	public static String PROP_START_DATE = "StartDate";
	public static String PROP_MER_CATEGORY = "MerCategory";
	public static String PROP_ID = "Id";
	public static String PROP_EXPAND = "Expand";


	// constructors
	public BaseMerBasic () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMerBasic (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMerBasic (
		java.lang.String id,
		java.lang.String merName,
		java.lang.Integer merCategory,
		java.lang.Integer merState,
		java.lang.Integer startDate,
		java.lang.Integer endDate) {

		this.setId(id);
		this.setMerName(merName);
		this.setMerCategory(merCategory);
		this.setMerState(merState);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String merName;
	private java.lang.Integer merCategory;
	private java.lang.String merAbbreviation;
	private java.lang.Integer merState;
	private java.lang.String city;
	private java.lang.Integer merType;
	private java.lang.Integer startDate;
	private java.lang.Integer endDate;
	private java.lang.String channel;
	private java.lang.String expand;
	private java.lang.String expandno;
	private java.lang.String provinces;

	// collections
	private java.util.Set<cn.com.chinaebi.dz.object.MerBilling> merBillings;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="mer_code"
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
	 * Return the value associated with the column: mer_name
	 */
	public java.lang.String getMerName () {
		return merName;
	}

	/**
	 * Set the value related to the column: mer_name
	 * @param merName the mer_name value
	 */
	public void setMerName (java.lang.String merName) {
		this.merName = merName;
	}



	/**
	 * Return the value associated with the column: mer_category
	 */
	public java.lang.Integer getMerCategory () {
		return merCategory;
	}

	/**
	 * Set the value related to the column: mer_category
	 * @param merCategory the mer_category value
	 */
	public void setMerCategory (java.lang.Integer merCategory) {
		this.merCategory = merCategory;
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
	 * Return the value associated with the column: mer_state
	 */
	public java.lang.Integer getMerState () {
		return merState;
	}

	/**
	 * Set the value related to the column: mer_state
	 * @param merState the mer_state value
	 */
	public void setMerState (java.lang.Integer merState) {
		this.merState = merState;
	}



	/**
	 * Return the value associated with the column: city
	 */
	public java.lang.String getCity () {
		return city;
	}

	/**
	 * Set the value related to the column: city
	 * @param city the city value
	 */
	public void setCity (java.lang.String city) {
		this.city = city;
	}



	/**
	 * Return the value associated with the column: mer_type
	 */
	public java.lang.Integer getMerType () {
		return merType;
	}

	/**
	 * Set the value related to the column: mer_type
	 * @param merType the mer_type value
	 */
	public void setMerType (java.lang.Integer merType) {
		this.merType = merType;
	}



	/**
	 * Return the value associated with the column: startDate
	 */
	public java.lang.Integer getStartDate () {
		return startDate;
	}

	/**
	 * Set the value related to the column: startDate
	 * @param startDate the startDate value
	 */
	public void setStartDate (java.lang.Integer startDate) {
		this.startDate = startDate;
	}



	/**
	 * Return the value associated with the column: endDate
	 */
	public java.lang.Integer getEndDate () {
		return endDate;
	}

	/**
	 * Set the value related to the column: endDate
	 * @param endDate the endDate value
	 */
	public void setEndDate (java.lang.Integer endDate) {
		this.endDate = endDate;
	}



	/**
	 * Return the value associated with the column: channel
	 */
	public java.lang.String getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: channel
	 * @param channel the channel value
	 */
	public void setChannel (java.lang.String channel) {
		this.channel = channel;
	}



	/**
	 * Return the value associated with the column: expand
	 */
	public java.lang.String getExpand () {
		return expand;
	}

	/**
	 * Set the value related to the column: expand
	 * @param expand the expand value
	 */
	public void setExpand (java.lang.String expand) {
		this.expand = expand;
	}



	/**
	 * Return the value associated with the column: expandno
	 */
	public java.lang.String getExpandno () {
		return expandno;
	}

	/**
	 * Set the value related to the column: expandno
	 * @param expandno the expandno value
	 */
	public void setExpandno (java.lang.String expandno) {
		this.expandno = expandno;
	}



	/**
	 * Return the value associated with the column: provinces
	 */
	public java.lang.String getProvinces () {
		return provinces;
	}

	/**
	 * Set the value related to the column: provinces
	 * @param provinces the provinces value
	 */
	public void setProvinces (java.lang.String provinces) {
		this.provinces = provinces;
	}


 
	public java.util.Set<cn.com.chinaebi.dz.object.MerBilling> getMerBillings () {
			return merBillings==null? new java.util.TreeSet<cn.com.chinaebi.dz.object.MerBilling>():merBillings;
	}

	/**
	 * Set the value related to the column: MerBillings
	 * @param merBillings the MerBillings value
	 */
	public void setMerBillings (java.util.Set<cn.com.chinaebi.dz.object.MerBilling> merBillings) {
		this.merBillings = merBillings;
	}

	public void addToMerBillings (cn.com.chinaebi.dz.object.MerBilling merBilling) {
		if (null == getMerBillings()) setMerBillings(new java.util.TreeSet<cn.com.chinaebi.dz.object.MerBilling>());
		getMerBillings().add(merBilling);
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.MerBasic)) return false;
		else {
			cn.com.chinaebi.dz.object.MerBasic merBasic = (cn.com.chinaebi.dz.object.MerBasic) obj;
			if (null == this.getId() || null == merBasic.getId()) return false;
			else return (this.getId().equals(merBasic.getId()));
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