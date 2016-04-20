package cn.com.chinaebi.dz.object.dao.iface;


public interface TmoneyDAO {
	public cn.com.chinaebi.dz.object.Tmoney get(cn.com.chinaebi.dz.object.TmoneyPK key);

	public cn.com.chinaebi.dz.object.Tmoney load(cn.com.chinaebi.dz.object.TmoneyPK key);

	public java.util.List<cn.com.chinaebi.dz.object.Tmoney> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tmoney a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public cn.com.chinaebi.dz.object.TmoneyPK save(cn.com.chinaebi.dz.object.Tmoney tmoney);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tmoney a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.Tmoney tmoney);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tmoney a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.Tmoney tmoney);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.TmoneyPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tmoney the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.Tmoney tmoney);

	/**
	 * 调用线下头寸调拨存储过程方法
	 * @param trade_time 	交易日期
	 * @param tableName		原始数据表名
	 * @param deduct_stlm_date	清算日期
	 * @return
	 */
	
	/**
	 * 调用线下头寸调拨存储过程方法
	 * @param trade_time 交易日期
	 * @param tableName 原始数据表名
	 * @param deduct_stlm_date 清算日期
	 * @param inst_id 渠道ID
	 * @param inst_type 渠道类型
	 * @return
	 */
	public boolean proce_pos_head_handle(String trade_time,String tableName,int deduct_stlm_date, int inst_id, int inst_type);
	
	/**
	 * 调用线上头寸调拨存储过程方法
	 * @param trade_time 交易日期
	 * @param tableName  原始数据表名
	 * @param deduct_stlm_date 清算日期
	 * @param tableColumn 日期字段
	 * @param amountColumn 金额字段
	 * @param inst_id 渠道ID
	 * @param inst_type 渠道类型
	 * @return
	 */
	public boolean proce_ryf_head_handle(String trade_time,String tableName,int deduct_stlm_date,String tableColumn,String amountColumn, int inst_id, int inst_type);
	
	/**
	 * 删除头寸调拨表数据
	 * @param deduct_stlm_date 清算日期
	 * @param inst_id 渠道ID
	 * @param inst_type 渠道类型
	 * @return
	 */
	public boolean deleteTmoneyData(int deduct_stlm_date, int inst_id, int inst_type);
}