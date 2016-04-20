package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ManualRec;
import com.chinaebi.entity.Merchants;
import com.chinaebi.utils.mybaits.Page;

/**
 * 
 * @author wufei
 *
 */
public interface ManualRecDao {
	/**
	 * 根据商户号获取商户信息
	 * @param merCode
	 * @return
	 */
	public Merchants queryMerInfoByMerCode(String merCode);
	
	/**
	 * 手工调账请求，向手工调账表中添加数据
	 * @param map
	 * @return
	 */
	public int addManualRec(Map<String, Object> map);
	
	/**
	 * 手工调账审核  分页查询数据
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<ManualRec> queryPageDataLst(Page<ManualRec> page, Map<String, Object> map);
	
	/**
	 * 手工调账审核  审核成功
	 * @param map
	 * @return
	 */
	public int updateDataById(Map<String, Object> map);
	
	/**
	 * 手工调账审核  审核失败
	 * @param map
	 * @return
	 */
	public int updateDataToFail(Map<String, Object> map);
	
	/**
	 * 手工调账查询  数据下载
	 * @param map
	 * @return
	 */
	public List<ManualRec> queryDataLst(Map<String, Object> map);
	
	/**
	 * 通过商户号查询商户已审核未结算的手工调账记录
	 * @param mer_code
	 * @return
	 */
	public List<ManualRec> queryMerManualRec(String mer_code);
	
	/**
	 * 修改商户手工调账数据结算状态
	 * @param map
	 * @return
	 */
	public int updateMerManualRecJsStatus(Map<String,Object> map);
	
	/**
	 * 手工调账查询
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<ManualRec> queryPageAllData(Page<ManualRec> page, Map<String, Object> map);
}
