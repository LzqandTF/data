package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tmoney table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tmoney"
 */

public abstract class BaseTmoney  implements Serializable {

	public static String REF = "Tmoney";
	public static String PROP_NAME = "Name";
	public static String PROP_BIL_ACCOUNT = "BilAccount";
	public static String PROP_ID = "Id";
	public static String PROP_BIL_ACCOUNTNAME = "BilAccountname";
	public static String PROP_SETTLE_WAY = "SettleWay";
	public static String PROP_TOTAL_MONEY = "TotalMoney";


	// constructors
	public BaseTmoney () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTmoney (cn.com.chinaebi.dz.object.TmoneyPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTmoney (
		cn.com.chinaebi.dz.object.TmoneyPK id,
		java.lang.String name,
		java.lang.Long totalMoney,
		java.lang.Integer settleWay) {

		this.setId(id);
		this.setName(name);
		this.setTotalMoney(totalMoney);
		this.setSettleWay(settleWay);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private cn.com.chinaebi.dz.object.TmoneyPK id;

	// fields
	private java.lang.String name;
	private java.lang.Long totalMoney;
	private java.lang.Integer settleWay;
	private java.lang.String bilAccount;
	private java.lang.String bilAccountname;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public cn.com.chinaebi.dz.object.TmoneyPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (cn.com.chinaebi.dz.object.TmoneyPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: _name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: _name
	 * @param name the _name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: total_money
	 */
	public java.lang.Long getTotalMoney () {
		return totalMoney;
	}

	/**
	 * Set the value related to the column: total_money
	 * @param totalMoney the total_money value
	 */
	public void setTotalMoney (java.lang.Long totalMoney) {
		this.totalMoney = totalMoney;
	}



	/**
	 * Return the value associated with the column: settle_way
	 */
	public java.lang.Integer getSettleWay () {
		return settleWay;
	}

	/**
	 * Set the value related to the column: settle_way
	 * @param settleWay the settle_way value
	 */
	public void setSettleWay (java.lang.Integer settleWay) {
		this.settleWay = settleWay;
	}



	/**
	 * Return the value associated with the column: bil_account
	 */
	public java.lang.String getBilAccount () {
		return bilAccount;
	}

	/**
	 * Set the value related to the column: bil_account
	 * @param bilAccount the bil_account value
	 */
	public void setBilAccount (java.lang.String bilAccount) {
		this.bilAccount = bilAccount;
	}



	/**
	 * Return the value associated with the column: bil_accountname
	 */
	public java.lang.String getBilAccountname () {
		return bilAccountname;
	}

	/**
	 * Set the value related to the column: bil_accountname
	 * @param bilAccountname the bil_accountname value
	 */
	public void setBilAccountname (java.lang.String bilAccountname) {
		this.bilAccountname = bilAccountname;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.Tmoney)) return false;
		else {
			cn.com.chinaebi.dz.object.Tmoney tmoney = (cn.com.chinaebi.dz.object.Tmoney) obj;
			if (null == this.getId() || null == tmoney.getId()) return false;
			else return (this.getId().equals(tmoney.getId()));
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