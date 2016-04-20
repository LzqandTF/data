package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.ReasonCodeDao;
import com.chinaebi.entity.ReasonCode;
import com.chinaebi.exception.PageException;
import com.chinaebi.service.ReasonCodeDaoService;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "reasonCodeDaoService")
public class ReasonCodeDaoServiceImpl implements ReasonCodeDaoService {

	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	@Qualifier(value = "reasonCodeDao")
	private ReasonCodeDao reasonCodeDao;
	
	@Override
	public Page<ReasonCode> queryPageReasonCode(Page<ReasonCode> page,
			Map<String, Object> map) {
		Page<ReasonCode> pageList = null;
		try {
			pageList = reasonCodeDao.queryPageReasonCode(page, map);
			if(pageList.getResult() == null){
				throw new PageException("reasonCodeDao.queryPageReasonCode(page,map) 查询数据为NULL");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}

	@Override
	public boolean deleteReasonCode(String id) {
		try {
			int flag = reasonCodeDao.deleteReasonCode(id);
			if(flag > 0){
				return true;
			}else{
				logger.error(id + "reasonCodeDao.deleteReasonCode 删除失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean updateReasonCode(ReasonCode reasonCode) {
		try {
			int flag = reasonCodeDao.updateReasonCode(reasonCode);
			if(flag > 0){
				return true;
			}else{
				logger.error(reasonCode.getId() + "reasonCodeDao.updateReasonCode 修改失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean addReasonCode(ReasonCode reasonCode) {
		try {
			int flag = reasonCodeDao.addReasonCode(reasonCode);
			if(flag > 0){
				return true;
			}else{
				logger.error(reasonCode.getReason_desc() + "reasonCodeDao.addReasonCode 添加失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public List<ReasonCode> getReasonCodeLstId(ReasonCode reasonCode) {
		List<ReasonCode> list = null;
		try {
			list = reasonCodeDao.getReasonCodeLstId(reasonCode);
			if(list == null){
				logger.error(reasonCode.getId() + " reasonCodeDao.getReasonCodeLstId 获取差错方式原因码为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	@Override
	public List<ReasonCode> getReasonCodeLst() {
		List<ReasonCode> list = null;
		try {
			list = reasonCodeDao.getReasonCodeLst();
			if(list == null){
				logger.error(" reasonCodeDao.getReasonCodeLst 获取差错方式原因码为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

}
