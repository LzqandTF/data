package cn.com.chinaebi.dz.service;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.base.DzFileParsing;
import cn.com.chinaebi.dz.base.exception.DuizhangNotFoundException;
import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.util.MailSendInfoUtil;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.webservice.ManualParseDzFileType;

public class BankFileHandleService {
	
	private static final Log log = LogFactory.getLog(BankFileHandleService.class);
	
	private static cn.com.chinaebi.dz.object.dao.iface.EmailPoliceDAO emailPoliceDAO = cn.com.chinaebi.dz.object.dao.EmailPoliceDAO.getInstance();
	
	
	public static boolean dzFileParseHandle(ManualParseDzFileType type) throws Exception{
		boolean flag = false;
		
		int bankId = type.getBankId();
		
		BankInst bankInst = Backstage.getInstance().getBankInst(bankId);
		
		try{
			String parseDzFileClass = "";
			Class<?> c = null;
			if(type.getFileType() == 3){
				ResourceBundle resource = ResourceBundle.getBundle("conf");
				parseDzFileClass = resource.getString("ppfw_parsing_class");
				if(StringUtils.isBlank(parseDzFileClass)){
					parseDzFileClass = "cn.com.chinaebi.dz.file.parsing.Pos_CupsLfeParsing";
				}
				c = Class.forName(parseDzFileClass);
			}else{
				parseDzFileClass = bankInst.getParseDzFileClass();
				String[] parseDzFileClassArr = parseDzFileClass.split(";");
				
				c = Class.forName(type.getFileType() == 1?parseDzFileClassArr[0]:parseDzFileClassArr[1]);
			}
			
			DzFileParsing dzFileParsing = (DzFileParsing) c.newInstance();
			String fileName = new String(type.getSummaryFileName().getBytes("ISO-8859-1"),"utf-8");
			dzFileParsing.parseBankDzFile(fileName,type.getSummaryFileDate(),bankInst);
			
			flag = true;
			
		}catch(Exception e){
			log.error("解析"+bankInst.getBankName()+"对账文件抛出异常:"+e);
			if(e instanceof DuizhangNotFoundException){
				MailSendInfoUtil.sendEmailForBankInstError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, type.getSummaryFileDate(), bankInst,MailSendInfoUtil.NULL_FILE_ERROR);
			}else if(e instanceof FileNotFoundException){
				MailSendInfoUtil.sendEmailForBankInstError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, type.getSummaryFileDate(), bankInst,MailSendInfoUtil.NO_FILE_ERROR);
			}else{
				MailSendInfoUtil.sendEmailForBankInstError(MailSendInfoUtil.DZ_FILE_PARSE, emailPoliceDAO, type.getSummaryFileDate(), bankInst,MailSendInfoUtil.PARSE_FILE_ERROR);
			}
			throw e;
		}
		return flag;
	}
}
