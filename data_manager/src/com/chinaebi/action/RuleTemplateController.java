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

import com.chinaebi.entity.RuleTemplate;
import com.chinaebi.service.RuleTemplateService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class RuleTemplateController {
	
	private static final Logger log = LoggerFactory.getLogger(RuleTemplateController.class);

	public static final String RULE_TEMPLATE = "/customDzFile/rule_template";
	
	public static final String QUERYRULETEMPLATE = "/queryRuleTemplate.do";
	public static final String ADDRULETEMPLATE = "/addRuleTemplate.do";
	public static final String UPDATERULETEMPLATE = "/updateRuleTemplate.do";
	public static final String DELETERULETEMPLATE = "/deleteRuleTemplate.do";
	public static final String QUERYRULETEMPLATELIST = "/queryRuleTemplateList.do";
	
	@Autowired
	@Qualifier(value = "ruleTemplateService")
	private RuleTemplateService ruleTemplateService;
	
	@RequestMapping(value=QUERYRULETEMPLATE,method=RequestMethod.POST)
	public String queryRuleTemplate(ServletRequest request,Model model) throws Exception{
		log.info("进入查询规则模板配置页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String template_id = request.getParameter("template_id_");
		String template_name = request.getParameter("template_name_");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(template_id)){
			map.put("template_id", Integer.valueOf(template_id));
		}
		if(StringUtils.isNotBlank(template_name)){
			map.put("template_name", template_name);
		}
		
		Page<RuleTemplate> page = new Page<RuleTemplate>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(20);
				
		model.addAttribute("pageRuleTemplate",ruleTemplateService.queryPageRuleTemplate(page,map));
		return RULE_TEMPLATE;
	}
	
	@RequestMapping(value = ADDRULETEMPLATE,method=RequestMethod.POST)
	@ResponseBody
	public int addRuleTemplate(ServletRequest request) throws Exception{
		log.info("进入添加规则模板方法");
		int effectNum = 0;
		String template_name = request.getParameter("template_name").trim();
		String template_function = request.getParameter("template_function").trim();
		String template_descripe = request.getParameter("template_descripe").trim();
		if(StringUtils.isNotBlank(template_name) && StringUtils.isNotBlank(template_function) && StringUtils.isNotBlank(template_descripe)){
			RuleTemplate ruleTemplate = new RuleTemplate();
			ruleTemplate.setTemplate_name(template_name);
			ruleTemplate.setTemplate_function(template_function);
			ruleTemplate.setTemplate_descripe(template_descripe);
			effectNum = ruleTemplateService.insertRuleTemplate(ruleTemplate);
		}
		return effectNum;
	}
	
	@RequestMapping(value = UPDATERULETEMPLATE,method=RequestMethod.POST)
	@ResponseBody
	public int updateRuleTemplate(ServletRequest request,RuleTemplate ruleTemplate) throws Exception{
		log.info("进入修改规则模板方法");
		
		int effectNum = ruleTemplateService.updateRuleTemplate(ruleTemplate);
		
		return effectNum;
	}
	
	@RequestMapping(value = DELETERULETEMPLATE,method=RequestMethod.POST)
	@ResponseBody
	public int deleteRuleTemplate(ServletRequest request) throws Exception{
		log.info("进入删除规则模板方法");
		String template_id = request.getParameter("template_id");
		if(StringUtils.isBlank(template_id)){
			log.error("传输参数-规则模板ID为NULL,删除操作失败");
			return 0;
		}
		int effectNum = ruleTemplateService.deleteRuleTemplate(Integer.valueOf(template_id));
		
		return effectNum;
	}
	
	@RequestMapping(value = QUERYRULETEMPLATELIST,method=RequestMethod.POST)
	@ResponseBody
	public List<RuleTemplate> queryRuleTemplateList(ServletRequest request){
		List<RuleTemplate> list = ruleTemplateService.queryRuleTemplateList();
		return list;
	}
}
