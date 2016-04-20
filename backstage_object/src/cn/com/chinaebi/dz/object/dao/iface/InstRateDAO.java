package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.Map;

public interface InstRateDAO {
	public cn.com.chinaebi.dz.object.InstRate get(cn.com.chinaebi.dz.object.InstRatePK key);

	public cn.com.chinaebi.dz.object.InstRate load(cn.com.chinaebi.dz.object.InstRatePK key);

	public java.util.List<cn.com.chinaebi.dz.object.InstRate> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param instRate a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public cn.com.chinaebi.dz.object.InstRatePK save(cn.com.chinaebi.dz.object.InstRate instRate);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param instRate a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.InstRate instRate);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param instRate a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.InstRate instRate);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.InstRatePK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param instRate the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.InstRate instRate);
	
	/**
	 * 获取渠道费率类型 1:按MCC费率、2:银行对账单、3:按固定费率
	 * @param inst_id ：渠道ID
	 * @param inst_type ：渠道类型 0:线下、1:线上
	 * @return Object[]
	 */
	public Object[] getInstRateType(Integer inst_id,Integer inst_type);
	
	/**
	 * 查询渠道费率配置(添加例外-按mcc费率配置)
	 * @param instId : 渠道ID
	 * @param instType ：渠道类型
	 * @return Map<String, boolean>
	 */
	public Map<String, Boolean> findChanelMccRateConf(Integer instId,Integer instType);


}