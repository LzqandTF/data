package com.chinaebi.utils;

import java.io.*;
import java.security.MessageDigest;
import java.util.Properties;


/**
 * 公共类
 * 
 * @author Administrator
 */
public class Common {
	@SuppressWarnings("unused")
	private static final String String = null;
	public static final String PUB_KEY = "E:/test_pub.txt";

	public static final String PRI_KEY = "E:/";

	/**
	 * 读取配置文件
	 * 
	 * @param name
	 * @return
	 */
	public static String getProperties(String name) {
		try {
			// 定义一个properties文件的名字
			String propFile = "application.properties";
			// 定义一个properties对象
			Properties properties = new Properties();
			// 读取properties
			String path = Common.class.getClassLoader().getResource(propFile)
					.getPath();
			FileInputStream in = new FileInputStream(path);
			// 加载properties文件
			properties.load(in);
			// 读取properties中的某一项
			String value = properties.getProperty(name);
			return value.trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过rsa私钥存取文件名，获取私钥
	 * 
	 * @param file
	 *            rsa私钥存取文件名
	 * @return 以字符串的形式返回私钥
	 */
	// 读取私钥文件写入本地文件
	public static String WriteFile(String path, String prikey) {
		File f = new File(path);
		String msg = "ok";
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(f));
			w.write(prikey);
			w.flush();
			w.close();
		} catch (Exception e) {
			msg = "文件写入失败";
		}
		return msg;
	}

	public static String getPrivateKey(String file) {
		try {
			byte[] pri = new byte[1024];
			FileInputStream inpri = new FileInputStream(file);
			inpri.read(pri);
			inpri.close();
			String privateKey = new String(pri, "UTF-8");
			return privateKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 通过rsa私钥存取文件名，获取私钥
	 * 
	 * @param file
	 *            rsa私钥存取文件名
	 * @return 以字符串的形式返回私钥
	 */
	public static String getPubliceKey(String file) {
		try {
			byte[] pub = new byte[1024];
			FileInputStream in = new FileInputStream(file);
			in.read(pub);
			in.close();
			String publicKey = new String(pub, "UTF-8");
			return publicKey;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String getPriKey(String mid) {
		return getKey(PRI_KEY + mid + "_pri.der");
	}

	public static String getPubKey() {
		return getKey(PUB_KEY);
	}

	public static String getKey(String file) {
		try {
			byte[] pri = new byte[1024];
			FileInputStream inpri = new FileInputStream(file);
			inpri.read(pri);
			inpri.close();
			String privateKey = new String(pri, "UTF-8");
			return privateKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static String toMd5String(String s) throws Exception{  
		StringBuffer sb = new StringBuffer();
		try {
			byte[] btInput = s.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();

			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			throw e;
		}
		return sb.toString();
	}
	
	public static String getMd5Key(){
		return getPrivateKey(getProperties("md5_key")).trim();
	}
	
	public static void main(String[] args) {
		System.out.println(getProperties("whetherTk"));
	}
}
