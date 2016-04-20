package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;

public interface DuizhangJsyhLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangJsyhLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangJsyhLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangJsyhLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangJsyhLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangJsyhLst duizhangJsyhLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangJsyhLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangJsyhLst duizhangJsyhLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangJsyhLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangJsyhLst duizhangJsyhLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangJsyhLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangJsyhLst duizhangJsyhLst);

	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String sql,String deduct_stlm_date) throws Exception;
	
	/**
	 * 修改对账文件对账情况
	 * @param reqStance ：流水号
	 * @param oid : 订单号
	 * @param deduct_stlm_date : 清算日期
	 * @param bk_chk ：对账状态
	 * @param flag : 0-按流水号更新、1-按订单号更新、2-按流水号、订单号更新
	 * @return boolean
	 */
	public boolean updateDzBkChk(String reqStance,String oid,String deduct_stlm_date,int bk_chk,int flag);
}