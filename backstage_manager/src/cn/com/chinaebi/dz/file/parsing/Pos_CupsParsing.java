package cn.com.chinaebi.dz.file.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
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
import cn.com.chinaebi.dz.object.util.DataStatus;
import cn.com.chinaebi.dz.util.DYDataUtil;


/**
 * 银联CUPS对账文件解析
 *
 */
public class Pos_CupsParsing implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Pos_CupsParsing.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	static String deduct_stlm_date = "";//清算日期
	
    public  void parseBankDzFile(String filePath,String date,BankInst bankInst) throws Exception{
		Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt_del = null;
		
		try {
				
			String[] bankData = new String[37];
				
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "insert into duizhang_cups_lst(id,acqInstIdCode,fwdInstIdCode,reqSysStance,reqTime,outAccount,tradeAmount,portionAmount,tradeFee,msgType,process,merType,termId,merCode,deductSysReference,reqType,authorizationCode,rcvgInstIdCode,origDataStance,deductSysResponse,reqInputType,acceptorReceiveFee,acceptorPayFee,throughServiceFee,convertShow,cardNumber,termReadAbility,idConditionCode,origDataTime,issuingBankCode,tradeAdress,terminalType,byStagesFee,otherInfo,dz_file_name,inst_name,deduct_stlm_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			session = hlogDAO.getCurrentSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(baseSql);
            String encoding="utf-8";
	            	
        	Calendar calendar = Calendar.getInstance();//系统当前时间
    		
    		if(StringUtils.isBlank(date)){
    			calendar.add(Calendar.DATE, -1);
    		}else{
    			calendar.setTime(DYDataUtil.getSimpleDateFormat("yyyyMMdd").parse(date));
    		}
    		deduct_stlm_date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());
    		
    		log.info("文件解析路径:"+filePath);
    		File file = new File(filePath);
    		if(file.isFile() && file.exists()){//判断是否为当天对账文件
    			
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                
                String lineTxt = null;
                String[] dataArr = null;
                
                //删除同日期数据
                stmt_del = conn.prepareStatement("delete from duizhang_cups_lst where deduct_stlm_date ="+deduct_stlm_date);
                
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(!StringUtils.isBlank(lineTxt)){
                		dataArr = formatString(lineTxt).substring(1, formatString(lineTxt).length()-1).split(",");
                		if(dataArr != null && dataArr.length > 32){
                			if(trimMySelf(dataArr[9]).equals("300000")){
                	    		continue;
                	    	}
                			bankData[0] = UUID.randomUUID().toString().replaceAll("-", "");
                			bankData[1] = trimMySelf(dataArr[0]);
                			bankData[2] = trimMySelf(dataArr[1]);
                			bankData[3] = trimMySelf(dataArr[2]);
//                			bankData[4] = trimMySelf((deduct_stlm_date.substring(0, 4) + dataArr[3]).replaceAll(" ", ""));
                			
                			if ("0101".equals(deduct_stlm_date.substring(4, 8))) {
                				if ("1231".equals(trimMySelf(dataArr[3]).substring(0, 4))) {
                    				calendar.add(Calendar.YEAR, -1);
                    				bankData[4] = trimMySelf(DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime()).substring(0, 4) + dataArr[3]).replaceAll(" ", "");
                    			} else {
                    				bankData[4] = trimMySelf((deduct_stlm_date.substring(0, 4) + dataArr[3]).replaceAll(" ", ""));
                    			}
                			} else {
                				bankData[4] = trimMySelf((deduct_stlm_date.substring(0, 4) + dataArr[3]).replaceAll(" ", ""));
                			}
                			
                			bankData[5] = trimMySelf(dataArr[4]);
                			bankData[6] = StringUtils.isBlank(dataArr[5]) ? "0.00" : trimMySelf(String.valueOf(Double.valueOf(dataArr[5])/100));
                			bankData[7] = trimMySelf(dataArr[6]);
                			bankData[8] = trimMySelf(dataArr[7]);
                			bankData[9] = trimMySelf(dataArr[8]);
                			bankData[10] = trimMySelf(dataArr[9]);
                			bankData[11] = trimMySelf(dataArr[10]);
                			bankData[12] = trimMySelf(dataArr[11]);
                			bankData[13] = trimMySelf(dataArr[12]);
                			bankData[14] = trimMySelf(dataArr[13]);
                			bankData[15] = trimMySelf(dataArr[14]);
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
                			bankData[31] = trimMySelf(dataArr[30]);
                			bankData[32] = trimMySelf(dataArr[31]);
                			bankData[33] = trimMySelf(dataArr[32]);
                			bankData[34] = file.getName();
                			bankData[35] = bankInst.getBankName();
                			bankData[36] = deduct_stlm_date;
                			
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
     * 自定义trim方法
     * @param str
     * @return
     */
    private static String trimMySelf(String str){
    	return "".equals(str)||str==null||"".equals(str.trim()) ?null:str.trim();
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
    
    public static void main(String[] args) {
		String date = "20160101";
		Calendar calendar = Calendar.getInstance();
		if(StringUtils.isBlank(date)){
			calendar.add(Calendar.DATE, -1);
		}else{
			try {
				calendar.setTime(DYDataUtil.getSimpleDateFormat("yyyyMMdd").parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		deduct_stlm_date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());
		
//		String time = "0101102220";
		String time = "1231232258";
		String reqTime = null;
		if ("1231".equals(time.substring(0, 4))) {
			calendar.add(Calendar.YEAR, -1);
			reqTime = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime()).substring(0, 4) + time.replaceAll(" ", "");
		} else {
			reqTime = deduct_stlm_date.substring(0, 4) + time.replaceAll(" ", "");
		}
		System.out.println(reqTime);
	}
}
