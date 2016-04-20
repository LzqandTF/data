package com.chinaebi.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rytong.encrypto.provider.DesEncrypto;

import com.chinaebi.entity.Merchants;
import com.chinaebi.entity.UnionpayAreaCode;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.MerchantsService;
import com.chinaebi.service.UnionpayAreaCodeService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 商户信息管理控制层
 *
 */
@Controller
public class MerchantsController {
	private static Logger log = LoggerFactory.getLogger(MerchantsController.class);
	
	// 商户信息查询
	private static final String QUERY_MERINFO_LIST = "/queryMerInfoList.do";
	private static final String JSP_PAGE = "/merInfo/queryMerInfo";
	
	private static final String VAGUEQUERYMERCHANTINFOBYMERCODE = "/vagueQueryMerchantInfoByMerCode.do";
	
	private static final String UPDATEMERCHANTACCOUNT = "/updateMerchantAccount.do";
	
	// 商户信息配置，根据商户号查询商户基本信息和结算信息
	private static final String QUERY_ALL_MERINFO_BY_MERCODE = "queryAllMerInfoByMerCode.do";
	
	// 商户信息配置操作
	private static final String UPDATE_MERINFO_BY_MERCODE = "updateMerInfoByMerCode.do";
	
	@Autowired
	private MerchantsService  merchantsService;
	
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "unionpayAreaCodeService")
	private UnionpayAreaCodeService unionpayAreaCodeService;
	
	/**
	 * 商户信息查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_MERINFO_LIST, method = RequestMethod.POST)
	public String queryPageMerchantsList(ServletRequest request, Model model) {
		log.info("进入商户信息查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			
			Page<Merchants> page = new Page<Merchants>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String mer_code = request.getParameter("merCode");
			String mer_name = request.getParameter("mer_name");
			String bil_status = request.getParameter("bilStatus");
			String bil_manual = request.getParameter("bilManual");
			String mer_category = request.getParameter("merCategory");
			String bil_type = request.getParameter("bilType");
			String mer_state = request.getParameter("merState");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mer_code)) {
				map.put("mer_code", mer_code);
			}
			if (StringUtils.isNotBlank(mer_name)) {
				map.put("mer_name", mer_name);
			}
			if (StringUtils.isNotBlank(bil_status)) {
				map.put("bil_status", bil_status);
			}
			if (StringUtils.isNotBlank(bil_manual)) {
				map.put("bil_manual", bil_manual);
			}
			if (StringUtils.isNotBlank(mer_category)) {
				map.put("mer_category", mer_category);
			}
			if (StringUtils.isNotBlank(bil_type)) {
				map.put("bil_type", bil_type);
			}
			if (StringUtils.isNotBlank(mer_state)) {
				map.put("mer_state", mer_state);
			}
			
			model.addAttribute("merchantsList", merchantsService.queryPageMerchantsList(page, map));
			model.addAttribute("bil_status", bil_status);
			model.addAttribute("bil_manual", bil_manual);
			model.addAttribute("mer_category", mer_category);
			model.addAttribute("bil_type", bil_type);
			model.addAttribute("mer_state", mer_state);
			model.addAttribute("mer_name", mer_name);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return JSP_PAGE;
	}
	
	/**
	 * 通过商户号模糊查询商户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = VAGUEQUERYMERCHANTINFOBYMERCODE,method=RequestMethod.POST)
	@ResponseBody
	public List<Merchants> queryMerchantInfoByMerCode(ServletRequest request){
		List<Merchants> list = null;
		try{
			String mer_code = request.getParameter("mer_code");
			if(StringUtils.isNotBlank(mer_code)){
				list = merchantsService.vagueQueryMerchantInfoByMerCode(mer_code);
			}
		}catch(Exception e){
			log.error("通过商户号模糊查询商户信息时抛出异常:"+e.getMessage());
		}
		return list;
	}
	
	@RequestMapping(value=UPDATEMERCHANTACCOUNT,method=RequestMethod.POST)
	@ResponseBody
	public int updateMerchantAccount(){
		int result = 0;
		try{
			List<Merchants> list = merchantsService.queryMerchantsBilBankAccountInfo();
			Map<String,Object> map = new HashMap<String,Object>();
			if(list != null && list.size() > 0){
				for (Merchants merchants : list) {
					map.put("mer_code", merchants.getMer_code());
					map.put("bil_bankaccount",DesEncrypto.decrypt(merchants.getBil_bankaccount(),"iFv5x6Cu"));
					result += merchantsService.updateMerBillingBankAccount(map);
				}
				log.info("共查询到"+list.size()+"条商户信息,并成功修改商户结算银行卡号信息"+result+"条");
				if(result != list.size()){
					result = 0;
				}else{
					result = 1;
				}
			}
		}catch(Exception e){
			log.error("修改商户结算银行账户号抛出异常:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = QUERY_ALL_MERINFO_BY_MERCODE)
	@ResponseBody
	public Merchants queryAllMerInfoByMerCode(ServletRequest request) {
		log.info("开始根据商户号查询商户基本信息和结算信息...");
		Merchants merchants = null;
		try {
			String merCode = request.getParameter("merCode");
			merchants = merchantsService.queryAllMerInfoByMerCode(merCode);
			if (merchants != null) {
				String province = merchants.getProvinces();
				String city = merchants.getCity();
				if (StringUtils.isNotBlank(province)) {
					UnionpayAreaCode unionpayAreaCode = unionpayAreaCodeService.queryAreaNameByProvince(province);
					if (unionpayAreaCode != null) {
						merchants.setProvinces(unionpayAreaCode.getArea_name());
					} else {
						merchants.setProvinces("");
					}
				} else {
					merchants.setProvinces("");
				}
				if (StringUtils.isNotBlank(province) && StringUtils.isNotBlank(city)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("province", province);
					map.put("city", city);
					UnionpayAreaCode unionpayAreaCode = unionpayAreaCodeService.queryAreaNameByProvinceAndCity(map);
					if (unionpayAreaCode != null) {
						merchants.setCity(unionpayAreaCode.getArea_name());
					} else {
						merchants.setCity("");
					}
				} else {
					merchants.setCity("");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return merchants;
	}
	
	@RequestMapping(value = UPDATE_MERINFO_BY_MERCODE, method = RequestMethod.POST)
	@ResponseBody
	public int updateMerInfoByMerCode(ServletRequest request) {
		log.info("开始根据商户号修改商户信息...");
		int effectNum = 0;
		try {
			String mer_code = request.getParameter("mer_code");
			String endDate = request.getParameter("endDate");
			String bil_manual = request.getParameter("bil_manual");
			String bil_status = request.getParameter("bil_status");
			String bil_cycle = request.getParameter("bil_cycle");
			String bil_smallamt = request.getParameter("bil_smallamt");
			String whtherFz = request.getParameter("whtherFz");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mer_code)) {
				map.put("mer_code", mer_code);
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate);
			}
			if (StringUtils.isNotBlank(bil_manual)) {
				map.put("bil_manual", bil_manual);
			}
			if (StringUtils.isNotBlank(bil_status)) {
				map.put("bil_status", bil_status);
			}
			if (StringUtils.isNotBlank(bil_cycle)) {
				map.put("bil_cycle", bil_cycle);
			}
			if (StringUtils.isNotBlank(bil_smallamt)) {
				map.put("bil_smallamt", bil_smallamt);
			}
			if (StringUtils.isNotBlank(whtherFz)) {
				map.put("whtherFz", whtherFz);
			}
			
			int merBasicCount = merchantsService.updateMerBasicByMerCode(map);
			if (merBasicCount > 0) {
				log.info("根据商户号" + mer_code + "修改商户基本信息成功");
				int merBillingCount = merchantsService.updateMerBillingByMerCode(map);
				if (merBillingCount > 0) {
					log.info("根据商户号" + mer_code + "修改商户结算信息成功");
					Merchants merchants = merchantsService.queryAllMerInfoByMerCode(mer_code);
					dataManagerInit.setMerBasicMap(mer_code, merchants);
					log.info("更新管理平台内存中商户基本信息成功");
					boolean result = merchantsService.updateRamMerBasicInfo(mer_code);
					if (result) {
						log.info("更新后台内存中商户基本信息成功");
					} else {
						log.info("更新后台内存中商户基本信息失败");
					}
					
					dataManagerInit.setMerBillingMap(mer_code, merchants);
					log.info("更新管理平台内存中商户结算信息成功");
					boolean merBillgingResult = merchantsService.updateRamMerBillingInfo(mer_code);
					if (merBillgingResult) {
						log.info("更新后台内存中商户结算信息成功");
					} else {
						log.info("更新后台内存中商户结算信息失败");
					}
					effectNum = 1;
				}
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}
}
