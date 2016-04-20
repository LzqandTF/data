package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


public abstract class BaseInstMerRateConfPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.Integer gid;
	private java.lang.Integer gType;
	private java.lang.String merCode;
	private java.lang.String cardType;
	private java.lang.Integer lineOrinter;


	public BaseInstMerRateConfPK () {}
	
	public BaseInstMerRateConfPK (
		java.lang.Integer gid,
		java.lang.Integer gType,
		java.lang.String merCode,
		java.lang.String cardType,
		java.lang.Integer lineOrinter) {

		this.setGid(gid);
		this.setGType(gType);
		this.setMerCode(merCode);
		this.setCardType(cardType);
		this.setLineOrinter(lineOrinter);
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



	/**
	 * Return the value associated with the column: g_type
	 */
	public java.lang.Integer getGType () {
		return gType;
	}

	/**
	 * Set the value related to the column: g_type
	 * @param gType the g_type value
	 */
	public void setGType (java.lang.Integer gType) {
		this.gType = gType;
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
	 * Return the value associated with the column: card_type
	 */
	public java.lang.String getCardType () {
		return cardType;
	}

	/**
	 * Set the value related to the column: card_type
	 * @param cardType the card_type value
	 */
	public void setCardType (java.lang.String cardType) {
		this.cardType = cardType;
	}



	/**
	 * Return the value associated with the column: lineOrinter
	 */
	public java.lang.Integer getLineOrinter () {
		return lineOrinter;
	}

	/**
	 * Set the value related to the column: lineOrinter
	 * @param lineOrinter the lineOrinter value
	 */
	public void setLineOrinter (java.lang.Integer lineOrinter) {
		this.lineOrinter = lineOrinter;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.InstMerRateConfPK)) return false;
		else {
			cn.com.chinaebi.dz.object.InstMerRateConfPK mObj = (cn.com.chinaebi.dz.object.InstMerRateConfPK) obj;
			if (null != this.getGid() && null != mObj.getGid()) {
				if (!this.getGid().equals(mObj.getGid())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getGType() && null != mObj.getGType()) {
				if (!this.getGType().equals(mObj.getGType())) {
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
			if (null != this.getCardType() && null != mObj.getCardType()) {
				if (!this.getCardType().equals(mObj.getCardType())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getLineOrinter() && null != mObj.getLineOrinter()) {
				if (!this.getLineOrinter().equals(mObj.getLineOrinter())) {
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
			if (null != this.getGid()) {
				sb.append(this.getGid().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getGType()) {
				sb.append(this.getGType().hashCode());
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
			if (null != this.getCardType()) {
				sb.append(this.getCardType().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getLineOrinter()) {
				sb.append(this.getLineOrinter().hashCode());
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