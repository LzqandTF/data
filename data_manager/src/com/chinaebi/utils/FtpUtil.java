package com.chinaebi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.chinaebi.entity.CustomObject;

public class FtpUtil {
	private static Log log = LogFactory.getLog(FtpUtil.class);
	
	/** 
     * 获取FTPClient对象 
     * @param ftpHost FTP主机服务器 
     * @param ftpPassword FTP 登录密码 
     * @param ftpUserName FTP登录用户名 
     * @param ftpPort FTP端口 默认为21 
     * @return 
     */  
    public static FTPClient getFTPClient(String ftpHost, String ftpPassword,  
            String ftpUserName, int ftpPort) {  
        FTPClient ftpClient = null;  
        try {  
            ftpClient = new FTPClient();  
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器  
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器  
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
                log.info("未连接到FTP，用户名或密码错误。");  
                ftpClient.disconnect();  
            } else {  
                log.info("FTP连接成功。");  
            }  
        } catch (SocketException e) {  
            log.info("FTP的IP地址可能错误，请正确配置。"+e);  
        } catch (IOException e) {  
            log.info("FTP的端口错误,请正确配置。"+e);  
        }  
        return ftpClient;  
    }
	
	/**
	 * 文件上传
	 * @param obj : 接口对象
	 * @param is ：文件字符流
	 * @param fileName ：文件名称
	 * @param date ：日期
	 */
	public static synchronized boolean upLoadFileFtp(CustomObject obj,InputStream is, String fileName,String date) throws Exception{
		FTPClient ftpClient = null;
		try {
			String ftpHost = obj.getFtp_ip();
			int port = Integer.valueOf(obj.getFtp_port());
			String userName = obj.getFtp_username();
			String passWord = obj.getFtp_password();
			String path = obj.getFtp_address()+"/"+date;
			ftpClient = getFTPClient(ftpHost,passWord,userName, port);// ftpHost为FTP服务器的IP地址，port为FTP服务器的登陆端口,ftpHost为String型,port为int型。
			boolean f = isDirExist(path,ftpClient);
			if(f){
				
			}else{
				String[] pathArr = path.split("/");
				for(int i=0;i<pathArr.length;i++){
					if(StringUtils.isNotBlank(pathArr[i])){
						if(isDirExist(pathArr[i],ftpClient)){
							log.info("当前文件夹已经存在");
						}else{
//							createDir(pathArr[i],ftpClient);
							ftpClient.makeDirectory(pathArr[i]);
							ftpClient.changeWorkingDirectory(pathArr[i]);
						}
					}
				}
			}
			
			ftpClient.setControlEncoding("UTF-8"); // 中文支持  
	        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			ftpClient.changeWorkingDirectory(path);
			
			ftpClient.enterLocalPassiveMode();
			
			return ftpClient.storeFile(fileName, is);  
			
		} catch (Exception e) {
			log.error("上传文件失败！请检查系统FTP设置,并确认FTP服务启动 ："+e);
			throw e;
		}finally{
			if(is != null){
				is.close();	
			}
			if(ftpClient != null){
				ftpClient.disconnect();
			}
		}
	}
	/** 
	 * 在ftp服务器上创建文件夹
	 * @param path	目标路径
	 * @param ftpClient
	 * @return
	 * @throws Exception
	 */
	public static boolean createDir(String path, FTPClient ftpClient)
			throws Exception {
		ftpClient.sendCommand("MKD " + path + "\r\n");
		ftpClient.setControlEncoding("UTF-8"); // 中文支持  
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
		return false;
	}

	/**
	 * 检查文件夹是否存在
	 * @param dir 目标文件夹路径
	 * @param ftpClient
	 * @return
	 */
	public static boolean isDirExist(String dir, FTPClient ftpClient) {
		try {
			log.info("判断路径" + dir + "是否存在");
			return ftpClient.changeWorkingDirectory(dir);
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
}
