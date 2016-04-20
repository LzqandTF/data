package com.chinaebi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 手续费计算
 * @author zhu.hongliang
 *
 */
public class PoundageCalculate {
	
	private static Log log = LogFactory.getLog(PoundageCalculate.class);
	
	private static final int two = 2;
	private static final int three = 3;
	private static final int four = 4;
	
	/**
	 * 俩个数相除取得倍数、不要余数
	 * @return int
	 */
	 public static int div(String num1, String num2) {
		 BigDecimal bd1 = new BigDecimal(num1);
		 BigDecimal bd2 = new BigDecimal(num2);
		 return bd1.divide(bd2).intValue()/100;
	 }
	 
	/**
	 * 字符串忽略大小写替换
	 * 特殊字符
	 * getReplaceAll(merPoundage, "amt\\*", "")
	 * @param merPoundage ：字符串
	 * @param replaceValue ：被替换的值
	 * @param character ：替换成的数值
	 * @return
	 */
	private static String getReplaceAll(String merPoundage,String replaceValue,String character){
		if(StringUtils.isNotBlank(merPoundage)){
			merPoundage = merPoundage.replaceAll("(?i)"+replaceValue, character);
		}
		return merPoundage;
	}
	
	/**
	 * 获取()里面的数值
	 * @param merPoundage
	 * @return String
	 */
	private static String getDataVlue(String merPoundage){
		int start = merPoundage.indexOf("(")+1;
		int end = merPoundage.indexOf(")");
		return merPoundage.substring(start, end);
	}
	
	/**
	 * 保留decimalPlaces位小数点
	 * @param value ：值
	 * @param decimalPlaces ：小数点位数
	 * @return String
	 */
	public static String roundString(float value, int decimalPlaces) 
	{    
		NumberFormat nf = NumberFormat.getNumberInstance();    
		nf.setRoundingMode(RoundingMode.HALF_UP); 
		// 四舍五入    
		nf.setMaximumFractionDigits(decimalPlaces);    
		nf.setMinimumFractionDigits(decimalPlaces);    
		return nf.format(value).replaceAll(",", "");
	}
	
	/**
	   * 提供精确的乘法运算,结果除以100
	   * @param v1
	   * @param v2
	   * @return 两个参数的数学积，以字符串格式返回
	   */
	public static float multiplyParse(String v1, String v2){
	    BigDecimal b1 = new BigDecimal(v1.trim());
	    BigDecimal b2 = new BigDecimal(v2.trim());
	    return (b1.multiply(b2).floatValue())/100;
	}
	
	/**
	 * 提供精确的乘法运算
	 * @param v1
	 * @param v2
	 * @return 两个参数的数学积，以字符串格式返回
	 */
	public static float multiply(String v1, String v2){
		BigDecimal b1 = new BigDecimal(v1.trim());
		BigDecimal b2 = new BigDecimal(v2.trim());
		return (b1.multiply(b2).floatValue());
	}
	
	/**
	 * 指定小数点位数、指定四舍五入值,不保留小数点最后一位0
	 * @param value : 数值 String
	 * @param scale : 设置小数点位数  
	 * @param roundingMode : 表示四舍五入，可以选择其他舍值方式，例如去尾，等等
	 * @return float
	 */
	public static BigDecimal roundFloat_roundingMode(String value,int scale,int roundingMode){
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale,roundingMode);  
		return bd;  
	}
	/**
	 * 指定小数点位数、指定四舍五入值,不保留小数点最后一位0
	 * @param value : 数值 String
	 * @param scale : 设置小数点位数  
	 * @param roundingMode : 表示四舍五入，可以选择其他舍值方式，例如去尾，等等
	 * @return String
	 */
	public static BigDecimal roundFloat_roundingMode(float value,int scale,int roundingMode){
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale,roundingMode);  
		return bd;  
	}
	
	/**
	 * 保留指定位数小数点-不四舍五入
	 * @param number ：字符串金额
	 * @param precision ：位数
	 * @return String
	 * 
	 * setScale(1)表示保留一位小数，默认用四舍五入方式
	 * setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3
	 * setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4
	 * setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
	 * setScaler(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
	 */
	public static String keepPrecision(String number, int precision) {  
        BigDecimal bg = new BigDecimal(number);  
        return bg.setScale(precision, BigDecimal.ROUND_DOWN).toPlainString();  
    }
	
	/**
	 * 进行加法运算
	 * @param d1 : String
	 * @param d2 : String
	 * @return
	 */
	public static String add(String d1, String d2){
         BigDecimal b1 = new BigDecimal(d1);
         BigDecimal b2 = new BigDecimal(d2);
         return b1.add(b2).toString();
    }
	
	/**
	 * 进行减法运算
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String sub(String d1, String d2){        
	     BigDecimal b1 = new BigDecimal(d1);
	     BigDecimal b2 = new BigDecimal(d2);
	     return b1.subtract(b2).toString();
	}
	
	
	/**
	 * 进行加法运算
	 * @param d1 : Object
	 * @param d2 : Object
	 * @return BigDecimal
	 */
	public static BigDecimal add(Object d1, Object d2){
         BigDecimal b1 = new BigDecimal(d1.toString());
         BigDecimal b2 = new BigDecimal(d2.toString());
         return b1.add(b2).setScale(PoundageCalculate.two, BigDecimal.ROUND_DOWN);
    }
	
	/**
	 * 进行减法运算
	 * @param d1 : Object
	 * @param d2 : Object
	 * @return BigDecimal
	 */
	public static BigDecimal sub(Object d1, Object d2){        
	     BigDecimal b1 = new BigDecimal(d1.toString());
	     BigDecimal b2 = new BigDecimal(d2.toString());
	     return b1.subtract(b2).setScale(PoundageCalculate.two, BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * 进行乘法运算
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String mul(String d1, String d2){        
	     BigDecimal b1 = new BigDecimal(d1);
	     BigDecimal b2 = new BigDecimal(d2);
	     return b1.multiply(b2).toString();
	}
	
	/**
	 * 进行除法运算
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String division(String d1, String d2){        
	     BigDecimal b1 = new BigDecimal(d1);
	     BigDecimal b2 = new BigDecimal(d2);
	     return b1.divide(b2).toString();
	}
	
	/**
	 * 提供精确的乘法-返回按分计算
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal multiply_minute(String v1,String v2){
	    BigDecimal b1 = new BigDecimal(v1.trim());
	    BigDecimal b2 = new BigDecimal(v2.trim());
	    return b1.multiply(b2);
	}
	
	public static void main(String[] args) {
		
		System.out.println(add(333f, 4.309f).floatValue());
		
//		System.out.println(roundFloat(100f, 2, 2).floatValue());
//		String merPoundage = "BKFEE(0.1,100,0.008,2)";
//		String merPoundage = "AMT*0.008";
//		String merPoundage = "MAX(0.2,AMT*0.005)";
//		String merPoundage = "MIN(50,AMT*0.005)";
//		String merPoundage = "SGL(2.5)";
//		String merPoundage = "FLO(500,0.08,5000,0.06,50000,0.05,500000,0.03,0.01)";
//		String merPoundage = "FIX(20000,2,50000,3,100000,5,10)";
//		String merPoundage = "INC(2.5,20000)";
//		String merPoundage = "IFBIG(5000, AMT*0.08)";
//		String merPoundage = "MAX(0.2,AMT*0.005)";
//		String tradeAmount = "00000100000";
//		System.out.println(getMerFee(tradeAmount, merPoundage));
//		System.out.println(multiply("0","1"));
	}
}
