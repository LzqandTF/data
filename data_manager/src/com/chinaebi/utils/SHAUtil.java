package com.chinaebi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {
	public static String unpack_hex(byte[] binaryBytes) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < binaryBytes.length; i++) {
			int b = (int) binaryBytes[i];
			if (b < 0) {
				b += 256;
			}
			if (b <= 0x0F)
				buffer.append("0");
			buffer.append(Integer.toHexString(b));
		}
		return buffer.toString().toUpperCase();
	}
	
	public static String digestMessage(byte[] input) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(input);
		return unpack_hex(md.digest());
	}
}
