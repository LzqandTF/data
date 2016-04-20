package cn.com.chinaebi.dz.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.object.TimingTaskConf;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.webservice.BankInstUpdateRamType;
import cn.com.chinaebi.dz.webservice.DeductChannelOpenOrCloseType;
import cn.com.chinaebi.dz.webservice.DeductChannelUpdateRamType;
import cn.com.chinaebi.dz.webservice.MerBasicUpdateRamType;
import cn.com.chinaebi.dz.webservice.MerBillingUpdateRamType;

public class InstInfoService {
	private static final Log log = LogFactory.getLog(InstInfoService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.TimingTaskConfDAO timingTaskConfDAO = cn.com.chinaebi.dz.object.dao.TimingTaskConfDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.InstInfoDAO instInfoDAO = cn.com.chinaebi.dz.object.dao.InstInfoDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.BankInstDAO bankInstDAO = cn.com.chinaebi.dz.object.dao.BankInstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.MerBasicDAO merBasicDAO = cn.com.chinaebi.dz.object.dao.MerBasicDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerBillingDAO merBillingDAO = cn.com.chinaebi.dz.object.dao.MerBillingDAO.getInstance();
	
	public static boolean instInfoStatusHandle(DeductChannelOpenOrCloseType type) throws Exception{
		try{
			int inst_id = type.getInstId();
			int inst_type = type.getInstType();
			String handle = type.getOpenOrclose().toString();//open 开通  ;close 关闭
			
			log.info("获得接口报文参数为：渠道ID--"+inst_id+";渠道状态处理类型--"+handle);
			
			List<TimingTaskConf> list = timingTaskConfDAO.getTimingTaskConfListByChannelId(inst_id,inst_type);//根据渠道ID获得对应的定时任务配置信息
			
			for (TimingTaskConf timingTaskConf : list) {
				if("open".equals(handle)){
					if(StringUtils.isNotBlank(timingTaskConf.getAcquisitionTimeName())){
						if(Backstage.getInstance().checkJob(timingTaskConf.getAcquisitionTimeName())){
							Backstage.getInstance().updateTimingTaskConf(timingTaskConf.getId(), timingTaskConf.getAcquisitionTimeName(),timingTaskConf.getAcquisitionTime());
						}else{
							Backstage.getInstance().addTimingTaskConf(timingTaskConf.getAcquisitionTimeName(),timingTaskConf.getAcquisitionTime());
						}
					}else if(StringUtils.isNotBlank(timingTaskConf.getDzHandlerTimeName())){
						if(Backstage.getInstance().checkJob(timingTaskConf.getDzHandlerTimeName())){
							Backstage.getInstance().updateTimingTaskConf(timingTaskConf.getId(), timingTaskConf.getDzHandlerTimeName(),timingTaskConf.getDzHandlerTime());
						}else{
							Backstage.getInstance().addTimingTaskConf(timingTaskConf.getDzHandlerTimeName(),timingTaskConf.getDzHandlerTime());
						}
					}
				}else if("close".equals(handle)){
					if(StringUtils.isNotBlank(timingTaskConf.getAcquisitionTimeName())){
						if(Backstage.getInstance().checkJob(timingTaskConf.getAcquisitionTimeName())){
							Backstage.getInstance().deleteTimingTaskConf(timingTaskConf.getId(), timingTaskConf.getAcquisitionTimeName());
						}
					}else if(StringUtils.isNotBlank(timingTaskConf.getDzHandlerTimeName())){
						if(Backstage.getInstance().checkJob(timingTaskConf.getDzHandlerTimeName())){
							Backstage.getInstance().deleteTimingTaskConf(timingTaskConf.getId(), timingTaskConf.getDzHandlerTimeName());
						}
					}
				}
			}
			
			Backstage.getInstance().initTimingTaskConf();
			
			InstInfo instInfo = instInfoDAO.getInstInfoByIdInSQL(inst_id,inst_type);
			
			InstInfoPK infoPK = new InstInfoPK(inst_id,inst_type);
			
			if(instInfo != null){
				//针对指定渠道进行更改内存信息，在Backstage中添加set方法
				Backstage.getInstance().setInstInfoMap(infoPK, instInfo);
			}
			
			return true;
		}catch(Exception e){
			log.error("更新渠道定时任务信息配置失败,抛出异常:"+e);
			throw e;
		}
	}
	
	public static boolean deductChannelUpdateRam(DeductChannelUpdateRamType type) throws Exception{
		boolean flag = false;
		try{
			int instId = type.getInstId();
			int inst_type = type.getInstType();
			
			InstInfo instInfo = instInfoDAO.getInstInfoByIdInSQL(instId,inst_type);
			
			InstInfoPK infoPK = new InstInfoPK(instId,inst_type);
			
			if(instInfo != null){
				//针对指定渠道进行更改内存信息，在Backstage中添加set方法
				Backstage.getInstance().setInstInfoMap(infoPK, instInfo);
			}else{
				Backstage.getInstance().removeInstInfo(instId);
			}
			
			flag = true;
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return flag;
	}
	
	public static boolean bankInstUpdateRam(BankInstUpdateRamType type) throws Exception {
		boolean flag = false;
		try {
			int bankId = type.getBankId();
			
			BankInst bankInst = bankInstDAO.getBankInstByIdInSQL(bankId);
			if (bankInst != null) {
				Backstage.getInstance().setBankInstMap(bankInst);
			} else {
				Backstage.getInstance().removeBankInst(bankId);
			}
			flag = true;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return flag;
	}
	
	public static boolean merBasicUpdateRam(MerBasicUpdateRamType type) throws Exception {
		boolean flag = false;
		try {
			String merCode = type.getMerCode();
			
			MerBasic merBasic = merBasicDAO.getMerBasicByInSql(merCode);
			if (merBasic != null) {
				Backstage.getInstance().setMerBasicMap(merCode, merBasic);
			}
			flag = true;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return flag;
	}
	
	public static boolean merBillingUpdateRam(MerBillingUpdateRamType type) throws Exception {
		boolean flag = false;
		try {
			String merCode = type.getMerCode();
			
			MerBilling merBilling = merBillingDAO.getMerBillingByInSql(merCode);
			if (merBilling != null) {
				Backstage.getInstance().setMerBillingMap(merCode, merBilling);
			}
			flag = true;
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return flag;
	}
}
