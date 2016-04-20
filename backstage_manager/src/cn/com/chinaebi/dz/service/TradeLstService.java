package cn.com.chinaebi.dz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.object.util.PropertiesUtils;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.webservice.ManualSummaryOriginalType;

public class TradeLstService {
	private static final Log log = LogFactory.getLog(TradeLstService.class);
	private static cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO = cn.com.chinaebi.dz.object.dao.TradeLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = cn.com.chinaebi.dz.object.dao.HlogDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.TmoneyDAO tmoneyDAO = cn.com.chinaebi.dz.object.dao.TmoneyDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO = cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO.getInstance();
	
	private static final ResourceBundle res = ResourceBundle.getBundle("conf");
	
	//交易日期
	private static final String sys_date = res.getString("sys_date");
	
	public static boolean channelDataCollectHandle(ManualSummaryOriginalType type) throws Exception{
		int bankGate = type.getBankGate();
		int inst_id = type.getInstId();
		int inst_type = type.getInstType();
		String tradeTime = type.getSummaryDate();
		
		log.info("收到管理平台请求接口,参数为:交易时间---"+tradeTime+";银行网关ID---"+bankGate);
		
		List<InstInfo> instList = new ArrayList<InstInfo>();
		
		if(inst_id == 0){
			instList = Backstage.getInstance().getInstInfoByBankId(bankGate);
		}else{
			InstInfoPK infoPK = new InstInfoPK(inst_id,inst_type);
			instList.add(Backstage.getInstance().getInstInfo(infoPK));
		}
		
		BankInst bankInst = Backstage.getInstance().getBankInst(bankGate);
		String oriTableName = bankInst.getOriginalDataTableName();
		int bank_id = bankInst.getId();
		boolean data_collect_flag = false;
		if(instList != null && instList.size() > 0){
			for (InstInfo inst_info : instList) {
				log.info("开始手动汇总"+inst_info.getName()+"的交易数据");
				
				try{
					if(inst_type == 1){
						data_collect_flag = rytDataCollectHandle(inst_info,bankInst, tradeTime);
					}else{
						if(inst_info.isWhetherOuterDz()){
							data_collect_flag = posDataCollectHandle(inst_info,bankInst, tradeTime);
						}else{
							data_collect_flag = noDzdataCollectHanler(tradeTime,oriTableName,inst_info.getId().getInstId(),bank_id);
						}
					}
				}catch(Exception e){
					log.error("手动汇总"+inst_info.getName()+"的交易数据失败,抛出异常"+e);
					throw e;
				}
				
				if(data_collect_flag){
					executeNodeDAO.updateExecuteNodeStatus("trade_collect", inst_info.getId().getInstId(), tradeTime, "1",inst_info.getName(),inst_info.getId().getInstType());
				}else{
					executeNodeDAO.updateExecuteNodeStatus("trade_collect", inst_info.getId().getInstId(), tradeTime, "2",inst_info.getName(),inst_info.getId().getInstType());
				}
				
				if(!data_collect_flag){
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.ORIGINAL_DATA_COLLECT, emailPoliceDAO, tradeTime,inst_info,MailSendInfoUtil.DATA_COLLCT_ERROR);
				}
			}
		}else{
			log.warn(bankInst.getBankName()+"下无渠道配置信息");
		}
		
		
		return data_collect_flag;
	}
	
	
	/**
	 * 汇总线下不需要外部对账渠道数据
	 * @param tradeTime ：交易时间
	 * @param deduct_sys_id : 渠道ID
	 * @param bank_id : 银行网关号
	 * @return boolean
	 */
	public static boolean noDzdataCollectHanler(String tradeTime,String tableName,int deduct_sys_id,int bank_id){
		boolean data_collect_flag = false;
		int count = tradeLstDAO.noDzdataCollectHanler(tradeTime, tableName, deduct_sys_id, bank_id);
		data_collect_flag = count >= 0 ? true : false;
		return data_collect_flag;
	}
	
	/**
	 * 线下数据汇总
	 * 1.数据拉取
	 * 2.数据分割
	 * 3.数据有效性处理
	 * 4.数据金额正负处理
	 * 5.多次冲正去重
	 * 6.成功交易资金流水
	 * @param instInfo
	 * @param bankInst
	 * @param tradeTime
	 * @return
	 * @throws Exception
	 */
	public static boolean posDataCollectHandle(InstInfo instInfo,BankInst bankInst,String tradeTime) throws Exception{
		boolean data_collect_flag = false;
		try {
			int inst_id = instInfo.getId().getInstId();
			int bank_id = bankInst.getId();
			String originalDataTableName = bankInst.getOriginalDataTableName();
			int dataCollectDataNum = tradeLstDAO.dataCollectHanler(tradeTime, originalDataTableName, inst_id,bank_id);
			if(dataCollectDataNum > 0){
				boolean splitData_flag = tradeLstDAO.splitData(tradeTime, originalDataTableName,inst_id,bank_id);
				if(splitData_flag){
					boolean dispenseWithHandler_flag = true;
					if(instInfo.isWhetherOuterDz()){//判断该渠道是否需要外部对账
						dispenseWithHandler_flag = tradeLstDAO.dispenseWithHandle(tradeTime, originalDataTableName, 1);
					}
					if(dispenseWithHandler_flag){
						boolean handlerMoney_flag = tradeLstDAO.handleMoney(tradeTime, originalDataTableName,inst_id);
						if(handlerMoney_flag){
							//对交易数据进行去重操作（多次冲正时，只保留一条有效信息）
							boolean distinctTradeRollBkData_flag = tradeLstDAO.deductRollBkDeWeight(tradeTime, originalDataTableName,inst_id);
							if(distinctTradeRollBkData_flag){
								//处理商户余额、资金流水
								boolean merStanceAndBalance_flag = tradeLstDAO.saveMerStanceAndBalance(tradeTime, instInfo,bank_id);
								if(merStanceAndBalance_flag){
									data_collect_flag = true;
								}else{
									log.info(instInfo.getName()+"渠道处理进入商户余额、资金流水操作失败");
								}
							}else{
								log.info(instInfo.getName()+"渠道数据去重操作失败");
							}
						}else{
							log.info(instInfo.getName()+"渠道数据金额处理失败");
						}
					}else{
						log.info(instInfo.getName()+"渠道数据无需对账状态处理失败");
					}
				}else{
					log.info(instInfo.getName()+"渠道数据分割失败");
				}
			}else if(dataCollectDataNum == 0){
				data_collect_flag = true;
				MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.ORIGINAL_DATA_COLLECT,emailPoliceDAO,tradeTime,instInfo,MailSendInfoUtil.DATA_ZERO_COUNT);
			}
			
			try{
				 executeNodeDAO.updateExecuteNodeStatus("trade_collect", instInfo.getId().getInstId(), tradeTime, data_collect_flag?"1":"2",instInfo.getName(),instInfo.getId().getInstType());
			}catch(Exception e){
				log.error("更新"+instInfo.getName()+"自动汇总交易数据工作流节点抛出异常："+e);
			}
			
			if(data_collect_flag){
				if(instInfo.isWhetherOuterDz()){//判断该渠道是否需要外部对账
					boolean deleteFlag = tmoneyDAO.deleteTmoneyData(Integer.valueOf(tradeTime.replaceAll("-", "")), inst_id, instInfo.getId().getInstType());
					if(deleteFlag){
						boolean tcdb_flag = tmoneyDAO.proce_pos_head_handle(tradeTime, originalDataTableName, Integer.valueOf(tradeTime.replaceAll("-", "")), inst_id, instInfo.getId().getInstType());//执行头寸调拨存储过程
						if(!tcdb_flag){
							log.debug("调用头寸调拨存储过程失败，请查看原因");
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.ORIGINAL_DATA_COLLECT,emailPoliceDAO,tradeTime,instInfo,MailSendInfoUtil.DATA_TOU_CUN_DIAO_BO_FAIL);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("线上数据汇总失败："+e);
			throw e;
		}
		return data_collect_flag;
	}
	
	/**
	 * 线上数据汇总
	 * @param instInfo
	 * @param bankInst
	 * @param tradeTime
	 * @return
	 * @throws Exception
	 */
	public static boolean rytDataCollectHandle(InstInfo instInfo,BankInst bankInst,String tradeTime) throws Exception{
		boolean data_collect_flag = false;
		try {
			int inst_id = instInfo.getId().getInstId();
			int bank_id = bankInst.getId();
			int inst_type = instInfo.getId().getInstType();
			
			String originalDataTableName = bankInst.getOriginalDataTableName();
			String[] tables = originalDataTableName.split(",");
			
			int dzSysOnlineDataCount = hlogDAO.getOnlineDataCountFromDzSys(tradeTime, instInfo.getId().getInstId(), sys_date);
			log.info("统计对账系统数据库中"+instInfo.getName()+"渠道"+tradeTime+"交易数据数量"+dzSysOnlineDataCount);
			
			int ryfSysOnlineDataCount = hlogDAO.getOnLineDataCountFromRYF(tradeTime, instInfo.getId().getInstId(), sys_date);
			log.info("统计融易付系统数据库中"+instInfo.getName()+"渠道"+tradeTime+"交易数据数量"+ryfSysOnlineDataCount);
			
			//处理融易付平台hlog到对账系统hlog
			if(dzSysOnlineDataCount != ryfSysOnlineDataCount){
				log.info("统计对账系统数据库中"+instInfo.getName()+"渠道"+tradeTime+"交易数据数量与融易付系统数据库中数据不一致,删除已有数据,重新汇总");
				int dataCount = hlogDAO.deleteHlogDataCount(tradeTime,inst_id);
				if(dataCount >= 0){
					try{
						String gid = PropertiesUtils.rtReadProperties("gid", "conf");
						hlogDAO.getRytHlog(tradeTime, inst_id,sys_date,gid);
					}catch(Exception e){
						log.error("从融易付hlog表同步数据失败："+e);
					}
				}
			}
			
			//处理对账系统hlog 到 各个渠道表
			boolean flag_upmp = true;
			int dataCount_upmp = hlogDAO.dataCollectHanler(tradeTime, tables[0], inst_id,bank_id);
			if(dataCount_upmp >= 0){
				data_collect_flag = true;
				log.info("汇总的融易付收款交易数据总数为"+dataCount_upmp);
				
				data_collect_flag = hlogDAO.rytDispenseWithHandle(tradeTime, tables[0], 1,inst_id);
				if(!data_collect_flag){
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.ORIGINAL_DATA_COLLECT, emailPoliceDAO, tradeTime,instInfo,MailSendInfoUtil.DATA_STATUS_ERROR); 
				}
				
				log.info("开始将收款交易成功的交易数据进入商户余额、商户资金流水...");
				hlogDAO.handlerRytSuccessData(tradeTime, sys_date, inst_id, tables[0],bank_id);
			 }else if(dataCount_upmp == -1){
				 flag_upmp = false;
			 }
			if(flag_upmp){//针对线上收款数据执行头寸调拨存储过程
				if(instInfo.isWhetherOuterDz()){//判断该渠道是否需要外部对账
					boolean deleteFlag = tmoneyDAO.deleteTmoneyData(Integer.valueOf(tradeTime.replaceAll("-", "")), inst_id, inst_type);
					if(deleteFlag){
						boolean tcdb_flag = tmoneyDAO.proce_ryf_head_handle(tradeTime, tables[0], Integer.valueOf(tradeTime.replaceAll("-", "")),"sys_date","amount", inst_id, inst_type);
						if(!tcdb_flag){
							log.debug("针对线上收款数据调用头寸调拨存储过程失败，请查看原因");
							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.ORIGINAL_DATA_COLLECT,emailPoliceDAO,tradeTime,instInfo,MailSendInfoUtil.ONLINE_DATA_TCDO_FAIL);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("线上数据汇总失败"+e);
			throw e;
		}
		return data_collect_flag;
	} 

}
