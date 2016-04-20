package cn.com.chinaebi.dz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.dao.iface.HlogDAO;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.reload.Backstage;

public class TradeOkList{

	private HlogDAO hlogDAO = cn.com.chinaebi.dz.object.dao.HlogDAO.getInstance();
	private Log logger = LogFactory.getLog(getClass());
	private static TradeOkList tradeOkList;
	
	//线程池
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 200,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10),
				new ThreadPoolExecutor.CallerRunsPolicy());
	
	//private volatile List<String[]> okList = new ArrayList<String[]>();
	private static Queue<String[]> okList = new ConcurrentLinkedQueue<String[]>();
	private volatile boolean isRunnable = true;
	
	public synchronized boolean isRunnable(){
		return isRunnable;
	}
	
	public synchronized void setIsRunnable(boolean isRunnable){
		this.isRunnable = isRunnable;
	}
	
	public static TradeOkList getInstance(){
		if(tradeOkList == null)
			tradeOkList = new TradeOkList();
		return tradeOkList;
	}
	
	public void run(){
		while(isRunnable()){
			if(isEmpty()){
				try {
					new Thread().sleep(5000);
				} catch (InterruptedException e) {
					logger.info("sleep : "+e);
				}
				logger.info("okList 集合数据不存在");
			}else{
				final String[] strArr = getPoll();
				Runnable runnable = new Runnable() {
					public void run() {
						List<Object> objList = update(strArr);
						Object flag = objList.get(0);
						if(!Boolean.valueOf(flag.toString())){
							addList((String[])objList.get(1));
						}
					}
				};
				executor.submit(runnable);
			}
		}
	}
	
	/**
	 * 数据更新
	 * @param strArr ：数据
	 * @return List<Object>{tseq,boolean,String[]}
	 */
	public List<Object> update(String[] obj){
		List<Object> list = new ArrayList<Object>();
		boolean update_flag = false;
		if(obj != null){
			try {
				String sys_type = obj[0];
				String tseq = obj[1];
				String gid = obj[2];
				String tstat = obj[3];
				String bk_flag = obj[4];
				String bk_date = obj[5];
				String bk_seq1 = obj[6];
				String bk_seq2 = obj[7];
				String bk_resp = obj[8];
				String error_msg = obj[9];
				if(Integer.valueOf(sys_type) == 1){
					InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(Integer.valueOf(gid), DataStatus.inst_type_1));
					BankInst bankInst = instInfo.getBank();
					int count = hlogDAO.queryOringinalTableCount(Long.valueOf(tseq), bankInst.getOriginalDataTableName());
					if(count > 0){
						update_flag = hlogDAO.updateSynOkTrade(tseq, gid, tstat, bk_flag, bk_date, bk_seq1, bk_seq2, bk_resp, error_msg, bankInst);
						list.add(update_flag);
						if(update_flag)
							logger.info(tseq+" : 同步完成交易更新成功  : "+update_flag);
						else{
							logger.info(tseq+" : 同步完成交易更新失败  : "+update_flag);
							list.add(obj);
						}
					}else{
						logger.warn(tseq+" : "+bankInst.getOriginalDataTableName()+" 表中数据不存在");
						list.add(update_flag);
						list.add(obj);
					}
				}else{
					//TODO 更新线下交易
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}else{
			logger.error("update obj参数值不能为空");
		}
		return list;
	}
	
	
	/**
	 * 添加数据到-okList
	 * @param strArr
	 * @return boolean
	 */
	public static boolean addList(String[] strArr){
		return okList.add(strArr);
	}
	
	/**
	 * 判断此队列是否有元素 ，没有返回true
	 * @return
	 */
	public static boolean isEmpty(){
		return okList.isEmpty();
	}
	
	/**
	 * 获取并移除此队列的头 ，如果此队列为空，则返回 null
	 * @return
	 */
	public static String[] getPoll() {
		return okList.poll();
	}
	
	public static List<String> list = new ArrayList<String>();
	public static void main(String[] args) {
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 200,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10),
				new ThreadPoolExecutor.CallerRunsPolicy());
		while(true){
			//final String value = list.get(i);
			boolean flag = list.size() > 0;
			final String value = flag ? list.iterator().next() : null;
			if(StringUtils.isNotBlank(value)){
				list.remove(value);
				Runnable runnable = new Runnable() {
					public void run() {
						boolean flag = update(value);
						if(!flag){
							list.add(value);
						}
					}
				};
				executor.submit(runnable);
			}else{
				try {
					new Thread().sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("list size : "+flag);
			}
		}
	}
	
	
	public static boolean  update(String value){
		System.out.println("value : "+value);
		if(Integer.valueOf(value)%2 == 0){
			return true;
		}
		return true;
	}
}
