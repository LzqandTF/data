package cn.com.chinaebi.dz.object.util;

public class DataStatus {
	/**
	 * 根据流水查询
	 */
	public static final Integer flag_0 = 0;
	/**
	 * 根据订单号查询
	 */
	public static final Integer flag_1 = 1;
	/**
	 * 根据流水号或者订单号查询
	 */
	public static final Integer flag_2 = 2;
	/**
	 * 根据流水号匹配原交易(bk_seq1-银行流水号)
	 */
	public static final Integer flag_3 = 3;
	/**
	 * 无差错
	 */
	public static final Integer no_error_status= 0;
	
	/**
	 * 长款,走差错处理
	 */
	public static final Integer long_status = 1;
	
	/**
	 * 短款,走差错处理
	 */
	public static final Integer short_status = 2;
	
	/**
	 * 金额不符,走差错处理
	 */
	public static final Integer amt_error = 3;
	
	/**
	 * 短款-该数据已清算未对账
	 * 备注:线上银联23点之后的成功交易需要未对账做清算
	 */
	public static final Integer short_no_dz_status = 4;
	
	/**
	 * 需要清算
	 */
	public static final boolean clean = true;
	
	/**
	 * 不需要清算
	 */
	public static final boolean no_clean = false;
	
	/**
	 * 未对账
	 */
	public static final Integer not_dz = 0;
	
	/**
	 * 对账成功
	 */
	public static final Integer dz_success = 1;
	
	/**
	 * 对账失败,走差错处理
	 */
	public static final Integer dz_error = 2;
	
	/**
	 * 无需处理
	 */
	public static final Integer no_handle = 3;
	
	/**
	 * 请结算状态
	 */
	public static final Integer qs_handle = 4;
	
	/**
	 * 是否冲正
	 */
	public static final boolean no_deductRollBk = false;
	public static final boolean deductRollBk = true;
	
	/**
	 * 是否日切
	 */
	public static final boolean riqie = true;
	public static final boolean no_riqie = false;
	
	/**
	 * 对账标识
	 * 0:自动
	 * 1:手动
	 */
	public static final Integer auto = 0;
	public static final Integer no_auto = 1;
	
	/**
	 * 是否退款
	 */
	public static final boolean no_tk = false;
	public static final boolean tk = true;
	
	/**
	 * 是否结算
	 */
	public static final boolean js_yes = true;
	public static final boolean js_no = false;
	
	/**
	 * 交易状态
	 * 	1–待支付
	 * 	2–成功
	 * 	3–失败
	 *  4-请求银行失败
	 */
	public static final Integer tstat_dzf = 1;
	public static final Integer tstat_succes = 2;
	public static final Integer tstat_fail = 3;
	public static final Integer tstat_req_bank_fail = 3;
	
	
	/**
	 * 线下渠道类型
	 */
	public static final Integer inst_type_0 = 0;
	
	/**
	 * 线上渠道类型
	 */
	public static final Integer inst_type_1 = 1;
	
	
	/**
	 * 1:消费(支付)
	 */
	public static final Integer derc_status_1 = 1;
	/**
	 * 2:退款(冲正)
	 */
	public static final Integer derc_status_2 = 2;
	/**
	 * 3:差错调整
	 */
	public static final Integer derc_status_3 = 3;
	/**
	 * 4:结算到电银账户
	 */
	public static final Integer derc_status_4 = 4;
	
	/**
	 * 线上交易状态-初始化
	 */
	public static final Integer ryt_tstat_0 = 0;
	
	/**
	 * 线上交易状态-待支付
	 */
	public static final Integer ryt_tstat_1 = 1;
	
	/**
	 * 线上交易状态-成功
	 */
	public static final Integer ryt_tstat_2 = 2;
	
	/**
	 * 线上交易状态-失败
	 */
	public static final Integer ryt_tstat_3 = 3;
	
	/**
	 * 线上交易状态-请求银行失败
	 */
	public static final Integer ryt_tstat_4 = 4;
	
	/**
	 * 线上交易状态-撤销
	 */
	public static final Integer ryt_tstat_5 = 5;
	
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
	 * 退款经办
	 */
	public static final Integer ryt_tk_jb = 1;
	
	/**
	 * 退款审核
	 */
	public static final Integer ryt_tk_sh = 2;
	
	/**
	 * T+1统计 正常对账统计数据
	 */
	public static final Integer t1_dz_cl = 0;
	
	/**
	 * T+1统计 退款经办
	 */
	public static final Integer t1_ryt_tk_jb = 1;
	
	/**
	 * T+1统计 退款审核
	 */
	public static final Integer t1_ryt_tk_sh = 2;
	
	/**
	 * T+1统计 差错处理审核
	 */
	public static final Integer t1_ryt_cc_sh = 3;
	
	/**
	 * 原始交易差错
	 */
	public static final Integer error_resouce_original = 0;
	
	/**
	 * 对账文件交易差错
	 */
	public static final Integer error_resouce_duizhang = 1;
	
	
	/**
	 * 差错处理状态-未处理
	 */
	public static final Integer error_handle_0 = 0;
	
	/**
	 * 差错处理状态-待审核
	 */
	public static final Integer error_handle_1 = 1;
	
	/**
	 * 差错处理状态-审核
	 */
	public static final Integer error_handle_2 = 2;
	
	/**
	 * 差错处理状态-已驳回
	 */
	public static final Integer error_handle_3 = 3;
	
	/**
	 * 差错处理状态-无需处理
	 */
	public static final Integer error_handle_4 = 4;
	
	/**
	 * 重对账未成功
	 */
	public static final Integer double_check_fail = 0;
	
	/**
	 * 重对账成功
	 */
	public static final Integer double_check_success = 1;
	
	/**
	 * 重对账-对账单数据不存在
	 */
	public static final Integer double_check_duizhangNotData = 2;
	
	/**
	 * 重对账-无需对账
	 */
	public static final Integer double_check_OriginalNotData = 3;
	
	/**
	 * 工作流节点状态
	 */
	public static final String excute_sucess = "1";//成功
	public static final String excute_fail = "2";//失败
	public static final String no_dz_data = "3";//对账单数据不存在
	public static final String no_ori_data = "4";//原始交易数据不存在
	
	
	public static final Integer dz_file = 1;//对账总表
	public static final Integer js_file = 2;//结算文件
	public static final Integer qs_file = 3;//内部清算文件
	
}
