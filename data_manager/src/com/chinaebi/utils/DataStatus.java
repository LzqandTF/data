package com.chinaebi.utils;

public class DataStatus {
	/**
	 * 渠道类型-线下
	 */
	public static final int instType_0 = 0;
	
	/**
	 * 渠道类型-线上
	 */
	public static final int instType_1 = 1;
	
	/**
	 * 需要清算
	 */
	public static final int clean = 1;
	
	/**
	 * 不需要清算
	 */
	public static final int no_clean = 0;
	
	/**
	 * 消费(支付)
	 */
	public static final int CONSUME = 1;
	/**
	 * 退款(冲正)
	 */
	public static final int REFUND = 2;
	/**
	 * 差错调整(支付)
	 */
	public static final int ERROR_ADJUST_CONSUME = 3;
	/**
	 * 差错调整(冲正)
	 */
	public static final int ERROR_ADJUST_REFUND = 4;
	/**
	 * 结算到电银账户
	 */
	public static final int SETTLE_TO_DY = 5;
	/**
	 * 结算到银行账户
	 */
	public static final int SETTLE_TO_BANK = 6;
	
	/**
	 * 手工调账
	 */
	public static final int SHOU_GONG_TIAO_ZHANG = 7;
	
	/**
	 * 一般对账文件
	 */
	public static final int DZ_FILE = 1;
	
	/**
	 * 差错对账文件
	 */
	public static final int ERROR_DZ_FILE = 2;
	
	/**
	 * 品牌服务费文件
	 */
	public static final int PPFW_FILE = 3;
	
	/**
	 * 对账文件名称日期格式 YYYY-MM-DD
	 */
	public static final String date_format_1 = "yyyy-MM-dd";
	/**
	 * 对账文件名称日期格式 YYYYMMDD
	 */
	public static final String date_format_2 = "yyyyMMdd";
	/**
	 * 对账文件名称日期格式 MMDD
	 */
	public static final String date_format_3 = "MMdd";
	
	/**
	 * 对账文件名称日期格式 yyyyMMddHHmmss
	 */
	public static final String date_format_4 = "yyyyMMddHHmmss";
	
	/**
	 *  对账文件名称日期格式 yyMMdd
	 */
	public static final String date_format_5 = "yyMMdd";
	
	/**
	 * 对账文件名称日期格式 MM.dd
	 */
	public static final String date_format_6 = "MM.dd";
	
	/**
	 * 原始交易数据 需要清算
	 */
	public static final int data_qs = 1;
	
	/**
	 * 退款经办
	 */
	public static final int TK_PRO = 1;
	/**
	 * 退款审核
	 */
	public static final int TK_REF = 1;
	
	/**
	 * 退款
	 */
	public static final int TK_YES = 1;
	
	/**
	 * 不退款
	 */
	public static final int TK_NO = 0;
	
	/**
	 * 日期格式 yyyy-MM-dd HH:mm:ss
	 */
	public static final String date_format_7 = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 *  日期格式 yyyy-MM-dd HH:mm:ss:SSS
	 */
	public static final String date_format_8 = "yyyy-MM-dd HH:mm:ss:SSS";
	
	/**
	 * 原始差错
	 */
	public static final int ORG_ERROR_RESOURCE = 0;
	
	/**
	 * 对账文件差错
	 */
	public static final int DZ_ERROR_RESOURCE = 1;
	
	/**
	 * 未冲正
	 */
	public static final int NO_ROLL_BK = 0;
	
	/**
	 * 冲正
	 */
	public static final int ROLL_BK = 1;
}
