/**
 * 定时任务信息配置类
 * @author Li.Jikui
 * @date 2014-2-25
 */
package com.chinaebi.dao;
import java.util.List;
import java.util.Map;

import com.chinaebi.entity.TimingTaskConf;
import com.chinaebi.utils.mybaits.Page;

public interface TimingTaskConfDao {
	
	
	
	/**
	 * 添加定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	public int addTimingTaskConf(TimingTaskConf timingTaskConf);
	
	/**
	 * 修改定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	public int updateTimingTaskConf(TimingTaskConf timingTaskConf);
	
	/**
	 * 分页查询
	 * @param map
	 * @param page
	 * @return
	 */
	public Page<TimingTaskConf> queryPageTimingTaskConf(Map<String, Object> map,Page<TimingTaskConf> page);
		
	
	/**
	 * 删除定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	public int delTimingTaskConfById(int instId);
	/**
	 * 通过定时任务名称获取对象信息
	 * @param name
	 * @return
	 */
	public TimingTaskConf queryTimingTaskByName(String name);
	/**
	 * 查询各渠道对账时间配置信息
	 * @return
	 */
	public List<TimingTaskConf> queryDzTimingTask();
	/**
	 * 查询各渠道获取对账文件时间配置信息
	 * @return
	 */
	public List<TimingTaskConf> queryAcquisitionTimingTask();
	/**
	 * 通过对账处理定时任务名称找到定时任务对象
	 * @param dzHandlerTimeName
	 * @return
	 */
	public TimingTaskConf queryDzTimingTaskByChannelInfo(Map<String,Integer> map);
	/**
	 * 通过对账文件解析定时任务名称找到定时任务对象
	 * @param acquisitionTimeName
	 * @return
	 */
	public TimingTaskConf queryAcquisitionTimingTaskByName(String acquisitionTimeName);
	
	/**
	 * 通过对账文件生成定时任务名称找到定时任务对象
	 * @param dzFileCreateTimeName
	 * @return
	 */
	public TimingTaskConf queryDzFileCreateTimingTask(String dzFileCreateTimeName);
}
