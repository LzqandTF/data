package cn.com.chinaebi.dz.base;


import java.util.Calendar;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.service.TradeLstService;

/**
 * 获取不同扣款渠道原始交易数据
 */
public class OriginalTotalDataLst {
	private static Log log = LogFactory.getLog(OriginalTotalDataLst.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO = cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	public static synchronized  void originalTotalData(String deductDate) throws Exception{

		Map<InstInfoPK,Object> map_inst = Backstage.getInstance().getInstInfoMap();
		Calendar calendar = Calendar.getInstance();//系统当前时间
		calendar.add(Calendar.DATE, -1);
		String tradeTime =  DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		try{
			//线下交易数据汇总
			for(Map.Entry<InstInfoPK, Object> entry:map_inst.entrySet()){
				InstInfo instInfo = (InstInfo)entry.getValue();
				BankInst bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
				Integer inst_id = instInfo.getId().getInstId();
				Integer inst_type = instInfo.getId().getInstType();
				String originalDataTableName = bankInst.getOriginalDataTableName();
				Integer bank_id = bankInst.getId();
				boolean data_collect_flag = false;
				if(instInfo.isActive()){
					if(inst_type == 0){
						if(instInfo.isWhetherOuterDz()){
							data_collect_flag = TradeLstService.posDataCollectHandle(instInfo, bankInst, tradeTime);
						}else{
							data_collect_flag = TradeLstService.noDzdataCollectHanler(tradeTime, originalDataTableName, inst_id,bank_id);
						}
					}else if(inst_type == 1){
						 data_collect_flag = TradeLstService.rytDataCollectHandle(instInfo, bankInst, tradeTime);
					}
					try{
						executeNodeDAO.updateExecuteNodeStatus("trade_collect", instInfo.getId().getInstId(), tradeTime, data_collect_flag?"1":"2",instInfo.getName(),instInfo.getId().getInstType());
					}catch(Exception e){
						log.error("更新"+instInfo.getName()+"自动汇总交易数据工作流节点抛出异常："+e);
					}
					
					if(!data_collect_flag){
						MailSendInfoUtil.sendEmailForInstInfoError(MailSendInfoUtil.ORIGINAL_DATA_COLLECT,emailPoliceDAO,tradeTime,instInfo,MailSendInfoUtil.DATA_ZERO_COUNT);
					}
				}
			}
		}catch(Exception e){
			log.error(e);
			MailSendInfoUtil.sendEmailForErrorWork(MailSendInfoUtil.ORIGINAL_DATA_COLLECT,emailPoliceDAO,tradeTime);
			throw e;
		}
	
	}
}
