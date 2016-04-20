package cn.com.chinaebi.dz.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.base.CreateInnerClearFile;
import cn.com.chinaebi.dz.base.CreateSettleFile;
import cn.com.chinaebi.dz.base.CustomDzFileCreate;
import cn.com.chinaebi.dz.base.DzFileCreate;
import cn.com.chinaebi.dz.object.CustomInstConfig;
import cn.com.chinaebi.dz.object.CustomObject;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.object.util.FtpUtil;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.webservice.CreateSettleFileType;
import cn.com.chinaebi.dz.webservice.ReductionGenerateDzFileType;

public class FileCreateHandleService {

	private static final Log log = LogFactory.getLog(FileCreateHandleService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.CustomObjectDAO customObjectDAO = cn.com.chinaebi.dz.object.dao.CustomObjectDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO = cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.FtpUploadRecordDAO ftpUploadRecordDAO = cn.com.chinaebi.dz.object.dao.FtpUploadRecordDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.DzFileTabDAO dzFileTabDAO = cn.com.chinaebi.dz.object.dao.DzFileTabDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.CustomInstConfigDAO customInstConfigDAO = cn.com.chinaebi.dz.object.dao.CustomInstConfigDAO.getInstance();
	private static StringPingJie stringPingJie = StringPingJie.getInstance();
	
	public static boolean createFileHandle(ReductionGenerateDzFileType type) throws Exception{
		boolean flag = false;
		String deductStlmDate = "";
		try{
			deductStlmDate = type.getGenerateDate();
			int fileType = type.getFileType();
			int object_id = type.getCustomId();
			CustomObject customObject = customObjectDAO.queryCustomObject(object_id);
			log.info(stringPingJie.getStringPingJie("获取系统对象",customObject==null?0:1,"条"));
			if(customObject!=null){
				log.info(stringPingJie.getStringPingJie("获取系统对象ID：",customObject.getId(),",系统名称为：",customObject.getObjectName(),",系统文件存放路径为：",customObject.getFileAddress(),",系统对账文件总表名称为：",customObject.getDzFileName()));
				if(fileType == 1){ //对账总表
					flag = CustomDzFileCreate.createDzFile(deductStlmDate,customObject);
					if(flag){
						log.info(stringPingJie.getStringPingJie("手动生成日期为",deductStlmDate,"的对账文件成功"));
						String file_name = deductStlmDate+customObject.getDzFileName();
						String file_type = "对账文件总表";
						DzFileCreate.insertDataToDzFileTab(deductStlmDate,file_name,file_type,customObject.getFileAddress(),customObject.getId(),customObject.getObjectName());
					} else {
						MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, deductStlmDate, customObject.getObjectName());
					}
					
					List<InstInfo> inst_info = new ArrayList<InstInfo>();
					Map<InstInfoPK,Object> instInfoMap = Backstage.getInstance().getOutDzInstMap();
					
					if(customObject.isWhetherCreateFileByInst()){
						List<CustomInstConfig> customInstConfigList = customInstConfigDAO.getCustomInstConfig(customObject.getId());//系统接口相关渠道配置信息
						if(customInstConfigList != null){
							InstInfoPK instInfo_pk = null;
							InstInfo instInfo = null;
							for (CustomInstConfig customInstConfig : customInstConfigList) {
								if(customInstConfig.getInstId() != null && customInstConfig.getInstType() != null){
									instInfo_pk = new InstInfoPK(customInstConfig.getInstId(),customInstConfig.getInstType());
									if(instInfoMap.containsKey(instInfo_pk)){
										instInfo = Backstage.getInstance().getInstInfo(instInfo_pk);
										inst_info.add(instInfo);
									}
								}
							}
						}
					}else{
						inst_info = Backstage.getInstance().getInstInfoListOfWhetherOuterDz();
					}
					
					if(inst_info != null && inst_info.size() > 0){
						for (InstInfo instInfo : inst_info) {
						       boolean update_exeuteNode_flag = executeNodeDAO.updateExecuteNodeStatus("dz_file_create", instInfo.getId().getInstId(), deductStlmDate, flag?"1":"2",instInfo.getName(),instInfo.getId().getInstType());
						       if(update_exeuteNode_flag){
						    	   log.info(stringPingJie.getStringPingJie("更新",instInfo.getName(),"对账文件生成节点状态成功"));
						       }else{
						    	   log.info(stringPingJie.getStringPingJie("更新",instInfo.getName(),"对账文件生成节点状态失败"));
						       }
						    }
					}
				}else if(fileType == 2){//差错总表
					if(customObject.getWhetherCreateErrorFile() == 1){
						flag = DzFileCreate.createErrorDzFile(deductStlmDate,customObjectDAO.queryCustomObject(object_id));
						if(flag){
							log.info(stringPingJie.getStringPingJie("手动生成日期为",deductStlmDate,"的差错文件总表成功"));
							String file_name = deductStlmDate+customObject.getErrorFileName();
							String file_type = "差错文件总表";
							boolean file_flag = DzFileCreate.insertDataToDzFileTab(deductStlmDate,file_name,file_type,customObject.getFileAddress(),customObject.getId(),customObject.getObjectName());
							if(file_flag){
								log.info("向文件表中插入数据成功");
							}else{
								log.info("向文件表中插入数据失败");
							}
						}else{
							log.info(stringPingJie.getStringPingJie("手动生成日期为",deductStlmDate,"的差错文件总表失败"));
							MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, deductStlmDate, customObject.getObjectName());
						}
					}
				}else if(fileType == 3){//内部清算文件
					flag = CreateInnerClearFile.createInnerClearFile("0", deductStlmDate, customObject);
					if(flag){
						log.info("数据汇总后,名称为"+customObject.getObjectName()+"的系统接口生成内部清算文件成功");
					}else{
						log.info("数据汇总后,名称为"+customObject.getObjectName()+"的系统接口生成内部清算文件失败");
					}
				}
			}
		}catch(Exception e){
			MailSendInfoUtil.sendEmailForErrorWork(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, deductStlmDate);
			log.error(e);
			throw e;
		}
		return flag;
	}
	
	public static boolean createSettleFile(CreateSettleFileType type) throws Exception{
		boolean is_flag = false;
		try{
			int startDate = type.getStartDate();
			int endDate = type.getEndDate();
			int settleAccountType = type.getSettleAccountType();
			
			Calendar calendar = Calendar.getInstance();//系统当前时间
			String date = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			
			List<CustomObject> customObjectList = customObjectDAO.queryCustomObjectByFileType(DataStatus.js_file);
			
			for (CustomObject customObject : customObjectList) {
				boolean flag = CreateSettleFile.createSettleFile(date, startDate, endDate, settleAccountType, customObject);
				//文件创建成功
				if (flag) {
					String file_name = date + customObject.getDzFileName().toString();
					String file_type = "结算单总表";
					String create_last_time = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					String file_path = customObject.getFileAddress();
					int object_id = customObject.getId();
					String object_name = customObject.getObjectName();
					dzFileTabDAO.insertDzFileData(date, file_name, file_type, create_last_time, file_path, object_id, object_name);
					
					
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
								log.info("结算单上传到FTP成功");
								upload_status = 1;
							}else{
								log.info("结算单上传到FTP失败");
								MailSendInfoUtil.sendEmailForCustomObjectJsFileCreate(MailSendInfoUtil.FTP_UPLOAD_FILE, emailPoliceDAO, date, customObject.getObjectName(),MailSendInfoUtil.PUSH_JS_FILE_ERROR);
							}
						}catch(Exception e){
							log.error(e);
							MailSendInfoUtil.sendEmailForCustomObjectJsFileCreate(MailSendInfoUtil.FTP_UPLOAD_FILE, emailPoliceDAO, date, customObject.getObjectName(),MailSendInfoUtil.PUSH_JS_FILE_ERROR);
						}
						boolean ftp_flag = ftpUploadRecordDAO.insertOrUpdateFtpUploadRecord(customObject.getId(), date, customObject.getObjectName(), dz_content.substring(dz_content.lastIndexOf("/")+1, dz_content.length()), new Date(), upload_status);
						if(ftp_flag){
							log.info("向FTP上传记录表添加数据成功");
						}else{
							log.info("向FTP上传记录表添加数据失败");
						}
					}
				}else{
					log.error(stringPingJie.getStringPingJie("创建结算总表" ,customObject.getId() ," : ", customObject.getObjectName() ," : ", customObject.getDzFileName() , "失败"));
				}
			}
			is_flag = true;
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return is_flag;
	}
}
