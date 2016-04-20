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


/**
 * 大连交行对账文件解析
 * @author sp
 *
 */
public class Pos_DljhParsing implements DzFileParsing{
	
	 private static final Log log = LogFactory.getLog(Pos_DljhParsing.class);
	 
	 private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	 
	 static String deduct_stlm_date = "";

	 public void parseBankDzFile(String filePath,String date,BankInst bankInst) throws Exception{
		Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt_del = null;
		
		try {
				
			String[] bankData = new String[25];
				
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "INSERT INTO duizhang_dljh_lst(id,reqSysStance,reqTime,outAccount,tradeAmount,tradeFee,termId,merCode,deductSysReference,settleAmount,origDeductSysReference,batch_no,mer_name,msgType,process,origDataStance,accountName,accountBank,accountType,whetherErroeHandle,dz_file_name,inst_name,bk_chk,deduct_stlm_date,authorizationCode) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
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
			
			deduct_stlm_date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());//清算日期
			
			log.info("文件解析路径:"+filePath);
			File file = new File(filePath);
	        
	        
	        if(file.isFile() && file.exists()){
	        	InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
	            BufferedReader bufferedReader = new BufferedReader(read);
	            String lineTxt = null;
	            String[] dataArr = null;
	          //删除同日期数据
	            stmt_del = conn.prepareStatement("delete from duizhang_dljh_lst where deduct_stlm_date ="+deduct_stlm_date);
                
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(!StringUtils.isBlank(lineTxt)){
	                      lineTxt = lineTxt.replaceAll(" ", "").replace("\"","");
	                      dataArr = formatString(lineTxt).substring(1, formatString(lineTxt).length()-1).split(",");
	                		if(dataArr != null && dataArr.length > 17){
	                			bankData[0] = UUID.randomUUID().toString().replaceAll("-", "");
	                			bankData[1] = dataArr[7];
	                			bankData[2] = dataArr[15] + dataArr[16];
	                			bankData[3] = dataArr[14];
	                			bankData[4] = StringUtils.isBlank(dataArr[9]) ? "0.00" : dataArr[9];
	                			bankData[5] = StringUtils.isBlank(dataArr[10]) ? "0.00" : dataArr[10];
	                			bankData[6] = dataArr[2];
	                			bankData[7] = dataArr[1];
	                			bankData[8] = dataArr[4];
	                			bankData[9] = dataArr[11];
	                			bankData[10] = dataArr[5];
	                			bankData[11] = dataArr[6];
	                			bankData[12] = dataArr[0];
	                			bankData[13] = dataArr[3].substring(0, 4);
	                			bankData[14] = dataArr[3].substring(4,10);
	                			bankData[15] = dataArr[8];
	                			bankData[16] = dataArr[12];
	                			bankData[17] = dataArr[13];
	                			bankData[18] = dataArr[17];
	                			bankData[19] = "0";
	                			bankData[20] = file.getName();
	                			bankData[21] = bankInst.getBankName();
	                			bankData[22] = "0";
	                			bankData[23] = deduct_stlm_date;
	                			bankData[24] = "";
	                			
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
        } catch (Exception e) {
        	log.error("读取文件内容出错"+e);
        	throw e;
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
 		String[] arr = str.split(" ");
 		List<String> list = new ArrayList<String>();
 		for(int i=0;i<arr.length;i++){
 			if(arr[i].equals("") || arr[i]==null){
 			}else{
 				list.add(arr[i]);
 			}
 		}
 		return Arrays.toString(list.toArray());
     }
    
}