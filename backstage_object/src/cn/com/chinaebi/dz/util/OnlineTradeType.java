package cn.com.chinaebi.dz.util;

public class OnlineTradeType {
	public static String getTradeTypeInChinese(int type_code){
		String tradeName = "";
		switch (type_code) {
		case 0:
			tradeName = "初始化";
			break;
		case 1:
			tradeName = "个人网银支付";
			break;
		case 2:
			tradeName = "消费充值卡支付";
			break;
		case 3:
			tradeName = "信用卡支付";
			break;
		case 4:
			tradeName = "退款交易";
			break;
		case 5:
			tradeName = "增值业务";
			break;
		case 6:
			tradeName = "语音支付";
			break;
		case 7:
			tradeName = "企业网银支付";
			break;
		case 8:
			tradeName = "手机WAP支付";
			break;
		case 9:
			tradeName = "退货交易";
			break;
		case 10:
			tradeName = "POS支付";
			break;
		case 11:
			tradeName = "对私代付";
			break;
		case 12:
			tradeName = "对公代付";
			break;
		case 13:
			tradeName = "B2B充值";
			break;
		case 14:
			tradeName = "线下充值";
			break;
		case 15:
			tradeName = "对私代扣";
			break;
		case 16:
			tradeName = "对公提现";
			break;
		case 17:
			tradeName = "对私提现";
			break;
		case 18:
			tradeName = "快捷支付";
			break;
		default:
			break;
		}
		
		return tradeName;
	}
	public static String getRefundTypeInChinese(int refund_type){
		String refundTypeName = "";
		switch (refund_type) {
		case 0:
			refundTypeName = "人工经办";
			break;
		case 1:
			refundTypeName = "联机退款";
			break;
		case 2:
			refundTypeName = "联机退款-人工经办";
			break;
		default:
			break;
		}
		return refundTypeName;
	}
}
