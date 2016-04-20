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
import cn.com.chinaebi.dz.object.DuizhangCupsLst;
import cn.com.chinaebi.dz.object.DuizhangErrorCupsLst;
import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.ExecuteNode;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.OriginalCupsLst;
import cn.com.chinaebi.dz.object.RiqieCupsLst;
import cn.com.chinaebi.dz.object.dao.DuizhangCupsLstDAO;
import cn.com.chinaebi.dz.object.dao.DuizhangErrorCupsLstDAO;
import cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO;
import cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO;
import cn.com.chinaebi.dz.object.dao.InstRateDAO;
import cn.com.chinaebi.dz.object.dao.OriginalCupsLstDAO;
import cn.com.chinaebi.dz.object.dao.RiqieCupsLstDAO;
import cn.com.chinaebi.dz.object.dao.SettleMerchantConfigDAO;
import cn.com.chinaebi.dz.object.dao.SettleMerchantMatchTableDAO;
import cn.com.chinaebi.dz.object.dao.TradeLstDAO;
import cn.com.chinaebi.dz.object.dao.YlcupsErrorEntryDAO;
import cn.com.chinaebi.dz.object.dao.iface.ChannelDzCollectDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.StringPingJie;

/**
 * 
 * 银联CUPS交易对账处理类
 * @author zhu.hongliang
 */
public class CupsTradeDzHandler implements TradeDzHandler {
	
	private Log log = LogFactory.getLog(getClass());
	private cn.com.chinaebi.dz.object.dao.iface.OriginalCupsLstDAO originalCupsLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.DuizhangCupsLstDAO duizhangCupsLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.RiqieCupsLstDAO riqiecupLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorDataLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.YlcupsErrorEntryDAO ylcupsErrorEntryDAO;
	private cn.com.chinaebi.dz.object.dao.iface.DuizhangErrorCupsLstDAO duizhangErrorCupsLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.InstRateDAO instRateDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO;
	private ChannelDzCollectDAO channelDzCollectDAO;
	
	private final Integer inst_type = DataStatus.inst_type_0;
	private final String columnName = "trade_id";
	private final Integer whether_flag = 1;

	/**
	 * 	 a:根据清算日期获取到riqie_cups_lst获取日切交易数据
	 *   b:逐笔计算商户手续费
	 *   c:对账流程
	 *   		成功交易
	 *   			对账文件存在
	 *   				消费
	 *   					对账成功-进资金流水(消费-支付)
	 *   				消费退货
	 *              	   	 对账成功-进资金流水(消费-冲正)
	 *   				消费撤销
	 *                  	对账成功-进资金流水(消费-冲正)
	 *   				冲正
	 *   					对账成功-进资金流水(消费-冲正)
	 *   			对账文件不存在
	 *   				消费
	 *   					对账失败-进入差错
	 *   				消费退货
	 *   					对账失败-消费退货、消费进入差错
	 *   				消费撤销
	 *   					对账失败-消费撤销、消费进入差错
	 *   				冲正
	 *   					对账失败-消费、冲正进入差错
	 *   		失败交易
	 *            	对账文件存在
	 *            		消费
	 *   					对账失败-进入差错
	 *   				消费退货
	 *   					对账失败-消费退货、消费进入差错
	 *   				消费撤销
	 *   					对账失败-消费撤销、消费进入差错
	 *            		冲正
	 *            			核对原笔是否对账成功
	 *            				对账成功-标记为对账成功
	 *            				对账失败-标记为对账失败-进入差错
	 *           	对账文件不存在
	 *           		所有交易标记为无需对账
	 *   		超时交易
	 *   			对账文件存在
	 *            		消费
	 *   					对账失败-进入差错
	 *   				消费退货
	 *   					对账失败-消费退货、消费进入差错
	 *   				消费撤销
	 *   					对账失败-消费撤销、消费进入差错
	 *            		冲正
	 *            			核对原笔是否对账成功
	 *            				对账成功-标记为对账成功
	 *            				对账失败-标记为对账失败-进入差错
	 *           	对账文件不存在
	 *           		所有交易标记为无需对账
	 */
	@Override
	public synchronized void tradeDzDeal(String originalReqTime,String duizhangCupsReqTime,String deductStlmDate,Boolean innertTradeHandler,
			Integer bank_id,Integer instId,Integer instType,Integer flagStatus) 
			throws OriginalDataNotFoundException,DuizhangNotFoundException,Exception{
		log.info("获取原始交易数据日期:"+originalReqTime);
		log.info("获取对账文件数据日期:"+duizhangCupsReqTime);
		log.info("日切数据日期:"+deductStlmDate);
		SimpleDateFormat dateFormat = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_6);
		originalCupsLstDAO = OriginalCupsLstDAO.getInstance();
		duizhangCupsLstDAO = DuizhangCupsLstDAO.getInstance();
		riqiecupLstDAO = RiqieCupsLstDAO.getInstance();
		errorDataLstDAO = ErrorDataLstDAO.getInstance();
		instRateDAO = InstRateDAO.getInstance();
		executeNodeDAO = ExecuteNodeDAO.getInstance();
		tradeLstDAO = TradeLstDAO.getInstance();
		StringPingJie stringPingJie = StringPingJie.getInstance();
		
		Map<String, String> cupsDz_map = duizhangCupsLstDAO.findDzFileData(duizhangCupsReqTime);
		if(cupsDz_map != null && cupsDz_map.keySet() != null && cupsDz_map.keySet().size() > 0){
			Double zf_fee = 0d;
			Integer whetherReturnFee = 0;
			Integer instRateType = 0;
			Object[] instRateObj = instRateDAO.getInstRateType(instId, inst_type);
			Map<String, Boolean> rateMap = instRateDAO.findChanelMccRateConf(instId, inst_type);
			int rateMapSize = rateMap == null ? 0 : rateMap.keySet().size();
			InstInfoPK infoPK = new InstInfoPK();
			infoPK.setInstId(instId);
			infoPK.setInstType(inst_type);
			InstInfo instInfo = Backstage.getInstance().getInstInfo(infoPK);
			String originalDataTableName = instInfo.getBank().getOriginalDataTableName();
			String riqieOriginalTableName = instInfo.getBank().getRiqieOriginalTableName();
			String dzDataTableName = instInfo.getBank().getDzDataTableName();
			try {
				List<RiqieCupsLst> listRiqieCupsLsts = riqiecupLstDAO.findRiqieCupsLst(deductStlmDate);
				if(listRiqieCupsLsts != null && listRiqieCupsLsts.size() > 0){
					log.info("开始处理电银银联CUPS日切数据对账...");
					for (RiqieCupsLst riqieCupsLst:listRiqieCupsLsts) {
						whetherReturnFee = 0;
						instRateType = 0;
						zf_fee = 0d;
						/**
						 * 计算商户手续费
						tradeAmount = riqieCupsLst.getTradeAmount().toString().replaceAll(",", "").trim();
						if(riqieCupsLst.getTrademsgType() == 2 && StringUtils.equals(riqieCupsLst.getReqProcess(), "480000")  && StringUtils.isNotBlank(riqieCupsLst.getTradeFee())){
							String tradeFee = riqieCupsLst.getTradeFee().substring(1);
							mer_fee = Double.valueOf(tradeFee)/100;
						}else
							mer_fee = PoundageCalculate.getMerFee(tradeAmount,riqieCupsLst.getReqMerCode(),instInfo.getGate());
						*/
						//计算银行手续费
						if(instRateObj != null){
							whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
							instRateType = Integer.valueOf(instRateObj[0].toString());
							if(instRateType != 2){
								zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(riqieCupsLst.getTradeAmount()), riqieCupsLst.getOutAccount(), riqieCupsLst.getDeductSysId(), inst_type, riqieCupsLst.getDeductMerCode());
							}
							//按MCC计算
							if(instRateType == 1){
								String mcc_code = PoundageCalculate.mccCodeSubstring(riqieCupsLst.getDeductMerCode());
								if(StringUtils.isNotBlank(mcc_code)){
									boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
									if(riqieCupsLst.getTrademsgType() == 20){//退货交易
										if(whetherReturnFee == 0 && !lw_flag){//不退还
											zf_fee = 0d;
										}else if(whetherReturnFee == 1 && lw_flag){//退还
											zf_fee = 0d;
										}
									}
								}
							}else if(instRateType == 3){//按固定费率
								//退货交易-不退还手续费
								if(riqieCupsLst.getTrademsgType() != null && riqieCupsLst.getTrademsgType() == 20 && whetherReturnFee == 0){
									zf_fee = 0d;
								}
							}
						}
						
						//成功
						if(((StringUtils.equals(riqieCupsLst.getDeductSysResponse(), "00") && riqieCupsLst.getDeductSysResponse() != null)||
								(StringUtils.equals(riqieCupsLst.getDeductRollBkResponse(), "00") && riqieCupsLst.getDeductRollBkResponse() != null))
								&& riqieCupsLst.getTrademsgType() != null){
							//查询对账文件中是否存在
//							String zf_file_fee = duizhangCupsLstDAO.findDzFileData(riqieCupsLst,oriObject, duizObject,instId,DataStatus.riqie,duizhangCupsReqTime);
							String zf_file_fee = cupsDz_map.get(riqieCupsLst.getDeductSysStance());
							if(StringUtils.isNotBlank(zf_file_fee)){
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("成功交易、流水:",riqieCupsLst.getDeductSysStance()," 对账成功,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieCupsLst.getTrademsgType() == 2 ||  riqieCupsLst.getTrademsgType() == 56 || riqieCupsLst.getTrademsgType() == 110) &&
										!riqieCupsLst.isDeductRollBk()){//消费or预授权完成
									riqiecupLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改原始交易日切对账状态
									originalCupsLstDAO.updateCleanRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改计算手续费
									riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
								}else if(riqieCupsLst.getTrademsgType() == 20){ //退货
									riqiecupLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改原始交易日切对账状态
									originalCupsLstDAO.updateCleanRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改计算手续费
									riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
								}else if((riqieCupsLst.getTrademsgType() == 18 || riqieCupsLst.getTrademsgType() == 58)
										&& !riqieCupsLst.isDeductRollBk()){//撤销or预授权完成撤销
									//当前这笔需要清算
									riqiecupLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalCupsLstDAO.updateCleanRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									//修改计算手续费
									riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
								}else{//冲正
									//核对原笔对账结果
									OriginalCupsLst originalCupsLst = originalCupsLstDAO.queryWhetherDzSuccess(riqieCupsLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalCupsLst != null){
										if(originalCupsLst.getBkChk() == 1){//对账成功
											//当前这笔需要清算
											riqiecupLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalCupsLstDAO.updateCleanRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										}else if(originalCupsLst.getBkChk() == 2){//对账失败
											
											//修改冲正不需要清算--对账失败
											riqiecupLstDAO.updateOriginalError(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, DataStatus.deductRollBk, deductStlmDate);
											originalCupsLstDAO.updateOriginalError(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, DataStatus.deductRollBk, deductStlmDate);
											
											//修改计算手续费
											riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											
											//冲正进入差错
											riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieCupsLst.setBkChk(DataStatus.dz_error);
											riqieCupsLst.setWhetherQs(false);
											riqieCupsLst.setWhetherTk(DataStatus.no_tk);
											riqieCupsLst.setZfFileFee(zf_file_fee);
											riqieCupsLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
											
										}else{
											//当前这笔需要清算
											riqiecupLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalCupsLstDAO.updateCleanRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										}
									}else{
										//当前这笔需要清算
										riqiecupLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
										originalCupsLstDAO.updateCleanRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
										//修改计算手续费
										riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
									}
									duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangCupsReqTime);
								}
							}else{//成功交易对账单中不存在-未对账
								log.info(stringPingJie.getStringPingJie("成功交易、流水:",riqieCupsLst.getDeductSysStance()," 对账单不存在-未对账,支付手续费:",zf_fee));
								if(riqieCupsLst.isDeductRollBk()){
									riqiecupLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_clean,DataStatus.not_dz,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
									originalCupsLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_clean,DataStatus.not_dz,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
								}else{
									riqiecupLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.no_clean,DataStatus.not_dz,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.no_clean,DataStatus.not_dz,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
								}
							}
						}//超时
						else if(((riqieCupsLst.getDeductResult() != null && riqieCupsLst.getDeductResult() == 1) || 
								(riqieCupsLst.getDeductRollBkResult() != null && riqieCupsLst.getDeductRollBkResult() == 1))
								&& riqieCupsLst.getTrademsgType() != null){
							//查询对账文件中是否存在
							//String zf_file_fee = duizhangCupsLstDAO.findDzFileData(riqieCupsLst,oriObject, duizObject,instId,DataStatus.riqie,duizhangCupsReqTime);
							String zf_file_fee = cupsDz_map.get(riqieCupsLst.getDeductSysStance());
							if(StringUtils.isNotBlank(zf_file_fee)){//存在
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("超时交易、流水:",riqieCupsLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieCupsLst.getTrademsgType() == 2 || riqieCupsLst.getTrademsgType() == 56 || riqieCupsLst.getTrademsgType() == 110) &&
										!riqieCupsLst.isDeductRollBk()){//消费
									
									riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.long_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.long_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									
									//修改计算手续费
									riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									
									riqieCupsLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieCupsLst.setBkChk(DataStatus.dz_error);
									riqieCupsLst.setWhetherQs(false);
									riqieCupsLst.setWhetherTk(DataStatus.no_tk);
									riqieCupsLst.setZfFileFee(zf_file_fee);
									riqieCupsLst.setZfFee(zf_fee);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
									duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
								}else if(riqieCupsLst.getTrademsgType() == 20 && !riqieCupsLst.isDeductRollBk()){//退货
									
									riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									
									//修改计算手续费
									riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									
									riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
									riqieCupsLst.setBkChk(DataStatus.dz_error);
									riqieCupsLst.setWhetherQs(false);
									riqieCupsLst.setWhetherTk(DataStatus.tk);
									riqieCupsLst.setZfFileFee(zf_file_fee);
									riqieCupsLst.setZfFee(zf_fee);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
									
									duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
								}else if((riqieCupsLst.getTrademsgType() == 18 || riqieCupsLst.getTrademsgType() == 58) && !riqieCupsLst.isDeductRollBk()){//撤销
									
									riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
									
									//修改计算手续费
									riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									
									riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
									riqieCupsLst.setBkChk(DataStatus.dz_error);
									riqieCupsLst.setWhetherQs(false);
									riqieCupsLst.setWhetherTk(DataStatus.no_tk);
									riqieCupsLst.setZfFileFee(zf_file_fee);
									riqieCupsLst.setZfFee(zf_fee);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
									
									duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
								}else{//冲正
									//核对原笔对账结果
									OriginalCupsLst originalCupsLst = originalCupsLstDAO.queryWhetherDzSuccess(riqieCupsLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalCupsLst != null){
										if(originalCupsLst.getBkChk() == 1){ //对账成功
											//当前这笔需要清算
											riqiecupLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalCupsLstDAO.updateCleanRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											//修改计算手续费
											riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											
											if(!riqieCupsLst.isWhetherAccessStance()){
												boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieCupsLst.getTradeAmount().toString(), riqieCupsLst.getReqMerCode(), instInfo.getGate(), riqieCupsLst.getDeductSysTime(), 
														riqieCupsLst.getDeductRollbkSysTime(), riqieCupsLst.getDeductSysStance(), riqieCupsLst.isDeductRollBk(), 
														instId, inst_type, deductStlmDate, bank_id, riqieCupsLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
												if(update_flag){
													log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieCupsLst.getDeductSysStance()," 对账成功-进入资金流水"));
												}
											}
											
										}else if(originalCupsLst.getBkChk() == 2){//对账失败
											
											//冲正交易进入差错
											riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											
											//修改计算手续费
											riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											
											riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieCupsLst.setBkChk(DataStatus.dz_error);
											riqieCupsLst.setWhetherQs(false);
											riqieCupsLst.setWhetherTk(DataStatus.no_tk);
											riqieCupsLst.setZfFileFee(zf_file_fee);
											riqieCupsLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
											
										}else{
											//冲正交易进入差错
											riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											
											//修改计算手续费
											riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											
											riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieCupsLst.setBkChk(DataStatus.dz_error);
											riqieCupsLst.setWhetherQs(false);
											riqieCupsLst.setWhetherTk(DataStatus.no_tk);
											riqieCupsLst.setZfFileFee(zf_file_fee);
											riqieCupsLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
										}
									}else{
										//冲正交易进入差错
										riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
										originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
										
										//修改计算手续费
										riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										
										riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieCupsLst.setBkChk(DataStatus.dz_error);
										riqieCupsLst.setWhetherQs(false);
										riqieCupsLst.setWhetherTk(DataStatus.no_tk);
										riqieCupsLst.setZfFileFee(zf_file_fee);
										riqieCupsLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
									}
									
									duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangCupsReqTime);
								}
							}else{//不存在
								log.info(stringPingJie.getStringPingJie("超时交易、流水:",riqieCupsLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
								if((riqieCupsLst.getTrademsgType() == 2 || riqieCupsLst.getTrademsgType() == 18
										|| riqieCupsLst.getTrademsgType() == 20 || riqieCupsLst.getTrademsgType() == 56 || riqieCupsLst.getTrademsgType() == 58) 
										&&  !riqieCupsLst.isDeductRollBk()){
									riqiecupLstDAO.updateNoNeedHandle(riqieCupsLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieCupsLst.getTrademsgType(), deductStlmDate);
									originalCupsLstDAO.updateNoNeedHandle(riqieCupsLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieCupsLst.getTrademsgType(), deductStlmDate);
								}else{
									riqiecupLstDAO.updateNoNeedHandle(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieCupsLst.getTrademsgType(), deductStlmDate);
									originalCupsLstDAO.updateNoNeedHandle(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieCupsLst.getTrademsgType(), deductStlmDate);
								}
							}
						}//失败
						else{
							if(riqieCupsLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangCupsLstDAO.findDzFileData(riqieCupsLst,oriObject, duizObject,instId,DataStatus.riqie,duizhangCupsReqTime);
								String zf_file_fee = cupsDz_map.get(riqieCupsLst.getDeductSysStance());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									
									log.info(stringPingJie.getStringPingJie("失败交易、流水:",riqieCupsLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
									if((riqieCupsLst.getTrademsgType() == 2 || riqieCupsLst.getTrademsgType() == 56 || riqieCupsLst.getTrademsgType() == 110) &&
											!riqieCupsLst.isDeductRollBk()){//消费
										riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.long_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.long_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										
										//修改计算手续费
										riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										
										riqieCupsLst.setWhetherErroeHandle(DataStatus.long_status);
										riqieCupsLst.setBkChk(DataStatus.dz_error);
										riqieCupsLst.setWhetherQs(false);
										riqieCupsLst.setWhetherTk(DataStatus.no_tk);
										riqieCupsLst.setZfFileFee(zf_file_fee);
										riqieCupsLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
										duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									}else if(riqieCupsLst.getTrademsgType() == 20 && !riqieCupsLst.isDeductRollBk()){//退货
										
										riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										
										//修改计算手续费
										riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										
										riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieCupsLst.setBkChk(DataStatus.dz_error);
										riqieCupsLst.setWhetherQs(false);
										riqieCupsLst.setWhetherTk(DataStatus.tk);
										riqieCupsLst.setZfFileFee(zf_file_fee);
										riqieCupsLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
										
										duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									}else if((riqieCupsLst.getTrademsgType() == 18 || riqieCupsLst.getTrademsgType() == 58) && !riqieCupsLst.isDeductRollBk()){//撤销
										
										riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,deductStlmDate);
										
										//修改计算手续费
										riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										
										riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieCupsLst.setBkChk(DataStatus.dz_error);
										riqieCupsLst.setWhetherQs(false);
										riqieCupsLst.setWhetherTk(DataStatus.no_tk);
										riqieCupsLst.setZfFileFee(zf_file_fee);
										riqieCupsLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
										
										duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									}else{//冲正
										
										//核对原笔对账结果
										OriginalCupsLst originalCupsLst = originalCupsLstDAO.queryWhetherDzSuccess(riqieCupsLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
										if(originalCupsLst != null){
											if(originalCupsLst.getBkChk() == 1){ //对账成功
												//当前这笔需要清算
												riqiecupLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												originalCupsLstDAO.updateCleanRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												
												//修改计算手续费
												riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												
												if(!riqieCupsLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieCupsLst.getTradeAmount().toString(), riqieCupsLst.getReqMerCode(), instInfo.getGate(), riqieCupsLst.getDeductSysTime(), 
															riqieCupsLst.getDeductRollbkSysTime(), riqieCupsLst.getDeductSysStance(), riqieCupsLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, riqieCupsLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieCupsLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
											}else if(originalCupsLst.getBkChk() == 2){//对账失败
												
												//冲正交易进入差错
												riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												
												//修改计算手续费
												riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												
												riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieCupsLst.setBkChk(DataStatus.dz_error);
												riqieCupsLst.setWhetherQs(false);
												riqieCupsLst.setWhetherTk(DataStatus.no_tk);
												riqieCupsLst.setZfFileFee(zf_file_fee);
												riqieCupsLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
												
											}else{
												//冲正交易进入差错
												riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												
												//修改计算手续费
												riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												
												riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieCupsLst.setBkChk(DataStatus.dz_error);
												riqieCupsLst.setWhetherQs(false);
												riqieCupsLst.setWhetherTk(DataStatus.no_tk);
												riqieCupsLst.setZfFileFee(zf_file_fee);
												riqieCupsLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
											}
										}else{
											//冲正交易进入差错
											riqiecupLstDAO.updateCleanAndError(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											originalCupsLstDAO.updateCleanAndErrorRiqie(riqieCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,riqieCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
											
											//修改计算手续费
											riqiecupLstDAO.updateSettleInfo(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalCupsLstDAO.updateSettleInfoRiqie(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieCupsLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											
											riqieCupsLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieCupsLst.setBkChk(DataStatus.dz_error);
											riqieCupsLst.setWhetherQs(false);
											riqieCupsLst.setWhetherTk(DataStatus.no_tk);
											riqieCupsLst.setZfFileFee(zf_file_fee);
											riqieCupsLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieCupsLst));
										}
										
										duizhangCupsLstDAO.updateClean(riqieCupsLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangCupsReqTime);
									}
								}else{//不存在
									log.info(stringPingJie.getStringPingJie("失败交易、流水:",riqieCupsLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
									if((riqieCupsLst.getTrademsgType() == 2 || riqieCupsLst.getTrademsgType() == 18
											|| riqieCupsLst.getTrademsgType() == 20 || riqieCupsLst.getTrademsgType() == 56 || riqieCupsLst.getTrademsgType() == 58) 
											&&  !riqieCupsLst.isDeductRollBk()){
										riqiecupLstDAO.updateNoNeedHandle(riqieCupsLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieCupsLst.getTrademsgType(), deductStlmDate);
										originalCupsLstDAO.updateNoNeedHandle(riqieCupsLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieCupsLst.getTrademsgType(), deductStlmDate);
									}else{
										riqiecupLstDAO.updateNoNeedHandle(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieCupsLst.getTrademsgType(), deductStlmDate);
										originalCupsLstDAO.updateNoNeedHandle(riqieCupsLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieCupsLst.getTrademsgType(), deductStlmDate);
									}
								}
							}
						}
					}
				}else{
					log.error("没有获取到  "+deductStlmDate+"  日期的银联CUPS原始交易日切数据");
				}
			} catch (Exception e) {
				log.error("银联CUPS日切对账错误:"+e);
			}
		
			try {
				
				List<OriginalCupsLst> listoriginalCupsLsts = originalCupsLstDAO.findOriginalCupsbankLst(originalReqTime);
				if(listoriginalCupsLsts != null && listoriginalCupsLsts.size() > 0){
					log.info("开始处理电银银联CUPS数据对账...");
					for (OriginalCupsLst originalCupsLst:listoriginalCupsLsts) {
						zf_fee = 0d;
						whetherReturnFee = 0; 
						instRateType = 0;
						String _deductStlmDate = dateFormat.format(originalCupsLst.getDeductStlmDate());
						String date = DYDataUtil.getNextDateString(originalReqTime);
						if(StringUtils.equalsIgnoreCase(_deductStlmDate,date)){
							//处理日切交易数据
							log.info(stringPingJie.getStringPingJie("银联CUPS日切数据流水:",originalCupsLst.getDeductSysStance()," 日期:",_deductStlmDate));
							originalCupsLstDAO.updateDataRiqie(originalCupsLst.getDeductSysStance(), DataStatus.riqie,_deductStlmDate);
							originalCupsLst.setWhetherRiqie(true);
							riqiecupLstDAO.saveRiqieCupsLst(new RiqieCupsLst(originalCupsLst),flagStatus);
						}else{
							/**
							 * 计算商户手续费
							tradeAmount = originalCupsLst.getTradeAmount().toString().replaceAll(",", "").trim();
							if(originalCupsLst.getTrademsgType() == 2 && StringUtils.equals(originalCupsLst.getReqProcess(), "480000") && StringUtils.isNotBlank(originalCupsLst.getTradeFee())){
								String tradeFee = originalCupsLst.getTradeFee().substring(1);
								mer_fee = Double.valueOf(tradeFee)/100;
							}else
								mer_fee = PoundageCalculate.getMerFee(tradeAmount,originalCupsLst.getReqMerCode(),instInfo.getGate());
							*/
							//计算银行手续费
							if(instRateObj != null){
								whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
								instRateType = Integer.valueOf(instRateObj[0].toString());
								if(instRateType != 2){
									zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(originalCupsLst.getTradeAmount()), originalCupsLst.getOutAccount(), originalCupsLst.getDeductSysId(), inst_type, originalCupsLst.getDeductMerCode());
								}
								//按MCC计算
								if(instRateType == 1){
									String mcc_code = PoundageCalculate.mccCodeSubstring(originalCupsLst.getDeductMerCode());
									if(StringUtils.isNotBlank(mcc_code)){
										boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
										if(originalCupsLst.getTrademsgType() == 20){//退货交易
											if(whetherReturnFee == 0 && !lw_flag){//不退还
												zf_fee = 0d;
											}else if(whetherReturnFee == 1 && lw_flag){//退还
												zf_fee = 0d;
											}
										}
									}
								}else if(instRateType == 3){//按固定费率
									//退货交易-不退还手续费
									if(originalCupsLst.getTrademsgType() != null && originalCupsLst.getTrademsgType() == 20 && whetherReturnFee == 0){
										zf_fee = 0d;
									}
								}
							}
							
							//成功
							if(((StringUtils.equals(originalCupsLst.getDeductSysResponse(), "00") && originalCupsLst.getDeductSysResponse() != null)||
									(StringUtils.equals(originalCupsLst.getDeductRollBkResponse(), "00") && originalCupsLst.getDeductRollBkResponse() != null))
									&& originalCupsLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangCupsLstDAO.findDzFileData(originalCupsLst,oriObject, duizObject,instId,DataStatus.no_riqie,duizhangCupsReqTime);
								String zf_file_fee = cupsDz_map.get(originalCupsLst.getDeductSysStance());
								if(StringUtils.isNotBlank(zf_file_fee)){
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("成功交易、流水:",originalCupsLst.getDeductSysStance()," 对账成功,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
									if((originalCupsLst.getTrademsgType() == 2 || originalCupsLst.getTrademsgType() == 56 || originalCupsLst.getTrademsgType() == 110) &&
											!originalCupsLst.isDeductRollBk()){//消费or预授权完成
										originalCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
										//修改手续费
										originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);
										duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									}else if(originalCupsLst.getTrademsgType() == 20){ //退货
										originalCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
										//修改手续费
										originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);
										duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									}else if((originalCupsLst.getTrademsgType() == 18 || originalCupsLst.getTrademsgType() == 58) && !originalCupsLst.isDeductRollBk()){//撤销
										//当前这笔需要清算
										originalCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.clean,DataStatus.dz_success,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
										//修改手续费
										originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);

										duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									
									}else{//冲正
										
										//核对原笔对账结果
										OriginalCupsLst originalCupsLst2 = originalCupsLstDAO.queryWhetherDzSuccess(originalCupsLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
										if(originalCupsLst2 != null){
											if(originalCupsLst2.getBkChk() == 1){//对账成功
												//当前这笔需要清算
												originalCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
												//修改手续费
												originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
												
												//修改原笔需要清算 对账成功
												originalCupsLstDAO.updateClean(originalCupsLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,DataStatus.no_deductRollBk,originalReqTime);
											}else if(originalCupsLst2.getBkChk() == 2){//对账失败
												
												//修改冲正不需要清算--对账失败
												originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,deductStlmDate);
												//修改手续费
												originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
												
												//冲正进入差错
												originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
												originalCupsLst.setBkChk(DataStatus.dz_error);
												originalCupsLst.setWhetherQs(false);
												originalCupsLst.setWhetherTk(DataStatus.no_tk);
												originalCupsLst.setZfFileFee(zf_file_fee);
												originalCupsLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
												
											}else{
												//当前这笔需要清算
												originalCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
												//修改手续费
												originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
											}
										}else{
											//当前这笔需要清算
											originalCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
											//修改手续费
											originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
										}
										duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangCupsReqTime);
									}
									
								}else{//成功交易对账单中不存在-未对账
									log.info(stringPingJie.getStringPingJie("成功交易、流水:",originalCupsLst.getDeductSysStance()," 对账单不存在-未对账,支付手续费:",zf_fee));
									if(originalCupsLst.isDeductRollBk()){
										originalCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.no_clean,DataStatus.not_dz,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
									}else{
										originalCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.no_clean,DataStatus.not_dz,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
									}
								}
							}//超时
							else if(((originalCupsLst.getDeductResult() != null && originalCupsLst.getDeductResult() == 1) || 
									(originalCupsLst.getDeductRollBkResult() != null && originalCupsLst.getDeductRollBkResult() == 1))
									&& originalCupsLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangCupsLstDAO.findDzFileData(originalCupsLst,oriObject, duizObject,instId,DataStatus.no_riqie,duizhangCupsReqTime);
								String zf_file_fee = cupsDz_map.get(originalCupsLst.getDeductSysStance());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("超时交易、流水:",originalCupsLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
									if((originalCupsLst.getTrademsgType() == 2 || originalCupsLst.getTrademsgType() == 56 || originalCupsLst.getTrademsgType() == 110) &&
											!originalCupsLst.isDeductRollBk()){//消费
										originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductSysStance(),DataStatus.no_clean,DataStatus.long_status,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
										//修改手续费
										originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);
										
										originalCupsLst.setWhetherErroeHandle(DataStatus.long_status);
										originalCupsLst.setBkChk(DataStatus.dz_error);
										originalCupsLst.setWhetherQs(false);
										originalCupsLst.setWhetherTk(DataStatus.no_tk);
										originalCupsLst.setZfFileFee(zf_file_fee);
										originalCupsLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
										duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									}else if(originalCupsLst.getTrademsgType() == 20){//退货
										originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductSysStance(),DataStatus.no_clean,DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
										//修改手续费
										originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);
										
										originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
										originalCupsLst.setBkChk(DataStatus.dz_error);
										originalCupsLst.setWhetherQs(false);
										originalCupsLst.setWhetherTk(DataStatus.tk);
										originalCupsLst.setZfFileFee(zf_file_fee);
										originalCupsLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
										
										duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									}else if((originalCupsLst.getTrademsgType() == 18 || originalCupsLst.getTrademsgType() == 58) && !originalCupsLst.isDeductRollBk()){//撤销
										originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductSysStance(),DataStatus.no_clean,DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
										//修改手续费
										originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);
										
										originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
										originalCupsLst.setBkChk(DataStatus.dz_error);
										originalCupsLst.setWhetherQs(false);
										originalCupsLst.setWhetherTk(DataStatus.no_tk);
										originalCupsLst.setZfFileFee(zf_file_fee);
										originalCupsLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
										
										duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
									}else{//冲正
										
										//核对原笔对账结果
										OriginalCupsLst originalCupsLst2 = originalCupsLstDAO.queryWhetherDzSuccess(originalCupsLst.getReqSysStance(), originalReqTime, DataStatus.no_deductRollBk);
										if(originalCupsLst2 != null){
											if(originalCupsLst2.getBkChk() == 1){ //对账成功
												//当前这笔需要清算
												originalCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
												//修改手续费
												originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
												
												//修改原笔需要清算 对账成功
												originalCupsLstDAO.updateClean(originalCupsLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,DataStatus.no_deductRollBk,deductStlmDate);
												
												if(!originalCupsLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(originalCupsLst.getTradeAmount().toString(), originalCupsLst.getReqMerCode(), instInfo.getGate(), originalCupsLst.getDeductSysTime(), 
															originalCupsLst.getDeductRollbkSysTime(), originalCupsLst.getDeductSysStance(), originalCupsLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, originalCupsLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("超时交易、流水: ",originalCupsLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
											}else if(originalCupsLst2.getBkChk() == 2){//对账失败
												
												//冲正交易进入差错
												originalCupsLstDAO.updateCleanAndErrorRiqie(originalCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
												//修改手续费
												originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
												
												originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
												originalCupsLst.setBkChk(DataStatus.dz_error);
												originalCupsLst.setWhetherQs(false);
												originalCupsLst.setWhetherTk(DataStatus.no_tk);
												originalCupsLst.setZfFileFee(zf_file_fee);
												originalCupsLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
												
											}else{
												//冲正交易进入差错
												originalCupsLstDAO.updateCleanAndErrorRiqie(originalCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
												//修改手续费
												originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
												
												originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
												originalCupsLst.setBkChk(DataStatus.dz_error);
												originalCupsLst.setWhetherQs(false);
												originalCupsLst.setWhetherTk(DataStatus.no_tk);
												originalCupsLst.setZfFileFee(zf_file_fee);
												originalCupsLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
											}
										}else{
											//冲正交易进入差错
											originalCupsLstDAO.updateCleanAndErrorRiqie(originalCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
											//修改手续费
											originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
											
											originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
											originalCupsLst.setBkChk(DataStatus.dz_error);
											originalCupsLst.setWhetherQs(false);
											originalCupsLst.setWhetherTk(DataStatus.no_tk);
											originalCupsLst.setZfFileFee(zf_file_fee);
											originalCupsLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
										}
										
										duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangCupsReqTime);
									}
								}else{//不存在
									log.info(stringPingJie.getStringPingJie("超时交易、流水:",originalCupsLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
									if((originalCupsLst.getTrademsgType() == 2 || originalCupsLst.getTrademsgType() == 18
											|| originalCupsLst.getTrademsgType() == 20 || originalCupsLst.getTrademsgType() == 56 || originalCupsLst.getTrademsgType() == 58) 
											&&  !originalCupsLst.isDeductRollBk()){
										originalCupsLstDAO.updateNoNeedHandle(originalCupsLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, originalCupsLst.getTrademsgType(), originalReqTime);
									}else{
										originalCupsLstDAO.updateNoNeedHandle(originalCupsLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, originalCupsLst.getTrademsgType(), originalReqTime);
									}
								}
							}//失败
							else{
								if(originalCupsLst.getTrademsgType() != null){
									//查询对账文件中是否存在
									//String zf_file_fee = duizhangCupsLstDAO.findDzFileData(originalCupsLst,oriObject, duizObject,instId,DataStatus.no_riqie,duizhangCupsReqTime);
									String zf_file_fee = cupsDz_map.get(originalCupsLst.getDeductSysStance());
									if(StringUtils.isNotBlank(zf_file_fee)){//存在
										if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
											zf_fee = Double.valueOf(zf_file_fee);
										}
										log.info(stringPingJie.getStringPingJie("失败交易、流水:",originalCupsLst.getDeductSysStance()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
										if((originalCupsLst.getTrademsgType() == 2 || originalCupsLst.getTrademsgType() == 56 || originalCupsLst.getTrademsgType() == 110) &&
												!originalCupsLst.isDeductRollBk()){//消费

											originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.long_status,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
											//修改手续费
											originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);
											
											originalCupsLst.setWhetherErroeHandle(DataStatus.long_status);
											originalCupsLst.setBkChk(DataStatus.dz_error);
											originalCupsLst.setWhetherQs(false);
											originalCupsLst.setWhetherTk(DataStatus.no_tk);
											originalCupsLst.setZfFileFee(zf_file_fee);
											originalCupsLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
											duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
										}else if(originalCupsLst.getTrademsgType() == 20){//退货
											
											originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
											//修改手续费
											originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);
											
											originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
											originalCupsLst.setBkChk(DataStatus.dz_error);
											originalCupsLst.setWhetherQs(false);
											originalCupsLst.setWhetherTk(DataStatus.tk);
											originalCupsLst.setZfFileFee(zf_file_fee);
											originalCupsLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
											
											duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
										}else if((originalCupsLst.getTrademsgType() == 18 || originalCupsLst.getTrademsgType() == 58) && !originalCupsLst.isDeductRollBk()){//撤销
											
											originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductSysStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.no_deductRollBk,originalReqTime);
											//修改手续费
											originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.no_deductRollBk,originalReqTime,whetherReturnFee);
											
											originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
											originalCupsLst.setBkChk(DataStatus.dz_error);
											originalCupsLst.setWhetherQs(false);
											originalCupsLst.setWhetherTk(DataStatus.no_tk);
											originalCupsLst.setZfFileFee(zf_file_fee);
											originalCupsLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
											
											duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductSysStance(), DataStatus.dz_success, DataStatus.no_deductRollBk,duizhangCupsReqTime);
										}else{//冲正
											
											//核对原笔对账结果
											OriginalCupsLst originalCupsLst2 = originalCupsLstDAO.queryWhetherDzSuccess(originalCupsLst.getReqSysStance(), originalReqTime, DataStatus.no_deductRollBk);
											if(originalCupsLst2 != null){
												if(originalCupsLst2.getBkChk() == 1){ //对账成功
													//当前这笔需要清算
													originalCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.clean,DataStatus.dz_success,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
													//修改手续费
													originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
													
													//修改原笔需要清算 对账成功
													originalCupsLstDAO.updateClean(originalCupsLst.getReqSysStance(), DataStatus.clean,DataStatus.dz_success,DataStatus.no_deductRollBk,originalReqTime);
													
													if(!originalCupsLst.isWhetherAccessStance()){
														boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(originalCupsLst.getTradeAmount().toString(), originalCupsLst.getReqMerCode(), instInfo.getGate(), originalCupsLst.getDeductSysTime(), 
																originalCupsLst.getDeductRollbkSysTime(), originalCupsLst.getDeductSysStance(), originalCupsLst.isDeductRollBk(), 
																instId, inst_type, deductStlmDate, bank_id, originalCupsLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
														if(update_flag){
															log.info(stringPingJie.getStringPingJie("失败交易、流水: ",originalCupsLst.getDeductSysStance()," 对账成功-进入资金流水"));
														}
													}
												}else if(originalCupsLst2.getBkChk() == 2){//对账失败
													
													//冲正交易进入差错
													originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
													//修改手续费
													originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
													originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
													originalCupsLst.setBkChk(DataStatus.dz_error);
													originalCupsLst.setWhetherQs(false);
													originalCupsLst.setWhetherTk(DataStatus.no_tk);
													originalCupsLst.setZfFileFee(zf_file_fee);
													originalCupsLst.setZfFee(zf_fee);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
													
												}else{
													//冲正交易进入差错
													originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
													//修改手续费
													originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
													
													originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
													originalCupsLst.setBkChk(DataStatus.dz_error);
													originalCupsLst.setWhetherQs(false);
													originalCupsLst.setWhetherTk(DataStatus.no_tk);
													originalCupsLst.setZfFileFee(zf_file_fee);
													originalCupsLst.setZfFee(zf_fee);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
												}
											}else{
												//冲正交易进入差错
												originalCupsLstDAO.updateCleanAndError(originalCupsLst.getDeductRollBkStance(),DataStatus.no_clean, DataStatus.short_status,originalCupsLst.getTrademsgType(),DataStatus.deductRollBk,originalReqTime);
												//修改手续费
												originalCupsLstDAO.updateSettleInfo(originalCupsLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalCupsLst.getTrademsgType(), DataStatus.deductRollBk,originalReqTime,whetherReturnFee);
												
												originalCupsLst.setWhetherErroeHandle(DataStatus.short_status);
												originalCupsLst.setBkChk(DataStatus.dz_error);
												originalCupsLst.setWhetherQs(false);
												originalCupsLst.setWhetherTk(DataStatus.no_tk);
												originalCupsLst.setZfFileFee(zf_file_fee);
												originalCupsLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalCupsLst));
											}
											
											duizhangCupsLstDAO.updateClean(originalCupsLst.getDeductRollBkStance(), DataStatus.dz_success, DataStatus.deductRollBk,duizhangCupsReqTime);
											
										}
									}else{//不存在
										log.info(stringPingJie.getStringPingJie("失败交易、流水:",originalCupsLst.getDeductSysStance()," 对账单不存在-无需对账,支付手续费:",zf_fee));
										if((originalCupsLst.getTrademsgType() == 2 || originalCupsLst.getTrademsgType() == 18
												|| originalCupsLst.getTrademsgType() == 20 || originalCupsLst.getTrademsgType() == 56 || originalCupsLst.getTrademsgType() == 58) 
												&&  !originalCupsLst.isDeductRollBk()){
											originalCupsLstDAO.updateNoNeedHandle(originalCupsLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, originalCupsLst.getTrademsgType(), originalReqTime);
										}else{
											originalCupsLstDAO.updateNoNeedHandle(originalCupsLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, originalCupsLst.getTrademsgType(), originalReqTime);
										}
									}
								}
							}
						}
					}
					
					log.info(stringPingJie.getStringPingJie("开始统计",duizhangCupsReqTime ,"日期对账成功数据"));
					Map<String, Object> mapMerBasic = Backstage.getInstance().getMerBasicList();
					tradeLstDAO = TradeLstDAO.getInstance();
					log.info(stringPingJie.getStringPingJie("开始统计",duizhangCupsReqTime,"日期T+1日统计数据"));
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
										log.info(stringPingJie.getStringPingJie("银联CUPST+1日  ",dy_merCode,"-->",settleMerCode,"保存",isSuccess));
									}else{
										log.info(stringPingJie.getStringPingJie("银联CUPST+1日  ",dy_merCode,"-->",settleMerCode,"失败",isSuccess));
									}
								}else{
									log.info(stringPingJie.getStringPingJie("查询银联CUPS交易日期",deductStlmDate,"交易",settleMerCode,"结算商户为空"));
								}
							}else{
								Object objMer = mapMerBasic.get(dy_merCode);
								if(objMer != null){
									MerBasic merBasic = (MerBasic)objMer;
									Object[] obj = tradeLstDAO.findPosChanneltotall(deductStlmDate, dy_merCode, DataStatus.clean, originalDataTableName);
									boolean isSuccess = tradeLstDAO.merchantSettleStatisticsUtil(deductStlmDate,dy_merCode, DataStatus.clean, originalDataTableName, instId, merBasic.getMerCategory(),inst_type,obj,settle_flag,bank_id);
									if(isSuccess){
										log.info(stringPingJie.getStringPingJie("银联CUPST+1日  ",dy_merCode,"保存",isSuccess));
									}else{
										log.info(stringPingJie.getStringPingJie("银联CUPST+1日  ",dy_merCode,"失败",isSuccess));
									}
								}else{
									log.info(stringPingJie.getStringPingJie("查询银联CUPS交易日期",deductStlmDate,"交易",dy_merCode,"结算商户为空"));
								}
							}
						}
					}
					
					log.info("开始汇总银联CUPS对账之后数据汇总");
					channelDzCollectDAO = cn.com.chinaebi.dz.object.dao.ChannelDzCollectDAO.getInstance();
					int count = channelDzCollectDAO.savePosChannelDzCollect(instInfo, deductStlmDate,map,mapMerBasic);
					log.info(stringPingJie.getStringPingJie("银联CUPS对账之后汇总条数",count));
					log.info("结束汇总银联CUPS对账之后数据汇总");
					
					ExecuteNode executeNode  = executeNodeDAO.findExecuteNodeData(deductStlmDate, instInfo.getId().getInstId(), instInfo.getId().getInstType());
					if(executeNode != null){
						/**
						 * 0:未执行、1:执行成功、2:执行失败、3:对账单数据不存在、4:原始交易数据不存在
						 * 0、3、4:表示数据没有勾兑过,不会产生对账数据状态影响
						 */
						Integer dzHandle = executeNode.getDzHandle();
						if(dzHandle != 0 && dzHandle != 3 && dzHandle != 4){
							log.info("开始检测银联CUPS原始交易差错重对账处理");
							List<ErrorDataLst> oriList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_original, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							if(oriList != null && oriList.size() > 0){
								log.info(stringPingJie.getStringPingJie("重对账检测到银联CUPS原始交易差错条数为:",oriList.size()));
								for (ErrorDataLst errorDataLst : oriList) {
									String tradeId = errorDataLst.getId();
									Integer handlingStatus = errorDataLst.getHandlingStatus();
									Integer bkChk = tradeLstDAO.findOriChannelStanceData(deductStlmDate, tradeId, originalDataTableName);
									if(bkChk == 1){//对账成功
										//0：未处理、1：待审核、2：已审核、3：已驳回、4：无需处理
										if(handlingStatus == 2){
											deductStlmDate = errorDataLst.getJsDate();
											if(errorDataLst.getHandlingId() == 1 && StringUtils.isNotBlank(deductStlmDate)){//正常结算
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
								log.info("没有到检测银联CUPS原始交易差错数据");
							}
							
							log.info("开始检测银联CUPS对账单交易数据差错重对账处理");
							List<ErrorDataLst> duizList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_duizhang, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							if(duizList != null && duizList.size() > 0){
								log.info(stringPingJie.getStringPingJie("重对账检测银联CUPS对账单交易数据差错条数为:",duizList.size()));
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
								log.info("没有到检测银联CUPS对账单交易数据差错数据");
							}
						}else{
							log.info("第一次执行,无需处理重对账差错数据核对");
						}
					}
					
					log.info("开始检测银联CUPS对账文件是否有可疑交易数据");
					//处理银联对账文件未对账的可以交易数据
					List<DuizhangCupsLst> cupsList = duizhangCupsLstDAO.findDateData(duizhangCupsReqTime, DataStatus.not_dz);
					if(cupsList != null && cupsList.size() > 0){
						log.info("银联CUPS对账文件可疑交易数据总条数是:"+cupsList.size());
						for (DuizhangCupsLst duizhangCupsLst:cupsList) {
							errorDataLstDAO.saveErrorData(new ErrorDataLst(duizhangCupsLst));
						}
					}else{
						log.info("没有检测到银联CUPS对账文件的可疑交易数据");
					}
				}else{
					throw new OriginalDataNotFoundException("没有获取到电银银联CUPS " +originalReqTime + " 日期的交易数据");
				}
			}catch (Exception e) {
				throw e;
			}
		}else{
			throw new DuizhangNotFoundException("没有获取到  "+duizhangCupsReqTime+"  日期的银联CUPS对账文件数据");
		}
	}

	@Override
	public void tradeErrorDzDeal(String originalPepDate,
			String duizhangErrorReqTime, Integer instId,Integer flagStatus)
			throws OriginalDataNotFoundException, DuizhangNotFoundException,
			Exception {
		
		log.info("获取原始交易数据日期:"+originalPepDate);
		log.info("获取差错对账文件数据日期:"+duizhangErrorReqTime);
		ylcupsErrorEntryDAO = YlcupsErrorEntryDAO.getInstance();
		duizhangErrorCupsLstDAO = DuizhangErrorCupsLstDAO.getInstance();
		try {
			List<DuizhangErrorCupsLst> list = duizhangErrorCupsLstDAO.findDateData(duizhangErrorReqTime);
			if(list != null && list.size() > 0){
				log.info("开始处理电银银联CUPS差错数据对账...");
				for (DuizhangErrorCupsLst duizhangErrorCupsLst:list) {
					log.info("流水:"+duizhangErrorCupsLst.getOrigDataStance()+" 开始对账");
					boolean flag = ylcupsErrorEntryDAO.findYlcupsErrorEntry(duizhangErrorCupsLst.getOrigDataStance());
					if(flag){
						ylcupsErrorEntryDAO.updateClean(duizhangErrorCupsLst.getOrigDataStance(),DataStatus.dz_success);
						duizhangErrorCupsLstDAO.updateClean(duizhangErrorCupsLst.getOrigDataStance(),DataStatus.dz_success);
					}
				}
			}else{
				throw new DuizhangNotFoundException("没有获取到  "+duizhangErrorReqTime+"  日期的银联CUPS差错对账文件数据");
			}
		} catch (Exception e) {
			log.error("银联CUPS差错对账错误:"+e);
			throw e;
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
	
	public static void main(String[] args) {
		String ss = "132017.79";
		System.out.println(Float.parseFloat(ss));
	}
}
