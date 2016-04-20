package cn.com.chinaebi.dz.base;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
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

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.CustomInstConfig;
import cn.com.chinaebi.dz.object.CustomObject;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.util.FileStatus;
import cn.com.chinaebi.dz.object.util.FileUtil;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.object.util.RegularExpressionUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.FindTradeCodeUtil;

public class CustomDzFileCreate {

	private static Log log = LogFactory.getLog(CustomDzFileCreate.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.ObjectRelevanceColumnDAO objectRelevanceColumnDAO = cn.com.chinaebi.dz.object.dao.ObjectRelevanceColumnDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.MerTradecodeDAO merTradecodeDAO = cn.com.chinaebi.dz.object.dao.MerTradecodeDAO.getInstance();

	private static cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO = cn.com.chinaebi.dz.object.dao.TradeLstDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = cn.com.chinaebi.dz.object.dao.HlogDAO.getInstance();
	
	private static cn.com.chinaebi.dz.object.dao.iface.CustomInstConfigDAO customInstConfigDAO = cn.com.chinaebi.dz.object.dao.CustomInstConfigDAO.getInstance();
	
	/**
	 * 创建一般对账文件
	 * @param date 文件日期
	 * @param customObject 系统接口对象
	 * @return
	 */
	public static synchronized boolean createDzFile(String date,CustomObject customObject) throws Exception {
		log.info("进入生成对账文件方法……");
		boolean flag_excel = false;
		boolean flag_txt = false;
		
		Double total_trade_amount = 0.0;// 总交易金额
		Double total_fee_amount = 0.0;// 总手续费
		
		String path = "";
		String dzFileName = "";
		Integer saveDataNum = 0;
		
		
		if(customObject != null ){
			log.info("获取系统对象ID："+customObject.getId()+",系统名称为："+customObject.getObjectName()+",系统文件存放路径为："+customObject.getFileAddress()+",系统对账文件总表名称为："+customObject.getDzFileName());
			path = customObject.getFileAddress();//系统接口的文件地址
			dzFileName = customObject.getDzFileName();//系统接口的对账文件名称
			
			try {
				
				File f = new File(path+ "/" +date);
				if (!f.exists()) {
					f.mkdirs();
				}
				
				WritableWorkbook wbook = Workbook.createWorkbook(new File(path+ "/" + date + "/" + date + dzFileName + ".xls"));
				WritableSheet wsheet = wbook.createSheet("电银对账文件", 0);
				// 设置excel里的字体
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);
				// 给标题规定字体的格式
				WritableCellFormat titleFormat = new WritableCellFormat(wf);

				
				File file = new File(path+ "/" + date + "/" + date + dzFileName + ".txt");
				if (file.exists()) {
					log.info(date + dzFileName + ".txt对账总表已经存在，执行删除操作");
					boolean flag = file.delete();
					if (flag) {
						log.info("删除" + date + dzFileName + ".txt对账总表成功");
					}

				} else {
					log.info(date + dzFileName + ".txt对账总表不存在,不需要删除");
				}

				List<Object> customDzFileInfoList = objectRelevanceColumnDAO.queryCustomDzFileInfo(customObject.getId(),FileStatus.DZ_FILE_TYPE);
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				int titleNum = 0;
				int i = 0;
				int trade_amount_column = -1;
				int trade_fee_column = -1;
				int totalDataNum = 0;
				if(customDzFileInfoList !=null && customDzFileInfoList.size() > 0){
					
					for (Object object : customDzFileInfoList) {
						Object[] o = (Object[]) object;
						if ("tradeAmount".equalsIgnoreCase(o[2].toString())) {
							trade_amount_column = titleNum;
						}
						if ("zfFileFee".equalsIgnoreCase(o[2].toString())) {
							trade_fee_column = titleNum;
						}
						Label excelTitle = new Label(titleNum++, 0, o[1].toString(),
								titleFormat);
						wsheet.addCell(excelTitle);
						int hz = RegularExpressionUtil.statisticalChineseNumber(o[1].toString());
						int column_length = Integer.valueOf(o[9].toString()).intValue();
						int x = column_length - hz;
						log.info(o[1].toString()+"的自身长度为"+hz+"对应的字段设置宽度为"+column_length+"得到的列的宽度为"+x);
						bw.write(String.format("%-"+x+"s", o[1].toString()) + "|");
					}
					i++;
					bw.newLine();
					bw.write("          ");
					bw.newLine();
					int infoNum = 1;
					
					Field field_mer_code = null;
					Field field_trade_code = null;
					
					Map<InstInfoPK,Object> instInfoMap = Backstage.getInstance().getOutDzInstMap();
					
					Map<InstInfoPK,Object> bankMap = new HashMap<InstInfoPK,Object>();
					
					if(customObject.isWhetherCreateFileByInst()){
						List<CustomInstConfig> customInstConfigList = customInstConfigDAO.getCustomInstConfig(customObject.getId());//系统接口相关渠道配置信息
						if(customInstConfigList != null){
							InstInfoPK instInfo_pk = null;
							InstInfo instInfo = null;
							for (CustomInstConfig customInstConfig : customInstConfigList) {
								if(customInstConfig.getInstId() != null && customInstConfig.getInstType() != null){
									instInfo_pk = new InstInfoPK(customInstConfig.getInstId(),customInstConfig.getInstType());
									if(instInfoMap.containsKey(instInfo_pk)){
										instInfo = Backstage.getInstance().getInstInfo(instInfo_pk);
										if(instInfo != null){
											if(instInfo.getBank().getId() != 0){
												bankMap.put(instInfo.getId(),instInfo);
											}
										}
									}
								}
							}
						}
					}else{
						bankMap = instInfoMap;
					}
					
					log.info("当前系统接口配置下的渠道数量为："+bankMap.size());
					
					//customDzFileInfoList遍历得到的数组中各位置对应的值意义为：
					//0-文件类型；1-显示名称；2-线下字段属性值；3-字段属性名称；
					//4-规则类型；5-新值；6-旧值；7-规则模板函数；
					//8-应用规则的字段属性值；9-字段长度；10-规则ID；11-线上字段属性值；
					if(customObject.getFileNeedOnlineData() == 1 || customObject.getFileNeedOnlineData() == 2){//判断系统接口生成文件时需要数据类型：1-线下数据，2-线上和线下数据，3-线上数据
						
						Field field = null;
						String content = "";
						String txtContent = "";
						if(bankMap != null){
							InstInfo instInfo = null;
							BankInst bankInst = null;
							for (InstInfoPK instInfopk : bankMap.keySet()) {
								instInfo = Backstage.getInstance().getInstInfo(instInfopk);
								bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
								if(instInfo != null){
									if(instInfo.getId().getInstType() == 0){
										List<?> offLinelist = null;
										List<?> offLineRollBkList = null;
										try{
											if(StringUtils.isNotBlank(bankInst.getInstEntityName()) && instInfo.isWhetherOuterDz()){
												Class<?> c = Class.forName(bankInst.getInstEntityName());
												
												offLinelist = tradeLstDAO.queryQsData(c, bankInst.getOriginalDataTableName(),date,instInfo.getId().getInstId());
												offLineRollBkList = tradeLstDAO.queryQsDataOfRollBk(c, bankInst.getOriginalDataTableName(), date,instInfo.getId().getInstId());
												
												Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
												if (offLinelist != null && offLinelist.size() > 0) {
													log.info("将"+instInfo.getName()+"非冲正对账成功数据添加到文件中");
													int currentColumn = 0;
													for (Object bean : offLinelist) {
														field_trade_code = bean.getClass().getSuperclass().getDeclaredField("terminalInfo");
														field_trade_code.setAccessible(true);
														String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(field_trade_code.get(bean).toString());
														//判断系统对账文件生成类型：1-全部数据;2-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;3-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
														
														boolean flag = false;
														/*
														 * 是按数据范围生成数据,表示文件中只会出现该配置数据
														 */
														if(customObject.isWhetherCreateFileByRange()){
															if(customObject.getGenerateNumber() == 2){
																field_mer_code = bean.getClass().getSuperclass().getDeclaredField("reqMerCode");
																field_mer_code.setAccessible(true);
																if(map.containsKey(field_mer_code.get(bean).toString())){
																	flag = true;
																}
															}else if(customObject.getGenerateNumber() == 3){
																if(map.containsKey(trade_code)){
																	flag = true;
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
																field_mer_code = bean.getClass().getSuperclass().getDeclaredField("reqMerCode");
																field_mer_code.setAccessible(true);
																if(!map.containsKey(field_mer_code.get(bean).toString())){
																	flag = true;
																}
															}else if(customObject.getGenerateNumber() == 3){
																if(!map.containsKey(trade_code)){
																	flag = true;
																}
															}else if(customObject.getGenerateNumber() == 1){
																flag = true;
															}
														}
														
														
														if(flag){
															totalDataNum++ ;
															for (Object object : customDzFileInfoList) {
																Object[] o = (Object[]) object;
																// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
																try {
																	
																	content = FileUtil.parseTradeDataInfo(o[2].toString(), instInfo,bankInst, bean,o[9].toString());
																	
																	if(StringUtils.isBlank(content)){
																		field = bean.getClass().getSuperclass().getDeclaredField(o[2].toString());
																		field.setAccessible(true); //设置些属性是可以访问的
																		content = (field.get(bean) == null || field.get(bean).toString().equals("null")) ? "" : field.get(bean).toString();
																		txtContent = (field.get(bean) == null || field.get(bean).toString().equals("null")) ? "" : field.get(bean).toString();
																	}
																	
																	if("null".equalsIgnoreCase(content)){
																		content = "";
																	}
																	
																	txtContent = content ;
																	
																	content = ruleHandleContent(o, content);
																	
																	txtContent = ruleHandleContent(o, txtContent);
																	
																	if("additionalData".equalsIgnoreCase(o[2].toString())){
																		txtContent = (StringUtils.isBlank(txtContent) || "null".equals(txtContent))?"":txtContent.length()>=20?txtContent.substring(0, 20):txtContent;
																	}
																	
																} catch (NoSuchFieldException eee) {
																	content = "";
																	txtContent = "";
																}
																Label value = new Label(currentColumn++, infoNum,content);
																wsheet.addCell(value);
																if(currentColumn == customDzFileInfoList.size()){
																	if(o[9]==null || StringUtils.isBlank(o[9].toString())){
																		bw.write(String.format("%-60s", txtContent));
																	}else if("merName".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
																		bw.write(txtContent);
																	}else{
																		bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
																	}
																}else{
																	if(o[9]==null || StringUtils.isBlank(o[9].toString())){
																		bw.write(String.format("%-60s", txtContent)+ "|");
																	}else if("merName".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
																		int hz = RegularExpressionUtil.statisticalChineseNumber(txtContent);
																		int column_length = Integer.valueOf(o[9].toString()).intValue();
																		int x = column_length - hz;
																		bw.write(String.format("%-"+x+"s", txtContent) + "|");
																	}else{
																		bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
																	}
																}
															}
															field = bean.getClass().getSuperclass().getDeclaredField("tradeAmount");
															field.setAccessible(true);
															total_trade_amount = total_trade_amount + ( field.get(bean) == null ? 0d : Double.valueOf((field.get(bean)+""))/100 );
															
															field = bean.getClass().getSuperclass().getDeclaredField("zfFileFee");
															field.setAccessible(true);
															total_fee_amount = total_fee_amount + ( field.get(bean) == null ? 0d : Double.valueOf((field.get(bean)+"")));
															currentColumn = 0;
															i++;
															
															field = bean.getClass().getSuperclass().getDeclaredField("deductSysStance");
															field.setAccessible(true);
															log.info("将"+instInfo.getName()+"的非冲正对账成功的流水号为"+field.get(bean)+"数据添加到第"+infoNum+"行");
															infoNum++;
															bw.newLine();
															saveDataNum++;
														}
													}
												} else {
													log.info("不存在清算日期与交易日期为" + date + "的"+instInfo.getName()+"的对账成功数据");
												}
												
												if (offLineRollBkList != null && offLineRollBkList.size() > 0) {
													log.info("将"+instInfo.getName()+"冲正对账成功数据添加到文件中");
													int currentColumn = 0;
													for (Object bean : offLineRollBkList) {
														field_trade_code = bean.getClass().getSuperclass().getDeclaredField("terminalInfo");
														field_trade_code.setAccessible(true);
														String trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(field_trade_code.get(bean).toString());
														//判断系统对账文件生成类型：1-全部数据;2-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;3-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
														
														boolean flag = false;
														/*
														 * 是按数据范围生成数据,表示文件中只会出现该配置数据
														 */
														if(customObject.isWhetherCreateFileByRange()){
															if(customObject.getGenerateNumber() == 2){
																field_mer_code = bean.getClass().getSuperclass().getDeclaredField("reqMerCode");
																field_mer_code.setAccessible(true);
																if(map.containsKey(field_mer_code.get(bean).toString())){
																	flag = true;
																}
															}else if(customObject.getGenerateNumber() == 3){
																if(map.containsKey(trade_code)){
																	flag = true;
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
																field_mer_code = bean.getClass().getSuperclass().getDeclaredField("reqMerCode");
																field_mer_code.setAccessible(true);
																if(!map.containsKey(field_mer_code.get(bean).toString())){
																	flag = true;
																}
															}else if(customObject.getGenerateNumber() == 3){
																if(!map.containsKey(trade_code)){
																	flag = true;
																}
															}else if(customObject.getGenerateNumber() == 1){
																flag = true;
															}
														}
														
														
														if(flag){
															totalDataNum++ ;
															for (Object object : customDzFileInfoList) {
																Object[] o = (Object[]) object;
																// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
																try {
																	content = FileUtil.parseTradeDataInfo(o[2].toString(), instInfo,bankInst,bean,o[9].toString());
																	
																	if(StringUtils.isBlank(content)){
																		field = bean.getClass().getSuperclass().getDeclaredField(o[2].toString());
																		field.setAccessible(true); //设置些属性是可以访问的
																		content = (field.get(bean) == null || field.get(bean).toString().equals("null")) ? "" : field.get(bean).toString();
																		txtContent = (field.get(bean) == null || field.get(bean).toString().equals("null")) ? "" : field.get(bean).toString();
																	}
																	
																	if("null".equalsIgnoreCase(content)){
																		content = "";
																	}
																	
																	txtContent = content ;
																	
																	content = ruleHandleContent(o, content);
																	
																	txtContent = ruleHandleContent(o, txtContent);
																	
																	if("additionalData".equalsIgnoreCase(o[2].toString())){
																		txtContent = (StringUtils.isBlank(txtContent) || "null".equals(txtContent))?"":txtContent.length()>=20?txtContent.substring(0, 20):txtContent;
																	}
																	
																} catch (NoSuchFieldException eee) {
																	content = "";
																	txtContent = "";
																}
																Label value = new Label(currentColumn++, infoNum,content);
																wsheet.addCell(value);
																if(currentColumn == customDzFileInfoList.size()){
																	if(o[9]==null || StringUtils.isBlank(o[9].toString())){
																		bw.write(String.format("%-60s", txtContent));
																	}else if("merName".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
																		bw.write(txtContent);
																	}else{
																		bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
																	}
																}else{
																	if(o[9]==null || StringUtils.isBlank(o[9].toString())){
																		bw.write(String.format("%-60s", txtContent)+ "|");
																	}else if("merName".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
																		int hz = RegularExpressionUtil.statisticalChineseNumber(txtContent);
																		int column_length = Integer.valueOf(o[9].toString()).intValue();
																		int x = column_length - hz;
																		bw.write(String.format("%-"+x+"s", txtContent) + "|");
																	}else{
																		bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
																	}
																}
															}
															field = bean.getClass().getSuperclass().getDeclaredField("tradeAmount");
															field.setAccessible(true);
															total_trade_amount = total_trade_amount + ( field.get(bean) == null ? 0d : Double.valueOf((field.get(bean)+""))/100 );
															
															field = bean.getClass().getSuperclass().getDeclaredField("zfFileFee");
															field.setAccessible(true);
															total_fee_amount = total_fee_amount + ( field.get(bean) == null ? 0d : Double.valueOf((field.get(bean)+"")));
															currentColumn = 0;
															i++;
															
															field = bean.getClass().getSuperclass().getDeclaredField("deductSysStance");
															field.setAccessible(true);
															log.info("将"+instInfo.getName()+"的冲正对账成功的流水号为"+field.get(bean)+"数据添加到第"+infoNum+"行");
															infoNum++;
															bw.newLine();
															saveDataNum++;
														}
													}
												} else {
													log.info("不存在清算日期与交易日期为" + date + "的"+instInfo.getName()+"的冲正对账成功数据");
												}
												
											}
										}catch(Exception e){
											log.error(e);
										}
									}
								}
							}
						}
					}
					if(customObject.getFileNeedOnlineData() == 3 || customObject.getFileNeedOnlineData() == 2){
						Field field = null;
						String content = "";
						String txtContent = "";
						if(bankMap != null){
							InstInfo instInfo = null;
							BankInst bankInst = null;
							for (InstInfoPK instInfopk : bankMap.keySet()) {
								instInfo = Backstage.getInstance().getInstInfo(instInfopk);
								bankInst = Backstage.getInstance().getBankInst(instInfo.getBank().getId());
								if(instInfo != null){
									if(instInfo.getId().getInstType() == 1){
										List<?> onLineList = null;
										try{
											if(StringUtils.isNotBlank(bankInst.getInstEntityName()) && instInfo.isWhetherOuterDz()){
												Class<?> c = Class.forName(bankInst.getInstEntityName());
												
												onLineList = hlogDAO.queryQsData(c, bankInst.getOriginalDataTableName(), date,instInfo.getId().getInstId());
												
												Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
												
												if (onLineList != null && onLineList.size() > 0) {
													
													log.info("将"+instInfo.getName()+"对账成功数据添加到文件中");
													int currentColumn = 0;
													for (Object bean : onLineList) {
														
														boolean flag = false;
														
														//判断系统接口是否按照范围创建文件
														if(customObject.isWhetherCreateFileByRange()){
															if(customObject.getGenerateNumber() == 2){
																field_mer_code = bean.getClass().getSuperclass().getDeclaredField("mid");
																field_mer_code.setAccessible(true);
																if(map.containsKey(field_mer_code.get(bean).toString())){
																	flag = true;
																}
															}else if(customObject.getGenerateNumber() == 1){
																flag = true;
															}
														}else{
															if(customObject.getGenerateNumber() == 2){
																field_mer_code = bean.getClass().getSuperclass().getDeclaredField("mid");
																field_mer_code.setAccessible(true);
																if(!map.containsKey(field_mer_code.get(bean).toString())){
																	flag = true;
																}
															}else if(customObject.getGenerateNumber() == 1){
																flag = true;
															}
														}
														
														if(flag){
															totalDataNum++;
															for (Object object : customDzFileInfoList) {
																Object[] o = (Object[]) object;
																// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
																try {
																	if(o[11] == null){
																		content = "";
																		txtContent = "";
																	}else{
																		content = FileUtil.parseTradeDataInfo(o[11].toString(), instInfo,bankInst, bean,o[9].toString());
																		
																		if(StringUtils.isBlank(content)){
																			field = bean.getClass().getSuperclass().getDeclaredField(o[11].toString());
																			field.setAccessible(true); //设置些属性是可以访问的
																			content = (field.get(bean) == null || field.get(bean).toString().equals("null")) ? "" : field.get(bean).toString();
																			txtContent = (field.get(bean) == null || field.get(bean).toString().equals("null")) ? "" : field.get(bean).toString();
																		}
																		
																		if("null".equalsIgnoreCase(content)){
																			content = "";
																		}
																		
																		txtContent = content ;
																		
																		content = ruleHandleContent(o, content);
																		
																		txtContent = ruleHandleContent(o, txtContent);
																	}
																} catch (NoSuchFieldException eee) {
																	content = "";
																	txtContent = "";
																}
																Label value = new Label(currentColumn++, infoNum,content);
																wsheet.addCell(value);
																if(currentColumn == customDzFileInfoList.size()){
																	if(o[9]==null || StringUtils.isBlank(o[9].toString())){
																		bw.write(String.format("%-60s", txtContent));
																	}else if("merName".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
																		bw.write(txtContent);
																	}else{
																		bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
																	}
																}else{
																	if(o[9]==null || StringUtils.isBlank(o[9].toString())){
																		bw.write(String.format("%-60s", txtContent)+ "|");
																	}else if("merName".equals(o[2].toString()) || "tradeMsgType".equals(o[2].toString()) || "tradeTypeInChinese".equals(o[2].toString()) || "receiviName".equals(o[2].toString()) || "deductSysName".equals(o[2].toString())){
																		int hz = RegularExpressionUtil.statisticalChineseNumber(txtContent);
																		int column_length = Integer.valueOf(o[9].toString()).intValue();
																		int x = column_length - hz;
																		bw.write(String.format("%-"+x+"s", txtContent) + "|");
																	}else{
																		bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
																	}
																}
															}
															field = bean.getClass().getSuperclass().getDeclaredField("amount");
															field.setAccessible(true);
															total_trade_amount = total_trade_amount + ( field.get(bean) == null ? 0d : Double.valueOf((field.get(bean)+""))/100 );
															
															field = bean.getClass().getSuperclass().getDeclaredField("zfFileFee");
															field.setAccessible(true);
															total_fee_amount = total_fee_amount + ( field.get(bean) == null ? 0d : Double.valueOf((field.get(bean)+"")));
															currentColumn = 0;
															i++;
															
															field = bean.getClass().getSuperclass().getDeclaredField("id");
															field.setAccessible(true);
															log.info("将"+instInfo.getName()+"的对账成功的流水号为"+field.get(bean)+"数据添加到第"+infoNum+"行");
															infoNum++;
															bw.newLine();
															saveDataNum++;
														}
													}
												} else {
													log.info("不存在清算日期与交易日期为" + date + "的"+instInfo.getName()+"的对账成功数据");
												}
											}
										}catch(Exception e){
											log.error(e);
										}
									}
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
				
				FileUtil.appendContentMethod((path+ "/" + date + "/" + date + dzFileName + ".txt"), (totalDataNum+""));
				
				flag_txt = true;
				log.info("日期为" + date + "txt文件创建成功");
				log.info("日期为" + date + "excel文件创建成功!");
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
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
					if (o[10] != null && StringUtils.isNotBlank(o[10].toString())) {
						List<Object> list = objectRelevanceColumnDAO.queryReplaceValue(Integer.valueOf(o[10].toString()));
						if(list != null && list.size() > 0){
							if (o[7] != null && StringUtils.isNotBlank(o[7].toString())) {
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
		}
		
		return content;
	}
}
