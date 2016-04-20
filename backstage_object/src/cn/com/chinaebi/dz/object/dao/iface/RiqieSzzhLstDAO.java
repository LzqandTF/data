package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.RiqieDljhLst;
import cn.com.chinaebi.dz.object.RiqieSzzhLst;

public interface RiqieSzzhLstDAO {
	public cn.com.chinaebi.dz.object.RiqieSzzhLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.RiqieSzzhLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.RiqieSzzhLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param riqieSzzhLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.RiqieSzzhLst riqieSzzhLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param riqieSzzhLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RiqieSzzhLst riqieSzzhLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param riqieSzzhLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RiqieSzzhLst riqieSzzhLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param riqieSzzhLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RiqieSzzhLst riqieSzzhLst);

	
	/**
	 * 根据清算日期查询日切交易数据
	 * @param deductStlmDate ：日期
	 * @return List<RiqieDljhLst>
	 */
	public List<RiqieSzzhLst> findRiqieSzzhLst(String deductStlmDate);
	
	/**
	 * 需要tradeMsgType更新clean、bkchk值
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param tradeMsgType
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	
	/**
	 * 不需要tradeMsgType更新clean、bkchk值
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String deductStmlDate);
	
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
	
	/**
	 * 修改数据"无需对账"
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param tradeMsgType
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateNoNeedHandle(String reqSysStance,boolean clean,Integer bkchk,Integer tradeMsgType,String deductStmlDate);
	
	/**
	 * 不需要tradeMsgType修改数据为差错
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param whetherError
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer bkchk,Integer whetherError,boolean deductRollBk,String deductStmlDate);
	
	/**
	 * 需要tradeMsgType修改数据为差错
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param whetherError
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer bkchk,Integer whetherError,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);

	/**
	 * 保存原始交易为日切表中
	 * @param riqieDljhLst
	 * @param flagStatus
	 * @return
	 */
	public boolean saveRiqieDljhLst(RiqieSzzhLst riqieSzzhLst,Integer flagStatus);

}