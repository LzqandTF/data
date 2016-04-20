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

import cn.com.chinaebi.dz.object.ManualRec;
import cn.com.chinaebi.dz.util.StringPingJie;

public class CreateManualRecFileService {
	private static Log log = LogFactory.getLog(CreateManualRecFileService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.ManualRecDAO manualRecDAO = cn.com.chinaebi.dz.object.dao.ManualRecDAO.getInstance();
	
	private static StringPingJie pingJie = StringPingJie.getInstance();

	public static boolean createFileAndUpload(String merCode, String startDate, String endDate, String date,String date_hms){
		boolean fileFlag = false;
		try{
			List<ManualRec> dataList = manualRecDAO.queryManualRecDataLst(merCode, startDate, endDate);
			
			String basePath = "/var/www/apps/java/data/sgtz";
			
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
			log.info(pingJie.getStringPingJie("开始创建excel文件,全路径为:",basePath , "/" , merCode , "/" , date+ "/" , date_hms , "_sgtz.xls"));
			WritableWorkbook wbook = Workbook.createWorkbook(new File(basePath + "/" + merCode + "/" + date
					+ "/" + date_hms + "_sgtz.xls"));
			WritableSheet wsheet = wbook.createSheet("手工调账数据", 0);
			
			// 设置excel里的字体
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);
			// 给标题规定字体的格式
			WritableCellFormat titleFormat = new WritableCellFormat(wf);
			
			Label excelTitle = new Label(0, 0, "商户号",titleFormat);
			wsheet.addCell(excelTitle);
			Label excelTitle1 = new Label(1, 0, "商户简称",titleFormat);
			wsheet.addCell(excelTitle1);
			Label excelTitle2 = new Label(2, 0, "调账处理时间",titleFormat);
			wsheet.addCell(excelTitle2);
			Label excelTitle3 = new Label(3, 0, "调账金额",titleFormat);
			wsheet.addCell(excelTitle3);
			Label excelTitle4 = new Label(4, 0, "调账类型",titleFormat);
			wsheet.addCell(excelTitle4);
			Label excelTitle5 = new Label(5, 0, "调账状态",titleFormat);
			wsheet.addCell(excelTitle5);
			Label excelTitle6 = new Label(6, 0, "调账审核时间",titleFormat);
			wsheet.addCell(excelTitle6);
			Label excelTitle7 = new Label(7, 0, "调账原因",titleFormat);
			wsheet.addCell(excelTitle7);
			
			if(dataList != null && dataList.size() > 0){
				int dataCount = 1;
				String addorsub = null;
				String data_status = null;
				for (ManualRec manualRec : dataList) {
					Label excelTitle_mer_code = new Label(0, dataCount, manualRec.getMerCode().getId(), titleFormat);
					wsheet.addCell(excelTitle_mer_code);
					Label excelTitle_mer_abbreviation = new Label(1, dataCount, manualRec.getMerAbbreviation(), titleFormat);
					wsheet.addCell(excelTitle_mer_abbreviation);
					Label excelTitle_handler_time = new Label(2, dataCount, manualRec.getHandlerTime().toString(), titleFormat);
					wsheet.addCell(excelTitle_handler_time);
					Label excelTitle_rec_amount = new Label(3, dataCount, manualRec.getRecAmount(), titleFormat);
					wsheet.addCell(excelTitle_rec_amount);
					if (manualRec.getAddorsub() == 1) {
						addorsub = "增加";
					} else {
						addorsub = "减少";
					}
					Label excelTitle_addorsub = new Label(4, dataCount, addorsub, titleFormat);
					wsheet.addCell(excelTitle_addorsub);
					if (manualRec.getDataStatus() == 1) {
						data_status = "调账提交";
					} else if (manualRec.getDataStatus() == 2) {
						data_status = "审核成功";
					} else {
						data_status = "审核失败";
					}
					Label excelTitle_data_status = new Label(5, dataCount, data_status, titleFormat);
					wsheet.addCell(excelTitle_data_status);
					Label excelTitle_audit_time = new Label(6, dataCount, manualRec.getAuditTime().toString(), titleFormat);
					wsheet.addCell(excelTitle_audit_time);
					Label excelTitle_request_desc = new Label(7, dataCount, manualRec.getRequestDesc(), titleFormat);
					wsheet.addCell(excelTitle_request_desc);
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
