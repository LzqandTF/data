package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.OriginalCupsLst;
import cn.com.chinaebi.dz.object.OriginalDljhLst;

public interface OriginalDljhLstDAO {
	public cn.com.chinaebi.dz.object.OriginalDljhLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.OriginalDljhLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.OriginalDljhLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param originalDljhLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param originalDljhLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param originalDljhLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param originalDljhLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.OriginalDljhLst originalDljhLst);
	/**
	 * 获取原笔对账结果Id,BkChk
	 * @param reqSysStance
	 * @param deductStlmDate
	 * @param deductRollBk
	 * @return
	 */
	public OriginalDljhLst queryWhetherDzSuccess(String reqSysStance,String deductStlmDate,boolean deductRollBk);
	
	/**
	 * 获取原笔对账结果(所有字段)
	 * @param reqSysStance
	 * @param deductStlmDate
	 * @param deductRollBk
	 * @return
	 */
	public OriginalDljhLst queryWhetherDzSuccessAll(String reqSysStance,String deductStlmDate,boolean deductRollBk);
	
	/**
	 * 获取原始交易数据
	 * @param originalReqTime
	 * @return List<OriginalDljhLst>
	 */
	public List<OriginalDljhLst> findOriginalDljhLst(String originalReqTime);
	
	/**
	 * 根据流水修改数据状态为日切
	 * @param reqSysStance
	 * @param whetherRiqie
	 * @return
	 */
	public boolean updateDataRiqie(String reqSysStance,boolean whetherRiqie,String originalReqTime);
	
	/**
	 * 标记为"无需对账"
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param tradeMsgType
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateNoNeedHandle(String reqSysStance,boolean clean,Integer bkchk,Integer tradeMsgType,String originalReqTime);
	
	
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
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String originalReqTime);
	
	/**
	 * 不需要tradeMsgType更新clean、bkchk值
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,boolean deductRollBk,String originalReqTime);
	
	
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
	public boolean updateSettleInfo(String reqSysStance,boolean whetherTk,Double zf_fee,String zf_file_fee,Integer tradeMsgType,boolean deductRollBk,String originalReqTime,Integer whetherReturnFee);
	
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
	public boolean updateSettleInfoRiqie(String reqSysStance,boolean whetherTk,Double zf_fee,String zf_file_fee,Integer tradeMsgType,boolean deductRollBk,String originalReqTime,Integer whetherReturnFee);
	
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
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer bkchk,Integer whetherError,boolean deductRollBk,String originalReqTime);
	
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
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer bkchk,Integer whetherError,Integer tradeMsgType,boolean deductRollBk,String originalReqTime);

	/**
	 * 需要tradeMsgType修改日切原笔
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param tradeMsgType
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,Integer whetherError,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	
	/**
	 * 不需要tradeMsgType修改日切原笔
	 * @param reqSysStance
	 * @param clean
	 * @param bkchk
	 * @param tradeMsgType
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,Integer whetherError,boolean deductRollBk,String deductStmlDate);
	
	/**
	 * 查询大连交行非冲正对账成功交易数据
	 * @param date	清算日期
	 * @return
	 */
	public List<OriginalDljhLst> queryDljhDzSucessOfNotRollBk(String date);
	
	/**
	 * 查询大连交行冲正对账成功交易数据
	 * @param date 清算日期
	 * @return
	 */
	public List<OriginalDljhLst> quertDljgDzSucessOfRollBk(String date);
}