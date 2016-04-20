package cn.com.chinaebi.mfs.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.util.FtpUtil;
import cn.com.chinaebi.dz.object.util.PropertiesUtils;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;

/**
 * 商户流水下载接口
 * @author wufei
 *
 */
public class MerFundStanceDataDownLoadService extends HttpServlet {
	private static final long serialVersionUID = 5802435885748848623L;

	private static Log log = LogFactory.getLog(MerFundStanceDataDownLoadService.class);
	private StringPingJie pingJie = StringPingJie.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	private String returnResultJson(String tranCode,String resCode,String resMsg,String merPriv){
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		    //---------tranCode(交易码)
			buffer.append("\"tranCode\":");
			buffer.append("\"");
			buffer.append(tranCode);
			buffer.append("\",");
			//---------resCode(返回码)
			buffer.append("\"resCode\":");
			buffer.append("\"");
			buffer.append(resCode);
			buffer.append("\",");
			//---------resMsg(返回信息)
			buffer.append("\"resMsg\":");
			buffer.append("\"");
			buffer.append(resMsg);
			buffer.append("\",");
			//---------merPriv(私有域)
			buffer.append("\"merPriv\":");
			buffer.append("\"");
			buffer.append(merPriv);
			buffer.append("\"");
		buffer.append("}");
		log.info(pingJie.getStringPingJie("返回结果:",buffer.toString()));
		return buffer.toString();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response){
		log.info("开始调用商户流水下载接口...");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String version = request.getParameter("version");  //版本号
		String tranCode = request.getParameter("tranCode");//交易码
		String merPriv = request.getParameter("merPriv");//商户私有域
		merPriv = merPriv==null?"":merPriv;
		log.info(pingJie.getStringPingJie("version = ",version,",tranCode = ",tranCode,",merPriv = ",merPriv));
		PrintWriter out = null;
		
		String resCode = "001";
		String resMsg = "参数传输错误,tranCode数据值错误";
		
		String mer_code = null;
		String start_date = null;
		String end_date = null;
		try{
			out = response.getWriter();
			//开始处理(商户流水下载)
			if(StringUtils.isNotBlank(version) && StringUtils.isNotBlank(tranCode)){
				if(StringUtils.equals(tranCode, "ZH0011")){ //商户流水下载
					mer_code = request.getParameter("mer_code");
					start_date = request.getParameter("start_date");
					end_date = request.getParameter("end_date");
					String backUrl = request.getParameter("backUrl");
					log.info(pingJie.getStringPingJie("mer_code = ",mer_code,",start_date = ",start_date,",end_date = ",end_date, ",backUrl = ", backUrl));
					if(StringUtils.isBlank(mer_code)){
						resMsg = "商户流水下载-参数mer_code不能为空";
					}else if(StringUtils.isBlank(start_date)){
						resMsg = "商户流水下载-参数start_date不能为空";
					}else if(StringUtils.isBlank(end_date)){
						resMsg = "商户流水下载-参数end_date不能为空";
					} else if (StringUtils.isNotBlank(backUrl)) {
						resMsg = "商户流水下载-参数backUrl不能为空";
					}else{
						resCode = "000";
						resMsg = "";
					}
				}
			}else{
				resMsg = "version或者tranCode不能为空";
			}
		}catch(Exception e){
			log.error(e);
		}finally{
			if(out != null){
				// 实时返回消息
				out.print(returnResultJson(tranCode,resCode,resMsg,merPriv));
				out.close();
			}
			
			// 开始下载
			if(resCode.equals("000")){
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMddHHmmss");
				String date=sdformat.format(calendar.getTime());
				
				boolean file_flag = CreateMerFundStanceFileService.createFileAndUpload(mer_code, start_date, end_date, date.substring(0, 8),date);
				if(file_flag){
					final String fileSavePath_local = PropertiesUtils.readProperties("shls_fileSavePath_local");
					final String ftp_ip = PropertiesUtils.readProperties("FtpHost");
					final int ftp_port = Integer.valueOf(PropertiesUtils.readProperties("FtpPort"));
					final String ftp_username = PropertiesUtils.readProperties("FtpUser");
					final String ftp_password = PropertiesUtils.readProperties("FtpPassword");
					final String fileSavePath_ftp = PropertiesUtils.readProperties("shls_fileSavePath_ftp");
					String filePath = fileSavePath_local + "/" + mer_code  + "/" + date.substring(0, 8) + "/" + date +"_shls.xls";//文件路径组成    基础路径+商户号+日期
					log.info(pingJie.getStringPingJie("执行上传文件至FTP操作;   ftp配置信息---ip--",ftp_ip,"--port--",ftp_port,"---路径---",fileSavePath_ftp,"/",mer_code,"/",date.substring(0, 8)));
					
					try{
						File file = new File(filePath);
						FileInputStream is = new FileInputStream(file);
						//上传
						boolean dz_upload = FtpUtil.upLoadFileFtp(ftp_ip,ftp_port,ftp_username,ftp_password,fileSavePath_ftp+"/"+mer_code, is, file.getName(),date.substring(0, 8));
						if(dz_upload){
							log.info("上传到FTP成功");
							//TODO 发送接口，通知商户后台文件已生成
						}else{
							log.info("上传到FTP失败");
						}
					}catch(Exception e){
						log.error(e);
					}
				}
			}
		}
	}
}
