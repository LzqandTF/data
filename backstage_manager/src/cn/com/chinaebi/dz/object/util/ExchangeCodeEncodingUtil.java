package cn.com.chinaebi.dz.object.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExchangeCodeEncodingUtil {
	private static Log log =LogFactory.getLog(ExchangeCodeEncodingUtil.class);
	public static String parseISO88591ToUTF8(String str){
		String result = "";
		try {
			if(StringUtils.isNotBlank(str)){
				result = new String(str.getBytes("ISO-8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("编码格式转换错误");
		}
		return result;
	}
}
