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
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import cn.com.chinaebi.dz.base.DzFileParsing;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.dao.HlogDAO;
import cn.com.chinaebi.dz.object.util.FileUtil;

/***
 *	中国银联wap对账文件解析
 * @author sp
 */
public class Ryf_YlLstParsing   implements DzFileParsing{
	     
	 private static final Log log = LogFactory.getLog(Ryf_YlLstParsing.class);
	 
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
	 private static void parserBankFile(File file,String tk_context,String tk_type,int tk_column,int start_row,int bankId,String bankName,boolean whetherTkFlag)throws Exception{
		String encoding="gbk";
		Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		
		try {
			
			String[] bankData = new String[44];
			
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "insert ignore into duizhang_yl_lst(id,tradingNo,agentCode,sendCode,trackingNo,reqTime,outAccount,tradeAmount,merCategory,terminalType,reqSysStance,rawPayWay,orderId,patType,rawReqSysStance,rawReqTime,tradeFee,settlementAmount,payWay,merCode,tradingType,tradingAsType,businessType,accountType,billType,billNo,interact,rawReqSysStances,merCodes,bookedWay,twoMercode,twoMerAbbreviation,twoMerAmount,netAmount,terminalNo,mercustom,preferentialAmount,invoiceAmount,keep,dz_file_name,inst_name,bk_chk,deduct_stlm_date,whetherTk) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			session = hlogDAO.getCurrentSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(baseSql);
			
        	InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            String[] dataArr = null;
            int io = 1 ;
            
	        while((lineTxt = bufferedReader.readLine()) != null){
	        	io++;
	        	if(io > start_row){
	        		if(!StringUtils.isBlank(lineTxt)){
		        		dataArr = formatString(lineTxt).substring(1, formatString(lineTxt).length()-1).split(",");
		        		if(dataArr != null && dataArr.length > 37){
							bankData[0] = bankId+trimMySelf(dataArr[9])+deduct_stlm_date.substring(0,4)+trimMySelf(dataArr[4]);//主键组成元素：网关号+流水号+交易时间
							bankData[1] = trimMySelf(dataArr[0]);
							bankData[2] = trimMySelf(dataArr[1]);
							bankData[3] = trimMySelf(dataArr[2]);
							bankData[4] = trimMySelf(dataArr[3]);
							bankData[5] = deduct_stlm_date.substring(0,4)+trimMySelf(dataArr[4]).toString();
							bankData[6] = trimMySelf(dataArr[5]);
							bankData[7] = StringUtils.isBlank(dataArr[6].toString())?"0.00":String.format("%.2f",Double.valueOf(dataArr[6].toString())/100);
							bankData[8] = trimMySelf(dataArr[7]);
							bankData[9] = trimMySelf(dataArr[8]);
							bankData[10] = trimMySelf(dataArr[9]);
							bankData[11] = trimMySelf(dataArr[10]);
							bankData[12] = trimMySelf(dataArr[11]);
							bankData[13] = trimMySelf(dataArr[12]);
							bankData[14] = trimMySelf(dataArr[13]);
							bankData[15] = trimMySelf(dataArr[14]);
							bankData[16] = ( StringUtils.isNotBlank(dataArr[15]) && dataArr[15].length() > 2 ) ? String.format("%.2f",Double.valueOf(dataArr[15].substring(2,dataArr[15].length()))/100) : "0.00";
							bankData[17] = ( StringUtils.isNotBlank(dataArr[16]) && dataArr[16].length() > 2 ) ? String.format("%.2f",Double.valueOf(dataArr[16].substring(2,dataArr[16].length()))/100) : "0.00";
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
							bankData[34] = trimMySelf(dataArr[33]);
							bankData[35] = trimMySelf(dataArr[34]);
							bankData[36] = trimMySelf(dataArr[35]);
							bankData[37] = trimMySelf(dataArr[36]);
							bankData[38] = trimMySelf(dataArr[37]);
							bankData[39] = file.getName();
							bankData[40] = bankName;
							bankData[41] = "0";
							bankData[42] = deduct_stlm_date;
							bankData[43] = FileUtil.getBankInstWhetherTk(dataArr, tk_type, tk_column, tk_context, bankName,whetherTkFlag) + "";
							
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
     * String字符串格式化
     * @param str
     * @return
     */
    private static String formatString(String str){
    	List<String> sb = new ArrayList<String>();
    	try{
    		sb.add(str.substring(0,3));
    		sb.add(str.substring(4,15));
    		sb.add(str.substring(16,27));
    		sb.add(str.substring(28,34));
    		sb.add(str.substring(35,45));
    		sb.add(str.substring(46,65));
    		sb.add(str.substring(66,78));
    		sb.add(str.substring(79,83));
    		sb.add(str.substring(84,86));
    		sb.add(str.substring(87,108));
    		sb.add(str.substring(109,111));
    		sb.add(str.substring(112,144));
    		sb.add(str.substring(145,147));
    		sb.add(str.substring(148,154));
    		sb.add(str.substring(155,165));
    		sb.add(str.substring(166,179));
    		sb.add(str.substring(180,193));
    		sb.add(str.substring(194,198));
    		sb.add(str.substring(199,214));
    		sb.add(str.substring(215,217));
    		sb.add(str.substring(218,220));
    		sb.add(str.substring(221,227));
    		sb.add(str.substring(228,230));
    		sb.add(str.substring(231,235));
    		sb.add(str.substring(236,268));
    		sb.add(str.substring(269,270));
    		sb.add(str.substring(271,292));
    		sb.add(str.substring(293,308));
    		sb.add(str.substring(309,310));
    		sb.add(str.substring(311,326));
    		sb.add(str.substring(327,359));
    		sb.add(str.substring(360,373));
    		sb.add(str.substring(374,387));
    		sb.add(str.substring(388,396));
    		sb.add(str.substring(397,429));
    		sb.add(str.substring(430,443));
    		sb.add(str.substring(444,457));
    		sb.add(str.substring(458,578));
    	}catch(Exception e){
    		log.error(e);
    	}
		return Arrays.toString(sb.toArray());
    }
    
    private static String trimMySelf(String str){
    	return str==null || "null".equals(str)||"".equals(str.trim()) ?"":str.trim();
    }
}
