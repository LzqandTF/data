/**
 * 定时任务信息配置类
 * @author Li.Jikui
 * @date 2014-2-25
 */
package com.chinaebi.service;
import java.util.List;

import com.chinaebi.entity.TimingTaskConf;

public interface TimingTaskConfService {
	
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
	 * 删除定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	public boolean delTimingTaskConfById(int instId);
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
	 * 添加执行对账功能的定时任务
	 * @param instInfoName
	 * @param start_hour
	 * @param start_minute
	 * @param end_hour
	 * @param end_minute
	 * @return
	 */
	public int addDzTimingTask(int inst_id,int inst_type, String inst_name,String start_hour,
			String start_minute, String end_hour, String end_minute);
	/**
	 * 查询各渠道获取对账文件时间配置信息
	 * @return
	 */
	public List<TimingTaskConf> queryAcquisitionTimingTask();

	/**
	 * 更新后台系统内存原始交易数据汇总定时任务配置
	 * @param timingTaskConf_
	 * @return
	 */
	public String modifyRAMOriginalData(TimingTaskConf timingTaskConf_);
	
	/**
	 * 通过对账处理定时任务名称找到定时任务对象
	 * @param dzHandlerTimeName
	 * @return
	 */
	public TimingTaskConf queryDzTimingTaskByChannelInfo(int channel_id,int inst_type);
	/**
	 * 通过对账文件解析定时任务名称找到定时任务对象
	 * @param acquisitionTimeName
	 * @return
	 */
	public TimingTaskConf queryAcquisitionTimingTaskByName(String acquisitionTimeName);
	/**
	 * 更新后台系统内存对账定时任务配置
	 * @param timingTaskConf_
	 * @return
	 */
	public String modifyRAMDzData(TimingTaskConf timingTaskConf);

	/**
	 * 更新后台系统内存对账文件解析定时任务配置
	 * @param timingTaskConf_new
	 * @return
	 */
	public String modifyRAMAcquisitionData(TimingTaskConf timingTaskConf);
	/**
	 * 通过对账文件生成定时任务名称找到定时任务对象
	 * @param dzFileCreateTimeName
	 * @return
	 */
	public TimingTaskConf queryDzFileCreateTimingTask(String dzFileCreateTimeName);

	/**
	 * 更新后台系统内存对账文件生成定时任务配置
	 * @param timingTaskConf
	 * @return
	 */
	public String modifyDzFileCreateTimeRAM(TimingTaskConf timingTaskConf);
	
}
