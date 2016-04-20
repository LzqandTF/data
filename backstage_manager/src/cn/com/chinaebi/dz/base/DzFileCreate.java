package cn.com.chinaebi.dz.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.CreditcardpayTradeLst;
import cn.com.chinaebi.dz.object.CustomObject;
import cn.com.chinaebi.dz.object.ErrorDataLst;
import cn.com.chinaebi.dz.object.OriginalBeijingbankLst;
import cn.com.chinaebi.dz.object.OriginalCupsLst;
import cn.com.chinaebi.dz.object.OriginalDljhLst;
import cn.com.chinaebi.dz.object.OriginalShengjingbankLst;
import cn.com.chinaebi.dz.object.OriginalSzzhLst;
import cn.com.chinaebi.dz.object.OriginalZhongxingbankLst;
import cn.com.chinaebi.dz.object.RytUpmp;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.FileStatus;
import cn.com.chinaebi.dz.object.util.InnerTradeBeanValueMap;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.object.util.RegularExpressionUtil;
import cn.com.chinaebi.dz.object.util.TradeBeanValueMap;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.FindTradeCodeUtil;
import cn.com.chinaebi.dz.util.StringPingJie;

public class DzFileCreate {

	private static Log log = LogFactory.getLog(DzFileCreate.class);
	private static cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO errorData = cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.OriginalBeijingbankLstDAO originalBeijingbank = cn.com.chinaebi.dz.object.dao.OriginalBeijingbankLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.OriginalCupsLstDAO originalCups = cn.com.chinaebi.dz.object.dao.OriginalCupsLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.OriginalZhongxingbankLstDAO originalZhongxinbank = cn.com.chinaebi.dz.object.dao.OriginalZhongxingbankLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.OriginalSzzhLstDAO originalSzzhLstDAO = cn.com.chinaebi.dz.object.dao.OriginalSzzhLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.OriginalDljhLstDAO originalDljhLstDAO = cn.com.chinaebi.dz.object.dao.OriginalDljhLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.DuizhangCupsLstDAO duizhangCupsDao = cn.com.chinaebi.dz.object.dao.DuizhangCupsLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.DuizhangBeijingbankLstDAO duizhangBeijingbankDao = cn.com.chinaebi.dz.object.dao.DuizhangBeijingbankLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.DuizhangZhongxingbankLstDAO duizhangZhongxinbankDao = cn.com.chinaebi.dz.object.dao.DuizhangZhongxingbankLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.ObjectRelevanceColumnDAO objectRelevanceColumnDAO = cn.com.chinaebi.dz.object.dao.ObjectRelevanceColumnDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.DzFileTabDAO dzFileTabDao = cn.com.chinaebi.dz.object.dao.DzFileTabDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.MerTradecodeDAO merTradecodeDAO = cn.com.chinaebi.dz.object.dao.MerTradecodeDAO.getInstance();

	private static cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO = cn.com.chinaebi.dz.object.dao.TradeLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.RytUpmpDAO rytUpmpDAO = cn.com.chinaebi.dz.object.dao.RytUpmpDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = cn.com.chinaebi.dz.object.dao.HlogDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.OriginalShengjingbankLstDAO originalShengjingbankLstDAO = cn.com.chinaebi.dz.object.dao.OriginalShengjingbankLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.CreditcardpayTradeLstDAO creditcardpayTradeDao = cn.com.chinaebi.dz.object.dao.CreditcardpayTradeLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	
	private static StringPingJie stringPingJie = StringPingJie.getInstance();
	
	/**
	 * 创建一般对账文件
	 * @param date 文件日期
	 * @param customObject 系统接口对象
	 * @return
	 */
	public static synchronized boolean createDzFile(String date,CustomObject customObject) {
		log.info("进入生成对账文件方法……");
		boolean flag_excel = false;
		boolean flag_txt = false;
		
		Double total_trade_amount = 0.0;// 总交易金额
		Double total_fee_amount = 0.0;// 总手续费
		
		String path = "";
		String dzFileName = "";
		Integer saveDataNum = 0;
		
		
		if(customObject != null ){
			log.info(stringPingJie.getStringPingJie("获取系统对象ID：",customObject.getId(),",系统名称为：",customObject.getObjectName(),",系统文件存放路径为：",customObject.getFileAddress(),",系统对账文件总表名称为：",customObject.getDzFileName()));
			path = customObject.getFileAddress();
			dzFileName = customObject.getDzFileName();
			
			List<String> codeArr = merTradecodeDAO.getMerCodeList(customObject.getId());
			int saveDataNum_offLine = 0;
			if(customObject.getFileNeedOnlineData() == 1 || customObject.getFileNeedOnlineData() == 2){
				saveDataNum_offLine = tradeLstDAO.getTotalCountOfDzSucessDataOfOffLine(date, codeArr,customObject.getGenerateNumber(),customObject.isWhetherCreateFileByRange());
			}
			
			int saveDataNum_onLine = 0;
			if(customObject.getFileNeedOnlineData() == 2 || customObject.getFileNeedOnlineData() == 3){
				saveDataNum_onLine = hlogDAO.getTotalCountOfDzSucessDataOfOnLine(date, codeArr, customObject.getGenerateNumber(),customObject.isWhetherCreateFileByRange());
			}
			
			try {
				
				File f = new File(path+ "/" +date);
				if (!f.exists()) {
					f.mkdirs();
				}
				
				log.info("开始创建excel文件");
				WritableWorkbook wbook = Workbook.createWorkbook(new File(path+ "/" + date
						+ "/" + date + dzFileName + ".xls"));
				WritableSheet wsheet = wbook.createSheet("电银对账文件", 0);
				// 设置excel里的字体
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
						WritableFont.NO_BOLD, false);
				// 给标题规定字体的格式
				WritableCellFormat titleFormat = new WritableCellFormat(wf);

				
				File file = new File(path+ "/" + date + "/" + date
						+ dzFileName + ".txt");
				if (file.exists()) {
					log.info(stringPingJie.getStringPingJie(date , dzFileName,".txt对账总表已经存在，执行删除操作"));
					boolean flag = file.delete();
					if (flag) {
						log.info(stringPingJie.getStringPingJie("删除" , date , dzFileName,".txt对账总表成功"));
					}

				} else {
					log.info(stringPingJie.getStringPingJie(date , dzFileName,".txt对账总表不存在,不需要删除"));
				}

				List<Object> customDzFileInfoList = objectRelevanceColumnDAO
						.queryCustomDzFileInfo(customObject.getId(),
								FileStatus.DZ_FILE_TYPE);
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				int titleNum = 0;
				int i = 0;
				int trade_amount_column = -1;
				int trade_fee_column = -1;
				if(customDzFileInfoList !=null && customDzFileInfoList.size() > 0){
					for (Object object : customDzFileInfoList) {
						Object[] o = (Object[]) object;
						if ("tradeAmount".equalsIgnoreCase(o[2].toString())) {
							trade_amount_column = titleNum;
						}
						if ("acceptorPayFee".equalsIgnoreCase(o[2].toString())) {
							trade_fee_column = titleNum;
						}
						Label excelTitle = new Label(titleNum++, 0, o[1].toString(),
								titleFormat);
						wsheet.addCell(excelTitle);
						int hz = RegularExpressionUtil.statisticalChineseNumber(o[1].toString());
						int column_length = Integer.valueOf(o[9].toString()).intValue();
						int x = column_length - hz;
						log.info(stringPingJie.getStringPingJie(o[1].toString(),"的自身长度为",hz,"对应的字段设置宽度为",column_length,"得到的列的宽度为",x));
						bw.write(String.format("%-"+x+"s", o[1].toString()) + "|");
					}
					i++;
					bw.newLine();
					bw.write((saveDataNum_offLine + saveDataNum_onLine) + "");
					bw.newLine();
					int infoNum = 1;
					//customDzFileInfoList遍历得到的数组中各位置对应的值意义为：
					//0-文件类型；1-显示名称；2-线下字段属性值；3-字段属性名称；
					//4-规则类型；5-新值；6-旧值；7-规则模板函数；
					//8-应用规则的字段属性值；9-字段长度；10-规则ID；11-线上字段属性值；
					if(customObject.getFileNeedOnlineData() == 1 || customObject.getFileNeedOnlineData() == 2){
						
						// 查询各渠道对账成功数据
						List<OriginalZhongxingbankLst> list_zhongxin = originalZhongxinbank.queryZhongxingbankDzSucessDataOfNotRollBk(date);
						List<OriginalZhongxingbankLst> list_zhongxin_roll_bk = originalZhongxinbank.queryZhongxingbankDzSucessDataOfRollBk(date);
						List<OriginalBeijingbankLst> list_beijing = originalBeijingbank.queryBeijingbankDzSucessDataOfNotRollBk(date);
						List<OriginalBeijingbankLst> list_beijing_roll_bk = originalBeijingbank.queryBeijingbankDzSucessDataOfRollBk(date);
						List<OriginalCupsLst> list_cups = originalCups.queryCupsDzSucessDataOfNotRollBk(date);
						List<OriginalCupsLst> list_cups_roll_bk = originalCups.queryCupsDzSucessDataOfRollBk(date);
						List<OriginalSzzhLst> list_szzh = originalSzzhLstDAO.querySzzhDzSucessOfNotRollBk(date);
						List<OriginalSzzhLst> list_szzh_roll_bk = originalSzzhLstDAO.querySzzhDzSucessOfRollBk(date);
						List<OriginalDljhLst> list_dljh = originalDljhLstDAO.queryDljhDzSucessOfNotRollBk(date);
						List<OriginalDljhLst> list_dljh_roll_bk	= originalDljhLstDAO.quertDljgDzSucessOfRollBk(date);
						
						Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
						if (list_cups != null && list_cups.size() > 0) {
							log.info("将银联CUPS非冲正对账成功数据添加到Label中");
							int currentColumn = 0;
							for (OriginalCupsLst cupsLst : list_cups) {
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(cupsLst.getTerminalInfo());
								//判断系统对账文件生成类型：1-全部数据;2-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;3-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								
								boolean flag = false;
								/*
								 * 是按数据范围生成数据,表示文件中只会出现该配置数据
								 */
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(cupsLst.getReqMerCode())){
											//if("0".equals(map.get(cupsLst.getReqMerCode()).toString())){
												flag = true;
//											}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								/*
								 * 不按数据范围生成数据,表示文件中不会出现该配置数据
								 */
								else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(cupsLst.getReqMerCode())){
											//if("0".equals(map.get(cupsLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
										String content = TradeBeanValueMap
												.getOriginalValueOfNotRollBkForExcel(
														o[2].toString(), cupsLst, 11)
												+ "";
										
										content = ruleHandleContent(o, content);
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
										String txtContent = TradeBeanValueMap
												.getOriginalValueOfNotRollBkForTxt(
														o[2].toString(), cupsLst, 11,o[9].toString())
												+ "";
										txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent));
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent);
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent)+ "|");
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent + "|");
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
											}
										}
									}
									
									total_trade_amount = total_trade_amount + (cupsLst.getTradeAmount()==null?0d:Double.valueOf(cupsLst.getTradeAmount())/100);
									
									total_fee_amount = total_fee_amount
											+ Double.valueOf(duizhangCupsDao
													.getAcceptorPayFeeByTraceFromDzCupsFile(
															cupsLst.getReqSysStance(), date));
									currentColumn = 0;
									i++;
									log.info(stringPingJie.getStringPingJie("将银联cups的非冲正对账成功的流水号为",cupsLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							}
						} else {
							log.info(stringPingJie.getStringPingJie("不存在清算日期与交易日期为" , date , "的银联CUPS的对账成功数据"));
						}
	
						// 向文件中写冲正对账成功数据
						if (list_cups_roll_bk != null && list_cups_roll_bk.size() > 0) {
							log.info("将银联CUPS冲正对账成功数据添加到Label中");
							int currentColumn = 0;
							for (OriginalCupsLst cupsLst : list_cups_roll_bk) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(cupsLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(cupsLst.getReqMerCode())){
											//if("0".equals(map.get(cupsLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(cupsLst.getReqMerCode())){
											//if("0".equals(map.get(cupsLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										String content = TradeBeanValueMap
												.getOriginalValueOfRollBkForExcel(
														o[2].toString(), cupsLst, 11)
												+ "";
										content = ruleHandleContent(o, content);
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
										String txtContent = TradeBeanValueMap
												.getOriginalValueOfRollBkForTxt(
														o[2].toString(), cupsLst, 11,o[9].toString())
												+ "";
										txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent));
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent);
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent)+ "|");
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent + "|");
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
											}
										}
									}
									
									total_trade_amount = total_trade_amount + (cupsLst.getTradeAmount()==null?0d:Double.valueOf(cupsLst.getTradeAmount())/100);
									
									total_fee_amount = total_fee_amount
											+ Double.valueOf(duizhangCupsDao
													.getAcceptorPayFeeByTraceFromDzCupsFile(
															cupsLst.getDeductRollBkStance(),
															date));
									currentColumn = 0;
									i++;
									log.info(stringPingJie.getStringPingJie("将银联cups的冲正对账成功的流水号为",cupsLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							}
						}
	
						if (list_beijing != null && list_beijing.size() > 0) {
							log.info("将上海银联POSP数据添加到Label中");
							int currentColumn = 0;
							for (OriginalBeijingbankLst beijingLst : list_beijing) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(beijingLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(beijingLst.getReqMerCode())){
											//if("0".equals(map.get(beijingLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(beijingLst.getReqMerCode())){
											//if("0".equals(map.get(beijingLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										String content = TradeBeanValueMap
												.getOriginalValueOfNotRollBkForExcel(
														o[2].toString(), beijingLst, 70001)
												+ "";
										content = ruleHandleContent(o, content);
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
										String txtContent = TradeBeanValueMap
												.getOriginalValueOfNotRollBkForTxt(
														o[2].toString(), beijingLst, 70001,o[9].toString())
												+ "";
										txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent));
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent);
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent)+ "|");
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent + "|");
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
											}
										}
									}
									total_trade_amount = total_trade_amount + (beijingLst.getTradeAmount()==null?0d:Double.valueOf(beijingLst.getTradeAmount())/100);
									total_fee_amount = total_fee_amount
											+ Double.valueOf(duizhangBeijingbankDao
													.getAcceptorPayFeeByTraceFromDzBjbankFile(
															beijingLst.getReqSysStance(),
															date));
									currentColumn = 0;
									i++;
									log.info(stringPingJie.getStringPingJie("将北京银行的非冲正对账成功的流水号为",beijingLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							}
						} else {
							log.info(stringPingJie.getStringPingJie("不存在清算日期与交易日期为" , date , "的上海银联POSP的对账成功数据"));
						}
	
						if (list_beijing_roll_bk != null && list_beijing_roll_bk.size() > 0) {
							log.info("将上海银联POSP冲正数据添加到Label中");
							int currentColumn = 0;
							for (OriginalBeijingbankLst beijingLst : list_beijing_roll_bk) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(beijingLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(beijingLst.getReqMerCode())){
											//if("0".equals(map.get(beijingLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(beijingLst.getReqMerCode())){
											//if("0".equals(map.get(beijingLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										String content = TradeBeanValueMap
												.getOriginalValueOfRollBkForExcel(
														o[2].toString(), beijingLst, 70001)
												+ "";
										content = ruleHandleContent(o, content);
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
										String txtContent = TradeBeanValueMap
												.getOriginalValueOfRollBkForTxt(
														o[2].toString(), beijingLst, 70001,o[9].toString())
												+ "";
										txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent));
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent);
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent)+ "|");
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent + "|");
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
											}
										}
										
									}
									total_trade_amount = total_trade_amount + (beijingLst.getTradeAmount()==null?0d:Double.valueOf(beijingLst.getTradeAmount())/100);
									total_fee_amount = total_fee_amount
											+ Double.valueOf(duizhangBeijingbankDao
													.getAcceptorPayFeeByTraceFromDzBjbankFile(
															beijingLst
																	.getDeductRollBkStance(),
															date));
									currentColumn = 0;
									i++;
									log.info(stringPingJie.getStringPingJie("将北京银行的冲正对账成功的流水号为",beijingLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							}
						}
	
						if (list_zhongxin != null && list_zhongxin.size() > 0) {
							log.info("将上海中信数据添加到Label中");
							int currentColumn = 0;
							for (OriginalZhongxingbankLst zhongxinLst : list_zhongxin) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(zhongxinLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(zhongxinLst.getReqMerCode())){
											//if("0".equals(map.get(zhongxinLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(zhongxinLst.getReqMerCode())){
											//if("0".equals(map.get(zhongxinLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										Label value = new Label(currentColumn++, infoNum++,
												TradeBeanValueMap
														.getOriginalValueOfRollBkForExcel(
																o[2].toString(), zhongxinLst,
																10)
														+ "");
										wsheet.addCell(value);
										if(currentColumn == customDzFileInfoList.size()){
											bw.write(TradeBeanValueMap
													.getOriginalValueOfRollBkForExcel(
															o[2].toString(), zhongxinLst, 10)+"");
										}else{
											bw.write(TradeBeanValueMap
													.getOriginalValueOfRollBkForExcel(
															o[2].toString(), zhongxinLst, 10)
													+ "|");
										}
										total_trade_amount = total_trade_amount + (zhongxinLst.getTradeAmount()==null?0d:Double.valueOf(zhongxinLst.getTradeAmount())/100);
										total_fee_amount = total_fee_amount
												+ Double.valueOf(duizhangZhongxinbankDao
														.getAcceptorPayFeeByTraceFromDzZxFile(zhongxinLst
																.getReqSysStance()));
									}
									log.info(stringPingJie.getStringPingJie("将中信银行的非冲正对账成功的流水号为",zhongxinLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									i++;
									bw.newLine();
									saveDataNum++;
								}
							}
						} else {
							log.info(stringPingJie.getStringPingJie("不存在清算日期与交易日期为" , date , "的上海中信的对账成功数据"));
						}
	
						if (list_zhongxin_roll_bk != null
								&& list_zhongxin_roll_bk.size() > 0) {
							log.info("将上海中信冲正数据添加到Label中");
							int currentColumn = 0;
							for (OriginalZhongxingbankLst zhongxinLst : list_zhongxin_roll_bk) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(zhongxinLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(zhongxinLst.getReqMerCode())){
											//if("0".equals(map.get(zhongxinLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(zhongxinLst.getReqMerCode())){
											//if("0".equals(map.get(zhongxinLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*(else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										Label value = new Label(currentColumn++, infoNum++,
												TradeBeanValueMap
														.getOriginalValueOfRollBkForExcel(
																o[2].toString(), zhongxinLst,
																10)
														+ "");
										wsheet.addCell(value);
										bw.write(TradeBeanValueMap
												.getOriginalValueOfRollBkForExcel(
														o[2].toString(), zhongxinLst, 10)
												+ "|");
										total_trade_amount = total_trade_amount + (zhongxinLst.getTradeAmount()==null?0d:Double.valueOf(zhongxinLst.getTradeAmount())/100);
										total_fee_amount = total_fee_amount
												+ Double.valueOf(duizhangZhongxinbankDao
														.getAcceptorPayFeeByTraceFromDzZxFile(zhongxinLst
																.getDeductRollBkStance()));
									}
									i++;
									log.info(stringPingJie.getStringPingJie("将中信银行的冲正对账成功的流水号为",zhongxinLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									bw.newLine();
									saveDataNum++;
								}
							}
						}
						
						if (list_szzh != null && list_szzh.size() > 0) {
							log.info("将深圳中行非冲正对账成功数据添加到Label中");
							int currentColumn = 0;
							for (OriginalSzzhLst szzhLst : list_szzh) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(szzhLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(szzhLst.getReqMerCode())){
											//if("0".equals(map.get(szzhLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(szzhLst.getReqMerCode())){
											//if("0".equals(map.get(szzhLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
										String content = TradeBeanValueMap
												.getOriginalValueOfNotRollBkForExcel(
														o[2].toString(), szzhLst, 3)
												+ "";
										
										content = ruleHandleContent(o, content);
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
										String txtContent = TradeBeanValueMap
												.getOriginalValueOfNotRollBkForTxt(
														o[2].toString(), szzhLst, 3,o[9].toString())
												+ "";
										txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent));
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent);
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent)+ "|");
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent + "|");
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
											}
										}
									}
									
									total_trade_amount = total_trade_amount + (szzhLst.getTradeAmount()==null?0d:Double.valueOf(szzhLst.getTradeAmount())/100);
									
									total_fee_amount = total_fee_amount
											+ Double.valueOf(szzhLst.getZfFileFee());
									currentColumn = 0;
									i++;
									log.info(stringPingJie.getStringPingJie("将深圳中行的非冲正对账成功的流水号为",szzhLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							}
						} else {
							log.info(stringPingJie.getStringPingJie("不存在清算日期与交易日期为" , date , "的深圳中行的对账成功数据"));
						}
	
						// 向文件中写冲正对账成功数据
						if (list_szzh_roll_bk != null && list_szzh_roll_bk.size() > 0) {
							log.info("将深圳中行冲正对账成功数据添加到Label中");
							int currentColumn = 0;
							for (OriginalSzzhLst szzhLst : list_szzh_roll_bk) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(szzhLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(szzhLst.getReqMerCode())){
											//if("0".equals(map.get(szzhLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(szzhLst.getReqMerCode())){
											//if("0".equals(map.get(szzhLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										String content = TradeBeanValueMap
												.getOriginalValueOfRollBkForExcel(
														o[2].toString(), szzhLst, 3)
												+ "";
										content = ruleHandleContent(o, content);
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
										String txtContent = TradeBeanValueMap
												.getOriginalValueOfRollBkForTxt(
														o[2].toString(), szzhLst, 3,o[9].toString())
												+ "";
										txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent));
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent);
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent)+ "|");
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent + "|");
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
											}
										}
									}
									
									total_trade_amount = total_trade_amount + (szzhLst.getTradeAmount()==null?0d:Double.valueOf(szzhLst.getTradeAmount())/100);
									
									total_fee_amount = total_fee_amount
											+ Double.valueOf(szzhLst.getZfFileFee());
									currentColumn = 0;
									i++;
									log.info(stringPingJie.getStringPingJie("将深圳中行的冲正对账成功的流水号为",szzhLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							}
						}
						
						if (list_dljh != null && list_dljh.size() > 0) {
							log.info("将大连交行非冲正对账成功数据添加到Label中");
							int currentColumn = 0;
							for (OriginalDljhLst dljhLst : list_dljh) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(dljhLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(dljhLst.getReqMerCode())){
											//if("0".equals(map.get(dljhLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(dljhLst.getReqMerCode())){
											//if("0".equals(map.get(dljhLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
										String content = TradeBeanValueMap
												.getOriginalValueOfNotRollBkForExcel(
														o[2].toString(), dljhLst, 14)
												+ "";
										
										content = ruleHandleContent(o, content);
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
										String txtContent = TradeBeanValueMap
												.getOriginalValueOfNotRollBkForTxt(
														o[2].toString(), dljhLst, 14,o[9].toString())
												+ "";
										txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent));
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent);
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent)+ "|");
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent + "|");
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
											}
										}
									}
									
									total_trade_amount = total_trade_amount + (dljhLst.getTradeAmount()==null?0d:Double.valueOf(dljhLst.getTradeAmount())/100);
									
									total_fee_amount = total_fee_amount
											+ Double.valueOf(dljhLst.getZfFileFee());
									currentColumn = 0;
									i++;
									log.info(stringPingJie.getStringPingJie("将大连交行的非冲正对账成功的流水号为",dljhLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							}
						} else {
							log.info(stringPingJie.getStringPingJie("不存在清算日期与交易日期为" , date , "的大连交行的对账成功数据"));
						}
	
						// 向文件中写冲正对账成功数据
						if (list_dljh_roll_bk != null && list_dljh_roll_bk.size() > 0) {
							log.info("将大连交行冲正对账成功数据添加到Label中");
							int currentColumn = 0;
							for (OriginalDljhLst dljhLst : list_dljh_roll_bk) {
//								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(dljhLst.getTerminalInfo());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(dljhLst.getReqMerCode())){
											//if("0".equals(map.get(dljhLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 3){
										if(map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(dljhLst.getReqMerCode())){
											//if("0".equals(map.get(dljhLst.getReqMerCode()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 3){
										if(!map.containsKey(trade_code)){
											//if("0".equals(map.get(trade_code).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										String content = TradeBeanValueMap
												.getOriginalValueOfRollBkForExcel(
														o[2].toString(), dljhLst, 14)
												+ "";
										content = ruleHandleContent(o, content);
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
										String txtContent = TradeBeanValueMap
												.getOriginalValueOfRollBkForTxt(
														o[2].toString(), dljhLst, 14,o[9].toString())
												+ "";
										txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent));
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent);
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", txtContent)+ "|");
											}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
												bw.write(txtContent + "|");
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
											}
										}
									}
									
									total_trade_amount = total_trade_amount + (dljhLst.getTradeAmount()==null?0d:Double.valueOf(dljhLst.getTradeAmount())/100);
									
									total_fee_amount = total_fee_amount
											+ Double.valueOf(dljhLst.getZfFileFee());
									currentColumn = 0;
									i++;
									log.info(stringPingJie.getStringPingJie("将大连交行的冲正对账成功的流水号为",dljhLst.getDeductSysStance(),"数据添加到第",infoNum,"行"));
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							}
						}
						
					}
					
					if(customObject.getFileNeedOnlineData() == 2 || customObject.getFileNeedOnlineData() == 3){
						//融易通线上交易数据
						List<RytUpmp> list_upmp = rytUpmpDAO.queryRytUpmpDzSucessData(date);
						if(list_upmp != null && list_upmp.size() > 0){
							int currentColumn = 0;
							for(RytUpmp rytUpmp : list_upmp){
								Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
								//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
//								if((customObject.getGenerateNumber() == 2 && map.containsKey(rytUpmp.getMid()) && "0".equals(map.get(rytUpmp.getMid()).toString())) 
//										|| (customObject.getGenerateNumber() == 1)){
								boolean flag = false;
								if(customObject.isWhetherCreateFileByRange()){
									if(customObject.getGenerateNumber() == 2){
										if(map.containsKey(rytUpmp.getMid())){
											//if("0".equals(map.get(rytUpmp.getMid()).toString())){
												flag = true;
											//}
										}
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}else{
									if(customObject.getGenerateNumber() == 2){
										if(!map.containsKey(rytUpmp.getMid())){
											//if("0".equals(map.get(rytUpmp.getMid()).toString())){
												flag = true;
											//}
										}/*else{
											flag = true;
										}*/
									}else if(customObject.getGenerateNumber() == 1){
										flag = true;
									}
								}
								
								
								if(flag){
									for (Object object : customDzFileInfoList) {
										Object[] o = (Object[]) object;
										String content = (o[11]==null||StringUtils.isBlank(o[11].toString()))?"":(TradeBeanValueMap
												.getRyfHlog(o[11].toString(), rytUpmp, 55001)
												+ "");
										content = ruleHandleContent(o, content);
										if(content == null || "null".equals(content)){
											content = "";
										}
										Label value = new Label(currentColumn++, infoNum,
												content);
										wsheet.addCell(value);
	//									String txtContent = TradeBeanValueMap
	//											.getOriginalValueOfNotRollBkForTxt(
	//													o[11].toString(), beijingLst, 70001,o[9].toString())
	//											+ "";
	//									txtContent = ruleHandleContent(o, txtContent);
										if(currentColumn == customDzFileInfoList.size()){
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", content));
											}else if(o[11]!=null && StringUtils.isNotBlank(o[11].toString())){
												if("tradeTypeInChinese".equals(o[11].toString()) || "deductSysName".equals(o[11].toString())){
													int hz = RegularExpressionUtil.statisticalChineseNumber(content);
													int column_length = Integer.valueOf(o[9].toString()).intValue();
													int x = column_length - hz;
													bw.write(String.format("%-"+x+"s", content));
												}else{
													bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", content));
												}
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", content));
											}
										}else{
											if(o[9]==null || StringUtils.isBlank(o[9].toString())){
												bw.write(String.format("%-60s", content)+ "|");
											}else if(o[11]!=null && StringUtils.isNotBlank(o[11].toString())){
												if("tradeTypeInChinese".equals(o[11].toString()) || "deductSysName".equals(o[11].toString())){
													int hz = RegularExpressionUtil.statisticalChineseNumber(content);
													int column_length = Integer.valueOf(o[9].toString()).intValue();
													int x = column_length - hz;
													bw.write(String.format("%-"+x+"s", content) + "|");
												}else{
													bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", content)+ "|");
												}
											}else{
												bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", content)+ "|");
											}
										}
									}
									total_trade_amount = total_trade_amount + (rytUpmp.getAmount()==null?0d:Double.valueOf(rytUpmp.getAmount())/100);
									total_fee_amount = total_fee_amount + Double.valueOf(rytUpmp.getBankFee());
									currentColumn = 0;
									i++;
									infoNum++;
									bw.newLine();
									saveDataNum++;
								}
							
							}
						}
						
					}
				}else{
					log.info("该系统没有配置任何自定义对账字段信息");
					MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, date,customObject.getObjectName()+"没有配置对账字段，生成对账文件失败，请查看.");
				}
				if(trade_amount_column == 0 || trade_fee_column == 0){
					Label totalDataSize = new Label(0, i+1, saveDataNum + "");
					wsheet.addCell(totalDataSize);
				}else{
					Label totalDataSize = new Label(0, i, saveDataNum + "");
					wsheet.addCell(totalDataSize);
				}
				
				if(trade_amount_column != -1){
					java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					Label totalTradeAmount_ = new Label(trade_amount_column, i,nf.format(total_trade_amount));
					wsheet.addCell(totalTradeAmount_);
				}
				if(trade_fee_column != -1){
					Label totalFeeAmount = new Label(trade_fee_column, i,
							(0 == total_fee_amount) ? "0.0" : String.format("%.2f",
									total_fee_amount));
					wsheet.addCell(totalFeeAmount);
				}
				
				// 设置列宽
				wsheet.setColumnView(0, 10);
				wsheet.setColumnView(1, 20);
				wsheet.setColumnView(2, 25);
				wsheet.setColumnView(3, 15);
				wsheet.setColumnView(4, 15);
				wsheet.setColumnView(5, 15);
				wsheet.setColumnView(6, 15);
				wsheet.setColumnView(7, 10);
				wsheet.setColumnView(8, 20);
				wsheet.setColumnView(9, 15);
				wsheet.setColumnView(10, 20);
				wsheet.setColumnView(11, 20);
				wsheet.setColumnView(12, 25);
				wsheet.setColumnView(13, 20);
				wsheet.setColumnView(14, 20);
				wsheet.setColumnView(15, 20);
				wsheet.setColumnView(16, 20);
				wsheet.setColumnView(17, 20);
				wsheet.setColumnView(18, 10);
				wsheet.setColumnView(19, 30);
				wsheet.setColumnView(20, 10);
				wsheet.setColumnView(21, 10);
				wsheet.setColumnView(22, 30);
				wsheet.setColumnView(23, 20);
				wsheet.setColumnView(24, 20);
				wsheet.setColumnView(25, 20);
				wsheet.setColumnView(26, 20);
				// 写入文件
				wbook.write();
				wbook.close();
				flag_excel = true;
				bw.flush();
				bw.close();
				fw.close();
				flag_txt = true;
				log.info(stringPingJie.getStringPingJie("日期为" , date , "txt文件创建成功"));
				log.info(stringPingJie.getStringPingJie("日期为" , date , "excel文件创建成功!"));
			} catch (Exception e) {
				log.error(e);
				log.error(e.getMessage());
			}
		}
		
		return flag_excel && flag_txt;
	}

	/**
	 * 创建差错文件
	 * @param date 文件日期
	 * @param customObject  系统接口对象
	 * @return
	 */
	public static synchronized boolean createErrorDzFile(String date,CustomObject customObject) throws Exception{
		log.info("进入生成差错对账成功对账文件方法……");
		boolean flag_excel = false;
		boolean flag_txt = false;
		List<Integer> ids = Backstage.getInstance().getInstInfoIdList();
		List<ErrorDataLst> list_cups = null;
		Double totalTradeAmount = 0d;
		try {
			
			String path = "";
			String dzFileName = "";
			Integer saveDataNum = 0;
			if(customObject != null){
				log.info(stringPingJie.getStringPingJie("获取系统对象ID：",customObject.getId(),",系统名称为：",customObject.getObjectName(),",系统文件存放路径为：",customObject.getFileAddress(),",系统差错文件总表名称为：",customObject.getErrorFileName()));
				path = customObject.getFileAddress();
				dzFileName = customObject.getErrorFileName();
				
				List<String> codeArr = merTradecodeDAO.getMerCodeList(customObject.getId());
				
				Calendar calendar = Calendar.getInstance();//系统当前时间
				Date date_ = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").parse(date);
				calendar.setTime(date_);
				calendar.add(Calendar.DATE, -1);
	    		String folder_date = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				
				File f = new File(path+ "/" + folder_date);
				if (!f.exists()) {
					f.mkdirs();
				}
				
				log.info("开始创建excel文件");
				WritableWorkbook wbook = Workbook.createWorkbook(new File(path+ "/" + folder_date + "/" + date + dzFileName + ".xls"));
				WritableSheet wsheet = wbook.createSheet("电银差错文件", 0);
				// 设置excel里的字体
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false);
				// 给标题规定字体的格式
				WritableCellFormat titleFormat = new WritableCellFormat(wf);
				log.info("开始创建txt文件");
				File file = new File(path+ "/" + folder_date + "/" + date + dzFileName + ".txt");
				if (file.exists()) {
					log.info(stringPingJie.getStringPingJie(date , dzFileName,".txt差错总表已经存在,执行删除操作"));
					boolean flag = file.delete();
					if (flag) {
						log.info(stringPingJie.getStringPingJie("删除" , date , dzFileName , ".txt差错总表成功"));
					}
				} else {
					log.info(stringPingJie.getStringPingJie(date , dzFileName , ".txt差错总表不存在,不需要删除"));
				}

				List<Object> customDzFileInfoList = objectRelevanceColumnDAO.queryCustomDzFileInfo(customObject.getId(),FileStatus.ERROR_FILE_TYPE);
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);

				int titleNum = 0;
				int i = 0;
				int trade_amount_column = 0;
				if(customDzFileInfoList != null && customDzFileInfoList.size() > 0){
					for (Object object : customDzFileInfoList) {
						Object[] o = (Object[]) object;
						if ("tradeAmount".equalsIgnoreCase(o[2].toString())) {
							trade_amount_column = titleNum;
						}
						Label excelTitle = new Label(titleNum++, 0, o[1].toString(),titleFormat);
						wsheet.addCell(excelTitle);
						int x = Integer.valueOf(o[9].toString()).intValue()-(RegularExpressionUtil.statisticalChineseNumber(o[1].toString()));
						bw.write(String.format("%-"+x+"s", o[1].toString()) + "|");
					}
					bw.newLine();
					bw.write(errorData.getTotalErrorDzDataNum(date, ids, codeArr,customObject.getGenerateNumber()) + "");
					bw.newLine();
					
					i++;
					
					if (ids == null || ids.size() == 0) {
						log.info("不存在符合生成差错文件的渠道信息");
					} else {
						list_cups = errorData.queryErrorDzSucessData(date, ids, codeArr,customObject.getGenerateNumber());
					}

					if (list_cups != null && list_cups.size() > 0) {
						log.info("将差错数据添加到Label中");
						int currentColumn = 0;
						int infoNum = 1;
						for (ErrorDataLst errLst : list_cups) {
							Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
							String trade_code = "";
							if(StringUtils.isNotBlank(errLst.getTerminalInfo())){
								trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(errLst.getTerminalInfo());
							}
							//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
							boolean flag = false;
							if(customObject.isWhetherCreateFileByRange()){
								if(customObject.getGenerateNumber() == 2){
									if(map.containsKey(errLst.getReqMerCode())){
										if("0".equals(map.get(errLst.getReqMerCode()).toString())){
											flag = true;
										}
									}
								}else if(customObject.getGenerateNumber() == 3){
									if(map.containsKey(trade_code)){
										if("0".equals(map.get(trade_code).toString())){
											flag = true;
										}
									}
								}else if(customObject.getGenerateNumber() == 1){
									flag = true;
								}
							}else{
								if(customObject.getGenerateNumber() == 2){
									if(map.containsKey(errLst.getReqMerCode())){
										if("0".equals(map.get(errLst.getReqMerCode()).toString())){
											flag = true;
										}
									}else{
										flag = true;
									}
								}else if(customObject.getGenerateNumber() == 3){
									if(map.containsKey(trade_code)){
										if("0".equals(map.get(trade_code).toString())){
											flag = true;
										}
									}else{
										flag = true;
									}
								}else if(customObject.getGenerateNumber() == 1){
									flag = true;
								}
							}
							
							
							if(flag){
								for (Object object : customDzFileInfoList) {
									Object[] o = (Object[]) object;
									log.info("当前属性名称为："+o[2].toString());
									String content = "";
									if(errLst.getDeductSysId() != null){
										content = TradeBeanValueMap.getOriginalErrorValueOfExcel(o[2].toString(), errLst, errLst.getDeductSysId()) + "";
									}
									content = ruleHandleContent(o, content);
									Label value = new Label(currentColumn++, infoNum,content);
									wsheet.addCell(value);
									String txtContent =  "";
									if(errLst.getDeductSysId() != null){
										txtContent = TradeBeanValueMap.getOriginalErrorValueOfTxt(o[2].toString(), errLst, errLst.getDeductSysId(),o[9].toString())+"";
									}
									
									txtContent = ruleHandleContent(o, txtContent);
									if(currentColumn == customDzFileInfoList.size()){
										if(o[9]==null || StringUtils.isBlank(o[9].toString())){
											bw.write(String.format("%-60s", txtContent));
										}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
											bw.write(txtContent);
										}else{
											bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
										}
									}else{
										if(o[9]==null || StringUtils.isBlank(o[9].toString())){
											bw.write(String.format("%-60s", txtContent)+ "|");
										}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
											bw.write(txtContent + "|");
										}else{
											bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
										}
									}
									
								}
								totalTradeAmount = totalTradeAmount
										+ (errLst.getTradeAmount() == null ? 0d
												: (Double.valueOf(errLst
														.getTradeAmount()) / 100));
								
								currentColumn = 0;
								i++;
								infoNum++;
								bw.newLine();
								saveDataNum++;
							}
						}
					} else {
						log.info(stringPingJie.getStringPingJie("不存在清算日期与交易日期为" , date , "的差错数据"));
					}

				}else{
					log.info("该系统没有配置任何自定义对账字段信息");
					MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, date,customObject.getObjectName()+"没有配置对账字段，生成差错文件失败，请查看.");
				}
				
				Label totalDataSize = new Label(0, i, saveDataNum + "");
				wsheet.addCell(totalDataSize);
				if(trade_amount_column != 0){
					Label totalTradeAmount_ = new Label(trade_amount_column, i,
							String.format("%.2f", totalTradeAmount));
					wsheet.addCell(totalTradeAmount_);
				}
				
				// 设置列宽
				wsheet.setColumnView(0, 10);
				wsheet.setColumnView(1, 20);
				wsheet.setColumnView(2, 25);
				wsheet.setColumnView(3, 25);
				wsheet.setColumnView(4, 25);
				wsheet.setColumnView(5, 25);
				wsheet.setColumnView(6, 25);
				wsheet.setColumnView(7, 20);
				wsheet.setColumnView(8, 20);
				wsheet.setColumnView(9, 25);
				wsheet.setColumnView(10, 20);
				wsheet.setColumnView(11, 25);
				wsheet.setColumnView(12, 25);
				wsheet.setColumnView(13, 20);
				wsheet.setColumnView(14, 20);
				wsheet.setColumnView(15, 20);
				wsheet.setColumnView(16, 20);
				wsheet.setColumnView(17, 20);
				wsheet.setColumnView(18, 30);
				wsheet.setColumnView(19, 30);
				wsheet.setColumnView(20, 30);
				wsheet.setColumnView(21, 30);
				wsheet.setColumnView(22, 30);
				wsheet.setColumnView(23, 30);
				wsheet.setColumnView(24, 30);
				wsheet.setColumnView(25, 30);
				wsheet.setColumnView(26, 30);
				wsheet.setColumnView(27, 30);
				wsheet.setColumnView(28, 30);
				// wsheet.setColumnView(29,30);
				// 写入文件
				wbook.write();
				wbook.close();
				flag_excel = true;
				bw.flush();
				bw.close();
				fw.close();
				flag_txt = true;
				log.info(stringPingJie.getStringPingJie("日期为" , date , "txt文件创建成功"));
				log.info(stringPingJie.getStringPingJie("日期为" , date , "excel文件创建成功!"));
					
			}
		} catch (Exception e) {
			log.error(e);
			throw e;
		}
		return flag_excel && flag_txt;
	}

	/**
	 * 对文件内容进行规则化处理
	 * @param o 系统配置内容数组
	 * @param content 将要应用规则的字符串
	 * @return
	 */
	public static String ruleHandleContent(Object[] o, String content) {//o[5] 新值 o[6] 旧值
		if(StringUtils.isNotBlank(content)){
			if (o[8] != null && StringUtils.isNotBlank(o[8].toString())) {// 判断有没有应用规则的属性
				if (o[2].toString().equals(o[8].toString())) {// 判断当前循环的属性是否为应用规则的属性
					if (o[7] != null && StringUtils.isNotBlank(o[7].toString())) {
						List<Object> list = objectRelevanceColumnDAO.queryReplaceValue(Integer.valueOf(o[10].toString()));
						if(list != null && list.size() > 0){
							if ("0".equals(o[7].toString())) {

							} else if ("replaceall".equalsIgnoreCase(o[7].toString())) {// 应用模板精确替换
								if (o[4] != null
										&& StringUtils.isNotBlank(o[4].toString())) {
									if ("0".equals(o[4].toString())) {// 针对所有进行替换
										Object[] obj_ = (Object[])list.get(0);
										if(obj_[2] != null && StringUtils.isNotBlank(obj_[2].toString())){
											content = obj_[2].toString();
										}
									} else {
										Map<String,String> map = new HashMap<String,String>();
										for (Object object_m : list) {
											Object[] obj_m = (Object[])object_m;
											map.put(obj_m[1].toString(), obj_m[2].toString());
										}
										if(map != null){
											if (map.containsKey(content)) {
												content = map.get(content);
											}
										}
									}
								}
							} else if ("replaceLike".equalsIgnoreCase(o[7].toString())) {// 应用模板模糊查询后替换
								for (Object object : list) {
									Object[] obj = (Object[])object;
									if(obj[1] != null && StringUtils.isNotBlank(obj[1].toString())){
										int strLength = RegularExpressionUtil.complieString(obj[1].toString(), "%");
										if (strLength == 1) {
											if (obj[1].toString().endsWith("%")) {
												if (content.startsWith(obj[1].toString().substring(0, obj[1].toString().length()-1))) {
													content = obj[2].toString();
												}
											} else if (obj[1].toString().startsWith("%")) {
												if (content.endsWith(obj[1].toString().substring(1, obj[1].toString().length()))) {
													content = obj[2].toString();
												}
											}
										} else {
											if (content.contains(obj[1].toString().substring(1, obj[1].toString().length()-1))) {
												content = obj[2].toString();
											}
										}
									}
								}
							} else if ("subString".equalsIgnoreCase(o[7].toString())) {// 应用模板截取显示
								for (Object object : list) {
									Object[] obj = (Object[])object;
									if(obj[1] != null && StringUtils.isNotBlank(obj[1].toString())){
										content = content.substring(
												Integer.valueOf(obj[1].toString()),
												Integer.valueOf(obj[2].toString()));
									}
								}
							}
						}
					}
				}
			}
		}
		
		return content;
	}
	/**
	 * 向对账文件表中插入记录
	 * @param date 文件日期
	 * @param file_name 文件名称
	 * @param file_type 文件类型
	 * @param dz_file_path 文件路径
	 * @param object_id 系统对象ID
	 * @param object_name 系统对象名称
	 * @return
	 */
	public static boolean insertDataToDzFileTab(String date,String file_name,String file_type,String dz_file_path,int object_id,String object_name) throws Exception{
		boolean flag = false;
		try{
			String create_last_time = DYDataUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			flag = dzFileTabDao.insertDzFileData(date,file_name,file_type,create_last_time,dz_file_path,object_id,object_name);
			if(flag){
				log.info(stringPingJie.getStringPingJie("向对账文件表插入",date,"数据成功"));
			}else{
				log.info(stringPingJie.getStringPingJie("向对账文件表插入",date,"数据失败"));
			}
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return flag;
	}
	
	/**
	 * 创建内部渠道对账文件
	 * @param date 文件数据日期
	 * @param customObject 系统接口对象
	 * @return
	 */
	public static synchronized boolean createInnerDzFile(String date,CustomObject customObject) throws Exception {
		
		boolean createFileFlag = false;
		
		List<OriginalShengjingbankLst> list_sjyh = originalShengjingbankLstDAO.querySJYHTradeLstClearInnerData(date,"0");
		List<CreditcardpayTradeLst> list_creditcard = creditcardpayTradeDao.queryCreditcardpaySucessData(date, "0");
		Double total_trade_amount = 0.0;// 总交易金额
		Double total_fee_amount = 0.0;// 总手续费
		
		String path = "";
		String dzFileName = "";
		Integer saveDataNum = 0;
		
		
			if(customObject != null ){
				log.info(stringPingJie.getStringPingJie("获取系统对象ID：",customObject.getId(),",系统名称为：",customObject.getObjectName(),",系统文件存放路径为：",customObject.getFileAddress(),",系统对账文件总表名称为：",customObject.getDzFileName()));
				path = customObject.getFileAddress();
				dzFileName = customObject.getDzFileName();
				
				List<String> codeArr = merTradecodeDAO.getMerCodeList(customObject.getId());
				int saveDataNum_sjyh = originalShengjingbankLstDAO.getTotalCountOfDzSucessDataOfSjyh(date, codeArr, customObject.getGenerateNumber());
				int saveDataNum_creditcard = creditcardpayTradeDao.getTotalCountOfDzSucessDataOfCreditCard(date, codeArr, customObject.getGenerateNumber());
				
				try {
					
					File f = new File(path+ "/" +date);
					if (!f.exists()) {
						f.mkdirs();
					}
					
					log.info("开始创建excel文件");
					WritableWorkbook wbook = Workbook.createWorkbook(new File(path+ "/" + date
							+ "/" + date + dzFileName + ".xls"));
					WritableSheet wsheet = wbook.createSheet("电银对账文件", 0);
					// 设置excel里的字体
					WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
							WritableFont.NO_BOLD, false);
					// 给标题规定字体的格式
					WritableCellFormat titleFormat = new WritableCellFormat(wf);
	
					
					File file = new File(path+ "/" + date + "/" + date
							+ dzFileName + ".txt");
					if (file.exists()) {
						log.info(stringPingJie.getStringPingJie(date , dzFileName,".txt对账总表已经存在，执行删除操作"));
						boolean flag = file.delete();
						if (flag) {
							log.info(stringPingJie.getStringPingJie("删除" , date , dzFileName,".txt对账总表成功"));
						}
	
					} else {
						log.info(stringPingJie.getStringPingJie(date , dzFileName,".txt对账总表不存在,不需要删除"));
					}
	
					List<Object> customDzFileInfoList = objectRelevanceColumnDAO
							.queryCustomDzFileInfo(customObject.getId(),
									FileStatus.DZ_FILE_TYPE);
					FileWriter fw = new FileWriter(file, true);
					BufferedWriter bw = new BufferedWriter(fw);
					int titleNum = 0;
					int i = 0;
					int trade_amount_column = -1;
					int trade_fee_column = -1;
					if(customDzFileInfoList !=null && customDzFileInfoList.size() > 0){
						for (Object object : customDzFileInfoList) {
							Object[] o = (Object[]) object;
							if ("tradeAmount".equalsIgnoreCase(o[2].toString())) {
								trade_amount_column = titleNum;
							}
							if ("acceptorPayFee".equalsIgnoreCase(o[2].toString())) {
								trade_fee_column = titleNum;
							}
							Label excelTitle = new Label(titleNum++, 0, o[1].toString(),
									titleFormat);
							wsheet.addCell(excelTitle);
							int hz = RegularExpressionUtil.statisticalChineseNumber(o[1].toString());
							int column_length = Integer.valueOf(o[9].toString()).intValue();
							int x = column_length - hz;
							log.info(stringPingJie.getStringPingJie(o[1].toString(),"的自身长度为",hz,"对应的字段设置宽度为",column_length,"得到的列的宽度为",x));
							bw.write(String.format("%-"+x+"s", o[1].toString()) + "|");
						}
						i++;
						bw.newLine();
						bw.write((saveDataNum_sjyh + saveDataNum_creditcard) + "");
						bw.newLine();
						int infoNum = 1;
						//customDzFileInfoList遍历得到的数组中各位置对应的值意义为：
						//0-文件类型；1-显示名称；2-线下字段属性值；3-字段属性名称；
						//4-规则类型；5-新值；6-旧值；7-规则模板函数；
						//8-应用规则的字段属性值；9-字段长度；10-规则ID；11-线上字段属性值；
						
						if(customObject.getFileNeedOnlineData() == 1 || customObject.getFileNeedOnlineData() == 2){
							if (list_sjyh != null && list_sjyh.size() > 0) {
								log.info("将盛京银行交易成功数据添加到Label中");
								int currentColumn = 0;
								for (OriginalShengjingbankLst sjyhLst : list_sjyh) {
									Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
									String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(sjyhLst.getTerminalInfo());
									//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
									boolean flag = false;
									if(customObject.isWhetherCreateFileByRange()){
										if(customObject.getGenerateNumber() == 2){
											if(map.containsKey(sjyhLst.getReqMerCode())){
												if("0".equals(map.get(sjyhLst.getReqMerCode()).toString())){
													flag = true;
												}
											}
										}else if(customObject.getGenerateNumber() == 3){
											if(map.containsKey(trade_code)){
												if("0".equals(map.get(trade_code).toString())){
													flag = true;
												}
											}
										}else if(customObject.getGenerateNumber() == 1){
											flag = true;
										}
									}else{
										if(customObject.getGenerateNumber() == 2){
											if(map.containsKey(sjyhLst.getReqMerCode())){
												if("0".equals(map.get(sjyhLst.getReqMerCode()).toString())){
													flag = true;
												}
											}else{
												flag = true;
											}
										}else if(customObject.getGenerateNumber() == 3){
											if(map.containsKey(trade_code)){
												if("0".equals(map.get(trade_code).toString())){
													flag = true;
												}
											}else{
												flag = true;
											}
										}else if(customObject.getGenerateNumber() == 1){
											flag = true;
										}
									}
									
									
									if(flag){
										for (Object object : customDzFileInfoList) {
											Object[] o = (Object[]) object;
											// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
											log.info(stringPingJie.getStringPingJie("当前属性为：",o[2].toString(),"++++++++++++++++++++++++++++++"));
											String content = InnerTradeBeanValueMap
													.getInnerObjectValue(
															o[2].toString(), sjyhLst)
													+ "";
											
											content = ruleHandleContent(o, content);
											Label value = new Label(currentColumn++, infoNum,
													content);
											wsheet.addCell(value);
											String txtContent = InnerTradeBeanValueMap
													.getInnerObjectValueForTxt(
															o[2].toString(), sjyhLst, o[9].toString())
													+ "";
											txtContent = ruleHandleContent(o, txtContent);
											if(currentColumn == customDzFileInfoList.size()){
												if(o[9]==null || StringUtils.isBlank(o[9].toString())){
													bw.write(String.format("%-60s", txtContent));
												}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
													bw.write(txtContent);
												}else{
													bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
												}
											}else{
												if(o[9]==null || StringUtils.isBlank(o[9].toString())){
													bw.write(String.format("%-60s", txtContent)+ "|");
												}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
													bw.write(txtContent + "|");
												}else{
													bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
												}
											}
										}
										
										total_trade_amount = total_trade_amount + (sjyhLst.getTradeAmount()==null?0d:Double.valueOf(sjyhLst.getTradeAmount())/100);
										
										total_fee_amount = total_fee_amount + Double.valueOf(sjyhLst.getTradeFee().substring(1, sjyhLst.getTradeFee().length()))/100;
										currentColumn = 0;
										i++;
										log.info(stringPingJie.getStringPingJie("将盛京银行的交易成功的流水号为",sjyhLst.getReqSysStance(),"数据添加到第",infoNum,"行"));
										infoNum++;
										bw.newLine();
										saveDataNum++;
									}
								}
							} else {
								log.info("不存在交易日期为" + date + "的盛京银行的交易成功数据");
							}
							
							if (list_creditcard != null && list_creditcard.size() > 0) {
								log.info("将信用卡交易成功数据添加到Label中");
								int currentColumn = 0;
								for (CreditcardpayTradeLst creditCardLst : list_creditcard) {
									Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
									String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(creditCardLst.getTerminalInfo());
									//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
									if((customObject.getGenerateNumber() == 2 && map.containsKey(creditCardLst.getReqMerCode()) && "0".equals(map.get(creditCardLst.getReqMerCode()).toString())) 
											|| (customObject.getGenerateNumber() == 3 && map.containsKey(trade_code)) 
											|| (customObject.getGenerateNumber() == 1)){
										for (Object object : customDzFileInfoList) {
											Object[] o = (Object[]) object;
											// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
											String content = InnerTradeBeanValueMap
													.getInnerObjectValue(
															o[2].toString(), creditCardLst)
															+ "";
											
											content = ruleHandleContent(o, content);
											Label value = new Label(currentColumn++, infoNum,
													content);
											wsheet.addCell(value);
											String txtContent = InnerTradeBeanValueMap
													.getInnerObjectValueForTxt(
															o[2].toString(), creditCardLst,o[9].toString())
															+ "";
											txtContent = ruleHandleContent(o, txtContent);
											if(currentColumn == customDzFileInfoList.size()){
												if(o[9]==null || StringUtils.isBlank(o[9].toString())){
													bw.write(String.format("%-60s", txtContent));
												}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
													bw.write(txtContent);
												}else{
													bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
												}
											}else{
												if(o[9]==null || StringUtils.isBlank(o[9].toString())){
													bw.write(String.format("%-60s", txtContent)+ "|");
												}else if("merNameOrAddress".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
													bw.write(txtContent + "|");
												}else{
													bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
												}
											}
										}
										
										total_trade_amount = total_trade_amount + (creditCardLst.getTradeAmount()==null?0d:Double.valueOf(creditCardLst.getTradeAmount())/100);
										
										total_fee_amount = total_fee_amount + Double.valueOf(creditCardLst.getTradeFee().substring(1, creditCardLst.getTradeFee().length()))/100;
										currentColumn = 0;
										i++;
										log.info(stringPingJie.getStringPingJie("将信用卡的交易成功的流水号为",creditCardLst.getReqSysStance(),"数据添加到第",infoNum,"行"));
										infoNum++;
										bw.newLine();
										saveDataNum++;
									}
								}
							} else {
								log.info(stringPingJie.getStringPingJie("不存在交易日期为" , date , "的信用卡交易成功数据"));
							}
						}
					}else{
						MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, date,customObject.getObjectName()+"没有配置对账字段，生成对账文件失败，请查看.");
					}
					if(trade_amount_column == 0 || trade_fee_column == 0){
						Label totalDataSize = new Label(0, i+1, saveDataNum + "");
						wsheet.addCell(totalDataSize);
					}else{
						Label totalDataSize = new Label(0, i, saveDataNum + "");
						wsheet.addCell(totalDataSize);
					}
				
					if(trade_amount_column != -1){
						java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
						nf.setGroupingUsed(false);
						Label totalTradeAmount_ = new Label(trade_amount_column, i,nf.format(total_trade_amount));
						wsheet.addCell(totalTradeAmount_);
					}
					if(trade_fee_column != -1){
						Label totalFeeAmount = new Label(trade_fee_column, i,
								(0 == total_fee_amount) ? "0.0" : String.format("%.2f",
										total_fee_amount));
						wsheet.addCell(totalFeeAmount);
					}
				
				// 设置列宽
				wsheet.setColumnView(0, 10);
				wsheet.setColumnView(1, 20);
				wsheet.setColumnView(2, 25);
				wsheet.setColumnView(3, 15);
				wsheet.setColumnView(4, 15);
				wsheet.setColumnView(5, 15);
				wsheet.setColumnView(6, 15);
				wsheet.setColumnView(7, 10);
				wsheet.setColumnView(8, 20);
				wsheet.setColumnView(9, 15);
				wsheet.setColumnView(10, 20);
				wsheet.setColumnView(11, 20);
				wsheet.setColumnView(12, 25);
				wsheet.setColumnView(13, 20);
				wsheet.setColumnView(14, 20);
				wsheet.setColumnView(15, 20);
				wsheet.setColumnView(16, 20);
				wsheet.setColumnView(17, 20);
				wsheet.setColumnView(18, 10);
				wsheet.setColumnView(19, 30);
				wsheet.setColumnView(20, 10);
				wsheet.setColumnView(21, 10);
				wsheet.setColumnView(22, 30);
				wsheet.setColumnView(23, 20);
				wsheet.setColumnView(24, 20);
				wsheet.setColumnView(25, 20);
				wsheet.setColumnView(26, 20);
				// 写入文件
				wbook.write();
				wbook.close();
				bw.flush();
				bw.close();
				fw.close();
				createFileFlag = true;
				log.info(stringPingJie.getStringPingJie("日期为" , date , "txt文件创建成功"));
				log.info(stringPingJie.getStringPingJie("日期为" , date , "excel文件创建成功!"));
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
		return createFileFlag;
	}
}
