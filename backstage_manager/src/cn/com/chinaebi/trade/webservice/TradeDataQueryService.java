package cn.com.chinaebi.trade.webservice;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.ChannelTradeCollect;
import cn.com.chinaebi.dz.object.dao.ChannelTradeCollectDAO;
import cn.com.chinaebi.dz.object.util.HttpUtil;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;

/**
 * 交易数据查询接口
 * @author sp
 *
 */
public class TradeDataQueryService extends HttpServlet {

	private static final long serialVersionUID = -2635543483007944359L;
	private static Log log = LogFactory.getLog(TradeDataQueryService.class);
	private StringPingJie pingJie = StringPingJie.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.ChannelTradeCollectDAO channelTradeCollectDao = ChannelTradeCollectDAO.getInstance();
	
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
	 * @param data_count	总条数
	 * @param amt_count		总金额
	 * @param items		交易明细
	 * @return
	 */
	private String returnResultJson(String tranCode,String resCode,String resMsg,String merPriv,long data_count,double amt_count,String items){
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
			//---------data_count(交易笔数)
			buffer.append("\"data_count\":");
			buffer.append("\"");
			buffer.append(data_count);
			buffer.append("\",");
			//---------amt_count(交易金额)
			buffer.append("\"amt_count\":");
			buffer.append("\"");
			buffer.append(amt_count);
			buffer.append("\",");
			//---------mer_fee(退回商户手续费-按分计算)
			buffer.append("\"items\":");
			buffer.append("\"");
			buffer.append(items);
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
		long data_count = 0l;//交易总数
		double amt_count = 0.00d;//总金额
		StringBuffer dataBuffer = new StringBuffer("");//交易明细
		
		try{
			out = response.getWriter();
			if(StringUtils.isNotBlank(version)){
				log.info(pingJie.getStringPingJie("请求参数 ------消息版本号:	",version));
				if("10".equals(version)){
					if(StringUtils.isNotBlank(tranCode)){
						log.info(pingJie.getStringPingJie("请求参数 ------交易码:	",tranCode));
						if(StringUtils.isNotBlank(query_type)){
							
							log.info(pingJie.getStringPingJie("请求参数 ------渠道类型:	",query_type));
							
							String mid = request.getParameter("mid");//平台商户号
							String start_date= request.getParameter("start_date");//查询起始日期
							String end_date= request.getParameter("end_date");//查询截止日期
							String pageNo= request.getParameter("pageNo");//页码
							String pageNum= request.getParameter("pageNum");//每页查询条数
							
							if("0".equals(query_type) || "1".equals(query_type)){
								if(StringUtils.isNotBlank(mid)){
									log.info(pingJie.getStringPingJie("请求参数 ------商户号:	",mid));
									if(StringUtils.isNotBlank(start_date)){
										log.info(pingJie.getStringPingJie("请求参数 ------起始日期:	",start_date));
										if(StringUtils.isNotBlank(end_date)){
											log.info(pingJie.getStringPingJie("请求参数 ------截止日期:	",end_date));
											if(StringUtils.isNotBlank(pageNo)){
												log.info(pingJie.getStringPingJie("请求参数 ------页码:	",pageNo));
												if(StringUtils.isNotBlank(pageNum)){
													log.info(pingJie.getStringPingJie("请求参数 ------每页显示条数:	",pageNum));
													
													List<ChannelTradeCollect> dataList = null;
													
													if("DZ0001".equals(tranCode)){//当天交易查询接口
														Map<String,String[]> map = request.getParameterMap();
														dataList = channelTradeCollectDao.getTodayChannelTradeCollectPageData(map);
														resCode="000";//标记成功
													}else if("DZ0002".equals(tranCode)){//历史交易查询接口
														Map<String,String[]> map = request.getParameterMap();
														dataList = channelTradeCollectDao.getHistoryChannelTradeCollectPageData(map);
														resCode="000";//标记成功
													}else{
														resCode = "002";
														resMsg = "请求参数交易码不正确";
														log.debug(pingJie.getStringPingJie("交易码---",tranCode,";请求参数交易码不正确,请核实"));
													}
													
													if("000".equals(resCode) && dataList != null && dataList.size() > 0){
														data_count = dataList.size();
														int dataCount = dataList.size();
														
														for (ChannelTradeCollect data : dataList) {
															dataCount --;
															amt_count += data.getTradeAmount()/100;
															
															dataBuffer.append(data.getDeductSysStance());//交易流水号
															dataBuffer.append(",");
															dataBuffer.append(data.getOid());//订单号
															dataBuffer.append(",");
															dataBuffer.append(data.getTradeAmount()/100);//交易金额
															dataBuffer.append(",");
															dataBuffer.append(data.getOutAccount());//卡号
															dataBuffer.append(",");
															dataBuffer.append(data.getTradeTime());//交易日期
															dataBuffer.append(",");
															dataBuffer.append(data.getMerFee());//手续费
															dataBuffer.append(",");
															dataBuffer.append(data.getTradeResult());//交易结果
															if(dataCount != 0){
																dataBuffer.append("|");
															}
														}
													}else{
														log.debug(pingJie.getStringPingJie("根据请求参数查询数据为空"));
													}
												}else{
													resCode = "002";
													resMsg = "请求参数每页显示条数不能为空";
													log.debug(pingJie.getStringPingJie("每页显示条数---",pageNum,";请求参数每页显示条数不能为空"));
												}
											}else{
												resCode = "002";
												resMsg = "请求参数页码不能为空";
												log.debug(pingJie.getStringPingJie("页码---",pageNo,";请求参数页码不能为空"));
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
				out.print(returnResultJson(tranCode,resCode,resMsg,merPriv,data_count,amt_count,dataBuffer.toString()));
				out.close();
			}
		}
	}
	
	public static void main(String[] args) {
		int len = 100;
		long start = System.currentTimeMillis();
		System.out.println("start : "+start);
		//http://192.168.20.138:8014/backstagemamage/tradeDataQueryService?tranCode=ZH0002&mid=295&merPriv=1234&query_type=1&start_date=20150301&end_date=20150401&pageNo=1&pageNum=50
		for(int i=1;i<len;i++){
			Map<String, String> params = new HashMap<String, String>();
			params.put("version", "10");
			params.put("tranCode", "DZ0002");
			params.put("merPriv", "1234");
			params.put("mid", "295");
			params.put("query_type", "1");
			params.put("start_date", "20150301");
			params.put("end_date", "20150401");
			params.put("pageNo", i+"");
			params.put("pageNum", "100");
			try {
				String jsonStr = HttpUtil.sendPostRequest("http://192.168.20.138:8014/backstagemamage/tradeDataQueryService", 
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
