package cn.com.chinaebi.trade.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.util.FtpUtil;
import cn.com.chinaebi.dz.object.util.HttpUtil;
import cn.com.chinaebi.dz.object.util.PropertiesUtils;
import cn.com.chinaebi.trade.webservice.CreateMerchantTradeFileService;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;

/**
 * 交易数据下载接口
 * @author sp
 *
 */
public class TradeDataDownLoadService extends HttpServlet{
	
	private static final long serialVersionUID = 2364790329614628695L;
	private static Log log = LogFactory.getLog(TradeDataQueryService.class);
	private StringPingJie pingJie = StringPingJie.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	/**
	 * 返回json格式脚本
	 * @param tranCode	交易码
	 * @param resCode	返回码
	 * @param resMsg	返回信息
	 * @param merPriv	私有域
	 * @return
	 */
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
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String version = request.getParameter("version");  //版本号
		String tranCode = request.getParameter("tranCode");//交易码
		String query_type = request.getParameter("query_type");//查询类型 0:线下;1:线上
		
		PrintWriter out = null;
		String resCode = "";//返回码
		String resMsg = "";//返回信息
		String merPriv = "";//私有域
		String mid = "";//平台商户号
		int today_or_history = 0;//1-当天交易查询；2-历史交易查询
		
		try{
			out = response.getWriter();
			if(StringUtils.isNotBlank(version)){
				log.info(pingJie.getStringPingJie("请求参数 ------消息版本号:	",version));
				if("10".equals(version)){
					if(StringUtils.isNotBlank(tranCode)){
						log.info(pingJie.getStringPingJie("请求参数 ------交易码:	",tranCode));
						if(StringUtils.isNotBlank(query_type)){
							log.info(pingJie.getStringPingJie("请求参数 ------渠道类型:	",query_type));
							
							mid = request.getParameter("mid");//平台商户号
							String start_date= request.getParameter("start_date");//查询起始日期
							String end_date= request.getParameter("end_date");//查询截止日期
							
							if("0".equals(query_type) || "1".equals(query_type)){
								if(StringUtils.isNotBlank(mid)){
									log.info(pingJie.getStringPingJie("请求参数 ------商户号:	",mid));
									if(StringUtils.isNotBlank(start_date)){
										log.info(pingJie.getStringPingJie("请求参数 ------起始日期:	",start_date));
										if(StringUtils.isNotBlank(end_date)){
											log.info(pingJie.getStringPingJie("请求参数 ------截止日期:	",end_date));
											if("DZ0001".equals(tranCode)){
												today_or_history = 1;
												resCode="000";//标记成功
											}else if("DZ0009".equals(tranCode)){
												today_or_history = 2;
												resCode="000";//标记成功
											}else{
												resCode = "002";
												resMsg = "请求参数交易码不正确";
												log.debug(pingJie.getStringPingJie("交易码---",tranCode,";请求参数交易码不正确,请核实"));
											}
										}else{
											resCode = "002";
											resMsg = "请求参数查询结束交易日期不能为空";
											log.debug(pingJie.getStringPingJie("查询结束交易日期---",end_date,";请求参数查询结束交易日期不能为空"));
										}
									}else{
										resCode = "002";
										resMsg = "请求参数查询起始交易日期不能为空";
										log.debug(pingJie.getStringPingJie("查询起始交易日期---",start_date,";请求参数查询起始交易日期不能为空"));
									}
								}else{
									resCode = "002";
									resMsg = "请求参数平台商户号不能为空";
									log.debug(pingJie.getStringPingJie("平台商户号---",mid,";请求参数平台商户号不能为空"));
								}
							}else{
								resCode = "002";
								resMsg = "请求参数查询类型不正确";
								log.debug(pingJie.getStringPingJie("查询类型---",query_type,";请求参数查询类型不正确,请核实"));
							}
						}else{
							resCode = "002";
							resMsg = "请求参数查询类型不能为空";
							log.debug(pingJie.getStringPingJie("查询类型---",query_type,";请求参数查询类型不能为空"));
						}
						
					}else{
						resCode = "002";
						resMsg = "请求参数交易码不能为空";
						log.debug(pingJie.getStringPingJie("交易码---",tranCode,";请求参数交易码不能为空"));
					}
				}else{
					resCode = "002";
					resMsg = "请求参数版本号不正确";
					log.debug(pingJie.getStringPingJie("版本号---",version,";请求参数版本号不正确,请核实"));
				}
			}else{
				resCode = "002";
				resMsg = "请求参数版本号不能为空";
				log.debug(pingJie.getStringPingJie("版本号---",version,";请求参数版本号不能为空"));
			}
		}catch(Exception e){
			resCode = "001";
			resMsg = "系统异常";
			log.error(e);
		}finally{
			if(out != null){
				out.print(returnResultJson(tranCode,resCode,resMsg,merPriv));
				out.close();
			}
			
			if(resCode.equals("000") && (today_or_history == 1 || today_or_history == 2)){
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMddHHmmss");
				String date=sdformat.format(calendar.getTime());
				boolean file_flag = CreateMerchantTradeFileService.createMerchantTradeFile(request.getParameterMap(),date.substring(0, 8),date,mid,today_or_history);//创建文件
				if(file_flag){
					final String fileSavePath_local = PropertiesUtils.readProperties("fileSavePath_local");//本地存放文件路径
					final String ftp_ip = PropertiesUtils.readProperties("FtpHost");//FTP IP地址
					final int ftp_port = Integer.valueOf(PropertiesUtils.readProperties("FtpPort"));//FTP 端口
					final String ftp_username = PropertiesUtils.readProperties("FtpUser");//FTP 用户名
					final String ftp_password = PropertiesUtils.readProperties("FtpPassword");//FTP 密码
					final String fileSavePath_ftp = PropertiesUtils.readProperties("fileSavePath_ftp");//FTP 存放文件基础路径
					String filePath = fileSavePath_local + "/" + mid  + "/" + date.substring(0, 8) + "/" + date +"_TradeData.xls";//文件路径组成    基础路径+商户号+日期
					log.info(pingJie.getStringPingJie("执行上传文件至FTP操作;   ftp配置信息---ip--",ftp_ip,"--port--",ftp_port,"---路径---",fileSavePath_ftp,"/",mid,"/",date.substring(0, 8)));
					
					try{
						File file = new File(filePath);
						FileInputStream is = new FileInputStream(file);
						//上传
						boolean dz_upload = FtpUtil.upLoadFileFtp(ftp_ip,ftp_port,ftp_username,ftp_password,fileSavePath_ftp+"/"+mid, is, file.getName(),date.substring(0, 8));
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
	
	
	public static void main(String[] args) {
//		int len = 10;
		long start = System.currentTimeMillis();
		System.out.println("start : "+start);
		//http://192.168.20.138:8014/backstagemamage/tradeDataDownLoadService?tranCode=ZH0002&mid=295&merPriv=1234&query_type=1&start_date=20150301&end_date=20150401&pageNo=1&pageNum=50
//		for(int i=0;i<len;i++){
			Map<String, String> params = new HashMap<String, String>();
			params.put("version", "10");
			params.put("tranCode", "DZ0009");
			params.put("merPriv", "1234");
			params.put("mid", "295");
			params.put("query_type", "1");
			params.put("start_date", "20150301");
			params.put("end_date", "20150401");
			try {
				String jsonStr = HttpUtil.sendPostRequest("http://192.168.20.138:8014/backstagemamage/tradeDataDownLoadService", 
						params, "UTF-8");
				System.out.println("收到返回数据 : "+jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		long end = System.currentTimeMillis();
		System.out.println("end : "+end);
		System.out.println("花费时间 : "+(end-start));
	}
}
