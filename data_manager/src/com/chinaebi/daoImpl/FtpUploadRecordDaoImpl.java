package com.chinaebi.daoImpl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.chinaebi.dao.FtpUploadRecordDao;
import com.chinaebi.entity.FtpUploadRecord;
import com.chinaebi.utils.mybaits.MyBatisDao;
import com.chinaebi.utils.mybaits.Page;

@Component(value = "ftpUploadRecordDao")
public class FtpUploadRecordDaoImpl extends MyBatisDao implements FtpUploadRecordDao{
	public Page<FtpUploadRecord> queryPageFtpUploadRecord(Page<FtpUploadRecord> page,Map<String,Object> map){
		return selectPage(page, "FtpUploadRecord.queryPageFtpUploadRecord","FtpUploadRecord.selectCount",map);
	}
	
	public int insertFtpUploadRecord(FtpUploadRecord ftpUploadRecord){
		return getSqlSession().insert("FtpUploadRecord.insertFtpUploadRecord",ftpUploadRecord);
	}
	
	public int updateFtpUploadRecord(FtpUploadRecord ftpUploadRecord){
		return getSqlSession().update("FtpUploadRecord.updateFtpUploadRecord",ftpUploadRecord);
	}
	
	public FtpUploadRecord queryFtpUploadRecordByObjectIdAndDate(Map<String,Object> map){
		return (FtpUploadRecord) getSqlSession().selectOne("FtpUploadRecord.queryFtpUploadRecordByObjectIdAndDate", map);
	}
}
