package com.chinaebi.utils;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 * @author wufei
 *
 */
public class ReflectUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);
	/**
	 * 根据实体对象获取对应实体属性值
	 * @param obj : 实体对象
	 * @param fieldName ：属性名称
	 * @return Object
	 */
	public static Object getFieldValue(Object obj,String fieldName) throws Exception{
		Object objValue = null;
		try {
			if (obj != null && StringUtils.isNotBlank(fieldName)) {
				Class<? extends Object> cls = obj.getClass();
				Field f = cls.getDeclaredField(fieldName);
				f.setAccessible(true); //设置些属性是可以访问的  
				objValue = f.get(obj);//得到此属性的值  
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return objValue;
	}
}
