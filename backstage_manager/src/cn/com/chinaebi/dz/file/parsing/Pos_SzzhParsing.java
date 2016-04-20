package cn.com.chinaebi.dz.file.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
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
 * 深圳中行对账文件解析
 * @author sp
 */
public class Pos_SzzhParsing implements DzFileParsing{
	
	 private static final Log log = LogFactory.getLog(Pos_SzzhParsing.class);
	 
	 private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	 
	 static String deduct_stlm_date = "";
	
	 public void parseBankDzFile(String filePath,String date,BankInst bankInst)throws Exception{
		Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt_del = null;
		
		try {
				
			String[] bankData = new String[19];
				
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "insert into duizhang_szzh_lst(id,merCode,termId,batch_no,outAccount,tradeDate,reqTime,tradeAmount,tradeFee,settleAmount,authorizationCode,trade_code,stage,card_category,deductSysReference,dz_file_name,inst_name,reqSysStance,deduct_stlm_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			session = hlogDAO.getCurrentSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(baseSql);
            String encoding="utf-8";
            
        		Calendar calendar = Calendar.getInstance();//系统当前时间
        		if(StringUtils.isNotBlank(date)){
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
		            stmt_del = conn.prepareStatement("delete from duizhang_szzh_lst where deduct_stlm_date ="+deduct_stlm_date);
	                
		            int startRow = 0;
	                while((lineTxt = bufferedReader.readLine()) != null){
	                	if(!StringUtils.isBlank(lineTxt)){
	                		startRow++;
	                		if(startRow >1){
	                			dataArr = lineTxt.split("\\|");;
		                		if(dataArr != null && dataArr.length > 12){
		                			bankData[0] = UUID.randomUUID().toString().replaceAll("-", "");
		                			bankData[1] = trimMySelf(dataArr[0]);
		                			bankData[2] = trimMySelf(dataArr[1]);
		                			bankData[3] = trimMySelf(dataArr[2]);
		                			bankData[4] = trimMySelf(dataArr[3]);
		                			bankData[5] = trimMySelf(dataArr[4].replaceAll("/", ""));
		                			bankData[6] = dataArr[4].replaceAll("/", "") + trimMySelf(dataArr[5]);
		                			bankData[7] = trimMySelf(dataArr[6]);
		                			bankData[8] = trimMySelf(dataArr[7]);
		                			bankData[9] = trimMySelf(dataArr[8]);
		                			bankData[10] = trimMySelf(dataArr[9]);
		                			bankData[11] = trimMySelf(dataArr[10]);
		                			bankData[12] = trimMySelf(dataArr[11]);
		                			bankData[13] = trimMySelf(dataArr[12]);
		                			bankData[14] = dataArr.length > 13 ? trimMySelf(dataArr[13]) : "";
		                			bankData[15] = file.getName();
		                			bankData[16] = bankInst.getBankName();
		                			bankData[17] = dataArr.length > 13 ? ((StringUtils.isNotBlank(dataArr[13]) && dataArr[13].length() > 6) ? dataArr[13].substring(dataArr[13].length()-6) : "") : "";
		                			bankData[18] = deduct_stlm_date;
		                			
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
	 
    
    private static String trimMySelf(String str){
    	return "".equals(str)||str==null||"".equals(str.trim()) ?null:str.trim();
    }
     
}
