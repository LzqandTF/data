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

import com.chinaebi.entity.CustomObject;
import com.chinaebi.entity.DzFileInfo;
import com.chinaebi.service.CustomObjectService;
import com.chinaebi.service.DzFileInfoService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class DzFileInfoController {
	// 记录查询时的日志
	private Logger log = LoggerFactory.getLogger(DzFileInfoController.class);
	//请求
	private static final String QUERYPAGEDZFILEINFO	= "/queryPageDzFileInfo.do";
	//页面
	private static final String DZFILEINFO = "/duizhangFileUpload/dz_file_upload";
	
	@Autowired
	@Qualifier(value = "dzFileInfoService")
	private DzFileInfoService dzFileInfoService;
	
	@Autowired
	@Qualifier(value = "customObjectService")
	private CustomObjectService customObjectService;
	
	@RequestMapping(value=QUERYPAGEDZFILEINFO)
	public String queryPageDzFileInfo(ServletRequest request,Model model){
		log.info("进入对账文件分页查询页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		
		String deduct_sys_date = request.getParameter("deduct_sys_date");
		String object_id = request.getParameter("object_id");
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(deduct_sys_date)){
			map.put("deduct_sys_date",deduct_sys_date);
		}
		if(StringUtils.isNotBlank(object_id)){
			map.put("object_id",Integer.valueOf(object_id));
		}
		
		Page<DzFileInfo> page = new Page<DzFileInfo>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		
		Page<DzFileInfo> pageDzFile = dzFileInfoService.queryPageDzFileInfo(page, map);
		model.addAttribute("pageDzFile",pageDzFile);
		
		List<CustomObject> customObjectList = customObjectService.queryCustomObjectList();
		model.addAttribute("customObjectList", customObjectList);
		
		model.addAttribute("object_id",object_id);
		
		return DZFILEINFO;
	}
}
