package cn.com.chinaebi.dz.object.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO;

/**
 * TODO ...
 * 
 **/
public class FindTradeCodeUtil {
	
	private static Log logger = LogFactory.getLog(FindTradeCodeUtil.class);
	
	private static FindTradeCodeUtil findTradeCodeUtil;
	
	public static FindTradeCodeUtil getNewInstance(){
		return findTradeCodeUtil == null ? findTradeCodeUtil = new FindTradeCodeUtil() : findTradeCodeUtil;
	}
	
	/**
	 * 获取交易码类型
	 * @param tradeCode
	 * @return String
	 */
	public String findTradeCodeName(String tradeCode, ErrorDataLstDAO tradeTypeService){
		String tradeCodeName = null;
		if(StringUtils.isNotBlank(tradeCode) && tradeTypeService != null){
			tradeCodeName = tradeTypeService.getTradeCodeName(tradeCode);
			if(!StringUtils.isNotBlank(tradeCodeName)){
				logger.error(tradeCodeName + ":交易码数据库不存在");
			}
		}else {
			logger.error("交易码不能为空 or 注入对象为空");
		}
		return tradeCodeName;
	}
	
	
	
	/**
	 * 处理前置系统报文21域，解析出来的值保存在数据库terminal_id字段和terminal_info字段
	 * 1、移动刷卡设备数据解析规则
	 * 其中terminal_id字段保存psam卡号，terminal_info字段保存报文21域的所有信息
	 * 21域格式：psam卡号（16字节）+ 电话号码，区号与电话号码用|区分（或者手机号）（15字节，左对齐，右补空格）+ 交易发起方式（1字节，ewp平台送过来的信息）+
	 *						 终端类型（2字节，刷卡设备取值为01）+ 交易码（3字节）+ 终端流水（6字节，不存在的话填全0）+ 渠道号（4字节）
	 * 2、pos机数据解析规则
	 * terminal_id字段保存商户号+终端号的信息，terminal_info字段保存报文21域的所有信息。
	 * 21域格式：原pos商户号（15字节）+ 原pos终端号（8字节）+ 交易发起方式（1字节，固定值‘3’）+
	 *						 终端类型（2字节，pos设备取值为03）+ 交易码（3字节）+ 当前pos终端流水（6字节）+ 当前交易批次号（6字节） + 终端当前交易处理码（6字节）+ 渠道号（4字节）
	 * */
	public String tradeCodeSubString(String terminalInfo){
		String tradeName = null;
		if(StringUtils.isNotBlank(terminalInfo)){
			int length = terminalInfo.length();
			switch (length) {
			case 47:
				tradeName = terminalInfo.substring(34, 37);
				break;
			case 51:
				tradeName = terminalInfo.substring(26, 29);
				break;
			case 54:
				tradeName = terminalInfo.substring(34, 37);
				break;
			case 58:
				tradeName = terminalInfo.substring(26, 29);
				break;
			default:
				break;
			}
		}else {
			logger.error("terminal_info 终端信息不能为空");
		}
		return tradeName;
	}
	
	
	public String returnTradeCodeName(String terminalInfo,ErrorDataLstDAO errorDataLstDAO){
		String tradeCode = tradeCodeSubString(terminalInfo);
		return findTradeCodeName(tradeCode,errorDataLstDAO);
	}
	
	
/*	public static void main(String[] args) {
		System.out.println("9997700173100011234649430300200017000000120000000010000000".length());
		System.out.println("9997700173100011234649430300200017000000120000000010000000".substring(26, 29));
//		System.out.println("0DE031002000033218098886424    4012270000000007".length());
//		System.out.println("0DE031002000033218098886424    4012270000000007".substring(34, 37));
	}*/
}

