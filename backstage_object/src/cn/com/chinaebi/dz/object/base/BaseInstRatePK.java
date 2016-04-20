package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


public abstract class BaseInstRatePK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.Integer instType;
	private java.lang.Integer instId;


	public BaseInstRatePK () {}
	
	public BaseInstRatePK (
		java.lang.Integer instType,
		java.lang.Integer instId) {

		this.setInstType(instType);
		this.setInstId(instId);
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



	/**
	 * Return the value associated with the column: inst_id
	 */
	public java.lang.Integer getInstId () {
		return instId;
	}

	/**
	 * Set the value related to the column: inst_id
	 * @param instId the inst_id value
	 */
	public void setInstId (java.lang.Integer instId) {
		this.instId = instId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.InstRatePK)) return false;
		else {
			cn.com.chinaebi.dz.object.InstRatePK mObj = (cn.com.chinaebi.dz.object.InstRatePK) obj;
			if (null != this.getInstType() && null != mObj.getInstType()) {
				if (!this.getInstType().equals(mObj.getInstType())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getInstId() && null != mObj.getInstId()) {
				if (!this.getInstId().equals(mObj.getInstId())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getInstType()) {
				sb.append(this.getInstType().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getInstId()) {
				sb.append(this.getInstId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}