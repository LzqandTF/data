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

import com.chinaebi.entity.ChannelMccCalculate;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.MccBigType;
import com.chinaebi.entity.MccDiscount;
import com.chinaebi.entity.MccType;
import com.chinaebi.service.ChannelMccCalculateService;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.service.MccBigTypeService;
import com.chinaebi.service.MccDiscountService;
import com.chinaebi.service.MccTypeService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class MccController {
	@Autowired
	@Qualifier(value = "mccTypeService")
	private MccTypeService mccTypeService;
	
	@Autowired
	@Qualifier(value="mccBigTypeService")
	private MccBigTypeService mccBigTypeService;
	
	@Autowired
	@Qualifier(value="mccDiscountService")
	private MccDiscountService mccDiscountService;
	
	@Autowired
	@Qualifier(value="channelMccCalculateService")
	private ChannelMccCalculateService channelMccCalculateService;
	
	@Autowired
	@Qualifier(value="instInfoService")
	private InstInfoService instInfoService;
	
	private static Logger log = LoggerFactory.getLogger(MccController.class);
	
	//方法
	private static final String UPDATEMCCTYPE = "/updateMccType.do";
	private static final String ADDMCCTYPE = "/addMccType.do";
	private static final String DELETEMCCTYPE = "/deleteMccType.do";
	private static final String QUERYMCCTYPEPAGE = "/queryMccTypePage.do";
	
	private static final String UPDATEMCCBIGTYPE = "/updateMccBigType.do";
	private static final String ADDMCCBIGTYPE = "/addMccBigType.do";
	private static final String DELETEMCCBIGTYPE = "/deleteMccBigType.do";
	private static final String QUERYMCCBIGTYPEPAGE = "/queryMccBigTypePage.do";
	private static final String QUERYALLMCCBIGTYPE = "/queryAllMccBigType.do";
	
	private static final String UPDATEMCCDISCOUNT = "/updateMccDiscount.do";
	private static final String ADDMCCDISCOUNT = "/addMccDiscount.do";
	private static final String DELETEMCCDISCOUNT = "/deleteMccDiscount.do";
	private static final String QUERYMCCDISCOUNTPAGE = "/queryMccDiscountPage.do";
	
	private static final String UPDATECHANNELMCCCALCULATE = "/updateChannelMccCalculate.do";
	private static final String ADDCHANNELMCCCALCULATE = "/addChannelMccCalculate.do";
	private static final String DELETECHANNELMCCCALCULATE = "/deleteChannelMccCalculate.do";
	private static final String QUERYCHANNELMCCCALCULATEPAGE = "/queryChannelMccCalculatePage.do";
	private static final String CHECKINSTINFOOFMCCCALCULATE = "/checkInstInfoOfMccCalculate.do";
	
	private static final String QUERYALLMCCTYPEBYPARENTID = "/queryAllMccTypeByParentId.do";
	
	
	//页面
	private static final String MCCTYPEINFO = "/sysConfig/mcc_type";
	private static final String MCCBIGTYPEINFO = "/sysConfig/mcc_big_type";
	private static final String MCCDISCOUNT	 = "/sysConfig/mcc_discount";
	private static final String CHANNELMCCCALCULATE = "/sysConfig/channel_mcc_calculate";
	
	@RequestMapping(value=QUERYMCCBIGTYPEPAGE,method=RequestMethod.POST)
	public String queryMccBigTypePage(ServletRequest request,Model model) throws Exception{
		log.info("进入Mcc大类分页查询页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		String type_name = request.getParameter("type_name");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(type_name)){
			map.put("type_name",type_name);
		}
		
		Page<MccBigType> page = new Page<MccBigType>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		
		Page<MccBigType> pageMccBigType = mccBigTypeService.queryPageMccBigType(page, map);
		model.addAttribute("pageDataLst",pageMccBigType);
		model.addAttribute("pageSize",pageSize);
		return MCCBIGTYPEINFO;
	}
	
	@RequestMapping(value=UPDATEMCCBIGTYPE,method=RequestMethod.POST)
	@ResponseBody
	public int updateMccBigType(ServletRequest request,MccBigType mccBigType) throws Exception{
		return mccBigTypeService.updateMccBigType(mccBigType);
	}
	
	@RequestMapping(value=ADDMCCBIGTYPE,method=RequestMethod.POST)
	@ResponseBody
	public int addMccBigType(ServletRequest request,MccBigType mccBigType) throws Exception{
		return mccBigTypeService.addMccBigType(mccBigType);
	}
	
	@RequestMapping(value = DELETEMCCBIGTYPE,method = RequestMethod.POST)
	@ResponseBody
	public int deleteMccBigType(ServletRequest request) throws Exception{
		String big_type_id = request.getParameter("big_type_id");
		
		int result = 0;
		
		if(StringUtils.isNotBlank(big_type_id)){
			List<MccType> list = mccTypeService.queryMccTypeByParentId(Integer.valueOf(big_type_id));
			if(list != null && list.size() > 0){
				
				for (MccType mccType : list) {
					if(mccDiscountService.deleteMccDiscountByMccTypeId(mccType.getId()) >= 0){
						result += mccTypeService.deleteMccType(mccType.getId());
					}
				}
				
				if(result == list.size()){
					result = mccBigTypeService.deleteMccBigType(Integer.valueOf(big_type_id));
				}
			}else{
				result = mccBigTypeService.deleteMccBigType(Integer.valueOf(big_type_id));
			}
		}
		return result;
	}
	
	@RequestMapping(value=QUERYALLMCCBIGTYPE,method=RequestMethod.POST)
	@ResponseBody
	public List<MccBigType> queryAllMccBigType(){
		try{
			List<MccBigType> list = mccBigTypeService.queryAllMccBigType();
			return list;
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return null;
	}
	
	@RequestMapping(value=QUERYMCCTYPEPAGE,method=RequestMethod.POST)
	public String queryMccTypePage(ServletRequest request,Model model) throws Exception{
		log.info("进入Mcc类型分页查询页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		String type_name = request.getParameter("type_name");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(type_name)){
			map.put("type_name",type_name);
		}
		
		Page<MccType> page = new Page<MccType>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		
		Page<MccType> pageMccType = mccTypeService.queryPageMccType(page, map);
		model.addAttribute("pageDataLst",pageMccType);
		model.addAttribute("pageSize",pageSize);
		return MCCTYPEINFO;
	}
	
	@RequestMapping(value=UPDATEMCCTYPE,method=RequestMethod.POST)
	@ResponseBody
	public int updateMccType(ServletRequest request,MccType mccType) throws Exception{
		return mccTypeService.updateMccType(mccType);
	}
	
	@RequestMapping(value=ADDMCCTYPE,method=RequestMethod.POST)
	@ResponseBody
	public int addMccType(ServletRequest request,MccType mccType) throws Exception{
		return mccTypeService.addMccType(mccType);
	}
	
	@RequestMapping(value = DELETEMCCTYPE,method = RequestMethod.POST)
	@ResponseBody
	public int deleteMccType(ServletRequest request) throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			int result = mccDiscountService.deleteMccDiscountByMccTypeId(Integer.valueOf(id));
			if(result >= 0){
				return mccTypeService.deleteMccType(Integer.valueOf(id));
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	@RequestMapping(value=QUERYMCCDISCOUNTPAGE,method=RequestMethod.POST)
	public String queryPageMccDiscount(ServletRequest request,Model model) throws Exception{
		log.info("进入Mcc配置分页查询页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		String mcc_type = request.getParameter("mcc_type");
		String parent_id = request.getParameter("parent_id");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(mcc_type)){
			map.put("mcc_type",mcc_type);
		}
		if(StringUtils.isNotBlank(parent_id)){
			map.put("parent_id",Integer.valueOf(parent_id));
		}
		
		Page<MccDiscount> page = new Page<MccDiscount>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		
		Page<MccDiscount> pageMccDiscount = mccDiscountService.queryPageMccDiscount(page, map);
		model.addAttribute("pageDataLst",pageMccDiscount);
		
		List<MccType> MccTypeList = mccTypeService.queryAllMccType();
		model.addAttribute("mccTypeList", MccTypeList);
		model.addAttribute("pageSize", pageSize);
		
		return MCCDISCOUNT;
	}
	
	@RequestMapping(value=UPDATEMCCDISCOUNT,method=RequestMethod.POST)
	@ResponseBody
	public int updateMccDiscount(ServletRequest request,MccDiscount mccDiscount) throws Exception{
		return mccDiscountService.updateMccDiscount(mccDiscount);
	}
	
	@RequestMapping(value=ADDMCCDISCOUNT,method=RequestMethod.POST)
	@ResponseBody
	public int addMccDiscount(ServletRequest request,MccDiscount mccDiscount) throws Exception{
		return mccDiscountService.addMccDiscount(mccDiscount);
	}
	
	@RequestMapping(value=DELETEMCCDISCOUNT,method=RequestMethod.POST)
	@ResponseBody
	public int deleteMccDiscount(ServletRequest request) throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			return mccDiscountService.deleteMccDiscount(Integer.valueOf(id));
		}else{
			return 0;
		}
	}
	
	@RequestMapping(value=QUERYCHANNELMCCCALCULATEPAGE,method=RequestMethod.POST)
	public String queryChannelMccCalculatePage(ServletRequest request,Model model) throws Exception{
		log.info("进入渠道计算扣率方式分页查询页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		
		String inst_id = request.getParameter("inst_id_");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(inst_id)){
			map.put("inst_id",Integer.valueOf(inst_id));
		}
		
		Page<ChannelMccCalculate> page = new Page<ChannelMccCalculate>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		
		Page<ChannelMccCalculate> pageChannelMccCalculate = channelMccCalculateService.queryPageChannelMccCalculate(page, map);
		model.addAttribute("pageDataLst",pageChannelMccCalculate);
		
		List<InstInfo> list = instInfoService.queryAll();
		model.addAttribute("instInfoList",list);
		
		return CHANNELMCCCALCULATE;
	}
	
	@RequestMapping(value=UPDATECHANNELMCCCALCULATE,method=RequestMethod.POST)
	@ResponseBody
	public int updateChannelMccCalculate(ServletRequest request,ChannelMccCalculate channelMccCalculate) throws Exception{
		return channelMccCalculateService.updateChannelMccCalculate(channelMccCalculate);
	}
	
	@RequestMapping(value=ADDCHANNELMCCCALCULATE,method=RequestMethod.POST)
	@ResponseBody
	public int addChannelMccCalculate(ServletRequest request,ChannelMccCalculate channelMccCalculate) throws Exception{ 
		return channelMccCalculateService.addChannelMccCalculate(channelMccCalculate);
	}
	
	@RequestMapping(value=DELETECHANNELMCCCALCULATE,method=RequestMethod.POST)
	@ResponseBody
	public int deleteChannelMccCalculate(ServletRequest request) throws Exception{
		String inst_id = request.getParameter("inst_id");
		if(StringUtils.isNotBlank(inst_id)){
			return channelMccCalculateService.deleteChannelMccCalculate(Integer.valueOf(inst_id));
		}else{
			return 0;
		}
	}
	
	@RequestMapping(value=CHECKINSTINFOOFMCCCALCULATE,method=RequestMethod.POST)
	@ResponseBody
	public int checkInstInfoOfMccCalculate(ServletRequest request){
		String instId = request.getParameter("instId");
		if(StringUtils.isNotBlank(instId)){
			ChannelMccCalculate calculate = channelMccCalculateService.queryChannelMccCalculateById(Integer.valueOf(instId));
			if(calculate == null){
				return 1;
			}else{
				return 0;
			}
		}
		return 1;
	}
	
	@RequestMapping(value=QUERYALLMCCTYPEBYPARENTID,method=RequestMethod.POST)
	@ResponseBody
	public List<MccType> queryAllMccTypeByParentId(ServletRequest request){
		try{
			String big_type_id = request.getParameter("big_type_id");
			List<MccType> list = null;
			if(StringUtils.isNotBlank(big_type_id)){
				log.info("通过MCC大类ID"+big_type_id+"查找MCC细类信息");
				list = mccTypeService.queryMccTypeByParentId(Integer.valueOf(big_type_id));
				log.info("通过MCC大类ID"+big_type_id+"查找MCC细类信息:  查询结果 "+list.size() + "条");
			}
			return list;
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return null;
	}
	
}
