package cn.com.chinaebi.dz.tk.webservice;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.util.HttpUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.service.RytMerSysTkHandleService;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;

/**
 * 处理商户后台退款接口
 * 申请退款、申请撤销退款、确认退款
 * 
 * 申请退款:http://192.168.20.138:8014/backstagemamage/reciveTradeDataTkService?version=10&tranCode=ZH0014&merPriv=1234&tseq=6108657&mid=327&org_oid=FT150301000337435979&ref_amt=1&mdate=20150301&refund_reason=测试
 * 申请撤销退款:http://192.168.20.138:8014/backstagemamage/reciveTradeDataTkService?version=10&tranCode=ZH0015&id=1977&etro_reason=测试撤销&merPriv=12345
 * 确认退款:http://192.168.20.138:8014/backstagemamage/reciveTradeDataTkService?version=10&tranCode=ZH0016&id=1978&merPriv=123456
 * @author zhu.hongliang
 */
public class ReciveTradeDataTkService extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1927816656948491341L;
	private static Log log = LogFactory.getLog(ReciveTradeDataTkService.class);
	private StringPingJie pingJie = StringPingJie.getInstance();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	/**
	 * 返回json格式字符串
	 * @param tranCode : 交易码
	 * @param id ：退款主键id
	 * @param resCode ：返回码
	 * @param resMsg ：返回信息
	 * @param merPriv ：商户私有域
	 * @return String
	 */
	private String returnResultJson(String tranCode,String id,String resCode,String resMsg,String merPriv,String mer_fee){
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		    //---------tranCode(交易码)
			buffer.append("\"tranCode\":");
			buffer.append("\"");
			buffer.append(tranCode);
			buffer.append("\",");
			//---------id(主键ID)
			buffer.append("\"id\":");
			buffer.append("\"");
			buffer.append(id);
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
			//---------mer_fee(退回商户手续费-按分计算)
			if(StringUtils.isNotBlank(mer_fee)){
				buffer.append(",\"mer_fee\":");
				buffer.append("\"");
				buffer.append(mer_fee);
				buffer.append("\"");
			}
		buffer.append("}");
		log.info(pingJie.getStringPingJie("返回结果:",buffer.toString()));
		return buffer.toString();
	}
	
	
	/**
	 * 返回退款查询json格式字符串
	 * @param tranCode : 交易码
	 * @param merPriv ：商户私有域
	 * @param mer_abbreviation ：商户简称
	 * @param resCode ：响应码
	 * @param currency ：000：成功、001：未查询到原笔、其他均为失败
	 * @param mid ：商户号
	 * @param tseq ：电银流水号
	 * @param oid ：订单号
	 * @param mdate ：商户日期
	 * @param amount ：交易金额
	 * @param mer_fee ：商户手续费
	 * @param total_tk_amount ：总申请退款金额(分)
	 * @param allow_tk_amount ：可退款金额(分)
	 * @return String
	 */
	private String returnTkSelectJson(String tranCode,String merPriv,
			Object mer_abbreviation,Object resCode,String resMsg,Object currency,Object mid,Object tseq,
			Object oid,Object mdate,Object amount,Object mer_fee,Object total_tk_amount,Object allow_tk_amount){
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		    //---------tranCode(交易码)
			buffer.append("\"tranCode\":");
			buffer.append("\"");
			buffer.append(tranCode);
			buffer.append("\",");
			//---------merPriv(私有域)
			buffer.append("\"merPriv\":");
			buffer.append("\"");
			buffer.append(merPriv);
			buffer.append("\",");
			//---------mer_abbreviation(商户简称)
			buffer.append("\"mer_abbreviation\":");
			buffer.append("\"");
			buffer.append(mer_abbreviation);
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
			//---------currency(币种)
			buffer.append("\"currency\":");
			buffer.append("\"");
			buffer.append(currency);
			buffer.append("\",");
			//---------currency(商户号)
			buffer.append("\"mid\":");
			buffer.append("\"");
			buffer.append(mid);
			buffer.append("\",");
			//---------tseq(电银流水号)
			buffer.append("\"tseq\":");
			buffer.append("\"");
			buffer.append(tseq);
			buffer.append("\",");
			//---------oid(订单号)
			buffer.append("\"oid\":");
			buffer.append("\"");
			buffer.append(oid);
			buffer.append("\",");
			//---------mdate(商户日期)
			buffer.append("\"mdate\":");
			buffer.append("\"");
			buffer.append(mdate);
			buffer.append("\",");
			//---------amount(交易金额)
			buffer.append("\"amount\":");
			buffer.append("\"");
			buffer.append(amount);
			buffer.append("\",");
			//---------mer_fee(系统手续费)
			buffer.append("\"mer_fee\":");
			buffer.append("\"");
			buffer.append(mer_fee);
			buffer.append("\",");
			//---------total_tk_amount(总申请退款金额(分))
			buffer.append("\"total_tk_amount\":");
			buffer.append("\"");
			buffer.append(total_tk_amount);
			buffer.append("\",");
			//---------allow_tk_amount(可退款金额(分))
			buffer.append("\"allow_tk_amount\":");
			buffer.append("\"");
			buffer.append(allow_tk_amount);
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
		String merPriv = request.getParameter("merPriv");//商户私有域
		merPriv = merPriv==null?"":merPriv;
		log.info(pingJie.getStringPingJie("version = ",version,",tranCode = ",tranCode,",merPriv = ",merPriv));
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String resCode = "001";
			String resMsg = "参数传输错误,tranCode数据值错误";
			//开始处理(5  商户申请退款、1  商户确认退款、7  商户撤销退款)
			if(StringUtils.isNotBlank(version) && StringUtils.isNotBlank(tranCode)){
				RytMerSysTkHandleService rytMerSysTkHandleService = RytMerSysTkHandleService.getInstance();
				if(StringUtils.equals(tranCode, "ZH0014")){ //申请退款
					String tseq = request.getParameter("tseq");
					String mid = request.getParameter("mid");
					String org_oid = request.getParameter("org_oid");
					String ref_amt = request.getParameter("ref_amt").trim();
					String mdate = request.getParameter("mdate");
					String refund_reason = new String(request.getParameter("refund_reason").getBytes("ISO-8859-1"), "utf-8");
					log.info(pingJie.getStringPingJie("tseq = ",tseq,",mid = ",mid,",org_oid = ",org_oid,",ref_amt = ",ref_amt,",mdate = ",mdate,",refund_reason = ",refund_reason));
					String id = "";
					String mer_fee = "0";
					if(StringUtils.isBlank(tseq)){
						resMsg = "申请退款-参数tseq不能为空";
					}else if(StringUtils.isBlank(ref_amt) || Double.valueOf(ref_amt) == 0d){
						resMsg = "申请退款-参数ref_amt不能为空或者不能为0";
					}else if(StringUtils.isBlank(mid)){
						resMsg = "申请退款-参数mid不能为空";
					}else if(StringUtils.isBlank(mdate)){
						resMsg = "申请退款-参数mdate不能为空";
					}else{
						String[] result = rytMerSysTkHandleService.merReqIfaceHanle(tseq, mid, org_oid, ref_amt, mdate, refund_reason);
						resCode = result[0];
						resMsg = result[1];
						id = result[2];
						mer_fee = result[3];
					}
					out.print(returnResultJson(tranCode, id, resCode, resMsg, merPriv,mer_fee));
				}else if(StringUtils.equals(tranCode, "ZH0015")){ //申请撤销
					String id = request.getParameter("id");
					String etro_reason = new String(request.getParameter("etro_reason").getBytes("ISO-8859-1"), "utf-8");
					log.info(pingJie.getStringPingJie("id = ",id,",etro_reason = ",etro_reason));
					if(StringUtils.isBlank(id)){
						resMsg = "申请撤销-参数id不能为空";
					}else{
						Integer stat = rytMerSysTkHandleService.queryDataById(id);
						if(stat != null && stat != 0){
							switch(stat){
								case 5:
									boolean cx_flag = rytMerSysTkHandleService.merTkUpdateIfaceHandle(id, "stat",7,"etro_reason",etro_reason);
									if(cx_flag){
										resCode = "000";
										resMsg = "申请撤销-处理成功";
									}else{
										resMsg = "申请撤销-系统异常";
									}
									break;
								case 7:
									resMsg = pingJie.getStringPingJie("申请撤销-不能重复撤销");
									break;
								default:
									resMsg = pingJie.getStringPingJie("申请撤销-确认状态数据不能被撤销");
									break;
							}
						}else{
							resMsg = pingJie.getStringPingJie("申请撤销 ,id=",id,",无此退款交易数据");
						}
					}
					out.print(returnResultJson(tranCode, id, resCode, resMsg, merPriv,null));
				}else if(StringUtils.equals(tranCode, "ZH0016")){ //确认退款
					String id = request.getParameter("id");
					log.info(pingJie.getStringPingJie("id = ",id));
					if(StringUtils.isBlank(id)){
						resMsg = "确认退款-参数id不能为空";
					}else{
						Integer stat = rytMerSysTkHandleService.queryDataById(id);
						if(stat != null && stat != 0){
							switch(stat){
								case 5:
									String req_date = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_3).format(new Date());//确认日期
									boolean cx_flag = rytMerSysTkHandleService.merTkUpdateIfaceHandle(id, "stat",1,"req_date",Integer.valueOf(req_date));
									if(cx_flag){
										resCode = "000";
										resMsg = "确认退款-处理成功";
									}else{
										resMsg = "确认退款-系统异常";
									}
									break;
								case 1:
									resMsg = pingJie.getStringPingJie("确认退款-不能重复确认");
									break;
								default:
									resMsg = pingJie.getStringPingJie("确认退款-撤销状态数据不能被确认");
									break;
							}
						}else{
							resMsg = pingJie.getStringPingJie("确认退款 ,id=",id,",无此退款交易数据");
						}
					}
					out.print(returnResultJson(tranCode, id, resCode, resMsg, merPriv,null));
				}else if(StringUtils.equals(tranCode, "ZH0020")){//退款查询
					String select_type = request.getParameter("select_type");
					String mdate = request.getParameter("mdate");
					String oid = request.getParameter("oid");
					String tseq = request.getParameter("tseq");
					Object[] obj = null;
					if(StringUtils.isNotBlank(select_type)){
						int type = Integer.valueOf(select_type);
						if(type == 0 && StringUtils.isNotBlank(tseq) && StringUtils.isNotBlank(mdate)){//按流水
							obj = rytMerSysTkHandleService.tkQuery(type, Long.valueOf(mdate), oid, Long.valueOf(tseq));
						}else if(type == 1 && StringUtils.isNotBlank(oid) && StringUtils.isNotBlank(mdate)){//按订单
							obj = rytMerSysTkHandleService.tkQuery(type, Long.valueOf(mdate), oid, Long.valueOf(tseq));
						}else{
							resMsg = pingJie.getStringPingJie("退款查询请求参数错误");
						}
					}else{
						resMsg = pingJie.getStringPingJie("退款查询 select_type 不能为空");
					}
					if(obj != null){
						resCode = obj[0].toString();
						if(StringUtils.equals(resCode, "000")){
							resMsg = "查询成功";
//							returnObj[0] = response;
//							returnObj[1] = currency;
//							returnObj[2] = mid;
//							returnObj[3] = ori_tseq;
//							returnObj[4] = oriOid;
//							returnObj[5] = oriMdate;
//							returnObj[6] = amount;
//							returnObj[7] = mer_fee;
//							returnObj[8] = tkAmt;
//							returnObj[9] = refund_amt;
//							MerBasic merBasic = Backstage.getInstance().getMerBasic(mid);
//							returnObj[10] = merBasic == null ? "无" : merBasic.getMerAbbreviation();
							out.print(returnTkSelectJson(tranCode, merPriv, obj[10], resCode,resMsg,
									obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7], obj[8], obj[9]));
						}else{
							resMsg = StringUtils.equals(resCode, "001") ? "没有找到原笔交易" : "其他错误";
							out.print(returnTkSelectJson(tranCode, merPriv, "", resCode,resMsg,"", "", "", "", "", "", "0.00", "0.00", "0.00"));
						}
					}else{
						out.print(returnTkSelectJson(tranCode, merPriv, "", resCode,resMsg,"", "", "", "", "", "", "0.00", "0.00", "0.00"));
					}
				}else{
					out.print(returnResultJson(tranCode, "", resCode, resMsg, merPriv,null));
				}
			}else{
				resMsg = "version或者tranCode不能为空";
				out.print(returnResultJson(tranCode, "", resCode, resMsg, merPriv,null));
			}
		} catch (Exception e) {
			log.error(e);
			out.print(returnResultJson(tranCode, "", "001", "接口处理异常", merPriv,null));
		}
	}
	
	
	public static void main(String[] args) {
		int len = 10;
		long start = System.currentTimeMillis();
		System.out.println("start : "+start);
		//http://192.168.20.138:8014/backstagemamage/reciveTradeDataTkService?tranCode=ZH0014&tseq=6096248&merPriv=1234&refund_reason=%E6%B5%8B%E8%AF%95&org_oid=201502270813147843&mid=300&ref_amt=100&version=10&
		for(int i=0;i<len;i++){
			Map<String, String> params = new HashMap<String, String>();
			params.put("version", "10");
			params.put("tranCode", "ZH0014");
			params.put("merPriv", "1234");
			params.put("tseq", "6096248");
			params.put("mdate", "20151223");
			params.put("mid", "300");
			params.put("org_oid", "201502270813147843");
			params.put("ref_amt", "100");
			params.put("refund_reason", "测试");
			try {
				String jsonStr = HttpUtil.sendPostRequest("http://192.168.20.138:8014/backstagemamage/reciveTradeDataTkService", 
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
