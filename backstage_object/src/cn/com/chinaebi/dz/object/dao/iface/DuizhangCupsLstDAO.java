package cn.com.chinaebi.dz.object.dao.iface;

import java.util.List;
import java.util.Map;

import cn.com.chinaebi.dz.object.DuizhangCupsLst;

public interface DuizhangCupsLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangCupsLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangCupsLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangCupsLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangCupsLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangCupsLst duizhangCupsLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangCupsLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangCupsLst duizhangCupsLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangCupsLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangCupsLst duizhangCupsLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangCupsLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangCupsLst duizhangCupsLst);

	public List<DuizhangCupsLst> findDuizhangCupsLst(String reqTime);
	
	/**
	 * 查询对账文件数据
	 * @param reqSysStance : 流水号
	 * @param msgType ：交易类型
	 * @param cardNo ：卡号
	 * @return boolean
	 */
	public String findDzFileData(Object entity,Object[] oriObject,Object[] dzFileObjct,Integer instId,boolean isRiqie,String duizhangCupsReqTime) throws Exception;
	
	/**
	 * 查询对账文件数据
	 * @param duizhangCupsReqTime ：清算日期
	 * @return Map<String, String>
	 * @throws Exception
	 */
	public Map<String, String> findDzFileData(String duizhangCupsReqTime) throws Exception;
	
	public boolean findDateData(String reqTime);
	
	public boolean updateClean(String reqSysStance, int duizhangFlag,boolean deductRollBk,String deductStmlDate);
	
	public boolean updateError(String reqSysStance,Integer whetherErroeHandle);
	
	public boolean deleteErrorDzDataBySummaryDate(String summaryFileDate) ;
	
	public void deleteDzDataBySummaryDate(String summaryFileDate);
	
	/**
	 * 获取银联CUPS
	 * @param reqTime
	 * @param bkChk
	 * @return
	 */
	public List<DuizhangCupsLst> findDateData(String reqTime,int bkChk);
	
	public boolean insertDzFileData(String date,String file_name,String file_type,String create_last_time,String dz_file_path,int object_id,String object_name);
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzCupsFile(String sysStance,String date);
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费（针对冲正交易）
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorReceiveFeeByTraceFromDzCupsFile(String sysStance,String date);
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费（针对冲正交易），针对差错总表的查询方法
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorReceiveFeeByTraceFromDzCupsFile(String sysStance);
	/**
	 * 根据交易流水号，在对账文件表中查找指定交易的手续费，针对差错总表的查询方法
	 * @param sysStance
	 * @return
	 */
	public String getAcceptorPayFeeByTraceFromDzCupsFile(String sysStance);
	
	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String sql,int batchNum,String deduct_stlm_date,String type) throws Exception;
	
}