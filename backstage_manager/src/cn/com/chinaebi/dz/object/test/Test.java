package cn.com.chinaebi.dz.object.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;

import cn.com.chinaebi.dz.object.util.Base64;
import cn.com.chinaebi.dz.object.util.HmacMD5;

@SuppressWarnings("deprecation")
class testRewu {

	public testRewu() {
	};

	public void run(Map<String, Boolean> map) {
		for (String key : map.keySet()) {
			System.out.println("key : " + key + "、value : " + map.get(key));
		}
	}
}

public class Test {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
//		Map<String, Boolean> map = new HashMap<String, Boolean>();
//		map.put("1", true);
//		map.put("2", true);
//		Class classType = Class
//				.forName("cn.com.chinaebi.dz.object.test.testRewu");
//		Object invokeTester = classType.getConstructor(new Class[] {})
//				.newInstance(new Object[] {});
//
//		Method runMethod = classType
//				.getMethod("run", new Class[] { Map.class });
//		runMethod.invoke(invokeTester, new Object[] { map });

		// TradeLstDAO dao = new TradeLstDAO();
		// dao.findReqTime("2014-03-03");
		// PepJnlsDAO pepDao = new PepJnlsDAO();
		// List<PepJnls> listJnls =pepDao.findPepDate("20140306");
		// pepDao.insertPepData(listJnls);
		// PepJnlsDAO dao = new PepJnlsDAO();
		// dao.findPepDate("20140303");
		// TradeLstDAO tradeDao = new TradeLstDAO();
		// List<TradeLst> list = tradeDao.findReqTime("2014-3-06");
		// tradeDao.insertTradeData(list);

		// OriginalDataLstDAO odDao = new OriginalDataLstDAO();
		// odDao.originalDataHandle("20140306","2014-03-06");
		// odDao.splitDataHandler("20140306");

		// OriginalBeijingbankLstDAO beijingbankLstDAO = new
		// OriginalBeijingbankLstDAO();
		// beijingbankLstDAO.beiJingBank("20140306");

		// OriginalCupsLstDAO originalCupsLstDAO = new OriginalCupsLstDAO();
		// originalCupsLstDAO.cpusBank("20140306");
		//
		// OriginalZhongxingbankLstDAO zxDao = new
		// OriginalZhongxingbankLstDAO();
		// zxDao.zhongXingBank("20140306");

		// ApplicationContext springContext = new
		// ClassPathXmlApplicationContext("classpath:spring-quartz.xml");
		// SchedulerService schedulerService =
		// (SchedulerService)springContext.getBean( "schedulerService" );
		// //执行业务逻辑...
		// //设置调度任务
		// //每10秒中执行调试一次 //0 28 17 * * ?
		// schedulerService.schedule("simpleService","0/1 * * ? * * *");
		//
		// try {
		// Thread.sleep(10000);
		// //schedulerService.modifyJob("simpleService", "0/5 * * ? * * *");
		// schedulerService.removeJob("simpleService");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		/*
		 * MerchantFundSettleDAO merchantFundSettleDAO = new
		 * cn.com.chinaebi.dz.object.dao.MerchantFundSettleDAO(); try { String[]
		 * idList = {"1", "2"}; Map<String, String> map = new HashMap<String,
		 * String>(); DecimalFormat df = new DecimalFormat("##0.00"); for
		 * (String id : idList) { List<MerchantFundSettle> list =
		 * merchantFundSettleDAO.queryNeedDeductedMerList(id); //获取MD5的文本 String
		 * md5Key = Common.getMd5Key(); for (MerchantFundSettle
		 * merchantFundSettle : list) { map.put("version", "10");//消息版本
		 * map.put("accountId", merchantFundSettle.getMerCode());//商户号
		 * map.put("orderId", merchantFundSettle.getMerBatchNo());//商户结算批次号
		 * map.put("transAmt",
		 * df.format(Float.parseFloat(merchantFundSettle.getSettleAmount
		 * ())));//应结算金额 map.put("transType", "C1");//接口类型 map.put("dfType",
		 * "A");//代付类型 map.put("cardFlag", "0");//卡/存折标识 map.put("rcvAcno",
		 * merchantFundSettle.getOpenAccountCode());//卡号 map.put("rcvAcname",
		 * merchantFundSettle.getOpenAcountName());//持卡人姓名/公司名称
		 * map.put("bankNo", "123456789012");//开户联行号 map.put("purpose",
		 * "1");//用途 map.put("merPriv", "");//商家私有域 map.put("bgRetUrl",
		 * "http://localhost:8014/backstagemamage/backstageservice");//交易结果接收地址
		 * map.put("dataSource", "3");//数据来源 String signStr = "10" +
		 * merchantFundSettle.getMerBatchNo() +
		 * df.format(Float.parseFloat(merchantFundSettle.getSettleAmount())) +
		 * "C1" + merchantFundSettle.getOpenAccountCode() + md5Key; String
		 * chkValue = Common.MD5(signStr).toUpperCase();
		 * map.put("chkValue",chkValue);//商家签名
		 * 
		 * String xmlResult = HttpUtil.sendPostRequest(RequestUrlConf.url, map,
		 * "utf-8");
		 * 
		 * Document doc = DocumentHelper.parseText(xmlResult); String value =
		 * doc.getRootElement().element("status").element("value").getText();
		 * 
		 * //判断订单录入是否成功 if (value.equals("0")) { //订单录入成功,显示返回结果 String bk_ordId
		 * =
		 * doc.getRootElement().element("transResult").element("orderId").getText
		 * ();
		 * 
		 * String bk_accountId =
		 * doc.getRootElement().element("transResult").element
		 * ("accountId").getText();
		 * 
		 * String bk_transAmt =
		 * doc.getRootElement().element("transResult").element
		 * ("transAmt").getText(); String money =
		 * df.format(Float.parseFloat(bk_transAmt));
		 * 
		 * String bk_transType =
		 * doc.getRootElement().element("transResult").element
		 * ("transType").getText();
		 * 
		 * String bk_sysDate =
		 * doc.getRootElement().element("transResult").element
		 * ("sysDate").getText();
		 * 
		 * String bk_sysTime =
		 * doc.getRootElement().element("transResult").element
		 * ("sysTime").getText();
		 * 
		 * String bk_transStatus =
		 * doc.getRootElement().element("transResult").element
		 * ("transStatus").getText();
		 * 
		 * String bk_tseq =
		 * doc.getRootElement().element("transResult").element("tseq"
		 * ).getText();
		 * 
		 * String bk_errorMsg =
		 * doc.getRootElement().element("transResult").element
		 * ("errorMsg").getText();
		 * 
		 * String bk_merPriv =
		 * doc.getRootElement().element("transResult").element
		 * ("merPriv").getText();
		 * 
		 * String bk_chkValue =
		 * doc.getRootElement().element("transResult").element
		 * ("chkValue").getText();
		 * 
		 * String bk_signStr = bk_ordId + bk_transAmt + bk_tseq + bk_transStatus
		 * + md5Key; String bk_sign = Common.MD5(bk_signStr).toUpperCase();
		 * 
		 * String TransResult = null; if(bk_sign.equals(bk_chkValue))
		 * TransResult = "验签成功，交易状态："; else TransResult = "验签失败，交易状态：";
		 * 
		 * if("W".equals(bk_transStatus)) TransResult += "处理中"; else
		 * if("S".equals(bk_transStatus)) TransResult += "交易成功"; else
		 * if("F".equals(bk_transStatus)) TransResult += "交易失败"; else
		 * TransResult += "状态未知"; } } } } catch (Exception e) { e.getMessage();
		 * }
		 */
		

		//获得密匙库     
		String keyPath = "E:/shdy.keystore";
		//下载验证码
		String url = "https://192.168.18.13/ryf_money/errorAnalysis/addErrorData";

		KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());          
		FileInputStream instream = new FileInputStream(new File(keyPath));           
		//密匙库的密码          
		trustStore.load(instream, "changeit".toCharArray());           
		//注册密匙库          
		SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);           
		//不校验域名
		socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
		Scheme sch = new Scheme("https", 443, socketFactory); 
		//线程安全的
		HttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		//加入签名
		httpClient.getConnectionManager().getSchemeRegistry().register(sch); 
		HttpPost postMethod = new HttpPost(url);

		//设置策略（设置请求重试处理，请求3次）
		DefaultHttpRequestRetryHandler handler = new DefaultHttpRequestRetryHandler(3,false);
		((AbstractHttpClient) httpClient).setHttpRequestRetryHandler(handler); 

		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,1000);//连接时间
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,1000);//数据传输时间
		
		StringBuffer sb = new StringBuffer();
		sb.setLength(0);
		sb.append("<?xml version='1.0' encoding='UTF-8'?>\n");
		sb.append("<MsgText>\n");
			sb.append("\t<data>\n");
				sb.append("\t\t<id>");
				sb.append("193106357820150332034500");
				sb.append("</id>\n");
				sb.append("\t\t<gid>");
				sb.append(4);
				sb.append("</gid>\n");
				sb.append("\t\t<date>");
				sb.append("20150401");
				sb.append("</date>\n");
				sb.append("\t\t<refAmount>");
				sb.append(1290);
				sb.append("</refAmount>\n");
				sb.append("\t\t<flag>");
				sb.append(1);
				sb.append("</flag>\n");
			sb.append("\t</data>\n");
		sb.append("</MsgText>");
		String mac = HmacMD5.encryptMAC("HmacMD5", sb.toString().getBytes(), Base64.decode("E/0VWzwEqUD1gC0F4dn4Y2JFT8QetPI7icPCVrnH1cs="));
		String message = mac + sb.toString();
		
		System.out.println("请求报文："+message);
		
		//转换为字节流
		ByteArrayInputStream byteMessage = new ByteArrayInputStream(message.getBytes(), 0, message.getBytes().length);
				//获取请求体对象
		HttpEntity httpEntity = new InputStreamEntity(byteMessage, message.getBytes().length);
				//设置请求体
		postMethod.setEntity(httpEntity);
		
		postMethod.setHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		
		//生成图片
		try {	
			HttpResponse resp = httpClient.execute(postMethod);
			System.out.println(resp.getStatusLine().getStatusCode());
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is = resp.getEntity().getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				System.out.println(br.readLine());
			}else{
				System.out.println("失败!");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭连接,释放资源
			httpClient.getConnectionManager().shutdown();
		}
	}
}
