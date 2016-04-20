package cn.com.chinaebi.dz.object.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.util.DYDataUtil;

public class FtpUtil {
	private static Log log = LogFactory.getLog(FtpUtil.class);

	private static final ResourceBundle res = ResourceBundle.getBundle("conf");
	// 主机ip
	private static final String FtpHost = res.getString("FtpHost");
	// 端口号
	private static final int FtpPort = Integer
			.valueOf(res.getString("FtpPort"));
	// ftp用户名
	private static final String FtpUser = res.getString("FtpUser");
	// ftp密码
	private static final String FtpPassword = res.getString("FtpPassword");

	/**
	 *  上传文件至FTP通用方法
	 * @param ftpHost	FTP IP
	 * @param port		FTP 端口
	 * @param userName	用户名
	 * @param passWord	密码
	 * @param ftpFilePath	保存路径
	 * @param is		FileInputStream     io流
	 * @param fileName	文件名称
	 * @param date		日期
	 * @return		Boolean值
	 * @throws Exception
	 */
	public static synchronized boolean upLoadFileFtp(String ftpHost,int port,String userName,String passWord,String ftpFilePath,
			InputStream is, String fileName, String date) throws Exception {
		FTPClient ftpClient = null;
		try {
			String path = ftpFilePath + "/" + date;
			ftpClient = getFTPClient(ftpHost, passWord, userName,port);
			ftpClient.login(userName, passWord);// userName、passWord分别为FTP服务器的登陆用户名和密码
	        
			String[] pathArr = path.split("\\/");
			for (int i = 0; i < pathArr.length; i++) {
				if (StringUtils.isNotBlank(pathArr[i])) {
					if (isDirExist(pathArr[i], ftpClient)) {
						log.info("当前文件夹已经存在");
					} else {
						log.info("创建文件夹"+pathArr[i]);
						ftpClient.makeDirectory(pathArr[i]);
						ftpClient.changeWorkingDirectory(pathArr[i]);
						
					}
				}
			}
			ftpClient.changeWorkingDirectory(path);
			
	        return ftpClient.storeFile(fileName, is);  
	        
		} catch (Exception e) {
			log.error("上传文件失败！请检查系统FTP设置,并确认FTP服务启动");
			log.error(e);
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
	 * 下载FTP文件
	 * @param fileName	待下载文件名称
	 * @param outputStream	写文件io流
	 * @param inst_id	渠道ID
	 * @param ftp_file_path	ftp文件存放路径
	 * @return
	 * @throws Exception
	 */
	public static boolean downloadFileFtp(String fileName, OutputStream outputStream, int inst_id,String ftp_file_path) throws Exception {
		boolean flag = false;
		InputStream in = null; 
		try {
			String ftpHost = FtpHost;
			int port = FtpPort;
			String userName = FtpUser;
			String passWord = FtpPassword;
			String path = "";
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			String date = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			// 通过参数渠道ID指定不同的下载对账文件路径
			if (inst_id == 11 || inst_id == 55001) {
				path = ftp_file_path + date + "/";
			} else {
				path = ftp_file_path;
			}
			FTPClient ftpClient = getFTPClient(ftpHost, passWord, userName,port);
			ftpClient.changeWorkingDirectory(path);// path为FTP服务器上保存上传文件的路径。
			try {
				boolean f = isDirExist(path, ftpClient);
				if (f) {
					log.info("找到指定文件夹");
				} else {
					throw new FileNotFoundException();
				}
				log.info("切换到目录：" + ftpClient.pwd());
				log.info("即将下载的文件名称为：" + fileName);
				in = ftpClient.retrieveFileStream(fileName);
				if (in != null) { 
					byte[] bytes = new byte[1024];
					int cnt = 0;
					while ((cnt = in.read(bytes, 0, bytes.length)) != -1) {
						outputStream.write(bytes, 0, cnt);
					}
					outputStream.close();
					in.close();
					flag = true;
				}else{
					log.error("ftpClient.retrieveFileStream(fileName) 返回NULL");
				}
			} catch (Exception e) {
				log.error(e);
				ftpClient.disconnect();
			}finally{  
                try {  
                    ftpClient.disconnect();  
                } catch (IOException e) {  
                	log.error(e); 
                }  
            } 
		} catch (Exception e) {
			log.error(e);
			log.info("下载文件失败！请检查系统FTP设置,并确认FTP服务启动");
		}
		return flag;
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

	/**
	 * 到FTP服务器上拉取对账文件
	 * @param inst_id	渠道ID
	 * @param inst_name	渠道名称
	 * @param file_path	拉取后对账文件存放路径
	 * @param file_pattern	对账文件格式
	 * @param ftp_file_path	FTP对账文件存放路径
	 * @param inst_type	渠道类型
	 * @param whether_T_1	渠道是否T-1
	 * @param fileFlag	文件类型  true-一般对账文件 ; false--差错文件
	 * @return
	 */
	public static boolean downLoadFile(int inst_id,String inst_name,String file_path,String file_pattern,String ftp_file_path,int inst_type,boolean whether_T_1, boolean fileFlag) {
		boolean flag = false;
		FileOutputStream out = null;
		try {
			Calendar calendar = Calendar.getInstance();
			if (whether_T_1) {
				calendar.add(Calendar.DATE, -1);
			}
			String date = DYDataUtil.getSimpleDateFormat("yyMMdd").format(
					calendar.getTime());
			String date_zx = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(
					calendar.getTime());
			String filePattern = "";

			if (file_pattern.contains(DataStatus.date_format_2)) {
				filePattern = file_pattern.replace(DataStatus.date_format_2, date_zx);
			} else if (file_pattern.contains(DataStatus.date_format_5) && !file_pattern.contains(DataStatus.date_format_2)) {
				if ((inst_id == 11 || inst_id == 55001) && !fileFlag) {// 差错文件
					filePattern = "IND" + date + "99AERRN";
				} else {
					filePattern = file_pattern.replace(DataStatus.date_format_5, date);
				}
			}
			
			if(inst_type == 1){//线上渠道，添加日期文件夹
				file_path = file_path + "/" + DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());
				
				File file_ = new File(file_path);
				
				if(!file_.exists() && !file_.isDirectory()){
					file_.mkdir();
				}
			}

			log.info("当前渠道为：" + inst_name + ";指定的对账文件路径及文件为：" + file_path + "/" + filePattern);
			
			File file = new File(file_path + "/" + filePattern);
			out = new FileOutputStream(file);
			flag = downloadFileFtp(filePattern, out, inst_id,ftp_file_path);
			
			log.info("拉取"+inst_name+"一般对账文件成功");
			
			if (flag) {
				log.info("从FTP上下载对账文件成功");
			} else {
				log.info("从FTP上下载对账文件失败");
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return flag;
	}

	/**
	 * 从银行的FTP上获取对账文件(FTP:10.248.2.9)
	 * 
	 * @param fileName
	 * @param clientFileName
	 * @param outputStream
	 * @param instInfo
	 * @return
	 * @throws Exception
	 */
	public static boolean downLoadBankFileFtp(String fileName,String clientFileName, InstInfo instInfo)
			throws Exception {
		boolean flag = false;
		FTPClient ftpClient = null;
		FTPClient _ftpClient = null;
		try {
			String bankFtpHost = null;
			int bankPort = 0;
			String bankUserName = null;
			String bankPassWord = null;
			String bankPath = "";
			BankInst bankInst = instInfo.getBank();
			Calendar calendar = Calendar.getInstance();
			if (instInfo.isBankftpDownload()) {
				bankFtpHost = instInfo.getBankftpIp();
				bankPort = Integer.valueOf(instInfo.getBankftpPort());
				bankUserName = instInfo.getBankftpUsername();
				bankPassWord = instInfo.getBankftpPassword();
				bankPath = instInfo.getBankftpPath();
				if (instInfo.isWhether_T_1()) {
					calendar.add(Calendar.DATE, -1);
				} else {
					calendar.add(Calendar.DATE, 0);
				}
				if (bankPath.contains(DataStatus.date_format_1)) {
					String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_1).format(calendar.getTime());
					bankPath = bankPath.replace(DataStatus.date_format_1, date);
				} else if (bankPath.contains(DataStatus.date_format_2)) {
					String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());
					bankPath = bankPath.replace(DataStatus.date_format_2, date);
				} else if (bankPath.contains(DataStatus.date_format_3)) {
					String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_3).format(calendar.getTime());
					bankPath = bankPath.replace(DataStatus.date_format_3, date);
				} else if (bankPath.contains(DataStatus.date_format_4)) {
					String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_4).format(calendar.getTime());
					bankPath = bankPath.replace(DataStatus.date_format_4, date);
				} else if (bankPath.contains(DataStatus.date_format_5)) {
					String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_5).format(calendar.getTime());
					bankPath = bankPath.replace(DataStatus.date_format_5, date);
				} else {
					String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_6).format(calendar.getTime());
					bankPath = bankPath.replace(DataStatus.date_format_6, date);
				}
			}
			ftpClient = getFTPClient(bankFtpHost, bankPassWord, bankUserName, bankPort);
            ftpClient.changeWorkingDirectory(bankPath); 
			try {
				boolean f = isDirExist(bankPath, ftpClient);
				if (f) {
					log.info("找到指定文件夹");
				} else {
					throw new FileNotFoundException();
				}
				log.info("即将下载的文件名称为：" + fileName);
				flag = true;
				if (flag) {
					try {
						String ftpHost = FtpHost;
						int port = FtpPort;
						String userName = FtpUser;
						String passWord = FtpPassword;
						String path = bankInst.getFtpDzFilePath();
						
						_ftpClient = getFTPClient(ftpHost, passWord, userName, port);// ftpHost为FTP服务器的IP地址，port为FTP服务器的登陆端口,ftpHost为String型,port为int型。
						boolean f_ = isDirExist(path, _ftpClient);
						if (f_) {

						} else {
							String[] pathArr = path.split("/");
							for (int i = 0; i < pathArr.length; i++) {
								if (StringUtils.isNotBlank(pathArr[i])) {
									if (isDirExist(pathArr[i], _ftpClient)) {

									} else {
//										createDir(pathArr[i], _ftpClient);
										_ftpClient.makeDirectory(pathArr[i]);
										_ftpClient.changeWorkingDirectory(pathArr[i]);
									}
								}
							}
						}
						InputStream in = ftpClient.retrieveFileStream(fileName);
						
						_ftpClient.changeWorkingDirectory(path);
						
						_ftpClient.storeFile(fileName, in);
						
						in.close();
						
						_ftpClient.disconnect();
						
						return true;
					} catch (Exception e) {
						log.error("从银行FTP下载文件失败！请检查系统FTP设置,并确认FTP服务启动");
						log.error(e);
						throw e;
					}
				
				}
			} catch (Exception e) {
				log.error(e);
				ftpClient.disconnect();
			}
			ftpClient.disconnect();
		} catch (Exception e) {
			log.info("从银行FTP下载文件失败！请检查银行FTP设置,并确认FTP服务启动" + e.getMessage());
		}
		return flag;
	}

	/**
	 * 下载银行FTP对账文件
	 * 
	 * @param instInfo
	 * @param fileFlag
	 * @return
	 */
	public static boolean downLoadBankFile(InstInfo instInfo, boolean fileFlag) {
		boolean flag = false;
		boolean flag_ = false;
		FileOutputStream out = null;
		if (instInfo == null) {
			return false;
		}
		BankInst bankInst = instInfo.getBank();
		try {
			String path = bankInst.getDzFilePath();
			String filePattern = "";
			
			Calendar calendar = Calendar.getInstance();
			
			if (instInfo.isWhether_T_1()) {
				calendar.add(Calendar.DATE, -1);
			} else {
				calendar.add(Calendar.DATE, 0);
			}
			if (bankInst.getDzFileNamePattern().contains(DataStatus.date_format_1)) {
				String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_1).format(calendar.getTime());
				filePattern = bankInst.getDzFileNamePattern().replace(DataStatus.date_format_1, date);
			} else if (bankInst.getDzFileNamePattern().contains(DataStatus.date_format_2)) {
				String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());
				filePattern = bankInst.getDzFileNamePattern().replace(DataStatus.date_format_2, date);
			} else if (bankInst.getDzFileNamePattern().contains(DataStatus.date_format_3)) {
				String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_3).format(calendar.getTime());
				filePattern = bankInst.getDzFileNamePattern().replace(DataStatus.date_format_3, date);
			} else if (bankInst.getDzFileNamePattern().contains(DataStatus.date_format_4)) {
				String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_4).format(calendar.getTime());
				filePattern = bankInst.getDzFileNamePattern().replace(DataStatus.date_format_4, date);
			} else if (bankInst.getDzFileNamePattern().contains(DataStatus.date_format_5)) {
				String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_5).format(calendar.getTime());
				filePattern = bankInst.getDzFileNamePattern().replace(DataStatus.date_format_5, date);
			} else {
				String date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_6).format(calendar.getTime());
				filePattern = bankInst.getDzFileNamePattern().replace(DataStatus.date_format_6, date);
			}

			log.info("当前渠道为：" + bankInst.getBankName() + ";银行FTP指定的对账文件路径及文件为：" + instInfo.getBankftpPath() + "/" + filePattern);

			File file = new File(path + "/" + filePattern);
			out = new FileOutputStream(file);
			flag = downLoadBankFileFtp(filePattern, file.getName(), instInfo);
			if (flag) {
				log.info("从银行FTP"+instInfo.getBankftpIp()+"上下载对账文件存放到"+FtpHost+"成功");
				flag_ = downLoadFile(instInfo.getId().getInstId(), instInfo.getName(), bankInst.getDzFilePath(), bankInst.getDzFileNamePattern(), bankInst.getFtpDzFilePath(), instInfo.getId().getInstType(),instInfo.isWhether_T_1(),true);
				if (flag_) {
					log.info("从"+FtpHost+"目录" + bankInst.getFtpDzFilePath() + "将对账文件存放到对账系统" + bankInst.getDzFilePath() +"目录下成功");
				} else {
					log.info("从"+FtpHost+"目录" + bankInst.getFtpDzFilePath() + "将对账文件存放到对账系统" + bankInst.getDzFilePath() +"目录下失败");
				}
			} else {
				log.info("从银行FTP"+instInfo.getBankftpIp()+"上下载对账文件存放到"+FtpHost+"失败");
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		return flag_;
	}
	
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
            
            ftpClient.setControlEncoding("UTF-8"); // 中文支持  
	        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
	        ftpClient.enterLocalPassiveMode();
        } catch (SocketException e) {  
            log.info("FTP的IP地址可能错误，请正确配置。"+e);  
        } catch (IOException e) {  
            log.info("FTP的端口错误,请正确配置。"+e);  
        }  
        return ftpClient;  
    } 
}
