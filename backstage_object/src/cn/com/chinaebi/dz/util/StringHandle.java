package cn.com.chinaebi.dz.util;

import org.apache.commons.lang.StringUtils;

public class StringHandle {

	/**
	 * 字符串左填充
	 * @param stringValue ：数据
	 * @param size ：长度
	 * @param padString ：补充的内容
	 * @return String
	 */
	public static String leftPad(String stringValue,int size,String padString){
		size = size-stringValue.length();
		String padStrings = StringUtils.repeat(padString, size);
		return StringUtils.reverse(StringUtils.reverse(stringValue)+padStrings);		
	}
	
	/**
	 * 字符串右填充
	 * @param stringValue ：数据
	 * @param size ：长度
	 * @param padString ：补充的内容
	 * @return String
	 */
	/**
	 * 字符串左填充
	 * @param stringValue ：数据
	 * @param size ：长度
	 * @param padString ：补充的内容
	 * @return String
	 */ static String rightPad(String stringValue,int size,String padString){
		size = size-stringValue.length();
		String padStrings = StringUtils.repeat(padString, size);
		return stringValue+padStrings;
	}
	
	public static void main(String[] args) {
		System.out.println(StringHandle.leftPad("9100030813", 12, "0"));
		System.out.println(StringHandle.rightPad("9100030813", 12, "0"));
	}
}
