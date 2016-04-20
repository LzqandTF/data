package cn.com.chinaebi.mer.webservice;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.object.dao.iface.MerBalanceDAO;
import cn.com.chinaebi.dz.object.util.HttpUtil;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;

/**
 * 商户余额查询接口
 * @author sp
 *
 */
public class MerchantBalanceQueryService extends HttpServlet {

	private static final long serialVersionUID = -7071599473603692680L;

	private static Log log = LogFactory.getLog(MerFundSettleDataQueryService.class);
	
	private StringPingJie pingJie = StringPingJie.getInstance();
	
	private static MerBalanceDAO merBalanceDAO = cn.com.chinaebi.dz.object.dao.MerBalanceDAO.getInstance();
	
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	/**
	 * 返回json格式数据
	 * @param tranCode	交易码
	 * @param resCode	返回码	
	 * @param resMsg	返回消息
	 * @param merPriv	私有域
	 * @param mer_code	商户号
	 * @param mer_balance	商户余额
	 * @param mer_state	商户状态
	 * @return
	 */
	private String returnResultJson(String tranCode,String resCode,String resMsg,String merPriv,String mer_code,double mer_balance,int mer_state){
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
			buffer.append("\",");
			//---------mer_code(商户号)
			buffer.append("\"mer_code\":");
			buffer.append("\"");
			buffer.append(mer_code);
			buffer.append("\",");
			//---------mer_balance(商户余额)
			buffer.append("\"mer_balance\":");
			buffer.append("\"");
			buffer.append(mer_balance);
			buffer.append("\",");
			//---------mer_state(商户状态)
			buffer.append("\"mer_state\":");
			buffer.append("\"");
			buffer.append(mer_state);
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
		String mer_code = "";//商户号
		
		double balance = 0.00d;//商户余额
		int mer_state = 6;//商户状态
		
		try{
			out = response.getWriter();
			if(StringUtils.isNotBlank(version)){
				log.info(pingJie.getStringPingJie("请求参数 ------消息版本号:	",version));
				if("10".equals(version)){
					if(StringUtils.isNotBlank(tranCode)){
						if("ZH0004".equals(tranCode)){
							log.info(pingJie.getStringPingJie("请求参数 ------交易码:	",tranCode));
							
							mer_code = request.getParameter("mer_code");//平台商户号
							
							if(StringUtils.isNotBlank(mer_code)){
								log.info(pingJie.getStringPingJie("请求参数 ------商户号:	",mer_code));
								
								MerBalance merBalance = merBalanceDAO.findMerBalance(mer_code);
								
								if(merBalance != null){
									balance = StringUtils.isBlank(merBalance.getMerBalance()) ? 0.00d : Double.valueOf(merBalance.getMerBalance());
									mer_state = merBalance.getMerState();
									resCode="000";//标记成功
								}else{
									resCode = "003";//未找到商户余额对象
									log.debug(pingJie.getStringPingJie("根据请求参数查询数据为空"));
								}
							}else{
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
				out.print(returnResultJson(tranCode,resCode,resMsg,merPriv,mer_code,balance,mer_state));
				out.close();
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
			params.put("tranCode", "ZH0004");
			params.put("merPriv", "1234");
			params.put("mer_code", "295");
			try {
				String jsonStr = HttpUtil.sendPostRequest("http://192.168.20.138:8014/backstagemamage/merchantBalanceQueryService", 
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
