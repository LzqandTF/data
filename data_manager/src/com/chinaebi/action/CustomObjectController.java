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

import com.chinaebi.entity.CustomInstConfig;
import com.chinaebi.entity.CustomObject;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.CustomInstConfigService;
import com.chinaebi.service.CustomObjectService;
import com.chinaebi.service.DataInterfaceConfigService;
import com.chinaebi.service.ObjectRelevanceColumnService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class CustomObjectController {
	
	private static final Logger log = LoggerFactory.getLogger(CustomObjectController.class);
	/*
	 * 系统接口配置页面
	 */
	public static final String CUSTOM_OBJECT = "/customDzFile/custom_object";
	/*
	 * 分页查询系统接口配置信息
	 */
	public static final String QUERYCUSTOMOBJECT = "/queryCustomObject.do";
	/*
	 * 新增系统接口配置
	 */
	public static final String ADDCUSTOMOBJECT = "/addCustomObject.do";
	/*
	 * 修改系统接口配置
	 */
	public static final String UPDATECUSTOMOBJECT = "/updateCustomObject.do";
	/*
	 * 删除系统接口配置
	 */
	public static final String DELETECUSTOMOBJECT = "/deleteCustomObject.do";
	/*
	 * 检验系统接口名称是否重复
	 */
	public static final String UNIONCHECKOBJECTNAME = "/unionCheckobjectName.do";
	/*
	 * 检验系统接口所配置的文件路径是否重复
	 */
	public static final String UNIONCHECKFILEADDRESS = "/unionCheckfileAddress.do";
	/*
	 * 查询系统接口信息，返回List集合
	 */
	public static final String QUERYCUSTOMOBJECTLIST = "/queryCustomObjectList.do";
	/*
	 * 查询接口数据范围不是全部的系统接口配置信息
	 */
	public static final String QUERYCUSTOMOBJECTLISTOFINTERFACETYPENOTALL = "/queryCustomObjectListOfInterfaceTypeNotAll.do";
	/*
	 * 通过系统接口ID查询系统接口配置信息
	 */
	public static final String QUERYCUSTOMOBJECTBYID  = "/queryCustomObjectById.do";
	
	@Autowired
	@Qualifier(value = "customObjectService")
	private CustomObjectService customObjectService;
	
	@Autowired
	@Qualifier(value = "objectRelevanceColumnService")
	private ObjectRelevanceColumnService objectRelevanceColumnService;
	
	
	@Autowired
	@Qualifier(value = "dataInterfaceConfigService")
	private DataInterfaceConfigService dataInterfaceConfigService;
	
	@Autowired
	@Qualifier(value="customInstConfigService")
	private CustomInstConfigService customInstConfigService;
	
	//从内存中加载各种配置列表
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@RequestMapping(value=QUERYCUSTOMOBJECT,method=RequestMethod.POST)
	public String queryCustomObject(ServletRequest request,Model model) throws Exception{
		log.info("进入查询系统接口配置页面");
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String object_id = request.getParameter("object_id_");
		String object_name = request.getParameter("object_name_");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(object_id)){
			map.put("object_id", Integer.valueOf(object_id));
		}
		if(StringUtils.isNotBlank(object_name)){
			map.put("object_name", object_name);
		}
		
		Page<CustomObject> page = new Page<CustomObject>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(20);
				
		model.addAttribute("pageCustomObject",customObjectService.queryPageCustomObject(page,map));
		return CUSTOM_OBJECT;
	}
	
	@RequestMapping(value = UNIONCHECKOBJECTNAME,method=RequestMethod.POST)
	public void unionCheckobjectName(ServletRequest request,ServletResponse response) throws Exception{
		
		String object_name = request.getParameter("object_name");
		
		if(StringUtils.isNotBlank(object_name)){
			CustomObject customObject = new CustomObject();
			customObject.setObject_name(object_name.trim());
			boolean flag = customObjectService.unionCheckobjectName(customObject);
			if(flag){
				response.getWriter().print("true");
			}else{
				response.getWriter().print("false");
			}
		}else{
			response.getWriter().print("false");
		}
	}
	
	@RequestMapping(value = UNIONCHECKFILEADDRESS,method=RequestMethod.POST)
	public void unionCheckfileAddress(ServletRequest request,ServletResponse response) throws Exception{
		
		String file_address = request.getParameter("file_address");
		
		if(StringUtils.isNotBlank(file_address)){
			CustomObject customObject = new CustomObject();
			customObject.setFile_address(file_address.trim());
			boolean flag = customObjectService.unionCheckfileAddress(customObject);
			if(flag){
				response.getWriter().print("true");
			}else{
				response.getWriter().print("false");
			}
		}else{
			response.getWriter().print("false");
		}
	}
	
	@RequestMapping(value = ADDCUSTOMOBJECT,method=RequestMethod.POST)
	@ResponseBody
	public int addCustomObject(ServletRequest request,CustomObject customObject) throws Exception{
		log.info("进入添加系统接口方法");
		
		int effectNum = customObjectService.insertCustomObject(customObject);
		
		if(customObject.getFile_type() != 2){
			if(customObject.getWhether_create_file_by_inst() == 1){
				if(StringUtils.isNotBlank(customObject.getSelectedColumn())){
					CustomObject saveObject = customObjectService.queryCustomObjectByName(customObject.getObject_name());
					if(saveObject != null){
						int object_id = saveObject.getObject_id();
						String[] selectInstInfo = customObject.getSelectedColumn().split(",");
						if(selectInstInfo != null){
							int arrLength = selectInstInfo.length;
							CustomInstConfig customInstConfig = null;
							InstInfo instInfo = null;
							for(int i=0;i<arrLength;i++){
								instInfo = dataManagerInit.getInstInfoById(Integer.valueOf(selectInstInfo[i].split("-")[0]), Integer.valueOf(selectInstInfo[i].split("-")[1]));
								customInstConfig = new CustomInstConfig();
								customInstConfig.setObject_id(object_id);
								customInstConfig.setInst_id(Integer.valueOf(selectInstInfo[i].split("-")[0]));
								customInstConfig.setInst_type(Integer.valueOf(selectInstInfo[i].split("-")[1]));
								customInstConfig.setInst_name(instInfo.getName());
								customInstConfigService.insertCustomInstConfig(customInstConfig);
							}
						}
					}
				}
			}
		}
		
		return effectNum;
	}
	
	@RequestMapping(value = UPDATECUSTOMOBJECT,method=RequestMethod.POST)
	@ResponseBody
	public int updateCustomObject(ServletRequest request,CustomObject customObject){
		log.info("进入修改系统接口方法");
		int effectNum = 0;
		try{
			//获取修改前的接口数据范围
			int object_id = customObject.getObject_id();
			CustomObject customObject_ = customObjectService.queryCustomObjectById(object_id);
			
			//进行修改操作
			effectNum = customObjectService.updateCustomObject(customObject);
			//如果保存前后的接口数据范围不一致，则删除接口数据配置表中该系统配置的数据
			if(customObject_.getGenerate_number() != customObject.getGenerate_number()){
				log.info("保存前后的接口数据范围不一致，则删除接口数据配置表中该系统配置的数据");
				int delNum = dataInterfaceConfigService.deleteDataInterfaceConfigByObjectId(object_id);
				if(delNum < 0){
					log.info("删除"+customObject.getObject_name()+"系统对应的接口数据失败");
				}
			}
			
			if(customObject.getFile_type() != 2){
				customInstConfigService.deleteCustomInstConfig(object_id);
				if(customObject.getWhether_create_file_by_inst() == 1){
					if(StringUtils.isNotBlank(customObject.getSelectedColumn())){
						String[] selectInstInfo = customObject.getSelectedColumn().split(",");
						if(selectInstInfo != null){
							int arrLength = selectInstInfo.length;
							CustomInstConfig customInstConfig = null;
							InstInfo instInfo = null;
							for(int i=0;i<arrLength;i++){
								instInfo = dataManagerInit.getInstInfoById(Integer.valueOf(selectInstInfo[i].split("-")[0]), Integer.valueOf(selectInstInfo[i].split("-")[1]));
								if(instInfo != null){
									customInstConfig = new CustomInstConfig();
									customInstConfig.setObject_id(object_id);
									customInstConfig.setInst_id(Integer.valueOf(selectInstInfo[i].split("-")[0]));
									customInstConfig.setInst_type(Integer.valueOf(selectInstInfo[i].split("-")[1]));
									customInstConfig.setInst_name(instInfo.getName());
									customInstConfigService.insertCustomInstConfig(customInstConfig);
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			log.error("修改系统接口失败，抛出异常"+e.getMessage());
		}
		return effectNum;
	}
	
	@RequestMapping(value = DELETECUSTOMOBJECT,method=RequestMethod.POST)
	@ResponseBody
	public int deleteCustomObject(ServletRequest request) throws Exception{
		log.info("进入删除系统接口方法");
		int effectNum = 0;
		String object_id = request.getParameter("object_id");
		if(StringUtils.isBlank(object_id)){
			log.error("传输参数-系统接口ID为NULL,删除操作失败");
			return 0;
		}
		boolean flag = objectRelevanceColumnService.deleteObjectRelevanceColumnCountByObjectId(Integer.valueOf(object_id),0);
		int delNum = dataInterfaceConfigService.deleteDataInterfaceConfigByObjectId(Integer.valueOf(object_id));
		int delCustomInstConfig = customInstConfigService.deleteCustomInstConfig(Integer.valueOf(object_id));
		if(flag && delNum >= 0 && delCustomInstConfig >= 0){
			log.info("删除关联表自定义对账文件配置数据成功");
			effectNum = customObjectService.deleteCustomObject(Integer.valueOf(object_id));
		}else{
			log.info("删除关联表自定义对账文件配置数据失败");
		}
		return effectNum;
	}
	
	@RequestMapping(value = QUERYCUSTOMOBJECTLIST,method=RequestMethod.POST)
	@ResponseBody
	public List<CustomObject> queryCustomObjectList(){
		List<CustomObject> list = null;
		try{
			list = customObjectService.queryCustomObjectList();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	
	@RequestMapping(value = QUERYCUSTOMOBJECTLISTOFINTERFACETYPENOTALL,method=RequestMethod.POST)
	@ResponseBody
	public List<CustomObject> queryCustomObjectListOfInterfaceTypeNotAll(){
		List<CustomObject> list = null;
		try{
			list = customObjectService.queryCustomObjectListOfInterfaceTypeNotAll();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	
	@RequestMapping(value=QUERYCUSTOMOBJECTBYID,method=RequestMethod.POST)
	@ResponseBody
	public CustomObject	queryCustomObjectById(ServletRequest request){
		CustomObject customObject = null;
		try{
			String object_id = request.getParameter("object_id");
			if(StringUtils.isNotBlank(object_id)){
				customObject = customObjectService.queryCustomObjectById(Integer.valueOf(object_id));
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return customObject;
	}
	
}
