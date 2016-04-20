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
import java.util.ResourceBundle;
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
 * 银联upmp对账文件解析
 *	@author sp
 */
public class Ryf_UpmpParsing implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Ryf_UpmpParsing.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	static ResourceBundle resource = ResourceBundle.getBundle("conf");
	static String save_fwdInstIdCode = resource.getString("save_fwdInstIdCode");
	
	static String deduct_stlm_date = "";
	
    public  void parseBankDzFile(String filePath,String date,BankInst bankInst) throws Exception{
    	Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
        try {
            String encoding="utf-8";
        	Calendar calendar = Calendar.getInstance();//系统当前时间
    		
    		if(StringUtils.isBlank(date)){
    			calendar.add(Calendar.DATE, -1);
    		}else{
    			calendar.setTime(DYDataUtil.getSimpleDateFormat("yyyyMMdd").parse(date));
    		}
    		String file_year = DYDataUtil.getSimpleDateFormat("yyyy").format(calendar.getTime());
    		deduct_stlm_date = DYDataUtil.getSimpleDateFormat(DataStatus.date_format_2).format(calendar.getTime());
    		
    		log.info("文件解析路径:"+filePath);
    		File file = new File(filePath);
    		if(file.isFile() && file.exists()){//判断是否为当天对账文件
    			
				String[] bankData = new String[39];
				
				int readyInsertSqlNum = 0;
				boolean insert_flag = false;
				int totalExcuteNum = 0;
				int sucessExcuteNum = 0;
				
				String baseSql = "INSERT IGNORE INTO duizhang_upmp_lst(id,acqInstIdCode,fwdInstIdCode,reqSysStance,reqTime,outAccount,tradeAmount,portionAmount,tradeFee,msgType,process,merType,termId,merCode,deductSysReference,reqType,authorizationCode,rcvgInstIdCode,origDataStance,deductSysResponse,reqInputType,acceptorReceiveFee,acceptorPayFee,throughServiceFee,convertShow,cardNumber,termReadAbility,idConditionCode,origDataTime,issuingBankCode,tradeAdress,terminalType,eci,byStagesFee,otherInfo,dz_file_name,inst_name,whetherTk,deduct_stlm_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				session = hlogDAO.getCurrentSession();
				conn = session.connection();
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(baseSql);
    			
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String[] dataArr = null;
                
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(!StringUtils.isBlank(lineTxt)){
                		dataArr = formatString(lineTxt).substring(1, formatString(lineTxt).length()-1).split(",");
                		if(StringUtils.isNotBlank(save_fwdInstIdCode)){
    		    			if(StringUtils.isNotBlank(dataArr[1])){
    		    				if(save_fwdInstIdCode.indexOf(trimMySelf(dataArr[1])) == -1 || trimMySelf(dataArr[9]).equals("300000")){
    		    					continue;
    		    				}
    		    			}
    		    		}
                		if(dataArr != null && dataArr.length > 33){
							bankData[0] = bankInst.getId()+Long.valueOf(dataArr[13].trim()).toString()+trimMySelf(dataArr[3]).replaceAll(" ", "");//主键组成元素：网关号+流水号+交易时间
							bankData[1] = trimMySelf(dataArr[0]);
							bankData[2] = trimMySelf(dataArr[1]);
							bankData[3] = StringUtils.isBlank(dataArr[13])?"":Long.valueOf(dataArr[13].trim()).toString();
							bankData[4] = trimMySelf((file_year+dataArr[3]).replaceAll(" ", ""));
							bankData[5] = trimMySelf((file_year+dataArr[4]).replaceAll(" ", ""));
							bankData[6] = StringUtils.isBlank(dataArr[5])?"0.00":String.format("%.2f", Double.valueOf(dataArr[5])/100);
							bankData[7] = trimMySelf(dataArr[6]);
							bankData[8] = StringUtils.isBlank(dataArr[7])?"0.00":String.format("%.2f", Double.valueOf(dataArr[7])/100);
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
							bankData[34] = trimMySelf(dataArr[33]);
							bankData[35] = file.getName();
							bankData[36] = bankInst.getBankName();
							bankData[37] = trimMySelf(dataArr[8]).equals("0220")?"1":"0";
							bankData[38] = deduct_stlm_date;
							
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
    
}
