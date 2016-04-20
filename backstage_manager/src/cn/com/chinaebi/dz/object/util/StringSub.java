package cn.com.chinaebi.dz.object.util;

import org.apache.commons.lang.StringUtils;

public class StringSub {

	private static int th = 10; //退货
	private static int ls = 6;  //流水长度
	
	/**
	 * 获取退货交易原笔流水号
	 * @param originalTransInfo
	 * @return String
	 */
	public static String getOriTh(String originalTransInfo){
		String oriReqSysStance = null;
		if(StringUtils.isNotBlank(originalTransInfo)){
			oriReqSysStance = originalTransInfo.substring(originalTransInfo.length()-th,originalTransInfo.length()-th+ls);
		}
		return oriReqSysStance;
	}
	
	/**
	 * 获取撤销交易原笔流水号
	 * @param originalTransInfo
	 * @return String
	 */
	public static String getOriCx(String originalTransInfo){
		String oriReqSysStance = null;
		if(StringUtils.isNotBlank(originalTransInfo)){
			oriReqSysStance = originalTransInfo.substring(originalTransInfo.length()-ls,originalTransInfo.length());
		}
		return oriReqSysStance;
	}
	
	public static void main(String[] args) {
		System.out.println(StringSub.getOriTh("4702640910"));
		System.out.println(StringSub.getOriCx("4702640910"));
	}
}
