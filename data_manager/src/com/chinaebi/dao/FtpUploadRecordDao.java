package com.chinaebi.dao;

import java.util.Map;

import com.chinaebi.entity.FtpUploadRecord;
import com.chinaebi.utils.mybaits.Page;

public interface FtpUploadRecordDao {
	public Page<FtpUploadRecord> queryPageFtpUploadRecord(Page<FtpUploadRecord> page,Map<String,Object> map);
	
	public int insertFtpUploadRecord(FtpUploadRecord ftpUploadRecord);
	
	public int updateFtpUploadRecord(FtpUploadRecord ftpUploadRecord);
	
	public FtpUploadRecord queryFtpUploadRecordByObjectIdAndDate(Map<String,Object> map);
}
