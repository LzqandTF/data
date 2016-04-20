package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.chinaebi.entity.BankInst;
import com.chinaebi.entity.DuizhangData;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.utils.mybaits.Page;

public interface DuizhangDataService {
	/**
	 * 手动解析对账文件
	 */
	public boolean manualParsingDzData(int bank_id,int inst_id,String summaryDate,String file_path,int file_type,int instType) ;
	
	/**
	 * 差错对账
	 * @param inst_id
	 * @param summaryDate
	 * @param implClass
	 * @return
	 */
	public boolean manualErrorDzData(int inst_id, String summaryDate,String implClass,int intType);
	/**
	 * 差错对账之前，还原差错数据数据状态
	 * @param inst_id
	 * @param summaryDate
	 * @return
	 */
	public boolean reductionErrorDataStatusType(int inst_id, String summaryDate,int intType);
	
//	/**
//	 * 手动对账之前，进行原始交易数据状态的还原
//	 * @param inst_id
//	 * @param summaryDate
//	 * @return
//	 */
//	public boolean reductionDataStatusType(int inst_id, String summaryDate,int instType);
	
	/**
	 * 查询线上对账文件中的数据
	 * @param map
	 * @return
	 */
	public List<DuizhangData> queryOnlineDzFileData(Map<String, Object> map);
	
	/**
	 * 手动上传对账文件
	 * @param bankInst  银行机构
	 * @param instInfo	扣款渠道
	 * @param file		上传文件
	 * @param date		日期
	 * @return
	 */
	public int uploadDzFile(BankInst bankInst,InstInfo instInfo,MultipartFile file,String date);
	
	/**
	 * 解析对账文件
	 * @param bankInst	银行机构
	 * @param instInfo	扣款渠道
	 * @param date		日期
	 * @param fileName	解析文件的文件名称
	 * @return
	 */
	public boolean parseDzFile(BankInst bankInst,InstInfo instInfo,String date,String fileName);
	
	/**
	 * 对账处理
	 * @param bankInst	银行机构
	 * @param instInfo	扣款渠道
	 * @param date		日期
	 * @return
	 */
	public boolean dzHandle(BankInst bankInst,InstInfo instInfo,String date);
	
	/**
	 * 查询银行对账单中，对账差错数据
	 * @param map
	 * @return
	 */
	public Page<DuizhangData> queryBankErrorData(Page<DuizhangData> page,Map<String, Object> map);
	
	/**
	 * 统计银行对账单中有效数据的总数
	 * @param map
	 * @return
	 */
	public int queryBankDataCountOfAll(Map<String,Object> map);
	
	/**
	 * 统计银行对账单中对账成功数据的总数
	 * @param map
	 * @return
	 */
	public int queryBankDataCountOfDzSucess(Map<String,Object> map);
	
	/**
	 * 统计银行对账单中对账可疑数据的总数
	 * @param map
	 * @return
	 */
	public int queryBankErrorDataCount(Map<String, Object> map);
	
	/**
	 * 查询银行对账可疑数据
	 * @param map
	 * @return
	 */
	public List<DuizhangData> queryBankErrorDataList(Map<String,Object> map);
}
