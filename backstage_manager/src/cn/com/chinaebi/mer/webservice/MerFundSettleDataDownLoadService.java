package cn.com.chinaebi.mer.webservice;

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
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;
import cn.com.chinaebi.trade.webservice.TradeDataQueryService;

/**
 * 商户结算单下载接口
 * @author Admin
 *
 */
public class MerFundSettleDataDownLoadService extends HttpServlet {
	private static final long serialVersionUID = -4268183692303993203L;
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
		
		PrintWriter out = null;
		String resCode = "";//返回码
		String resMsg = "";//返回信息
		String merPriv = "";//私有域
		String mer_code = "";//平台商户号
		
		try{
			out = response.getWriter();
			if(StringUtils.isNotBlank(version)){
				log.info(pingJie.getStringPingJie("请求参数 ------消息版本号:	",version));
				if("10".equals(version)){
					if(StringUtils.isNotBlank(tranCode)){
						log.info(pingJie.getStringPingJie("请求参数 ------交易码:	",tranCode));
						if("DZ0010".equals(tranCode)){
							mer_code = request.getParameter("mer_code");//平台商户号
							String start_date= request.getParameter("start_date");//查询起始日期
							String end_date= request.getParameter("end_date");//查询截止日期
							if(StringUtils.isNotBlank(mer_code)){
								log.info(pingJie.getStringPingJie("请求参数 ------商户号:	",mer_code));
								if(StringUtils.isNotBlank(start_date)){
									log.info(pingJie.getStringPingJie("请求参数 ------起始日期:	",start_date));
									if(StringUtils.isNotBlank(end_date)){
										log.info(pingJie.getStringPingJie("请求参数 ------截止日期:	",end_date));
										resCode="000";//标记成功
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
								log.debug(pingJie.getStringPingJie("平台商户号---",mer_code,";请求参数平台商户号不能为空"));
							}
						}else{
							resCode = "002";
							resMsg = "请求参数交易码不正确";
							log.debug(pingJie.getStringPingJie("交易码---",tranCode,";请求参数交易码不正确,请核实"));
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
				out.print(returnResultJson(tranCode,resCode,resMsg,merPriv));//同步回执接口
				out.close();
			}		
			if(resCode.equals("000")){//返回码为全0时，进行创建文件并上传操作
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMddHHmmss");
				String date=sdformat.format(calendar.getTime());//获得yyyyMMDdd格式的当前系统日期
				boolean file_flag = CreateMerchantFundSettleFileService.createMerFundSettleFile(request.getParameterMap(),date.substring(0, 8),date,mer_code);//创建商户结算单文件
				if(file_flag){
					final String fileSavePath_local = PropertiesUtils.readProperties("fileSavePath_local");//本地存放文件路径
					final String ftp_ip = PropertiesUtils.readProperties("FtpHost");//FTP IP地址
					final int ftp_port = Integer.valueOf(PropertiesUtils.readProperties("FtpPort"));//FTP 端口
					final String ftp_username = PropertiesUtils.readProperties("FtpUser");//FTP 用户名
					final String ftp_password = PropertiesUtils.readProperties("FtpPassword");//FTP 密码
					final String fileSavePath_ftp = PropertiesUtils.readProperties("fileSavePath_ftp");//FTP 存放文件基础路径
					String filePath = fileSavePath_local + "/" + mer_code  + "/" + date.substring(0, 8) + "/" + date +"_MerFundSettleData.xls";//文件路径组成    基础路径+商户号+日期
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
	public static void main(String[] args) {
		int len = 10;
		long start = System.currentTimeMillis();
		System.out.println("start : "+start);
		//http://192.168.20.138:8014/backstagemamage/tradeDataDownLoadService?tranCode=ZH0002&mid=295&merPriv=1234&query_type=1&start_date=20150301&end_date=20150401&pageNo=1&pageNum=50
		for(int i=0;i<len;i++){
			Map<String, String> params = new HashMap<String, String>();
			params.put("version", "10");
			params.put("tranCode", "DZ0010");
			params.put("merPriv", "1234");
			params.put("mer_code", (295+i)+"");
			params.put("start_date", "2015"+(i>8?"":"0")+(1+i)+"01");
			params.put("end_date", "2015"+(i>7?"":"0")+(2+i)+"01");
			try {
				String jsonStr = HttpUtil.sendPostRequest("http://192.168.20.138:8014/backstagemamage/merFundSettleDataDownLoadService", 
						params, "UTF-8");
				System.out.println("收到返回数据 : "+jsonStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("end : "+end);
		System.out.println("花费时间 : "+(end-start));
	}
}
