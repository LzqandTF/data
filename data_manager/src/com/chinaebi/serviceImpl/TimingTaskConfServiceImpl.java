package com.chinaebi.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfDocument;
import cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType;
import cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateDocument;
import cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType;
import cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfDocument;
import cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType;
import cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfDocument;
import cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType;

import com.chinaebi.dao.TimingTaskConfDao;
import com.chinaebi.entity.TimingTaskConf;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.TimingTaskConfService;
import com.chinaebi.utils.HttpUtil;
import com.chinaebi.utils.RequestUrlConf;

@Component(value = "timingTaskConfService")
public class TimingTaskConfServiceImpl implements TimingTaskConfService{
	
	protected Logger log = LoggerFactory.getLogger(TimingTaskConfServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "timingTaskConfDao")
	private TimingTaskConfDao timingTaskConfDao;

	/**
	 * 添加定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	@Override
	public int addTimingTaskConf(TimingTaskConf timingTaskConf) {
		int effectNum = 0;
		try{
			effectNum = timingTaskConfDao.addTimingTaskConf(timingTaskConf);
			if(!(effectNum > 0)){
				throw new InsertException("timingTaskConfDao.addTimingTaskConf(timingTaskConf)  定时任务配置信息添加失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	/**
	 * 修改定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	@Override
	public int updateTimingTaskConf(TimingTaskConf timingTaskConf) {
		int effectNum = 0;
		try{
			effectNum = timingTaskConfDao.updateTimingTaskConf(timingTaskConf);
			if(!(effectNum > 0)){
				throw new UpdateException("timingTaskConfDao.updateTimingTaskConf(timingTaskConf)  定时任务配置信息修改失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}


	/**
	 * 删除定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	@Override
	public boolean delTimingTaskConfById(int instId) {
		try{
			int effectNum = timingTaskConfDao.delTimingTaskConfById(instId);
			if(effectNum > 0){
				return true;
			}else{
				throw new DeleteException("timingTaskConfDao.delTimingTaskConfById(instId)  定时任务配置信息删除失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return false;
	}
	/**
	 * 通过定时任务名称获取对象信息
	 * @param name
	 * @return
	 */
	public TimingTaskConf queryTimingTaskByName(String name){
		TimingTaskConf timingTaskConf = null;
		try{
			timingTaskConf = timingTaskConfDao.queryTimingTaskByName(name);
			if(timingTaskConf == null){
				throw new SelectException("timingTaskConfDao.queryTimingTaskByName(name)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return timingTaskConf;
	}
	/**
	 * 查询各渠道对账时间配置信息
	 * @return
	 */
	public List<TimingTaskConf> queryDzTimingTask(){
		List<TimingTaskConf> list = new ArrayList<TimingTaskConf>();
		try{
			list = timingTaskConfDao.queryDzTimingTask();
			if(list == null || list.size() <=0){
				throw new SelectException("timingTaskConfDao.queryDzTimingTask() 查询结果为NUL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	/**
	 * 添加执行对账的定时任务
	 */
	@Override
	public int addDzTimingTask(int channel_id,int inst_type, String inst_name,String start_hour,
			String start_minute, String end_hour, String end_minute) {
		int effectNum = 0;
		try{
			String dzHandlerTime = "00"+" "+start_minute+" "+start_hour+" "+"* * ?";
			String dzHandlerEndTime = "00"+" "+end_minute+" "+end_hour+" "+"* * ?";
			TimingTaskConf timingTaskConf = new TimingTaskConf();
			if(channel_id == 70001 && inst_type == 0){
				timingTaskConf.setDzHandlerTimeName("dzBeijingTask");
			}else if(channel_id == 10 && inst_type == 0){
				timingTaskConf.setDzHandlerTimeName("dzZhongxinTask");
			}else if(channel_id == 11 && inst_type == 0){
				timingTaskConf.setDzHandlerTimeName("dzCupsTask");
			}else if(channel_id == 55001 && inst_type == 1){
				timingTaskConf.setDzHandlerTimeName("dzRytUpmpTask");
			}
			timingTaskConf.setDzHandlerTime(dzHandlerTime);
			timingTaskConf.setDzHandlerEndTime(dzHandlerEndTime);
			timingTaskConf.setChannel_id(channel_id);
			timingTaskConf.setInst_type(inst_type);
			timingTaskConf.setDzHandlerTimeDesc(inst_name);
			effectNum = timingTaskConfDao.addTimingTaskConf(timingTaskConf);
			if(effectNum == 0){
				throw new InsertException("timingTaskConfDao.addTimingTaskConf(timingTaskConf) 添加执行对账定时任务失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
	/**
	 * 查询各渠道获取对账文件时间配置信息
	 * @return
	 */
	public List<TimingTaskConf> queryAcquisitionTimingTask(){
		List<TimingTaskConf> list = new ArrayList<TimingTaskConf>();
		try{
			list = timingTaskConfDao.queryAcquisitionTimingTask();
			if(list==null || list.size()<=0){
				throw new SelectException("timingTaskConfDao.queryAcquisitionTimingTask() 查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	/**
	 * 更新后台系统内存原始交易数据汇总定时任务配置
	 * @param timingTaskConf_
	 * @return
	 */
	public String modifyRAMOriginalData(TimingTaskConf timingTaskConf_){
		String xmlString = null;
		try {
			log.info("进入更新内存数据方法……");
			TimingTaskSummaryDataConfDocument document = TimingTaskSummaryDataConfDocument.Factory.newInstance();
			TimingTaskSummaryDataConfType type = document.addNewTimingTaskSummaryDataConf();
			type.setGatherDataName(timingTaskConf_.getGatherDataTimeName());
			type.setGatherDataTime(timingTaskConf_.getGatherDataTime());
			type.setInstId(timingTaskConf_.getInstId());
			type.setTrace("8899");
			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return xmlString;
	}
	/**
	 * 通过对账处理定时任务名称找到定时任务对象
	 * @param dzHandlerTimeName
	 * @return
	 */
	public TimingTaskConf queryDzTimingTaskByChannelInfo(int channel_id,int inst_type){
		TimingTaskConf timingTaskConf = null;
		try{
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("channel_id",channel_id);
			map.put("inst_type", inst_type);
			timingTaskConf = timingTaskConfDao.queryDzTimingTaskByChannelInfo(map);
			if(timingTaskConf == null){
				throw new SelectException("timingTaskConfDao.queryDzTimingTaskByName(dzHandlerTimeName)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return timingTaskConf;
	}
	/**
	 * 通过对账文件解析定时任务名称找到定时任务对象
	 * @param acquisitionTimeName
	 * @return
	 */
	public TimingTaskConf queryAcquisitionTimingTaskByName(String acquisitionTimeName){
		TimingTaskConf timingTaskConf = null;
		try{
			timingTaskConf = timingTaskConfDao.queryAcquisitionTimingTaskByName(acquisitionTimeName);
			if(timingTaskConf == null){
				throw new SelectException("timingTaskConfDao.queryAcquisitionTimingTaskByName(acquisitionTimeName)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return timingTaskConf;
	}
	/**
	 * 更新后台系统内存对账处理定时任务配置
	 * @param timingTaskConf_
	 * @return
	 */
	@Override
	public String modifyRAMDzData(TimingTaskConf timingTaskConf) {
		String xmlString = "";
		log.info("进入修改内存对账处理定时任务配置方法……");
		try {
			TimingTaskDzHandlerConfDocument document = TimingTaskDzHandlerConfDocument.Factory.newInstance();
			TimingTaskDzHandlerConfType type = document.addNewTimingTaskDzHandlerConf();
			type.setDzHandlerName(timingTaskConf.getDzHandlerTimeName());
			type.setDzHandlerTime(timingTaskConf.getDzHandlerTime());
			type.setTrace("09999");
			type.setId(timingTaskConf.getInstId());
			type.setInstId(timingTaskConf.getChannel_id());
			type.setInstType(timingTaskConf.getInst_type());
			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return xmlString ;
	}
	/**
	 * 更新后台系统内存对账文件解析定时任务配置
	 * @param timingTaskConf_new
	 * @return
	 */
	public String modifyRAMAcquisitionData(TimingTaskConf timingTaskConf){
		String xmlString = "";
		log.info("进入修改内存对账文件解析定时任务配置方法……");
		try{
			TimingTaskDzFileConfDocument document = TimingTaskDzFileConfDocument.Factory.newInstance();
			log.info("获取TimingTaskDzFileConfDocument对象"+document==null?"失败":"成功");
			TimingTaskDzFileConfType type = document.addNewTimingTaskDzFileConf();
			log.info("获取TimingTaskDzFileConfType对象"+document==null?"失败":"成功");
			type.setAcquisitionName(timingTaskConf.getAcquisitionTimeName());
			type.setAcquisitionTime(timingTaskConf.getAcquisitionTime());
			type.setId(timingTaskConf.getInstId());
			type.setInstId(timingTaskConf.getChannel_id());
			type.setInstType(timingTaskConf.getInst_type());
			type.setTrace("7777");
			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return xmlString;
	}
	/**
	 * 通过对账文件生成定时任务名称找到定时任务对象
	 * @param dzFileCreateTimeName
	 * @return
	 */
	public TimingTaskConf queryDzFileCreateTimingTask(String dzFileCreateTimeName){
		TimingTaskConf timingTaskConf = null;
		try{
			timingTaskConf = timingTaskConfDao.queryDzFileCreateTimingTask(dzFileCreateTimeName);
			if(timingTaskConf == null){
				throw new SelectException("timingTaskConfDao.queryDzFileCreateTimingTask(dzFileCreateTimeName)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return timingTaskConf;
	}
	/**
	 * 更新后台系统内存对账文件生成定时任务配置
	 * @param timingTaskConf
	 * @return
	 */
	public String modifyDzFileCreateTimeRAM(TimingTaskConf timingTaskConf){
		String xmlString = "";
		log.info("进入修改内存对账文件生成定时任务配置方法……");
		try{
			TimingTaskDzFileGenerateDocument document = TimingTaskDzFileGenerateDocument.Factory.newInstance();
			log.info("获取TimingTaskDzFileGenerateDocument对象"+document==null?"失败":"成功");
			TimingTaskDzFileGenerateType type = document.addNewTimingTaskDzFileGenerate();
			log.info("获取TimingTaskDzFileConfType对象"+type==null?"失败":"成功");
			type.setInstId(timingTaskConf.getInstId());
			type.setDzFileHandlerName(timingTaskConf.getDzFileCreateTimeName());
			type.setDzFileHandlerTime(timingTaskConf.getDzFileCreateTime());
			type.setTrace("55555");
			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return xmlString;
	}
}
