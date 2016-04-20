package cn.com.chinaebi.dz.file.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
 * 青岛银行 对账文件解析类
 * @author sp
 *
 */
public class Ryf_QdyhParsing implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Ryf_QdyhParsing.class);
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	static String deduct_stlm_date = "";
	
	public void parseBankDzFile(String filePath,String date,BankInst bankInst)throws Exception{
		try{
			if (StringUtils.isNotBlank(date)) {
				deduct_stlm_date = date;
				File file = new File(filePath);
	            if(file.isFile() && file.exists()){
	            	parserBankFile(filePath,file.getName(),bankInst.getTkContext(),bankInst.getTkType().toString(),bankInst.getTkColumn()==null?0:bankInst.getTkColumn(),bankInst.getStartRow(),bankInst.getId(),bankInst.getBankName(),bankInst.isIsTk());
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
	public static void parserBankFile(String filePath,String fileName,String tk_context,String tk_type,int tk_column,int startRow,int bankId,String bankName,boolean whetherTkFlag) throws Exception  {   
		Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		try {
			String[] bankData = new String[14];
			InputStream is = new FileInputStream(filePath);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "INSERT ignore INTO duizhang_qdyh_lst(orderId,reqTime,reqSysStance,merName,qsDate,tradeAmount,tradeFee,tradeStatus,tradeResult,deduct_stlm_date,dz_file_name,inst_name,whetherTk,id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			session = hlogDAO.getCurrentSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(baseSql);
			
			// Read the Sheet
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = startRow-1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null) {
						bankData[0] = hssfRow.getCell(0)==null?"":hssfRow.getCell(0).toString();
						bankData[1] = hssfRow.getCell(1)==null?"":FileUtil.formatDataTimeToYYYYMMddhhmmss(FileUtil.deleteChineseStrFormDate(hssfRow.getCell(1).toString()));
						bankData[2] = hssfRow.getCell(2)==null?"":hssfRow.getCell(2).toString();
						bankData[3] = hssfRow.getCell(3)==null?"":hssfRow.getCell(3).toString();
						bankData[4] = hssfRow.getCell(4)==null?"":FileUtil.formatDataTimeToYYYYMMddhhmmss(FileUtil.deleteChineseStrFormDate(hssfRow.getCell(4).toString()));
						bankData[5] = hssfRow.getCell(5)==null?"":hssfRow.getCell(5).toString().replaceAll(",", "");
						bankData[6] = hssfRow.getCell(6)==null?"":hssfRow.getCell(6).toString().replaceAll(",", "");
						bankData[7] = hssfRow.getCell(7)==null?"":hssfRow.getCell(7).toString();
						bankData[8] = hssfRow.getCell(8)==null?"":hssfRow.getCell(8).toString();
						bankData[9] = deduct_stlm_date;
						bankData[10] = fileName;
						bankData[11] = bankName;
						bankData[12] = FileUtil.getBankInstWhetherTk(bankData, tk_type, tk_column, tk_context, bankName,whetherTkFlag)+"";
						bankData[13] = bankId+bankData[0]+FileUtil.formatDataTimeToYYYYMMddhhmmss(FileUtil.deleteChineseStrFormDate(bankData[1]));//银行机构ID + 订单号 + 交易日期
						
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
			
			is.close();
			
			stmt.executeBatch();
			
			conn.commit();
			
 	        if(totalExcuteNum != sucessExcuteNum){
             	log.debug(bankName+"-----"+deduct_stlm_date+"----对账单解析失败");
             	throw new Exception();
             }else{
             	log.info(bankName+"-----"+deduct_stlm_date+"----对账单解析成功");
             }
		} catch(Exception e){
			try {
				if(conn != null){
					conn.rollback();
				}
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
			throw e;
		}finally{
			if(conn != null){
				try {  
					conn.close();  
	             } catch (SQLException e) {  
	            	 log.error(e); 
	             } 
			}
			if(session != null){
				session.close();
			}
		}
	}
}
