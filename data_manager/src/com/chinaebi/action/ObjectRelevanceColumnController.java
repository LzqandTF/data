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

import com.chinaebi.entity.CustomObject;
import com.chinaebi.entity.DzFileColumnConf;
import com.chinaebi.entity.ObjectRelevanceColumn;
import com.chinaebi.entity.RuleHandler;
import com.chinaebi.service.CustomObjectService;
import com.chinaebi.service.DzFileColumnConfService;
import com.chinaebi.service.ObjectRelevanceColumnService;
import com.chinaebi.service.RuleHandlerService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class ObjectRelevanceColumnController {
	
	private static final Logger log = LoggerFactory.getLogger(ObjectRelevanceColumnController.class);

	public static final String OBJECT_RELEVANCE_COLUMN = "/customDzFile/object_relevance_column";
	
	public static final String QUERYOBJECTRELEVANCECOLUMN = "/queryObjectRelevanceColumn.do";
	public static final String ADDOBJECTRELEVANCECOLUMN = "/addObjectRelevanceColumn.do";
	public static final String UPDATEOBJECTRELEVANCECOLUMN = "/updateObjectRelevanceColumn.do";
	public static final String DELETEOBJECTRELEVANCECOLUMN = "/deleteObjectRelevanceColumn.do";
	public static final String QUERYOBJECTCOLUMNLIST = "/queryObjectColumnList.do";
	public static final String QUERYCUSTOMOBJECTBYFILETYPE = "/queryCustomObjectByFileType.do";
	
	@Autowired
	@Qualifier(value = "objectRelevanceColumnService")
	private ObjectRelevanceColumnService objectRelevanceColumnService;
	
	@Autowired
	@Qualifier(value = "dzFileColumnConfService")
	private DzFileColumnConfService dzFileColumnConfService;
	
	@Autowired
	@Qualifier(value = "ruleHandlerService")
	private RuleHandlerService ruleHandlerService;
	
	@Autowired
	@Qualifier(value = "customObjectService")
	private CustomObjectService customObjectService;
	
	@RequestMapping(value=QUERYOBJECTRELEVANCECOLUMN)
	public String queryObjectRelevanceColumn(ServletRequest request,Model model) throws Exception{
		log.info("进入查询自定义对账文件配置页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String object_id = request.getParameter("object_id");
		String file_type = request.getParameter("file_type");
		String file_category = request.getParameter("file_category");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(object_id)){
			map.put("object_id", Integer.valueOf(object_id));
		}
		if(StringUtils.isNotBlank(file_type)){
			map.put("file_type", Integer.valueOf(file_type));
		}
		
		Page<ObjectRelevanceColumn> page = new Page<ObjectRelevanceColumn>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		model.addAttribute("pageObjectRelevanceColumn",objectRelevanceColumnService.queryPageObjectRelevanceColumn(page,map));
		
		List<CustomObject> customObjectList = customObjectService.queryCustomObjectList();
		model.addAttribute("customObjectList", customObjectList);
		
		List<DzFileColumnConf> columnList = dzFileColumnConfService.queryDzFileColumnConfList();
		model.addAttribute("columnList", columnList);
		
		List<RuleHandler> ruleList = ruleHandlerService.queryRuleHandlerList();
		model.addAttribute("ruleList",ruleList);
		model.addAttribute("file_type",file_type);
		model.addAttribute("file_category",file_category);
		model.addAttribute("object_id",object_id);
		model.addAttribute("pageSize", pageSize);
		
		return OBJECT_RELEVANCE_COLUMN;
	}
	
	@RequestMapping(value = QUERYOBJECTCOLUMNLIST,method=RequestMethod.POST)
	@ResponseBody
	public void queryObjectColumnList(ServletRequest request,ServletResponse response,Model model) throws Exception{
		String object_id = request.getParameter("object_id");
		String file_type = request.getParameter("file_type");
		StringBuffer jsonStr = new StringBuffer();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		ObjectRelevanceColumn objectRelevanceColumn = null;
		model.addAttribute(object_id);
		if(StringUtils.isNotBlank(object_id) && StringUtils.isNotBlank(file_type)){
			objectRelevanceColumn = new ObjectRelevanceColumn();
			objectRelevanceColumn.setFile_type(Integer.valueOf(file_type));
			objectRelevanceColumn.setObject_id(Integer.valueOf(object_id));
			
			List<ObjectRelevanceColumn> list = objectRelevanceColumnService.queryObjectColumnList(objectRelevanceColumn);
			if(list!=null && list.size()>0){
				jsonStr.append("[");
				for (ObjectRelevanceColumn objectRelevanceColumn2 : list) {
					jsonStr.append("{id :" +objectRelevanceColumn2.getId()+",");
						jsonStr.append("object_id : "+objectRelevanceColumn2.getObject_id()+",");
						jsonStr.append("dz_column_id : "+objectRelevanceColumn2.getDz_column_id()+",");
						jsonStr.append("rule_id : "+objectRelevanceColumn2.getRule_id()+",");
						jsonStr.append("file_type : "+objectRelevanceColumn2.getFile_type()+",");
						jsonStr.append("attribute_column : '"+objectRelevanceColumn2.getAttribute_column()+"',");
						jsonStr.append("attribute_name : '"+objectRelevanceColumn2.getAttribute_name()+"',");
						jsonStr.append("show_attribute_name : '"+objectRelevanceColumn2.getShow_attribute_name()+"',");
						jsonStr.append("attribute_type : '"+objectRelevanceColumn2.getAttribute_type()+"'");
					jsonStr.append("},");
				}
				jsonStr.append("]");
				String jsonData = jsonStr.toString().replaceAll("},]", "}]");
				out.print(jsonData);
			}else{
				out.print("null");
			}
		}else{
			log.error("file_type = "+file_type +" and object_id = "+object_id+" 参数值不能为空");
			out.print("null");
		}
		
	}
	
	
	@RequestMapping(value = ADDOBJECTRELEVANCECOLUMN,method=RequestMethod.POST)
	@ResponseBody
	public int addObjectRelevanceColumn(ServletRequest request){
		log.info("进入添加自定义对账文件方法");
		int effectNum = 0;
		int sucessNum = 0;
		try {
			String object_id = request.getParameter("object_id");
			String file_type = request.getParameter("file_type");
			String selectColumn = request.getParameter("selectedColumn");
			String handler_type = request.getParameter("handler_type");
			String dz_column_id = request.getParameter("dz_column_id");
			
			//handler_type:处理类型 1：删除、2：添加、3：添加所有、4：删除所有、5：上移/下移
			if(StringUtils.isNotBlank(handler_type)){
				Map<String,Object> map = new HashMap<String, Object>();
				if(Integer.valueOf(handler_type) == 1){
					if(StringUtils.isNotBlank(dz_column_id)){
						String[] objStr = dz_column_id.split(",");
						for (String dzcolumnid : objStr) {
							String[] column_id = dzcolumnid.split("-");
							map.put("dz_column_id", Integer.valueOf(column_id[0]));
							map.put("object_id", Integer.valueOf(object_id));
							effectNum = objectRelevanceColumnService.deleteObjectOrDzColumnId(map);
							if(effectNum > 0){
								sucessNum ++;
							}
							map.clear();
						}
						
						if(sucessNum == objStr.length){
							return 1;
						}else{
							return 0;
						}
					}else{
						log.error("handler_type == 1 dz_column_id 属性值为null");
					}
				}else if(Integer.valueOf(handler_type) == 2){
					if(StringUtils.isNotBlank(dz_column_id)){
						String[] objStr = dz_column_id.split(",");
						ObjectRelevanceColumn column  = new ObjectRelevanceColumn();
						for (String dzcolumnid : objStr) {
							column.setFile_type(Integer.valueOf(file_type));
							column.setObject_id(Integer.valueOf(object_id));
							column.setRule_id(0);
							DzFileColumnConf dzFileColumnConf = dzFileColumnConfService.queryDzFileColumnConfById(Integer.valueOf(dzcolumnid));
							column.setShow_attribute_name(dzFileColumnConf.getAttribute_name());
							column.setDz_column_id(Integer.valueOf(dzcolumnid));
							effectNum = objectRelevanceColumnService.insertObjectRelevanceColumn(column);
							if(effectNum > 0){
								sucessNum ++;
							}
						}
					}else{
						log.error("handler_type == 2 dz_column_id 属性值为null");
					}
					
				}else {
					String[] dz_column_ids = null;
					ObjectRelevanceColumn column = new ObjectRelevanceColumn();;
					
					if(objectRelevanceColumnService.deleteObjectRelevanceColumnCountByObjectId(Integer.valueOf(object_id),Integer.valueOf(file_type))){
						log.info("根据系统id删除自定义对账文件配置数据成功");
						if(StringUtils.isNotBlank(selectColumn)){
							dz_column_ids = selectColumn.split(",");
							for(int i=0;i<dz_column_ids.length;i++){
								String[] id_rule = dz_column_ids[i].split("-");
								if(id_rule.length == 1){
									column.setRule_id(0);
								}else if(id_rule.length == 2){
									if(StringUtils.isNotBlank(id_rule[1])){
										column.setRule_id(Integer.valueOf(id_rule[1]));
									}else{
										column.setRule_id(0);
									}
								}else{
									column.setRule_id(0);
								}
								DzFileColumnConf dzFileColumnConf = dzFileColumnConfService.queryDzFileColumnConfById(Integer.valueOf(id_rule[0]));
								column.setFile_type(Integer.valueOf(file_type));
								column.setObject_id(Integer.valueOf(object_id));
								
								column.setShow_attribute_name(dzFileColumnConf.getAttribute_name());
								column.setDz_column_id(Integer.valueOf(id_rule[0]));
								effectNum = objectRelevanceColumnService.insertObjectRelevanceColumn(column);
								if(effectNum > 0){
									sucessNum ++;
								}
							}
							if(sucessNum == dz_column_ids.length){
								return 1;
							}else{
								return 0;
							}
						}else{
							return 1;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return effectNum;
	}
	
	@RequestMapping(value = UPDATEOBJECTRELEVANCECOLUMN,method=RequestMethod.POST)
	@ResponseBody
	public int updateObjectRelevanceColumn(ServletRequest request,ObjectRelevanceColumn ObjectRelevanceColumn) throws Exception{
		log.info("进入修改自定义对账文件方法");
		
		int effectNum = objectRelevanceColumnService.updateObjectRelevanceColumn(ObjectRelevanceColumn);
		
		return effectNum;
	}
	
	@RequestMapping(value=QUERYCUSTOMOBJECTBYFILETYPE,method=RequestMethod.POST)
	@ResponseBody
	public List<CustomObject> queryCustomObjectByFileType(ServletRequest request){
		String file_category = request.getParameter("file_category");
		List<CustomObject> list = null;
		if(StringUtils.isNotBlank(file_category)){
			list = customObjectService.queryCustomObjectByFileType(Integer.valueOf(file_category));
		}
		return list;
	}
	
}
