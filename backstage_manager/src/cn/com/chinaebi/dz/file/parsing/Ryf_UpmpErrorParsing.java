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
 * UPMP差错文件解析类
 * @author sp
 *
 */
public class Ryf_UpmpErrorParsing  implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Ryf_UpmpErrorParsing.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	static String deduct_stlm_date = "";
	
    public void parseBankDzFile(String filePath,String date,BankInst bankInst) throws Exception{
    	Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		
		try {
			
			
			
            String encoding="gbk";
    		Calendar calendar = Calendar.getInstance();//系统当前时间
    		
    		if(StringUtils.isBlank(date)){
    			calendar.add(Calendar.DATE, -1);
    		}else{
    			calendar.setTime(DYDataUtil.getSimpleDateFormat("yyyyMMdd").parse(date));
    		}
    		String file_year = DYDataUtil.getSimpleDateFormat("yyyy").format(calendar.getTime());
    		deduct_stlm_date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());
    		
    		log.info("文件路径"+filePath);
    		File file = new File(filePath);
    		if(file.isFile() && file.exists()){//判断是否为当天对账文件
    			
    			String[] bankData = new String[54];
    			
    			int readyInsertSqlNum = 0;
    			boolean insert_flag = false;
    			int totalExcuteNum = 0;
    			int sucessExcuteNum = 0;
    			
    			String baseSql = "INSERT IGNORE INTO duizhang_error_upmp_lst(id,error_trade_flag,acqInstIdCode,fwdInstIdCode,reqSysStance,reqTime,outAccount,tradeAccount,msgType,process,merType,termId,deductSysReference,reqType,authorizationCode,rcvgInstIdCode,issuingBankCode,origDataStance,deductSysResponse,reqInputType,acceptorReceiveFee,acceptorPayFee,byStagesFee,tradeFee,errorTradePayFee,errorTradeReceiveFee,error_info,ReceiveRollOutCode,accountIdentification,intoInstCode,onTradeTime,cardNumber,termReadAbility,idConditionCode,onDeduct_stlm_date,onTradeAccount,tradeAdress,eci,merchant_code,sender_clearing_organizations,recipient_clearing_organization,transferee_clearing_organizations,before_transation_ter_type,merchant_name_address,special_billing_type,special_charges_grade,tac_logo,card_product_info,tran_code_caused_error,tran_initiated_method,account_settle_type,reserved_for_use,dz_file_name,inst_name) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    			
    			session = hlogDAO.getCurrentSession();
    			conn = session.connection();
    			conn.setAutoCommit(false);
    			stmt = conn.prepareStatement(baseSql);
    			
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String[] dataArr = null;
                
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(!StringUtils.isBlank(lineTxt)){
                		dataArr = formatString(lineTxt).substring(1, formatString(lineTxt).length()-1).split(",");
                		if(dataArr != null && dataArr.length > 50){
							bankData[0] = bankInst.getId()+trimMySelf(dataArr[3])+trimMySelf(dataArr[4].replaceAll(" ", ""));//主键组成元素：网关号+流水号+交易时间
							bankData[1] = trimMySelf(dataArr[0]);
							bankData[2] = trimMySelf(dataArr[1]);
							bankData[3] = trimMySelf(dataArr[2]);
							bankData[4] = trimMySelf(dataArr[3]);
							bankData[5] = trimMySelf((file_year+dataArr[4]).replaceAll(" ", ""));
							bankData[6] = trimMySelf(dataArr[5]);
							bankData[7] = StringUtils.isBlank(dataArr[6])?"0.00":trimMySelf(String.format("%.2f",Double.valueOf(dataArr[6])/100));
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
							bankData[23] = StringUtils.isBlank(dataArr[22])?"0.00":trimMySelf(String.format("%.2f", Double.valueOf(dataArr[22])/100));
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
							bankData[39] = trimMySelf(dataArr[38]);
							bankData[40] = trimMySelf(dataArr[39]);
							bankData[41] = trimMySelf(dataArr[40]);
							bankData[42] = trimMySelf(dataArr[41]);
							bankData[43] = trimMySelf(dataArr[42]);
							bankData[44] = trimMySelf(dataArr[43]);
							bankData[45] = trimMySelf(dataArr[44]);
							bankData[46] = trimMySelf(dataArr[45]);
							bankData[47] = trimMySelf(dataArr[46]);
							bankData[48] = trimMySelf(dataArr[47]);
							bankData[49] = trimMySelf(dataArr[48]);
							bankData[50] = trimMySelf(dataArr[49]);
							bankData[51] = trimMySelf(dataArr[50]);
							bankData[52] = file.getName();
							bankData[53] = bankInst.getBankName();
							
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
                	}else{
                		log.info("此行记录为空白行");
                	}
                }
                
                bufferedReader.close();
                read.close();
                
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
        } catch (Exception e) {
        	log.error("读取差错对账文件内容出错"+e.getMessage());
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
    	return "".equals(str)||str==null||"".equals(str.trim()) ?"":str.trim();
    }
    /**
     * String字符串格式化
     * @param str
     * @return
     */
    private static String formatString(String str){
    	List<String> sb = new ArrayList<String>();
    	try{
    		sb.add(str.substring(0, 3));
    		
    		sb.add(str.substring(4, 15));
    		
    		sb.add(str.substring(16, 27));
    		
    		sb.add(str.substring(28, 34));
    		
    		sb.add(str.substring(35, 45));
    		
    		sb.add(str.substring(46, 65));
    		
    		sb.add(str.substring(66, 78));
    		
    		sb.add(str.substring(79, 83));
    		
    		sb.add(str.substring(84, 90));
    		
    		sb.add(str.substring(91, 95));
    		
    		sb.add(str.substring(96, 104));
    		
    		sb.add(str.substring(105, 117));
    		
    		sb.add(str.substring(118, 120));
    		
    		sb.add(str.substring(121, 127));
    		
    		sb.add(str.substring(128, 139));
    		
    		sb.add(str.substring(140, 151));
    		
    		sb.add(str.substring(152, 158));
    		
    		sb.add(str.substring(159, 161));
    		
    		sb.add(str.substring(162, 165));
    		
    		sb.add(str.substring(166, 178));
    		
    		sb.add(str.substring(179, 191));
    		
    		sb.add(str.substring(192, 204));
    		
    		sb.add(str.substring(205, 217));
    		
    		sb.add(str.substring(218, 230));
    		
    		sb.add(str.substring(231, 243));
    		
    		sb.add(str.substring(244, 248));
    		
    		sb.add(str.substring(249, 260));
    		
    		sb.add(str.substring(261, 280));
    		
    		sb.add(str.substring(281, 292));
    		
    		sb.add(str.substring(313, 323));
    		
    		sb.add(str.substring(324, 327));
    		
    		sb.add(str.substring(328, 329));
    		
    		sb.add(str.substring(330, 331));
    		
    		sb.add(str.substring(332, 336));
    		
    		sb.add(str.substring(337, 349));
    		
    		sb.add(str.substring(350, 351));
    		
    		sb.add(str.substring(352, 354));
    		
    		sb.add(str.substring(355, 370));
    		
    		sb.add(str.substring(371, 382));
    		
    		sb.add(str.substring(383, 394));
    		
    		sb.add(str.substring(395, 406));
    		
    		sb.add(str.substring(407, 409));
    		
    		sb.add(str.substring(410, 446));
    		
    		sb.add(str.substring(447, 449));
    		
    		sb.add(str.substring(450, 451));
    		
    		sb.add(str.substring(452, 460));
    		
    		sb.add(str.substring(461, 485));
    		
    		sb.add(str.substring(486, 489));
    		
    		sb.add(str.substring(490, 491));
    		
    		sb.add(str.substring(492, 494));
    		
    		sb.add(str.substring(495, 497));
    		
    	}catch(Exception e){
    		log.error(e);
    	}
		return Arrays.toString(sb.toArray());
    }
}
