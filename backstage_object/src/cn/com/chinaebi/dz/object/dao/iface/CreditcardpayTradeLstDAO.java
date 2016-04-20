package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.CreditcardpayTradeLst;

public interface CreditcardpayTradeLstDAO {
	public cn.com.chinaebi.dz.object.CreditcardpayTradeLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.CreditcardpayTradeLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.CreditcardpayTradeLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param creditcardpayTradeLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.CreditcardpayTradeLst creditcardpayTradeLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param creditcardpayTradeLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.CreditcardpayTradeLst creditcardpayTradeLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param creditcardpayTradeLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.CreditcardpayTradeLst creditcardpayTradeLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param creditcardpayTradeLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.CreditcardpayTradeLst creditcardpayTradeLst);
	
	public boolean getCreditcardpayTradeData(String tradeTime);
	/**
	 * 查询信用卡还款内部清算数据
	 * @param date
	 * @param instId
	 * @param status
	 * @return
	 */
	public List<CreditcardpayTradeLst> queryCreditcardpayClearInnerData(String date,int instId,String status);
	/**
	 * 手动汇总信用卡还款数据之前，删除同一时间数据
	 * @param tradeTime
	 * @return
	 */
	public boolean deleteCreditcardPayLst(String tradeTime);
	/**
	 * 向对账文件表记录中添加信用卡还款清算文件记录
	 * @param date 文件日期
	 * @param file_name 文件名称
	 * @param file_type 文件类型
	 * @param create_last_time 最终生成时间
	 * @return
	 */
	public boolean insertDzFileData(String date,String file_name,String file_type,String create_last_time,String path,int object_id,String object_name);
	/**
	 * 查询信用卡成功交易记录
	 * @param date
	 * @param status
	 * @return
	 */
	public List<CreditcardpayTradeLst> queryCreditcardpaySucessData(String date,String status);
	/**
	 * 统计
	 * @param date
	 * @param codeArr
	 * @param generateNumber
	 * @return
	 */
	public int getTotalCountOfDzSucessDataOfCreditCard(String date,List<String> codeArr,int generateNumber);

}