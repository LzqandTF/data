package cn.com.chinaebi.dz.object.test;

import cn.com.chinaebi.dz.object.dao.HlogDAO;
import cn.com.chinaebi.dz.object.dao.OriginalBeijingbankLstDAO;
import cn.com.chinaebi.dz.object.dao.OriginalCupsLstDAO;
import cn.com.chinaebi.dz.object.dao.OriginalZhongxingbankLstDAO;
import cn.com.chinaebi.dz.object.dao.TradeLstDAO;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		TradeLstDAO trade = new TradeLstDAO();
//		trade.splitData("2014-03-20");
//		trade.handleMoney("2014-03-19","trade_lst");
		
//		OriginalBeijingbankLstDAO beijing = new OriginalBeijingbankLstDAO();
//		beijing.getBeijingBankData("2014-03-20");
//		
//		OriginalCupsLstDAO cups = new OriginalCupsLstDAO();
//		cups.getCupsData("2014-03-20");
//		
//		OriginalZhongxingbankLstDAO zhongxing = new OriginalZhongxingbankLstDAO();
//		zhongxing.getZhongxingBankData("2014-03-20");
		
		//测试线上容易付收款交易汇总
//		HlogDAO hloDao = new HlogDAO();
		//测试收款交易的数据汇总
//		hloDao.getRytHlog("2014-11-20", 55001);//23条数据
//		hloDao.getRytHlog("2014-12-08", 55001);//9条数据
//		hloDao.getRytHlog("2014-11-27", 55001);//16条数据
		
		//将hlog表中的收款交易数据汇总到ryt_upmp
//		hloDao.dataCollectHanler("2014-11-19", "ryt_upmp", 55001);//34条数据
//		hloDao.dataCollectHanler("2014-11-20", "ryt_upmp", 55001);//23条数据
		
		//测试线上容易付退款交易汇总
//		RytRefundLogDAO rytRefundLogDAO = new RytRefundLogDAO();
//		rytRefundLogDAO.getRytRefundLog("2014-12-01", 55001);//2条数据
//		rytRefundLogDAO.getRytRefundLog("2014-12-08", 55001);//2条数据
		
		//将refund_log表中的收款交易数据汇总到ryt_refund_log
//		rytRefundLogDAO.dataCollectHanler("2014-11-24", "ryt_refund_log", 55001);//11条数据
//		rytRefundLogDAO.dataCollectHanler("2014-12-08", "ryt_refund_log", 55001);//2条数据
	}

}

