package cn.com.chinaebi.syn.webservice;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.service.TradeOkList;
import cn.com.chinaebi.dz.util.StringPingJie;
import cn.com.chinaebi.dz.util.StringUtils;

/**
 * 接收交易完成请求交易数据
 * @author zhu.hongliang
 */
public class ReciveTradeDataOkService extends HttpServlet{
	
	private static final long serialVersionUID = -2638830087908561831L;
	private TradeOkList tradeOkList = TradeOkList.getInstance();
	private static Log log = LogFactory.getLog(ReciveTradeDataOkService.class);
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
				if(StringUtils.equals(tranCode, "ZH0019")){
					if(StringUtils.isNotBlank(sys_type) && StringUtils.equals(sys_type, "1")){ //线上
						String gid = request.getParameter("gid");
						log.info(pingJie.getStringPingJie("gid = ",gid));
						String tstat = request.getParameter("tstat");
						log.info(pingJie.getStringPingJie("tstat = ",tstat));
						String bk_flag = request.getParameter("bk_flag");
						log.info(pingJie.getStringPingJie("bk_flag = ",bk_flag));
						String bk_date = request.getParameter("bk_date");
						log.info(pingJie.getStringPingJie("bk_date = ",bk_date));
						String bk_seq1 = request.getParameter("bk_seq1");
						log.info(pingJie.getStringPingJie("bk_seq1 = ",bk_seq1));
						String bk_seq2 = request.getParameter("bk_seq2");
						log.info(pingJie.getStringPingJie("bk_seq2 = ",bk_seq2));
						String bk_resp = request.getParameter("bk_resp");
						log.info(pingJie.getStringPingJie("bk_resp = ",bk_resp));
						String error_msg = request.getParameter("error_msg");
						log.info(pingJie.getStringPingJie("error_msg = ",error_msg));
						String[] obj = {sys_type,tseq,gid,tstat,bk_flag,bk_date,bk_seq1,bk_seq2,bk_resp,error_msg};
						tradeOkList.addList(obj);
						resCode = "000";
						resMsg = "受理成功";
					}else if(StringUtils.isNotBlank(sys_type) && StringUtils.equals(sys_type, "0")){//线下
						/*String terminal_info = request.getParameter("terminal_info");
						String terminal_type = request.getParameter("terminal_type");
						String trade_time = request.getParameter("trade_time");
						String out_account = request.getParameter("out_account");
						String out_account_type = request.getParameter("out_account_type");
						String in_account = request.getParameter("in_account");
						String in_card_name = request.getParameter("in_card_name");
						String in_bank_id = request.getParameter("in_bank_id");
						String trade_amount = request.getParameter("trade_amount");
						String trade_fee = request.getParameter("trade_fee");
						String trade_currency = request.getParameter("trade_currency");
						String trade_result = request.getParameter("trade_result");
						String req_sys_id = request.getParameter("req_sys_id");
						String req_sys_stance = request.getParameter("req_sys_stance");
						String req_mer_code = request.getParameter("req_mer_code");
						String req_mer_term_id = request.getParameter("req_mer_term_id");
						String req_response = request.getParameter("req_response");
						String deduct_sys_id = request.getParameter("deduct_sys_id");
						String deduct_sys_stance = request.getParameter("deduct_sys_stance");
						String deduct_mer_code = request.getParameter("deduct_mer_code");
						String deduct_mer_term_id = request.getParameter("deduct_mer_term_id");
						String deduct_result = request.getParameter("deduct_result");
						String deduct_sys_response = request.getParameter("deduct_sys_response");
						String deduct_roll_bk = request.getParameter("deduct_roll_bk");
						String deduct_roll_bk_result = request.getParameter("deduct_roll_bk_result");
						String deduct_roll_bk_reason = request.getParameter("deduct_roll_bk_reason");
						String deduct_roll_bk_response = request.getParameter("deduct_roll_bk_response");
						String deduct_roll_bk_stance = request.getParameter("deduct_roll_bk_stance");
						String trade_type = request.getParameter("trade_type");
						String pass_wd_mode = request.getParameter("pass_wd_mode");
						String req_type = request.getParameter("req_type");
						String req_input_type = request.getParameter("req_input_type");
						String req_time = request.getParameter("req_time");
						String trade_req_method = request.getParameter("trade_req_method");
						String trade_desc = request.getParameter("trade_desc");
						String trade_other_info = request.getParameter("trade_other_info");
						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
						String deduct_sys_time = request.getParameter("deduct_sys_time");
						String gain_sys_id = request.getParameter("gain_sys_id");
						String gain_sys_stance = request.getParameter("gain_sys_stance");
						String gain_mer_code = request.getParameter("gain_mer_code");
						String gain_mer_term_id = request.getParameter("gain_mer_term_id");
						String gain_sys_response = request.getParameter("gain_sys_response");
						String gain_result = request.getParameter("gain_result");
						String gain_trade_amount = request.getParameter("gain_trade_amount");
						String gain_sys_reference = request.getParameter("gain_sys_reference");*/
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
//						String deduct_stlm_date = request.getParameter("deduct_stlm_date");
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
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://192.168.20.138:8014/backstagemamage/reciveTradeDataOkService?");
		buffer.append("tranCode=ZH0019&sys_type=1&merPriv=123&tseq=与待支付流水保持一致&gid=10&tstat=2&bk_flag=1&bk_date=20151226");
		buffer.append("&bk_seq1&bk_seq1=&bk_resp=&error_msg=");
		System.out.println(buffer.toString());
	}
}
