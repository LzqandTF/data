package com.chinaebi.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.chinaebi.entity.FeePoundage;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.InstMerRateConfig;
import com.chinaebi.entity.InstRate;
import com.chinaebi.entity.InstRateMccConfig;
import com.chinaebi.entity.Login;
import com.chinaebi.entity.MccBigType;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.FeePoundageService;
import com.chinaebi.service.InstMerRateConfigService;
import com.chinaebi.service.InstRateMccConfigService;
import com.chinaebi.service.InstRateService;
import com.chinaebi.service.MccBigTypeService;
import com.chinaebi.service.MccTypeService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class InstRateController {
	private static final Logger log = LoggerFactory.getLogger(InstRateController.class);
	
	@Autowired
	@Qualifier(value="instRateService")
	private InstRateService instRateService;
	
	@Autowired
	@Qualifier(value="instMerRateConfigService")
	private InstMerRateConfigService instMerRateConfigService;
	
	@Autowired
	@Qualifier(value="feePoundageService")
	private FeePoundageService feePoundageService;
	
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value="instRateMccConfigService")
	private InstRateMccConfigService instRateMccConfigService;
	
	@Autowired
	@Qualifier(value="mccBigTypeService")
	private MccBigTypeService mccBigTypeService;
	
	@Autowired
	@Qualifier(value="mccTypeService")
	private MccTypeService mccTypeService;
	
	//页面
	private static final String INST_RATE_JSP = "sysConfig/inst_rate";
	private static final String INST_MER_RATE_CONFIG_JSP = "sysConfig/inst_mer_rate_config";
	private static final String INST_RATE_MCC_TYPE_JSP = "sysConfig/inst_rate_mcc_type";
	private static final String FEE_POUNDAGE_JSP = "sysConfig/fee_poundage";
	
	//方法
	private static final String ADD_INST_RATE = "/addInstRate.do";
	private static final String UPDATE_INST_RATE = "/updateInstRate.do";
	private static final String DELETE_INST_RATE = "deleteInstRate.do";
	private static final String QUERY_PAGE_INST_RATE = "/queryPageInstRate.do";
	
	private static final String GET_OUTERDZ_INFOLIST_OF_NOT_IN_CONFIG = "/getOuterDzInfoListOfNotInConfig.do";
	private static final String QUERY_INST_MER_RATE_CONFIG = "/queryInstMerRateConfig.do";
	private static final String INSERTINSTMERRATECONFIG = "/insertInstMerRateConfig.do";
	private static final String DELETEINSTMERRATECONFIG = "/deleteInstMerRateConfig.do";
	private static final String CHECKINSTMERRATECONFIGEXISTORNOT = "/checkInstMerRateConfigExistOrNot.do";
	private static final String QUERYFEEPOUNDAGE = "/queryFeePoundage.do";
	
	/**
	 * 分页查询渠道费率配置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERY_PAGE_INST_RATE,method=RequestMethod.POST)
	public String queryPageInstRate(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String inst_name = request.getParameter("inst_name_select");
		String inst_rate_type = request.getParameter("instRate_type");
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(inst_name)){
			map.put("inst_name", inst_name);
		}
		if(StringUtils.isNotBlank(inst_rate_type)){
			map.put("inst_rate_type", inst_rate_type);
		}
		Page<InstRate> page = new Page<InstRate>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(20);
		model.addAttribute("pageDataLst", instRateService.queryPageInstRate(page, map));
		model.addAttribute("inst_rate_type",inst_rate_type);
		return INST_RATE_JSP;
	}
	
	/**
	 * 新增渠道费率配置
	 * @param request
	 * @param instRate
	 * @param session
	 * @return
	 */
	@RequestMapping(value=ADD_INST_RATE,method=RequestMethod.POST)
	@ResponseBody
	public int addInstRate(ServletRequest request,InstRate instRate,HttpSession session){
		int result = 0;
		try{
			Login login = (Login)session.getAttribute("login");
			instRate.setUser_name(login.getLoginName());
			result = instRateService.addInstRate(instRate);
		}catch(Exception e){
			log.error("新增渠道费率配置抛出异常"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value=UPDATE_INST_RATE,method=RequestMethod.POST)
	@ResponseBody
	public int updateInstRate(ServletRequest request,InstRate instRate,HttpSession session){
		int result = 0;
		try{
			Login login = (Login)session.getAttribute("login");
			instRate.setUser_name(login.getLoginName());
			result = instRateService.updateInstRate(instRate);
		}catch(Exception e){
			log.error("新增渠道费率配置抛出异常"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 删除渠道费率配置
	 * @param request
	 * @return
	 */
	@RequestMapping(value=DELETE_INST_RATE,method=RequestMethod.POST)
	@ResponseBody
	public int deleteInstRate(ServletRequest request){
		int result = 0;
		try{
			String inst_id = request.getParameter("inst_id");
			String inst_type = request.getParameter("inst_type");
			InstRateMccConfig instRateMccConfig = new InstRateMccConfig();
			Map<String,Object> map = new HashMap<String,Object>();
			if(StringUtils.isNotBlank(inst_id)){
				map.put("inst_id", inst_id);
				instRateMccConfig.setInst_id(Integer.valueOf(inst_id));
			}
			if(StringUtils.isNotBlank(inst_type)){
				map.put("inst_type", inst_type);
				instRateMccConfig.setInst_type(Integer.valueOf(inst_type));
			}
			
			result = instMerRateConfigService.deleteInstMerRateConfigByInstIdAndInstType(map);
			
			result += instRateMccConfigService.deleteInstRateMccConfig(instRateMccConfig);
			
			if(result >= 0){
				result = instRateService.deleteInstRate(map);
			}
		}catch(Exception e){
			log.error("删除渠道费率配置操作抛出异常"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询未配置费率的渠道信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value=GET_OUTERDZ_INFOLIST_OF_NOT_IN_CONFIG,method=RequestMethod.POST)
	@ResponseBody
	public List<InstInfo> getOuterDzInfoListOfNotInConfig(ServletRequest request){
		try {
			List<InstRate> list = instRateService.queryInstRateInstId();
			List<InstInfo> innerDuizhangInfoList = dataManagerInit.getOuterDzInfo(1);
			if(list != null){
				InstInfo instInfo = null;
				for(InstRate instRate : list){
					instInfo = dataManagerInit.getInstInfoById(instRate.getInst_id(), instRate.getInst_type());
					if(innerDuizhangInfoList.contains(instInfo)){
						innerDuizhangInfoList.remove(instInfo);
					}
				}
			}
			if (innerDuizhangInfoList.size() > 0) {
				return innerDuizhangInfoList;
			} else {
				log.info("getOuterDzInfoListOfNotInConfig() 获取需要外部对账的渠道为空...");
			}
		} catch (Exception e) {
			log.error("getOuterDzInfoListOfNotInConfig()获取需要外部对账的渠道为空" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 查询渠道商户费率配置信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERY_INST_MER_RATE_CONFIG,method=RequestMethod.POST)
	public String queryInstMerRateConfig(ServletRequest request,Model model){
		try{
			String inst_id = request.getParameter("inst_id");
			String inst_type = request.getParameter("inst_type");
			String inst_rate_type = request.getParameter("inst_rate_type");
			
			Map<String,Integer> map = new HashMap<String,Integer>();
			if(StringUtils.isNotBlank(inst_id)){
				map.put("inst_id", Integer.valueOf(inst_id));
			}
			if(StringUtils.isNotBlank(inst_type)){
				map.put("inst_type", Integer.valueOf(inst_type));
			}
			
			if(StringUtils.isNotBlank(inst_rate_type)){
				if(inst_rate_type.equals("1")){
					List<InstRateMccConfig> list = instRateMccConfigService.queryMccLiWaiConfig(map);
					for (InstRateMccConfig instRateMccConfig : list) {
						instRateMccConfig.setMcc_type_list(mccTypeService.queryMccTypeByParentId(instRateMccConfig.getMcc_b_type()));
					}
					model.addAttribute("list", list);
					List<MccBigType> mccBigTypeList = mccBigTypeService.queryAllMccBigType();
					model.addAttribute("mccBigTypeList", mccBigTypeList);
					return INST_RATE_MCC_TYPE_JSP;
				}else if(inst_rate_type.equals("3")){
					List<InstMerRateConfig> list = instMerRateConfigService.queryInstMerRateConfigInstByInstIdAndInstType(map);
					model.addAttribute("list", list);
					return INST_MER_RATE_CONFIG_JSP;
				}else{
					return null;
				}
			}
			return null;
		}catch(Exception e){
			log.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 新增渠道商户费率配置
	 * @param request
	 * @param instMerRateConfig
	 * @param session
	 * @return
	 */
	@RequestMapping(value=INSERTINSTMERRATECONFIG,method=RequestMethod.POST)
	@ResponseBody
	public int addInstMerRateConfig(ServletRequest request,InstMerRateConfig instMerRateConfig,HttpSession session){
		int result = 0;
		try{
			Login login = (Login)session.getAttribute("login");
			instMerRateConfig.setUser_name(login.getLoginName());
			result = instMerRateConfigService.addInstMerRateConfig(instMerRateConfig);
		}catch(Exception e){
			log.error("新增渠道商户费率配置信息抛出异常:"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 删除渠道商户费率配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value=DELETEINSTMERRATECONFIG,method=RequestMethod.POST)
	@ResponseBody
	public int deleteInstMerRateConfig(ServletRequest request,InstMerRateConfig instMerRateConfig){
		int result = 0;
		try{
			result = instMerRateConfigService.deleteInstMerRateConfig(instMerRateConfig);
		}catch(Exception e){
			log.error("删除渠道商户费率配置抛出异常:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value=CHECKINSTMERRATECONFIGEXISTORNOT,method=RequestMethod.POST)
	@ResponseBody
	public boolean checkInstMerRateConfigExistOrNot(ServletRequest request,InstMerRateConfig instMerRateConfig){
		boolean result = false;
		try{
			result = instMerRateConfigService.checkInstMerRateConfigExistOrNot(instMerRateConfig);
		}catch(Exception e){
			log.error("查询是否存在相同渠道商户费率配置信息操作抛出异常:"+e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value=QUERYFEEPOUNDAGE)
	public String queryFeePoundage(ServletRequest request,Model model){
		try{
			List<FeePoundage> list = feePoundageService.queryFeePoundage();
			model.addAttribute("dataList", list);
		}catch(Exception e){
			log.error("查询费率公式操作抛出异常:"+e.getMessage());
		}
		return FEE_POUNDAGE_JSP;
	}
}
