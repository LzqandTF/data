package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.OriginalBeijingbankLst;
import cn.com.chinaebi.dz.object.OriginalCupsLst;
import cn.com.chinaebi.dz.object.OriginalZhongxingbankLst;

public interface OriginalZhongxingbankLstDAO {
	public cn.com.chinaebi.dz.object.OriginalZhongxingbankLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.OriginalZhongxingbankLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.OriginalZhongxingbankLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param originalZhongxingbankLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.OriginalZhongxingbankLst originalZhongxingbankLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param originalZhongxingbankLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.OriginalZhongxingbankLst originalZhongxingbankLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param originalZhongxingbankLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.OriginalZhongxingbankLst originalZhongxingbankLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param originalZhongxingbankLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.OriginalZhongxingbankLst originalZhongxingbankLst);

	
	public List<OriginalZhongxingbankLst> findOriginalZhongxingbankLst(String originalReqTime);
	
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String tradeTime);
	public boolean updateClean(String reqSysStance, boolean clean,boolean deductRollBk,String tradeTime);
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,boolean deductRollBk,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,Integer tradeMsgType,String tradeTime);
	
	/**
	 * 修改数据不需要清算，差错处理
	 * @param reqSysStance
	 * @param clean
	 * @param whetherErroeHandle
	 * @return
	 */
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String tradeTime);
	public boolean updateCleanAndErrorRiqie(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,String tradeTime);
	
	public boolean innerHandler(String tradeTime);

	public boolean getZhongxingBankData(String tradeTime);
	
	public boolean splitOriginalZhongxingData(String tradeTime);
	
	public boolean handleMoney(String tradeTime);
	/**
	 * 在进行手动汇总中信银行数据之前，根据交易时间，将之前由存储过程汇总完成的同一时间的数据删除
	 */
	public boolean deleteZhongxinbankDataByTradeTime(String tradeTime);
	/**
	 * 在手动对账之前，根据交易时间，将中信银行原始交易数据以及中信银行对账数据的是否差错处理字段的状态还原
	 * @param summaryDate
	 * @return
	 */
	public boolean reductionDataStatusType(String summaryDate,InstInfo instInfo);
	/**
	 * 查询上海中信银行对账成功数据，冲正数据
	 * @param date
	 * @return
	 */
	public List<OriginalZhongxingbankLst> queryZhongxingbankDzSucessDataOfRollBk(String date);
	/**
	 * 查询上海中信银行对账成功数据，非冲正数据
	 * @param date
	 * @return
	 */
	public List<OriginalZhongxingbankLst> queryZhongxingbankDzSucessDataOfNotRollBk(String date);
	/**
	 * 查询上海中信内部清算数据
	 * @param date
	 * @return
	 */
	public List<OriginalZhongxingbankLst> queryZhongxinbankInnerClearData(String date);
	
	/**
	 * 根据流水修改数据状态为日切
	 * @param reqSysStance
	 * @param whetherRiqie
	 * @return
	 */
	public boolean updateDataRiqie(String reqSysStance,boolean whetherRiqie,String deductStlmDate);
	/**
	 * 获得上海中信非冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getZhongxingbankTotalTradeAmountOfNotRollBk(String date);
	/**
	 * 获得上海中信冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getZhongxingbankTotalTradeAmountOfRollBk(String date);
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对冲正交易)
	 * @param sysStance
	 * @return
	 */
	public String getOriginalTradeTimeOfRollBk(String sysStance);
	
	/**
	 * 根据当前交易流水号查询原笔交易的交易时间(针对撤销和退货交易)
	 * @param sysStance
	 * @return
	 */
	public String getOriginalTradeTimeOfCancel(String sysStance,int tradeMsgType);
	/**
	* 处理明确失败的交易为无需对账
	* 改存储过程在定时任务时执行
	*/
	public boolean dispenseWithHandleOfZhongxinBank(String tradeTime,String tableName);
}