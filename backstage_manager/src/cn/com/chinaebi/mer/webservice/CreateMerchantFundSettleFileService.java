package cn.com.chinaebi.mer.webservice;

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
import cn.com.chinaebi.dz.object.MerchantFundSettle;
import cn.com.chinaebi.dz.object.dao.iface.MerchantFundSettleDAO;
import cn.com.chinaebi.dz.object.util.PropertiesUtils;
import cn.com.chinaebi.dz.util.StringPingJie;

/**
 * 创建商户结算单文件类
 * @author sp
 *
 */
public class CreateMerchantFundSettleFileService {
	
	private static Log log = LogFactory.getLog(CreateMerchantFundSettleFileService.class);
	
	private static MerchantFundSettleDAO merFundSettleDAO = cn.com.chinaebi.dz.object.dao.MerchantFundSettleDAO.getInstance();
	
	private static StringPingJie pingJie = StringPingJie.getInstance();

	/**
	 * 创建商户结算单文件
	 * @param map	查询商户结算单数据参数
	 * @param date	文件保存日期
	 * @param date_hms	文件名称中的日期组成
	 * @param mid	商户号
	 * @return
	 */
	public static boolean createMerFundSettleFile(Map<String,String[]> map,String date,String date_hms,String mid){
		boolean fileFlag = false;
		try{
			List<MerchantFundSettle> dataList = merFundSettleDAO.getMerchantFundSettleDataList(map);//根据参数查询出的商户结算单信息集合
			
			String basePath = PropertiesUtils.readProperties("fileSavePath_local");//从配置文件中读取 本地保存文件路径
			
			if(StringUtils.isBlank(basePath)){//若读取数据为空，则默认以下地址
				basePath = "/var/www/apps/java/data/shht";
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
			
			log.info(pingJie.getStringPingJie("开始创建excel文件,全路径为:",basePath , "/" , mid , "/" , date+ "/" , date_hms , "_MerFundSettleData.xls"));
			WritableWorkbook wbook = Workbook.createWorkbook(new File(basePath + "/" + mid + "/" + date
					+ "/" + date_hms + "_MerFundSettleData.xls"));
			
			WritableSheet wsheet = wbook.createSheet("商户结算单", 0);//工作区间名称定义
			
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);// 设置excel里的字体
			
			WritableCellFormat titleFormat = new WritableCellFormat(wf);// 给标题规定字体的格式
			
			
			/*
			 * 写入标题栏
			 */
			Label excelTitle = new Label(0, 0, "商户批次号",titleFormat);
			wsheet.addCell(excelTitle);
			Label excelTitle1 = new Label(1, 0, "商户简称",titleFormat);
			wsheet.addCell(excelTitle1);
			Label excelTitle2 = new Label(2, 0, "结算金额",titleFormat);
			wsheet.addCell(excelTitle2);
			Label excelTitle3 = new Label(3, 0, "商户手续费",titleFormat);
			wsheet.addCell(excelTitle3);
			Label excelTitle4 = new Label(4, 0, "系统批次号",titleFormat);
			wsheet.addCell(excelTitle4);
			Label excelTitle5 = new Label(5, 0, "结算截止日期",titleFormat);
			wsheet.addCell(excelTitle5);
			Label excelTitle6 = new Label(6, 0, "结算确认日期",titleFormat);
			wsheet.addCell(excelTitle6);
			
			
			//判断商户结算单数据是否为空，若有数据，则写入文件中
			if(dataList != null && dataList.size() > 0){
				int dataCount = 1;//文件行数标识，每写入一条，加一
				
				for (MerchantFundSettle merchantFundSettle : dataList) {//写数据
					
					Label excelTitle_mbtn = new Label(0, dataCount, merchantFundSettle.getMerBatchNo(),titleFormat);//商户批次号
					wsheet.addCell(excelTitle_mbtn);
					Label excelTitle_mname = new Label(1, dataCount, merchantFundSettle.getMerName(),titleFormat);//商户名称
					wsheet.addCell(excelTitle_mname);
					Label excelTitle_samt = new Label(2, dataCount, merchantFundSettle.getSettleAmount(),titleFormat);//结算金额
					wsheet.addCell(excelTitle_samt);
					Label excelTitle_mfee = new Label(3, dataCount, merchantFundSettle.getMerFee(),titleFormat);//商户手续费
					wsheet.addCell(excelTitle_mfee);
					Label excelTitle_sbtn = new Label(4, dataCount, merchantFundSettle.getSysBatchNo(),titleFormat);//系统批次号
					wsheet.addCell(excelTitle_sbtn);
					Label excelTitle_edate = new Label(5, dataCount, merchantFundSettle.getEndDate()+"",titleFormat);//结算截止日期
					wsheet.addCell(excelTitle_edate);
					Label excelTitle_sdate = new Label(6, dataCount, merchantFundSettle.getSettleDate()+"",titleFormat);//结算日期
					wsheet.addCell(excelTitle_sdate);
					
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
