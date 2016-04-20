package cn.com.chinaebi.dz.object.dao.iface;

import java.util.List;
import java.util.Map;

import cn.com.chinaebi.dz.object.MerchantFundSettle;

public interface MerchantFundSettleDAO {
	public cn.com.chinaebi.dz.object.MerchantFundSettle get(java.lang.Integer key);

	public cn.com.chinaebi.dz.object.MerchantFundSettle load(java.lang.Integer key);

	public java.util.List<cn.com.chinaebi.dz.object.MerchantFundSettle> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param merchantFundSettle a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Integer save(cn.com.chinaebi.dz.object.MerchantFundSettle merchantFundSettle);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param merchantFundSettle a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.MerchantFundSettle merchantFundSettle);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param merchantFundSettle a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.MerchantFundSettle merchantFundSettle);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param merchantFundSettle the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.MerchantFundSettle merchantFundSettle);
	
	/**
	 * 查询商户结算信息
	 * @param startDate 结算发起时间范围起始值
	 * @param endDate   结算发起时间截止值
	 * @param settleAccountType  结算账户类型(1-电银账户;2-银行卡账户)
	 * @return
	 */
	public List<MerchantFundSettle> queryMerchantSettleData(int startDate,int endDate,int settleAccountType);
	
	/**
	 * 根据ID获得需要代付的数据
	 * @param id
	 * @return
	 */
	public List<MerchantFundSettle> queryNeedDeductedMerList(String id);
	
	/**
	 * 根据ID修改划款状态及划款失败信息
	 * @param id
	 * @param df_result
	 * @param errorMsg
	 * @return
	 */
	public boolean updateMerchantFundSettleDfResult(Integer id, Integer df_result,String errorMsg);
	
	/**
	 * 线上退款交易截止日期验证
	 * 清算日期 > 结算截止日期  ? 清算日期 : 结算截止日期
	 * @param deductStlmDate ：清算日期
	 * @param mid  ：商户号
	 * @return int
	 */
	public int queryCheckEndDate(Integer deductStlmDate,String mid);
	
	/**
	 * 获取商户结算单数据--分页
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MerchantFundSettle> getMerchantFundSettlePageData(Map<String,String[]> map) throws Exception;
	
	/**
	 * 获取商户结算单数据
	 * @param map
	 * @return
	 */
	public List<MerchantFundSettle> getMerchantFundSettleDataList(Map<String,String[]> map) throws Exception;
}