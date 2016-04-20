package com.chinaebi.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.InnerErrorHandling;
import com.chinaebi.service.InnerErrorHandlingService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class InnerErrorHandlingController {
	private static final String ERROR_HANDLING = "sysConfig/inner_error_handling";
	//访问路径
	private static final String QUERY_ALL = "/getInnerErrorHandlingLst.do";
	private static final String DELETE_METHOD = "/deleteInnerErrorHandlingLst.do";
	private static final String UPDATE_METHOD = "/updateInnerErrorHandlingLst.do";
	private static final String ADD_METHOD = "/addInnerErrorHandlingLst.do";
	//获取内部差错处理方式
	private static final String FIND_ALL = "/findInnerErrorHandlingLst.do";
	
	@Autowired
	@Qualifier(value = "innerErrorHandlingService")
	private InnerErrorHandlingService innerErrorHandlingService;
	
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryPageInnerErrorHandling(ServletRequest request,Model model){
		// 分页参数
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		//查询参数
		String handling_id = request.getParameter("handling_id");
		String handling_name = request.getParameter("handling_name");
		Page<InnerErrorHandling> page = new Page<InnerErrorHandling>();
		if (StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if (StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else
			page.setPageSize(50);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(handling_id)){
			map.put("handling_id", Integer.parseInt(handling_id));
		}
		if(StringUtils.isNotBlank(handling_name)){
			map.put("handling_name", handling_name);
		}
		model.addAttribute("pageDataLst", innerErrorHandlingService.queryPageInnerErrorHandling(page, map));
		
		return ERROR_HANDLING;
	}
	
	@RequestMapping(value = DELETE_METHOD ,method = RequestMethod.POST)
	@ResponseBody
	public int deleteInnerErrorHandling(ServletRequest request){
		String handling_id = request.getParameter("id");
		int effectNum = innerErrorHandlingService.deleteErrorHandling(Integer.valueOf(handling_id));
		return effectNum;
	}
	
	@RequestMapping(value = UPDATE_METHOD ,method = RequestMethod.POST)
	@ResponseBody
	public int updateInnerErrorHandling(ServletRequest request,InnerErrorHandling innerErrorHandling){
		return innerErrorHandlingService.updateErrorHandling(innerErrorHandling);
	}
	
	@RequestMapping(value = ADD_METHOD ,method = RequestMethod.POST)
	@ResponseBody
	public int addInnerErrorHandling(ServletRequest request,InnerErrorHandling innerErrorHandling){
		return innerErrorHandlingService.addErrorHandling(innerErrorHandling);
	}
	
	@RequestMapping(value = FIND_ALL ,method = RequestMethod.POST)
	@ResponseBody
	public List<InnerErrorHandling> findInnerErrorHandling(ServletRequest request,Model model){
		List<InnerErrorHandling> list = innerErrorHandlingService.queryInnerErrorHandlingAll();
		return list;
	}
	
}
