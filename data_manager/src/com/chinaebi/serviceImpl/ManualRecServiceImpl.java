package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.ManualRecDao;
import com.chinaebi.entity.ManualRec;
import com.chinaebi.entity.Merchants;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.service.ManualRecService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "manualRecService")
public class ManualRecServiceImpl implements ManualRecService {
	private static final Logger logger = LoggerFactory.getLogger(ManualRecServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "manualRecDao")
	private ManualRecDao manualRecDao;
	
	@Override
	public Merchants queryMerInfoByMerCode(String merCode) {
		Merchants merchants = null;
		try {
			merchants = manualRecDao.queryMerInfoByMerCode(merCode);
			if (merchants == null) {
				throw new SelectException("manualRecDao.queryMerInfoByMerCode(merCode)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return merchants;
	}

	@Override
	public int addManualRec(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = manualRecDao.addManualRec(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}
	
	@Override
	public Page<ManualRec> queryPageDataLst(Page<ManualRec> page,
			Map<String, Object> map) {
		Page<ManualRec> pageList = null;
		try {
			pageList = manualRecDao.queryPageDataLst(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("manualRecDao.queryPageDataLst(pageList, map) 查询结果为NULL");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return pageList;
	}

	@Override
	public int updateDataById(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = manualRecDao.updateDataById(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateDataToFail(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = manualRecDao.updateDataToFail(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public List<ManualRec> queryDataLst(Map<String, Object> map) {
		List<ManualRec> list = null;
		try {
			list = manualRecDao.queryDataLst(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	@Override
	public List<ManualRec> queryMerManualRec(String mer_code) {
		List<ManualRec> manualRecList = null;
		try{
			manualRecList = manualRecDao.queryMerManualRec(mer_code);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return manualRecList;
	}

	@Override
	public int updateMerManualRecJsStatus(Map<String, Object> map) {
		int result = 0;
		try{
			result = manualRecDao.updateMerManualRecJsStatus(map);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public Page<ManualRec> queryPageAllData(Page<ManualRec> page, Map<String, Object> map) {
		Page<ManualRec> pageList = null;
		try {
			pageList = manualRecDao.queryPageAllData(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("manualRecDao.queryPageAllData(pageList, map) 查询结果为NULL");
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return pageList;
	}
}
