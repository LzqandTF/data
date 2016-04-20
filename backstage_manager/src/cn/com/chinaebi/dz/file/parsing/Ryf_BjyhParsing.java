package cn.com.chinaebi.dz.file.parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;

import cn.com.chinaebi.dz.base.DzFileParsing;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.dao.HlogDAO;
import cn.com.chinaebi.dz.object.util.FileUtil;

/**
 * 北京银行VAS 对账文件解析类
 * @author sp
 *
 */
public class Ryf_BjyhParsing implements DzFileParsing{
	private static final Log log = LogFactory.getLog(Ryf_BjyhParsing.class);
	private static cn.com.chinaebi.dz.object.dao.iface.HlogDAO hlogDAO = HlogDAO.getInstance();
	
	static String deduct_stlm_date = "";
	
	public void parseBankDzFile(String filePath,String date,BankInst bankInst)throws Exception{
		try{
			if (StringUtils.isNotBlank(date)) {
				deduct_stlm_date = date;
				File file = new File(filePath);
	            if(file.isFile() && file.exists()){
	            	parserBankFile(filePath,file.getName(),bankInst.getTkContext(),bankInst.getTkType().toString(),bankInst.getTkColumn()==null?0:bankInst.getTkColumn(),bankInst.getId(),bankInst.getBankName(),bankInst.isIsTk());
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
	public static void parserBankFile(String filePath,String fileName,String tk_context,String tk_type,int tk_column,int bankId,String bankName,boolean whetherTkFlag) throws Exception {   
		File inputXml=new File(filePath);   
		SAXReader saxReader = new SAXReader();   
		Connection conn = null;
		Session session = null;
		PreparedStatement stmt = null;
		try {
			String[] bankData = new String[12];
			
			int readyInsertSqlNum = 0;
			boolean insert_flag = false;
			int totalExcuteNum = 0;
			int sucessExcuteNum = 0;
			
			String baseSql = "INSERT ignore INTO duizhang_bjyh_vas_lst(merCode,reqSysStance,outAccount,payType,tradeAmount,reqTime,orderId,deduct_stlm_date,dz_file_name,inst_name,whetherTk,id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			session = hlogDAO.getCurrentSession();
			conn = session.connection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(baseSql);
			
			Document document = saxReader.read(inputXml);   
			Element employees=document.getRootElement(); 
			for(Iterator<?> i = employees.elementIterator(); i.hasNext();){   
				Element employee = (Element) i.next();   
				for(Iterator<?> j = employee.elementIterator(); j.hasNext();){   
					Element node=(Element) j.next();
					bankData[0] = node.attributeValue("MerCode");//商户号
					bankData[1] = node.attributeValue("OrderNum");//订单号
					bankData[2] = node.attributeValue("PayAcctNo");//卡号
					if(!"01".equals(node.attributeValue("PayType"))){//需剔除失败与退款交易   01支付 02支付不成功 03退款 04部分退款
						continue;
					}
					bankData[3] = node.attributeValue("PayType");//支付类型
					bankData[4] = StringUtils.isBlank(node.attributeValue("Amt"))?"0.00":String.format("%.2f", Double.valueOf(node.attributeValue("Amt"))/100);//交易金额
					bankData[5] = FileUtil.formatDataTimeToYYYYMMddhhmmss(node.attributeValue("TranTime"));//交易日期
					bankData[6] = node.attributeValue("OrderNum");//订单号
					bankData[7] = deduct_stlm_date;
					bankData[8] = fileName;
					bankData[9] = bankName;
					bankData[10] = FileUtil.getBankInstWhetherTk(bankData, tk_type, tk_column, tk_context, bankName,whetherTkFlag)+"";
					bankData[11] = bankId+bankData[1]+FileUtil.formatDataTimeToYYYYMMddhhmmss(bankData[5]);//银行机构ID + 订单号 + 交易日期
					
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
			stmt.executeBatch();
			conn.commit();
			
 	        if(totalExcuteNum != sucessExcuteNum){
             	log.debug(bankName+"-----"+deduct_stlm_date+"----对账单解析失败");
             	throw new Exception();
             }else{
             	log.info(bankName+"-----"+deduct_stlm_date+"----对账单解析成功");
             }
		} catch (DocumentException e) {   
			log.error(e);
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
}
