package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;

import cn.com.chinaebi.dz.object.InstInfo;

public interface InstInfoDAO {
	public cn.com.chinaebi.dz.object.InstInfo get(cn.com.chinaebi.dz.object.InstInfoPK key);

	public cn.com.chinaebi.dz.object.InstInfo load(cn.com.chinaebi.dz.object.InstInfoPK key);

	public java.util.List<cn.com.chinaebi.dz.object.InstInfo> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param instInfo a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public cn.com.chinaebi.dz.object.InstInfoPK save(cn.com.chinaebi.dz.object.InstInfo instInfo);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param instInfo a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.InstInfo instInfo);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param instInfo a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.InstInfo instInfo);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.InstInfoPK key);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param instInfo the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.InstInfo instInfo);

	public String getInstInfoNameById(int instId,int inst_type);
	
	public String getReceiviNameById(int instId,int inst_type);
	
	public InstInfo getInstInfoByIdInSQL(int instId,int inst_type) throws Exception;
	
	/**
	 * 通过渠道ID查询渠道开通状态
	 * @param instId 渠道id
	 * @return
	 */
	public boolean getInstStatusByInstId(int instId,int inst_type);
}