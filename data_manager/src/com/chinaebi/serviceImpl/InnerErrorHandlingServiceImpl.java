package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.InnerErrorHandlingDao;
import com.chinaebi.entity.InnerErrorHandling;
import com.chinaebi.service.InnerErrorHandlingService;
import com.chinaebi.utils.mybaits.Page;
@Service(value ="innerErrorHandlingService")
public class InnerErrorHandlingServiceImpl implements InnerErrorHandlingService{
	
	//记录日志
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier(value = "innerErrorHandlingDao")
	private InnerErrorHandlingDao innerErrorHandlingDao;
	/**
	 * 分页查询内部差错处理方式信息
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<InnerErrorHandling> queryPageInnerErrorHandling(Page<InnerErrorHandling> page,Map<String,Object> map){
		return innerErrorHandlingDao.queryPageInnerErrorHandling(page, map);
	}
	/**
	 * 根据ID删除内部差错处理方式数据
	 * @param id
	 * @return
	 */
	public int deleteErrorHandling(int id){
		return innerErrorHandlingDao.deleteErrorHandling(id);
	}
	/**
	 * 修改内部差错处理方式数据
	 * @param innerErrorHandling
	 * @return
	 */
	public int updateErrorHandling(InnerErrorHandling innerErrorHandling){
		return innerErrorHandlingDao.updateErrorHandling(innerErrorHandling);
	}
	/**
	 * 添加内部差错处理方式数据
	 * @param innerErrorHandling
	 * @return
	 */
	public int addErrorHandling(InnerErrorHandling innerErrorHandling){
		return innerErrorHandlingDao.addErrorHandling(innerErrorHandling);
	}
	
	@Override
	public List<InnerErrorHandling> queryInnerErrorHandlingAll() {
		List<InnerErrorHandling> list = null;
		try {
			list= innerErrorHandlingDao.queryInnerErrorHandlingAll();
			if(list == null){
				logger.warn("获取内部差错处理方式数据为空");
			}
		} catch (Exception e) {
			logger.error("获取所有内部差错处理方式数据错误");
			logger.error(e.getMessage());
		}
		return list;
	}
	@Override
	public InnerErrorHandling queryInnerErrorHandling(int id) {
		InnerErrorHandling innerErrorHandling = null;
		try {
			innerErrorHandling = innerErrorHandlingDao.queryInnerErrorHandling(id);
			if(innerErrorHandling == null){
				logger.warn("根据ID获取内部差错处理方式数据为空");
			}
		} catch (Exception e) {
			logger.error("根据ID获取内部差错处理方式数据错误");
			logger.error(e.getMessage());
		}
		return innerErrorHandling;
	}
}
