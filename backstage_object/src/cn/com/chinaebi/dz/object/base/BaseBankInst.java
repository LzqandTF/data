package cn.com.chinaebi.dz.object.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the bank_inst table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="bank_inst"
 */

public abstract class BaseBankInst  implements Serializable {

	public static String REF = "BankInst";
	public static String PROP_BANK_TYPE = "BankType";
	public static String PROP_TK_COLUMN = "TkColumn";
	public static String PROP_PARSE_DZ_FILE_CLASS = "ParseDzFileClass";
	public static String PROP_ORIGINAL_DATA_TABLE_NAME = "OriginalDataTableName";
	public static String PROP_FTP_DZ_FILE_PATH = "FtpDzFilePath";
	public static String PROP_TK_TABLE_NAME = "TkTableName";
	public static String PROP_START_ROW = "StartRow";
	public static String PROP_DZ_DATA_TABLE_NAME = "DzDataTableName";
	public static String PROP_BANK_NAME = "BankName";
	public static String PROP_TRADE_DZ_IMPL_CLASS = "TradeDzImplClass";
	public static String PROP_RIQIE_ORIGINAL_TABLE_NAME = "RiqieOriginalTableName";
	public static String PROP_IS_TK = "IsTk";
	public static String PROP_TK_CONTEXT = "TkContext";
	public static String PROP_TK_TYPE = "TkType";
	public static String PROP_DZ_FILE_NAME_PATTERN = "DzFileNamePattern";
	public static String PROP_WHETHER_OUTER_DZ = "WhetherOuterDz";
	public static String PROP_INST_ENTITY_NAME = "InstEntityName";
	public static String PROP_ID = "Id";
	public static String PROP_DZ_FILE_PATH = "DzFilePath";
	public static String PROP_BANK_ENTITY_NAME = "BankEntityName";


	// constructors
	public BaseBankInst () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseBankInst (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseBankInst (
		java.lang.Integer id,
		java.lang.String bankName,
		java.lang.String parseDzFileClass,
		java.lang.String dzDataTableName,
		java.lang.String ftpDzFilePath,
		java.lang.String dzFileNamePattern,
		java.lang.String dzFilePath,
		java.lang.String originalDataTableName,
		java.lang.Integer startRow,
		boolean isTk) {

		this.setId(id);
		this.setBankName(bankName);
		this.setParseDzFileClass(parseDzFileClass);
		this.setDzDataTableName(dzDataTableName);
		this.setFtpDzFilePath(ftpDzFilePath);
		this.setDzFileNamePattern(dzFileNamePattern);
		this.setDzFilePath(dzFilePath);
		this.setOriginalDataTableName(originalDataTableName);
		this.setStartRow(startRow);
		this.setIsTk(isTk);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String bankName;
	private java.lang.String parseDzFileClass;
	private java.lang.String tradeDzImplClass;
	private java.lang.String dzDataTableName;
	private java.lang.String ftpDzFilePath;
	private java.lang.String dzFileNamePattern;
	private java.lang.String dzFilePath;
	private java.lang.String originalDataTableName;
	private java.lang.String riqieOriginalTableName;
	private java.lang.String tkTableName;
	private java.lang.String instEntityName;
	private java.lang.String bankEntityName;
	private java.lang.Integer startRow;
	private boolean isTk;
	private java.lang.Integer tkType;
	private java.lang.String tkContext;
	private java.lang.Integer tkColumn;
	private java.lang.Integer bankType;
	private boolean whetherOuterDz;

	// collections
	private java.util.Set<cn.com.chinaebi.dz.object.InstInfo> instInfos;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="bank_id"
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
	 * Return the value associated with the column: bank_name
	 */
	public java.lang.String getBankName () {
		return bankName;
	}

	/**
	 * Set the value related to the column: bank_name
	 * @param bankName the bank_name value
	 */
	public void setBankName (java.lang.String bankName) {
		this.bankName = bankName;
	}



	/**
	 * Return the value associated with the column: parse_dz_file_class
	 */
	public java.lang.String getParseDzFileClass () {
		return parseDzFileClass;
	}

	/**
	 * Set the value related to the column: parse_dz_file_class
	 * @param parseDzFileClass the parse_dz_file_class value
	 */
	public void setParseDzFileClass (java.lang.String parseDzFileClass) {
		this.parseDzFileClass = parseDzFileClass;
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
	 * Return the value associated with the column: dz_data_tableName
	 */
	public java.lang.String getDzDataTableName () {
		return dzDataTableName;
	}

	/**
	 * Set the value related to the column: dz_data_tableName
	 * @param dzDataTableName the dz_data_tableName value
	 */
	public void setDzDataTableName (java.lang.String dzDataTableName) {
		this.dzDataTableName = dzDataTableName;
	}



	/**
	 * Return the value associated with the column: ftp_dz_file_path
	 */
	public java.lang.String getFtpDzFilePath () {
		return ftpDzFilePath;
	}

	/**
	 * Set the value related to the column: ftp_dz_file_path
	 * @param ftpDzFilePath the ftp_dz_file_path value
	 */
	public void setFtpDzFilePath (java.lang.String ftpDzFilePath) {
		this.ftpDzFilePath = ftpDzFilePath;
	}



	/**
	 * Return the value associated with the column: dz_file_name_pattern
	 */
	public java.lang.String getDzFileNamePattern () {
		return dzFileNamePattern;
	}

	/**
	 * Set the value related to the column: dz_file_name_pattern
	 * @param dzFileNamePattern the dz_file_name_pattern value
	 */
	public void setDzFileNamePattern (java.lang.String dzFileNamePattern) {
		this.dzFileNamePattern = dzFileNamePattern;
	}



	/**
	 * Return the value associated with the column: dz_file_path
	 */
	public java.lang.String getDzFilePath () {
		return dzFilePath;
	}

	/**
	 * Set the value related to the column: dz_file_path
	 * @param dzFilePath the dz_file_path value
	 */
	public void setDzFilePath (java.lang.String dzFilePath) {
		this.dzFilePath = dzFilePath;
	}



	/**
	 * Return the value associated with the column: original_data_tableName
	 */
	public java.lang.String getOriginalDataTableName () {
		return originalDataTableName;
	}

	/**
	 * Set the value related to the column: original_data_tableName
	 * @param originalDataTableName the original_data_tableName value
	 */
	public void setOriginalDataTableName (java.lang.String originalDataTableName) {
		this.originalDataTableName = originalDataTableName;
	}



	/**
	 * Return the value associated with the column: riqie_original_tableName
	 */
	public java.lang.String getRiqieOriginalTableName () {
		return riqieOriginalTableName;
	}

	/**
	 * Set the value related to the column: riqie_original_tableName
	 * @param riqieOriginalTableName the riqie_original_tableName value
	 */
	public void setRiqieOriginalTableName (java.lang.String riqieOriginalTableName) {
		this.riqieOriginalTableName = riqieOriginalTableName;
	}



	/**
	 * Return the value associated with the column: tk_tableName
	 */
	public java.lang.String getTkTableName () {
		return tkTableName;
	}

	/**
	 * Set the value related to the column: tk_tableName
	 * @param tkTableName the tk_tableName value
	 */
	public void setTkTableName (java.lang.String tkTableName) {
		this.tkTableName = tkTableName;
	}



	/**
	 * Return the value associated with the column: inst_entity_name
	 */
	public java.lang.String getInstEntityName () {
		return instEntityName;
	}

	/**
	 * Set the value related to the column: inst_entity_name
	 * @param instEntityName the inst_entity_name value
	 */
	public void setInstEntityName (java.lang.String instEntityName) {
		this.instEntityName = instEntityName;
	}



	/**
	 * Return the value associated with the column: bank_entity_name
	 */
	public java.lang.String getBankEntityName () {
		return bankEntityName;
	}

	/**
	 * Set the value related to the column: bank_entity_name
	 * @param bankEntityName the bank_entity_name value
	 */
	public void setBankEntityName (java.lang.String bankEntityName) {
		this.bankEntityName = bankEntityName;
	}



	/**
	 * Return the value associated with the column: start_row
	 */
	public java.lang.Integer getStartRow () {
		return startRow;
	}

	/**
	 * Set the value related to the column: start_row
	 * @param startRow the start_row value
	 */
	public void setStartRow (java.lang.Integer startRow) {
		this.startRow = startRow;
	}



	/**
	 * Return the value associated with the column: isTk
	 */
	public boolean isIsTk () {
		return isTk;
	}

	/**
	 * Set the value related to the column: isTk
	 * @param isTk the isTk value
	 */
	public void setIsTk (boolean isTk) {
		this.isTk = isTk;
	}



	/**
	 * Return the value associated with the column: tk_type
	 */
	public java.lang.Integer getTkType () {
		return tkType;
	}

	/**
	 * Set the value related to the column: tk_type
	 * @param tkType the tk_type value
	 */
	public void setTkType (java.lang.Integer tkType) {
		this.tkType = tkType;
	}



	/**
	 * Return the value associated with the column: tk_context
	 */
	public java.lang.String getTkContext () {
		return tkContext;
	}

	/**
	 * Set the value related to the column: tk_context
	 * @param tkContext the tk_context value
	 */
	public void setTkContext (java.lang.String tkContext) {
		this.tkContext = tkContext;
	}



	/**
	 * Return the value associated with the column: tk_column
	 */
	public java.lang.Integer getTkColumn () {
		return tkColumn;
	}

	/**
	 * Set the value related to the column: tk_column
	 * @param tkColumn the tk_column value
	 */
	public void setTkColumn (java.lang.Integer tkColumn) {
		this.tkColumn = tkColumn;
	}



	/**
	 * Return the value associated with the column: bank_type
	 */
	public java.lang.Integer getBankType () {
		return bankType;
	}

	/**
	 * Set the value related to the column: bank_type
	 * @param bankType the bank_type value
	 */
	public void setBankType (java.lang.Integer bankType) {
		this.bankType = bankType;
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


 
	public java.util.Set<cn.com.chinaebi.dz.object.InstInfo> getInstInfos () {
			return instInfos==null? new java.util.TreeSet<cn.com.chinaebi.dz.object.InstInfo>():instInfos;
	}

	/**
	 * Set the value related to the column: InstInfos
	 * @param instInfos the InstInfos value
	 */
	public void setInstInfos (java.util.Set<cn.com.chinaebi.dz.object.InstInfo> instInfos) {
		this.instInfos = instInfos;
	}

	public void addToInstInfos (cn.com.chinaebi.dz.object.InstInfo instInfo) {
		if (null == getInstInfos()) setInstInfos(new java.util.TreeSet<cn.com.chinaebi.dz.object.InstInfo>());
		getInstInfos().add(instInfo);
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof cn.com.chinaebi.dz.object.BankInst)) return false;
		else {
			cn.com.chinaebi.dz.object.BankInst bankInst = (cn.com.chinaebi.dz.object.BankInst) obj;
			if (null == this.getId() || null == bankInst.getId()) return false;
			else return (this.getId().equals(bankInst.getId()));
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