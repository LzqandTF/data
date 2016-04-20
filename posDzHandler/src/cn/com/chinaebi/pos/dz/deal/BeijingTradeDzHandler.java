package cn.com.chinaebi.pos.dz.deal;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.base.TradeDzHandler;
import cn.com.chinaebi.dz.base.exception.DuizhangNotFoundException;
import cn.com.chinaebi.dz.base.exception.OriginalDataNotFoundException;
import cn.com.chinaebi.dz.object.DuizhangBeijingbankLst;
import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.ExecuteNode;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.OriginalBeijingbankLst;
import cn.com.chinaebi.dz.object.RiqieBeijingbankLst;
import cn.com.chinaebi.dz.object.dao.DuizhangBeijingbankLstDAO;
import cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO;
import cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO;
import cn.com.chinaebi.dz.object.dao.InstRateDAO;
import cn.com.chinaebi.dz.object.dao.OriginalBeijingbankLstDAO;
import cn.com.chinaebi.dz.object.dao.RiqieBeijingbankLstDAO;
import cn.com.chinaebi.dz.object.dao.SettleMerchantConfigDAO;
import cn.com.chinaebi.dz.object.dao.SettleMerchantMatchTableDAO;
import cn.com.chinaebi.dz.object.dao.TradeLstDAO;
import cn.com.chinaebi.dz.object.dao.iface.ChannelDzCollectDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.StringPingJie;

/**
 * 
 * 北京银行交易对账处理类
 * @author zhu.hongliang
 * 
 */
public class BeijingTradeDzHandler implements TradeDzHandler {

	private Log log = LogFactory.getLog(getClass());
	
	private cn.com.chinaebi.dz.object.dao.iface.OriginalBeijingbankLstDAO originalBeijingbankLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.DuizhangBeijingbankLstDAO duizhangBeijingbankLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.RiqieBeijingbankLstDAO riqieBeijingbankLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorDataLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.InstRateDAO instRateDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO;
	private final Integer inst_type = DataStatus.inst_type_0;
	private final String columnName = "trade_id";
	private final Integer whether_flag = 1;
	private ChannelDzCollectDAO channelDzCollectDAO;
	
	@Override
	public synchronized void tradeDzDeal(String originalPepDate,String duizhangBeijingReqTime,String deductStlmDate,Boolean innertTradeHandler,
			Integer bank_id,Integer instId,Integer instType,Integer flagStatus)
			throws OriginalDataNotFoundException,DuizhangNotFoundException,Exception{
		log.info("北京银行获取原始交易数据日期:"+originalPepDate);
		log.info("北京银行获取对账文件数据日期:"+duizhangBeijingReqTime);
		log.info("北京银行日切数据日期: "+deductStlmDate);
		SimpleDateFormat dateFormat = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_6);
		originalBeijingbankLstDAO = OriginalBeijingbankLstDAO.getInstance();
		duizhangBeijingbankLstDAO = DuizhangBeijingbankLstDAO.getInstance();
		riqieBeijingbankLstDAO = RiqieBeijingbankLstDAO.getInstance();
		errorDataLstDAO = ErrorDataLstDAO.getInstance();
		instRateDAO = InstRateDAO.getInstance();
		executeNodeDAO = ExecuteNodeDAO.getInstance();
		tradeLstDAO = TradeLstDAO.getInstance();
		StringPingJie stringPingJie = StringPingJie.getInstance();
		Map<String, String> beijingDz_map = duizhangBeijingbankLstDAO.findDzFileData(duizhangBeijingReqTime);
		//if(beijingDz_map != null && beijingDz_map.keySet() != null && beijingDz_map.keySet().size() > 0){
			Double zf_fee = 0d;
			Integer whetherReturnFee = 0;
			Integer instRateType = 0;
			Object[] instRateObj = instRateDAO.getInstRateType(instId, inst_type);
			Map<String, Boolean> rateMap = instRateDAO.findChanelMccRateConf(instId, inst_type);
			int rateMapSize = rateMap == null ? 0 : rateMap.keySet().size();
			InstInfoPK infoPK = new InstInfoPK();
			infoPK.setInstId(instId);
			infoPK.setInstType(0);
			InstInfo instInfo = Backstage.getInstance().getInstInfo(infoPK);
			String originalDataTableName = instInfo.getBank().getOriginalDataTableName();
			String riqieOriginalTableName = instInfo.getBank().getRiqieOriginalTableName();
			String dzDataTableName = instInfo.getBank().getDzDataTableName();
			try {
				List<RiqieBeijingbankLst> listRiqieBeijingbankLsts = riqieBeijingbankLstDAO.findRiqieBeijingbankLst(deductStlmDate);
				if(listRiqieBeijingbankLsts != null && listRiqieBeijingbankLsts.size() > 0){
					log.info("开始处理电银北京银行日切数据对账...");
					//逐笔勾对日切数据
					for (RiqieBeijingbankLst riqieBeijingbankLst:listRiqieBeijingbankLsts) {
						whetherReturnFee = 0;
						instRateType = 0;
						zf_fee = 0d;
						
						//计算银行手续费
						if(instRateObj != null){
							whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
							instRateType = Integer.valueOf(instRateObj[0].toString());
							//按MCC、按固定费率进入此判断
							if(instRateType != 2){
								zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(riqieBeijingbankLst.getTradeAmount()), riqieBeijingbankLst.getOutAccount(), riqieBeijingbankLst.getDeductSysId(), inst_type, riqieBeijingbankLst.getDeductMerCode());
							}
							//按MCC计算
							if(instRateType == 1){
								String mcc_code = PoundageCalculate.mccCodeSubstring(riqieBeijingbankLst.getDeductMerCode());
								if(StringUtils.isNotBlank(mcc_code)){
									boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
									if(riqieBeijingbankLst.getTrademsgType() == 20){//退货交易
										if(whetherReturnFee == 0 && !lw_flag){//不退还
											zf_fee = 0d;
										}else if(whetherReturnFee == 1 && lw_flag){//退还
											zf_fee = 0d;
										}
									}
								}
							}else if(instRateType == 3){//按固定费率
								//退货交易-不退还手续费
								if(riqieBeijingbankLst.getTrademsgType() != null && riqieBeijingbankLst.getTrademsgType() == 20 && whetherReturnFee == 0){
									zf_fee = 0d;
								}
							}
						}
						
						//成功
						if(((StringUtils.equals(riqieBeijingbankLst.getDeductSysResponse(), "00") && riqieBeijingbankLst.getDeductSysResponse() != null)||
								(StringUtils.equals(riqieBeijingbankLst.getDeductRollBkResponse(), "00") && riqieBeijingbankLst.getDeductRollBkResponse() != null))
								&& riqieBeijingbankLst.getTrademsgType() != null){
							//查询对账文件中是否存在
							String zf_file_fee = beijingDz_map.get(riqieBeijingbankLst.getDeductSysStance());
//							String zf_file_fee = duizhangBeijingbankLstDAO.findDzFileData(riqieBeijingbankLst,oriObject, duizObject,instId,DataStatus.riqie,duizhangBeijingReqTime);
							if(StringUtils.isNotBlank(zf_file_fee)){
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("成功交易、流水: ",riqieBeijingbankLst.getDeductSysStance()," 对账成功,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if(riqieBeijingbankLst.getTrademsgType() == 2 &&
										!riqieBeijingbankLst.isDeductRollBk()){//消费
									riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalBeijingbankLstDAO.updateCleanRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改计算手续费
									riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
								}else if(riqieBeijingbankLst.getTrademsgType() == 20){//退货
									riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalBeijingbankLstDAO.updateCleanRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改计算手续费
									riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
								}else if(riqieBeijingbankLst.getTrademsgType() == 18 && !riqieBeijingbankLst.isDeductRollBk()){//撤销
									//修改当前这笔交易需要清算
									riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalBeijingbankLstDAO.updateCleanRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改计算手续费
									riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									
									duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
								}else{//冲正
									
									//核对原笔对账结果
									OriginalBeijingbankLst originalBeijingbankLst = originalBeijingbankLstDAO.queryWhetherDzSuccess(riqieBeijingbankLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalBeijingbankLst != null){
										if(originalBeijingbankLst.getBkChk() == 1){//对账成功
											//当前这笔需要清算
											riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalBeijingbankLstDAO.updateCleanRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
										}else if(originalBeijingbankLst.getBkChk() == 2){//对账失败
											
											//修改冲正不需要清算--对账失败
											riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											
											//冲正进入差错
											riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
											riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
											riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
											riqieBeijingbankLst.setZfFee(zf_fee);
											riqieBeijingbankLst.setZfFileFee(zf_file_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
										}else{
											//当前这笔需要清算
											riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalBeijingbankLstDAO.updateCleanRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
										}
									}else{
										//当前这笔需要清算
										riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
										originalBeijingbankLstDAO.updateCleanRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
										//修改计算手续费
										riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
										originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
									}
									duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangBeijingReqTime);
								}
							}else{//成功交易-对账单不存在-未对账
								log.info(stringPingJie.getStringPingJie("成功交易、流水: ",riqieBeijingbankLst.getDeductSysStance()," 对账单不存在-未对账,支付手续费:",zf_fee));
								if(riqieBeijingbankLst.isDeductRollBk()){
									riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_clean,DataStatus.not_dz,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
									originalBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_clean,DataStatus.not_dz,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
								}else{
									riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean,DataStatus.not_dz,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean,DataStatus.not_dz,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
								}
							}
						}//超时
						else if(((riqieBeijingbankLst.getDeductResult() != null && riqieBeijingbankLst.getDeductResult() == 1) || 
								(riqieBeijingbankLst.getDeductRollBkResult() != null && riqieBeijingbankLst.getDeductRollBkResult() == 1))
								&& riqieBeijingbankLst.getTrademsgType() != null){
							//查询对账文件中是否存在
							//String zf_file_fee = duizhangBeijingbankLstDAO.findDzFileData(riqieBeijingbankLst,oriObject, duizObject,instId,DataStatus.riqie,duizhangBeijingReqTime);
							String zf_file_fee = beijingDz_map.get(riqieBeijingbankLst.getDeductSysStance());
							if(StringUtils.isNotBlank(zf_file_fee)){//存在
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieBeijingbankLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieBeijingbankLst.getTrademsgType() == 2) &&
										!riqieBeijingbankLst.isDeductRollBk()){//消费
									riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.long_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.long_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改计算手续费
									riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									
									riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
									riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
									riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
									riqieBeijingbankLst.setZfFileFee(zf_file_fee);
									riqieBeijingbankLst.setZfFee(zf_fee);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
									duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
								}else if(riqieBeijingbankLst.getTrademsgType() == 20){//退货
									
									riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
									originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
									//修改计算手续费
									riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									
									riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
									riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
									riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
									riqieBeijingbankLst.setWhetherTk(DataStatus.tk);
									riqieBeijingbankLst.setZfFileFee(zf_file_fee);
									riqieBeijingbankLst.setZfFee(zf_fee);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
								
									duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
								}else if(riqieBeijingbankLst.getTrademsgType() == 18 && !riqieBeijingbankLst.isDeductRollBk()){//撤销
									
									riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
									originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
									//修改计算手续费
									riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
									
									riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
									riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
									riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
									riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
									riqieBeijingbankLst.setZfFileFee(zf_file_fee);
									riqieBeijingbankLst.setZfFee(zf_fee);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
									
									duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
								}else{//冲正
									
									//核对原笔对账结果
									OriginalBeijingbankLst originalBeijingbankLst = originalBeijingbankLstDAO.queryWhetherDzSuccess(riqieBeijingbankLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalBeijingbankLst != null){
										if(originalBeijingbankLst.getBkChk() == 1){ //对账成功
											//当前这笔需要清算
											riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalBeijingbankLstDAO.updateCleanRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											
											//修改计算手续费
											riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											
											if(!riqieBeijingbankLst.isWhetherAccessStance()){
												
												boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieBeijingbankLst.getTradeAmount().toString(), riqieBeijingbankLst.getReqMerCode(), instInfo.getGate(), riqieBeijingbankLst.getDeductSysTime(), 
														riqieBeijingbankLst.getDeductRollbkSysTime(), riqieBeijingbankLst.getDeductSysStance(), riqieBeijingbankLst.isDeductRollBk(), 
														instId, inst_type, deductStlmDate, bank_id, riqieBeijingbankLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
												if(update_flag){
													log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieBeijingbankLst.getDeductSysStance()," 对账成功-进入资金流水"));
												}
											}
										}else if(originalBeijingbankLst.getBkChk() == 2){//对账失败
											
											//冲正交易进入差错
											riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											
											riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
											riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
											riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
											riqieBeijingbankLst.setZfFileFee(zf_file_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
										}else{
											//冲正交易进入差错
											riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											
											riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
											riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
											riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
											riqieBeijingbankLst.setZfFileFee(zf_file_fee);
											riqieBeijingbankLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
										}
									}else{
										//冲正交易进入差错
										riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
										originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
										//修改计算手续费
										riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
										originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
										
										riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
										riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
										riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
										riqieBeijingbankLst.setZfFileFee(zf_file_fee);
										riqieBeijingbankLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
									}
									
									duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangBeijingReqTime);
								}
							}else{//不存在
								log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieBeijingbankLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
								if((riqieBeijingbankLst.getTrademsgType() == 2 || riqieBeijingbankLst.getTrademsgType() == 18
										|| riqieBeijingbankLst.getTrademsgType() == 20 || riqieBeijingbankLst.getTrademsgType() == 56 || riqieBeijingbankLst.getTrademsgType() == 58) 
										&&  !riqieBeijingbankLst.isDeductRollBk()){
									riqieBeijingbankLstDAO.updateNoNeedHandle(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieBeijingbankLst.getTrademsgType(), deductStlmDate);
									originalBeijingbankLstDAO.updateNoNeedHandle(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieBeijingbankLst.getTrademsgType(), deductStlmDate);
								}else{
									riqieBeijingbankLstDAO.updateNoNeedHandle(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieBeijingbankLst.getTrademsgType(), deductStlmDate);
									originalBeijingbankLstDAO.updateNoNeedHandle(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieBeijingbankLst.getTrademsgType(), deductStlmDate);
								}
							}
						}//失败
						else{
							if(riqieBeijingbankLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangBeijingbankLstDAO.findDzFileData(riqieBeijingbankLst,oriObject, duizObject,instId,DataStatus.riqie,duizhangBeijingReqTime);
								String zf_file_fee = beijingDz_map.get(riqieBeijingbankLst.getDeductSysStance());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieBeijingbankLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
									if((riqieBeijingbankLst.getTrademsgType() == 2) &&
											!riqieBeijingbankLst.isDeductRollBk()){//消费
										riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.long_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.long_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										//修改计算手续费
										riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
										originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
										
										riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.long_status);
										riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
										riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
										riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
										riqieBeijingbankLst.setZfFileFee(zf_file_fee);
										riqieBeijingbankLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
										duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else if(riqieBeijingbankLst.getTrademsgType() == 20){//退货
										riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										//修改计算手续费
										riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.tk, zf_fee,zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
										originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
										
										riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
										riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
										riqieBeijingbankLst.setWhetherTk(DataStatus.tk);
										riqieBeijingbankLst.setZfFileFee(zf_file_fee);
										riqieBeijingbankLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
										
										duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else if(riqieBeijingbankLst.getTrademsgType() == 18 && !riqieBeijingbankLst.isDeductRollBk()){//撤销
										
										riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										//修改计算手续费
										riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
										originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.no_deductRollBk,deductStlmDate,whetherReturnFee);
										
										riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
										riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
										riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
										riqieBeijingbankLst.setZfFileFee(zf_file_fee);
										riqieBeijingbankLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
										
										duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else{//冲正
										
										//核对原笔对账结果
										OriginalBeijingbankLst originalBeijingbankLst = originalBeijingbankLstDAO.queryWhetherDzSuccess(riqieBeijingbankLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
										if(originalBeijingbankLst != null){
											if(originalBeijingbankLst.getBkChk() == 1){ //对账成功
												//当前这笔需要清算
												riqieBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												originalBeijingbankLstDAO.updateCleanRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												//修改计算手续费
												riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
												originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
												
												if(!riqieBeijingbankLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieBeijingbankLst.getTradeAmount().toString(), riqieBeijingbankLst.getReqMerCode(), instInfo.getGate(), riqieBeijingbankLst.getDeductSysTime(), 
															riqieBeijingbankLst.getDeductRollbkSysTime(), riqieBeijingbankLst.getDeductSysStance(), riqieBeijingbankLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, riqieBeijingbankLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieBeijingbankLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
												
											}else if(originalBeijingbankLst.getBkChk() == 2){//对账失败
												
												//冲正交易进入差错
												riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												//修改计算手续费
												riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
												originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
												
												riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
												riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
												riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
												riqieBeijingbankLst.setZfFileFee(zf_file_fee);
												riqieBeijingbankLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
											}else{
												//冲正交易进入差错
												riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												//修改计算手续费
												riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
												originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
												
												riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
												riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
												riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
												riqieBeijingbankLst.setZfFileFee(zf_file_fee);
												riqieBeijingbankLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
											}
										}else{
											//冲正交易进入差错
											riqieBeijingbankLstDAO.updateCleanAndError(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalBeijingbankLstDAO.updateCleanAndErrorRiqie(riqieBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqieBeijingbankLstDAO.updateSettleInfo(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											originalBeijingbankLstDAO.updateSettleInfoRiqie(riqieBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieBeijingbankLst.getTrademsgType(), DataStatus.deductRollBk,deductStlmDate,whetherReturnFee);
											
											riqieBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieBeijingbankLst.setBkChk(DataStatus.dz_error);
											riqieBeijingbankLst.setWhetherQs(DataStatus.no_clean);
											riqieBeijingbankLst.setWhetherTk(DataStatus.no_tk);
											riqieBeijingbankLst.setZfFileFee(zf_file_fee);
											riqieBeijingbankLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieBeijingbankLst));
										}
										
										duizhangBeijingbankLstDAO.updateClean(riqieBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangBeijingReqTime);
									}
								}else{//不存在
									log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieBeijingbankLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
									if((riqieBeijingbankLst.getTrademsgType() == 2 || riqieBeijingbankLst.getTrademsgType() == 18
											|| riqieBeijingbankLst.getTrademsgType() == 20 || riqieBeijingbankLst.getTrademsgType() == 56 || riqieBeijingbankLst.getTrademsgType() == 58) 
											&&  !riqieBeijingbankLst.isDeductRollBk()){
										riqieBeijingbankLstDAO.updateNoNeedHandle(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieBeijingbankLst.getTrademsgType(), deductStlmDate);
										originalBeijingbankLstDAO.updateNoNeedHandle(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieBeijingbankLst.getTrademsgType(), deductStlmDate);
									}else{
										riqieBeijingbankLstDAO.updateNoNeedHandle(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieBeijingbankLst.getTrademsgType(), deductStlmDate);
										originalBeijingbankLstDAO.updateNoNeedHandle(riqieBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieBeijingbankLst.getTrademsgType(), deductStlmDate);
									}
								}
							}else{
								log.error("riqieBeijingbankLst.getTrademsgType() 为空不");
							}
						}
					}
				}else{
					log.error("没有获取到  "+deductStlmDate+"  日期的北京银行原始交易日切数据");
				}
					
			} catch (Exception e) {
				log.error("北京银行日切对账错误: "+e);
			}
		
			try {
				List<OriginalBeijingbankLst> listBeijingbankLsts =  originalBeijingbankLstDAO.findOriginalBeijingbankLst(originalPepDate);
				if(listBeijingbankLsts != null && listBeijingbankLsts.size() > 0){
					log.info("开始处理电银北京银行原始交易数据对账...");
					for (OriginalBeijingbankLst oriBeijingbankLst:listBeijingbankLsts) {
						zf_fee = 0d;
						whetherReturnFee = 0;
						instRateType = 0;
						String _deductStlmDate = dateFormat.format(oriBeijingbankLst.getDeductStlmDate());
						String date = DYDataUtil.getNextDateString(originalPepDate);
						if(StringUtils.equalsIgnoreCase(_deductStlmDate,date)){
							//处理日切交易数据
							log.info("北京银行日切数据流水: "+oriBeijingbankLst.getDeductSysStance()+" 日期:"+_deductStlmDate);
							originalBeijingbankLstDAO.updateDataRiqie(oriBeijingbankLst.getDeductSysStance(), DataStatus.riqie,_deductStlmDate);
							oriBeijingbankLst.setWhetherRiqie(true);
							riqieBeijingbankLstDAO.saveRiqieBeijingbankLst(new RiqieBeijingbankLst(oriBeijingbankLst),flagStatus);
						}else{
							
							//计算银行手续费
							if(instRateObj != null){
								whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
								instRateType = Integer.valueOf(instRateObj[0].toString());
								if(instRateType != 2){
									zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(oriBeijingbankLst.getTradeAmount()), oriBeijingbankLst.getOutAccount(), oriBeijingbankLst.getDeductSysId(), inst_type, oriBeijingbankLst.getDeductMerCode());
								}
								
								//按MCC计算
								if(instRateType == 1){
									String mcc_code = PoundageCalculate.mccCodeSubstring(oriBeijingbankLst.getDeductMerCode());
									if(StringUtils.isNotBlank(mcc_code)){
										boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
										if(oriBeijingbankLst.getTrademsgType() == 20){//退货交易
											if(whetherReturnFee == 0 && !lw_flag){//不退还
												zf_fee = 0d;
											}else if(whetherReturnFee == 1 && lw_flag){//退还
												zf_fee = 0d;
											}
										}
									}
								}else if(instRateType == 3){//按固定费率
									//退货交易-不退还手续费
									if(oriBeijingbankLst.getTrademsgType() != null && oriBeijingbankLst.getTrademsgType() == 20 && whetherReturnFee == 0){
										zf_fee = 0d;
									}
								}
							}
							
							//成功
							if(((StringUtils.equals(oriBeijingbankLst.getDeductSysResponse(), "00") && oriBeijingbankLst.getDeductSysResponse() != null)||
									(StringUtils.equals(oriBeijingbankLst.getDeductRollBkResponse(), "00") && oriBeijingbankLst.getDeductRollBkResponse() != null))
									&& oriBeijingbankLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangBeijingbankLstDAO.findDzFileData(oriBeijingbankLst,oriObject, duizObject,instId,DataStatus.no_riqie,duizhangBeijingReqTime);
								String zf_file_fee = beijingDz_map.get(oriBeijingbankLst.getDeductSysStance());
								if(StringUtils.isNotBlank(zf_file_fee)){
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("成功交易、流水: ",oriBeijingbankLst.getDeductSysStance()," 对账成功,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee," ,",_deductStlmDate+"->",date));
									if(oriBeijingbankLst.getTrademsgType() == 2 &&
											!oriBeijingbankLst.isDeductRollBk()){//消费
										originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
										//修改计算手续费
										originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
										duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else if(oriBeijingbankLst.getTrademsgType() == 20){//退货
										originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
										//修改计算手续费
										originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
										duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else if(oriBeijingbankLst.getTrademsgType() == 18 && !oriBeijingbankLst.isDeductRollBk()){//撤销
										//当前这笔需要清算
										originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
										//修改计算手续费
										originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
										duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else{//冲正
										
										//核对原笔对账结果
										OriginalBeijingbankLst oriBeijingbankLst2 = originalBeijingbankLstDAO.queryWhetherDzSuccess(oriBeijingbankLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
										if(oriBeijingbankLst2 != null){
											if(oriBeijingbankLst2.getBkChk() == 1){//对账成功
												//当前这笔需要清算
												originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
												//修改计算手续费
												originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
												//修改原笔需要清算 对账成功
												originalBeijingbankLstDAO.updateCleanRiqie(oriBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,DataStatus.no_deductRollBk,originalPepDate);
											}else if(oriBeijingbankLst2.getBkChk() == 2){//对账失败
												
												//修改冲正不需要清算--对账失败
												originalBeijingbankLstDAO.updateCleanAndErrorRiqie(oriBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												//修改计算手续费
												originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
												
												//冲正进入差错
												oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
												oriBeijingbankLst.setBkChk(DataStatus.dz_error);
												oriBeijingbankLst.setWhetherQs(DataStatus.no_clean);
												oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
												oriBeijingbankLst.setZfFileFee(zf_file_fee);
												oriBeijingbankLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
											}else{
												//当前这笔需要清算
												originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
												//修改计算手续费
												originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
											}
										}else{
											//当前这笔需要清算
											originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
											//修改计算手续费
											originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
										}
										
										duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangBeijingReqTime);
									}
								}else{
									log.info(stringPingJie.getStringPingJie("成功交易、流水: ",oriBeijingbankLst.getDeductSysStance()," 对账单不存在-未对账,支付手续费:",zf_fee,",",_deductStlmDate,"->",date));
									if(oriBeijingbankLst.isDeductRollBk()){
										originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.no_clean,DataStatus.not_dz,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
									}else{
										originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.no_clean,DataStatus.not_dz,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
									}
								}
							}//超时
							else if(((oriBeijingbankLst.getDeductResult() != null && oriBeijingbankLst.getDeductResult() == 1) || 
									(oriBeijingbankLst.getDeductRollBkResult() != null && oriBeijingbankLst.getDeductRollBkResult() == 1))
									&& oriBeijingbankLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangBeijingbankLstDAO.findDzFileData(oriBeijingbankLst,oriObject, duizObject,instId,DataStatus.no_riqie,duizhangBeijingReqTime);
								String zf_file_fee = beijingDz_map.get(oriBeijingbankLst.getDeductSysStance());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									
									log.info(stringPingJie.getStringPingJie("超时交易、流水: ",oriBeijingbankLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee," ,",_deductStlmDate+"->",date));
									if((oriBeijingbankLst.getTrademsgType() == 2) &&
											!oriBeijingbankLst.isDeductRollBk()){//消费
										
										originalBeijingbankLstDAO.updateCleanAndError(oriBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.long_status,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
										//修改计算手续费
										originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
										
										oriBeijingbankLst.setWhetherErroeHandle(DataStatus.long_status);
										oriBeijingbankLst.setBkChk(DataStatus.dz_error);
										oriBeijingbankLst.setWhetherQs(false);
										oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
										oriBeijingbankLst.setZfFileFee(zf_file_fee);
										oriBeijingbankLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
										duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else if(oriBeijingbankLst.getTrademsgType() == 20){//退货
										
										originalBeijingbankLstDAO.updateCleanAndError(oriBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
										//修改计算手续费
										originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
										
										oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
										oriBeijingbankLst.setBkChk(DataStatus.dz_error);
										oriBeijingbankLst.setWhetherQs(false);
										oriBeijingbankLst.setWhetherTk(DataStatus.tk);
										oriBeijingbankLst.setZfFileFee(zf_file_fee);
										oriBeijingbankLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
									
										duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else if(oriBeijingbankLst.getTrademsgType() == 18 && !oriBeijingbankLst.isDeductRollBk()){//撤销
										
										originalBeijingbankLstDAO.updateCleanAndError(oriBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
										//修改计算手续费
										originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
										
										oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
										oriBeijingbankLst.setBkChk(DataStatus.dz_error);
										oriBeijingbankLst.setWhetherQs(false);
										oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
										oriBeijingbankLst.setZfFileFee(zf_file_fee);
										oriBeijingbankLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
										
										duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
									}else{//冲正
										
										//核对原笔对账结果
										OriginalBeijingbankLst oriBeijingbankLst2 = originalBeijingbankLstDAO.queryWhetherDzSuccess(oriBeijingbankLst.getReqSysStance(), originalPepDate, DataStatus.no_deductRollBk);
										if(oriBeijingbankLst2 != null){
											if(oriBeijingbankLst2.getBkChk() == 1){ //对账成功
												//当前这笔不需要清算
												originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
												//修改计算手续费
												originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
												
												//修改原笔不需要清算 对账成功
												originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,DataStatus.no_deductRollBk,deductStlmDate);
												
												if(!oriBeijingbankLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(oriBeijingbankLst.getTradeAmount().toString(), oriBeijingbankLst.getReqMerCode(), instInfo.getGate(), oriBeijingbankLst.getDeductSysTime(), 
															oriBeijingbankLst.getDeductRollbkSysTime(), oriBeijingbankLst.getDeductSysStance(), oriBeijingbankLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, oriBeijingbankLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("超时交易、流水: ",oriBeijingbankLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
												
											}else if(oriBeijingbankLst2.getBkChk() == 2){//对账失败
												
												//冲正交易进入差错
												originalBeijingbankLstDAO.updateCleanAndErrorRiqie(oriBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
												//修改计算手续费
												originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk, zf_fee,zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
												
												oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
												oriBeijingbankLst.setBkChk(DataStatus.dz_error);
												oriBeijingbankLst.setWhetherQs(false);
												oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
												oriBeijingbankLst.setZfFileFee(zf_file_fee);
												oriBeijingbankLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
												
											}else{
												//冲正交易进入差错
												originalBeijingbankLstDAO.updateCleanAndErrorRiqie(oriBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
												//修改计算手续费
												originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
												
												oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
												oriBeijingbankLst.setBkChk(DataStatus.dz_error);
												oriBeijingbankLst.setWhetherQs(false);
												oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
												oriBeijingbankLst.setZfFileFee(zf_file_fee);
												oriBeijingbankLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
											}
										}else{
											//冲正交易进入差错
											originalBeijingbankLstDAO.updateCleanAndErrorRiqie(oriBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
											//修改计算手续费
											originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
											
											oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
											oriBeijingbankLst.setBkChk(DataStatus.dz_error);
											oriBeijingbankLst.setWhetherQs(false);
											oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
											oriBeijingbankLst.setZfFileFee(zf_file_fee);
											oriBeijingbankLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
										}
										
										duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangBeijingReqTime);
									}
								}else{//不存在
									log.info(stringPingJie.getStringPingJie("超时交易、流水: ",oriBeijingbankLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee,",_deductStlmDate","->",date));
									if((oriBeijingbankLst.getTrademsgType() == 2 || oriBeijingbankLst.getTrademsgType() == 18
											|| oriBeijingbankLst.getTrademsgType() == 20 || oriBeijingbankLst.getTrademsgType() == 56 || oriBeijingbankLst.getTrademsgType() == 58) 
											&&  !oriBeijingbankLst.isDeductRollBk()){
										riqieBeijingbankLstDAO.updateNoNeedHandle(oriBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, oriBeijingbankLst.getTrademsgType(), deductStlmDate);
										originalBeijingbankLstDAO.updateNoNeedHandle(oriBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, oriBeijingbankLst.getTrademsgType(), deductStlmDate);
									}else{
										riqieBeijingbankLstDAO.updateNoNeedHandle(oriBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, oriBeijingbankLst.getTrademsgType(), deductStlmDate);
										originalBeijingbankLstDAO.updateNoNeedHandle(oriBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, oriBeijingbankLst.getTrademsgType(), deductStlmDate);
									}
								}
							}//失败
							else{
								if(oriBeijingbankLst.getTrademsgType() != null){
									//查询对账文件中是否存在
									//String zf_file_fee = duizhangBeijingbankLstDAO.findDzFileData(oriBeijingbankLst,oriObject, duizObject,instId,DataStatus.no_riqie,duizhangBeijingReqTime);
									String zf_file_fee = beijingDz_map.get(oriBeijingbankLst.getDeductSysStance());
									if(StringUtils.isNotBlank(zf_file_fee)){//存在
										if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
											zf_fee = Double.valueOf(zf_file_fee);
										}
										log.info(stringPingJie.getStringPingJie("失败交易、流水: ",oriBeijingbankLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee," ,",_deductStlmDate+"->",date));
										if((oriBeijingbankLst.getTrademsgType() == 2) &&
												!oriBeijingbankLst.isDeductRollBk()){//消费
											originalBeijingbankLstDAO.updateCleanAndError(oriBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.long_status,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
											//修改计算手续费
											originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
											
											oriBeijingbankLst.setWhetherErroeHandle(DataStatus.long_status);
											oriBeijingbankLst.setBkChk(DataStatus.dz_error);
											oriBeijingbankLst.setWhetherQs(false);
											oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
											oriBeijingbankLst.setZfFileFee(zf_file_fee);
											oriBeijingbankLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
											duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
										}else if(oriBeijingbankLst.getTrademsgType() == 20){//退货
											originalBeijingbankLstDAO.updateCleanAndError(oriBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
											//修改计算手续费
											originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
											
											oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
											oriBeijingbankLst.setBkChk(DataStatus.dz_error);
											oriBeijingbankLst.setWhetherQs(false);
											oriBeijingbankLst.setWhetherTk(DataStatus.tk);
											oriBeijingbankLst.setZfFileFee(zf_file_fee);
											oriBeijingbankLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
											
											duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
										}else if(oriBeijingbankLst.getTrademsgType() == 18 && !oriBeijingbankLst.isDeductRollBk()){//撤销
											
											originalBeijingbankLstDAO.updateCleanAndError(oriBeijingbankLst.getReqSysStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate);
											//修改计算手续费
											originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getReqSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.no_deductRollBk,originalPepDate,whetherReturnFee);
											
											oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
											oriBeijingbankLst.setBkChk(DataStatus.dz_error);
											oriBeijingbankLst.setWhetherQs(false);
											oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
											oriBeijingbankLst.setZfFileFee(zf_file_fee);
											oriBeijingbankLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
											
											duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangBeijingReqTime);
										}else{//冲正
											
											//核对原笔对账结果
											OriginalBeijingbankLst oriBeijingbankLst2 = originalBeijingbankLstDAO.queryWhetherDzSuccess(oriBeijingbankLst.getReqSysStance(), originalPepDate, DataStatus.no_deductRollBk);
											if(oriBeijingbankLst2 != null){
												if(oriBeijingbankLst2.getBkChk() == 1){ //对账成功
													//当前这笔需要清算
													originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
													//修改计算手续费
													originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
													
													//修改原笔需要清算 对账成功
													originalBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,DataStatus.no_deductRollBk,originalPepDate);
													
													if(!oriBeijingbankLst.isWhetherAccessStance()){
														boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(oriBeijingbankLst.getTradeAmount().toString(), oriBeijingbankLst.getReqMerCode(), instInfo.getGate(), oriBeijingbankLst.getDeductSysTime(), 
																oriBeijingbankLst.getDeductRollbkSysTime(), oriBeijingbankLst.getDeductSysStance(), oriBeijingbankLst.isDeductRollBk(), 
																instId, inst_type, deductStlmDate, bank_id, oriBeijingbankLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
														if(update_flag){
															log.info(stringPingJie.getStringPingJie("失败交易、流水: ",oriBeijingbankLst.getDeductSysStance()," 对账成功-进入资金流水"));
														}
													}
													
												}else if(oriBeijingbankLst2.getBkChk() == 2){//对账失败
													
													//冲正交易进入差错
													originalBeijingbankLstDAO.updateCleanAndErrorRiqie(oriBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
													//修改计算手续费
													originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
													
													oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
													oriBeijingbankLst.setBkChk(DataStatus.dz_error);
													oriBeijingbankLst.setWhetherQs(false);
													oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
													oriBeijingbankLst.setZfFileFee(zf_file_fee);
													oriBeijingbankLst.setZfFee(zf_fee);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
												}else{
													//冲正交易进入差错
													originalBeijingbankLstDAO.updateCleanAndErrorRiqie(oriBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
													//修改计算手续费
													originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
													
													oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
													oriBeijingbankLst.setBkChk(DataStatus.dz_error);
													oriBeijingbankLst.setWhetherQs(false);
													oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
													oriBeijingbankLst.setZfFileFee(zf_file_fee);
													oriBeijingbankLst.setZfFee(zf_fee);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
												}
											}else{
												//冲正交易进入差错
												originalBeijingbankLstDAO.updateCleanAndErrorRiqie(oriBeijingbankLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate);
												//修改计算手续费
												originalBeijingbankLstDAO.updateSettleInfo(oriBeijingbankLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, oriBeijingbankLst.getTrademsgType(),DataStatus.deductRollBk,originalPepDate,whetherReturnFee);
												
												oriBeijingbankLst.setWhetherErroeHandle(DataStatus.short_status);
												oriBeijingbankLst.setBkChk(DataStatus.dz_error);
												oriBeijingbankLst.setWhetherQs(false);
												oriBeijingbankLst.setWhetherTk(DataStatus.no_tk);
												oriBeijingbankLst.setZfFileFee(zf_file_fee);
												oriBeijingbankLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(oriBeijingbankLst));
											}
											
											duizhangBeijingbankLstDAO.updateClean(oriBeijingbankLst.getReqSysStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangBeijingReqTime);
										}
									}else{//不存在
										log.info(stringPingJie.getStringPingJie("失败交易、流水: ",oriBeijingbankLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee,",_deductStlmDate","->",date));
										if((oriBeijingbankLst.getTrademsgType() == 2 || oriBeijingbankLst.getTrademsgType() == 18
												|| oriBeijingbankLst.getTrademsgType() == 20 || oriBeijingbankLst.getTrademsgType() == 56 || oriBeijingbankLst.getTrademsgType() == 58) 
												&&  !oriBeijingbankLst.isDeductRollBk()){
											riqieBeijingbankLstDAO.updateNoNeedHandle(oriBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, oriBeijingbankLst.getTrademsgType(), deductStlmDate);
											originalBeijingbankLstDAO.updateNoNeedHandle(oriBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, oriBeijingbankLst.getTrademsgType(), deductStlmDate);
										}else{
											riqieBeijingbankLstDAO.updateNoNeedHandle(oriBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, oriBeijingbankLst.getTrademsgType(), deductStlmDate);
											originalBeijingbankLstDAO.updateNoNeedHandle(oriBeijingbankLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, oriBeijingbankLst.getTrademsgType(), deductStlmDate);
										}
									}
								}
							}
						}
					}
					
					log.info(stringPingJie.getStringPingJie("开始统计",duizhangBeijingReqTime ,"日期对账成功数据"));
					Map<String, Object> mapMerBasic = Backstage.getInstance().getMerBasicList();
					tradeLstDAO = TradeLstDAO.getInstance();
					log.info(stringPingJie.getStringPingJie("开始统计",duizhangBeijingReqTime,"日期T+1日统计数据"));
					//获取该渠道下T+1交易的商户
					List<Object> listObj = tradeLstDAO.getChannelMerCode(originalDataTableName, DataStatus.clean, deductStlmDate, "deduct_stlm_date");
					Map<String, String> map = SettleMerchantMatchTableDAO.getInstance().getSettleMerCode();
					List<Object> settleMercodeConfiglist = SettleMerchantConfigDAO.getInstance().getSettleMerchantConfig();
					if(listObj != null){
						//先删除对应结算商户T+1统计数据
						if(settleMercodeConfiglist != null && settleMercodeConfiglist.size() > 0){
							for(Object settleMerCode : settleMercodeConfiglist){
								boolean delete  = tradeLstDAO.deleteSettleMerchantSettleStatistics(deductStlmDate, settleMerCode.toString(), instId, inst_type);
								log.info(stringPingJie.getStringPingJie("日期:",deductStlmDate,"渠道ID:",instId,"渠道类型:",inst_type,"对应结算商户号:",settleMerCode,"T+1日统计数据data_status = 0删除 :",delete));
							}
						}
						for (Object object:listObj) {
							String dy_merCode = object.toString();
							boolean settle_flag = map.get(dy_merCode) != null ? true : false;
							if(map != null && settle_flag){//处理结算商户对应电银商户T+1统计
								String settleMerCode = map.get(dy_merCode);
								Object objMer = mapMerBasic.get(settleMerCode);
								if(objMer != null){
									MerBasic merBasic = (MerBasic)objMer;
									Object[] obj = tradeLstDAO.findPosChanneltotall(deductStlmDate, dy_merCode, DataStatus.clean, originalDataTableName);
									boolean isSuccess = tradeLstDAO.merchantSettleStatisticsUtil(deductStlmDate,settleMerCode, DataStatus.clean, originalDataTableName, instId, merBasic.getMerCategory(),inst_type,obj,settle_flag,bank_id);
									if(isSuccess){
										log.info(stringPingJie.getStringPingJie("北京银行T+1日  ",dy_merCode,"-->",settleMerCode,"保存",isSuccess));
									}else{
										log.info(stringPingJie.getStringPingJie("北京银行T+1日  ",dy_merCode,"-->",settleMerCode,"失败",isSuccess));
									}
								}else{
									log.info(stringPingJie.getStringPingJie("查询北京银行交易日期",deductStlmDate,"交易",settleMerCode,"结算商户为空"));
								}
							}else{
								Object objMer = mapMerBasic.get(dy_merCode);
								if(objMer != null){
									MerBasic merBasic = (MerBasic)objMer;
									Object[] obj = tradeLstDAO.findPosChanneltotall(deductStlmDate, dy_merCode, DataStatus.clean, originalDataTableName);
									boolean isSuccess = tradeLstDAO.merchantSettleStatisticsUtil(deductStlmDate,dy_merCode, DataStatus.clean, originalDataTableName, instId, merBasic.getMerCategory(),inst_type,obj,settle_flag,bank_id);
									if(isSuccess){
										log.info(stringPingJie.getStringPingJie("北京银行T+1日  ",dy_merCode,"保存",isSuccess));
									}else{
										log.info(stringPingJie.getStringPingJie("北京银行T+1日  ",dy_merCode,"失败",isSuccess));
									}
								}else{
									log.info(stringPingJie.getStringPingJie("查询北京银行交易日期",deductStlmDate,"交易",dy_merCode,"结算商户为空"));
								}
							}
						}
					}
					
					log.info("开始汇总北京银行对账之后数据汇总");
					channelDzCollectDAO = cn.com.chinaebi.dz.object.dao.ChannelDzCollectDAO.getInstance();
					int count = channelDzCollectDAO.savePosChannelDzCollect(instInfo, deductStlmDate,map,mapMerBasic);
					log.info(stringPingJie.getStringPingJie("北京银行对账之后汇总条数",count));
					log.info("结束汇总北京银行对账之后数据汇总");
					
					ExecuteNode executeNode  = executeNodeDAO.findExecuteNodeData(deductStlmDate, instInfo.getId().getInstId(), instInfo.getId().getInstType());
					if(executeNode != null){
						/**
						 * 0:未执行、1:执行成功、2:执行失败、3:对账单数据不存在、4:原始交易数据不存在
						 * 0、3、4:表示数据没有勾兑过,不会产生对账数据状态影响
						 */
						Integer dzHandle = executeNode.getDzHandle();
						if(dzHandle != 0 && dzHandle != 3 && dzHandle != 4){
							log.info("开始检测北京银行原始交易差错重对账处理");
							List<ErrorDataLst> oriList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_original, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							if(oriList != null && oriList.size() > 0){
								log.info(stringPingJie.getStringPingJie("重对账检测到北京银行原始交易差错条数为:",oriList.size()));
								for (ErrorDataLst errorDataLst : oriList) {
									String tradeId = errorDataLst.getId();
									Integer handlingStatus = errorDataLst.getHandlingStatus();
									Integer bkChk = tradeLstDAO.findOriChannelStanceData(deductStlmDate, tradeId, originalDataTableName);
									if(bkChk == 1){//对账成功
										//0：未处理、1：待审核、2：已审核、3：已驳回、4：无需处理
										if(handlingStatus == 2){
											deductStlmDate = errorDataLst.getJsDate();
											if(errorDataLst.getHandlingId() == 1  && StringUtils.isNotBlank(deductStlmDate)){//正常结算
												String settleMerCode = errorDataLst.getReqMerCode();
												if(map != null && map.get(settleMerCode) != null){
													settleMerCode = map.get(settleMerCode);
												}
												tradeLstDAO.updateMerchantSettleStatistics(settleMerCode,errorDataLst.getDeductSysStance(), errorDataLst.getTradeAmount(), errorDataLst.getMerFee(), errorDataLst.getZfFee(), errorDataLst.getZfFileFee(), deductStlmDate, instId, inst_type, DataStatus.t1_ryt_cc_sh, errorDataLst.isWhetherTk());
											}
											errorDataLstDAO.updateErrorHandlerStatus(tradeId, DataStatus.error_handle_4,DataStatus.double_check_success, "重对账成功");
										}else{
											errorDataLstDAO.updateErrorHandlerStatus(tradeId, DataStatus.error_handle_4,DataStatus.double_check_success, "重对账成功");
										}
									}else if(bkChk == 3){//无需对账
										if(handlingStatus == 2){
											deductStlmDate = errorDataLst.getJsDate();
											if(errorDataLst.getHandlingId() == 1  && StringUtils.isNotBlank(deductStlmDate)){//正常结算
												String settleMerCode = errorDataLst.getReqMerCode();
												if(map != null && map.get(settleMerCode) != null){
													settleMerCode = map.get(settleMerCode);
												}
												tradeLstDAO.updateMerchantSettleStatistics(settleMerCode,errorDataLst.getDeductSysStance(), errorDataLst.getTradeAmount(), errorDataLst.getMerFee(), errorDataLst.getZfFee(), errorDataLst.getZfFileFee(), deductStlmDate, instId, inst_type, DataStatus.t1_ryt_cc_sh, errorDataLst.isWhetherTk());
											}
											errorDataLstDAO.updateErrorHandlerStatus(tradeId, DataStatus.error_handle_4,DataStatus.double_check_OriginalNotData, "重对账-无需对账");
										}else{
											errorDataLstDAO.updateErrorHandlerStatus(tradeId, DataStatus.error_handle_4,DataStatus.double_check_OriginalNotData, "重对账-无需对账");
										}
									}else{//对账失败
										errorDataLstDAO.updateErrorHandlerStatus(tradeId, handlingStatus,DataStatus.double_check_fail, "重对账-对账失败");
									}
								}
							}else{
								log.info("没有到检测北京银行原始交易差错数据");
							}
							
							log.info("开始检测北京银行对账单交易数据差错重对账处理");
							List<ErrorDataLst> duizList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_duizhang, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							if(duizList != null && duizList.size() > 0){
								log.info(stringPingJie.getStringPingJie("重对账检测北京银行对账单交易数据差错条数为:",duizList.size()));
								for (ErrorDataLst errorDataLst : duizList) {
									String tradeId = errorDataLst.getId();
									//0：未处理、1：待审核、2：已审核、3：已驳回、4：无需处理
									Integer handlingStatus = errorDataLst.getHandlingStatus();
									Integer bkChk = tradeLstDAO.findDuizChannelStanceData(deductStlmDate, tradeId, "id", dzDataTableName);
									if(bkChk != null && bkChk == 1){//对账成功
										if(handlingStatus == 2){
											deductStlmDate = errorDataLst.getJsDate();
											if(errorDataLst.getHandlingId() == 1  && StringUtils.isNotBlank(deductStlmDate)){//正常结算
												String settleMerCode = errorDataLst.getReqMerCode();
												if(map != null && map.get(settleMerCode) != null){
													settleMerCode = map.get(settleMerCode);
												}
												tradeLstDAO.updateMerchantSettleStatistics(settleMerCode,errorDataLst.getDeductSysStance(), errorDataLst.getTradeAmount(), errorDataLst.getMerFee(), errorDataLst.getZfFee(), errorDataLst.getZfFileFee(), deductStlmDate, instId, inst_type, DataStatus.t1_ryt_cc_sh, errorDataLst.isWhetherTk());
											}
											errorDataLstDAO.updateErrorHandlerStatus(tradeId, DataStatus.error_handle_4,DataStatus.double_check_success, "重对账成功");
										}else{
											errorDataLstDAO.updateErrorHandlerStatus(tradeId, DataStatus.error_handle_4,DataStatus.double_check_success, "重对账成功");
										}
									}else if(bkChk != null && bkChk == 0){//未对账
										errorDataLstDAO.updateErrorHandlerStatus(tradeId, handlingStatus,DataStatus.double_check_fail, "重对账,对账单数据未对账");
									}
								}
							}else{
								log.info("没有到检测北京银行对账单交易数据差错数据");
							}
						}else{
							log.info("第一次执行,无需处理重对账差错数据核对");
						}
					}
					
					log.info("开始检测北京银行对账文件是否有可疑交易数据");
					//处理北京银行对账文件未对账的可以交易数据
					List<DuizhangBeijingbankLst> beijingList = duizhangBeijingbankLstDAO.findDateData(duizhangBeijingReqTime, DataStatus.not_dz);
					if(beijingList != null && beijingList.size() > 0){
						log.info("北京银行对账文件可疑交易数据总条数是:"+beijingList.size());
						for (DuizhangBeijingbankLst beijingbankLst:beijingList) {
							errorDataLstDAO.saveErrorData(new ErrorDataLst(beijingbankLst));
						}
					}else{
						log.info("没有检测到北京银行对账文件的可疑交易数据");
					}
				}else{
					throw new OriginalDataNotFoundException("获取电银北京银行交易数据 " +originalPepDate + " is not data");
				}
			} catch (Exception e) {
				log.error("北京银行对账错误: "+e);
				throw e;
			}
		//}else{
			//throw new DuizhangNotFoundException("没有获取到  "+duizhangBeijingReqTime+"  日期的北京银行对账文件数据");
		//}
	}
	
	@Override
	public void tradeErrorDzDeal(String originalPepDate,
			String duizhangBeijingReqTime,Integer instId,Integer flagStatus)
			throws OriginalDataNotFoundException, DuizhangNotFoundException,
			Exception {
		log.info("目前暂时不支持北京银行差错对账");
		
	}

	@Override
	public void onLineTradeDzDeal(Integer startDate,
			Integer endDate, String deductStlmDate,
			Integer bank_gate,Integer instId,Integer instType, Integer flagStatus)
			throws OriginalDataNotFoundException, DuizhangNotFoundException,
			Exception {
		// TODO Auto-generated method stub
		
	}
}
