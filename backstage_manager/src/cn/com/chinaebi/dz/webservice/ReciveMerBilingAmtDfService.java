package cn.com.chinaebi.dz.webservice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.util.Base64;
import cn.com.chinaebi.dz.object.util.Common;
import cn.com.chinaebi.dz.service.MerchantFundSettleService;

/**
 * 接收融易付银行卡代付结果回调通知接口
 * @author wufei
 *
 */
public class ReciveMerBilingAmtDfService extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(ReciveMerBilingAmtDfService.class);
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response){
		log.info("开始接受代付异步通知参数...");
		try {
			request.setCharacterEncoding("ISO-8859-1");
			String accountId = request.getParameter("accountId"); //账户号
			String orderId = request.getParameter("orderId");     //订单号
			String transAmt = request.getParameter("transAmt");   //交易金额
			String transType = request.getParameter("transType"); //交易类型
			String sysDate = request.getParameter("sysDate");     //交易发起日期
			String sysTime = request.getParameter("sysTime");     //交易发起时间
			String transStatus = request.getParameter("transStatus"); //交易状态
			String tseq = request.getParameter("tseq");               //交易流水号
			String errorMsg = new String(request.getParameter("errorMsg").getBytes("ISO-8859-1"), "utf-8");       //错误信息
			String merPriv = request.getParameter("merPriv");         //商家私有域
			String chkValue = new String(request.getParameter("chkValue").getBytes(),"UTF-8");       //数字签名
			String md5Key = Common.getMd5Key();
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(orderId);
			buffer.append(transAmt);
			buffer.append(tseq);
			buffer.append(transStatus);
			buffer.append(md5Key);
			String sign = Common.MD5(buffer.toString()).toUpperCase();
			
			log.info("异步通知的参数信息如下：");
			log.info("账户号：" + accountId + "\t" + "订单号：" + orderId + "\t" + "订单金额：" + transAmt + "\t" +
			"交易类型：" + transType + "\t" + "交易发起日期：" + sysDate + "\t" + "交易发起时间：" + sysTime + "\t" +
			"交易状态是：" + transStatus + "\t" + "交易流水号：" + tseq + "\t" + "错误信息：" + errorMsg + "\t" +
			"商家私有域：" + Base64.getFromBASE64(merPriv) + "\t" + "数字签名：" + chkValue);
			Integer df_result = 0;
			String str = "";
			if(sign.equals(chkValue)){
				if(StringUtils.equals(transStatus, "F")){
					df_result = 2;
					str = "失败";
				}else if(StringUtils.equals(transStatus, "S")){
					df_result = 1;
					str = "成功";
				}else{
					df_result = 5;
					str = "代付未知";
				}
			} else {
				df_result = 4;
				str = "签名认证失败";
			}
			log.info("代付异步通知结果是："+str);
			
			String id = Base64.getFromBASE64(merPriv);
			if (StringUtils.isNotBlank(id)) {
				//收到异步通知结果修改数据状态
				boolean flag = MerchantFundSettleService.asnycReciveMerBilingAmtDf(Integer.valueOf(id), df_result, errorMsg);
				if(flag){
					log.info("融易付银行卡代付异步通知代付结果处理成功");
				}else{
					log.info("融易付银行卡代付异步通知代付结果处理失败");
				}
			} else {
				log.info("异步通知返回的ID为空");
			}
			buffer.setLength(0);
			buffer.append("RECV_RYT_ORD_ID_");
			buffer.append(orderId);
			response.getWriter().print(buffer.toString());
		} catch (Exception e) {
			log.error("接受代付异步通知结果出现异常：" + e.getMessage());
		}
	}
}
