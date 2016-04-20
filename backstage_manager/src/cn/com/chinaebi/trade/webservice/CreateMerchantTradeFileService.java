package cn.com.chinaebi.trade.webservice;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import cn.com.chinaebi.dz.object.ChannelTradeCollect;
import cn.com.chinaebi.dz.object.dao.ChannelTradeCollectDAO;
import cn.com.chinaebi.dz.object.util.PropertiesUtils;
import cn.com.chinaebi.dz.util.StringPingJie;

/**
 * 创建商户交易数据文件类
 * @author sp
 *
 */

public class CreateMerchantTradeFileService {
	
	private static Log log = LogFactory.getLog(CreateMerchantTradeFileService.class);
	private static cn.com.chinaebi.dz.object.dao.iface.ChannelTradeCollectDAO channelTradeCollectDao = ChannelTradeCollectDAO.getInstance();
	private static StringPingJie pingJie = StringPingJie.getInstance();

	/**
	 * 创建商户交易数据文件方法
	 * @param map	查询数据参数
	 * @param date	文件存放日期目录
	 * @param mid	电银商户号
	 * @return
	 */
	public static boolean createMerchantTradeFile(Map<String,String[]> map,String date,String date_hms,String mid,int today_or_history){
		boolean fileFlag = false;
		try{
			List<ChannelTradeCollect> dataList = null;//根据查询参数，查询商户交易数据
			
			if(today_or_history == 1){
				dataList = channelTradeCollectDao.getTodayChannelTradeCollectDataList(map);
			}else if(today_or_history == 2){
				dataList = channelTradeCollectDao.getHistoryChannelTradeCollectDataList(map);
			}else{
				log.debug("接口参数不正确，非当天或历史交易查询,请查看");
				return false;
			}
			
			
			String basePath = PropertiesUtils.readProperties("fileSavePath_local");//从配置文件中读取 本地文件存放路径
			
			if(StringUtils.isBlank(basePath)){//若读取失败，则默认为以下地址
				basePath = "/var/www/apps/java/data/shht";//默认地址
			}
			
			File f1 = new File(basePath);//判断基础路径是否存在,若不存在则创建
			if (!f1.exists()) {
				f1.mkdirs();
			}
			
			File f2 = new File(basePath + "/" + mid);//判断基础路径下商户文件夹是否存在,若不存在则创建
			if (!f2.exists()) {
				f2.mkdirs();
			}
			
			
			File f = new File(basePath + "/" + mid + "/" +date);//判断路径下日期文件夹是否存在,若不存在则创建
			if (!f.exists()) {
				f.mkdirs();
			}
			
			log.info(pingJie.getStringPingJie("开始创建excel文件,全路径为:",basePath , "/" , mid , "/" , date+ "/" , date_hms , "_TradeData.xls"));
			WritableWorkbook wbook = Workbook.createWorkbook(new File(basePath + "/" + mid + "/" + date
					+ "/" + date_hms + "_TradeData.xls"));
			
			WritableSheet wsheet = wbook.createSheet("交易数据", 0);//工作区间名称定义
			
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);// 设置excel里的字体
			
			WritableCellFormat titleFormat = new WritableCellFormat(wf);// 给标题规定字体的格式
			
			/*
			 * 创建列标题
			 */
			Label excelTitle = new Label(0, 0, "交易流水号",titleFormat);
			wsheet.addCell(excelTitle);
			Label excelTitle1 = new Label(1, 0, "订单号",titleFormat);
			wsheet.addCell(excelTitle1);
			Label excelTitle2 = new Label(2, 0, "交易金额",titleFormat);
			wsheet.addCell(excelTitle2);
			Label excelTitle3 = new Label(3, 0, "卡号",titleFormat);
			wsheet.addCell(excelTitle3);
			Label excelTitle4 = new Label(4, 0, "交易日期",titleFormat);
			wsheet.addCell(excelTitle4);
			Label excelTitle5 = new Label(5, 0, "手续费",titleFormat);
			wsheet.addCell(excelTitle5);
			Label excelTitle6 = new Label(6, 0, "交易结果",titleFormat);
			wsheet.addCell(excelTitle6);
			
			//写入数据
			if(dataList != null && dataList.size() > 0){
				int dataCount = 1;
				
				for (ChannelTradeCollect data : dataList) {
					
					Label excelTitle_tseq = new Label(0, dataCount, data.getDeductSysStance(),titleFormat);
					wsheet.addCell(excelTitle_tseq);
					Label excelTitle_oid = new Label(1, dataCount, data.getOid(),titleFormat);
					wsheet.addCell(excelTitle_oid);
					Label excelTitle_amt = new Label(2, dataCount, (data.getTradeAmount()/100)+"",titleFormat);
					wsheet.addCell(excelTitle_amt);
					Label excelTitle_cno = new Label(3, dataCount, data.getOutAccount(),titleFormat);
					wsheet.addCell(excelTitle_cno);
					Label excelTitle_sdate = new Label(4, dataCount, data.getTradeTime()+"",titleFormat);
					wsheet.addCell(excelTitle_sdate);
					Label excelTitle_mfee = new Label(5, dataCount, data.getMerFee()+"",titleFormat);
					wsheet.addCell(excelTitle_mfee);
					Label excelTitle_tstat = new Label(6, dataCount, data.getTradeResult()+"",titleFormat);
					wsheet.addCell(excelTitle_tstat);
					
					dataCount ++;
				}
			}else{
				log.debug(pingJie.getStringPingJie("根据请求参数查询数据为空"));
			}
			// 写入文件
			wbook.write();
			wbook.close();
			
			fileFlag = true;
			log.info("文件创建成功");
		}catch(Exception e){
			log.error(e);
		}
		return fileFlag;
	}
}
