package cn.com.chinaebi.dz.util;

public class DercStatusConvert {
	public static String getDercStatus(int derc_status) {
		String dercStatus = "";
		switch (derc_status) {
		case 1:
			dercStatus = "消费(支付)";
			break;
		case 2:
			dercStatus = "退款(冲正)";
			break;
		case 3:
			dercStatus = "差错调整(支付)";
			break;
		case 4:
			dercStatus = "差错调整(冲正)";
			break;
		case 5:
			dercStatus = "结算到电银账户";
			break;
		case 6:
			dercStatus = "结算到银行账户";
			break;
		case 7:
			dercStatus = "手工调整";
			break;
		default:
			dercStatus = "其他";
			break;
		}
		return dercStatus;
	}
}
