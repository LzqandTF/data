package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.RytUpmpDao;
import com.chinaebi.entity.RytRefundLog;
import com.chinaebi.entity.RytUpmp;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.service.RytUpmpService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "rytUpmpService")
public class RytUpmpServiceImpl implements RytUpmpService {
	private static Logger loggger = LoggerFactory.getLogger(RytUpmpServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "rytUpmpDao")
	private RytUpmpDao rytUpmpDao;

	@Override
	public Page<RytUpmp> queryPageRytUpmp(Page<RytUpmp> page, Map<String, Object> map) {
		Page<RytUpmp> pageList = null;
		try {
			pageList = rytUpmpDao.queryPageRtyUpmp(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("rytUpmpDao.queryPageRtyUpmp(page, map)  查询结果为NULL");
			}
		} catch (Exception e) {
			loggger.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public Page<RytRefundLog> queryPageRytRefundLog(Page<RytRefundLog> page,
			Map<String, Object> map) {
		Page<RytRefundLog> pageList = null;
		try {
			pageList = rytUpmpDao.queryPageRytRefundLog(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("rytUpmpDao.queryPageRytRefundLog(page, map)  查询结果为NULL");
			}
		} catch (Exception e) {
			loggger.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public RytUpmp queryDetailRytUpmp(Map<String, Object> map) {
		RytUpmp rytUpmp = null;
		try {
			rytUpmp = rytUpmpDao.queryDetail(map);
			if (rytUpmp == null) {
				throw new SelectException("rytUpmpDao.queryDetail(tseq)  查询结果为NULL");
			}
		} catch (Exception e) {
			loggger.error(e.getMessage());
		}
		return rytUpmp;
	}

	@Override
	public RytRefundLog querRytRefundLog(Map<String, Object> map) {
		RytRefundLog rytRefundLog = null;
		try {
			rytRefundLog = rytUpmpDao.queryDetailLog(map);
			if (rytRefundLog == null) {
				throw new SelectException("rytUpmpDao.queryDetailLog(id)  查询结果为NULL");
			}
		} catch (Exception e) {
			loggger.error(e.getMessage());
		}
		return rytRefundLog;
	}
	
	public Page<RytUpmp> queryPageRytUpmpForDz(Page<RytUpmp> page, Map<String, Object> map){
		Page<RytUpmp> pageList = null;
		try {
			pageList = rytUpmpDao.queryPageRtyUpmpForDz(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("rytUpmpDao.queryPageRtyUpmp(page, map)  查询结果为NULL");
			}
		} catch (Exception e) {
			loggger.error(e.getMessage());
		}
		return pageList;
	}
	public String queryTotalMoneyOfDz(Map<String,Object> map){
		String totalMoney = "";
		try{
			totalMoney = rytUpmpDao.queryTotalMoneyOfDz(map);
		}catch(Exception e){
			loggger.error(e.getMessage());
		}
		return totalMoney;
	}

	@Override
	public List<RytUpmp> queryRytDzDetailDataList(Map<String, Object> map) {
		List<RytUpmp> rytUpmpList = null;
		try {
			rytUpmpList = rytUpmpDao.queryRytDzDetailDataList(map);
			if (rytUpmpList != null && rytUpmpList.size() > 0) {
				return rytUpmpList;
			}
		} catch (Exception e) {
			loggger.error(e.getMessage());
		}
		return rytUpmpList;
	}

	@Override
	public int updateDataByTesq(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = rytUpmpDao.updateDataByTesq(map);
		} catch (Exception e) {
			loggger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateBkchkByTradeId(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = rytUpmpDao.updateBkchkByTradeId(map);
		} catch (Exception e) {
			loggger.error(e.getMessage());
		}
		return effectNum;
	}
}
