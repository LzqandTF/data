package cn.com.chinaebi.syn.webservice;

import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.util.HttpUtil;
import cn.com.chinaebi.dz.service.TradeDzfList;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;


/**
 * 接收待支付请求交易数据
 * http://192.168.20.138:8014/backstagemamage/reciveTradeDataDzfService
 * @author zhu.hongliang
 */
public class ReciveTradeDataDzfService extends HttpServlet{
	
	private static final long serialVersionUID = 3925002846525796324L;
	
	private static Log log = LogFactory.getLog(ReciveTradeDataDzfService.class);
	private TradeDzfList tradeDzfList = TradeDzfList.getInstance();
	private StringPingJie pingJie = StringPingJie.getInstance();

	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	public String returnJsonString(String tranCode,String tseq,String resCode,String resMsg,String merPriv){
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
		log.info(pingJie.getStringPingJie("返回结果 : ",buffer.toString()));
		return buffer.toString();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response){
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String tranCode = request.getParameter("tranCode");
		String sys_type = request.getParameter("sys_type").trim();
		String merPriv = request.getParameter("merPriv");
		String tseq = request.getParameter("tseq");
		log.info(pingJie.getStringPingJie("tranCode = ",tranCode));
		log.info(pingJie.getStringPingJie("sys_type = ",sys_type));
		log.info(pingJie.getStringPingJie("tseq = ",tseq));
		log.info(pingJie.getStringPingJie("merPriv = ",merPriv));
		String resCode = "001";
		String resMsg = "参数错误";
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if(StringUtils.isBlank(tseq)){
				resMsg = "参数tseq不能为空";
			}else if(StringUtils.isBlank(sys_type)){
				resMsg = "参数sys_type不能为空";
			}else if(StringUtils.isBlank(tranCode)){
				resMsg = "参数tranCode值不能为空";
			}else{
				if(StringUtils.equals(tranCode, "ZH0018")){
					if(StringUtils.isNotBlank(sys_type) && StringUtils.equals(sys_type, "1")){ //线上
						String version = request.getParameter("version");
						log.info(pingJie.getStringPingJie("version = ",version));
						String mdate = request.getParameter("mdate");
						log.info(pingJie.getStringPingJie("mdate = ",mdate));
						String mid = request.getParameter("mid");
						log.info(pingJie.getStringPingJie("mid = ",mid));
						String bid = request.getParameter("bid");
						log.info(pingJie.getStringPingJie("bid = ",bid));
						String oid = request.getParameter("oid");
						log.info(pingJie.getStringPingJie("oid = ",oid));
						String amount = request.getParameter("amount");
						log.info(pingJie.getStringPingJie("amount = ",amount));
						String type = request.getParameter("type");
						log.info(pingJie.getStringPingJie("type = ",type));
						String gate = request.getParameter("gate");
						log.info(pingJie.getStringPingJie("gate = ",gate));
						String author_type = request.getParameter("author_type");
						log.info(pingJie.getStringPingJie("author_type = ",author_type));
						String sys_date = request.getParameter("sys_date");
						log.info(pingJie.getStringPingJie("sys_date = ",sys_date));
						String init_sys_date = request.getParameter("init_sys_date");
						log.info(pingJie.getStringPingJie("init_sys_date = ",init_sys_date));
						String sys_time = request.getParameter("sys_time");
						log.info(pingJie.getStringPingJie("sys_time = ",sys_time));
						String fee_amt = request.getParameter("fee_amt");
						log.info(pingJie.getStringPingJie("fee_amt = ",fee_amt));
						String bank_fee = request.getParameter("bank_fee");
						log.info(pingJie.getStringPingJie("bank_fee = ",bank_fee));
						String tstat = request.getParameter("tstat");
						log.info(pingJie.getStringPingJie("tstat = ",tstat));
						String mobile_no = request.getParameter("mobile_no");
						log.info(pingJie.getStringPingJie("mobile_no = ",mobile_no));
						String phone_no = request.getParameter("phone_no");
						log.info(pingJie.getStringPingJie("phone_no = ",phone_no));
						String card_no = request.getParameter("card_no");
						log.info(pingJie.getStringPingJie("card_no = ",card_no));
						String gid = request.getParameter("gid");
						log.info(pingJie.getStringPingJie("gid = ",gid));
						String pre_amt = request.getParameter("pre_amt");
						log.info(pingJie.getStringPingJie("pre_amt = ",pre_amt));
						String pre_amt1 = request.getParameter("pre_amt1");
						log.info(pingJie.getStringPingJie("pre_amt1 = ",pre_amt1));
						String bk_fee_model = request.getParameter("bk_fee_model");
						log.info(pingJie.getStringPingJie("bk_fee_model = ",bk_fee_model));
						String pay_amt = request.getParameter("pay_amt");
						log.info(pingJie.getStringPingJie("pay_amt = ",pay_amt));
						String currency = request.getParameter("currency");
						log.info(pingJie.getStringPingJie("currency = ",currency));
						String exchange_rate = request.getParameter("exchange_rate");
						log.info(pingJie.getStringPingJie("exchange_rate = ",exchange_rate));
						String out_user_id = request.getParameter("out_user_id");
						log.info(pingJie.getStringPingJie("out_user_id = ",out_user_id));
						String in_user_id = request.getParameter("in_user_id");
						log.info(pingJie.getStringPingJie("in_user_id = ",in_user_id));
						String bind_mid = request.getParameter("bind_mid");
						log.info(pingJie.getStringPingJie("bind_mid = ",bind_mid));
						String[] obj = {sys_type,tseq,version,mdate,mid,bid,oid,amount,type,gate,author_type,sys_date,init_sys_date,sys_time,fee_amt,
								bank_fee,tstat,mobile_no,phone_no,card_no,gid,pre_amt,pre_amt1,bk_fee_model,pay_amt,currency,exchange_rate,
								out_user_id,in_user_id,bind_mid};
						tradeDzfList.addList(obj);
						resCode = "000";
						resMsg = "受理成功";
					}else if(StringUtils.isNotBlank(sys_type) && StringUtils.equals(sys_type, "0")){//线下
						// TODO 待支付功能线下交易数据同步先不处理
					}else{
						resMsg = "参数sys_type传值错误";
					}
				}else{
					resMsg = "参数tranCode传值错误";
				}
			}
			out.print(returnJsonString(tranCode, tseq, resCode, resMsg, merPriv));
		} catch (Exception e) {
			log.error(e);
			resMsg = "系统异常";
			out.print(returnJsonString(tranCode, tseq, resCode, resMsg, merPriv));
		}
	}
	
	
	public static void main(String[] args) {
		int len = 100;
		long start = System.currentTimeMillis();
		System.out.println("start : "+start);
		Random rand = new Random();
		int randNum = 0;
		for(int i=0;i<len;i++){
			randNum = rand.nextInt(100000000);
			System.out.println("randNum : "+randNum);
			StringBuffer buffer = new StringBuffer("http://192.168.30.44:8014/backstagemamage/reciveTradeDataDzfService?");
			buffer.append("tranCode=ZH0018&sys_type=1");
			buffer.append("&tseq=");
			buffer.append(randNum);
			buffer.append("&version=10&mdate=20151224&mid=300&bid=&oid=");
			buffer.append(System.currentTimeMillis());
			buffer.append("&amount=1&type=1&gate=80002&author_type=0");
			buffer.append("&sys_date=20151224&init_sys_date=20151224&sys_time=83346&fee_amt=1&bank_fee=1");
			buffer.append("&tstat=1&mobile_no=&phone_no=&card_no=6222518299901004&gid=10&pre_amt=1&pre_amt1=1");
			buffer.append("&bk_fee_model=AMT*0.005&pay_amt=1&currency=01&exchange_rate=1&out_user_id=&in_user_id=&bind_mid=&merPriv=3453453");
			System.out.println(buffer.toString());
			try {
				String str = HttpUtil.sendDeleteRequest(buffer.toString(), "UTF-8");
				System.out.println("返回数据 : "+str);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			buffer.setLength(0);
//			buffer.append("http://192.168.30.44:8014/backstagemamage/reciveTradeDataOkService?");
//			buffer.append("tranCode=ZH0019&sys_type=1");
//			buffer.append("&tseq=");
//			buffer.append(randNum);
//			buffer.append("&merPriv=3453453");
//			buffer.append("&version=10&gid=10&tstat=2&bk_flag=1&bk_date=20151228&bk_seq1=123321&bk_seq2&bk_resp=&error_msg=");
//			System.out.println(buffer.toString());
//			try {
//				String str = HttpUtil.sendDeleteRequest(buffer.toString(), "UTF-8");
//				System.out.println("返回数据 : "+str);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		long end = System.currentTimeMillis();
		System.out.println("话费时间 : "+(end - start));
	}
}
