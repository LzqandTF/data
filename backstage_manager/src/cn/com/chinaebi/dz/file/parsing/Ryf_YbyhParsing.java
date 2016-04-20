package cn.com.chinaebi.dz.file.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;

import cn.com.chinaebi.dz.base.DzFileParsing;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.dao.HlogDAO;
import cn.com.chinaebi.dz.object.util.FileUtil;

/**
 * 恒丰银行 对账文件解析类
 * @author sp
 *
 */
public class Ryf_YbyhParsing implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Ryf_YbyhParsing.class);
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	static String deduct_stlm_date = "";
	
	public void parseBankDzFile(String filePath,String date,BankInst bankInst)throws Exception{
		try{
			if (StringUtils.isNotBlank(date)) {
				deduct_stlm_date = date;
				File file = new File(filePath);
	            if(file.isFile() && file.exists()){
	            	parserBankFile(file,file.getName(),bankInst.getTkContext(),bankInst.getTkType().toString(),bankInst.getTkColumn()==null?0:bankInst.getTkColumn(),bankInst.getStartRow(),bankInst.getId(),bankInst.getBankName(),bankInst.isIsTk());
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
	
	/**
	 * 
	 * @param filePath  	文件路径
	 * @param fileName		文件名称	
	 * @param tk_context	银行网关退款内容
	 * @param tk_type		银行网关退款标识
	 * @param tk_column		银行网关指定退款列
	 * @param bankId		银行网关ID
	 * @param bankName		银行网关名称
	 */
	public static void parserBankFile(File file,String fileName,String tk_context,String tk_type,int tk_column,int startRow,int bankId,String bankName,boolean whetherTkFlag) throws Exception  {   
		Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		
		try {
				
			String[] bankData = new String[12];
				
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "INSERT ignore INTO duizhang_ybyh_lst(orderId,bankOrderId,reqSysStance,tradeAmount,reqTime,commodityName,extendInfo,deduct_stlm_date,dz_file_name,inst_name,whetherTk,id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			session = hlogDAO.getCurrentSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(baseSql);
			
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"gbk");//考虑到编码格式
	        BufferedReader bufferedReader = new BufferedReader(read);
	        String lineTxt = null;
	        String[] dataArr = null;
	        int io= 1;
	        
	       
	        while((lineTxt = bufferedReader.readLine()) != null){
		       	io++;
		       	if(!StringUtils.isBlank(lineTxt)){
		       		if(io > startRow){
		       			dataArr = lineTxt.split(",");
		       			if(dataArr != null && dataArr.length > 4){
		       				if(StringUtils.isBlank(formatString(dataArr[0]))){
			    				continue;
			    			}
							bankData[0] = trimMySelf(formatString(dataArr[0]));
							bankData[1] = trimMySelf(formatString(dataArr[1]));
							bankData[2] = trimMySelf(formatString(dataArr[2]));
							bankData[3] = new BigDecimal(trimMySelf(formatString(dataArr[3]))).setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
							bankData[4] = new BigDecimal(FileUtil.formatDataTimeToYYYYMMddhhmmss(formatString(dataArr[4]))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
							bankData[5] = dataArr.length > 5 ? trimMySelf(formatString(dataArr[5])) : "";
							bankData[6] = dataArr.length > 6 ? trimMySelf(formatString(dataArr[6])) : "";
							bankData[7] = deduct_stlm_date;
							bankData[8] = fileName;
							bankData[9] = bankName;
							bankData[10] = FileUtil.getBankInstWhetherTk(bankData, tk_type, tk_column, tk_context, bankName,whetherTkFlag) + "";
							bankData[11] = bankId+trimMySelf(formatString(dataArr[0]))+new BigDecimal(FileUtil.formatDataTimeToYYYYMMddhhmmss(formatString(dataArr[4]))).setScale(0, BigDecimal.ROUND_HALF_UP).toString();//银行机构ID + 订单号 + 交易日期 
							
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
			
			stmt.executeBatch();
  			conn.commit();
  			
   	        if(totalExcuteNum != sucessExcuteNum){
              	log.debug(bankName+"-----"+deduct_stlm_date+"----对账单解析失败");
              	throw new Exception();
            }else{
             	log.info(bankName+"-----"+deduct_stlm_date+"----对账单解析成功");
            }

		} catch (Exception e) {   
			log.error(e.getMessage()); 
			throw e;
		} finally{
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
	    
	    private static String formatString(String str){
	    	if(StringUtils.isNotBlank(str)){
	    		str = str.replaceAll("\'", "").replaceAll("\"", "").replaceAll("	", "").trim();
	    		return str;
	    	}
	    	return "";
	    }
}
