package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.DuizhangData;
import com.chinaebi.utils.mybaits.Page;

/**
 * 对账文件Dao接口
 * @author wufei
 *
 */
public interface DuizhangDataDao {
	
	/**
	 * 查询线上对账文件中的数据
	 * @param map
	 * @return
	 */
	public List<DuizhangData> queryOnlineDzFileData(Map<String, Object> map);
	
	/**
	 * 查询银行对账单中，对账差错数据
	 * @param map
	 * @return
	 */
	public Page<DuizhangData> queryBankErrorData(Page<DuizhangData> page,Map<String,Object> map);
	
	/**
	 * 统计银行对账单中有效数据的总数
	 * @param map
	 * @return
	 */
	public int queryBankDataCountOfAll(Map<String,Object> map);
	
	/**
	 * 统计银行对账单中对账成功数据的总数
	 * @param map
	 * @return
	 */
	public int queryBankDataCountOfDzSucess(Map<String,Object> map);
	
	/**
	 * 统计银行对账单中对账可疑数据的总数
	 * @param map
	 * @return
	 */
	public int queryBankErrorDataCount(Map<String, Object> map);
	
	/**
	 * 查询银行对账可疑数据
	 * @param map
	 * @return
	 */
	public List<DuizhangData> queryBankErrorDataList(Map<String,Object> map);
	
	
}
