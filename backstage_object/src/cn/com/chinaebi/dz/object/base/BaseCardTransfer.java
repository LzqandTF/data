package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the card_transfer table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="card_transfer"
 */

public abstract class BaseCardTransfer  implements Serializable {

	public static String REF = "CardTransfer";
	public static String PROP_CARD_INS_DESC = "CardInsDesc";
	public static String PROP_CARD_TRACKTHREE_LENGTH = "CardTrackthreeLength";
	public static String PROP_CARD_LENGTH = "CardLength";
	public static String PROP_CARD_POS = "CardPos";
	public static String PROP_CARD_TRACKTWO = "CardTracktwo";
	public static String PROP_CARD_HEAD = "CardHead";
	public static String PROP_CARD_TRACKTHREE = "CardTrackthree";
	public static String PROP_CARD_TYPE_DESC = "CardTypeDesc";
	public static String PROP_CARD_ATM = "CardAtm";
	public static String PROP_CARD_DESC = "CardDesc";
	public static String PROP_CARD_TRACKTWO_LENGTH = "CardTracktwoLength";
	public static String PROP_CARD_INS_CODE = "CardInsCode";
	public static String PROP_CARD_NO_LENGTH = "CardNoLength";
	public static String PROP_ID = "Id";
	public static String PROP_CARD_TYPE = "CardType";


	// constructors
	public BaseCardTransfer () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCardTransfer (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCardTransfer (
		java.lang.Integer id,
		java.lang.String cardInsDesc,
		java.lang.String cardInsCode,
		java.lang.Integer cardNoLength,
		java.lang.Integer cardLength,
		java.lang.String cardHead) {

		this.setId(id);
		this.setCardInsDesc(cardInsDesc);
		this.setCardInsCode(cardInsCode);
		this.setCardNoLength(cardNoLength);
		this.setCardLength(cardLength);
		this.setCardHead(cardHead);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String cardInsDesc;
	private java.lang.String cardInsCode;
	private java.lang.String cardDesc;
	private java.lang.Integer cardAtm;
	private java.lang.Integer cardPos;
	private java.lang.String cardTracktwo;
	private java.lang.String cardTrackthree;
	private java.lang.Integer cardTracktwoLength;
	private java.lang.Integer cardTrackthreeLength;
	private java.lang.Integer cardNoLength;
	private java.lang.Integer cardLength;
	private java.lang.String cardHead;
	private java.lang.String cardTypeDesc;
	private java.lang.String cardType;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="org.hibernate.id.UUIDHexGenerator"
     *  column="card_id"
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
	 * Return the value associated with the column: card_ins_desc
	 */
	public java.lang.String getCardInsDesc () {
		return cardInsDesc;
	}

	/**
	 * Set the value related to the column: card_ins_desc
	 * @param cardInsDesc the card_ins_desc value
	 */
	public void setCardInsDesc (java.lang.String cardInsDesc) {
		this.cardInsDesc = cardInsDesc;
	}



	/**
	 * Return the value associated with the column: card_ins_code
	 */
	public java.lang.String getCardInsCode () {
		return cardInsCode;
	}

	/**
	 * Set the value related to the column: card_ins_code
	 * @param cardInsCode the card_ins_code value
	 */
	public void setCardInsCode (java.lang.String cardInsCode) {
		this.cardInsCode = cardInsCode;
	}



	/**
	 * Return the value associated with the column: card_desc
	 */
	public java.lang.String getCardDesc () {
		return cardDesc;
	}

	/**
	 * Set the value related to the column: card_desc
	 * @param cardDesc the card_desc value
	 */
	public void setCardDesc (java.lang.String cardDesc) {
		this.cardDesc = cardDesc;
	}



	/**
	 * Return the value associated with the column: card_atm
	 */
	public java.lang.Integer getCardAtm () {
		return cardAtm;
	}

	/**
	 * Set the value related to the column: card_atm
	 * @param cardAtm the card_atm value
	 */
	public void setCardAtm (java.lang.Integer cardAtm) {
		this.cardAtm = cardAtm;
	}



	/**
	 * Return the value associated with the column: card_pos
	 */
	public java.lang.Integer getCardPos () {
		return cardPos;
	}

	/**
	 * Set the value related to the column: card_pos
	 * @param cardPos the card_pos value
	 */
	public void setCardPos (java.lang.Integer cardPos) {
		this.cardPos = cardPos;
	}



	/**
	 * Return the value associated with the column: card_tracktwo
	 */
	public java.lang.String getCardTracktwo () {
		return cardTracktwo;
	}

	/**
	 * Set the value related to the column: card_tracktwo
	 * @param cardTracktwo the card_tracktwo value
	 */
	public void setCardTracktwo (java.lang.String cardTracktwo) {
		this.cardTracktwo = cardTracktwo;
	}



	/**
	 * Return the value associated with the column: card_trackthree
	 */
	public java.lang.String getCardTrackthree () {
		return cardTrackthree;
	}

	/**
	 * Set the value related to the column: card_trackthree
	 * @param cardTrackthree the card_trackthree value
	 */
	public void setCardTrackthree (java.lang.String cardTrackthree) {
		this.cardTrackthree = cardTrackthree;
	}



	/**
	 * Return the value associated with the column: card_tracktwo_length
	 */
	public java.lang.Integer getCardTracktwoLength () {
		return cardTracktwoLength;
	}

	/**
	 * Set the value related to the column: card_tracktwo_length
	 * @param cardTracktwoLength the card_tracktwo_length value
	 */
	public void setCardTracktwoLength (java.lang.Integer cardTracktwoLength) {
		this.cardTracktwoLength = cardTracktwoLength;
	}



	/**
	 * Return the value associated with the column: card_trackthree_length
	 */
	public java.lang.Integer getCardTrackthreeLength () {
		return cardTrackthreeLength;
	}

	/**
	 * Set the value related to the column: card_trackthree_length
	 * @param cardTrackthreeLength the card_trackthree_length value
	 */
	public void setCardTrackthreeLength (java.lang.Integer cardTrackthreeLength) {
		this.cardTrackthreeLength = cardTrackthreeLength;
	}



	/**
	 * Return the value associated with the column: card_no_length
	 */
	public java.lang.Integer getCardNoLength () {
		return cardNoLength;
	}

	/**
	 * Set the value related to the column: card_no_length
	 * @param cardNoLength the card_no_length value
	 */
	public void setCardNoLength (java.lang.Integer cardNoLength) {
		this.cardNoLength = cardNoLength;
	}



	/**
	 * Return the value associated with the column: card_length
	 */
	public java.lang.Integer getCardLength () {
		return cardLength;
	}

	/**
	 * Set the value related to the column: card_length
	 * @param cardLength the card_length value
	 */
	public void setCardLength (java.lang.Integer cardLength) {
		this.cardLength = cardLength;
	}



	/**
	 * Return the value associated with the column: card_head
	 */
	public java.lang.String getCardHead () {
		return cardHead;
	}

	/**
	 * Set the value related to the column: card_head
	 * @param cardHead the card_head value
	 */
	public void setCardHead (java.lang.String cardHead) {
		this.cardHead = cardHead;
	}



	/**
	 * Return the value associated with the column: card_type_desc
	 */
	public java.lang.String getCardTypeDesc () {
		return cardTypeDesc;
	}

	/**
	 * Set the value related to the column: card_type_desc
	 * @param cardTypeDesc the card_type_desc value
	 */
	public void setCardTypeDesc (java.lang.String cardTypeDesc) {
		this.cardTypeDesc = cardTypeDesc;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.CardTransfer)) return false;
		else {
			cn.com.chinaebi.dz.object.CardTransfer cardTransfer = (cn.com.chinaebi.dz.object.CardTransfer) obj;
			if (null == this.getId() || null == cardTransfer.getId()) return false;
			else return (this.getId().equals(cardTransfer.getId()));
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