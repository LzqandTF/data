package cn.com.chinaebi.dz.object.util;

import java.util.ResourceBundle;

/**
 * 对账系统管理平台接口访问请求路径配置
 * @author Admin
 *
 */
public class RequestUrlConf {
	static ResourceBundle res = ResourceBundle.getBundle("conf");
	public static final String url = res.getString("URL");
}
