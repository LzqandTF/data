package com.chinaebi.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.CustomObject;
import com.chinaebi.entity.DzFileInfo;
import com.chinaebi.entity.FtpUploadRecord;
import com.chinaebi.service.CustomObjectService;
import com.chinaebi.service.DzFileInfoService;
import com.chinaebi.service.EmailPoliceService;
import com.chinaebi.service.FtpUploadRecordService;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.FtpUtil;
import com.chinaebi.utils.MailSendInfoUtil;

@Controller
public class DzFileUploadController {
	
	protected Logger log = LoggerFactory.getLogger(DzFileUploadController.class);
	
	private final String UPLOADDZFILE_TXT = "/uploadDzFile_txt.do";
	private final String UPLOADDZFILE_XLS = "/uploadDzFile_xls.do";
	
	//手动生成对账总表
//	private final String MANUALCREATEDZFILE = "/manualCreateDzFile.do";
	private final String CREATEDZFILE = "/createDzFile.do";
	//手动生成对账总表的页面
//	private final String MANUALCREATEDZFILEINFO = "/duizhangFileUpload/manualCreateDzFile";
	
	@Autowired
	@Qualifier(value = "dzFileInfoService")
	private DzFileInfoService dzFileInfoService;
	
	@Autowired
	@Qualifier(value = "customObjectService")
	private CustomObjectService customObjectService;
	
	@Autowired
	@Qualifier(value = "ftpUploadRecordService")
	private FtpUploadRecordService ftpUploadRecordService;
	
	@Autowired
	@Qualifier(value = "emailPoliceService")
	private EmailPoliceService emailPoliceService;
	
	@RequestMapping(value=UPLOADDZFILE_TXT)
	public void downloadFileTxt(HttpServletResponse response,HttpServletRequest req) throws Exception{    
        response.setCharacterEncoding("utf-8");    
        response.setContentType("multipart/form-data");
        
        String deduct_sys_date = req.getParameter("deduct_sys_date");
        String fileType = new String(req.getParameter("fileType").getBytes("iso-8859-1"),"utf-8").trim();
        String file_id = req.getParameter("file_id");
        DzFileInfo dzFileInfo = dzFileInfoService.queryDzFileInfoById(Integer.valueOf(file_id));
        String file_name = dzFileInfo.getFile_name();
        String path = dzFileInfo.getFile_path();
        
        if(fileType.equals("差错文件总表")){
			Calendar calendar_ = Calendar.getInstance();//系统当前时间
			Date date = DateUtil.parseDateFormat(deduct_sys_date, "yyyy-MM-dd");
			calendar_.setTime(date);
			calendar_.add(Calendar.DATE, -1);
			deduct_sys_date = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd");
        }
        
        
        try{
        	response.setHeader("Content-Disposition", "attachment;fileName="+new String((file_name+".txt").getBytes(),"iso8859-1"));
            // 读到流中
            InputStream inStream = new FileInputStream(path+"/"+deduct_sys_date+"/"+file_name+".txt");// 文件的存放路径
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            try {
                while ((len = inStream.read(b)) > 0)
                    response.getOutputStream().write(b, 0, len);
                inStream.close();
            } catch (IOException e) {
            	log.error(e.getMessage());
            }
        }catch(Exception e){
        	log.error(e.getMessage());
        }
    } 
	
	@RequestMapping(value=UPLOADDZFILE_XLS)
	public void downLoadFileXls(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");    
        response.setContentType("multipart/form-data");    
        String deduct_sys_date = request.getParameter("deduct_sys_date");
        String fileType = new String(request.getParameter("fileType").getBytes("iso-8859-1"),"utf-8");
        String file_id = request.getParameter("file_id");
        DzFileInfo dzFileInfo = dzFileInfoService.queryDzFileInfoById(Integer.valueOf(file_id));
        String file_name = dzFileInfo.getFile_name();
        String path = dzFileInfo.getFile_path();
        
        if(fileType.equals("差错文件总表")){
			Calendar calendar_ = Calendar.getInstance();//系统当前时间
			Date date = DateUtil.parseDateFormat(deduct_sys_date, "yyyy-MM-dd");
			calendar_.setTime(date);
			calendar_.add(Calendar.DATE, -1);
			deduct_sys_date = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd");
        }
        
        try{
        	
        	response.setHeader("Content-Disposition", "attachment;fileName="+new String((file_name+".xls").getBytes(),"iso8859-1"));
            // 读到流中
            InputStream inStream = new FileInputStream(path+"/"+deduct_sys_date+"/"+file_name+".xls");// 文件的存放路径
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            try {
                while ((len = inStream.read(b)) > 0)
                    response.getOutputStream().write(b, 0, len);
                inStream.close();
            } catch (IOException e) {
            	log.error(e.getMessage());
            }
        }catch(Exception e){
        	log.error(e.getMessage());
        }
	}	 
	
//	@RequestMapping(value = MANUALCREATEDZFILE)
//	public String manualCreateDzFile(ServletRequest request , Model model){
//		List<CustomObject> list = customObjectService.queryCustomObjectList();
//		model.addAttribute("customObjectList", list);
//		return MANUALCREATEDZFILEINFO;
//	}
	
	@RequestMapping(value = CREATEDZFILE ,method = RequestMethod.POST)
	@ResponseBody
	public int createDzFile(ServletRequest request){
		int effectNum = 0;
		String deductStlmDate = request.getParameter("summaryDate");
		String fileType = request.getParameter("fileType");
		String object_id = request.getParameter("object_id");
		try{
			effectNum = dzFileInfoService.createDzFile(0, deductStlmDate,Integer.valueOf(fileType),Integer.valueOf(object_id));
		}catch(Exception e){
			log.error(e.getMessage());
		}
		if(effectNum == 1){
			if(StringUtils.isNotBlank(object_id)){
				CustomObject customObject = customObjectService.queryCustomObjectById(Integer.valueOf(object_id));
				
				Calendar calendar_ = Calendar.getInstance();//系统当前时间
				Date date = DateUtil.parseDateFormat(deductStlmDate, "yyyy-MM-dd");
				calendar_.setTime(date);
				calendar_.add(Calendar.DATE, -1);
				String ccFileDate = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd");
				
				if(customObject.getWhether_upload() == 1){//1代表上传到FTP，0代表不上传到FTP
					StringBuffer dz_contentbBuffer = new StringBuffer();
					dz_contentbBuffer.append(customObject.getFile_address());
					dz_contentbBuffer.append("/");
					dz_contentbBuffer.append(deductStlmDate);
					dz_contentbBuffer.append("/");
					dz_contentbBuffer.append(deductStlmDate);
					dz_contentbBuffer.append(customObject.getDz_file_name());
					dz_contentbBuffer.append(customObject.getFile_suffix());
					String dz_content = dz_contentbBuffer.toString();
					
					StringBuffer cc_contentbBuffer = new StringBuffer();
					cc_contentbBuffer.append(customObject.getFile_address());
					cc_contentbBuffer.append("/");
					cc_contentbBuffer.append(ccFileDate);
					cc_contentbBuffer.append("/");
					cc_contentbBuffer.append(deductStlmDate);
					cc_contentbBuffer.append(customObject.getError_file_name());
					cc_contentbBuffer.append(customObject.getFile_suffix());
					String cc_content = cc_contentbBuffer.toString();
					
					log.info("得到的对账文件路径以及文件名称为："+dz_content);
					log.info("得到的差错文件路径以及文件名称为："+cc_content);
					int ftp_record = 2;
					String upload_content = "";
					log.info("获得系统名称为："+customObject.getObject_name()+"获得的主机为："+customObject.getFtp_ip()+"获得的端口号为："+customObject.getFtp_port()+"获得的用户名称为："+customObject.getFtp_username()+"获得的密码为："+customObject.getFtp_password()	+"获得的FTP上传地址为："+customObject.getFtp_address());
					if(StringUtils.isNotBlank(fileType)){
						if(Integer.valueOf(fileType) == 1){
							try{
								upload_content = dz_content.substring(dz_content.lastIndexOf("/")+1, dz_content.length());
								File file_dz = new File(dz_content);
								FileInputStream is_dz = new FileInputStream(file_dz);
								boolean uploadflag_dz = FtpUtil.upLoadFileFtp(customObject, is_dz, file_dz.getName(),deductStlmDate);
								if(uploadflag_dz){
									ftp_record = 1;
								}
							}catch(Exception e){
								log.error(e.getMessage());
								MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.FTP_UPLOAD_DZ_FILE,emailPoliceService,deductStlmDate,customObject,"FTP上传错误");
							}
						}else if(Integer.valueOf(fileType) == 2){
							try{
								upload_content = cc_content.substring(cc_content.lastIndexOf("/")+1, cc_content.length());
								File file_cc = new File(cc_content);
								FileInputStream is_cc = new FileInputStream(file_cc);
								boolean uploadflag_cc = FtpUtil.upLoadFileFtp(customObject, is_cc, file_cc.getName(),ccFileDate);
								if(uploadflag_cc){
									ftp_record = 1;
								}
							}catch(Exception e){
								log.error(e.getMessage());
								MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.FTP_UPLOAD_DZ_FILE,emailPoliceService,deductStlmDate,customObject,"FTP上传错误");
							}
						}
					}
					
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("object_id", Integer.valueOf(object_id));
					if(Integer.valueOf(fileType) == 1){
						map.put("deduct_stlm_date", deductStlmDate);
					}else if(Integer.valueOf(fileType) == 2){
						map.put("deduct_stlm_date", ccFileDate);
					}
					FtpUploadRecord ftpUploadRecord = ftpUploadRecordService.queryFtpUploadRecordByObjectIdAndDate(map);
					Calendar calendar = Calendar.getInstance();//系统当前时间
					if(ftpUploadRecord == null){
						ftpUploadRecord = new FtpUploadRecord();
						ftpUploadRecord.setDeduct_stlm_date(Integer.valueOf(fileType) == 1?deductStlmDate:ccFileDate);
						ftpUploadRecord.setObject_id(Integer.valueOf(object_id));
						ftpUploadRecord.setObject_name(customObject.getObject_name());
						ftpUploadRecord.setUpload_status(ftp_record);
						ftpUploadRecord.setUpload_content(upload_content);
						ftpUploadRecord.setGenerate_time(calendar.getTime());
						int insertNum = ftpUploadRecordService.insertFtpUploadRecord(ftpUploadRecord);
						if(insertNum > 0){
							log.info("添加FTP上传记录成功");
						}else{
							log.info("添加FTP上传记录失败");
						}
					}else{
						ftpUploadRecord.setUpload_status(ftp_record);
						ftpUploadRecord.setGenerate_time(calendar.getTime());
						if(ftpUploadRecord.getUpload_content().contains(upload_content)){
							
						}else{
							ftpUploadRecord.setUpload_content(ftpUploadRecord.getUpload_content()+";"+upload_content);
						}
						int updateNum = ftpUploadRecordService.updateFtpUploadRecord(ftpUploadRecord);
						if(updateNum > -1){
							log.info("修改FTP上传记录成功");
						}else{
							log.info("修改FTP上传记录失败");
						}
					}
				}
			}
		}
		
		return effectNum;
	}
}
