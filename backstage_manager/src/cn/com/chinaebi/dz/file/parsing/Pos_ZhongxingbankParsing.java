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
 * 中信对账文件解析
 *
 */
public class Pos_ZhongxingbankParsing implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Pos_ZhongxingbankParsing.class);

	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	static String deduct_stlm_date = "";
	
    public  void parseBankDzFile(String filePath,String date,BankInst bankInst) throws Exception{
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
			
			String baseSql = "INSERT INTO duizhang_zhongxingbank_lst(id,deductStlmDate,tradeTime,reqResponse,reqSysStance,outAccount,tradeAmount,tradeFee,settlementAmount,referenceKoulv,authorizationCode,deductSysReference,originalReference,deductMerTermId,deductMerCode,merName,dz_file_name,inst_name,deduct_stlm_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
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
        	if(file.isFile() && file.exists()){ //判断文件是否存在
        		InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                
                String lineTxt = null;
                String[] dataArr = null;
                
                //删除同日期数据
                stmt_del = conn.prepareStatement("delete from duizhang_zhongxingbank_lst where deduct_stlm_date ="+deduct_stlm_date);
                
                int startRow = 0;
                
                while((lineTxt = bufferedReader.readLine()) != null){
                	startRow ++;
                	if(!StringUtils.isBlank(lineTxt)){
                		if(startRow > 2){
                			if(lineTxt.startsWith("-------")){
                				break;
                			}
                			dataArr = lineTxt.split("\\|");;
                    		if(dataArr != null && dataArr.length > 14){
                    			bankData[0] = UUID.randomUUID().toString().replaceAll("-", "");
                    			bankData[1] = trimMySelf(dataArr[0]);
                    			bankData[2] = trimMySelf((deduct_stlm_date.substring(0, 4) + dataArr[1]).replaceAll(" ", ""));
                    			bankData[3] = trimMySelf(dataArr[2]);
                    			bankData[4] = trimMySelf(dataArr[3]);
                    			bankData[5] = trimMySelf(dataArr[4]);
                    			bankData[6] = StringUtils.isBlank(dataArr[5]) ? "0.00" : String.valueOf(Double.valueOf(dataArr[5])/100);
                    			bankData[7] = trimMySelf(dataArr[6]);
                    			bankData[8] = trimMySelf(dataArr[7]);
                    			bankData[9] = trimMySelf(dataArr[8]);
                    			bankData[10] = trimMySelf(dataArr[9]);
                    			bankData[11] = trimMySelf(dataArr[10]);
                    			bankData[12] = trimMySelf(dataArr[11]);
                    			bankData[13] = trimMySelf(dataArr[12]);
                    			bankData[14] = trimMySelf(dataArr[13]);
                    			bankData[15] = trimMySelf(dataArr[14]);
                    			bankData[16] = file.getName();
                    			bankData[17] = bankInst.getBankName();
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
    			log.info("找不到指定的日期的对账文件");
    			throw new FileNotFoundException();
    		}
        } catch (Exception e) {
        	log.error("读取文件内容出错"+e.getMessage());
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
