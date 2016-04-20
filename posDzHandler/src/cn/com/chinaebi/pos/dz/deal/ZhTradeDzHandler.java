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
import cn.com.chinaebi.dz.object.DuizhangSzzhLst;
import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.ExecuteNode;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.OriginalSzzhLst;
import cn.com.chinaebi.dz.object.RiqieSzzhLst;
import cn.com.chinaebi.dz.object.dao.DuizhangSzzhLstDAO;
import cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO;
import cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO;
import cn.com.chinaebi.dz.object.dao.InstRateDAO;
import cn.com.chinaebi.dz.object.dao.OriginalSzzhLstDAO;
import cn.com.chinaebi.dz.object.dao.RiqieSzzhLstDAO;
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
 * 深圳中行对账处理
 * @author zhu.hongliang
 * 
 * 
 * 深圳中行对账处理
 * 	1:对账文件内容
 * 	 	a:消费-冲正 会抵消
 *      b:消费-消费撤销会抵消
 *      c:消费-退货不会抵消
 *      d:trade_code = 'REFI' 表示退货交易、trade_code = 'PCEI' 表示消费交易
 *  2:对账规则
 *  	1>:内部对账(存储过程实现 proce_channelBankInnerDz_handler)
 *  		a:消费-冲正 内部勾对
 *     		b:消费-消费撤销内部勾对
 *      2>:对账文件勾对(通过(deductSysReference)参考号和(trade_code)交易码勾对)
 */
public class ZhTradeDzHandler implements TradeDzHandler{
	private Log log = LogFactory.getLog(getClass());
	private cn.com.chinaebi.dz.object.dao.iface.OriginalSzzhLstDAO originalSzzhLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.DuizhangSzzhLstDAO duizhangSzzhLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.RiqieSzzhLstDAO riqieSzzhLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorDataLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO;
	private cn.com.chinaebi.dz.object.dao.iface.InstRateDAO instRateDAO;
	private cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO;
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
		originalSzzhLstDAO = OriginalSzzhLstDAO.getInstance();
		duizhangSzzhLstDAO = DuizhangSzzhLstDAO.getInstance();
		riqieSzzhLstDAO = RiqieSzzhLstDAO.getInstance();
		errorDataLstDAO = ErrorDataLstDAO.getInstance();
		executeNodeDAO = ExecuteNodeDAO.getInstance();
		tradeLstDAO = TradeLstDAO.getInstance();
		instRateDAO = InstRateDAO.getInstance();
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
			if(innertTradeHandler){ //走内部对账流程
				log.info(stringPingJie.getStringPingJie("深圳中行开始勾对内部交易处理:",originalPepDate));
				if(instInfo != null){
					boolean riqieflag = tradeLstDAO.channelInnerDz(deductStlmDate, riqieOriginalTableName, "deduct_stlm_date");
					if(!riqieflag)
						log.error("深圳中行内部日切原始交易勾对处理失败....");
					boolean oriflag = tradeLstDAO.channelInnerDz(deductStlmDate, originalDataTableName, "deduct_stlm_date");
					if(!oriflag)
						log.error("深圳中行内部原始交易勾对处理失败....");
				}else{
					log.info("深圳中行勾对内部获取渠道ID为空");
				}
			}else{
				log.warn(stringPingJie.getStringPingJie(instId ,":无需内部对账"));
			}
		} catch (Exception e) {
			log.info("深圳中行内部对账错误"+e);
		}
		Map<String, String> szzhDz_map = duizhangSzzhLstDAO.findDzFileData(duizhangReqTime);
		if(szzhDz_map != null && szzhDz_map.keySet() != null && szzhDz_map.keySet().size() > 0){
			try {
				List<RiqieSzzhLst> listRiqieSzzhLst = riqieSzzhLstDAO.findRiqieSzzhLst(deductStlmDate);
				if(listRiqieSzzhLst != null && listRiqieSzzhLst.size() > 0){
					log.info("开始处理电银深圳中行日切数据对账...");
					for (RiqieSzzhLst riqieSzzhLst:listRiqieSzzhLst) {
						whetherReturnFee = 0;
						instRateType = 0;
						zf_fee = 0d;

						//计算银行手续费
						if(instRateObj != null){
							whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
							instRateType = Integer.valueOf(instRateObj[0].toString());
							if(instRateType != 2){
								zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(riqieSzzhLst.getTradeAmount()), riqieSzzhLst.getOutAccount(), riqieSzzhLst.getDeductSysId(), inst_type, riqieSzzhLst.getDeductMerCode());
							}
							
							if(instRateType == 1){
								String mcc_code = PoundageCalculate.mccCodeSubstring(riqieSzzhLst.getDeductMerCode());
								if(StringUtils.isNotBlank(mcc_code)){
									boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
									if(riqieSzzhLst.getTrademsgType() == 20){//退货交易
										if(whetherReturnFee == 0 && !lw_flag){//不退还
											zf_fee = 0d;
										}else if(whetherReturnFee == 1 && lw_flag){//退还
											zf_fee = 0d;
										}
									}
								}
							}else if(instRateType == 3){//按固定费率
								//退货交易-不退还手续费
								if(riqieSzzhLst.getTrademsgType() != null && riqieSzzhLst.getTrademsgType() == 20 && whetherReturnFee == 0){
									zf_fee = 0d;
								}
							}
						}
						
						//成功
						if(((StringUtils.equals(riqieSzzhLst.getDeductSysResponse(), "00") && riqieSzzhLst.getDeductSysResponse() != null)||
								(StringUtils.equals(riqieSzzhLst.getDeductRollBkResponse(), "00") && riqieSzzhLst.getDeductRollBkResponse() != null))
								&& riqieSzzhLst.getTrademsgType() != null){
							String zf_file_fee = null;
							switch(riqieSzzhLst.getTrademsgType()){
								case 20:
									zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"REFI");
									if(StringUtils.isEmpty(zf_file_fee)){
										zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"REFP");
									}
									break;
								default:
									zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"PCEI");
									if(StringUtils.isEmpty(zf_file_fee)){
										zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"PCEP");
									}
									break;
							}
							//查询对账文件中是否存在
							//String zf_file_fee = duizhangSzzhLstDAO.findDzFileData(riqieSzzhLst, oriObject, duizObject, instId, DataStatus.riqie, duizhangReqTime,riqieSzzhLst.getTrademsgType());
							if(StringUtils.isNotBlank(zf_file_fee)){
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("成功交易、参考号: ",riqieSzzhLst.getDeductSysReference()," 对账成功,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieSzzhLst.getTrademsgType() == 2 ||  riqieSzzhLst.getTrademsgType() == 56) &&
										!riqieSzzhLst.isDeductRollBk()){//消费or预授权完成
									/*
									 * 1:对账成功、需要清算
									 * 2:修改为商户手续费、退款交易、对账文件手续费
									 */
									riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}else if(riqieSzzhLst.getTrademsgType() == 20){ //退货
									/*
									 * 1:对账成功、需要清算
									 * 2:修改为商户手续费、退款交易、对账文件手续费
									 */
									riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee,zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee,zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}else if((riqieSzzhLst.getTrademsgType() == 18 || riqieSzzhLst.getTrademsgType() == 58)
										&& !riqieSzzhLst.isDeductRollBk()){//撤销or预授权完成撤销
									/*
									 * 1:对账成功、需要清算
									 * 2:修改为商户手续费、退款交易、对账文件手续费
									 */
									riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}else{//冲正
									//获取原笔交易对账信息
									OriginalSzzhLst originalSzzhLst  = originalSzzhLstDAO.queryWhetherDzSuccess(riqieSzzhLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalSzzhLst != null){
										if(originalSzzhLst.getBkChk() == 1){//对账成功
											
											//当前这笔需要清算
											riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										}else if(originalSzzhLst.getBkChk() == 2){//对账失败
											
											/*
											 * 1:当前交易对账失败、不需要清算 
											 * 2:修改为商户手续费、退款交易、对账文件手续费
											 * 3:当前交易进入差错，并且保存"2"部分数据
											 */
											riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//冲正进入差错
											riqieSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieSzzhLst.setBkChk(DataStatus.dz_error);
											riqieSzzhLst.setWhetherQs(false);
											riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
											riqieSzzhLst.setZfFileFee(zf_file_fee);
											riqieSzzhLst.setZfFee(zf_fee);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
											
										}else{
											//当前这笔需要清算
											riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										}
									}else{
										//当前这笔需要清算
										riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
									}
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}
							}else{
								log.info(stringPingJie.getStringPingJie("成功交易、参考号: ",riqieSzzhLst.getDeductSysReference()," 对账文件不存在-未对账,支付手续费:",zf_fee));
								if(riqieSzzhLst.isDeductRollBk()){
									riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateClean(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
								}else{
									riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
								}
							}
						}//超时
						else if(((riqieSzzhLst.getDeductResult() != null && riqieSzzhLst.getDeductResult() == 1) || 
								(riqieSzzhLst.getDeductRollBkResult() != null && riqieSzzhLst.getDeductRollBkResult() == 1))
								&& riqieSzzhLst.getTrademsgType() != null){
							
							String zf_file_fee = null;
							switch(riqieSzzhLst.getTrademsgType()){
								case 20:
									zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"REFI");
									if(StringUtils.isEmpty(zf_file_fee)){
										zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"REFP");
									}
									break;
								default:
									zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"PCEI");
									if(StringUtils.isEmpty(zf_file_fee)){
										zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"PCEP");
									}
									break;
							}
							//查询对账文件中是否存在
							//String zf_file_fee = duizhangSzzhLstDAO.findDzFileData(riqieSzzhLst, oriObject, duizObject, instId, DataStatus.riqie, duizhangReqTime,riqieSzzhLst.getTrademsgType());
							if(StringUtils.isNotBlank(zf_file_fee)){//存在
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("超时交易、参考号: ",riqieSzzhLst.getDeductSysReference()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieSzzhLst.getTrademsgType() == 2 || riqieSzzhLst.getTrademsgType() == 56) &&
										!riqieSzzhLst.isDeductRollBk()){//消费
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieSzzhLst.setBkChk(DataStatus.dz_error);
									riqieSzzhLst.setWhetherQs(false);
									riqieSzzhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieSzzhLst.setZfFileFee(zf_file_fee);
									riqieSzzhLst.setZfFee(zf_fee);
									riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
									//修改对账文件-对账成功
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}else if(riqieSzzhLst.getTrademsgType() == 20){ //退货
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieSzzhLst.setBkChk(DataStatus.dz_error);
									riqieSzzhLst.setWhetherQs(false);
									riqieSzzhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieSzzhLst.setZfFileFee(zf_file_fee);
									riqieSzzhLst.setZfFee(zf_fee);
									riqieSzzhLst.setWhetherTk(DataStatus.tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
									
									//修改对账文件-对账成功
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								
									
								}else if((riqieSzzhLst.getTrademsgType() == 18 || riqieSzzhLst.getTrademsgType() == 58) 
										&& !riqieSzzhLst.isDeductRollBk()){//撤销
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieSzzhLst.setBkChk(DataStatus.dz_error);
									riqieSzzhLst.setWhetherQs(false);
									riqieSzzhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieSzzhLst.setZfFileFee(zf_file_fee);
									riqieSzzhLst.setZfFee(zf_fee);
									riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
									
									//修改对账文件-对账成功
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								
								}else{//冲正
									OriginalSzzhLst originalSzzhLst  = originalSzzhLstDAO.queryWhetherDzSuccessAll(riqieSzzhLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalSzzhLst != null){
										if(originalSzzhLst.getBkChk() == 1){ //对账成功
											//当前这笔需要清算
											riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											
											if(!riqieSzzhLst.isWhetherAccessStance()){
												boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieSzzhLst.getTradeAmount().toString(), riqieSzzhLst.getReqMerCode(), instInfo.getGate(), riqieSzzhLst.getDeductSysTime(), 
														riqieSzzhLst.getDeductRollbkSysTime(), riqieSzzhLst.getDeductSysStance(), riqieSzzhLst.isDeductRollBk(), 
														instId, inst_type, deductStlmDate, bank_id, riqieSzzhLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
												if(update_flag){
													log.info(stringPingJie.getStringPingJie("超时交易、流水: ",riqieSzzhLst.getDeductSysStance()," 对账成功-进入资金流水"));
												}
											}
										}else if(originalSzzhLst.getBkChk() == 2){ //对账失败
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieSzzhLst.setBkChk(DataStatus.dz_error);
											riqieSzzhLst.setWhetherQs(false);
											riqieSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieSzzhLst.setZfFileFee(zf_file_fee);
											riqieSzzhLst.setZfFee(zf_fee);
											riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
										}else{
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieSzzhLst.setBkChk(DataStatus.dz_error);
											riqieSzzhLst.setWhetherQs(false);
											riqieSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieSzzhLst.setZfFileFee(zf_file_fee);
											riqieSzzhLst.setZfFee(zf_fee);
											riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
										}
									}else{
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieSzzhLst.setBkChk(DataStatus.dz_error);
										riqieSzzhLst.setWhetherQs(false);
										riqieSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieSzzhLst.setZfFileFee(zf_file_fee);
										riqieSzzhLst.setZfFee(zf_fee);
										riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
									}
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}
							}else{//不存在
								log.info(stringPingJie.getStringPingJie("超时交易、参考号: ",riqieSzzhLst.getDeductSysReference()," 对账文件不存在-无需对账,支付手续费:",zf_fee));
								if((riqieSzzhLst.getTrademsgType() == 2 || riqieSzzhLst.getTrademsgType() == 18
										|| riqieSzzhLst.getTrademsgType() == 20 || riqieSzzhLst.getTrademsgType() == 56 || riqieSzzhLst.getTrademsgType() == 58) 
										&&  !riqieSzzhLst.isDeductRollBk()){
									riqieSzzhLstDAO.updateNoNeedHandle(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzzhLst.getTrademsgType(), deductStlmDate);
									originalSzzhLstDAO.updateNoNeedHandle(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzzhLst.getTrademsgType(), deductStlmDate);
									
								}else{
									riqieSzzhLstDAO.updateNoNeedHandle(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzzhLst.getTrademsgType(), deductStlmDate);
									originalSzzhLstDAO.updateNoNeedHandle(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzzhLst.getTrademsgType(), deductStlmDate);
								}
							}
						}else{//失败
							String zf_file_fee = null;
							switch(riqieSzzhLst.getTrademsgType()){
								case 20:
									zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"REFI");
									if(StringUtils.isEmpty(zf_file_fee)){
										zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"REFP");
									}
									break;
								default:
									zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"PCEI");
									if(StringUtils.isEmpty(zf_file_fee)){
										zf_file_fee = szzhDz_map.get(riqieSzzhLst.getDeductSysReference()+"PCEP");
									}
									break;
							}
							//查询对账文件中是否存在
							//String zf_file_fee = duizhangSzzhLstDAO.findDzFileData(riqieSzzhLst, oriObject, duizObject, instId, DataStatus.riqie, duizhangReqTime,riqieSzzhLst.getTrademsgType());
							if(StringUtils.isNotBlank(zf_file_fee)){//存在
								if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
									zf_fee = Double.valueOf(zf_file_fee);
								}
								log.info(stringPingJie.getStringPingJie("失败交易、参考号: ",riqieSzzhLst.getDeductSysReference()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee));
								if((riqieSzzhLst.getTrademsgType() == 2 || riqieSzzhLst.getTrademsgType() == 56) &&
										!riqieSzzhLst.isDeductRollBk()){//消费
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieSzzhLst.setBkChk(DataStatus.dz_error);
									riqieSzzhLst.setWhetherQs(false);
									riqieSzzhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieSzzhLst.setZfFileFee(zf_file_fee);
									riqieSzzhLst.setZfFee(zf_fee);
									riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
									//修改对账文件-对账成功
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}else if(riqieSzzhLst.getTrademsgType() == 20){ //退货
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieSzzhLst.setBkChk(DataStatus.dz_error);
									riqieSzzhLst.setWhetherQs(false);
									riqieSzzhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieSzzhLst.setZfFileFee(zf_file_fee);
									riqieSzzhLst.setZfFee(zf_fee);
									riqieSzzhLst.setWhetherTk(DataStatus.tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
									
									//修改对账文件-对账成功
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								
									
								}else if((riqieSzzhLst.getTrademsgType() == 18 || riqieSzzhLst.getTrademsgType() == 58) 
										&& !riqieSzzhLst.isDeductRollBk()){//撤销
									/*
									 * 1:修改不需要清算--对账失败
									 * 2:修改结算信息
									 */
									riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status,riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate);
									riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.no_deductRollBk, deductStlmDate,whetherReturnFee);
									//进入差错-需要处理结算信息
									riqieSzzhLst.setBkChk(DataStatus.dz_error);
									riqieSzzhLst.setWhetherQs(false);
									riqieSzzhLst.setWhetherErroeHandle(DataStatus.long_status);
									riqieSzzhLst.setZfFileFee(zf_file_fee);
									riqieSzzhLst.setZfFee(zf_fee);
									riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
									errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
									
									//修改对账文件-对账成功
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}else{//冲正
									OriginalSzzhLst originalSzzhLst  = originalSzzhLstDAO.queryWhetherDzSuccessAll(riqieSzzhLst.getReqSysStance(), deductStlmDate, DataStatus.no_deductRollBk);
									if(originalSzzhLst != null){
										if(originalSzzhLst.getBkChk() == 1){ //对账成功
											//当前这笔需要清算
											riqieSzzhLstDAO.updateClean(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, DataStatus.no_error_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											
											if(!riqieSzzhLst.isWhetherAccessStance()){
												boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(riqieSzzhLst.getTradeAmount().toString(), riqieSzzhLst.getReqMerCode(), instInfo.getGate(), riqieSzzhLst.getDeductSysTime(), 
														riqieSzzhLst.getDeductRollbkSysTime(), riqieSzzhLst.getDeductSysStance(), riqieSzzhLst.isDeductRollBk(), 
														instId, inst_type, deductStlmDate, bank_id, riqieSzzhLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
												if(update_flag){
													log.info(stringPingJie.getStringPingJie("失败交易、流水: ",riqieSzzhLst.getDeductSysStance()," 对账成功-进入资金流水"));
												}
											}
										}else if(originalSzzhLst.getBkChk() == 2){ //对账失败
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieSzzhLst.setBkChk(DataStatus.dz_error);
											riqieSzzhLst.setWhetherQs(false);
											riqieSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieSzzhLst.setZfFileFee(zf_file_fee);
											riqieSzzhLst.setZfFee(zf_fee);
											riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
										}else{
											/*
											 * 1:修改不需要清算--对账失败
											 * 2:修改结算信息
											 */
											riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
											riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
											//进入差错-需要处理结算信息
											riqieSzzhLst.setBkChk(DataStatus.dz_error);
											riqieSzzhLst.setWhetherQs(false);
											riqieSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
											riqieSzzhLst.setZfFileFee(zf_file_fee);
											riqieSzzhLst.setZfFee(zf_fee);
											riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
										}
									}else{
										/*
										 * 1:修改不需要清算--对账失败
										 * 2:修改结算信息
										 */
										riqieSzzhLstDAO.updateCleanAndError(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										originalSzzhLstDAO.updateCleanRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status,riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate);
										riqieSzzhLstDAO.updateSettleInfo(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(), DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										originalSzzhLstDAO.updateSettleInfoRiqie(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, riqieSzzhLst.getTrademsgType(),DataStatus.deductRollBk, deductStlmDate,whetherReturnFee);
										//进入差错-需要处理结算信息
										riqieSzzhLst.setBkChk(DataStatus.dz_error);
										riqieSzzhLst.setWhetherQs(false);
										riqieSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
										riqieSzzhLst.setZfFileFee(zf_file_fee);
										riqieSzzhLst.setZfFee(zf_fee);
										riqieSzzhLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(riqieSzzhLst));
									}
									duizhangSzzhLstDAO.updateClean(riqieSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,riqieSzzhLst.getTrademsgType());
								}
							}else{//不存在
								log.info(stringPingJie.getStringPingJie("失败交易、参考号: ",riqieSzzhLst.getDeductSysReference()," 对账文件不存在-无需对账,支付手续费:",zf_fee));
								if((riqieSzzhLst.getTrademsgType() == 2 || riqieSzzhLst.getTrademsgType() == 18
										|| riqieSzzhLst.getTrademsgType() == 20 || riqieSzzhLst.getTrademsgType() == 56 || riqieSzzhLst.getTrademsgType() == 58) 
										&&  !riqieSzzhLst.isDeductRollBk()){
									riqieSzzhLstDAO.updateNoNeedHandle(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzzhLst.getTrademsgType(), deductStlmDate);
									originalSzzhLstDAO.updateNoNeedHandle(riqieSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzzhLst.getTrademsgType(), deductStlmDate);
									
								}else{
									riqieSzzhLstDAO.updateNoNeedHandle(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzzhLst.getTrademsgType(), deductStlmDate);
									originalSzzhLstDAO.updateNoNeedHandle(riqieSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, riqieSzzhLst.getTrademsgType(), deductStlmDate);
								}
							}
						}
					}
				}else{
					log.error(stringPingJie.getStringPingJie("没有获取到  ",deductStlmDate,"  日期的深圳中行原始交易日切数据"));
				}
			} catch (Exception e) {
				log.error("深圳中行日切对账错误: "+e);
			}
			
			try {
				List<OriginalSzzhLst> listOriginalSzzhLst = originalSzzhLstDAO.findOriginalSzzhLst(originalPepDate);
				if(listOriginalSzzhLst != null && listOriginalSzzhLst.size() > 0){
					log.info("开始处理电银深圳中行数据对账...");
					for (OriginalSzzhLst originalSzzhLst:listOriginalSzzhLst) {
						whetherReturnFee = 0;
						zf_fee = 0d;
						String _deductStlmDate = dateFormat.format(originalSzzhLst.getDeductStlmDate());
						String date = DYDataUtil.getNextDateString(originalPepDate);
						if(StringUtils.equalsIgnoreCase(_deductStlmDate,date)){//日切
							//处理日切交易数据
							log.info(stringPingJie.getStringPingJie("大连交行日切数据流水:",originalSzzhLst.getDeductSysStance()," 日期:",_deductStlmDate));
							originalSzzhLstDAO.updateDataRiqie(originalSzzhLst.getDeductSysStance(), DataStatus.riqie,originalPepDate);
							originalSzzhLst.setWhetherRiqie(true);
							riqieSzzhLstDAO.saveRiqieDljhLst(new RiqieSzzhLst(originalSzzhLst), flagStatus);
						}else{

							//计算银行手续费
							instRateType = 0;
							if(instRateObj != null){
								whetherReturnFee = Integer.valueOf(instRateObj[3].toString());
								instRateType = Integer.valueOf(instRateObj[0].toString());
								if(instRateType != 2){
									zf_fee = PoundageCalculate.getOfflineTradeMerFeeUtil(instRateObj,String.valueOf(originalSzzhLst.getTradeAmount()), originalSzzhLst.getOutAccount(), originalSzzhLst.getDeductSysId(), inst_type, originalSzzhLst.getDeductMerCode());
								}
								
								if(instRateType == 1){
									String mcc_code = PoundageCalculate.mccCodeSubstring(originalSzzhLst.getDeductMerCode());
									if(StringUtils.isNotBlank(mcc_code)){
										boolean lw_flag = rateMapSize == 0 ? false : (rateMap.get(mcc_code) == null ? false : true);
										if(originalSzzhLst.getTrademsgType() == 20){//退货交易
											if(whetherReturnFee == 0 && !lw_flag){//不退还
												zf_fee = 0d;
											}else if(whetherReturnFee == 1 && lw_flag){//退还
												zf_fee = 0d;
											}
										}
									}
								}else if(instRateType == 3){//按固定费率
									//退货交易-不退还手续费
									if(originalSzzhLst.getTrademsgType() != null && originalSzzhLst.getTrademsgType() == 20 && whetherReturnFee == 0){
										zf_fee = 0d;
									}
								}
							}
							
							//成功
							if(((StringUtils.equals(originalSzzhLst.getDeductSysResponse(), "00") && originalSzzhLst.getDeductSysResponse() != null)||
									(StringUtils.equals(originalSzzhLst.getDeductRollBkResponse(), "00") && originalSzzhLst.getDeductRollBkResponse() != null))
									&& originalSzzhLst.getTrademsgType() != null){
								String zf_file_fee = null;
								switch(originalSzzhLst.getTrademsgType()){
									case 20:
										zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"REFI");
										if(StringUtils.isEmpty(zf_file_fee)){
											zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"REFP");
										}
										break;
									default:
										zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"PCEI");
										if(StringUtils.isEmpty(zf_file_fee)){
											zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"PCEP");
										}
										break;
								}
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangSzzhLstDAO.findDzFileData(originalSzzhLst, oriObject, duizObject, instId, DataStatus.no_riqie, duizhangReqTime,originalSzzhLst.getTrademsgType());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("成功交易、参考号: ",originalSzzhLst.getDeductSysReference()," 对账成功,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
									if((originalSzzhLst.getTrademsgType() == 2 || originalSzzhLst.getTrademsgType() == 56) &&
											!originalSzzhLst.isDeductRollBk()){//消费or预授权完成
										originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}else if(originalSzzhLst.getTrademsgType() == 20){ //退货
										originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}else if((originalSzzhLst.getTrademsgType() == 18 || originalSzzhLst.getTrademsgType() == 58) 
											&& !originalSzzhLst.isDeductRollBk()){//撤销
										originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysStance(), DataStatus.clean, DataStatus.dz_success, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}else{//冲正
										OriginalSzzhLst originalSzzhLst2 =  originalSzzhLstDAO.queryWhetherDzSuccess(originalSzzhLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
										if(originalSzzhLst2 != null){
											if(originalSzzhLst2.getBkChk() == 1){//对账成功
												originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}else if(originalSzzhLst2.getBkChk() == 2){ //对账失败
												originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalSzzhLst.setBkChk(DataStatus.dz_error);
												originalSzzhLst.setWhetherQs(false);
												originalSzzhLst.setZfFileFee(zf_file_fee);
												originalSzzhLst.setZfFee(zf_fee);
												originalSzzhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}else{
												originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
										}else{
											originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
											originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
										}
										
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}
								}else{
									log.info(stringPingJie.getStringPingJie("成功交易、参考号: ",originalSzzhLst.getDeductSysReference()," 对账文件不存在-未对账,支付手续费:",zf_fee));
									if(originalSzzhLst.isDeductRollBk()){
										originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.not_dz, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
									}else{
										originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.not_dz, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
									}
								}
							}//超时
							else if(((originalSzzhLst.getDeductResult() != null && originalSzzhLst.getDeductResult() == 1) || 
									(originalSzzhLst.getDeductRollBkResult() != null && originalSzzhLst.getDeductRollBkResult() == 1))
									&& originalSzzhLst.getTrademsgType() != null){
								
								String zf_file_fee = null;
								switch(originalSzzhLst.getTrademsgType()){
									case 20:
										zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"REFI");
										if(StringUtils.isEmpty(zf_file_fee)){
											zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"REFP");
										}
										break;
									default:
										zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"PCEI");
										if(StringUtils.isEmpty(zf_file_fee)){
											zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"PCEP");
										}
										break;
								}
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangSzzhLstDAO.findDzFileData(originalSzzhLst, oriObject, duizObject, instId, DataStatus.no_riqie, duizhangReqTime,originalSzzhLst.getTrademsgType());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("超时交易、参考号: ",originalSzzhLst.getDeductSysReference()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
									if((originalSzzhLst.getTrademsgType() == 2 || originalSzzhLst.getTrademsgType() == 56) &&
											!originalSzzhLst.isDeductRollBk()){//消费or预授权完成
										//修改不需要清算--对账失败
										originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLst.setWhetherErroeHandle(DataStatus.long_status);
										originalSzzhLst.setBkChk(DataStatus.dz_error);
										originalSzzhLst.setWhetherQs(false);
										originalSzzhLst.setWhetherTk(DataStatus.no_tk);
										originalSzzhLst.setZfFileFee(zf_file_fee);
										originalSzzhLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
										//修改结算信息
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}else if(originalSzzhLst.getTrademsgType() == 20){ //退货
										originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
										originalSzzhLst.setBkChk(DataStatus.dz_error);
										originalSzzhLst.setWhetherQs(false);
										originalSzzhLst.setZfFileFee(zf_file_fee);
										originalSzzhLst.setZfFee(zf_fee);
										originalSzzhLst.setWhetherTk(DataStatus.tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}else if((originalSzzhLst.getTrademsgType() == 18 || originalSzzhLst.getTrademsgType() == 58) 
											&& !originalSzzhLst.isDeductRollBk()){//撤销

										originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
										originalSzzhLst.setBkChk(DataStatus.dz_error);
										originalSzzhLst.setWhetherQs(false);
										originalSzzhLst.setZfFileFee(zf_file_fee);
										originalSzzhLst.setZfFee(zf_fee);
										originalSzzhLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}else{//冲正
										OriginalSzzhLst originalSzzhLst2 =  originalSzzhLstDAO.queryWhetherDzSuccessAll(originalSzzhLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
										if(originalSzzhLst2 != null){
											if(originalSzzhLst2.getBkChk() == 1){//对账成功
												originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
												
												if(!originalSzzhLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(originalSzzhLst.getTradeAmount().toString(), originalSzzhLst.getReqMerCode(), instInfo.getGate(), originalSzzhLst.getDeductSysTime(), 
															originalSzzhLst.getDeductRollbkSysTime(), originalSzzhLst.getDeductSysStance(), originalSzzhLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, originalSzzhLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("超时交易、流水: ",originalSzzhLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
											}else if(originalSzzhLst2.getBkChk() == 2){ //对账失败
												originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalSzzhLst.setBkChk(DataStatus.dz_error);
												originalSzzhLst.setWhetherQs(false);
												originalSzzhLst.setZfFileFee(zf_file_fee);
												originalSzzhLst.setZfFee(zf_fee);
												originalSzzhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}else{
												originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalSzzhLst.setBkChk(DataStatus.dz_error);
												originalSzzhLst.setWhetherQs(false);
												originalSzzhLst.setZfFileFee(zf_file_fee);
												originalSzzhLst.setZfFee(zf_fee);
												originalSzzhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
										}else{
											originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
											//冲正进入差错
											originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
											originalSzzhLst.setBkChk(DataStatus.dz_error);
											originalSzzhLst.setWhetherQs(false);
											originalSzzhLst.setZfFileFee(zf_file_fee);
											originalSzzhLst.setZfFee(zf_fee);
											originalSzzhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
											originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
										}
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}
								}else{//不存在
									log.info(stringPingJie.getStringPingJie("超时交易、参考号: ",originalSzzhLst.getDeductSysReference()," 对账文件不存在-无需对账,支付手续费:",zf_fee));
									if((originalSzzhLst.getTrademsgType() == 2 || originalSzzhLst.getTrademsgType() == 18
											|| originalSzzhLst.getTrademsgType() == 20 || originalSzzhLst.getTrademsgType() == 56 || originalSzzhLst.getTrademsgType() == 58) 
											&&  !originalSzzhLst.isDeductRollBk()){
										originalSzzhLstDAO.updateNoNeedHandle(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, originalSzzhLst.getTrademsgType(), originalPepDate);
									}else{
										originalSzzhLstDAO.updateNoNeedHandle(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, originalSzzhLst.getTrademsgType(), originalPepDate);
									}
								}
							}//失败
							else{
								String zf_file_fee = null;
								switch(originalSzzhLst.getTrademsgType()){
									case 20:
										zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"REFI");
										if(StringUtils.isEmpty(zf_file_fee)){
											zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"REFP");
										}
										break;
									default:
										zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"PCEI");
										if(StringUtils.isEmpty(zf_file_fee)){
											zf_file_fee = szzhDz_map.get(originalSzzhLst.getDeductSysReference()+"PCEP");
										}
										break;
								}
								//查询对账文件中是否存在
								//String zf_file_fee = duizhangSzzhLstDAO.findDzFileData(originalSzzhLst, oriObject, duizObject, instId, DataStatus.no_riqie, duizhangReqTime,originalSzzhLst.getTrademsgType());
								if(StringUtils.isNotBlank(zf_file_fee)){//存在
									if(instRateObj != null && instRateType == 2){//获取对账文件银行手续费
										zf_fee = Double.valueOf(zf_file_fee);
									}
									log.info(stringPingJie.getStringPingJie("失败交易、参考号: ",originalSzzhLst.getDeductSysReference()," 对账成功-进入原始交易差错,支付手续费:",zf_fee,",对账文件手续费:",zf_file_fee,", ",_deductStlmDate,"->",date));
									if((originalSzzhLst.getTrademsgType() == 2 || originalSzzhLst.getTrademsgType() == 56) &&
											!originalSzzhLst.isDeductRollBk()){//消费or预授权完成
										//修改不需要清算--对账失败
										originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.long_status, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLst.setWhetherErroeHandle(DataStatus.long_status);
										originalSzzhLst.setBkChk(DataStatus.dz_error);
										originalSzzhLst.setWhetherQs(false);
										originalSzzhLst.setWhetherTk(DataStatus.no_tk);
										originalSzzhLst.setZfFileFee(zf_file_fee);
										originalSzzhLst.setZfFee(zf_fee);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
										//修改结算信息
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									
									}else if(originalSzzhLst.getTrademsgType() == 20){ //退货

										originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
										originalSzzhLst.setBkChk(DataStatus.dz_error);
										originalSzzhLst.setWhetherQs(false);
										originalSzzhLst.setZfFileFee(zf_file_fee);
										originalSzzhLst.setZfFee(zf_fee);
										originalSzzhLst.setWhetherTk(DataStatus.tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);

										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}else if((originalSzzhLst.getTrademsgType() == 18 || originalSzzhLst.getTrademsgType() == 58) 
											&& !originalSzzhLst.isDeductRollBk()){//撤销
										
										originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate);
										originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
										originalSzzhLst.setBkChk(DataStatus.dz_error);
										originalSzzhLst.setWhetherQs(false);
										originalSzzhLst.setZfFileFee(zf_file_fee);
										originalSzzhLst.setZfFee(zf_fee);
										originalSzzhLst.setWhetherTk(DataStatus.no_tk);
										errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
										originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductSysStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.no_deductRollBk, originalPepDate,whetherReturnFee);
										
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.no_deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}else{//冲正
										OriginalSzzhLst originalSzzhLst2 =  originalSzzhLstDAO.queryWhetherDzSuccess(originalSzzhLst.getReqSysStance(), _deductStlmDate, DataStatus.no_deductRollBk);
										if(originalSzzhLst2 != null){
											if(originalSzzhLst2.getBkChk() == 1){//对账成功
												originalSzzhLstDAO.updateClean(originalSzzhLst.getDeductRollBkStance(), DataStatus.clean, DataStatus.dz_success, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											
												if(!originalSzzhLst.isWhetherAccessStance()){
													boolean update_flag = tradeLstDAO.updateDeductRollBkStanceHandler(originalSzzhLst.getTradeAmount().toString(), originalSzzhLst.getReqMerCode(), instInfo.getGate(), originalSzzhLst.getDeductSysTime(), 
															originalSzzhLst.getDeductRollbkSysTime(), originalSzzhLst.getDeductSysStance(), originalSzzhLst.isDeductRollBk(), 
															instId, inst_type, deductStlmDate, bank_id, originalSzzhLst.getId(), originalDataTableName, riqieOriginalTableName, columnName, whether_flag);
													if(update_flag){
														log.info(stringPingJie.getStringPingJie("失败交易、流水: ",originalSzzhLst.getDeductSysStance()," 对账成功-进入资金流水"));
													}
												}
											}else if(originalSzzhLst2.getBkChk() == 2){ //对账失败
												originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalSzzhLst.setBkChk(DataStatus.dz_error);
												originalSzzhLst.setWhetherQs(false);
												originalSzzhLst.setZfFileFee(zf_file_fee);
												originalSzzhLst.setZfFee(zf_fee);
												originalSzzhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}else{
												originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
												//冲正进入差错
												originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
												originalSzzhLst.setBkChk(DataStatus.dz_error);
												originalSzzhLst.setWhetherQs(false);
												originalSzzhLst.setZfFileFee(zf_file_fee);
												originalSzzhLst.setZfFee(zf_fee);
												originalSzzhLst.setWhetherTk(DataStatus.no_tk);
												errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
												originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
											}
										}else{
											originalSzzhLstDAO.updateCleanAndError(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.dz_error, DataStatus.short_status, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate);
											//冲正进入差错
											originalSzzhLst.setWhetherErroeHandle(DataStatus.short_status);
											originalSzzhLst.setBkChk(DataStatus.dz_error);
											originalSzzhLst.setWhetherQs(false);
											originalSzzhLst.setZfFileFee(zf_file_fee);
											originalSzzhLst.setZfFee(zf_fee);
											originalSzzhLst.setWhetherTk(DataStatus.no_tk);
											errorDataLstDAO.saveErrorData(new ErrorDataLst(originalSzzhLst));
											originalSzzhLstDAO.updateSettleInfo(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_tk,zf_fee, zf_file_fee, originalSzzhLst.getTrademsgType(), DataStatus.deductRollBk, originalPepDate,whetherReturnFee);
										}
										duizhangSzzhLstDAO.updateClean(originalSzzhLst.getDeductSysReference(), DataStatus.dz_success, DataStatus.deductRollBk, duizhangReqTime,originalSzzhLst.getTrademsgType());
									}
								}else{//不存在
									log.info(stringPingJie.getStringPingJie("失败交易、参考号: ",originalSzzhLst.getDeductSysReference()," 对账文件不存在-无需对账,支付手续费:",zf_fee));
									if((originalSzzhLst.getTrademsgType() == 2 || originalSzzhLst.getTrademsgType() == 18
											|| originalSzzhLst.getTrademsgType() == 20 || originalSzzhLst.getTrademsgType() == 56 || originalSzzhLst.getTrademsgType() == 58) 
											&&  !originalSzzhLst.isDeductRollBk()){
										originalSzzhLstDAO.updateNoNeedHandle(originalSzzhLst.getDeductSysStance(), DataStatus.no_clean, DataStatus.no_handle, originalSzzhLst.getTrademsgType(), originalPepDate);
									}else{
										originalSzzhLstDAO.updateNoNeedHandle(originalSzzhLst.getDeductRollBkStance(), DataStatus.no_clean, DataStatus.no_handle, originalSzzhLst.getTrademsgType(), originalPepDate);
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
								if(objMer != null && map.get(dy_merCode) != null){
									MerBasic merBasic = (MerBasic)objMer;
									Object[] obj = tradeLstDAO.findPosChanneltotall(deductStlmDate, dy_merCode, DataStatus.clean, originalDataTableName);
									boolean isSuccess = tradeLstDAO.merchantSettleStatisticsUtil(deductStlmDate,settleMerCode, DataStatus.clean, originalDataTableName, instId, merBasic.getMerCategory(),inst_type,obj,settle_flag,bank_id);
									if(isSuccess){
										log.info(stringPingJie.getStringPingJie("深圳中行T+1日  ",dy_merCode,"-->",settleMerCode,"保存",isSuccess));
									}else{
										log.info(stringPingJie.getStringPingJie("深圳中行T+1日  ",dy_merCode,"-->",settleMerCode,"失败",isSuccess));
									}
								}else{
									log.info(stringPingJie.getStringPingJie("查询深圳中行交易日期",deductStlmDate,"交易",settleMerCode,"结算商户为空"));
								}
							}else{
								Object objMer = mapMerBasic.get(dy_merCode);
								if(objMer != null){
									MerBasic merBasic = (MerBasic)objMer;
									Object[] obj = tradeLstDAO.findPosChanneltotall(deductStlmDate, dy_merCode, DataStatus.clean, originalDataTableName);
									boolean isSuccess = tradeLstDAO.merchantSettleStatisticsUtil(deductStlmDate,dy_merCode, DataStatus.clean, originalDataTableName, instId, merBasic.getMerCategory(),inst_type,obj,settle_flag,bank_id);
									if(isSuccess){
										log.info(stringPingJie.getStringPingJie("深圳中行T+1日  ",dy_merCode,"保存",isSuccess));
									}else{
										log.info(stringPingJie.getStringPingJie("深圳中行T+1日  ",dy_merCode,"失败",isSuccess));
									}
								}else{
									log.info(stringPingJie.getStringPingJie("查询深圳中行交易日期",deductStlmDate,"交易",dy_merCode,"结算商户为空"));
								}
							}
						}
					}
					
					log.info("开始汇总深圳中行对账之后数据汇总");
					channelDzCollectDAO = cn.com.chinaebi.dz.object.dao.ChannelDzCollectDAO.getInstance();
					int count = channelDzCollectDAO.savePosChannelDzCollect(instInfo, deductStlmDate,map,mapMerBasic);
					log.info(stringPingJie.getStringPingJie("银联CUPS对账之后汇总条数",count));
					log.info("结束汇总深圳中行对账之后数据汇总");
					
					ExecuteNode executeNode  = executeNodeDAO.findExecuteNodeData(deductStlmDate, instInfo.getId().getInstId(), instInfo.getId().getInstType());
					if(executeNode != null){
						/**
						 * 0:未执行、1:执行成功、2:执行失败、3:对账单数据不存在、4:原始交易数据不存在
						 * 0、3、4:表示数据没有勾兑过,不会产生对账数据状态影响
						 */
						Integer dzHandle = executeNode.getDzHandle();
						if(dzHandle != 0 && dzHandle != 3 && dzHandle != 4){
							log.info("开始检测深圳中行原始交易差错重对账处理");
							List<ErrorDataLst> oriList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_original, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							if(oriList != null && oriList.size() > 0){
								log.info(stringPingJie.getStringPingJie("重对账检测到深圳中行原始交易差错条数为:",oriList.size()));
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
								log.info("没有到检测深圳中行原始交易差错数据");
							}
							
							log.info("开始检测深圳中行对账单交易数据差错重对账处理");
							List<ErrorDataLst> duizList = errorDataLstDAO.findOriErrorChannelData(deductStlmDate, DataStatus.error_resouce_duizhang, instInfo.getId().getInstId(), instInfo.getId().getInstType());
							if(duizList != null && duizList.size() > 0){
								log.info(stringPingJie.getStringPingJie("重对账检测深圳中行对账单交易数据差错条数为:",duizList.size()));
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
								log.info("没有到检测深圳中行对账单交易数据差错数据");
							}
						}else{
							log.info("第一次执行,无需处理重对账差错数据核对");
						}
					}
					
					log.info("开始检测深圳中行对账文件是否有可疑交易数据");
					//处理银联对账文件未对账的可以交易数据
					List<DuizhangSzzhLst> szzhLsts = duizhangSzzhLstDAO.findDateData(duizhangReqTime, DataStatus.not_dz);
					if(szzhLsts != null && szzhLsts.size() > 0){
						log.info(stringPingJie.getStringPingJie("深圳中行对账文件可疑交易数据总条数是:",szzhLsts.size()));
						for (DuizhangSzzhLst duizhangSzzhLst:szzhLsts) {
							errorDataLstDAO.saveErrorData(new ErrorDataLst(duizhangSzzhLst));
						}
					}else{
						log.info("没有检测到深圳中行对账文件的可疑交易数据");
					}
				}else{
					throw new OriginalDataNotFoundException("没有获取到电银深圳中行 " +originalPepDate + " 日期的交易数据");
				}
			} catch (Exception e) {
				log.error("深圳中行对账错误: "+e);
				throw e;
			}
		}else{
			throw new DuizhangNotFoundException("没有获取到  "+duizhangReqTime+"  日期的深圳中行对账文件数据");
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
