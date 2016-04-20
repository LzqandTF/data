package com.chinaebi.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.entity.InnerErrorHandling;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.ReasonCode;
import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.entity.TradeType;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.utils.FindTradeCodeUtil;
import com.chinaebi.utils.StringUtils;

/**
 * 从内存中获取交易类型、交易类别、原因码
 * @author wufei
 *
 */
@Component(value="commonClass")
public class CommonClass {
	private static Logger logger = LoggerFactory.getLogger(CommonClass.class);
	
	//从内存中获取数据
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	/**
	 * 获取交易类型
	 * @param process 处理码
	 * @param tradeMsgType 交易消息类型
	 * @return
	 */
	public String getTradeTypeByProcessAndTradeMsgType(String process, int tradeMsgType) {
		String tradeType = "未知";
		try {
			if (StringUtils.isNotBlank(process) && tradeMsgType != 0) {
				TradeAmountConf tradeAmountConf = dataManagerInit.getTradeAmountConfMap(process + tradeMsgType);
				if (tradeAmountConf != null) {
					tradeType = tradeAmountConf.getName();
				} 
			}  
		} catch (Exception e) {
			logger.error("根据处理码" + process + "和交易消息类型" + tradeMsgType + "获取交易类型getTradeTypeByProcessAndTradeMsgType(String process, int tradeMsgType)出错：" + e.getMessage());
		}
		return tradeType;
	}
	
	/**
	 * 根据终端信息获取交易类别
	 * @param terminalInfo
	 * @return
	 */
	public String getTradeNameByTerminalInfo(String terminalInfo) {
		String tradeName = "未知";
		try {
			if (StringUtils.isNotBlank(terminalInfo)) {
				String info = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(terminalInfo);
				TradeType tradeType =  dataManagerInit.getTradeType(info);
				if (tradeType != null) {
					tradeName = tradeType.getTrade_name();
				} else {
					return tradeName;
				}
			} else {
				logger.error("终端信息为空");
				return tradeName;
			}
		} catch (Exception e) {
			logger.error("根据终端信息" +terminalInfo + "获取交易类别getTradeNameByTerminalInfo(String terminalInfo)出错：" + e.getMessage());
		}
		return tradeName;
	}
	
	/**
	 * 根据Id获取处理方式
	 * @param id
	 * @return
	 */
	public String getHandlingNameById(int id) {
		String handingName = "未知";
		try {
			ErrorHandling errorHandling =  dataManagerInit.getErrorHanding(id);
			if(errorHandling != null){
				handingName = errorHandling.getHandling_name();
			}
		} catch (Exception e) {
			logger.error("根据" + id + "获取处理方式名称getHandlingNameById(int id)出错：" + e.getMessage());
		}
		return handingName;
	}
	
	/**
	 * 根据原因码获取原因描述
	 * @param reasonCode
	 * @return
	 */
	public String getReasonDescByReasonCode(String reasonCode) {
		String reasonDes = "未知";
		try {
			if (StringUtils.isNotBlank(reasonCode)) {
				ReasonCode reasonCodes = dataManagerInit.getReasonCode(reasonCode);
				if (reasonCodes != null) {
					reasonDes = reasonCodes.getReason_desc();
				}
			} else {
				return reasonDes;
			}
		} catch (Exception e) {
			logger.error("根据原因码" + reasonCode + "获取原因描述出错：" + e.getMessage());
		}
		return reasonDes;
	}
	
	/**
	 * 根据原因码获得原因ID
	 * @param reasonCode
	 * @return
	 */
	public int getIdByReasonCode(String reasonCode) {
		int reasonId = 0;
		try {
			if (StringUtils.isNotBlank(reasonCode)) {
				ReasonCode reasonCodes = dataManagerInit.getReasonCode(reasonCode);
				if (reasonCodes != null) {
					reasonId = reasonCodes.getId();
				}
			} else {
				return reasonId;
			}
		} catch (Exception e) {
			logger.error("根据原因码" + reasonCode + "获取原因ID出错：" + e.getMessage());
		}
		return reasonId;
	}
	
	public List<ErrorHandling> getErrorHandlingList(){
		List<ErrorHandling> list = null;
		try {
			list = dataManagerInit.getErrorHandlingList();
			if(list == null)
				logger.error("获取差错处理方式数据为空");
			else
				for (ErrorHandling errorHandling : list) {
					logger.info(errorHandling.getHandling_name());
				}
		} catch (Exception e) {
			logger.error("获取差错处理方式数据错误 ：" + e.getMessage());
		}
		return list;
	}
	
	public List<InstInfo> getInstInfoList(){
		return dataManagerInit.getInstInfoList();
	}
	
	/**
	 * 根据处理方式ID获取内部差错处理方式的名称
	 * @param handlingId
	 * @return
	 */
	public String getInnerHandlingNameByHandlingId(int handlingId) {
		String handingName = "未知";
		try {
			InnerErrorHandling innerErrorHandling =  dataManagerInit.getInnerErrorHanding(handlingId);
			if(innerErrorHandling != null){
				handingName = innerErrorHandling.getHandling_name();
			}
		} catch (Exception e) {
			logger.error("根据" + handlingId + "获取处理方式名称getInnerHandlingNameByHandlingId(int handlingId)出错：" + e.getMessage());
		}
		return handingName;
	}
}

