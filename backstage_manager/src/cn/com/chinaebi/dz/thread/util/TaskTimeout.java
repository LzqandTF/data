package cn.com.chinaebi.dz.thread.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TaskTimeout {
	
	private static Log log = LogFactory.getLog(TaskTimeout.class);
	
	@SuppressWarnings("unchecked")
	public static int testTask(ExecutorService exec, int timeout,Object task_obj) throws Exception{
		Callable<String> task = (Callable<String>)task_obj;
		Future<String> future = exec.submit(task);
		int taskResult = -1;
        try {  
            // 等待计算结果，最长等待timeout秒，timeout秒后中止任务  
            Object obj = future.get(timeout, TimeUnit.SECONDS); 
            log.info("运行结果 ： "+obj);
            if(obj != null){
            	taskResult = 0;
            }
        } catch (InterruptedException e) {  
        	log.error("主线程在等待计算结果时被中断!"+e); 
            taskResult = 1;
        } catch (ExecutionException e) {  
        	log.error("主线程等待计算结果，但计算抛出异常!"+e);  
        	e.printStackTrace();
        	taskResult = 2;
        } catch (TimeoutException e) {  
        	log.error("主线程等待计算结果超时，因此中断任务线程!"+e);  
        	taskResult = 3;
            exec.shutdownNow();  
        } catch (Exception e) {
        	log.error(e);
        	taskResult = 4;
        	throw e;
		}
        return taskResult;
	}
}