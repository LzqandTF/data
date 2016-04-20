package com.chinaebi.dao;

import java.util.Map;

import com.chinaebi.entity.DzFileInfo;
import com.chinaebi.utils.mybaits.Page;

public interface DzFileInfoDao {
	/**
	 * 分页查询对账文件生成
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<DzFileInfo> queryPageDzFileInfo(Page<DzFileInfo> page,Map<String,Object> map);
	
	public DzFileInfo queryDzFileInfoById(int id);
}
