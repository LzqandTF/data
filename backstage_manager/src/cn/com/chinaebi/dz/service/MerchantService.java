package cn.com.chinaebi.dz.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.object.util.ExchangeCodeEncodingUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.webservice.MerInfoHandlerReqType;

public class MerchantService {
	private static Log log = LogFactory.getLog(MerchantService.class);
	private static cn.com.chinaebi.dz.object.dao.iface.MerBasicDAO merBasicDAO = cn.com.chinaebi.dz.object.dao.MerBasicDAO.getInstance();
	
	public synchronized static boolean handleMerchantInfo(MerInfoHandlerReqType type) throws Exception{
		boolean flag = false;
		try{
			
			Map<String,Object> parameterMap = new HashMap<String,Object>();
			
			/**  商户基本信息    */
			String innerMerCode=type.getInnerMerCode();
			
			String province=type.getProvince();//所属省份
			String prov = "";
			String city = "";
			if(StringUtils.isNotBlank(province)){
				if(province.contains("|")){
					String[] citys=province.split("\\|");
					if(citys != null && citys.length != 0){
						prov=citys[0].toString();//省份编号
						city=citys[1].toString();//城市编号
					}
				}else{
					prov=province;
				}
			}
			
			String startDate= type.getStartDateFrom();//合同起始日期
//			StringBuilder sdf=new StringBuilder();
//			String startDateFrom=sdf.append(startDate.substring(0, 4)).append("-").append(startDate.substring(4, 6)).append("-").append(startDate.substring(6, 8)).toString();
			
			String endDate=type.getEndDateFrom();//合同结束日期
//			StringBuilder sdg=new StringBuilder();
//			String endDateFrom=sdg.append(endDate.substring(0, 4)).append("-").append(endDate.substring(4, 6)).append("-").append(endDate.substring(6, 8)).toString();
			
			/** 商户基本信息  */
			parameterMap.put("innerMerCode", type.getInnerMerCode());//电银商户号
			if(type.getMerCategory()==1 || type.getMerCategory() ==3){
				parameterMap.put("merCategory", 1);//商户类别
			}else{
				parameterMap.put("merCategory", 2);//商户类别
			}
			parameterMap.put("merName", ExchangeCodeEncodingUtil.parseISO88591ToUTF8(type.getMerName()));//商户名称
			parameterMap.put("merAbbreviation", ExchangeCodeEncodingUtil.parseISO88591ToUTF8(type.getMerAbbreviation()));//商户简称
			parameterMap.put("industry", type.getIndustry());//商户状态
			parameterMap.put("prov", prov);//省份编号
			parameterMap.put("city", city);//城市编号
			parameterMap.put("merType", type.getMerType());//商户类型
			parameterMap.put("startDateFrom", Integer.valueOf(startDate));//合同起始日期
			parameterMap.put("endDateFrom", Integer.valueOf(endDate));//合同结束日期
			parameterMap.put("tradeChannel", ExchangeCodeEncodingUtil.parseISO88591ToUTF8(type.getTradeChannel()) );//所属交易渠道
			parameterMap.put("merExpand", ExchangeCodeEncodingUtil.parseISO88591ToUTF8(type.getMerExpand()));//商户拓展方(代理商名称)
			parameterMap.put("merExpandno", type.getMerExpandNo());//商户拓展方(代理商编号)
			parameterMap.put("dataHandler", type.getDataHandler()+"");//数据处理  add : 增、delete : 删、update : 改
			
			/**  商户结算信息    */
			parameterMap.put("clearingObject", type.getClearingObject());//结算对象
			parameterMap.put("clearingBankNo", type.getClearingBankNo());//结算银行联行号
			parameterMap.put("clearingBankName", ExchangeCodeEncodingUtil.parseISO88591ToUTF8(type.getClearingBankName()));//结算银行名称
			parameterMap.put("clearingAccountName", ExchangeCodeEncodingUtil.parseISO88591ToUTF8(type.getClearingAccountName()));//结算账户名称
			parameterMap.put("clearingCardNo", type.getClearingCardNo());//结算银行账号
			parameterMap.put("clearingWay", type.getClearingWay());//结算方式
			parameterMap.put("clearingMinMoney", type.getClearingMinMoney());//结算最少金额(元)
			parameterMap.put("clearingPeriod", type.getClearingPeriod());//结算周期 
			if(type.getHandworkClearing()==1){
				parameterMap.put("handworkClearing", 2);//手工结算
			}else{
				parameterMap.put("handworkClearing", 1);//手工结算
			}
			parameterMap.put("dyClearingNo", type.getDyClearingNo());//电银结算账号
			parameterMap.put("clearingStatus", type.getClearingStatus());//结算状态
			parameterMap.put("merSignedFee", type.getMerSignedFee());//商户签约手续费
			if(type.getClearingObject()==1){//结算类型
				parameterMap.put("bilType", 1);
			}else if(type.getClearingObject()==2){
				parameterMap.put("bilType", 2);
			}
			
			//逻辑判断
			//TODO 先查询该商户信息是否存在，然后再做相应的添加或者修改处理
			MerBasic merBasic = Backstage.getInstance().getMerBasic(innerMerCode);
			
			parameterMap.put("merBasic", merBasic);//商户基本信息
			
			MerBilling merBilling = Backstage.getInstance().getMerBilling(innerMerCode);
			
			parameterMap.put("merBilling", merBilling);//商户结算信息
			
			Map<String,Object> map = merBasicDAO.saveOrUpdateMerInfo(parameterMap);
			
			boolean resultFlag = (Boolean)map.get("result");
			if(resultFlag){
				log.info("将内存中商户基本信息更新，更新对象的商户号为:"+innerMerCode);
				Backstage.getInstance().setMerBasicMap(innerMerCode, (MerBasic)map.get("merBasic"));
				log.info("将内存中商户结算信息更新，更新对象的商户号为:"+innerMerCode);
				Backstage.getInstance().setMerBillingMap(innerMerCode, (MerBilling)map.get("merBilling"));
			}
			flag = resultFlag;
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return flag;
	}
	
	/**
	 * 更新融易付同步过来的商户基本信息和商户结算信息
	 * @param merBasic    商户基本信息
	 * @param merBilling  商户结算信息
	 * @return
	 * @throws Exception
	 */
	public  static boolean handleRYFMerInfo(MerBasic merBasic,MerBilling merBilling) throws Exception{
	    boolean b = false;
		try{
			if(merBasic == null || merBilling == null){
				log.error("商户基本信息或者商户结算信息为空");
				return false;
			}
			b = merBasicDAO.saveOrUpdateMerAndBasic(merBasic, merBilling);
		}catch(Exception e){
			log.error("出现异常" + e.getMessage());
		}
		return b;
	}
	
	
//	/**
//	 * 更新加密后的结算商户信息
//	 * @param merBasic    商户基本信息
//	 * @param merBilling  商户结算信息
//	 * @return
//	 * @throws Exception
//	 */
//	public  static boolean updateMerDesInfo(List<MerBilling> merBillings) throws Exception{
//	    boolean b = false;
//		try{
//			if(merBillings == null || merBillings.size() == 0){
//				log.error("商户结算信息为空");
//				return false;
//			}
//			b = merBasicDAO.updateMerDesInfo(merBillings);
//		}catch(Exception e){
//			log.error("出现异常" + e.getMessage());
//		}
//		return b;
//	}
}
