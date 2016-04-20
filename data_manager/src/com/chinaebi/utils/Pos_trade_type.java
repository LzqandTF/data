package com.chinaebi.utils;

/**
 * 定义POS交易类型常量
 * 
 * @author zhu.hongliang
 * 
 */
public class Pos_trade_type {
	
	/**
	 * 根据tradeMsgType,req_process转换数据保存总表
	 * @param tradeMsgType : 交易消息类型
	 * @param req_process ：交易处理码
	 * @return
	 */
	public static int getPosTradeMsgType(int tradeMsgType,String req_process) {
		int tradeMsgType_ = 0;
		switch(tradeMsgType){
			case 2://消费
				if(StringUtils.isNotBlank(req_process)){
					if(StringUtils.equals(req_process, "480000")){
						tradeMsgType_ = 29;
					}else
						tradeMsgType_ = 19;
				}else
					tradeMsgType_ = 19;
				break;
			case 26://消费冲正
				tradeMsgType_ = 20;
				break;
			case 18://消费撤销
				tradeMsgType_ = 21;
				break;
			case 28://消费撤销冲正
				tradeMsgType_ = 22;
				break;
			case 20://退货
				tradeMsgType_ = 23;
				break;
			case 56://预授权完成
				tradeMsgType_ = 24;
				break;
			case 58://预授权完成撤销
				tradeMsgType_ = 25;
				break;
			case 80://预授权完成冲正
				tradeMsgType_ = 26;
				break;
			case 82://预授权完成撤销冲正
				tradeMsgType_ = 27;
			case 110://脱机消费
				tradeMsgType_ = 28;
				break;
			default://未知
				tradeMsgType_ = 30;
				break;
		}
		return tradeMsgType_;
	}
}
