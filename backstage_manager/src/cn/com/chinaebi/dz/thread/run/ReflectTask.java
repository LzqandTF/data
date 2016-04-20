package cn.com.chinaebi.dz.thread.run;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.thread.util.TaskTimeout;
import cn.com.chinaebi.dz.util.ReflectUtil;

@SuppressWarnings("rawtypes")
public class ReflectTask implements Callable{
	
	private static Log log = LogFactory.getLog(ReflectTask.class);
	
	private static ReflectTask reflectTask;
	private static ReflectUtil reflectUtil;
	private int timeout;
	private Class classType;
	private String methodName;
	private Class[] classParamter;
	
	public static ReflectTask getInstance(Object ... obj){
		reflectUtil = new ReflectUtil(obj);
		if(reflectTask == null) 
			reflectTask = new ReflectTask();
		return reflectTask;
	}
	
	/**
	 * 
	 * @param className ：类名
	 * @param methodName ：调用方法名
	 * @param timeout ：超时时间 - 按秒计算
	 * @param taskObj ：实现Callable类对象
	 * @param classParamter ：参数类class
	 * @return int
	 */
	public int run(String className,String methodName,int timeout,Object taskObj,Class ... classParamter){
		int taskResult = -1;
		try {
			this.classType = Class.forName(className);
			this.methodName = methodName;
			this.timeout = timeout;
			this.classParamter = classParamter;
			//执行超时线程任务
			ExecutorService exec = Executors.newCachedThreadPool(); 
			//运行call方法
			taskResult = TaskTimeout.testTask(exec, timeout, taskObj);
			exec.shutdown();
		} catch (Exception e) {
			log.error(e);
		}
		return taskResult;
	}

	@Override
	public Object call() throws Exception {
		Object obj = reflectUtil.execute(classType, methodName, classParamter);
		return obj;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("开始");
		String riqi = "2015-08-31";
		ReflectTask reflectTask = ReflectTask.getInstance(riqi);
		try {
			Object object = reflectTask.run("cn.com.chinaebi.dz.util.DYDataUtil", "getformatConversionDate3", 6,reflectTask, String.class);
			System.out.println("execute : "+object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("结束");
	}

}
