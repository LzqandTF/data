package cn.com.chinaebi.dz.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
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

import cn.com.chinaebi.dz.object.CustomObject;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;
import cn.com.chinaebi.dz.object.MerchantFundSettle;
import cn.com.chinaebi.dz.object.util.FileStatus;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.object.util.MerchantFundSettleBeanValueMap;
import cn.com.chinaebi.dz.object.util.RegularExpressionUtil;
import cn.com.chinaebi.dz.reload.Backstage;

public class CreateSettleFile {
	
	private static cn.com.chinaebi.dz.object.dao.iface.MerTradecodeDAO merTradecodeDAO = cn.com.chinaebi.dz.object.dao.MerTradecodeDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.ObjectRelevanceColumnDAO objectRelevanceColumnDAO = cn.com.chinaebi.dz.object.dao.ObjectRelevanceColumnDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	private static cn.com.chinaebi.dz.object.dao.iface.MerchantFundSettleDAO merchantFundSettleDAO = cn.com.chinaebi.dz.object.dao.MerchantFundSettleDAO.getInstance();
	
	private static Log log = LogFactory.getLog(CreateSettleFile.class);
	
	/**
	 * 创建结算单
	 * @param date 文件日期
	 * @param customObject 系统接口对象
	 * @return
	 */
	public static synchronized boolean createSettleFile(String date,int startDate,int endDate,int settleAccountType,CustomObject customObject) throws Exception{
		log.info("进入生成结算单方法……");
		boolean flag_excel = false;
		boolean flag_txt = false;
		
		String path = "";
		String dzFileName = "";
		Integer saveDataNum = 0;
		
		
		if(customObject != null ){
			log.info("获取系统对象ID："+customObject.getId()+",系统名称为："+customObject.getObjectName()+",系统文件存放路径为："+customObject.getFileAddress()+",系统结算单名称为："+customObject.getDzFileName());
			path = customObject.getFileAddress();
			dzFileName = customObject.getDzFileName();
			
			List<MerchantFundSettle> settleList = new ArrayList<MerchantFundSettle>();
			
			try {
				
				File f = new File(path+ "/" +date);
				if (!f.exists()) {
					f.mkdirs();
				}
				
				log.info("开始创建excel文件");
				WritableWorkbook wbook = Workbook.createWorkbook(new File(path+ "/" + date
						+ "/" + date + dzFileName + ".xls"));
				WritableSheet wsheet = wbook.createSheet("结算单", 0);
				// 设置excel里的字体
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
						WritableFont.NO_BOLD, false);
				// 给标题规定字体的格式
				WritableCellFormat titleFormat = new WritableCellFormat(wf);

				
				File file = new File(path+ "/" + date + "/" + date
						+ dzFileName + ".txt");
				if (file.exists()) {
					log.info(date + dzFileName
							+ ".txt结算单已经存在，执行删除操作");
					boolean flag = file.delete();
					if (flag) {
						log.info("删除" + date + dzFileName
								+ ".txt结算单成功");
					}

				} else {
					log.info(date + dzFileName
							+ ".txt结算单不存在,不需要删除");
				}

				List<Object> customDzFileInfoList = objectRelevanceColumnDAO
						.queryCustomDzFileInfo(customObject.getId(),
								FileStatus.SETTLE_FILE_TYPE);
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				int titleNum = 0;
				if(customDzFileInfoList !=null && customDzFileInfoList.size() > 0){
					for (Object object : customDzFileInfoList) {
						Object[] o = (Object[]) object;
						Label excelTitle = new Label(titleNum++, 0, o[1].toString(),
								titleFormat);
						wsheet.addCell(excelTitle);
						int hz = RegularExpressionUtil.statisticalChineseNumber(o[1].toString());
						int column_length = Integer.valueOf(o[9].toString()).intValue();
						int x = column_length - hz;
						log.info(o[1].toString()+"的自身长度为"+hz+"对应的字段设置宽度为"+column_length+"得到的列的宽度为"+x);
						bw.write(String.format("%-"+x+"s", o[1].toString()) + "|");
					}
					bw.newLine();
					int infoNum = 1;
					
					settleList = merchantFundSettleDAO.queryMerchantSettleData(startDate, endDate, settleAccountType);
					
					//customDzFileInfoList遍历得到的数组中各位置对应的值意义为：
					//0-文件类型；1-显示名称；2-线下字段属性值；3-字段属性名称；
					//4-规则类型；5-新值；6-旧值；7-规则模板函数；
					//8-应用规则的字段属性值；9-字段长度；10-规则ID；11-线上字段属性值；
					if (settleList != null && settleList.size() > 0) {
						int currentColumn = 0;
						for (MerchantFundSettle merchantFundSettle : settleList) {
							Map<String,Object> map = merTradecodeDAO.getMerCodeMap(customObject.getId());
							//判断系统对账文件生成类型：0-全部数据;1-按商户号生成，并判断当前交易数据的商户号是否在接口数据配置中;2-按交易码生成，并判断当前交易数据的交易码是否在接口数据配置中
							boolean flag = false;
							if(customObject.isWhetherCreateFileByRange()){
								if(customObject.getGenerateNumber() == 2){
									if(map.containsKey(merchantFundSettle.getMerCode())){
//										if("0".equals(map.get(merchantFundSettle.getMerCode()).toString())){
											flag = true;
//										}
									}
								}else if(customObject.getGenerateNumber() == 1){
									flag = true;
								}
							}else{
								if(customObject.getGenerateNumber() == 2){
									if(map.containsKey(merchantFundSettle.getMerCode())){
//										if("0".equals(map.get(merchantFundSettle.getMerCode()).toString())){
											flag = true;
//										}
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
									Object result = MerchantFundSettleBeanValueMap.getSettleFileValueForExcel(o[2].toString(), merchantFundSettle);
									String content = result==null?"":(result+ "");
									
									content = ruleHandleContent(o, content);
									Label value = new Label(currentColumn++, infoNum,
											content);
									wsheet.addCell(value);
									Object resultTxt = MerchantFundSettleBeanValueMap.getSettleFileValueForTxt(o[2].toString(), merchantFundSettle,o[9].toString());
									String txtContent = resultTxt==null?"":(resultTxt+ "");
									txtContent = ruleHandleContent(o, txtContent);
									if(currentColumn == customDzFileInfoList.size()){
										if(o[9]==null || StringUtils.isBlank(o[9].toString())){
											bw.write(String.format("%-60s", txtContent));
										}else if("openAcountName".equals(o[2].toString()) || "settle_mer_name".equals(o[2].toString()) || "openBankName".equals(o[2].toString())){
											bw.write(txtContent);
										}else{
											bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent));
										}
									}else{
										if(o[9]==null || StringUtils.isBlank(o[9].toString())){
											bw.write(String.format("%-60s", txtContent)+ "|");
										}else if("openAcountName".equals(o[2].toString()) || "settle_mer_name".equals(o[2].toString()) || "openBankName".equals(o[2].toString())){
											bw.write(txtContent + "|");
										}else{
											bw.write(String.format("%-"+Integer.valueOf(o[9].toString())+"s", txtContent)+ "|");
										}
									}
								}
								
								currentColumn = 0;
								infoNum++;
								bw.newLine();
								saveDataNum++;
							}
						}
					} else {
						//TODO 日志信息
					}

				}else{
					log.info("该系统没有配置任何自定义结算单字段信息");
					MailSendInfoUtil.sendEmailForInstInfoCreate(MailSendInfoUtil.CREATE_FILE, emailPoliceDAO, date,customObject.getObjectName()+"没有配置结算单字段，生成结算单失败，请查看.");
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
	 * 通过渠道ID获取收单机构名称
	 * @param deductSysId
	 * @return
	 */
	public static String getShouDanInstName(int deductSysId) {
		try {
			Map<InstInfoPK, Object> map_inst = Backstage.getInstance().getInstInfoMap();
			if (map_inst != null) {
				for (Map.Entry<InstInfoPK, Object> entry : map_inst.entrySet()) {
					InstInfo instInfo = (InstInfo) entry.getValue();
					if (instInfo.getId().getInstId() == deductSysId) {
						return instInfo.getReceiviName();
					}
				}
			} else {
				log.info("内存中的机构信息为空！");
			}
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * 通过渠道ID获取渠道名称
	 * @param deductSysId
	 * @return
	 */
	public static String getInstInfoName(int deductSysId) {
		try {
			Map<InstInfoPK, Object> map_inst = Backstage.getInstance()
					.getInstInfoMap();
			if (map_inst != null) {
				for (Map.Entry<InstInfoPK, Object> entry : map_inst.entrySet()) {
					InstInfo instInfo = (InstInfo) entry.getValue();
					if (instInfo.getId().getInstId() == deductSysId) {
						return instInfo.getName();
					}
				}
			} else {
				log.info("内存中的渠道信息为空！");
			}
		} catch (Exception e) {
			log.error(e);
		}
		return null;
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
