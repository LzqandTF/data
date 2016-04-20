package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.chinaebi.dz.object.ChannelTradeCollect;


public interface ChannelTradeCollectDAO {
	public cn.com.chinaebi.dz.object.ChannelTradeCollect get(java.lang.String key);

	public cn.com.chinaebi.dz.object.ChannelTradeCollect load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.ChannelTradeCollect> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param channelTradeCollect a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.ChannelTradeCollect channelTradeCollect);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param channelTradeCollect a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.ChannelTradeCollect channelTradeCollect);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param channelTradeCollect a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.ChannelTradeCollect channelTradeCollect);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param channelTradeCollect the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.ChannelTradeCollect channelTradeCollect);
	
	/**
	 * 获取历史交易数据分页
	 * @param map
	 * @return
	 */
	public List<ChannelTradeCollect> getHistoryChannelTradeCollectPageData(Map<String,String[]> map) throws Exception;
	/**
	 * 获取历史交易数据
	 * @param map
	 * @return
	 */
	public List<ChannelTradeCollect> getHistoryChannelTradeCollectDataList(Map<String,String[]> map) throws Exception;
	/**
	 * 获取当天交易数据分页
	 * @param map
	 * @return
	 */
	public List<ChannelTradeCollect> getTodayChannelTradeCollectPageData(Map<String,String[]> map) throws Exception;
	/**
	 * 获取当天交易数据
	 * @param map
	 * @return
	 */
	public List<ChannelTradeCollect> getTodayChannelTradeCollectDataList(Map<String,String[]> map) throws Exception;


}