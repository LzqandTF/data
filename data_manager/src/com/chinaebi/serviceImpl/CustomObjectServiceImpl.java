package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.CustomObjectDao;
import com.chinaebi.entity.CustomObject;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.CustomObjectService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "customObjectService")
public class CustomObjectServiceImpl implements CustomObjectService{
	
	private static final Logger log =LoggerFactory.getLogger(CustomObjectServiceImpl.class); 
	
	@Autowired
	@Qualifier(value = "customObjectDao")
	private CustomObjectDao customObjectDao;

	@Override
	public Page<CustomObject> queryPageCustomObject(Page<CustomObject> page,
			Map<String, Object> map) {
		Page<CustomObject> page_ = null;
		try{
			page_ = customObjectDao.queryPageCustomObject(page, map);
			if(page.getResult() == null){
				throw new PageException("customObjectDao.queryPageCustomObject(page,map)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public int insertCustomObject(CustomObject customObject) {
		int effectNum = 0;
		try{
			effectNum = customObjectDao.insertCustomObject(customObject);
			if(!(effectNum > 0)){
				throw new InsertException("customObjectDao.insertCustomObject 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateCustomObject(CustomObject customObject) {
		int effectNum = 0;
		try{
			effectNum = customObjectDao.updateCustomObject(customObject);
			if(!(effectNum > 0)){
				throw new UpdateException("customObjectDao.updateCustomObject 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteCustomObject(int object_id) {
		int effectNum = 0;
		try{
			effectNum = customObjectDao.deleteCustomObject(object_id);
			if(!(effectNum > 0)){
				throw new DeleteException("customObjectDao.deleteCustomObject 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public CustomObject queryCustomObjectById(int object_id) {
		CustomObject customObject = null;
		try{
			customObject = customObjectDao.queryCustomObjectById(object_id);
			if(customObject == null){
				throw new SelectException("customObjectDao.queryCustomObjectById  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return customObject;
	}

	@Override
	public List<CustomObject> queryCustomObjectList() {
		List<CustomObject> list = null;
		try{
			list = customObjectDao.queryCustomObjectList();
			if(list == null){
				throw new SelectException("customObjectDao.queryCustomObjectList()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	@Override
	public List<CustomObject> queryCustomObjectListOfInterfaceTypeNotAll() {
		List<CustomObject> list = null;
		try{
			list = customObjectDao.queryCustomObjectListOfInterfaceTypeNotAll();
			if(list == null){
				throw new SelectException("customObjectDao.queryCustomObjectListOfInterfaceTypeNotAll()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public boolean unionCheckobjectName(CustomObject customObject) {
		boolean flag = false;
		try{
			int effectNum = customObjectDao.unionCheckobjectName(customObject);
			if(effectNum > 0 ){
				flag = true;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return flag;
	}
	
	@Override
	public boolean unionCheckfileAddress(CustomObject customObject) {
		boolean flag = false;
		try{
			int effectNum = customObjectDao.unionCheckfileAddress(customObject);
			if(effectNum > 0 ){
				flag = true;
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public List<CustomObject> queryCustomObjectByFileType(
			int file_type) {
		List<CustomObject> list = null;
		try{
			list = customObjectDao.queryCustomObjectByFileType(file_type);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public CustomObject queryCustomObjectByName(String object_name) {
		CustomObject customObject = null;
		try{
			customObject = customObjectDao.queryCustomObjectByName(object_name);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return customObject;
	}
	
}
