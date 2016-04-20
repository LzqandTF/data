package com.chinaebi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;


public class DateUtil {

    private static DateUtil classInstance = new DateUtil();

    public static final int DATE_DAYS     = 0;
    public static final int DATE_HOURS    = 1;
    public static final int DATE_MINUTES  = 2;
    public static final int DATE_SECONDS  = 3;
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";  
	public static final String DATE_FORMAT_2 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_3 = "yyyyMMdd";
	public static final String DATE_FORMAT_4 = "HHmmss";
	public static final String DATE_FORMAT_5 = "HH";
	public static final String DATE_FORMAT_6 = "yyyy-MM-dd";
	public static final String DATE_FORMAT_7 = "yyyyMMddHHmm";
    
    private static Map<String, SimpleDateFormat> mapThreadLocal = new HashMap<String, SimpleDateFormat>();
    public static SimpleDateFormat getSimpleDateFormat(String pattern){
		SimpleDateFormat df = mapThreadLocal.get(pattern);  
		if (df == null) {  
            df = new SimpleDateFormat(pattern);  
            mapThreadLocal.put(pattern, df);  
        }
        return df; 
	}

    public static DateUtil getInstance() {
        return classInstance;
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }

        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm";
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static String defaultFormat(Date date) {
        return formatDate(date, null);
    }

    public static String defaultFormatYMD(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static Date parseDateFormat() {
        SimpleDateFormat fo = new SimpleDateFormat();
        Date date = new java.util.Date(System.currentTimeMillis());
        fo.applyPattern("yyyy-MM-dd");

        try {
            date = fo.parse(DateFormatUtils.format(date, "yyyy-MM-dd"));
        } catch (Exception e) {
        }

        return date;
    }

    public static Date parseDateFormat(String dateString, String pattern) {
        SimpleDateFormat fo = new SimpleDateFormat();
        fo.applyPattern(pattern);

        try {
            return fo.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据类型返回日期格式
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate2String(Date date, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm";
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static Date string2Date(String str) {
        if (StringUtils.isEmpty(str))
            return new Date();
        return java.sql.Date.valueOf(str);
    }

    /**
     * 字符串格式时间转换到对象时间
     * 
     * @param str
     * @return
     */
    public static Date string2DateTime(String str) {
        if (StringUtils.isEmpty(str))
            return new Date();
        SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        try {
            date = fo.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串格式时间转换到对象时间,时分秒
     * 
     * @param str
     * @return
     */
    public static Date stringDateTime(String str) {
        if (StringUtils.isEmpty(str))
            return new Date();
        SimpleDateFormat fo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = fo.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 返回星期几
     * 
     * @param date
     * @return
     */
    public static String getWeekDayString(Date date) {
        String weekString = "";
        final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            date = new Date();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        weekString = dayNames[dayOfWeek - 1];
        return weekString;

    }

    public static boolean between(Date srcDate, Date startDate, Date endDate) {
        if (startDate.after(srcDate))
            return false;
        if (endDate.before(srcDate))
            return false;
        return true;
    }

    /**
     * 返回两时间比较
     * 
     * @param startDate
     * @param endDate
     * @return true:开始时间小于结束时间 false:开始时间大于结束时间
     */
    public static boolean between(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) <= 0)
            return true;
        return false;
    }

    /**
     * 返回createDate+2天是否在当天之前
     * 
     * @param createDate
     *            创建时间
     * @return true:当前时间小于创建时间 false:当前时间大于创建时间
     */
    public static boolean isBeforeNow(Date createDate) {
        return between(new Date(), addDays(createDate, 2));
    }

    /**
     * 获得剩余时间
     * 
     * @param overTime
     *            过期时间
     * @return 剩余时间
     */
    public static String marginTimeFormat(Date overTime) {
        String marginTimeFormat = "";
        // 过期日期
        Calendar overDate = Calendar.getInstance();
        overDate.setTime(overTime);
        // 现在的日期
        Calendar nowDate = Calendar.getInstance();
        // 到现在一共的剩余多少秒
        long seconds = (overDate.getTime().getTime() - nowDate.getTime().getTime()) / 1000;
        if (seconds <= 0)
            return "过期";
        // 1(day)*24(hour)*60(minite)*60(seconds)
        // 天
        long dayMargin = seconds / 86400;
        // 小时
        seconds = seconds - (dayMargin * 86400);
        long hourMargin = seconds / 3600;
        // 分钟
        seconds = seconds - (hourMargin * 3600);
        long minuteMargin = seconds / 60;
        // 秒
        seconds = seconds - (minuteMargin * 60);
        // long secondMargin = seconds;

        // 双位显示,如: 01小时02分01秒
        // hstr = String.valueOf(hourMargin);
        // mstr = String.valueOf(minuteMargin);
        // sstr = String.valueOf(secondMargin);
        // hstr = String.valueOf(hourMargin).length() < 2 ? ("0" + hstr) : hstr;
        // mstr = String.valueOf(minuteMargin).length() < 2 ? ("0" + mstr) :
        // mstr;
        // sstr = String.valueOf(secondMargin).length() < 2 ? ("0" + sstr) :
        // sstr;

        if (dayMargin > 0) {
            marginTimeFormat = dayMargin + "天";
            return marginTimeFormat;
        } else if (hourMargin > 0) {
            marginTimeFormat = hourMargin + "小时";
            return marginTimeFormat;
        } else if (minuteMargin > 0) {
            marginTimeFormat = minuteMargin + "分钟";
            return marginTimeFormat;
        } else {
            marginTimeFormat = 1 + "分钟";
            return marginTimeFormat;
        }
    }

    /**
     * 获得剩余时间
     * 
     * @param overTime
     *            过期时间
     * @return 剩余时间
     */
    public static String marginTimeFormat(Date overTime, int pattern) {
        StringBuilder result = new StringBuilder();
        // 过期日期
        Calendar overDate = Calendar.getInstance();
        overDate.setTime(overTime);
        // 现在的日期
        Calendar nowDate = Calendar.getInstance();
        // 到现在一共的剩余多少秒
        long seconds = (overDate.getTime().getTime() - nowDate.getTime().getTime()) / 1000;

        // 天
        long dayMargin = seconds / 86400;
        // 小时
        seconds = seconds - (dayMargin * 86400);
        long hourMargin = seconds / 3600;
        // 分钟
        seconds = seconds - (hourMargin * 3600);
        long minuteMargin = seconds / 60;
        // 秒
        seconds = seconds - (minuteMargin * 60);
        long secondMargin = seconds;
        switch (pattern) {
            case DATE_SECONDS:
                result.append(concat2(dayMargin, "天")).append(concat2(hourMargin, "小时")).append(concat2(minuteMargin, "分钟"))
                    .append(concat2(secondMargin, "秒"));
                break;
            case DATE_MINUTES:
                result.append(concat2(dayMargin, "天")).append(concat2(hourMargin, "小时")).append(concat2(minuteMargin, "分钟"));
                break;
            case DATE_HOURS:
                result.append(concat2(dayMargin, "天")).append(concat2(hourMargin, "小时"));
                break;
            case DATE_DAYS:
            default:
                result.append(concat2(dayMargin, "天"));
        }
        return result.toString();
    }

    /**
     * 双位显示,如: 01小时
     * 
     * @param time
     * @param pattern
     * @return String
     */
    private static String concat2(long time, String pattern) {
        String timeStr = String.valueOf(time);
        return timeStr.length() >= 2 ? (timeStr + pattern) : ("0" + timeStr + pattern);
    }

    /**
     * 根据当前时间增加小时分
     * 
     * @param date
     * @return
     */
    public static Date addDateHM(Date date) {
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        return calendar.getTime();
    }

    /**
     * 增加修改小时 添加的时间为 h 分钟为当前时间
     * 
     * @param date
     * @param h
     * @return
     */
    public static Date addDateHMMinus1(Date date, int h) {
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        return calendar.getTime();
    }

    /**
     * 增加修改小时 主要适用拍卖
     * 
     * @param date
     * @param h
     * @return
     */
    public static Date addDateHM(Date date, int h) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }

    /**
     * 增加天
     * 
     * @param date
     * @param i
     * @return
     */
    public static Date addDays(Date date, int i) {
        return org.apache.commons.lang.time.DateUtils.addDays(date, i);
    }

    /**
     * 增加月
     * 
     * @param date
     * @param i
     * @return
     */
    public static Date addMonths(Date date, int i) {
        return org.apache.commons.lang.time.DateUtils.addMonths(date, i);
    }

    /**
     * 增加年
     * 
     * @param date
     * @param i
     * @return
     */
    public static Date addYears(Date date, int i) {
        return org.apache.commons.lang.time.DateUtils.addYears(date, i);
    }

    /**
     * 增加小时
     * 
     * @param date
     * @param h
     * @return
     */
    public static Date addHours(Date date, int h) {
        return org.apache.commons.lang.time.DateUtils.addHours(date, h);
    }

    /**
     * 由于用24小时 返回时间+1
     * 
     * @param date
     * @return
     */
    public static int getHoursAdd1(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY) + 1;
    }

    /**
     * 返回小时
     * 
     * @param date
     * @return
     */
    public static int getHours(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回当天时间 只到天 2008-05-20 00:00
     * 
     * @return
     */
    public static Date getNowDate() {
        return org.apache.commons.lang.time.DateUtils.truncate(new Date(), Calendar.DATE);
    }

    /**
     * 返回date时间 只到天 2008-05-20 00:00
     * 
     * @return
     */
    public static Date getNoHHMMDate(Date date) {
        return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DATE);
    }

    /**
     * 有效时间为某天的最后时刻
     * 
     * @param date
     *            选择的时间
     * @return Date
     */
    public static Date getOverTime(Date date) {
        Date time = addDateHM(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();

    }

    /**
     * 返回 分钟
     * 
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 时间是否相等 true:相等 false:不等
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean equalsDate(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) == 0)
            return true;
        return false;
    }

    /**
     * 是否周末
     * 
     * @param day
     * @return
     */
    public static boolean isWeekend(Date day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        int tmp = cal.get(Calendar.DAY_OF_WEEK);
        return (Calendar.SATURDAY == tmp || Calendar.SUNDAY == tmp) ? true : false;
    }

    /**
     * 获取日期差
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getSubDays(Date startTime, Date endTime) {
        int day = 0;
        day = (int) ((endTime.getTime() - startTime.getTime()) / 86400000); // 24
                                                                            // *
                                                                            // 60
                                                                            // *
                                                                            // 60
                                                                            // *
                                                                            // 1000
        return day;
    }

    /**
     * 判断时间onlineTime是否在过去days天以内
     * 
     * @param date
     * @param days
     * @return
     */
    public static boolean isWithin(Date date, int days) {
        if (null == date) {
            return false;
        }
        return addDays(date, days).after(new Date());
    }
    
    public static String parseToNormalTime(String utcSeconds){
    	if(StringUtils.isBlank(utcSeconds)){
    		return null;
    	}
    	return getStringTime(Integer.valueOf(utcSeconds));
    }
    /**
     * 将UTC-second类型时间转化为hh:mm:ss格式
     * @return
     */
    public static String getStringTime(int nowtime) {
	    int hour = (nowtime % 86400) / 3600;
	    int min = (nowtime % 3600) / 60;
	    int second = nowtime % 60;
	    StringBuffer buffer = new StringBuffer();
	    if(hour > 9){
	    	buffer.append(hour);
	    }else{
	    	buffer.append("0");
	    	buffer.append(hour);
	    }
	    buffer.append(":");
	    buffer.append(min < 10 ? "0" + min : min);
	    buffer.append(":");
	    buffer.append(second < 10 ? "0" + second : second);
	    return buffer.toString();
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
    
    public static String parseTimePattern(String date,String time){
    	String date_time = "";
    	if(StringUtils.isNotBlank(date) && StringUtils.isNotBlank(time)){
    		date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    		time = parseToNormalTime(time);
    		date_time = date+" "+time;
    	}
    	return date_time;
    }
    
    public static String parseDatePattern(String date){
    	String date_ = "";
    	if (null != date && !"".equals(date) && !"null".equals(date) && date.length() >= 8) {
    		date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    		date_ = date;
    	}
    	return date_;
    }
    
    /**
     * 获取上周星期一日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getLastWeekMonday(String date) throws ParseException{
    	Calendar calendar = Calendar.getInstance();
    	int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1; 
		int offset=1-dayOfWeek; 
		calendar.add(Calendar.DATE, offset-7); 
		return getSimpleDateFormat(DATE_FORMAT_6).format(calendar.getTime());
    }
    
    /**
     * 获取上周星期日日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getLastWeekSunday(String date) throws ParseException{
    	Calendar calendar = Calendar.getInstance();
    	int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1; 
    	int offset=7-dayOfWeek; 
    	calendar.add(Calendar.DATE, offset-7); 
    	return getSimpleDateFormat(DATE_FORMAT_6).format(calendar.getTime());
    }
  
    /** 
     * 获取日期年份 
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static int getYear(String date) throws ParseException{  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(getSimpleDateFormat(DATE_FORMAT_6).parse(date));  
        return calendar.get(Calendar.YEAR);  
    }  
      
    /** 
     * 获取日期月份 
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static int getMonth(String date) throws ParseException{  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(getSimpleDateFormat(DATE_FORMAT_6).parse(date));  
        return (calendar.get(Calendar.MONTH) + 1);  
    }  
      
    /** 
     * 获取日期号 
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static int getDay(String date) throws ParseException{  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(getSimpleDateFormat(DATE_FORMAT_6).parse(date));  
        return calendar.get(Calendar.DAY_OF_MONTH);  
    }  
    /** 
     * 获取月份起始日期 
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static String getMinMonthDate(String date) throws ParseException{  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(getSimpleDateFormat(DATE_FORMAT_6).parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return getSimpleDateFormat(DATE_FORMAT_6).format(calendar.getTime());  
    }  
      
    /** 
     * 获取月份最后日期 
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static String getMaxMonthDate(String date) throws ParseException{  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(getSimpleDateFormat(DATE_FORMAT_6).parse(date));  
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return getSimpleDateFormat(DATE_FORMAT_6).format(calendar.getTime());  
    }
    
    /** 
     * 获取上一个月份起始日期 
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static String getMinLastMonthDate(String date) throws ParseException{  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(getSimpleDateFormat(DATE_FORMAT_6).parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
        calendar.add(Calendar.MONTH, -1);
        return getSimpleDateFormat(DATE_FORMAT_6).format(calendar.getTime());  
    }  
    
    /**
     * 获取前一天日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getLastDay(String date) throws ParseException{
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(getSimpleDateFormat(DATE_FORMAT_6).parse(date));
		calendar.add(Calendar.DATE, -1);
		return getSimpleDateFormat(DATE_FORMAT_6).format(calendar.getTime());
    }
    /**
     * 获取本周星期一日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getThisWeekMonday(String date) throws ParseException{
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(getSimpleDateFormat(DATE_FORMAT_6).parse(date));
    	int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1; 
    	
		int offset=1-dayOfWeek; 
		
		if(dayOfWeek == 0){
			calendar.add(Calendar.DATE, offset-7); 
    	}else{
    		calendar.add(Calendar.DATE, offset); 
    	}
		
		return getSimpleDateFormat(DATE_FORMAT_6).format(calendar.getTime());
    }
    
    /**
	 * 字符串yyyy-MM-dd 转日期yyyy-MM-dd :00:00:00
	 * @param dateStr : yyyy-MM-dd + 00:00:00
	 * @return yyyy-MM-dd HH:mm:ss 
	 */
	public static String getformatConversionStart(String dateStr){
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateStr);
		buffer.append(" 00:00:00");
		return buffer.toString();
	}
	
	/**
	 * 字符串yyyy-MM-dd 转日期yyyy-MM-dd :23:59:59
	 * @param dateStr : yyyy-MM-dd + 23:59:59
	 * @return yyyy-MM-dd HH:mm:ss 
	 */
	public static String getformatConversionEnd(String dateStr){
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateStr);
		buffer.append(" 23:59:59");
		return buffer.toString();
	}
	/**
	 * 字符串yyyy-MM-dd 转日期yyyy-MM-dd :00:00:00:000
	 * @param dateStr : yyyy-MM-dd + 00:00:00:000
	 * @return yyyy-MM-dd HH:mm:ss:sss 
	 */
	public static String getFormatOfMsDateConversionStart(String dateStr){
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateStr);
		buffer.append(" 00:00:00:000");
		return buffer.toString();
	}
	
	/**
	 * 字符串yyyy-MM-dd 转日期yyyy-MM-dd :23:59:59:999
	 * @param dateStr : yyyy-MM-dd + 23:59:59:999
	 * @return yyyy-MM-dd HH:mm:ss:sss 
	 */
	public static String getFormatOfMsDateConversionEnd(String dateStr){
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateStr);
		buffer.append(" 23:59:59:999");
		return buffer.toString();
	}
	
	/**
	 * 字符串yyyyMMdd 转日期yyyyMMdd000000
	 * @param dateStr : yyyyMMdd + 000000
	 * @return yyyyMMddHHmmss 
	 */
	public static String getformatyyyyMMddHHmmssStart(String dateStr){
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateStr);
		buffer.append("000000");
		return buffer.toString();
	}
	
	/**
	 * 字符串yyyyMMdd 转日期yyyyMMdd235959
	 * @param dateStr : yyyyMMdd + 235959
	 * @return yyyyMMddHHmmss 
	 */
	public static String getformatyyyyMMddHHmmssEnd(String dateStr){
		StringBuffer buffer = new StringBuffer();
		buffer.append(dateStr);
		buffer.append("235959");
		return buffer.toString();
	}
    
    public static void main(String[] args) throws ParseException {  
//    	Calendar calendar1 = Calendar.getInstance(); 
//		calendar1.setTime(parseDateFormat("2015-01-25", "yyyy-MM-dd"));
//		Calendar calendar2 = Calendar.getInstance(); 
//		int dayOfWeek=calendar1.get(Calendar.DAY_OF_WEEK)-1; 
//		int offset1=1-dayOfWeek; 
//		int offset2=7-dayOfWeek; 
//		calendar1.add(Calendar.DATE, offset1-7); 
//		calendar2.add(Calendar.DATE, offset2-7); 
//		System.out.println(calendar1.getTime());//last Monday 
//		System.out.println(calendar2.getTime());//last Sunday 
		System.out.println(getRyfDateHandler(20150302, 127063));
		
    }
}
