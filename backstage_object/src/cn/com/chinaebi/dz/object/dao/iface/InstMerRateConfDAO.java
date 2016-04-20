package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.InstMerRateConf;

public interface InstMerRateConfDAO {
	public cn.com.chinaebi.dz.object.InstMerRateConf get(cn.com.chinaebi.dz.object.InstMerRateConfPK key);

	public cn.com.chinaebi.dz.object.InstMerRateConf load(cn.com.chinaebi.dz.object.InstMerRateConfPK key);

	public java.util.List<cn.com.chinaebi.dz.object.InstMerRateConf> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param instMerRateConf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public cn.com.chinaebi.dz.object.InstMerRateConfPK save(cn.com.chinaebi.dz.object.InstMerRateConf instMerRateConf);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param instMerRateConf a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.InstMerRateConf instMerRateConf);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param instMerRateConf a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.InstMerRateConf instMerRateConf);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.InstMerRateConfPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param instMerRateConf the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.InstMerRateConf instMerRateConf);
	
	/**
	 * 获取渠道手续费
	 * @param inst_id ：渠道ID
	 * @param inst_type ：渠道类型 0:线下、1:线上
	 * @param mer_code : 商户号
	 * @return Object[]
	 */
	public List<InstMerRateConf> getInstMerRateConf(Integer inst_id,Integer inst_type,String mer_code);


}