package cn.com.chinaebi.dz.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 字符串处理
 * @author zhu.hongliang
 */
public class StringPingJie {
	
	private static Log log = LogFactory.getLog(StringPingJie.class);
	/**
	 * int corePoolSize : 线程池大小
	 * int maximumPoolSize ：线程池最大线程数
	 * long keepAliveTime ：线程没有任务执行时最多保持多久时间会终止
	 * TimeUnit unit ：参数keepAliveTime的时间单位
	 * TimeUnit.DAYS; //天 
	 * TimeUnit.HOURS; //小时 
	 * TimeUnit.MINUTES; //分钟 
	 * TimeUnit.SECONDS; //秒 
	 * TimeUnit.MILLISECONDS; //毫秒 
	 * TimeUnit.MICROSECONDS; //微妙 
	 * TimeUnit.NANOSECONDS; //纳秒
	 * BlockingQueue<Runnable> workQueue ：一个阻塞队列，用来存储等待执行的任务
	 * ArrayBlockingQueue
	 * LinkedBlockingQueue
	 * SynchronousQueue
	 */
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 500, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
	
	private static StringPingJie instance;
	
	public static StringPingJie getInstance () {
		if (null == instance) instance = new StringPingJie();
		return instance;
	}
	
	public void shutdown(){
		if(executor != null)executor.shutdown();
	}
	
	public String getStringPingJie(Object ...objects){
		LogUtil logUtil = new LogUtil(objects);
		Future<String> future = executor.submit(logUtil);
		String pjStr = null;
		try {
			pjStr = future.get(400, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {  
			log.error("主线程在等待计算结果时被中断!"+e); 
        } catch (ExecutionException e) {
        	log.error("主线程等待计算结果，但计算抛出异常!"+e);  
        } catch (TimeoutException e) { 
        	log.error("主线程等待计算结果超时，因此中断任务线程!"+e);
        } catch (Exception e) {
        	log.error(e);
		}
		return pjStr;
	}
	

	/**
	 * 字符串左填充
	 * @param stringValue ：数据
	 * @param size ：长度
	 * @param padString ：补充的内容
	 * @return String
	 */
	public static String leftPad(String stringValue,int size,String padString){
		size = size-stringValue.length();
		String padStrings = org.apache.commons.lang.StringUtils.repeat(padString, size);
		return org.apache.commons.lang.StringUtils.reverse(org.apache.commons.lang.StringUtils.reverse(stringValue)+padStrings);		
	}


	/**
	 * 字符串右填充
	 * @param stringValue ：数据
	 * @param size ：长度
	 * @param padString ：补充的内容
	 * @return String
	 */
	/**
	 * 字符串左填充
	 * @param stringValue ：数据
	 * @param size ：长度
	 * @param padString ：补充的内容
	 * @return String
	 */ 
	public static String rightPad(String stringValue,int size,String padString){
		size = size-stringValue.length();
		String padStrings = org.apache.commons.lang.StringUtils.repeat(padString, size);
		return stringValue+padStrings;
	}
	 
	
	public static void test(int i) throws Exception{
		StringPingJie stringPingJie = StringPingJie.getInstance();
			String str = stringPingJie.getStringPingJie("字","符","串");
			System.out.println("String : "+str+":"+i);
			System.out.println("线程池中线程数目："+stringPingJie.executor.getPoolSize()+"，队列中等待执行的任务数目："+
					stringPingJie.executor.getQueue().size()+"，已执行玩别的任务数目："+stringPingJie.executor.getCompletedTaskCount());
	}
	 
	 public static void main(String[] args) throws Exception{
//		System.out.println(rightPad("20110825", 14, "0"));
//		System.out.println(leftPad("20110825", 14, "0"));
		 /**
		  * 主线程会等待
		  
		long start = System.currentTimeMillis();
		// 开始的倒数锁
		final CountDownLatch begin = new CountDownLatch(1);
		// 结束的倒数锁
		final CountDownLatch end = new CountDownLatch(10000);
        
		ExecutorService executor = Executors.newFixedThreadPool(10);  
		for (int index = 0; index < 30; index++) {
			final int NO = index + 1;
			Runnable run = new Runnable() {
				public void run() {
					try {
						begin.await();
						Thread.sleep((long) (Math.random() * 10000));  
						System.out.println("No." + NO + " arrived");  
					} catch (InterruptedException e) {
					} finally {
						end.countDown();
					}
				}
			};
			executor.submit(run);
		}
		System.out.println("Game Start");
		begin.countDown();
		end.await();
		System.out.println("Game Over");
		executor.shutdown();
		System.out.print("共计用时 ");
		System.out.println(System.currentTimeMillis() - start);*/
		
		
		
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.HOURS, 
//				new ArrayBlockingQueue<Runnable>(5), 
//        		new ThreadPoolExecutor.CallerRunsPolicy());
		 
        //对于整个比赛，所有运动员结束后才算结束
        final CountDownLatch end = new CountDownLatch(30);
        long start = System.currentTimeMillis();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
		            new LinkedBlockingQueue<Runnable>(5),new ThreadPoolExecutor.CallerRunsPolicy());
		for (int index = 0; index < 30; index++) {//循环及比赛开始
			final int NO = index + 1;
			System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
					executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
			Runnable run = new Runnable() {
				public void run() {
					try {
						Thread.sleep(30000);  
						System.out.println("No." + NO + " arrived");  
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally{
						end.countDown();
					}
				}
			};
			executor.submit(run);
		}
		try{
            end.await();            //等待end状态变为0，即为比赛结束
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            System.out.println("Race ends!");
        }
		executor.shutdown();
		System.out.println("花费时间:"+(start - System.currentTimeMillis()));
		System.out.println("执行完毕");
	 }
}
