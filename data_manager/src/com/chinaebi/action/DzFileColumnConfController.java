package com.chinaebi.action;

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

import com.chinaebi.entity.DzFileColumnConf;
import com.chinaebi.service.DzFileColumnConfService;
import com.chinaebi.service.ObjectRelevanceColumnService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class DzFileColumnConfController {
	
	private static final Logger log = LoggerFactory.getLogger(DzFileColumnConfController.class);

	public static final String DZ_FILE_COLUMN_CONF = "/customDzFile/dz_file_column_conf";
	
	public static final String QUERYDZFILECOLUMNCONF = "/queryDzFileColumnConf.do";
	public static final String ADDDZFILECOLUMNCONF = "/addDzFileColumnConf.do";
	public static final String UPDATEDZFILECOLUMNCONF = "/updateDzFileColumnConf.do";
	public static final String DELETEDZFILECOLUMNCONF = "/deleteDzFileColumnConf.do";
	public static final String UNIONCHECKCOLUMNNAME = "/unionCheckDzColumnName.do";
	public static final String UNIONCHECKCOLUMNATTR = "/unionCheckDzColumnattr.do";
	public static final String LOADDZFILECOLUMNCONF = "/loadDzFileColumnConf.do";
	
	@Autowired
	@Qualifier(value = "dzFileColumnConfService")
	private DzFileColumnConfService dzFileColumnConfService;
	
	@Autowired
	@Qualifier(value = "objectRelevanceColumnService")
	private ObjectRelevanceColumnService objectRelevanceColumnService;
	
	@RequestMapping(value=QUERYDZFILECOLUMNCONF,method=RequestMethod.POST)
	public String queryDzFileColumnConf(ServletRequest request,Model model) throws Exception{
		log.info("进入查询对账字段配置页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String dz_column_id = request.getParameter("dz_column_id_");
		String attribute_name = request.getParameter("attribute_name_");
		String attribute_column = request.getParameter("attribute_column_");
		String attribute_type = request.getParameter("attribute_type_");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(dz_column_id)){
			map.put("dz_column_id", Integer.valueOf(dz_column_id));
		}
		if(StringUtils.isNotBlank(attribute_name)){
			map.put("attribute_name", attribute_name);
		}
		if(StringUtils.isNotBlank(attribute_column)){
			map.put("attribute_column", attribute_column);
		}
		if(StringUtils.isNotBlank(attribute_type)){
			map.put("attribute_type", Integer.valueOf(attribute_type));
		}
		
		Page<DzFileColumnConf> page = new Page<DzFileColumnConf>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		model.addAttribute("pageDzFileColumnConf",dzFileColumnConfService.queryPageDzFileColumnConf(page,map));
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("attribute_type_",attribute_type);
		return DZ_FILE_COLUMN_CONF;
	}
	
	
	@RequestMapping(value = UNIONCHECKCOLUMNNAME,method=RequestMethod.POST)
	public void unionCheckDzColumnName(ServletRequest request,ServletResponse response) throws Exception{
		
		String attribute_name = request.getParameter("attribute_name");
		
		if(StringUtils.isNotBlank(attribute_name)){
			DzFileColumnConf dzFileColumnConf = new DzFileColumnConf();
			dzFileColumnConf.setAttribute_name(attribute_name.trim());
			boolean flag = dzFileColumnConfService.unionCheckDzColumnName(dzFileColumnConf);
			if(flag){
				response.getWriter().print("true");
			}else{
				response.getWriter().print("false");
			}
		}else{
			response.getWriter().print("false");
		}
	}
	
	@RequestMapping(value = UNIONCHECKCOLUMNATTR,method=RequestMethod.POST)
	public void unionCheckDzColumnattr(ServletRequest request,ServletResponse response) throws Exception{
		
		String attribute_column = request.getParameter("attribute_column");
		
		if(StringUtils.isNotBlank(attribute_column)){
			DzFileColumnConf dzFileColumnConf = new DzFileColumnConf();
			dzFileColumnConf.setAttribute_column(attribute_column.trim());
			boolean flag = dzFileColumnConfService.unionCheckDzColumnattr(dzFileColumnConf);
			if(flag){
				response.getWriter().print("true");
			}else{
				response.getWriter().print("false");
			}
		}else{
			response.getWriter().print("false");
		}
	}
	
	
	@RequestMapping(value = ADDDZFILECOLUMNCONF,method=RequestMethod.POST)
	@ResponseBody
	public int addDzFileColumnConf(ServletRequest request,DzFileColumnConf dzFileColumnConf) throws Exception{
		log.info("进入添加对账字段方法");
		
		int effectNum = dzFileColumnConfService.insertDzFileColumnConf(dzFileColumnConf);
		
		return effectNum;
	}
	
	@RequestMapping(value = UPDATEDZFILECOLUMNCONF,method=RequestMethod.POST)
	@ResponseBody
	public int updateDzFileColumnConf(ServletRequest request,DzFileColumnConf dzFileColumnConf) throws Exception{
		log.info("进入修改对账字段方法");
		
		int effectNum = dzFileColumnConfService.updateDzFileColumnConf(dzFileColumnConf);
		
		return effectNum;
	}
	
	@RequestMapping(value = DELETEDZFILECOLUMNCONF,method=RequestMethod.POST)
	@ResponseBody
	public int deleteDzFileColumnConf(ServletRequest request) throws Exception{
		log.info("进入删除对账字段方法");
		int effectNum = 0;
		String dz_column_id = request.getParameter("dz_column_id");
		if(StringUtils.isBlank(dz_column_id)){
			log.error("传输参数-对账字段ID为NULL,删除操作失败");
			return 0;
		}
		boolean flag = objectRelevanceColumnService.deleteObjectRelevanceColumnCountByDzColumnId(Integer.valueOf(dz_column_id));
		if(flag){
			effectNum = dzFileColumnConfService.deleteDzFileColumnConf(Integer.valueOf(dz_column_id));
			log.info("删除关联表自定义对账文件配置数据成功");
		}else{
			log.info("删除关联表自定义对账文件配置数据失败");
		}
		return effectNum;
	}
	
	@RequestMapping(value = LOADDZFILECOLUMNCONF,method=RequestMethod.POST)
	@ResponseBody
	public List<DzFileColumnConf> loadDzFileColumnConf(ServletRequest request) throws Exception{
		String attribute_name = request.getParameter("dz_column_name");
		String file_type = request.getParameter("file_type");
		List<DzFileColumnConf> list = dzFileColumnConfService.queryDzFileColumnConfListByName(attribute_name,file_type);
		if(list!=null && list.size()>0){
			return list;
		}else{
			return null;
		}
		
	}
}
