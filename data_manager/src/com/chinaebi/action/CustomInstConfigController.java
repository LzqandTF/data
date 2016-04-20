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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.CustomInstConfig;
import com.chinaebi.service.CustomInstConfigService;
import com.chinaebi.utils.StringUtils;

@Controller
public class CustomInstConfigController {
	private static final Logger log = LoggerFactory.getLogger(CustomInstConfigController.class);
	
	@Autowired
	@Qualifier(value="customInstConfigService")
	private CustomInstConfigService customInstConfigService;
	
	private static final String QUERYCUSTOMINSTINFOLIST = "/queryCustomObjectInstInfoList.do";
	
	
	@RequestMapping(value=QUERYCUSTOMINSTINFOLIST,method=RequestMethod.POST)
	@ResponseBody
	public List<CustomInstConfig> queryCustomInstConfig(ServletRequest request){
		List<CustomInstConfig> list = null;
		String object_id = request.getParameter("object_id");
		String dataSource = request.getParameter("dataSource");
		String inst_type = "";
		try {
			if(StringUtils.isNotBlank(object_id)){
				if(StringUtils.isNotBlank(dataSource)){
					if("1".equals(dataSource)){
						inst_type = "0";
					}else if("3".equals(dataSource)){
						inst_type = "1";
					}
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("object_id",object_id);
					map.put("inst_type",inst_type);
					list = customInstConfigService.queryCustomInstConfig(map);
				}else{
					list = customInstConfigService.queryCustomInstConfigByObjectId(Integer.valueOf(object_id));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list;
	}
}
