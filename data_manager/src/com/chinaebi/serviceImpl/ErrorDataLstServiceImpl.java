package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinaebi.action.CommonClass;
import com.chinaebi.dao.ErrorDataLstDao;
import com.chinaebi.dao.ICommonDao;
import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.DuizhangData;
import com.chinaebi.entity.ErrorDataLst;
import com.chinaebi.entity.InnerErrorHandling;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.exception.PageException;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.ErrorDataLstService;
import com.chinaebi.service.InnerErrorHandlingService;
import com.chinaebi.utils.ReflectUtils;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "errorDataLstService")
public class ErrorDataLstServiceImpl implements ErrorDataLstService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier(value = "errorDataLstDao")
	private ErrorDataLstDao errorDataLstDao;

	@Autowired
	@Qualifier(value = "commonClass")
	private CommonClass commonClass;
	
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "commonDao")
	private ICommonDao<DuizhangData> duizhangDataDao;
	
	@Autowired
	@Qualifier(value = "innerErrorHandlingService")
	private InnerErrorHandlingService innerErrorHandlingService;
	
	/**
	 * 内部差错调整数据查询
	 */
	@Override
	public Page<ErrorDataLst> queryPageErrorDataLst(Page<ErrorDataLst> page, Map<String, Object> map) {
		Page<ErrorDataLst> errorDataLstPage = null;
		List<ErrorDataLst> list = null;
		try{
			errorDataLstPage = errorDataLstDao.queryPageErrorDataLst(page, map);
			list = errorDataLstPage.getResult();
			if(list != null && list.size() > 0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				
				StringBuffer sb = new StringBuffer();
				//处理页面字段的显示
				for (ErrorDataLst errorDataLst : list) {
					//处理电银内部交易情况  交易类型
					//处理码
					String process = errorDataLst.getReq_process();
					//交易消息类型
					int trade_msg_type = errorDataLst.getTrademsg_type();
					errorDataLst.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
					
					//处理驳回原因的显示
					String turnDown_remark = errorDataLst.getTurnDown_remark();
					if (StringUtils.isNotBlank(turnDown_remark)) {
						if (turnDown_remark.length() > 3) {
							sb.setLength(0);
							sb.append(turnDown_remark.substring(0, 3)).append("...");
							errorDataLst.setTurnDown_remark(sb.toString());
						}
					}
					
					int handlingId = errorDataLst.getHandling_id();
					InnerErrorHandling innerErrorHandling = innerErrorHandlingService.queryInnerErrorHandling(handlingId);
					if(innerErrorHandling != null){
						errorDataLst.setHandling_name(innerErrorHandling.getHandling_name());
					}
					
					/**
					 * 0:原始差错
					 * 1:对账文件差错  (目前来讲对账文件数据在对账处理时直接保存了,获取数据时只需要根据数据来源为1的就ok了)
					 */
					int errorResource = errorDataLst.getError_resource();
					//如果是原始差错，则根据流水号去对账文件查询数据显示在扣款渠道交易情况
					if (errorResource == 0) {
						int deductSysId = errorDataLst.getDeduct_sys_id();
						//获取线上、线下标识
						int instType = errorDataLst.getInst_type();
						//根据扣款渠道ID获取对账文件表的名称
						InstInfo instInfo = dataManagerInit.getInstInfoById(deductSysId, instType);
						BankInst bankInst = dataManagerInit.getBankInstByBankId(errorDataLst.getBank_id());
						paramMap.put("dz_data_tableName", bankInst.getDz_data_tableName());
						paramMap.put("instType", instType);
						if(deductSysId == 3){
							switch(trade_msg_type){
							   case 20:
								   paramMap.put("REFICode", "REFI");
								   paramMap.put("REFPCode", "REFP");
								   break;
							   default:
								   paramMap.put("REFICode", "PCEI");
								   paramMap.put("REFPCode", "PCEP");
								   break;
							}
						}
						//判断是否冲正
						if(errorDataLst.getDeduct_roll_bk() == 1){
							paramMap.put("reqSysStance", ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column()));
							
							String deductRollbkSysTime = errorDataLst.getDeduct_rollbk_sys_time();
							if (StringUtils.isNotBlank(deductRollbkSysTime)) {
								paramMap.put("reqTime", deductRollbkSysTime.substring(0, 10).replace("-", ""));
							}
							DuizhangData duizhangData = duizhangDataDao.queryForObject("Duizhang_Data.queryByReqSysStance", paramMap);
							if (duizhangData != null) {
								String tradeTime = duizhangData.getReqTime();
								String trade_amount = duizhangData.getTradeAmount();
								double tradeAmount = 0d;
								if (StringUtils.isNotBlank(trade_amount)) {
									tradeAmount = Double.valueOf(trade_amount);
								}
								//处理对账文件的时间
								if (StringUtils.isNotBlank(tradeTime)) {
									sb.setLength(0);
									sb.append(tradeTime.substring(0, 4)).append("-").append(tradeTime.substring(4, 6)).append("-").
									append(tradeTime.substring(6, 8)).append(" ").append(tradeTime.substring(8, 10)).append(":").
									append(tradeTime.substring(10, 12)).append(":").append(tradeTime.substring(12, 14));
									errorDataLst.setDzTime(sb.toString());
								}
								//处理对账文件的金额
								if (errorDataLst.getTrade_amount() < 0) {
									double amount = 0 - tradeAmount;
									errorDataLst.setDzTradeAmount(String.valueOf(amount));
								} else {
									errorDataLst.setDzTradeAmount(String.valueOf(tradeAmount));
								}
								
								//处理交易类型
								errorDataLst.setDzTradeType(errorDataLst.getTradeType());
								//交易结果
								errorDataLst.setDzTradeResult("成功");
							} 
						} else {
							//如果没有冲正，则根据扣款流水号去对账文件查询
							Object dzDataColumn = ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column());
							if (dzDataColumn != null && !"null".equals(dzDataColumn) && !"".equals(dzDataColumn)) {
								if (instType == 0) {
									paramMap.put("reqSysStance", dzDataColumn);
								} else {
									paramMap.put("id", dzDataColumn);
								}
								String deductSysTime = errorDataLst.getDeduct_stlm_date();
								if (StringUtils.isNotBlank(deductSysTime)) {
									paramMap.put("reqTime", deductSysTime.substring(0, 10).replace("-", ""));
								}
								DuizhangData duizhangData = duizhangDataDao.queryForObject("Duizhang_Data.queryByReqSysStance", paramMap);
								if (duizhangData != null) {
									String tradeTime = duizhangData.getReqTime();
									String trade_amount = duizhangData.getTradeAmount();
									double tradeAmount = 0d;
									if (StringUtils.isNotBlank(trade_amount)) {
										tradeAmount = Double.valueOf(trade_amount);
									}
									//处理对账文件时间
									if (StringUtils.isNotBlank(tradeTime)) {
										errorDataLst.setDzTime(tradeTime);
									}
									//处理对账文件金额正负
									if (errorDataLst.getTrade_amount() < 0) {
										double amount = 0 - tradeAmount;
										errorDataLst.setDzTradeAmount(String.valueOf(amount));
									} else {
										errorDataLst.setDzTradeAmount(String.valueOf(tradeAmount));
									}
									
									//处理交易类型
									if (instType == 0) {
										errorDataLst.setDzTradeType(errorDataLst.getTradeType());
									} else {
										//如果是线上交易
										if (trade_msg_type == 2) {
											errorDataLst.setDzTradeType("收款交易");
										}
										if (trade_msg_type == 20) {
											errorDataLst.setDzTradeType("退款交易");
										}
									}
									//交易结果
									errorDataLst.setDzTradeResult("成功");
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("内部差错调整查询数据出错：" + e.getMessage());
		}
		return errorDataLstPage;
	}

	@Override
	public ErrorDataLst queryErrorDataLstDetails(String trade_id) {
		return errorDataLstDao.queryErrorDataLstDetails(trade_id);
	}

	@Override
	public int updateErrorHandleMethod(ErrorDataLst errorDataLst) {
		int flag = 0;
		try {
			flag = errorDataLstDao.updateErrorHandleMethod(errorDataLst);
			if (flag > 0) {
				return flag;
			} else {
				logger.error("内部差错调整失败...");
			}
		} catch (Exception e) {
			logger.error("内部出错调整出错：" + e.getMessage());
		}
		return flag;
	}

	@Override
	@Transactional
	public int updateHandlingStatus(HttpServletRequest request, ErrorDataLst errorData) {
		int updateNum = 0;
		try {
			updateNum = errorDataLstDao.updateHandlingStatus(errorData);
			if (updateNum > 0) {
				return updateNum;
			}
		} catch (Exception e) {
			logger.error("内部差错审批修改数据状态出现异常：" + e.getMessage());
		}
		return updateNum;
	}

	@Override
	public int updateApproval(ErrorDataLst errorDataLst) {
		int flag = 0;
		try {
			flag = errorDataLstDao.updateApproval(errorDataLst);
			if (flag == 0) {
				logger.error("驳回操作失败...");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public int updateHandlerRemark(ErrorDataLst errorDataLst) {
		int flag = 0;
		try {
			flag = errorDataLstDao.updateHandlerRemark(errorDataLst);
			if (flag == 0) {
				logger.error("修改备注失败...");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public List<ErrorDataLst> queryErrorDataLst(Map<String, Object> map) {
		return errorDataLstDao.selectList(map);
	}
	
	/**
	 * 内部差错调整审批  数据查询
	 */
	@Override
	public Page<ErrorDataLst> queryPageApprovalData(Page<ErrorDataLst> page, Map<String, Object> map) {
		Page<ErrorDataLst> pageApprovalData = null;
		List<ErrorDataLst> list = null;
		try {
			pageApprovalData = errorDataLstDao.queryPageApprovalData(page, map);
			list = pageApprovalData.getResult();
			if(list != null && list.size() > 0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				
				StringBuffer sb = new StringBuffer();
				//处理页面字段的显示
				for (ErrorDataLst errorDataLst : list) {
					//处理电银内部交易情况  交易类型
					//处理码
					String process = errorDataLst.getReq_process();
					//交易消息类型
					int trade_msg_type = errorDataLst.getTrademsg_type();
					errorDataLst.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
					
					//处理驳回原因的显示
					String turnDown_remark = errorDataLst.getTurnDown_remark();
					if (StringUtils.isNotBlank(turnDown_remark)) {
						if (turnDown_remark.length() > 3) {
							sb.setLength(0);
							sb.append(turnDown_remark.substring(0, 3)).append("...");
							errorDataLst.setTurnDown_remark(sb.toString());
						}
					}
					
					/**
					 * 0:原始差错
					 * 1:对账文件差错  (目前来讲对账文件数据在对账处理时直接保存了,获取数据时只需要根据数据来源为1的就ok了)
					 */
					int errorResource = errorDataLst.getError_resource();
					//如果是原始差错，则根据流水号去对账文件查询数据显示在扣款渠道交易情况
					if (errorResource == 0) {
						int deductSysId = errorDataLst.getDeduct_sys_id();
						//获取线上、线下标识
						int instType = errorDataLst.getInst_type();
						//根据扣款渠道ID获取对账文件表的名称
						InstInfo instInfo = dataManagerInit.getInstInfoById(deductSysId, instType);
						BankInst bankInst = dataManagerInit.getBankInstByBankId(instInfo.getBank_id());
						paramMap.put("dz_data_tableName", bankInst.getDz_data_tableName());
						paramMap.put("instType", instType);
						
						if(deductSysId == 3){
							switch(trade_msg_type){
							   case 20:
								   paramMap.put("REFICode", "REFI");
								   paramMap.put("REFPCode", "REFP");
								   break;
							   default:
								   paramMap.put("REFICode", "PCEI");
								   paramMap.put("REFPCode", "PCEP");
								   break;
							}
						}
						
						//判断是否冲正
						if(errorDataLst.getDeduct_roll_bk() == 1){
							paramMap.put("reqSysStance", ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column()));
							String deductRollbkSysTime = errorDataLst.getDeduct_rollbk_sys_time();
							if (StringUtils.isNotBlank(deductRollbkSysTime)) {
								paramMap.put("reqTime", deductRollbkSysTime.substring(0, 10).replace("-", ""));
							}
							DuizhangData duizhangData = duizhangDataDao.queryForObject("Duizhang_Data.queryByReqSysStance", paramMap);
							if (duizhangData != null) {
								String tradeTime = duizhangData.getReqTime();
								String trade_amount = duizhangData.getTradeAmount();
								double tradeAmount = 0d;
								if (StringUtils.isNotBlank(trade_amount)) {
									tradeAmount = Double.valueOf(trade_amount);
								}
								//处理对账文件的时间
								if (StringUtils.isNotBlank(tradeTime)) {
									sb.setLength(0);
									sb.append(tradeTime.substring(0, 4)).append("-").append(tradeTime.substring(4, 6)).append("-").
									append(tradeTime.substring(6, 8)).append(" ").append(tradeTime.substring(8, 10)).append(":").
									append(tradeTime.substring(10, 12)).append(":").append(tradeTime.substring(12, 14));
									errorDataLst.setDzTime(sb.toString());
								}
								//处理对账文件的金额
								if (errorDataLst.getTrade_amount() < 0) {
									double amount = 0 - tradeAmount;
									errorDataLst.setDzTradeAmount(String.valueOf(amount));
								} else {
									errorDataLst.setDzTradeAmount(String.valueOf(tradeAmount));
								}
								
								//处理交易类型
								errorDataLst.setDzTradeType(errorDataLst.getTradeType());
								//交易结果
								errorDataLst.setDzTradeResult("成功");
							} 
						} else {
							//如果没有冲正，则根据扣款流水号去对账文件查询
							Object dzDataColumn = ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column());
							if (dzDataColumn != null && !"null".equals(dzDataColumn) && !"".equals(dzDataColumn)) {
								if (instType == 0) {
									paramMap.put("reqSysStance", dzDataColumn);
								} else {
									paramMap.put("id", dzDataColumn);
								}
								String deductSysTime = errorDataLst.getDeduct_stlm_date();
								if (StringUtils.isNotBlank(deductSysTime)) {
									paramMap.put("reqTime", deductSysTime.substring(0, 10).replace("-", ""));
								}
								DuizhangData duizhangData = duizhangDataDao.queryForObject("Duizhang_Data.queryByReqSysStance", paramMap);
								if (duizhangData != null) {
									String tradeTime = duizhangData.getReqTime();
									String trade_amount = duizhangData.getTradeAmount();
									double tradeAmount = 0d;
									if (StringUtils.isNotBlank(trade_amount)) {
										tradeAmount = Double.valueOf(trade_amount);
									}
									//处理对账文件时间
									if (StringUtils.isNotBlank(tradeTime)) {
										errorDataLst.setDzTime(tradeTime);
									}
									//处理对账文件金额正负
									if (errorDataLst.getTrade_amount() < 0) {
										double amount = 0 - tradeAmount;
										errorDataLst.setDzTradeAmount(String.valueOf(amount));
									} else {
										errorDataLst.setDzTradeAmount(String.valueOf(tradeAmount));
									}
									
									//处理交易类型
									if (instType == 0) {
										errorDataLst.setDzTradeType(errorDataLst.getTradeType());
									} else {
										//如果是线上交易
										if (trade_msg_type == 2) {
											errorDataLst.setDzTradeType("收款交易");
										}
										if (trade_msg_type == 20) {
											errorDataLst.setDzTradeType("退款交易");
										}
									}
									//交易结果
									errorDataLst.setDzTradeResult("成功");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("内部差错调整审批  查询数据出错：" + e.getMessage());
		}
		return pageApprovalData;
	}

	@Override
	public Page<ErrorDataLst> queryOptionErrorLst(Page<ErrorDataLst> page, Map<String, Object> map) {
		Page<ErrorDataLst> pageList = null;
		try{
			pageList = errorDataLstDao.queryOptionErrorLst(page, map);
			if(pageList.getResult() == null){
				throw new PageException("errorDataLstDao.queryOptionErrorLst(page, map) 查询数据为NULL");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<ErrorDataLst> queryAllForDownExcel(Map<String, Object> map) {
		List<ErrorDataLst> list = null;
		try{
			list = errorDataLstDao.queryAllForDownExcel(map);
			if(list != null && list.size() > 0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				
				StringBuffer sb = new StringBuffer();
				//处理页面字段的显示
				for (ErrorDataLst errorDataLst : list) {
					//处理电银内部交易情况  交易类型
					//处理码
					String process = errorDataLst.getReq_process();
					//交易消息类型
					int trade_msg_type = errorDataLst.getTrademsg_type();
					errorDataLst.setTradeType(commonClass.getTradeTypeByProcessAndTradeMsgType(process,trade_msg_type));
					
					//处理驳回原因的显示
					String turnDown_remark = errorDataLst.getTurnDown_remark();
					if (StringUtils.isNotBlank(turnDown_remark)) {
						if (turnDown_remark.length() > 3) {
							sb.setLength(0);
							sb.append(turnDown_remark.substring(0, 3)).append("...");
							errorDataLst.setTurnDown_remark(sb.toString());
						}
					}
					
					int handlingId = errorDataLst.getHandling_id();
					InnerErrorHandling innerErrorHandling = innerErrorHandlingService.queryInnerErrorHandling(handlingId);
					if(innerErrorHandling != null){
						errorDataLst.setHandling_name(innerErrorHandling.getHandling_name());
					}
					
					/**
					 * 0:原始差错
					 * 1:对账文件差错  (目前来讲对账文件数据在对账处理时直接保存了,获取数据时只需要根据数据来源为1的就ok了)
					 */
					int errorResource = errorDataLst.getError_resource();
					//如果是原始差错，则根据流水号去对账文件查询数据显示在扣款渠道交易情况
					if (errorResource == 0) {
						int deductSysId = errorDataLst.getDeduct_sys_id();
						//获取线上、线下标识
						int instType = errorDataLst.getInst_type();
						//根据扣款渠道ID获取对账文件表的名称
						InstInfo instInfo = dataManagerInit.getInstInfoById(deductSysId, instType);
						BankInst bankInst = dataManagerInit.getBankInstByBankId(instInfo.getBank_id());
						paramMap.put("dz_data_tableName", bankInst.getDz_data_tableName());
						paramMap.put("instType", instType);
						if(deductSysId == 3){
							switch(trade_msg_type){
							   case 20:
								   paramMap.put("REFICode", "REFI");
								   paramMap.put("REFPCode", "REFP");
								   break;
							   default:
								   paramMap.put("REFICode", "PCEI");
								   paramMap.put("REFPCode", "PCEP");
								   break;
							}
						}
						//判断是否冲正
						if(errorDataLst.getDeduct_roll_bk() == 1){
							paramMap.put("reqSysStance", ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column()));
							String deductRollbkSysTime = errorDataLst.getDeduct_rollbk_sys_time();
							if (StringUtils.isNotBlank(deductRollbkSysTime)) {
								paramMap.put("reqTime", deductRollbkSysTime.substring(0, 10).replace("-", ""));
							}
							DuizhangData duizhangData = duizhangDataDao.queryForObject("Duizhang_Data.queryByReqSysStance", paramMap);
							if (duizhangData != null) {
								String tradeTime = duizhangData.getReqTime();
								String trade_amount = duizhangData.getTradeAmount();
								double tradeAmount = 0d;
								if (StringUtils.isNotBlank(trade_amount)) {
									tradeAmount = Double.valueOf(trade_amount);
								}
								//处理对账文件的时间
								if (StringUtils.isNotBlank(tradeTime)) {
									sb.setLength(0);
									sb.append(tradeTime.substring(0, 4)).append("-").append(tradeTime.substring(4, 6)).append("-").
									append(tradeTime.substring(6, 8)).append(" ").append(tradeTime.substring(8, 10)).append(":").
									append(tradeTime.substring(10, 12)).append(":").append(tradeTime.substring(12, 14));
									errorDataLst.setDzTime(sb.toString());
								}
								//处理对账文件的金额
								if (errorDataLst.getTrade_amount() < 0) {
									double amount = 0 - tradeAmount;
									errorDataLst.setDzTradeAmount(String.valueOf(amount));
								} else {
									errorDataLst.setDzTradeAmount(String.valueOf(tradeAmount));
								}
								
								//处理交易类型
								errorDataLst.setDzTradeType(errorDataLst.getTradeType());
								//交易结果
								errorDataLst.setDzTradeResult("成功");
							} 
						} else {
							//如果没有冲正，则根据扣款流水号去对账文件查询
							if (instType == 0) {
								paramMap.put("reqSysStance", ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column()));
							} else {
								paramMap.put("id", ReflectUtils.getFieldValue(errorDataLst, instInfo.getDz_data_column()));
							}
							String deductSysTime = errorDataLst.getTrade_time();
							if (StringUtils.isNotBlank(deductSysTime)) {
								paramMap.put("reqTime", deductSysTime.substring(0, 10).replace("-", ""));
							}
							DuizhangData duizhangData = duizhangDataDao.queryForObject("Duizhang_Data.queryByReqSysStance", paramMap);
							if (duizhangData != null) {
								String tradeTime = duizhangData.getReqTime();
								String trade_amount = duizhangData.getTradeAmount();
								double tradeAmount = 0d;
								if (StringUtils.isNotBlank(trade_amount)) {
									tradeAmount = Double.valueOf(trade_amount);
								}
								//处理对账文件时间
								if (StringUtils.isNotBlank(tradeTime)) {
									errorDataLst.setDzTime(tradeTime);
								}
								//处理对账文件金额正负
								if (errorDataLst.getTrade_amount() < 0) {
									double amount = 0 - tradeAmount;
									errorDataLst.setDzTradeAmount(String.valueOf(amount));
								} else {
									errorDataLst.setDzTradeAmount(String.valueOf(tradeAmount));
								}
								
								//处理交易类型
								if (instType == 0) {
									errorDataLst.setDzTradeType(errorDataLst.getTradeType());
								} else {
									//如果是线上交易
									if (trade_msg_type == 2) {
										errorDataLst.setDzTradeType("收款交易");
									}
									if (trade_msg_type == 20) {
										errorDataLst.setDzTradeType("退款交易");
									}
								}
								//交易结果
								errorDataLst.setDzTradeResult("成功");
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("内部差错调整下载xls查询数据为空:" + e.getMessage());
		}
		return list;
	}

	@Override
	public ErrorDataLst queryDetailByTradeId(String tradeId) {
		ErrorDataLst errorDataLst = null;
		try {
			errorDataLst = errorDataLstDao.queryDetailByTradeId(tradeId);
		} catch (Exception e) {
			logger.error("根据tradeId查询差错数据详情信息出现异常：" + e.getMessage());
		}
		return errorDataLst;
	}

	@Override
	public int queryErrorNoHandlerCount(Map<String, Object> map) {
		int result = 0;
		try{
			result = errorDataLstDao.queryErrorNoHandlerCount(map);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public Page<ErrorDataLst> queryPageDzTotalErrorData(Page<ErrorDataLst> page, Map<String, Object> map) {
		Page<ErrorDataLst> pageList = null;
		try{
			pageList = errorDataLstDao.queryPageDzTotalErrorData(page, map);
			if(pageList.getResult() == null){
				throw new PageException("errorDataLstDao.queryPageDzTotalErrorData(page, map) 查询数据为NULL");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public int queryCountDzTotalErrorData(Map<String, Object> map) {
		int result = 0;
		try{
			result = errorDataLstDao.queryCountDzTotalErrorData(map);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public List<ErrorDataLst> queryDzTotalErrorDataList(Map<String, Object> map) {
		List<ErrorDataLst> list = null;
		try{
			list = errorDataLstDao.queryDzTotalErrorDataList(map);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return list;
	}

	@Override
	public int addDataToErrorDataLst(ErrorDataLst errorDataLst) {
		int effectNum = 0;
		try {
			effectNum = errorDataLstDao.addDataToErrorDataLst(errorDataLst);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int addUnderLineDataToErrorDataLst(ErrorDataLst errorDataLst) {
		int effectNum = 0;
		try {
			effectNum = errorDataLstDao.addUnderLineDataToErrorDataLst(errorDataLst);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}
}
