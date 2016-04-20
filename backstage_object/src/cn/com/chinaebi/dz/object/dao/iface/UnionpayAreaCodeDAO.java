package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;

public interface UnionpayAreaCodeDAO {
	public cn.com.chinaebi.dz.object.UnionpayAreaCode get(java.lang.Integer key);

	public cn.com.chinaebi.dz.object.UnionpayAreaCode load(java.lang.Integer key);

	public java.util.List<cn.com.chinaebi.dz.object.UnionpayAreaCode> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param unionpayAreaCode a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Integer save(cn.com.chinaebi.dz.object.UnionpayAreaCode unionpayAreaCode);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param unionpayAreaCode a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.UnionpayAreaCode unionpayAreaCode);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param unionpayAreaCode a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.UnionpayAreaCode unionpayAreaCode);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param unionpayAreaCode the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.UnionpayAreaCode unionpayAreaCode);


}