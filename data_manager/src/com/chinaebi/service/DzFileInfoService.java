package com.chinaebi.service;

import java.util.Map;

import com.chinaebi.entity.DzFileInfo;
import com.chinaebi.utils.mybaits.Page;

public interface DzFileInfoService {
	/**
	 * 分页查询对账文件生成
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<DzFileInfo> queryPageDzFileInfo(Page<DzFileInfo> page,Map<String,Object> map);
	/**
	 * 根据渠道ID 和 交易时间 手动生成对账总表 
	 * @param instId
	 * @param tradeTime
	 * @return
	 */
	public int createDzFile(int instId , String tradeTime,Integer fileType,int object_id);
	public DzFileInfo queryDzFileInfoById(int id);
}
