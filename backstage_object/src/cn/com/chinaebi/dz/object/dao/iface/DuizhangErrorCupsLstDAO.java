package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.DuizhangErrorCupsLst;

public interface DuizhangErrorCupsLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangErrorCupsLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangErrorCupsLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangErrorCupsLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangErrorCupsLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangErrorCupsLst duizhangErrorCupsLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangErrorCupsLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangErrorCupsLst duizhangErrorCupsLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangErrorCupsLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangErrorCupsLst duizhangErrorCupsLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangErrorCupsLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangErrorCupsLst duizhangErrorCupsLst);

	/**
	 * 根据日期查询银联CUPS差错文件数据
	 * @param reqTime : 日期
	 * @return List<DuizhangErrorCupsLst>
	 */
	public List<DuizhangErrorCupsLst> findDateData(String reqTime);
	
	/**
	 * 更新银联CUPS差错文件对账状态
	 * @param reqSysStance ：流水号
	 * @param duizhangFlag ：对账状态
	 * @return boolean
	 */
	public boolean updateClean(String reqSysStance,int duizhangFlag);
	
	/**
	 * 查询对账文件数据
	 * @param reqSysStance : 流水号
	 * @return boolean
	 */
	public boolean findDzFileData(String reqStance)throws Exception;
}