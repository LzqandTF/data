package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.RiqieBeijingbankLst;

public interface RiqieBeijingbankLstDAO {
	public cn.com.chinaebi.dz.object.RiqieBeijingbankLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.RiqieBeijingbankLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.RiqieBeijingbankLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param riqieBeijingbankLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.RiqieBeijingbankLst riqieBeijingbankLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param riqieBeijingbankLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RiqieBeijingbankLst riqieBeijingbankLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param riqieBeijingbankLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RiqieBeijingbankLst riqieBeijingbankLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param riqieBeijingbankLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RiqieBeijingbankLst riqieBeijingbankLst);

	
	/**
	 * 根据日期查询日切交易数据
	 * @param pepdate
	 * @return
	 */
	public List<RiqieBeijingbankLst> findRiqieBeijingbankLst(String pepdate);
	
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,Integer tradeMsgType,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,boolean deductRollBk,String deductStlmDate);
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String deductStlmDate);
	public boolean updateOriginalError(String reqSysStance, boolean clean,Integer bkchk, Integer whetherError, boolean deductRollBk,String tradeTime);
	
	/**
	 * 处理无需对账
	 * @param reqSysStance ：流水号
	 * @param clean ：是否清算
	 * @param bkchk ：对账状态
	 * @param tradeMsgType ：交易消费类型
	 * @param deductStmlDate ：清算日期
	 * @return
	 */
	public boolean updateNoNeedHandle(String reqSysStance,boolean clean,Integer bkchk,Integer tradeMsgType,String deductStmlDate);
	
	/**
	 * 修改数据不需要清算，差错处理
	 * @param reqSysStance
	 * @param clean
	 * @param whetherErroeHandle
	 * @return
	 */
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);

	/**
	 * 保存日切数据
	 * @param riqieBeijingbankLst ：日切实体
	 * @param flagStatus 
	 * @return boolean
	 */
	public boolean saveRiqieBeijingbankLst(RiqieBeijingbankLst riqieBeijingbankLst,Integer flagStatus);
	
	/**
	 * 需要tradeMsgType更新mer_fee、whetherTk、zf_file_fee值
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param tradeMsgType
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @param whetherReturnFee
	 * @return
	 */
	public boolean updateSettleInfo(String reqSysStance,boolean whetherTk,Double zf_fee,String zf_file_fee,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate,Integer whetherReturnFee);
	
}