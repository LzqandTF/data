package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.YlcupsErrorEntry;

public interface YlcupsErrorEntryDAO {
	public cn.com.chinaebi.dz.object.YlcupsErrorEntry get(java.lang.String key);

	public cn.com.chinaebi.dz.object.YlcupsErrorEntry load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.YlcupsErrorEntry> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param ylcupsErrorEntry a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.YlcupsErrorEntry ylcupsErrorEntry);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param ylcupsErrorEntry a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.YlcupsErrorEntry ylcupsErrorEntry);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param ylcupsErrorEntry a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.YlcupsErrorEntry ylcupsErrorEntry);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param ylcupsErrorEntry the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.YlcupsErrorEntry ylcupsErrorEntry);
	
	/**
	 * 根据日期查询原始差错数据(包含线上/线下)
	 * @param errorDataReqTime ：日期
	 * @return List<YlcupsErrorEntry>
	 */
	public List<YlcupsErrorEntry> findYlcupsErrorEntryLst(String errorDataReqTime);
	
	/**
	 * 更新线下原始交易数据对账状态
	 * @param reqSysStance ：流水号
	 * @param duizhangFlag ：对账状态
	 * @return boolean
	 */
	public boolean updateClean(String reqSysStance,int duizhangFlag);
	
	/**
	 * 更新线上原始交易数据对账状态
	 * @param deductSysReference ：流水号,对账单中是参考号，不足12位右补0
	 * @param duizhangFlag ：对账状态
	 * @return boolean
	 */
	public boolean rytUpdateClean(String deductSysReference,int duizhangFlag);
	
	/**
	 * 根据流水号查询银联CUPS原始差错数据
	 * @param reqSysStance ：流水号
	 * @return boolean
	 */
	public boolean findYlcupsErrorEntry(String reqSysStance);
	
	/**
	 * 根据流水号查询银联UPMP原始差错数据
	 * @param deductSysReference : 流水号,对账单中是参考号，不足12位右补0
	 * @return boolean
	 */
	public boolean findRytYlcupsErrorEntry(String deductSysReference);


}