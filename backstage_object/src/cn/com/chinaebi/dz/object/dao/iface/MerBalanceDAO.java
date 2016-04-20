package cn.com.chinaebi.dz.object.dao.iface;

import cn.com.chinaebi.dz.object.MerBalance;

public interface MerBalanceDAO {
	public cn.com.chinaebi.dz.object.MerBalance get(java.lang.Integer key);

	public cn.com.chinaebi.dz.object.MerBalance load(java.lang.Integer key);

	public java.util.List<cn.com.chinaebi.dz.object.MerBalance> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param merBalance a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Integer save(cn.com.chinaebi.dz.object.MerBalance merBalance);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param merBalance a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.MerBalance merBalance);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param merBalance a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.MerBalance merBalance);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param merBalance the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.MerBalance merBalance);

	/**
	 * 查询单个商户号余额
	 * @param mer_code : 商户号
	 * @return MerBalance
	 */
	public MerBalance findMerBalance(String mer_code);
	
	/**
	 * 根据商户号修改商户余额
	 * @param merCode
	 * @return
	 */
	public int updateMerBalanceByMerCode(String mer_code, String mer_balance);
	
	/**
	 * 添加商户余额信息
	 * @param merBalance
	 * @return
	 */
	public int addMerBalance(MerBalance merBalance);
}