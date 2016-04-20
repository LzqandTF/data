package com.chinaebi.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.TimingTaskConf;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.service.TimingTaskConfService;
import com.chinaebi.utils.ReturnXmlHandler;
import com.chinaebi.utils.StringUtils;

@Controller
public class TimingTaskConfController {
	protected Logger log = LoggerFactory.getLogger(TimingTaskConfController.class);
	@Autowired
	@Qualifier(value = "timingTaskConfService")
	private TimingTaskConfService timingTaskConfService;
	
	@Autowired
	@Qualifier(value="instInfoService")
	private InstInfoService instInfoService;
	
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	public static final String TIMING_TASK_CONF = "/sysConfig/timing_task_conf";
	public static final String ORIGINAL_TIMING_TASK = "/sysConfig/original_timing_task";
	public static final String DZ_TIMING_TASK = "/sysConfig/dz_timing_task";
	public static final String DZ_DATA_TIMING_TASK = "/sysConfig/acquisition_timing_task";
	public static final String CREATE_DZ_FILE_TASK	= "/sysConfig/create_dz_file_timing_task";
	
	public static final String DELTIMINGTASKCONF = "/delTimingTaskConf.do";
	public static final String ADDORIGINALTIMINGTASK = "/addOriginalTimingTask.do";
	public static final String ORIGINALTIMINGTASK = "/originalTimingTask.do";
	public static final String DZTIMINGTASK = "/dzTimingTask.do";
	public static final String ADDDZTIMINGTASK = "/addDzTimingTask.do";
	public static final String UPDATEDZTIMINGTASK =  "/updateDzTimingTask.do";
	public static final String DZDATATIMINGTASK="/dzDataTime.do";
	public static final String ADDDZDATATIMINGTASK = "/addDzDataTimingTask.do";
	public static final String UPDATEDZDATATIMINGTASK = "/updateDzDataTimingTask.do";
	public static final String ADDCREATEDZFILETIMINGTASK = "/addCreateDzFileTimingTask.do";
	public static final String CREATEDZFILETASK	= "/createDzFileTimingTask.do";
	
	/**
	 * 删除定时任务信息配置
	 * @param instInfo
	 * @return
	 */
	@RequestMapping(value = DELTIMINGTASKCONF,method = RequestMethod.POST)
	public void delTimingTaskConf(ServletRequest request,ServletResponse response) throws Exception{
		String instId = request.getParameter("instId");
		if(StringUtils.isNotBlank(instId)){
			boolean flag = timingTaskConfService.delTimingTaskConfById(Integer.valueOf(instId));
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}
	}
	/**
	 * 添加交易数据汇总的定时任务配置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= ADDORIGINALTIMINGTASK, method = RequestMethod.POST)
	@ResponseBody
	public int addOriginalTimingTask(ServletRequest request,ServletResponse response){
		log.info("进入设置交易汇总定时任务方法……");
		String hour = request.getParameter("hour");
		String minute = request.getParameter("minute");
		String interval_minute = request.getParameter("interval_time");
		String most_times = request.getParameter("most_times");
		int effectNum  = 0;
		log.info("通过交易汇总定时任务名称到数据库中查询是否存在");
		TimingTaskConf timingTaskConf_ = timingTaskConfService.queryTimingTaskByName("originalTotalDataLst");
		if(timingTaskConf_ == null){
			log.info("不存在该定时任务名称指定的信息，则添加一条");
			TimingTaskConf timingTaskConf = new TimingTaskConf();
			timingTaskConf.setGatherDataMostTimes(Integer.valueOf(most_times));
			timingTaskConf.setGatherDataIntervalTime(interval_minute);
			timingTaskConf.setGatherDataTime("00"+" "+minute+" "+hour+" "+"* * ?");
			timingTaskConf.setGatherDataTimeName("originalTotalDataLst");
			timingTaskConf.setGatherDataTimeDesc(hour+":"+minute);
			effectNum = timingTaskConfService.addTimingTaskConf(timingTaskConf);
			log.info("定时任务修改成功，调用后台接口，更新内存数据");
			if(effectNum > 0){
				TimingTaskConf timingTaskConf_new = timingTaskConfService.queryTimingTaskByName("originalTotalDataLst");
				log.info("指定的定时任务"+timingTaskConf_new==null?"未找到":"存在，对内存中的数据进行更新");
				String xmlString  = timingTaskConfService.modifyRAMOriginalData(timingTaskConf_new);
				boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
				log.info("更新内存信息"+(flag?"成功":"失败"));
				if(!flag){
					effectNum = 0;
				}
			}
		}else{
			log.info("存在该定时任务名称指定的信息，则修改之前的配置信息");
			if(StringUtils.isNotBlank(most_times)){
				timingTaskConf_.setGatherDataMostTimes(Integer.valueOf(most_times));
			}
			log.info("设置汇总原始交易数据的间隔时间为："+interval_minute);
			timingTaskConf_.setGatherDataIntervalTime(interval_minute);
			timingTaskConf_.setGatherDataTime("00"+" "+minute+" "+hour+" "+"* * ?");
			log.info("更改汇总原始交易数据时间为："+"00"+" "+minute+" "+hour+" "+"* * ?");
			timingTaskConf_.setGatherDataTimeDesc(hour+":"+minute);
			log.info("设置汇总原始交易数据的任务描述为："+hour+":"+minute);
			try {
				String xmlString  = timingTaskConfService.modifyRAMOriginalData(timingTaskConf_);
				boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
				log.info("更新内存信息"+(flag?"成功":"失败"));
				if(flag){
					log.info("更新内存信息成功，执行更新数据库操作");
					effectNum = timingTaskConfService.updateTimingTaskConf(timingTaskConf_);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		return effectNum;
		
	}
	/**
	 * 跳转到设置交易数据汇总定时任务信息页面
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = ORIGINALTIMINGTASK)
	public String originalTimingTask(ServletRequest request,Model model) throws Exception{
		log.info("进入设置交易数据汇总定时任务信息方法…… ");
		TimingTaskConf timingTaskConf = timingTaskConfService.queryTimingTaskByName("originalTotalDataLst");
		String[] timeArr = null;
		String hour = "";
		String minute = "";
		if(timingTaskConf != null){
			if(StringUtils.isNotBlank(timingTaskConf.getGatherDataTimeDesc())){
				timeArr = timingTaskConf.getGatherDataTimeDesc().split(":");
				hour = timeArr[0];
				minute = timeArr[1];
			}
		}
		model.addAttribute("timingTaskConf", timingTaskConf);
		model.addAttribute("hour",hour);
		model.addAttribute("minute",minute);
		return ORIGINAL_TIMING_TASK;
	}
	/**
	 * 跳转到设置对账定时任务信息页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = DZTIMINGTASK)
	public String dzTimingTask(ServletRequest request,Model model){
		log.info("进入设置对账定时任务信息方法……");
		List<TimingTaskConf> dzTimingTasklist = timingTaskConfService.queryDzTimingTask();
		List<InstInfo> instInfolist = instInfoService.queryAll();
		model.addAttribute("dzTimingTasklist",dzTimingTasklist);
		model.addAttribute("instInfolist",instInfolist);
		return DZ_TIMING_TASK;
	}
	/**
	 * 添加对账定时任务配置
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = ADDDZTIMINGTASK,method = RequestMethod.POST)
	@ResponseBody
	public int addDzHandleTimingTask(ServletRequest request,ServletResponse response){
		log.info("进入添加对账定时任务配置方法……");
		int effectNum = 0;
		String instInfo = request.getParameter("instInfo");
		String start_hour = request.getParameter("start_hour");
		String start_minute = request.getParameter("start_minute");
		String end_hour = request.getParameter("end_hour");
		String end_minute = request.getParameter("end_minute");
		if(StringUtils.isNotBlank(instInfo)){
			String[] inst_info = instInfo.split(",");
			if(inst_info != null && inst_info.length == 2){
				
				InstInfo inst = dataManagerInit.getInstInfoById(Integer.valueOf(inst_info[0]),Integer.valueOf(inst_info[1]));
				
				effectNum = timingTaskConfService.addDzTimingTask(inst.getInstId(),inst.getInst_type(),inst.getName(),start_hour,start_minute,end_hour,end_minute);
				
				if(effectNum > 0){
					TimingTaskConf timingTaskConf = timingTaskConfService.queryDzTimingTaskByChannelInfo(inst.getInstId(), inst.getInst_type());
					String xmlString = timingTaskConfService.modifyRAMDzData(timingTaskConf);
					boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
					if(!flag){
						effectNum = 0;
					}
				}
			}
		}
		
		return effectNum;
	}
	/**
	 * 修改执行对账方法的时间配置
	 * @param request
	 * @param response
	 * @param timingTaskConf
	 * @return
	 */
	@RequestMapping(value=UPDATEDZTIMINGTASK,method=RequestMethod.POST)
	@ResponseBody
	public int updateDzTimingTask(ServletRequest request,ServletResponse response,TimingTaskConf timingTaskConf){
		log.info("进入修改对账定时任务配置方法……");
		int effectNum = 0;
		String startHour = request.getParameter("startHour");
		String startMinute = request.getParameter("startMinute");
		String endHour = request.getParameter("endHour");
		String endMinute = request.getParameter("endMinute");
		String dzHandlerTime = "00"+" "+startMinute+" "+startHour+" "+"* * ?";
		String dzHandlerEndTime = "00"+" "+endMinute+" "+endHour+" "+"* * ?";
		timingTaskConf.setDzHandlerTime(dzHandlerTime);
		timingTaskConf.setDzHandlerEndTime(dzHandlerEndTime);
		String xmlString = timingTaskConfService.modifyRAMDzData(timingTaskConf);
		boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
		if(flag){
			effectNum = timingTaskConfService.updateTimingTaskConf(timingTaskConf);
		}
		log.info("修改对账定时任务"+(effectNum==0?"失败":"成功"));
		return effectNum;
	}
	/**
	 * 跳转到对账文件获取定时任务配置方法
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=DZDATATIMINGTASK)
	public String dzDataTimingTask(ServletRequest request,Model model){
		log.info("进入对账文件获取定时任务配置方法……");
		List<TimingTaskConf> list = timingTaskConfService.queryAcquisitionTimingTask();
		model.addAttribute("dzDataTimingTaskList", list);
		List<InstInfo> instInfolist = instInfoService.queryAll();
		model.addAttribute("instInfolist",instInfolist);
		return DZ_DATA_TIMING_TASK;
	}
	@RequestMapping(value=ADDDZDATATIMINGTASK,method=RequestMethod.POST)
	@ResponseBody
	public int addDzFileHandleTimingTask(ServletRequest request,ServletResponse response,TimingTaskConf timingTaskConf) throws Exception{
		log.info("进入获取对账文件定时任务配置方法……");
		int effectNum = 0;
		String startHour = request.getParameter("start_hour");
		String startMinute = request.getParameter("start_minute");
		String inst_info = request.getParameter("inst_info");
		String acquisitionTime = "00"+" "+startMinute+" "+startHour+" "+"* * ?";
		String name = "";
		if(timingTaskConf != null){
			timingTaskConf.setAcquisitionTime(acquisitionTime);
			if(StringUtils.isNotBlank(inst_info)){
				String[] instInfo = inst_info.split(",");
				if(instInfo != null){
					InstInfo inst = dataManagerInit.getInstInfoById(Integer.valueOf(instInfo[0]),Integer.valueOf(instInfo[1]));
					if(70001 == inst.getInstId() && 0 == inst.getInst_type()){
						timingTaskConf.setAcquisitionTimeName("beijingParsing");
						name = "beijingParsing";
					}else if(10 == inst.getInstId() && 0 == inst.getInst_type()){
						timingTaskConf.setAcquisitionTimeName("zhongxinParsing");
						name = "zhongxinParsing";
					}else if(11 == inst.getInstId() && 0 == inst.getInst_type()){
						if("一般对账文件".equals(timingTaskConf.getDzFileType())){
							timingTaskConf.setAcquisitionTimeName("cupsParsing");
							name = "cupsParsing";
						}else{
							timingTaskConf.setAcquisitionTimeName("errorCupsParsing");
							name = "errorCupsParsing";
						}
					}
					
					timingTaskConf.setChannel_id(inst.getInstId());
					timingTaskConf.setInst_type(inst.getInst_type());
				}
			}
			
			effectNum = timingTaskConfService.addTimingTaskConf(timingTaskConf);
			if(effectNum > 0){
				TimingTaskConf timingTaskConf_new = timingTaskConfService.queryAcquisitionTimingTaskByName(name);
				String xmlString = timingTaskConfService.modifyRAMAcquisitionData(timingTaskConf_new);
				boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
				if(!flag){
					effectNum = 0;
				}
			}
		}
		log.info("添加获取对账文件定时任务"+(effectNum==0?"失败":"成功"));
		return effectNum;
	}
	@RequestMapping(value=UPDATEDZDATATIMINGTASK,method=RequestMethod.POST)
	@ResponseBody
	public int updateDzDataTimingTask(ServletRequest request,TimingTaskConf timingTaskConf){
		log.info("进入修改获取对账文件定时任务配置……");
		int effectNum = 0;
		String startHour = request.getParameter("start_hour");
		String startMinute = request.getParameter("start_minute");
		String acquisitionTime = "00"+" "+startMinute+" "+startHour+" "+"* * ?";
		if(timingTaskConf != null){
			timingTaskConf.setAcquisitionTime(acquisitionTime);
			TimingTaskConf timingTaskConf_new = timingTaskConfService.queryAcquisitionTimingTaskByName(timingTaskConf.getAcquisitionTimeName());
			timingTaskConf_new.setAcquisitionTime(acquisitionTime);//设置新的时间
			String xmlString = timingTaskConfService.modifyRAMAcquisitionData(timingTaskConf_new);
			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				effectNum = timingTaskConfService.updateTimingTaskConf(timingTaskConf);
			}
		}
		log.info("添加获取对账文件定时任务"+(effectNum==0?"失败":"成功"));
		return effectNum;
	}
	
	@RequestMapping(value = CREATEDZFILETASK)
	public String createDzFileTimingTask(ServletRequest request,Model model) throws Exception{
		log.info("进入设置交易数据汇总定时任务信息方法…… ");
		TimingTaskConf timingTaskConf = timingTaskConfService.queryDzFileCreateTimingTask("dzFileCreate");
		String[] timeArr = null;
		String hour = "";
		String minute = "";
		if(timingTaskConf != null){
			if(StringUtils.isNotBlank(timingTaskConf.getDzFileCreateTime())){
				timeArr = timingTaskConf.getDzFileCreateTime().split(" ");
				hour = timeArr[2];
				minute = timeArr[1];
			}
		}
		model.addAttribute("timingTaskConf", timingTaskConf);
		model.addAttribute("hour",hour);
		model.addAttribute("minute",minute);
		return CREATE_DZ_FILE_TASK;
	}
	
	@RequestMapping(value=ADDCREATEDZFILETIMINGTASK,method=RequestMethod.POST)
	@ResponseBody
	public int addCreateDzFileTimingTask(ServletRequest request){
		int effectNum = 0;
		String hour = request.getParameter("hour");
		String minute = request.getParameter("minute");
		
		TimingTaskConf timingTaskConf = timingTaskConfService.queryDzFileCreateTimingTask("dzFileCreate");
		if(timingTaskConf == null){
			log.info("不存在该定时任务名称指定的信息，则添加一条");
			timingTaskConf = new TimingTaskConf();
			timingTaskConf.setDzFileCreateTime("00"+" "+minute+" "+hour+" "+"* * ?");
			timingTaskConf.setDzFileCreateTimeName("dzFileCreate");
			effectNum = timingTaskConfService.addTimingTaskConf(timingTaskConf);
			log.info("定时任务修改成功，调用后台接口，更新内存数据");
			if(effectNum > 0){
				TimingTaskConf timingTaskConf_new = timingTaskConfService.queryDzFileCreateTimingTask("dzFileCreate");
				log.info("指定的定时任务"+timingTaskConf_new==null?"未找到":"存在，对内存中的数据进行更新");
				String xmlString  = timingTaskConfService.modifyDzFileCreateTimeRAM(timingTaskConf_new);
				boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
				log.info("更新内存信息"+(flag?"成功":"失败"));
				if(!flag){
					effectNum = 0;
				}
			}
		}else{
			timingTaskConf.setDzFileCreateTime("00"+" "+minute+" "+hour+" "+"* * ?");
			timingTaskConf.setDzFileCreateTimeName("dzFileCreate");
			log.info("设置汇总原始交易数据的任务描述为："+hour+":"+minute);
			try {
				String xmlString  = timingTaskConfService.modifyDzFileCreateTimeRAM(timingTaskConf);
				boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
				log.info("更新内存信息"+(flag?"成功":"失败"));
				if(flag){
					log.info("更新内存信息成功，执行更新数据库操作");
					effectNum = timingTaskConfService.updateTimingTaskConf(timingTaskConf);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			if(effectNum > 0){
				log.info("添加对账文件生成定时任务成功");
			}else{
				log.info("添加对账文件生成定时任务失败");
			}
		}
		
		return effectNum;
	}
}
