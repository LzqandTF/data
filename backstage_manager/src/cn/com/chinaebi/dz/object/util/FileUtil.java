package cn.com.chinaebi.dz.object.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.dao.iface.ErrorDataLstDAO;
import cn.com.chinaebi.dz.object.dao.iface.TradeAmountConfDAO;
import cn.com.chinaebi.dz.object.dao.iface.TradeLstDAO;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.util.DYDataUtil;

public class FileUtil {
	
	private static Log log = LogFactory.getLog(FileUtil.class);
	
	private static TradeAmountConfDAO tradeAmountConfDao = cn.com.chinaebi.dz.object.dao.TradeAmountConfDAO.getInstance();
	
	private static  ErrorDataLstDAO errorData =  cn.com.chinaebi.dz.object.dao.ErrorDataLstDAO.getInstance();  
	
	private static TradeLstDAO tradeLstDAO = cn.com.chinaebi.dz.object.dao.TradeLstDAO.getInstance();
	
	/**
	 * 向文件中追加内容
	 * @param fileName 指定路径下的文件名
	 * @param content  待追加内容
	 */
	public static void appendContentMethod(String fileName, String content){
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
//			long fileLength = randomFile.length();
			//将写文件指针移到文件尾。
//			randomFile.seek(fileLength);
			randomFile.readLine();//跳过首行
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e){
			log.error("在文件中追加或插入内容抛出异常：");
			log.error(e.getMessage());
		}
	}
	
	
	/**
	 * 处理 交易数据中的特殊信息
	 * @param column  待处理字段属性
	 * @param content  待处理内容
	 * @param inst_type  渠道类型
	 * @return
	 */
	public static String parseTradeDataInfo(String column,InstInfo instInfo,BankInst bankInst,Object bean,String columnLength){
		Field field = null;
		String content = "";
		try {
			if(StringUtils.isNotBlank(column)){
				if(instInfo.getId().getInstType() == 0){
					if(column.equalsIgnoreCase("tradeAmount")){
						field = bean.getClass().getSuperclass().getDeclaredField("tradeAmount");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						
						content = String.format("%.2f",StringUtils.isBlank(content)?0d:Double.valueOf(content)/100);
					}else if(column.equalsIgnoreCase("tradeFee")){
						field = bean.getClass().getSuperclass().getDeclaredField("tradeFee");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						
						content = StringUtils.isBlank(content)?"0.0":String.format("%.2f",Double.valueOf(content.substring(1,content.length()))/100);
					}else if(column.equalsIgnoreCase("tradeMsgType")){
						field = bean.getClass().getSuperclass().getDeclaredField("reqProcess");
						field.setAccessible(true); //设置些属性是可以访问的
						String reqProcess = field.get(bean) == null ? "" : field.get(bean).toString();
						
						field = bean.getClass().getSuperclass().getDeclaredField("trademsgType");
						field.setAccessible(true); //设置些属性是可以访问的
						String tradeMsgType = field.get(bean) == null ? "" : field.get(bean).toString();
						
						if(StringUtils.isNotBlank(tradeMsgType)){
							content = tradeAmountConfDao.getTradeTypeName(Integer.valueOf(tradeMsgType), reqProcess);
						}
						
					}else if(column.equalsIgnoreCase("tradeType")){
						field = bean.getClass().getSuperclass().getDeclaredField("terminalInfo");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						content = FindTradeCodeUtil.getNewInstance().tradeCodeSubString(content);
					}else if(column.equalsIgnoreCase("tradeTypeInChinese")){
						field = bean.getClass().getSuperclass().getDeclaredField("terminalInfo");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						content = FindTradeCodeUtil.getNewInstance().returnTradeCodeName(content, errorData);
					}else if(column.equalsIgnoreCase("originalTransInfo")){
						
						field = bean.getClass().getSuperclass().getDeclaredField("deductRollBk");
						field.setAccessible(true); //设置些属性是可以访问的
						String deductRollBk = field.get(bean) == null ? "" : field.get(bean).toString();
						
						if("1".equals(deductRollBk)){
							field = bean.getClass().getSuperclass().getDeclaredField("deductRollBkStance");
							field.setAccessible(true); //设置些属性是可以访问的
							content = field.get(bean) == null ? "" : field.get(bean).toString();
						}else{
							field = bean.getClass().getSuperclass().getDeclaredField("originalTransInfo");
							field.setAccessible(true); //设置些属性是可以访问的
							content = field.get(bean) == null ? "" : field.get(bean).toString();
						}
						
						content = (StringUtils.isBlank(content) || "null".equals(content))?"":content;
					}else if(column.equalsIgnoreCase("originalTransTime")){
						
						field = bean.getClass().getSuperclass().getDeclaredField("deductRollBk");
						field.setAccessible(true); //设置些属性是可以访问的
						String deductRollBk = field.get(bean) == null ? "" : field.get(bean).toString();
						
						if("1".equals(deductRollBk)){
							field = bean.getClass().getSuperclass().getDeclaredField("deductRollbkSysTime");
							field.setAccessible(true); //设置些属性是可以访问的
							content = field.get(bean) == null ? "" : field.get(bean).toString();
						}else{
							field = bean.getClass().getSuperclass().getDeclaredField("originalTransInfo");
							field.setAccessible(true); //设置些属性是可以访问的
							String originalTransInfo = field.get(bean) == null ? "" : field.get(bean).toString();
							
							field = bean.getClass().getSuperclass().getDeclaredField("deductStlmDate");
							field.setAccessible(true); //设置些属性是可以访问的
							String deductStlmDate = field.get(bean) == null ? "" : field.get(bean).toString();
							
							field = bean.getClass().getSuperclass().getDeclaredField("trademsgType");
							field.setAccessible(true); //设置些属性是可以访问的
							String tradeMsgType = field.get(bean) == null ? "" : field.get(bean).toString();
							
							content = tradeLstDAO.getOriginalTradeTimeOfCancel(originalTransInfo, Integer.valueOf(tradeMsgType), deductStlmDate.toString().substring(0, 10), bankInst.getOriginalDataTableName());
						}
					}else if(column.equalsIgnoreCase("deductStlmDate")){
						field = bean.getClass().getSuperclass().getDeclaredField("deductStlmDate");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						
						content = StringUtils.isBlank(content)?"":content.substring(0,10).replace("-", "");
					}else if(column.equalsIgnoreCase("tradeTime")){
						field = bean.getClass().getSuperclass().getDeclaredField("deductSysTime");
						field.setAccessible(true); //设置些属性是可以访问的
						String deductSysTime = field.get(bean) == null ? "" : field.get(bean).toString();
						
						content = StringUtils.isBlank(deductSysTime)?"":deductSysTime.substring(0, deductSysTime.length()-2).replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
					}else if(column.equalsIgnoreCase("receiviName")){
						content = instInfo.getReceiviName();
					}else if(column.equalsIgnoreCase("deductSysName")){
						content = instInfo.getName();
					}else if(column.equalsIgnoreCase("deductRollBk")){
						field = bean.getClass().getSuperclass().getDeclaredField("deductRollBk");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						
						content = content.equals("true")?"1":"0";
					}else if(column.equalsIgnoreCase("merName")){
						field = bean.getClass().getSuperclass().getDeclaredField("reqMerCode");
						field.setAccessible(true); //设置些属性是可以访问的
						String reqMerCode = field.get(bean) == null ? "" : field.get(bean).toString();
						
						MerBasic merBasic = Backstage.getInstance().getMerBasic(reqMerCode);
						content = merBasic==null?"":merBasic.getMerAbbreviation();
					}else if(column.equalsIgnoreCase("gate")){
						content = instInfo.getGate()+"";
					}else if(instInfo.getId().getInstId() == 12){//内部清算文件，盛京银行扣款渠道ID字段特殊，进行特别处理
						if(column.equalsIgnoreCase("deductSysId")){
							field = bean.getClass().getSuperclass().getDeclaredField("gainSysId");
							field.setAccessible(true); //设置些属性是可以访问的
							content = field.get(bean) == null ? "" : field.get(bean).toString();
						}
					}
				}else if(instInfo.getId().getInstType() == 1){
					if(column.equalsIgnoreCase("receiviName")){
						content = instInfo.getReceiviName();
					}else if(column.equalsIgnoreCase("deductSysName")){
						content = instInfo.getName();
					}else if(column.equalsIgnoreCase("deductRollBk")){
						content = "0";
					}else if(column.equalsIgnoreCase("merName")){
						field = bean.getClass().getSuperclass().getDeclaredField("mid");
						field.setAccessible(true); //设置些属性是可以访问的
						String reqMerCode = field.get(bean) == null ? "" : field.get(bean).toString();
						
						MerBasic merBasic = Backstage.getInstance().getMerBasic(reqMerCode);
						content = merBasic==null?"":merBasic.getMerAbbreviation();
					}else if(column.equals("amount")){
						field = bean.getClass().getSuperclass().getDeclaredField("amount");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						
						content = String.format("%.2f",StringUtils.isBlank(content)?0d:Double.valueOf(content)/100);
					}else if(column.equals("bankFee")){
						field = bean.getClass().getSuperclass().getDeclaredField("bankFee");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						
						content = String.format("%.2f",StringUtils.isBlank(content)?0d:Double.valueOf(content)/100);
					}else if(column.equals("feeAmt")){
						field = bean.getClass().getSuperclass().getDeclaredField("feeAmt");
						field.setAccessible(true); //设置些属性是可以访问的
						content = field.get(bean) == null ? "" : field.get(bean).toString();
						
						content = String.format("%.2f",StringUtils.isBlank(content)?0d:Double.valueOf(content)/100);
					}else if(column.equalsIgnoreCase("gate")){
						content = instInfo.getGate()+"";
					}
				}
			}
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (SecurityException e) {
			log.error(e);
		} catch (NoSuchFieldException e) {
			log.error(e);
		} catch(Exception e){
			log.error(e);
		}
		return StringUtils.isBlank(content)?"":content;
	}
	
	/**
     * 针对交易请求日期做格式化处理
     * 
     * @param date  格式yyyyMddHHmm或yyyyMddHmmss或yyyyMdHHmm 转换为 yyyyMMddHHmmss
     * @return
     */
    public static String formatDateTime(String date){
    	if(StringUtils.isNotBlank(date)){
    		if(Integer.valueOf(date.substring(4, 6)) > 12){
    			date = date.substring(0, 4) + "0" + date.substring(4, date.length());
    		}
    		
    		if(date.length() == 11){
    			date = date.substring(0,8)+"0"+date.substring(8,date.length());
    		}
    		
			date = StringUtils.rightPad(date, 14, "0");
			return date;
    	}
    	return date;
    }
    
    /**
     * 针对CSV文件 日期格式转换
     * @param date
     * @return
     */
    public static String formatDateTimeToYYYYMMDDHHmmss(String date){
    	if(StringUtils.isNotBlank(date)){
    		if(date.indexOf(" ") > -1){
    			String[] dates = date.split(" ");
        		String YMD = dates[0];
        		if(YMD.indexOf("-") > -1){
        			String[] YMDs = YMD.split("-");
        			if(YMDs.length > 2){
        				String year = YMDs[0];
        				String month = YMDs[1].length() > 1 ? YMDs[1] : ("0" + YMDs[1]);
        				String day = YMDs[2].length() > 1 ? YMDs[2] : ("0" + YMDs[2]);
        				date = year + month + day;
        			}
        		}
        		String Hms = dates[1];
        		date = date + Hms.replaceAll(":", "");
        		date = StringUtils.rightPad(date, 14, "0");
    			return date;
    		}
    	}
    	return date;
    }
    
    /**
     * 将日期字符串转换为 YYYYMMddhhmmss 格式，长度不足右补零
     * @param date
     * @return
     */
    public static String formatDataTimeToYYYYMMddhhmmss(String date){
    	if(StringUtils.isNotBlank(date)){
    		date = date.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
    		if(date.length() < 14){
    			date = StringUtils.rightPad(date, 14, "0");
    		}
    	}
    	return date;
    }
    
    public static String getFileName(String namePattern,Date date){
    	String date_pattern = "";
    	String file_name = "";
    	if(namePattern.contains(DataStatus.date_format_1)){
			date_pattern = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_1).format(date);
			file_name = namePattern.replace(DataStatus.date_format_1, date_pattern);
		}else if(namePattern.contains(DataStatus.date_format_2) && !namePattern.contains(DataStatus.date_format_4)){
			date_pattern = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(date);
			file_name = namePattern.replace(DataStatus.date_format_2, date_pattern);
		}else if(namePattern.contains(DataStatus.date_format_3) && !namePattern.contains(DataStatus.date_format_2) && !namePattern.contains(DataStatus.date_format_5) && !namePattern.contains(DataStatus.date_format_4)){
			date_pattern = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_3).format(date);
			file_name = namePattern.replace(DataStatus.date_format_3, date_pattern);
		}else if(namePattern.contains(DataStatus.date_format_4)){
			date_pattern = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_4).format(date);
			file_name = namePattern.replace(DataStatus.date_format_4, date_pattern);
		}else if(namePattern.contains(DataStatus.date_format_5) && !namePattern.contains(DataStatus.date_format_2) && !namePattern.contains(DataStatus.date_format_4)){
			date_pattern = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_5).format(date);
			file_name = namePattern.replace(DataStatus.date_format_5, date_pattern);
		}else if(namePattern.contains(DataStatus.date_format_6)){
			date_pattern = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_3).format(date);
			date_pattern = Integer.valueOf(date_pattern.substring(0, 2))+"."+Integer.valueOf(date_pattern.substring(2, 4));
			file_name = namePattern.replace(DataStatus.date_format_6, date_pattern);
		}
    	
    	return file_name;
    }
    
    public static int getBankInstWhetherTk(String[] bankData,String tk_type,int tk_column,String tk_content,String bankName,boolean whetherTkFlag) throws Exception{
    	int whetherTk = 0;
    	if(StringUtils.isBlank(tk_type) || "0".equals(tk_type) || !whetherTkFlag){
			 log.debug("银行机构"+bankName+"是否退款标识 为"+tk_type+",不做退款标识处理");
		 }else{
			 tk_column = tk_column -1;
			 if(tk_column >= 0){
				 if(bankData.length > tk_column){
					 if(bankData[tk_column] != null){
						 if((bankData[tk_column].indexOf("\"") > -1) || (bankData[tk_column].indexOf("\'") > -1) || (bankData[tk_column].indexOf("	") > -1) || (bankData[tk_column].indexOf(",") > -1)){
							 bankData[tk_column] = bankData[tk_column].replaceAll("\"", "").replaceAll("\'", "").replaceAll("	", "").replaceAll(",", "").trim();
						 }
						 if("1".equals(tk_type)){
							 try {
								 if(Double.parseDouble(bankData[tk_column].toString())<0){
									 whetherTk = 1;
								 } 
							 } catch (NumberFormatException e) {
								 log.error("银行机构"+bankName+"金额数据列取值有误:"+e.getMessage());
								 throw e;
							 }
						 }else if("2".equals(tk_type)){
							 if(bankData[tk_column].toString().trim().equals(tk_content)){
								 whetherTk = 1;
							 }
						 }
					 }else{
						 log.debug("银行机构"+bankName+"退款标识列"+tk_column+"下查询数据内容为空,该条数据不做退款标识处理");
					 }
				 }else{
					 log.debug("银行机构"+bankName+"退款标识列为"+tk_column+",超过对账单中数据列数,不做退款标识处理");
				 }
			 }
		 }
    	return whetherTk;
    }
    
    /**
     * 将日期中中文字符剔除掉
     * ex:   2015年09月09日  ---->  20150909
     * @param date
     * @return
     */
    public static String deleteChineseStrFormDate(String date){
    	if(StringUtils.isNotBlank(date)){
    		date = date.replaceAll("年", "").replaceAll("月", "").replaceAll("日", "");
    	}
    	return date;
    }
    
    public static void main(String[] args) {
		String date = "201503180910";
		date = StringUtils.rightPad(date, 14, "0");
		System.out.println(StringUtils.leftPad("123", 2, "1"));
		System.out.println(date);
	}
}
