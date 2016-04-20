package cn.com.chinaebi.task.quartz;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.com.chinaebi.task.quartz.iface.SchedulerService;

/**
 * quartz 定时任务处理(增删改)
 * @author zhu.hongliang
 */
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

	private static final Log log = LogFactory
			.getLog(SchedulerServiceImpl.class);

	private static String JOB_GROUP_NAME = "group1";
	private static String TRIGGER_GROUP_NAME = "trigger1";

	private Scheduler scheduler;
	private JobDetail jobDetail;

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Autowired
	public void setJobDetail(@Qualifier("jobDetail") JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	@Override
	public void schedule(String cronExpression) {
		schedule(null, cronExpression);
	}

	@Override
	public void schedule(String name, String cronExpression) {
		try {
			schedule(name, new CronExpression(cronExpression));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(CronExpression cronExpression) {
		schedule(null, cronExpression);
	}

	// [秒] [分] [小时] [日] [月] [周] [年]
	@Override
	public void schedule(String jobName, CronExpression cronExpression) {
		 try  {   
			 if(jobDetail != null){
				 jobDetail.setName(jobName);
				 jobDetail.setGroup(JOB_GROUP_NAME);
	             CronTrigger cronTrigger =  new  CronTrigger(jobName, TRIGGER_GROUP_NAME);
	             cronTrigger.setCronExpression(cronExpression);    
	             scheduler.scheduleJob(jobDetail,cronTrigger);
	             if(!scheduler.isShutdown())
	            	 scheduler.start();
			 }else{
				 log.error("定时任务  "+jobName+" jobDetail对象为空");
				 //System.out.println("定时任务  "+jobName+" jobDetail对象为空");
			 }
            // scheduler.rescheduleJob(name, Scheduler.DEFAULT_GROUP, cronTrigger);    
         }  catch  (SchedulerException e) {    
        	 log.error(e);
             throw new RuntimeException(e);    
         }    
	}

	public boolean removeJob(String jobName) throws SchedulerException {
		boolean flag = false;
		Trigger trigger = scheduler.getTrigger(jobName, TRIGGER_GROUP_NAME);
		if (trigger != null) {
			scheduler.deleteJob(jobName, JOB_GROUP_NAME);
			flag = true;
			log.info(jobName + " : 工作任务删除成功");
		}
		return flag;
	}

	public boolean modifyJob(String jobName, String cronExpression)
			throws SchedulerException, ParseException {
		boolean flag = false;
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(jobName,
				TRIGGER_GROUP_NAME);
		if (trigger != null) {
			if (StringUtils.equals(trigger.getCronExpression(), cronExpression)) {
				log.info(jobName + " : 定时任务表达式一致、不需要修改");
				flag = true;
			} else {
				trigger.setCronExpression(cronExpression);
				scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(),
						trigger);
				log.info(jobName + " : 定时任务修改成功");
				flag = true;
			}
		}
		return flag;
	}
	
	public boolean checkJob(String jobName) throws SchedulerException, ParseException {
		boolean flag = false;
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(jobName,
				TRIGGER_GROUP_NAME);
		if (trigger != null) {
			flag = true;
		}
		return flag;
	}

	@Override
	public void schedule(Date startTime) {
		schedule(startTime, null);
	}

	@Override
	public void schedule(String name, Date startTime) {
		schedule(name, startTime, null);
	}

	@Override
	public void schedule(Date startTime, Date endTime) {
		schedule(startTime, endTime, 0);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime) {
		schedule(name, startTime, endTime, 0);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount) {
		schedule(null, startTime, endTime, repeatCount);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount) {
		schedule(name, startTime, endTime, repeatCount, 0L);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval) {
		schedule(null, startTime, endTime, repeatCount, repeatInterval);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}
		try {
			scheduler.addJob(jobDetail, true);

			SimpleTrigger simpleTrigger = new SimpleTrigger(name,
					Scheduler.DEFAULT_GROUP, jobDetail.getName(),
					Scheduler.DEFAULT_GROUP, startTime, endTime, repeatCount,
					repeatInterval);
			scheduler.scheduleJob(simpleTrigger);
			scheduler.rescheduleJob(name, Scheduler.DEFAULT_GROUP,
					simpleTrigger);
		} catch (SchedulerException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

}
