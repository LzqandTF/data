package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the fee_calc_mode table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="fee_calc_mode"
 */

public abstract class BaseFeeCalcMode  implements Serializable {

	public static String REF = "FeeCalcMode";
	public static String PROP_BASE_FEE = "BaseFee";
	public static String PROP_TYPE = "Type";
	public static String PROP_MAX_FEE = "MaxFee";
	public static String PROP_TRANS_MODE = "TransMode";
	public static String PROP_GID = "Gid";
	public static String PROP_CALC_MODE = "CalcMode";
	public static String PROP_GATE_ID = "GateId";
	public static String PROP_BK_FEE_MODE = "BkFeeMode";
	public static String PROP_MIN_FEE = "MinFee";
	public static String PROP_STATE = "State";
	public static String PROP_CALC_COND = "CalcCond";
	public static String PROP_AUTHOR_TYPE = "AuthorType";
	public static String PROP_ID = "Id";


	// constructors
	public BaseFeeCalcMode () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseFeeCalcMode (cn.com.chinaebi.dz.object.FeeCalcModePK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private cn.com.chinaebi.dz.object.FeeCalcModePK id;

	// fields
	private java.lang.Byte type;
	private java.lang.String calcMode;
	private java.lang.Integer baseFee;
	private java.lang.Integer minFee;
	private java.lang.Integer maxFee;
	private java.lang.String calcCond;
	private java.lang.Byte authorType;
	private java.lang.Byte state;
	private java.lang.Byte transMode;
	private java.lang.String gateId;
	private java.lang.String bkFeeMode;
	private java.lang.Integer gid;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public cn.com.chinaebi.dz.object.FeeCalcModePK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (cn.com.chinaebi.dz.object.FeeCalcModePK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: type
	 */
	public java.lang.Byte getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.Byte type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: calc_mode
	 */
	public java.lang.String getCalcMode () {
		return calcMode;
	}

	/**
	 * Set the value related to the column: calc_mode
	 * @param calcMode the calc_mode value
	 */
	public void setCalcMode (java.lang.String calcMode) {
		this.calcMode = calcMode;
	}



	/**
	 * Return the value associated with the column: base_fee
	 */
	public java.lang.Integer getBaseFee () {
		return baseFee;
	}

	/**
	 * Set the value related to the column: base_fee
	 * @param baseFee the base_fee value
	 */
	public void setBaseFee (java.lang.Integer baseFee) {
		this.baseFee = baseFee;
	}



	/**
	 * Return the value associated with the column: min_fee
	 */
	public java.lang.Integer getMinFee () {
		return minFee;
	}

	/**
	 * Set the value related to the column: min_fee
	 * @param minFee the min_fee value
	 */
	public void setMinFee (java.lang.Integer minFee) {
		this.minFee = minFee;
	}



	/**
	 * Return the value associated with the column: max_fee
	 */
	public java.lang.Integer getMaxFee () {
		return maxFee;
	}

	/**
	 * Set the value related to the column: max_fee
	 * @param maxFee the max_fee value
	 */
	public void setMaxFee (java.lang.Integer maxFee) {
		this.maxFee = maxFee;
	}



	/**
	 * Return the value associated with the column: calc_cond
	 */
	public java.lang.String getCalcCond () {
		return calcCond;
	}

	/**
	 * Set the value related to the column: calc_cond
	 * @param calcCond the calc_cond value
	 */
	public void setCalcCond (java.lang.String calcCond) {
		this.calcCond = calcCond;
	}



	/**
	 * Return the value associated with the column: author_type
	 */
	public java.lang.Byte getAuthorType () {
		return authorType;
	}

	/**
	 * Set the value related to the column: author_type
	 * @param authorType the author_type value
	 */
	public void setAuthorType (java.lang.Byte authorType) {
		this.authorType = authorType;
	}



	/**
	 * Return the value associated with the column: state
	 */
	public java.lang.Byte getState () {
		return state;
	}

	/**
	 * Set the value related to the column: state
	 * @param state the state value
	 */
	public void setState (java.lang.Byte state) {
		this.state = state;
	}



	/**
	 * Return the value associated with the column: trans_mode
	 */
	public java.lang.Byte getTransMode () {
		return transMode;
	}

	/**
	 * Set the value related to the column: trans_mode
	 * @param transMode the trans_mode value
	 */
	public void setTransMode (java.lang.Byte transMode) {
		this.transMode = transMode;
	}



	/**
	 * Return the value associated with the column: gate_id
	 */
	public java.lang.String getGateId () {
		return gateId;
	}

	/**
	 * Set the value related to the column: gate_id
	 * @param gateId the gate_id value
	 */
	public void setGateId (java.lang.String gateId) {
		this.gateId = gateId;
	}



	/**
	 * Return the value associated with the column: bk_fee_mode
	 */
	public java.lang.String getBkFeeMode () {
		return bkFeeMode;
	}

	/**
	 * Set the value related to the column: bk_fee_mode
	 * @param bkFeeMode the bk_fee_mode value
	 */
	public void setBkFeeMode (java.lang.String bkFeeMode) {
		this.bkFeeMode = bkFeeMode;
	}



	/**
	 * Return the value associated with the column: gid
	 */
	public java.lang.Integer getGid () {
		return gid;
	}

	/**
	 * Set the value related to the column: gid
	 * @param gid the gid value
	 */
	public void setGid (java.lang.Integer gid) {
		this.gid = gid;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.FeeCalcMode)) return false;
		else {
			cn.com.chinaebi.dz.object.FeeCalcMode feeCalcMode = (cn.com.chinaebi.dz.object.FeeCalcMode) obj;
			if (null == this.getId() || null == feeCalcMode.getId()) return false;
			else return (this.getId().equals(feeCalcMode.getId()));
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