package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst;


public interface DuizhangZhongxingbankLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangZhongxingbankLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst duizhangZhongxingbankLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangZhongxingbankLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst duizhangZhongxingbankLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangZhongxingbankLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst duizhangZhongxingbankLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangZhongxingbankLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangZhongxingbankLst duizhangZhongxingbankLst);
	
	public boolean insertBankData(String sql,int batchNum,String tradeTime) throws Exception;
	
	public void excutePro();
	
	
	/**
	 * 查询对账文件数据
	 * @param reqSysStance : 流水号
	 * @param msgType ：交易类型
	 * @param cardNo ：卡号
	 * @return boolean
	 */
	public boolean findDzFileData(Object entity,Object[] oriObject,Object[] dzFileObjct,Integer instId,boolean isRiqie,String deductStmlDate)throws Exception;
	
	public boolean findDateData(String reqTime);
	
	public boolean updateClean(String reqSysStance, int duizhangFlag,String deductStlmDate);
	
	public boolean updateError(String reqSysStance,Integer whetherErroeHandle);
	
	public void deleteDzDataBySummaryDate(String summaryFileDate);
	
	public List<DuizhangZhongxingbankLst> findDateData(String reqTime,int bkChk);

	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzZxFile(String sysStance);
}