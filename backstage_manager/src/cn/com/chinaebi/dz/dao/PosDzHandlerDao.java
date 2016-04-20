package cn.com.chinaebi.dz.dao;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.object.dao.MerBalanceDAO;
import cn.com.chinaebi.dz.object.dao.MerFundStanceDAO;
import cn.com.chinaebi.dz.object.dao.SettleMerchantMatchTableDAO;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.reload.Backstage;

public class PosDzHandlerDao {

	private static Log logger = LogFactory.getLog(PosDzHandlerDao.class);
	private static PosDzHandlerDao posDzHandlerDao = new PosDzHandlerDao();
	private cn.com.chinaebi.dz.object.dao.iface.SettleMerchantMatchTableDAO settleMerchantMatchTableDAO = SettleMerchantMatchTableDAO.getInstance();
	private cn.com.chinaebi.dz.object.dao.iface.MerBalanceDAO merBalanceDAO = MerBalanceDAO.getInstance();
	private cn.com.chinaebi.dz.object.dao.iface.MerFundStanceDAO merFundStanceDAO = MerFundStanceDAO.getInstance();
	
	public static PosDzHandlerDao getInstance(){
		return posDzHandlerDao;
	}
	
	/**
	 * 线下渠道对账保存商户资金流水修改更新商户余额
	 * @param merCode : 商户号
	 * @param tradeTime ：交易日期
	 * @param tradeStance ：交易流水
	 * @param mer_fee ：商户手续费
	 * @param tradeAmount ：交易金额
	 * @param derc_status ：数据状态 1:消费(支付)、2:退款(冲正)、3:差错调整、4:结算到电银账户
	 * @param isDeductRollBk ：是否冲正
	 * @param instId : 渠道ID
	 * @param instType ：渠道类型
	 * @param deductStlmDate ：清算日期
	 * @param bank_id ：银行网关
	 * @return boolean
	 */
	public boolean saveMerFundStanceAndupdateMerBalance(String merCode,
			Date deductSysTime,Date deductRollbkSysTime, String tradeStance, double mer_fee,
			Long tradeAmount, Integer derc_status,boolean isDeductRollBk,Integer instId,Integer instType,String deductStlmDate,int bank_id) {
		String settleMerCode = null;
		boolean flag = false;
		try {
			settleMerCode = settleMerchantMatchTableDAO.getSettleMerCode(merCode);
			if(StringUtils.isEmpty(settleMerCode)){
				settleMerCode = merCode;
			}
			//商户资金流水
			MerBasic merBasic = Backstage.getInstance().getMerBasic(settleMerCode);
			if(merBasic != null){
				MerBalance merBalance = merBalanceDAO.findMerBalance(settleMerCode);
				double change_amount = 0d;
				String balance = "0";
				double account_amount = 0d;
				double trade_amount = 0d;
				if(merBalance != null){
					balance = merBalance.getMerBalance();//商户余额
				}
				if(derc_status == DataStatus.derc_status_2){
					mer_fee = 0-mer_fee;
				}
				trade_amount = tradeAmount.doubleValue()/100;
				MerBilling merBilling = Backstage.getInstance().getMerBilling(settleMerCode);
				if(merBilling != null){
					if(merBilling.getBilWay() == 1){ //全额
						change_amount = trade_amount;
					}else if(merBilling.getBilWay() == 2){ //净额
						change_amount = trade_amount-mer_fee;//变动金额
					}else{
						change_amount = trade_amount-mer_fee;//变动金额
					}
					
					account_amount = PoundageCalculate.add(balance, change_amount);//account_amount+change_amount;//账户余额
					Date tradeTime = isDeductRollBk ? deductRollbkSysTime : deductSysTime;
					merFundStanceDAO.saveMerFundStance(settleMerCode, tradeTime, trade_amount, mer_fee, change_amount, account_amount, tradeStance, derc_status, merBasic.getMerState(), merBasic.getMerCategory(), merBasic.getMerAbbreviation(),instId,deductStlmDate,instType,bank_id);
					flag = true;
				}else{
					logger.warn(settleMerCode+" 商户结算信息不存在");
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return flag;
	}
}
