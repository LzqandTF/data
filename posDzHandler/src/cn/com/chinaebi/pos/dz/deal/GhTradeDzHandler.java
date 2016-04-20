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
import cn.com.chinaebi.dz.object.DuizhangSzghLst;
import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.ExecuteNode;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.OriginalSzghLst;
import cn.com.chinaebi.dz.object.RiqieSzghLst;
import cn.com.chinaebi.dz.object.dao.DuizhangSzghLstDAO;
import cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO;
import cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO;
import cn.com.chinaebi.dz.object.dao.InstRateDAO;
import cn.com.chinaebi.dz.object.dao.OriginalSzghLstDAO;
import cn.com.chinaebi.dz.object.dao.RiqieSzghLstDAO;
import cn.com.chinaebi.dz.object.dao.SettleMerchantConfigDAO;
import cn.com.chinaebi.dz.object.dao.SettleMerchantMatchTableDAO;
import cn.com.chinaebi.dz.object.dao.TradeLstDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.util.PoundageCalculate;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.StringPingJie;


/**
 * 深圳工行对账处理
 * 1.内部对账
 * 2.日切对账
 * 3.正常对账
 * @author zhu.hongliang
 *
 */
public class GhTradeDzHandler implements TradeDzHandler{

	private Log log = LogFactory.getLog(getClass());
	private cn.com.chinaebi.dz.object.dao.iface.OriginalSzghLstDAO originalSzghLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.RiqieSzghLstDAO riqieSzghLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorDataLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.DuizhangSzghLstDAO duizhangSzghLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.InstRateDAO instRateDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO;
	private final Integer inst_type = DataStatus.inst_type_0;
	private final String columnName = "trade_id";
	private final Integer whether_flag = 1;
	
	
	@Override
	public void tradeDzDeal(String originalPepDate, String duizhangReqTime,
			String deductStlmDate, Boolean innertTradeHandler,
			Integer bank_id,Integer instId,Integer instType,
			Integer flagStatus) throws OriginalDataNotFoundException,
			DuizhangNotFoundException, Exception {
		log.info("获取原始交易数据日期 ："+originalPepDate);
		log.info("获取对账文件数据日期 ："+duizhangReqTime);
		log.info("日切数据日期 ： "+deductStlmDate);
		originalSzghLstDAO = OriginalSzghLstDAO.getInstance();
		duizhangSzghLstDAO = DuizhangSzghLstDAO.getInstance();
		riqieSzghLstDAO = RiqieSzghLstDAO.getInstance();
		errorDataLstDAO = ErrorDataLstDAO.getInstance();
		tradeLstDAO = TradeLstDAO.getInstance();
		instRateDAO = InstRateDAO.getInstance();
		executeNodeDAO = ExecuteNodeDAO.getInstance();
		StringPingJie stringPingJie = StringPingJie.getInstance();
		
		SimpleDateFormat dateFormat = DYDataUtil.getSimpleDateFormat(DYDataUtil.DATE_FORMAT_6);
		Double zf_fee = 0d;
		Integer whetherReturnFee = 0;
		Integer instRateType = 0;
		
		InstInfoPK infoPK = new InstInfoPK();
		infoPK.setInstId(instId);
		infoPK.setInstType(0);
		InstInfo instInfo = Backstage.getInstance().getInstInfo(infoPK);
		String originalDataTableName = instInfo.getBank().getOriginalDataTableName();
		String riqieOriginalTableName = instInfo.getBank().getRiqieOriginalTableName();
		String dzDataTableName = instInfo.getBank().getDzDataTableName();
		
		Object[] instRateObj = instRateDAO.getInstRateType(instId, inst_type);
		Map<String, Boolean> rateMap = instRateDAO.findChanelMccRateConf(instId, inst_type);
		int rateMapSize = rateMap == null ? 0 : rateMap.keySet().size();
		try {
			if(innertTradeHandler){
				log.info(stringPingJie.getStringPingJie("深圳工行开始勾对内部交易处理 ：",originalPepDate));
				if(instInfo != null){
					boolean riqieflag = tradeLstDAO.channelInnerDz(deductStlmDate, riqieOriginalTableName, "deduct_stlm_date");
					if(!riqieflag)
						log.error("深圳工行内部日切原始交易勾对处理失败....");
					boolean oriflag = tradeLstDAO.channelInnerDz(deductStlmDate, originalDataTableName, "deduct_stlm_date");
					if(!oriflag)
						log.error("深圳工行内部原始交易勾对处理失败....");
				}else{
					log.info("深圳工行勾对内部获取渠道ID为空");
				}
			}else{
				log.warn(stringPingJie.getStringPingJie(instId ," : 无需内部对账"));
			}
			
			Map<String, String> szghDz_map = duizhangSzghLstDAO.findDzFileData(duizhangReqTime);
			if(szghDz_map != null && szghDz_map.keySet() != null && szghDz_map.keySet().size() > 0){
				try {
					List<RiqieSzghLst> listRiqieSzghLst = riqieSzghLstDAO.findRiqieSzghLst(deductStlmDate);
					if(listRiqieSzghLst != null && listRiqieSzghLst.size() > 0){
						log.info("开始处理电银深圳工行日切数据对账...");
						for (RiqieSzghLst riqieSzghLst : listRiqieSzghLst) {
							whetherReturnFee = 0;
							instRateType = 0;
							zf_fee = 0d;
							
							//计算银行手续费
							if(instRateObj != null){
								whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
								instRateType = Integer.valueOf(instRateObj[0].toString());
								if(instRateType != 2){
									zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(riqieSzghLst.getTradeAmount()), riqieSzghLst.getOutAccount(), riqieSzghLst.getDeductSysId(), inst_type, riqieSzghLst.getDeductMerCode());
								}
								//按MCC计算
								if(instRateType == 1){
									String mcc_code = PoundageCalculate.mccCodeSubstring(riqieSzghLst.getDeductMerCode());
									if(StringUtils.isNotBlank(mcc_code)){
										boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
										if(riqieSzghLst.getTrademsgType() == 20){//退货交易
											if(whetherReturnFee == 0 && !lw_flag){//不退还
												zf_fee = 0d;
											}else if(whetherReturnFee == 1 && lw_flag){//退还
												zf_fee = 0d;
											}
										}
									}
								}else if(instRateType == 3){//按固定费率
									//退货交易-不退还手续费
									if(riqieSzghLst.getTrademsgType() != null && riqieSzghLst.getTrademsgType() == 20 && whetherReturnFee == 0){
										zf_fee = 0d;
									}
								}
							}
							
							log.info(stringPingJie.getStringPingJie("流水 ： ",riqieSzghLst.getDeductSysStance(),"--",riqieSzghLst.getAdditionalResponseData() == null ? "无" : riqieSzghLst.getAdditionalResponseData()," 开始对账"));
							
							//成功
							if(((StringUtils.equals(riqieSzghLst.getDeductSysResponse(), "00") && riqieSzghLst.getDeductSysResponse() != null)||
									(StringUtils.equals(riqieSzghLst.getDeductRollBkResponse(), "00") && riqieSzghLst.getDeductRollBkResponse() != null))
									&& riqieSzghLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								String zf_file_fee = riqieSzghLst.getAdditionalResponseData() == null ? null : szghDz_map.get(riqieSzghLst.getAdditionalResponseData());
								if(StringUtils.isNotBlank(zf_file_fee)){
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									if((riqieSzghLst.getTrademsgType() == 2 ||  riqieSzghLst.getTrademsgType() == 56) &&
											!riqieSzghLst.isDeductRollBk()){//消费or预授权完成
										/*
										 * 1:对账成功、需要清算
										 * 2:修改为商户手续费、退款交易、对账文件手续费
										 */
										riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}else if(riqieSzghLst.getTrademsgType() == 20){ //退货
										/*
										 * 1:对账成功、需要清算
										 * 2:修改为商户手续费、退款交易、对账文件手续费
										 */
										riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee,zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee,zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}else if((riqieSzghLst.getTrademsgType() == 18 || riqieSzghLst.getTrademsgType() == 58)
											&& !riqieSzghLst.isDeductRollBk()){//撤销or预授权完成撤销
										/*
										 * 1:对账成功、需要清算
										 * 2:修改为商户手续费、退款交易、对账文件手续费
										 */
										riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}else{//冲正
										//获取原笔交易对账信息
										OriginalSzghLst originalSzghLst  = originalSzghLstDAO.queryWhetherDzSuccess(riqieSzghLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
										if(originalSzghLst != null){
											if(originalSzghLst.getBkChk() == 1){//对账成功
												
												//当前这笔需要清算
												riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											}else if(originalSzghLst.getBkChk() == 2){//对账失败
												
												/*
												 * 1:当前交易对账失败、不需要清算 
												 * 2:修改为商户手续费、退款交易、对账文件手续费
												 * 3:当前交易进入差错，并且保存"2"部分数据
												 */
												riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												//冲正进入差错
												riqieSzghLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieSzghLst.setBkChk(DataStatus.dz_error);
												riqieSzghLst.setWhetherQs(false);
												riqieSzghLst.setWhetherTk(DataStatus.no_tk);
												riqieSzghLst.setZfFileFee(zf_file_fee);
												riqieSzghLst.setZfFee(zf_fee);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
												
											}else{
												//当前这笔需要清算
												riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											}
										}else{
											//当前这笔需要清算
											riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										}
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}
								}else{
									log.info(stringPingJie.getStringPingJie("成功交易、流水:",riqieSzghLst.getDeductSysStance()," 对账单不存在-未对账,支付手续费:",zf_fee));
									if(riqieSzghLst.isDeductRollBk()){
										riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateClean(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
									}else{
										riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateClean(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									}
								}
							}//超时
							else if(((riqieSzghLst.getDeductResult() != null && riqieSzghLst.getDeductResult() == 1) || 
									(riqieSzghLst.getDeductRollBkResult() != null && riqieSzghLst.getDeductRollBkResult() == 1))
									&& riqieSzghLst.getTrademsgType() != null){
								//查询对账文件中是否存在
								String zf_file_fee = riqieSzghLst.getAdditionalResponseData() == null ? null : szghDz_map.get(riqieSzghLst.getAdditionalResponseData());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									if((riqieSzghLst.getTrademsgType() == 2 || riqieSzghLst.getTrademsgType() == 56) &&
											!riqieSzghLst.isDeductRollBk()){//消费
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieSzghLst.setBkChk(DataStatus.dz_error);
										riqieSzghLst.setWhetherQs(false);
										riqieSzghLst.setWhetherErroeHandle(DataStatus.long_status);
										riqieSzghLst.setZfFileFee(zf_file_fee);
										riqieSzghLst.setZfFee(zf_fee);
										riqieSzghLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
										//修改对账文件-对账成功
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}else if(riqieSzghLst.getTrademsgType() == 20){ //退货
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieSzghLst.setBkChk(DataStatus.dz_error);
										riqieSzghLst.setWhetherQs(false);
										riqieSzghLst.setWhetherErroeHandle(DataStatus.long_status);
										riqieSzghLst.setZfFileFee(zf_file_fee);
										riqieSzghLst.setZfFee(zf_fee);
										riqieSzghLst.setWhetherTk(DataStatus.tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
										
										//修改对账文件-对账成功
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									
										
									}else if((riqieSzghLst.getTrademsgType() == 18 || riqieSzghLst.getTrademsgType() == 58) 
											&& !riqieSzghLst.isDeductRollBk()){//撤销
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieSzghLst.setBkChk(DataStatus.dz_error);
										riqieSzghLst.setWhetherQs(false);
										riqieSzghLst.setWhetherErroeHandle(DataStatus.long_status);
										riqieSzghLst.setZfFileFee(zf_file_fee);
										riqieSzghLst.setZfFee(zf_fee);
										riqieSzghLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
										
										//修改对账文件-对账成功
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									
									}else{//冲正
										OriginalSzghLst originalSzghLst  = originalSzghLstDAO.queryWhetherDzSuccessAll(riqieSzghLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
										if(originalSzghLst != null){
											if(originalSzghLst.getBkChk() == 1){ //对账成功
												//当前这笔需要清算
												riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												
												if(!riqieSzghLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieSzghLst.getTradeAmount().toString(), riqieSzghLst.getReqMerCode(), instInfo.getGate(), riqieSzghLst.getDeductSysTime(), 
															riqieSzghLst.getDeductRollbkSysTime(), riqieSzghLst.getDeductSysStance(), riqieSzghLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, riqieSzghLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieSzghLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
											}else if(originalSzghLst.getBkChk() == 2){ //对账失败
												/*
												 * 1:修改不需要清算--对账失败
												 * 2:修改结算信息
												 */
												riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												//进入差错-需要处理结算信息
												riqieSzghLst.setBkChk(DataStatus.dz_error);
												riqieSzghLst.setWhetherQs(false);
												riqieSzghLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieSzghLst.setZfFileFee(zf_file_fee);
												riqieSzghLst.setZfFee(zf_fee);
												riqieSzghLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
											}else{
												/*
												 * 1:修改不需要清算--对账失败
												 * 2:修改结算信息
												 */
												riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												//进入差错-需要处理结算信息
												riqieSzghLst.setBkChk(DataStatus.dz_error);
												riqieSzghLst.setWhetherQs(false);
												riqieSzghLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieSzghLst.setZfFileFee(zf_file_fee);
												riqieSzghLst.setZfFee(zf_fee);
												riqieSzghLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
											}
										}else{
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieSzghLst.setBkChk(DataStatus.dz_error);
											riqieSzghLst.setWhetherQs(false);
											riqieSzghLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieSzghLst.setZfFileFee(zf_file_fee);
											riqieSzghLst.setZfFee(zf_fee);
											riqieSzghLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
										}
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}
								}else{//不存在
									if((riqieSzghLst.getTrademsgType() == 2 || riqieSzghLst.getTrademsgType() == 18
											|| riqieSzghLst.getTrademsgType() == 20 || riqieSzghLst.getTrademsgType() == 56 || riqieSzghLst.getTrademsgType() == 58) 
											&&  !riqieSzghLst.isDeductRollBk()){
										riqieSzghLstDAO.updateNoNeedHandle(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzghLst.getTrademsgType(), deductStlmDate);
										originalSzghLstDAO.updateNoNeedHandle(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzghLst.getTrademsgType(), deductStlmDate);
										
									}else{
										riqieSzghLstDAO.updateNoNeedHandle(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzghLst.getTrademsgType(), deductStlmDate);
										originalSzghLstDAO.updateNoNeedHandle(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzghLst.getTrademsgType(), deductStlmDate);
									}
								}
							}else{//失败
								//查询对账文件中是否存在
								String zf_file_fee = riqieSzghLst.getAdditionalResponseData() == null ? null : szghDz_map.get(riqieSzghLst.getAdditionalResponseData());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									if((riqieSzghLst.getTrademsgType() == 2 || riqieSzghLst.getTrademsgType() == 56) &&
											!riqieSzghLst.isDeductRollBk()){//消费
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieSzghLst.setBkChk(DataStatus.dz_error);
										riqieSzghLst.setWhetherQs(false);
										riqieSzghLst.setWhetherErroeHandle(DataStatus.long_status);
										riqieSzghLst.setZfFileFee(zf_file_fee);
										riqieSzghLst.setZfFee(zf_fee);
										riqieSzghLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
										//修改对账文件-对账成功
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}else if(riqieSzghLst.getTrademsgType() == 20){ //退货
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieSzghLst.setBkChk(DataStatus.dz_error);
										riqieSzghLst.setWhetherQs(false);
										riqieSzghLst.setWhetherErroeHandle(DataStatus.long_status);
										riqieSzghLst.setZfFileFee(zf_file_fee);
										riqieSzghLst.setZfFee(zf_fee);
										riqieSzghLst.setWhetherTk(DataStatus.tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
										
										//修改对账文件-对账成功
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									
										
									}else if((riqieSzghLst.getTrademsgType() == 18 || riqieSzghLst.getTrademsgType() == 58) 
											&& !riqieSzghLst.isDeductRollBk()){//撤销
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
										riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieSzghLst.setBkChk(DataStatus.dz_error);
										riqieSzghLst.setWhetherQs(false);
										riqieSzghLst.setWhetherErroeHandle(DataStatus.long_status);
										riqieSzghLst.setZfFileFee(zf_file_fee);
										riqieSzghLst.setZfFee(zf_fee);
										riqieSzghLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));

										//修改对账文件-对账成功
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}else{//冲正
										OriginalSzghLst originalSzghLst  = originalSzghLstDAO.queryWhetherDzSuccessAll(riqieSzghLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
										if(originalSzghLst != null){
											if(originalSzghLst.getBkChk() == 1){ //对账成功
												//当前这笔需要清算
												riqieSzghLstDAO.updateClean(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											
												if(!riqieSzghLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieSzghLst.getTradeAmount().toString(), riqieSzghLst.getReqMerCode(), instInfo.getGate(), riqieSzghLst.getDeductSysTime(), 
															riqieSzghLst.getDeductRollbkSysTime(), riqieSzghLst.getDeductSysStance(), riqieSzghLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, riqieSzghLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieSzghLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
											}else if(originalSzghLst.getBkChk() == 2){ //对账失败
												/*
												 * 1:修改不需要清算--对账失败
												 * 2:修改结算信息
												 */
												riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												//进入差错-需要处理结算信息
												riqieSzghLst.setBkChk(DataStatus.dz_error);
												riqieSzghLst.setWhetherQs(false);
												riqieSzghLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieSzghLst.setZfFileFee(zf_file_fee);
												riqieSzghLst.setZfFee(zf_fee);
												riqieSzghLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
											}else{
												/*
												 * 1:修改不需要清算--对账失败
												 * 2:修改结算信息
												 */
												riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
												riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
												//进入差错-需要处理结算信息
												riqieSzghLst.setBkChk(DataStatus.dz_error);
												riqieSzghLst.setWhetherQs(false);
												riqieSzghLst.setWhetherErroeHandle(DataStatus.short_status);
												riqieSzghLst.setZfFileFee(zf_file_fee);
												riqieSzghLst.setZfFee(zf_fee);
												riqieSzghLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
											}
										}else{
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieSzghLstDAO.updateCleanAndError(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzghLstDAO.updateCleanRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzghLstDAO.updateSettleInfo(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzghLstDAO.updateSettleInfoRiqie(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzghLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieSzghLst.setBkChk(DataStatus.dz_error);
											riqieSzghLst.setWhetherQs(false);
											riqieSzghLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieSzghLst.setZfFileFee(zf_file_fee);
											riqieSzghLst.setZfFee(zf_fee);
											riqieSzghLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzghLst));
										}
										duizhangSzghLstDAO.updateClean(riqieSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,riqieSzghLst.getTrademsgType());
									}
								}else{//不存在
									if((riqieSzghLst.getTrademsgType() == 2 || riqieSzghLst.getTrademsgType() == 18
											|| riqieSzghLst.getTrademsgType() == 20 || riqieSzghLst.getTrademsgType() == 56 || riqieSzghLst.getTrademsgType() == 58) 
											&&  !riqieSzghLst.isDeductRollBk()){
										riqieSzghLstDAO.updateNoNeedHandle(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzghLst.getTrademsgType(), deductStlmDate);
										originalSzghLstDAO.updateNoNeedHandle(riqieSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzghLst.getTrademsgType(), deductStlmDate);
										
									}else{
										riqieSzghLstDAO.updateNoNeedHandle(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzghLst.getTrademsgType(), deductStlmDate);
										originalSzghLstDAO.updateNoNeedHandle(riqieSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzghLst.getTrademsgType(), deductStlmDate);
									}
								}
							}
						}
					}else{
						
					}
				} catch (Exception e) {
					log.error("深圳工行日切对账错误 ： "+e);
				}
				
				
				try {
					List<OriginalSzghLst> listOriginalSzghLst = originalSzghLstDAO.findOriginalSzghLst(originalPepDate);
					if(listOriginalSzghLst != null && listOriginalSzghLst.size() > 0){
						log.info("开始处理电银深圳工行数据对账...");
						for (OriginalSzghLst originalSzghLst : listOriginalSzghLst) {
							whetherReturnFee = 0;
							zf_fee = 0d;
							String _deductStlmDate = dateFormat.format(originalSzghLst.getDeductStlmDate());
							String date = DYDataUtil.getNextDateString(originalPepDate);
							log.info(stringPingJie.getStringPingJie("流水 ： ",originalSzghLst.getDeductSysStance(),"--",originalSzghLst.getAdditionalResponseData() == null ? "无" : originalSzghLst.getAdditionalResponseData()," 开始对账",_deductStlmDate," --> ",date));
							if(StringUtils.equalsIgnoreCase(_deductStlmDate,date)){//日切
								//处理日切交易数据
								log.info(stringPingJie.getStringPingJie("大连交行日切数据流水 ：",originalSzghLst.getDeductSysStance()," 日期 ：",_deductStlmDate));
								originalSzghLstDAO.updateDataRiqie(originalSzghLst.getDeductSysStance(), DataStatus.riqie,originalPepDate);
								originalSzghLst.setWhetherRiqie(true);
								riqieSzghLstDAO.saveRiqieSzghLst(new RiqieSzghLst(originalSzghLst), flagStatus);
							}else{

								//计算银行手续费
								instRateType = 0;
								if(instRateObj != null){
									whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
									instRateType = Integer.valueOf(instRateObj[0].toString());
									if(instRateType != 2){
										zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(originalSzghLst.getTradeAmount()), originalSzghLst.getOutAccount(), originalSzghLst.getDeductSysId(), inst_type, originalSzghLst.getDeductMerCode());
									}
									//按MCC计算
									if(instRateType == 1){
										String mcc_code = PoundageCalculate.mccCodeSubstring(originalSzghLst.getDeductMerCode());
										if(StringUtils.isNotBlank(mcc_code)){
											boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
											if(originalSzghLst.getTrademsgType() == 20){//退货交易
												if(whetherReturnFee == 0 && !lw_flag){//不退还
													zf_fee = 0d;
												}else if(whetherReturnFee == 1 && lw_flag){//退还
													zf_fee = 0d;
												}
											}
										}
									}else if(instRateType == 3){//按固定费率
										//退货交易-不退还手续费
										if(originalSzghLst.getTrademsgType() != null && originalSzghLst.getTrademsgType() == 20 && whetherReturnFee == 0){
											zf_fee = 0d;
										}
									}
								}
								
								log.info(stringPingJie.getStringPingJie("流水 ： ",originalSzghLst.getDeductSysStance(),"--",originalSzghLst.getAdditionalResponseData() == null ? "无" : originalSzghLst.getAdditionalResponseData()));
								//成功
								if(((StringUtils.equals(originalSzghLst.getDeductSysResponse(), "00") && originalSzghLst.getDeductSysResponse() != null)||
										(StringUtils.equals(originalSzghLst.getDeductRollBkResponse(), "00") && originalSzghLst.getDeductRollBkResponse() != null))
										&& originalSzghLst.getTrademsgType() != null){
									//查询对账文件中是否存在
									String zf_file_fee = originalSzghLst.getAdditionalResponseData() == null ? null : szghDz_map.get(originalSzghLst.getAdditionalResponseData());
									if(StringUtils.isNotBlank(zf_file_fee)){//存在
										if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
											zf_fee = Double.valueOf(zf_file_fee);
										}
										if((originalSzghLst.getTrademsgType() == 2 || originalSzghLst.getTrademsgType() == 56) &&
												!originalSzghLst.isDeductRollBk()){//消费or预授权完成
											originalSzghLstDAO.updateClean(originalSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}else if(originalSzghLst.getTrademsgType() == 20){ //退货
											originalSzghLstDAO.updateClean(originalSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}else if((originalSzghLst.getTrademsgType() == 18 || originalSzghLst.getTrademsgType() == 58) 
												&& !originalSzghLst.isDeductRollBk()){//撤销
											originalSzghLstDAO.updateClean(originalSzghLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}else{//冲正
											OriginalSzghLst originalSzghLst2 =  originalSzghLstDAO.queryWhetherDzSuccess(originalSzghLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
											if(originalSzghLst2 != null){
												if(originalSzghLst2.getBkChk() == 1){//对账成功
													originalSzghLstDAO.updateClean(originalSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												}else if(originalSzghLst2.getBkChk() == 2){ //对账失败
													originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													//冲正进入差错
													originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
													originalSzghLst.setBkChk(DataStatus.dz_error);
													originalSzghLst.setWhetherQs(false);
													originalSzghLst.setZfFileFee(zf_file_fee);
													originalSzghLst.setZfFee(zf_fee);
													originalSzghLst.setWhetherTk(DataStatus.no_tk);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												}else{
													originalSzghLstDAO.updateClean(originalSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												}
											}else{
												originalSzghLstDAO.updateClean(originalSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
											
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}
									}else{
										log.info(stringPingJie.getStringPingJie("成功交易、流水:",originalSzghLst.getDeductSysStance()," 对账单不存在-未对账,支付手续费:",zf_fee));
										if(originalSzghLst.isDeductRollBk()){
											originalSzghLstDAO.updateClean(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
										}else{
											originalSzghLstDAO.updateClean(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										}
									}
								}//超时
								else if(((originalSzghLst.getDeductResult() != null && originalSzghLst.getDeductResult() == 1) || 
										(originalSzghLst.getDeductRollBkResult() != null && originalSzghLst.getDeductRollBkResult() == 1))
										&& originalSzghLst.getTrademsgType() != null){
									//查询对账文件中是否存在
									String zf_file_fee = originalSzghLst.getAdditionalResponseData() == null ? null : szghDz_map.get(originalSzghLst.getAdditionalResponseData());
									if(StringUtils.isNotBlank(zf_file_fee)){//存在
										if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
											zf_fee = Double.valueOf(zf_file_fee);
										}
										if((originalSzghLst.getTrademsgType() == 2 || originalSzghLst.getTrademsgType() == 56) &&
												!originalSzghLst.isDeductRollBk()){//消费or预授权完成
											//修改不需要清算--对账失败
											originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLst.setWhetherErroeHandle(DataStatus.long_status);
											originalSzghLst.setBkChk(DataStatus.dz_error);
											originalSzghLst.setWhetherQs(false);
											originalSzghLst.setWhetherTk(DataStatus.no_tk);
											originalSzghLst.setZfFileFee(zf_file_fee);
											originalSzghLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
											//修改结算信息
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}else if(originalSzghLst.getTrademsgType() == 20){ //退货
											originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
											originalSzghLst.setBkChk(DataStatus.dz_error);
											originalSzghLst.setWhetherQs(false);
											originalSzghLst.setZfFileFee(zf_file_fee);
											originalSzghLst.setZfFee(zf_fee);
											originalSzghLst.setWhetherTk(DataStatus.tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);

											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}else if((originalSzghLst.getTrademsgType() == 18 || originalSzghLst.getTrademsgType() == 58) 
												&& !originalSzghLst.isDeductRollBk()){//撤销

											originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
											originalSzghLst.setBkChk(DataStatus.dz_error);
											originalSzghLst.setWhetherQs(false);
											originalSzghLst.setZfFileFee(zf_file_fee);
											originalSzghLst.setZfFee(zf_fee);
											originalSzghLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
											
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}else{//冲正
											OriginalSzghLst originalSzghLst2 =  originalSzghLstDAO.queryWhetherDzSuccessAll(originalSzghLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
											if(originalSzghLst2 != null){
												if(originalSzghLst2.getBkChk() == 1){//对账成功
													originalSzghLstDAO.updateClean(originalSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
													
													if(!originalSzghLst.isWhetherAccessStance()){
														boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(originalSzghLst.getTradeAmount().toString(), originalSzghLst.getReqMerCode(), instInfo.getGate(), originalSzghLst.getDeductSysTime(), 
																originalSzghLst.getDeductRollbkSysTime(), originalSzghLst.getDeductSysStance(), originalSzghLst.isDeductRollBk(), 
																instId, inst_type, deductStlmDate, bank_id, originalSzghLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
														if(update_flag){
															log.info(stringPingJie.getStringPingJie("超时交易、流水: ",originalSzghLst.getDeductSysStance()," 对账成功-进入资金流水"));
														}
													}
												}else if(originalSzghLst2.getBkChk() == 2){ //对账失败
													originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													//冲正进入差错
													originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
													originalSzghLst.setBkChk(DataStatus.dz_error);
													originalSzghLst.setWhetherQs(false);
													originalSzghLst.setZfFileFee(zf_file_fee);
													originalSzghLst.setZfFee(zf_fee);
													originalSzghLst.setWhetherTk(DataStatus.no_tk);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												}else{
													originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													//冲正进入差错
													originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
													originalSzghLst.setBkChk(DataStatus.dz_error);
													originalSzghLst.setWhetherQs(false);
													originalSzghLst.setZfFileFee(zf_file_fee);
													originalSzghLst.setZfFee(zf_fee);
													originalSzghLst.setWhetherTk(DataStatus.no_tk);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												}
											}else{
												originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
												originalSzghLst.setBkChk(DataStatus.dz_error);
												originalSzghLst.setWhetherQs(false);
												originalSzghLst.setZfFileFee(zf_file_fee);
												originalSzghLst.setZfFee(zf_fee);
												originalSzghLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
												originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}
									}else{//不存在
										if((originalSzghLst.getTrademsgType() == 2 || originalSzghLst.getTrademsgType() == 18
												|| originalSzghLst.getTrademsgType() == 20 || originalSzghLst.getTrademsgType() == 56 || originalSzghLst.getTrademsgType() == 58) 
												&&  !originalSzghLst.isDeductRollBk()){
											originalSzghLstDAO.updateNoNeedHandle(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, originalSzghLst.getTrademsgType(), originalPepDate);
										}else{
											originalSzghLstDAO.updateNoNeedHandle(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, originalSzghLst.getTrademsgType(), originalPepDate);
										}
									}
								}//失败
								else{
									//查询对账文件中是否存在
									String zf_file_fee = originalSzghLst.getAdditionalResponseData() == null ? null : szghDz_map.get(originalSzghLst.getAdditionalResponseData());
									if(StringUtils.isNotBlank(zf_file_fee)){//存在
										if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
											zf_fee = Double.valueOf(zf_file_fee);
										}
										if((originalSzghLst.getTrademsgType() == 2 || originalSzghLst.getTrademsgType() == 56) &&
												!originalSzghLst.isDeductRollBk()){//消费or预授权完成
											//修改不需要清算--对账失败
											originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLst.setWhetherErroeHandle(DataStatus.long_status);
											originalSzghLst.setBkChk(DataStatus.dz_error);
											originalSzghLst.setWhetherQs(false);
											originalSzghLst.setWhetherTk(DataStatus.no_tk);
											originalSzghLst.setZfFileFee(zf_file_fee);
											originalSzghLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
											//修改结算信息
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										
										}else if(originalSzghLst.getTrademsgType() == 20){ //退货

											originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
											originalSzghLst.setBkChk(DataStatus.dz_error);
											originalSzghLst.setWhetherQs(false);
											originalSzghLst.setZfFileFee(zf_file_fee);
											originalSzghLst.setZfFee(zf_fee);
											originalSzghLst.setWhetherTk(DataStatus.tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);

											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}else if((originalSzghLst.getTrademsgType() == 18 || originalSzghLst.getTrademsgType() == 58) 
												&& !originalSzghLst.isDeductRollBk()){//撤销
											
											originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
											originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
											originalSzghLst.setBkChk(DataStatus.dz_error);
											originalSzghLst.setWhetherQs(false);
											originalSzghLst.setZfFileFee(zf_file_fee);
											originalSzghLst.setZfFee(zf_fee);
											originalSzghLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
											originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);

											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}else{//冲正
											OriginalSzghLst originalSzghLst2 =  originalSzghLstDAO.queryWhetherDzSuccess(originalSzghLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
											if(originalSzghLst2 != null){
												if(originalSzghLst2.getBkChk() == 1){//对账成功
													originalSzghLstDAO.updateClean(originalSzghLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												
													if(!originalSzghLst.isWhetherAccessStance()){
														boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(originalSzghLst.getTradeAmount().toString(), originalSzghLst.getReqMerCode(), instInfo.getGate(), originalSzghLst.getDeductSysTime(), 
																originalSzghLst.getDeductRollbkSysTime(), originalSzghLst.getDeductSysStance(), originalSzghLst.isDeductRollBk(), 
																instId, inst_type, deductStlmDate, bank_id, originalSzghLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
														if(update_flag){
															log.info(stringPingJie.getStringPingJie("失败交易、流水: ",originalSzghLst.getDeductSysStance()," 对账成功-进入资金流水"));
														}
													}
												}else if(originalSzghLst2.getBkChk() == 2){ //对账失败
													originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													//冲正进入差错
													originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
													originalSzghLst.setBkChk(DataStatus.dz_error);
													originalSzghLst.setWhetherQs(false);
													originalSzghLst.setZfFileFee(zf_file_fee);
													originalSzghLst.setZfFee(zf_fee);
													originalSzghLst.setWhetherTk(DataStatus.no_tk);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												}else{
													originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
													//冲正进入差错
													originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
													originalSzghLst.setBkChk(DataStatus.dz_error);
													originalSzghLst.setWhetherQs(false);
													originalSzghLst.setZfFileFee(zf_file_fee);
													originalSzghLst.setZfFee(zf_fee);
													originalSzghLst.setWhetherTk(DataStatus.no_tk);
													errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
													originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												}
											}else{
												originalSzghLstDAO.updateCleanAndError(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalSzghLst.setWhetherErroeHandle(DataStatus.short_status);
												originalSzghLst.setBkChk(DataStatus.dz_error);
												originalSzghLst.setWhetherQs(false);
												originalSzghLst.setZfFileFee(zf_file_fee);
												originalSzghLst.setZfFee(zf_fee);
												originalSzghLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzghLst));
												originalSzghLstDAO.updateSettleInfo(originalSzghLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzghLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
											duizhangSzghLstDAO.updateClean(originalSzghLst.getAdditionalResponseData(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,originalSzghLst.getTrademsgType());
										}
									}else{//不存在
										if((originalSzghLst.getTrademsgType() == 2 || originalSzghLst.getTrademsgType() == 18
												|| originalSzghLst.getTrademsgType() == 20 || originalSzghLst.getTrademsgType() == 56 || originalSzghLst.getTrademsgType() == 58) 
												&&  !originalSzghLst.isDeductRollBk()){
											originalSzghLstDAO.updateNoNeedHandle(originalSzghLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, originalSzghLst.getTrademsgType(), originalPepDate);
										}else{
											originalSzghLstDAO.updateNoNeedHandle(originalSzghLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, originalSzghLst.getTrademsgType(), originalPepDate);
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
							for (Object object : listObj) {
								String dy_merCode = object.toString();
								boolean settle_flag = map.get(dy_merCode) != null ? true : false;
								if(map != null && settle_flag){//处理结算商户对应电银商户T+1统计
									String settleMerCode = map.get(dy_merCode);
									Object objMer = mapMerBasic.get(settleMerCode);
									if(objMer != null && map.get(dy_merCode) != null){
										MerBasic merBasic = (MerBasic)objMer;
										Object[] obj = tradeLstDAO.findPosChanneltotall(deductStlmDate, dy_merCode, DataStatus.clean, originalDataTableName);
										boolean isSuccess = tradeLstDAO.merchantSettleStatisticsUtil(deductStlmDate,settleMerCode, DataStatus.clean, originalDataTableName, instId, merBasic.getMerCategory(),inst_type,obj,settle_flag,bank_id);
										if(isSuccess){
											log.info(stringPingJie.getStringPingJie("深圳工行T+1日  ",dy_merCode,"-->",settleMerCode,"保存",isSuccess));
										}else{
											log.info(stringPingJie.getStringPingJie("深圳工行T+1日  ",dy_merCode,"-->",settleMerCode,"失败",isSuccess));
										}
									}else{
										log.info(stringPingJie.getStringPingJie("查询深圳工行交易日期",deductStlmDate,"交易",settleMerCode,"结算商户为空"));
									}
								}else{
									Object objMer = mapMerBasic.get(dy_merCode);
									if(objMer != null){
										MerBasic merBasic = (MerBasic)objMer;
										Object[] obj = tradeLstDAO.findPosChanneltotall(deductStlmDate, dy_merCode, DataStatus.clean, originalDataTableName);
										boolean isSuccess = tradeLstDAO.merchantSettleStatisticsUtil(deductStlmDate,dy_merCode, DataStatus.clean, originalDataTableName, instId, merBasic.getMerCategory(),inst_type,obj,settle_flag,bank_id);
										if(isSuccess){
											log.info(stringPingJie.getStringPingJie("深圳工行T+1日  ",dy_merCode,"保存",isSuccess));
										}else{
											log.info(stringPingJie.getStringPingJie("深圳工行T+1日  ",dy_merCode,"失败",isSuccess));
										}
									}else{
										log.info(stringPingJie.getStringPingJie("查询深圳工行交易日期",deductStlmDate,"交易",dy_merCode,"结算商户为空"));
									}
								}
							}
						}
						
						ExecuteNode executeNode  = executeNodeDAO.findExecuteNodeData(deductStlmDate, instInfo.getId().getInstId(), instInfo.getId().getInstType());
						if(executeNode != null){
							/**
							 * 0:未执行、1:执行成功、2:执行失败、3:对账单数据不存在、4:原始交易数据不存在
							 * 0、3、4:表示数据没有勾兑过,不会产生对账数据状态影响
							 */
							Integer dzHandle = executeNode.getDzHandle();
							if(dzHandle != 0 && dzHandle != 3 && dzHandle != 4){
								log.info("开始检测深圳工行原始交易差错重对账处理");
								List<ErrorDataLst> oriList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_original, instInfo.getId().getInstId(), instInfo.getId().getInstType());
								if(oriList != null && oriList.size() > 0){
									log.info(stringPingJie.getStringPingJie("重对账检测到深圳工行原始交易差错条数为:",oriList.size()));
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
									log.info("没有到检测深圳工行原始交易差错数据");
								}
								
								log.info("开始检测北京银行对账单交易数据差错重对账处理");
								List<ErrorDataLst> duizList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_duizhang, instInfo.getId().getInstId(), instInfo.getId().getInstType());
								if(duizList != null && duizList.size() > 0){
									log.info(stringPingJie.getStringPingJie("重对账检测深圳工行对账单交易数据差错条数为:",duizList.size()));
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
									log.info("没有到检测深圳工行对账单交易数据差错数据");
								}
							}else{
								log.info("第一次执行,无需处理重对账差错数据核对");
							}
						}
						
						log.info("开始检测深圳工行对账文件是否有可疑交易数据");
						//处理银联对账文件未对账的可以交易数据
						List<DuizhangSzghLst> szghLsts = duizhangSzghLstDAO.findDateData(duizhangReqTime, DataStatus.not_dz);
						if(szghLsts != null && szghLsts.size() > 0){
							log.info(stringPingJie.getStringPingJie("深圳工行行对账文件可疑交易数据总条数是 ：",szghLsts.size()));
							for (DuizhangSzghLst duizhangSzghLst : szghLsts) {
								errorDataLstDAO.saveErrorData(new ErrorDataLst(duizhangSzghLst));
							}
						}else{
							log.info("没有检测到深圳工行对账文件的可疑交易数据");
						}
					}else{
						throw new OriginalDataNotFoundException("没有获取到电银深圳工行 " +originalPepDate + " 日期的交易数据");
					}
				} catch (Exception e) {
					log.error("深圳工行对账错误 ： "+e);
					throw e;
				}
			}else{
				throw new DuizhangNotFoundException("没有获取到  "+duizhangReqTime+"  日期的深圳工行对账文件数据");
			}
		} catch (Exception e) {
			log.info("深圳工行内部对账错误"+e);
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
