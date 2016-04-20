package com.chinaebi.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class StringPingJie {
	
	private Log log = LogFactory.getLog(getClass());
	private static StringBuffer stringBuffer = new StringBuffer();
	
	private static StringPingJie instance;
	
	public static StringPingJie getInstance () {
		if (null == instance) instance = new StringPingJie();
		return instance;
	}
	
	public String getStringPingJie(Object ...objects){
		stringBuffer.setLength(0);
		if(objects != null && objects.length > 0){
			for (Object object : objects) {
				stringBuffer.append(object.toString());
			}
		}else{
			log.error("调用getStringPingJie字符串拼接方法参数个数不能为空");
		}
		return stringBuffer.toString();
	}
	
}
