package cn.com.chinaebi.dz.service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.dao.iface.HlogDAO;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.reload.Backstage;

public class TradeDzfList {
	
	private HlogDAO hlogDAO = cn.com.chinaebi.dz.object.dao.HlogDAO.getInstance();
	private Log logger = LogFactory.getLog(getClass());
	private static TradeDzfList tradeDzfList;
	
	//线程池
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 200,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10),
				new ThreadPoolExecutor.CallerRunsPolicy());
	
//	private List<String[]> dzfList = new ArrayList<String[]>();
	private static Queue<String[]> dzfList = new ConcurrentLinkedQueue<String[]>();
	private volatile boolean isRunnable = true;
	
	public static TradeDzfList getInstance(){
		if(tradeDzfList == null)
			tradeDzfList = new TradeDzfList();
		return tradeDzfList;
	}
	
	
	public synchronized boolean isRunnable(){
		return isRunnable;
	}
	
	public synchronized void setIsRunnable(boolean isRunnable){
		this.isRunnable = isRunnable;
	}
	
	public void run(){
		while(isRunnable()){
			if(isEmpty()){
				try {
					logger.info("dzfList 集合数据不存在");
					new Thread().sleep(5000);
				} catch (InterruptedException e) {
					logger.error(e);
				}
			}else{
				final String[] strArr = getPoll();
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						String sys_type = strArr[0];
						//sys_type : 0-线下、1-线上
						if(Integer.valueOf(sys_type) == 1){
							addRyfSave(strArr);
						}else{
							addPosSave(strArr);
						}
					}
				};
				executor.execute(runnable);
			}
		}
	}
	
	/**
	 * 线下数据保存
	 * @param strArr ：数据
	 * @return Object[]{tseq,boolean}
	 */
	public Object[] addPosSave(String[] strArr){
		Object[] returnObj = new Object[2];
		boolean save_flag = false;
		if(strArr != null){
			try {
				String trade_id = strArr[1];
				String req_sys_stance = strArr[2];
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return returnObj;
	}
	
	/**
	 * 线上数据保存
	 * @param strArr ：数据
	 * @return Object[]{tseq,boolean}
	 */
	public Object[] addRyfSave(String[] strArr){
		Object[] returnObj = new Object[2];
		boolean save_flag = false;
		if(strArr != null){
			try {
//				String sys_type = strArr[0];
				String tseq = strArr[1];
				String version = strArr[2];
				String mdate = strArr[3];
				String mid = strArr[4];
				String bid = strArr[5];
				String oid = strArr[6];
				String amount = strArr[7];
				String type = strArr[8];
				String gate = strArr[9];
				String author_type = strArr[10];
				String sys_date = strArr[11];
				String init_sys_date = strArr[12];
				String sys_time = strArr[13];
				String fee_amt = strArr[14];
				String bank_fee = strArr[15];
				String tstat = strArr[16];
				String mobile_no = strArr[17];
				String phone_no = strArr[18];
				String card_no = strArr[19];
				String gid = strArr[20];
				String pre_amt = strArr[21];
				String pre_amt1 = strArr[22];
				String bk_fee_model = strArr[23];
				String pay_amt = strArr[24];
				String currency = strArr[25];
				String exchange_rate = strArr[26];
				String out_user_id = strArr[27];
				String in_user_id = strArr[28];
				String bind_mid = strArr[29];
				returnObj[0] = tseq;
				//数据保存
				InstInfo instInfo = Backstage.getInstance().getInstInfo(new InstInfoPK(Integer.valueOf(gid), DataStatus.inst_type_1));
				BankInst bankInst = instInfo.getBank();
				MerBasic merBasic =  Backstage.getInstance().getMerBasic(mid);
				save_flag = hlogDAO.saveSynDzfTrade(tseq, version, mdate, mid, bid, oid, amount, type, gate, author_type, sys_date, 
						init_sys_date, sys_time, fee_amt, bank_fee, tstat, mobile_no, phone_no, card_no, bankInst, instInfo, 
						pre_amt, pre_amt1, bk_fee_model, pay_amt, currency, exchange_rate, out_user_id, in_user_id, bind_mid,merBasic);
				if(save_flag)
					logger.info(tseq+" : 同步保存成功  : "+save_flag);
				else
					logger.info(tseq+" : 同步保存失败  : "+save_flag);
			} catch (Exception e) {
				logger.error(e);
			}
		}else{
			logger.error("insert strArr 参数值不能为空");
		}
		returnObj[1] = save_flag;
		return returnObj;
	}
	
	/**
	 * 添加数据到-dzfList
	 * @param strArr
	 * @return boolean
	 */
	public static boolean addList(String[] strArr){
		return dzfList.add(strArr);
	}
	
	/**
	 * 判断此队列是否有元素 ，没有返回true
	 * @return
	 */
	public static boolean isEmpty(){
		return dzfList.isEmpty();
	}
	
	/**
	 * 获取并移除此队列的头 ，如果此队列为空，则返回 null
	 * @return
	 */
	public static String[] getPoll() {
		return dzfList.poll();
	}
	
	public static void main(String[] args) {
		TradeOkList.list.add("8");
	}
}
