package cn.com.chinaebi.dz.object.dao.iface;

import java.util.List;

import cn.com.chinaebi.dz.object.OriginalShengjingbankLst;

public interface OriginalShengjingbankLstDAO {
	public cn.com.chinaebi.dz.object.OriginalShengjingbankLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.OriginalShengjingbankLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.OriginalShengjingbankLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param originalShengjingbankLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.OriginalShengjingbankLst originalShengjingbankLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param originalShengjingbankLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.OriginalShengjingbankLst originalShengjingbankLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param originalShengjingbankLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.OriginalShengjingbankLst originalShengjingbankLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param originalShengjingbankLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.OriginalShengjingbankLst originalShengjingbankLst);
	
	/**
	 * 调用存储过程，汇总盛京银行的交易数据
	 * @param tradeTime 交易日期(自然日)
	 * @return
	 */
	public boolean getSJYHTradeLstData(String tradeTime);
	
	/**
	 * 查询信用卡还款内部清算数据
	 * @param date
	 * @param instId
	 * @param status
	 * @return
	 */
	public List<OriginalShengjingbankLst> querySJYHTradeLstClearInnerData(String date,String status);
	/**
	 * 向对账文件表记录中添加盛京银行清算文件记录
	 * @param date 文件日期
	 * @param file_name 文件名称
	 * @param file_type 文件类型
	 * @param create_last_time 最终生成时间
	 * @return
	 */
	public boolean insertDzFileData(String date,String file_name,String file_type,String create_last_time,String path,int object_id,String object_name);

	/**
	 * 手动汇总数据之前，删除同一时间数据
	 * @param tradeTime
	 * @return
	 */
	public boolean deleteOriginalShengjingbankLst(String tradeTime);
	
	/**
	 * 统计盛京银行交易成功数据条数
	 * @param date
	 * @param codeArr
	 * @param generateNumber
	 * @return
	 */
	public int getTotalCountOfDzSucessDataOfSjyh(String date,List<String> codeArr,int generateNumber);


}