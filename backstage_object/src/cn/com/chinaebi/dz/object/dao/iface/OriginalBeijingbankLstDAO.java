package cn.com.chinaebi.dz.object.dao.iface;

import java.util.List;

import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.OriginalBeijingbankLst;

public interface OriginalBeijingbankLstDAO {
	public cn.com.chinaebi.dz.object.OriginalBeijingbankLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.OriginalBeijingbankLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.OriginalBeijingbankLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param originalBeijingbankLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.OriginalBeijingbankLst originalBeijingbankLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param originalBeijingbankLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.OriginalBeijingbankLst originalBeijingbankLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param originalBeijingbankLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.OriginalBeijingbankLst originalBeijingbankLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param originalBeijingbankLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.OriginalBeijingbankLst originalBeijingbankLst);

	public List<OriginalBeijingbankLst> findOriginalBeijingbankLst(String originalReqTime);
	
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String tradeTime);
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,boolean deductRollBk,String deductStmlDate);
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String deductStmlDate);
	public boolean updateOriginalError(String reqSysStance, boolean clean,Integer bkchk, Integer whetherError, boolean deductRollBk,String tradeTime);
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String tradeTime);
	public boolean updateClean(String reqSysStance, boolean clean,boolean deductRollBk,String tradeTime);
	public boolean updateClean(String reqSysStance, boolean clean,Integer tradeMsgType,String tradeTime);
	//获取交易对账结果
	public OriginalBeijingbankLst queryWhetherDzSuccess(String reqSysStance,String deductStlmDate,boolean deductRollBk);
	public OriginalBeijingbankLst queryWhetherDzSuccessAll(String reqSysStance,String deductStlmDate,boolean deductRollBk);
	
	/**
	 * 修改数据不需要清算，差错处理
	 * @param reqSysStance
	 * @param clean
	 * @param whetherErroeHandle
	 * @return
	 */
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,String tradeTime);
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	public boolean updateCleanAndErrorRiqie(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	
	/**
	 * 处理无需对账
	 * @param reqSysStance ：流水号
	 * @param clean ：是否清算
	 * @param bkchk ：对账状态
	 * @param tradeMsgType ：交易消息类型
	 * @param deductStmlDate ：清算日期
	 * @return
	 */
	public boolean updateNoNeedHandle(String reqSysStance, boolean clean,Integer bkchk, Integer tradeMsgType, String deductStmlDate);
	
	public boolean getBeijingBankData(String tradeTime);
	
	public boolean splitOriginalBeijingData(String tradeTime);
	
	public boolean handleMoney(String tradeTime);
	/**
	 * 在进行手动汇总北京银行数据之前，根据交易时间，将之前由存储过程汇总完成的同一时间的数据删除
	 */
	public boolean deleteBeijingbankDataByTradeTime(String tradeTime);
	/**
	 * 在手动对账之前，根据交易时间，将北京银行原始交易数据 以及北京银行对账数据是否差错处理字段的状态还原
	 * @param summaryDate
	 * @return
	 */
	public boolean reductionDataStatusType(String summaryDate,InstInfo instInfo);
	/**
	 * 查询上海银联POSP对账成功数据，冲正数据
	 * @param date
	 * @return
	 */
	public List<OriginalBeijingbankLst> queryBeijingbankDzSucessDataOfRollBk(String date);
	/**
	 * 查询上海银联POSP对账成功数据，非冲正数据
	 * @param date
	 * @return
	 */
	public List<OriginalBeijingbankLst> queryBeijingbankDzSucessDataOfNotRollBk(String date);
	/**
	 * 查询上海银联POSP内部清算数据
	 * @param date
	 * @return
	 */
	public List<OriginalBeijingbankLst> queryBeijingbankClearInnerData(String date);
	
	/**
	 * 根据流水修改数据状态为日切
	 * @param reqSysStance
	 * @param whetherRiqie
	 * @return
	 */
	public boolean updateDataRiqie(String reqSysStance,boolean whetherRiqie,String deductStlmDate);
	/**
	 * 获得上海银联POSP非冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getBeijingbankTotalTradeAmountOfNotRollBk(String date);
	/**
	 * 获得上海银联POSP冲正交易的对账成功数据的总金额
	 * @param date
	 * @return
	 */
	public Double getBeijingbankTotalTradeAmountOfRollBk(String date);
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
	public boolean dispenseWithHandleOfBeijingBank(String tradeTime,String tableName);
	
	
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