package com.chinaebi.serviceImpl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.YlCupsErrorEntryDao;
import com.chinaebi.entity.YlCupsErrorEntry;
import com.chinaebi.exception.PageException;
import com.chinaebi.service.YlCupsErrorEntryService;
import com.chinaebi.utils.mybaits.Page;

/**
 * 银联差错录入ServiceImpl
 */
@Service(value = "ylCupsErrorEntryService")
public class YlCupsErrorEntryServiceImpl implements YlCupsErrorEntryService {

	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	// 实现依赖注入
	@Autowired
	@Qualifier(value = "ylCupsErrorEntryDao")
	private YlCupsErrorEntryDao ylCupsErrorEntryDao;

	@Override
	public Page<YlCupsErrorEntry> queryPageCupsErrorInput(Page<YlCupsErrorEntry> page, Map<String, Object> map) {
		Page<YlCupsErrorEntry> page2 = null;
		try {
			page2 = ylCupsErrorEntryDao.queryPageCupsErrorInput(page, map);
			if(page2.getResult() == null){
				logger.error("获取银联差错内部来源交易数据为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return page2;
	}

	@Override
	public int addYlcupsErrorEntry(YlCupsErrorEntry ylcupsErrorEntry) {
		int flag = 0;
		try {
			flag = ylCupsErrorEntryDao.addYlcupsErrorEntry(ylcupsErrorEntry);
			if (flag == 0) {
				logger.error("ylcupsErrorEntryDao.addYlcupsErrorEntry 银联差错录入失败");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public YlCupsErrorEntry queryCupsErrorInputDetail(String id) {
		return ylCupsErrorEntryDao.queryCupsErrorInputDetail(id);
	}

	@Override
	public int updateTradeStatus(Map<String, Object> map) {
		int flag = 0;
		try {
			flag = ylCupsErrorEntryDao.updateTradeStatus(map);
			if (flag == 0) {
				logger.error("修改银联差错录入数据状态出错...");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public int updatePass(Map<String, Object> map) {
		int flag = 0;
		try {
			flag = ylCupsErrorEntryDao.updatePass(map);
			if (flag == 0) {
				logger.error("银联差错审批通过操作失败...");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public int updateReject(YlCupsErrorEntry ylCupsErrorEntry) {
		int flag = 0;
		try {
			flag = ylCupsErrorEntryDao.updateReject(ylCupsErrorEntry);
			if (flag == 0) {
				logger.error("银联差错审批驳回操作失败...");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public int updateData(YlCupsErrorEntry ylCupsErrorEntry) {
		int flag = 0;
		try {
			flag = ylCupsErrorEntryDao.updateData(ylCupsErrorEntry);
			if (flag == 0) {
				logger.error("根据ID修改银联差错数据失败...");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public Page<YlCupsErrorEntry> queryPageNoCommitData(Page<YlCupsErrorEntry> page, Map<String, Object> map) {
		Page<YlCupsErrorEntry> pageList = null;
		try {
			pageList = ylCupsErrorEntryDao.queryPageNoCommitData(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("cupsErrorInputDao.queryPageNoCommitData(page, map)  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public Page<YlCupsErrorEntry> queryNeedApprovalData(Page<YlCupsErrorEntry> page, Map<String, Object> map) {
		Page<YlCupsErrorEntry> pageData = null;
		try {
			pageData = ylCupsErrorEntryDao.queryPageNeedApprovalData(page, map);
			if(pageData.getResult() == null){
				logger.error("获取银联差错待审批数据为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageData;
	}

	@Override
	public int queryDataByReqSysStance(String reqSysStance) {
		int count = 0;
		try {
			count = ylCupsErrorEntryDao.queryDataByReqSysStance(reqSysStance);
		} catch (Exception e) {
			logger.error("根据流水号" + reqSysStance + "查询银联查询数据出错：" + e.getMessage());
		}
		return count;
	}

	@Override
	public int addOriginalData(YlCupsErrorEntry ylCupsErrorEntry) {
		int count = 0;
		try {
			count = ylCupsErrorEntryDao.addOriginalData(ylCupsErrorEntry);
			if (count == 0) {
				logger.error("无原交易录入失败  cupsErrorInputDao.addOriginalData(ylCupsErrorEntry)");
			}
		} catch (Exception e) {
			logger.error("银联差错录入  无原交易录入失败" + e.getMessage());
		}
		return count;
	}
}
