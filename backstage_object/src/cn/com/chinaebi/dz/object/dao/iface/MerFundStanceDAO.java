package cn.com.chinaebi.dz.object.dao.iface;

import java.util.Date;
import java.util.List;

import cn.com.chinaebi.dz.object.MerFundStance;

public interface MerFundStanceDAO {
	public cn.com.chinaebi.dz.object.MerFundStance get(java.lang.String key);

	public cn.com.chinaebi.dz.object.MerFundStance load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.MerFundStance> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param merFundStance a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.MerFundStance merFundStance);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param merFundStance a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.MerFundStance merFundStance);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param merFundStance a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.MerFundStance merFundStance);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param merFundStance the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.MerFundStance merFundStance);

	/**
	 * 保存商户资金流水数据(对账成功)
	 * @param mer_code : 商户号
	 * @param trade_time : 交易时间
	 * @param trade_amount ：交易金额
	 * @param mer_fee ：商户手续费
	 * @param change_amount ：变动金额
	 * @param account_amount ：账户余额
	 * @param trade_stance ：交易流水号
	 * @param derc_status ：简短说明
	 * @param mer_state ：商户状态
	 * @param mer_category ：商户类别
	 * @param mer_name ：商户简称
	 * @param inst_id ：渠道ID
	 * @param deduct_stlm_date : 清算日期
	 * @param inst_type : 渠道类型
	 * @param bank_id : 银行网关
	 * @return boolean
	 */
	public boolean saveMerFundStance(String mer_code,
			Date trade_time,
			double trade_amount,
			double mer_fee,
			double change_amount,
			double account_amount,
			String trade_stance,
			Integer derc_status,
			Integer mer_state,
			Integer mer_category,
			String mer_name,
			Integer inst_id,
			String deduct_stlm_date,
			Integer inst_type,
			int bank_id);
	
	/**
	 * 通过扣款渠道ID、清算日期和inst_type，根据商户号分组统计指定日期内，商户的变动金额
	 * @param deduct_stlm_date
	 * @param inst_id
	 * @param inst_type
	 * @return
	 */
	public List<Object[]> queryMerFundStance(String deduct_stlm_date,int inst_id, int inst_type);
	
	/**
	 * 向商户资金流水中插入数据
	 * @param merFundStance
	 * @return
	 */
	public boolean addMerFundStance(MerFundStance merFundStance);
	
	/**
	 * 商户资金流水的还原
	 * @param deduct_stlm_date
	 * @param inst_id
	 * @param inst_type
	 * @return
	 */
	public boolean deleteMerFundStance(String deduct_stlm_date,int inst_id, int inst_type);
	/**
	 * 商户差错调整状态资金流水的还原
	 * @param deduct_stlm_date
	 * @param inst_id
	 * @param inst_type
	 * @return
	 */
	public boolean deleteMerFundStanceOfErrorAdjust(String deduct_stlm_date, int inst_id, int inst_type);
	
	/**
	 * 统计差错调整产生的资金流水变动金额
	 * @param deduct_stlm_date
	 * @param inst_id
	 * @param inst_type
	 * @return
	 */
	public List<Object[]> queryMerFundStanceOfErrorAdjust(String deduct_stlm_date,int inst_id, int inst_type);
	
	/**
	 * 查询总笔数和总金额
	 * @param merCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Object queryCountAndMoney(String merCode, String startDate, String endDate);
	
	public List<MerFundStance> queryMerFundStanceByMerCode(String merCode, String startDate, String endDate, int startRow, int endRow);
	
	public List<MerFundStance> queryMerFundStanceDataLst(String merCode, String startDate, String endDate);
}