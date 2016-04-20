package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the refund_log table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="refund_log"
 */

public abstract class BaseRefundLog  implements Serializable {

	public static String REF = "RefundLog";
	public static String PROP_REF_AMT = "RefAmt";
	public static String PROP_MID = "Mid";
	public static String PROP_MER_PRIV = "MerPriv";
	public static String PROP_OID = "Oid";
	public static String PROP_USER_NAME = "UserName";
	public static String PROP_ONLINE_REFUND_REASON = "OnlineRefundReason";
	public static String PROP_MDATE = "Mdate";
	public static String PROP_REF_DATE = "RefDate";
	public static String PROP_PRE_AMT1 = "PreAmt1";
	public static String PROP_CARD_NO = "CardNo";
	public static String PROP_BATCH = "Batch";
	public static String PROP_ORG_MDATE = "OrgMdate";
	public static String PROP_ORG_BK_SEQ = "OrgBkSeq";
	public static String PROP_AUTHOR_TYPE = "AuthorType";
	public static String PROP_REFUND_TYPE = "RefundType";
	public static String PROP_ORG_AMT = "OrgAmt";
	public static String PROP_GATE = "Gate";
	public static String PROP_REASON = "Reason";
	public static String PROP_BK_FEE = "BkFee";
	public static String PROP_SYS_DATE = "SysDate";
	public static String PROP_ORG_OID = "OrgOid";
	public static String PROP_REFUND_REASON = "RefundReason";
	public static String PROP_BG_RET_URL = "BgRetUrl";
	public static String PROP_ONLINE_REFUND_ID = "OnlineRefundId";
	public static String PROP_GID = "Gid";
	public static String PROP_REQ_DATE = "ReqDate";
	public static String PROP_ONLINE_REFUND_STATE = "OnlineRefundState";
	public static String PROP_P1 = "P1";
	public static String PROP_PRE_AMT = "PreAmt";
	public static String PROP_BK_FEE_REAL = "BkFeeReal";
	public static String PROP_MER_FEE = "MerFee";
	public static String PROP_PRO_DATE = "ProDate";
	public static String PROP_STAT = "Stat";
	public static String PROP_VSTATE = "Vstate";
	public static String PROP_ORG_PAY_AMT = "OrgPayAmt";
	public static String PROP_ETRO_REASON = "EtroReason";
	public static String PROP_ID = "Id";
	public static String PROP_TSEQ = "Tseq";
	public static String PROP_SYS_TIME = "SysTime";


	// constructors
	public BaseRefundLog () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseRefundLog (java.lang.Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseRefundLog (
		java.lang.Long id,
		java.lang.Integer authorType) {

		this.setId(id);
		this.setAuthorType(authorType);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long id;

	// fields
	private java.lang.Long tseq;
	private java.lang.Integer mdate;
	private java.lang.String mid;
	private java.lang.String oid;
	private java.lang.Integer orgMdate;
	private java.lang.String orgOid;
	private java.lang.Long refAmt;
	private java.lang.Integer sysDate;
	private java.lang.Integer gate;
	private java.lang.String cardNo;
	private java.lang.String userName;
	private java.lang.Integer reqDate;
	private java.lang.Integer proDate;
	private java.lang.Integer refDate;
	private java.lang.Byte stat;
	private java.lang.String reason;
	private java.lang.String etroReason;
	private java.lang.String refundReason;
	private java.lang.String batch;
	private java.lang.String bgRetUrl;
	private java.lang.Byte vstate;
	private java.lang.Integer authorType;
	private java.lang.Integer merFee;
	private java.lang.Integer bkFee;
	private java.lang.Integer bkFeeReal;
	private java.lang.Long orgAmt;
	private java.lang.String orgBkSeq;
	private java.lang.Integer gid;
	private java.lang.Integer preAmt;
	private java.lang.Long orgPayAmt;
	private java.lang.Integer preAmt1;
	private java.lang.String merPriv;
	private java.lang.String p1;
	private java.lang.String onlineRefundId;
	private java.lang.Byte onlineRefundState;
	private java.lang.String onlineRefundReason;
	private java.lang.Byte refundType;
	private java.lang.Integer sysTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="id"
     */
	public java.lang.Long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: tseq
	 */
	public java.lang.Long getTseq () {
		return tseq;
	}

	/**
	 * Set the value related to the column: tseq
	 * @param tseq the tseq value
	 */
	public void setTseq (java.lang.Long tseq) {
		this.tseq = tseq;
	}



	/**
	 * Return the value associated with the column: mdate
	 */
	public java.lang.Integer getMdate () {
		return mdate;
	}

	/**
	 * Set the value related to the column: mdate
	 * @param mdate the mdate value
	 */
	public void setMdate (java.lang.Integer mdate) {
		this.mdate = mdate;
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
	 * Return the value associated with the column: oid
	 */
	public java.lang.String getOid () {
		return oid;
	}

	/**
	 * Set the value related to the column: oid
	 * @param oid the oid value
	 */
	public void setOid (java.lang.String oid) {
		this.oid = oid;
	}



	/**
	 * Return the value associated with the column: org_mdate
	 */
	public java.lang.Integer getOrgMdate () {
		return orgMdate;
	}

	/**
	 * Set the value related to the column: org_mdate
	 * @param orgMdate the org_mdate value
	 */
	public void setOrgMdate (java.lang.Integer orgMdate) {
		this.orgMdate = orgMdate;
	}



	/**
	 * Return the value associated with the column: org_oid
	 */
	public java.lang.String getOrgOid () {
		return orgOid;
	}

	/**
	 * Set the value related to the column: org_oid
	 * @param orgOid the org_oid value
	 */
	public void setOrgOid (java.lang.String orgOid) {
		this.orgOid = orgOid;
	}



	/**
	 * Return the value associated with the column: ref_amt
	 */
	public java.lang.Long getRefAmt () {
		return refAmt;
	}

	/**
	 * Set the value related to the column: ref_amt
	 * @param refAmt the ref_amt value
	 */
	public void setRefAmt (java.lang.Long refAmt) {
		this.refAmt = refAmt;
	}



	/**
	 * Return the value associated with the column: sys_date
	 */
	public java.lang.Integer getSysDate () {
		return sysDate;
	}

	/**
	 * Set the value related to the column: sys_date
	 * @param sysDate the sys_date value
	 */
	public void setSysDate (java.lang.Integer sysDate) {
		this.sysDate = sysDate;
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



	/**
	 * Return the value associated with the column: card_no
	 */
	public java.lang.String getCardNo () {
		return cardNo;
	}

	/**
	 * Set the value related to the column: card_no
	 * @param cardNo the card_no value
	 */
	public void setCardNo (java.lang.String cardNo) {
		this.cardNo = cardNo;
	}



	/**
	 * Return the value associated with the column: user_name
	 */
	public java.lang.String getUserName () {
		return userName;
	}

	/**
	 * Set the value related to the column: user_name
	 * @param userName the user_name value
	 */
	public void setUserName (java.lang.String userName) {
		this.userName = userName;
	}



	/**
	 * Return the value associated with the column: req_date
	 */
	public java.lang.Integer getReqDate () {
		return reqDate;
	}

	/**
	 * Set the value related to the column: req_date
	 * @param reqDate the req_date value
	 */
	public void setReqDate (java.lang.Integer reqDate) {
		this.reqDate = reqDate;
	}



	/**
	 * Return the value associated with the column: pro_date
	 */
	public java.lang.Integer getProDate () {
		return proDate;
	}

	/**
	 * Set the value related to the column: pro_date
	 * @param proDate the pro_date value
	 */
	public void setProDate (java.lang.Integer proDate) {
		this.proDate = proDate;
	}



	/**
	 * Return the value associated with the column: ref_date
	 */
	public java.lang.Integer getRefDate () {
		return refDate;
	}

	/**
	 * Set the value related to the column: ref_date
	 * @param refDate the ref_date value
	 */
	public void setRefDate (java.lang.Integer refDate) {
		this.refDate = refDate;
	}



	/**
	 * Return the value associated with the column: stat
	 */
	public java.lang.Byte getStat () {
		return stat;
	}

	/**
	 * Set the value related to the column: stat
	 * @param stat the stat value
	 */
	public void setStat (java.lang.Byte stat) {
		this.stat = stat;
	}



	/**
	 * Return the value associated with the column: reason
	 */
	public java.lang.String getReason () {
		return reason;
	}

	/**
	 * Set the value related to the column: reason
	 * @param reason the reason value
	 */
	public void setReason (java.lang.String reason) {
		this.reason = reason;
	}



	/**
	 * Return the value associated with the column: etro_reason
	 */
	public java.lang.String getEtroReason () {
		return etroReason;
	}

	/**
	 * Set the value related to the column: etro_reason
	 * @param etroReason the etro_reason value
	 */
	public void setEtroReason (java.lang.String etroReason) {
		this.etroReason = etroReason;
	}



	/**
	 * Return the value associated with the column: refund_reason
	 */
	public java.lang.String getRefundReason () {
		return refundReason;
	}

	/**
	 * Set the value related to the column: refund_reason
	 * @param refundReason the refund_reason value
	 */
	public void setRefundReason (java.lang.String refundReason) {
		this.refundReason = refundReason;
	}



	/**
	 * Return the value associated with the column: batch
	 */
	public java.lang.String getBatch () {
		return batch;
	}

	/**
	 * Set the value related to the column: batch
	 * @param batch the batch value
	 */
	public void setBatch (java.lang.String batch) {
		this.batch = batch;
	}



	/**
	 * Return the value associated with the column: bgRetUrl
	 */
	public java.lang.String getBgRetUrl () {
		return bgRetUrl;
	}

	/**
	 * Set the value related to the column: bgRetUrl
	 * @param bgRetUrl the bgRetUrl value
	 */
	public void setBgRetUrl (java.lang.String bgRetUrl) {
		this.bgRetUrl = bgRetUrl;
	}



	/**
	 * Return the value associated with the column: vstate
	 */
	public java.lang.Byte getVstate () {
		return vstate;
	}

	/**
	 * Set the value related to the column: vstate
	 * @param vstate the vstate value
	 */
	public void setVstate (java.lang.Byte vstate) {
		this.vstate = vstate;
	}



	/**
	 * Return the value associated with the column: author_type
	 */
	public java.lang.Integer getAuthorType () {
		return authorType;
	}

	/**
	 * Set the value related to the column: author_type
	 * @param authorType the author_type value
	 */
	public void setAuthorType (java.lang.Integer authorType) {
		this.authorType = authorType;
	}



	/**
	 * Return the value associated with the column: mer_fee
	 */
	public java.lang.Integer getMerFee () {
		return merFee;
	}

	/**
	 * Set the value related to the column: mer_fee
	 * @param merFee the mer_fee value
	 */
	public void setMerFee (java.lang.Integer merFee) {
		this.merFee = merFee;
	}



	/**
	 * Return the value associated with the column: bk_fee
	 */
	public java.lang.Integer getBkFee () {
		return bkFee;
	}

	/**
	 * Set the value related to the column: bk_fee
	 * @param bkFee the bk_fee value
	 */
	public void setBkFee (java.lang.Integer bkFee) {
		this.bkFee = bkFee;
	}



	/**
	 * Return the value associated with the column: bk_fee_real
	 */
	public java.lang.Integer getBkFeeReal () {
		return bkFeeReal;
	}

	/**
	 * Set the value related to the column: bk_fee_real
	 * @param bkFeeReal the bk_fee_real value
	 */
	public void setBkFeeReal (java.lang.Integer bkFeeReal) {
		this.bkFeeReal = bkFeeReal;
	}



	/**
	 * Return the value associated with the column: org_amt
	 */
	public java.lang.Long getOrgAmt () {
		return orgAmt;
	}

	/**
	 * Set the value related to the column: org_amt
	 * @param orgAmt the org_amt value
	 */
	public void setOrgAmt (java.lang.Long orgAmt) {
		this.orgAmt = orgAmt;
	}



	/**
	 * Return the value associated with the column: org_bk_seq
	 */
	public java.lang.String getOrgBkSeq () {
		return orgBkSeq;
	}

	/**
	 * Set the value related to the column: org_bk_seq
	 * @param orgBkSeq the org_bk_seq value
	 */
	public void setOrgBkSeq (java.lang.String orgBkSeq) {
		this.orgBkSeq = orgBkSeq;
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
	 * Return the value associated with the column: pre_amt
	 */
	public java.lang.Integer getPreAmt () {
		return preAmt;
	}

	/**
	 * Set the value related to the column: pre_amt
	 * @param preAmt the pre_amt value
	 */
	public void setPreAmt (java.lang.Integer preAmt) {
		this.preAmt = preAmt;
	}



	/**
	 * Return the value associated with the column: org_pay_amt
	 */
	public java.lang.Long getOrgPayAmt () {
		return orgPayAmt;
	}

	/**
	 * Set the value related to the column: org_pay_amt
	 * @param orgPayAmt the org_pay_amt value
	 */
	public void setOrgPayAmt (java.lang.Long orgPayAmt) {
		this.orgPayAmt = orgPayAmt;
	}



	/**
	 * Return the value associated with the column: pre_amt1
	 */
	public java.lang.Integer getPreAmt1 () {
		return preAmt1;
	}

	/**
	 * Set the value related to the column: pre_amt1
	 * @param preAmt1 the pre_amt1 value
	 */
	public void setPreAmt1 (java.lang.Integer preAmt1) {
		this.preAmt1 = preAmt1;
	}



	/**
	 * Return the value associated with the column: mer_priv
	 */
	public java.lang.String getMerPriv () {
		return merPriv;
	}

	/**
	 * Set the value related to the column: mer_priv
	 * @param merPriv the mer_priv value
	 */
	public void setMerPriv (java.lang.String merPriv) {
		this.merPriv = merPriv;
	}



	/**
	 * Return the value associated with the column: p1
	 */
	public java.lang.String getP1 () {
		return p1;
	}

	/**
	 * Set the value related to the column: p1
	 * @param p1 the p1 value
	 */
	public void setP1 (java.lang.String p1) {
		this.p1 = p1;
	}



	/**
	 * Return the value associated with the column: online_refund_id
	 */
	public java.lang.String getOnlineRefundId () {
		return onlineRefundId;
	}

	/**
	 * Set the value related to the column: online_refund_id
	 * @param onlineRefundId the online_refund_id value
	 */
	public void setOnlineRefundId (java.lang.String onlineRefundId) {
		this.onlineRefundId = onlineRefundId;
	}



	/**
	 * Return the value associated with the column: online_refund_state
	 */
	public java.lang.Byte getOnlineRefundState () {
		return onlineRefundState;
	}

	/**
	 * Set the value related to the column: online_refund_state
	 * @param onlineRefundState the online_refund_state value
	 */
	public void setOnlineRefundState (java.lang.Byte onlineRefundState) {
		this.onlineRefundState = onlineRefundState;
	}



	/**
	 * Return the value associated with the column: online_refund_reason
	 */
	public java.lang.String getOnlineRefundReason () {
		return onlineRefundReason;
	}

	/**
	 * Set the value related to the column: online_refund_reason
	 * @param onlineRefundReason the online_refund_reason value
	 */
	public void setOnlineRefundReason (java.lang.String onlineRefundReason) {
		this.onlineRefundReason = onlineRefundReason;
	}



	/**
	 * Return the value associated with the column: refund_type
	 */
	public java.lang.Byte getRefundType () {
		return refundType;
	}

	/**
	 * Set the value related to the column: refund_type
	 * @param refundType the refund_type value
	 */
	public void setRefundType (java.lang.Byte refundType) {
		this.refundType = refundType;
	}



	/**
	 * Return the value associated with the column: sys_time
	 */
	public java.lang.Integer getSysTime () {
		return sysTime;
	}

	/**
	 * Set the value related to the column: sys_time
	 * @param sysTime the sys_time value
	 */
	public void setSysTime (java.lang.Integer sysTime) {
		this.sysTime = sysTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.RefundLog)) return false;
		else {
			cn.com.chinaebi.dz.object.RefundLog refundLog = (cn.com.chinaebi.dz.object.RefundLog) obj;
			if (null == this.getId() || null == refundLog.getId()) return false;
			else return (this.getId().equals(refundLog.getId()));
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