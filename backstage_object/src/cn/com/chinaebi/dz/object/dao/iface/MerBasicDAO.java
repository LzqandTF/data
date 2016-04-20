package cn.com.chinaebi.dz.object.dao.iface;

import java.util.Map;

import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;

public interface MerBasicDAO {
	public cn.com.chinaebi.dz.object.MerBasic get(java.lang.String key);

	public cn.com.chinaebi.dz.object.MerBasic load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.MerBasic> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param merBasic a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.MerBasic merBasic);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param merBasic a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.MerBasic merBasic);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param merBasic a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.MerBasic merBasic);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param merBasic the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.MerBasic merBasic);
	
	/**
	 * 保存或修改商户信息
	 * @param map	商户信息集合
	 * @return
	 */
	public Map<String,Object> saveOrUpdateMerInfo(Map<String,Object> map);
	
	/**
	 * 融易付商户信息同步
	 * @param merBasic   商户基本信息
	 * @param merBilling 商户结算信息
	 * @return
	 */
	public boolean saveOrUpdateMerAndBasic(MerBasic merBasic,MerBilling merBilling);
	
	/**
	 * 根据商户号获取商户信息
	 * @param merCode 商户号
	 * @return Object
	 */
	public Object queryMerInfoByMerCode(String merCode);
	
	/**
	 * 根据商户号获取商户类型(代付用)
	 * @param merCode 商户号
	 * @return
	 */
	public Object queryMerTypeByMerCode(String merCode);
	
	/**
	 * 根据商户号查询字段信息(MerState,MerCategory,MerAbbreviation)
	 * @param merCode : 商户号
	 * @return MerBasic
	 */
	public MerBasic queryMerBasic(String merCode);
	
	
//	/**
//	 * 通过存储过程导出来的数据结算银行账号是加密的需要解密
//	 * @param merBillings
//	 * @return
//	 */
//	public boolean updateMerDesInfo(List<MerBilling> merBillings);
	
	public MerBasic getMerBasicByInSql(String merCode) throws Exception;
}