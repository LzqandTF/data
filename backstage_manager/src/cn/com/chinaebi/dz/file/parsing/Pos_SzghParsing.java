package cn.com.chinaebi.dz.file.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import cn.com.chinaebi.dz.base.DzFileParsing;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.dao.HlogDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.util.PoundageCalculate;



/**
 * 深圳工行对账文件解析
 * @author wang.hui
 */
	public class Pos_SzghParsing implements DzFileParsing{
		
	 private static final Log log = LogFactory.getLog(Pos_SzghParsing.class);
	 
	 private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	 
	 static String deduct_stlm_date = "";
	 
	 public void parseBankDzFile(String filePath,String date,BankInst bankInst)throws Exception{
		 Connection conn = null;
		 Session session = null;
		 PreparedStatement stmt = null;
		 PreparedStatement stmt_del = null;
		
		 try {
				
			 String[] bankData = new String[43];
				
			 int readyInsertSqlNum = 0;
			 boolean insert_flag = false;
			 int totalExcuteNum = 0;
			 int sucessExcuteNum = 0;
			
			 String baseSql = "insert into duizhang_szgh_lst(id,coopNo,zoneNo,brNo,reqSysStance,outAccount,process,tradeAmount,incAmt,gcFeeAmt,szFeeAmt,fwFeeAmt,feeRate,fwFeeRate,drcRf,reqTime,timeStmp,authorizationCode,streamNo,saccType,setAccNo,accName,coopName,merCode,tradeAdress,alternateCount1,alternateCount2,reserveAmountField1,reserveAmountField2,reserveAmountField3,reserveAmountField4,reserveAmountField5,reserveAmountField6,reserveAmountField7,reserveAmountField8,reserveAmountField9,reserveCharacter1,whetherErroeHandle,dz_file_name,inst_name,bk_chk,deduct_stlm_date,tradeFee) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			 session = hlogDAO.getCurrentSession();
			 conn = session.connection();
			 conn.setAutoCommit(false);
			 stmt = conn.prepareStatement(baseSql);
			
			 String encoding="gbk";
			 Calendar calendar = Calendar.getInstance();//系统当前时间
			 if(StringUtils.isBlank(date)){
				 calendar.add(Calendar.DATE, -1);
			 }else{
				 calendar.setTime(DYDataUtil.getSimpleDateFormat("yyyyMMdd").parse(date));
			 }
    		
			 deduct_stlm_date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());
    		
			 log.info("文件解析路径:"+filePath);
			 File file = new File(filePath);
			 if(file.isFile() && file.exists()){
				 InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
		       	 BufferedReader bufferedReader = new BufferedReader(read);
		       	 String lineTxt = null;
		       	 String[] dataArr = null;
		       	 
		       	 //删除同日期数据
		       	 stmt_del = conn.prepareStatement("delete from duizhang_szgh_lst where deduct_stlm_date ="+deduct_stlm_date);
		       	 
	             String tradeFee = "0.00";
	             
		       	 while((lineTxt = bufferedReader.readLine()) != null){
		       		 
		       		if(!StringUtils.isBlank(lineTxt)){
                		dataArr = formatString(lineTxt).substring(1, formatString(lineTxt).length()-1).split(",");
                		if(dataArr != null && dataArr.length > 25){
                			bankData[0] = UUID.randomUUID().toString().replaceAll("-", "");
                			bankData[1] = trimMySelf(dataArr[0]);
                			bankData[2] = trimMySelf(dataArr[1]);
                			bankData[3] = trimMySelf(dataArr[2]);
                			bankData[4] = trimMySelf(dataArr[3]);
                			bankData[5] = trimMySelf(dataArr[4]);
                			bankData[6] = trimMySelf(dataArr[5]);
                			bankData[7] = StringUtils.isBlank(dataArr[6]) ? "0.00" : String.valueOf(Double.valueOf(trimMySelf(dataArr[6]))/100);
                			bankData[8] = StringUtils.isBlank(dataArr[7]) ? "0.00" : String.valueOf(Double.valueOf(trimMySelf(dataArr[7]))/100);
                			bankData[9] = StringUtils.isBlank(dataArr[8]) ? "0.00" : String.valueOf(Double.valueOf(trimMySelf(dataArr[8]))/100);
                			bankData[10] = StringUtils.isBlank(dataArr[9]) ? "0.00" : String.valueOf(Double.valueOf(trimMySelf(dataArr[9]))/100);
                			bankData[11] = StringUtils.isBlank(dataArr[10]) ? "0.00" : String.valueOf(Double.valueOf(trimMySelf(dataArr[10]))/100);
                			bankData[12] = StringUtils.isBlank(dataArr[11]) ? "0.00" : String.valueOf(Double.valueOf(trimMySelf(dataArr[10]))/1000);
                			bankData[13] = StringUtils.isBlank(dataArr[12]) ? "0.00" : String.valueOf(Double.valueOf(trimMySelf(dataArr[10]))/1000);
                			bankData[14] = trimMySelf(dataArr[13]);
                			bankData[15] = deduct_stlm_date + trimMySelf(dataArr[14]).replaceAll("\\.","");
                			bankData[16] = trimMySelf(dataArr[15]);
                			bankData[17] = trimMySelf(dataArr[16]);
                			bankData[18] = trimMySelf(dataArr[17]);
                			bankData[19] = trimMySelf(dataArr[18]);
                			bankData[20] = trimMySelf(dataArr[19]);
                			bankData[21] = trimMySelf(dataArr[20]);
                			bankData[22] = trimMySelf(dataArr[21]);
                			bankData[23] = trimMySelf(dataArr[22]);
                			bankData[24] = trimMySelf(dataArr[23]);
                			bankData[25] = trimMySelf(dataArr[24]);
                			bankData[26] = trimMySelf(dataArr[25]);
                			bankData[27] = trimMySelf(dataArr[26]);
                			bankData[28] = trimMySelf(dataArr[27]);
                			bankData[29] = trimMySelf(dataArr[28]);
                			bankData[30] = trimMySelf(dataArr[29]);
                			bankData[31] = trimMySelf(dataArr[20]);
                			bankData[32] = trimMySelf(dataArr[21]);
                			bankData[33] = trimMySelf(dataArr[22]);
                			bankData[34] = trimMySelf(dataArr[23]);
                			bankData[35] = trimMySelf(dataArr[24]);
                			bankData[36] = trimMySelf(dataArr[25]);
                			bankData[37] = "0";
                			bankData[38] = file.getName();
                			bankData[39] = bankInst.getBankName();
                			bankData[40] = "0";
                			bankData[41] = deduct_stlm_date;
                			
            	    		if(StringUtils.isNotBlank(dataArr[6]) && StringUtils.isNotBlank(dataArr[12])){
            		    		tradeFee = PoundageCalculate.roundString((Double.valueOf(trimMySelf(dataArr[6]))/100)*(Double.valueOf(trimMySelf(dataArr[12]))/100000), 2);
            		    	}
                			
                			bankData[42] = tradeFee;
                			
                			totalExcuteNum++;
							insert_flag = hlogDAO.saveBankData(bankData,stmt);
							if(insert_flag){
								sucessExcuteNum ++;
								readyInsertSqlNum ++;
							}
							if(readyInsertSqlNum % 1000 == 0){
								stmt.executeBatch();
							}
                		}
                	}
                }
                bufferedReader.close();
                read.close();
                
                stmt_del.execute();
                stmt.executeBatch();
     			conn.commit();
     			
      	        if(totalExcuteNum != sucessExcuteNum){
                  	log.debug(bankInst.getBankName()+"-----"+deduct_stlm_date+"----对账单解析失败");
                  	throw new Exception();
                }else{
                 	log.info(bankInst.getBankName()+"-----"+deduct_stlm_date+"----对账单解析成功");
                }
			 }else{
    			log.info("找不到指定的文件");
    			throw new FileNotFoundException();
			 }
		}catch(Exception e){
			log.error(e);
		}finally{
			if(conn != null){
				conn.close();
			}
			if (null != session){
				session.close();
			}
		}
	 }
   /**
    * String字符串格式化
    * @param str
    * @return
    */
   private static String formatString(String str){
	   	List<String> sb = new ArrayList<String>();
	   	try{
	   		sb.add(str.substring(0,5));
	   		sb.add(str.substring(5,10));
	   		sb.add(str.substring(10,15));
	   		sb.add(str.substring(15,38));
	   		sb.add(str.substring(38,72));
	   		sb.add(str.substring(72,77));
	   		sb.add(str.substring(77,95));
	   		sb.add(str.substring(95,113));
	   		sb.add(str.substring(113,131));
	   		sb.add(str.substring(131,149));
	   		sb.add(str.substring(149,167));
	   		sb.add(str.substring(167,172));
	   		sb.add(str.substring(172,177));
	   		sb.add(str.substring(177,178));
	   		sb.add(str.substring(178,186));
	   		sb.add(str.substring(186,212));
	   		sb.add(str.substring(212,218));
	   		sb.add(str.substring(218,227));
	   		sb.add(str.substring(227,230));
	   		sb.add(str.substring(230,264));
	   		sb.add(str.substring(264,294));
	   		sb.add(str.substring(294,354));
	   		sb.add(str.substring(360,373));
	   		sb.add(str.substring(373,393));
	   		sb.add(str.substring(393,410));
	   		sb.add(str.substring(410,427));
	   		sb.add(str.substring(427,445));
	   		sb.add(str.substring(445,463));
	   		sb.add(str.substring(463,481));
	   		sb.add(str.substring(481,499));
	   		sb.add(str.substring(499,517));
	   		sb.add(str.substring(517,535));
	   		sb.add(str.substring(535,553));
	   		sb.add(str.substring(553,571));
	   		sb.add(str.substring(571,589));
	   		sb.add(str.substring(589,789));
	   	}catch(Exception e){
	   		log.error(e);
	   	}
		return Arrays.toString(sb.toArray());
   }
   
   private static String trimMySelf(String str){
   		return str==null || "null".equals(str)||"".equals(str.trim()) ?"":str.trim().replace(" ", "");
   }
}
