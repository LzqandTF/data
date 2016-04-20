package cn.com.chinaebi.dz.object.dao.iface;

import java.util.List;

import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.OriginalCupsLst;

public interface OriginalCupsLstDAO {
	public cn.com.chinaebi.dz.object.OriginalCupsLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.OriginalCupsLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.OriginalCupsLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param originalCupsLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.OriginalCupsLst originalCupsLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param originalCupsLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.OriginalCupsLst originalCupsLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param originalCupsLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.OriginalCupsLst originalCupsLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param originalCupsLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.OriginalCupsLst originalCupsLst);
	
	
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String tradeTime);
	public boolean updateClean(String reqSysStance, boolean clean,boolean deductRollBk,String tradeTime);
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String tradeTime);
	public boolean updateOriginalError(String reqSysStance, boolean clean,Integer bkchk,Integer whetherError,boolean deductRollBk,String deductStmlDate);
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,Integer tradeMsgType,String tradeTime);
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer tradeMsgType,String deductStmlDate);
	public boolean updateNoNeedHandle(String reqSysStance,boolean clean,Integer bkchk,Integer tradeMsgType,String deductStmlDate);
	//获取交易对账结果
	public OriginalCupsLst queryWhetherDzSuccess(String reqSysStance,String deductStlmDate,boolean deductRollBk);
	public OriginalCupsLst queryWhetherDzSuccessAll(String reqSysStance,String deductStlmDate,boolean deductRollBk);
	
	/**
	 * 修改数据不需要清算，差错处理
	 * @param reqSysStance
	 * @param clean
	 * @param whetherErroeHandle
	 * @return
	 */
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String tradeTime);
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,boolean deductRollBk,String tradeTime);
	public boolean updateCleanAndErrorRiqie(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	
	public List<OriginalCupsLst> findOriginalCupsbankLst(String originalReqTime);
	
	public boolean getCupsData(String tradeTime);
	
	public boolean splitOriginalCupsData(String tradeTime);
	
	public boolean handleMoney(String tradeTime);
	/**
	 * 在进行手动汇总银联数据之前，根据交易时间，将之前由存储过程汇总完成的同一时间的数据删除
	 */
	public boolean deleteCupsDataByTradeTime(String tradeTime);
	/**
	 * 在手动对账之前，根据交易时间，将银联原始交易数据以及银联对账文件的是否差错处理字段的状态还原
	 * @param summaryDate
	 * @return
	 */
	public boolean reductionDataStatusType(String summaryDate,InstInfo instInfo);
	
	/**
	 * 查询银联CUPS对账成功数据，非冲正数据
	 * @param date
	 * @return
	 */
	public List<OriginalCupsLst> queryCupsDzSucessDataOfNotRollBk(String date);
	/**
	 * 查询银联CUPS对账成功数据，冲正数据
	 * @param date
	 * @return
	 */
	public List<OriginalCupsLst> queryCupsDzSucessDataOfRollBk(String date);
	/**
	 * 查询银联内部清算数据
	 * @param date
	 * @return
	 */
	public List<OriginalCupsLst> queryCupsInnerClearData(String date);

	/**
	 * 根据流水修改数据状态为日切
	 * @param reqSysStance
	 * @param whetherRiqie
	 * @return
	 */
	public boolean updateDataRiqie(String reqSysStance,boolean whetherRiqie,String deductStmlDate);
	/**
	 * 还原银联差错数据状态
	 * @param summaryDate
	 * @return
	 */
	public boolean reductionErrorDataStatusType(String summaryDate) throws Exception;
	/**
	 * 在进行手动汇总数据之前，根据交易时间和渠道ID，将之前由存储过程分割完成的同一时间的数据删除
	 */
	public boolean deleteSplitDataByTradeTime(String summaryDate,int instId);
	
	/**
	 * 获得银联cups非冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getCupsTotalTradeAmountOfNotRollBk(String date);
	/**
	 * 获得银联cups冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getCupsTotalTradeAmountOfRollBk(String date);
	
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对冲正交易)
	 * @param sysStance
	 * @return
	 */
	public String getOriginalTradeTimeOfRollBk(String sysStance,String date);
	
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对撤销和退货交易)
	 * @param sysStance
	 * @return
	 */
	public String getOriginalTradeTimeOfCancel(String sysStance,int tradeMsgType,String date);
	/**
	* 处理明确失败的交易为无需对账
	* 改存储过程在定时任务时执行
	*/
	public boolean dispenseWithHandleOfCups(String tradeTime,String tableName);
	
	
	/**
	 * 需要tradeMsgType更新mer_fee、whetherTk、zf_file_fee值
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param tradeMsgType
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @param whetherReturnFee
	 * @return
	 */
	public boolean updateSettleInfoRiqie(String reqSysStance,boolean whetherTk,Double zf_fee,String zf_file_fee,Integer tradeMsgType,boolean deductRollBk,String originalReqTime,Integer whetherReturnFee);
	
	
	/**
	 * 需要tradeMsgType更新mer_fee、whetherTk、zf_file_fee值
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param tradeMsgType
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @param whetherReturnFee
	 * @return
	 */
	public boolean updateSettleInfo(String reqSysStance,boolean whetherTk,Double zf_fee,String zf_file_fee,Integer tradeMsgType,boolean deductRollBk,String originalReqTime,Integer whetherReturnFee);
	
}