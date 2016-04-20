package cn.com.chinaebi.dz.object.util;

import java.math.BigDecimal;
import java.util.StringTokenizer;
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
		int count = 0;
		if(StringUtils.isNotBlank(name_)){
			name_ = name_.replaceAll("/", "");
			String regexZm="([a-zA-Z0-9(),;.]+)";
			name_ = name_.replaceAll(regexZm, "");
			String regex="([\u4e00-\u9fa5]+)";
			Matcher matchera = Pattern.compile(regex).matcher(name_);
			if(matchera.find()){
				String hz = matchera.group(0);
				count = hz.length();
			}
		}
		return count;
	}
	
	/**
	 * 替换中文括号、中文逗号、中文句号、中文分号
	 * @param name_
	 * @return
	 */
	public static String replaseZwZf(String name_){
		if(StringUtils.isNotBlank(name_)){
			String regex="([（），。；]+)";
			Matcher matchera = Pattern.compile(regex).matcher(name_);
			if(matchera.find()){
				name_ = name_.replaceAll("（", "(");
				name_ = name_.replaceAll("）", ")");
				name_ = name_.replaceAll("，", ",");
				name_ = name_.replaceAll("。", ".");
				name_ = name_.replaceAll("；", ";");
			}
			return name_;
		}else
			return "";
	}
	
	public static int statistiNumber(String name_){
		int count = 0;
		if(StringUtils.isNotBlank(name_)){
			String regex="([^\u4e00-\u9fa5]+)";
			Matcher matchera = Pattern.compile(regex).matcher(name_);
			if(matchera.find()){
				String hz = matchera.group(0);
				count = hz.length();
			}
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
    	String str = "(盛京慧生活AAa)09876（。，；收款。，）";
    	
    	
    	StringTokenizer ss = new StringTokenizer(str, "（");
    	System.out.println(ss.hasMoreTokens());
    	
    	int count = 0;
    	String regex="([（），。；]+)";
		Matcher matchera = Pattern.compile(regex).matcher(str);
		if(matchera.find()){
			StringTokenizer ykStr = new StringTokenizer(str, "（");
			int yk = ykStr.countTokens();
			count+= yk == 1 ? yk : yk - 1;
			StringTokenizer zkStr = new StringTokenizer(str, "）");
			int zk = zkStr.countTokens();
			count+= zk == 1 ? zk : zk - 1;
			StringTokenizer jhStr = new StringTokenizer(str, "。");
			int jh = jhStr.countTokens();
			count+= jh == 1 ? jh : jh - 1;
			StringTokenizer dhStr = new StringTokenizer(str, "，");
			int dh = dhStr.countTokens();
			count+= dh == 1 ? dh : dh - 1;
			StringTokenizer fhStr = new StringTokenizer(str, "；");
			int fh = fhStr.countTokens();
			count+= dh == 1 ? fh : fh - 1;
			count += statisticalChineseNumber(replaseZwZf(str));
			System.out.println("总共中文长度 : "+count);
		}else{
			count += statisticalChineseNumber(str);
			System.out.println("总共中文长度 : "+count);
		}
		System.out.println(statisticalChineseNumber("你好ABC098你好"));
    	/*
    	int count = 0;
    	String regex="([（），。；]+)";
		Matcher matchera = Pattern.compile(regex).matcher(str);
		if(matchera.find()){
			int yk = str.split("（").length;
			count+= yk == 1 ? yk : yk - 1;
			int zk = str.split("）").length;
			count+= zk == 1 ? zk : zk - 1;
			int jh = str.split("。").length;
			count+= jh == 1 ? jh : jh - 1;
			int dh = str.split("，").length;
			count+= dh == 1 ? dh : dh - 1;
			int fh = str.split("；").length;
			count+= dh == 1 ? fh : fh - 1;
			count += statisticalChineseNumber(replaseZwZf(str));
			System.out.println("总共中文长度 : "+count);
		}else{
			count += statisticalChineseNumber(str);
			System.out.println("总共中文长度 : "+count);
		}*/
	}
}
