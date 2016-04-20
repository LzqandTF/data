package com.chinaebi.dao;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ChannelMccCalculate;
import com.chinaebi.utils.mybaits.Page;

public interface ChannelMccCalculateDao {
	/**
	 * 分页查询渠道扣率计算方式
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<ChannelMccCalculate> queryPageChannelMccCalculate(Page<ChannelMccCalculate> page,Map<String,Object> map);
	
	/**
	 * 通过id查询渠道扣率计算方式
	 * @param id
	 * @return
	 */
	public ChannelMccCalculate queryChannelMccCalculateById(int id);
	
	/**
	 * 返回所有渠道扣率计算方式集合
	 * @return
	 */
	public List<ChannelMccCalculate> queryAllChannelMccCalculate();
	
	/**
	 * 通过id删除渠道扣率计算方式
	 * @param id
	 * @return
	 */
	public int deleteChannelMccCalculate(int id);
	
	/**
	 * 修改渠道扣率计算方式
	 * @param ChannelMccCalculate
	 * @return
	 */
	public int updateChannelMccCalculate(ChannelMccCalculate ChannelMccCalculate);
	
	/**
	 * 添加渠道扣率计算方式
	 * @param ChannelMccCalculate
	 * @return
	 */
	public int addChannelMccCalculate(ChannelMccCalculate ChannelMccCalculate);
	
	
}
