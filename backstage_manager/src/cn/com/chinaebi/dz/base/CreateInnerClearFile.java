package cn.com.chinaebi.dz.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
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

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.CustomInstConfig;
import cn.com.chinaebi.dz.object.CustomObject;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.FileStatus;
import cn.com.chinaebi.dz.object.util.FileUtil;
import cn.com.chinaebi.dz.object.util.RegularExpressionUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.FindTradeCodeUtil;

/**
 * 生成不同扣款渠道的内部清算文件
 */
public class CreateInnerClearFile {
	private static Log log = LogFactory.getLog(CreateInnerClearFile.class);

	private static cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO tradeLstDAO = cn.com.chinaebi.dz.object.dao.TradeLstDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.CustomInstConfigDAO customInstConfigDAO = cn.com.chinaebi.dz.object.dao.CustomInstConfigDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ObjectRelevanceColumnDAO objectRelevanceColumnDAO = cn.com.chinaebi.dz.object.dao.ObjectRelevanceColumnDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerTradecodeDAO merTradecodeDAO = cn.com.chinaebi.dz.object.dao.MerTradecodeDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.DzFileTabDAO dzFileTabDAO = cn.com.chinaebi.dz.object.dao.DzFileTabDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ExecuteNodeDAO executeNodeDAO = cn.com.chinaebi.dz.object.dao.ExecuteNodeDAO.getInstance();
	
	/**
	 * 创建内部清算文件方法
	 * @param instInfo 渠道对象
	 * @param status  数据状态
	 * @param date	交易日期
	 * @param customObject  系统接口对象
	 * @return
	 */
	public static boolean createInnerClearFile(String status,String date,CustomObject customObject){
		List<?> list = null;//清算数据集合
		
		
		
		Map<InstInfoPK,Object> instInfoMap = Backstage.getInstance().getInnerJsInstMap();
		
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
		
		boolean createFileFlag = false;
		
		String path = "";
		String dzFileName = "";
		
		if(customObject != null ){
			log.info("获取系统对象ID："+customObject.getId()+",系统名称为："+customObject.getObjectName()+",系统文件存放路径为："+customObject.getFileAddress()+",系统对账文件总表名称为："+customObject.getDzFileName());
			if(StringUtils.isNotBlank(customObject.getFileAddress())){
				path = customObject.getFileAddress();
			}
			dzFileName = customObject.getDzFileName();
			
			int dataCount = (list==null?0:list.size());
			
			try {
				
				File f = new File(path+ "/" +date);
				if (!f.exists()) {
					f.mkdirs();
				}
				
				log.info("开始创建excel文件");
				WritableWorkbook wbook = Workbook.createWorkbook(new File(path+ "/" + date + "/" + date + dzFileName + ".xls"));
				
				WritableSheet wsheet = wbook.createSheet("内部清算文件", 0);
				
				// 设置excel里的字体
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);
				
				// 给标题规定字体的格式
				WritableCellFormat titleFormat = new WritableCellFormat(wf);
				
				File file = new File(path+ "/" + date + "/" + date + dzFileName + ".txt");
				if (file.exists()) {
					log.info(date + dzFileName+ ".txt对账总表已经存在，执行删除操作");
					boolean flag = file.delete();
					if (flag) {
						log.info("删除" + date + dzFileName+ ".txt对账总表成功");
					}

				} else {
					log.info(date + dzFileName+ ".txt对账总表不存在,不需要删除");
				}

				List<Object> customDzFileInfoList = objectRelevanceColumnDAO.queryCustomDzFileInfo(customObject.getId(),FileStatus.INNER_CLEAR_FILE_TYPE);
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				int titleNum = 0;
				int i = 0;
				int saveDataNum = 0;
				if(customDzFileInfoList !=null && customDzFileInfoList.size() > 0){
					for (Object object : customDzFileInfoList) {
						Object[] o = (Object[]) object;
						Label excelTitle = new Label(titleNum++, 0, o[1].toString(),titleFormat);
						wsheet.addCell(excelTitle);
						int hz = RegularExpressionUtil.statisticalChineseNumber(o[1].toString());
						int column_length = Integer.valueOf(o[9].toString()).intValue();
						int x = column_length - hz;
						log.info(o[1].toString()+"的自身长度为"+hz+"对应的字段设置宽度为"+column_length+"得到的列的宽度为"+x);
						bw.write(String.format("%-"+x+"s", o[1].toString()) + "|");
					}
					i++;
					bw.newLine();
					bw.write(dataCount + "");
					bw.newLine();
					int infoNum = 1;
					//customDzFileInfoList遍历得到的数组中各位置对应的值意义为：
					//0-文件类型；1-显示名称；2-线下字段属性值；3-字段属性名称；
					//4-规则类型；5-新值；6-旧值；7-规则模板函数；
					//8-应用规则的字段属性值；9-字段长度；10-规则ID；11-线上字段属性值；
					
					if(bankMap != null){
						InstInfo inst = null;
						BankInst bankInst = null;
						Field field = null;
						String content = "";
						String txtContent = "";
						Field field_mer_code = null;
						Field field_trade_code = null;
						Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
						for (InstInfoPK instInfopk : bankMap.keySet()) {
							inst = Backstage.getInstance().getInstInfo(instInfopk);
							if(inst != null){
								bankInst = Backstage.getInstance().getBankInst(inst.getBank().getId());
								try{
									if(StringUtils.isNotBlank(bankInst.getInstEntityName())){
										Class<?> c = Class.forName(bankInst.getInstEntityName());
										
										list = tradeLstDAO.queryInnerClearData(c, bankInst.getOriginalDataTableName(),inst.getId().getInstId(), status, date,bankInst.getBankType());
										
										log.info("获得"+inst.getName()+"数据条数为"+(list==null?0:list.size())+"条");
									}
								}catch(Exception e){
									log.error(e);
								}
								
								if (list != null && list.size() > 0) {
									int currentColumn = 0;
									for (Object bean : list) {
										
										String trade_code = "";
										
										if(instInfopk.getInstType() == 0){
											field_trade_code = bean.getClass().getSuperclass().getDeclaredField("terminalInfo");
											field_trade_code.setAccessible(true);
											trade_code = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(field_trade_code.get(bean).toString());
										}
										
										//判断系统对账文件生成类型：1-全部数据;2-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;3-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
										
										boolean flag = false;
										/*
										 * 是按数据范围生成数据,表示文件中只会出现该配置数据
										 */
										if(customObject.isWhetherCreateFileByRange()){
											if(customObject.getGenerateNumber() == 2){
												if(instInfopk.getInstType() == 0){
													field_mer_code = bean.getClass().getSuperclass().getDeclaredField("reqMerCode");
												}else{
													field_mer_code = bean.getClass().getSuperclass().getDeclaredField("mid");
												}
												
												field_mer_code.setAccessible(true);
												if(map.containsKey(field_mer_code.get(bean).toString())){
													flag = true;
												}
											}else if(customObject.getGenerateNumber() == 3){
												if(StringUtils.isNotBlank(trade_code)){
													if(map.containsKey(trade_code)){
														flag = true;
													}
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
												if(instInfopk.getInstType() == 0){
													field_mer_code = bean.getClass().getSuperclass().getDeclaredField("reqMerCode");
												}else{
													field_mer_code = bean.getClass().getSuperclass().getDeclaredField("mid");
												}
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
											for (Object object : customDzFileInfoList) {
												Object[] o = (Object[]) object;
												// o[4]:处理类型（0-所有；1-部分）,o[5]:新值,o[6]:旧值,o[7]:模板ID,o[8]:应用规则属性值,o[9]:字段长度
												try {
													
													content = FileUtil.parseTradeDataInfo(o[2].toString(), inst,bankInst, bean,o[9].toString());
													
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
											if(instInfopk.getInstType() == 0){
												field = bean.getClass().getSuperclass().getDeclaredField("reqSysStance");
											}else{
												field = bean.getClass().getSuperclass().getDeclaredField("tseq");
											}
											
											field.setAccessible(true);
											log.info("将"+inst.getName()+"的流水号为"+field.get(bean)+"的成功数据添加到第"+infoNum+"行");
										}
										
										currentColumn = 0;
										i++;
										infoNum++;
										bw.newLine();
										saveDataNum++;
									}
								} else {
									log.info("不存在交易日期为" + date + "的"+inst.getName()+"的交易成功数据");
								}
							}
						}
					}
				}else{
					log.info("没有配置对账字段，生成内部清算文件失败，请查看");
				}
				Label totalDataSize = new Label(0, i, saveDataNum + "");
				wsheet.addCell(totalDataSize);
			
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
				FileUtil.appendContentMethod((path+ "/" + date + "/" + date + dzFileName + ".txt"), (saveDataNum+""));
				log.info("日期为" + date + "txt文件创建成功");
				log.info("日期为" + date + "excel文件创建成功!");
			} catch (Exception e) {
				log.error(e);
			}
		}
		InstInfo inst_info_update = null;
		BankInst bankInst = null;
		for (InstInfoPK instInfopk : bankMap.keySet()) {
			if(createFileFlag){
				inst_info_update = Backstage.getInstance().getInstInfo(instInfopk);
				bankInst = Backstage.getInstance().getBankInst(inst_info_update.getBank().getId());
				//将原始交易数据表中数据是否内部清算状态修改为已内部清算
				boolean updateResult = tradeLstDAO.updateInnerClearDataInnerJsStatus(bankInst.getOriginalDataTableName(),inst_info_update.getId().getInstId(), status, date,bankInst.getBankType());
				
				if(updateResult){
					log.info("渠道"+inst_info_update.getName()+"交易日期为"+date+"的原始交易数据表中交易成功数据是否内部清算状态修改成功");
				}else{
					log.info("渠道"+inst_info_update.getName()+"交易日期为"+date+"的原始交易数据表中交易成功数据是否内部清算状态修改失败");
				}
			}
			executeNodeDAO.updateExecuteNodeStatus("dz_file_create", inst_info_update.getId().getInstId(), date, createFileFlag?"1":"2",inst_info_update.getName(),inst_info_update.getId().getInstType());
		}
		
		String file_name = date+customObject.getDzFileName();
		String file_type = "内部清算文件";
		try {
			dzFileTabDAO.insertDzFileData(date, file_name, file_type,DYDataUtil.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),customObject.getFileAddress(),customObject.getId(),customObject.getObjectName());
		} catch (Exception e) {
			log.error("将系统接口名称为"+customObject.getObjectName()+"的内部清算文件"+"file_name"+"插入文件表失败,抛出异常");
		}
		
		return createFileFlag;
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
}
