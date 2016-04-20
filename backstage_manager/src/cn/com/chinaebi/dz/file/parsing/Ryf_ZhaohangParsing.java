package cn.com.chinaebi.dz.file.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import cn.com.chinaebi.dz.base.DzFileParsing;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.dao.HlogDAO;
import cn.com.chinaebi.dz.object.util.FileUtil;

/**
 * 招行对账文件解析
 * @author sp
 */
public class Ryf_ZhaohangParsing implements DzFileParsing{

	 private static final Log log = LogFactory.getLog(Ryf_ZhaohangParsing.class);
	 
	 private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	 
	 static String deduct_stlm_date = "";
	 
	 public  void parseBankDzFile(String filePath,String date,BankInst bankInst)throws Exception{
		try{
			if (StringUtils.isNotBlank(date)) {
				deduct_stlm_date = date;
				File file = new File(filePath);
	            if(file.isFile() && file.exists()){
	            	parserBankFile(file,bankInst.getTkContext(),bankInst.getTkType().toString(),bankInst.getTkColumn()==null?0:bankInst.getTkColumn(),bankInst.getStartRow(),bankInst.getId(),bankInst.getBankName(),bankInst.isIsTk());
	            }else{
	    			log.info("找不到指定的文件");
	    			throw new FileNotFoundException();
		    	}
			}
		}catch(Exception e){
			log.error("读取文件内容出错"+e);
        	throw e;
		}
	 }
	 
	private static void parserBankFile(File file,String tk_context,String tk_type,int tk_column,int start_row,int bankId,String bankName,boolean whetherTkFlag)throws Exception{
 		String encoding="gbk";
		String regEx = "['   ']+"; // 一个或多个空格
		Pattern p = Pattern.compile(regEx);
		Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		
		try {
			
			String[] bankData = new String[13];
			
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "insert ignore into duizhang_zhaohang_lst(id,orderId,reqTime,reqSysStance,tradingType,tradeAmount,tradeFee,bookedDate,dz_file_name,inst_name,bk_chk,deduct_stlm_date,whetherTk) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			session = hlogDAO.getCurrentSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(baseSql);
			
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			String[] dataArr = null;
			int io = 1;
	         
			while((lineTxt = bufferedReader.readLine()) != null){
	        	 io++;
	        	if(!StringUtils.isBlank(lineTxt)){
	        		if(io > start_row){
	        			Matcher m=p.matcher(lineTxt.toString());
			        	String tostring=m.replaceAll("/").trim();
			        	dataArr = tostring.split("/");
			        	if(dataArr != null && dataArr.length > 6){
							bankData[0] = bankId+(StringUtils.isBlank(dataArr[2]) ? "" : trimMySelf(String.valueOf(Long.valueOf(dataArr[2]))))+trimMySelf(FileUtil.formatDataTimeToYYYYMMddhhmmss(dataArr[1]));//主键组成元素：网关号+流水号+交易时间
							bankData[1] = trimMySelf(dataArr[0]);
							bankData[2] = trimMySelf(FileUtil.formatDataTimeToYYYYMMddhhmmss(dataArr[1]));
							bankData[3] = StringUtils.isBlank(dataArr[2]) ? "" : trimMySelf(String.valueOf(Long.valueOf(dataArr[2])));
							bankData[4] = trimMySelf(dataArr[3]);
							bankData[5] = trimMySelf(dataArr[4]);
							bankData[6] = trimMySelf(dataArr[5]);
							bankData[7] = trimMySelf(dataArr[6]);
							bankData[8] = file.getName();
							bankData[9] = bankName;
							bankData[10] = "0";
							bankData[11] = deduct_stlm_date;
							bankData[12] = FileUtil.getBankInstWhetherTk(dataArr, tk_type, tk_column, tk_context, bankName,whetherTkFlag) + "";
			        	}
			        	
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
			
			stmt.executeBatch();
   			conn.commit();
   			
	        if(totalExcuteNum != sucessExcuteNum){
            	log.debug(bankName+"-----"+deduct_stlm_date+"----对账单解析失败");
            	throw new Exception();
	        }else{
            	log.info(bankName+"-----"+deduct_stlm_date+"----对账单解析成功");
	        }
		} catch (Exception e) {
			log.error("写入文件内容"+e);
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
