package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.TimingTaskConf;

public interface TimingTaskConfDAO {
	public cn.com.chinaebi.dz.object.TimingTaskConf get(java.lang.Integer key);

	public cn.com.chinaebi.dz.object.TimingTaskConf load(java.lang.Integer key);

	public java.util.List<cn.com.chinaebi.dz.object.TimingTaskConf> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param timingTaskConf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Integer save(cn.com.chinaebi.dz.object.TimingTaskConf timingTaskConf);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param timingTaskConf a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.TimingTaskConf timingTaskConf);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param timingTaskConf a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.TimingTaskConf timingTaskConf);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Integer id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param timingTaskConf the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.TimingTaskConf timingTaskConf);
	
	/**
	 * 获取某个定时任务对象值
	 * @param whereColumn ：数据库where查询字段-该字段是映射属性名称
	 * @param value ：值
	 * @return TimingTaskConf
	 */
	public TimingTaskConf getDzHandlerTime(String whereColumn,String value);
	
	/**
	 * 获取某个定时任务对象所需要的字段值
	 * @param whereColumn ：数据库where查询字段-该字段是数据库字段名称
	 * @param value ：值
	 * @param objects ：需要返回的字段值
	 * @return
	 */
	public Object getReturnColumn(String whereColumn,String value,String returnColumn);
	/**
	 * 通过渠道id获得配置定时任务
	 * @param channel_id
	 * @return
	 */
	public List<TimingTaskConf> getTimingTaskConfListByChannelId(int channel_id,int inst_type);
	
	/**
	 * 通过对账定时任务名称获取渠道ID和渠道类型
	 * @param dzHandleName  对账定时任务名称
	 * @return
	 */
	public TimingTaskConf getInstIdAntInstType(String dzHandleName);


}