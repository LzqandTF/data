package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.chinaebi.dz.object.DuizhangBeijingbankLst;
import cn.com.chinaebi.dz.object.RiqieBeijingbankLst;

public interface DuizhangBeijingbankLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangBeijingbankLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangBeijingbankLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangBeijingbankLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangBeijingbankLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangBeijingbankLst duizhangBeijingbankLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangBeijingbankLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangBeijingbankLst duizhangBeijingbankLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangBeijingbankLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangBeijingbankLst duizhangBeijingbankLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangBeijingbankLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangBeijingbankLst duizhangBeijingbankLst);
	
	
	public List<DuizhangBeijingbankLst> findDuizhangBeijingbankLst(String reqTime);

	
	public boolean updateError(String reqSysStance,Integer whetherErroeHandle);
	
	/**
	 * 查询对账文件数据
	 * @param reqSysStance : 流水号
	 * @param msgType ：交易类型
	 * @param cardNo ：卡号
	 * @return String
	 */
	public String findDzFileData(Object entity,Object[] oriObject,Object[] dzFileObjct,Integer instId,boolean isRiqie,String deductStmlDate) throws Exception;
	
	public Map<String, String> findDzFileData(String deductStmlDate) throws Exception;
	
	public boolean findDateData(String reqTime);
	
	public List<DuizhangBeijingbankLst> findDateData(String reqTime,int bkChk);
	
	public boolean updateClean(String reqSysStance, int duizhangFlag,boolean deductRollBk,String deductStlmDate);
	
	public List<DuizhangBeijingbankLst> findNotDuiZhangFileData(String reqTime);
	
	public void deleteDzDataBySummaryDate(String summaryFileDate);
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费
	 * @param sysStance
	 * @param date
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzBjFile(String sysStance,String date);
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费(针对冲正交易)
	 * @param sysStance
	 * @param date
	 * @return
	 */
	public String getAcceptorReceiveFeeByTraceFromDzBjFile(String sysStance,String date);
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费(针对冲正交易),针对差错总表的方法
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorReceiveFeeByTraceFromDzBjFile(String sysStance);
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费,针对差错总表的方法
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzBjFile(String sysStance);
	
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzBjbankFile(String sysStance,String date);
	
	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String sql,int batchNum,String reqTime) throws Exception;

}