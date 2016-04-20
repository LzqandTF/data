package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inst_info table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inst_info"
 */

public abstract class BaseInstInfo  implements Serializable {

	public static String REF = "InstInfo";
	public static String PROP_BANKFTP_PASSWORD = "BankftpPassword";
	public static String PROP_BANKFTP_PORT = "BankftpPort";
	public static String PROP_WHETHER_OUTER_ERROR_DZ = "WhetherOuterErrorDz";
	public static String PROP_BANKFTP_USERNAME = "BankftpUsername";
	public static String PROP_ERROR_DZ_DATA_TABLENAME = "ErrorDzDataTablename";
	public static String PROP_WHETHER_APPLY_ONLINE_TK = "WhetherApplyOnlineTk";
	public static String PROP_RECEIVI_NAME = "ReceiviName";
	public static String PROP_WHETHER_INNER_DZ = "WhetherInnerDz";
	public static String PROP_BANK = "Bank";
	public static String PROP_BANKFTP_IP = "BankftpIp";
	public static String PROP_BANKFTP_DOWNLOAD = "BankftpDownload";
	public static String PROP_TRADE_DZ_IMPL_CLASS = "TradeDzImplClass";
	public static String PROP_NAME = "Name";
	public static String PROP_ERROR_ORIGINAL_DATA_TABLENAME = "ErrorOriginalDataTablename";
	public static String PROP_WHETHER_PARSE_BRANK = "WhetherParseBrank";
	public static String PROP_ACTIVE = "Active";
	public static String PROP_WHETHER_INNER_JS = "WhetherInnerJs";
	public static String PROP_WHETHER_OUTER_DZ = "WhetherOuterDz";
	public static String PROP_ID = "Id";
	public static String PROP_WHETHER_T_1 = "Whether_T_1";
	public static String PROP_DZ_DATA_COLUMN = "DzDataColumn";
	public static String PROP_BANKFTP_PATH = "BankftpPath";
	public static String PROP_REMARK = "Remark";
	public static String PROP_GATE = "Gate";


	// constructors
	public BaseInstInfo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInstInfo (cn.com.chinaebi.dz.object.InstInfoPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInstInfo (
		cn.com.chinaebi.dz.object.InstInfoPK id,
		cn.com.chinaebi.dz.object.BankInst bank,
		boolean whetherInnerDz,
		java.lang.String name,
		boolean whetherInnerJs,
		boolean active,
		boolean whetherOuterErrorDz,
		boolean whetherOuterDz,
		java.lang.Integer gate) {

		this.setId(id);
		this.setBank(bank);
		this.setWhetherInnerDz(whetherInnerDz);
		this.setName(name);
		this.setWhetherInnerJs(whetherInnerJs);
		this.setActive(active);
		this.setWhetherOuterErrorDz(whetherOuterErrorDz);
		this.setWhetherOuterDz(whetherOuterDz);
		this.setGate(gate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private cn.com.chinaebi.dz.object.InstInfoPK id;

	// fields
	private boolean whetherInnerDz;
	private java.lang.String tradeDzImplClass;
	private java.lang.String name;
	private boolean whetherInnerJs;
	private boolean whetherApplyOnlineTk;
	private boolean active;
	private boolean whetherOuterErrorDz;
	private boolean whetherOuterDz;
	private java.lang.String remark;
	private java.lang.String errorDzDataTablename;
	private java.lang.String errorOriginalDataTablename;
	private java.lang.String receiviName;
	private java.lang.String dzDataColumn;
	private java.lang.Integer gate;
	private boolean bankftpDownload;
	private java.lang.String bankftpPath;
	private java.lang.String bankftpIp;
	private java.lang.String bankftpPort;
	private java.lang.String bankftpUsername;
	private java.lang.String bankftpPassword;
	private boolean whether_T_1;
	private boolean whetherParseBrank;

	// many to one
	private cn.com.chinaebi.dz.object.BankInst bank;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public cn.com.chinaebi.dz.object.InstInfoPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (cn.com.chinaebi.dz.object.InstInfoPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: whether_inner_dz
	 */
	public boolean isWhetherInnerDz () {
		return whetherInnerDz;
	}

	/**
	 * Set the value related to the column: whether_inner_dz
	 * @param whetherInnerDz the whether_inner_dz value
	 */
	public void setWhetherInnerDz (boolean whetherInnerDz) {
		this.whetherInnerDz = whetherInnerDz;
	}



	/**
	 * Return the value associated with the column: trade_dz_impl_class
	 */
	public java.lang.String getTradeDzImplClass () {
		return tradeDzImplClass;
	}

	/**
	 * Set the value related to the column: trade_dz_impl_class
	 * @param tradeDzImplClass the trade_dz_impl_class value
	 */
	public void setTradeDzImplClass (java.lang.String tradeDzImplClass) {
		this.tradeDzImplClass = tradeDzImplClass;
	}



	/**
	 * Return the value associated with the column: name_
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name_
	 * @param name the name_ value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: whether_inner_js
	 */
	public boolean isWhetherInnerJs () {
		return whetherInnerJs;
	}

	/**
	 * Set the value related to the column: whether_inner_js
	 * @param whetherInnerJs the whether_inner_js value
	 */
	public void setWhetherInnerJs (boolean whetherInnerJs) {
		this.whetherInnerJs = whetherInnerJs;
	}



	/**
	 * Return the value associated with the column: whether_apply_online_tk
	 */
	public boolean isWhetherApplyOnlineTk () {
		return whetherApplyOnlineTk;
	}

	/**
	 * Set the value related to the column: whether_apply_online_tk
	 * @param whetherApplyOnlineTk the whether_apply_online_tk value
	 */
	public void setWhetherApplyOnlineTk (boolean whetherApplyOnlineTk) {
		this.whetherApplyOnlineTk = whetherApplyOnlineTk;
	}



	/**
	 * Return the value associated with the column: active
	 */
	public boolean isActive () {
		return active;
	}

	/**
	 * Set the value related to the column: active
	 * @param active the active value
	 */
	public void setActive (boolean active) {
		this.active = active;
	}



	/**
	 * Return the value associated with the column: whether_outer_error_dz
	 */
	public boolean isWhetherOuterErrorDz () {
		return whetherOuterErrorDz;
	}

	/**
	 * Set the value related to the column: whether_outer_error_dz
	 * @param whetherOuterErrorDz the whether_outer_error_dz value
	 */
	public void setWhetherOuterErrorDz (boolean whetherOuterErrorDz) {
		this.whetherOuterErrorDz = whetherOuterErrorDz;
	}



	/**
	 * Return the value associated with the column: whether_outer_dz
	 */
	public boolean isWhetherOuterDz () {
		return whetherOuterDz;
	}

	/**
	 * Set the value related to the column: whether_outer_dz
	 * @param whetherOuterDz the whether_outer_dz value
	 */
	public void setWhetherOuterDz (boolean whetherOuterDz) {
		this.whetherOuterDz = whetherOuterDz;
	}



	/**
	 * Return the value associated with the column: remark
	 */
	public java.lang.String getRemark () {
		return remark;
	}

	/**
	 * Set the value related to the column: remark
	 * @param remark the remark value
	 */
	public void setRemark (java.lang.String remark) {
		this.remark = remark;
	}



	/**
	 * Return the value associated with the column: error_dz_data_tableName
	 */
	public java.lang.String getErrorDzDataTablename () {
		return errorDzDataTablename;
	}

	/**
	 * Set the value related to the column: error_dz_data_tableName
	 * @param errorDzDataTablename the error_dz_data_tableName value
	 */
	public void setErrorDzDataTablename (java.lang.String errorDzDataTablename) {
		this.errorDzDataTablename = errorDzDataTablename;
	}



	/**
	 * Return the value associated with the column: error_original_data_tableName
	 */
	public java.lang.String getErrorOriginalDataTablename () {
		return errorOriginalDataTablename;
	}

	/**
	 * Set the value related to the column: error_original_data_tableName
	 * @param errorOriginalDataTablename the error_original_data_tableName value
	 */
	public void setErrorOriginalDataTablename (java.lang.String errorOriginalDataTablename) {
		this.errorOriginalDataTablename = errorOriginalDataTablename;
	}



	/**
	 * Return the value associated with the column: receivi_name
	 */
	public java.lang.String getReceiviName () {
		return receiviName;
	}

	/**
	 * Set the value related to the column: receivi_name
	 * @param receiviName the receivi_name value
	 */
	public void setReceiviName (java.lang.String receiviName) {
		this.receiviName = receiviName;
	}



	/**
	 * Return the value associated with the column: dz_data_column
	 */
	public java.lang.String getDzDataColumn () {
		return dzDataColumn;
	}

	/**
	 * Set the value related to the column: dz_data_column
	 * @param dzDataColumn the dz_data_column value
	 */
	public void setDzDataColumn (java.lang.String dzDataColumn) {
		this.dzDataColumn = dzDataColumn;
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
	 * Return the value associated with the column: bankftp_download
	 */
	public boolean isBankftpDownload () {
		return bankftpDownload;
	}

	/**
	 * Set the value related to the column: bankftp_download
	 * @param bankftpDownload the bankftp_download value
	 */
	public void setBankftpDownload (boolean bankftpDownload) {
		this.bankftpDownload = bankftpDownload;
	}



	/**
	 * Return the value associated with the column: bankftp_path
	 */
	public java.lang.String getBankftpPath () {
		return bankftpPath;
	}

	/**
	 * Set the value related to the column: bankftp_path
	 * @param bankftpPath the bankftp_path value
	 */
	public void setBankftpPath (java.lang.String bankftpPath) {
		this.bankftpPath = bankftpPath;
	}



	/**
	 * Return the value associated with the column: bankftp_ip
	 */
	public java.lang.String getBankftpIp () {
		return bankftpIp;
	}

	/**
	 * Set the value related to the column: bankftp_ip
	 * @param bankftpIp the bankftp_ip value
	 */
	public void setBankftpIp (java.lang.String bankftpIp) {
		this.bankftpIp = bankftpIp;
	}



	/**
	 * Return the value associated with the column: bankftp_port
	 */
	public java.lang.String getBankftpPort () {
		return bankftpPort;
	}

	/**
	 * Set the value related to the column: bankftp_port
	 * @param bankftpPort the bankftp_port value
	 */
	public void setBankftpPort (java.lang.String bankftpPort) {
		this.bankftpPort = bankftpPort;
	}



	/**
	 * Return the value associated with the column: bankftp_username
	 */
	public java.lang.String getBankftpUsername () {
		return bankftpUsername;
	}

	/**
	 * Set the value related to the column: bankftp_username
	 * @param bankftpUsername the bankftp_username value
	 */
	public void setBankftpUsername (java.lang.String bankftpUsername) {
		this.bankftpUsername = bankftpUsername;
	}



	/**
	 * Return the value associated with the column: bankftp_password
	 */
	public java.lang.String getBankftpPassword () {
		return bankftpPassword;
	}

	/**
	 * Set the value related to the column: bankftp_password
	 * @param bankftpPassword the bankftp_password value
	 */
	public void setBankftpPassword (java.lang.String bankftpPassword) {
		this.bankftpPassword = bankftpPassword;
	}



	/**
	 * Return the value associated with the column: whether_t_1
	 */
	public boolean isWhether_T_1 () {
		return whether_T_1;
	}

	/**
	 * Set the value related to the column: whether_t_1
	 * @param whether_T_1 the whether_t_1 value
	 */
	public void setWhether_T_1 (boolean whether_T_1) {
		this.whether_T_1 = whether_T_1;
	}



	/**
	 * Return the value associated with the column: whether_parse_brank
	 */
	public boolean isWhetherParseBrank () {
		return whetherParseBrank;
	}

	/**
	 * Set the value related to the column: whether_parse_brank
	 * @param whetherParseBrank the whether_parse_brank value
	 */
	public void setWhetherParseBrank (boolean whetherParseBrank) {
		this.whetherParseBrank = whetherParseBrank;
	}



	/**
	 * Return the value associated with the column: bank_id
	 */
	public cn.com.chinaebi.dz.object.BankInst getBank () {
		return bank;
	}

	/**
	 * Set the value related to the column: bank_id
	 * @param bank the bank_id value
	 */
	public void setBank (cn.com.chinaebi.dz.object.BankInst bank) {
		this.bank = bank;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.InstInfo)) return false;
		else {
			cn.com.chinaebi.dz.object.InstInfo instInfo = (cn.com.chinaebi.dz.object.InstInfo) obj;
			if (null == this.getId() || null == instInfo.getId()) return false;
			else return (this.getId().equals(instInfo.getId()));
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