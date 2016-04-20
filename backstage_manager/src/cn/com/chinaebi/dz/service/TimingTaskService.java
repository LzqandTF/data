package cn.com.chinaebi.dz.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.TimingTaskConf;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.webservice.TimingTaskDzFileConfType;
import cn.com.chinaebi.dz.webservice.TimingTaskDzFileGenerateType;
import cn.com.chinaebi.dz.webservice.TimingTaskDzHandlerConfType;
import cn.com.chinaebi.dz.webservice.TimingTaskSummaryDataConfType;

public class TimingTaskService {
	
	private static final Log log = LogFactory.getLog(TimingTaskService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.InstInfoDAO instInfoDAO = cn.com.chinaebi.dz.object.dao.InstInfoDAO.getInstance();
	
	/**
	 * 修改对账单解析定时任务方法
	 * @param type 接口类型
	 * @return
	 */
	public static boolean dzFileParseTimingTaskHandle(TimingTaskDzFileConfType type) throws Exception{
		
		boolean flag = false;
		
		try {
			Map<Integer, Object> map = Backstage.getInstance().getTimingTaskConfMap();

			boolean active = instInfoDAO.getInstStatusByInstId(type.getInstId(),type.getInstType());
			log.info("当前渠道是否开通 : "+active);
			if(active){//判断该渠道是否处于开通状态
				Object obj = map.get(type.getId());
				if(obj != null){//判断obj是否为空 ……
					TimingTaskConf taskConf = (TimingTaskConf)obj;
					if(StringUtils.equals(type.getAcquisitionName(), taskConf.getAcquisitionTimeName())){//判断名称是否一致 ……
						taskConf.setDzHandlerTime(type.getAcquisitionTime());
						map.put(type.getId(), taskConf);
					}
					log.info("修改对账处理定时任务信息 ……");
					flag =  Backstage.getInstance().updateTimingTaskConf(type.getId(), type.getAcquisitionName(), type.getAcquisitionTime());
					log.info("修改对账处理定时任务信息 ……"+(flag?"成功":"失败"));
				}else{
					log.info("添加对账处理定时任务信息 ……");
					Backstage.getInstance().initTimingTaskConf();
					flag = Backstage.getInstance().addTimingTaskConf(type.getAcquisitionName(), type.getAcquisitionTime());
					log.info("添加对账处理定时任务信息 ……"+(flag?"成功":"失败"));
				}
			}else{//后台不做任何处理，直接返回成功标识
				log.info("渠道处于关闭状态");
				flag = true;
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		
		return flag;
	}
	
	/**
	 * 修改对账操作定时任务方法
	 * @param type  接口类型
	 * @return
	 */
	public static boolean duiZhangTimingTaskHandle(TimingTaskDzHandlerConfType type) throws Exception{
		
		boolean flag = false;
		
		try {
			Map<Integer, Object> map = Backstage.getInstance().getTimingTaskConfMap();
			
			boolean active = instInfoDAO.getInstStatusByInstId(type.getInstId(),type.getInstType()); 
			
			log.info("当前渠道是否开通 : "+active);
			
			if(active){//判断该渠道是否处于开通状态
				Object obj = map.get(type.getId());
				if(obj != null){//判断obj是否为空 ……
					TimingTaskConf taskConf = (TimingTaskConf)obj;
					if(StringUtils.equals(type.getDzHandlerName(), taskConf.getDzHandlerTimeName())){//判断名称是否一致 ……
						taskConf.setDzHandlerTime(type.getDzHandlerTime());
						map.put(type.getId(), taskConf);
					}
					flag =  Backstage.getInstance().updateTimingTaskConf(type.getId(), type.getDzHandlerName(), type.getDzHandlerTime());
				}else{
					Backstage.getInstance().initTimingTaskConf();
					flag = Backstage.getInstance().addTimingTaskConf(type.getDzHandlerName(), type.getDzHandlerTime());
				}
			}else{
				flag = true;
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		
		return flag;
	}
	
	/**
	 * 修改数据汇总定时任务方法
	 * @param type 接口类型
	 * @return
	 */
	public static boolean summaryDataTimingTaskHandle(TimingTaskSummaryDataConfType type) throws Exception{
		boolean flag = false;
		try {
			Map<Integer, Object> map = Backstage.getInstance().getTimingTaskConfMap();
			if(map != null){
				Object obj = map.get(type.getInstId());
				if(obj != null){
					TimingTaskConf taskConf = (TimingTaskConf)obj;
					if(StringUtils.equals(type.getGatherDataName(), taskConf.getGatherDataTimeName())){
						log.info("更新内存……");
						taskConf.setGatherDataTime(type.getGatherDataTime());
						map.put(type.getInstId(), taskConf);
						log.info("修改原始交易数据定时任务信息");
						flag =  Backstage.getInstance().updateTimingTaskConf(type.getInstId(), type.getGatherDataName(), type.getGatherDataTime());
						log.info("修改原始交易数据定时任务信息 "+(flag?"成功":"失败"));
					}else{
						log.error(taskConf.getGatherDataTimeName() + " 名称不一致");
					}
				}else{
					log.info("添加原始交易数据定时任务信息 ……");
					Backstage.getInstance().initTimingTaskConf();
					flag = Backstage.getInstance().addTimingTaskConf(type.getGatherDataName(), type.getGatherDataTime());
					log.info("添加原始交易数据定时任务信息 ……"+(flag?"成功":"失败"));
				}
				
			}else{
				log.info("map 对象为空");
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return flag;
	}
	
	/**
	 * 修改对账文件生成定时任务方法
	 * @param type 接口类型
	 * @return
	 */
	public static boolean dzFileGenerateTimingTaskHandle(TimingTaskDzFileGenerateType type) throws Exception{
		boolean flag = false;
		try {
			Map<Integer, Object> map = Backstage.getInstance().getTimingTaskConfMap();
			if(map != null){
				Object obj = map.get(type.getInstId());
				if(obj != null){
					TimingTaskConf taskConf = (TimingTaskConf)obj;
					if(StringUtils.equals(type.getDzFileHandlerName(), taskConf.getDzFileCreateName())){
						taskConf.setDzFileCreateTime(type.getDzFileHandlerTime());
						map.put(type.getInstId(), taskConf);
						log.info("修改对账文件生成定时任务信息");
						flag =  Backstage.getInstance().updateTimingTaskConf(type.getInstId(), type.getDzFileHandlerName(), type.getDzFileHandlerTime());
						log.info("修改对账文件生成定时任务信息 "+(flag?"成功":"失败"));
					}else{
						log.error(taskConf.getDzFileCreateName() + " 名称不一致");
					}
				}else{
					log.info("添加对账文件生成定时任务信息 ……");
					Backstage.getInstance().initTimingTaskConf();
					flag = Backstage.getInstance().addTimingTaskConf(type.getDzFileHandlerName(), type.getDzFileHandlerTime());
					log.info("添加对账文件生成定时任务信息 ……"+(flag?"成功":"失败"));
				}
				
			}else{
				log.info("map 对象为空");
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return flag;
	}
}
