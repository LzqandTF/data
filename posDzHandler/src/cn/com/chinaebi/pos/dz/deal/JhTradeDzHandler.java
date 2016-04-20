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
import cn.com.chinaebi.dz.object.DuizhangDljhLst;
import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.ExecuteNode;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.OriginalDljhLst;
import cn.com.chinaebi.dz.object.RiqieDljhLst;
import cn.com.chinaebi.dz.object.dao.DuizhangDljhLstDAO;
import cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO;
import cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO;
import cn.com.chinaebi.dz.object.dao.InstRateDAO;
import cn.com.chinaebi.dz.object.dao.OriginalDljhLstDAO;
import cn.com.chinaebi.dz.object.dao.RiqieDljhLstDAO;
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
 * 大连交行交易对账处理类
 * @author zhu.hongliang
 * 
 */
public class JhTradeDzHandler implements TradeDzHandler{
	private Log log = LogFactory.getLog(getClass());
	private cn.com.chinaebi.dz.object.dao.iface.OriginalDljhLstDAO originalDljhLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.DuizhangDljhLstDAO duizhangDljhLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.RiqieDljhLstDAO riqieDljhLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorDataLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO;
	private cn.com.chinaebi.dz.object.dao.iface.InstRateDAO instRateDAO;
	private ChannelDzCollectDAO channelDzCollectDAO;
	
	private final Integer inst_type = DataStatus.inst_type_0;
	private final String columnName = "trade_id";
	private final Integer whether_flag = 1;
	
	@Override
	public void tradeDzDeal(String originalPepDate, String duizhangReqTime,
			String deductStlmDate, Boolean innertTradeHandler,
			Integer bank_id,Integer instId,Integer instType,
			Integer flagStatus) throws OriginalDataNotFoundException,
			DuizhangNotFoundException, Exception {
		log.info("获取原始交易数据日期:"+originalPepDate);
		log.info("获取对账文件数据日期:"+duizhangReqTime);
		log.info("日切数据日期: "+deductStlmDate);
		originalDljhLstDAO = OriginalDljhLstDAO.getInstance();
		duizhangDljhLstDAO = DuizhangDljhLstDAO.getInstance();
		riqieDljhLstDAO = RiqieDljhLstDAO.getInstance();
		errorDataLstDAO = ErrorDataLstDAO.getInstance();
		executeNodeDAO = ExecuteNodeDAO.getInstance();
		tradeLstDAO = TradeLstDAO.getInstance();
		instRateDAO = InstRateDAO.getInstance();
		StringPingJie stringPingJie = StringPingJie.getInstance();
		SimpleDateFormat dateFormat = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_6);
		
		Map<String, String> dljhDz_map = duizhangDljhLstDAO.findDzFileData(duizhangReqTime);
		if(dljhDz_map != null && dljhDz_map.keySet() != null && dljhDz_map.keySet().size() > 0){
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
				List<RiqieDljhLst> listRiqieDljhLst = riqieDljhLstDAO.findRiqieDljhLst(deductStlmDate);
				if(listRiqieDljhLst != null && listRiqieDljhLst.size() > 0){
					log.info("开始处理电银大连交行日切数据对账...");
					for (RiqieDljhLst riqieDljhLst:listRiqieDljhLst) {
						whetherReturnFee = 0;
						instRateType = 0;
						zf_fee = 0d;

						//计算银行手续费
						if(instRateObj != null){
							whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
							instRateType = Integer.valueOf(instRateObj[0].toString());
							if(instRateType != 2){
								zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(riqieDljhLst.getTradeAmount()), riqieDljhLst.getOutAccount(), riqieDljhLst.getDeductSysId(), inst_type, riqieDljhLst.getDeductMerCode());
							}
							
							if(instRateType == 1){
								String mcc_code = PoundageCalculate.mccCodeSubstring(riqieDljhLst.getDeductMerCode());
								if(StringUtils.isNotBlank(mcc_code)){
									boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
									if(riqieDljhLst.getTrademsgType() == 20){//退货交易
										if(whetherReturnFee == 0 && !lw_flag){//不退还
											zf_fee = 0d;
										}else if(whetherReturnFee == 1 && lw_flag){//退还
											zf_fee = 0d;
										}
									}
								}
							}else if(instRateType == 3){//按固定费率
								//退货交易-不退还手续费
								if(riqieDljhLst.getTrademsgType() != null && riqieDljhLst.getTrademsgType() == 20 && whetherReturnFee == 0){
									zf_fee = 0d;
								}
							}
						}
						
						//成功
						if(((StringUtils.equals(riqieDljhLst.getDeductSysResponse(), "00") && riqieDljhLst.getDeductSysResponse() != null)||
								(StringUtils.equals(riqieDljhLst.getDeductRollBkResponse(), "00") && riqieDljhLst.getDeductRollBkResponse() != null))
								&& riqieDljhLst.getTrademsgType() != null){
							//查询对账文件中是否存在
							//String zf_file_fee = duizhangDljhLstDAO.findDzFileData(riqieDljhLst, oriObject, duizObject, instId, DataStatus.riqie, duizhangReqTime);
							String zf_file_fee = dljhDz_map.get(riqieDljhLst.getDeductSysStance());
							if(StringUtils.isNotBlank(zf_file_fee)){
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("成功交易、流水: ",riqieDljhLst.getDeductSysStance()," 对账成功,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieDljhLst.getTrademsgType() == 2 ||  riqieDljhLst.getTrademsgType() == 56) &&
										!riqieDljhLst.isDeductRollBk()){//消费or预授权完成
									/*
									 * 1:对账成功、需要清算
									 * 2:修改为商户手续费、退款交易、对账文件手续费
									 */
									riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
								}else if((riqieDljhLst.getTrademsgType() == 18 || riqieDljhLst.getTrademsgType() == 58)
										&& !riqieDljhLst.isDeductRollBk()){//撤销or预授权完成撤销
									/*
									 * 1:对账成功、需要清算
									 * 2:修改为商户手续费、退款交易、对账文件手续费
									 */
									riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
								}else{//冲正
									//获取原笔交易对账信息
									OriginalDljhLst originalDljhLst  = originalDljhLstDAO.queryWhetherDzSuccess(riqieDljhLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalDljhLst != null){
										if(originalDljhLst.getBkChk() == 1){//对账成功
											
											//当前这笔需要清算
											riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										}else if(originalDljhLst.getBkChk() == 2){//对账失败
											
											/*
											 * 1:当前交易对账失败、不需要清算 
											 * 2:修改为商户手续费、退款交易、对账文件手续费
											 * 3:当前交易进入差错，并且保存"2"部分数据
											 */
											riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//冲正进入差错
											riqieDljhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieDljhLst.setBkChk(DataStatus.dz_error);
											riqieDljhLst.setWhetherQs(false);
											riqieDljhLst.setWhetherTk(DataStatus.no_tk);
											riqieDljhLst.setZfFileFee(zf_file_fee);
											riqieDljhLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
											
										}else{
											//当前这笔需要清算
											riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										}
									}else{
										//当前这笔需要清算
										riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
									}
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime);
								}
							}else{
								log.info(stringPingJie.getStringPingJie("成功交易、流水: ",riqieDljhLst.getDeductSysStance()," 对账单不存在-未对账,支付手续费:",zf_fee));
								if(riqieDljhLst.isDeductRollBk()){
									riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
									originalDljhLstDAO.updateClean(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
								}else{
									riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
								}
							}
						}//超时
						else if(((riqieDljhLst.getDeductResult() != null && riqieDljhLst.getDeductResult() == 1) || 
								(riqieDljhLst.getDeductRollBkResult() != null && riqieDljhLst.getDeductRollBkResult() == 1))
								&& riqieDljhLst.getTrademsgType() != null){
							//查询对账文件中是否存在
//							String zf_file_fee = duizhangDljhLstDAO.findDzFileData(riqieDljhLst, oriObject, duizObject, instId, DataStatus.riqie, duizhangReqTime);
							String zf_file_fee = dljhDz_map.get(riqieDljhLst.getDeductSysStance());
							if(StringUtils.isNotBlank(zf_file_fee)){//存在
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieDljhLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieDljhLst.getTrademsgType() == 2 || riqieDljhLst.getTrademsgType() == 56) &&
										!riqieDljhLst.isDeductRollBk()){//消费
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieDljhLst.setBkChk(DataStatus.dz_error);
									riqieDljhLst.setWhetherQs(false);
									riqieDljhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieDljhLst.setZfFileFee(zf_file_fee);
									riqieDljhLst.setZfFee(zf_fee);
									riqieDljhLst.setWhetherTk(DataStatus.no_tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
									//修改对账文件-对账成功
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
								}else if((riqieDljhLst.getTrademsgType() == 18 || riqieDljhLst.getTrademsgType() == 58) 
										&& !riqieDljhLst.isDeductRollBk()){//撤销
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieDljhLst.setBkChk(DataStatus.dz_error);
									riqieDljhLst.setWhetherQs(false);
									riqieDljhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieDljhLst.setZfFileFee(zf_file_fee);
									riqieDljhLst.setZfFee(zf_fee);
									riqieDljhLst.setWhetherTk(DataStatus.no_tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
									
									//修改对账文件-对账成功
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
								}else{//冲正
									OriginalDljhLst originalDljhLst  = originalDljhLstDAO.queryWhetherDzSuccessAll(riqieDljhLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalDljhLst != null){
										if(originalDljhLst.getBkChk() == 1){ //对账成功
											//当前这笔需要清算
											riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										
											if(!riqieDljhLst.isWhetherAccessStance()){
												boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieDljhLst.getTradeAmount().toString(), riqieDljhLst.getReqMerCode(), instInfo.getGate(), riqieDljhLst.getDeductSysTime(), 
														riqieDljhLst.getDeductRollbkSysTime(), riqieDljhLst.getDeductSysStance(), riqieDljhLst.isDeductRollBk(), 
														instId, inst_type, deductStlmDate, bank_id, riqieDljhLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
												if(update_flag){
													log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieDljhLst.getDeductSysStance()," 对账成功-进入资金流水"));
												}
											}
										}else if(originalDljhLst.getBkChk() == 2){ //对账失败
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieDljhLst.setBkChk(DataStatus.dz_error);
											riqieDljhLst.setWhetherQs(false);
											riqieDljhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieDljhLst.setZfFileFee(zf_file_fee);
											riqieDljhLst.setZfFee(zf_fee);
											riqieDljhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
										}else{
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieDljhLst.setBkChk(DataStatus.dz_error);
											riqieDljhLst.setWhetherQs(false);
											riqieDljhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieDljhLst.setZfFileFee(zf_file_fee);
											riqieDljhLst.setZfFee(zf_fee);
											riqieDljhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
										}
									}else{
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieDljhLst.setBkChk(DataStatus.dz_error);
										riqieDljhLst.setWhetherQs(false);
										riqieDljhLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieDljhLst.setZfFileFee(zf_file_fee);
										riqieDljhLst.setZfFee(zf_fee);
										riqieDljhLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
									}
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime);
								}
							}else{//不存在
								log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieDljhLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
								if((riqieDljhLst.getTrademsgType() == 2 || riqieDljhLst.getTrademsgType() == 18
										|| riqieDljhLst.getTrademsgType() == 20 || riqieDljhLst.getTrademsgType() == 56 || riqieDljhLst.getTrademsgType() == 58) 
										&&  !riqieDljhLst.isDeductRollBk()){
									riqieDljhLstDAO.updateNoNeedHandle(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieDljhLst.getTrademsgType(), deductStlmDate);
									originalDljhLstDAO.updateNoNeedHandle(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieDljhLst.getTrademsgType(), deductStlmDate);
								}else{
									riqieDljhLstDAO.updateNoNeedHandle(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieDljhLst.getTrademsgType(), deductStlmDate);
									originalDljhLstDAO.updateNoNeedHandle(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieDljhLst.getTrademsgType(), deductStlmDate);
								}
							}
						}//失败
						else{
							//查询对账文件中是否存在
							String zf_file_fee = dljhDz_map.get(riqieDljhLst.getDeductSysStance());
//							String zf_file_fee = duizhangDljhLstDAO.findDzFileData(riqieDljhLst, oriObject, duizObject, instId, DataStatus.riqie, duizhangReqTime);
							if(StringUtils.isNotBlank(zf_file_fee)){//存在
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								
								log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieDljhLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieDljhLst.getTrademsgType() == 2 || riqieDljhLst.getTrademsgType() == 56) &&
										!riqieDljhLst.isDeductRollBk()){//消费
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieDljhLst.setBkChk(DataStatus.dz_error);
									riqieDljhLst.setWhetherQs(false);
									riqieDljhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieDljhLst.setZfFileFee(zf_file_fee);
									riqieDljhLst.setZfFee(zf_fee);
									riqieDljhLst.setWhetherTk(DataStatus.no_tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
									//修改对账文件-对账成功
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
								}else if((riqieDljhLst.getTrademsgType() == 18 || riqieDljhLst.getTrademsgType() == 58) 
										&& !riqieDljhLst.isDeductRollBk()){//撤销
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieDljhLst.setBkChk(DataStatus.dz_error);
									riqieDljhLst.setWhetherQs(false);
									riqieDljhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieDljhLst.setZfFileFee(zf_file_fee);
									riqieDljhLst.setZfFee(zf_fee);
									riqieDljhLst.setWhetherTk(DataStatus.no_tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
									
									//修改对账文件-对账成功
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
								}else{//冲正
									OriginalDljhLst originalDljhLst  = originalDljhLstDAO.queryWhetherDzSuccessAll(riqieDljhLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalDljhLst != null){
										if(originalDljhLst.getBkChk() == 1){ //对账成功
											//当前这笔需要清算
											riqieDljhLstDAO.updateClean(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										
											if(!riqieDljhLst.isWhetherAccessStance()){
												boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieDljhLst.getTradeAmount().toString(), riqieDljhLst.getReqMerCode(), instInfo.getGate(), riqieDljhLst.getDeductSysTime(), 
														riqieDljhLst.getDeductRollbkSysTime(), riqieDljhLst.getDeductSysStance(), riqieDljhLst.isDeductRollBk(), 
														instId, inst_type, deductStlmDate, bank_id, riqieDljhLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
												if(update_flag){
													log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieDljhLst.getDeductSysStance()," 对账成功-进入资金流水"));
												}
											}
										}else if(originalDljhLst.getBkChk() == 2){ //对账失败
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieDljhLst.setBkChk(DataStatus.dz_error);
											riqieDljhLst.setWhetherQs(false);
											riqieDljhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieDljhLst.setZfFileFee(zf_file_fee);
											riqieDljhLst.setZfFee(zf_fee);
											riqieDljhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
										}else{
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieDljhLst.setBkChk(DataStatus.dz_error);
											riqieDljhLst.setWhetherQs(false);
											riqieDljhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieDljhLst.setZfFileFee(zf_file_fee);
											riqieDljhLst.setZfFee(zf_fee);
											riqieDljhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
										}
									}else{
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieDljhLstDAO.updateCleanAndError(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										originalDljhLstDAO.updateCleanRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										riqieDljhLstDAO.updateSettleInfo(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										originalDljhLstDAO.updateSettleInfoRiqie(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieDljhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieDljhLst.setBkChk(DataStatus.dz_error);
										riqieDljhLst.setWhetherQs(false);
										riqieDljhLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieDljhLst.setZfFileFee(zf_file_fee);
										riqieDljhLst.setZfFee(zf_fee);
										riqieDljhLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieDljhLst));
									}
									duizhangDljhLstDAO.updateClean(riqieDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime);
								}
							}else{//不存在
								log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieDljhLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
								if((riqieDljhLst.getTrademsgType() == 2 || riqieDljhLst.getTrademsgType() == 18
										|| riqieDljhLst.getTrademsgType() == 20 || riqieDljhLst.getTrademsgType() == 56 || riqieDljhLst.getTrademsgType() == 58) 
										&&  !riqieDljhLst.isDeductRollBk()){
									riqieDljhLstDAO.updateNoNeedHandle(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieDljhLst.getTrademsgType(), deductStlmDate);
									originalDljhLstDAO.updateNoNeedHandle(riqieDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieDljhLst.getTrademsgType(), deductStlmDate);
								}else{
									riqieDljhLstDAO.updateNoNeedHandle(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieDljhLst.getTrademsgType(), deductStlmDate);
									originalDljhLstDAO.updateNoNeedHandle(riqieDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieDljhLst.getTrademsgType(), deductStlmDate);
								}
							}
						}
					}
				}else{
					log.error(stringPingJie.getStringPingJie("没有获取到  ",deductStlmDate,"  日期的大连交行原始交易日切数据"));
				}
			} catch (Exception e) {
				log.error("大连交行日切对账错误: "+e);
			}
			
			try {
				List<OriginalDljhLst> listOriginalDljhLst = originalDljhLstDAO.findOriginalDljhLst(originalPepDate);
				if(listOriginalDljhLst != null && listOriginalDljhLst.size() > 0){
					log.info("开始处理电银大连交行数据对账...");
					for (OriginalDljhLst originalDljhLst:listOriginalDljhLst) {
						zf_fee = 0d;
						whetherReturnFee = 0;
						instRateType = 0;
						String _deductStlmDate = dateFormat.format(originalDljhLst.getDeductSysTime());
						String date = DYDataUtil.getNextDateString(originalPepDate);
						if(StringUtils.equalsIgnoreCase(_deductStlmDate,date)){//日切
							//处理日切交易数据
							log.info(stringPingJie.getStringPingJie("大连交行日切数据流水:",originalDljhLst.getDeductSysStance()," 日期:",_deductStlmDate));
							originalDljhLstDAO.updateDataRiqie(originalDljhLst.getDeductSysStance(), DataStatus.riqie,originalPepDate);
							originalDljhLst.setWhetherRiqie(true);
							riqieDljhLstDAO.saveRiqieDljhLst(new RiqieDljhLst(originalDljhLst), flagStatus);
						}else{
							//计算银行手续费
							if(instRateObj != null){
								whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
								instRateType = Integer.valueOf(instRateObj[0].toString());
								if(instRateType != 2){
									zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(originalDljhLst.getTradeAmount()), originalDljhLst.getOutAccount(), originalDljhLst.getDeductSysId(), inst_type, originalDljhLst.getDeductMerCode());
								}
								
								if(instRateType == 1){
									String mcc_code = PoundageCalculate.mccCodeSubstring(originalDljhLst.getDeductMerCode());
									if(StringUtils.isNotBlank(mcc_code)){
										boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
										if(originalDljhLst.getTrademsgType() == 20){//退货交易
											if(whetherReturnFee == 0 && !lw_flag){//不退还
												zf_fee = 0d;
											}else if(whetherReturnFee == 1 && lw_flag){//退还
												zf_fee = 0d;
											}
										}
									}
								}else if(instRateType == 3){//按固定费率
									//退货交易-不退还手续费
									if(originalDljhLst.getTrademsgType() != null && originalDljhLst.getTrademsgType() == 20 && whetherReturnFee == 0){
										zf_fee = 0d;
									}
								}
							}
							
							//成功
							if(((StringUtils.equals(originalDljhLst.getDeductSysResponse(), "00") && originalDljhLst.getDeductSysResponse() != null)||
									(StringUtils.equals(originalDljhLst.getDeductRollBkResponse(), "00") && originalDljhLst.getDeductRollBkResponse() != null))
									&& originalDljhLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								String zf_file_fee = dljhDz_map.get(originalDljhLst.getDeductSysStance());
//								String zf_file_fee = duizhangDljhLstDAO.findDzFileData(originalDljhLst, oriObject, duizObject, instId, DataStatus.no_riqie, duizhangReqTime);
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("成功交易、流水: ",originalDljhLst.getDeductSysStance()," 对账成功,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
									if((originalDljhLst.getTrademsgType() == 2 || originalDljhLst.getTrademsgType() == 56) &&
											!originalDljhLst.isDeductRollBk()){//消费or预授权完成
										originalDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
									}else if((originalDljhLst.getTrademsgType() == 18 || originalDljhLst.getTrademsgType() == 58) 
											&& !originalDljhLst.isDeductRollBk()){//撤销
										originalDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
									}else{//冲正
										OriginalDljhLst originalDljhLst2 =  originalDljhLstDAO.queryWhetherDzSuccess(originalDljhLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
										if(originalDljhLst2 != null){
											if(originalDljhLst2.getBkChk() == 1){//对账成功
												originalDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}else if(originalDljhLst2.getBkChk() == 2){ //对账失败
												originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalDljhLst.setBkChk(DataStatus.dz_error);
												originalDljhLst.setWhetherQs(false);
												originalDljhLst.setZfFileFee(zf_file_fee);
												originalDljhLst.setZfFee(zf_fee);
												originalDljhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}else{
												originalDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
										}else{
											originalDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
											originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
										}
										
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime);
									}
								}else{
									log.info(stringPingJie.getStringPingJie("成功交易、流水: ",originalDljhLst.getDeductSysStance()," 对账单不存在-未对账,支付手续费:",zf_fee));
									if(originalDljhLst.isDeductRollBk()){
										originalDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
									}else{
										originalDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
									}
								}
							}//超时
							else if(((originalDljhLst.getDeductResult() != null && originalDljhLst.getDeductResult() == 1) || 
									(originalDljhLst.getDeductRollBkResult() != null && originalDljhLst.getDeductRollBkResult() == 1))
									&& originalDljhLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangDljhLstDAO.findDzFileData(originalDljhLst, oriObject, duizObject, instId, DataStatus.no_riqie, duizhangReqTime);
								String zf_file_fee = dljhDz_map.get(originalDljhLst.getDeductSysStance());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("超时交易、流水: ",originalDljhLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
									if((originalDljhLst.getTrademsgType() == 2 || originalDljhLst.getTrademsgType() == 56) &&
											!originalDljhLst.isDeductRollBk()){//消费or预授权完成
										//修改不需要清算--对账失败
										originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalDljhLst.setWhetherErroeHandle(DataStatus.long_status);
										originalDljhLst.setBkChk(DataStatus.dz_error);
										originalDljhLst.setWhetherQs(false);
										originalDljhLst.setWhetherTk(DataStatus.no_tk);
										originalDljhLst.setZfFileFee(zf_file_fee);
										originalDljhLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
										//修改结算信息
										originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
									}else if((originalDljhLst.getTrademsgType() == 18 || originalDljhLst.getTrademsgType() == 58) 
											&& !originalDljhLst.isDeductRollBk()){//撤销
										
										originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
										originalDljhLst.setBkChk(DataStatus.dz_error);
										originalDljhLst.setWhetherQs(false);
										originalDljhLst.setZfFileFee(zf_file_fee);
										originalDljhLst.setZfFee(zf_fee);
										originalDljhLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
										originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
									}else{//冲正
										OriginalDljhLst originalDljhLst2 =  originalDljhLstDAO.queryWhetherDzSuccessAll(originalDljhLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
										if(originalDljhLst2 != null){
											if(originalDljhLst2.getBkChk() == 1){//对账成功
												originalDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												
												if(!originalDljhLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(originalDljhLst.getTradeAmount().toString(), originalDljhLst.getReqMerCode(), instInfo.getGate(), originalDljhLst.getDeductSysTime(), 
															originalDljhLst.getDeductRollbkSysTime(), originalDljhLst.getDeductSysStance(), originalDljhLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, originalDljhLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("超时交易、流水: ",originalDljhLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
											}else if(originalDljhLst2.getBkChk() == 2){ //对账失败
												originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalDljhLst.setBkChk(DataStatus.dz_error);
												originalDljhLst.setWhetherQs(false);
												originalDljhLst.setZfFileFee(zf_file_fee);
												originalDljhLst.setZfFee(zf_fee);
												originalDljhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}else{
												originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalDljhLst.setBkChk(DataStatus.dz_error);
												originalDljhLst.setWhetherQs(false);
												originalDljhLst.setZfFileFee(zf_file_fee);
												originalDljhLst.setZfFee(zf_fee);
												originalDljhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
										}else{
											originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
											//冲正进入差错
											originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
											originalDljhLst.setBkChk(DataStatus.dz_error);
											originalDljhLst.setWhetherQs(false);
											originalDljhLst.setZfFileFee(zf_file_fee);
											originalDljhLst.setZfFee(zf_fee);
											originalDljhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
											originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
										}
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime);
									}
								}else{//不存在
									log.info(stringPingJie.getStringPingJie("超时交易、流水: ",originalDljhLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
									if((originalDljhLst.getTrademsgType() == 2 || originalDljhLst.getTrademsgType() == 18
											|| originalDljhLst.getTrademsgType() == 20 || originalDljhLst.getTrademsgType() == 56 || originalDljhLst.getTrademsgType() == 58) 
											&&  !originalDljhLst.isDeductRollBk()){
										originalDljhLstDAO.updateNoNeedHandle(originalDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, originalDljhLst.getTrademsgType(), originalPepDate);
									}else{
										originalDljhLstDAO.updateNoNeedHandle(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, originalDljhLst.getTrademsgType(), originalPepDate);
									}
								}
							}//失败
							else{
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangDljhLstDAO.findDzFileData(originalDljhLst, oriObject, duizObject, instId, DataStatus.no_riqie, duizhangReqTime);
								String zf_file_fee = dljhDz_map.get(originalDljhLst.getDeductSysStance());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("失败交易、流水: ",originalDljhLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
									if((originalDljhLst.getTrademsgType() == 2 || originalDljhLst.getTrademsgType() == 56) &&
											!originalDljhLst.isDeductRollBk()){//消费or预授权完成
										//修改不需要清算--对账失败
										originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalDljhLst.setWhetherErroeHandle(DataStatus.long_status);
										originalDljhLst.setBkChk(DataStatus.dz_error);
										originalDljhLst.setWhetherQs(false);
										originalDljhLst.setWhetherTk(DataStatus.no_tk);
										originalDljhLst.setZfFileFee(zf_file_fee);
										originalDljhLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
										//修改结算信息
										originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
									}else if((originalDljhLst.getTrademsgType() == 18 || originalDljhLst.getTrademsgType() == 58) && !originalDljhLst.isDeductRollBk()){//撤销
										
										originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
										originalDljhLst.setBkChk(DataStatus.dz_error);
										originalDljhLst.setWhetherQs(false);
										originalDljhLst.setZfFileFee(zf_file_fee);
										originalDljhLst.setZfFee(zf_fee);
										originalDljhLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
										originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime);
									}else{//冲正
										OriginalDljhLst originalDljhLst2 =  originalDljhLstDAO.queryWhetherDzSuccess(originalDljhLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
										if(originalDljhLst2 != null){
											if(originalDljhLst2.getBkChk() == 1){//对账成功
												originalDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												
												if(!originalDljhLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(originalDljhLst.getTradeAmount().toString(), originalDljhLst.getReqMerCode(), instInfo.getGate(), originalDljhLst.getDeductSysTime(), 
															originalDljhLst.getDeductRollbkSysTime(), originalDljhLst.getDeductSysStance(), originalDljhLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, originalDljhLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("失败交易、流水: ",originalDljhLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
											}else if(originalDljhLst2.getBkChk() == 2){ //对账失败
												originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalDljhLst.setBkChk(DataStatus.dz_error);
												originalDljhLst.setWhetherQs(false);
												originalDljhLst.setZfFileFee(zf_file_fee);
												originalDljhLst.setZfFee(zf_fee);
												originalDljhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}else{
												originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalDljhLst.setBkChk(DataStatus.dz_error);
												originalDljhLst.setWhetherQs(false);
												originalDljhLst.setZfFileFee(zf_file_fee);
												originalDljhLst.setZfFee(zf_fee);
												originalDljhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
												originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
										}else{
											originalDljhLstDAO.updateCleanAndError(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
											//冲正进入差错
											originalDljhLst.setWhetherErroeHandle(DataStatus.short_status);
											originalDljhLst.setBkChk(DataStatus.dz_error);
											originalDljhLst.setWhetherQs(false);
											originalDljhLst.setZfFileFee(zf_file_fee);
											originalDljhLst.setZfFee(zf_fee);
											originalDljhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalDljhLst));
											originalDljhLstDAO.updateSettleInfo(originalDljhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalDljhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
										}
										duizhangDljhLstDAO.updateClean(originalDljhLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime);
									}
								}else{//不存在
									log.info(stringPingJie.getStringPingJie("失败交易、流水: ",originalDljhLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
									if((originalDljhLst.getTrademsgType() == 2 || originalDljhLst.getTrademsgType() == 18
											|| originalDljhLst.getTrademsgType() == 20 || originalDljhLst.getTrademsgType() == 56 || originalDljhLst.getTrademsgType() == 58) 
											&&  !originalDljhLst.isDeductRollBk()){
										originalDljhLstDAO.updateNoNeedHandle(originalDljhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, originalDljhLst.getTrademsgType(), originalPepDate);
									}else{
										originalDljhLstDAO.updateNoNeedHandle(originalDljhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, originalDljhLst.getTrademsgType(), originalPepDate);
									}
								}
							}
						}
					}
					
					log.info(stringPingJie.getStringPingJie("开始统计",duizhangReqTime ,"日期对账成功数据"));
					Map<String, Object> mapMerBasic = Backstage.getInstance().getMerBasicList();
					tradeLstDAO = TradeLstDAO.getInstance();
					log.info(stringPingJie.getStringPingJie("开始统计",duizhangReqTime,"日期T+1日统计数据"));
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
										log.info(stringPingJie.getStringPingJie("大连交行T+1日  ",dy_merCode,"-->",settleMerCode,"保存",isSuccess));
									}else{
										log.info(stringPingJie.getStringPingJie("大连交行T+1日  ",dy_merCode,"-->",settleMerCode,"失败",isSuccess));
									}
								}else{
									log.info(stringPingJie.getStringPingJie("查询大连交行交易日期",deductStlmDate,"交易",settleMerCode,"结算商户为空"));
								}
							}else{
								Object objMer = mapMerBasic.get(dy_merCode);
								if(objMer != null){
									MerBasic merBasic = (MerBasic)objMer;
									Object[] obj = tradeLstDAO.findPosChanneltotall(deductStlmDate, dy_merCode, DataStatus.clean, originalDataTableName);
									boolean isSuccess = tradeLstDAO.merchantSettleStatisticsUtil(deductStlmDate,dy_merCode, DataStatus.clean, originalDataTableName, instId, merBasic.getMerCategory(),inst_type,obj,settle_flag,bank_id);
									if(isSuccess){
										log.info(stringPingJie.getStringPingJie("大连交行T+1日  ",dy_merCode,"保存",isSuccess));
									}else{
										log.info(stringPingJie.getStringPingJie("大连交行T+1日  ",dy_merCode,"失败",isSuccess));
									}
								}else{
									log.info(stringPingJie.getStringPingJie("查询大连交行交易日期",deductStlmDate,"交易",dy_merCode,"结算商户为空"));
								}
							}
						}
					}
					
					log.info("开始汇总大连交行对账之后数据汇总");
					channelDzCollectDAO = cn.com.chinaebi.dz.object.dao.ChannelDzCollectDAO.getInstance();
					int count = channelDzCollectDAO.savePosChannelDzCollect(instInfo, deductStlmDate,map,mapMerBasic);
					log.info(stringPingJie.getStringPingJie("大连交行对账之后汇总条数",count));
					log.info("结束汇总大连交行对账之后数据汇总");
					
					ExecuteNode executeNode  = executeNodeDAO.findExecuteNodeData(deductStlmDate, instInfo.getId().getInstId(), instInfo.getId().getInstType());
					if(executeNode != null){
						/**
						 * 0:未执行、1:执行成功、2:执行失败、3:对账单数据不存在、4:原始交易数据不存在
						 * 0、3、4:表示数据没有勾兑过,不会产生对账数据状态影响
						 */
						Integer dzHandle = executeNode.getDzHandle();
						if(dzHandle != 0 && dzHandle != 3 && dzHandle != 4){
							log.info("开始检测大连交行原始交易差错重对账处理");
							List<ErrorDataLst> oriList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_original, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							if(oriList != null && oriList.size() > 0){
								log.info(stringPingJie.getStringPingJie("重对账检测到大连交行原始交易差错条数为:",oriList.size()));
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
								log.info("没有到检测大连交行原始交易差错数据");
							}
							
							log.info("开始检测大连交行对账单交易数据差错重对账处理");
							List<ErrorDataLst> duizList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_duizhang, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							if(duizList != null && duizList.size() > 0){
								log.info(stringPingJie.getStringPingJie("重对账检测大连交行对账单交易数据差错条数为:",duizList.size()));
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
								log.info("没有到检测大连交行对账单交易数据差错数据");
							}
						}else{
							log.info("第一次执行,无需处理重对账差错数据核对");
						}
					}
					
					log.info("开始检测大连交行对账文件是否有可疑交易数据");
					//处理大连交行对账文件未对账的可以交易数据
					List<DuizhangDljhLst> dljhList = duizhangDljhLstDAO.findDateData(duizhangReqTime, DataStatus.not_dz);
					if(dljhList != null && dljhList.size() > 0){
						log.error(stringPingJie.getStringPingJie("大连交行对账文件可疑交易数据总条数是:",dljhList.size()));
						for (DuizhangDljhLst duizhangDljhLst:dljhList) {
							errorDataLstDAO.saveErrorData(new ErrorDataLst(duizhangDljhLst));
						}
					}else{
						log.info("没有检测到银联CUPS对账文件的可疑交易数据");
					}
				}else{
					throw new OriginalDataNotFoundException("没有获取到电银大连交行 " +originalPepDate + " 日期的交易数据");
				}
			}catch (Exception e) {
				log.error("大连交行对账错误: "+e);
				throw e;
			}
		}else{
			throw new DuizhangNotFoundException("没有获取到  "+duizhangReqTime+"  日期的大连交行对账文件数据");
		}
	}

	@Override
	public void onLineTradeDzDeal(Integer startDate,
			Integer endDate, String deductStlmDate,
			Integer bank_gate,Integer instId,Integer instType, Integer flagStatus)
			throws OriginalDataNotFoundException, DuizhangNotFoundException,
			Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tradeErrorDzDeal(String originalPepDate,
			String duizhangBeijingReqTime, Integer instId, Integer flagStatus)
			throws OriginalDataNotFoundException, DuizhangNotFoundException,
			Exception {
		// TODO Auto-generated method stub
		
	}

}
