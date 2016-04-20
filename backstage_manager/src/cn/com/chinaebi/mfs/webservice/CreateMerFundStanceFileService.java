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

import cn.com.chinaebi.dz.object.MerFundStance;
import cn.com.chinaebi.dz.util.StringPingJie;

public class CreateMerFundStanceFileService {
	private static Log log = LogFactory.getLog(CreateMerFundStanceFileService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.MerFundStanceDAO merFundStanceDAO = cn.com.chinaebi.dz.object.dao.MerFundStanceDAO.getInstance();
	
	private static StringPingJie pingJie = StringPingJie.getInstance();

	public static boolean createFileAndUpload(String merCode, String startDate, String endDate,String date,String date_hms){
		boolean fileFlag = false;
		try{
			List<MerFundStance> dataList = merFundStanceDAO.queryMerFundStanceDataLst(merCode, startDate, endDate);
			
			String basePath = "/var/www/apps/java/data/shls";
			
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
			log.info(pingJie.getStringPingJie("开始创建excel文件,全路径为:",basePath , "/" , merCode , "/" , date+ "/" , date_hms , "_shls.xls"));
			WritableWorkbook wbook = Workbook.createWorkbook(new File(basePath + "/" + merCode + "/" + date
					+ "/" + date_hms + "_shls.xls"));
			WritableSheet wsheet = wbook.createSheet("商户流水", 0);
			
			// 设置excel里的字体
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);
			// 给标题规定字体的格式
			WritableCellFormat titleFormat = new WritableCellFormat(wf);
			
			Label excelTitle = new Label(0, 0, "商户号",titleFormat);
			wsheet.addCell(excelTitle);
			Label excelTitle1 = new Label(1, 0, "交易日期",titleFormat);
			wsheet.addCell(excelTitle1);
			Label excelTitle2 = new Label(2, 0, "交易金额",titleFormat);
			wsheet.addCell(excelTitle2);
			Label excelTitle3 = new Label(3, 0, "变动金额",titleFormat);
			wsheet.addCell(excelTitle3);
			Label excelTitle4 = new Label(4, 0, "账户余额",titleFormat);
			wsheet.addCell(excelTitle4);
			Label excelTitle5 = new Label(5, 0, "简短说明",titleFormat);
			wsheet.addCell(excelTitle5);
			
			if(dataList != null && dataList.size() > 0){
				int dataCount = 1;
				String tradeDate = null;
				String tradeTime = null;
				for (MerFundStance merFundStance : dataList) {
					Label excelTitle_mer_code = new Label(0, dataCount, merFundStance.getMerCode().getId(), titleFormat);
					wsheet.addCell(excelTitle_mer_code);
					tradeDate = merFundStance.getTradeTime().toString().substring(0, 10).replace("-", "");
					tradeTime = merFundStance.getTradeTime().toString().substring(11, 19).replace(":", "");
					Label excelTitle_trade_time = new Label(1, dataCount, tradeDate + tradeTime, titleFormat);
					wsheet.addCell(excelTitle_trade_time);
					Label excelTitle_mer_fee = new Label(2, dataCount, String.format("%.2f", merFundStance.getTradeAmount()), titleFormat);
					wsheet.addCell(excelTitle_mer_fee);
					Label excelTitle_change_amount = new Label(3, dataCount, String.format("%.2f", merFundStance.getChangeAmount()), titleFormat);
					wsheet.addCell(excelTitle_change_amount);
					Label excelTitle_account_amount = new Label(4, dataCount, String.format("%.2f", merFundStance.getAccountAmount()), titleFormat);
					wsheet.addCell(excelTitle_account_amount);
					Label excelTitle_derc_status = new Label(5, dataCount, cn.com.chinaebi.dz.object.util.DercStatusConvert.getDercStatus(merFundStance.getDercStatus()), titleFormat);
					wsheet.addCell(excelTitle_derc_status);
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
