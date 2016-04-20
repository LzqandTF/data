package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.DuizhangErrorCupsLst;
import cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst;

public interface DuizhangErrorUpmpLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangErrorUpmpLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst duizhangErrorUpmpLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangErrorUpmpLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst duizhangErrorUpmpLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangErrorUpmpLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst duizhangErrorUpmpLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangErrorUpmpLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangErrorUpmpLst duizhangErrorUpmpLst);
	
	/**
	 * 根据日期查询银联UPMP差错文件数据
	 * @param reqTime ：日期
	 * @return List<DuizhangErrorUpmpLst>
	 */
	public List<DuizhangErrorUpmpLst> findDateData(String reqTime);
	
	/**
	 * 修改银联UPMP差错文件对账单对账状态
	 * @param deductSysReference ：线上流水号，不足12位右补0
	 * @param duizhangFlag ：对账状态
	 * @return boolean
	 */
	public boolean rytUpdateClean(String deductSysReference,int duizhangFlag);


}