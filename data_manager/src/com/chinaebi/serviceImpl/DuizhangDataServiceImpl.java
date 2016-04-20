package com.chinaebi.serviceImpl;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzDocument;
import cn.com.chinaebi.dz.webservice.ManualHandlerAgainDzType;
import cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzDocument;
import cn.com.chinaebi.dz.webservice.ManualHandlerErrorDzType;
import cn.com.chinaebi.dz.webservice.ManualParseDzFileDocument;
import cn.com.chinaebi.dz.webservice.ManualParseDzFileType;
import cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusDocument;
import cn.com.chinaebi.dz.webservice.ReductionErrorDataStatusType;

import com.chinaebi.dao.DuizhangDataDao;
import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.DuizhangData;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.service.DuizhangDataService;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.FileUtil;
import com.chinaebi.utils.HttpUtil;
import com.chinaebi.utils.RequestUrlConf;
import com.chinaebi.utils.ReturnXmlHandler;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "duizhangDataService")
public class DuizhangDataServiceImpl implements DuizhangDataService{

	private static Logger log = LoggerFactory.getLogger(DuizhangDataServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "duizhangDataDao")
	private DuizhangDataDao duizhangDataDao;
	
	@Override
	public boolean manualParsingDzData(int bank_id,int inst_id, String summaryDate,String file_path,int file_type,int instType) {
		boolean flag = false;
		try {
			ManualParseDzFileDocument document = ManualParseDzFileDocument.Factory.newInstance();
			ManualParseDzFileType type = document.addNewManualParseDzFile();
			type.setBankId(bank_id);
			type.setInstId(Integer.valueOf(inst_id));
			type.setSummaryFileDate(summaryDate);
			type.setSummaryFileName(file_path);
			type.setFileType(file_type);
			type.setInstType(instType);
			type.setTrace("03");
			String xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			log.info("手动解析银行对账文件请求后台回执信息："+xmlString);
			flag = ReturnXmlHandler.xmlStringHandling(xmlString);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return flag;
	}
	/**
	 * 差错对账之前，还原差错数据数据状态
	 * @param inst_id
	 * @param summaryDate
	 * @return
	 */
	@Override
	public boolean reductionErrorDataStatusType(int inst_id, String summaryDate,int instType){
		String xmlString = "";
		try{
			ReductionErrorDataStatusDocument document = ReductionErrorDataStatusDocument.Factory.newInstance();
			ReductionErrorDataStatusType type = document.addNewReductionErrorDataStatus();
			type.setInstId(inst_id);
			type.setInstType(instType);
			type.setSummaryDate(summaryDate);
			type.setTrace("00006");
			log.info("差错对账之前，向后台发送还原差错数据数据状态请求");
			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				log.info("差错对账之前，还原差错数据数据状态成功");
				return true;
			}else{
				log.info("差错对账之前，还原差错数据数据状态失败");
				return false;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return false;
	}
	/**
	 * 差错对账
	 * @param inst_id
	 * @param summaryDate
	 * @param implClass
	 * @return
	 */
	@Override
	public boolean manualErrorDzData(int inst_id, String summaryDate,String implClass,int instType){
		String xmlString = "";
		try{
			ManualHandlerErrorDzDocument document = ManualHandlerErrorDzDocument.Factory.newInstance();
			ManualHandlerErrorDzType type = document.addNewManualHandlerErrorDz();
			type.setInstId(inst_id);
			type.setInstType(instType);
			type.setDzHandlerClass(implClass);
			type.setSummaryDate(summaryDate);
			type.setTrace("900001");
			log.info("向后台发送差错对账请求");
			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				log.info("差错对账成功");
				return true;
			}else{
				log.info("差错对账失败");
				return false;
			}
		}catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
	}
	
//	/**
//	 * 手动对账之前，将原始交易数据的状态还原
//	 */
//	@Override
//	public boolean reductionDataStatusType(int inst_id, String summaryDate,int instType) {
//		String xmlString = "";
//		try{
//			ReductionDataStatusDocument document = ReductionDataStatusDocument.Factory.newInstance();
//			ReductionDataStatusType type = document.addNewReductionDataStatus();
//			type.setInstId(inst_id);
//			type.setInstType(instType);
//			type.setSummaryDate(summaryDate);
//			type.setTrace("00050");
//			log.info("手动对账之前，向后台发送将原始交易数据的状态还原请求");
//			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
//			log.info("收到后台针对手动对账之前，将原始交易数据的状态还原请求的回执信息："+xmlString);
//			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
//			if(flag){
//				log.info("手动对账之前，将原始交易数据的状态还原成功");
//				return true;
//			}else{
//				log.info("手动对账之前，将原始交易数据的状态还原失败");
//				return false;
//			}
//		}catch(Exception e){
//			log.error(e.getMessage());
//		}
//		return false;
//	}
	
	@Override
	public List<DuizhangData> queryOnlineDzFileData(Map<String, Object> map) {
		List<DuizhangData> list = null;
		try {
			list = duizhangDataDao.queryOnlineDzFileData(map);
		} catch (Exception e) {
			log.error("查询线上对账文件数据出现异常：" + e.getMessage());
		}
		return list;
	}
	@Override
	public int uploadDzFile(BankInst bankInst, InstInfo instInfo,MultipartFile file, String date) {
		int result = -1;

		String dz_file_name_pattern = bankInst.getDz_file_name_pattern();//对账文件名称格式
		String dz_file_path = bankInst.getDz_file_path();//对账文件拉取路径
		
		if(StringUtils.isNotBlank(dz_file_name_pattern) && StringUtils.isNotBlank(dz_file_path)){
			try {
				log.info("当前银行网关"+bankInst.getBank_name()+",对账文件格式为"+dz_file_name_pattern+",当前上传文件名称为"+file.getOriginalFilename());
				
				String file_name = dz_file_name_pattern;
				
				String upload_file_name = file.getOriginalFilename();
				
				if(dz_file_name_pattern.contains(DataStatus.date_format_1)){
					file_name = file_name.replaceAll(DataStatus.date_format_1,"\\\\d{4}-\\\\d{2}-\\\\d{2}");
				}else if(dz_file_name_pattern.contains(DataStatus.date_format_2) && !dz_file_name_pattern.contains(DataStatus.date_format_4)){
					file_name = file_name.replaceAll(DataStatus.date_format_2,"\\\\d{8}");
				}else if(dz_file_name_pattern.contains(DataStatus.date_format_3) && !dz_file_name_pattern.contains(DataStatus.date_format_2) && !dz_file_name_pattern.contains(DataStatus.date_format_5) && !dz_file_name_pattern.contains(DataStatus.date_format_4)){
					file_name = file_name.replaceAll(DataStatus.date_format_3,"\\\\d{2}");
				}else if(dz_file_name_pattern.contains(DataStatus.date_format_4)){
					file_name = file_name.replaceAll(DataStatus.date_format_4,"\\\\d{14}");
				}else if(dz_file_name_pattern.contains(DataStatus.date_format_5) && !dz_file_name_pattern.contains(DataStatus.date_format_2) && !dz_file_name_pattern.contains(DataStatus.date_format_4)){
					file_name = file_name.replaceAll(DataStatus.date_format_5,"\\\\d{6}");
				}else if(dz_file_name_pattern.contains(DataStatus.date_format_6)){
					file_name = file_name.replaceAll(DataStatus.date_format_6,"\\\\d{1,2}.\\\\d{1,2}");
    			}
				
				if(dz_file_name_pattern.contains("*")){
					if(file_name.split("\\*").length > 0){
						file_name = file_name.split("\\*")[0];
					}else{
						file_name = "";
					}
    				if(upload_file_name.length() > file_name.indexOf("*")){
    					upload_file_name = upload_file_name.substring(0, dz_file_name_pattern.indexOf("*"));
    				}
    			}
				
				if(upload_file_name.matches(file_name)){
					File file_ = new File(dz_file_path);
					if(!file_.exists() && !file_.isDirectory()){
						file_.mkdir();
					}
					
					if(bankInst.getBank_type() == 1){//判断渠道是否为线上渠道，如果为线上渠道，则创建日期文件夹保存相应对账文件
						file_ = new File(dz_file_path,date.replaceAll("-", ""));
						
						if(file_.exists()){
							File[] files =  file_.listFiles();
							for (File file2 : files) {
								file2.delete();
							}
						}
						
						if(!file_.exists() && !file_.isDirectory()){
							file_.mkdir();
						}
						
						file_ = new File(dz_file_path+"/"+date.replaceAll("-", ""),file.getOriginalFilename());
						if(!file_.exists()){
							file_.createNewFile();
						}
						
						dz_file_path = dz_file_path+"/"+date.replaceAll("-", "");//添加日期文件夹
					}
					
					FileUtil.inputstreamToFile(file.getInputStream(), new File(dz_file_path, file.getOriginalFilename()));
					result = 0;//上传文件成功
				}else{
					result = 2;//上传文件失败,文件格式不匹配
					log.info("上传文件格式不匹配，手动上传对账文件失败");
				}
			} catch (Exception e) {
				log.error("手动上传对账文件失败"+e);
				result = 1;//上传文件失败,系统错误
			}
		}else{
			result = 1;//上传文件失败,系统错误
		}
		
		return result;
	}
	@Override
	public boolean parseDzFile(BankInst bankInst, InstInfo instInfo, String date,String fileName) {
		boolean result = false;
		
		try {
			date = date.replaceAll("-", "");
			if(instInfo != null){
				if(instInfo.getWhether_t_1() == 0){//判断渠道对账文件日期是否需要做T-1处理
					Calendar calendar = Calendar.getInstance();//系统当前时间
					Date date_ = DateUtil.getSimpleDateFormat("yyyyMMdd").parse(date);
					calendar.setTime(date_);
					calendar.add(Calendar.DATE, 1);
					date = DateUtil.getSimpleDateFormat("yyyyMMdd").format(calendar.getTime());
				}
			}
			StringBuffer fileBuffer = new StringBuffer("");//文件全路径+文件名称
			fileBuffer.append(bankInst.getDz_file_path());
			if(bankInst.getBank_type() == 1){
				fileBuffer.append("/");
				fileBuffer.append(date);
			}
			fileBuffer.append("/");
			fileBuffer.append(fileName);
			
			if(instInfo != null){
				log.info("调用后台接口,解析对账文件,参数为:银行网关号--"+bankInst.getBank_id()+"扣款渠道ID"+instInfo.getInstId()+";对账文件存放路径"+fileBuffer.toString()+";文件日期"+date);
				result = manualParsingDzData(bankInst.getBank_id(),instInfo.getInstId(),date,fileBuffer.toString(),DataStatus.DZ_FILE,instInfo.getInst_type());
			}else{
				result = manualParsingDzData(bankInst.getBank_id(),0,date,fileBuffer.toString(),DataStatus.DZ_FILE,0);
			}
		} catch (ParseException e) {
			log.error("调用后台接口,解析对账文件失败"+e);
		}
		
		return result;
	}
	/**
	 * 手动对账
	 */
	@Override
	public boolean dzHandle(BankInst bankInst, InstInfo instInfo, String date) {
		String xmlString = "";
		try{
			ManualHandlerAgainDzDocument document = ManualHandlerAgainDzDocument.Factory.newInstance();
			ManualHandlerAgainDzType type = document.addNewManualHandlerAgainDz();
			type.setInstId(instInfo==null?0:instInfo.getInstId());
			type.setInstType(instInfo==null?0:instInfo.getInst_type());
			type.setSummaryDate(date);
			type.setInnertTradeHandler((instInfo!=null && instInfo.getWhetherInnerDz()==1)?true:false);
			type.setDzHandlerClass(bankInst.getTrade_dz_impl_class());
			type.setBankId(bankInst.getBank_id());
			type.setTrace("99999");
			log.info("向后台发送手动对账请求");
			xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			log.info("收到后台关于手动对账请求的回执信息："+xmlString);
			boolean flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				log.info("手动对账成功");
				return true;
			}else{
				log.info("手动对账失败");
				return false;
			}
		}catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
	}
	@Override
	public Page<DuizhangData> queryBankErrorData(Page<DuizhangData> page,Map<String, Object> map){
		Page<DuizhangData> page_ = null;
		try{
			page_ = duizhangDataDao.queryBankErrorData(page,map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}
	@Override
	public int queryBankDataCountOfAll(Map<String, Object> map) {
		int result = 0;
		try{
			result = duizhangDataDao.queryBankDataCountOfAll(map);
			log.info("参数：银行对账单名称--"+map.get("bankTable")+";清算日期--"+map.get("deduct_stlm_date")+",查询统计对账单有效数据总数为"+result);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
	@Override
	public int queryBankDataCountOfDzSucess(Map<String, Object> map) {
		int result = 0;
		try{
			result = duizhangDataDao.queryBankDataCountOfDzSucess(map);
			log.info("参数：银行对账单名称--"+map.get("bankTable")+";清算日期--"+map.get("deduct_stlm_date")+",查询统计对账单对账成功数据总数为"+result);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
	
	@Override
	public int queryBankErrorDataCount(Map<String, Object> map){
		int result = 0;
		try{
			result = duizhangDataDao.queryBankErrorDataCount(map);
			log.info("参数：银行对账单名称--"+map.get("bankTable")+";清算日期--"+map.get("deduct_stlm_date")+",查询统计对账单对账可疑数据总数为"+result);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}
	@Override
	public List<DuizhangData> queryBankErrorDataList(Map<String, Object> map) {
		List<DuizhangData> list = null;
		try{
			list = duizhangDataDao.queryBankErrorDataList(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
}
