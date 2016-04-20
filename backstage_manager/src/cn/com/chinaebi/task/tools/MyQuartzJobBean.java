package cn.com.chinaebi.task.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.com.chinaebi.dz.base.CreateInnerClearFile;
import cn.com.chinaebi.dz.base.CustomDzFileCreate;
import cn.com.chinaebi.dz.base.DzFileCreate;
import cn.com.chinaebi.dz.base.DzFileParsing;
import cn.com.chinaebi.dz.base.OriginalTotalDataLst;
import cn.com.chinaebi.dz.base.TradeDzHandler;
import cn.com.chinaebi.dz.base.exception.DuizhangNotFoundException;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.CustomObject;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.TimingTaskConf;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.object.util.FileUtil;
import cn.com.chinaebi.dz.object.util.FtpUtil;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.object.util.PropertiesUtils;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.BankInstDzUtil;
import cn.com.chinaebi.dz.webservice.BfjDataSynService;

public class MyQuartzJobBean extends QuartzJobBean {
	
	private Log log = LogFactory.getLog(getClass());
	
	private static cn.com.chinaebi.dz.object.dao.iface.CustomObjectDAO customObjectDAO = cn.com.chinaebi.dz.object.dao.CustomObjectDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.FtpUploadRecordDAO ftpUploadRecordDAO = cn.com.chinaebi.dz.object.dao.FtpUploadRecordDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO = cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.TimingTaskConfDAO timingTaskConfDAO = cn.com.chinaebi.dz.object.dao.TimingTaskConfDAO.getInstance();
	
	
	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException{
		try {
			log.info("进入定时任务执行方法……");
			Trigger trigger = jobexecutioncontext.getTrigger();    
			String triggerName = trigger.getName();  
			Calendar calendar1 = Calendar.getInstance();//系统当前时间
			calendar1.add(Calendar.DATE, -1);
			String deductDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar1.getTime());
			Map<InstInfoPK,Object> map_inst = Backstage.getInstance().getInstInfoMap();
			if (StringUtils.equals(triggerName, "szghParsing")) {
				log.info("执行解析深圳工行对账文件定时任务 ");
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(15,0));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				boolean flag = false;
				boolean dz_file_gain = false;
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadBankFile(instInfo,true);
					}catch(Exception e){
						log.error(e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				
				if(flag){
					try{
						log.info("在FTP服务器上找到指定的对账文件，并拉取成功");
						Class<?> c = Class.forName(bankInst.getParseDzFileClass());
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间
						
						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String file_name = FileUtil.getFileName(bankInst.getDzFileNamePattern(), calendar.getTime());
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+file_name,"",bankInst);
						
						dz_file_gain = true;
					}catch(Exception e){
						log.error(e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NULL_FILE_ERROR);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_FILE_ERROR);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PARSE_FILE_ERROR);
						}
					}
				}else{
					log.info("深圳工行对账文件拉取失败");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_FILE_ERROR);
				}
				try{
					executeNodeDAO.updateExecuteNodeStatus("dz_file_gain", instInfo.getId().getInstId(), deductDate, dz_file_gain?DataStatus.excute_sucess:DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
				}catch(Exception e){
					log.error("更新深圳工行对账文件解析工作流节点抛出异常："+e);
				}
			} else if(StringUtils.equals(triggerName, "beijingParsing")){
				log.info("执行解析北京银行对账文件定时任务 ");
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(70001,0));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				boolean flag = false;
				boolean dz_file_gain = false;
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), true);
					}catch(Exception e){
						log.error(e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				
				if(flag){
					try{
						log.info("在FTP服务器上找到指定的对账文件，并拉取成功");
						Class<?> c = Class.forName(bankInst.getParseDzFileClass());
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间

						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String file_name = FileUtil.getFileName(bankInst.getDzFileNamePattern(), calendar.getTime());
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+file_name,"",bankInst);
						
						dz_file_gain = true;
					}catch(Exception e){
						log.error(e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NULL_FILE_ERROR);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_FILE_ERROR);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PARSE_FILE_ERROR);
						}
					}
				}else{
					log.info("北京银行对账文件拉取失败");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_FILE_ERROR);
				}
				try{
					executeNodeDAO.updateExecuteNodeStatus("dz_file_gain", instInfo.getId().getInstId(), deductDate, dz_file_gain?DataStatus.excute_sucess:DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
				}catch(Exception e){
					log.error("更新北京银行对账文件解析工作流节点抛出异常："+e);
				}
			}else if(StringUtils.equals(triggerName, "dljhParsing")){
				log.info("执行解析大连交行对账文件定时任务 ");
				boolean flag = false;
				boolean dz_file_gain = false;
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(14,0));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), true);
					}catch(Exception e){
						log.error("大连交行对账文件ftp拉取失败，抛出异常:"+e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				
				if(flag){
					try{
						log.info("在FTP服务器上找到指定的对账文件，并拉取成功，进行解析文件");
						Class<?> c = Class.forName(bankInst.getParseDzFileClass());
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间
						
						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String file_name = FileUtil.getFileName(bankInst.getDzFileNamePattern(), calendar.getTime());
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+file_name,"",bankInst);
						
						dz_file_gain = true;
					}catch(Exception e){
						log.error("解析大连交行对账文件抛出异常："+e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NULL_FILE_ERROR);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_FILE_ERROR);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PARSE_FILE_ERROR);
						}
					}
				}else{
					log.info("对账文件拉取失败");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_FILE_ERROR);
				}
				try{
					executeNodeDAO.updateExecuteNodeStatus("dz_file_gain", instInfo.getId().getInstId(), deductDate, dz_file_gain?DataStatus.excute_sucess:DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
				}catch(Exception e){
					log.error("更新大连交行对账文件解析工作流节点抛出异常："+e);
				}
			}else if(StringUtils.equals(triggerName, "szzhParsing")){
				log.info("执行解析深圳中行对账文件定时任务 ");
				boolean flag = false;
				boolean dz_file_gain = false;
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(3,0));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), true);
					}catch(Exception e){
						log.error("深圳中行对账文件ftp拉取失败，抛出异常:"+e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				
				if(flag){
					try{
						log.info("在FTP服务器上找到指定的对账文件，并拉取成功，进行解析文件");
						Class<?> c = Class.forName(bankInst.getParseDzFileClass());
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间

						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String file_name = FileUtil.getFileName(bankInst.getDzFileNamePattern(), calendar.getTime());
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+file_name,"",bankInst);
						dz_file_gain = true;
					}catch(Exception e){
						log.error("解析深圳中行对账文件抛出异常："+e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NULL_FILE_ERROR);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_FILE_ERROR);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PARSE_FILE_ERROR);
						}
					}
				}else{
					log.info("对账文件拉取失败");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_FILE_ERROR);
				}
				try{
					executeNodeDAO.updateExecuteNodeStatus("dz_file_gain", instInfo.getId().getInstId(), deductDate, dz_file_gain?DataStatus.excute_sucess:DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
				}catch(Exception e){
					log.error("更新深圳中行对账文件解析工作流节点抛出异常："+e);
				}
			}else if(StringUtils.equals(triggerName, "cupsParsing")){
				log.info("执行解析银联对账文件定时任务 ");
				boolean flag = false;
				boolean dz_file_gain = false;
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(11,0));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), true);
					}catch(Exception e){
						log.error("银联cups对账文件ftp拉取失败，抛出异常:"+e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				
				if(flag){
					try{
						log.info("在FTP服务器上找到指定的对账文件，并拉取成功，进行解析文件");
						String parseDzFileClass = bankInst.getParseDzFileClass();
						String[] parseDzFileClassArr = parseDzFileClass.split(";");
						Class<?> c = Class.forName(parseDzFileClassArr[0]);
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间

						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String file_name = FileUtil.getFileName(bankInst.getDzFileNamePattern(), calendar.getTime());
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+file_name,"",bankInst);
						dz_file_gain = true;
					}catch(Exception e){
						log.error("解析银联cups对账文件抛出异常："+e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NULL_FILE_ERROR);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_FILE_ERROR);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PARSE_FILE_ERROR);
						}
					}
				}else{
					log.info("对账文件拉取失败");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_FILE_ERROR);
				}
				
				try{
					executeNodeDAO.updateExecuteNodeStatus("dz_file_gain", instInfo.getId().getInstId(), deductDate, dz_file_gain?DataStatus.excute_sucess:DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
				}catch(Exception e){
					log.error("更新银联cups对账文件解析工作流节点抛出异常："+e);
				}
				
				boolean ppfw_flag = false;
				ResourceBundle resource = ResourceBundle.getBundle("conf");
				if(instInfo != null){
					try{
						String ppfw_file_pattern = resource.getString("ppfw_file_pattern");
						if(StringUtils.isBlank(ppfw_file_pattern)){
							ppfw_file_pattern = "INDYYMMDD99ALFEE";
						}
						ppfw_flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								ppfw_file_pattern, bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), true);
					}catch(Exception e){
						log.error(e);
					}
				}
				if(ppfw_flag){
					try{
						log.info("在FTP服务器上找到指定的对账文件，并拉取成功，进行解析品牌服务费文件");
						String parseDzFileClass = resource.getString("ppfw_parsing_class");
						if(StringUtils.isBlank(parseDzFileClass)){
							parseDzFileClass = "cn.com.chinaebi.dz.file.parsing.Pos_CupsLfeParsing";
						}
						Class<?> c = Class.forName(parseDzFileClass);
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath(),"",bankInst);
						dz_file_gain = true;
					}catch(Exception e){
						log.error("解析银联cups品牌服务费文件抛出异常："+e);
					}
				}else{
					log.info("品牌服务费文件拉取失败");
				}
			}else if(StringUtils.equals(triggerName, "zhongxinParsing")){
				log.info("执行解析中信银行对账文件定时任务 ");
				boolean flag = false;
				boolean dz_file_gain = false;
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(10,0));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), true);
					}catch(Exception e){
						log.error("中信银行对账文件ftp拉取失败，抛出异常:"+e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				
				if(flag){
					try{
						log.info("在FTP服务器上找到指定的对账文件，并拉取成功");
						Class<?> c = Class.forName(bankInst.getParseDzFileClass());
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间

						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String file_name = FileUtil.getFileName(bankInst.getDzFileNamePattern(), calendar.getTime());
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+file_name,"",bankInst);
						
						dz_file_gain = true;
					}catch(Exception e){
						log.error(e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NULL_FILE_ERROR);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_FILE_ERROR);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PARSE_FILE_ERROR);
						}
					}
				}else{
					log.info("对账文件拉取失败");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_FILE_ERROR);
				}
				try{
					executeNodeDAO.updateExecuteNodeStatus("dz_file_gain", instInfo.getId().getInstId(), deductDate, dz_file_gain?DataStatus.excute_sucess:DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
				}catch(Exception e){
					log.error("更新中信银行对账文件解析工作流节点抛出异常："+e);
				}
			}else if(StringUtils.equals(triggerName, "upmpParsing")){
				log.info("执行解析银联upmp对账文件定时任务 ");
				boolean flag = false;
				boolean dz_file_gain = false;
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(55001,1));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), true);
					}catch(Exception e){
						log.error("银联upmp对账文件ftp拉取失败，抛出异常:"+e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				
				if(flag){
					try{
						log.info("在FTP服务器上找到指定的对账文件，并拉取成功，进行解析文件");
						String parseDzFileClass = bankInst.getParseDzFileClass();
						String[] parseDzFileClassArr = parseDzFileClass.split(";");
						Class<?> c = Class.forName(parseDzFileClassArr[0]);
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间

						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String file_name = FileUtil.getFileName(bankInst.getDzFileNamePattern(), calendar.getTime());
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+deductDate.replaceAll("-", "")+"/"+file_name,"",bankInst);
						dz_file_gain = true;
					}catch(Exception e){
						log.error("解析银联UPMP对账文件抛出异常："+e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NULL_FILE_ERROR);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_FILE_ERROR);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PARSE_FILE_ERROR);
						}
					}
				}else{
					log.info("对账文件拉取失败");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_FILE_ERROR);
				}
				try{
					executeNodeDAO.updateExecuteNodeStatus("dz_file_gain", instInfo.getId().getInstId(), deductDate, dz_file_gain?DataStatus.excute_sucess:DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
				}catch(Exception e){
					log.error("更新融易付银联UPMP对账文件解析工作流节点抛出异常："+e);
				}
			}else if(StringUtils.equals(triggerName, "errorCupsParsing")){
				log.info("解析银联差错对账文件");
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(11,0));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				boolean flag = false;
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), false);
					}catch(Exception e){
						log.error("银联差错对账文件ftp拉取失败，抛出异常:"+e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				if(flag){
					try{
						String parseDzFileClass = bankInst.getParseDzFileClass();
						String[] parseDzFileClassArr = parseDzFileClass.split(";");
						Class<?> c = Class.forName(parseDzFileClassArr[1]);
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间

						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String date_pattern = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_5).format(calendar.getTime());
						
						String file_name = "IND"+date_pattern+"99AERRN";
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+file_name,"",bankInst);
						
					}catch(Exception e){
						log.error(e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.ERROR_FILE_IS_NULL);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_ERROR_FILE);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.ERROR_FILE_PARSE_FAIL);
						}
					}
				}else{
					log.info("在FTP服务器上未找到指定的银联差错对账文件");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_ERROR_FILE_FAIL);
				}
			}else if(StringUtils.equals(triggerName, "errorUpmpParsing")){
				log.info("解析银联upmp差错对账文件");
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(55001,1));
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				boolean flag = false;
				if(instInfo != null){
					try{
						flag = FtpUtil.downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(),
								bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(), false);
					}catch(Exception e){
						log.error("银联upmp差错对账文件ftp拉取失败，抛出异常:"+e);
					}
				}else{
					log.info("从内存中获取扣款渠道信息失败,无法执行对账文件拉取操作，请核实！");
				}
				
				if(flag){
					try{
						String parseDzFileClass = bankInst.getParseDzFileClass();
						String[] parseDzFileClassArr = parseDzFileClass.split(";");
						Class<?> c = Class.forName(parseDzFileClassArr[1]);
						DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
						
						Calendar calendar = Calendar.getInstance();//系统当前时间

						if(instInfo.isWhether_T_1()){
							calendar.add(Calendar.DATE, -1);
						}
						
						String date_pattern = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_5).format(calendar.getTime());
						
						String file_name = "IND"+date_pattern+"99AERRN";
						
						dzFileParsing.parseBankDzFile(bankInst.getDzFilePath()+"/"+deductDate.replaceAll("-", "")+"/"+file_name,"",bankInst);
					}catch(Exception e){
						log.error(e);
						if(e instanceof DuizhangNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.ERROR_FILE_IS_NULL);
						}else if(e instanceof FileNotFoundException){
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_ERROR_FILE);
						}else{
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.ERROR_FILE_PARSE_FAIL);
						}
					}
				}else{
					log.info("在FTP服务器上未找到指定的银联upmp差错对账文件");
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_FILE_PULL, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.PULL_ERROR_FILE_FAIL);
				}
			}else if(StringUtils.equals(triggerName, "originalTotalDataLst")){
				try{
					OriginalTotalDataLst.originalTotalData(deductDate);
					
					//内部成功交易生产清算文件
					List<CustomObject> customObjectList = customObjectDAO.queryCustomObjectByFileType(DataStatus.qs_file);
					if(customObjectList != null && customObjectList.size() != 0){
						for (CustomObject customObject : customObjectList) {
							boolean fileFlag = CreateInnerClearFile.createInnerClearFile("0", deductDate, customObject);
							if(fileFlag){
								log.info("数据汇总后,名称为"+customObject.getObjectName()+"的系统接口生成内部清算文件成功");
							}else{
								log.info("数据汇总后,名称为"+customObject.getObjectName()+"的系统接口生成内部清算文件失败");
							}
						}
					}
					
					
				}catch(Exception e){
					log.error("数据汇总定时任务抛出异常"+e.getMessage());
				}
				
			}
			else if(StringUtils.equals(triggerName, "dzZhongxinTask")){
				TimingTaskConf taskConf =  timingTaskConfDAO.getInstIdAntInstType(triggerName);
				if(taskConf != null){
					InstInfoPK instInfoPk = new InstInfoPK(taskConf.getChannelId(),taskConf.getInstType());
					InstInfo instInfo = map_inst.get(instInfoPk)==null?null:(InstInfo)map_inst.get(instInfoPk);
					if(instInfo != null){
						if(instInfo.isWhetherOuterDz()){
							try {
								log.info("执行中信银行对账任务方法开始……");
								Calendar calendar = Calendar.getInstance();//系统当前时间
		                		calendar.add(Calendar.DATE, -1);
								String originalPepDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								String duizhangReqTime = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
								String deductStlmDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								log.info("原始数据查询时间 :"+originalPepDate);
								log.info("对账文件查询时间 :"+duizhangReqTime);
								log.info("日切 清算处理时间 :"+deductStlmDate);
								BankInstDzUtil.bankInstOfflineDzDeal(originalPepDate, duizhangReqTime, deductStlmDate, instInfo.getBank().getId(), DataStatus.no_auto, instInfo.getId().getInstId(), instInfo.getId().getInstType());
								log.info("执行中信银行对账任务方法完毕");
							} catch (Exception e) {
								log.error("执行中信银行对账任务方法  抛出异常"+e.getMessage());
							}
						}
					}
				}
			}else if(StringUtils.equals(triggerName, "dzBeijingTask")){
				TimingTaskConf taskConf =  timingTaskConfDAO.getInstIdAntInstType(triggerName);
				if(taskConf != null){
					InstInfoPK instInfoPk = new InstInfoPK(taskConf.getChannelId(),taskConf.getInstType());
					InstInfo instInfo = map_inst.get(instInfoPk)==null?null:(InstInfo)map_inst.get(instInfoPk);
					if(instInfo != null){
						if(instInfo.isWhetherOuterDz()){
							try {
								log.info("执行北京银行对账任务方法开始……");
								Calendar calendar = Calendar.getInstance();//系统当前时间
		                		calendar.add(Calendar.DATE, -1);
								String originalPepDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								String duizhangReqTime = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
								String deductStlmDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								log.info("原始数据查询时间 :"+originalPepDate);
								log.info("对账文件查询时间 :"+duizhangReqTime);
								log.info("日切 清算处理时间 :"+deductStlmDate);
								BankInstDzUtil.bankInstOfflineDzDeal(originalPepDate, duizhangReqTime, deductStlmDate, instInfo.getBank().getId(), DataStatus.no_auto, instInfo.getId().getInstId(), instInfo.getId().getInstType());
								log.info("执行北京银行对账任务方法完毕");
								log.info("开始更新北京银行工作流对账节点状态");
							} catch (Exception e) {
								log.error("执行北京银行对账任务方法  抛出异常"+e.getMessage());
							}
						}
					}
				}
			}else if(StringUtils.equals(triggerName, "dzCupsTask")){
				TimingTaskConf taskConf =  timingTaskConfDAO.getInstIdAntInstType(triggerName);
				if(taskConf != null){
					InstInfoPK instInfoPk = new InstInfoPK(taskConf.getChannelId(),taskConf.getInstType());
					InstInfo instInfo = map_inst.get(instInfoPk)==null?null:(InstInfo)map_inst.get(instInfoPk);
					if(instInfo != null){
						if(instInfo.isWhetherOuterDz()){
							Calendar calendar = Calendar.getInstance();//系统当前时间
	                		calendar.add(Calendar.DATE, -1);
							String originalPepDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
							String duizhangReqTime = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
							String deductStlmDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
							log.info("原始数据查询时间 :"+originalPepDate);
							log.info("对账文件查询时间 :"+duizhangReqTime);
							log.info("日切 清算处理时间 :"+deductStlmDate);
								
							log.info("执行银联对账任务方法开始……");
							try{
								BankInstDzUtil.bankInstOfflineDzDeal(originalPepDate, duizhangReqTime, deductStlmDate, instInfo.getBank().getId(), DataStatus.no_auto, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							}catch(Exception e){
								log.error("执行银联对账任务方法  抛出异常"+e.getMessage());
							}
							log.info("执行银联对账任务方法完毕");
						
							try {		
								if(instInfo.isWhetherOuterErrorDz()){
									try{
										log.info("执行银联差错对账开始");
										Class<?> c = Class.forName(instInfo.getTradeDzImplClass());
										TradeDzHandler t = (TradeDzHandler)c.newInstance();
										t.tradeErrorDzDeal(originalPepDate, duizhangReqTime,instInfo.getId().getInstId(),DataStatus.auto);
										boolean cups_error_flag = executeNodeDAO.updateExecuteNodeStatus("error_handle", instInfo.getId().getInstId(), deductStlmDate, DataStatus.excute_sucess,instInfo.getName(),instInfo.getId().getInstType());
										if(cups_error_flag){
											log.info("更新银联差错对账节点状态成功");
										}else{
											log.info("更新银联差错对账节点状态失败");
										}
									}catch(Exception e){
										if(e instanceof DuizhangNotFoundException){
											log.info("银联差错文件对账文件数据表中不存在数据，未执行对账操作，不修改对账节点");
											executeNodeDAO.updateExecuteNodeStatus("error_handle", instInfo.getId().getInstId(), deductDate, DataStatus.no_dz_data,instInfo.getName(),instInfo.getId().getInstType());
											MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.NO_ERROR_DZ_DATA);
										}else{
											log.error("执行银联差错对账任务方法  抛出异常"+e.getMessage());
											if(instInfo.getId().getInstId() != 0 && StringUtils.isNotBlank(deductDate)){
												boolean cups_error_flag_1 = executeNodeDAO.updateExecuteNodeStatus("error_handle", instInfo.getId().getInstId(), deductDate, DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
												if(cups_error_flag_1){
													log.info("更新银联差错对账节点状态成功");
												}else{
													log.info("更新银联差错对账节点状态失败");
												}
												MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, deductDate, instInfo,MailSendInfoUtil.EXCUTE_ERROR_DZ_FAIL);
											}
										}
									}
									log.info("执行银联差错对账任务方法完毕");
								}else{
									log.info("银联渠道不需要差错外部对账");
								}
							} catch (ClassNotFoundException e) {
								log.info("执行银联对账任务方法  抛出异常"+e.getMessage());
								if(instInfo.getId().getInstId() != 0 && StringUtils.isNotBlank(deductDate)){
									boolean cups_error_flag_1 = executeNodeDAO.updateExecuteNodeStatus("error_handle", instInfo.getId().getInstId(), deductDate, DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
									if(cups_error_flag_1){
										log.info("更新银联差错对账节点状态成功");
									}else{
										log.info("更新银联差错对账节点状态失败");
									}
								}
							}
						}
					}
				}
			}else if(StringUtils.equals(triggerName, "dzDljhTask")){
				TimingTaskConf taskConf =  timingTaskConfDAO.getInstIdAntInstType(triggerName);
				if(taskConf != null){
					InstInfoPK instInfoPk = new InstInfoPK(taskConf.getChannelId(),taskConf.getInstType());
					InstInfo instInfo = map_inst.get(instInfoPk)==null?null:(InstInfo)map_inst.get(instInfoPk);
					if(instInfo != null){
						if(instInfo.isWhetherOuterDz()){
							try {
								log.info("执行大连交行对账任务方法开始……");
								Calendar calendar = Calendar.getInstance();//系统当前时间
		                		calendar.add(Calendar.DATE, -1);
								String originalPepDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								String duizhangReqTime = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
								String deductStlmDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								log.info("原始数据查询时间 :"+originalPepDate);
								log.info("对账文件查询时间 :"+duizhangReqTime);
								log.info("日切 清算处理时间 :"+deductStlmDate);
								BankInstDzUtil.bankInstOfflineDzDeal(originalPepDate, duizhangReqTime, deductStlmDate, instInfo.getBank().getId(), DataStatus.no_auto, instInfo.getId().getInstId(), instInfo.getId().getInstType());
								log.info("执行大连交行对账任务方法完毕");
							} catch (Exception e) {
								log.error("执行大连交行对账任务方法  抛出异常"+e.getMessage());
							}
						}
					}
				}
			}else if(StringUtils.equals(triggerName, "dzSzzhTask")){
				TimingTaskConf taskConf =  timingTaskConfDAO.getInstIdAntInstType(triggerName);
				if(taskConf != null){
					InstInfoPK instInfoPk = new InstInfoPK(taskConf.getChannelId(),taskConf.getInstType());
					InstInfo instInfo = map_inst.get(instInfoPk)==null?null:(InstInfo)map_inst.get(instInfoPk);
					if(instInfo != null){
						if(instInfo.isWhetherOuterDz()){
							try {
								log.info("执行深圳中行对账任务方法开始……");
								Calendar calendar = Calendar.getInstance();//系统当前时间
		                		calendar.add(Calendar.DATE, -1);
								String originalPepDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								String duizhangReqTime = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
								String deductStlmDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
								log.info("原始数据查询时间 :"+originalPepDate);
								log.info("对账文件查询时间 :"+duizhangReqTime);
								log.info("日切 清算处理时间 :"+deductStlmDate);
								BankInstDzUtil.bankInstOfflineDzDeal(originalPepDate, duizhangReqTime, deductStlmDate, instInfo.getBank().getId(), DataStatus.no_auto, instInfo.getId().getInstId(), instInfo.getId().getInstType());
								log.info("执行深圳中行对账任务方法完毕");
							} catch (Exception e) {
								log.error("执行深圳中行对账任务方法  抛出异常"+e.getMessage());
							}
						}
					}
				}
			}else if(StringUtils.equals(triggerName, "dzFileCreate")){
				try{
					//生成内部对账文件
					Calendar calendar = Calendar.getInstance();//系统当前时间
					calendar.add(Calendar.DATE, -1);
					String date = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
					log.info("开始创建对账文件");
					boolean flag = false;
					List<CustomObject> customObjectList = customObjectDAO.queryCustomObjectByFileType(DataStatus.dz_file);
					log.info("获取系统对象"+(customObjectList==null?0:customObjectList.size()+"条"));
					if(customObjectList != null && customObjectList.size() != 0){
						for (CustomObject customObject : customObjectList) {
							log.info("获取系统对象ID："+customObject.getId()+",系统名称为："+customObject.getObjectName()+",系统文件存放路径为："+customObject.getFileAddress()+",系统对账文件总表名称为："+customObject.getDzFileName());
							flag = CustomDzFileCreate.createDzFile(date,customObject);
							if(flag){
								log.info("自动生成日期为"+date+"的对账文件成功");
								//向对账文件表中添加数据
								String file_name = date+customObject.getDzFileName();
								String file_type = "对账文件总表";
								DzFileCreate.insertDataToDzFileTab(date,file_name,file_type,customObject.getFileAddress(),customObject.getId(),customObject.getObjectName());
								if(customObject.isWhetherUpload()){
									//将生成的对账文件发送到FTP上
									StringBuffer s_ = new StringBuffer("");
									s_.append(customObject.getFileAddress());
									s_.append("/");
									s_.append(date);
									s_.append("/");
									s_.append(date);
									s_.append(customObject.getDzFileName());
									s_.append(customObject.getFileSuffix());
									String dz_content = s_.toString();
									int upload_status = 2;
									try{
										File file_dz = new File(dz_content);
										FileInputStream is_dz = new FileInputStream(file_dz);
										boolean dz_upload = FtpUtil.upLoadFileFtp(customObject.getFtpIp(),Integer.valueOf(customObject.getFtpPort()),customObject.getFtpUsername(),customObject.getFtpPassword(),customObject.getFtpAddress(), is_dz, file_dz.getName(),date);
										if(dz_upload){
											log.info("对账文件总表上传到FTP成功");
											upload_status = 1;
										}else{
											log.info("对账文件总表上传到FTP失败");
											MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.FTP_UPLOAD_FILE, emailPoliceDAO, deductDate, customObject.getObjectName());
										}
									}catch(Exception e){
										log.error(e);
										MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.FTP_UPLOAD_FILE, emailPoliceDAO, deductDate, customObject.getObjectName());
									}
									boolean ftp_flag = ftpUploadRecordDAO.insertOrUpdateFtpUploadRecord(customObject.getId(), date, customObject.getObjectName(), dz_content.substring(dz_content.lastIndexOf("/")+1, dz_content.length()), new Date(), upload_status);
									if(ftp_flag){
										log.info("向FTP上传记录表添加数据成功");
									}else{
										log.info("向FTP上传记录表添加数据失败");
									}
								}
							} else {
								log.error("生成对账文件失败");
								MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, deductDate, customObject.getObjectName());
							}
							//更新各渠道工作流节点
							List<InstInfo> inst_info = Backstage.getInstance().getInstInfoListOfWhetherOuterDz();
						    for (InstInfo instInfo : inst_info) {
						       boolean update_exeuteNode_flag = executeNodeDAO.updateExecuteNodeStatus("dz_file_create", instInfo.getId().getInstId(), deductDate, flag?DataStatus.excute_sucess:DataStatus.excute_fail,instInfo.getName(),instInfo.getId().getInstType());
						       if(update_exeuteNode_flag){
						    	   log.info("更新"+instInfo.getName()+"对账文件生成节点状态成功");
						       }else{
						    	   log.info("更新"+instInfo.getName()+"对账文件生成节点状态失败");
						       }
						    }
							
							if(customObject.getWhetherCreateErrorFile() == 1){
								Calendar calendar_error = Calendar.getInstance();//系统当前时间
					    		String date_error = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar_error.getTime());
					    		boolean flag_error_file = DzFileCreate.createErrorDzFile(date_error,customObject);
								if(flag_error_file){
									log.info("自动生成日期为"+date_error+"的差错文件总表成功");
									String file_name = date_error+customObject.getErrorFileName();
									String file_type = "差错文件总表";
									DzFileCreate.insertDataToDzFileTab(date_error,file_name,file_type,customObject.getFileAddress(),customObject.getId(),customObject.getObjectName());
									if(customObject.isWhetherUpload()){
										//将生成的对账文件发送到FTP上
										int upload_status = 2;
										StringBuffer s_cc = new StringBuffer("");
										s_cc.append(customObject.getFileAddress());
										s_cc.append("/");
										s_cc.append(date);
										s_cc.append("/");
										s_cc.append(date_error);
										s_cc.append(customObject.getErrorFileName());
										s_cc.append(customObject.getFileSuffix());
										String cc_content = s_cc.toString();
										try{
											File file_cc = new File(cc_content);
											FileInputStream is_cc = new FileInputStream(file_cc);
											boolean dz_upload = FtpUtil.upLoadFileFtp(customObject.getFtpIp(),Integer.valueOf(customObject.getFtpPort()),customObject.getFtpUsername(),customObject.getFtpPassword(),customObject.getFtpAddress(), is_cc, file_cc.getName(),date);
											if(dz_upload){
												upload_status = 1;
												log.info("差错文件总表上传到FTP成功");
											}else{
												log.info("差错文件总表上传到FTP失败");
												MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.FTP_UPLOAD_FILE, emailPoliceDAO, deductDate, customObject.getObjectName());
											}
										}catch(Exception e){
											log.error(e);
											MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.FTP_UPLOAD_FILE, emailPoliceDAO, deductDate, customObject.getObjectName());
										}
										boolean ftp_flag = ftpUploadRecordDAO.insertOrUpdateFtpUploadRecord(customObject.getId(), date, customObject.getObjectName(), cc_content.substring(cc_content.lastIndexOf("/")+1, cc_content.length()), new Date(), upload_status);
										if(ftp_flag){
											log.info("向FTP上传记录表添加数据成功");
										}else{
											log.info("向FTP上传记录表添加数据失败");
										}
									}
								}else{
									log.info("自动生成日期为"+date_error+"的差错文件总表失败");
									MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, deductDate, customObject.getObjectName());
								}
							}
						}
					}
				}catch(Exception e){
					log.error(e);
				}
			}else if(StringUtils.equals(triggerName, "dzRytUpmpTask")){
				TimingTaskConf taskConf =  timingTaskConfDAO.getInstIdAntInstType(triggerName);
				if(taskConf != null){
					InstInfoPK instInfoPk = new InstInfoPK(taskConf.getChannelId(),taskConf.getInstType());
					InstInfo instInfo = map_inst.get(instInfoPk)==null?null:(InstInfo)map_inst.get(instInfoPk);
					if(instInfo != null){
						if(instInfo.isWhetherOuterDz()){
							Calendar calendar = Calendar.getInstance();//系统当前时间
		            		calendar.add(Calendar.DATE, -1);
							String originalPepDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
							String duizhangBeijingReqTime = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
							String deductStlmDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
							
							try {
								log.info("执行融易付对账任务方法开始……");
								BankInstDzUtil.bankInstOnlineDzDeal(originalPepDate, duizhangBeijingReqTime, deductStlmDate, instInfo.getBank().getId(),DataStatus.no_auto,1,instInfo.getId().getInstId(),instInfo.getId().getInstType());
								log.info("执行融易付对账任务方法完毕");
								executeNodeDAO.updateExecuteNodeStatus("dz_handle", instInfo.getId().getInstId(), deductDate, DataStatus.excute_sucess,instInfo.getName(),instInfo.getId().getInstType());
							} catch (Exception e) {
								log.error("执行融易付对账任务方法  抛出异常"+e.getMessage());
							}
						}
					}
				}
			}else if(StringUtils.equals(triggerName, "bfjSynTask")){
				String bfj_flag = PropertiesUtils.rtReadProperties("bfj_flag", "conf");
				String keyPath = PropertiesUtils.rtReadProperties("keyStore", "conf");
				String bfj_url = PropertiesUtils.rtReadProperties("bfj_url", "conf");
				if(StringUtils.isNotBlank(bfj_flag) && StringUtils.isNotBlank(bfj_url))
					BfjDataSynService.getInstance().bfjDataSyn(Integer.valueOf(bfj_flag), keyPath, bfj_url);
				else
					log.error("备付金数据同步定时任务配置错误,bfj_flag、bfj_key、bfj_url不能为空");
			}
		} catch (Exception e) {
			log.error(e);
			try {
				throw e;
			} catch (Exception e1) {
				log.error(e1);
			}
		}
	}

}
