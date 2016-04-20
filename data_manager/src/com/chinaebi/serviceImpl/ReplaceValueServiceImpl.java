package com.chinaebi.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.ReplaceValueDao;
import com.chinaebi.entity.ReplaceValue;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.service.ReplaceValueService;

@Service(value = "replaceValueService")
public class ReplaceValueServiceImpl implements ReplaceValueService{

	private static Logger log = LoggerFactory.getLogger(ReplaceValueServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "replaceValueDao")
	private ReplaceValueDao replaceValueDao;
	
	@Override
	public int insertRepalceValue(ReplaceValue repalceValue) {
		int effectNum = 0;
		try{
			effectNum = replaceValueDao.insertRepalceValue(repalceValue);
			if(effectNum == 0){
				throw new InsertException("添加值替换数据失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteRepalceValue(int id) {
		int effectNum = 0;
		try{
			effectNum = replaceValueDao.deleteRepalceValue(id);
			if(effectNum == 0){
				throw new DeleteException("删除值替换数据失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateRepalceValue(ReplaceValue repalceValue) {
		int effectNum = 0;
		try{
			effectNum = replaceValueDao.updateRepalceValue(repalceValue);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public ReplaceValue queryReplaceValueById(int id) {
		ReplaceValue replaceValue = null;
		try{
			replaceValue = replaceValueDao.queryReplaceValueById(id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return replaceValue;
	}

	@Override
	public List<ReplaceValue> queryReplaceValueByRuleId(int rule_id) {
		List<ReplaceValue> replaceValue = null;
		try{
			replaceValue = replaceValueDao.queryReplaceValueByRuleId(rule_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return replaceValue;
	}

	@Override
	public List<ReplaceValue> queryReplaceValueList() {
		List<ReplaceValue> list = null;
		try{
			list = replaceValueDao.queryReplaceValueList();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	
	public int queryReplaceValueCountByRuleIdAndOldValue(ReplaceValue replaceValue){
		int effectNum = 0;
		try{
			effectNum = replaceValueDao.queryReplaceValueCountByRuleIdAndOldValue(replaceValue);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
	public int deleteReplaceValueByRuleId(int rule_id){
		int effectNum = 0;
		try{
			effectNum = replaceValueDao.deleteReplaceValueByRuleId(rule_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}
}
