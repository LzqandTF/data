package com.chinaebi.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

/**
 * @Description: 
 * @Author feng.ji
 * @Date 2011-10-26 下午12:22:00
 */
public class NumberUtils {

    /**
     * @param beforeTime 之前时间"yyyy-MM-dd"
     * @param afterTime  之后时间"yyyy-MM-dd"
     *                   比较两个时间相差的天数
     */
    public static long getCompareDate(String beforeTime, String afterTime) {
        String[] strDate = { beforeTime, afterTime };
        int len = strDate.length;
        int[] iYear = new int[len];
        int[] iMonth = new int[len];
        int[] iDay = new int[len];
        String[] year = new String[len];
        String[] month = new String[len];
        String[] day = new String[len];
        Calendar[] cale = new Calendar[len];
        for (int i = 0; i < len; i++) {
            year[i] = StringUtils.split(strDate[i], "-")[0];
            month[i] = StringUtils.split(strDate[i], "-")[1];
            day[i] = StringUtils.split(strDate[i], "-")[2];

            iYear[i] = Integer.parseInt(year[i]);
            iMonth[i] = Integer.parseInt(month[i]) - 1;
            iDay[i] = Integer.parseInt(day[i]);

            cale[i] = Calendar.getInstance();
            cale[i].clear();
            cale[i].set(iYear[i], iMonth[i], iDay[i]);
        }
        long mili1 = cale[0].getTime().getTime();
        long mili2 = cale[1].getTime().getTime();
        long result = (mili2 - mili1) / (24 * 60 * 60 * 1000);
        return result;
    }

    /**
     * 计算数字百分比
     *
     * @paramsum 数字总和
     * @paramcount 要比较的数字
     * @paramcount 精确度
     */
    public static String numPercent(double sum, int count, int accuracy) {

        BigDecimal b1 = new BigDecimal(Double.toString(count));
        BigDecimal b2 = new BigDecimal(Double.toString(sum));
        double result;
        if (b2.intValue() != 0)
            result = b1.divide(b2, accuracy, BigDecimal.ROUND_HALF_UP).doubleValue();
        else
            result = 0;
        NumberFormat ft = new DecimalFormat("#,##0.0");
        StringBuilder percent = new StringBuilder();
        percent.append(ft.format(result * 100));
        percent.append("%");
        return percent.toString();

    }

    /**
     * 格式化float 取小数点后几位
     *
     * @param num
     * @param count 取小数点后几位
     * @return
     */
    public static String numberFloatFormat(float num, int count) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(count);
        return numberFormat.format(num);
    }

    /**
     * 格式化货币100倍数的金额（例如6650），取小数点后两位（66.50），从数据库中取出、页面展示用
     *
     * @param num 货币100倍数的金额
     * @return
     */
    public static String numberMoneyFormat(Integer num) {
        String returnValue = "";
        if (num != null) {
            NumberFormat numberFormat = new DecimalFormat("#0.00");
            Double d = num * 0.01;
            returnValue = numberFormat.format(d);
        }
        return returnValue;
    }

    public static String numberMoneyFormat(Integer num, Float rate) {
        if (rate == null)
            rate = new Float(0);
        return numberMoneyFormat(roundMoney(num * rate));
    }

    /**
     * 价钱取整
     *
     * @param num
     * @return
     */
    public static Integer roundMoney(Float num) {
        if (num == null)
            return 0;
        return Math.round(num);
    }

    /**
     * 格式化货币100倍数的金额（例如2147483577），取小数点后两位（21474835.77）<br>
     * 主要用于几个金额合计的页面展示用（DB中现在都为Integer类型，相加后可能会超过它的范围，所以增至为Long类型）
     *
     * @param num 货币100倍数的金额
     * @return
     */
    public static String numberMoneyFormat(Long num) {
        String returnValue = "";
        if (num != null) {
            NumberFormat numberFormat = new DecimalFormat("#0.00");
            Double d = num * 0.01;
            returnValue = numberFormat.format(d);
        }
        return returnValue;
    }

    /**
     * 格式化成100倍数的货币金额，从页面输入、保存到数据库
     *
     * @param num 货币的金额
     * @return
     */
    public static Integer getIntegerMoney(Double num) {
        Integer returnValue = 0;
        if (num != null) {
            NumberFormat numberFormat = new DecimalFormat("##");
            String s = numberFormat.format(num * 100);
            returnValue = Integer.valueOf(s);
        }
        return returnValue;
    }

    /**
     * 返回格式化重量 0.21
     *
     * @param weight
     * @return 保留两位小数的重量
     */
    public static String getWeight(float weight) {
        String returnValue = "0";
        if (weight != 0f) {
            NumberFormat numberFormat = new DecimalFormat("#0.00");
            returnValue = numberFormat.format(weight);
        }
        return returnValue;
    }

    /**
     * 格式化折扣率
     *
     * @param discountRate
     * @return
     */
    public static String discountRateFormat(float discountRate) {
        NumberFormat numberFormat = new DecimalFormat("#0.0");
        return numberFormat.format(discountRate);
    }

    /**
     * 相除 精确到整数 12.5 = 13
     * @param price
     * @param tmp
     * @return
     */
    public static Integer getIntegerFormat(Integer price, Integer tmp) {
        Integer returnValue = 0;
        if (price != null && tmp != null && tmp != 0) {
            returnValue = (int) Math.ceil(price * 0.01 / tmp);
        }
        return returnValue;
    }

    /**
     * 返回格式化字符串 float保留一位小数
     */
    public static String getFloatFormat(float input) {
        NumberFormat numberFormat = new DecimalFormat("#0.0");
        return numberFormat.format(input);
    }

    /**
     * 相除 精确到整数 12.5 = 13
     * @param price
     * @param tmp
     * @return
     */
    public static Integer getIntegerFormat(Integer price, Float tmp) {
        Integer returnValue = 0;
        if (price != null && tmp != null && tmp != 0) {
            NumberFormat numberFormat = new DecimalFormat("#0");
            returnValue = Integer.parseInt(numberFormat.format(price * 0.01 / tmp));
        }
        return returnValue;
    }

    /**
     * 返回小数点后2位
     * @param price
     * @param tmp 积分数
     * @return
     */
    public static String getStringFormat(Integer price, Integer tmp) {
        return getStringFormat(price, tmp, "#0.0000");
    }

    public static String getStringFormat(Integer price, Integer tmp, String pattern) {
        NumberFormat numberFormat = new DecimalFormat(pattern);
        return numberFormat.format(price * 0.01 / tmp);
    }

    /**
     * 页面获取金额，输入到数据库
     * 
     * @param price
     * @return
     */
    public static Integer getPrice(String price) {
        if (StringUtils.isBlank(price))
            price = "0";
        return getIntegerMoney(Double.valueOf(price));
    }

    /**
     * 处理空金额
     * 
     * @param amount
     */
    public static String handleValueNull(Integer amount) {
        String str = String.valueOf(amount);
        return str == null || str.trim().length() == 0 || "null".equals(str) ? "" : str;
    }

    /**
     * 格式化金额，下载展示
     * 
     * @param amount
     */
    public static String handlePrice(Integer num) {
        return handleValueNull(num) == "" ? "0.00" : numberMoneyFormat(num);
    }

    /**
    * 格式化合计金额，下载展示
    * 
    * @param amount
    */
    public static String handlePriceAll(double sumPrice) {
        return new DecimalFormat("0.00").format(sumPrice);
    }

}
