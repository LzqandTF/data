package com.chinaebi.serviceImpl;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.com.chinaebi.dz.webservice.CreateSettleFileDocument;
import cn.com.chinaebi.dz.webservice.CreateSettleFileType;

import com.chinaebi.dao.ChannelDzCollectDao;
import com.chinaebi.dao.MerFundStanceDao;
import com.chinaebi.dao.MerchantFundSettleDao;
import com.chinaebi.dao.MerchantsBalanceDao;
import com.chinaebi.dao.MerchantsDao;
import com.chinaebi.entity.ChannelDzCollect;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.entity.MerchantFundSettle;
import com.chinaebi.entity.Merchants;
import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.entity.SettleMerchantMatch;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.service.MerchantFundSettleService;
import com.chinaebi.utils.Base64;
import com.chinaebi.utils.Common;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.EncodeUtils;
import com.chinaebi.utils.HttpUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.RequestUrlConf;
import com.chinaebi.utils.ReturnXmlHandler;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;
import com.sun.jmx.snmp.Timestamp;

@Service(value = "merchantFundSettleService")
public class MerchantFundSettleServiceImpl implements MerchantFundSettleService {
	private static Logger log = LoggerFactory.getLogger(MerchantFundSettleServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "merchantFundSettleDao")
	private MerchantFundSettleDao merchantFundSettleDao;
	
	@Autowired
	@Qualifier(value = "channelDzCollectDao")
	private ChannelDzCollectDao channelDzCollectDao;
	
	@Autowired
	@Qualifier(value = "merchantsBalanceDao")
	private MerchantsBalanceDao merchantsBalanceDao;
	@Autowired
	@Qualifier(value = "merchantsDao")
	private MerchantsDao merchantsDao;
	@Autowired
	@Qualifier(value = "merFundStanceDao")
	private MerFundStanceDao merFundStanceDao;
	
	private static int sucessNum = 0;
	
	@Override
	public Page<MerchantFundSettle> queryPageMerchantFundSettle(
			Page<MerchantFundSettle> page, Map<String, Object> map) {
		Page<MerchantFundSettle> pageList = null;
		try{
			pageList = merchantFundSettleDao.queryPageMerchantFundSettle(page, map);
			if(pageList.getResult() ==null){
				throw new PageException("merchantFundSettleDao.queryPageMerchantFundSettle(page, map)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<MerchantFundSettle> queryMerchantFundSettleList(
			Map<String, Object> map) {
		List<MerchantFundSettle> list = null;
		try {
			list = merchantFundSettleDao.queryMerchantFundSettleList(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int addMerchantFundSettle(MerchantFundSettle merchantFundSettle) {
		int result = 0;
		try{
			result = merchantFundSettleDao.addMerchantFundSettle(merchantFundSettle);
			if(result <= 0){
				throw new InsertException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public String queryMaxSysBatchNo(int end_date) {
		String sys_batch_no  = "";
		try{
			sys_batch_no = merchantFundSettleDao.queryMaxSysBatchNo(end_date);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return sys_batch_no;
	}

	@Override
	public int queryNoConfirmSettleDataCount(Map<String, Integer> map) {
		int result = 0;
		try{
			result = merchantFundSettleDao.queryNoConfirmSettleDataCount(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public boolean createSettleFile(Map<String, Integer> map) {
		boolean flag = false;
		try{
			int startDate = map.get("startTime");
			int endDate = map.get("endTime");
			int settleAccountType = map.get("settlementAccountType");
			CreateSettleFileDocument document = CreateSettleFileDocument.Factory.newInstance();
			CreateSettleFileType type = document.addNewCreateSettleFile();
			
			type.setStartDate(startDate);
			type.setEndDate(endDate);
			type.setSettleAccountType(settleAccountType);
			
			log.info("向后台发送创建结算单请求");
			String xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			log.info("创建结算单后台回执信息："+xmlString);
			
			flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		
		return flag;
	}

	@Override
	public Long getSysBatchNo() {
		Long sysBatchNo = 0l;
		try{
			sysBatchNo = merchantFundSettleDao.getSysBatchNo();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return sysBatchNo;
	}

	@Override
	public boolean checkMerchantFundSettleExsit(String mer_code) {
		boolean flag = false;
		try{
			int count = merchantFundSettleDao.queryMerchantFundSettleCount(mer_code);
			if(count > 0){
				flag = true;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public List<SettleMerchantMatch> queryDyMerCodeBySettleMerCode(String settle_mer_code) {
		List<SettleMerchantMatch> list = null;
		try {
			list = merchantFundSettleDao.queryDyMerCodeBySettleMerCode(settle_mer_code);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public Page<MerchantFundSettle> queryPageDfResult(Page<MerchantFundSettle> page, Map<String, Object> map) {
		Page<MerchantFundSettle> pageList = null;
		try {
			pageList = merchantFundSettleDao.queryPageDfResult(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("merchantFundSettleDao.queryPageDfResult(page, map) 查询结果为NULL");
			}
		} catch (Exception e) {
			log.error("代付结果查询出现异常：" + e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<MerchantFundSettle> queryDfResultDataLst(Map<String, Object> map) {
		List<MerchantFundSettle> list = null;
		try {
			list = merchantFundSettleDao.queryDfResultDataLst(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public Page<MerchantFundSettle> queryPageNeedZBDataLst(Page<MerchantFundSettle> page, Map<String, Object> map) {
		Page<MerchantFundSettle> pageList = null;
		try {
			pageList = merchantFundSettleDao.queryPageNeedZBDataLst(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("merchantFundSettleDao.queryPageNeedZBDataLst(page, map) 查询结果为NULL");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public Page<MerchantFundSettle> queryPageNeedQRDataLst(Page<MerchantFundSettle> page, Map<String, Object> map) {
		Page<MerchantFundSettle> pageList = null;
		try {
			pageList = merchantFundSettleDao.queryPageNeedQRDataLst(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("merchantFundSettleDao.queryPageNeedQRDataLst(page, map) 查询结果为NULL");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<MerchantFundSettle> queryDataListByIds(Map<String, Object> map) {
		List<MerchantFundSettle> list = null;
		try {
			list = merchantFundSettleDao.queryDataListByIds(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public Page<MerchantFundSettle> queryPageDyDfResult(Page<MerchantFundSettle> page, Map<String, Object> map) {
		Page<MerchantFundSettle> pageList = null;
		try {
			pageList = merchantFundSettleDao.queryPageDyDfResult(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("merchantFundSettleDao.queryPageDyDfResult(page, map) 查询结果为NULL");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pageList;
	}
	
	@Override
	public boolean updateSettleDataSynResult(int id,int syn_result,String error_msg) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Calendar calendar = Calendar.getInstance();
		map.put("syn_result", syn_result);
		map.put("syn_date", DateUtil.formatDate(calendar.getTime(), "yyyyMMdd"));
		map.put("error_msg", error_msg);
		map.put("id", id);
		int result = updateMerchantFundSettleSynResultAndDate(map);
		return result>=0?true:false;
	}
	
	@Override
	public void updateSettleDataStatus(MerchantFundSettle merchantFundSettle) throws Exception{
		
		try {
			//修改结算单为结算确认状态
			updateMerchantFundSettleDataQrStatus(merchantFundSettle.getId());
		} catch (Exception e) {
			log.error("修改结算单为结算确认状态操作出现异常"+e);
			throw e;
		}
		
		try {
			//修改商户余额
			MerchantsBalance merchantsBalance = merchantsBalanceDao.queryMerBalanceByMerCode(merchantFundSettle.getMer_code());
			if(merchantsBalance != null){
				log.info("商户号为"+merchantFundSettle.getMer_code()+"进行调整商户余额操作，调整前余额为"+merchantsBalance.getMer_balance());
				String mer_balance = PoundageCalculate.sub(merchantsBalance.getMer_balance(),merchantFundSettle.getSettle_amount());
				log.info("商户号为"+merchantFundSettle.getMer_code()+"进行调整商户余额操作，调整后余额为"+mer_balance);
				merchantsBalance.setMer_balance(mer_balance);
				merchantsBalanceDao.updateMerchantsBalance(merchantsBalance);
			}else{
				log.info("商户号为"+merchantFundSettle.getMer_code()+"在商户余额表中不存在查到余额记录");
			}
		} catch (Exception e) {
			log.error("修改商户余额操作出现异常"+e);
			throw e;
		}
		
		try {
			//向商户资金流水表中插入数据
			Merchants merchants = merchantsDao.queryMerchantBasicInfoByMerCode(merchantFundSettle.getMer_code());
			if(merchants != null){
				MerFundStance merFundStance = new MerFundStance();
				merFundStance.setId(UUID.randomUUID().toString().replaceAll("-", ""));//主键id
				merFundStance.setInst_id(0);//扣款渠道ID
				merFundStance.setInst_type(0);//渠道类型
				merFundStance.setBank_id(0);
				merFundStance.setMer_code(merchantFundSettle.getMer_code());//商户号
				merFundStance.setMer_name(merchants.getMer_abbreviation());//商户简称
				merFundStance.setTrade_amount(0-(Double.valueOf(merchantFundSettle.getTrade_amount()) + Double.valueOf(merchantFundSettle.getRefund_amount())));//交易金额
				merFundStance.setTrade_stance(merchantFundSettle.getMer_batch_no());//交易流水--商户批次号
				Calendar calendar_ = Calendar.getInstance();//系统当前时间
				String nowTime = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd HH:mm:ss");
				merFundStance.setTrade_time(nowTime);//交易时间
				merFundStance.setDeduct_stlm_date(nowTime.substring(0,10));//清算日期
				String stance_time = DateUtil.formatDate(calendar_.getTime(), "yyyy-MM-dd HH:mm:ss:SSS");
				merFundStance.setStance_time(stance_time);
				merFundStance.setMer_state(merchants.getMer_state());//商户状态
				merFundStance.setMer_category(merchants.getMer_category());//商户类别
				merFundStance.setDerc_status(merchantFundSettle.getSettle_way()==1?DataStatus.SETTLE_TO_BANK:DataStatus.SETTLE_TO_DY);//简短描述
				merFundStance.setMer_fee(0-(Double.valueOf(merchantFundSettle.getMer_fee()) + Double.valueOf(merchantFundSettle.getRefund_mer_fee())));//商户手续费
				merFundStance.setChange_amount(0-Double.valueOf(merchantFundSettle.getSettle_amount()));//变动金额
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("mer_code",merchantFundSettle.getMer_code());
				
				String accountAmount = merFundStanceDao.queryLastAccountAmount(map);
				if (StringUtils.isNotBlank(accountAmount)) {
					merFundStance.setAccount_amount(Double.valueOf(accountAmount) - Double.valueOf(merchantFundSettle.getSettle_amount()));//本期账户余额
				} else {
					merFundStance.setAccount_amount(0d);
				}
				
				int result_ = merFundStanceDao.insertMerFundStance(merFundStance);
				if(result_ == 0){
					log.debug("向商户资金流水表中插入相关数据失败,商户号为"+merchantFundSettle.getMer_code()+",商户简称为："+merchants.getMer_abbreviation()+",商户批次号为："+merchantFundSettle.getMer_batch_no());
				}
			}
		} catch (Exception e) {
			log.error("向商户资金流水表中插入数据操作出现异常"+e);
			throw e;
		}
	}

	@Override
	public boolean updateMerchantFundSettleDataSynchronized(final MerchantFundSettle merchantFundSettle,String zhxt_settle_data_synchronized_url,int connectTimeOut,int readTimeOut,String zhxtMd5Key) {
		boolean synchronizedFlag = false;
		if(merchantFundSettle != null){
			log.info("商户号"+merchantFundSettle.getMer_code()+"起始日期"+merchantFundSettle.getStart_date()+"截止日期"+merchantFundSettle.getEnd_date()+"的结算单数据调用接口同步至账户系统");
			try{
				
				//修改结算单数据同步数据状态为同步中
				updateSettleDataSynResult(merchantFundSettle.getId(),3,"");
				
				Map<String,Object> parameterMap = new HashMap<String,Object>();
				
				parameterMap.put("merCode", merchantFundSettle.getMer_code());
				parameterMap.put("startDate", merchantFundSettle.getStart_date());
				parameterMap.put("endDate", merchantFundSettle.getEnd_date());
				
				StringBuffer dataBuffer = new StringBuffer("");
				List<ChannelDzCollect> dataList = channelDzCollectDao.queryChannelDzCollectDataLst(parameterMap);//查询 结算单交易明细数据
				if(dataList != null){
					int dataCount = dataList.size();
					if(dataCount > 0){
						for (ChannelDzCollect channelDzCollect : dataList) {
							dataCount --;
							dataBuffer.append(channelDzCollect.getIn_user_id());//入账用户ID
							dataBuffer.append(",");
							dataBuffer.append(channelDzCollect.getTrade_type());//交易类型
							dataBuffer.append(",");
							dataBuffer.append(channelDzCollect.getReq_sys_stance());//RYF流水号
							dataBuffer.append(",");
							dataBuffer.append(channelDzCollect.getOid());//商户订单号
							dataBuffer.append(",");
							dataBuffer.append(channelDzCollect.getTrade_time());//交易日期
							dataBuffer.append(",");
							dataBuffer.append(channelDzCollect.getTrade_amount());//交易金额
							dataBuffer.append(",");
							dataBuffer.append(channelDzCollect.getMer_fee());//手续费
							if(dataCount != 0){
								dataBuffer.append("|");
							}
						}
					}
				}
				
				String whetherFz = "N";//是否资金分账标识,默认为否
				
				if(merchantFundSettle.getWhtherFz() == 1){//商户需要资金分账，查询结算单交易明细
					whetherFz = "Y";//修改标识值为 需要资金分账
				}
				
				Timestamp timestamp = new Timestamp();
				
				StringBuffer chkValue = new StringBuffer(""); //数字签名
				chkValue.append("10");//消息版本号
				chkValue.append("ZH0036");//交易码
				chkValue.append("RYF");//系统标示
				chkValue.append(merchantFundSettle.getMer_code());//电银商户号
				chkValue.append(merchantFundSettle.getMer_batch_no());//商户批次号
				chkValue.append(merchantFundSettle.getStart_date());//起始日期
				chkValue.append(merchantFundSettle.getEnd_date());//截止日期
				chkValue.append(merchantFundSettle.getRec_amount_add_count()+merchantFundSettle.getRec_amount_sub_count()+merchantFundSettle.getRefund_count()+merchantFundSettle.getTrade_count());
				chkValue.append(merchantFundSettle.getSettle_amount());//结算金额
				chkValue.append(whetherFz);//是否资金分账标识
				chkValue.append(dataBuffer.toString());//详细信息
				chkValue.append(timestamp.getDateTime());//时间戳
				chkValue.append(zhxtMd5Key);//账户系统自定义字符串
				
				log.info("商户号"+merchantFundSettle.getMer_code()+"起始日期"+merchantFundSettle.getStart_date()+"截止日期"+merchantFundSettle.getEnd_date()+"的结算单数据调用接口同步至账户系统，参数如下……");
				
				StringBuffer requestBw = new StringBuffer("");//请求报文
				requestBw.append("version=10");//消息版本号
				log.info("消息版本号------10");
				requestBw.append("&tranCode=ZH0036");//交易码
				log.info("交易码-------ZH0036");
				requestBw.append("&sys=RYF");//系统标示
				log.info("系统标示-------RYF");
				requestBw.append("&merId=");//电银商户号
				requestBw.append(merchantFundSettle.getMer_code());//电银商户号
				log.info("电银商户号------"+merchantFundSettle.getMer_code());
				requestBw.append("&batchNo=");//商户批次号
				requestBw.append(merchantFundSettle.getMer_batch_no());//商户批次号
				log.info("商户批次号------"+merchantFundSettle.getMer_batch_no());
				requestBw.append("&beginDate=");//起始日期
				requestBw.append(merchantFundSettle.getStart_date());//起始日期
				log.info("起始日期-------"+merchantFundSettle.getStart_date());
				requestBw.append("&endDate=");//截止日期
				requestBw.append(merchantFundSettle.getEnd_date());//截止日期
				log.info("截止日期-------"+merchantFundSettle.getEnd_date());
				requestBw.append("&totalItems=");//交易笔数
				requestBw.append(merchantFundSettle.getRec_amount_add_count()+merchantFundSettle.getRec_amount_sub_count()+merchantFundSettle.getRefund_count()+merchantFundSettle.getTrade_count());//交易笔数
				log.info("交易笔数-------"+(merchantFundSettle.getRec_amount_add_count()+merchantFundSettle.getRec_amount_sub_count()+merchantFundSettle.getRefund_count()+merchantFundSettle.getTrade_count()));
				requestBw.append("&totalAmt=");//结算金额
				requestBw.append(merchantFundSettle.getSettle_amount());//结算金额
				log.info("结算金额-------"+merchantFundSettle.getSettle_amount());
				requestBw.append("&checking=");//是否资金分账标识
				requestBw.append(whetherFz);//是否资金分账标识
				log.info("是否资金分账标识----"+whetherFz);
				requestBw.append("&items=");//详细信息
				requestBw.append(dataBuffer.toString());//详细信息
				requestBw.append("&timestamp=");//时间戳
				requestBw.append(timestamp.getDateTime());//时间戳
				log.info("时间戳--------"+timestamp.getDateTime());
				requestBw.append("&chkValue=");//数字签名   MD5加密
				requestBw.append(EncodeUtils.md5Encode(chkValue.toString()));//数字签名   MD5加密
				log.info("数字签名-----"+EncodeUtils.md5Encode(chkValue.toString()));
				
				String result = "";//账户系统回执信息
				try{
					result = HttpUtil.sendPostRequest(zhxt_settle_data_synchronized_url, requestBw.toString(), "utf-8",connectTimeOut,readTimeOut);
				}catch(ConnectException e){
					//修改结算单数据同步状态为同步失败
					updateSettleDataSynResult(merchantFundSettle.getId(),2,"服务器请求超时");
					return false;
				}catch(ConnectTimeoutException e){
					//修改结算单数据同步状态为同步失败
					updateSettleDataSynResult(merchantFundSettle.getId(),2,"服务器请求超时");
					return false;
				}catch(SocketTimeoutException e){
					//修改结算单数据同步状态为同步失败
					updateSettleDataSynResult(merchantFundSettle.getId(),2,"服务器响应超时");
					return false;
				}catch(Exception e){
					//修改结算单数据同步状态为同步失败
					updateSettleDataSynResult(merchantFundSettle.getId(),2,"结算确认请求账户系统失败");
					log.error("结算确认请求账户系统失败,抛出异常:"+e);
					return false;
				}
				
				log.info("商户号"+merchantFundSettle.getMer_code()+"起始日期"+merchantFundSettle.getStart_date()+"截止日期"+merchantFundSettle.getEnd_date()+",账户系统处理后返回结果："+result);
				if(StringUtils.isNotBlank(result)){
					JSONObject jsonObject = JSONObject.fromObject(URLDecoder.decode(result.toString(), "utf-8"));//转换为json对象
					
					if("000".equals(jsonObject.getString("resCode"))){//账户系统返回码为000，说明该结算单数据处理成功
						synchronizedFlag = true;
						sucessNum++;
						//修改结算单数据同步状态为同步成功
						updateSettleDataSynResult(merchantFundSettle.getId(),1,"");
					}else{
						//修改结算单数据同步状态为同步失败
						updateSettleDataSynResult(merchantFundSettle.getId(),2,jsonObject.getString("resMsg"));
					}
				}
			}catch(Exception e){
				throw new RuntimeException();//捕获到异常，抛出unchecked exception ,事务回滚
			}
		}
		return synchronizedFlag;
	}

	@Override
	public List<MerchantFundSettle> queryMerSettleDataOfNoQR(String ids, int settle_state, int settle_way) {
		List<MerchantFundSettle> list = null;
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			map.put("id", ids);
			map.put("settle_state", settle_state);
			map.put("settle_way", settle_way);
			list = merchantFundSettleDao.queryMerSettleDataOfNoQR(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int updateMerchantFundSettleSynResultAndDate(Map<String, Object> map) {
		int result = 0;
		try{
			result = merchantFundSettleDao.updateMerchantFundSettleSynResultAndDate(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
	
	@Override
	public int getDataSynSucessNum(){
		return sucessNum;
	}

	@Override
	public int updateMerchantFundSettleDataQrStatus(int id) {
		int result = 0;
		try{
			result = merchantFundSettleDao.updateMerchantFundSettleDataQrStatus(id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
	
	@Override
	public int updateDataStatusToZB(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = merchantFundSettleDao.updateDataStatusToZB(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public boolean updateMerBilingAmtDf(MerchantFundSettle merchantFundSettle) throws Exception {
		boolean flag = false;
		int df_result = 0;//默认待划款
		try {
			Map<String, String> map = new HashMap<String, String>();
			DecimalFormat df = new DecimalFormat("##0.00");
			String errorMsg = "";
			//获取MD5的文本
			String md5Key = Common.getMd5Key();
			if (StringUtils.isNotBlank(md5Key)) {
				String sysBatchNo = "";
				String merBatchNo = merchantFundSettle.getMer_batch_no();
				if (StringUtils.isNotBlank(merBatchNo) && merBatchNo.length() > 20) {
					sysBatchNo = merBatchNo.substring(merBatchNo.length()-20, merBatchNo.length());
				} else if (StringUtils.isNotBlank(merBatchNo) && merBatchNo.length() <= 20) {
					sysBatchNo = StringUtils.leftPad(merBatchNo, 20, "0");
				} else {
					sysBatchNo = StringUtils.leftPad(merchantFundSettle.getSys_batch_no(), 20, "0");
				}
				map.put("version", "10");//消息版本
				map.put("accountId", merchantFundSettle.getMer_code());//商户号
				map.put("orderId",sysBatchNo);//系统批次号
				String settleAmount = df.format(Double.parseDouble(merchantFundSettle.getSettle_amount()));
				map.put("transAmt",settleAmount);//应结算金额
				map.put("transType", "C1");//接口类型
				
				Merchants merchants = merchantsDao.queryMerchantBasicInfoByMerCode(merchantFundSettle.getMer_code());
				if (merchants != null) {
					int merType = merchants.getMer_type();//商户类型 1：企业 2：个人
					if (merType == 1) {
						map.put("dfType", "B");//对公代付
					} else {
						map.put("dfType", "A");//对私代付
					}
				}
				map.put("cardFlag", "0");//卡/存折标识
				map.put("rcvAcno", merchantFundSettle.getOpen_account_code());//卡号
				map.put("rcvAcname", merchantFundSettle.getOpen_acount_name());//持卡人姓名/公司名称
				map.put("bankNo", merchantFundSettle.getBil_bank());//开户联行号
				map.put("purpose", "df");//用途
				map.put("merPriv", Base64.encode(String.valueOf(merchantFundSettle.getId()).getBytes()));//商家私有域
				map.put("bgRetUrl", Common.getProperties("ryf_res_url"));//交易结果接收地址
				map.put("dataSource", "3");//数据来源
				String signStr = "10" + sysBatchNo + settleAmount + "C1" + merchantFundSettle.getOpen_account_code() + md5Key;
				log.info("参与签名数据：" + signStr);
				String chkValue = Common.MD5(signStr).toUpperCase();
				map.put("chkValue",chkValue);//商家签名
				log.info("签名值：" + chkValue);
				
				String xmlResult = HttpUtil.sendPostRequest(Common.getProperties("ryf_req_url"), map, "utf-8");
				log.info("订单受理接口实时返回的xml内容是：" + xmlResult);
				
			    Document doc = DocumentHelper.parseText(xmlResult);
			    String value = doc.getRootElement().element("status").element("value").getText();
			    errorMsg = doc.getRootElement().element("status").element("msg").getText();
			    StringBuffer transResult = new StringBuffer();
		       //判断订单录入是否成功
				if (value.equals("RYF_DF_000")) {
					String bk_ordId = doc.getRootElement().element("transResult").element("orderId").getText();
					String bk_transAmt = doc.getRootElement().element("transResult").element("transAmt").getText();
					String bk_transStatus = doc.getRootElement().element("transResult").element("transStatus").getText();
					String bk_tseq = doc.getRootElement().element("transResult").element("tseq").getText();
					String bk_chkValue = doc.getRootElement().element("transResult").element("chkValue").getText();
					String merPriv = doc.getRootElement().element("transResult").element("merPriv").getText();
					String bk_signStr = bk_ordId + bk_transAmt + bk_tseq + bk_transStatus + md5Key;
					String bk_sign = Common.MD5(bk_signStr).toUpperCase();
					if(bk_sign.equals(bk_chkValue)) {
						transResult.append("验签成功，交易状态：");
						if("W".equals(bk_transStatus)) {
							transResult.append("划款处理中");
							df_result = 3;
						}else if("S".equals(bk_transStatus)) {
							transResult.append("划款成功");
							df_result = 1;
						}else if("F".equals(bk_transStatus)) {
							transResult.append("划款失败");
							df_result = 2;
						}else {
							transResult.append("划款未知");
							df_result = 5;
						}
					} else {
						transResult.append("商户ID");
						transResult.append(Base64.getFromBASE64(merPriv));
						transResult.append(" : 验签失败");
						df_result = 4;
					}
				}else{
					transResult.append("商户ID：");
					transResult.append(merchantFundSettle.getId());
					transResult.append(" 代付失败-原因 ：");
					transResult.append(errorMsg);
					df_result = 6;
				}
				log.info("订单受理接口签名验证及交易状态是：" + transResult.toString());
				
				flag = updateSettleDataSynResult(merchantFundSettle.getId(),df_result, errorMsg);
				if (flag) {
					log.info("根据融易付订单受理接口返回的信息修改商户划款状态及错误信息成功");
				} else {
					log.info("根据融易付订单受理接口返回的信息修改商户划款状态及错误信息失败");
				}
			} else {
				log.info("获取MD5私钥为空");
			}
		} catch (Exception e) {
			log.error("实时代付出现异常：" + e.getMessage());
		}
		return flag;
	}

	@Override
	public void setDataSynSucessNum(int num){
		sucessNum = num;
	}
	
}
