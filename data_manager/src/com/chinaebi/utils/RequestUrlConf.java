package com.chinaebi.utils;

import java.util.ResourceBundle;

/**
 * 对账系统管理平台接口访问请求路径配置
 * @author Admin
 *
 */
public class RequestUrlConf {
	static ResourceBundle res = ResourceBundle.getBundle("application");
	public static final String url = res.getString("URL");
	public static final String pos_manager_url = res.getString("POS_MANAGER_URL");
}
