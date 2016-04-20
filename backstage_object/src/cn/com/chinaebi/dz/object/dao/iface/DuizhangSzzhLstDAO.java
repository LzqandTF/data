package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.chinaebi.dz.object.DuizhangSzzhLst;

public interface DuizhangSzzhLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangSzzhLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangSzzhLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangSzzhLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangSzzhLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangSzzhLst duizhangSzzhLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangSzzhLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangSzzhLst duizhangSzzhLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangSzzhLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangSzzhLst duizhangSzzhLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangSzzhLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangSzzhLst duizhangSzzhLst);
	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String insertSql,String deduct_stlm_date) throws Exception;
	
	
	/**
	 * 查询深圳中行对账文件是否有数据
	 * @param reqTime ：查询日期
	 * @return boolean
	 */
	public boolean findDateData(String reqTime);
	
	public List<DuizhangSzzhLst> findDateData(String reqTime,int bkchk);
	
	/**
	 * 查询对账文件数据
	 * @param reqSysStance : 流水号
	 * @param msgType ：交易类型
	 * @param cardNo ：卡号
	 * @return boolean
	 */
	public String findDzFileData(Object entity,Object[] oriObject,Object[] dzFileObjct,Integer instId,boolean isRiqie,String duizhangReqTime,int tradeMsgType) throws Exception;

	/**
	 * 查询对账文件数据
	 * @param duizhangReqTime ：清算日期
	 * @return Map<String, String>
	 * @throws Exception
	 */
	public Map<String, String> findDzFileData(String duizhangReqTime) throws Exception;

	/**
	 * 修改对账文件对账成功
	 * @param reqSysStance
	 * @param duizhangFlag
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateClean(String reqSysStance, int duizhangFlag,boolean deductRollBk,String deductStmlDate,int tradeMsgType);


}