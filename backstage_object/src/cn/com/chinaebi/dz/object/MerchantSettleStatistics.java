package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseMerchantSettleStatistics;



public class MerchantSettleStatistics extends BaseMerchantSettleStatistics {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MerchantSettleStatistics () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MerchantSettleStatistics (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MerchantSettleStatistics (
		java.lang.Integer id,
		java.lang.Integer instId,
		java.lang.String merCode,
		java.lang.Integer merType,
		java.lang.Integer deductStlmDate,
		java.lang.String tradeAmount,
		java.lang.Integer tradeCount,
		java.lang.String refundAmount,
		java.lang.Integer refundCount,
		java.lang.String merFee,
		java.lang.String systemFee,
		java.lang.String merRefundFee,
		java.lang.String settleAmount,
		java.lang.String systemRefundFee,
		java.lang.Integer bankId,
		java.lang.Integer tradeGcCount) {

		super (
			id,
			instId,
			merCode,
			merType,
			deductStlmDate,
			tradeAmount,
			tradeCount,
			refundAmount,
			refundCount,
			merFee,
			systemFee,
			merRefundFee,
			settleAmount,
			systemRefundFee,
			bankId,
			tradeGcCount);
	}

/*[CONSTRUCTOR MARKER END]*/


}