package com.chinaebi.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.Login;
import com.chinaebi.entity.SettleMerchantConfig;
import com.chinaebi.entity.SettleMerchantMatch;
import com.chinaebi.service.SettleMerchantConfigService;
import com.chinaebi.service.SettleMerchantMatchService;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class SettleMerchantConfigController {
	private static final Logger log = LoggerFactory.getLogger(SettleMerchantConfigController.class);
	
	@Autowired
	@Qualifier(value="settleMerchantConfigService")
	private SettleMerchantConfigService settleMerchantConfigService;
	
	@Autowired
	@Qualifier(value="settleMerchantMatchService")
	private SettleMerchantMatchService settleMerchantMatchService;
	
	/*
	 * 页面
	 */
	private static final String SETTLE_MERCHANT_CONFIG = "/merBillingManager/settle_merchant_config";
	
	/*
	 * 方法
	 */
	private static final String QUERYPAGESETTLEMERCHANTCONFIG = "/queryPageSettleMerchantConfig.do";
	private static final String INSERTSETTLEMERCHANTCONFIG ="/insertSettleMerchantConfig.do";
	private static final String DELETESETTLEMERCHANTCONFIG = "/deleteSettleMerchantConfig.do";
	private static final String QUERYCONFIGCOUNTBYSETTLEMERCODE = "/queryConfigCountBySettleMerCode.do";
	
	private static final String QUERYSETTLEMERCHANTMATCH = "/querySettleMerchantMatch.do";
	private static final String QUERYSETTLEMERCHANTMATCHCOUNT = "/querySettleMerchantMatchCount.do";
	private static final String ADDSETTLEMERCHANTMATCH = "/addSettleMerchantMatch.do";
	
	/**
	 * 分页查询结算商户配置信息
	 * @param request 页面查询参数
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERYPAGESETTLEMERCHANTCONFIG,method=RequestMethod.POST)
	public String queryPageSettleMerchantConfig(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String settle_mer_code = request.getParameter("settle_mer_code");
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(settle_mer_code)){
			map.put("settle_mer_code", settle_mer_code);
		}
		Page<SettleMerchantConfig> page = new Page<SettleMerchantConfig>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		model.addAttribute("pageDataList",settleMerchantConfigService.queryPageSettleMerchantConfig(page, map));
		model.addAttribute("pageSize", pageSize);
		return SETTLE_MERCHANT_CONFIG;
	}
	
	/**
	 * 新增结算商户配置信息
	 * @param request
	 * @param settleMerchantConfig
	 * @return
	 */
	@RequestMapping(value=INSERTSETTLEMERCHANTCONFIG,method=RequestMethod.POST)
	@ResponseBody
	public int insertSettleMerchantConfig(HttpSession session,ServletRequest request,SettleMerchantConfig settleMerchantConfig){
		int result = 0;
		try{
			settleMerchantConfig.setOperate_time(DateUtil.formatDate2String(new Date(), "yyyy_MM-dd HH:mm:ss"));
			
			Login login = (Login)session.getAttribute("login");
			
			settleMerchantConfig.setUser_id(login.getLoginName());
			
			result = settleMerchantConfigService.insertSettleMerchantConfig(settleMerchantConfig);
		}catch(Exception e){
			log.error("新增结算商户噢诶之信息时抛出异常:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 删除结算商户配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value=DELETESETTLEMERCHANTCONFIG,method=RequestMethod.POST)
	@ResponseBody
	public int deleteSettleMerchantConfig(ServletRequest request){
		int result = 0;
		try{
			String settle_mer_code = request.getParameter("settle_mer_code");
			if(StringUtils.isNotBlank(settle_mer_code)){
				int deleteResult = settleMerchantMatchService.deleteSettleMerchantMatchBySettleMerCode(settle_mer_code);
				if(deleteResult > -1){
					result = settleMerchantConfigService.deleteSettleMerchantConfig(settle_mer_code);
				}
			}
		}catch(Exception e){
			log.error("删除结算商户配置信息时抛出异常:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 通过结算商户号查询结算商户配置个数
	 * @param request
	 * @return
	 */
	@RequestMapping(value=QUERYCONFIGCOUNTBYSETTLEMERCODE,method=RequestMethod.POST)
	@ResponseBody
	public int queryConfigCountBySettleMerCode(ServletRequest request){
		int count = 0;
		try{
			String settle_mer_code = request.getParameter("settle_mer_code");
			count = settleMerchantConfigService.queryConfigCountBySettleMerCode(settle_mer_code);
		}catch(Exception e){
			log.error("通过结算商户号查询结算商户配置个数时抛出异常:"+e.getMessage());
		}
		return count;
	}
	
	/**
	 * 查询结算商户号与电银商户号配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value=QUERYSETTLEMERCHANTMATCH,method=RequestMethod.POST)
	@ResponseBody
	public List<SettleMerchantMatch> querySettleMerchantMatch(ServletRequest request){
		List<SettleMerchantMatch> list = null;
		try{
			String settle_mer_code = request.getParameter("settle_mer_code");
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(settle_mer_code)){
				map.put("settle_mer_code", settle_mer_code);
				list = settleMerchantMatchService.querySettleMerchantMatch(map);
			}
		}catch(Exception e){
			log.error("查询结算商户号与电银商户号配置信息抛出异常:"+e.getMessage());
		}
		return list;
	}
	
	/**
	 * 查询结算商户号对应表中是否配置相应内部商户号
	 * @param request 内置对象(电银商户号 )
	 * @return
	 */
	@RequestMapping(value=QUERYSETTLEMERCHANTMATCHCOUNT,method=RequestMethod.POST)
	@ResponseBody
	public int querySettleMerchantMatchCount(ServletRequest request){
		int count = 0;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			String dy_mer_code = request.getParameter("mer_code");
			if(StringUtils.isNotBlank(dy_mer_code)){
				map.put("dy_mer_code", dy_mer_code);
				count = settleMerchantMatchService.querySettleMerchantMatchCount(map);
			}
		}catch(Exception e){
			log.error("通过内部商户号查询结算商户号对应表数据抛出异常:"+e.getMessage());
		}
		return count;
	}
	
	/**
	 * 添加结算商户号与电银商户号配置
	 * @param request
	 * @return
	 */
	@RequestMapping(value=ADDSETTLEMERCHANTMATCH,method=RequestMethod.POST)
	@ResponseBody
	public int addSettleMerchantMatch(HttpSession session,ServletRequest request){
		int result = 0;
		try {
			String merCode = request.getParameter("merCode");
			String settle_mer_code = request.getParameter("settle_mer_code");
			if(StringUtils.isNotBlank(settle_mer_code) && StringUtils.isNotBlank(merCode)){
				String[] merCodes = merCode.split(",");
				String[] mer_codes = null;
				Map<String,Object> map = new HashMap<String,Object>();
				for(int index=0; index<merCodes.length; index++){
					mer_codes = merCodes[index].split(":");
					if("1".equals(mer_codes[1])){//客户端新增数据
						if(StringUtils.isNotBlank(mer_codes[0])){
							map.put("dy_mer_code", mer_codes[0]);
							if(settleMerchantMatchService.querySettleMerchantMatchCount(map) == 0){//查询此电银商户号是否已配置过
								SettleMerchantMatch settleMerchantMatch = null;
								
								Login login = (Login)session.getAttribute("login");
								
								settleMerchantMatch = new SettleMerchantMatch();
								settleMerchantMatch.setDy_mer_code(mer_codes[0]);
								settleMerchantMatch.setSettle_mer_code(settle_mer_code);
								settleMerchantMatch.setId(UUID.randomUUID().toString().replaceAll("-", ""));
								settleMerchantMatch.setUser_id(login.getLoginName());
								result += settleMerchantMatchService.insertSettleMerchantMatch(settleMerchantMatch);
							}
						}
					}else if("3".equals(mer_codes[1])){
						if(StringUtils.isNotBlank(mer_codes[0]) && StringUtils.isNotBlank(mer_codes[2])){
							map.put("dy_mer_code_new", mer_codes[0]);
							map.put("dy_mer_code_old", mer_codes[2]);
							
							result += settleMerchantMatchService.updateDyMerCode(map);
							
//							if(settleMerchantMatchService.querySettleMerchantMatchCount(map) == 0){//查询此电银商户号是否已配置过
//								SettleMerchantMatch settleMerchantMatch = null;
//								
//								Login login = (Login)session.getAttribute("login");
//								
//								settleMerchantMatch = new SettleMerchantMatch();
//								settleMerchantMatch.setDy_mer_code(mer_codes[0]);
//								settleMerchantMatch.setSettle_mer_code(settle_mer_code);
//								settleMerchantMatch.setId(UUID.randomUUID().toString().replaceAll("-", ""));
//								settleMerchantMatch.setUser_id(login.getLoginName());
//								result += settleMerchantMatchService.insertSettleMerchantMatch(settleMerchantMatch);
//							}
						}
					}else if("2".equals(mer_codes[1])){//客户端删除数据
						settleMerchantMatchService.deleteSettleMerchantMatchBySettleMerCodeAndDyCode(settle_mer_code,mer_codes[0]);
					}
				}
			}
		} catch (Exception e) {
			log.error("添加结算商户对应数据抛出异常:"+e.getMessage());
		}
		return result;
	}
}
