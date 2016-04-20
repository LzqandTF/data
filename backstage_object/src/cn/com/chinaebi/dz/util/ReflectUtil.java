package cn.com.chinaebi.dz.util;

import java.lang.reflect.Method;
/**
 * 反射 指定类执行某个方法
 * @author zhu.hongliang
 *
 */
public class ReflectUtil {

	private Object[] obj;
	public ReflectUtil(Object...obj){
		this.obj = obj;
	}
	
	/**
	 * 放射类指定调用方法
	 * @param classType ：类
	 * @param methodName ：调用方法名
	 * @param classParamter ：参数类型class
	 * @return Object
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object execute(Class classType,String methodName,Class ... classParamter) throws Exception{
		Object invokeTester = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
		
		Method runMethod = classType.getMethod(methodName, classParamter);
		return runMethod.invoke(invokeTester, obj);
	}
	
	
	
	public static void main(String[] args) {
		String riqi = "20150831";
		ReflectUtil reflectUtil = new ReflectUtil(riqi);
		try {
			Class classType = Class.forName("cn.com.chinaebi.dz.util.DYDataUtil");
			Object object = reflectUtil.execute(classType, "getCurrentTime", String.class);
			System.out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
