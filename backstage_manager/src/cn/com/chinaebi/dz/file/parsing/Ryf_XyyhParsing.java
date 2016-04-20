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
 * 兴业银行对账单解析类
 * @author sp
 *
 */
public class Ryf_XyyhParsing  implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Ryf_XyyhParsing.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	private static String deduct_stlm_date = "";
	
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
			
			String[] bankData = new String[17];
			
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "insert ignore into duizhang_xingye_lst(id,orderDate,orderId,merchantCode,reqSysStance,outAccount,tradeAmount,refundAmount,tradeFee,orderStatus,reqTime,deduct_stlm_date,bankSeq,dz_file_name,inst_name,bk_chk,whetherTk) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
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
	        			dataArr = lineTxt.split("\\|");
	        			if(dataArr != null && dataArr.length > 10){
							bankData[0] = bankId+trimMySelf(dataArr[3])+trimMySelf(dataArr[9]);//主键组成元素：网关号+流水号+交易时间
							bankData[1] = trimMySelf(dataArr[0]);
							bankData[2] = trimMySelf(dataArr[1]);
							bankData[3] = trimMySelf(dataArr[2]);
							bankData[4] = trimMySelf(dataArr[3]);
							bankData[5] = trimMySelf(dataArr[4]);
							bankData[6] = trimMySelf(dataArr[5]);
							bankData[7] = trimMySelf(dataArr[6]);
							bankData[8] = trimMySelf(dataArr[7]);
							bankData[9] = trimMySelf(dataArr[8]);
							bankData[10] = trimMySelf(dataArr[9]);
							bankData[11] = deduct_stlm_date;
							bankData[12] = trimMySelf(dataArr[11]);
							bankData[13] = file.getName();
							bankData[14] = bankName;
							bankData[15] = "0";
							bankData[16] = FileUtil.getBankInstWhetherTk(dataArr, tk_type, tk_column, tk_context, bankName,whetherTkFlag) + "";
							
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
    private static String trimMySelf(String str){
    	return "".equals(str)||str==null||"".equals(str.trim()) ?null:str.trim();
    }
}
