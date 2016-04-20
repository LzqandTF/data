package com.chinaebi.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinaebi.entity.ExecuteNode;
import com.chinaebi.service.ExecuteNodeService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class ExecuteNodeController {
	@Autowired
	@Qualifier(value="executeNodeService")
	private ExecuteNodeService executeNodeService;
	
	//页面
		public static final String EXECUTENODEINFO = "/executeNode/execute_node_info";
	//方法
		public static final String QUERYEXECUTENODE = "/queryExecuteNode.do";
		
	@RequestMapping(value=QUERYEXECUTENODE)
	public String queryExecuteNode(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String inst_info = request.getParameter("inst_info");
		String deduct_stml_date = request.getParameter("deduct_stml_date");
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(inst_info)){
			String[] instInfo = inst_info.split(",");
			map.put("deduct_sys_id",Integer.valueOf(instInfo[1]));
			map.put("inst_type",Integer.valueOf(instInfo[0]));
		}
		if(StringUtils.isNotBlank(deduct_stml_date)){
			map.put("deduct_stml_date", deduct_stml_date);
		}
		Page<ExecuteNode> page = new Page<ExecuteNode>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		
		model.addAttribute("pageData",executeNodeService.queryPage(page, map));
		model.addAttribute("inst_info", inst_info);
		return EXECUTENODEINFO;
	}
}
