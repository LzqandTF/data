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

import com.chinaebi.entity.ReasonCode;
import com.chinaebi.service.ReasonCodeDaoService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 差错处理方式原因码控制层处理
 *
 */
@Controller
public class ReasonCodeController {

	private Logger logger = LoggerFactory.getLogger(getClass()); 
	//
	private static final String ERROR_HANDLING = "sysConfig/reasonCode";
	//访问路径
	private static final String QUERY_ALL = "getReasonCodeLst.do";
	private static final String DELETE_ID = "deleteReasonCode.do";
	private static final String UPDATE_ID = "updateReasonCode.do";
	private static final String ADD_ID = "addReasonCode.do";
	private static final String QUERY_ID ="getReasonCodeLstId.do";
	
	// 实现依赖注入
	@Autowired
	@Qualifier(value = "reasonCodeDaoService")
	private ReasonCodeDaoService reasonCodeDaoService;
		
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryPageReasonCode(ServletRequest request, Model model) {
		logger.info("获取差错处理方式原因码数据");
		// 分页参数
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		//查询参数
		String id = request.getParameter("id");
		String reason_id = request.getParameter("reason_id");
		String reason_desc = request.getParameter("reason_desc");
		
		Page<ReasonCode> page = new Page<ReasonCode>();
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
		if(StringUtils.isNotBlank(reason_id)){
			map.put("reason_id", reason_id);
		}
		if(StringUtils.isNotBlank(reason_desc)){
			map.put("reason_desc", reason_desc);
		}
		Page<ReasonCode> page2 = reasonCodeDaoService.queryPageReasonCode(page, map);
		model.addAttribute("pageDataLst", page2);
		return ERROR_HANDLING;
	}
	
	@RequestMapping(value = DELETE_ID, method = RequestMethod.POST)
	public void deleteReasonCode(ServletRequest request,ServletResponse response, Model model)throws Exception {
		String reason_id = request.getParameter("reason_id");
		if(StringUtils.isNotBlank(reason_id)){
			boolean flag = reasonCodeDaoService.deleteReasonCode(reason_id);
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}else{
			logger.error("差错处理方式原因码reason_id主键编号不能为空");
		}
	}
	
	@RequestMapping(value = UPDATE_ID, method = RequestMethod.POST)
	public void updateReasonCode(ServletRequest request,ServletResponse response, Model model)throws Exception {
		String id = request.getParameter("id_update");
		String reason_id = request.getParameter("reason_id_update");
		String reason_desc = request.getParameter("reason_desc_update");
		if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(reason_desc) && StringUtils.isNotBlank(reason_id)){
			ReasonCode reasonCode = new ReasonCode();
			reasonCode.setId(Integer.valueOf(id));
			reasonCode.setReason_id(reason_id);
			reasonCode.setReason_desc(reason_desc);
			boolean flag = reasonCodeDaoService.updateReasonCode(reasonCode);
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}else{
			logger.error("主键编号和差错方式名称不能为空");
		}
	}
	
	@RequestMapping(value = ADD_ID, method = RequestMethod.POST)
	public void addReasonCode(ServletRequest request,ServletResponse response, Model model)throws Exception {
		String id = request.getParameter("id_insert");
		String reason_id = request.getParameter("reason_id_insert");
		String reason_desc = request.getParameter("reason_desc_insert");
		logger.info(id +"--"+reason_id+"---"+reason_desc);
		if(StringUtils.isNotBlank(reason_desc) && StringUtils.isNotBlank(reason_id) && !StringUtils.equals(id, "0")){
			ReasonCode reasonCode = new ReasonCode();
			reasonCode.setId(Integer.valueOf(id));
			reasonCode.setReason_id(reason_id);
			reasonCode.setReason_desc(reason_desc);
			boolean flag = reasonCodeDaoService.addReasonCode(reasonCode);
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}else{
			logger.error("差错处理方式编号  or 差错处理方式原因码名称 or 差错处理方式不能为空");
		}
	}
	
	@RequestMapping(value = QUERY_ID, method = RequestMethod.POST)
	public void getReasonCodeLstId(ServletRequest request,ServletResponse response, Model model){
		logger.info("开始获取差错处理方式对应的原因码信息");
		String id = request.getParameter("id");
		response.setCharacterEncoding("utf-8");
		try {
			if(StringUtils.isNotBlank(id)){
				PrintWriter out = response.getWriter();
				ReasonCode idCode = new ReasonCode();
				idCode.setId(Integer.valueOf(id));
				List<ReasonCode> list = reasonCodeDaoService.getReasonCodeLstId(idCode);
				StringBuffer buffer = new StringBuffer();
				buffer.append("[");
				for (ReasonCode reasonCode : list) {
					buffer.append("{");
						buffer.append("reason_id : '"+reasonCode.getReason_id()+"',");
						buffer.append("reason_desc : '"+reasonCode.getReason_desc()+"',");
						buffer.append("id : "+reasonCode.getId());
					buffer.append("},");
				}
				buffer.append("]");
				String json = buffer.toString().replaceAll("},]", "}]");
				logger.info(json);
				out.print(json);
				out.flush();
				out.close();
			}else
				logger.error("差错处理方式编号不能为空");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
