package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


public abstract class BaseTmoneyPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String id;
	private java.lang.String merCode;
	private java.lang.Integer deductStlmDate;


	public BaseTmoneyPK () {}
	
	public BaseTmoneyPK (
		java.lang.String id,
		java.lang.String merCode,
		java.lang.Integer deductStlmDate) {

		this.setId(id);
		this.setMerCode(merCode);
		this.setDeductStlmDate(deductStlmDate);
	}


	/**
	 * Return the value associated with the column: id
	 */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the value related to the column: id
	 * @param id the id value
	 */
	public void setId (java.lang.String id) {
		this.id = id;
	}



	/**
	 * Return the value associated with the column: mer_code
	 */
	public java.lang.String getMerCode () {
		return merCode;
	}

	/**
	 * Set the value related to the column: mer_code
	 * @param merCode the mer_code value
	 */
	public void setMerCode (java.lang.String merCode) {
		this.merCode = merCode;
	}



	/**
	 * Return the value associated with the column: deduct_stlm_date
	 */
	public java.lang.Integer getDeductStlmDate () {
		return deductStlmDate;
	}

	/**
	 * Set the value related to the column: deduct_stlm_date
	 * @param deductStlmDate the deduct_stlm_date value
	 */
	public void setDeductStlmDate (java.lang.Integer deductStlmDate) {
		this.deductStlmDate = deductStlmDate;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.TmoneyPK)) return false;
		else {
			cn.com.chinaebi.dz.object.TmoneyPK mObj = (cn.com.chinaebi.dz.object.TmoneyPK) obj;
			if (null != this.getId() && null != mObj.getId()) {
				if (!this.getId().equals(mObj.getId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMerCode() && null != mObj.getMerCode()) {
				if (!this.getMerCode().equals(mObj.getMerCode())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getDeductStlmDate() && null != mObj.getDeductStlmDate()) {
				if (!this.getDeductStlmDate().equals(mObj.getDeductStlmDate())) {
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
			if (null != this.getId()) {
				sb.append(this.getId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMerCode()) {
				sb.append(this.getMerCode().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getDeductStlmDate()) {
				sb.append(this.getDeductStlmDate().hashCode());
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