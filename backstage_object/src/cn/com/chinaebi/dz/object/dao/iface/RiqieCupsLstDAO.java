package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.RiqieCupsLst;

public interface RiqieCupsLstDAO {
	public cn.com.chinaebi.dz.object.RiqieCupsLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.RiqieCupsLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.RiqieCupsLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param riqieCupsLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.RiqieCupsLst riqieCupsLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param riqieCupsLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RiqieCupsLst riqieCupsLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param riqieCupsLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RiqieCupsLst riqieCupsLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param riqieCupsLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RiqieCupsLst riqieCupsLst);

	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String deductStmlDate);
	public boolean updateOriginalError(String reqSysStance, boolean clean,Integer bkchk,Integer whetherError,boolean deductRollBk,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,Integer tradeMsgType,String deductStmlDate);
	public boolean updateNoNeedHandle(String reqSysStance,boolean clean,Integer bkchk,Integer tradeMsgType,String deductStmlDate);
	
	/**
	 * 修改数据不需要清算，差错处理
	 * @param reqSysStance
	 * @param clean
	 * @param whetherErroeHandle
	 * @return
	 */
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	
	public List<RiqieCupsLst> findRiqieCupsLst(String deductStlmDate);
	
	public boolean saveRiqieCupsLst(RiqieCupsLst riqieCupsLst,Integer flagStatus);
	
	
	/**
	 * 需要tradeMsgType更新mer_fee、whetherTk、zf_file_fee值
	 * @param reqSysStance : 扣款流水号
	 * @param mer_fee ：商户手续费
	 * @param whetherTk ：是否标记为退款
	 * @param zf_fee ：支付手续费(平台计算)
	 * @param zf_file_fee ：银行手续费(对账单获取)
	 * @param tradeMsgType ：交易消息类型
	 * @param deductRollBk ：是否冲正
	 * @param deductStmlDate ：清算日期
	 * @param whetherReturnFee ：是否退还商户手续费
	 * @return
	 */
	public boolean updateSettleInfo(String reqSysStance,boolean whetherTk,Double zf_fee,String zf_file_fee,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate,Integer whetherReturnFee);
	
}