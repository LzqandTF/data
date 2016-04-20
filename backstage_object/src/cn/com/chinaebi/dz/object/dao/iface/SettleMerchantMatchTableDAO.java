package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SettleMerchantMatchTableDAO {
	public cn.com.chinaebi.dz.object.SettleMerchantMatchTable get(java.lang.String key);

	public cn.com.chinaebi.dz.object.SettleMerchantMatchTable load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.SettleMerchantMatchTable> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param settleMerchantMatchTable a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.SettleMerchantMatchTable settleMerchantMatchTable);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param settleMerchantMatchTable a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.SettleMerchantMatchTable settleMerchantMatchTable);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param settleMerchantMatchTable a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.SettleMerchantMatchTable settleMerchantMatchTable);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param settleMerchantMatchTable the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.SettleMerchantMatchTable settleMerchantMatchTable);
	
	/**
	 * 根据结算商户号查询电银内部商户号
	 * @param settleMerCode ：结算商户号
	 * @return List<Object>
	 */
	public List<Object> getSettleMerchantMatchTable(String settleMerCode);
	
	/**
	 * 根据电银内部商户号查询结算商户号
	 * @param dyMerCode : 电银内部商户号
	 * @return String
	 */
	public String getSettleMerCode(String dyMerCode);
	
	
	/**
	 * 
	 */
	public Map<String, String> getSettleMerCode();

}