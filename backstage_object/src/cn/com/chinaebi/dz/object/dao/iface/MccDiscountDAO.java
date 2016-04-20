package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;

public interface MccDiscountDAO {

	public java.util.List<cn.com.chinaebi.dz.object.MccDiscount> findAll ();


	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param mccDiscount a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.MccDiscount mccDiscount);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param mccDiscount a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.MccDiscount mccDiscount);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param mccDiscount the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.MccDiscount mccDiscount);
	
	/**
	 * 根据mcc查询总的手续费公式
	 * @param mccCode : mcc号码
	 */
	public String getMccDiscountTotal(String mccCode);
	
	/**
	 * 根据mcc查询发卡和银联比例部分的公式
	 * @param mccCode ：mcc号码
	 * @return Object[]
	 */
	public Object[] getMccDiscount(String mccCode);


}