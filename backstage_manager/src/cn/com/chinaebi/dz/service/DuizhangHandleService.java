package cn.com.chinaebi.dz.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.base.TradeDzHandler;
import cn.com.chinaebi.dz.base.exception.DuizhangNotFoundException;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.BankInstDzUtil;
import cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType;
import cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType;
import cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType;

public class DuizhangHandleService {
	private static final Log log = LogFactory.getLog(DuizhangHandleService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO = cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO = cn.com.chinaebi.dz.object.dao.TradeLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = cn.com.chinaebi.dz.object.dao.HlogDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.OriginalCupsLstDAO originalCupsLstDAO = cn.com.chinaebi.dz.object.dao.OriginalCupsLstDAO.getInstance();
	
	public static boolean duizhangHandle(ManualHandlerAgainDzType type) throws Exception{
		boolean flag = false;
		try{
			String deductStlmDate_ = type.getSummaryDate();
			
			//处理对账时间
			Calendar calendar = Calendar.getInstance();//系统当前时间
			Date date = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").parse(deductStlmDate_);
			calendar.setTime(date);
			String originalPepDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			String duizhangReqTime = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			String deductStlmDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			
			int instId = type.getInstId();
			
			int bankId = type.getBankId();
			
			int dz_flag = 0;
			
			BankInst bankInst = Backstage.getInstance().getBankInst(bankId);
			InstInfo instInfo = null;
			
			if(bankInst != null){
				if(instId != 0){//管理平台选择指定渠道进行对账操作
					dz_flag = 1;
					InstInfoPK infoPK = new InstInfoPK(instId,type.getInstType());
					instInfo = Backstage.getInstance().getInstInfo(infoPK);
				}
				if(bankInst.getBankType() == 0){//如果为线下渠道并且渠道ID条件为0时，通过网关号找到对应的渠道，进行对账操作
					if(instInfo == null){
						List<InstInfo> instInfoList = Backstage.getInstance().getInstInfoByBankId(bankId);
						if(instInfoList != null && instInfoList.size() > 0){
							instInfo = instInfoList.get(0);
						}
					}
				}
				
				log.info("手动对账开始……");
				try{
					if(bankInst.getBankType() == 0){
						flag = BankInstDzUtil.bankInstOfflineDzDeal(originalPepDate, duizhangReqTime, deductStlmDate, bankId, DataStatus.no_auto, instInfo.getId().getInstId(), instInfo.getId().getInstType());
					}else{
						flag = BankInstDzUtil.bankInstOnlineDzDeal(originalPepDate, duizhangReqTime, deductStlmDate, bankId,DataStatus.no_auto,dz_flag,type.getInstId(),type.getInstType());
					}
					log.info("手动对账结束");
				}catch(Exception e){
					log.error("手动对账失败"+e);
				}
			}else{
				log.info("网关号----"+bankId+"-----对应的银行机构为空");
			}
			
			
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return flag;
	}
	
	public static boolean errorDuizhangHandle(ManualHandlerErrorDzType type) throws Exception{
		boolean flag = false;
		try {
			String inst_id = type.getInstId()+"";
			log.info("得到机构号："+inst_id);
			String deductStlmDate_ = type.getSummaryDate();
			log.info("得到原始交易时间"+deductStlmDate_);
			//处理对账时间
			Calendar calendar = Calendar.getInstance();//系统当前时间
			Date date = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").parse(deductStlmDate_);
			calendar.setTime(date);
			String originalPepDate = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			String duizhangReqTime = DYDataUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
			
			log.info("对账处理类:"+type.getDzHandlerClass());
			int instId = type.getInstId();
			
			InstInfoPK instInfoPK = new InstInfoPK(instId,type.getInstType());
			
			InstInfo instInfo = Backstage.getInstance().getInstInfo(instInfoPK);
			try{
				Class<?> c = Class.forName(type.getDzHandlerClass().trim());
				TradeDzHandler t = (TradeDzHandler)c.newInstance();
				log.info("手动差错对账开始 ");
				t.tradeErrorDzDeal(originalPepDate, duizhangReqTime, instId,DataStatus.no_auto);
				log.info("手动差错对账结束");
				flag = true;
				executeNodeDAO.updateExecuteNodeStatus("error_handle", instId, originalPepDate, "1",instInfo.getName(),instInfoPK.getInstType());
			}catch(Exception e){
				if(e instanceof DuizhangNotFoundException){
					log.info("差错对账文件数据表中不存在数据，未执行差错对账操作");
					executeNodeDAO.updateExecuteNodeStatus("error_handle", instId, originalPepDate, DataStatus.no_dz_data,instInfo.getName(),instInfoPK.getInstType());
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfo,MailSendInfoUtil.NO_ERROR_DZ_DATA);
				}else{
					flag = false;
					log.error("手动差错对账抛出异常:"+e);
					executeNodeDAO.updateExecuteNodeStatus("error_handle", instId, originalPepDate, "2",instInfo.getName(),instInfoPK.getInstType());
					MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.DZ_ERROR, emailPoliceDAO, originalPepDate, instInfo,MailSendInfoUtil.EXCUTE_ERROR_DZ_FAIL);
				}
				throw e;
			}
			
		} catch (ParseException e) {
			log.error(e);
			throw e;
		}
		return flag;
	}
	
//	public static boolean reductionDataStatus(ReductionDataStatusType type) throws Exception{
//		boolean flag = false;
//		try{
//			int inst_id = type.getInstId();
//			String summaryDate = type.getSummaryDate();
//			
//			InstInfoPK instInfoPK = new InstInfoPK(inst_id,type.getInstType());
//			
//			InstInfo instInfo = Backstage.getInstance().getInstInfo(instInfoPK);
//			
//			if(instInfo.getId().getInstType() == 0){
//				flag = tradeLstDAO.reductionDataStatusType(summaryDate, instInfo);
//			}else if(instInfo.getId().getInstType() == 1){
//				flag = hlogDAO.reductionDataStatusType(summaryDate, instInfo);
//			}
//		}catch(Exception e){
//			log.error(e);
//			throw e;
//		}
//		return flag;
//	}
	
	public static boolean reductionErrorDataStatus(ReductionErrorDataStatusType type) throws Exception{
		boolean flag = false;
		int inst_id = type.getInstId();
		int inst_type = type.getInstType();
		String summaryDate = type.getSummaryDate();
		try {
			InstInfoPK instInfoPK = new InstInfoPK();
			instInfoPK.setInstId(inst_id);
			instInfoPK.setInstType(inst_type);
			InstInfo instInfo = Backstage.getInstance().getInstInfo(instInfoPK);
			if(instInfo.isWhetherOuterErrorDz()){
				flag = originalCupsLstDAO.reductionErrorDataStatusType(summaryDate);
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return flag;
	}
}
