/*********************************************************************
 * 
 * Copyright (C) 2011, Shanghai Chinaebi
 * All rights reserved.
 * http://www.chinaebi.com.cn/
 * 
 *********************************************************************/
package cn.com.chinaebi.dz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * Get SimpleDateFormat Util
 * */
public class DYDataUtil {

	private static Log log = LogFactory.getLog(DYDataUtil.class);
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";  
	public static final String DATE_FORMAT_2 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_3 = "yyyyMMdd";
	public static final String DATE_FORMAT_4 = "HHmmss";
	public static final String DATE_FORMAT_5 = "HH";
	public static final String DATE_FORMAT_6 = "yyyy-MM-dd";
	public static final String DATE_FORMAT_7 = "yyyyMMddHHmm";
	public static final String DATE_FORMAT_8 = "yyyy-MM-dd HH:mm:ss:SSS";
	
//	private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();  
	private static Map<String, SimpleDateFormat> mapThreadLocal = new HashMap<String, SimpleDateFormat>();

	/*public static void main(String[] args) {
		Date date = new Date();
		System.out.println(getSimpleDateFormat(DATE_FORMAT_1).format(date));
		System.out.println(getSimpleDateFormat(DATE_FORMAT_2).format(date));
		System.out.println(getSimpleDateFormat(DATE_FORMAT_3).format(date));
		System.out.println(getSimpleDateFormat(DATE_FORMAT_4).format(date));
	}*/
	
	/*public static SimpleDateFormat getSimpleDateFormat(String pattern) {  
    	SimpleDateFormat df = (SimpleDateFormat) threadLocal.get();  
        if (df == null) {  
            df = new SimpleDateFormat(pattern);  
            threadLocal.set(df);  
        }else
        	df.applyPattern(pattern);
        
        return df;  
    } */ 
	
	public static SimpleDateFormat getSimpleDateFormat(String pattern){
		SimpleDateFormat df = mapThreadLocal.get(pattern);  
		if (df == null) {  
            df = new SimpleDateFormat(pattern);  
            mapThreadLocal.put(pattern, df);  
        }
        return df; 
	}
	
	/**
	 * 字符串yyyy-MM-dd+1
	 * @param originalReqTime yyyy-MM-dd
	 * @return yyyy-MM-dd
	 */
	public static String getNextDateString(String originalReqTime){
		SimpleDateFormat dateFormat = getSimpleDateFormat(DATE_FORMAT_6);
		String date = null;
		try {
			Date originalDate = dateFormat.parse(originalReqTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(originalDate);
			int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
			calendar.set(Calendar.DAY_OF_MONTH, day);
			date = dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			log.info(e);
		}
		
		return date;
	}
	
	/**
	 * 字符串yyyyMMddHHmmss 转日期 yyyy-MM-dd HH:mm:ss 
	 * @param dateStr yyyyMMddHHmmss 
	 * @return yyyy-MM-dd HH:mm:ss 
	 */
	public static Date getformatConversionDate(String dateStr){
		SimpleDateFormat dateFormat = getSimpleDateFormat(DATE_FORMAT_2);
		Date date = null;
		try {
			Date date2 = dateFormat.parse(dateStr);
			SimpleDateFormat dateFormat2 = getSimpleDateFormat(DATE_FORMAT_1);
			String conversionStr = dateFormat2.format(date2);
			date = dateFormat2.parse(conversionStr);
		} catch (Exception e) {
			log.info(e);
		}
		return date;
	}
	
	/**
	 * 字符串yyyyMMdd 转日期 yyyy-MM-dd HH:mm:ss 
	 * @param dateStr yyyyMMdd 
	 * @return yyyy-MM-dd HH:mm:ss 
	 */
	public static Date getformatConversionDate2(String dateStr){
		SimpleDateFormat dateFormat = getSimpleDateFormat(DATE_FORMAT_3);
		Date date = null;
		try {
			Date date2 = dateFormat.parse(dateStr);
			SimpleDateFormat dateFormat2 = getSimpleDateFormat(DATE_FORMAT_1);
			String conversionStr = dateFormat2.format(date2);
			date = dateFormat2.parse(conversionStr);
		} catch (Exception e) {
			log.info(e);
		}
		return date;
	}
	
	/**
	 * 字符串yyyy-MM-dd 转日期yyyy-MM-dd :00:00:00
	 * @param dateStr : yyyy-MM-dd + 00:00:00
	 * @return yyyy-MM-dd HH:mm:ss 
	 */
	public static Date getformatConversionDate3(String dateStr){
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateStr);
		buffer.append(" 00:00:00");
		Date date = null;
		try {
			SimpleDateFormat dateFormat = getSimpleDateFormat(DATE_FORMAT_1);
			date = dateFormat.parse(buffer.toString());
		} catch (Exception e) {
			log.info(e);
		}
		return date;
	}
	
	/**
	 * 字符串yyyy-MM-dd 转日期yyyy-MM-dd :23:59:59
	 * @param dateStr : yyyy-MM-dd + 23:59:59
	 * @return yyyy-MM-dd HH:mm:ss 
	 */
	public static Date getformatConversionDate4(String dateStr){
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateStr);
		buffer.append(" 23:59:59");
		Date date = null;
		try {
			SimpleDateFormat dateFormat = getSimpleDateFormat(DATE_FORMAT_1);
			date = dateFormat.parse(buffer.toString());
		} catch (Exception e) {
			log.info(e);
		}
		return date;
	}
	
	public static Date parseStringToDate(String dateString,String parseFormat){
		SimpleDateFormat dateFormat = getSimpleDateFormat(parseFormat);
		Date originalDate = null;
		try{
			originalDate = dateFormat.parse(dateString);
		}catch(Exception e){
			log.error(e);
		}
		return originalDate;
	}
	
	public static Date getparseConversionDate(String sysDate,String sysTime){
		try {
			if(StringUtils.isNotBlank(sysDate) && StringUtils.isNotBlank(sysTime)){
				sysTime = getStringTime(Integer.valueOf(sysTime)).replaceAll(":", "");
				String tradeTime = sysDate + sysTime;
				return DYDataUtil.getSimpleDateFormat("yyyyMMddHHmmss").parse(tradeTime);
			}
		} catch (Exception e) {
			log.info(e);
		}
		return null;
	}
	
	/**
	 * 将UTC-second类型时间转化为hh:mm:ss格式
	 * @return
	 */
	public static String getStringTime(int nowtime) {
		int hour = (nowtime % 86400) / 3600;
		int min = (nowtime % 3600) / 60;
		int second = nowtime % 60;
		return hour + ":" + (min < 10 ? "0" + min : min) + ":" + (second < 10 ? "0" + second : second);
	}
	
	/**
	 * 将UTC-second类型时间转化为yyyyMMddHHmmss格式
	 * @return
	 */
	public static Long getStringTimeyyyyMMddHHmmss(String sys_dateStr,String sys_timeStr) {
		int sys_date = Integer.valueOf(sys_dateStr);
		int sys_time = Integer.valueOf(sys_timeStr);
		StringBuffer stringBuffer = new StringBuffer();
		int hour = (sys_time % 86400) / 3600;
		int min = (sys_time % 3600) / 60;
		int second = sys_time % 60;
		if(sys_time == 0)
			stringBuffer.append(sys_date);
		else{
			stringBuffer.append(sys_date);
			stringBuffer.append(hour < 10 ? "0" + hour : hour);
			stringBuffer.append(min < 10 ? "0" + min : min);
			stringBuffer.append(second < 10 ? "0" + second : second);
		}
		return Long.valueOf(stringBuffer.toString());
	}
	
	public static String getRyfDateHandler(int sys_date,int sys_time){
		StringBuffer stringBuffer = new StringBuffer();
		String yyyymmdd = String.valueOf(sys_date);
		String hhmmss = "";
		if(sys_time != 0){
			hhmmss = " "+getStringTime(sys_time);
		}
		stringBuffer.append(yyyymmdd.substring(0, 4));
		stringBuffer.append("-");
		stringBuffer.append(yyyymmdd.substring(4, 6));
		stringBuffer.append("-");
		stringBuffer.append(yyyymmdd.substring(6, 8));
		stringBuffer.append(hhmmss);
		return stringBuffer.toString();
	}
	
	/**
	 * date日期相减
	 * @param date ：日期
	 * @param pattern ：日期格式
	 * @param subInt ：相减天数
	 * @return String
	 */
	public static String getSubDate(String date,String pattern,int subInt){
		String subDate = null;
		try {
			Date t_date = DYDataUtil.getSimpleDateFormat(pattern).parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(t_date);
			calendar.add(Calendar.DATE, -subInt);
			subDate = DYDataUtil.getSimpleDateFormat(pattern).format(calendar.getTime());
		} catch (Exception e) {
			log.error(e);
		}
		return subDate;
	}
	
	/**
	 * 获取当前系统时间
	 * @return
	 */
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat sdf = getSimpleDateFormat(DYDataUtil.DATE_FORMAT_8);
		return sdf.format(date);
	}
	
	/**
	 * 获取俩个日期相差天数(bdate - smdate)
	 * @param smdate : 小日期
	 * @param bdate ：大日期
	 * @return int
	 * @throws ParseException
	 */
    public static int daysBetween(Object smdate,Object bdate,String pattern) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate.toString()));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate.toString()));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));  
    }
	
    /**
	 * 线上交易-获取小时
	 * @param sysTime
	 * @return int
	 */
	public static int getHour(int sysTime){
		int hour = (sysTime % 86400) / 3600;
		return hour;
	}
	
	/**
	 * 线上交易-获取分钟
	 * @param sysTime
	 * @return int
	 */
	public static int getMin(int sysTime){
		int min = (sysTime % 3600) / 60;
		return min;
	}
	
	/**
	 * 线上交易-获取秒钟
	 * @param sysTime
	 * @return int
	 */
	public static int getSecond(int sysTime){
		int second = sysTime % 60;
		return second;
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss 格式字符串转换为 yyyyMMddHHmmss 格式
	 * @param date
	 * @return
	 */
	public static String getyyyyMMddHHmmssTypeTime(String date){
		if(StringUtils.isNotBlank(date)){
			return date.replace(" ", "").replace("-", "").replace(":", "");
		}
		return null;
	}
	
	/**
	 * 将yyyy-M-d 的格式数据 转换成 yyyyMMdd 格式字符串
	 * @param date yyyy-M-d 的格式数据
	 * @return
	 */
	public static String parseTimeToyyyyMMddAnother(String date){
		String result = "";
		if(StringUtils.isNotBlank(date)){
			if(StringUtils.isNotBlank(date)){
				String[] contents = date.split("-");
				if(contents != null && contents.length > 2){
					result = contents[0] + StringUtils.leftPad(contents[1], 2, "0") + StringUtils.leftPad(contents[2], 2, "0");
				}
			}
		}
		return result;
	}
	
	/**
	 * 将yyyy-M-d HH:mm:ss 的格式数据转换成yyyyMMddHHmmss格式字符串
	 * @param date yyyy-M-d HH:mm:ss 的格式数据
	 * @return
	 */
	public static String parseTimeToyyyyMMddHHmmss(String date){
		String result = "";
		if(StringUtils.isNotBlank(date)){
			if(date.contains(" ")){
				String yMd = date.split(" ")[0];
				String hms = date.split(" ")[1];
				if(StringUtils.isNotBlank(yMd)){
					String[] contents = yMd.split("-");
					if(contents != null && contents.length > 2){
						result = contents[0] + StringUtils.leftPad(contents[1], 2, "0") + StringUtils.leftPad(contents[2], 2, "0") + hms.replaceAll(":", "");
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 将yyyy-M-d HH:mm:ss 的格式数据 转换成 yyyyMMdd 格式字符串
	 * @param date yyyy-M-d HH:mm:ss 的格式数据
	 * @return
	 */
	public static String parseTimeToyyyyMMdd(String date){
		String result = "";
		if(StringUtils.isNotBlank(date)){
			if(date.contains(" ")){
				date = date.split(" ")[0];
				if(StringUtils.isNotBlank(date)){
					String[] contents = date.split("-");
					if(contents != null && contents.length > 2){
						result = contents[0] + StringUtils.leftPad(contents[1], 2, "0") + StringUtils.leftPad(contents[2], 2, "0");
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * @param originalReqTime
	 * @return yyyy-MM-dd
	 */
	public static String getRYTNextDateString(String originalReqTime){
		SimpleDateFormat dateFormat = getSimpleDateFormat(DATE_FORMAT_3);
		String date = null;
		try {
			Date originalDate = dateFormat.parse(originalReqTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(originalDate);
			int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
			calendar.set(Calendar.DAY_OF_MONTH, day);
			date = dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			log.info(e);
		}
		
		return date;
	}
	
	/**
	 * @param originalReqTime
	 * @return yyyyMMdd
	 */
	public static String getRYTDateString(String originalReqTime){
		SimpleDateFormat dateFormat = getSimpleDateFormat(DATE_FORMAT_3);
		String date = null;
		try {
			Date originalDate = dateFormat.parse(originalReqTime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(originalDate);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			date = dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			log.info(e);
		}
		
		return date;
	}
    
	public static void main(String[] args) {
		try {
			System.out.println(getSimpleDateFormat(DYDataUtil.DATE_FORMAT_1).parse("2015-3-3  6:51:19"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(getStringTimeyyyyMMddHHmmss("20150716", "88888"));
	}
}
