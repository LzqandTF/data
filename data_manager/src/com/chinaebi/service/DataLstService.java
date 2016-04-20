package com.chinaebi.service;


/**
 * 汇总数据Service接口
 * @author admin
 *
 */
public interface DataLstService {
	/**
	 * 针对某个渠道进行原始交易汇总数据操作
	 * @param inst_id
	 * @param summaryDate
	 * @return
	 */
	public boolean manualSummaryData(int bank_id,int inst_id,String summaryDate,int instType);
}
