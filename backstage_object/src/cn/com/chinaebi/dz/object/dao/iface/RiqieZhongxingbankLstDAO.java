package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.RiqieCupsLst;
import cn.com.chinaebi.dz.object.RiqieZhongxingbankLst;

public interface RiqieZhongxingbankLstDAO {
	public cn.com.chinaebi.dz.object.RiqieZhongxingbankLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.RiqieZhongxingbankLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.RiqieZhongxingbankLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param riqieZhongxingbankLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.RiqieZhongxingbankLst riqieZhongxingbankLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param riqieZhongxingbankLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RiqieZhongxingbankLst riqieZhongxingbankLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param riqieZhongxingbankLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RiqieZhongxingbankLst riqieZhongxingbankLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param riqieZhongxingbankLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RiqieZhongxingbankLst riqieZhongxingbankLst);
	
	
	public boolean updateClean(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,boolean deductRollBk,String deductStmlDate);
	public boolean updateClean(String reqSysStance, boolean clean,Integer tradeMsgType,String deductStmlDate);
	
	/**
	 * 修改数据不需要清算，差错处理
	 * @param reqSysStance
	 * @param clean
	 * @param whetherErroeHandle
	 * @return
	 */
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,boolean deductRollBk,String deductStlmDate);
	public boolean updateCleanAndError(String reqSysStance, boolean clean,Integer whetherErroeHandle,Integer tradeMsgType,String deductStlmDate);
	public List<RiqieZhongxingbankLst> findRiqieZhongxingbankLst(String deductStlmDate);

	public boolean saveRiqieZhongxing(RiqieZhongxingbankLst riqieZhongxingbankLst,Integer flagStatus);

}