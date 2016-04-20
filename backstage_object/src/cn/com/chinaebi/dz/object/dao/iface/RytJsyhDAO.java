package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

public interface RytJsyhDAO {
	public cn.com.chinaebi.dz.object.RytJsyh get(java.lang.Long key);

	public cn.com.chinaebi.dz.object.RytJsyh load(java.lang.Long key);

	public java.util.List<cn.com.chinaebi.dz.object.RytJsyh> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param rytJsyh a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RytJsyh rytJsyh);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param rytJsyh a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RytJsyh rytJsyh);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param rytJsyh a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RytJsyh rytJsyh);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Long id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param rytJsyh the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RytJsyh rytJsyh);

	/**
	 * 根据交易日期查询
	 * @param sysDate ：日期
	 * @param gid : 渠道ID
	 * @param bk_chk : 是否对账
	 * @return List
	 */
	public List findRytJsyhList(String sysDate,Integer gid,int bk_chk);
	
	/**
	 * 更新对账处理结果
	 * @param tseq ：流水号
	 * @param bk_chk ：是否对账成功
	 * @param whetherQs ：是否清算
	 * @param zf_fee ：银行手续费(平台计算)
	 * @param zf_file_fee ：对账文件手续费(文件获取)
	 * @param mer_fee : 商户手续费
	 * @param whetherReturnFee : 是否退还手续费
	 * @param deductStlmDate ：清算日期,默认值0、与对账单数据勾对上时,该笔数据会赋值一个清算日期(对账日期)
	 * @return
	 */
	public boolean updateRytJsyh(String tseq,int bk_chk,boolean whetherQs,double zf_fee,String zf_file_fee,double mer_fee,Integer whetherReturnFee,Integer deductStlmDate);
	
}