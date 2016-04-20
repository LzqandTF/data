package com.chinaebi.action;

import java.util.ArrayList;
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

import com.chinaebi.entity.ReplaceValue;
import com.chinaebi.entity.RuleHandler;
import com.chinaebi.service.ObjectRelevanceColumnService;
import com.chinaebi.service.ReplaceValueService;
import com.chinaebi.service.RuleHandlerService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class RuleHandlerController {
	
	private static final Logger log = LoggerFactory.getLogger(RuleHandlerController.class);

	public static final String RULE_HANDLER = "/customDzFile/rule_handler";
	
	public static final String QUERYRULEHANDLER = "/queryRuleHandler.do";
	public static final String ADDRULEHANDLER = "/addRuleHandler.do";
	public static final String UPDATERULEHANDLER = "/updateRuleHandler.do";
	public static final String DELETERULEHANDLER = "/deleteRuleHandler.do";
	
	public static final String QUERYRULEHANDLERVALUELIST = "/queryRuleHandlerValueList.do";
	public static final String DELETEVALUEOFRULEHANDLER = "/deleteValueOfRuleHandler.do";
	public static final String QUERYRULEHANDLERLIST = "/queryRuleList.do";
	public static final String QUERYRULEHANDLERBYRULEID	 = "/queryRuleHandlerByRuleId.do";
	public static final String QUERYREPLACEVALUEBYRULEID = "/queryReplaceValueByRuleId.do";
	public static final String CHECKOLDVALUEODREPLACEVALUE = "/checkOldValueOfReplaceValue.do";
	public static final String UPDATEREPLACEVALUE = "/updateReplaceValue.do";
	@Autowired
	@Qualifier(value = "ruleHandlerService")
	private RuleHandlerService ruleHandlerService;
	
	@Autowired
	@Qualifier(value = "objectRelevanceColumnService")
	private ObjectRelevanceColumnService objectRelevanceColumnService;
	
	@Autowired
	@Qualifier(value = "replaceValueService")
	private ReplaceValueService replaceValueService;
	
	@RequestMapping(value=QUERYRULEHANDLER,method=RequestMethod.POST)
	public String queryRuleHandler(ServletRequest request,Model model) throws Exception{
		log.info("进入查询规则配置页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String rule_id = request.getParameter("rule_id_");
		String template_id = request.getParameter("template_id_");
		String attribute_column = request.getParameter("attribute_column_");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(template_id)){
			map.put("template_id", Integer.valueOf(template_id));
		}
		if(StringUtils.isNotBlank(rule_id)){
			map.put("rule_id", Integer.valueOf(rule_id.trim()));
		}
		if(StringUtils.isNotBlank(attribute_column)){
			map.put("attribute_column", attribute_column);
		}
		
		Page<RuleHandler> page = new Page<RuleHandler>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(20);
				
		model.addAttribute("pageRuleHandler",ruleHandlerService.queryPageRuleHandler(page,map));
		
		return RULE_HANDLER;
	}
	
	@RequestMapping(value = ADDRULEHANDLER,method=RequestMethod.POST)
	@ResponseBody
	public int addRuleHandler(ServletRequest request,RuleHandler ruleHandler) throws Exception{
		log.info("进入添加规则配置方法");
		
		int effectNum = ruleHandlerService.insertRuleHandler(ruleHandler);
		
		return effectNum;
	}
	
	
	@RequestMapping(value = UPDATERULEHANDLER,method=RequestMethod.POST)
	@ResponseBody
	public int updateRuleHandler(ServletRequest request,RuleHandler ruleHandler)throws Exception{
		log.info("进入修改规则配置方法");
		
		RuleHandler rHandler = ruleHandlerService.queryRuleHandlerById(ruleHandler.getRule_id());
		if(rHandler!=null){
			if(rHandler.getTemplate_id() != ruleHandler.getTemplate_id() || rHandler.getHandler_type() != ruleHandler.getHandler_type()){
				replaceValueService.deleteReplaceValueByRuleId(ruleHandler.getRule_id());
			}
		}
		
		int effectNum = ruleHandlerService.updateRuleHandler(ruleHandler);
		
		return effectNum;
	}
	
	
	@RequestMapping(value = UPDATEREPLACEVALUE,method=RequestMethod.POST)
	@ResponseBody
	public int updateReplaceValue(ServletRequest request,ReplaceValue replaceValue) throws Exception{
		log.info("进入修改值替换方法");
		int effectNum = 0;
		ReplaceValue value = replaceValueService.queryReplaceValueById(replaceValue.getId());
		if(value == null){
			effectNum = replaceValueService.insertRepalceValue(replaceValue);
		}else{
			effectNum = replaceValueService.updateRepalceValue(replaceValue);
		}
		return effectNum;
	}
	
	@RequestMapping(value = DELETERULEHANDLER,method=RequestMethod.POST)
	@ResponseBody
	public int deleteRuleHandler(ServletRequest request) throws Exception{
		log.info("进入删除规则配置方法");
		String rule_id = request.getParameter("rule_id");
		if(StringUtils.isBlank(rule_id)){
			log.error("传输参数-规则模板ID为NULL,删除操作失败");
			return 0;
		}
		List<ReplaceValue> list = replaceValueService.queryReplaceValueByRuleId(Integer.valueOf(rule_id));
		if(list != null && list.size() > 0){
			int flag_ = replaceValueService.deleteReplaceValueByRuleId(Integer.valueOf(rule_id));
			if(flag_ > 0){
				log.info("删除关联表值替换配置数据成功");
			}else{
				log.info("删除关联表值替换配置数据失败");
			}
		}
		int effectNum = ruleHandlerService.deleteRuleHandler(Integer.valueOf(rule_id));
		if(effectNum > 0){
			boolean flag = objectRelevanceColumnService.updateObjectRelevanceColumnByRuleId(Integer.valueOf(rule_id));
			if(flag){
				log.info("删除关联表自定义对账文件配置数据成功");
			}else{
				log.info("删除关联表自定义对账文件配置数据失败");
			}
			
		}
		return effectNum;
	}
	
	@RequestMapping(value = QUERYRULEHANDLERVALUELIST,method=RequestMethod.POST)
	@ResponseBody
	public String queryRuleHandlerValueList(ServletRequest request,Model model){
		String rule_id = request.getParameter("rule_id");
		String handler_type = request.getParameter("handler_type");
		String template_id = request.getParameter("template_id");
		List<ReplaceValue> replaceValues = replaceValueService.queryReplaceValueByRuleId(Integer.valueOf(rule_id));
		List<Map<String,Object>> list = null;
		Map<String,Object> temp = new HashMap<String,Object>();
		if(replaceValues == null || replaceValues.size() == 0){
			return null;
		}
			
		if((Integer.valueOf(handler_type)==1 && Integer.valueOf(template_id)==1 )|| Integer.valueOf(template_id)==2){
			list = new ArrayList<Map<String,Object>>();
			for(int i=0;i<replaceValues.size();i++){
				temp.put("old_value"+i,replaceValues.get(i).getOld_value());
				temp.put("new_value"+i,replaceValues.get(i).getNew_value());
				temp.put("rule_id",rule_id);
				temp.put("id"+i,replaceValues.get(i).getId());
				list.add(temp);
			}
		}else{
			return null;
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	@RequestMapping(value = DELETEVALUEOFRULEHANDLER,method=RequestMethod.POST)
	@ResponseBody
	public int deleteValueOfRuleHandler(ServletRequest request){
		String id = request.getParameter("id");
		int effectNum = 0;
		if(StringUtils.isNotBlank(id)){
			effectNum = replaceValueService.deleteRepalceValue(Integer.valueOf(id));
		}
		return effectNum;
	}
	
	@RequestMapping(value = QUERYRULEHANDLERLIST,method=RequestMethod.POST)
	@ResponseBody
	public List<RuleHandler> queryRuleHandlerList(ServletRequest request) throws Exception{
		String attribute_column = request.getParameter("attribute_column");
		List<RuleHandler> list = ruleHandlerService.queryRuleHandlerListByColumn(attribute_column);
		return list;
	}
	
	@RequestMapping(value = QUERYRULEHANDLERBYRULEID,method=RequestMethod.POST)
	@ResponseBody
	public RuleHandler queryRuleHandlerByRuleId(ServletRequest request){
		String rule_id = request.getParameter("rule_id");
		if(StringUtils.isNotBlank(rule_id)){
			RuleHandler ruleHandler = ruleHandlerService.queryRuleHandlerById(Integer.valueOf(rule_id));
			return ruleHandler;
		}
		return null;
	}

	@RequestMapping(value = QUERYREPLACEVALUEBYRULEID,method=RequestMethod.POST)
	@ResponseBody
	public ReplaceValue queryReplaceValueByRuleId(ServletRequest request){
		String rule_id = request.getParameter("rule_id");
		if(StringUtils.isNotBlank(rule_id)){
			List<ReplaceValue> list = replaceValueService.queryReplaceValueByRuleId(Integer.valueOf(rule_id));
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}
	
	@RequestMapping(value = CHECKOLDVALUEODREPLACEVALUE,method=RequestMethod.POST)
	@ResponseBody
	public int checkOldValueOfReplaceValue(ServletRequest request,ReplaceValue replaceValue){
		return replaceValueService.queryReplaceValueCountByRuleIdAndOldValue(replaceValue);
	}
	
}
