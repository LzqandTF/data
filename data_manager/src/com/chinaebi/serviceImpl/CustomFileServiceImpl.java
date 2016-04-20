package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.CustomFileDao;
import com.chinaebi.entity.CustomFileEntity;
import com.chinaebi.service.CustomFileService;
import com.chinaebi.utils.RegularExpressionUtil;

@Service(value="customFileService")
public class CustomFileServiceImpl implements CustomFileService{
	
	private static final Logger log =LoggerFactory.getLogger(CustomFileServiceImpl.class); 

	@Autowired
	@Qualifier(value = "customFileDao")
	private CustomFileDao customFileDao;
	
	@Override
	public List<CustomFileEntity> queryCustomFileEntityConfigInfo(Map<String,Object> map) {
		List<CustomFileEntity> resultList = null;
		try{
			resultList = customFileDao.queryCustomFileEntityConfigInfo(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return resultList;
	}

	@Override
	public List<CustomFileEntity> queryReplaceValue(Map<String, Object> map) {
		List<CustomFileEntity> resultList = null;
		try{
			resultList = customFileDao.queryReplaceValue(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return resultList;
	}
	/**
	 * 对文件内容进行规则化处理
	 * @param o 系统配置内容数组
	 * @param content 将要应用规则的字符串
	 * @return
	 */
	public String ruleHandleContent(CustomFileEntity customFileEntity, String content) {//o[5] 新值 o[6] 旧值
		//customDzFileInfoList遍历得到的数组中各位置对应的值意义为：
		//0-文件类型；1-显示名称；2-线下字段属性值；3-字段属性名称；
		//4-规则类型；5-新值；6-旧值；7-规则模板函数；
		//8-应用规则的字段属性值；9-字段长度；10-规则ID；11-线上字段属性值；
		if(StringUtils.isNotBlank(content)){
			Map<String,Object> map_parameter = new HashMap<String,Object>();
			if(customFileEntity != null){
				if (customFileEntity.getUser_rule_column() != null && StringUtils.isNotBlank(customFileEntity.getUser_rule_column())) {// 判断有没有应用规则的属性
					if (customFileEntity.getAttribute_column().equals(customFileEntity.getUser_rule_column())) {// 判断当前循环的属性是否为应用规则的属性
						if (customFileEntity.getTemplate_function() != null && StringUtils.isNotBlank(customFileEntity.getTemplate_function())) {
							map_parameter.put("rule_id", customFileEntity.getRule_id());
							List<CustomFileEntity> list = customFileDao.queryReplaceValue(map_parameter);
							if(list != null && list.size() > 0){
								if ("0".equals(customFileEntity.getTemplate_function())) {

								} else if ("replaceall".equalsIgnoreCase(customFileEntity.getTemplate_function())) {// 应用模板精确替换
									if (customFileEntity.getHandler_type() != 0) {
										if (customFileEntity.getHandler_type() == 0) {// 针对所有进行替换
											CustomFileEntity fileEntity = list.get(0);
											if(fileEntity.getNew_value() != null && StringUtils.isNotBlank(fileEntity.getNew_value())){
												content = fileEntity.getNew_value();
											}
										} else {
											Map<String,String> map = new HashMap<String,String>();
											for (Object object_m : list) {
												Object[] obj_m = (Object[])object_m;
												map.put(obj_m[1].toString(), obj_m[2].toString());
											}
											if(map != null){
												if (map.containsKey(content)) {
													content = map.get(content);
												}
											}
										}
									}
								} else if ("replaceLike".equalsIgnoreCase(customFileEntity.getTemplate_function())) {// 应用模板模糊查询后替换
									for (CustomFileEntity entity : list) {
										if(entity.getOle_value() != null && StringUtils.isNotBlank(entity.getOle_value())){
											int strLength = RegularExpressionUtil.complieString(entity.getOle_value(), "%");
											if (strLength == 1) {
												if (entity.getOle_value().endsWith("%")) {
													if (content.startsWith(entity.getOle_value().substring(0, entity.getOle_value().length()-1))) {
														content = entity.getNew_value();
													}
												} else if (entity.getOle_value().startsWith("%")) {
													if (content.endsWith(entity.getOle_value().substring(1, entity.getOle_value().length()))) {
														content = entity.getNew_value();
													}
												}
											} else {
												if (content.contains(entity.getOle_value().substring(1, entity.getOle_value().length()-1))) {
													content = entity.getNew_value();
												}
											}
										}
									}
								} else if ("subString".equalsIgnoreCase(customFileEntity.getTemplate_function())) {// 应用模板截取显示
									for (CustomFileEntity entity : list) {
										if(entity.getOle_value() != null && StringUtils.isNotBlank(entity.getOle_value())){
											content = content.substring(
													Integer.valueOf(entity.getOle_value()),
													Integer.valueOf(entity.getNew_value()));
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return content;
	}

}
