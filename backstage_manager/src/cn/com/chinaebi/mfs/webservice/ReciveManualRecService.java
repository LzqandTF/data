package cn.com.chinaebi.mfs.webservice;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.service.ManualRecService;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;

/**
 * 手工调账查询接口
 * @author wufei
 *
 */
public class ReciveManualRecService extends HttpServlet {

	private static final long serialVersionUID = -6405799827308403643L;
	
	private static Log log = LogFactory.getLog(ReciveMerStanceService.class);
	private StringPingJie pingJie = StringPingJie.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	private String returnResultJson(String tranCode, String resCode, String resMsg, String merPriv, int data_count, String amt_count, String items) {
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
			//---------resMsg(订单号)
			buffer.append("\"resMsg\":");
			buffer.append("\"");
			buffer.append(resMsg);
			buffer.append("\",");
			//---------merPriv(私有域)
			buffer.append("\"merPriv\":");
			buffer.append("\"");
			buffer.append(merPriv);
			buffer.append("\",");
			//---------data_count(总条数)
			buffer.append("\"data_count\":");
			buffer.append("\"");
			buffer.append(data_count);
			buffer.append("\"");
			//---------amt_count(总金额)
			if(StringUtils.isNotBlank(amt_count)){
				buffer.append(",\"amt_count\":");
				buffer.append("\"");
				buffer.append(amt_count);
				buffer.append("\",");
			}
			//---------调账明细(items)
			buffer.append("\"items\":");
			buffer.append("\"");
			buffer.append(items);
			buffer.append("\"");
		buffer.append("}");
		log.info(pingJie.getStringPingJie("返回结果:",  buffer.toString()));
		return  buffer.toString();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response){
		log.info("开始调用手工调账接口...");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String version = request.getParameter("version");  //版本号
		String tranCode = request.getParameter("tranCode");//交易码
		String merPriv = request.getParameter("merPriv");//商户私有域
		merPriv = merPriv==null?"":merPriv;
		log.info(pingJie.getStringPingJie("version = ",version,",tranCode = ",tranCode,",merPriv = ",merPriv));
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String resCode = "001";
			String resMsg = "参数传输错误,tranCode数据值错误";
			//开始处理(手工调账查询)
			if(StringUtils.isNotBlank(version) && StringUtils.isNotBlank(tranCode)){
				ManualRecService manualRecService = ManualRecService.getInstance();
				if(StringUtils.equals(tranCode, "ZH0006")){ //手工调账查询
					String mer_code = request.getParameter("mer_code");
					String start_date = request.getParameter("start_date");
					String end_date = request.getParameter("end_date");
					String pageNo = request.getParameter("pageNo");
					String pageNum = request.getParameter("pageNum");
					log.info(pingJie.getStringPingJie("mer_code = ",mer_code,",start_date = ",start_date,",end_date = ",end_date,",pageNo = ",pageNo,",pageNum = ",pageNum));
					int data_count = 0;
					double amt_count = 0d;
					String itmes = "";
					if(StringUtils.isBlank(mer_code)){
						resMsg = "手工调账查询-参数mer_code不能为空";
					}else if(StringUtils.isBlank(start_date)){
						resMsg = "手工调账查询-参数start_date不能为空";
					}else if(StringUtils.isBlank(end_date)){
						resMsg = "手工调账查询-参数end_date不能为空";
					}else if(StringUtils.isBlank(pageNo)){
						resMsg = "手工调账查询-参数pageNo不能为空";
					}else if(StringUtils.isBlank(pageNum)) {
						resMsg = "手工调账查询-参数pageNum不能为空";
					}else{
						Object object = manualRecService.queryCountAndMoney(mer_code, start_date, end_date);
						if (object != null) {
							Object[] obj = (Object[])object;
							data_count = Integer.valueOf(obj[0].toString()); // 总笔数
							amt_count = obj[1] == null ? 0d : Double.valueOf(obj[1].toString()); // 总金额
						}
						// 调账明细
						itmes = manualRecService.queryManualRecLst(mer_code, start_date, end_date, (Integer.valueOf(pageNo) - 1) * Integer.valueOf(pageNum), Integer.valueOf(pageNum));
						resCode = "000";
						resMsg = "";
					}
					out.print(returnResultJson(tranCode, resCode, resMsg, merPriv, data_count, String.format("%.2f", amt_count), itmes));
				}
			}else{
				resMsg = "version或者tranCode不能为空";
				out.print(returnResultJson(tranCode, resCode, resMsg, merPriv, 0 , "0.00", ""));
			}
		} catch (Exception e) {
			log.error(e);
			out.print(returnResultJson(tranCode, "001", "接口处理异常", merPriv, 0, "0.00", ""));
		}
	}
}
