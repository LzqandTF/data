package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.DzFileColumnConfDao;
import com.chinaebi.entity.DzFileColumnConf;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.DzFileColumnConfService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "dzFileColumnConfService")
public class DzFileColumnConfServiceImpl implements DzFileColumnConfService{
	
	private static final Logger log =LoggerFactory.getLogger(DzFileColumnConfServiceImpl.class); 
	
	@Autowired
	@Qualifier(value = "dzFileColumnConfDao")
	private DzFileColumnConfDao dzFileColumnConfDao;

	@Override
	public Page<DzFileColumnConf> queryPageDzFileColumnConf(Page<DzFileColumnConf> page,
			Map<String, Object> map) {
		Page<DzFileColumnConf> page_ = null;
		try{
			page_ = dzFileColumnConfDao.queryPageDzFileColumnConf(page, map);
			if(page.getResult() == null){
				throw new PageException("dzFileColumnConfDao.queryPageDzFileColumnConf(page,map)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public int insertDzFileColumnConf(DzFileColumnConf dzFileColumnConf) {
		int effectNum = 0;
		try{
			effectNum = dzFileColumnConfDao.insertDzFileColumnConf(dzFileColumnConf);
			if(!(effectNum > 0)){
				throw new InsertException("dzFileColumnConfDao.insertDzFileColumnConf 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateDzFileColumnConf(DzFileColumnConf dzFileColumnConf) {
		int effectNum = 0;
		try{
			effectNum = dzFileColumnConfDao.updateDzFileColumnConf(dzFileColumnConf);
			if(!(effectNum > 0)){
				throw new UpdateException("dzFileColumnConfDao.updateDzFileColumnConf 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteDzFileColumnConf(int object_id) {
		int effectNum = 0;
		try{
			effectNum = dzFileColumnConfDao.deleteDzFileColumnConf(object_id);
			if(!(effectNum > 0)){
				throw new DeleteException("dzFileColumnConfDao.deleteDzFileColumnConf 失败");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public DzFileColumnConf queryDzFileColumnConfById(int object_id) {
		DzFileColumnConf dzFileColumnConf = null;
		try{
			dzFileColumnConf = dzFileColumnConfDao.queryDzFileColumnConfById(object_id);
			if(dzFileColumnConf == null){
				throw new SelectException("dzFileColumnConfDao.queryDzFileColumnConfById  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return dzFileColumnConf;
	}

	@Override
	public List<DzFileColumnConf> queryDzFileColumnConfList() {
		List<DzFileColumnConf> list = null;
		try{
			list = dzFileColumnConfDao.queryDzFileColumnConfList();
			if(list == null){
				throw new SelectException("dzFileColumnConfDao.queryDzFileColumnConfList()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}
	
	@Override
	public List<DzFileColumnConf> queryDzFileColumnConfListByName(String attribute_name,String file_type){
		List<DzFileColumnConf> list = null;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("attribute_name", attribute_name);
			map.put("attribute_type", file_type);
			list = dzFileColumnConfDao.queryDzFileColumnConfListByName(map);
			if(list == null){
				throw new SelectException("dzFileColumnConfDao.queryDzFileColumnConfListByName()  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

	@Override
	public boolean unionCheckDzColumnName(DzFileColumnConf dzFileColumnConf) {
		boolean flag = false;
		try {
			int count = dzFileColumnConfDao.unionCheckDzColumnName(dzFileColumnConf);
			if(count > 0 ){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public boolean unionCheckDzColumnattr(DzFileColumnConf dzFileColumnConf) {
		boolean flag = false;
		try {
			int count = dzFileColumnConfDao.unionCheckDzColumnattr(dzFileColumnConf);
			if(count > 0 ){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}
	
}
