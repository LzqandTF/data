package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.exception.SelectException;

import com.chinaebi.dao.DuizhangErrorDataDao;
import com.chinaebi.entity.DuizhangErrorData;
import com.chinaebi.service.DuizhangErrorDataService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "duizhangErrorDataService")
public class DuizhangErrorDataServiceImpl implements DuizhangErrorDataService {
	private static final Logger log = LoggerFactory.getLogger(DuizhangErrorDataServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "duizhangErrorDataDao")
	private DuizhangErrorDataDao duizhangErrorDataDao;
	
	@Override
	public Page<DuizhangErrorData> queryPageDzErrorCupsLst(Page<DuizhangErrorData> page,Map<String,Object> map){
		Page<DuizhangErrorData> pageList = null;
		try{
			pageList = duizhangErrorDataDao.queryPageDzErrorCupsLst(page, map);
			if(pageList == null || pageList.getResult().size() == 0){
				throw new SelectException("duizhangErrorDataDao.queryPageDzErrorCupsLst(page, map)  查询结果为NULL");
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public DuizhangErrorData queryDetailById(Map<String, Object> map) {
		DuizhangErrorData duizhangErrorData = null;
		try {
			duizhangErrorData = duizhangErrorDataDao.queryDetailById(map);
			if (duizhangErrorData == null) {
				throw new SelectException("duizhangErrorDataDao.queryDetailById(map)  查询结果为null");
			}
		} catch (Exception e) {
			log.error("查询银联差错对账文件数据明细出错：" + e.getMessage());
		}
		return duizhangErrorData;
	}
}

