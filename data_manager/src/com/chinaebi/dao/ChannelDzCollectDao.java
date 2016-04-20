package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ChannelDzCollect;
import com.chinaebi.utils.mybaits.Page;

/**
 * 对账总表操作Dao接口
 * @author wufei
 *
 */
public interface ChannelDzCollectDao {
	/**
	 * 结算制表、结算制表确认、商户结算单查询(明细查询)
	 * @param page 分页参数
	 * @param map 查询参数
	 * @return
	 */
	public Page<ChannelDzCollect> queryPageChannelDzCollect(Page<ChannelDzCollect> page, Map<String, Object> map);
	
	/**
	 * 商户结算单明细下载
	 * @param map
	 * @return
	 */
	public List<ChannelDzCollect> queryChannelDzCollectDataLst(Map<String, Object> map);
	
	/**
	 * 修改总表数据清算状态
	 * @param trade_id  主键ID
	 * @return
	 */
	public int updateChannelDzCollectDataQsStatus(Map<String,Object> map);
	
	/**
	 * 对账明细下载分页查询方法
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<ChannelDzCollect> queryPageChannelDzData(Page<ChannelDzCollect> page,Map<String,Object> map);
	
	/**
	 * 对账明细下载数据查询方法
	 * @param map
	 * @return
	 */
	public List<ChannelDzCollect> queryChannelDzDataList(Map<String,Object> map);
	
	/**
	 * 统计对账明细交易金额，按照对账结果分组
	 * @param map
	 * @return
	 */
	public List<ChannelDzCollect> queryChannelDzDataTradeAmount(Map<String,Object> map);
	
	/**
	 * 将线上成功交易未对账数据保存至对账总表中
	 * @param channelDzCollect
	 * @return
	 */
	public int addNoDzDataToChannelDzCollect(ChannelDzCollect channelDzCollect);
}
