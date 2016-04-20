package com.chinaebi.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.TimingTaskConfDao;
import com.chinaebi.entity.TimingTaskConf;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "timingTaskConfDao")
public class TimingTaskConfDaoImpl extends MyBatisDao implements TimingTaskConfDao{

	/**
	 * 添加定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	@Override
	public int addTimingTaskConf(TimingTaskConf timingTaskConf) {
		return getSqlSession().insert("TimingTaskConf.addTimingTaskConf",timingTaskConf);
	}
	/**
	 * 修改定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	@Override
	public int updateTimingTaskConf(TimingTaskConf timingTaskConf) {
		return getSqlSession().update("TimingTaskConf.updateTimingTaskConf",timingTaskConf);
	}
	/**
	 * 分页查询
	 * @param map
	 * @param page
	 * @return
	 */
	@Override
	public Page<TimingTaskConf> queryPageTimingTaskConf(
			Map<String, Object> map, Page<TimingTaskConf> page) {
		return selectPage(page, "TimingTaskConf.queryPageTimingTaskConf","TimingTaskConf.queryPageTimingTaskConfCount",map);
	}
	/**
	 * 删除定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	@Override
	public int delTimingTaskConfById(int instId) {
		return getSqlSession().delete("TimingTaskConf.deleteTimingTaskConf", instId);
	}
	/**
	 * 通过定时任务名称获取对象信息
	 * @param name
	 * @return
	 */
	public TimingTaskConf queryTimingTaskByName(String name){
		return (TimingTaskConf) getSqlSession().selectOne("TimingTaskConf.queryTimingTaskByName", name);
	}
	/**
	 * 查询各渠道对账时间配置信息
	 * @return
	 */
	public List<TimingTaskConf> queryDzTimingTask(){
		return getSqlSession().selectList("TimingTaskConf.queryDzTimingTask");
	}
	/**
	 * 查询各渠道获取对账文件时间配置信息
	 * @return
	 */
	public List<TimingTaskConf> queryAcquisitionTimingTask(){
		return getSqlSession().selectList("TimingTaskConf.queryAcquisitionTimingTask");
	}
	/**
	 * 通过对账处理定时任务名称找到定时任务对象
	 * @param dzHandlerTimeName
	 * @return
	 */
	public TimingTaskConf queryDzTimingTaskByChannelInfo(Map<String,Integer> map){
		return (TimingTaskConf) getSqlSession().selectOne("TimingTaskConf.queryDzTimingTaskByName", map);
	}
	/**
	 * 通过对账文件解析定时任务名称找到定时任务对象
	 * @param acquisitionTimeName
	 * @return
	 */
	public TimingTaskConf queryAcquisitionTimingTaskByName(String acquisitionTimeName){
		return (TimingTaskConf) getSqlSession().selectOne("TimingTaskConf.queryAcquisitionTimingTaskByName", acquisitionTimeName);
	}
	/**
	 * 通过对账文件生成定时任务名称找到定时任务对象
	 * @param dzFileCreateTimeName
	 * @return
	 */
	public TimingTaskConf queryDzFileCreateTimingTask(String dzFileCreateTimeName){
		return (TimingTaskConf) getSqlSession().selectOne("TimingTaskConf.queryDzFileCreateTimingTask",dzFileCreateTimeName);
	}
}
