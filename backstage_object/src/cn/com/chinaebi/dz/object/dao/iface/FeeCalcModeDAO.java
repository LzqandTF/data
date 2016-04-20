package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;

public interface FeeCalcModeDAO {
	public cn.com.chinaebi.dz.object.FeeCalcMode get(cn.com.chinaebi.dz.object.FeeCalcModePK key);

	public cn.com.chinaebi.dz.object.FeeCalcMode load(cn.com.chinaebi.dz.object.FeeCalcModePK key);

	public java.util.List<cn.com.chinaebi.dz.object.FeeCalcMode> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param feeCalcMode a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public cn.com.chinaebi.dz.object.FeeCalcModePK save(cn.com.chinaebi.dz.object.FeeCalcMode feeCalcMode);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param feeCalcMode a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.FeeCalcMode feeCalcMode);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param feeCalcMode a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.FeeCalcMode feeCalcMode);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.FeeCalcModePK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param feeCalcMode the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.FeeCalcMode feeCalcMode);
	
	/**
	 * 获取线上交易商户手续费公式
	 * @param gate ：网关ID
	 * @param mid ：商户号
	 * @param gid : 渠道号
	 * @return String
	 */
	public String getMerFee(Integer gate,String mid,Integer gid);
	
	/**
	 * 获取线下交易商户手续费公式
	 * @param gate ：网关ID
	 * @param mid ：商户号
	 * @param gid : 渠道号
	 * @return String
	 */
	public String getMerFee(String mid,Integer gid);


}