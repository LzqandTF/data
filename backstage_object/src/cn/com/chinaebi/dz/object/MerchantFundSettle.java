package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMerchantFundSettle;



public class MerchantFundSettle extends BaseMerchantFundSettle {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MerchantFundSettle () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MerchantFundSettle (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MerchantFundSettle (
		java.lang.Integer id,
		java.lang.String merCode,
		java.lang.Integer merType,
		java.lang.Integer settleType,
		java.lang.String sysBatchNo,
		java.lang.String merBatchNo,
		java.lang.Integer startDate,
		java.lang.Integer endDate,
		java.lang.Integer createTabDate,
		java.lang.String merName,
		java.lang.String tradeAmount,
		java.lang.Integer tradeCount,
		java.lang.String refundAmount,
		java.lang.Integer refundCount,
		java.lang.String systemFee,
		java.lang.String refundMerFee,
		java.lang.String openBankName,
		java.lang.String openAcountName,
		java.lang.String openAccountCode,
		java.lang.String settleAmount,
		java.lang.Integer settleWay,
		java.lang.Integer settleState,
		java.lang.Integer settleDate,
		java.lang.Integer settleConfirmDate,
		java.lang.Integer bilManual,
		java.lang.String merFee,
		java.lang.String systemRefundFee) {

		super (
			id,
			merCode,
			merType,
			settleType,
			sysBatchNo,
			merBatchNo,
			startDate,
			endDate,
			createTabDate,
			merName,
			tradeAmount,
			tradeCount,
			refundAmount,
			refundCount,
			systemFee,
			refundMerFee,
			openBankName,
			openAcountName,
			openAccountCode,
			settleAmount,
			settleWay,
			settleState,
			settleDate,
			settleConfirmDate,
			bilManual,
			merFee,
			systemRefundFee);
	}

/*[CONSTRUCTOR MARKER END]*/


}