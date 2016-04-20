package com.chinaebi.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.ErrorHandling;
import com.chinaebi.service.ErrorHandlingService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 差错处理方式数据查询控制层
 * 
 * @author admin
 * 
 */
@Controller
public class ErrorHandlingController {
	
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	//
	private static final String ERROR_HANDLING = "sysConfig/error_handling";
	//访问路径
	private static final String QUERY_ALL = "getErrorHandlingLst.do";
	private static final String QUERY_UNI = "getErrorHandling.do";
	private static final String DELETE_ID = "deleteErrorHandlingLst.do";
	private static final String UPDATE_ID = "updateErrorHandlingLst.do";
	private static final String ADD_ID = "addErrorHandlingLst.do";
	
	private static final String ERROR_HANDLE_URL_JSON = "getErrorHandleMethodListJSON.do";
	
	// 实现依赖注入
	@Autowired
	@Qualifier(value = "errorHandlingService")
	private ErrorHandlingService errorHandlingService;
	
	
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryPageErrorHandling(ServletRequest request, Model model) {
		logger.info("获取差错处理方式数据");
		// 分页参数
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		//查询参数
		String id = request.getParameter("id");
		String handling_name = request.getParameter("handling_name");
		
		Page<ErrorHandling> page = new Page<ErrorHandling>();
		if (StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if (StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else
			page.setPageSize(50);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(id)){
			map.put("id", Integer.parseInt(id));
		}
		if(StringUtils.isNotBlank(handling_name)){
			map.put("handling_name", handling_name);
		}
		Page<ErrorHandling> page2 = errorHandlingService.queryPageErrorHandling(page, map);
		model.addAttribute("pageDataLst", page2);
		return ERROR_HANDLING;
	}
	
	@RequestMapping(value = DELETE_ID, method = RequestMethod.POST)
	public void deleteErrorHandling(ServletRequest request,ServletResponse response, Model model)throws Exception {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			boolean flag = errorHandlingService.deleteErrorHandling(Integer.valueOf(id));
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}else{
			logger.error("主键编号不能为空");
		}
	}
	
	@RequestMapping(value = UPDATE_ID, method = RequestMethod.POST)
	public void updateErrorHandling(ServletRequest request,ServletResponse response, Model model)throws Exception {
		String id = request.getParameter("id_update");
		String handling_name = request.getParameter("handling_name_update");
		if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(handling_name)){
			ErrorHandling errorHandling = new ErrorHandling();
			errorHandling.setId(Integer.valueOf(id));
			errorHandling.setHandling_name(handling_name);
			boolean flag = errorHandlingService.updateErrorHandling(errorHandling);
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}else{
			logger.error("主键编号和差错方式名称不能为空");
		}
	}
	
	@RequestMapping(value = ADD_ID, method = RequestMethod.POST)
	public void addErrorHandling(ServletRequest request,ServletResponse response, Model model)throws Exception {
		String handling_name = request.getParameter("handling_name_insert");
		if(StringUtils.isNotBlank(handling_name)){
			ErrorHandling errorHandling = new ErrorHandling();
			errorHandling.setHandling_name(handling_name);
			boolean flag = errorHandlingService.addErrorHandling(errorHandling);
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}else{
			logger.error("差错方式名称不能为空");
		}
	}
	
	/**
	 * 获取差错处理方式列表
	 * @return
	 */
	@RequestMapping(value = ERROR_HANDLE_URL_JSON, method = RequestMethod.POST)
	@ResponseBody
	public void getErrorHandleListJSON(ServletRequest request,ServletResponse response, Model model) {
		try {
			logger.info("获取差错处理方式列表数据");
			response.setCharacterEncoding("utf-8");
			List<ErrorHandling> errorHandleList = errorHandlingService.getErrorHandleList();
			StringBuffer buffer = new StringBuffer();
			buffer.append("[");
			for (ErrorHandling errorHandling : errorHandleList) {
				buffer.append("{");
					buffer.append("id : "+errorHandling.getId()+",");
					buffer.append("handling_name : '"+errorHandling.getHandling_name()+"'");
				buffer.append("},");
			}
			buffer.append("]");
			PrintWriter out = response.getWriter();
			logger.info(buffer.toString().replaceAll("},]", "}]"));
			out.print(buffer.toString().replaceAll("},]", "}]"));
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("获取差错处理方式列表出错：" + e.getMessage());
		}
	}
	@RequestMapping(value = QUERY_UNI, method = RequestMethod.POST)
	@ResponseBody
	public void getErrorHandleJSON(ServletRequest request,ServletResponse response, Model model)throws Exception {
		try {
			response.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				ErrorHandling errorHandling = errorHandlingService.getErrorHandle(Integer.valueOf(id));
				if(errorHandling != null){
					StringBuffer buffer = new StringBuffer();
					buffer.append("[");
						buffer.append("{id : "+errorHandling.getId()+",handling_name : '"+errorHandling.getHandling_name()+"'}");
					buffer.append("]");
					PrintWriter out = response.getWriter();
					logger.info(buffer.toString());
					out.print(buffer.toString());
					out.flush();
					out.close();
				}else
					logger.error(id + " ： 差错处理方式编号数据不存在");
			}else
				logger.error("差错处理方式编号不能为空");
		} catch (Exception e) {
			logger.error("获取差错处理方式数据出错：" + e.getMessage());
		}
		
	}
	
}
