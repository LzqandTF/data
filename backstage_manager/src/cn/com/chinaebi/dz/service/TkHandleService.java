package cn.com.chinaebi.dz.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.ChannelDzCollect;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBalance;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerFundStance;
import cn.com.chinaebi.dz.object.MerchantSettleStatistics;
import cn.com.chinaebi.dz.object.dao.InstRateDAO;
import cn.com.chinaebi.dz.object.dao.MerchantFundSettleDAO;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.util.StringPingJie;

/**
 * 融易付线上退款处理service
 * 1：退款数据影响商户余额变动 2：退款数据进入资金流水 3：退款数据进入商户T+1 4：退款数据进入对账总表
 * @author wufei
 *
 */
public class TkHandleService {
	public static TkHandleService instance;
	public static TkHandleService getInstance() {
		if (null == instance) instance = new TkHandleService();
		return instance;
	}
	
	private static final Log log = LogFactory.getLog(TkHandleService.class);
	private static cn.com.chinaebi.dz.object.dao.iface.MerBasicDAO merBasicDAO = cn.com.chinaebi.dz.object.dao.MerBasicDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerBalanceDAO merBalanceDAO = cn.com.chinaebi.dz.object.dao.MerBalanceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerFundStanceDAO merFundStanceDAO = cn.com.chinaebi.dz.object.dao.MerFundStanceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.InstRateDAO instRateDAO = InstRateDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerchantSettleStatisticsDAO merchantSettleStatisticsDAO = cn.com.chinaebi.dz.object.dao.MerchantSettleStatisticsDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerchantFundSettleDAO merchantFundSettleDAO = MerchantFundSettleDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ChannelDzCollectDAO channelDzCollectDAO = cn.com.chinaebi.dz.object.dao.ChannelDzCollectDAO.getInstance();
	
	public static boolean ryfTkDataHandle(Object object, int type) throws Exception {
		boolean flag = false;
		Object[] objRef = (Object[]) object;
		Integer gid = Integer.valueOf(objRef[28].toString());//渠道ID
		String mid = objRef[3].toString();//商户号
		String amount = String.valueOf(objRef[7]);//交易金额
		Integer sysDate = Integer.valueOf(objRef[2].toString());//商户申请退款日期
		
		String card_no = objRef[10] == null ? "" : objRef[10].toString();//卡号
		Integer sysTime = Integer.valueOf(objRef[38].toString());//交易时间
		String tesq = objRef[1].toString();//流水号
		String id = objRef[0].toString();//退款主键ID
		
		MerBalance merBalance = null;
		MerFundStance merFundStance = null;
		MerchantSettleStatistics merchantSettleStatistics = null;
		Date date = new Date();
		SimpleDateFormat sdf = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_6);
		String currentDate = sdf.format(date);
		double system_refund_fee = 0d;
		
		InstInfoPK infoPK = new InstInfoPK();
		infoPK.setInstId(gid);
		infoPK.setInstType(1);
		InstInfo instInfo = Backstage.getInstance().getInstInfo(infoPK);
		if (null != instInfo) {
			Object merObj = merBasicDAO.queryMerInfoByMerCode(mid);
			if (merObj != null) {
				Object[] obj = (Object[]) merObj;
				MerBasic merBasic = new MerBasic();
				merBasic.setId(mid);
				merBasic.setMerName(obj[0].toString());
				merBasic.setMerCategory(Integer.valueOf(obj[1].toString()));
				merBasic.setMerAbbreviation(obj[2].toString());
				merBasic.setMerState(Integer.valueOf(obj[3].toString()));
				merBasic.setMerType(Integer.valueOf(obj[4].toString()));
				Integer refundFee = Integer.valueOf(obj[6].toString());// 是否退回手续费：0不退回、1:退回
				
				double refAmt = Double.valueOf(amount)/100;//退款金额
				
				double mer_fee = 0d;//商户手续费
				mer_fee = Double.valueOf(String.valueOf(objRef[23]))/100;
				
				if (refundFee == 0) {// 不退回手续费
					mer_fee = 0;
				}
				
				String accountAmountStr = String.valueOf(PoundageCalculate.sub("0", refAmt));
				//1:经办
				if (type != 2) {
					//处理商户余额
					merBalance = merBalanceDAO.findMerBalance(mid);
					if (merBalance != null) {//该商户在商户余额表中存在，修改商户余额
						log.info("商户原有余额是：" + merBalance.getMerBalance());
						log.info("商户余额调幅是：" + accountAmountStr);
						accountAmountStr =  String.valueOf(PoundageCalculate.add(merBalance.getMerBalance(), accountAmountStr));
						log.info("商户调整后的余额是：" + accountAmountStr);
						merBalanceDAO.updateMerBalanceByMerCode(mid, accountAmountStr);
					} else {//该商户在商户余额表中不存在，向商户余额表中插入数据
						merBalance = new MerBalance();
						merBalance.setMerCode(merBasic);//商户号
						merBalance.setMerCategory(merBasic.getMerCategory());
						log.info("插入商户余额是：" + accountAmountStr);
						merBalance.setMerBalance(accountAmountStr);
						merBalance.setMerState(merBasic.getMerState());
						merBalanceDAO.addMerBalance(merBalance);
					}
					
					log.info("开始向资金流水中插入数据...");
					merFundStance = new MerFundStance();
					merFundStance.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					merFundStance.setMerCode(merBasic);
					merFundStance.setTradeTime(date);
					log.info("退款金额是：" + PoundageCalculate.sub("0",refAmt).doubleValue());
					merFundStance.setTradeAmount(PoundageCalculate.sub("0",refAmt).doubleValue());
					log.info("退款手续费是：" + PoundageCalculate.sub("0",mer_fee).doubleValue());
					merFundStance.setMerFee(PoundageCalculate.sub("0",mer_fee).doubleValue());
					log.info("变动金额是：" + PoundageCalculate.sub("0",refAmt).doubleValue());
					merFundStance.setChangeAmount(PoundageCalculate.sub("0",refAmt).doubleValue());
					merFundStance.setAccountAmount(Double.valueOf(accountAmountStr));
					merFundStance.setTradeStance(tesq);
					merFundStance.setDercStatus(2);//简短描述简单说明 1:消费(支付)、2:退款(冲正)、3:差错调整(支付)、4:差错调整(冲正)、5:结算到电银账户、6:结算到银行账户、7:手工调整
					merFundStance.setMerState(merBasic.getMerState());
					merFundStance.setMerCategory(merBasic.getMerCategory());
					merFundStance.setMerName(merBasic.getMerName());
					merFundStance.setInstId(gid);
					merFundStance.setDeductStlmDate(currentDate);
					merFundStance.setInstType(DataStatus.inst_type_1);
					merFundStance.setStanceTime(DYDataUtil.getCurrentTime());
					merFundStance.setBankId(instInfo.getBank().getId());
					merFundStanceDAO.addMerFundStance(merFundStance);
				}
				
				log.info("开始向T+1中插入数据...");
				Object[] instRateObj = instRateDAO.getInstRateType(gid, DataStatus.inst_type_1);
				Map<String, Boolean> rateMap = instRateDAO.findChanelMccRateConf(gid, DataStatus.inst_type_1);
				if(instRateObj != null && !"null".equals(instRateObj) && "".equals(instRateObj)){
					int whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
					Integer instRateType = Integer.valueOf(instRateObj[0].toString());
					if(instRateType != 2){//按MCC计算、按固定费率进入此判断
						system_refund_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,amount, card_no, gid, DataStatus.inst_type_1, mid);
					}
					//按MCC计算
					if(instRateType == 1){
						String mcc_code = PoundageCalculate.mccCodeSubstring(mid);
						if(StringUtils.isNotBlank(mcc_code)){
							boolean lw_flag = rateMap.keySet().size() == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
							if(whetherReturnFee == 0 && !lw_flag){//不退还
								system_refund_fee = 0d;
							}else if(whetherReturnFee == 1 && lw_flag){//退还
								system_refund_fee = 0d;
							}
						}
					}else if(instRateType == 3){//按固定费率
						//退货交易-不退还手续费
						if(whetherReturnFee == 0){
							system_refund_fee = 0d;
						}
					}
				}
				
				Integer deductStlmDate = Integer.valueOf(currentDate.replace("-", ""));
				if (type == 1) {
					merchantSettleStatistics = new MerchantSettleStatistics();
			    	merchantSettleStatistics.setInstId(gid);
			    	merchantSettleStatistics.setMerCode(mid);
			    	merchantSettleStatistics.setMerType(merBasic.getMerCategory());
			    	merchantSettleStatistics.setDeductStlmDate(deductStlmDate);
			    	merchantSettleStatistics.setTradeAmount("0.00");
			    	merchantSettleStatistics.setTradeCount(0);
			    	
			    	// 退款金额
			    	String refundAmount = PoundageCalculate.sub("0",String.format("%.2f", refAmt)).toString();
			    	log.info("退款金额是：" + refundAmount);
			    	merchantSettleStatistics.setRefundAmount(refundAmount);
			    	
			    	merchantSettleStatistics.setRefundCount(1);
			    	merchantSettleStatistics.setMerFee("0.00");
			    	merchantSettleStatistics.setSystemFee("0.00");
			    	
			    	// 退款商户手续费
			    	String merRefundFee = (PoundageCalculate.sub("0",String.format("%.2f", mer_fee))).toString();
			    	log.info("退回商户手续费是：" + merRefundFee);
			    	merchantSettleStatistics.setMerRefundFee(merRefundFee);
			    	
			    	merchantSettleStatistics.setSettleAmount("0.00");
			    	merchantSettleStatistics.setSystemRefundFee("0.00");
			    	merchantSettleStatistics.setWhetherJs(false);
			    	merchantSettleStatistics.setDataStatus(DataStatus.t1_ryt_tk_jb);
			    	merchantSettleStatistics.setInstType(DataStatus.inst_type_1);//渠道类型inst_type
			    	merchantSettleStatistics.setRefundZfFee(PoundageCalculate.sub("0",String.format("%.2f", system_refund_fee)).toString());
			    	log.info("退回支付手续费是：" + PoundageCalculate.sub("0",String.format("%.2f", system_refund_fee)).toString());
			    	merchantSettleStatistics.setBankId(instInfo.getBank().getId());
			    	int endDate = merchantFundSettleDAO.queryCheckEndDate(deductStlmDate, mid);
			    	endDate = endDate == 0 ? deductStlmDate : endDate;
			    	log.info("该笔退款清算日期为 : "+endDate);
			    	merchantSettleStatistics.setJsDate(endDate);
			    	merchantSettleStatisticsDAO.addMerchantSettleStatistics(merchantSettleStatistics);
				} else {
					merchantSettleStatistics = merchantSettleStatisticsDAO.queryMerchantSettleStatistics(mid, Integer.valueOf(currentDate.replace("-", "")), gid, DataStatus.inst_type_1, DataStatus.t1_ryt_tk_sh);
					if(merchantSettleStatistics == null){
						merchantSettleStatistics = new MerchantSettleStatistics();
				    	merchantSettleStatistics.setInstId(gid);
				    	merchantSettleStatistics.setMerCode(mid);
				    	merchantSettleStatistics.setMerType(merBasic.getMerCategory());
				    	merchantSettleStatistics.setDeductStlmDate(deductStlmDate);
				    	merchantSettleStatistics.setTradeAmount("0.00");
				    	merchantSettleStatistics.setTradeCount(0);
				    	
				    	// 退款金额
				    	String refundAmount = PoundageCalculate.sub("0",String.format("%.2f", refAmt)).toString();
				    	log.info("退款金额是：" + refundAmount);
				    	merchantSettleStatistics.setRefundAmount(refundAmount);
				    	
				    	merchantSettleStatistics.setRefundCount(1);
				    	merchantSettleStatistics.setMerFee("0.00");
				    	merchantSettleStatistics.setSystemFee("0.00");
				    	
				    	String merRefundFee = (PoundageCalculate.sub("0",String.format("%.2f", mer_fee))).toString();
				    	log.info("退回商户手续费是：" + merRefundFee);
				    	merchantSettleStatistics.setMerRefundFee(merRefundFee);
				    	
				    	merchantSettleStatistics.setSettleAmount("0.00");
				    	merchantSettleStatistics.setSystemRefundFee("0.00");
				    	merchantSettleStatistics.setWhetherJs(false);
				    	merchantSettleStatistics.setDataStatus(DataStatus.t1_ryt_tk_sh);
				    	merchantSettleStatistics.setInstType(DataStatus.inst_type_1);//渠道类型inst_type
				    	merchantSettleStatistics.setRefundZfFee(PoundageCalculate.sub("0",String.format("%.2f", system_refund_fee)).toString());
				    	log.info("退回支付手续费是：" + PoundageCalculate.sub("0",String.format("%.2f", system_refund_fee)).toString());
				       	merchantSettleStatistics.setBankId(instInfo.getBank().getId());
				       	merchantSettleStatistics.setJsDate(deductStlmDate);
				    	merchantSettleStatisticsDAO.addMerchantSettleStatistics(merchantSettleStatistics);
					}else{
						merchantSettleStatistics.setRefundAmount(PoundageCalculate.add(merchantSettleStatistics.getRefundAmount(),PoundageCalculate.sub("0",String.format("%.2f", refAmt)).toString()).toString());
						merchantSettleStatistics.setRefundCount(merchantSettleStatistics.getRefundCount() + 1);
						merchantSettleStatistics.setRefundZfFee(PoundageCalculate.add(merchantSettleStatistics.getSystemRefundFee(),PoundageCalculate.sub("0",String.format("%.2f", system_refund_fee))).toString());
						merchantSettleStatistics.setMerRefundFee(PoundageCalculate.add(merchantSettleStatistics.getMerRefundFee(),PoundageCalculate.sub("0",String.format("%.2f", mer_fee))).toString());
						merchantSettleStatisticsDAO.updateMerchantSettleStatistics(merchantSettleStatistics);
					}
				}
				if (type != 2) {
					log.info("开始将退款交易数据保存到对账总表中...");
					ChannelDzCollect cdc = new ChannelDzCollect();
					cdc.setId(id);
					cdc.setOutAccount(card_no);
					cdc.setTradeAmount(0-refAmt);
				//	交易结果  0:初始状态、1:待支付、2:成功、3:失败、4:请求银行失败、5:撤销、6:超时、7:未知
					cdc.setTradeResult(2);
					cdc.setReqSysStance(tesq);
					log.info("退款流水为：" + tesq);
					cdc.setReqMerCode(mid);
					cdc.setDeductSysId(gid);
					cdc.setDeductSysStance(tesq);
					cdc.setDeductMerCode(mid);
					Long tradeTime = DYDataUtil.getStringTimeyyyyMMddHHmmss(sysDate.toString(), sysTime.toString());
					tradeTime = Long.valueOf(StringPingJie.rightPad(tradeTime.toString(), 14, "0"));
					cdc.setTradeTime(tradeTime);
					cdc.setDeductSysTime(tradeTime);
					cdc.setDeductStlmDate(Integer.valueOf(currentDate.replace("-", "")));
					cdc.setTradeType(9);
					cdc.setOriginalTransInfo(objRef[27] == null ? "" : objRef[27].toString());
					cdc.setBkChk(3);
					cdc.setWhetherQs((byte)1);
					cdc.setMerFee(mer_fee);
					cdc.setWhetherTk((byte)1);
					cdc.setInstType(1);
					cdc.setGate(Integer.valueOf(objRef[9].toString()));
					cdc.setSettleCode(mid);
					cdc.setInstName(instInfo.getName());
					cdc.setDyMerName(merBasic.getMerAbbreviation());
					cdc.setOid(objRef[4] == null ? "" : objRef[4].toString());
					cdc.setSysDate(sysDate);
					cdc.setBankId(instInfo.getBank().getId());
					int endDate = merchantFundSettleDAO.queryCheckEndDate(deductStlmDate, mid);
			    	endDate = endDate == 0 ? deductStlmDate : endDate;
			    	cdc.setJsDate(endDate);
					channelDzCollectDAO.saveRytTKChannelDzCollect(cdc);
				}
				flag = true;
			} else {
				log.warn("商户号mid:" + mid + "在对账系统中不存在！");
			}
		} else {
			flag = true;
			log.warn("渠道gid:" + gid + "在对账系统中不存在！返回容易付结果为true");
		}
		return flag;
	}
}
