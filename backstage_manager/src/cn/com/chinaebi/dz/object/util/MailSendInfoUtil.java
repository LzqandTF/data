package cn.com.chinaebi.dz.object.util;

/**
 ** 发送邮件需要使用的基本信息   
 */
import java.util.Map;
import java.util.Properties;

import cn.com.chinaebi.dz.base.SimpleMailSender;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO;

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
	
	/*
	 * 邮件报警大类
	 */
	public static final int ORIGINAL_DATA_COLLECT = 1;//数据汇总
	public static final int DZ_FILE_PULL = 2;//对账文件拉取
	public static final int DZ_FILE_PARSE = 3;//对账文件解析
	public static final int DZ_ERROR = 4;//对账
	public static final int CREATE_FILE = 5;//生成文件
	public static final int FTP_UPLOAD_FILE = 6;//FTP上传文件
	
	/*
	 * 数据汇总类
	 */
	public static final String DATA_COLLCT_ERROR = "汇总操作失败";
	public static final String DATA_SPLIT_ERROR = "数据分割失败";
	public static final String DATA_MONEY_ERROR = "数据金额处理失败";
	public static final String DATA_STATUS_ERROR = "数据无需对账状态处理失败";
	public static final String DATA_ZERO_COUNT	= "数据汇总零条";
	public static final String DATA_ONLINE_ERROR = "线上收款交易数据汇总失败";
	public static final String DATA_REFUND_ERROR = "线上退款交易数据汇总失败";
	public static final String DATA_TOU_CUN_DIAO_BO_FAIL = "交易数据头寸调拨失败";
	public static final String ONLINE_DATA_TCDO_FAIL = "线上收款交易数据头寸调拨失败";
	public static final String REFUND_DATA_TCDO_FAIL = "线上退款交易数据头寸调拨失败";
	
	/*
	 * 对账单解析类
	 */
	public static final String NO_FILE_ERROR = "对账单不存在";
	public static final String NO_ERROR_FILE = "差错对账单不存在";
	public static final String NULL_FILE_ERROR = "对账单内容为空";
	public static final String ERROR_FILE_IS_NULL = "差错对账单内容为空";
	public static final String PARSE_FILE_ERROR = "解析对账单失败";
	public static final String ERROR_FILE_PARSE_FAIL = "差错文件解析失败";
	
	/*
	 * FTP 对账单拉取类
	 */
	public static final String PULL_FILE_ERROR = "FTP对账单拉取失败";
	public static final String PULL_ERROR_FILE_FAIL = "FTP差错对账单拉取失败";
	
	/*
	 * 对账类
	 */
	public static final String NO_ORI_DATA = "交易数据不存在";
	public static final String NO_DZ_DATA = "对账数据不存在";
	public static final String EXCUTE_DZ_ERROR = "对账操作失败";
	public static final String EXCUTE_ERROR_DZ_FAIL = "差错对账失败";
	public static final String NO_ERROR_DZ_DATA = "差错对账数据不存在";
	
	/*
	 * 对账文件生成类
	 */
	public static final String NO_COLUMN_ERROR = "未配置对账字段,生成空文件";
	public static final String CREATRE_FILE_ERROR = "生成对账文件失败";
	public static final String CREATE_ERROR_FILE_FAIL = "生成差错对账文件失败";
	
	/*
	 * FTP上传文件失败
	 */
	public static final String PUSH_DZ_FILE_ERROR = "FTP对账文件上传失败";
	public static final String PUSH_JS_FILE_ERROR = "FTP结算单上传失败";
	
	
	
	
	
	public static void sendEmailForErrorWork(int eventId,EmailPoliceDAO emailPoliceDAO,String tradeTime) throws Exception{
		Map<String, Object> map = emailPoliceDAO.queryByPoliceId(eventId);
		if (map != null && map.size() > 0) {
			MailSendInfoUtil.sendmail(map.get("emailTheme").toString(), "日期为"+tradeTime+"的"+map.get("emailContent"),  map.get("email").toString().split(","));
		}
	}
	
	public static void sendEmailForInstInfoError(int eventId,EmailPoliceDAO emailPoliceDAO,String tradeTime,InstInfo inst,String otherInformation) throws Exception{
		Map<String, Object> map = emailPoliceDAO.queryByPoliceId(eventId);
		if (map != null && map.size() > 0) {
			MailSendInfoUtil.sendmail(inst.getName()+map.get("emailTheme").toString(), "日期为"+tradeTime+"的"+inst.getName()+map.get("emailContent")+otherInformation,  map.get("email").toString().split(","));
		}
	}
	
	public static void sendEmailForBankInstError(int eventId,EmailPoliceDAO emailPoliceDAO,String tradeTime,BankInst bankInst,String otherInformation) throws Exception{
		Map<String, Object> map = emailPoliceDAO.queryByPoliceId(eventId);
		if (map != null && map.size() > 0) {
			MailSendInfoUtil.sendmail(bankInst.getBankName()+map.get("emailTheme").toString(), "日期为"+tradeTime+"的"+bankInst.getBankName()+map.get("emailContent")+otherInformation,  map.get("email").toString().split(","));
		}
	}
	
	public static void sendEmailForInstInfoCreate(int eventId,EmailPoliceDAO emailPoliceDAO,String tradeTime,String otherInformation) throws Exception{
		Map<String, Object> map = emailPoliceDAO.queryByPoliceId(eventId);
		if (map != null && map.size() > 0) {
			MailSendInfoUtil.sendmail(otherInformation+map.get("emailTheme").toString(), "日期为"+tradeTime+"的"+otherInformation+map.get("emailContent"), map.get("email").toString().split(","));
		}
	}
	public static void sendEmailForCustomObjectJsFileCreate(int eventId,EmailPoliceDAO emailPoliceDAO,String tradeTime,String customObjectName,String descripe) throws Exception{
		Map<String, Object> map = emailPoliceDAO.queryByPoliceId(eventId);
		if (map != null && map.size() > 0) {
			MailSendInfoUtil.sendmail(customObjectName+map.get("emailTheme").toString(), "日期为"+tradeTime+"的"+customObjectName+map.get("emailContent")+descripe, map.get("email").toString().split(","));
		}
	}
}