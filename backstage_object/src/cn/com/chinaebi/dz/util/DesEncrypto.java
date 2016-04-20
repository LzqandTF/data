package cn.com.chinaebi.dz.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class DesEncrypto {
	public static String decrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		javax.crypto.SecretKey secretKey = keyFactory
				.generateSecret(desKeySpec);
		cipher.init(2, secretKey);

		return new String(cipher.doFinal(Base64.decode(message)), "UTF-8");
	}
}
