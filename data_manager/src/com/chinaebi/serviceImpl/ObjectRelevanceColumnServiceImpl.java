package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.ObjectRelevanceColumnDao;
import com.chinaebi.entity.ObjectRelevanceColumn;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.ObjectRelevanceColumnService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "objectRelevanceColumnService")
public class ObjectRelevanceColumnServiceImpl implements ObjectRelevanceColumnService{
	
	private static final Logger log =LoggerFactory.getLogger(ObjectRelevanceColumnServiceImpl.class); 
	
	@Autowired
	@Qualifier(value = "objectRelevanceColumnDao")
	private ObjectRelevanceColumnDao objectRelevanceColumnDao;

	@Override
	public Page<ObjectRelevanceColumn> queryPageObjectRelevanceColumn(Page<ObjectRelevanceColumn> page,
			Map<String, Object> map) {
		Page<ObjectRelevanceColumn> page_ = null;
		try{
			page_ = objectRelevanceColumnDao.queryPageObjectRelevanceColumn(page, map);
			if(page.getResult() == null){
				throw new PageException("objectRelevanceColumnDao.queryPageObjectRelevanceColumn(page,map)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public int insertObjectRelevanceColumn(ObjectRelevanceColumn objectRelevanceColumn) {
		int effectNum = 0;
		try{
			effectNum = objectRelevanceColumnDao.insertObjectRelevanceColumn(objectRelevanceColumn);
			if(!(effectNum > 0)){
				throw new InsertException("objectRelevanceColumnDao.insertObjectRelevanceColumn 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateObjectRelevanceColumn(ObjectRelevanceColumn objectRelevanceColumn) {
		int effectNum = 0;
		try{
			effectNum = objectRelevanceColumnDao.updateObjectRelevanceColumn(objectRelevanceColumn);
			if(!(effectNum > 0)){
				throw new UpdateException("objectRelevanceColumnDao.updateObjectRelevanceColumn 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteObjectRelevanceColumn(int id) {
		int effectNum = 0;
		try{
			effectNum = objectRelevanceColumnDao.deleteObjectRelevanceColumn(id);
			if(!(effectNum >= 0)){
				throw new DeleteException("objectRelevanceColumnDao.deleteObjectRelevanceColumn 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public ObjectRelevanceColumn queryObjectRelevanceColumnById(int id) {
		ObjectRelevanceColumn objectRelevanceColumn = null;
		try{
			objectRelevanceColumn = objectRelevanceColumnDao.queryObjectRelevanceColumnById(id);
			if(objectRelevanceColumn == null){
				throw new SelectException("objectRelevanceColumnDao.queryObjectRelevanceColumnById  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return objectRelevanceColumn;
	}

	@Override
	public List<ObjectRelevanceColumn> queryObjectRelevanceColumnList() {
		List<ObjectRelevanceColumn> list = null;
		try{
			list = objectRelevanceColumnDao.queryObjectRelevanceColumnList();
			if(list == null){
				throw new SelectException("objectRelevanceColumnDao.queryObjectRelevanceColumnList()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	
	public boolean deleteObjectRelevanceColumnCountByObjectId(int object_id,int file_type){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("object_id", object_id);
		map.put("file_type", file_type);
		int countNum = 0;
		try{
			countNum = objectRelevanceColumnDao.deleteObjectRelevanceColumnCountByObjectId(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		if(countNum >=0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<ObjectRelevanceColumn> queryObjectColumnList(ObjectRelevanceColumn objectRelevanceColumn) {
		List<ObjectRelevanceColumn> list = null;
		try{
			list = objectRelevanceColumnDao.queryObjectColumnList(objectRelevanceColumn);
			if(list == null){
				throw new SelectException("objectRelevanceColumnDao.queryObjectRelevanceColumn()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	public boolean deleteObjectRelevanceColumnCountByDzColumnId(int dz_column_id){
		int countNum = 0;
		try{
			countNum = objectRelevanceColumnDao.deleteObjectRelevanceColumnCountByDzColumnId(dz_column_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		if(countNum >=0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean updateObjectRelevanceColumnByRuleId(int rule_id){
		int countNum = 0;
		try{
			countNum = objectRelevanceColumnDao.updateObjectRelevanceColumnByRuleId(rule_id);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		if(countNum >0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int deleteObjectOrDzColumnId(Map<String,Object> map) {
		int countNum = 0;
		try{
			countNum = objectRelevanceColumnDao.deleteObjectOrDzColumnId(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return countNum;
	}
}
