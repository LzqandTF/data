package com.chinaebi.utils;

/**
 * 定义融易付交易类型常量
 * 
 * @author wufei
 * 
 */
public class Ryt_trade_type {
	
	public static String getRytTradeName(int type) {
		String typeName = "";
		switch (type) {
		case 0:
			typeName = "初始化";
			break;
		case 1:
			typeName = "个人网银支付";
			break;
		case 2:
			typeName = "消费充值卡支付";
			break;
		case 3:
			typeName = "信用卡支付";
			break;
		case 4:
			typeName = "退款交易";
			break;
		case 5:
			typeName = "增值业务";
			break;
		case 6:
			typeName = "语音支付";
			break;
		case 7:
			typeName = "企业网银支付";
			break;
		case 8:
			typeName = "手机WAP支付";
			break;
		case 9:
			typeName = "退款交易";
			break;
		case 10:
			typeName = "POS支付";
			break;
		case 11:
			typeName = "对私代付";
			break;
		case 12:
			typeName = "对公代付";
			break;
		case 13:
			typeName = "B2B充值";
			break;
		case 14:
			typeName = "线下充值";
			break;
		case 15:
			typeName = "对私代扣";
			break;
		case 16:
			typeName = "对公提现";
			break;
		case 17:
			typeName = "对私提现";
			break;
		case 18:
			typeName = "快捷支付";
			break;
		case 19:
			typeName = "消费";
			break;
		case 20:
			typeName = "消费冲正";
			break;
		case 21:
			typeName = "消费撤销";
			break;
		case 22:
			typeName = "消费撤销冲正";
			break;
		case 23:
			typeName = "退货";
			break;
		case 24:
			typeName = "预授权完成";
			break;
		case 25:
			typeName = "预授权完成撤销";
			break;
		case 26:
			typeName = "预授权完成冲正";
			break;
		case 27:
			typeName = "预授权完成撤销冲正";
			break;
		case 28:
			typeName = "脱机消费";
			break;
		case 29:
			typeName = "转账汇款";
			break;
		case 30:
			typeName = "账户支付";
			break;
		case 31:
			typeName = "未知";
			break;
		default:
			typeName = "其他";
			break;
		}
		return typeName;
	}
}
