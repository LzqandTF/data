package cn.com.chinaebi.dz.object.util;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * properties配置文件读取工具类
 * @author zhu.hongliang
 */
public class PropertiesUtils {

	protected static Log log = LogFactory.getLog(PropertiesUtils.class);
	
	public static ResourceBundle res = ResourceBundle.getBundle("conf");
	
	/**
	 * 非实时读取,properties配置文件属性值 - 按属性名称读取-默认读取conf.properties文件
	 * @param propertiesName : 属性名称
	 * @return String
	 */
	public static String readProperties(String propertiesName){
		String value = null;
		if(StringUtils.isNotBlank(propertiesName) && res != null){
			value = res.getString(propertiesName);
		}
		return value;
	}
	
	/**
	 * 实时读取,properties配置文件属性值 - 按属性名称读取-默认读取conf.properties文件
	 * 
	 * @param propertiesName
	 *            ：属性名称
	 * @param fileName
	 *            ：文件名称-只加载properties文件
	 * @return String
	 */
	public static String rtReadProperties(String propertiesName, String fileName) {
		String value = null;
		try {
			if (StringUtils.isNotBlank(fileName)) {
				StringBuffer buffer = new StringBuffer(fileName);
				buffer.append(".properties");
				String path = PropertiesUtils.class.getClassLoader()
						.getResource(buffer.toString()).getPath();
				FileInputStream in = new FileInputStream(path);
				Properties properties = new Properties();
				properties.load(in);
				if (StringUtils.isNotBlank(propertiesName)) {
					value = properties.getProperty(propertiesName);
				}
			} else {
				if (StringUtils.isNotBlank(propertiesName)) {
					value = res.getString(propertiesName);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtils.readProperties("gid"));
		System.out.println(PropertiesUtils.rtReadProperties("gid","conf"));
	}
}
