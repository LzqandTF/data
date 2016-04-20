package cn.com.chinaebi.dz.object.dao.iface;

import java.util.List;
import java.util.Map;

import cn.com.chinaebi.dz.object.DuizhangCupsLst;
import cn.com.chinaebi.dz.object.DuizhangDljhLst;
import cn.com.chinaebi.dz.object.DuizhangUpmpLst;


public interface DuizhangDljhLstDAO {
	public cn.com.chinaebi.dz.object.DuizhangDljhLst get(java.lang.String key);

	public cn.com.chinaebi.dz.object.DuizhangDljhLst load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.DuizhangDljhLst> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param duizhangDljhLst a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.DuizhangDljhLst duizhangDljhLst);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param duizhangDljhLst a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.DuizhangDljhLst duizhangDljhLst);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param duizhangDljhLst a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.DuizhangDljhLst duizhangDljhLst);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param duizhangDljhLst the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.DuizhangDljhLst duizhangDljhLst);
	
	/**
	 * 批量插入数据
	 * @param sql
	 * @return
	 */
	public boolean insertBankData(String insertSql,String deduct_stlm_date) throws Exception;
	
	/**
	 * 查询大连交行对账文件是否有数据
	 * @param reqTime ：查询日期
	 * @return boolean
	 */
	public boolean findDateData(String reqTime);
	
	/**
	 * 查询大连交行对账文件bkchk
	 * @param reqTime ：日期
	 * @param bkChk ：是否对账
	 * @return 
	 */
	public List<DuizhangDljhLst> findDateData(String reqTime,int bkChk);
	
	/**
	 * 查询对账文件数据
	 * @param reqSysStance : 流水号
	 * @param msgType ：交易类型
	 * @param cardNo ：卡号
	 * @return boolean
	 */
	public String findDzFileData(Object entity,Object[] oriObject,Object[] dzFileObjct,Integer instId,boolean isRiqie,String duizhangReqTime) throws Exception;

	/**
	 * 查询对账文件数据
	 * @param duizhangReqTime
	 * @return Map<String, String>
	 */
	public Map<String, String> findDzFileData(String duizhangReqTime) throws Exception;

	/**
	 * 修改对账文件对账成功
	 * @param reqSysStance
	 * @param duizhangFlag
	 * @param deductRollBk
	 * @param deductStmlDate
	 * @return
	 */
	public boolean updateClean(String reqSysStance, int duizhangFlag,boolean deductRollBk,String deductStmlDate);
	
	
	//public boolean updateCleanRiqie(String reqSysStance, boolean clean,Integer bkchk,Integer tradeMsgType,boolean deductRollBk,String deductStmlDate);
}