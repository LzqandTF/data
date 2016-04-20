package cn.com.chinaebi.dz.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 银行卡代付接口
 * @author wufei
 *
 */
public class MerchantFundSettleService {
	private static Log log = LogFactory.getLog(MerchantFundSettleService.class);
	private static cn.com.chinaebi.dz.object.dao.iface.MerchantFundSettleDAO merchantFundSettleDAO = cn.com.chinaebi.dz.object.dao.MerchantFundSettleDAO.getInstance();
	
	public static MerchantFundSettleService instance;
	
	public static MerchantFundSettleService getInstance(){
		if (null == instance) instance = new MerchantFundSettleService();
		return instance;
	}
	
	/**
	 * 异步接收银行卡代付接口(融易付)
	 * @param obj
	 * @return boolean
	 */
	public static boolean asnycReciveMerBilingAmtDf(Integer id,Integer df_result, String errorMsg){
		boolean issuccess = false;
		try {
			issuccess = merchantFundSettleDAO.updateMerchantFundSettleDfResult(Integer.valueOf(id), df_result, errorMsg);
			if (issuccess) {
				log.info("根据融易付异步代付结果修改商户划款状态及错误信息成功");
			} else {
				log.info("根据融易付异步代付结果修改商户划款状态及错误信息失败");
			}
		} catch (Exception e) {
			log.error("根据融易付异步代付结果修改商户划款状态及错误信息出现异常：" + e.getMessage());
		}
		return issuccess;
	}
	
}
