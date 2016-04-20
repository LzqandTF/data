package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.SettleFailReasonDao;
import com.chinaebi.entity.SettleFailReason;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.SettleFailReasonService;
import com.chinaebi.utils.mybaits.Page;

@Service(value ="settleFailReasonService")
public class SettleFailReasonServiceImpl implements SettleFailReasonService{
	
	private static final Logger log = LoggerFactory.getLogger(SettleFailReasonServiceImpl.class);
	
	@Autowired
	@Qualifier(value="settleFailReasonDao")
	private SettleFailReasonDao settleFailReasonDao;
	
	@Override
	public int addSettleFailReason(SettleFailReason settleFailReason) {
		int result = 0;
		try{
			result = settleFailReasonDao.addSettleFailReason(settleFailReason);
			if(result == -1){
				throw new InsertException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int updateSettleFailReason(SettleFailReason settleFailReason) {
		int result = 0;
		try{
			result = settleFailReasonDao.updateSettleFailReason(settleFailReason);
			if(result == -1){
				throw new UpdateException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteSettleFailReason(int reason_id) {
		int result = 0;
		try{
			result = settleFailReasonDao.deleteSettleFailReason(reason_id);
			if(result == -1){
				throw new DeleteException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public Page<SettleFailReason> queryPageSettleFailReason(
			Page<SettleFailReason> page, Map<String, Object> map) {
		Page<SettleFailReason> page_ = null;
		try{
			page_ = settleFailReasonDao.queryPageSettleFailReason(page, map);
			if(page_ == null){
				throw new SelectException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public SettleFailReason querySettleFailReasonById(int reason_id) {
		SettleFailReason settleFailReason = null;
		try{
			settleFailReason = settleFailReasonDao.querySettleFailReasonById(reason_id);
			if(settleFailReason == null){
				throw new SelectException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return settleFailReason;
	}
	
	@Override
	public List<SettleFailReason> getSettleFailReasonList(){
		List<SettleFailReason> list = null;
		try{
			list = settleFailReasonDao.getSettleFailReasonList();
			if(list== null){
				throw new SelectException();
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

}
