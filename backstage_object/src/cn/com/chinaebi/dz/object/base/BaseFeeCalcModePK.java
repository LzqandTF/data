package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


public abstract class BaseFeeCalcModePK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mid;
	private java.lang.Integer gate;


	public BaseFeeCalcModePK () {}
	
	public BaseFeeCalcModePK (
		java.lang.String mid,
		java.lang.Integer gate) {

		this.setMid(mid);
		this.setGate(gate);
	}


	/**
	 * Return the value associated with the column: mid
	 */
	public java.lang.String getMid () {
		return mid;
	}

	/**
	 * Set the value related to the column: mid
	 * @param mid the mid value
	 */
	public void setMid (java.lang.String mid) {
		this.mid = mid;
	}



	/**
	 * Return the value associated with the column: gate
	 */
	public java.lang.Integer getGate () {
		return gate;
	}

	/**
	 * Set the value related to the column: gate
	 * @param gate the gate value
	 */
	public void setGate (java.lang.Integer gate) {
		this.gate = gate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.FeeCalcModePK)) return false;
		else {
			cn.com.chinaebi.dz.object.FeeCalcModePK mObj = (cn.com.chinaebi.dz.object.FeeCalcModePK) obj;
			if (null != this.getMid() && null != mObj.getMid()) {
				if (!this.getMid().equals(mObj.getMid())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getGate() && null != mObj.getGate()) {
				if (!this.getGate().equals(mObj.getGate())) {
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
			if (null != this.getMid()) {
				sb.append(this.getMid().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getGate()) {
				sb.append(this.getGate().hashCode());
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