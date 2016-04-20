package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseBankInst;



public class BankInst extends BaseBankInst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public BankInst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BankInst (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public BankInst (
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

		super (
			id,
			bankName,
			parseDzFileClass,
			dzDataTableName,
			ftpDzFilePath,
			dzFileNamePattern,
			dzFilePath,
			originalDataTableName,
			startRow,
			isTk);
	}

/*[CONSTRUCTOR MARKER END]*/


}