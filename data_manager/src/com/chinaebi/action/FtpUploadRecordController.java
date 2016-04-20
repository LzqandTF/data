package com.chinaebi.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.CustomObject;
import com.chinaebi.entity.FtpUploadRecord;
import com.chinaebi.service.CustomObjectService;
import com.chinaebi.service.EmailPoliceService;
import com.chinaebi.service.FtpUploadRecordService;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.FtpUtil;
import com.chinaebi.utils.MailSendInfoUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * FTP文件上传管理Controler
 * @author zhu.hongliang
 */
@Controller
public class FtpUploadRecordController {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	public static final String FTP_FILE_UPLOAD = "/ftpUpload/ftpFileUpload";
	
	//分页查询
	private static final String QUERYPAGEFTPUPLOADRECORD = "queryPageFtpUploadRecord.do";
	private static final String UPLOADDATATOFTP = "/uploadDataToFtp.do";
	
	@Autowired
	@Qualifier(value = "ftpUploadRecordService")
	private FtpUploadRecordService ftpUploadRecordService;
	
	@Autowired
	@Qualifier(value = "customObjectService")
	private CustomObjectService customObjectService;
	
	@Autowired
	@Qualifier(value = "emailPoliceService")
	private EmailPoliceService emailPoliceService;
	
	
	@RequestMapping(value=QUERYPAGEFTPUPLOADRECORD)
	public String  queryPageFtpUploadRecord(ServletRequest request,Model model){
		log.info("进入查询FTP文件上传管理页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		String deduct_stlm_date = request.getParameter("deduct_stlm_date");
		String object_id = request.getParameter("object_id");
		String upload_status = request.getParameter("upload_status");
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(deduct_stlm_date)){
			map.put("deduct_stlm_date", deduct_stlm_date);
		}
		if(StringUtils.isNotBlank(object_id)){
			map.put("object_id", Integer.valueOf(object_id));
		}
		if(StringUtils.isNotBlank(upload_status)){
			map.put("upload_status", upload_status);
		}
		
		Page<FtpUploadRecord> page = new Page<FtpUploadRecord>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize)){
			model.addAttribute("pageSize",pageSize);
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		}else 
			page.setPageSize(10);
		
		model.addAttribute("pageFtpUploadRecord",ftpUploadRecordService.queryPageFtpUploadRecord(page, map));
		
		List<CustomObject> customObjectList = customObjectService.queryCustomObjectList();
		model.addAttribute("customObjectList", customObjectList);
		
		model.addAttribute("object_id",object_id);
		model.addAttribute("deduct_stlm_date",deduct_stlm_date);
		model.addAttribute("upload_status",upload_status);
		
		return FTP_FILE_UPLOAD;
	}
	
	@RequestMapping(value = UPLOADDATATOFTP, method=RequestMethod.POST)
	@ResponseBody
	public int uploadDataToFtp(ServletRequest request){
		String object_id = request.getParameter("object_id");
		String deductStlmDate = request.getParameter("date");
		String upload_content = request.getParameter("upload_content");
		
		int ftp_record = 2;
		CustomObject customObject = customObjectService.queryCustomObjectById(Integer.valueOf(object_id));
		
		if(StringUtils.isNotBlank(object_id) && StringUtils.isNotBlank(deductStlmDate)){
			String dz_content = customObject.getFile_address()+"/"+deductStlmDate+"/"+deductStlmDate+customObject.getDz_file_name()+customObject.getFile_suffix();
			
			Calendar calendar_ = Calendar.getInstance();//系统当前时间
			Date date = DateUtil.parseDateFormat(deductStlmDate, "yyyy-MM-dd");
			calendar_.setTime(date);
			calendar_.add(Calendar.DATE, 1);
			String ccFileDate = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd");
			String cc_content = customObject.getFile_address()+"/"+deductStlmDate+"/"+ccFileDate+customObject.getError_file_name()+customObject.getFile_suffix();
			
			log.info("得到的对账文件路径以及文件名称为："+dz_content);
			log.info("得到的差错文件路径以及文件名称为："+cc_content);
			if(StringUtils.isNotBlank(upload_content)){
				if(upload_content.contains(customObject.getDz_file_name())){
					try{
						File file_dz = new File(dz_content);
						FileInputStream is_dz = new FileInputStream(file_dz);
						log.info("获得系统名称为："+customObject.getObject_name()+"获得的主机为："+customObject.getFtp_ip()+"获得的端口号为："+customObject.getFtp_port()+"获得的用户名称为："+customObject.getFtp_username()+"获得的密码为："+customObject.getFtp_password()	+"获得的FTP上传地址为："+customObject.getFtp_address());
						boolean uploadflag_dz = FtpUtil.upLoadFileFtp(customObject, is_dz, file_dz.getName(),deductStlmDate);
						if(uploadflag_dz){
							log.info("上传对账总表"+dz_content+"到FTP操作成功");
							ftp_record = 1;
						}else{
							log.info("上传对账总表"+dz_content+"到FTP操作失败");
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.FTP_UPLOAD_DZ_FILE,emailPoliceService,deductStlmDate,customObject,"(FTP上传对账文件失败)");
						}
					}catch(Exception e){
						log.error(e.getMessage());
						MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.FTP_UPLOAD_DZ_FILE,emailPoliceService,deductStlmDate,customObject,"(FTP上传对账文件失败)");
					}
				}
				if(upload_content.contains(customObject.getError_file_name())){
					try{
						File file_cc = new File(cc_content);
						FileInputStream is_cc = new FileInputStream(file_cc);
						boolean uploadflag_cc = FtpUtil.upLoadFileFtp(customObject, is_cc, file_cc.getName(),deductStlmDate);
						if(uploadflag_cc){
							ftp_record = 1;
							log.info("上传差错总表"+cc_content+"到FTP操作成功");
						}else{
							log.info("上传差错总表"+cc_content+"到FTP操作失败");
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.FTP_UPLOAD_DZ_FILE,emailPoliceService,deductStlmDate,customObject,"(FTP上传对账文件失败)");
						}
					}catch(Exception e){
						log.error(e.getMessage());
						MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.FTP_UPLOAD_DZ_FILE,emailPoliceService,deductStlmDate,customObject,"(FTP上传对账文件失败)");
					}
				}
				
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("object_id", Integer.valueOf(object_id));
				map.put("deduct_stlm_date", deductStlmDate);
				FtpUploadRecord ftpUploadRecord = ftpUploadRecordService.queryFtpUploadRecordByObjectIdAndDate(map);
				if(ftpUploadRecord == null){
					ftpUploadRecord = new FtpUploadRecord();
					ftpUploadRecord.setDeduct_stlm_date(deductStlmDate);
					ftpUploadRecord.setObject_id(Integer.valueOf(object_id));
					ftpUploadRecord.setObject_name(customObject.getObject_name());
					ftpUploadRecord.setUpload_status(ftp_record);
					ftpUploadRecord.setUpload_content(dz_content.substring(dz_content.lastIndexOf("/")+1, dz_content.length())+";"+cc_content.substring(cc_content.lastIndexOf("/")+1, cc_content.length()));
					ftpUploadRecord.setGenerate_time(new Date());
					int insertNum = ftpUploadRecordService.insertFtpUploadRecord(ftpUploadRecord);
					if(insertNum > 0){
						log.info("添加FTP上传记录成功");
					}else{
						log.info("添加FTP上传记录失败");
					}
				}else{
					ftpUploadRecord.setUpload_status(ftp_record);
					int updateNum = ftpUploadRecordService.updateFtpUploadRecord(ftpUploadRecord);
					if(updateNum > -1){
						log.info("修改FTP上传记录成功");
					}else{
						log.info("修改FTP上传记录失败");
					}
				}
			}
		}
		return ftp_record;
	}
}
