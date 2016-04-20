package com.chinaebi.serviceImpl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.com.chinaebi.dz.webservice.MerBasicUpdateRamDocument;
import cn.com.chinaebi.dz.webservice.MerBasicUpdateRamType;
import cn.com.chinaebi.dz.webservice.MerBillingUpdateRamDocument;
import cn.com.chinaebi.dz.webservice.MerBillingUpdateRamType;

import com.chinaebi.dao.MerFundStanceDao;
import com.chinaebi.dao.MerchantFundSettleDao;
import com.chinaebi.dao.MerchantSettleStatisticsDao;
import com.chinaebi.dao.MerchantsBalanceDao;
import com.chinaebi.dao.MerchantsDao;
import com.chinaebi.entity.MerFundStance;
import com.chinaebi.entity.MerchantSettleStatistics;
import com.chinaebi.entity.Merchants;
import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.HttpUtil;
import com.chinaebi.utils.PoundageCalculate;
import com.chinaebi.utils.RequestUrlConf;
import com.chinaebi.utils.ReturnXmlHandler;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "merchantsService")
public class MerchantsServiceImpl  implements MerchantsService {
	
	private Log log = LogFactory.getLog(MerchantsServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "merchantsDao")
	private MerchantsDao merchantsDao;
	
	@Autowired
	@Qualifier(value = "merFundStanceDao")
	private MerFundStanceDao merFundStanceDao;
	
	@Autowired
	@Qualifier(value = "merchantSettleStatisticsDao")
	private MerchantSettleStatisticsDao merchantSettleStatisticsDao;
	
	@Autowired
	@Qualifier(value = "merchantsBalanceDao")
	private MerchantsBalanceDao merchantsBalanceDao;
	
	@Autowired
	@Qualifier(value = "merchantFundSettleDao")
	private MerchantFundSettleDao merchantFundSettleDao;

	@Override
	public Page<Merchants> queryPageMerchantsList(Page<Merchants> page, Map<String, Object> map) {
		Page<Merchants> pageList = null;
		try {
			pageList = merchantsDao.queryPageMerchantsList(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("merchantsDao.queryPageMerchantsList(page, map) 查询结果为NULL");
			} else {
				return pageList;
			}
		} catch (Exception e) {
			log.error("查询商户信息出错：" + e.getMessage());
		}
		return pageList;
	}

	/**
	 * 通过商户号查询商户简称
	 * @param mer_code
	 * @return
	 */
	public boolean queryMerAbbreviationByMerCode(String mer_code,String merAbbreviation){
		boolean flag = false;
		try {
			String mer_abbreviation = merchantsDao.queryMerAbbreviationByMerCode(mer_code);
			if(merAbbreviation.equals(mer_abbreviation)){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public Page<Merchants> queryPageSettleMerchantInfo(Page<Merchants> page,
			Map<String, Object> map) {
		Page<Merchants> pageList = null;
		try {
			pageList = merchantsDao.queryPageSettleMerchantInfo(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("merchantsDao.queryPageSettleMerchantInfo(page, map) 查询结果为NULL");
			} else {
				return pageList;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<Merchants> querySettleMerchantInfoCount(Map<String, Object> map) {
		List<Merchants> list = null;
		try{
			list = merchantsDao.querySettleMerchantInfoCount(map);
			if(list == null){
				throw new SelectException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public List<Merchants> querySettleMerchantInfoList(Map<String, Object> map) {
		List<Merchants> list = null;
		try{
			list = merchantsDao.querySettleMerchantInfoList(map);
			if(list == null){
				throw new SelectException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public Merchants queryMerchantBasicInfoByMerCode(String mer_code) {
		Merchants merchants = null;
		try{
			merchants = merchantsDao.queryMerchantBasicInfoByMerCode(mer_code);
			if(merchants == null){
				throw new SelectException("merchantsDao.queryMerchantBasicInfoByMerCode(mer_code)  查询结果为空   参数为");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return merchants;
	}

	@Override
	public List<Merchants> vagueQueryMerchantInfoByMerCode(String mer_code) {
		List<Merchants> list = null;
		try{
			list = merchantsDao.vagueQueryMerchantInfoByMerCode(mer_code);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public List<Merchants> queryMerchantsBilBankAccountInfo() {
		List<Merchants> list = null;
		try{
			list = merchantsDao.queryMerchantsBilBankAccountInfo();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int updateMerBillingBankAccount(Map<String, Object> map) {
		int result = 0;
		try{
			result = merchantsDao.updateMerBillingBankAccount(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public Merchants queryAllMerInfoByMerCode(String merCode) {
		Merchants merchants = null;
		try {
			merchants = merchantsDao.queryAllMerInfoByMerCode(merCode);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return merchants;
	}
	
	@Override
	public int updateMerBasicByMerCode(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = merchantsDao.updateMerBasicByMerCode(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}
	
	@Override
	public int updateMerBillingByMerCode(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = merchantsDao.updateMerBillingByMerCode(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public List<Merchants> queryAllMerBasicInfo() {
		List<Merchants> list = null;
		try {
			list = merchantsDao.queryAllMerBasicInfo();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public List<Merchants> queryAllMerBillingInfo() {
		List<Merchants> list = null;
		try {
			list = merchantsDao.queryAllMerBillingInfo();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public boolean updateRamMerBasicInfo(String merCode) {
		boolean flag = false;
		try {
			MerBasicUpdateRamDocument document = MerBasicUpdateRamDocument.Factory.newInstance();
			MerBasicUpdateRamType type = document.addNewMerBasicUpdateRam();
			type.setTrace("011");
			type.setMerCode(merCode);
			log.info("向后台发送更新内存中商户基本信息请求");
			String xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			log.info("后台更新内存中商户基本信息请求信息："+xmlString);
			flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				log.info("更新内存中商户基本信息成功");
				flag = true;
			}else{
				log.info("更新内存中基本信息失败");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public boolean updateRamMerBillingInfo(String merCode) {
		boolean flag = false;
		try {
			MerBillingUpdateRamDocument document = MerBillingUpdateRamDocument.Factory.newInstance();
			MerBillingUpdateRamType type = document.addNewMerBillingUpdateRam();
			type.setTrace("011");
			type.setMerCode(merCode);
			log.info("向后台发送更新内存中商户结算信息请求");
			String xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			log.info("后台更新内存中商户结算信息请求信息："+xmlString);
			flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				log.info("更新内存中商户结算信息成功");
				flag = true;
			}else{
				log.info("更新内存中结算信息失败");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public List<Merchants> queryMerNameListByMerName(Map<String, Object> map) {
		List<Merchants> list = null;
		try {
			list = merchantsDao.queryMerNameListByMerName(map);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public synchronized int updateMerchantFundStanceAndBalance(String mer_code,
			int bank_id, int inst_id, int bank_type, double trade_amount,
			double mer_fee, int trade_date, String trade_stance,int desc_info,int whetherTk) throws Exception{
		
		if(StringUtils.isNotBlank(mer_code)){
			Merchants merchants = queryMerchantBasicInfoByMerCode(mer_code);
			
			if(merchants != null){
				
				double lastMerBalance = 0.00d;
				
				Calendar calendar = Calendar.getInstance();//系统当前时间
				String nowTime = DateUtil.formatDate(calendar.getTime(), DataStatus.date_format_7);
				String stance_time = DateUtil.formatDate(calendar.getTime(), DataStatus.date_format_8);
				
				try {
					MerchantsBalance balance = merchantsBalanceDao.queryMerBalanceByMerCode(mer_code);
					if(balance != null){
						
						lastMerBalance = StringUtils.isBlank(balance.getMer_balance()) ? 0.00d : Double.valueOf(balance.getMer_balance());
						
						log.info("商户号为"+mer_code+"进行调整商户余额操作，调整前余额为"+balance.getMer_balance());

						if(whetherTk == 1){
							if(Double.valueOf(balance.getMer_balance()) >= trade_amount){
								String mer_balance = PoundageCalculate.sub(balance.getMer_balance(),trade_amount).toString();
								log.info("商户号为"+mer_code+"进行调整商户余额操作，调整后余额为"+mer_balance);
								balance.setMer_balance(mer_balance);
								int effectNum = merchantsBalanceDao.updateMerchantsBalance(balance);
								if(effectNum <= 0){
									return 3;
								}
							}else{
								return 2;
							}
						}else{
							String mer_balance = PoundageCalculate.add(balance.getMer_balance(),trade_amount).toString();
							log.info("商户号为"+mer_code+"进行调整商户余额操作，调整后余额为"+mer_balance);
							balance.setMer_balance(mer_balance);
							int effectNum = merchantsBalanceDao.updateMerchantsBalance(balance);
							if(effectNum <= 0){
								return 3;
							}
						}
						
					}else{
						return 2;
					}
				} catch (Exception e) {
					log.error("修改商户余额操作出现异常"+e);
					throw e;
				}
				
				try {
					MerFundStance merFundStance = new MerFundStance();
					merFundStance.setId(UUID.randomUUID().toString().replaceAll("-", ""));//主键id
					merFundStance.setInst_id(inst_id);//扣款渠道ID
					merFundStance.setInst_type(bank_type);//渠道类型
					
					merFundStance.setBank_id(bank_id);//网关号
					merFundStance.setMer_code(mer_code);//商户号
					merFundStance.setMer_name(merchants.getMer_abbreviation());//商户简称
					merFundStance.setTrade_amount(whetherTk == 1 ? 0-trade_amount : trade_amount);//交易金额
					merFundStance.setTrade_stance(trade_stance);//交易流水--商户批次号
					merFundStance.setTrade_time(nowTime);//交易时间
					merFundStance.setDeduct_stlm_date(nowTime.substring(0,10));//清算日期
					merFundStance.setStance_time(stance_time);
					merFundStance.setMer_state(merchants.getMer_state());//商户状态
					merFundStance.setMer_category(merchants.getMer_category());//商户类别
					merFundStance.setDerc_status(desc_info);//简短描述
					merFundStance.setChange_amount(whetherTk == 1 ? 0-trade_amount : trade_amount);//变动金额
					
					if(whetherTk == 1){
						merFundStance.setMer_fee(merchants.getRefund_fee() == 0 ? 0.00d : mer_fee);//商户手续费
						merFundStance.setAccount_amount(lastMerBalance - Double.valueOf(trade_amount));//本期账户余额
					}else{
						merFundStance.setMer_fee(mer_fee);//商户手续费
						merFundStance.setAccount_amount(lastMerBalance + Double.valueOf(trade_amount));//本期账户余额
					}
					
					int result_ = merFundStanceDao.insertMerFundStance(merFundStance);
					if(result_ == 0){
						log.debug("向商户资金流水表中插入相关数据失败,商户号为"+mer_code+",商户简称为："+merchants.getMer_abbreviation()+",流水为："+trade_stance);
						return 4;
					}
				} catch (Exception e) {
					log.error("向商户资金流水表中插入数据操作出现异常"+e);
					throw e;
				}
				
				return 0;
			}else{
				return 1;
			}
		}else{
			log.debug("商户号参数mer_code为"+mer_code+",不能为空,请核实");
		}
		return 5;
	}

	@Override
	public synchronized int updateMerchantFundStanceAndBalanceAndStatistics(String mer_code,int bank_id,
			int inst_id,int bank_type,double trade_amount,double mer_fee,double zf_fee,double system_fee,
			int trade_date,String trade_stance,int desc_info,int whetherTk,int data_status) throws Exception{
		
		if(StringUtils.isNotBlank(mer_code)){
			Merchants merchants = queryMerchantBasicInfoByMerCode(mer_code);//查询当前商户信息
			
			if(merchants != null){
				try{
					Calendar calendar = Calendar.getInstance();//系统当前时间
					String nowTime = DateUtil.formatDate(calendar.getTime(), DataStatus.date_format_7);
					String stance_time = DateUtil.formatDate(calendar.getTime(), DataStatus.date_format_8);
					
					double lastMerBalance = 0.00d;
					
					try {
						MerchantsBalance balance = merchantsBalanceDao.queryMerBalanceByMerCode(mer_code);//查询当前商户余额信息
						if(balance != null){
							
							lastMerBalance = StringUtils.isBlank(balance.getMer_balance()) ? 0.00d : Double.valueOf(balance.getMer_balance());
							
							log.info("商户号为"+mer_code+"进行调整商户余额操作，调整前余额为"+balance.getMer_balance());
							
							if(whetherTk == 1){//退款类调整
								if(data_status == 1){//退款管理，判断余额是否大于调整金额
									if(Double.valueOf(balance.getMer_balance()) >= trade_amount){
										String mer_balance = PoundageCalculate.sub(balance.getMer_balance(),trade_amount).toString();
										log.info("商户号为"+mer_code+"进行调整商户余额操作，调整后余额为"+mer_balance);
										balance.setMer_balance(mer_balance);
										int effectNum = merchantsBalanceDao.updateMerchantsBalance(balance);
										if(effectNum <= 0){
											return 3;
										}
									}else{
										return 2;
									}
								}else{
									String mer_balance = PoundageCalculate.sub(balance.getMer_balance(),trade_amount).toString();
									log.info("商户号为"+mer_code+"进行调整商户余额操作，调整后余额为"+mer_balance);
									balance.setMer_balance(mer_balance);
									int effectNum = merchantsBalanceDao.updateMerchantsBalance(balance);
									if(effectNum <= 0){
										return 3;
									}
								}
								
							}else{
								String mer_balance = PoundageCalculate.add(balance.getMer_balance(),trade_amount).toString();
								log.info("商户号为"+mer_code+"进行调整商户余额操作，调整后余额为"+mer_balance);
								balance.setMer_balance(mer_balance);
								int effectNum = merchantsBalanceDao.updateMerchantsBalance(balance);
								if(effectNum <= 0){
									return 3;
								}
							}
							
						}else{//新增一条记录
							if(data_status == 1){//退款处理时,不做新增处理，直接返回商户余额不足信息
								return 2;
							}else{
								balance = new MerchantsBalance();
								balance.setMer_code(mer_code);
								balance.setMer_category(merchants.getMer_category());
								balance.setMer_balance(PoundageCalculate.add(lastMerBalance,trade_amount).toString());
								balance.setMer_state(merchants.getMer_state()+"");
								int effectNum = merchantsBalanceDao.addMerchantsBalance(balance);
								if(effectNum <= 0){
									return 3;
								}
							}
						}
					} catch (Exception e) {
						log.error("修改商户余额操作出现异常"+e);
						throw e;
					}
					
					try {
						MerFundStance merFundStance = new MerFundStance();
						merFundStance.setId(UUID.randomUUID().toString().replaceAll("-", ""));//主键id
						merFundStance.setInst_id(inst_id);//扣款渠道ID
						merFundStance.setInst_type(bank_type);//渠道类型
						merFundStance.setBank_id(bank_id);//网关号
						merFundStance.setMer_code(mer_code);//商户号
						merFundStance.setMer_name(merchants.getMer_abbreviation());//商户简称
						merFundStance.setTrade_amount(whetherTk == 1 ? 0-trade_amount : trade_amount);//交易金额
						merFundStance.setTrade_stance(trade_stance);//交易流水--商户批次号
						merFundStance.setTrade_time(nowTime);//交易时间
						merFundStance.setDeduct_stlm_date(nowTime.substring(0,10));//清算日期
						merFundStance.setStance_time(stance_time);
						merFundStance.setMer_state(merchants.getMer_state());//商户状态
						merFundStance.setMer_category(merchants.getMer_category());//商户类别
						merFundStance.setDerc_status(desc_info);//简短描述
						merFundStance.setChange_amount(whetherTk == 1 ? 0-trade_amount : trade_amount);//变动金额
						
						if(whetherTk == 1){
							merFundStance.setAccount_amount(lastMerBalance - trade_amount);//本期账户余额
							merFundStance.setMer_fee(merchants.getRefund_fee() == 0 ? 0.00d : mer_fee);//商户手续费
						}else{
							merFundStance.setAccount_amount(lastMerBalance + trade_amount);//本期账户余额
							merFundStance.setMer_fee(mer_fee);//商户手续费
						}
						
						int result_ = merFundStanceDao.insertMerFundStance(merFundStance);
						if(result_ <= 0){
							log.debug("向商户资金流水表中插入相关数据失败,商户号为"+mer_code+",商户简称为："+merchants.getMer_abbreviation()+",流水为："+trade_stance);
							return 4;
						}
					} catch (Exception e) {
						log.error("向商户资金流水表中插入数据操作出现异常"+e);
						throw e;
					}
					
					try {
						//差错调整时，向T+1数据统计表中插入数据
						Map<String,Object> parameterMap = new HashMap<String,Object>();
						parameterMap.put("inst_id", inst_id);
						parameterMap.put("inst_type", bank_type);
						parameterMap.put("mer_code", mer_code);
						parameterMap.put("data_status", data_status);
						parameterMap.put("deduct_stlm_date", trade_date);
						MerchantSettleStatistics merchantSettleStatistics = merchantSettleStatisticsDao.queryMerchantSettleStatisticsInfo(parameterMap);
						
						if(merchantSettleStatistics != null){
							if(whetherTk == 1){//退款
								merchantSettleStatistics.setRefund_amount(PoundageCalculate.sub(merchantSettleStatistics.getRefund_amount(), trade_amount).toString());
								merchantSettleStatistics.setRefund_count(merchantSettleStatistics.getRefund_count() + 1);
								merchantSettleStatistics.setMer_refund_fee(PoundageCalculate.add(merchantSettleStatistics.getMer_refund_fee(),mer_fee).toString());
								merchantSettleStatistics.setSystem_refund_fee(PoundageCalculate.add(merchantSettleStatistics.getSystem_refund_fee(),system_fee).toString());
								merchantSettleStatistics.setRefund_zf_fee(PoundageCalculate.add(merchantSettleStatistics.getRefund_zf_fee(),zf_fee).toString());
							}else{//消费
								merchantSettleStatistics.setTrade_amount(PoundageCalculate.add(merchantSettleStatistics.getTrade_amount(),trade_amount).toString());
								merchantSettleStatistics.setSystem_fee(PoundageCalculate.add(merchantSettleStatistics.getSystem_fee(),system_fee).toString());
								merchantSettleStatistics.setMer_fee(PoundageCalculate.add(merchantSettleStatistics.getMer_fee(),mer_fee).toString());
								merchantSettleStatistics.setZf_fee(PoundageCalculate.add(merchantSettleStatistics.getZf_fee(),zf_fee).toString());
								if(trade_amount < 0){
									merchantSettleStatistics.setTrade_gc_count(merchantSettleStatistics.getTrade_count());
									merchantSettleStatistics.setTrade_count(merchantSettleStatistics.getTrade_count() + 1);
								}else{
									merchantSettleStatistics.setTrade_count(merchantSettleStatistics.getTrade_count() + 1);
									merchantSettleStatistics.setTrade_gc_count(merchantSettleStatistics.getTrade_count());
								}
							}						
							int effectNum = merchantSettleStatisticsDao.updateMerchantSettleStatisticsInfo(merchantSettleStatistics);
							if(effectNum <= 0){
								return 5;
							}
						}else{
							
							int endDate = merchantFundSettleDao.queryMerSettleDataEndDate(mer_code);
							
							merchantSettleStatistics = new MerchantSettleStatistics();
							merchantSettleStatistics.setDeduct_stlm_date(trade_date);//清算日期
							if(data_status == 1){//退款经办
								merchantSettleStatistics.setJs_date(endDate==0?trade_date:endDate);//结算日期
							}else{//其他
								merchantSettleStatistics.setJs_date(trade_date);//结算日期
							}
							merchantSettleStatistics.setMer_code(mer_code);//商户号
							merchantSettleStatistics.setInst_id(inst_id);//渠道ID
							merchantSettleStatistics.setInst_type(bank_type);//网关类型
							merchantSettleStatistics.setMer_type(merchants.getMer_category());//商户类型
							merchantSettleStatistics.setSettle_amount("0.00");//结算金额
							merchantSettleStatistics.setData_status(data_status);//数据状态
							if(whetherTk == 1){//退款
//								merchantSettleStatistics.setRefund_amount(trade_amount+"");//退款金额
								merchantSettleStatistics.setRefund_amount(PoundageCalculate.sub(0, trade_amount).toString());//退款金额
								merchantSettleStatistics.setRefund_count(1);//退款笔数
								merchantSettleStatistics.setMer_refund_fee(mer_fee+"");//退回商户手续费
								merchantSettleStatistics.setSystem_refund_fee(system_fee+"");//退回系统手续费
								merchantSettleStatistics.setRefund_zf_fee(zf_fee+"");//退回银行手续费
								merchantSettleStatistics.setTrade_amount("0.00");//交易金额
								merchantSettleStatistics.setTrade_count(0);//交易笔数
								merchantSettleStatistics.setMer_fee("0.00");//商户手续费
								merchantSettleStatistics.setSystem_fee("0.00");//系统手续费
								merchantSettleStatistics.setZf_fee("0.00");//银行手续费
							}else {//消费
								if(trade_amount < 0){
									merchantSettleStatistics.setTrade_gc_count(0);
									merchantSettleStatistics.setTrade_count(1);
								}else{
									merchantSettleStatistics.setTrade_count(1);
									merchantSettleStatistics.setTrade_gc_count(merchantSettleStatistics.getTrade_count());
								}
								merchantSettleStatistics.setTrade_amount(trade_amount+"");
								merchantSettleStatistics.setSystem_fee(String.valueOf(system_fee));
								merchantSettleStatistics.setZf_fee(String.valueOf(zf_fee));
								merchantSettleStatistics.setMer_fee(mer_fee+"");
								merchantSettleStatistics.setRefund_amount("0.00");
								merchantSettleStatistics.setRefund_count(0);
								merchantSettleStatistics.setMer_refund_fee("0.00");
								merchantSettleStatistics.setSystem_refund_fee("0.00");
								merchantSettleStatistics.setRefund_zf_fee("0.00");
							}
							merchantSettleStatistics.setBank_id(bank_id);//网关ID
							int effectNum = merchantSettleStatisticsDao.addMerchantSettleStatisticsInfo(merchantSettleStatistics);
							if(effectNum <= 0){
								return 5;
							}
						}
					} catch (Exception e) {
						log.error("差错正常结算,保存T+1数据时抛出异常:"+e);
						throw e;
					}
					
					return 0;
				}catch(Exception e){
					throw new RuntimeException();
				}
			}else{
				return 1;
			}
		}else{
			log.debug("商户号参数mer_code为"+mer_code+",不能为空,请核实");
			throw new RuntimeException();
		}
	}
}
