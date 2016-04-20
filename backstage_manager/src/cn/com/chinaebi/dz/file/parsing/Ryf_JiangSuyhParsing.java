package cn.com.chinaebi.dz.file.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import cn.com.chinaebi.dz.base.DzFileParsing;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.dao.HlogDAO;
import cn.com.chinaebi.dz.object.util.FileUtil;

/**
 * 江苏银行对账单解析类
 * @author sp
 *
 */
public class Ryf_JiangSuyhParsing implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Ryf_JiangSuyhParsing.class);
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	static String deduct_stlm_date = "";
	
	public void parseBankDzFile(String filePath,String date,BankInst bankInst)throws Exception{
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
	private static void parserBankFile(File file,String tk_context,String tk_type,int tk_column,int start_row,int bankId,String bankName,boolean whetherTkFlag) throws Exception {
		 String encoding="utf-8";
		 Connection conn = null;
		 Session session = null;
		 PreparedStatement stmt = null;
		
		 try {
			
			String[] bankData = new String[23];
			
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "INSERT ignore INTO duizhang_jiangSuyh_lst(reqSysStance,payType,merName,orderId,reqTime,currency,tradeAmount,tradeFee,outAccount,outAccountName,inAccount,inAccountName,qsDate,qsStatus,qsResult,jsDate,jsStatus,orderStatus,deduct_stlm_date,dz_file_name,inst_name,whetherTk,id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			session = hlogDAO.getCurrentSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(baseSql);
			
		 	InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String[] dataArr = null;
            int io= 1;
            
	        while((lineTxt = bufferedReader.readLine()) != null){
	        	io++;
	        	if(!StringUtils.isBlank(lineTxt)){
	        		if(io > start_row){
	        			dataArr = formatString(lineTxt);
	        			if(dataArr != null && dataArr.length > 17){
	        				bankData[0] = trimMySelf(dataArr[0]);
	        				bankData[1] = trimMySelf(dataArr[1]);
	        				bankData[2] = trimMySelf(dataArr[2]);
	        				bankData[3] = trimMySelf(dataArr[3]);
	        				bankData[4] = trimMySelf(FileUtil.formatDataTimeToYYYYMMddhhmmss(dataArr[4]));
	        				bankData[5] = trimMySelf(dataArr[5]);
	        				bankData[6] = trimMySelf(dataArr[6]);
	        				bankData[7] = trimMySelf(dataArr[7]);
	        				bankData[8] = trimMySelf(dataArr[8]);
	        				bankData[9] = trimMySelf(dataArr[9]);
	        				bankData[10] = trimMySelf(dataArr[10]);
	        				bankData[11] = trimMySelf(dataArr[11]);
	        				bankData[12] = trimMySelf(dataArr[12]);
	        				bankData[13] = trimMySelf(dataArr[13]);
	        				bankData[14] = trimMySelf(dataArr[14]);
	        				bankData[15] = trimMySelf(dataArr[15]);
	        				bankData[16] = trimMySelf(dataArr[16]);
	        				bankData[17] = trimMySelf(dataArr[17]);
	        				bankData[18] = deduct_stlm_date;
	        				bankData[19] = file.getName();
	        				bankData[20] = bankName;
	        				bankData[21] = FileUtil.getBankInstWhetherTk(bankData, tk_type, tk_column, tk_context, bankName,whetherTkFlag) + "";
	        				bankData[22] = bankId+trimMySelf(dataArr[3])+FileUtil.formatDataTimeToYYYYMMddhhmmss(dataArr[4]);//银行机构ID + 订单号 + 交易日期;
	        				
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
			log.error(e);
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
    private static String[] formatString(String str){
    	if(StringUtils.isBlank(str)){
    		return null;
    	}else{
    		String[] arr = str.split("	");
        	return arr;
    	}
    }
}
