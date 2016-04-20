package cn.com.chinaebi.dz.object.dao.iface;

import cn.com.chinaebi.dz.object.MerchantSettleStatistics;


public interface MerchantSettleStatisticsDAO {
	public cn.com.chinaebi.dz.object.MerchantSettleStatistics get(java.lang.Integer key);

	public cn.com.chinaebi.dz.object.MerchantSettleStatistics load(java.lang.Integer key);

	public java.util.List<cn.com.chinaebi.dz.object.MerchantSettleStatistics> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param merchantSettleStatistics a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Integer save(cn.com.chinaebi.dz.object.MerchantSettleStatistics merchantSettleStatistics);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param merchantSettleStatistics a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.MerchantSettleStatistics merchantSettleStatistics);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param merchantSettleStatistics a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.MerchantSettleStatistics merchantSettleStatistics);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param merchantSettleStatistics the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.MerchantSettleStatistics merchantSettleStatistics);
	
	/**
	 * 查询商户T+1统计表中是否有数据
	 * @param mer_code  商户号
	 * @param deduct_stlm_date  清算日期
	 * @param inst_id  渠道号
	 * @return
	 */
	public MerchantSettleStatistics queryMerchantSettleStatistics(String mer_code, int deduct_stlm_date, int inst_id,int inst_type, int dataStatus);
	
	/**
	 * 修改商户T+1统计表数据
	 * @param mer_code
	 * @param deduct_stlm_date
	 * @param inst_id
	 * @return
	 */
	public int updateMerchantSettleStatistics(MerchantSettleStatistics merchantSettleStatistics);
	
	/**
	 * 向商户T+1统计表中插入数据
	 * @param merchantSettleStatistics
	 * @return
	 */
	public int addMerchantSettleStatistics(MerchantSettleStatistics merchantSettleStatistics);
	
	/**
	 * 商户T+1数据还原
	 * @param deduct_stlm_date 清算日期
	 * @param inst_id 渠道ID
	 * @param inst_type 渠道类型
	 * @return
	 */
	public boolean deleteMerchantSettleStatistics(String deduct_stlm_date, int inst_id, int inst_type);
}