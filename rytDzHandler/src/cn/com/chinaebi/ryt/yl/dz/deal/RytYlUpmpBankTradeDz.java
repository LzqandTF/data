package cn.com.chinaebi.ryt.yl.dz.deal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.base.TradeDzHandler;
import cn.com.chinaebi.dz.base.exception.DuizhangNotFoundException;
import cn.com.chinaebi.dz.base.exception.OriginalDataNotFoundException;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO;
import cn.com.chinaebi.dz.object.dao.HlogDAO;
import cn.com.chinaebi.dz.object.dao.iface.ChannelDzCollectDAO;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.util.StringPingJie;

/**
 * 银联wap网关对账
 * @author zhu.hongliang
 */
public class RytYlUpmpBankTradeDz implements TradeDzHandler {

	private Log log =LogFactory.getLog(getClass());
	private cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorDataLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO;
	private ChannelDzCollectDAO channelDzCollectDAO;
	private final String dateColumn = "deduct_stlm_date";
	
	@Override
	public void tradeDzDeal(String originalPepDate, String duizhangReqTime,
			String deductStlmDate, Boolean innertTradeHandler, Integer bank_id,
			Integer instId, Integer instType, Integer flagStatus)
			throws OriginalDataNotFoundException, DuizhangNotFoundException,
			Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLineTradeDzDeal(Integer startDate,
			Integer endDate, String deductStmlDate,
			Integer bank_id, Integer instId,
			Integer instType, Integer flagStatus)
			throws OriginalDataNotFoundException, DuizhangNotFoundException,
			Exception {
		log.info("获取原始交易数据开始日期 ："+startDate);
		log.info("获取原始交易数据结束日期 ："+endDate);
		log.info("对账文件获取日期 ： "+deductStmlDate);
		errorDataLstDAO = ErrorDataLstDAO.getInstance();
		hlogDAO = HlogDAO.getInstance();
		StringPingJie stringPingJie = StringPingJie.getInstance();
		SimpleDateFormat yyyyMMddHHmmss = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_2);
		SimpleDateFormat yyyyMMdd = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_3);
		try {
			deductStmlDate = deductStmlDate.replaceAll("-", "");
			BankInst bankInst = Backstage.getInstance().getBankInst(bank_id);
			if(bankInst != null){
				String dzDataTableName = bankInst.getDzDataTableName();
				String originalTableName = bankInst.getOriginalDataTableName();
				instType = bankInst.getBankType();
				String bankName = bankInst.getBankName();
				//查询对账单不是退款未对账数据
				List<Object> duizhangList = hlogDAO.queryDuizhangDataList(dzDataTableName, deductStmlDate, DataStatus.no_tk, DataStatus.not_dz);
				Object[] objMap = hlogDAO.queryAllMap(originalTableName, instId, startDate, endDate);
				if(duizhangList != null && duizhangList.size() > 0){
					Map<String, Object[]> tseqMap = objMap != null ? (Map<String, Object[]>)objMap[0] : null;
					Map<String, Object[]> oidMap = objMap != null ? (Map<String, Object[]>)objMap[1] : null;
					if(objMap != null && tseqMap != null && tseqMap.keySet().size() > 0){
						/**
						 * object[0] id 主键ID
						 * object[1] tradeAmount 交易金额
						 * object[2] tradeFee 交易手续费
						 * object[3] reqSysStance 交易流水号
						 * object[4] orderId 订单号
						 * object[5] deduct_stlm_date 清算日期
						 * object[6] reqTime 交易时间
						 * object[7] outAccount 交易卡号
						 */
						for (Object object : duizhangList) {
							Object[] dzObj = (Object[])object;
							String id = dzObj[0].toString();
							String tradeAmount = dzObj[1].toString();
							String zf_file_fee = dzObj[2].toString();
							String reqSysStance = dzObj[3].toString();
							String orderId = dzObj[4] == null ? "" : dzObj[4].toString();
							String dz_deductStlmDate = dzObj[5].toString();
							//对账单数据与原交易数据匹配
							Object[] oriObj = tseqMap.get(reqSysStance);
							oriObj = oriObj == null ? tseqMap.get(orderId) : oriObj;
							if(oriObj == null && StringUtils.isNotBlank(orderId)){
								oriObj = oidMap.get(orderId);
								oriObj = oriObj == null ? oidMap.get(reqSysStance) : oriObj;
							}
							//对账数据匹配原始交易数据
							//Object[] oriObj = hlogDAO.queryOriginalData(reqSysStance, orderId, originalTableName,instId, DataStatus.flag_2);
							if(oriObj != null){
								/*
								 *  Object[0] : sys_date int
								 *	Object[1] : sys_time int
								 *	Object[2] : tstat    int
								 *	Object[3] : tseq     int
								 *	Object[4] : card_no  String
								 *	Object[5] : amount   int
								 *	Object[6] : mer_fee  Double
								 *	Object[7] : mid      String
								 *	Object[8] : gid      int
								 *	Object[9] : gate     int
								 *	Object[10] : oid     String
								 *  Object[11] : deduct_stlm_date int
								 */
								Integer sys_date = Integer.valueOf(oriObj[0].toString());
								Integer sys_time = Integer.valueOf(oriObj[1].toString());
								Integer tstat = Integer.valueOf(oriObj[2].toString());//交易状态
								String tseq = oriObj[3].toString(); //tseq
								String card_no = oriObj[4] == null ? "" : oriObj[4].toString();
								String amount = oriObj[5].toString();
								double mer_fee = oriObj[6] == null ? 0d : Double.valueOf(oriObj[6].toString());
								String mid = oriObj[7].toString();
								Integer gid = Integer.valueOf(oriObj[8].toString());
								Integer gate = Integer.valueOf(oriObj[9].toString());
								String oid = oriObj[10] == null ? "" : oriObj[10].toString();
								dz_deductStlmDate = Integer.valueOf(oriObj[11].toString()) == 0 ? dz_deductStlmDate : oriObj[11].toString();
								//银行手续费计算
								double zf_fee = PoundageCalculate.getOnlineTradeMerFeeUtil(amount, card_no, gid, instType, mid, zf_file_fee);
								
								//对账状态修改
								if(tstat == 2){ //成功
									double ori_amount = Double.valueOf(amount)/100;
									double dz_amount = Double.valueOf(tradeAmount);
									if(ori_amount == dz_amount){
										log.info(stringPingJie.getStringPingJie("流水 ： ",tseq," 开始对账 - 对账成功 - 银行手续费 = ",zf_fee));
										hlogDAO.updateOriginalDataBkchk(originalTableName, tseq, DataStatus.dz_success, DataStatus.clean, zf_fee, zf_file_fee,dz_deductStlmDate);
									}else{
										log.info(stringPingJie.getStringPingJie("流水 ： ",tseq," 开始对账 - 对账失败(金额匹配错误-原始交易差错)-银行手续费 = ",zf_fee));
										hlogDAO.updateOriginalDataBkchk(originalTableName, tseq, DataStatus.dz_error, DataStatus.no_clean, zf_fee, zf_file_fee,dz_deductStlmDate);
										Date deduct_stlm_date = yyyyMMdd.parse(dz_deductStlmDate);
										errorDataLstDAO.saveRytOriginalTradeError(id,DataStatus.dz_error,sys_date, sys_time,deduct_stlm_date, tstat, tseq,oid, card_no, Long.valueOf(amount), mer_fee,zf_fee,zf_file_fee, gid,instType,DataStatus.amt_error,mid,bank_id);
									}
								}else {//失败交易-进入差错
									log.info(stringPingJie.getStringPingJie("流水 ： ",tseq," 开始对账 - 对账失败(交易数据失败-对账文件交易存在) - 银行手续费 = ",zf_fee));
									String deductstmldate = dz_deductStlmDate;
									Date deduct_stlm_date = yyyyMMdd.parse(deductstmldate);
									if(mer_fee == 0){
										mer_fee = PoundageCalculate.getTradeMerFee(Integer.valueOf(amount),gate,mid,gid);
									}
									hlogDAO.updateOriginalDataBkchk(originalTableName, tseq, DataStatus.dz_error, DataStatus.no_clean, zf_fee, zf_file_fee,dz_deductStlmDate);
									errorDataLstDAO.saveRytOriginalTradeError(id,DataStatus.dz_error,sys_date, sys_time,deduct_stlm_date, tstat, tseq,oid, card_no, Long.valueOf(amount), mer_fee,zf_fee,zf_file_fee, gid,instType,DataStatus.long_status,mid,bank_id);
								}
								hlogDAO.updateDuizhangDataBkchk(dzDataTableName, id, DataStatus.dz_success);
							}else{
								log.warn(stringPingJie.getStringPingJie("流水： ",reqSysStance," or 订单号：",orderId," 未匹配到原笔交易数据"));
							}
						}
						
						Integer js_deductStlmDate = Integer.valueOf(deductStmlDate);
						js_deductStlmDate = js_deductStlmDate - 1;
						log.info(stringPingJie.getStringPingJie("银行网关",bankInst.getBankName(),"日期 ",js_deductStlmDate,"未对账已清算数据查询"));
						List<Object> sjzfs = hlogDAO.queryNoDzJsOriginalData(originalTableName,js_deductStlmDate, DataStatus.not_dz,DataStatus.clean);
						if(sjzfs != null && sjzfs.size() > 0){
							//sys_date,sys_time,tstat,tseq,oid,card_no,amount,mer_fee,zf_fee,zf_file_fee,mid,gid
							for (Object object : sjzfs) {
								Object[] jsObj = (Object[])object;
								int sys_date = Integer.valueOf(jsObj[0].toString());
								int sys_time = Integer.valueOf(jsObj[1].toString());
								int tstat = Integer.valueOf(jsObj[2].toString());
								String tseq = jsObj[3].toString();
								String oid = jsObj[4].toString();
								String card_no = jsObj[5] == null ? "" : jsObj[5].toString();
								Long amount = Long.valueOf(jsObj[6].toString());
								Double mer_fee = jsObj[7] == null ? 0d : Double.valueOf(jsObj[7].toString());
								Double zf_fee = jsObj[8] == null ? 0d : Double.valueOf(jsObj[8].toString());
								String zf_file_fee = jsObj[9] == null ? "0" : jsObj[9].toString();
								String mid = jsObj[10].toString();
								int gid = Integer.valueOf(jsObj[11].toString());
								String date = DYDataUtil.getRyfDateHandler(sys_date,sys_time);
								Date qsdate = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_1).parse(date);
								errorDataLstDAO.saveRytOriginalTradeQsNoDzError(sys_date, sys_time, qsdate, tstat, tseq, 
										oid, card_no, amount, mer_fee, zf_fee, zf_file_fee, gid, instType, DataStatus.short_no_dz_status, mid, bank_id);
							}
						}else{
							log.info(stringPingJie.getStringPingJie("银行网关",bankInst.getBankName(),"日期 ",js_deductStlmDate,"日期无未对账已清算数据"));
						}
						
						log.info(stringPingJie.getStringPingJie("银行网关",bankInst.getBankName(),"日期",deductStmlDate,"23点未对账交易成功T+1日数据统计开始"));
						List<Object> listObject = hlogDAO.queryNoDzJsIsSuccessOriginalData(originalTableName,Integer.valueOf(deductStmlDate), DataStatus.not_dz,DataStatus.no_clean,DataStatus.tstat_succes);
						//sys_time,tseq
						for (Object object : listObject) {
							Object[] wObj = (Object[])object;
							Integer sysTime = Integer.valueOf(wObj[0].toString());
							int hour = DYDataUtil.getHour(sysTime);
							int min = DYDataUtil.getMin(sysTime);
							int second = DYDataUtil.getSecond(sysTime);
							if(hour >= 23 && min >= 0 && second >= 0){
								String tseq = wObj[1].toString();
								log.info(stringPingJie.getStringPingJie("银行网关",bankInst.getBankName(),"日期",deductStmlDate," : ",tseq," 标记为已清算"));
								hlogDAO.updateBkChkOrWhetherQs(originalTableName, tseq, DataStatus.clean,Integer.valueOf(deductStmlDate));
							}
						}
						log.info(stringPingJie.getStringPingJie("银行网关",bankInst.getBankName(),"日期",deductStmlDate,"23点未对账交易成功T+1日数据统计结束"));
						
						Map<String, Object> mapMerBasic = Backstage.getInstance().getMerBasicList();
						Map<InstInfoPK, Object> instInfoMap = Backstage.getInstance().getInstInfoMap();
						Set<InstInfo> instInfoSet = bankInst.getInstInfos();
						for (InstInfo instInfo : instInfoSet) {
							Integer deductStlmDate_ = Integer.valueOf(deductStmlDate);
							log.info(stringPingJie.getStringPingJie(instInfo.getName(),deductStlmDate_,"日期对账成功T+1日数据统计开始"));
							Integer _instId = instInfo.getId().getInstId();
							Integer _instType = instInfo.getId().getInstType();
							hlogDAO.saveMerchantSettleStatistics(originalTableName, dateColumn, _instId, deductStlmDate_, _instType,mapMerBasic,bank_id);
							log.info(stringPingJie.getStringPingJie(instInfo.getName(),deductStlmDate_,"日期对账成功T+1日数据统计结束"));
						}
						
						log.info(stringPingJie.getStringPingJie("开始汇总银行网关",bankName,"对账之后数据汇总"));
						channelDzCollectDAO = cn.com.chinaebi.dz.object.dao.ChannelDzCollectDAO.getInstance();
						int count = channelDzCollectDAO.saveRytChannelDzCollect(originalTableName, Integer.valueOf(deductStmlDate), mapMerBasic,instInfoMap);
						log.info(stringPingJie.getStringPingJie("银行网关",bankName,"对账之后汇总条数",count));
						log.info(stringPingJie.getStringPingJie("结束汇总银行网关",bankName,"对账之后数据汇总"));
						
						log.info(stringPingJie.getStringPingJie("核查银行网关",bankName,"是否存在未对账数据-进入差错"));
						List<Object> duizhangNotDzList = hlogDAO.queryDuizhangDataList(dzDataTableName, deductStmlDate, DataStatus.no_tk, DataStatus.not_dz);
						if(duizhangNotDzList != null && duizhangNotDzList.size() > 0){
							//id,tradeAmount,tradeFee,reqSysStance,orderId,deduct_stlm_date,reqTime,outAccount,dz_file_name
							errorDataLstDAO.saveRytDuizhangFileError(0, "",instType,bank_id,duizhangNotDzList,yyyyMMddHHmmss,yyyyMMdd);
						}else{
							log.info(stringPingJie.getStringPingJie("核查银行网关",bankName,"没有存在未对账数据-无需进入差错"));
						}
					}else{
						log.warn(stringPingJie.getStringPingJie("获取银行网关:",bankName,"开始日期:",startDate,",结束日期:",endDate,",没有原始交易数据"));
					}
				}else{
					log.warn(stringPingJie.getStringPingJie("银行网关",bankName," 日期 ：",deductStmlDate," 未查询到未对账对账单数据"));
				}
			}else{
				log.error(stringPingJie.getStringPingJie("网关ID ：",bank_id," 获取失败,不能为空"));
			}
		} catch (Exception e) {
			log.error("对账错误:"+e);
			throw e;
		}
	}

	@Override
	public void tradeErrorDzDeal(String originalPepDate,
			String duizhangBeijingReqTime, Integer instId, Integer flagStatus)
			throws OriginalDataNotFoundException, DuizhangNotFoundException,
			Exception {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		String deductStmlDate = "2016-01-01";
		try {
			System.out.println(DYDataUtil.getSubDate(deductStmlDate,DYDataUtil.DATE_FORMAT_6,3));
			
			
			deductStmlDate = "20151204";
			System.out.println(Integer.valueOf(deductStmlDate) - 3);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
