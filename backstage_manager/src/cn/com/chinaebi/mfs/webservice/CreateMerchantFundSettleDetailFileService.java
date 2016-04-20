package cn.com.chinaebi.mfs.webservice;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.ChannelDzCollect;
import cn.com.chinaebi.dz.util.Ryt_trade_type;
import cn.com.chinaebi.dz.util.StringPingJie;

public class CreateMerchantFundSettleDetailFileService {
	private static Log log = LogFactory.getLog(CreateMerchantFundSettleDetailFileService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.ChannelDzCollectDAO channelDzCollectDAO = cn.com.chinaebi.dz.object.dao.ChannelDzCollectDAO.getInstance();
	
	private static StringPingJie pingJie = StringPingJie.getInstance();

	public static boolean createFileAndUpload(String merCode, String startDate, String endDate, String date,String date_hms){
		boolean fileFlag = false;
		try{
			List<ChannelDzCollect> dataList = channelDzCollectDAO.queryChannelDzCollectDataLst(merCode, startDate, endDate);
			
			String basePath = "/var/www/apps/java/data/jsdmx";
			
			File f1 = new File(basePath);
			if (!f1.exists()) {
				f1.mkdirs();
			}
			
			File f2 = new File(basePath + "/" + merCode);//判断基础路径下商户文件夹是否存在,若不存在则创建
			if (!f2.exists()) {
				f2.mkdirs();
			}
			
			File f = new File(basePath + "/" + merCode + "/" +date);//判断路径下日期文件夹是否存在,若不存在则创建
			if (!f.exists()) {
				f.mkdirs();
			}
			
			log.info("开始创建excel文件");
			log.info(pingJie.getStringPingJie("开始创建excel文件,全路径为:",basePath , "/" , merCode , "/" , date+ "/" , date_hms , "_jsdmx.xls"));
			WritableWorkbook wbook = Workbook.createWorkbook(new File(basePath + "/" + merCode + "/" + date
					+ "/" + date_hms + "_jsdmx.xls"));
			WritableSheet wsheet = wbook.createSheet("结算单明细", 0);
			// 设置excel里的字体
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);
			// 给标题规定字体的格式
			WritableCellFormat titleFormat = new WritableCellFormat(wf);
			
			Label excelTitle = new Label(0, 0, "商户号",titleFormat);
			wsheet.addCell(excelTitle);
			Label excelTitle1 = new Label(1, 0, "交易时间",titleFormat);
			wsheet.addCell(excelTitle1);
			Label excelTitle2 = new Label(2, 0, "商户订单号",titleFormat);
			wsheet.addCell(excelTitle2);
			Label excelTitle3 = new Label(3, 0, "商户日期",titleFormat);
			wsheet.addCell(excelTitle3);
			Label excelTitle4 = new Label(4, 0, "交易类型",titleFormat);
			wsheet.addCell(excelTitle4);
			Label excelTitle5 = new Label(5, 0, "交易金额",titleFormat);
			wsheet.addCell(excelTitle5);
			Label excelTitle6 = new Label(6, 0, "商户手续费",titleFormat);
			wsheet.addCell(excelTitle6);
			Label excelTitle7 = new Label(7, 0, "电银流水号",titleFormat);
			wsheet.addCell(excelTitle7);
			Label excelTitle8 = new Label(8, 0, "支付完成时间",titleFormat);
			wsheet.addCell(excelTitle8);
			Label excelTitle9 = new Label(9, 0, "支付渠道",titleFormat);
			wsheet.addCell(excelTitle9);
			Label excelTitle10 = new Label(10, 0, "网关号",titleFormat);
			wsheet.addCell(excelTitle10);
			
			if(dataList != null && dataList.size() > 0){
				int dataCount = 1;
				for (ChannelDzCollect channelDzCollect : dataList) {
					Label excelTitle_mid = new Label(0, dataCount, channelDzCollect.getReqMerCode(),titleFormat);
					wsheet.addCell(excelTitle_mid);
					Label excelTitle_sys_date = new Label(1, dataCount, channelDzCollect.getTradeTime().toString(),titleFormat);
					wsheet.addCell(excelTitle_sys_date);
					Label excelTitle_oid = new Label(2, dataCount, channelDzCollect.getOid(),titleFormat);
					wsheet.addCell(excelTitle_oid);
					Label excelTitle_mdate = new Label(3, dataCount, channelDzCollect.getDeductSysTime().toString(),titleFormat);
					wsheet.addCell(excelTitle_mdate);
					Label excelTitle_type = new Label(4, dataCount, Ryt_trade_type.getRytTradeName(channelDzCollect.getTradeType()),titleFormat);
					wsheet.addCell(excelTitle_type);
					Label excelTitle_amount = new Label(5, dataCount, String.format("%.2f", channelDzCollect.getTradeAmount()),titleFormat);
					wsheet.addCell(excelTitle_amount);
					Label excelTitle_mer_fee = new Label(6, dataCount,String.format("%.2f", channelDzCollect.getMerFee()),titleFormat);
					wsheet.addCell(excelTitle_mer_fee);
					Label excelTitle_tseq = new Label(7, dataCount,channelDzCollect.getReqSysStance(),titleFormat);
					wsheet.addCell(excelTitle_tseq);
					Label excelTitle_bk_date = new Label(8, dataCount,channelDzCollect.getDeductSysTime().toString(),titleFormat);
					wsheet.addCell(excelTitle_bk_date);
					Label excelTitle_gid = new Label(9, dataCount,channelDzCollect.getInstName(),titleFormat);
					wsheet.addCell(excelTitle_gid);
					Label excelTitle_gate = new Label(10, dataCount,channelDzCollect.getGate().toString(),titleFormat);
					wsheet.addCell(excelTitle_gate);
					dataCount ++;
				}
			}else{
				log.debug(pingJie.getStringPingJie("根据请求参数查询数据为空"));
			}
			// 写入文件
			wbook.write();
			wbook.close();
			fileFlag = true;
		}catch(Exception e){
			log.error(e);
		}
		return fileFlag;
	}
}
