package com.chinaebi.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RegularExpressionUtil {
	public static int complieString(String sourceStr,String complieStr){
		Pattern p = Pattern.compile(complieStr,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sourceStr);
        int count = 0;
        while(m.find()){
              count ++;
        }
        return count;
	}
	
	/**
	 * 获取字符串中文个数
	 * @param name_
	 * @return int
	 */
	public static int statisticalChineseNumber(String name_){
		if(StringUtils.isNotBlank(name_)){
			name_ = name_.replaceAll("/", "");
		}
		int count = 0;
		String regex="([\u4e00-\u9fa5]+)";
		Matcher matchera = Pattern.compile(regex).matcher(name_);
		if(matchera.find()){
			String hz = matchera.group(0);
			count = hz.length();
		}
		return count;
	}
	
	public static int statistiNumber(String name_){
		int count = 0;
		String regex="([^\u4e00-\u9fa5]+)";
		Matcher matchera = Pattern.compile(regex).matcher(name_);
		if(matchera.find()){
			String hz = matchera.group(0);
			count = hz.length();
		}
		return count;
	}
	
	/** 
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)和gb(千兆字节)
     *  
     * @param bytes 
     * @return 
     */  
    public static String bytes2kb(long bytes) {  
        BigDecimal filesize = new BigDecimal(bytes);  
        BigDecimal gigabyte = new BigDecimal(1024 * 1024 * 1024);  
        double returnValue = filesize.divide(gigabyte, 2, BigDecimal.ROUND_UP).doubleValue();  
//        float returnValue = filesize.divide(gigabyte, 2, BigDecimal.ROUND_UP).floatValue();  
        if (returnValue > 1)  
            return (returnValue + "GB");  
        BigDecimal megabyte = new BigDecimal(1024 * 1024);  
        returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).doubleValue();  
//        returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();  
        if (returnValue > 1)  
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);  
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).doubleValue();  
//        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();  
        return (returnValue + "KB");  
    } 
    public static void main(String[] args) {
    	File file = new File("");
    	System.out.println(file.length());
    	System.out.println(bytes2kb(file.length()));
    	System.out.println(bytes2kb(0l));
	}
}
