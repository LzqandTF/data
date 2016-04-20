package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.FtpUploadRecordDao;
import com.chinaebi.entity.FtpUploadRecord;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.FtpUploadRecordService;
import com.chinaebi.utils.mybaits.Page;

@Service(value ="ftpUploadRecordService")
public class FtpUploadRecordServiceImpl implements FtpUploadRecordService{
	protected Logger log = LoggerFactory.getLogger(FtpUploadRecordServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "ftpUploadRecordDao")
	private FtpUploadRecordDao ftpUploadRecordDao;
	
	public Page<FtpUploadRecord> queryPageFtpUploadRecord(Page<FtpUploadRecord> page,Map<String,Object> map){
		Page<FtpUploadRecord> page_ = null;
		try{
			page_ = ftpUploadRecordDao.queryPageFtpUploadRecord(page, map);
			if(page_.getResult() == null && page_.getResult().size() == 0){
				throw new SelectException("分页查询结果为null");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}
	
	public int insertFtpUploadRecord(FtpUploadRecord ftpUploadRecord){
		int effectNum = 0;
		try{
			effectNum = ftpUploadRecordDao.insertFtpUploadRecord(ftpUploadRecord);
			if(effectNum == 0){
				throw new InsertException("FTP上传记录数据添加失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
	
	public int updateFtpUploadRecord(FtpUploadRecord ftpUploadRecord){
		int effectNum = -1;
		try{
			effectNum = ftpUploadRecordDao.updateFtpUploadRecord(ftpUploadRecord);
			if(effectNum == -1){
				throw new UpdateException("FTP上传记录数据修改失败");
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
	
	public FtpUploadRecord queryFtpUploadRecordByObjectIdAndDate(Map<String,Object> map){
		FtpUploadRecord ftpUploadRecord = null;
		try{
			ftpUploadRecord = ftpUploadRecordDao.queryFtpUploadRecordByObjectIdAndDate(map);
			if(ftpUploadRecord == null){
				throw new SelectException("通过系统ID和清算日期未查到指定的FTP上传记录");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return ftpUploadRecord;
	}
}
