package cn.com.chinaebi.dz.object.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 采用HmacMD5算法对xml进行加密，生成摘要mac
 * @author wufei
 *
 */
public class HmacMD5 {
	public static final String HEX_STR = "0123456789ABCDEF";
	
	/**
	 * 十进制字节数组转十六进制字符串
	 * @param data
	 * @return
	 */
	public static String byteArrayToHexStr(byte[] data){
		StringBuilder sb = new StringBuilder();
		for(byte b : data){
			char highStr = HEX_STR.charAt((b & 0xff) >> 4); 
			sb.append(highStr);
			char lowStr = HEX_STR.charAt(b & 0x0f);
			sb.append(lowStr);
		}	
		return sb.toString();
	}
	
	/**
	 * 生成摘要
	 * @param algorithm
	 * @param data
	 * @param key
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	public static String encryptMAC(String algorithm,byte[] data,byte[] key) throws InvalidKeyException, NoSuchAlgorithmException{
		SecretKey sk = new SecretKeySpec(key, algorithm);
		Mac mac = Mac.getInstance(algorithm);
		//初始化秘钥
		mac.init(sk);
		return byteArrayToHexStr(mac.doFinal(data));  
	}
	
	public static void main(String[] args) {
		String str = "E/0VWzwEqUD1gC0F4dn4Y2JFT8QetPI7icPCVrnH1cs=";
		byte[] keys = Base64.decode(str);
		try {
			System.out.println(encryptMAC("HmacMD5", "测试".getBytes(), keys));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
