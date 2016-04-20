package com.chinaebi.dao;

import java.util.Map;

import com.chinaebi.entity.DuizhangErrorData;
import com.chinaebi.utils.mybaits.Page;

/**
 * 差错对账文件处理Dao接口
 * @author wufei
 *
 */
public interface DuizhangErrorDataDao {
	/**
	 * 差错对账单查询  分页查询
	 * @param page  分页参数
	 * @param map  查询参数
	 * @return
	 */
	public Page<DuizhangErrorData> queryPageDzErrorCupsLst(Page<DuizhangErrorData> page,Map<String,Object> map);
	
	/**
	 * 差错单查询  详情查询
	 * @param map
	 * @return
	 */
	public DuizhangErrorData queryDetailById(Map<String, Object> map);
	
}

