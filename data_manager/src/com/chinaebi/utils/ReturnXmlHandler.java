package com.chinaebi.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.webservice.DzTradeContextDocument;
import cn.com.chinaebi.dz.webservice.DzTradeContextType;

/**
 * xmlString解析处理类
 *
 */
public class ReturnXmlHandler {

	private static Log log = LogFactory.getLog(ReturnXmlHandler.class);
	
	public static boolean xmlStringHandling(String xmlString){
		boolean flag = false;
		try {
			DzTradeContextDocument document = DzTradeContextDocument.Factory.parse(xmlString);
			DzTradeContextType type = document.getDzTradeContext();
			String respon = type.getOpCode();
			if(StringUtils.equals(respon, "00")){
				flag = true;
			}
		} catch (Exception e) {
			log.error("DzTradeContextDocument 对象回执信息解析失败");
			log.error(e);
		}
		return flag;
	}
}
