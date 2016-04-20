package com.chinaebi.service;

import java.util.List;
import java.util.Map;

import com.chinaebi.entity.ChannelMccCalculate;
import com.chinaebi.utils.mybaits.Page;

public interface ChannelMccCalculateService {
	/**
	 * 分页查询渠道计算扣率方式
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<ChannelMccCalculate> queryPageChannelMccCalculate(Page<ChannelMccCalculate> page,Map<String,Object> map);
	
	/**
	 * 通过id查询渠道计算扣率方式
	 * @param id
	 * @return
	 */
	public ChannelMccCalculate queryChannelMccCalculateById(int id);
	
	/**
	 * 返回所有渠道计算扣率方式集合
	 * @return
	 */
	public List<ChannelMccCalculate> queryAllChannelMccCalculate();
	
	/**
	 * 通过id删除渠道计算扣率方式
	 * @param id
	 * @return
	 */
	public int deleteChannelMccCalculate(int id);
	
	/**
	 * 修改渠道计算扣率方式
	 * @param ChannelMccCalculate
	 * @return
	 */
	public int updateChannelMccCalculate(ChannelMccCalculate channelMccCalculate);
	
	/**
	 * 添加渠道计算扣率方式
	 * @param ChannelMccCalculate
	 * @return
	 */
	public int addChannelMccCalculate(ChannelMccCalculate channelMccCalculate);
	
	
}
