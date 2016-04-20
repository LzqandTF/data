package cn.com.chinaebi.dz.object.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.apache.http.util.EntityUtils;

/**
 * HTTP相关的操作
 * 
 * @author bo.li
 * @Date 2011-10-9
 */
@SuppressWarnings("deprecation")
public class HttpUtil {
	private static Log log = LogFactory.getLog(HttpUtil.class);
	public static final String CHARACTER_ENCODING = "UTF-8";
	public static final String PATH_SIGN = "/";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_GET = "GET";
	public static final String METHOD_DELETE = "DELETE";
	public static final String METHOD_PUT = "PUT";
	public static final String CONTENT_TYPE = "Content-Type";
	static {
		System.setProperty("sun.net.client.defaultConnectTimeout", "50000");
		System.setProperty("sun.net.client.defaultReadTimeout", "50000");
	}

	/**
	 * 获取客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取对应ip的物理网卡地址
	 * 
	 * @param ip
	 * @return
	 */
	public String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	public static String getRootPath(ServletContext sctx) {
		String rootpath = sctx.getRealPath("/");
		if (rootpath != null) {
			rootpath = rootpath.replaceAll("\\", "/");
		} else {
			rootpath = "/";
		}
		if (!rootpath.endsWith("/")) {
			rootpath = rootpath + "/";
		}
		return rootpath;
	}

	@SuppressWarnings("unused")
	private static String inputStreamToString(InputStream is, String encode)
			throws Exception {
		StringBuffer buffer = new StringBuffer();
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(is, encode));
		int ch;
		for (int length = 0; (ch = rd.read()) > -1; length++) {
			buffer.append((char) ch);
		}
		rd.close();
		return buffer.toString();
	}

	/**
	 * 发送get请求，获取返回html
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param encode
	 *            页面编码
	 * @return
	 * @throws Exception
	 */
	public static String sendGetRequest(String strUrl, String encode)
			throws Exception {
		URL newUrl = new URL(strUrl);
		HttpURLConnection hConnect = (HttpURLConnection) newUrl
				.openConnection();
		InputStream is = hConnect.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		hConnect.disconnect();
		return str;
	}

	/**
	 * 发送delete请求，获取返回html
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param encode
	 *            页面编码
	 * @return
	 * @throws Exception
	 */
	public static String sendDeleteRequest(String requestUrl, String encode)
			throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_DELETE);// 提交模式
		conn.setConnectTimeout(5000);// 连接超时 单位毫秒
		conn.setReadTimeout(5000);// 读取超时 单位毫秒
		conn.setDoInput(true);
		conn.setUseCaches(false);

		InputStream is = conn.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		conn.disconnect();
		return str;
	}

	/**
	 * 发送post请求
	 * 
	 * @param requestUrl
	 *            请求URL地址
	 * @param params
	 *            请求参数 text1=aaa&text2=bbb
	 * @param encode
	 *            请求参数及页面的编码
	 * @return 返回页面返回的html
	 * @throws Exception
	 */
	public static String sendPostRequest(String requestUrl, String params,
			String encode) throws Exception {
		log.info(requestUrl + "?" + params);
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_POST);// 提交模式
		conn.setConnectTimeout(600000);// 连接超时 单位毫秒
		conn.setReadTimeout(600000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.setUseCaches(false);

		byte[] b = params.toString().getBytes(encode);
		OutputStream os = conn.getOutputStream();
		os.write(b);// 输入参数
		os.flush();
		os.close();

		InputStream is = conn.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		conn.disconnect();
		return str;
	}

	/**
	 * 发送post请求-指定timeOut
	 * 
	 * @param requestUrl
	 *            请求URL地址
	 * @param params
	 *            请求参数 text1=aaa&text2=bbb
	 * @param encode
	 *            请求参数及页面的编码
	 * @param timeOut
	 *            请求超时时间
	 * @return 返回页面返回的html
	 * @throws Exception
	 */
	public static String sendPostRequest(String requestUrl, String params,
			String encode, int timeOut) throws Exception {
		log.info(requestUrl + "?" + params);
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_POST);// 提交模式
		conn.setConnectTimeout(timeOut);// 连接超时 单位毫秒
		conn.setReadTimeout(timeOut);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.setUseCaches(false);

		byte[] b = params.toString().getBytes(encode);
		OutputStream os = conn.getOutputStream();
		os.write(b);// 输入参数
		os.flush();
		os.close();

		InputStream is = conn.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		conn.disconnect();
		return str;
	}

	/**
	 * 发送post请求
	 * 
	 * @param requestUrl
	 *            请求URL地址
	 * @param params
	 *            Map类型的参数
	 * @param encode
	 *            请求参数及页面的编码
	 * @return String
	 * @throws Exception
	 */
	public static String sendPostRequest(String requestUrl,
			Map<String, String> params, String encode) throws Exception {
		StringBuffer paramStr = new StringBuffer("");
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				paramStr.append(key);
				paramStr.append("=");
				paramStr.append(encode(params.get(key)));
				paramStr.append("&");
			}
		}
		return sendPostRequest(requestUrl, paramStr.toString(), encode);
	}

	/**
	 * 发送post请求-指定超时时间
	 * 
	 * @param requestUrl
	 *            请求URL地址
	 * @param params
	 *            Map类型的参数
	 * @param encode
	 *            请求参数及页面的编码
	 * @return String
	 * @throws Exception
	 */
	public static String sendPostRequest(String requestUrl,
			Map<String, String> params, String encode, int timeOut)
			throws Exception {
		StringBuffer paramStr = new StringBuffer("");
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				paramStr.append(key);
				paramStr.append("=");
				paramStr.append(encode(params.get(key)));
				paramStr.append("&");
			}
		}
		return sendPostRequest(requestUrl, paramStr.toString(), encode);
	}

	/**
	 * 发送put请求
	 * 
	 * @param requestUrl
	 *            请求URL地址
	 * @param params
	 *            请求参数 text1=aaa&text2=bbb
	 * @param encode
	 *            请求参数及页面的编码
	 * @return 返回页面返回的html
	 * @throws Exception
	 */
	public static String sendPutRequest(String requestUrl, String params,
			String encode) throws Exception {
		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_PUT);// 提交模式
		conn.setConnectTimeout(5000);// 连接超时 单位毫秒
		conn.setReadTimeout(5000);// 读取超时 单位毫秒
		conn.setDoOutput(true);// 是否输入参数
		conn.setDoInput(true);
		conn.setUseCaches(false);

		byte[] b = params.toString().getBytes(encode);
		OutputStream os = conn.getOutputStream();
		os.write(b);// 输入参数
		os.flush();
		os.close();

		InputStream is = conn.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		conn.disconnect();
		return str;
	}

	/**
	 * 发送带文件上传以及文本域的post请求
	 * 
	 * @param urlString
	 *            post请求地址
	 * @param params
	 *            文本
	 * @param files
	 *            文件
	 * @return 返回的html
	 * @throws Exception
	 */
	public static String sendPostFileRequest(String urlString,
			Map<String, String> params, Map<String, String> files, String encode)
			throws Exception {
		// 是否在控制台打印请求参数，方便调用
		boolean printArgs = false;

		// 构送请求http请求参数
		String BOUNDARY = "---------------------------7d4a6d158c9"; // 分隔线
		List<byte[]> headerList = new ArrayList<byte[]>();
		List<byte[]> contentList = new ArrayList<byte[]>();
		byte[] end = ("--" + BOUNDARY + "--\r\n").getBytes();
		int contentLength = 0;
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				byte[] header = ("--" + BOUNDARY
						+ "\r\nContent-Disposition: form-data;name=\"" + key + "\"\r\n\r\n")
						.getBytes(encode);
				byte[] content = params.get(key).getBytes(encode);
				headerList.add(header);
				contentList.add(content);
				contentLength += header.length + content.length
						+ "\r\n".getBytes().length;
				if (printArgs) {
					System.out.print(new String(header, encode));
					System.out.print(new String(content, encode));
					System.out.print(new String("\r\n".getBytes()));
				}
			}
		}
		if (files != null && files.size() > 0) {
			for (String key : files.keySet()) {
				String filename = files.get(key).substring(
						files.get(key).lastIndexOf("/") + 1);
				byte[] header = ("--" + BOUNDARY
						+ "\r\nContent-Disposition: form-data; name=\"" + key
						+ "\"; filename=\"" + filename + "\"\r\nContent-Type: multipart/form-data\r\n\r\n")
						.getBytes(encode);
				byte[] content = org.apache.commons.io.FileUtils
						.readFileToByteArray(new File(files.get(key)));
				headerList.add(header);
				contentList.add(content);
				contentLength += header.length + content.length
						+ "\r\n".getBytes().length;
				if (printArgs) {
					System.out.print(new String(header, encode));
					System.out.print(new String(content));
					System.out.print(new String("\r\n".getBytes()));
				}
			}
		}
		contentLength += end.length;
		if (printArgs) {
			System.out.print(new String(end));
		}

		// 发送http请求
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(METHOD_POST);
		conn.setConnectTimeout(5000);// 连接超时 单位毫秒
		conn.setReadTimeout(5000);// 读取超时 单位毫秒
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + BOUNDARY);
		conn.setRequestProperty("Content-Length", String.valueOf(contentLength));
		OutputStream os = conn.getOutputStream();
		for (int i = 0; i < headerList.size(); i++) {
			os.write(headerList.get(i));
			os.write(contentList.get(i));
			os.write("\r\n".getBytes());
		}
		os.write(end);
		os.flush();
		os.close();
		// 获取http请求结果
		InputStream is = conn.getInputStream();
		String str = inputStreamToString(is, encode);
		is.close();
		conn.disconnect();
		return str;
	}

	/**
	 * url解码
	 * 
	 * @param str
	 * @return 解码后的字符串,当异常时返回原始字符串。
	 */
	public static String decode(String url) {
		if (url == null) {
			return null;
		}
		try {
			return URLDecoder.decode(url, CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException ex) {
			return url;
		}
	}

	/**
	 * url编码
	 * 
	 * @param str
	 * @return 编码后的字符串,当异常时返回原始字符串。
	 */
	public static String encode(String url) {
		if (url == null) {
			return null;
		}
		try {
			return URLEncoder.encode(url, CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException ex) {
			return url;
		}
	}

	public static String sendMessage(String keyPath, String requestUrl, String message) throws Exception {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream instream = new FileInputStream(new File(keyPath));
		// 密匙库的密码
		trustStore.load(instream, "changeit".toCharArray());
		// 注册密匙库
		SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
		// 不校验域名
		socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", 443, socketFactory);
		// 线程安全的
		HttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		// 加入签名
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		HttpPost postMethod = new HttpPost(requestUrl);

		// 设置策略（设置请求重试处理，请求3次）
		DefaultHttpRequestRetryHandler handler = new DefaultHttpRequestRetryHandler(3, false);
		((AbstractHttpClient) httpClient).setHttpRequestRetryHandler(handler);

		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 500);// 连接时间
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,500);// 数据传输时间

		postMethod.setHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		
		// 转换为字节流
		ByteArrayInputStream byteMessage = new ByteArrayInputStream(message.getBytes(), 0, message.getBytes().length);
		// 获取请求体对象
		HttpEntity httpEntity = new InputStreamEntity(byteMessage,message.getBytes().length);
		// 设置请求体
		postMethod.setEntity(httpEntity);
		
		StringBuffer returnMessage = new StringBuffer();
		// 生成图片
		try {
			HttpResponse resp = httpClient.execute(postMethod);
			log.info(resp.getStatusLine().getStatusCode());
			
			// 判断返回状态码
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取返回内容
				HttpEntity entity = resp.getEntity();
				if (entity != null) {
					String lineMessage;
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
					while ((lineMessage = bufferedReader.readLine()) != null) {
						returnMessage.append(lineMessage);
					}
					bufferedReader.close();
				}
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return returnMessage.toString();
	}
}