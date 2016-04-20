package com.chinaebi.service;

import java.util.Map;

import com.chinaebi.entity.FtpUploadRecord;
import com.chinaebi.utils.mybaits.Page;

public interface FtpUploadRecordService {
	/**
	 * FTP上传记录分页查询
	 * @param page ：页数
	 * @param map ：查询where条件参数
	 * @return Page<FtpUploadRecord>
	 */
	public Page<FtpUploadRecord> queryPageFtpUploadRecord(Page<FtpUploadRecord> page,Map<String,Object> map);
	
	/**
	 * 添加FTP文件上传记录
	 * @param ftpUploadRecord
	 */
	public int insertFtpUploadRecord(FtpUploadRecord ftpUploadRecord);
	
	/**
	 * 修改FTP文件上传记录
	 * @param ftpUploadRecord
	 */
	public int updateFtpUploadRecord(FtpUploadRecord ftpUploadRecord);
	
	/**
	 * 查询FTP文件上传记录
	 * @param map ：参数是清算日期 deduct_stlm_date
	 * @return FtpUploadRecord
	 */
	public FtpUploadRecord queryFtpUploadRecordByObjectIdAndDate(Map<String,Object> map);
}
