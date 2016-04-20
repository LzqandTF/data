package cn.com.chinaebi.dz.util;

import java.math.BigDecimal;

import cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO;



/**
 * 数据汇总操作
 * @author zhu.hongliang@chinaebi.com
 *
 */
public class DataCollect {
	
	private static DataCollect dataCollect = null;
	private TradeLstDAO tradeLstDAO = cn.com.chinaebi.dz.object.dao.TradeLstDAO.getInstance();
	
	public static DataCollect getInstance(){
		if(dataCollect == null) dataCollect = new DataCollect();
		return dataCollect;
	}
	
	/**
	 * 数据分割
	 * @param tradeTime : 交易时间
	 * @param tableName : 表名称 
	 * @param deduct_sys_id ：渠道ID
	 * @return boolean
	 */
	public boolean splitData(String tradeTime,String tableName,int deduct_sys_id,int bank_id){
		return tradeLstDAO.splitData(tradeTime, tableName,deduct_sys_id,bank_id);
	}
	
	/**
	 * 金额处理
	 * @param tradeTime : 交易时间
	 * @param tableName : 表名称 
	 * @param deduct_sys_id ：渠道ID
	 * @return boolean
	 */
	public boolean handleMoney(String tradeTime, String tableName,int deduct_sys_id){
		return tradeLstDAO.handleMoney(tradeTime, tableName,deduct_sys_id);
	}
	
	/**
	 * 
	 * @param tradeTime : 交易时间
	 * @param tableName : 表名称
	 * @param deduct_sys_id : 渠道ID
	 * @param bank_id ：银行网关
	 * @return int
	 */
	public int dataCollectHanler(String tradeTime,String tableName,int deduct_sys_id,int bank_id){
		return tradeLstDAO.dataCollectHanler(tradeTime, tableName, deduct_sys_id,bank_id);
	}
	
	/**
	 * 保留指定位数小数点-不四舍五入
	 * @param number ：字符串金额
	 * @param precision ：位数
	 * @return String
	 */
	public static String keepPrecision(String number, int precision) {  
        BigDecimal bg = new BigDecimal(number);  
        return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).toPlainString();  
    }  
	
	/**
	 * 数值相加，保留指定位数小数点-不四舍五入
	 * @param number ：字符串金额
	 * @param values ：float金额
	 * @param precision ：小数点位数
	 * @return String
	 */
	public static String addingValues(String number,float values,int precision){
		Float amount = Float.valueOf(number);
		float mer_balance = amount+values;
		return keepPrecision(String.valueOf(mer_balance), precision);
	}
}
