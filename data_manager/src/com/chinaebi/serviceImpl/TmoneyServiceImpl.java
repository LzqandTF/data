package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.TmoneyDao;
import com.chinaebi.entity.Tmoney;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.SelectException;
import com.chinaebi.service.TmoneyService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "tmoneyService")
public class TmoneyServiceImpl implements TmoneyService {
	private static Logger logger = LoggerFactory.getLogger(TmoneyServiceImpl.class);
	
	@Autowired
	@Qualifier(value = "tmoneyDao")
	private TmoneyDao tmoneyDao;
	
	@Override
	public Page<Tmoney> queryPageBankAccountData(Page<Tmoney> page,
			Map<String, Object> map) {
		Page<Tmoney> pageList = null;
		try {
			pageList = tmoneyDao.queryPageBankAccountData(page, map);
			if (pageList == null) {
				throw new PageException("tmoneyDao.queryPageBankAccountData(page, map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("银行账户数据查询出错：" + e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<Tmoney> queryBankAccountDataList(Map<String, Object> map) {
		List<Tmoney> list = null;
		try {
			list = tmoneyDao.queryBankAccountDataList(map);
			if (list == null) {
				throw new SelectException("tmoneyDao.queryBankAccountDataList(map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("银行账户数据下载出错：" + e.getMessage());
		}
		return list;
	}

	@Override
	public Page<Tmoney> queryPageDyAccountData(Page<Tmoney> page,
			Map<String, Object> map) {
		Page<Tmoney> pageList = null;
		try {
			pageList = tmoneyDao.queryPageDyAccountData(page, map);
			if (pageList == null) {
				throw new PageException("tmoneyDao.queryPageDyAccountData(page, map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("查询电银账户数据列表出错：" + e.getMessage());
		}
		return pageList;
	}

	@Override
	public List<Tmoney> queryDyAccountDataList(Map<String, Object> map) {
		List<Tmoney> list = null;
		try {
			list = tmoneyDao.queryDyAccountDataList(map);
			if (list == null) {
				throw new SelectException("tmoneyDao.queryDyAccountDataList(map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("下载电银账户数据列表出错：" + e.getMessage());
		}
		return list;
	}
}
