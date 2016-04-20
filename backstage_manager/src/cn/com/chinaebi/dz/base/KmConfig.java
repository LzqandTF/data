package cn.com.chinaebi.dz.base;

/**
 * FTP 属性配置
 */
public class KmConfig {
	//主机ip
	private String FtpHost = "192.168.2.141";
	//端口号
	private int FtpPort = 21;
	//ftp用户名
	private String FtpUser = "posdz";
	//ftp密码
	private String FtpPassword = "1qaz2wsx";
	//ftp中的目录	
	private String FtpPath_bjyh = "/bjyh/";
	private String FtpPath_cups = "/chinaebi/48720000/";
	private String FtpPath_zxyh = "/zxyh/";
	private String path_bjyh = "/var/www/apps/java/data/bjyh/";
	private String path_cups = "/var/www/apps/java/data/cups/";
	private String path_zxyh = "/var/www/apps/java/data/zxyh/";
	public String getFtpHost() {
		return FtpHost;
	}
	public void setFtpHost(String ftpHost) {
		FtpHost = ftpHost;
	}
	public int getFtpPort() {
		return FtpPort;
	}
	public void setFtpPort(int ftpPort) {
		FtpPort = ftpPort;
	}
	public String getFtpUser() {
		return FtpUser;
	}
	public void setFtpUser(String ftpUser) {
		FtpUser = ftpUser;
	}
	public String getFtpPassword() {
		return FtpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		FtpPassword = ftpPassword;
	}
	public String getFtpPath_bjyh() {
		return FtpPath_bjyh;
	}
	public void setFtpPath_bjyh(String ftpPath_bjyh) {
		FtpPath_bjyh = ftpPath_bjyh;
	}
	public String getFtpPath_cups() {
		return FtpPath_cups;
	}
	public void setFtpPath_cups(String ftpPath_cups) {
		FtpPath_cups = ftpPath_cups;
	}
	public String getFtpPath_zxyh() {
		return FtpPath_zxyh;
	}
	public void setFtpPath_zxyh(String ftpPath_zxyh) {
		FtpPath_zxyh = ftpPath_zxyh;
	}
	public String getPath_bjyh() {
		return path_bjyh;
	}
	public void setPath_bjyh(String path_bjyh) {
		this.path_bjyh = path_bjyh;
	}
	public String getPath_cups() {
		return path_cups;
	}
	public void setPath_cups(String path_cups) {
		this.path_cups = path_cups;
	}
	public String getPath_zxyh() {
		return path_zxyh;
	}
	public void setPath_zxyh(String path_zxyh) {
		this.path_zxyh = path_zxyh;
	}
	
}
