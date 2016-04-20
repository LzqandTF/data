package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;

public interface DuizhangZhxtLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangZhxtLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangZhxtLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangZhxtLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangZhxtLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangZhxtLst duizhangZhxtLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangZhxtLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangZhxtLst duizhangZhxtLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangZhxtLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangZhxtLst duizhangZhxtLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangZhxtLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangZhxtLst duizhangZhxtLst);
	
	/**
	 * 批量插入对账单数据
	 * @param sql 插入数据sql语句
	 * @param deduct_stlm_date	清算日期
	 * @return
	 * @throws Exception
	 */
	public boolean insertBankData(String sql,String deduct_stlm_date) throws Exception;


}