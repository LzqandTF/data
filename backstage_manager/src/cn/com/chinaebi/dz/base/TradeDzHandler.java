package cn.com.chinaebi.dz.base;

import cn.com.chinaebi.dz.base.exception.DuizhangNotFoundException;
import cn.com.chinaebi.dz.base.exception.OriginalDataNotFoundException;

/**
 * 对账处理类
 */
public interface TradeDzHandler {

	/**
	 * 线下渠道对账处理的方法
	 * @param originalPepDate  ： 原始数据查询时间
	 * @param duizhangReqTime ： 对账文件查询时间
	 * @param deductStlmDate ：日切 清算处理时间
	 * @param innertTradeHandler ：是否内部勾对处理
	 * @param bank_id : 银行网关
	 * @param instId : 机构ID
	 * @param instType : 渠道类型 0：线下、1：线上
	 */
	public void tradeDzDeal(String originalPepDate,String duizhangReqTime,String deductStlmDate,Boolean innertTradeHandler,
			Integer bank_id,Integer instId,Integer instType,Integer flagStatus)
	throws OriginalDataNotFoundException,DuizhangNotFoundException,Exception;
	

	/**
	 * 线上渠道对账处理的方法
	 * @param startDate  ： 原始交易数据开始日期 yyyyMMdd
	 * @param endDate ：原始交易数据结束日期 yyyyMMdd
	 * @param deductStlmDate ：日切 清算处理时间 yyyyMMdd
	 * @param innertTradeHandler ：是否内部勾对处理
	 * @param gate : 银行网关号
	 * @param instId : 机构ID(该机构ID=gid 渠道号)
	 * @param instType : 渠道类型 0:线下、1:线上
	 * @param dzDataTableName : 对账单表名称
	 */
	public void onLineTradeDzDeal(Integer startDate,
			Integer endDate,String deductStmlDate,
			Integer bank_id,Integer instId,Integer instType,Integer flagStatus)
	throws OriginalDataNotFoundException,DuizhangNotFoundException,Exception;
	
	/**
	 * 差错渠道对账处理的方法
	 * @param originalPepDate  ： 原始数据查询时间
	 * @param duizhangBeijingReqTime ： 对账文件查询时间
	 * @param oriObject : 电银对账字段
	 * @param duizObject ：对账文件字段
	 * @param instId : 机构ID
	 */
	public void tradeErrorDzDeal(String originalPepDate,String duizhangBeijingReqTime,Integer instId,Integer flagStatus)
			throws OriginalDataNotFoundException,DuizhangNotFoundException,Exception;
}
