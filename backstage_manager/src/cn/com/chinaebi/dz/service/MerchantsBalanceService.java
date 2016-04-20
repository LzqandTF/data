package cn.com.chinaebi.dz.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.webservice.RyfMerStanceSelectReqType;

public class MerchantsBalanceService {
	
	private static Log log = LogFactory.getLog(MerchantsBalanceService.class);
	private static cn.com.chinaebi.dz.object.dao.iface.MerBalanceDAO merBalanceDAO = cn.com.chinaebi.dz.object.dao.MerBalanceDAO.getInstance();
	
	public synchronized static  MerBalance  handleMerchantInfo(RyfMerStanceSelectReqType type) throws Exception{
		MerBalance balance=null;
		try{
			String mer_code=type.getMerCode();//商户号
			balance=merBalanceDAO.findMerBalance(mer_code);
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return balance;
	}

}
