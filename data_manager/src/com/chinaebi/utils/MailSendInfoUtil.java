package com.chinaebi.utils;

/**
 ** 发送邮件需要使用的基本信息   
 */
import java.util.Map;
import java.util.Properties;

import com.chinaebi.entity.CustomObject;
import com.chinaebi.service.EmailPoliceService;


public class MailSendInfoUtil {
	// 发送邮件的服务器的IP和端口
	private String mailServerHost;
	private String mailServerPort = "25";
	// 邮件发送者的地址
	private String fromAddress;
	// 邮件接收者的地址
	private String[] toAddress;
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 是否需要身份验证
	private boolean validate = false;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private String[] attachFileNames;
	
	public static final int ORIGINAL_DATA_COLLECT = 1;//数据汇总
	public static final int DZ_FILE_PULL = 2;//对账文件拉取
	public static final int DZ_FILE_PARSE = 3;//对账文件解析
	public static final int DZ_ERROR = 4;//对账
	public static final int CREATE_DZ_FILE = 5;//生成对账文件
	public static final int FTP_UPLOAD_DZ_FILE = 6;//FTP上传对账文件

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		 Properties p = new Properties();
		  p.put("mail.smtp.host", this.mailServerHost);
		  p.put("mail.smtp.port", this.mailServerPort);
		  p.put("mail.smtp.auth", validate ? "true" : "false");
		  p.put("mail.smtp.starttls.enable", "true");
		  p.put("mail.smtp.ssl.checkserveridentity", "false");
		  p.put("mail.smtp.ssl.trust", "*");
		  return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] fileNames) {
		this.attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

	@SuppressWarnings("static-access")
	public static void sendmail(String Subject, String Content, String[] toAddress) {
		// 这个类主要是设置邮件
		MailSendInfoUtil mailInfo = new MailSendInfoUtil();
		mailInfo.setMailServerHost("mail.chinaebi.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("monitor.zhxt@chinaebi.com");
		mailInfo.setPassword("zhxt123456");// 您的邮箱密码
		mailInfo.setFromAddress("monitor.zhxt@chinaebi.com");
		mailInfo.setToAddress(toAddress);
		mailInfo.setSubject(Subject);
		mailInfo.setContent(Content);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailInfo);// 发送html格式
	}
	public static void sendEmailForInstInfoError(int eventId,EmailPoliceService emailPolice,String tradeTime,CustomObject instInfo,String otherInformation){
		Map<String, Object> map = emailPolice.queryEmailPoliceByEventId(eventId);
		if (map != null && map.size() > 0) {
			MailSendInfoUtil.sendmail(instInfo.getObject_name()+map.get("emailTheme").toString(), "日期为"+tradeTime+"的"+instInfo.getObject_name()+map.get("emailContent")+otherInformation,  map.get("email").toString().split(","));
		}
	}
	public static void main(String[] aa) {
		sendmail("test", "-_-!!!", new String[] { "shi.peng@chinaebi.com","jiang.lili@chinaebi.com"});
	}
}