package cn.com.chinaebi.dz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.base.TradeDzHandler;
import cn.com.chinaebi.dz.base.exception.DuizhangNotFoundException;
import cn.com.chinaebi.dz.base.exception.OriginalDataNotFoundException;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.object.util.PropertiesUtils;
import cn.com.chinaebi.dz.reload.Backstage;

/**
 * 银行网关对账
 */
public class BankInstDzUtil {

	private static Log log =LogFactory.getLog(BankInstDzUtil.class);
	private static StringPingJie pingJie;
	
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO = cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = cn.com.chinaebi.dz.object.dao.HlogDAO.getInstance();
	
	/**
	 * 线下银行网关对账(目前线下银行网关和渠道是一对一的关系)
	 * @param originalPepDate ：查询原始交易数据日期
	 * @param duizhangReqTime ：查询对账单交易数据日期
	 * @param deductStlmDate ：清算日期(判断日切)
	 * @param bank_id ：银行网关ID
	 * @param flagStatus
	 * @param inst_id ：渠道ID
	 * @param inst_type ：渠道类型
	 */
	public static boolean bankInstOfflineDzDeal(String originalPepDate,String duizhangReqTime,String deductStlmDate,
			Integer bank_id,Integer flagStatus,int instId, int inst_type){
		 boolean dzResult = false;
		 pingJie = StringPingJie.getInstance();
		 InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(instId, inst_type));
		 if(instInfo != null){
			 BankInst bankInst = instInfo.getBank();
			 String tradeDzImplClass = bankInst.getTradeDzImplClass();
			 boolean innertTradeHandler = instInfo.isWhetherInnerDz();
			 try {
				try {
					 Class<?> tradeDzCls = Class.forName(tradeDzImplClass);
					 TradeDzHandler t = (TradeDzHandler)tradeDzCls.newInstance();
					 t.tradeDzDeal(originalPepDate, duizhangReqTime, deductStlmDate, innertTradeHandler, bank_id, instId, inst_type, flagStatus);
					 dzResult = true;
					 
					 executeNodeDAO.updateExecuteNodeStatus("dz_handle", instId, originalPepDate, "1",instInfo.getName(),instInfo.getId().getInstType());
				} catch (Exception e) {
					if(e instanceof DuizhangNotFoundException){
						log.info("对账文件数据表中不存在数据，未执行对账操作");
						dzResult = false;
						executeNodeDAO.updateExecuteNodeStatus("dz_handle", instId, originalPepDate, DataStatus.no_dz_data,instInfo.getName(),instInfo.getId().getInstType());
						MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfo,MailSendInfoUtil.NO_DZ_DATA);
					}else if(e instanceof OriginalDataNotFoundException){
						log.info("原始交易件数据表中不存在数据，未执行对账操作");
						dzResult = false;
						executeNodeDAO.updateExecuteNodeStatus("dz_handle", instId, originalPepDate, DataStatus.no_ori_data,instInfo.getName(),instInfo.getId().getInstType());
						MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfo,MailSendInfoUtil.NO_ORI_DATA);
					}else{
						dzResult = false;
						log.error("手动对账抛出未知异常:"+e);
						executeNodeDAO.updateExecuteNodeStatus("dz_handle", instId, originalPepDate, "2",instInfo.getName(),instInfo.getId().getInstType());
						MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfo,MailSendInfoUtil.EXCUTE_DZ_ERROR);
					}
				}
			} catch (Exception e) {
				log.error(e);
			}
		 }else{
			 log.error(pingJie.getStringPingJie("渠道ID :",instId,"、渠道类型 : ",inst_type," inst_info配置信息中未找到该渠道信息"));
		 }
		 return dzResult;
	}
	
	/**
	 * dz_flag : 0- 网关对账、1-网关渠道对账
	 * 线上银行网关对账
	 * @param originalPepDate ：查询原始交易数据日期
	 * @param duizhangReqTime ：查询对账单交易数据日期
	 * @param deductStlmDate ：清算日期(判断日切)
	 * @param bank_id ：银行网关ID
	 * @param flagStatus
	 * 线上银行网关渠道对账
	 * @param originalPepDate ：查询原始交易数据日期
	 * @param duizhangReqTime ：查询对账单交易数据日期
	 * @param deductStlmDate ：清算日期(判断日切)
	 * @param bank_id ：银行网关ID
	 * @param flagStatus
	 * @param inst_id ：渠道ID
	 * @param inst_type ：渠道类型
	 */
	public static boolean bankInstOnlineDzDeal(String originalPepDate,String duizhangReqTime,String deductStlmDate,
			Integer bank_id,Integer flagStatus,int dz_flag,int inst_id, int inst_type){
		pingJie = StringPingJie.getInstance();
		
		boolean dzResult = false;
		BankInst bankInst = Backstage.getInstance().getBankInst(bank_id);
		String subDate = PropertiesUtils.rtReadProperties("subDate", "conf");
		Integer subDate_int = subDate == null ? 3 : Integer.valueOf(subDate);
		
		int bankTotalDataNum = 0;//银行对账单数据总数
		int oriTotalDataNum = 0;//原始交易数据总数
		boolean whetherCatchException = false;//是否捕获到异常标识
		
		int startDate = 0;
		String startDate_str = DYDataUtil.getSubDate(duizhangReqTime, DYDataUtil.DATE_FORMAT_3, subDate_int);
		if(StringUtils.isNotBlank(startDate_str)){
			startDate = Integer.valueOf(startDate_str);
		}else{
			startDate = Integer.valueOf(duizhangReqTime);
		}
		int endDate = Integer.valueOf(duizhangReqTime);
		
		if(bankInst != null){
			bankTotalDataNum = hlogDAO.getBankTotalDataNum(duizhangReqTime, bankInst.getDzDataTableName());
			oriTotalDataNum = hlogDAO.getOriTotalDataNum(startDate,endDate, bankInst.getOriginalDataTableName(),inst_id);
		}
		
		if(bankTotalDataNum != 0 && oriTotalDataNum != 0){//对账单与原始交易同时存在数据时，执行对账操作
			switch(dz_flag){
			 case 0:
			     if(bankInst != null){
			    	try {
						String tradeDzImplClass = bankInst.getTradeDzImplClass();
						Class<?> tradeDzCls = Class.forName(tradeDzImplClass);
						TradeDzHandler t = (TradeDzHandler)tradeDzCls.newInstance();
						t.onLineTradeDzDeal(startDate, endDate, deductStlmDate, bank_id, inst_id, inst_type, flagStatus);
						
						dzResult = true;
					} catch (Exception e) {
						whetherCatchException = true;
						log.info(e);
//						if(e instanceof DuizhangNotFoundException){
//							log.info("对账文件数据表中不存在数据，未执行对账操作");
//							executeNodeDAO.updateExecuteNodeStatus("dz_handle", instId, originalPepDate, DataStatus.no_dz_data,instInfo.getName(),instInfo.getId().getInstType());
//							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfo,MailSendInfoUtil.NO_DZ_DATA);
//						}else if(e instanceof OriginalDataNotFoundException){
//							log.info("原始交易件数据表中不存在数据，未执行对账操作");
//							executeNodeDAO.updateExecuteNodeStatus("dz_handle", instId, originalPepDate, DataStatus.no_ori_data,instInfo.getName(),instInfo.getId().getInstType());
//							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfo,MailSendInfoUtil.NO_ORI_DATA);
//						}else{
//							log.error("手动对账抛出未知异常:"+e);
//							executeNodeDAO.updateExecuteNodeStatus("dz_handle", instId, originalPepDate, "2",instInfo.getName(),instInfo.getId().getInstType());
//							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfo,MailSendInfoUtil.EXCUTE_DZ_ERROR);
//						}
					}
			     }else{
			    	 log.error(pingJie.getStringPingJie("银行网关ID :",bank_id," bank_inst配置信息中未找到该银行网关信息"));
			     }
				 break;
			 case 1:
				 String tradeDzImplClass = bankInst.getTradeDzImplClass();
					try {
						try {
							 Class<?> tradeDzCls = Class.forName(tradeDzImplClass);
							 TradeDzHandler t = (TradeDzHandler)tradeDzCls.newInstance();
							 t.onLineTradeDzDeal(startDate, endDate, deductStlmDate, bank_id, inst_id, inst_type, flagStatus);
							 
							 dzResult = true;
						 } catch (Exception e) {
							 whetherCatchException = true;
							 log.info(e);
						 }
					} catch (Exception e) {
						log.error(e);
					}
				break;
			}
		}else{
			log.warn(pingJie.getStringPingJie("网关号------",bank_id,"------",duizhangReqTime,"日期的对账单数据总数------",bankTotalDataNum,",原始交易数据总数------",oriTotalDataNum,",不执行对账操作"));
		}
		
		//更新工作流节点并发送相关邮件
		if(bankInst != null){
			
			try {
				List<InstInfo> list = new ArrayList<InstInfo>();
				
				InstInfo instInfoForEmail = null;
//				boolean dzFlag = false;//对账维度表示     false---以网关维度对账    true---以渠道维度对账
				
				if(inst_id != 0){//以渠道维度进行对账
					instInfoForEmail = Backstage.getInstance().getInstInfo(new InstInfoPK(inst_id,1));
					list.add(instInfoForEmail);
//					dzFlag = true;
				}else{//以网关维度进行对账
					list = Backstage.getInstance().getInstInfoByBankId(bankInst.getId());
				}
				
				if(bankTotalDataNum == 0){
					if(list != null && list.size() > 0){
						for (InstInfo instInfo : list) {
							if(instInfo != null){
								//以渠道为维度，逐一更新工作流节点
								executeNodeDAO.updateExecuteNodeStatus("dz_handle", instInfo.getId().getInstId(), originalPepDate, DataStatus.no_dz_data,instInfo.getName(),instInfo.getId().getInstType());
							}
						}
					}
					
//					if(dzFlag){
//						//以渠道为维度,发送系统邮件
//						MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfoForEmail,MailSendInfoUtil.NO_DZ_DATA);
//					}else{
//						//以网关为维度,发送系统邮件
//						MailSendInfoUtil.sendEmailForBankInstError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, bankInst,MailSendInfoUtil.NO_DZ_DATA);
//					}
					
				}else if(oriTotalDataNum == 0){
					if(list != null && list.size() > 0){
						for (InstInfo instInfo : list) {
							if(instInfo != null){
								//以渠道为维度，逐一更新工作流节点
								executeNodeDAO.updateExecuteNodeStatus("dz_handle", instInfo.getId().getInstId(), originalPepDate, DataStatus.no_ori_data,instInfo.getName(),instInfo.getId().getInstType());
							}
						}
					}
					
//					if(dzFlag){
//						//以渠道为维度,发送系统邮件
//						MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfoForEmail,MailSendInfoUtil.NO_ORI_DATA);
//					}else{
//						//以网关为维度,发送系统邮件
//						MailSendInfoUtil.sendEmailForBankInstError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, bankInst,MailSendInfoUtil.NO_ORI_DATA);
//					}
					
				}else{
					//TODO 统计网关下各渠道对账情况
					Map<Integer,Integer> dzResultMap = hlogDAO.getInstDzResult(duizhangReqTime, bankInst.getOriginalDataTableName(), DataStatus.dz_success,inst_id);
					
					InstInfo instInfo = null;
					
					for (Integer gid : dzResultMap.keySet()) {
						instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(gid,1));
						if(dzResultMap.get(gid)==0){//未执行对账操作
							executeNodeDAO.updateExecuteNodeStatus("dz_handle", instInfo.getId().getInstId(), originalPepDate, DataStatus.not_dz.toString(),instInfo.getName(),instInfo.getId().getInstType());
						}else{
							if(whetherCatchException){//捕获到异常,则将节点更新为对账失败,否则更新为对账成功
								executeNodeDAO.updateExecuteNodeStatus("dz_handle", instInfo.getId().getInstId(), originalPepDate, DataStatus.dz_error.toString(),instInfo.getName(),instInfo.getId().getInstType());
							}else{
								executeNodeDAO.updateExecuteNodeStatus("dz_handle", instInfo.getId().getInstId(), originalPepDate, DataStatus.dz_success.toString(),instInfo.getName(),instInfo.getId().getInstType());
							}
						}
					}
					
//					if(whetherCatchException){
//						if(dzFlag){//以渠道维度发送系统邮件
//							MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfoForEmail,MailSendInfoUtil.EXCUTE_DZ_ERROR);
//						}else{//以网关维度发送系统邮件
//							MailSendInfoUtil.sendEmailForBankInstError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, bankInst,MailSendInfoUtil.EXCUTE_DZ_ERROR);
//						}
//						
//					}
				}
			} catch (Exception e) {
				log.error("对账操作后，更新工作流节点及发送系统邮件抛出异常:"+e);
			}
		}else{
			log.info(pingJie.getStringPingJie("网关号------",bank_id,"------查找网关对象为空"));
		}
		
		
		return dzResult;
	}
}
